package com.impact.mobiprints.utils;


import android.util.ArrayMap;

import com.ibm.icu.text.RuleBasedNumberFormat;
import com.impact.mobiprints.models.PaymentModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class Helper {




    public static String getDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String strDate= formatter.format(date);

        return  strDate;
    }

    public static String getRef(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-ssmmhh");
        String strDate= formatter.format(date);

        return  strDate;
    }


    public static String getTime(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        String strDate= formatter.format(date);

        return  strDate;
    }

    @NotNull
    public  String convertToFigure(double amount){
        RuleBasedNumberFormat rb = new RuleBasedNumberFormat(Locale.getDefault(), RuleBasedNumberFormat.SPELLOUT);
        return rb.format(amount);
    }

    public RequestBody jsonBody(PaymentModel model){
        Map<String, Object> js = new ArrayMap<>();
        js.put("amount", model.getAmount());
        js.put("payername", model.getPayerName());
        js.put("payerphone", model.getPayerPhone());
        js.put("payeremail", model.getPayerEmail());
        js.put("paymentdate", model.getPaymentDate());
        js.put("paymenttime", model.getPaymentTime());
        js.put("agent", model.getAgent());
        js.put("revenuehead", model.getRevenueHead());
        js.put("synced", model.getSynced());
        js.put("ref", model.getRef());
        js.put("agentID", model.getAgentId());
        js.put("mdaID", model.getMdaId());
        js.put("revID", model.getRevId());
        js.put("cbalance", model.getCurrentBalance());
        js.put("pbalance", model.getPreviousBalance());
        js.put("lastserial", model.getLastSerial());
        js.put("method", model.getMethod());
        js.put("revcode", model.getRevCode());
        js.put("location", model.getLocation());
        js.put("dept", model.getDept());
        js.put("department", model.getDepartment());
        js.put("cate", model.getCate());
        js.put("category", model.getCategory());
        js.put("discount", model.getDiscount());
        js.put("quantity", model.getQuantity());
        js.put("mainamt", model.getMainAmt());
        js.put("desc", model.getRevenueHead());
        js.put("billno", model.getBillNo());
        js.put("shift", model.getShift());
        js.put("emr", model.getEmr());
        js.put("pricetype", model.getPriceType());


        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(js)).toString());
        return body;
    }


}
