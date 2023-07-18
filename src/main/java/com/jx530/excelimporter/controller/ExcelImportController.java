package com.jx530.excelimporter.controller;

import com.jx530.excelimporter.dao.ODS_ZZXQJCSJDao;
import com.jx530.excelimporter.dao.ODS_ZZXXGKJCSJDao;
import com.jx530.excelimporter.dto.CommonResult;
import com.jx530.excelimporter.model.ODS_ZZXQJCSJ;
import com.jx530.excelimporter.model.ODS_ZZXXGKJCSJ;
import com.jx530.excelimporter.service.ImportService;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/import")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ExcelImportController {

    ImportService importService;

    ODS_ZZXQJCSJDao ODS_ZZXQJCSJDao;

    ODS_ZZXXGKJCSJDao ODS_ZZXXGKJCSJDao;

    @PostMapping(value = "/ODS_ZZXQJCSJ")
    public CommonResult<?> ODS_ZZXQJCSJ(@RequestExcel(headRowNumber = 2, ignoreEmptyRow = true)
                                        List<ODS_ZZXQJCSJ> dataList,
                                        BindingResult bindingResult) {
        return importService.importData(ODS_ZZXQJCSJDao, dataList, bindingResult);
    }

    @PostMapping(value = "/ODS_ZZXXGKJCSJ")
    public CommonResult<?> ODS_ZZXXGKJCSJ(@RequestExcel(headRowNumber = 2, ignoreEmptyRow = true)
                                          List<ODS_ZZXXGKJCSJ> dataList,
                                          BindingResult bindingResult) {
        return importService.importData(ODS_ZZXXGKJCSJDao, dataList, bindingResult);
    }

}
