package com.jx530.excelimporter.model;

import com.alibaba.excel.annotation.ExcelProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 校区基本数据子类表
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class ODS_ZZXQJCSJ extends BaseModel {

    @ExcelProperty("市机构编码")
    @NotEmpty(message = "市机构编码不能为空")
    @Pattern(regexp = "\\d{6}", message = "市机构编码不正确，要求6位数字码")
    private String SHJGBM;

    @ExcelProperty("省机构名称")
    @NotEmpty(message = "省机构名称不能为空")
    @Size(max = 60, message = "学校名称过长，要求不能超过60个字符")
    private String SJGMC;

    @ExcelProperty("省机构编码")
    @NotEmpty(message = "省机构编码不能为空")
    @Pattern(regexp = "\\d{6}", message = "省机构编码不正确，要求6位数字码")
    private String SJGBM;

    @ExcelProperty("市机构名称")
    @NotEmpty(message = "市机构名称不能为空")
    @Size(max = 60, message = "市机构名称过长，要求不能超过60个字符")
    private String SHJGMC;

    @ExcelProperty("区县机构编码")
    @NotEmpty(message = "区县机构编码不能为空")
    @Pattern(regexp = "\\d{6}", message = "区县机构编码不正确，要求6位数字码")
    private String QXJGBM;

    @ExcelProperty("区县机构名称")
    @NotEmpty(message = "区县机构名称不能为空")
    @Size(max = 60, message = "区县机构名称过长，要求不能超过60个字符")
    private String QXJGMC;

    @ExcelProperty("学校机构代码")
    @NotEmpty(message = "学校机构代码不能为空")
    @Pattern(regexp = "\\w{10,20}", message = "学校机构代码不正确")
    private String XXJGDM;

    @ExcelProperty("学校机构名称")
    @NotEmpty(message = "学校机构名称不能为空")
    @Size(max = 60, message = "学校机构名称过长，要求不能超过60个字符")
    private String XXJGMC;

    @ExcelProperty("校区编号")
    @NotEmpty(message = "校区编号不能为空")
    @Size(max = 60, message = "校区编号过长，要求不能超过60个字符")
    private String XQBH;

    @ExcelProperty("校区名称")
    @NotEmpty(message = "校区名称不能为空")
    @Size(max = 180, message = "校区名称过长，要求不能超过180个字符")
    private String XQMC;

    @ExcelProperty("校区简称")
    @Size(max = 60, message = "校区简称过长，要求不能超过60个字符")
    private String XQJC;

    @ExcelProperty("校区所在地行政区划")
    @Size(max = 50, message = "校区所在地行政区划过长，要求不能超过50个字符")
    private String XQSZDXZQH;

    @ExcelProperty("校区地址")
    @Size(max = 300, message = "校区地址过长，要求不能超过300个字符")
    private String XQDZ;

    @ExcelProperty("校区邮政编码")
    @Size(max = 35, message = "校区邮政编码过长，要求不能超过300个字符")
    private String XQYZBM;

    @ExcelProperty("校区联系电话")
    @Size(max = 35, message = "校区联系电话过长，要求不能超过300个字符")
    private String XQLXDH;

    @ExcelProperty("校区负责人")
    @Size(max = 36, message = "校区负责人过长，要求不能超过36个字符")
    private String XQFZR;

    @ExcelProperty("校区教职工总数")
    @NotNull(message = "校区教职工总数不能为空")
    private Integer XQJZGZS;

    @ExcelProperty("校区学生总数")
    @NotNull(message = "校区学生总数不能为空")
    private Integer XQXSZS;

    @ExcelProperty("校区成立日期")
    private String XQCLRQ;

    @ExcelProperty("数据采集时间")
    @NotEmpty(message = "数据采集时间不能为空")
    private String SJCJSJ;

}
