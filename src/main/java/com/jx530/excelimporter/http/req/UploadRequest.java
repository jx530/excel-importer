package com.jx530.excelimporter.http.req;


import com.jx530.excelimporter.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UploadRequest<T extends BaseModel> {

    private String dataObjName;
    private List<T> fileds;
}
