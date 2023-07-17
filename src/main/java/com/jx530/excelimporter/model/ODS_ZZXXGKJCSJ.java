package com.jx530.excelimporter.model;

import com.alibaba.excel.annotation.ExcelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 学校基本数据子类表
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ODS_ZZXXGKJCSJ extends BaseModel {
    @ExcelProperty("*学校代码")
    @NotEmpty(message = "学校代码不能为空")
    @Pattern(regexp = "\\d{10}", message = "学校代码格式不正确，要求10位数字码")
    private String XXDM;

    @ExcelProperty("*学校名称")
    @NotEmpty(message = "学校名称不能为空")
    @Size(max = 60, message = "学校名称过长，要求不能超过60个字符")
    private String XXJGMC;

    @ExcelProperty("*省机构编码")
    @NotEmpty(message = "省机构编码不能为空")
    @Pattern(regexp = "\\d{6}", message = "省机构编码不正确，要求6位数字码")
    private String SJGBM;

    @ExcelProperty("*省机构名称")
    @NotEmpty(message = "省机构名称不能为空")
    @Size(max = 60, message = "学校名称过长，要求不能超过60个字符")
    private String SJGMC;

    @ExcelProperty("*市机构编码")
    @NotEmpty(message = "市机构编码不能为空")
    @Pattern(regexp = "\\d{6}", message = "市机构编码不正确，要求6位数字码")
    private String SHJGBM;

    @ExcelProperty("*市机构名称")
    @NotEmpty(message = "市机构名称不能为空")
    @Size(max = 60, message = "市机构名称过长，要求不能超过60个字符")
    private String SHJGMC;

    @ExcelProperty("*区县机构编码")
    @NotEmpty(message = "区县机构编码不能为空")
    @Pattern(regexp = "\\d{6}", message = "区县机构编码不正确，要求6位数字码")
    private String QXJGBM;

    @ExcelProperty("*区县机构名称")
    @NotEmpty(message = "区县机构名称不能为空")
    @Size(max = 60, message = "市机构名称过长，要求不能超过60个字符")
    private String QXJGMC;

    @ExcelProperty("*学校机构代码")
    @NotEmpty(message = "学校机构代码不能为空")
    @Pattern(regexp = "\\w{10,20}", message = "学校机构代码不正确")
    private String XXJGDM;

    @ExcelProperty("*学校类别")
    @NotEmpty(message = "学校类别不能为空")
    private String XXLB;

    @ExcelProperty("学校所属主管教育行政部门")
    private String XXSSZGJYXZBM;

    @ExcelProperty("*学校举办者名称")
    @NotEmpty(message = "学校举办者名称不能为空")
    private String XXJBZMC;

    @ExcelProperty("*学校举办者性质")
    @NotEmpty(message = "学校举办者性质不能为空")
    private String XXJBZXZ;

    @ExcelProperty("学校负责人姓名 （校长）")
    private String XXFZRXM;

    @ExcelProperty("*建校年月")
    @NotEmpty(message = "建校年月不能为空")
    private String JXNY;

    @ExcelProperty("*现有教职工总数")
    @NotNull(message = "现有教职工总数不能为空不能为空")
    private Integer XYJSS;

    @ExcelProperty("*现有学生数")
    @NotNull(message = "现有学生数不能为空不能为空")
    private Integer XYXSS;

    @ExcelProperty("*本校开设专业数")
    @NotNull(message = "本校开设专业数不能为空不能为空")
    private Integer BXKSZYS;

    @ExcelProperty("*学校特色应用数")
    @NotNull(message = "学校特色应用数不能为空不能为空")
    private Integer XXTSYYS;

    @ExcelProperty("*校园出口带宽")
    @NotNull(message = "校园出口带宽不能为空不能为空")
    private Long XYCKDK;

    @ExcelProperty("*校园主干网带宽")
    @NotNull(message = "校园主干网带宽不能为空不能为空")
    private Long XYZGWDK;

    @ExcelProperty("*有线网络接入数")
    @NotNull(message = "有线网络接入数不能为空不能为空")
    private Long YXWLJRS;

    @ExcelProperty("*无线网络接入数")
    @NotNull(message = "无线网络接入数不能为空不能为空")
    private Long WXWLJRS;

    @ExcelProperty("*多媒体教室数")
    @NotNull(message = "多媒体教室数不能为空不能为空")
    private Long DMTJSS;

    @ExcelProperty("*是否国家双优学校 / 省级双优学校 / 否")
    @NotEmpty(message = "是否国家双优学校 / 省级双优学校 / 否不能为空")
    private String SYXX;

    @ExcelProperty("*数据采集时间")
    @NotEmpty(message = "数据采集时间不能为空")
    private String SJCJSJ;

}
