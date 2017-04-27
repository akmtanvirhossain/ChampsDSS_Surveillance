package org.icddrb.champsdsssur;

import android.content.Context;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;
 public class Household_DataModel{

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
        private String _Religion = "";
        public String getReligion(){
              return _Religion;
         }
        private String _Note = "";
        public String getNote(){
             return _Note;
         }
        public void setNote(String newValue){
         _Note = newValue;
     }
        public void setReligion(String newValue){
              _Religion = newValue;
         }
        private String _MobileNo1 = "";
        public String getMobileNo1(){
              return _MobileNo1;
         }
        public void setMobileNo1(String newValue){
              _MobileNo1 = newValue;
         }
        private String _MobileNo2 = "";
        public String getMobileNo2(){
              return _MobileNo2;
         }
        public void setMobileNo2(String newValue){
              _MobileNo2 = newValue;
         }
        private String _HHHead = "";
        public String getHHHead(){
              return _HHHead;
         }
        public void setHHHead(String newValue){
              _HHHead = newValue;
         }
        private String _TotMem = "";
        public String getTotMem(){
              return _TotMem;
         }
        public void setTotMem(String newValue){
              _TotMem = newValue;
         }
        private String _TotRWo = "";
        public String getTotRWo(){
              return _TotRWo;
         }
        public void setTotRWo(String newValue){
              _TotRWo = newValue;
         }
        private String _EnType = "";
        public String getEnType(){
              return _EnType;
         }
        public void setEnType(String newValue){
              _EnType = newValue;
         }
        private String _EnDate = "";
        public String getEnDate(){
              return _EnDate;
         }
        public void setEnDate(String newValue){
              _EnDate = newValue;
         }
        private String _ExType = "";
        public String getExType(){
              return _ExType;
         }
        public void setExType(String newValue){
              _ExType = newValue;
         }
        private String _ExDate = "";
        public String getExDate(){
              return _ExDate;
         }
        public void setExDate(String newValue){
              _ExDate = newValue;
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
        private String _EndTime = "";
        public void setEndTime(String newValue){
              _EndTime = newValue;
         }
        private String _DeviceID = "";
        public void setDeviceID(String newValue){
              _DeviceID = newValue;
         }
        private String _EntryUser = "";
        public void setEntryUser(String newValue){
              _EntryUser = newValue;
         }
        private String _Lat = "";
        public void setLat(String newValue){
              _Lat = newValue;
         }
        private String _Lon = "";
        public void setLon(String newValue){
              _Lon = newValue;
         }
        private String _EnDt = "";
        public void setEnDt(String newValue){
              _EnDt = newValue;
         }
        private String _Upload = "2";

        String TableName = "Household";

        public String SaveUpdateData(Context context)
        {
            String response = "";
            C = new Connection(context);
            String SQL = "";
            try
            {
                 if(C.Existence("Select * from "+ TableName +"  Where Vill='"+ _Vill +"' and Bari='"+ _Bari +"' and HH='"+ _HH +"' "))
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
        Connection C;

        private String SaveData(Context context)
        {
            String response = "";
            C = new Connection(context);
            String SQL = "";
            try
              {
                 SQL = "Insert into "+ TableName +" (Vill,Bari,HH,Religion,MobileNo1,MobileNo2,HHHead,TotMem,TotRWo,EnType,EnDate,ExType,ExDate,Rnd,StartTime,EndTime,DeviceID,EntryUser,Lat,Lon,EnDt,Upload)Values('"+ _Vill +"', '"+ _Bari +"', '"+ _HH +"', '"+ _Religion +"', '"+ _MobileNo1 +"', '"+ _MobileNo2 +"', '"+ _HHHead +"', '"+ _TotMem +"', '"+ _TotRWo +"', '"+ _EnType +"', '"+ _EnDate +"', '"+ _ExType +"', '"+ _ExDate +"', '"+ _Rnd +"', '"+ _StartTime +"', '"+ _EndTime +"', '"+ _DeviceID +"', '"+ _EntryUser +"', '"+ _Lat +"', '"+ _Lon +"', '"+ _EnDt +"', '"+ _Upload +"')";
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
                 SQL = "Update "+ TableName +" Set Upload='2',Vill = '"+ _Vill +"',Bari = '"+ _Bari +"',HH = '"+ _HH +"',Religion = '"+ _Religion +"',MobileNo1 = '"+ _MobileNo1 +"',MobileNo2 = '"+ _MobileNo2 +"',HHHead = '"+ _HHHead +"',TotMem = '"+ _TotMem +"',TotRWo = '"+ _TotRWo +"',EnType = '"+ _EnType +"',EnDate = '"+ _EnDate +"',ExType = '"+ _ExType +"',ExDate = '"+ _ExDate +"',Rnd = '"+ _Rnd +"'  Where Vill='"+ _Vill +"' and Bari='"+ _Bari +"' and HH='"+ _HH +"'";
                 C.Save(SQL);
                 C.close();
              }
              catch(Exception  e)
              {
                 response = e.getMessage();
              }
           return response;
        }


        public List<Household_DataModel> SelectAll(Context context, String SQL)
     {
         Connection C = new Connection(context);
         List<Household_DataModel> data = new ArrayList<Household_DataModel>();
         Household_DataModel d = new Household_DataModel();
         Cursor cur = C.ReadData(SQL);

         cur.moveToFirst();
         while(!cur.isAfterLast())
         {
             d = new Household_DataModel();
             d._Vill = cur.getString(cur.getColumnIndex("Vill"));
             d._Bari = cur.getString(cur.getColumnIndex("Bari"));
             d._HH = cur.getString(cur.getColumnIndex("HH"));
             d._Religion = cur.getString(cur.getColumnIndex("Religion"));
             d._MobileNo1 = cur.getString(cur.getColumnIndex("MobileNo1"));
             d._MobileNo2 = cur.getString(cur.getColumnIndex("MobileNo2"));
             d._HHHead = cur.getString(cur.getColumnIndex("HHHead"));
             d._TotMem = cur.getString(cur.getColumnIndex("TotMem"));
             d._TotRWo = cur.getString(cur.getColumnIndex("TotRWo"));
             d._EnType = cur.getString(cur.getColumnIndex("EnType"));
             d._EnDate = cur.getString(cur.getColumnIndex("EnDate"));
             d._ExType = cur.getString(cur.getColumnIndex("ExType"));
             d._ExDate = cur.getString(cur.getColumnIndex("ExDate"));
             d._Rnd = cur.getString(cur.getColumnIndex("Rnd"));
             d._Note = cur.getString(cur.getColumnIndex("Note"));
             data.add(d);

             cur.moveToNext();
         }
         cur.close();
         return data;
     }

     private String _VStatus = "";
     public String getVStatus(){
         return _VStatus;
     }
     private String _VStatusOth = "";
     public String getVStatusOth(){
         return _VStatusOth;
     }
     private String _Resp = "";
     public String getResp(){
         return _Resp;
     }

     public List<Household_DataModel> SelectAllVisit(Context context, String SQL)
     {
         Connection C = new Connection(context);
         List<Household_DataModel> data = new ArrayList<Household_DataModel>();
         Household_DataModel d = new Household_DataModel();
         Cursor cur = C.ReadData(SQL);

         cur.moveToFirst();
         while(!cur.isAfterLast())
         {
             d = new Household_DataModel();
             d._Vill = cur.getString(cur.getColumnIndex("Vill"));
             d._Bari = cur.getString(cur.getColumnIndex("Bari"));
             d._HH = cur.getString(cur.getColumnIndex("HH"));
             d._Religion = cur.getString(cur.getColumnIndex("Religion"));
             d._MobileNo1 = cur.getString(cur.getColumnIndex("MobileNo1"));
             d._MobileNo2 = cur.getString(cur.getColumnIndex("MobileNo2"));
             d._HHHead = cur.getString(cur.getColumnIndex("HHHead"));
             d._TotMem = cur.getString(cur.getColumnIndex("TotMem"));
             d._TotRWo = cur.getString(cur.getColumnIndex("TotRWo"));
             d._EnType = cur.getString(cur.getColumnIndex("EnType"));
             d._EnDate = cur.getString(cur.getColumnIndex("EnDate"));
             d._ExType = cur.getString(cur.getColumnIndex("ExType"));
             d._ExDate = cur.getString(cur.getColumnIndex("ExDate"));
             d._Rnd = cur.getString(cur.getColumnIndex("Rnd"));
             d._Note = cur.getString(cur.getColumnIndex("Note"));

             d._VStatus = cur.getString(cur.getColumnIndex("vstatus"));
             d._VStatusOth = cur.getString(cur.getColumnIndex("vstatusoth"));
             d._Resp = cur.getString(cur.getColumnIndex("resp"));


             data.add(d);

             cur.moveToNext();
         }
         cur.close();
         return data;
     }
 }