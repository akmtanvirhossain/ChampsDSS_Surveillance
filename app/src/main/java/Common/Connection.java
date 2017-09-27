package Common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.icddrb.champsdsssur.MainMenu;
import org.icddrb.champsdsssur.R;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import Utility.CompressZip;

//--------------------------------------------------------------------------------------------------
// Created by TanvirHossain on 17/03/2015.
//test for abcd: 08 09 2017
//--------------------------------------------------------------------------------------------------

public class Connection extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DB_NAME = Global.DatabaseName;
    private static final String DBLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Global.DatabaseFolder + File.separator + DB_NAME;

    // Todo table name
    private static final String TABLE_TODO = "todo_items";
    private static Context ud_context;
    private Context dbContext;
    public Connection(Context context) {
        super(context, DBLocation, null, DATABASE_VERSION);
        dbContext = context;
        ud_context = context;

        //Save("Delete from tmpVisits");
        //Save("Delete from Visits");
        //Save("Delete from migMember");
    }

    //Split function
    //----------------------------------------------------------------------------------------------
    public static String[] split(String s, char separator) {
        ArrayList<String> d = new ArrayList<String>();
        for (int ini = 0, end = 0; ini <= s.length(); ini = end + 1) {
            end = s.indexOf(separator, ini);
            if (end == -1) {
                end = s.length();
            }

            String st = s.substring(ini, end).trim();


            if (st.length() > 0) {
                d.add(st);
            } else {
                d.add("");
            }
        }

        String[] temp = new String[d.size()];
        temp = d.toArray(temp);
        return temp;
    }

    public static void MessageBox(final Context ClassName, final String Msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ClassName);
        builder.setMessage(Msg)
                .setTitle("Message")
                .setCancelable(true)
                //.setIcon(R.drawable.logo)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (Msg.equals("Saved Successfully")) {
                            ((Activity) ClassName).finish();
                        }
                    }
                });
        builder.show();

    }

    //Check whether internet connectivity available or not
    //----------------------------------------------------------------------------------------------
    public static boolean haveNetworkConnection(Context con) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
        } catch (Exception e) {

        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public static String SelectedSpinnerValue(String SelectedTest, String SplitValue) {
        String[] D = SelectedTest.split(SplitValue);
        return D[0];
    }

    public static void ExecuteSQLFromFile(String fileName) {
        List<String> dataList = Global.ReadTextFile(fileName);
        Connection C = new Connection(ud_context);
        for (int i = 0; i < dataList.size(); i++) {
            C.Save(dataList.get(i));
        }
    }

    // Creating our initial tables
    // These is where we need to write create table statements.
    // This is called when database is created.
    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("Create Table abc(sid varchar(10))");
    }

    // Upgrading the database between versions
    // This method is called when database is upgraded like modifying the table structure,
    // adding constraints to database, etc
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 1) {
            // Wipe older tables if existed
            //db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
            // Create tables again
            onCreate(db);
        }
    }

    //Check the existence of database table
    //----------------------------------------------------------------------------------------------
    public boolean TableExists(String TableName) {
        Cursor c = null;
        boolean tableExists = false;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            c = db.rawQuery("Select * from " + TableName, null);
            tableExists = true;
            c.close();
            db.close();
        } catch (Exception e) {
        }
        return tableExists;
    }

    //Create database tables
    //----------------------------------------------------------------------------------------------
    public void CreateTable(String TableName, String SQL) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (!TableExists(TableName)) {
            db.execSQL(SQL);
        }
    }


    //Message Box
    //----------------------------------------------------------------------------------------------
/*    public static void MessageBox(Context ClassName,String Msg)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ClassName);
        builder.setMessage(Msg)
                .setTitle("Message")
                .setCancelable(true)
                //.setIcon(R.drawable.logo)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", null);
        builder.show();
    }*/

    //Read data from database and return to Cursor variable
    //----------------------------------------------------------------------------------------------
    public Cursor ReadData(String SQL) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery(SQL, null);
        return cur;
    }

    //Check existence of data in database
    //----------------------------------------------------------------------------------------------
    public boolean Existence(String SQL) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery(SQL, null);
        if (cur.getCount() == 0) {
            cur.close();
            db.close();
            return false;
        } else {
            cur.close();
            db.close();
            return true;
        }
    }

    //Return single result based on the SQL query
    //----------------------------------------------------------------------------------------------
    public String ReturnSingleValue(String SQL) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery(SQL, null);
        String retValue = "";
        cur.moveToFirst();
        while (!cur.isAfterLast()) {
            retValue = cur.getString(0);
            cur.moveToNext();
        }
        cur.close();
        db.close();
        return retValue;
    }

    //Save/Update/Delete data in to database
    //----------------------------------------------------------------------------------------------
    public void Save(String SQL) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL);
        db.close();
    }

    //Date: 22 Jun 2017 for DataSync
    public String SaveData(String SQL) {
        String response = "";
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(SQL);
        }catch(Exception ex){
            response = ex.getMessage();
        }finally {
            db.close();
        }
        return response;
    }

    //Generate data list
    //----------------------------------------------------------------------------------------------
    public List<String> getDataList(String SQL) {
        List<String> data = new ArrayList<String>();
        Cursor cursor = ReadData(SQL);
        if (cursor.moveToFirst()) {
            do {
                data.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return data;
    }

    public String[] getArrayList(String SQL) {
        List<String> data = new ArrayList<String>();
        Cursor cursor = ReadData(SQL);
        if (cursor.moveToFirst()) {
            do {
                data.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        String[] mStringArray = new String[data.size()];
        mStringArray = data.toArray(mStringArray);

        cursor.close();
        return mStringArray;
    }

    //Array adapter for spinner item
    //----------------------------------------------------------------------------------------------
    public ArrayAdapter<String> getArrayAdapter(String SQL) {
        List<String> dataList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SQL, null);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.dbContext,
                R.layout.multiline_spinner_dropdown_item, dataList);

        return dataAdapter;
    }

    //Execute command on Database Server
    //----------------------------------------------------------------------------------------------
    public String ExecuteCommandOnServer(String SQLStr) {
        String response = "";
        String result = "";
        ExecuteCommand e = new ExecuteCommand();

        try {
            response = e.execute(SQLStr).get();
            if (response.equals("done")) {
                result = "done";
            } else {
                result = "not";
            }
        } catch (Exception e1) {
            result = "not";
        }

        return result;
    }

    //Find the variable positions in an array list
    //----------------------------------------------------------------------------------------------
    public int VarPosition(String VariableName, String[] ColumnList) {
        int pos = 0;
        for (int i = 0; i < ColumnList.length; i++) {
            if (VariableName.trim().equalsIgnoreCase(ColumnList[i].toString().trim())) {
                pos = i;
                i = ColumnList.length;
            }
        }
        return pos;
    }

    // Getting array list for Upload with ^ separator from Cursor
    //----------------------------------------------------------------------------------------------
    public String[] GenerateArrayList(String VariableList, String TableName) {
        Cursor cur_H;
        cur_H = ReadData("Select " + VariableList + " from " + TableName + " where Upload='2'");

        cur_H.moveToFirst();
        String[] Data = new String[cur_H.getCount()];
        String DataList = "";
        String[] Count = VariableList.toString().split(",");
        int RecordCount = 0;

        while (!cur_H.isAfterLast()) {
            for (int c = 0; c < Count.length; c++) {
                if (c == 0) {
                    if (cur_H.getString(c) == null)
                        DataList = "";
                    else if (cur_H.getString(c).equals("null"))
                        DataList = "";
                    else
                        DataList = cur_H.getString(c).toString();

                } else {
                    if (cur_H.getString(c) == null)
                        DataList += "^" + "";
                    else if (cur_H.getString(c).equals("null"))
                        DataList += "^" + "";
                    else
                        DataList += "^" + cur_H.getString(c).toString();
                }
            }
            Data[RecordCount] = DataList;
            RecordCount += 1;
            cur_H.moveToNext();
        }
        cur_H.close();

        return Data;
    }

    public List<String> DataListJSON(String SQL) {
        Gson gson = new Gson();

        DownloadDataJSON dload = new DownloadDataJSON();
        String response = null;
        try {
            response = dload.execute(SQL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        List<String> data = new ArrayList<String>();
        DownloadClass responseData = gson.fromJson(response, DownloadClass.class);
        data = responseData.getdata();
        return data;
    }

    public List<DataClassProperty> GetDataListJSON(String VariableList, String TableName, String UniqueField) {
        Cursor cur_H = ReadData("Select " + VariableList + " from " + TableName + " where Upload='2'");
        cur_H.moveToFirst();
        List<DataClassProperty> data = new ArrayList<DataClassProperty>();
        DataClassProperty d;

        String DataList = "";
        String[] Count = VariableList.toString().split(",");
        String[] UField = UniqueField.toString().split(",");
        int RecordCount = 0;

        String WhereClause = "";
        String VarData[];
        int varPos = 0;
        while (!cur_H.isAfterLast()) {
            //Prepare Data List
            for (int c = 0; c < Count.length; c++) {
                if (c == 0) {
                    if (cur_H.getString(c) == null)
                        DataList = "";
                    else if (cur_H.getString(c).equals("null"))
                        DataList = "";
                    else
                        DataList = cur_H.getString(c).toString().trim();

                } else {
                    if (cur_H.getString(c) == null)
                        DataList += "^" + "";
                    else if (cur_H.getString(c).equals("null"))
                        DataList += "^" + "";
                    else
                        DataList += "^" + cur_H.getString(c).toString().trim();
                }
            }

            //Prepare Where Clause
            VarData = DataList.split("\\^");
            varPos = 0;


            for (int j = 0; j < UField.length; j++) {
                varPos = VarPosition(UField[j].toString(), Count);
                if (j == 0) {
                    WhereClause = UField[j].toString() + "=" + "'" + VarData[varPos].toString() + "'";
                } else {
                    WhereClause += " and " + UField[j].toString() + "=" + "'" + VarData[varPos].toString() + "'";
                }
            }

            d = new DataClassProperty();
            d.setdatalist(DataList);
            d.setuniquefieldwithdata(WhereClause);
            data.add(d);

            RecordCount += 1;
            cur_H.moveToNext();
        }
        cur_H.close();

        return data;
    }

    public List<DataClassProperty> GetDataListJSON_Bangla(String VariableList, String TableName, String UniqueField) {
        Cursor cur_H = ReadData("Select " + VariableList + " from " + TableName + " where Upload='2'");
        cur_H.moveToFirst();
        List<DataClassProperty> data = new ArrayList<DataClassProperty>();
        DataClassProperty d;

        String DataList = "";
        String[] Count = VariableList.toString().split(",");
        String[] UField = UniqueField.toString().split(",");
        int RecordCount = 0;

        String WhereClause = "";
        String VarData[];
        int varPos = 0;
        while (!cur_H.isAfterLast()) {
            //Prepare Data List
            for (int c = 0; c < Count.length; c++) {
                if (c == 0) {
                    if (cur_H.getString(c) == null)
                        DataList = "";
                    else if (cur_H.getString(c).equals("null"))
                        DataList = "";
                    else
                        DataList = "N" + cur_H.getString(c).toString().trim();

                } else {
                    if (cur_H.getString(c) == null)
                        DataList += "^N" + "";
                    else if (cur_H.getString(c).equals("null"))
                        DataList += "^N" + "";
                    else
                        DataList += "^N" + cur_H.getString(c).toString().trim();
                }
            }

            //Prepare Where Clause
            VarData = DataList.split("\\^");
            varPos = 0;


            for (int j = 0; j < UField.length; j++) {
                varPos = VarPosition(UField[j].toString(), Count);
                if (j == 0) {
                    WhereClause = UField[j].toString() + "=" + "'" + VarData[varPos].toString() + "'";
                } else {
                    WhereClause += " and " + UField[j].toString() + "=" + "'" + VarData[varPos].toString() + "'";
                }
            }

            d = new DataClassProperty();
            d.setdatalist(DataList);
            d.setuniquefieldwithdata(WhereClause);
            data.add(d);

            RecordCount += 1;
            cur_H.moveToNext();
        }
        cur_H.close();

        return data;
    }

    public String DownloadJSON(String SQL, String TableName, String ColumnList, String UniqueField) {
        String WhereClause = "";
        int varPos = 0;

        String response = "";
        String resp = "";

        try {
            DownloadDataJSON dload = new DownloadDataJSON();
            response = dload.execute(SQL).get();

            //Process Response
            DownloadClass d = new DownloadClass();
            Gson gson = new Gson();
            Type collType = new TypeToken<DownloadClass>() {
            }.getType();
            DownloadClass responseData = gson.fromJson(response, collType);

            String UField[] = UniqueField.split(",");
            String VarList[] = ColumnList.split(",");

            List<String> dataStatus = new ArrayList<>();

            for (int i = 0; i < responseData.getdata().size(); i++) {
                String VarData[] = split(responseData.getdata().get(i).toString(), '^');

                //Generate where clause
                SQL = "";
                WhereClause = "";
                varPos = 0;
                for (int j = 0; j < UField.length; j++) {
                    varPos = VarPosition(UField[j].toString(), VarList);
                    if (j == 0) {
                        WhereClause = UField[j].toString() + "=" + "'" + VarData[varPos].toString() + "'";
                    } else {
                        WhereClause += " and " + UField[j].toString() + "=" + "'" + VarData[varPos].toString() + "'";
                    }
                }

                //Update command
                if (Existence("Select " + VarList[0] + " from " + TableName + " Where " + WhereClause)) {
                    for (int r = 0; r < VarList.length; r++) {
                        if (r == 0) {
                            SQL = "Update " + TableName + " Set ";
                            SQL += VarList[r] + " = '" + VarData[r].toString() + "'";
                        } else {
                            if (r == VarData.length - 1) {
                                SQL += "," + VarList[r] + " = '" + VarData[r].toString() + "'";
                                SQL += " Where " + WhereClause;
                            } else {
                                SQL += "," + VarList[r] + " = '" + VarData[r].toString() + "'";
                            }
                        }
                    }

                    Save(SQL);
                }
                //Insert command
                else {
                    for (int r = 0; r < VarList.length; r++) {
                        if (r == 0) {
                            SQL = "Insert into " + TableName + "(" + ColumnList + ")Values(";
                            SQL += "'" + VarData[r].toString() + "'";
                        } else {
                            SQL += ",'" + VarData[r].toString() + "'";
                        }
                    }
                    SQL += ")";

                    Save(SQL);
                }

                dataStatus.add(WhereClause);
            }


            //Status back to server
            if (dataStatus.size() > 0) {

            }


        } catch (Exception e) {
            resp = e.getMessage();
            e.printStackTrace();
        }

        return resp;
    }

    public String DownloadJSON_InsertOnly(String SQL, String TableName, String ColumnList, String UniqueField) {
        String WhereClause = "";
        int varPos = 0;

        String response = "";
        String resp = "";

        try {
            DownloadDataJSON dload = new DownloadDataJSON();
            response = dload.execute(SQL).get();

            //Process Response
            DownloadClass d = new DownloadClass();
            Gson gson = new Gson();
            Type collType = new TypeToken<DownloadClass>() {
            }.getType();
            DownloadClass responseData = gson.fromJson(response, collType);

            String UField[] = UniqueField.split(",");
            String VarList[] = ColumnList.split(",");

            List<String> dataStatus = new ArrayList<>();

            for (int i = 0; i < responseData.getdata().size(); i++) {
                String VarData[] = split(responseData.getdata().get(i).toString(), '^');

                SQL = "";

                //Insert command
                for (int r = 0; r < VarList.length; r++) {
                    if (r == 0) {
                        SQL = "Insert into " + TableName + "(" + ColumnList + ")Values(";
                        SQL += "'" + VarData[r].toString() + "'";
                    } else {
                        SQL += ",'" + VarData[r].toString() + "'";
                    }
                }
                SQL += ")";

                Save(SQL);

                dataStatus.add(WhereClause);
            }


            //Status back to server
            if (dataStatus.size() > 0) {

            }


        } catch (Exception e) {
            resp = e.getMessage();
            e.printStackTrace();
        }

        return resp;
    }

    public String DownloadJSON_UpdateServer(String SQL, String TableName, String ColumnList, String UniqueField) {
        String WhereClause = "";
        int varPos = 0;

        String response = "";
        String resp = "";

        try {
            DownloadDataJSON dload = new DownloadDataJSON();
            response = dload.execute(SQL).get();

            //Process Response
            DownloadClass d = new DownloadClass();
            Gson gson = new Gson();
            Type collType = new TypeToken<DownloadClass>() {
            }.getType();
            DownloadClass responseData = gson.fromJson(response, collType);

            String UField[] = UniqueField.split(",");
            String VarList[] = ColumnList.split(",");

            List<String> dataStatus = new ArrayList<>();

            for (int i = 0; i < responseData.getdata().size(); i++) {
                String VarData[] = split(responseData.getdata().get(i).toString(), '^');

                //Generate where clause
                SQL = "";
                WhereClause = "";
                varPos = 0;
                for (int j = 0; j < UField.length; j++) {
                    varPos = VarPosition(UField[j].toString(), VarList);
                    if (j == 0) {
                        WhereClause = UField[j].toString() + "=" + "'" + VarData[varPos].toString() + "'";
                    } else {
                        WhereClause += " and " + UField[j].toString() + "=" + "'" + VarData[varPos].toString() + "'";
                    }
                }

                //Update command
                if (Existence("Select " + VarList[0] + " from " + TableName + " Where " + WhereClause)) {
                    for (int r = 0; r < VarList.length; r++) {
                        if (r == 0) {
                            SQL = "Update " + TableName + " Set ";
                            SQL += VarList[r] + " = '" + VarData[r].toString() + "'";
                        } else {
                            if (r == VarData.length - 1) {
                                SQL += "," + VarList[r] + " = '" + VarData[r].toString() + "'";
                                SQL += " Where " + WhereClause;
                            } else {
                                SQL += "," + VarList[r] + " = '" + VarData[r].toString() + "'";
                            }
                        }
                    }

                    Save(SQL);
                }
                //Insert command
                else {
                    for (int r = 0; r < VarList.length; r++) {
                        if (r == 0) {
                            SQL = "Insert into " + TableName + "(" + ColumnList + ")Values(";
                            SQL += "'" + VarData[r].toString() + "'";
                        } else {
                            SQL += ",'" + VarData[r].toString() + "'";
                        }
                    }
                    SQL += ")";

                    Save(SQL);
                }

                dataStatus.add(WhereClause);
            }


            //Status back to server
            if (dataStatus.size() > 0) {
                //Generate SQL String
                List<String> sqlString = new ArrayList<>();
                for (String data : dataStatus) {
                    sqlString.add("Update " + TableName + " Set Upload='1' Where " + data);
                }

                DataClass_SQL_Update dt = new DataClass_SQL_Update();
                dt.setSQLString(sqlString);

                String json = gson.toJson(dt);
                UploadDataSQLJSON u = new UploadDataSQLJSON();
                try {
                    response = u.execute(json).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        } catch (Exception e) {
            resp = e.getMessage();
            e.printStackTrace();
        }

        return resp;
    }

    //Remove data on local device
    public String DownloadJSON_Delete_UpdateServer(String SQL, String LocalTableName, String ServerTableName, String ColumnList, String UniqueField) {
        String WhereClause = "";
        int varPos = 0;

        String response = "";
        String resp = "";

        try {
            DownloadDataJSON dload = new DownloadDataJSON();
            response = dload.execute(SQL).get();

            //Process Response
            DownloadClass d = new DownloadClass();
            Gson gson = new Gson();
            Type collType = new TypeToken<DownloadClass>() {
            }.getType();
            DownloadClass responseData = gson.fromJson(response, collType);

            String UField[] = UniqueField.split(",");
            String VarList[] = ColumnList.split(",");

            List<String> dataStatus = new ArrayList<>();

            for (int i = 0; i < responseData.getdata().size(); i++) {
                String VarData[] = split(responseData.getdata().get(i).toString(), '^');

                //Generate where clause
                SQL = "";
                WhereClause = "";
                varPos = 0;
                for (int j = 0; j < UField.length; j++) {
                    varPos = VarPosition(UField[j].toString(), VarList);
                    if (j == 0) {
                        WhereClause = UField[j].toString() + "=" + "'" + VarData[varPos].toString() + "'";
                    } else {
                        WhereClause += " and " + UField[j].toString() + "=" + "'" + VarData[varPos].toString() + "'";
                    }
                }

                //Delete command
                SQL = "Delete from " + LocalTableName + " Where " + WhereClause;

                Save(SQL);

                dataStatus.add(WhereClause);
            }


            //Status back to server
            if (dataStatus.size() > 0) {
                //Generate SQL String
                List<String> sqlString = new ArrayList<>();
                for (String data : dataStatus) {
                    sqlString.add("Update " + ServerTableName + " Set Upload='2' Where " + data);
                }

                DataClass_SQL_Update dt = new DataClass_SQL_Update();
                dt.setSQLString(sqlString);

                String json = gson.toJson(dt);
                UploadDataSQLJSON u = new UploadDataSQLJSON();
                try {
                    response = u.execute(json).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        } catch (Exception e) {
            resp = e.getMessage();
            e.printStackTrace();
        }

        return resp;
    }

    public String DownloadJSON_BlockUpdate_UpdateServer(String SQL, String LocalTableName, String ColumnList, String UniqueField) {
        String WhereClause = "";
        int varPos = 0;

        String response = "";
        String resp = "";

        try {
            DownloadDataJSON dload = new DownloadDataJSON();
            response = dload.execute(SQL).get();

            //Process Response
            DownloadClass d = new DownloadClass();
            Gson gson = new Gson();
            Type collType = new TypeToken<DownloadClass>() {
            }.getType();
            DownloadClass responseData = gson.fromJson(response, collType);

            String UField[] = UniqueField.split(",");
            String VarList[] = ColumnList.split(",");

            List<String> dataStatus = new ArrayList<>();

            for (int i = 0; i < responseData.getdata().size(); i++) {
                String VarData[] = split(responseData.getdata().get(i).toString(), '^');

                //Generate where clause
                SQL = "";
                WhereClause = "";
                varPos = 0;
                for (int j = 0; j < UField.length; j++) {
                    varPos = VarPosition(UField[j].toString(), VarList);
                    if (j == 0) {
                        WhereClause = UField[j].toString() + "=" + "'" + VarData[varPos].toString() + "'";
                    } else {
                        WhereClause += " and " + UField[j].toString() + "=" + "'" + VarData[varPos].toString() + "'";
                    }
                }

                //Delete command
                SQL = "Delete from " + LocalTableName + " Where " + WhereClause;

                Save(SQL);

                dataStatus.add(WhereClause);
            }


            //Status back to server
            if (dataStatus.size() > 0) {
                //Generate SQL String
                List<String> sqlString = new ArrayList<>();
                for (String data : dataStatus) {
                    sqlString.add("Update BariRemove Set Upload='2' Where " + data);
                }

                DataClass_SQL_Update dt = new DataClass_SQL_Update();
                dt.setSQLString(sqlString);

                String json = gson.toJson(dt);
                UploadDataSQLJSON u = new UploadDataSQLJSON();
                try {
                    response = u.execute(json).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        } catch (Exception e) {
            resp = e.getMessage();
            e.printStackTrace();
        }

        return resp;
    }

    //Download list item from server based on SQl query
    public List<String> DownloadJSONList(String SQL) {
        String response = "";
        String resp = "";

        List<String> dataStatus = new ArrayList<>();
        try {

            DownloadDataJSON dload = new DownloadDataJSON();
            response = dload.execute(SQL).get();

            //Process Response
            DownloadClass d = new DownloadClass();
            Gson gson = new Gson();
            Type collType = new TypeToken<DownloadClass>() {
            }.getType();
            DownloadClass responseData = gson.fromJson(response, collType);
            dataStatus = responseData.getdata();

        } catch (Exception e) {
            resp = e.getMessage();
            e.printStackTrace();
        }

        return dataStatus;
    }

    public String UploadJSON(String TableName, String ColumnList, String UniqueFields) {
        String response = "";
        List<DataClassProperty> data = GetDataListJSON(ColumnList, TableName, UniqueFields);

        if (data.size() > 0) {
            DataClass dt = new DataClass();
            dt.settablename(TableName);
            dt.setcolumnlist(ColumnList);
            dt.setuniquefields(UniqueFields);

            dt.setdata(data);
            Gson gson = new Gson();
            String json = gson.toJson(dt);
            UploadDataJSON u = new UploadDataJSON();
            try {
                response = u.execute(json).get();

                //Process Response
                if (response != null) {
                    DownloadClass d = new DownloadClass();
                    Type collType = new TypeToken<DownloadClass>() {
                    }.getType();
                    DownloadClass responseData = gson.fromJson(response, collType);

                    //upload all records as successfull upload then update status of upload=2 for unsuccessfull
                    for (int i = 0; i < responseData.getdata().size(); i++) {
                        Save("Update " + TableName + " Set Upload='1' where " + responseData.getdata().get(i).toString());
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    // Getting result from database server based on SQL
    //----------------------------------------------------------------------------------------------
    public String ReturnResult(String MethodName, String SQL) {
        ReturnResult r = new ReturnResult();
        String response = "";
        r.Method_Name = MethodName;
        r.SQLStr = SQL;
        try {
            response = r.execute("").get();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (ExecutionException e) {

            e.printStackTrace();
        }
        return response;
    }

    //Rebuild Local Database from Server
    //----------------------------------------------------------------------------------------------
    public void RebuildDatabase(String DeviceID, String Cluster, final ProgressDialog progDialog, Handler progHandler) {
        List<String> listItem = new ArrayList<String>();
        listItem = DownloadJSONList("Select TableName+'^'+TableScript from DatabaseTab");

        for (int i = 0; i < listItem.size(); i++) {
            String VarData[] = split(listItem.get(i), '^');
            CreateTable(VarData[0], VarData[1]);
        }

        //------------------------------------------------------------------------------------------
        //Data Sync: Download data from server
        //------------------------------------------------------------------------------------------
        String Res = "";
        String TableName;
        String VariableList;
        String UniqueField;
        String SQLStr, SQLStr1;

        try {
            //Remove data from Sync_Management
            //--------------------------------------------------------------------------------------
            ExecuteCommandOnServer("Delete from Sync_Management where UserId='" + DeviceID + "'");

            //Master Database Sync (Required for any database system)
            //--------------------------------------------------------------------------------------
            SQLStr = "Select TableName, TableScript, ColumnList, UniqueID, BatchSize from DatabaseTab";
            TableName = "DatabaseTab";
            VariableList = "TableName, TableScript, ColumnList, UniqueID, BatchSize";
            UniqueField = "TableName";
            Res = DownloadJSON(SQLStr, TableName, VariableList, UniqueField);

            this.Sync_Download("DeviceList", DeviceID, "DeviceId='" + DeviceID + "'");
            this.Sync_Download("DataCollector",DeviceID, "Status='d'");

            //Project Specific Database Sync
            //--------------------------------------------------------------------------------------
            this.Sync_Download("Upazila",DeviceID, "");
            this.Sync_Download("Unions",DeviceID, "");
            this.Sync_Download("Mouza",DeviceID, "");

            //progDialog.setMessage("Downloading Village Data...");
            //Global.getInstance().setProgressMessage("Downloading Village Data...");
            //progressHandler.sendMessage(progressHandler.obtainMessage());
            this.Sync_Download("Village",DeviceID, "");

            //Download data from server
            //------------------------------------------------------------------------------
            Integer batchSize = 0;
            Integer total     = 0;


            //Baris
            //--------------------------------------------------------------------------------------
            progHandler.post(new Runnable() {
                public void run() {
                    progDialog.setProgress(5);
                    progDialog.setMessage("Downloading Bari Data ...");
                }
            });
            TableName    = "Baris";
            SQLStr1 = "Select count(*)total " +
                    " from Baris where Cluster='"+ Cluster +"'";
            total = Integer.valueOf(ReturnResult("ReturnSingleValue",SQLStr1));
            batchSize = Integer.valueOf(ReturnSingleValue("select ifnull(batchsize,0)batchsize from DatabaseTab where TableName='" + TableName + "'"));
            batchSize = batchSize==0?total:batchSize;

            SQLStr = "Select top "+ batchSize +" Vill, Bari, Cluster, Block, BariName, BariLoc, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, '1' Upload, modifyDate " +
                    " from Baris b where Cluster='"+ Cluster +"' and" +
                    " not exists(Select TableName from sync_management where TableName='"+ TableName +"' and UserId='"+ DeviceID +"' and UniqueId=b.Vill+b.Bari)";

            VariableList = "Vill, Bari, Cluster, Block, BariName, BariLoc, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, modifyDate";
            UniqueField  = "Vill, Bari";
            Sync_Download_Rebuild_Batch(SQLStr, TableName, VariableList, UniqueField,DeviceID,total,batchSize);

            //Household
            //--------------------------------------------------------------------------------------
            progHandler.post(new Runnable() {
                public void run() {
                    progDialog.setProgress(10);
                    progDialog.setMessage("Downloading Household Data ...");
                }
            });
            TableName    = "Household";

            SQLStr  = "Select count(*)";
            SQLStr += " from Baris b";
            SQLStr += " inner join Household h on b.Vill=h.Vill and b.Bari=h.Bari";
            SQLStr += " where b.Cluster='"+ Cluster +"'";

            total = Integer.valueOf(ReturnResult("ReturnSingleValue",SQLStr));
            batchSize = Integer.valueOf(ReturnSingleValue("select ifnull(batchsize,0)batchsize from DatabaseTab where TableName='" + TableName + "'"));
            batchSize = batchSize==0?total:batchSize;

            SQLStr  = "Select top "+ batchSize +" h.Vill, h.Bari, HH, Religion, MobileNo1, MobileNo2, HHHead, TotMem, TotRWo, EnType, EnDate, ExType, ExDate, Rnd,";
            SQLStr += " h.StartTime, h.EndTime, h.DeviceID, h.EntryUser, h.Lat, h.Lon, h.EnDt, '1' Upload, h.modifyDate";
            SQLStr += " from Baris b";
            SQLStr += " inner join Household h on b.Vill=h.Vill and b.Bari=h.Bari";
            SQLStr += " where b.Cluster='"+ Cluster +"' and" +
                    " not exists(Select TableName from sync_management where TableName='"+ TableName +"' and UserId='"+ DeviceID +"' and UniqueId=h.Vill+h.Bari+h.HH)";


            VariableList = "Vill, Bari, HH, Religion, MobileNo1, MobileNo2, HHHead, TotMem, TotRWo, EnType, EnDate, ExType, ExDate, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, modifyDate";
            UniqueField  = "Vill, Bari, HH";
            //Res = DownloadJSON_Update_Sync_Management_InsertOnly(SQLStr, TableName, VariableList, UniqueField,DeviceID);
            Sync_Download_Rebuild_Batch(SQLStr, TableName, VariableList, UniqueField,DeviceID,total,batchSize);

            //SES
            //--------------------------------------------------------------------------------------
            progHandler.post(new Runnable() {
                public void run() {
                    progDialog.setProgress(30);
                    progDialog.setMessage("Downloading SES Data ...");
                }
            });
            TableName    = "SES";
            SQLStr  = " Select count(*)";
            SQLStr += " from Baris b";
            SQLStr += " inner join Household h on b.Vill=h.Vill and b.Bari=h.Bari";
            SQLStr += " inner join SES s on h.Vill=s.vill and h.Bari=s.Bari and h.hh=s.hh";
            SQLStr += " where b.Cluster='"+ Cluster +"'";
            total = Integer.valueOf(ReturnResult("ReturnSingleValue",SQLStr));
            batchSize = Integer.valueOf(ReturnSingleValue("select ifnull(batchsize,0)batchsize from DatabaseTab where TableName='" + TableName + "'"));
            batchSize = batchSize==0?total:batchSize;

            SQLStr  = " Select top "+ batchSize +" s.Vill, s.Bari, s.HH, SESNo, VDate, VStatus, VStatusOth, s.Rnd, WSDrink, WSDrinkOth, WSCook, WSCookOth, WSWash, WSWashOth, Latrine, LatrineOth,";
            SQLStr += " Electricity, Radio, TV, Mobile, Telephone, Refrige, Watch, ElecFan, RickVan, Bicycle, MotCycle, Computer, Buffalo, Bull, Goat, Chicken, Pigeon,";
            SQLStr += " Roof, RoofOth, Wall, WallOth, Floor, FloorOth, Homestead, HomesteadOth, OthLand, s.StartTime, s.EndTime, s.DeviceID, s.EntryUser, s.Lat, s.Lon, s.EnDt, '1' Upload, s.modifyDate";
            SQLStr += " from Baris b";
            SQLStr += " inner join Household h on b.Vill=h.Vill and b.Bari=h.Bari";
            SQLStr += " inner join SES s on h.Vill=s.vill and h.Bari=s.Bari and h.hh=s.hh";
            SQLStr += " where b.Cluster='"+ Cluster +"' and" +
                    " not exists(Select TableName from sync_management where TableName='"+ TableName +"' and UserId='"+ DeviceID +"' and UniqueId=s.Vill+s.Bari+s.HH+s.SESNo)";

            VariableList = "Vill, Bari, HH, SESNo, VDate, VStatus, VStatusOth, Rnd, WSDrink, WSDrinkOth, WSCook, WSCookOth, WSWash, WSWashOth, Latrine, LatrineOth, Electricity, Radio, TV, Mobile, Telephone, Refrige, Watch, ElecFan, RickVan, Bicycle, MotCycle, Computer, Buffalo, Bull, Goat, Chicken, Pigeon, Roof, RoofOth, Wall, WallOth, Floor, FloorOth, Homestead, HomesteadOth, OthLand, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, modifyDate";
            UniqueField  = "Vill, Bari, HH, SESNo";
            //Res = DownloadJSON_Update_Sync_Management_InsertOnly(SQLStr, TableName, VariableList, UniqueField,DeviceID);
            Sync_Download_Rebuild_Batch(SQLStr, TableName, VariableList, UniqueField,DeviceID,total,batchSize);

            //Member
            //--------------------------------------------------------------------------------------
            progHandler.post(new Runnable() {
                public void run() {
                    progDialog.setProgress(40);
                    progDialog.setMessage("Downloading Member Data ...");
                }
            });
            TableName    = "Member";
            SQLStr  = "Select count(*) from Baris b " +
                    "inner join Member m on b.Vill=m.vill and b.Bari=m.Bari " +
                    "where b.Cluster='"+ Cluster +"' and " +
                    "not exists(Select TableName from sync_management where TableName='Member' and UserId='"+ DeviceID +"' and UniqueId=m.Vill+m.Bari+m.HH+m.MSlNo)";
            total = Integer.valueOf(ReturnResult("ReturnSingleValue",SQLStr));
            batchSize = Integer.valueOf(ReturnSingleValue("select ifnull(batchsize,0)batchsize from DatabaseTab where TableName='" + TableName + "'"));
            batchSize = batchSize==0?total:batchSize;

            SQLStr  = "Select top "+ batchSize +" m.Vill, m.Bari, m.HH, MSlNo, PNo, Name, Rth, Sex, BDate, AgeY, MoNo, FaNo, Edu, MS, Ocp, Sp1, Sp2, Sp3, Sp4, Pstat, LmpDt, m.EnType, m.EnDate, m.ExType, m.ExDate, NeedReview, PosMig, PosMigDate, m.StartTime, m.EndTime, m.DeviceID, m.EntryUser, m.Lat, m.Lon, m.EnDt, '1' Upload, m.modifyDate " +
                    "from Baris b " +
                    "inner join Household h on b.Vill=h.Vill and b.Bari=h.Bari " +
                    "inner join Member m on h.Vill=m.vill and h.Bari=m.Bari and h.hh=m.hh " +
                    "where b.Cluster='"+ Cluster +"' and not exists(Select TableName from sync_management where TableName='"+ TableName +"' and UserId='"+ DeviceID +"' and UniqueId=m.Vill+m.Bari+m.HH+m.MSlNo)";

            VariableList = "Vill, Bari, HH, MSlNo, PNo, Name, Rth, Sex, BDate, AgeY, MoNo, FaNo, Edu, MS, Ocp, Sp1, Sp2, Sp3, Sp4, Pstat, LmpDt, EnType, EnDate, ExType, ExDate, NeedReview, PosMig, PosMigDate, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, modifyDate";
            UniqueField  = "Vill, Bari, HH, MSlNo";

            Sync_Download_Rebuild_Batch(SQLStr, TableName, VariableList, UniqueField,DeviceID,total,batchSize);


            //PregHis
            //--------------------------------------------------------------------------------------
            progHandler.post(new Runnable() {
                public void run() {
                    progDialog.setProgress(70);
                    progDialog.setMessage("Downloading Pregnancy History Data ...");
                }
            });
            TableName    = "PregHis";
            SQLStr  = " Select count(*)";
            SQLStr += " from Baris b";
            SQLStr += " inner join Household h on b.Vill=h.Vill and b.Bari=h.Bari";
            SQLStr += " inner join PregHis s on h.Vill=s.vill and h.Bari=s.Bari and h.hh=s.hh";
            SQLStr += " where b.Cluster='"+ Cluster +"'";

            total = Integer.valueOf(ReturnResult("ReturnSingleValue",SQLStr));
            batchSize = Integer.valueOf(ReturnSingleValue("select ifnull(batchsize,0)batchsize from DatabaseTab where TableName='" + TableName + "'"));
            batchSize = batchSize==0?total:batchSize;

            SQLStr  = " Select top "+ batchSize +" s.Vill, s.Bari, s.HH, MSlNo, PNo, VDate, VStatus, VStatusOth, MarriageStatus, MarMon, MarYear, MarDK, GaveBirth, ChildLivWWo, SonLivWWo, DaugLivWWo,";
            SQLStr += " ChldLivOut, SonLivOut, DaugLivOut, ChldDie, BoyDied, GirlDied, NotLivBrth, TotLB, TotPregOut, CurPreg, LMPDate, s.StartTime, s.EndTime, s.DeviceID,";
            SQLStr += " s.EntryUser, s.Lat, s.Lon, s.EnDt, '1' Upload, s.modifyDate";
            SQLStr += " from Baris b";
            SQLStr += " inner join Household h on b.Vill=h.Vill and b.Bari=h.Bari";
            SQLStr += " inner join PregHis s on h.Vill=s.vill and h.Bari=s.Bari and h.hh=s.hh";
            SQLStr += " where b.Cluster='"+ Cluster +"' and" +
                    " not exists(Select TableName from sync_management where TableName='"+ TableName +"' and UserId='"+ DeviceID +"' and UniqueId=s.Vill+s.Bari+s.HH+s.MSlNo)";

            VariableList = "Vill, Bari, HH, MSlNo, PNo, VDate, VStatus, VStatusOth, MarriageStatus, MarMon, MarYear, MarDK, GaveBirth, ChildLivWWo, SonLivWWo, DaugLivWWo, ChldLivOut, SonLivOut, DaugLivOut, ChldDie, BoyDied, GirlDied, NotLivBrth, TotLB, TotPregOut, CurPreg, LMPDate, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, modifyDate";
            UniqueField  = "Vill, Bari, HH, MSlNo";
            //Res = DownloadJSON_Update_Sync_Management_InsertOnly(SQLStr, TableName, VariableList, UniqueField,DeviceID);
            Sync_Download_Rebuild_Batch(SQLStr, TableName, VariableList, UniqueField,DeviceID,total,batchSize);

            //Visits
            //--------------------------------------------------------------------------------------
            progHandler.post(new Runnable() {
                public void run() {
                    progDialog.setProgress(80);
                    progDialog.setMessage("Downloading Visits Data ...");
                }
            });
            TableName    = "Visits";
            SQLStr  = " Select count(*)";
            SQLStr += " from Baris b";
            SQLStr += " inner join Household h on b.Vill=h.Vill and b.Bari=h.Bari";
            SQLStr += " inner join Visits s on h.Vill=s.vill and h.Bari=s.Bari and h.hh=s.hh";
            SQLStr += " where b.Cluster='"+ Cluster +"'";
            total = Integer.valueOf(ReturnResult("ReturnSingleValue",SQLStr));
            batchSize = Integer.valueOf(ReturnSingleValue("select ifnull(batchsize,0)batchsize from DatabaseTab where TableName='" + TableName + "'"));
            batchSize = batchSize==0?total:batchSize;

            SQLStr  = " Select top "+ batchSize +" s.Vill, s.Bari, s.HH, VDate, VStatus, VStatusOth, VisitNo, Resp, s.Rnd, s.StartTime, s.EndTime, s.DeviceID, s.EntryUser, s.Lat, s.Lon, s.EnDt, '1' Upload, s.modifyDate";
            SQLStr += " from Baris b";
            SQLStr += " inner join Household h on b.Vill=h.Vill and b.Bari=h.Bari";
            SQLStr += " inner join Visits s on h.Vill=s.vill and h.Bari=s.Bari and h.hh=s.hh";
            SQLStr += " where b.Cluster='"+ Cluster +"' and" +
                    " not exists(Select TableName from sync_management where TableName='"+ TableName +"' and UserId='"+ DeviceID +"' and UniqueId=s.Vill+s.Bari+s.HH+s.Rnd)";

            VariableList = "Vill, Bari, HH, VDate, VStatus, VStatusOth, VisitNo, Resp, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, modifyDate";
            UniqueField  = "Vill, Bari, HH, Rnd";
            //Res = DownloadJSON_Update_Sync_Management_InsertOnly(SQLStr, TableName, VariableList, UniqueField,DeviceID);
            Sync_Download_Rebuild_Batch(SQLStr, TableName, VariableList, UniqueField,DeviceID,total,batchSize);

            //Events
            //--------------------------------------------------------------------------------------
            progHandler.post(new Runnable() {
                public void run() {
                    progDialog.setProgress(95);
                    progDialog.setMessage("Downloading Events Data ...");
                }
            });
            TableName    = "Events";
            SQLStr  = "Select count(*) from Events e " +
                    "inner join Baris b on e.Vill=b.Vill and e.Bari=b.Bari " +
                    " where b.Cluster='"+ Cluster +"'";
            total = Integer.valueOf(ReturnResult("ReturnSingleValue",SQLStr));
            batchSize = Integer.valueOf(ReturnSingleValue("select ifnull(batchsize,0)batchsize from DatabaseTab where TableName='" + TableName + "'"));
            batchSize = batchSize==0?total:batchSize;

            SQLStr  = "Select top "+ batchSize +" e.Vill, e.Bari, HH, MSlNo, PNo, EvType, EvDate, Info1, Info2, Info3, Info4, VDate, Rnd," +
                    " e.StartTime, e.EndTime, e.DeviceID, e.EntryUser, e.Lat, e.Lon, e.EnDt, '1' Upload, e.modifyDate from Events e " +
                    "inner join Baris b on e.Vill=b.Vill and e.Bari=b.Bari " +
                    " where b.Cluster='"+ Cluster +"' and" +
                    " not exists(Select TableName from sync_management where TableName='"+ TableName +"' and UserId='"+ DeviceID +"' and UniqueId=e.Vill+e.Bari+e.HH+e.MSlNo+e.EvType+e.EvDate+e.Rnd)";

            VariableList = "Vill, Bari, HH, MSlNo, PNo, EvType, EvDate, Info1, Info2, Info3, Info4, VDate, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, modifyDate";
            UniqueField  = "Vill, Bari, HH, MSlNo, EvType, EvDate, Rnd";
            //Res = DownloadJSON_Update_Sync_Management_InsertOnly(SQLStr, TableName, VariableList, UniqueField,DeviceID);
            Sync_Download_Rebuild_Batch(SQLStr, TableName, VariableList, UniqueField,DeviceID,total,batchSize);

            //Code List
            //--------------------------------------------------------------------------------------
            progHandler.post(new Runnable() {
                public void run() {
                    progDialog.setProgress(100);
                    progDialog.setMessage("Finishing Download Process ...");
                }
            });
            Sync_Download("EventCode",DeviceID,"");
            Sync_Download("EDU",DeviceID,"");
            Sync_Download("OCP",DeviceID,"");
            Sync_Download("POA",DeviceID,"");
            Sync_Download("POP",DeviceID,"");
            Sync_Download("POR",DeviceID,"");
            Sync_Download("RTH",DeviceID,"");

            //Round Visit
            this.Sync_Download("RoundVisit", DeviceID, "CurrRound='1'");

            //Update status on server
            //--------------------------------------------------------------------------------------
            ExecuteCommandOnServer("Update DeviceList set Setting='2' where DeviceId='" + DeviceID + "'");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String DownloadJSON_Batch(String SQL, String TableName, String ColumnList, String UniqueField, String UserId) {
        String WhereClause = "";
        int varPos = 0;
        int varPos_modifyDate = 0;

        String response = "";
        String resp = "";

        try {

            DownloadDataJSON dload = new DownloadDataJSON();
            response = dload.execute(SQL).get();

            //Process Response
            DownloadClass d = new DownloadClass();
            Gson gson = new Gson();
            Type collType = new TypeToken<DownloadClass>() {
            }.getType();
            DownloadClass responseData = gson.fromJson(response, collType);

            String UField[] = UniqueField.split(",");
            String VarList[] = ColumnList.split(",");

            List<String> dataStatus = new ArrayList<>();
            String modifyDate = "";
            String UID = "";
            String USID = "";
            String DataList = "";
            DataClassProperty dd;
            List<DataClassProperty> dataTemp = new ArrayList<DataClassProperty>();
            List<DataClassProperty> data     = new ArrayList<DataClassProperty>();

            String downloadSyncStatus = "";

            if (responseData != null & responseData.getdata().size()>0) {
                SQL = "Insert or replace into "+ TableName +"("+ ColumnList +")Values";
                for (int i = 0; i < responseData.getdata().size(); i++) {
                    String VarData[] = split(responseData.getdata().get(i).toString(), '^');

                    //Generate where clause/Unique ID
                    //------------------------------------------------------------------------------
                    //Generate Unique ID
                    //------------------------------------------------------------------------------
                    for (int j = 0; j < UField.length; j++) {
                        varPos = VarPosition(UField[j].toString(), VarList);

                        if (j == 0) {
                            UID += VarData[varPos].toString();
                        } else {
                            UID += VarData[varPos].toString();
                        }
                    }

                    varPos_modifyDate = VarPosition("modifyDate", VarList);
                    modifyDate = VarData[varPos_modifyDate].toString();//.replace("null", "");

                    //UID = Sync_UID(UField, VarList, VarData);
                    //modifyDate = Sync_modifyDate("modifyDate", VarList, VarData);

                    //------------------------------------------------------------------------------
                    if (i == 0) {
                        SQL += "('" + responseData.getdata().get(i).toString().replace("^","','").replace("null","") +"')";
                    } else {
                        SQL += ",('" + responseData.getdata().get(i).toString().replace("^","','").replace("null","") +"')";
                    }

                    //Populate class with data for sync_management
                    //------------------------------------------------------------------------------
                    DataList = TableName + "^" + UID + "^" + UserId + "^" + modifyDate;
                    dd = new DataClassProperty();
                    dd.setdatalist(DataList);
                    dd.setuniquefieldwithdata("" +
                            "TableName='" + TableName + "' and " +
                            "UniqueID='" + UID + "' and " +
                            "UserId='" + UserId + "' and " +
                            "modifyDate='" + modifyDate + "'");
                    dataTemp.add(dd);

                    UID = "";
                }

                //If there have no error then response send back to server
                downloadSyncStatus = SaveData(SQL);
                if(downloadSyncStatus.length()==0){
                    data = dataTemp;
                }else{
                    resp = downloadSyncStatus;
                }


                //30 Jul 2017
                //Update data to Server on sync management
                //------------------------------------------------------------------------------
                DataClass dt = new DataClass();
                dt.settablename("Sync_Management");
                dt.setcolumnlist("TableName, UniqueID, UserId, modifyDate");
                dt.setuniquefields("TableName, UniqueID, UserId, modifyDate");
                dt.setdata(data);

                Gson gson1 = new Gson();
                String json1 = gson1.toJson(dt);
                String resp1 = "";

                UploadDataJSON u = new UploadDataJSON();

                try {
                    resp1 = u.execute(json1).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        } catch (Exception e) {
            resp += e.getMessage();
            e.printStackTrace();
        }

        return resp;
    }


    private String Sync_UID(String[] UField, String[] VarList, String[] VarData){
        String UID = "";
        for (int x = 0; x < UField.length; x++) {
            for (int y = 0; y < VarList.length; y++) {
                if (UField[x].trim().equalsIgnoreCase(VarList[y].toString().trim())) {
                    UID +=  VarData[y].toString();
                    y = VarList.length;
                }
            }
        }
        return UID;
    }

    private String Sync_modifyDate(String modifyDate, String[] VarList, String[] VarData){
        String ModDate = "";
        for (int y = 0; y < VarList.length; y++) {
            if (modifyDate.trim().equalsIgnoreCase(VarList[y].toString().trim())) {
                ModDate +=  VarData[y].toString();
                y = VarList.length;
            }
        }
        return ModDate;
    }


    //For Rebuild database: done
    public void Sync_Download_Rebuild(String TableName, String WhereCondition) {
        String VariableList = "";
        String SQL_VariableList = "";
        String UniqueField = "";
        String SQLString = "";
        String Res = "";

        Cursor cur_H = ReadData("Select ColumnList as columnlist, UniqueID as uniqueid from DatabaseTab where lower(tablename)='" + TableName.toLowerCase() + "'");
        cur_H.moveToFirst();

        while (!cur_H.isAfterLast()) {
            VariableList = cur_H.getString(cur_H.getColumnIndex("columnlist"));
            UniqueField = cur_H.getString(cur_H.getColumnIndex("uniqueid"));
            SQL_VariableList = Convert_VariableList(TableName, VariableList);

            if (WhereCondition.length() == 0) {
                SQLString = "Select " + SQL_VariableList + " from " + TableName;
            } else {
                SQLString = "Select " + SQL_VariableList + " from " + TableName + " Where " + WhereCondition;
            }

            cur_H.moveToNext();
        }
        cur_H.close();

        Res = DownloadJSON_InsertOnly(SQLString, TableName, VariableList, UniqueField);
    }

    public void Sync_Download_Rebuild_Batch(String SQL, String TableName, String VariableList, String UniqueField, String UserId, Integer totalRec,Integer batchSize) {
        String Res = "";

        //calculate total records
        //------------------------------------------------------------------------------------------
        Integer totalRecords = 0;

        if (totalRec == null)
            totalRecords = 0;
        else
            totalRecords = Integer.valueOf(totalRec);

        //Calculate batch size
        //------------------------------------------------------------------------------------------
        //0(zero) means all selected data
        //Integer batchSize = Integer.valueOf(ReturnSingleValue("select ifnull(batchsize,0)batchsize from DatabaseTab where TableName='" + TableName + "'"));
        Integer totalBatch = 1;

        if (batchSize == 0) {
            totalBatch = 1;
            batchSize = totalRecords;
        } else if (batchSize > 0) {
            totalBatch = totalRecords / batchSize;
            if (totalRecords % batchSize > 0)
                totalBatch += 1;
        }

        //Execute batch download
        //------------------------------------------------------------------------------------------
        for (int i = 0; i < totalBatch; i++) {
            //Res = DownloadJSON_Update_Sync_Management_InsertOnly(SQL, TableName, VariableList, UniqueField, UserId);
            Res = DownloadJSON_Batch(SQL, TableName, VariableList, UniqueField, UserId);
        }
    }

    //done
    //batch wise data sync : based on the value of Column BatchSize in DatabaseTab table
    public void Sync_Download(String TableName, String UserId, String WhereClause) {
        //Retrieve sync parameter
        //------------------------------------------------------------------------------------------
        String[] SyncParam = Sync_Parameter(TableName);

        String SQLStr = SyncParam[0];
        String VariableList = SyncParam[1];
        String UniqueField = SyncParam[2];
        String SQL_VariableList = SyncParam[3];
        String Res = "";
        String SQL = "";

        //Generate Unique ID field
        //------------------------------------------------------------------------------------------
        String[] U = UniqueField.split(",");
        String UID = "";
        //String UID_Sync = "";
        for (int i = 0; i < U.length; i++) {
            if (i == 0)
                UID = "cast(t." + U[i] + " as varchar(50))";
            else
                UID += "+cast(t." + U[i] + " as varchar(50))";
        }

        //calculate total records
        //------------------------------------------------------------------------------------------
        Integer totalRecords = 0;
        SQL = "Select Count(*)totalRec from " + TableName + " as t";
        SQL += " where not exists(select * from Sync_Management where";
        SQL += " lower(TableName)  = lower('" + TableName + "') and";
        SQL += " UniqueID   = " + UID + " and";
        SQL += " convert(varchar(19),modifydate,120) = convert(varchar(19),t.modifydate,120) and";

        SQL += " UserId   ='" + UserId + "')";
        if (WhereClause.length() > 0) {
            SQL += " and " + WhereClause;
        }

        String totalRec = ReturnResult("ReturnSingleValue", SQL);
        if (totalRec == null)
            totalRecords = 0;
        else
            totalRecords = Integer.valueOf(totalRec);

        //Calculate batch size
        //------------------------------------------------------------------------------------------
        //0(zero) means all selected data
        Integer batchSize = Integer.valueOf(ReturnSingleValue("select ifnull(batchsize,0)batchsize from DatabaseTab where TableName='" + TableName + "'"));
        Integer totalBatch = 1;

        if (batchSize == 0) {
            totalBatch = 1;
            batchSize = totalRecords;
        } else if (batchSize > 0) {
            totalBatch = totalRecords / batchSize;
            if (totalRecords % batchSize > 0)
                totalBatch += 1;
        }

        //Execute batch download
        //------------------------------------------------------------------------------------------
        try {
            for (int i = 0; i < totalBatch; i++) {
                SQL = "Select top " + batchSize + " " + SQL_VariableList + " from " + TableName + " as t";
                SQL += " where not exists(select * from Sync_Management where";
                SQL += " lower(TableName)  = lower('" + TableName + "') and";
                SQL += " UniqueID   = " + UID + " and";
                SQL += " convert(varchar(19),modifydate,120) = convert(varchar(19),t.modifydate,120) and";
                SQL += " UserId   ='" + UserId + "')";
                if (WhereClause.length() > 0) {
                    SQL += " and " + WhereClause;
                }

                //Res = DownloadJSON_Update_Sync_Management(SQL, TableName, VariableList, UniqueField, UserId);
                Res = DownloadJSON_Batch(SQL, TableName, VariableList, UniqueField, UserId);
            }
        }catch(Exception ex){

        }
    }

    //done
    //download data from server and include those id's into Table: Sync_Management
    /*private String DownloadJSON_Update_Sync_Management(String SQL, String TableName, String ColumnList, String UniqueField, String UserId) {
        String WhereClause = "";
        int varPos = 0;

        String response = "";
        String resp = "";

        try {

            DownloadDataJSON dload = new DownloadDataJSON();
            response = dload.execute(SQL).get();

            //Process Response
            DownloadClass d = new DownloadClass();
            Gson gson = new Gson();
            Type collType = new TypeToken<DownloadClass>() {
            }.getType();
            DownloadClass responseData = gson.fromJson(response, collType);

            String UField[] = UniqueField.split(",");
            String VarList[] = ColumnList.split(",");

            List<String> dataStatus = new ArrayList<>();
            String modifyDate = "";
            String UID = "";
            String USID = "";
            String DataList = "";
            DataClassProperty dd;
            List<DataClassProperty> data = new ArrayList<DataClassProperty>();

            for (int i = 0; i < responseData.getdata().size(); i++) {
                String VarData[] = split(responseData.getdata().get(i).toString(), '^');

                //Generate where clause
                SQL = "";
                WhereClause = "";
                varPos = 0;
                for (int j = 0; j < UField.length; j++) {
                    varPos = VarPosition(UField[j].toString(), VarList);
                    if (j == 0) {
                        WhereClause = UField[j].toString() + "=" + "'" + VarData[varPos].toString().replace("'", "") + "'";
                        UID = VarData[varPos].toString();
                    } else {
                        WhereClause += " and " + UField[j].toString() + "=" + "'" + VarData[varPos].toString().replace("'", "") + "'";
                        UID += VarData[varPos].toString();
                    }
                }

                //Update command
                if (Existence("Select " + VarList[0] + " from " + TableName + " Where " + WhereClause)) {
                    for (int r = 0; r < VarList.length; r++) {
                        if (r == 0) {
                            SQL = "Update " + TableName + " Set ";
                            SQL += VarList[r] + " = '" + VarData[r].toString().replace("'", "") + "'";
                        } else {
                            if (r == VarData.length - 1) {
                                SQL += "," + VarList[r] + " = '" + VarData[r].toString().replace("'", "") + "'";
                                SQL += " Where " + WhereClause;
                            } else {
                                SQL += "," + VarList[r] + " = '" + VarData[r].toString().replace("'", "") + "'";
                            }
                        }

                        if (VarList[r].toString().toLowerCase().equals("modifydate"))
                            modifyDate = VarData[r].toString();
                    }

                    Save(SQL);
                }
                //Insert command
                else {
                    for (int r = 0; r < VarList.length; r++) {
                        if (r == 0) {
                            SQL = "Insert into " + TableName + "(" + ColumnList + ")Values(";
                            SQL += "'" + VarData[r].toString().replace("'", "") + "'";
                        } else {
                            SQL += ",'" + VarData[r].toString().replace("'", "") + "'";
                        }

                        if (VarList[r].toString().toLowerCase().equals("modifydate"))
                            modifyDate = VarData[r].toString();

                    }
                    SQL += ")";

                    Save(SQL);
                }

                DataList = TableName + "^" + UID + "^" + UserId + "^" + modifyDate;
                dd = new DataClassProperty();
                dd.setdatalist(DataList);
                dd.setuniquefieldwithdata("" +
                        "TableName='" + TableName + "' and " +
                        "UniqueID='" + UID + "' and " +
                        "UserId='" + UserId + "' and " +
                        "modifyDate='" + modifyDate + "'");
                data.add(dd);
            }

            DataClass dt = new DataClass();
            dt.settablename("Sync_Management");
            dt.setcolumnlist("TableName, UniqueID, UserId, modifyDate");
            dt.setuniquefields(UniqueField);
            dt.setdata(data);

            Gson gson1 = new Gson();
            String json1 = gson1.toJson(dt);
            String resp1 = "";

            UploadDataJSON u = new UploadDataJSON();

            try {
                resp1 = u.execute(json1).get();
            } catch (Exception e) {
                e.printStackTrace();
            }



        } catch (Exception e) {
            resp = e.getMessage();
            e.printStackTrace();
        }

        return resp;
    }*/


    private String DownloadJSON_Update_Sync_Management_InsertOnly(String SQL, String TableName, String ColumnList, String UniqueField, String UserId) {
        String WhereClause = "";
        int varPos = 0;

        String response = "";
        String resp = "";

        try {

            DownloadDataJSON dload = new DownloadDataJSON();
            response = dload.execute(SQL).get();

            //Process Response
            DownloadClass d = new DownloadClass();
            Gson gson = new Gson();
            Type collType = new TypeToken<DownloadClass>() {
            }.getType();
            DownloadClass responseData = gson.fromJson(response, collType);

            String UField[] = UniqueField.split(",");
            String VarList[] = ColumnList.split(",");

            List<String> dataStatus = new ArrayList<>();
            String modifyDate = "";
            String UID = "";
            String USID = "";
            String DataList = "";
            DataClassProperty dd;
            List<DataClassProperty> data = new ArrayList<DataClassProperty>();



            for (int i = 0; i < responseData.getdata().size(); i++) {
                String VarData[] = split(responseData.getdata().get(i).toString(), '^');

                for (int j = 0; j < UField.length; j++) {
                    varPos = VarPosition(UField[j].toString(), VarList);
                    if (j == 0) {
                        //WhereClause = UField[j].toString() + "=" + "'" + VarData[varPos].toString().replace("'", "") + "'";
                        UID = VarData[varPos].toString();
                    } else {
                        //WhereClause += " and " + UField[j].toString() + "=" + "'" + VarData[varPos].toString().replace("'", "") + "'";
                        UID += VarData[varPos].toString();
                    }
                }

                for (int r = 0; r < VarList.length; r++) {
                    if (r == 0) {
                        SQL = "Insert into " + TableName + "(" + ColumnList + ")Values(";
                        SQL += "'" + VarData[r].toString().replace("'", "") + "'";
                    } else {
                        SQL += ",'" + VarData[r].toString().replace("'", "") + "'";
                    }

                    if (VarList[r].toString().toLowerCase().equals("modifydate"))
                        modifyDate = VarData[r].toString();

                }
                SQL += ")";

                Save(SQL);

                DataList = TableName + "^" + UID + "^" + UserId + "^" + modifyDate;
                dd = new DataClassProperty();
                dd.setdatalist(DataList);
                dd.setuniquefieldwithdata("" +
                        "TableName='" + TableName + "' and " +
                        "UniqueID='" + UID + "' and " +
                        "UserId='" + UserId + "' and " +
                        "modifyDate='" + modifyDate + "'");
                data.add(dd);
            }

            DataClass dt = new DataClass();
            dt.settablename("Sync_Management");
            dt.setcolumnlist("TableName, UniqueID, UserId, modifyDate");
            dt.setuniquefields(UniqueField);
            dt.setdata(data);

            Gson gson1 = new Gson();
            String json1 = gson1.toJson(dt);
            String resp1 = "";

            UploadDataJSON u = new UploadDataJSON();

            try {
                resp1 = u.execute(json1).get();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            resp = e.getMessage();
            e.printStackTrace();
        }

        return resp;
    }


    //done
    public String[] Sync_Parameter(String TableName) {
        String VariableList = "";
        String UniqueField = "";
        String SQLStr = "";
        String SQL_VariableList = "";

        Cursor cur_H = ReadData("Select ColumnList as columnlist, UniqueID as uniqueid from DatabaseTab where tablename='" + TableName + "'");
        cur_H.moveToFirst();

        while (!cur_H.isAfterLast()) {
            SQLStr = "Select " + cur_H.getString(cur_H.getColumnIndex("columnlist")) + " from " + TableName + " Where Upload='2'";
            VariableList = cur_H.getString(cur_H.getColumnIndex("columnlist"));
            SQL_VariableList = Convert_VariableList(TableName, VariableList);
            UniqueField = cur_H.getString(cur_H.getColumnIndex("uniqueid"));

            cur_H.moveToNext();
        }
        cur_H.close();
        String[] ParaList = new String[]{
                SQLStr,
                VariableList,
                UniqueField,
                SQL_VariableList
        };

        return ParaList;
    }

    //done
    private String Convert_VariableList(String TableName, String VariableList) {
        String finalVariableList = "";
        String[] tempList = VariableList.split(",");
        String tempVar = "";
        String temp = "";
        String[] DateVarList = DateVariableList(TableName).split(",");
        int matched = 2;
        for (int i = 0; i < tempList.length; i++) {
            temp = tempList[i];
            matched = 2;

            for (int j = 0; j < DateVarList.length; j++) {
                if (temp.equalsIgnoreCase(DateVarList[j]))
                    matched = 1;
            }

            if (matched == 1) {
                if (temp.equalsIgnoreCase("endt") | temp.equalsIgnoreCase("modifydate") | temp.equalsIgnoreCase("uploaddt"))
                    finalVariableList += finalVariableList.length() == 0 ? "Convert(varchar(19)," + tempList[i] + ",120)" : ", Convert(varchar(19)," + tempList[i] + ",120)";
                else
                    finalVariableList += finalVariableList.length() == 0 ? "Convert(varchar(10)," + tempList[i] + ",120)" : ", Convert(varchar(10)," + tempList[i] + ",120)";
            } else {
                if (temp.equalsIgnoreCase("upload"))
                    finalVariableList += finalVariableList.length() == 0 ? "'1'" : ", '1'";
                else
                    finalVariableList += finalVariableList.length() == 0 ? tempList[i] : ", " + tempList[i];
            }
        }
        return finalVariableList;
    }

    //done
    private String DateVariableList(String TableName) {
        Cursor cur_H = ReadData("PRAGMA table_info('" + TableName + "')");
        cur_H.moveToFirst();
        String temp = "";
        String type = "";
        String name = "";
        String dateVariable = "";
        while (!cur_H.isAfterLast()) {
            type = cur_H.getString(cur_H.getColumnIndex("type"));
            name = cur_H.getString(cur_H.getColumnIndex("name")).toLowerCase();
            if (type.equalsIgnoreCase("date") | type.equalsIgnoreCase("datetime")) {
                dateVariable += dateVariable.length() == 0 ? cur_H.getString(cur_H.getColumnIndex("name")) : "," + cur_H.getString(cur_H.getColumnIndex("name"));
            }

            cur_H.moveToNext();
        }
        cur_H.close();

        return dateVariable;
    }

    //done
    //Upload data to server
    int tabIndex = 0;
    int count = 0;
    public void Sync_Upload(final List<String> tableList, final ProgressDialog progDialog, Handler progHandler) {
        count = 0;
        for (int i = 0; i < tableList.size(); i++) {
            try {
                tabIndex = i;
                count += 15;
                progHandler.post(new Runnable() {
                    public void run() {
                        progDialog.setProgress(count);
                        progDialog.setMessage("Uploading "+ tableList.get(tabIndex).toString() +" Data ... ");
                    }
                });
                Sync_Upload_Process(tableList.get(i).toString(),progDialog,progHandler);
            }catch (Exception ex){

            }
        }
    }

    //done
    public void Sync_Upload_Process(String TableName,ProgressDialog progDialog, Handler progHandler) {
        String VariableList = "";
        String UniqueField = "";
        String SQLStr = "";
        String Res = "";

        Cursor cur_H = ReadData("Select ColumnList as columnlist, UniqueID as uniqueid from DatabaseTab where tablename='" + TableName + "'");
        cur_H.moveToFirst();

        while (!cur_H.isAfterLast()) {
            SQLStr = "Select " + cur_H.getString(cur_H.getColumnIndex("columnlist")) + " from " + TableName + " Where Upload='2'";
            VariableList = cur_H.getString(cur_H.getColumnIndex("columnlist"));
            UniqueField = cur_H.getString(cur_H.getColumnIndex("uniqueid"));
            cur_H.moveToNext();
        }
        cur_H.close();

        Res = UploadJSON(TableName, VariableList, UniqueField);
    }

    private String Discard_UploadDT_modifyDate(String VariableList) {
        String finalVarList = "";
        String[] VList = VariableList.split(",");
        for (int i = 0; i < VList.length; i++) {
            if (!VList[i].equalsIgnoreCase("uploaddt") & !VList[i].equalsIgnoreCase("modifydate"))
                finalVarList += finalVarList.length() == 0 ? VList[i] : "," + VList[i];
        }
        return finalVarList;
    }



    //Download Table List from server
    /*private String[] TableListServer()
    {
        String SQLStr= "";
        DownloadData d = new DownloadData();
        d.Method_Name = "DownloadData";
        d.SQLStr = "Select TableName from DatabaseTab";

        String DataArray[] = null;

        try {
            DataArray = d.execute("").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return DataArray;
    }*/

    //To get the list of columns(string) in table
    //----------------------------------------------------------------------------------------------
    public String GetColumnList(String TableName) {
        String CList = "";
        Cursor cur_H;
        cur_H = ReadData("pragma table_info('" + TableName + "')");

        cur_H.moveToFirst();
        int RecordCount = 0;

        while (!cur_H.isAfterLast()) {
            if (RecordCount == 0)
                CList += cur_H.getString(cur_H.getColumnIndex("name"));
            else
                CList += "," + cur_H.getString(cur_H.getColumnIndex("name"));

            RecordCount += 1;
            cur_H.moveToNext();
        }
        cur_H.close();

        return CList;
    }

    public int GetTotalColumn(String TableName) {
        int totalCol = 0;
        Cursor cur_H;
        cur_H = ReadData("pragma table_info('" + TableName + "')");
        totalCol = cur_H.getColumnCount();
        cur_H.close();

        return totalCol;
    }

    //TableStructureSync
    public void TableStructureSync(String TableName) {
        //Creating Table if not exists
        String tableScript  = ReturnSingleValue("Select TableScript from DatabaseTab where TableName='"+ TableName +"'");
        CreateTable(TableName, tableScript);

        //Local database
        String[] local = GetColumnListArray(TableName);

        //Server database
        String[] Server = ReturnSingleValue("select ColumnList from DatabaseTab where TableName='"+ TableName +"'").toString().split(",");

        String[] C;
        Boolean matched = false;
        String newVariable = "";

        //matched database columns(local and server)
        for (int i = 0; i < Server.length; i++) {
            matched = false;
            for (int j = 0; j < local.length; j++) {
                newVariable = Server[i].toString();
                if (Server[i].toString().toLowerCase().equals(local[j].toString().toLowerCase())) {
                    matched = true;
                    j = local.length;
                }
            }
            if (matched == false) {
                Save("Alter table " + TableName + " add column " + newVariable + " varchar(50) default ''");
            }
        }
    }

    //TableStructureSync
    public void Sync_DatabaseStructure(String UserId)
    {
        //Retrieve sync parameter
        //------------------------------------------------------------------------------------------
        String TableName = "DatabaseTab";
        String[] SyncParam = Sync_Parameter(TableName);

        String SQLStr       = SyncParam[0];
        String VariableList = SyncParam[1];
        String UniqueField  = SyncParam[2];
        String SQL_VariableList  = SyncParam[3];
        String Res = "";
        String SQL = "";

        //Generate Unique ID field
        //------------------------------------------------------------------------------------------
        String[] U = UniqueField.split(",");
        String UID = "";
        //String UID_Sync = "";
        for(int i=0; i<U.length; i++){
            if(i==0)
                UID = "cast(t."+ U[i] +" as varchar(50))";
            else
                UID += "+cast(t."+ U[i] +" as varchar(50))";
        }

        SQL  = "Select "+ SQL_VariableList +" from "+ TableName +" as t";
        SQL += " where not exists(select * from Sync_Management where";
        SQL += " lower(TableName)  = lower('"+ TableName +"') and";
        SQL += " UniqueID   = "+ UID +" and";
        SQL += " convert(varchar(19),modifydate,120) = convert(varchar(19),t.modifydate,120) and";
        SQL += " UserId   ='"+ UserId +"')";

        Res = Sync_DatabaseTab_Management(SQL, TableName, VariableList, UniqueField, UserId);
    }

    //TableStructureSync
    private String Sync_DatabaseTab_Management(String SQL, String TableName,String ColumnList, String UniqueField, String UserId)
    {
        String WhereClause="";
        int varPos=0;

        String response = "";
        String resp = "";

        try{

            DownloadDataJSON dload = new DownloadDataJSON();
            response=dload.execute(SQL).get();

            //Process Response
            DownloadClass d = new DownloadClass();
            Gson gson = new Gson();
            Type collType = new TypeToken<DownloadClass>() {
            }.getType();
            DownloadClass responseData = gson.fromJson(response, collType);

            String UField[]  = UniqueField.split(",");
            String VarList[] = ColumnList.split(",");

            List<String> dataStatus = new ArrayList<>();
            String modifyDate = "";
            String UID        = "";
            String USID       = "";
            String DataList = "";
            DataClassProperty dd;
            List<DataClassProperty> data = new ArrayList<DataClassProperty>();
            String rowTableName = "";
            for(int i=0; i<responseData.getdata().size(); i++)
            {
                String VarData[] = split(responseData.getdata().get(i).toString(),'^');

                //Generate where clause
                SQL="";
                WhereClause="";
                varPos=0;
                for(int j=0; j< UField.length; j++)
                {
                    varPos = VarPosition(UField[j].toString(),VarList);
                    if(j==0)
                    {
                        WhereClause = UField[j].toString()+"="+ "'"+ VarData[varPos].toString().replace("'","") +"'";
                        UID = VarData[varPos].toString();
                    }
                    else
                    {
                        WhereClause += " and "+ UField[j].toString()+"="+ "'"+ VarData[varPos].toString().replace("'","") +"'";
                        UID += VarData[varPos].toString();
                    }
                }

                //Update command
                if(Existence("Select "+ VarList[0] +" from "+ TableName +" Where "+ WhereClause))
                {
                    for(int r=0;r<VarList.length;r++)
                    {
                        if(r==0)
                        {
                            SQL = "Update "+ TableName +" Set ";
                            SQL+= VarList[r] + " = '"+ VarData[r].toString().replace("'","") +"'";
                        }
                        else
                        {
                            if(r == VarData.length-1)
                            {
                                SQL+= ","+ VarList[r] + " = '"+ VarData[r].toString().replace("'","") +"'";
                                SQL += " Where "+ WhereClause;
                            }
                            else
                            {
                                SQL+= ","+ VarList[r] + " = '"+ VarData[r].toString().replace("'","") +"'";
                            }
                        }

                        if(VarList[r].toString().toLowerCase().equals("modifydate"))
                            modifyDate = VarData[r].toString();

                        if(VarList[r].toString().toLowerCase().equals("tablename"))
                            rowTableName = VarData[r].toString();

                    }

                    Save(SQL);

                    TableStructureSync(rowTableName);
                }
                //Insert command
                else
                {
                    for(int r=0;r<VarList.length;r++)
                    {
                        if(r==0)
                        {
                            SQL = "Insert into "+ TableName +"("+ ColumnList +")Values(";
                            SQL+= "'"+ VarData[r].toString().replace("'","") +"'";
                        }
                        else
                        {
                            SQL+= ",'"+ VarData[r].toString().replace("'","") +"'";
                        }

                        if(VarList[r].toString().toLowerCase().equals("modifydate"))
                            modifyDate = VarData[r].toString();

                        if(VarList[r].toString().toLowerCase().equals("tablename"))
                            rowTableName = VarData[r].toString();
                    }
                    SQL += ")";

                    Save(SQL);
                    TableStructureSync(rowTableName);
                }

                DataList = TableName + "^" + UID + "^"+ UserId + "^" + modifyDate;
                dd = new DataClassProperty();
                dd.setdatalist(DataList);
                dd.setuniquefieldwithdata("" +
                        "TableName='"+ TableName +"' and " +
                        "UniqueID='"+ UID +"' and " +
                        "UserId='"+ UserId +"' and " +
                        "modifyDate='"+ modifyDate +"'");
                data.add(dd);
            }

            DataClass dt = new DataClass();
            dt.settablename("Sync_Management");
            dt.setcolumnlist("TableName, UniqueID, UserId, modifyDate");
            dt.setuniquefields(UniqueField);
            dt.setdata(data);

            Gson gson1   = new Gson();
            String json1 = gson1.toJson(dt);
            String resp1 = "";

            UploadDataJSON u = new UploadDataJSON();

            try{
                resp1=u.execute(json1).get();
            } catch (Exception e) {
                e.printStackTrace();
            }



        } catch (Exception e) {
            resp = e.getMessage();
            e.printStackTrace();
        }

        return resp;
    }

    //To get the list of columns(string array) in table
    //----------------------------------------------------------------------------------------------
    public String[] GetColumnListArray(String TableName)
    {
        //List<String> varList = new ArrayList<String>();
        Cursor cur = ReadData("SELECT * FROM " + TableName + " WHERE 0");
        String[] columnNames;
        try {
            columnNames = cur.getColumnNames();
        } finally {
            cur.close();
        }
        return columnNames;
    }

    public boolean InsertData(String TableName, ContentValues content_value) {
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TableName, null, content_value);
        return true;
    }

    public boolean UpdateData(String TableName, String UniqueID_Field, String UniqueID, ContentValues content_value) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TableName, content_value, UniqueID_Field + " = ? ", new String[]{UniqueID});
        return true;
    }

    public Integer DeleteData(String TableName, String UniqueID_Field, String UniqueID) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TableName,
                UniqueID_Field + " = ? ",
                new String[]{UniqueID});
    }

    public Cursor GetData(String TableName, String UniqueID_Field, String UniqueID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TableName + " WHERE " +
                UniqueID_Field + "=?", new String[]{UniqueID});
        return res;
    }

    public Cursor GetAllData(String TableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TableName, null);
        return res;
    }


    public void DatabaseUpload(String DeviceID) {
        //Upload File from Specific Folder
        String[] FilePathStrings;
        String[] FileNameStrings;
        File[] listFile;

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + Global.DatabaseFolder);
        file.mkdirs();
        if (file.isDirectory()) {
            listFile = file.listFiles();
            FilePathStrings = new String[listFile.length];
            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                FileNameStrings[i] = listFile[i].getName();

                //Upload file to server
                FileUpload myTask = new FileUpload();
                String[] params = new String[2];
                if (listFile[i].getName().equalsIgnoreCase(ProjectSetting.DatabaseName)) {
                    params[0] = listFile[i].getName();
                    params[1] = DeviceID + "_" + Global.CurrentDMY() + "_" + listFile[i].getName();
                    myTask.execute(params);
                }
            }
        }
    }

    private void zipDatabase(String DeviceID)
    {
        CompressZip compressZip = new CompressZip();
        String[] dbFile = new String[1];
        dbFile[0] = Environment.getExternalStorageDirectory() + File.separator + Global.DatabaseFolder + File.separator + ProjectSetting.DatabaseName;
        String dbFolder = Environment.getExternalStorageDirectory() + File.separator + Global.DatabaseFolder;
        String output   = ProjectSetting.zipDatabaseName;
        compressZip.zip(dbFile, dbFolder, output);
    }

    public void DatabaseUploadZip(String DeviceID) {

        //Compress database
        zipDatabase(DeviceID);

        //Upload File from Specific Folder
        String[] FilePathStrings;
        String[] FileNameStrings;
        File[] listFile;

        //
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + Global.DatabaseFolder);
        file.mkdirs();
        if (file.isDirectory()) {
            listFile = file.listFiles();
            FilePathStrings = new String[listFile.length];
            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                FileNameStrings[i] = listFile[i].getName();

                //Upload file to server
                FileUpload myTask = new FileUpload();
                String[] params = new String[2];

                if (listFile[i].getName().equalsIgnoreCase(ProjectSetting.zipDatabaseName)) {
                    params[0] = listFile[i].getName();
                    params[1] = DeviceID + "_" + Global.CurrentDMY() + "_" + listFile[i].getName();
                    myTask.execute(params);
                }
            }
        }
    }

    public static void SyncDataService(String UniqueID)
    {
        try {
            Connection C = new Connection(ud_context);

            //Reqular data sync
            //--------------------------------------------------------------------------------------
            C.Sync_DatabaseStructure(UniqueID);
            C.Sync_Download("RoundVisit",UniqueID,"CurrRound='1'");

            //Download data from server
            //--------------------------------------------------------------------------------------
            String SQLStr = "";
            String SQLStr1 = "";
            String TableName;
            String VariableList;
            String UniqueField;
            String Res;


            //for surveillance
            String Cluster = C.ReturnSingleValue("Select Cluster from Baris limit 1");

            //Baris
            TableName    = "Baris";
            SQLStr = "Select Vill, Bari, Cluster, Block, BariName, BariLoc, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, modifyDate " +
                    " from Baris b where Cluster='"+ Cluster +"' and" +
                    " not exists(Select TableName from sync_management where TableName='"+ TableName +"' and UserId='"+ UniqueID +"' and UniqueId=b.Vill+b.Bari and convert(varchar(19),modifydate,120) = convert(varchar(19),b.modifydate,120))";

            VariableList = "Vill, Bari, Cluster, Block, BariName, BariLoc, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, modifyDate";
            UniqueField  = "Vill, Bari";
            Res = C.DownloadJSON_Batch(SQLStr, TableName, VariableList, UniqueField,UniqueID);

            //Household
            TableName    = "Household";
            SQLStr  = "Select h.Vill, h.Bari, HH, Religion, MobileNo1, MobileNo2, HHHead, TotMem, TotRWo, EnType, EnDate, ExType, ExDate, Rnd," +
                    " h.StartTime, h.EndTime, h.DeviceID, h.EntryUser, h.Lat, h.Lon, h.EnDt, '1' Upload, h.modifyDate" +
                    " from Baris b" +
                    " inner join Household h on b.Vill=h.Vill and b.Bari=h.Bari" +
                    " where b.Cluster='"+ Cluster +"' and" +
                    " not exists(Select TableName from sync_management where TableName='"+ TableName +"' and UserId='"+ UniqueID +"' and UniqueId=h.Vill+h.Bari+h.HH and convert(varchar(19),modifydate,120) = convert(varchar(19),h.modifydate,120))";


            VariableList = "Vill, Bari, HH, Religion, MobileNo1, MobileNo2, HHHead, TotMem, TotRWo, EnType, EnDate, ExType, ExDate, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, modifyDate";
            UniqueField  = "Vill, Bari, HH";
            Res = C.DownloadJSON_Batch(SQLStr, TableName, VariableList, UniqueField,UniqueID);

            //SES
            TableName    = "SES";
            SQLStr  = " Select s.Vill, s.Bari, s.HH, SESNo, VDate, VStatus, VStatusOth, s.Rnd, WSDrink, WSDrinkOth, WSCook, WSCookOth, WSWash, WSWashOth, Latrine, LatrineOth,";
            SQLStr += " Electricity, Radio, TV, Mobile, Telephone, Refrige, Watch, ElecFan, RickVan, Bicycle, MotCycle, Computer, Buffalo, Bull, Goat, Chicken, Pigeon,";
            SQLStr += " Roof, RoofOth, Wall, WallOth, Floor, FloorOth, Homestead, HomesteadOth, OthLand, s.StartTime, s.EndTime, s.DeviceID, s.EntryUser, s.Lat, s.Lon, s.EnDt, '1' Upload, s.modifyDate";
            SQLStr += " from Baris b";
            SQLStr += " inner join Household h on b.Vill=h.Vill and b.Bari=h.Bari";
            SQLStr += " inner join SES s on h.Vill=s.vill and h.Bari=s.Bari and h.hh=s.hh";
            SQLStr += " where b.Cluster='"+ Cluster +"' and " +
                    " not exists(Select TableName from sync_management where TableName='"+ TableName +"' and UserId='"+ UniqueID +"' and UniqueId=s.Vill+s.Bari+s.HH+s.SESNo and convert(varchar(19),modifydate,120) = convert(varchar(19),s.modifydate,120))";

            VariableList = "Vill, Bari, HH, SESNo, VDate, VStatus, VStatusOth, Rnd, WSDrink, WSDrinkOth, WSCook, WSCookOth, WSWash, WSWashOth, Latrine, LatrineOth, Electricity, Radio, TV, Mobile, Telephone, Refrige, Watch, ElecFan, RickVan, Bicycle, MotCycle, Computer, Buffalo, Bull, Goat, Chicken, Pigeon, Roof, RoofOth, Wall, WallOth, Floor, FloorOth, Homestead, HomesteadOth, OthLand, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, modifyDate";
            UniqueField  = "Vill, Bari, HH, SESNo";
            Res = C.DownloadJSON_Batch(SQLStr, TableName, VariableList, UniqueField,UniqueID);

            //Member
            TableName    = "Member";
            SQLStr  = "Select top 500 m.Vill, m.Bari, m.HH, MSlNo, PNo, Name, Rth, Sex, BDate, AgeY, MoNo, FaNo, Edu, MS, Ocp, Sp1, Sp2, Sp3, Sp4, Pstat, LmpDt, m.EnType, m.EnDate, m.ExType, m.ExDate, NeedReview, PosMig, PosMigDate, m.StartTime, m.EndTime, m.DeviceID, m.EntryUser, m.Lat, m.Lon, m.EnDt, '1' Upload, m.modifyDate " +
                    " from Baris b " +
                    " inner join Household h on b.Vill=h.Vill and b.Bari=h.Bari " +
                    " inner join Member m on h.Vill=m.vill and h.Bari=m.Bari and h.hh=m.hh " +
                    " where b.Cluster='"+ Cluster +"' and " +
                    " not exists(Select TableName from sync_management where TableName='"+ TableName +"' and UserId='"+ UniqueID +"' and UniqueId=m.Vill+m.Bari+m.HH+m.MSlNo and convert(varchar(19),modifydate,120) = convert(varchar(19),m.modifydate,120))";

            VariableList = "Vill, Bari, HH, MSlNo, PNo, Name, Rth, Sex, BDate, AgeY, MoNo, FaNo, Edu, MS, Ocp, Sp1, Sp2, Sp3, Sp4, Pstat, LmpDt, EnType, EnDate, ExType, ExDate, NeedReview, PosMig, PosMigDate, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, modifyDate";
            UniqueField  = "Vill, Bari, HH, MSlNo";
            Res = C.DownloadJSON_Batch(SQLStr, TableName, VariableList, UniqueField,UniqueID);

            //PregHis
            TableName    = "PregHis";
            SQLStr  = " Select s.Vill, s.Bari, s.HH, MSlNo, PNo, VDate, VStatus, VStatusOth, MarriageStatus, MarMon, MarYear, MarDK, GaveBirth, ChildLivWWo, SonLivWWo, DaugLivWWo,";
            SQLStr += " ChldLivOut, SonLivOut, DaugLivOut, ChldDie, BoyDied, GirlDied, NotLivBrth, TotLB, TotPregOut, CurPreg, LMPDate, s.StartTime, s.EndTime, s.DeviceID,";
            SQLStr += " s.EntryUser, s.Lat, s.Lon, s.EnDt, '1' Upload, s.modifyDate";
            SQLStr += " from Baris b";
            SQLStr += " inner join Household h on b.Vill=h.Vill and b.Bari=h.Bari";
            SQLStr += " inner join PregHis s on h.Vill=s.vill and h.Bari=s.Bari and h.hh=s.hh";
            SQLStr += " where b.Cluster='"+ Cluster +"' and " +
                    " not exists(Select TableName from sync_management where TableName='"+ TableName +"' and UserId='"+ UniqueID +"' and UniqueId=s.Vill+s.Bari+s.HH+s.MslNo and convert(varchar(19),modifydate,120) = convert(varchar(19),s.modifydate,120))";

            VariableList = "Vill, Bari, HH, MSlNo, PNo, VDate, VStatus, VStatusOth, MarriageStatus, MarMon, MarYear, MarDK, GaveBirth, ChildLivWWo, SonLivWWo, DaugLivWWo, ChldLivOut, SonLivOut, DaugLivOut, ChldDie, BoyDied, GirlDied, NotLivBrth, TotLB, TotPregOut, CurPreg, LMPDate, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, modifyDate";
            UniqueField  = "Vill, Bari, HH, MSlNo";
            Res = C.DownloadJSON_Batch(SQLStr, TableName, VariableList, UniqueField,UniqueID);

            //Visits
            TableName    = "Visits";
            SQLStr  = " Select s.Vill, s.Bari, s.HH, VDate, VStatus, VStatusOth, VisitNo, Resp, s.Rnd, s.StartTime, s.EndTime, s.DeviceID, s.EntryUser, s.Lat, s.Lon, s.EnDt, '1' Upload, s.modifyDate";
            SQLStr += " from Baris b";
            SQLStr += " inner join Household h on b.Vill=h.Vill and b.Bari=h.Bari";
            SQLStr += " inner join Visits s on h.Vill=s.vill and h.Bari=s.Bari and h.hh=s.hh";
            SQLStr += " where b.Cluster='"+ Cluster +"' and " +
                    " not exists(Select TableName from sync_management where TableName='"+ TableName +"' and UserId='"+ UniqueID +"' and UniqueId=s.Vill+s.Bari+s.HH+s.Rnd and convert(varchar(19),modifydate,120) = convert(varchar(19),s.modifydate,120))";

            VariableList = "Vill, Bari, HH, VDate, VStatus, VStatusOth, VisitNo, Resp, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, modifyDate";
            UniqueField  = "Vill, Bari, HH, Rnd";
            Res = C.DownloadJSON_Batch(SQLStr, TableName, VariableList, UniqueField,UniqueID);

            //Events
            TableName    = "Events";
            SQLStr  = "Select e.Vill, e.Bari, HH, MSlNo, PNo, EvType, EvDate, Info1, Info2, Info3, Info4, VDate, Rnd," +
                    " e.StartTime, e.EndTime, e.DeviceID, e.EntryUser, e.Lat, e.Lon, e.EnDt, '1' Upload, e.modifyDate from Events e " +
                    "inner join Baris b on e.Vill=b.Vill and e.Bari=b.Bari " +
                    " where b.Cluster='"+ Cluster +"' and " +
                    " not exists(Select TableName from sync_management where TableName='"+ TableName +"' and UserId='"+ UniqueID +"' and UniqueId=e.Vill+e.Bari+e.HH+e.MSlNo+e.EvType+e.EvDate+e.Rnd and convert(varchar(19),modifydate,120) = convert(varchar(19),e.modifydate,120))";

            VariableList = "Vill, Bari, HH, MSlNo, PNo, EvType, EvDate, Info1, Info2, Info3, Info4, VDate, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, modifyDate";
            UniqueField  = "Vill, Bari, HH, MSlNo, EvType, EvDate, Rnd";
            Res = C.DownloadJSON_Batch(SQLStr, TableName, VariableList, UniqueField,UniqueID);

            //Code List
            C.Sync_Download("Village", UniqueID, "");
            C.Sync_Download("migMember",UniqueID,"");
            C.Sync_Download("migPregHis",UniqueID,"");
            C.Sync_Download("EventCode",UniqueID,"");
            C.Sync_Download("EDU",UniqueID,"");
            C.Sync_Download("OCP",UniqueID,"");
            C.Sync_Download("POA",UniqueID,"");
            C.Sync_Download("POP",UniqueID,"");
            C.Sync_Download("POR",UniqueID,"");
            C.Sync_Download("RTH",UniqueID,"");

            //Data Correction Note
            C.Sync_Download("DataCorrectionNote",UniqueID,"Cluster='"+ Cluster +"'");
        }
        catch(Exception ex)
        {
        }
    }

    public String TransactionDataInsert(String SQL1,String SQL2,String SQL3,String SQL4) {
        String response = "";
        SQLiteDatabase database = this.getWritableDatabase();
        //db.execSQL(SQL);
        //db.close();
        database.beginTransaction();
        try {
            if(SQL1.length()>0) database.execSQL(SQL1);
            if(SQL2.length()>0) database.execSQL(SQL2);
            if(SQL3.length()>0) database.execSQL(SQL3);
            if(SQL4.length()>0) database.execSQL(SQL4);

            database.setTransactionSuccessful();
        } catch (Exception e) {
            response = e.getMessage();
        } finally {
            database.endTransaction();

            database.close();
        }
        return response;
    }
}