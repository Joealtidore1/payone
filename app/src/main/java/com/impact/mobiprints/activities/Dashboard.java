package com.impact.mobiprints.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.impact.mobiprints.R;
import com.impact.mobiprints.database.DBHelper;
import com.impact.mobiprints.utils.Helper;

import java.util.Locale;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Dashboard extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView balanceTv, todayTHD, nTransactions, nCharge, totalTransactions, date;
    String currentDate;
    DBHelper db;
    String todaysTransaction, todaysCollection, overallCharge, bal;

    ImageView closeSearchImage, searchImage;
    EditText searchRecords;
    TextView dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        init();
        populateViews();
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

    public void clickPayment(View view){
        startActivity(new Intent(Dashboard.this, PaymentActivity.class));
        finish();
    }

    public void clickSearch(View view){
        clicks(true);
    }
    public void clickClose(View view){
        clicks(false);
    }



}