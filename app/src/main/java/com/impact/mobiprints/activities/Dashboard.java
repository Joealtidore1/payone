package com.impact.mobiprints.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.impact.mobiprints.JSONSchema.PaymentSchema;
import com.impact.mobiprints.JSONSchema.ResponseSchema;
import com.impact.mobiprints.R;
import com.impact.mobiprints.database.DBHelper;
import com.impact.mobiprints.models.PaymentModel;
import com.impact.mobiprints.network.ApiClient;
import com.impact.mobiprints.utils.Helper;
import com.impact.mobiprints.utils.NetworkConnectivity;
import com.impact.mobiprints.utils.NetworkUtils;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Dashboard extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView balanceTv, todayTHD, nTransactions, nCharge, totalTransactions, date;
    String currentDate;
    DBHelper db;
    String todaysTransaction, todaysCollection, overallCharge, bal;
    List<PaymentModel> paymentModels;

    ImageView closeSearchImage, searchImage;
    EditText searchRecords;
    TextView dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        init();

        populateViews();
        if(paymentModels.size() != 0) {

            Call<PaymentSchema> call = new ApiClient().getApi().uploadPayment(new Helper().jsonBody(paymentModels.get(0)));
            call.enqueue(new Callback<PaymentSchema>() {
                @Override
                public void onResponse(Call<PaymentSchema> call, Response<PaymentSchema> response) {
                    try {

                        if (response.body().isStatus()) {
                            Toast.makeText(Dashboard.this, "running background thread", Toast.LENGTH_LONG).show();
                            db.updatePayment(response.body().getRef());
                            paymentModels.remove(0);
                            Log.d("TRANSFER REF", response.body().getRef());
                            if (paymentModels.size() > 0) {
                                launch();
                            } else {
                                Toast.makeText(Dashboard.this, response.code() + "", Toast.LENGTH_SHORT).show();
                                Log.d("Launch Error", response.code() + "");
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(Dashboard.this, "failed. ", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<PaymentSchema> call, Throwable t) {
                    Toast.makeText(Dashboard.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("Launch Error", t.getMessage());


                }
            });
        }
        //launch();
    }

    private void init() {
        closeSearchImage = findViewById(R.id.closeSearchImage);
        searchImage = findViewById(R.id.searchImage);
        searchRecords = findViewById(R.id.searchRecords);
        dashboard = findViewById(R.id.dashboard);
        drawerLayout = findViewById(R.id.drawerLayout);
        balanceTv = findViewById(R.id.balanceTv);
        todayTHD = findViewById(R.id.todayTHD);
        nTransactions = findViewById(R.id.nTransactions);
        nCharge = findViewById(R.id.nCharge);
        totalTransactions = findViewById(R.id.totalTransactions);
        db = new DBHelper(getApplicationContext());
        currentDate = Helper.getDate();
        todaysTransaction = db.getNTransactions(currentDate)[0];
        todaysCollection = db.getNTransactions(currentDate)[1];
        overallCharge = db.sum();
        bal = db.getBalance();
        //db.deleteRecs();
        paymentModels = db.getPayment();
    }

    public void clicks(boolean b){
        if(b){
            closeSearchImage.setVisibility(VISIBLE);
            searchImage.setVisibility(GONE);
            searchRecords.setVisibility(VISIBLE);
            dashboard.setVisibility(GONE);
        }else{
            closeSearchImage.setVisibility(GONE);
            searchImage.setVisibility(VISIBLE);
            searchRecords.setVisibility(GONE);
            dashboard.setVisibility(VISIBLE);
        }
    }

    public void clickMenu(View view){
        openDrawer(drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout) {

        drawerLayout.openDrawer((GravityCompat.START));
    }

    public  void clickLogo(View view){
        closeDrawer(drawerLayout);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void populateViews(){
        todayTHD.setText(String.format(Locale.getDefault(), "Today's Transactions (%s)", currentDate));
        nCharge.setText(String.format("₦ %s", todaysCollection));
        nTransactions.setText(String.format("%s Transactions", todaysTransaction));
        totalTransactions.setText(String.format("\u20a6 %s", overallCharge));
        balanceTv.setText(bal);

    }

    public void clickWallet(View view){
        startActivity(new Intent(Dashboard.this, WalletActivity.class));
        finish();
    }

    public void clickReport(View view){
        startActivity(new Intent(getApplicationContext(), ReportActivity.class));
    }

    public void clickInvoice(View view){

    }
    public void clickHome(View view){
        closeDrawer(drawerLayout);
    }

    public void clickPayment(View view){
        startActivity(new Intent(Dashboard.this, PaymentActivity.class));
    }

    public void clickSearch(View view){
        clicks(true);
    }
    public void clickClose(View view){
        clicks(false);
    }

    NetworkListener listener;

    public void launch(){
        if(new NetworkConnectivity(getApplicationContext()).getCm()){

                    final int i = 0;

        }else{

            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    listener = new NetworkListener();
                    /* Create an Intent that will start the Menu-Activity. */
                    //finish();
                    //new NetworkConnectivity(getApplicationContext()).listener();
                    //BroadcastReceiver br = new MyBroadcastReceiver();
                    IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
                    filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
                    Dashboard.this.registerReceiver(listener, filter);


                }
            }, 3000);
        }
    }



    public class NetworkListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intents) {
            int status = NetworkUtils.getConnectivityStatusString(context);
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intents.getAction())) {
                if (status != NetworkUtils.NETWORK_STATUS_NOT_CONNECTED) {


                }
            }
        }

    }



}