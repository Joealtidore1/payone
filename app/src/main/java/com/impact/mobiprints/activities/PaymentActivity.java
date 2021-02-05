package com.impact.mobiprints.activities;

import androidx.appcompat.app.AppCompatActivity;

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
import com.impact.mobiprints.utils.Helper;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    double totalAmount;

    LinearLayout linearList;
    FloatingActionButton addPD;

    List<String> departments, revHeads, paymentMethods, priceTypes;
    List<Double> amounts;

    List<String> dept, rHeads, pMethods, pTypes;
    DBHelper db;
    List<DepartmentsModel> dModel;
    List<RevHeadsModel> rModel;
    Spinner department, revHead, payMethod, priceType;
    EditText amount;

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

    String location;
    String mdaId, pName, pEmail, pNum, pMethod;
    String revId;
    String total;
    String mainAmount;
    String subs;
    String pay_des;
    String dpt;
    String cat;
    String category;
    private PaymentModel paymentModel;

    public void initVar(){
        date = Helper.getDate();
        time = Helper.getTime();
        walletModel = db.getWallet();
        userModel = db.getUsers();
        agent = walletModel.getAgent();
        pRef = agent + "-" + date.substring(3).replace("-", "") + time.replace(":", "");
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

        payerName = findViewById(R.id.payerName);
        payerEmail = findViewById(R.id.payerEmail);
        payerPhone = findViewById(R.id.payerPhone);
        payMethod = findViewById(R.id.paymentMethod);
        linearList = findViewById(R.id.linearList);
        addPD = findViewById(R.id.addPD);
        amountToPay = findViewById(R.id.payableAmount);


        departments = new ArrayList<>();
        revHeads = new ArrayList<>();
        paymentMethods = new ArrayList<>();
        amounts = new ArrayList<>();
        dept = new ArrayList<>();
        pMethods = new ArrayList<>();
        pTypes = new ArrayList<>();
        priceTypes = new ArrayList<>();


        dept.add("--select department--");
        pMethods.add("--payment method--");
        pTypes.add("Default");
        pMethods.add("Card");
        pMethods.add("Cash");
        dModel = db.getDepts();

        pTypes.add("Child");
        pTypes.add("Adult");
        pTypes.add("Worker");




        for(DepartmentsModel d : dModel){
            dept.add(d.getDeptName());
        }


        ArrayAdapter<String> pMethodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pMethods);
        payMethod.setAdapter(pMethodAdapter);
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


        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dept);
        department.setAdapter(deptAdapter);
        deptAdapter.notifyDataSetChanged();

        ArrayAdapter<String> pTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pTypes);
        priceType.setAdapter(pTypeAdapter);
        pTypeAdapter.notifyDataSetChanged();

        amount.addTextChangedListener(new TextWatcher() {
            int len;
            double val;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                len = amount.length();
                if(amount.getText() != null)
                    if(!amount.getText().toString().isEmpty())
                        val = Double.parseDouble(amount.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(len > amount.length()){
                    totalAmount -= val;
                    amountToPay.setText(new Helper().convertToFigure(totalAmount));
                }
                totalAmount += Double.parseDouble(amount.getText().toString());

                amountToPay.setText(new Helper().convertToFigure(totalAmount));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    rHeads = new ArrayList<>();
                    rHeads.add("--add revenue head--");
                    rModel = db.getRevHeadsByDept(dept.get(position));
                    for(RevHeadsModel r : rModel){
                        rHeads.add(r.getRevenueHead());
                    }
                    ArrayAdapter<String> rHeadAdapter = new ArrayAdapter<>(PaymentActivity.this, android.R.layout.simple_spinner_item, rHeads);
                    revHead.setAdapter(rHeadAdapter);
                   rHeadAdapter.notifyDataSetChanged();
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

       // totalAmount = totalAmount + Double.parseDouble(amount.getText().toString());

        departments.add(department.getSelectedItem().toString());
        revHeads.add(revHead.getSelectedItem().toString());
        amounts.add(Double.parseDouble(amount.getText().toString()));
        priceTypes.add(priceType.getSelectedItem().toString());

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
        findViewById(R.id.pay).setEnabled(true);
        addPD.setEnabled(false);

        initVar();

        for(String s : departments){
            Log.d("DEPARTMENTS", s);
            //Log.d("REVHEADS", )
        }
    }

    public void pay(View view) {
        findViewById(R.id.pay).setEnabled(false);
        addPD.setEnabled(true);

        int rIndex;
        for(int i = 0; i < rHeads.size(); i++){
            {
                rIndex = revHeadId(revHeads.get(i));
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
                        userModel.getMdaCode() + "",
                        rIndex >= 0 ? rModel.get(rIndex).getRevenueId() + "" : "",
                        walletModel.getAgentId() + "",
                        quantity + "",
                        transFee + "",
                        amounts.get(i) - 0 + "",
                        payMethod.getSelectedItem().toString() + "",
                        rModel.get(rIndex).getRevenueCode() + "",
                        "",
                        rModel.get(rIndex).getDept() + "",
                        rModel.get(rIndex).getDepartment() + "",
                        rModel.get(rIndex).getCate() + "",
                        rModel.get(rIndex).getCategory() + "",
                        0 + "", amounts.get(i) + "",
                        rModel.get(rIndex).getSubs() + ""
                );
            }

            boolean b = db.addPayment(paymentModel);
            if(b){
                showToast("success");
            }else{
                showToast("failure");
            }
            prev_balance = curr_balance;
        }
    }

    public void initPayment(){

    }

    public int revHeadId(String val){
        for(int r = 0; r < rModel.size(); r++){
            if (rModel.get(r).getRevenueHead().equalsIgnoreCase(val)) {
                return r;
            }
        }
        return -1;
    }


}