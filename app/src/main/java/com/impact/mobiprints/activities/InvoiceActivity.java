package com.impact.mobiprints.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;
import com.impact.mobiprints.R;
import com.impact.mobiprints.database.DBHelper;
import com.impact.mobiprints.models.PaymentModel;
import com.impact.mobiprints.models.UserModel;

import java.util.List;

public class InvoiceActivity extends AppCompatActivity {

    String centerName, payerName, items="", total = "0", discount="0",  ref, method, date, teller, printData;
    double amount=0.0;
    TextView data;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        checkPermission();
        init();
        print();

    }

    public void init(){
        data = findViewById(R.id.data);
        ref = getIntent().getStringExtra("transRef");

        db = new DBHelper(InvoiceActivity.this);
        UserModel userModel  = db.getUsers();

        List<PaymentModel>  pModels = db.getPaymentByRef(ref);

        payerName = pModels.get(0).getPayerName();
        method = pModels.get(0).getMethod();
        date = pModels.get(0).getPaymentDate();

        for(PaymentModel s : pModels){
            items=items + "[" + s.getRevCode() + "] " + s.getRevenueHead() + "\n" + s.getAmount() + "\n";
            discount = String.valueOf(Double.parseDouble(discount) + Double.parseDouble(s.getDiscount()));
            total = String.valueOf(Double.parseDouble(total)+s.getAmount()-Double.parseDouble(discount));
            amount += s.getAmount();
        }

        centerName = userModel.getOrganization();
        teller = userModel.getName();

        String td = "";
        td = td.concat("\n" + centerName);
        td = td.concat("\n\n" + payerName);
        td = td.concat("\n" + items);
        td = td.concat("\nDiscount: " + discount);
        td = td.concat("\nmain amt: " + amount);
        td = td.concat("\n\nReceipt No: \n" + ref);


        printData = "===============================\n" +

        centerName +
                "\n[********************************\n" +
                "[C]OFFICIAL RECEIPT</center>" +
                "\n[C]<u>--------------------------------" +
                "PAYER NAME: " + payerName +
                "\nItem(s)\n" +
                items +
                "\n\n"+
                "------------------------------\nTotal: " + total +
                "\nDISCOUNT: " + discount +
                "\nMAIN AMOUNT: "+
                amount +
                "\n********************************\n" +
                "RECEIPT NO: " +ref +
                "\n" +
                "METHOD: " + method +
                "\n" +
                "DATE: " + date +
                "\n" +
                "TELLER: " + teller +
                "\n********************************\n" +
                "[C]Powered by Pay1One";

        data.setText(td);

    }


    public boolean checkPermission(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(InvoiceActivity.this, new String[]{Manifest.permission.BLUETOOTH}, 1000);
            return false;
        }
        return true;
    }

    public void print(){
        if (checkPermission()) {
            try {
                EscPosPrinter printer = new EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 48f, 32);
                printer.printFormattedText(
                        "[C]<img>"  + PrinterTextParserImg.bitmapToHexadecimalString(printer, this.getApplicationContext().getResources().getDrawableForDensity(R.drawable.logs, DisplayMetrics.DENSITY_DEFAULT)) +
                                "</img>\n" +
                               printData
                );
            } catch (EscPosConnectionException | EscPosBarcodeException | EscPosEncodingException | EscPosParserException e) {
                Log.d("ERROR", e.getMessage());
                e.printStackTrace();
            }
        }else{
            Log.d("PERMISSION ERROR", "No permission");
        }
    }

    public void clicked(View view) {
        print();
    }

    public void prints(View view) {
        print();
    }

    public void backHandle(View view) {
        startActivity(new Intent(getApplicationContext(), Dashboard.class));
        finish();
        InvoiceActivity.super.onBackPressed();
    }

    /*public  String convertToFigure(double amount){
        RuleBasedNumberFormat rb = new RuleBasedNumberFormat(Locale.getDefault(), RuleBasedNumberFormat.SPELLOUT);
        return rb.format(amount);
    }*/
}