package com.impact.mobiprints.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.impact.mobiprints.constants.DBConsts;
import com.impact.mobiprints.models.AppDefaultsModel;
import com.impact.mobiprints.models.CategoriesModel;
import com.impact.mobiprints.models.CheckModel;
import com.impact.mobiprints.models.DepartmentsModel;
import com.impact.mobiprints.models.PaymentModel;
import com.impact.mobiprints.models.RevHeadsModel;
import com.impact.mobiprints.models.UserModel;
import com.impact.mobiprints.models.WalletModel;

import java.util.ArrayList;
import java.util.List;

import static com.impact.mobiprints.constants.DBConsts.*;

public class DBHelper extends SQLiteOpenHelper {

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use for locating paths to the the database
     */
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_WALLET_TABLE);
        db.execSQL(CREATE_REV_HEADS_TABLE);
        db.execSQL(CREATE_APP_DEF_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_PAYMENT_TABLE);
        db.execSQL(CREATE_DEPT_TABLE);
        db.execSQL(CREATE_CHEC_TABLE);

    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addUser(UserModel user){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(USER_ID, user.getUserId());
        cv.put(USER_NAME, user.getName());
        cv.put(USER_PHONE_NO, user.getPhoneNumber());
        cv.put(USER_EMAIL, user.getEmail());
        cv.put(USER_ADDRESS, user.getAddress());
        cv.put(USER_ORGANIZATION, user.getOrganization());
        cv.put(USER_USERNAME, user.getUsername());
        cv.put(USER_MDA_CODE, user.getMdaCode());
        cv.put(USER_LAST_LOGIN, user.getLastLogin());
        cv.put(USER_LOCATION, user.getLocation());


        return db.insert(USER_TABLE_NAME, null, cv) != -1;
    }

    public boolean addWallet(WalletModel wallet){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(WALLET_AGENT, wallet.getAgent());
        cv.put(WALLET_AGENT_ID, wallet.getAgentId());
        cv.put(WALLET_BALANCE, wallet.getBalance());

        return db.insert(WALLET_TABLE_NAME, null, cv) != -1;

    }

    public boolean addPayment(PaymentModel payment){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(PAYMENT_AMOUNT, payment.getAmount());
        cv.put(PAYMENT_PAYER_NAME, payment.getPayerName());
        cv.put(PAYMENT_PAYER_PHONE, payment.getPayerPhone());
        cv.put(PAYMENT_PAYER_EMAIL, payment.getPayerEmail());
        cv.put(PAYMENT_PAYMENT_DATE, payment.getPaymentDate());
        cv.put(PAYMENT_PAYMENT_TIME, payment.getPaymentTime());
        cv.put(PAYMENT_AGENT, payment.getAgent());
        cv.put(PAYMENT_REVENUE_HEAD, payment.getRevenueHead());
        cv.put(PAYMENT_SYNCED, payment.getSynced());
        cv.put(PAYMENT_REF, payment.getRef());
        cv.put(PAYMENT_PREVIOUS_BALANCE, payment.getPreviousBalance());
        cv.put(PAYMENT_CURRENT_BALANCE, payment.getCurrentBalance());
        cv.put(PAYMENT_RRR, payment.getRrr());
        cv.put(PAYMENT_LOCATION, payment.getLocation());
        cv.put(PAYMENT_MDA_ID, payment.getMdaId());
        cv.put(PAYMENT_REV_ID, payment.getRevId());
        cv.put(PAYMENT_AGENT_ID, payment.getAgentId());
        cv.put(PAYMENT_QUANTITY, payment.getQuantity());
        cv.put(PAYMENT_TRANS_FEE, payment.getTransFee());
        cv.put(PAYMENT_TOTAL, payment.getTotal());
        cv.put(PAYMENT_METHOD, payment.getMethod());
        cv.put(PAYMENT_REV_CODE, payment.getRevCode());
        cv.put(PAYMENT_LAST_SERIAL, payment.getLastSerial());
        cv.put(PAYMENT_DISCOUNT, payment.getDiscount());
        cv.put(PAYMENT_MAIN_AMT, payment.getMainAmt());
        cv.put(PAYMENT_SUBS, payment.getSubs());
        cv.put(PAYMENT_DESC, payment.getDesc());
        cv.put(DEPT, payment.getDept());
        cv.put(DEPARTMENT, payment.getDepartment());
        cv.put(CAT, payment.getCate());
        cv.put(CATEGORY, payment.getCategory());
        cv.put(EMR, payment.getEmr());
        cv.put(PRICETYPE, payment.getPriceType());
        cv.put(SHIFT, payment.getShift());


        return db.insert(PAYMENT_TABLE_NAME, null, cv) != -1;
    }

    public boolean addRevHeads(RevHeadsModel r) {
        SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(REV_HEAD, r.getRevenueHead());
            cv.put(REV_CODE, r.getRevenueCode());
            cv.put(REV_ID, r.getRevenueId());
            cv.put(REV_AMOUNT, r.getAmount());
            cv.put(REV_SUBS, r.getSubs());
            cv.put(DEPT, r.getDept());
            cv.put(DEPARTMENT, r.getDepartment());
            cv.put(CAT, r.getCate());
            cv.put(CATEGORY, r.getCategory());
            cv.put(EMR, r.getEmr());
            cv.put(PRICETYPE, r.getPriceType());

            return db.insert(REV_HEADS_TABLE_NAME, null, cv) != -1;
    }

    public boolean addAppDefaults(AppDefaultsModel defs){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(APP_DEF_MDA_ID, defs.getMdaId());
        cv.put(APP_DEF_EMAIL, defs.getEmail());
        cv.put(APP_DEF_PHONE, defs.getPhone());
        cv.put(APP_DEF_CHARGE_TYPE, defs.getChargeType());
        cv.put(APP_DEF_CHARGE, defs.getCharge());

        return db.insert(APP_DEFAULTS_TABLE_NAME, null,cv) != -1;
    }

    public boolean addCheck(CheckModel check){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CHEC_VALUE, check.getValue());

        return db.insert(CHEC_TABLE_NAME, null, cv) != -1;
    }

    public boolean addDepartment(DepartmentsModel d) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

            cv.put(DEPT_ID, d.getDeptId());
            cv.put(DEPT, d.getDeptName());
            cv.put(DEPT_ABBR, d.getDeptAbbr());

           return db.insert(DEPARTMENT_TABLE_NAME, null, cv) != -1;
    }

    public boolean addCategory(CategoriesModel c) {
        SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(CAT_ID, c.getCategoryId());
            cv.put(CATEGORY, c.getCategory());
            cv.put(DEPT, c.getDept());
            cv.put(DEPARTMENT, c.getDepartment());

            return  db.insert(CATEGORIES_TABLE_NAME, null, cv) != -1;
    }

    //query methods

    public AppDefaultsModel getAppDefaults(String mdaId){
        String whereClause = APP_DEF_MDA_ID + " = ?";
        String[] args = {mdaId};

        SQLiteDatabase db = getReadableDatabase();
        Cursor query = db.query(APP_DEFAULTS_TABLE_NAME, null, whereClause, args, null, null, null);

        AppDefaultsModel model = null;

        while(query.moveToNext()){
            model = new AppDefaultsModel(query.getInt(query.getColumnIndex(ID)),
                    query.getInt(query.getColumnIndexOrThrow(APP_DEF_MDA_ID)),
                    query.getString(query.getColumnIndexOrThrow(APP_DEF_EMAIL)),
                    query.getString(query.getColumnIndexOrThrow(APP_DEF_PHONE)),
                    query.getString(query.getColumnIndexOrThrow(APP_DEF_CHARGE_TYPE)),
                    query.getString(query.getColumnIndexOrThrow(APP_DEF_CHARGE))
                    );
        }
        return model;
    }

    public void deleteRecs(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(PAYMENT_TABLE_NAME, null, null);
    }

    public AppDefaultsModel getAppDefaults(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor query = db.query(APP_DEFAULTS_TABLE_NAME, null, null, null, null, null, null);

        AppDefaultsModel model = null;

        while(query.moveToNext()){
            model = new AppDefaultsModel(query.getInt(query.getColumnIndex(ID)),
                    query.getInt(query.getColumnIndexOrThrow(APP_DEF_MDA_ID)),
                    query.getString(query.getColumnIndexOrThrow(APP_DEF_EMAIL)),
                    query.getString(query.getColumnIndexOrThrow(APP_DEF_PHONE)),
                    query.getString(query.getColumnIndexOrThrow(APP_DEF_CHARGE_TYPE)),
                    query.getString(query.getColumnIndexOrThrow(APP_DEF_CHARGE)));
        }
        return model;
    }

    public List<CheckModel> getCheck(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor query = db.query(CHEC_TABLE_NAME, null, null, null, null, null, null);
        List<CheckModel> models = new ArrayList<>();

        while(query.moveToNext()){
            models.add(new CheckModel(
                    query.getInt(query.getColumnIndexOrThrow(CHEC_VALUE)),
                    query.getString(query.getColumnIndexOrThrow(CHEC_VALUE))
            ));
        }
        return models;
    }

    public List<DepartmentsModel> getDepts(){
        SQLiteDatabase db = getReadableDatabase();
        List<DepartmentsModel> models = new ArrayList<>();
        Cursor query = db.query(DEPARTMENT_TABLE_NAME, null, null, null, null, null, null);

        while(query.moveToNext()){
            models.add(new DepartmentsModel(
                    gI("id", query),
                    gI("deptID", query),
                    gS("dept", query),
                    gS("deptabbr", query))
            );
        }

        return models;
    }

    public List<RevHeadsModel> getRevHeadsByDept(String deptName){
        SQLiteDatabase db = getReadableDatabase();
        List<RevHeadsModel> models = new ArrayList<>();

        String whereClause = DEPARTMENT + " = ?";
        String[] args = {deptName};

        Cursor query = db.query(REV_HEADS_TABLE_NAME, null, whereClause, args, null, null, null);
        while(query.moveToNext()){
            models.add(new RevHeadsModel(
                    gI("id", query),
                    gS("revenuehead", query),
                    gS("revenuecode", query),
                    gI("revenueID", query),
                    gS("amount", query),
                    gS("dept", query),
                    gS("department", query),
                    gS("cate", query),
                    gS("category", query),
                    gS("subs", query),
                    gS(EMR, query),
                    gS(PRICETYPE,query)
            ));
        }
        return models;
    }

    public List<CategoriesModel> getCategories(){
        SQLiteDatabase db = getReadableDatabase();
        List<CategoriesModel> models = new ArrayList<>();

        Cursor query = db.query(CATEGORIES_TABLE_NAME, null, null, null, null, null, null);
        while(query.moveToNext()){
            models.add(new CategoriesModel(
                    gI(ID, query),
                    gI(CAT_ID, query),
                    gS(CATEGORY, query),
                    gI(DEPT, query),
                    gS(DEPARTMENT, query)
            ));
        }

        return models;
    }

    public List<RevHeadsModel> getRevHeads(){
        SQLiteDatabase db = getReadableDatabase();
        List<RevHeadsModel> models = new ArrayList<>();

        Cursor query = db.query(REV_HEADS_TABLE_NAME, null, null, null, null, null, null);
        while(query.moveToNext()){
            models.add(new RevHeadsModel(
                    gI(ID, query),
                    gS(REV_HEAD, query),
                    gS(REV_CODE, query),
                    gI(REV_ID, query),
                    gS(REV_AMOUNT, query),
                    gS(DEPT, query),
                    gS(DEPARTMENT, query),
                    gS(CAT, query),
                    gS(CATEGORY, query),
                    gS(REV_SUBS, query),
                    gS(EMR, query),
                    gS(PRICETYPE,query)
            ));
        }
        return models;
    }

    public WalletModel getWallet(){
        SQLiteDatabase db = getReadableDatabase();
        WalletModel models = null;

        Cursor query = db.query(WALLET_TABLE_NAME, null, null, null, null, null, null);
        while(query.moveToNext()){
            models=new WalletModel(
                    gI(ID, query),
                    gS(WALLET_AGENT, query),
                    gD(WALLET_BALANCE, query),
                    gI(WALLET_AGENT_ID, query)
            );
        }

        return models;
    }

    public UserModel getUsers(){
        SQLiteDatabase db = getReadableDatabase();
        UserModel models = null;

        Cursor query = db.query(USER_TABLE_NAME, null, null, null, null, null, null);
        while(query.moveToNext()){
            models = new UserModel(
                    gI(ID, query),
                    gS(USER_ID, query),
                    gS(USER_NAME, query),
                    gS(USER_EMAIL, query),
                    gS(USER_ADDRESS, query),
                    gS(USER_ORGANIZATION, query),
                    gS(USER_USERNAME, query),
                    gS(USER_MDA_CODE, query),
                    gS(USER_LAST_LOGIN, query),
                    gS(USER_LOCATION, query),
                    gS(USER_PHONE_NO, query)
            );
        }
        return models;
    }

    public List<PaymentModel> getPayment(){
        SQLiteDatabase db = getReadableDatabase();
        List<PaymentModel> models = new ArrayList<>();

        Cursor query = db.query(PAYMENT_TABLE_NAME, null, null, null, null, null, null);
        while(query.moveToNext()){
            models.add(new PaymentModel(
                    gI(ID, query),
                    gD(PAYMENT_AMOUNT, query),
                    gS(PAYMENT_PAYER_NAME, query),
                    gS(PAYMENT_PAYER_PHONE, query),
                    gS(PAYMENT_PAYER_EMAIL, query),
                    gS(PAYMENT_PAYMENT_DATE, query),
                    gS(PAYMENT_PAYMENT_TIME, query),
                    gS(PAYMENT_AGENT, query),
                    gS(PAYMENT_REVENUE_HEAD, query),
                    gS(PAYMENT_SYNCED, query),
                    gS(PAYMENT_REF, query),
                    gS(PAYMENT_PREVIOUS_BALANCE, query),
                    gS(PAYMENT_CURRENT_BALANCE, query),
                    gS(PAYMENT_RRR, query),
                    gS(PAYMENT_LOCATION, query),
                    gS(PAYMENT_MDA_ID, query),
                    gS(PAYMENT_REV_ID, query),
                    gS(PAYMENT_AGENT_ID, query),
                    gS(PAYMENT_QUANTITY, query),
                    gS(PAYMENT_TRANS_FEE, query),
                    gS(PAYMENT_TOTAL, query),
                    gS(PAYMENT_METHOD, query),
                    gS(PAYMENT_REV_CODE, query),
                    gS(PAYMENT_LAST_SERIAL, query),
                    gS(DEPT, query),
                    gS(DEPARTMENT, query),
                    gS(CAT, query),
                    gS(CATEGORY, query),
                    gS(PAYMENT_DISCOUNT, query),
                    gS(PAYMENT_MAIN_AMT, query),
                    gS(PAYMENT_SUBS, query),
                    gS(EMR, query),
                    gS(SHIFT, query),
                    gS(PRICETYPE, query)
            ));
        }
        return models;
    }

    public String[] getNTransactions(String date){
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = PAYMENT_PAYMENT_DATE + " = ?";
        String[] args = {date};
        double amount = 0.0;

        Cursor query = db.query(PAYMENT_TABLE_NAME, null, whereClause, args, null, null, null);
        int n =  query.getCount();
        while(query.moveToNext()){
            amount += Double.parseDouble(gS(PAYMENT_AMOUNT, query));
        }
        String[] val =  {String.valueOf(n), String.valueOf(amount)};

        return val;
    }

    public List<PaymentModel> getUnsynced(){
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = PAYMENT_SYNCED + " = ?";
        String[] args = {"0"};
        List<PaymentModel> models = new ArrayList<>();

        Cursor query = db.query(REV_HEADS_TABLE_NAME, null, null, null, null, null, null);
        while(query.moveToNext()){
            models.add(new PaymentModel(
                    gI(ID, query),
                    gD(PAYMENT_AMOUNT, query),
                    gS(PAYMENT_PAYER_NAME, query),
                    gS(PAYMENT_PAYER_PHONE, query),
                    gS(PAYMENT_PAYER_EMAIL, query),
                    gS(PAYMENT_PAYMENT_DATE, query),
                    gS(PAYMENT_PAYMENT_TIME, query),
                    gS(PAYMENT_AGENT, query),
                    gS(PAYMENT_REVENUE_HEAD, query),
                    gS(PAYMENT_SYNCED, query),
                    gS(PAYMENT_REF, query),
                    gS(PAYMENT_PREVIOUS_BALANCE, query),
                    gS(PAYMENT_CURRENT_BALANCE, query),
                    gS(PAYMENT_RRR, query),
                    gS(PAYMENT_LOCATION, query),
                    gS(PAYMENT_MDA_ID, query),
                    gS(PAYMENT_REV_ID, query),
                    gS(PAYMENT_AGENT_ID, query),
                    gS(PAYMENT_QUANTITY, query),
                    gS(PAYMENT_TRANS_FEE, query),
                    gS(PAYMENT_TOTAL, query),
                    gS(PAYMENT_METHOD, query),
                    gS(PAYMENT_REV_CODE, query),
                    gS(PAYMENT_LAST_SERIAL, query),
                    gS(DEPT, query),
                    gS(DEPARTMENT, query),
                    gS(CAT, query),
                    gS(CATEGORY, query),
                    gS(PAYMENT_DISCOUNT, query),
                    gS(PAYMENT_MAIN_AMT, query),
                    gS(PAYMENT_SUBS, query),
                    gS(EMR, query),
                    gS(SHIFT, query),
                    gS(PRICETYPE, query)
            ));
        }
        return models;
    }

    public String sum(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT SUM( "+ PAYMENT_AMOUNT +") FROM " + PAYMENT_TABLE_NAME, null);
        if(cur.moveToFirst())
        {
            return String.valueOf(cur.getDouble(0));
        }
        return "-1";
    }

    public String getBalance(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor query = db.query(WALLET_TABLE_NAME, null, null, null, null, null, null);
        if(query.moveToNext())
            return String.format("\u20a6 %.2f",query.getDouble(query.getColumnIndexOrThrow("balance")));
        else
            return "0.0";
    }

    public boolean updateWallet(double amount){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(WALLET_BALANCE, amount);
        String whereClause = ID + "=?";
        String[] args = {"1"};

        return db.update(WALLET_TABLE_NAME, cv, whereClause, args) != 0;
    }

    public boolean updatePayment(String ref){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(PAYMENT_SYNCED, "1");

        String whereClause = PAYMENT_REF + "=?";
        String[] args = {ref};

        return db.update(PAYMENT_TABLE_NAME, cv, whereClause, args) != 0;
    }

    public List<PaymentModel> getPaymentByRef(String ref){
        SQLiteDatabase db = getReadableDatabase();
        List<PaymentModel> models= new ArrayList<>();

        String where = PAYMENT_REF + "=?";
        String[] args = {ref};

        Cursor query = db.query(PAYMENT_TABLE_NAME, null, where, args, null, null, null);
        while(query.moveToNext()){
            models.add(new PaymentModel(
                    gI(ID, query),
                    gD(PAYMENT_AMOUNT, query),
                    gS(PAYMENT_PAYER_NAME, query),
                    gS(PAYMENT_PAYER_PHONE, query),
                    gS(PAYMENT_PAYER_EMAIL, query),
                    gS(PAYMENT_PAYMENT_DATE, query),
                    gS(PAYMENT_PAYMENT_TIME, query),
                    gS(PAYMENT_AGENT, query),
                    gS(PAYMENT_REVENUE_HEAD, query),
                    gS(PAYMENT_SYNCED, query),
                    gS(PAYMENT_REF, query),
                    gS(PAYMENT_PREVIOUS_BALANCE, query),
                    gS(PAYMENT_CURRENT_BALANCE, query),
                    gS(PAYMENT_RRR, query),
                    gS(PAYMENT_LOCATION, query),
                    gS(PAYMENT_MDA_ID, query),
                    gS(PAYMENT_REV_ID, query),
                    gS(PAYMENT_AGENT_ID, query),
                    gS(PAYMENT_QUANTITY, query),
                    gS(PAYMENT_TRANS_FEE, query),
                    gS(PAYMENT_TOTAL, query),
                    gS(PAYMENT_METHOD, query),
                    gS(PAYMENT_REV_CODE, query),
                    gS(PAYMENT_LAST_SERIAL, query),
                    gS(DEPT, query),
                    gS(DEPARTMENT, query),
                    gS(CAT, query),
                    gS(CATEGORY, query),
                    gS(PAYMENT_DISCOUNT, query),
                    gS(PAYMENT_MAIN_AMT, query),
                    gS(PAYMENT_SUBS, query),
                    gS(EMR, query),
                    gS(SHIFT, query),
                    gS(PRICETYPE, query)
            ));
        }
        return models;

    }







    public int gI(String columnName, Cursor cursor){
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }

    public String gS(String columnName, Cursor cursor){
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }
    public double gD(String columnName, Cursor cursor){
        return cursor.getDouble(cursor.getColumnIndexOrThrow(columnName));
    }






}
