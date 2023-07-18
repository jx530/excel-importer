package com.jx530.excelimporter.http;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.jx530.excelimporter.dto.CommonResult;
import com.jx530.excelimporter.http.req.UploadEncryptRequest;
import com.jx530.excelimporter.http.resp.LoginResponse;
import com.jx530.excelimporter.http.resp.UploadResponse;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.Map;

@RetrofitClient(baseUrl = "${upload.baseUrl}")
public interface HttpApi {

    @POST("/prod-api4/api/web/auth/login/interface/oauth")
    CommonResult<LoginResponse> login(@Query("grant_type") String grant_type,
                                      @Query("client_id") String client_id,
                                      @Query("username") String username,
                                      @Query("password") String password);

    @POST("/prod-api4/api/web/auth/login/interface/refresh")
    CommonResult<LoginResponse> refreshToken(@Query("grant_type") String grant_type,
                                             @Query("client_id") String client_id,
                                             @Query("client_secret") String client_secret,
                                             @Query("refresh_token") String refresh_token);

    @POST("/prod-api4/api/web/convergence/collect/interface/saveInterfaceData")
    UploadResponse uploadData(@HeaderMap Map<String, String> headers,
                              @Body UploadEncryptRequest requestBody);

    @POST("/prod-api4/api/web/convergence/collect/interface/getResultsByTime")
    UploadResponse uploadResult(@HeaderMap Map<String, String> headers,
                                @Body UploadEncryptRequest requestBody);

}