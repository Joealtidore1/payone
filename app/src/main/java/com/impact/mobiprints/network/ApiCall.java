package com.impact.mobiprints.network;

import android.app.Activity;

import com.impact.mobiprints.JSONSchema.AppDefaultsSchema;
import com.impact.mobiprints.JSONSchema.CategorySchema;
import com.impact.mobiprints.JSONSchema.DepartmentSchema;
import com.impact.mobiprints.JSONSchema.JSONResponse;
import com.impact.mobiprints.JSONSchema.ResponseSchema;
import com.impact.mobiprints.JSONSchema.RevenueHeads;
import com.impact.mobiprints.JSONSchema.UpdateWalletSchema;
import com.impact.mobiprints.JSONSchema.WalletSchema;
import com.impact.mobiprints.activities.LoginActivity;
import com.impact.mobiprints.constants.Definitions;
import com.impact.mobiprints.models.AppDefaultsModel;
import com.impact.mobiprints.models.CategoriesModel;
import com.impact.mobiprints.models.DepartmentsModel;
import com.impact.mobiprints.models.RevHeadsModel;
import com.impact.mobiprints.models.UserModel;
import com.impact.mobiprints.models.WalletModel;
import com.impact.mobiprints.utils.SystemInformation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCall {

    public void getLoginResponse(Activity context, String un, String pw, NetworkCallBack nCallBack){
        String uuid = SystemInformation.getUuid(context);
        String model = SystemInformation.getModel();
        String manufacturer = SystemInformation.getManufacturer();
        String serial = SystemInformation.getSerial();
        String version = SystemInformation.getVersion();
        Call<JSONResponse> login = new ApiClient().getApi().login(un,
                pw,
                uuid,
                serial,
                manufacturer,
                version,
                model,
                Definitions.X_API_KEY);

        login.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                try{
                    JSONResponse rp = response.body();
                        if(rp.isStatus() ){
                                if((rp.getWalletSchema() != null)
                                && (rp.getAppDefaultsSchema() != null)
                                && (rp.getDepartmentSchemas() != null)
                                && (rp.getResponseSchema() != null)
                                && (rp.getRevenueHeads() != null)
                                && (rp.getWalletSchema() != null)
                                ) {

                                    List<CategoriesModel> categoryModel = new ArrayList<>();
                                    List<DepartmentsModel> departmentModel = new ArrayList<>();
                                    List<RevHeadsModel> revHeadsModel = new ArrayList<>();
                                    UserModel userModel;
                                    WalletModel walletModel;
                                    AppDefaultsModel appDefaultModel;
                                    if(rp.getCategorySchemas() != null) {
                                        for (CategorySchema ca : rp.getCategorySchemas()) {
                                            categoryModel.add(new CategoriesModel(
                                                    0, ca.getCategoryId(), ca.getCategory(), ca.getDept(), ca.getDepartment()
                                            ));
                                        }
                                    }

                                    for (DepartmentSchema dp : rp.getDepartmentSchemas()) {
                                        departmentModel.add(new DepartmentsModel(0, dp.getDeptID(), dp.getDeptName(), dp.getDeptAbbr()));
                                    }
                                    for (RevenueHeads rh : rp.getRevenueHeads()) {
                                        revHeadsModel.add(new RevHeadsModel(0, rh.getRevenueHead(), rh.getCode(), rh.getId(), rh.getAmount(), rh.getDept(), rh.getDepartment(), rh.getCate(), rh.getCategory(), rh.getSubs()/*, rh.getEmr()*/));
                                    }

                                    ResponseSchema us = rp.getResponseSchema();
                                    userModel = new UserModel(0, us.getUserId(), us.getName(), us.getEmail(), us.getAddress(), us.getOrganisation(), us.getUsername(), us.getMdacode(), us.getLastLogin(), us.getLocation(), us.getPhone());

                                    WalletSchema ws = rp.getWalletSchema();
                                    walletModel = new WalletModel(0, ws.getAgent(), ws.getBalance(), ws.getAgentId());

                                    AppDefaultsSchema ad = rp.getAppDefaultsSchema();
                                    appDefaultModel = new AppDefaultsModel(0, ad.getMdaId(), ad.getEmail(), ad.getPhone(), ad.getChargeType(), ad.getCharge());


                                    nCallBack.onSuccess(appDefaultModel, categoryModel, departmentModel, userModel, revHeadsModel, walletModel, rp.isStatus());
                                }else{
                                    nCallBack.onFailure("Login failed due to an error!");
                                }
                    }else{
                        nCallBack.onFailure(rp.getMessage());
                    }

                } catch (Exception e) {
                    nCallBack.onFailure("An error occurred. try again");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                nCallBack.onFailure("A network error occurred. try again");
            }
        });
    }

    public void recharge(String ref, double bal, RechargeCallBack recharge){
        Call<UpdateWalletSchema> call = new ApiClient().getApi().recharge(bal, ref, Definitions.X_API_KEY);
        call.enqueue(new Callback<UpdateWalletSchema>() {
            @Override
            public void onResponse(Call<UpdateWalletSchema> call, Response<UpdateWalletSchema> response) {
                try{
                    if(response.body().isStatus()){
                        recharge.onSuccess(response.body().getBalance());
                    }else{
                        recharge.onFailure(response.body().getMessage());
                    }
                } catch (Exception e) {
                    recharge.onFailure("an error occurred and recharge could not be completed");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UpdateWalletSchema> call, Throwable t) {
                recharge.onFailure("oops! a possible network error occurred");
            }
        });
    }



    public interface NetworkCallBack{
        public void onSuccess(AppDefaultsModel appDefaults,
                              List<CategoriesModel> categories,
                              List<DepartmentsModel> departments,
                              UserModel userDetails,
                              List<RevHeadsModel> revHeads,
                              WalletModel wallet,
                              boolean status
                          );

        public void onFailure(String message);
    }

    public interface RechargeCallBack{
        public void onSuccess(double balance);
        public void onFailure(String message);
    }
}
