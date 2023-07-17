package com.jx530.excelimporter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> {

    private int code;

    private String msg;

    private T data;

    public static <T> CommonResult<T> ok(T data) {
        return CommonResult.<T>builder()
                .code(HttpStatus.OK.value())
                .msg(HttpStatus.OK.getReasonPhrase())
                .data(data).build();
    }

    public static <T> CommonResult<T> badRequest(T data) {
        return CommonResult.<T>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .msg(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .data(data).build();
    }

}
