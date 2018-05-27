package org.icddrb.champsdsssur;

import android.content.Context;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;
 public class Member_SB_DataModel{

        private String _UnCode = "";
        public String getUnCode(){return _UnCode;}
        public void setUnCode(String newValue){
              _UnCode = newValue;
         }
        private String _UnionName = "";
        public String getUnionName(){
              return _UnionName;
         }
        public void setUnionName(String newValue){
              _UnionName = newValue;
         }
        private String _Vill = "";
        public String getVill(){
              return _Vill;
         }
        public void setVill(String newValue){
              _Vill = newValue;
         }
        private String _VillageName = "";
        public String getVillageName(){
              return _VillageName;
         }
        public void setVillageName(String newValue){
              _VillageName = newValue;
         }
        private String _Bari = "";
        public String getBari(){
              return _Bari;
         }
        public void setBari(String newValue){
              _Bari = newValue;
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
        private String _Name = "";
        public String getName(){
              return _Name;
         }
        public void setName(String newValue){
              _Name = newValue;
         }
        private String _OutResut = "";
        public String getOutResut(){
              return _OutResut;
         }
        public void setOutResut(String newValue){
              _OutResut = newValue;
         }
        private String _BDate = "";
        public String getBDate(){
              return _BDate;
         }
        public void setBDate(String newValue){
              _BDate = newValue;
         }
        private String _AgeY = "";
        public String getAgeY(){
              return _AgeY;
         }
        public void setAgeY(String newValue){
              _AgeY = newValue;
         }
        private String _MoNo = "";
        public String getMoNo(){
              return _MoNo;
         }
        public void setMoNo(String newValue){
              _MoNo = newValue;
         }
        private String _MotPNo = "";
        public String getMotPNo(){
              return _MotPNo;
         }
        public void setMotPNo(String newValue){
              _MotPNo = newValue;
         }
        private String _MotName = "";
        public String getMotName(){
              return _MotName;
         }
        public void setMotName(String newValue){
              _MotName = newValue;
         }
        private String _FaNo = "";
        public String getFaNo(){
              return _FaNo;
         }
        public void setFaNo(String newValue){
              _FaNo = newValue;
         }
        private String _FatName = "";
        public String getFatName(){
              return _FatName;
         }
        public void setFatName(String newValue){
              _FatName = newValue;
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
        private String _modifyDate = "";
        public void setmodifyDate(String newValue){
        _modifyDate = newValue;
        }

        String TableName = "Member_SB";

        public String SaveUpdateData(Context context)
        {
            String response = "";
            C = new Connection(context);
            String SQL = "";
            try
            {
                 if(C.Existence("Select * from "+ TableName +"  Where PNo='"+ _PNo +"' "))
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
                 SQL = "Insert into "+ TableName +" (UnCode,UnionName,Vill,VillageName,Bari,BariName,BariLoc,HH,MSlNo,PNo,Name,OutResut,BDate,AgeY,MoNo,MotPNo,MotName,FaNo,FatName,EnType,EnDate,ExType,ExDate,StartTime,EndTime,DeviceID,EntryUser,Lat,Lon,EnDt,Upload,modifyDate)Values('"+ _UnCode +"', '"+ _UnionName +"', '"+ _Vill +"', '"+ _VillageName +"', '"+ _Bari +"', '"+ _BariName +"', '"+ _BariLoc +"', '"+ _HH +"', '"+ _MSlNo +"', '"+ _PNo +"', '"+ _Name +"', '"+ _OutResut +"', '"+ _BDate +"', '"+ _AgeY +"', '"+ _MoNo +"', '"+ _MotPNo +"', '"+ _MotName +"', '"+ _FaNo +"', '"+ _FatName +"', '"+ _EnType +"', '"+ _EnDate +"', '"+ _ExType +"', '"+ _ExDate +"', '"+ _StartTime +"', '"+ _EndTime +"', '"+ _DeviceID +"', '"+ _EntryUser +"', '"+ _Lat +"', '"+ _Lon +"', '"+ _EnDt +"', '"+ _Upload +"', '"+ _modifyDate +"')";
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
                 SQL = "Update "+ TableName +" Set Upload='2',modifyDate='" + _modifyDate + "' ,UnCode = '"+ _UnCode +"',UnionName = '"+ _UnionName +"',Vill = '"+ _Vill +"',VillageName = '"+ _VillageName +"',Bari = '"+ _Bari +"',BariName = '"+ _BariName +"',BariLoc = '"+ _BariLoc +"',HH = '"+ _HH +"',MSlNo = '"+ _MSlNo +"',PNo = '"+ _PNo +"',Name = '"+ _Name +"',OutResut = '"+ _OutResut +"',BDate = '"+ _BDate +"',AgeY = '"+ _AgeY +"',MoNo = '"+ _MoNo +"',MotPNo = '"+ _MotPNo +"',MotName = '"+ _MotName +"',FaNo = '"+ _FaNo +"',FatName = '"+ _FatName +"',EnType = '"+ _EnType +"',EnDate = '"+ _EnDate +"',ExType = '"+ _ExType +"',ExDate = '"+ _ExDate +"'  Where PNo='"+ _PNo +"'";
                 C.Save(SQL);
                 C.close();
              }
              catch(Exception  e)
              {
                 response = e.getMessage();
              }
           return response;
        }


        public List<Member_SB_DataModel> SelectAll(Context context, String SQL)
        {
            Connection C = new Connection(context);
            List<Member_SB_DataModel> data = new ArrayList<Member_SB_DataModel>();
            Member_SB_DataModel d = new Member_SB_DataModel();
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {
                d = new Member_SB_DataModel();
                d._UnCode = cur.getString(cur.getColumnIndex("UnCode"));
                d._UnionName = cur.getString(cur.getColumnIndex("UnionName"));
                d._Vill = cur.getString(cur.getColumnIndex("Vill"));
                d._VillageName = cur.getString(cur.getColumnIndex("VillageName"));
                d._Bari = cur.getString(cur.getColumnIndex("Bari"));
                d._BariName = cur.getString(cur.getColumnIndex("BariName"));
                d._BariLoc = cur.getString(cur.getColumnIndex("BariLoc"));
                d._HH = cur.getString(cur.getColumnIndex("HH"));
                d._MSlNo = cur.getString(cur.getColumnIndex("MSlNo"));
                d._PNo = cur.getString(cur.getColumnIndex("PNo"));
                d._Name = cur.getString(cur.getColumnIndex("Name"));
                d._OutResut = cur.getString(cur.getColumnIndex("OutResut"));
                d._BDate = cur.getString(cur.getColumnIndex("BDate"));
                d._AgeY = cur.getString(cur.getColumnIndex("AgeY"));
                d._MoNo = cur.getString(cur.getColumnIndex("MoNo"));
                d._MotPNo = cur.getString(cur.getColumnIndex("MotPNo"));
                d._MotName = cur.getString(cur.getColumnIndex("MotName"));
                d._FaNo = cur.getString(cur.getColumnIndex("FaNo"));
                d._FatName = cur.getString(cur.getColumnIndex("FatName"));
                d._EnType = cur.getString(cur.getColumnIndex("EnType"));
                d._EnDate = cur.getString(cur.getColumnIndex("EnDate"));
                d._ExType = cur.getString(cur.getColumnIndex("ExType"));
                d._ExDate = cur.getString(cur.getColumnIndex("ExDate"));
                data.add(d);

                cur.moveToNext();
            }
            cur.close();
          return data;
        }
 }