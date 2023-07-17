package com.jx530.excelimporter.controller;

import com.jx530.excelimporter.dao.ODS_ZZXQJCSJDao;
import com.jx530.excelimporter.dto.CommonResult;
import com.jx530.excelimporter.model.ODS_ZZXQJCSJ;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;
import com.pig4cloud.plugin.excel.vo.ErrorMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/import")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ExcelImportController {

    ODS_ZZXQJCSJDao ODS_ZZXQJCSJDao;

    @PostMapping(value = "/ODS_ZZXQJCSJ", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<?> ODS_ZZXQJCSJ(@RequestExcel(headRowNumber = 2, ignoreEmptyRow = true) List<ODS_ZZXQJCSJ> dataList,
                                        BindingResult bindingResult) {
        List<ErrorMessage> errorMessageList = (List<ErrorMessage>) bindingResult.getTarget();
        if (!CollectionUtils.isEmpty(errorMessageList)) {
            return CommonResult.badRequest(errorMessageList);
        }
        ODS_ZZXQJCSJDao.saveAll(dataList);
        return CommonResult.ok("保存成功");
    }

}
