package com.impact.mobiprints.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.impact.mobiprints.R;
import com.impact.mobiprints.database.DBHelper;
import com.impact.mobiprints.models.AppDefaultsModel;
import com.impact.mobiprints.models.CategoriesModel;
import com.impact.mobiprints.models.DepartmentsModel;
import com.impact.mobiprints.models.RevHeadsModel;
import com.impact.mobiprints.models.UserModel;
import com.impact.mobiprints.models.WalletModel;
import com.impact.mobiprints.network.ApiCall;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText username, password;
    ProgressBar progressBar;
    DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        initDB();
        findViewById(R.id.signing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        ActivityCompat.requestPermissions(LoginActivity.this,
                new String[]{Manifest.permission.READ_PHONE_STATE},
                1000);

    }

    private void initDB(){
        db = new DBHelper(getApplicationContext());
    }

    private void init(){
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("shared_preference", 0);
        if(preferences.getString("loggedIn", "").equalsIgnoreCase("true")){
            startActivity(new Intent(LoginActivity.this, Dashboard.class));
            finish();
        }
    }


    public void login() {
        if(username.getEditableText().toString().isEmpty() || password.getEditableText().toString().isEmpty()){
            Toast.makeText(this, "no empty fields allowed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, username.getEditableText(), Toast.LENGTH_SHORT).show();
            getLoginResponse();
        }
    }

    public void getLoginResponse(){
        progressBar.setVisibility(View.VISIBLE);
        String un = username.getEditableText().toString();
        String pw = password.getEditableText().toString();
        new ApiCall().getLoginResponse(LoginActivity.this, un, pw, new ApiCall.NetworkCallBack() {
            @Override
            public void onSuccess(AppDefaultsModel appDefaults, List<CategoriesModel> categories, List<DepartmentsModel> departments, UserModel userDetails, List<RevHeadsModel> revHeads, WalletModel wallet, boolean status) {
                boolean a = db.addAppDefaults(appDefaults);
                boolean b = true, c = true, e = true;

                if(categories != null){
                    for(int i = 0; i < categories.size(); i++){
                        b = db.addCategory(categories.get(i));
                        Toast.makeText(LoginActivity.this, String.format("updating %d of %d", i+1, categories.size()), Toast.LENGTH_SHORT).show();
                    }
                }

                for(int i = 0; i < departments.size(); i++){
                    c = db.addDepartment(departments.get(i));
                    Toast.makeText(LoginActivity.this, String.format("updating %d of %d", i+1, departments.size()), Toast.LENGTH_SHORT).show();
                }
                boolean d = db.addUser(userDetails);

                for(int i = 0; i < revHeads.size(); i++){
                    e = db.addRevHeads(revHeads.get(i));
                    Toast.makeText(LoginActivity.this, String.format("updating %d of %d", i+1, revHeads.size()), Toast.LENGTH_SHORT).show();
                }
                boolean f = db.addWallet(wallet);

                Toast.makeText(LoginActivity.this, String.valueOf(status), Toast.LENGTH_SHORT).show();

                if(a && b && c && d && e && f){
                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("shared_preference", 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("loggedIn", "true");
                    editor.apply();
                    startActivity(new Intent(LoginActivity.this, Dashboard.class));
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "could not insert", Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
        /*String uuid = SystemInformation.getUuid(LoginActivity.this);
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
                    if(rp.isStatus()) {
                        appDefaults = rp.getAppDefaultsSchema();
                        category = rp.getCategorySchemas();
                        department = rp.getDepartmentSchemas();
                        userDetails = rp.getResponseSchema();
                        revHeads = rp.getRevenueHeads();
                        wallet = rp.getWalletSchema();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });*/
    }



}