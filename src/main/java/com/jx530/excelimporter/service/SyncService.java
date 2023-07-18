package com.jx530.excelimporter.service;

import cn.hutool.crypto.symmetric.SM4;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jx530.excelimporter.config.UploadProps;
import com.jx530.excelimporter.dao.BaseModifiableDao;
import com.jx530.excelimporter.dao.SyncProgressDao;
import com.jx530.excelimporter.dto.CommonResult;
import com.jx530.excelimporter.dto.SyncResponse;
import com.jx530.excelimporter.http.HttpApi;
import com.jx530.excelimporter.http.req.UploadEncryptRequest;
import com.jx530.excelimporter.http.req.UploadHeaders;
import com.jx530.excelimporter.http.req.UploadRequest;
import com.jx530.excelimporter.http.resp.LoginResponse;
import com.jx530.excelimporter.http.resp.UploadResponse;
import com.jx530.excelimporter.model.BaseModel;
import com.jx530.excelimporter.model.SyncProgress;
import com.jx530.excelimporter.model.SyncProgress.Progress;
import com.jx530.excelimporter.model.SyncType;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class SyncService {

    SyncProgressDao syncProgressDao;
    BeanFactory beanFactory;
    HttpApi httpApi;
    ObjectMapper objectMapper;
    UploadProps uploadProps;

    @SneakyThrows
    public void runSyncTask(SyncProgress sync, Consumer<SyncResponse> consumer) {
        SyncType syncTable = sync.getType();
        Class<? extends BaseModifiableDao<?>> daoClass = syncTable.getDaoClass();
        BaseModifiableDao<?> dao = beanFactory.getBean(daoClass);
        Progress progress = sync.getProgress();
        LocalDateTime current = progress.getCurrent();

        CommonResult<LoginResponse> login = httpApi.login("refresh_token", uploadProps.getAppKey(), uploadProps.getUsername(), uploadProps.getPassword());
        Assert.isTrue(login.getCode() == 200, "登录失败");
        LoginResponse loginResponse = login.getData();
        SM4 sm4 = new SM4(loginResponse.getAppSecret().getBytes(StandardCharsets.UTF_8));
        List<? extends BaseModel> batch;
        while (!CollectionUtils.isEmpty(batch = dao.findTop100By__modifiedBetween(current, LocalDateTime.now()))) {
            var req = new UploadRequest<>(syncTable.name(), batch);
            String json = objectMapper.writeValueAsString(req);
            UploadHeaders headers = new UploadHeaders(loginResponse, json);
            UploadEncryptRequest request = new UploadEncryptRequest(ByteUtils.toHexString(sm4.encrypt(json)));
            UploadResponse resp = httpApi.uploadData(headers.asMap(), request);
            progress.setCurrent(batch.stream().map(BaseModel::get__modified).max(Comparator.naturalOrder()).get());
            sync = syncProgressDao.save(sync);
            consumer.accept(new SyncResponse(sync, resp));
        }
    }

}
