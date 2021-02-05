package com.impact.mobiprints.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.impact.mobiprints.JSONSchema.AppDefaultsSchema;
import com.impact.mobiprints.R;
import com.impact.mobiprints.database.DBHelper;
import com.impact.mobiprints.models.AppDefaultsModel;
import com.impact.mobiprints.models.DepartmentsModel;
import com.impact.mobiprints.models.PaymentModel;
import com.impact.mobiprints.models.RevHeadsModel;
import com.impact.mobiprints.models.UserModel;
import com.impact.mobiprints.models.WalletModel;
import com.impact.mobiprints.utils.CustomAdapter;
import com.impact.mobiprints.utils.CustomItem;
import com.impact.mobiprints.utils.Helper;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    double totalAmount;

    LinearLayout linearList;
    FloatingActionButton addPD;

    List<String> departments, revHeads, paymentMethods, priceTypes;
    List<Double> amounts;
    List<RevHeadsModel> mainRevHeads;

    List<String> dept, rHeads, pMethods, pTypes, sShifts, pTypeValues;
    DBHelper db;
    List<DepartmentsModel> dModel;
    List<RevHeadsModel> rModel;
    Spinner department, revHead, payMethod, priceType, shift;
    EditText amount;


    DrawerLayout drawerLayout;

    EditText payerName, payerPhone, payerEmail;

    TextView amountToPay;

    WalletModel walletModel;
    UserModel userModel;

    double prev_balance,  curr_balance;
    int transFee, quantity;
    String date;
    String time;
    String agent;
    String rvHead, rrr=null;
    String pRef;

    String location, shiftData;
    String  pName, pEmail, pNum;
    AppDefaultsModel app;

    private PaymentModel paymentModel;

    public void initVar(){
        date = Helper.getDate();
        time = Helper.getTime();
        walletModel = db.getWallet();
        userModel = db.getUsers();
        agent = walletModel.getAgent();
        pRef = userModel.getMdaCode() + "-" + Helper.getRef();
        prev_balance = walletModel.getBalance();
        location = userModel.getLocation();
        transFee = 0;
        quantity = 1;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        init();

    }

    public void init(){
        db = new DBHelper(getApplicationContext());


        drawerLayout = findViewById(R.id.drawerLayout);
        payerName = findViewById(R.id.payerName);
        payerEmail = findViewById(R.id.payerEmail);
        payerPhone = findViewById(R.id.payerPhone);
        payMethod = findViewById(R.id.paymentMethod);
        linearList = findViewById(R.id.linearList);
        addPD = findViewById(R.id.addPD);
        amountToPay = findViewById(R.id.payableAmount);
        shift = findViewById(R.id.shift);


        departments = new ArrayList<>();
        revHeads = new ArrayList<>();
        paymentMethods = new ArrayList<>();
        amounts = new ArrayList<>();
        dept = new ArrayList<>();
        pMethods = new ArrayList<>();
        pTypes = new ArrayList<>();
        priceTypes = new ArrayList<>();
        sShifts = new ArrayList<>();
        pTypeValues = new ArrayList<>();
        mainRevHeads = new ArrayList<>();

        sShifts.add("--select shift--");
        sShifts.add("1");
        sShifts.add("2");
        sShifts.add("3");
        dept.add("--select department--");
        pMethods.add("--payment method--");

        pMethods.add("Card");
        pMethods.add("Cash");
        dModel = db.getDepts();
        mainRevHeads = db.getRevHeads();
        app = db.getAppDefaults();

        pTypes.add("Default");
        pTypes.add("Hospital Child");
        pTypes.add("Staff");
        pTypes.add("Non Hospital Child");
        pTypes.add("Non Hospital Adult");
        pTypes.add("CMPC LOC");

        pTypeValues.add("default");
        pTypeValues.add("hoschild");
        pTypeValues.add("staff");
        pTypeValues.add("nonhoschild");
        pTypeValues.add("nonhosadult");
        pTypeValues.add("cmpc");






        for(DepartmentsModel d : dModel){
            dept.add(d.getDeptName());
        }

        CustomAdapter shiftAdapter = new CustomAdapter(this, sShifts);
        shift.setAdapter(shiftAdapter);
        shift.setSelection(0);
        shiftAdapter.notifyDataSetChanged();


        CustomAdapter pMethodAdapter = new CustomAdapter(this,  pMethods);
        payMethod.setAdapter(pMethodAdapter);
        payMethod.setSelection(0);
        pMethodAdapter.notifyDataSetChanged();

        totalAmount = 0.0;

        addViews();

    }

    public void addViews(){
        View view = getLayoutInflater().inflate(R.layout.payment_details, null, false);

        department = view.findViewById(R.id.department);
        revHead = view.findViewById(R.id.revHeads);
        priceType = view.findViewById(R.id.priceType);
        amount = view.findViewById(R.id.amount);


        CustomAdapter deptAdapter = new CustomAdapter(this,  dept);
        department.setAdapter(deptAdapter);
        department.setSelection(0);
        deptAdapter.notifyDataSetChanged();

       CustomAdapter pTypeAdapter = new CustomAdapter(this,  pTypes);
        priceType.setAdapter(pTypeAdapter);
        priceType.setSelection(0);
        pTypeAdapter.notifyDataSetChanged();

        rHeads = new ArrayList<>();
        rHeads.add("--add revenue head--");
        CustomAdapter rHeadAdapter = new CustomAdapter(PaymentActivity.this,  rHeads);
        revHead.setAdapter(rHeadAdapter);
        revHead.setSelection(0);



        priceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                priceTypes.add(pTypeValues.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {

                    rModel = db.getRevHeadsByDept(dept.get(position));
                    for(RevHeadsModel r : rModel){
                        rHeads.add(r.getRevenueHead());
                    }

                   rHeadAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        revHead.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                if(i == 0){
                    showToast("select a revenue head");
                    return;
                }else
                if(rModel.get(i-1).getAmount() != null && !rModel.get(i-1).getAmount().isEmpty() || rModel.get(i-1).getAmount().equalsIgnoreCase("NULL")){
                    amount.setText(rModel.get(i-1).getAmount());
                    amount.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        linearList.addView(view);
    }

    public void addPayments(View view) {
        if(department.getSelectedItemId() == 0){
            showToast("select departments first");
            return;
        }
        if(revHead.getSelectedItemId() == 0){
            showToast("select revenue heads first");
            return;
        }

        if(amount.getText() == null ||  amount.getText().toString().isEmpty()){
            showToast("enter amount first");
            return;
        }



        departments.add(department.getSelectedItem().toString());
        revHeads.add(revHead.getSelectedItem().toString());
        amounts.add(Double.parseDouble(amount.getText().toString()));


        addViews();
    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void confirmPayment(View view) {
        AppDefaultsModel app = db.getAppDefaults();
        if(payerName.getText()==null || payerName.getText().toString().isEmpty()){
            showToast("name field cannot be empty");
            return;
        }
        if(payerPhone.getText()==null || payerPhone.getText().toString().isEmpty()){
            pNum = app.getPhone();
        }else{
            pNum = payerPhone.getText().toString();
        }

        if(payerEmail.getText()==null || payerEmail.getText().toString().isEmpty()){
            pEmail = app.getEmail();
        }
        if(payMethod.getSelectedItemId() == 0){
            showToast("select payment method");
            return;
        }

        if(shift.getSelectedItemId() == 0){
            showToast("select shift");
            return;
        }

        if(department.getSelectedItemId() != 0) {
            departments.add(department.getSelectedItem().toString());
            revHeads.add(revHead.getSelectedItem().toString());
            amounts.add(Double.parseDouble(amount.getText().toString()));
            priceTypes.add(priceType.getSelectedItem().toString());
        }
        if(departments.size() == 0){
            showToast("you've not selected any payments");
            return;
        }
        initVar();
        if(totalAmount > walletModel.getBalance()){
            showToast("you have insufficient balance to perform this transaction");
        }
        findViewById(R.id.pay).setEnabled(true);
        addPD.setEnabled(false);


        shift.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shiftData = shift.getSelectedItem().toString();
                Toast.makeText(PaymentActivity.this, shift + "  jcjfjfjfj", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        pName = payerName.getText().toString();

        for(String s : departments){
            Log.d("DEPARTMENTS", s);
            //Log.d("REVHEADS", )
        }
    }

    public void pay(View view) {
        findViewById(R.id.pay).setEnabled(false);
        addPD.setEnabled(true);

        int rIndex;


        for(int i = 0; i < revHeads.size(); i++){
            {
                String r = revHeads.get(i);
                rIndex = revHeadId(r);
                curr_balance = prev_balance - amounts.get(i);
                 paymentModel = new PaymentModel(0,
                        amounts.get(i),
                        pName,
                        pNum,
                        pEmail,
                        date,
                        time,
                        agent,
                        revHeads.get(i) + "",
                        0 + "",
                        pRef + "",
                        prev_balance + "",
                        curr_balance + "",
                        rrr + "",
                        location + "",
                         app.getMdaId()+ "",
                        mainRevHeads.get(rIndex).getRevenueId() + "",
                        userModel.getUserId() + "",
                        quantity + "",
                        transFee + "",
                        amounts.get(i) - 0 + "",
                        payMethod.getSelectedItem().toString() + "",
                        mainRevHeads.get(rIndex).getRevenueCode() + "",
                        "",
                        mainRevHeads.get(rIndex).getDept() + "",
                        mainRevHeads.get(rIndex).getDepartment() + "",
                        mainRevHeads.get(rIndex).getCate() + "",
                        mainRevHeads.get(rIndex).getCategory() + "",
                        0 + "", amounts.get(i) + "",
                        mainRevHeads.get(rIndex).getSubs() + "",
                         mainRevHeads.get(rIndex).getEmr(),
                         shift.getSelectedItem().toString(),
                         mainRevHeads.get(i).getPriceType()
                );


            }

            boolean b = db.addPayment(paymentModel);
            prev_balance = curr_balance;
            if(b){
                db.updateWallet(prev_balance);
                showToast("success");
            }else{
                showToast("failure");
            }

        }

        startInvoice();
    }

    public void startInvoice(){
        startActivity(new Intent(PaymentActivity.this, InvoiceActivity.class).putExtra("transRef", pRef));
        finish();
    }

    public int revHeadId(String val){
        int i = 0;
        for(RevHeadsModel r : mainRevHeads){

            if (r.getRevenueHead().equalsIgnoreCase(val)) {

                return i;
            }
            i++;
        }
        return -1;
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

public void clickWallet(View view){
        /*startActivity(new Intent(Dashboard.this, WalletActivity.class));
        finish();*/
    }

    public void clickReport(View view){

    }

    public void clickInvoice(View view){

    }
    public void clickHome(View view){

    }

    public void clickPayment(View view){
        /*startActivity(new Intent(Dashboard.this, PaymentActivity.class));*/
    }


}