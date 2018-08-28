package org.icddrb.champsdsssur;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import Common.Connection;

public class Events_DataModel_Main {

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
       private String _EvType = "";
       public String getEvType(){
             return _EvType;
        }
       public void setEvType(String newValue){
             _EvType = newValue;
        }
       private String _EvDate = "";
       public String getEvDate(){
             return _EvDate;
        }
       public void setEvDate(String newValue){
             _EvDate = newValue;
        }
       private String _Info1 = "";
       public String getInfo1(){
             return _Info1;
        }
       public void setInfo1(String newValue){
             _Info1 = newValue;
        }
       private String _Info2 = "";
       public String getInfo2(){
             return _Info2;
        }
       public void setInfo2(String newValue){
             _Info2 = newValue;
        }
       private String _Info3 = "";
       public String getInfo3(){
             return _Info3;
        }
       public void setInfo3(String newValue){
             _Info3 = newValue;
        }

       private String _Info4 = "";
       public String getInfo4(){
             return _Info4;
        }
       public void setInfo4(String newValue){ _Info4 = newValue;  }

       private String _Info5 = "";
       public String getInfo5(){
            return _Info5;
        }
       public void setInfo5(String newValue){ _Info5 = newValue;  }

       private String _VDate = "";
       public String getVDate(){
             return _VDate;
        }
       public void setVDate(String newValue){
             _VDate = newValue;
        }
       private String _Rnd = "";
       public String getRnd(){
             return _Rnd;
        }
       public void setRnd(String newValue){
             _Rnd = newValue;
        }
    private String _StartTime = "";
    public void setStartTime(String newValue){
        _StartTime = newValue;
    }
    public String getStartTime(){
        return _StartTime;
    }

    private String _EndTime = "";
    public void setEndTime(String newValue){
        _EndTime = newValue;
    }
    public String getEndTime(){
        return _EndTime;
    }
    private String _DeviceID = "";
    public void setDeviceID(String newValue){
        _DeviceID = newValue;
    }
    public String getDeviceID(){
        return _DeviceID;
    }

    private String _EntryUser = "";
    public void setEntryUser(String newValue){
        _EntryUser = newValue;
    }
    public String getEntryUser(){
        return _EntryUser;
    }

    private String _Lat = "";
    public void setLat(String newValue){
        _Lat = newValue;
    }
    public String getLat(){
        return _Lat;
    }

    private String _Lon = "";
    public void setLon(String newValue){
        _Lon = newValue;
    }
    public String getLon(){
        return _Lon;
    }

    private String _EnDt = "";
    public void setEnDt(String newValue){
        _EnDt = newValue;
    }
    public String getEnDt(){
        return _EnDt;
    }

       private String _Upload = "2";
       private String _modifyDate = "";
       public void setmodifyDate(String newValue){
       _modifyDate = newValue;
       }

       String TableName = "Events";

       public String SaveUpdateData(Context context)
       {
           String response = "";
           C = new Connection(context);
           String SQL = "";
           try
           {
                if(C.Existence("Select * from "+ TableName +"  Where Vill='"+ _Vill +"' and Bari='"+ _Bari +"' and HH='"+ _HH +"' and MSlNo='"+ _MSlNo +"' and EvType='"+ _EvType +"' and EvDate='"+ _EvDate +"' and Rnd='"+ _Rnd +"' "))
                   response = UpdateData(context);
                else
                   response = SaveData(context);
           }
           catch(Exception  e)
           {
                response = e.getMessage();
           }
          return response;
       }

    public String TransactionSQL(Context context)
    {
        C = new Connection(context);
        String SQL = "";
        try
        {
            if(C.Existence("Select * from "+ TableName +"  Where Vill='"+ _Vill +"' and Bari='"+ _Bari +"' and HH='"+ _HH +"' and MSlNo='"+ _MSlNo +"' and EvType='"+ _EvType +"' and EvDate='"+ _EvDate +"' and Rnd='"+ _Rnd +"' "))
                SQL = "Update "+ TableName +" Set Upload='2',modifyDate='" + _modifyDate + "' ,Vill = '"+ _Vill +"',Bari = '"+ _Bari +"',HH = '"+ _HH +"',MSlNo = '"+ _MSlNo +"',PNo = '"+ _PNo +"',EvType = '"+ _EvType +"',EvDate = '"+ _EvDate +"',Info1 = '"+ _Info1 +"',Info2 = '"+ _Info2 +"',Info3 = '"+ _Info3 +"',Info4 = '"+ _Info4 +"',Info5 = '"+ _Info5 +"',VDate = '"+ _VDate +"',Rnd = '"+ _Rnd +"'  Where Vill='"+ _Vill +"' and Bari='"+ _Bari +"' and HH='"+ _HH +"' and MSlNo='"+ _MSlNo +"' and EvType='"+ _EvType +"' and EvDate='"+ _EvDate +"' and Rnd='"+ _Rnd +"'";
            else
                SQL = "Insert into "+ TableName +" (Vill,Bari,HH,MSlNo,PNo,EvType,EvDate,Info1,Info2,Info3,Info4,Info5,VDate,Rnd,StartTime,EndTime,DeviceID,EntryUser,Lat,Lon,EnDt,Upload,modifyDate)Values('"+ _Vill +"', '"+ _Bari +"', '"+ _HH +"', '"+ _MSlNo +"', '"+ _PNo +"', '"+ _EvType +"', '"+ _EvDate +"', '"+ _Info1 +"', '"+ _Info2 +"', '"+ _Info3 +"', '"+ _Info4 +"', '"+ _Info5 +"', '"+ _VDate +"', '"+ _Rnd +"', '"+ _StartTime +"', '"+ _EndTime +"', '"+ _DeviceID +"', '"+ _EntryUser +"', '"+ _Lat +"', '"+ _Lon +"', '"+ _EnDt +"', '"+ _Upload +"', '"+ _modifyDate +"')";
        }
        catch(Exception  e)
        {
            SQL = e.getMessage();
        }
        return SQL;
    }

       Connection C;

       private String SaveData(Context context)
       {
           String response = "";
           C = new Connection(context);
           String SQL = "";
           try
             {
                SQL = "Insert into "+ TableName +" (Vill,Bari,HH,MSlNo,PNo,EvType,EvDate,Info1,Info2,Info3,Info4,Info5,VDate,Rnd,StartTime,EndTime,DeviceID,EntryUser,Lat,Lon,EnDt,Upload,modifyDate)Values('"+ _Vill +"', '"+ _Bari +"', '"+ _HH +"', '"+ _MSlNo +"', '"+ _PNo +"', '"+ _EvType +"', '"+ _EvDate +"', '"+ _Info1 +"', '"+ _Info2 +"', '"+ _Info3 +"', '"+ _Info4 +"', '"+ _Info5 +"', '"+ _VDate +"', '"+ _Rnd +"', '"+ _StartTime +"', '"+ _EndTime +"', '"+ _DeviceID +"', '"+ _EntryUser +"', '"+ _Lat +"', '"+ _Lon +"', '"+ _EnDt +"', '"+ _Upload +"', '"+ _modifyDate +"')";
                C.Save(SQL);
                C.close();
             }
             catch(Exception  e)
             {
                response = e.getMessage();
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
                SQL = "Update "+ TableName +" Set Upload='2',modifyDate='" + _modifyDate + "' ,Vill = '"+ _Vill +"',Bari = '"+ _Bari +"',HH = '"+ _HH +"',MSlNo = '"+ _MSlNo +"',PNo = '"+ _PNo +"',EvType = '"+ _EvType +"',EvDate = '"+ _EvDate +"',Info1 = '"+ _Info1 +"',Info2 = '"+ _Info2 +"',Info3 = '"+ _Info3 +"',Info4 = '"+ _Info4 +"',Info5 = '"+ _Info5 +"',VDate = '"+ _VDate +"',Rnd = '"+ _Rnd +"'  Where Vill='"+ _Vill +"' and Bari='"+ _Bari +"' and HH='"+ _HH +"' and MSlNo='"+ _MSlNo +"' and EvType='"+ _EvType +"' and EvDate='"+ _EvDate +"' and Rnd='"+ _Rnd +"'";
                C.Save(SQL);
                C.close();
             }
             catch(Exception  e)
             {
                response = e.getMessage();
             }
          return response;
       }


       public List<Events_DataModel_Main> SelectAll(Context context, String SQL)
       {
           Connection C = new Connection(context);
           List<Events_DataModel_Main> data = new ArrayList<Events_DataModel_Main>();
           Events_DataModel_Main d = new Events_DataModel_Main();
           Cursor cur = C.ReadData(SQL);

           cur.moveToFirst();
           while(!cur.isAfterLast())
           {
               d = new Events_DataModel_Main();
               d._Vill = cur.getString(cur.getColumnIndex("Vill"));
               d._Bari = cur.getString(cur.getColumnIndex("Bari"));
               d._HH = cur.getString(cur.getColumnIndex("HH"));
               d._MSlNo = cur.getString(cur.getColumnIndex("MSlNo"));
               d._PNo = cur.getString(cur.getColumnIndex("PNo"));
               d._EvType = cur.getString(cur.getColumnIndex("EvType"));
               d._EvDate = cur.getString(cur.getColumnIndex("EvDate"));
               d._Info1 = cur.getString(cur.getColumnIndex("Info1"));
               d._Info2 = cur.getString(cur.getColumnIndex("Info2"));
               d._Info3 = cur.getString(cur.getColumnIndex("Info3"));
               d._Info4 = cur.getString(cur.getColumnIndex("Info4"));
               d._Info5 = cur.getString(cur.getColumnIndex("Info5"));
               d._VDate = cur.getString(cur.getColumnIndex("VDate"));
               d._Rnd = cur.getString(cur.getColumnIndex("Rnd"));

               d._EnDt= cur.getString(cur.getColumnIndex("EnDt"));
               d._StartTime= cur.getString(cur.getColumnIndex("StartTime"));
               d._EndTime= cur.getString(cur.getColumnIndex("EndTime"));
               d._DeviceID= cur.getString(cur.getColumnIndex("DeviceID"));
               d._EntryUser= cur.getString(cur.getColumnIndex("EntryUser"));
               d._Lat= cur.getString(cur.getColumnIndex("Lat"));
               d._Lon= cur.getString(cur.getColumnIndex("Lon"));

               data.add(d);


               cur.moveToNext();
           }
           cur.close();
         return data;
       }
}