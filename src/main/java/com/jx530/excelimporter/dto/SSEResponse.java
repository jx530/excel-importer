package com.jx530.excelimporter.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SSEResponse<T> {
    String type;
    T data;
}
