package com.impact.mobiprints.network;

import com.impact.mobiprints.JSONSchema.JSONResponse;
import com.impact.mobiprints.JSONSchema.PaymentSchema;
import com.impact.mobiprints.JSONSchema.ResponseSchema;
import com.impact.mobiprints.JSONSchema.UpdateWalletSchema;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @POST("login/index")
    @FormUrlEncoded
    Call<JSONResponse> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @POST("sync/updateWallet")
    @FormUrlEncoded
    Call<UpdateWalletSchema> recharge(
            @Field("balance") double balance,
            @Field("ref") String ref,
            @Field("X-API-KEY") String apiKey
    );

    @Headers({"Accept:application/json", "Content-Type:application/json;"})
    @POST("sync/payments")
    Call<PaymentSchema> uploadPayment(@Body RequestBody body);

}
