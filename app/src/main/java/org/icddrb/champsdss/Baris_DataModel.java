package org.icddrb.champsdss;

import android.content.Context;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;
 public class Baris_DataModel{

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
        private String _Cluster = "";
        public String getCluster(){
              return _Cluster;
         }
        public void setCluster(String newValue){
              _Cluster = newValue;
         }
        private String _Block = "";
        public String getBlock(){
              return _Block;
         }
        public void setBlock(String newValue){
              _Block = newValue;
         }
        private String _BariName = "";
        public String getBariName(){
              return _BariName;
         }
        public void setBariName(String newValue){
              _BariName = newValue;
         }
        private String _BariLoc = "";
        public String getBariLoc(){
              return _BariLoc;
         }
        public void setBariLoc(String newValue){
              _BariLoc = newValue;
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

        String TableName = "Baris";

        public String SaveUpdateData(Context context)
        {
            String response = "";
            C = new Connection(context);
            String SQL = "";
            try
            {
                 if(C.Existence("Select * from "+ TableName +"  Where Vill='"+ _Vill +"' and Bari='"+ _Bari +"' "))
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
                 SQL = "Insert into "+ TableName +" (Vill,Bari,Cluster,Block,BariName,BariLoc,StartTime,EndTime,DeviceID,EntryUser,Lat,Lon,EnDt,Upload)Values('"+ _Vill +"', '"+ _Bari +"', '"+ _Cluster +"', '"+ _Block +"', '"+ _BariName +"', '"+ _BariLoc +"', '"+ _StartTime +"', '"+ _EndTime +"', '"+ _DeviceID +"', '"+ _EntryUser +"', '"+ _Lat +"', '"+ _Lon +"', '"+ _EnDt +"', '"+ _Upload +"')";
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
                 SQL = "Update "+ TableName +" Set Upload='2',Vill = '"+ _Vill +"',Bari = '"+ _Bari +"',Cluster = '"+ _Cluster +"',Block = '"+ _Block +"',BariName = '"+ _BariName +"',BariLoc = '"+ _BariLoc +"'  Where Vill='"+ _Vill +"' and Bari='"+ _Bari +"'";
                 C.Save(SQL);
                 C.close();
              }
              catch(Exception  e)
              {
                 response = e.getMessage();
              }
           return response;
        }


        public List<Baris_DataModel> SelectAll(Context context, String SQL)
        {
            Connection C = new Connection(context);
            List<Baris_DataModel> data = new ArrayList<Baris_DataModel>();
            Baris_DataModel d = new Baris_DataModel();
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {
                d = new Baris_DataModel();
                d._Vill = cur.getString(cur.getColumnIndex("Vill"));
                d._Bari = cur.getString(cur.getColumnIndex("Bari"));
                d._Cluster = cur.getString(cur.getColumnIndex("Cluster"));
                d._Block = cur.getString(cur.getColumnIndex("Block"));
                d._BariName = cur.getString(cur.getColumnIndex("BariName"));
                d._BariLoc = cur.getString(cur.getColumnIndex("BariLoc"));
                data.add(d);

                cur.moveToNext();
            }
            cur.close();
          return data;
        }
 }