package org.icddrb.champsdsssur;

import android.content.Context;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;
 public class Visits_DataModel{

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
        private String _VDate = "";
        public String getVDate(){
              return _VDate;
         }
        public void setVDate(String newValue){
              _VDate = newValue;
         }
        private String _VStatus = "";
        public String getVStatus(){
              return _VStatus;
         }
        public void setVStatus(String newValue){
              _VStatus = newValue;
         }

        private String _VStatusOth = "";
        public String getVStatusOth(){
              return _VStatusOth;
         }
        public void setVStatusOth(String newValue){
              _VStatusOth = newValue;
         }

         private String _Note = "";
         public String getNote(){
             return _Note;
         }
         public void setNote(String newValue){
             _Note = newValue;
         }

         private String _Resp = "";
        public String getResp(){
              return _Resp;
         }
        public void setResp(String newValue){
              _Resp = newValue;
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

        String TableName = "Visits";

        public String SaveUpdateData(Context context)
        {
            String response = "";
            C = new Connection(context);
            String SQL = "";
            try
            {
                 if(C.Existence("Select * from "+ TableName +"  Where Vill='"+ _Vill +"' and Bari='"+ _Bari +"' and HH='"+ _HH +"' and Rnd='"+ _Rnd +"' "))
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
                 SQL = "Insert into "+ TableName +" (Vill,Bari,HH,VDate,VStatus,VStatusOth,Note,Resp,Rnd,StartTime,EndTime,DeviceID,EntryUser,Lat,Lon,EnDt,Upload)Values('"+ _Vill +"', '"+ _Bari +"', '"+ _HH +"', '"+ _VDate +"', '"+ _VStatus +"', '"+ _VStatusOth +"', '"+ _Note +"','"+ _Resp +"', '"+ _Rnd +"', '"+ _StartTime +"', '"+ _EndTime +"', '"+ _DeviceID +"', '"+ _EntryUser +"', '"+ _Lat +"', '"+ _Lon +"', '"+ _EnDt +"', '"+ _Upload +"')";
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
                 SQL = "Update "+ TableName +" Set Upload='2',Vill = '"+ _Vill +"',Bari = '"+ _Bari +"',HH = '"+ _HH +"',VDate = '"+ _VDate +"',VStatus = '"+ _VStatus +"',VStatusOth = '"+ _VStatusOth +"',Note = '"+ _Note +"',Resp = '"+ _Resp +"',Rnd = '"+ _Rnd +"'  Where Vill='"+ _Vill +"' and Bari='"+ _Bari +"' and HH='"+ _HH +"' and Rnd='"+ _Rnd +"'";
                 C.Save(SQL);
                 C.close();
              }
              catch(Exception  e)
              {
                 response = e.getMessage();
              }
           return response;
        }


        public List<Visits_DataModel> SelectAll(Context context, String SQL)
        {
            Connection C = new Connection(context);
            List<Visits_DataModel> data = new ArrayList<Visits_DataModel>();
            Visits_DataModel d = new Visits_DataModel();
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {
                d = new Visits_DataModel();
                d._Vill = cur.getString(cur.getColumnIndex("Vill"));
                d._Bari = cur.getString(cur.getColumnIndex("Bari"));
                d._HH = cur.getString(cur.getColumnIndex("HH"));
                d._VDate = cur.getString(cur.getColumnIndex("VDate"));
                d._VStatus = cur.getString(cur.getColumnIndex("VStatus"));
                d._VStatusOth = cur.getString(cur.getColumnIndex("VStatusOth"));
                d._Note = cur.getString(cur.getColumnIndex("Note"));
                d._Resp = cur.getString(cur.getColumnIndex("Resp"));
                d._Rnd = cur.getString(cur.getColumnIndex("Rnd"));
                data.add(d);

                cur.moveToNext();
            }
            cur.close();
          return data;
        }
 }