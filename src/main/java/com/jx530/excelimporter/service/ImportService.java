package com.jx530.excelimporter.service;

import com.jx530.excelimporter.dto.CommonResult;
import com.pig4cloud.plugin.excel.vo.ErrorMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class ImportService {

    public <T> CommonResult<?> importData(JpaRepository<T, Long> dao,
                                          List<T> dataList,
                                          BindingResult bindingResult) {
        List<ErrorMessage> errorMessageList = (List<ErrorMessage>) bindingResult.getTarget();
        if (!CollectionUtils.isEmpty(errorMessageList)) {
            return CommonResult.badRequest(errorMessageList);
        }
        dao.saveAll(dataList);
        return CommonResult.ok("保存成功");
    }

}
