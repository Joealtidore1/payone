package com.impact.mobiprints.network;

import com.impact.mobiprints.JSONSchema.JSONResponse;
import com.impact.mobiprints.JSONSchema.UpdateWalletSchema;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @POST("login/index")
    @FormUrlEncoded
    Call<JSONResponse> login(
            @Field("username") String username,
            @Field("password") String password,
            @Field("uuid") String uuid,
            @Field("serial") String serial,
            @Field("manufacturer") String manufacturer,
            @Field("version") String version,
            @Field("model") String model,
            @Field("X-API-KEY") String apiKey
    );

    @POST("sync/updateWallet")
    @FormUrlEncoded
    Call<UpdateWalletSchema> recharge(
            @Field("balance") double balance,
            @Field("ref") String ref,
            @Field("X-API-KEY") String apiKey
    );
}
