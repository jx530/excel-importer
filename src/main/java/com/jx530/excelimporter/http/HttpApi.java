package com.jx530.excelimporter.http;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.jx530.excelimporter.dto.CommonResult;
import com.jx530.excelimporter.http.req.UploadEncryptRequest;
import com.jx530.excelimporter.http.resp.LoginResponse;
import com.jx530.excelimporter.http.resp.UploadResponse;
import retrofit2.http.*;

import java.util.Map;

@RetrofitClient(baseUrl = "${upload.baseUrl}")
public interface HttpApi {

    @FormUrlEncoded
    @POST("/prod-api4/api/web/auth/login/interface/oauth")
    CommonResult<LoginResponse> login(@Field("grant_type") String grant_type,
                                      @Field("client_id") String client_id,
                                      @Field("username") String username,
                                      @Field("password") String password);

    @FormUrlEncoded
    @POST("/prod-api4/api/web/auth/login/interface/refresh")
    CommonResult<LoginResponse> refreshToken(@Field("grant_type") String grant_type,
                                             @Field("client_id") String client_id,
                                             @Field("client_secret") String client_secret,
                                             @Field("refresh_token") String refresh_token);

    @POST("/prod-api4/api/web/convergence/collect/interface/saveInterfaceData")
    UploadResponse uploadData(@HeaderMap Map<String, String> headers,
                              @Body UploadEncryptRequest requestBody);

    @POST("/prod-api4/api/web/convergence/collect/interface/getResultsByTime")
    UploadResponse uploadResult(@HeaderMap Map<String, String> headers,
                                @Body UploadEncryptRequest requestBody);

}