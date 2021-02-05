package com.impact.mobiprints.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.impact.mobiprints.R;
import com.impact.mobiprints.database.DBHelper;
import com.impact.mobiprints.network.ApiCall;
import com.impact.mobiprints.utils.Helper;

import java.util.Locale;

import static android.view.View.GONE;

public class WalletActivity extends AppCompatActivity {

    EditText rechargeVoucher;
    TextView balanceTv;
    private DBHelper db;
    String currentDate;
    private String bal;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        init();
        populateViews();
    }

    private void init() {
        rechargeVoucher = findViewById(R.id.rechargeVoucher);
        balanceTv = findViewById(R.id.balanceTv);
        db = new DBHelper(getApplicationContext());
        currentDate = Helper.getDate();
        bal = db.getBalance();
        progressBar = findViewById(R.id.progressBar);

    }

    public void recharge(View view) {
        String ref = rechargeVoucher.getText().toString();
        if(ref != null && !ref.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            rechargeCall(ref);
        }
        else
            Toast.makeText(this, "field must not be empty", Toast.LENGTH_SHORT).show();
    }

    private void populateViews(){
        balanceTv.setText(bal);
    }

    private void rechargeCall(String ref){
        double bal = Double.parseDouble(this.bal.substring(2));
        new ApiCall().recharge(ref, bal, new ApiCall.RechargeCallBack() {
            @Override
            public void onSuccess(double balance) {
                if (db.updateWallet(bal + balance)) {
                    Toast.makeText(WalletActivity.this, "Recharge successfull", Toast.LENGTH_LONG).show();
                    WalletActivity.this.bal = db.getBalance();
                    populateViews();
                }
                progressBar.setVisibility(GONE);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(WalletActivity.this, message, Toast.LENGTH_LONG).show();
                progressBar.setVisibility(GONE);
            }
        });
    }

    public void backHandle(View view) {
        startActivity(new Intent(WalletActivity.this, Dashboard.class));
        finish();
    }
}