package com.impact.mobiprints.utils;


import com.ibm.icu.text.RuleBasedNumberFormat;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


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


}
