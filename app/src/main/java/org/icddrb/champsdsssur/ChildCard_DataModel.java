package org.icddrb.champsdsssur;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import Common.Connection;

public class ChildCard_DataModel {

       private String _Vill = "";
       public String getVill(){
             return _Vill;
        }
       public void setVill(String newValue){
             _Vill = newValue;
        }
       private String _Bari = "";
       public String getBari(){
             return _Bari;
        }
       public void setBari(String newValue){
             _Bari = newValue;
        }
       private String _HH = "";
       public String getHH(){
             return _HH;
        }
       public void setHH(String newValue){
             _HH = newValue;
        }
       private String _MSlNo = "";
       public String getMSlNo(){
             return _MSlNo;
        }
       public void setMSlNo(String newValue){
             _MSlNo = newValue;
        }
       private String _PNo = "";
       public String getPNo(){
             return _PNo;
        }
       public void setPNo(String newValue){
             _PNo = newValue;
        }
       private String _ReqDate = "";
       public String getReqDate(){
             return _ReqDate;
        }
       public void setReqDate(String newValue){
             _ReqDate = newValue;
        }
       private String _Process = "";
       public String getProcess(){
             return _Process;
        }
       public void setProcess(String newValue){
             _Process = newValue;
        }
       private String _ProcessDT = "";
       public String getProcessDT(){
             return _ProcessDT;
        }
       public void setProcessDT(String newValue){
             _ProcessDT = newValue;
        }

       private String _Upload = "2";

       String TableName = "ChildCardRequest";

    public String TransactionSQL(Context context)
    {
        C = new Connection(context);
        String SQL = "";
        try
        {
            if(C.Existence("Select * from "+ TableName +"  Where Vill='"+ _Vill +"' and Bari='"+ _Bari +"' and HH='"+ _HH +"' and MSlNo='"+ _MSlNo +"' "))
                SQL = "Update "+ TableName +" Set Upload='2',Vill = '"+ _Vill +"',Bari = '"+ _Bari +"',HH = '"+ _HH +"',MSlNo = '"+ _MSlNo +"',PNo = '"+ _PNo +"',ReqDate = '"+ _ReqDate +"',Process = '"+ _Process +"',ProcessDT = '"+ _ProcessDT +"'  Where Vill='"+ _Vill +"' and Bari='"+ _Bari +"' and HH='"+ _HH +"' and MSlNo='"+ _MSlNo +"'";
            else
                SQL = "Insert into "+ TableName +" (Vill,Bari,HH,MSlNo,PNo,ReqDate,Process,ProcessDT,Upload)Values('"+ _Vill +"', '"+ _Bari +"', '"+ _HH +"', '"+ _MSlNo +"', '"+ _PNo +"', '"+ _ReqDate +"', '"+ _Process +"', '"+ _ProcessDT +"', '"+ _Upload +"')";

        }
        catch(Exception  e)
        {
            SQL = e.getMessage();
        }finally {
            C.close();
        }
        return SQL;
    }

       public String SaveUpdateData(Context context)
       {
           String response = "";
           C = new Connection(context);
           String SQL = "";
           try
           {
                if(C.Existence("Select * from "+ TableName +"  Where Vill='"+ _Vill +"' and Bari='"+ _Bari +"' and HH='"+ _HH +"' and MSlNo='"+ _MSlNo +"' "))
                   response = UpdateData(context);
                else
                   response = SaveData(context);
           }
           catch(Exception  e)
           {
                response = e.getMessage();
           }finally {
               C.close();
           }
          return response;
       }
       Connection C;

       private String SaveData(Context context)
       {
           String response = "";
           C = new Connection(context);
           String SQL = "";
           try
             {
                SQL = "Insert into "+ TableName +" (Vill,Bari,HH,MSlNo,PNo,ReqDate,Process,ProcessDT,Upload)Values('"+ _Vill +"', '"+ _Bari +"', '"+ _HH +"', '"+ _MSlNo +"', '"+ _PNo +"', '"+ _ReqDate +"', '"+ _Process +"', '"+ _ProcessDT +"', '"+ _Upload +"')";
                C.Save(SQL);
             }
             catch(Exception  e)
             {
                response = e.getMessage();
             }finally {
               C.close();
           }
          return response;
       }

       private String UpdateData(Context context)
       {
           String response = "";
           C = new Connection(context);
           String SQL = "";
           try
             {
                SQL = "Update "+ TableName +" Set Upload='2',Vill = '"+ _Vill +"',Bari = '"+ _Bari +"',HH = '"+ _HH +"',MSlNo = '"+ _MSlNo +"',PNo = '"+ _PNo +"',ReqDate = '"+ _ReqDate +"',Process = '"+ _Process +"',ProcessDT = '"+ _ProcessDT +"'  Where Vill='"+ _Vill +"' and Bari='"+ _Bari +"' and HH='"+ _HH +"' and MSlNo='"+ _MSlNo +"'";
                C.Save(SQL);
             }
             catch(Exception  e)
             {
                response = e.getMessage();
             }finally {
               C.close();
           }
          return response;
       }


       public List<ChildCard_DataModel> SelectAll(Context context, String SQL)
       {
           Connection C = new Connection(context);
           List<ChildCard_DataModel> data = new ArrayList<ChildCard_DataModel>();
           ChildCard_DataModel d = new ChildCard_DataModel();
           Cursor cur = C.ReadData(SQL);

           cur.moveToFirst();
           while(!cur.isAfterLast())
           {
               d = new ChildCard_DataModel();
               d._Vill = cur.getString(cur.getColumnIndex("Vill"));
               d._Bari = cur.getString(cur.getColumnIndex("Bari"));
               d._HH = cur.getString(cur.getColumnIndex("HH"));
               d._MSlNo = cur.getString(cur.getColumnIndex("MSlNo"));
               d._PNo = cur.getString(cur.getColumnIndex("PNo"));
               d._ReqDate = cur.getString(cur.getColumnIndex("ReqDate"));
               d._Process = cur.getString(cur.getColumnIndex("Process"));
               d._ProcessDT = cur.getString(cur.getColumnIndex("ProcessDT"));
               data.add(d);

               cur.moveToNext();
           }
           cur.close();
           C.close();
         return data;
       }
}