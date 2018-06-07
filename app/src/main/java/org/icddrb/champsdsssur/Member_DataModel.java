package org.icddrb.champsdsssur;

import android.content.Context;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;
 public class Member_DataModel{

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
        private String _Name = "";
        public String getName(){
              return _Name;
         }
        public void setName(String newValue){
              _Name = newValue;
         }
        private String _Rth = "";
        public String getRth(){return _Rth;}
        public void setRth(String newValue){
              _Rth = newValue;
         }
        private String _Sex = "";
        public String getSex(){
              return _Sex;
         }
        public void setSex(String newValue){
              _Sex = newValue;
         }
        private String _BDate = "";
        public String getBDate(){
              return _BDate;
         }
        public void setBDate(String newValue){
              _BDate = newValue;
         }
        private String _AgeY = "";
        public String getAgeY(){return _AgeY;}
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
        private String _FaNo = "";
        public String getFaNo(){
              return _FaNo;
         }
        public void setFaNo(String newValue){
              _FaNo = newValue;
         }
        private String _Edu = "";
        public String getEdu(){
              return _Edu;
         }
        public void setEdu(String newValue){
              _Edu = newValue;
         }

        private String _Pstat = "";
        public String getPstat(){
         return _Pstat;
     }
        public void setPstat(String newValue){
         _Pstat = newValue;
     }
        private String _LmpDt = "";
        public String getLmpDt(){
         return _LmpDt;
     }
        public void setLmpDt(String newValue){
         _LmpDt = newValue;
     }

        private String _MS = "";
        public String getMS(){
              return _MS;
         }
        public void setMS(String newValue){
              _MS = newValue;
         }
        private String _Ocp = "";
        public String getOcp(){
              return _Ocp;
         }
        public void setOcp(String newValue){
              _Ocp = newValue;
         }
        private String _Sp1 = "";
        public String getSp1(){
              return _Sp1;
         }
        public void setSp1(String newValue){
              _Sp1 = newValue;
         }
        private String _Sp2 = "";
        public String getSp2(){
              return _Sp2;
         }
        public void setSp2(String newValue){
              _Sp2 = newValue;
         }
        private String _Sp3 = "";
        public String getSp3(){
              return _Sp3;
         }
        public void setSp3(String newValue){
              _Sp3 = newValue;
         }
        private String _Sp4 = "";
        public String getSp4(){
              return _Sp4;
         }
        public void setSp4(String newValue){
              _Sp4 = newValue;
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

        private String _PosMig = "";
        public String getPosMig(){
         return _PosMig;
     }
        public void setPosMig(String newValue){
         _PosMig = newValue;
     }
        private String _PosMigDate = "";
        public String getPosMigDate(){
         return _PosMigDate;
     }
        public void setPosMigDate(String newValue){
         _PosMigDate = newValue;
     }


        private String _NeedReview = "";
        public String getNeedReview(){
         return _NeedReview;
     }
        public void setNeedReview(String newValue){
         _NeedReview = newValue;
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

     String TableName = "tmpMember";


     public String TransactionSQL(Context context)
     {
         C = new Connection(context);
         String SQL = "";
         try
         {
             if(C.Existence("Select * from "+ TableName +"  Where Vill='"+ _Vill +"' and Bari='"+ _Bari +"' and HH='"+ _HH +"' and MSlNo='"+ _MSlNo +"' "))
                 SQL = "Update "+ TableName +" Set Upload='2',Vill = '"+ _Vill +"',Bari = '"+ _Bari +"',HH = '"+ _HH +"',MSlNo = '"+ _MSlNo +"',PNo = '"+ _PNo +"',Name = '"+ _Name +"',Rth = '"+ _Rth +"',Sex = '"+ _Sex +"',BDate = '"+ _BDate +"',AgeY = '"+ _AgeY +"',MoNo = '"+ _MoNo +"',FaNo = '"+ _FaNo +"',Edu = '"+ _Edu +"',MS = '"+ _MS + "',Pstat = '"+ _Pstat + "',LmpDt = '"+ _LmpDt +"',Ocp = '"+ _Ocp +"',Sp1 = '"+ _Sp1 +"',Sp2 = '"+ _Sp2 +"',Sp3 = '"+ _Sp3 +"',Sp4 = '"+ _Sp4 +"',EnType = '"+ _EnType +"',EnDate = '"+ _EnDate +"',ExType = '"+ _ExType +"',ExDate = '"+ _ExDate +"',PosMig = '"+ _PosMig +"',PosMigDate = '"+ _PosMigDate +"',NeedReview='"+ _NeedReview +"'  Where Vill='"+ _Vill +"' and Bari='"+ _Bari +"' and HH='"+ _HH +"' and MSlNo='"+ _MSlNo +"'";
             else
                 SQL = "Insert into "+ TableName +" (Vill,Bari,HH,MSlNo,PNo,Name,Rth,Sex,BDate,AgeY,MoNo,FaNo,Edu,MS,Pstat,LmpDt,Ocp,Sp1,Sp2,Sp3,Sp4,EnType,EnDate,ExType,ExDate,PosMig,PosMigDate,NeedReview,StartTime,EndTime,DeviceID,EntryUser,Lat,Lon,EnDt,Upload)Values('"+ _Vill +"', '"+ _Bari +"', '"+ _HH +"', '"+ _MSlNo +"', '"+ _PNo +"', '"+ _Name +"', '"+ _Rth +"', '"+ _Sex +"', '"+ _BDate +"', '"+ _AgeY +"', '"+ _MoNo +"', '"+ _FaNo +"', '"+ _Edu +"', '"+ _MS + "', '"+ _Pstat +  "', '"+ _LmpDt + "', '"+ _Ocp +"', '"+ _Sp1 +"', '"+ _Sp2 +"', '"+ _Sp3 +"', '"+ _Sp4 +"', '"+ _EnType +"', '"+ _EnDate +"', '"+ _ExType +"', '"+ _ExDate + "','"+ _PosMig +  "', '"+ _PosMigDate +"', '"+_NeedReview +"', '"+_StartTime +"', '"+ _EndTime +"', '"+ _DeviceID +"', '"+ _EntryUser +"', '"+ _Lat +"', '"+ _Lon +"', '"+ _EnDt +"', '"+ _Upload +"')";
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
            }
            finally {
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
                 SQL = "Insert into "+ TableName +" (Vill,Bari,HH,MSlNo,PNo,Name,Rth,Sex,BDate,AgeY,MoNo,FaNo,Edu,MS,Pstat,LmpDt,Ocp,Sp1,Sp2,Sp3,Sp4,EnType,EnDate,ExType,ExDate,PosMig,PosMigDate,NeedReview,StartTime,EndTime,DeviceID,EntryUser,Lat,Lon,EnDt,Upload)Values('"+ _Vill +"', '"+ _Bari +"', '"+ _HH +"', '"+ _MSlNo +"', '"+ _PNo +"', '"+ _Name +"', '"+ _Rth +"', '"+ _Sex +"', '"+ _BDate +"', '"+ _AgeY +"', '"+ _MoNo +"', '"+ _FaNo +"', '"+ _Edu +"', '"+ _MS + "', '"+ _Pstat +  "', '"+ _LmpDt +"', '"+ _Ocp +"', '"+ _Sp1 +"', '"+ _Sp2 +"', '"+ _Sp3 +"', '"+ _Sp4 +"', '"+ _EnType +"', '"+ _EnDate +"', '"+ _ExType +"', '"+ _ExDate + "','"+ _PosMig +  "', '"+ _PosMigDate + _NeedReview +"', '"+ _StartTime +"', '"+ _EndTime +"', '"+ _DeviceID +"', '"+ _EntryUser +"', '"+ _Lat +"', '"+ _Lon +"', '"+ _EnDt +"', '"+ _Upload +"')";
                 C.Save(SQL);
              }
              catch(Exception  e)
              {
                 response = e.getMessage();
              }
            finally {
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
                 SQL = "Update "+ TableName +" Set Upload='2',Vill = '"+ _Vill +"',Bari = '"+ _Bari +"',HH = '"+ _HH +"',MSlNo = '"+ _MSlNo +"',PNo = '"+ _PNo +"',Name = '"+ _Name +"',Rth = '"+ _Rth +"',Sex = '"+ _Sex +"',BDate = '"+ _BDate +"',AgeY = '"+ _AgeY +"',MoNo = '"+ _MoNo +"',FaNo = '"+ _FaNo +"',Edu = '"+ _Edu +"',MS = '"+ _MS + "',Pstat = '"+ _Pstat + "',LmpDt = '"+ _LmpDt +"',Ocp = '"+ _Ocp +"',Sp1 = '"+ _Sp1 +"',Sp2 = '"+ _Sp2 +"',Sp3 = '"+ _Sp3 +"',Sp4 = '"+ _Sp4 +"',EnType = '"+ _EnType +"',EnDate = '"+ _EnDate +"',ExType = '"+ _ExType +"',ExDate = '"+ _ExDate + "',PosMig = '"+ _PosMig +  "',PosMigDate = '"+ _PosMigDate +"',NeedReview='"+ _NeedReview +"'  Where Vill='"+ _Vill +"' and Bari='"+ _Bari +"' and HH='"+ _HH +"' and MSlNo='"+ _MSlNo +"'";
                 C.Save(SQL);
              }
              catch(Exception  e)
              {
                 response = e.getMessage();
              }
            finally {
                C.close();
            }
           return response;
        }


        public List<Member_DataModel> SelectAll(Context context, String SQL)
        {
            C = new Connection(context);
            List<Member_DataModel> data = new ArrayList<Member_DataModel>();
            Member_DataModel d = new Member_DataModel();
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {
                d = new Member_DataModel();
                d._Vill = cur.getString(cur.getColumnIndex("Vill"));
                d._Bari = cur.getString(cur.getColumnIndex("Bari"));
                d._HH = cur.getString(cur.getColumnIndex("HH"));
                d._MSlNo = cur.getString(cur.getColumnIndex("MSlNo"));
                d._PNo = cur.getString(cur.getColumnIndex("PNo"));
                d._Name = cur.getString(cur.getColumnIndex("Name"));
                d._Rth = cur.getString(cur.getColumnIndex("Rth"));
                d._Sex = cur.getString(cur.getColumnIndex("Sex"));
                d._BDate = cur.getString(cur.getColumnIndex("BDate"));
                d._AgeY = cur.getString(cur.getColumnIndex("AgeY"));
                d._MoNo = cur.getString(cur.getColumnIndex("MoNo"));
                d._FaNo = cur.getString(cur.getColumnIndex("FaNo"));
                d._Edu = cur.getString(cur.getColumnIndex("Edu"));
                d._MS = cur.getString(cur.getColumnIndex("MS"));
                d._Pstat = cur.getString(cur.getColumnIndex("Pstat"))==null?"":cur.getString(cur.getColumnIndex("Pstat"));
                d._LmpDt = cur.getString(cur.getColumnIndex("LmpDt"))==null?"":cur.getString(cur.getColumnIndex("LmpDt"));
                d._Ocp = cur.getString(cur.getColumnIndex("Ocp"));
                d._Sp1 = cur.getString(cur.getColumnIndex("Sp1"));
                d._Sp2 = cur.getString(cur.getColumnIndex("Sp2"));
                d._Sp3 = cur.getString(cur.getColumnIndex("Sp3"));
                d._Sp4 = cur.getString(cur.getColumnIndex("Sp4"));
                d._EnType = cur.getString(cur.getColumnIndex("EnType"));
                d._EnDate = cur.getString(cur.getColumnIndex("EnDate"));
                d._ExType = cur.getString(cur.getColumnIndex("ExType"));
                d._ExDate = cur.getString(cur.getColumnIndex("ExDate"));
                d._PosMig = cur.getString(cur.getColumnIndex("PosMig"))==null?"":cur.getString(cur.getColumnIndex("PosMig"));
                d._PosMigDate = cur.getString(cur.getColumnIndex("PosMigDate"))==null?"":cur.getString(cur.getColumnIndex("PosMigDate"));
                d._NeedReview = cur.getString(cur.getColumnIndex("NeedReview"))==null?"":cur.getString(cur.getColumnIndex("NeedReview"));

                d._EnDt = cur.getString(cur.getColumnIndex("EnDt"));
                d._StartTime = cur.getString(cur.getColumnIndex("StartTime"));
                d._EndTime = cur.getString(cur.getColumnIndex("EndTime"));
                d._DeviceID = cur.getString(cur.getColumnIndex("DeviceID"));
                d._EntryUser = cur.getString(cur.getColumnIndex("EntryUser"));
                d._Lat = cur.getString(cur.getColumnIndex("Lat"));
                d._Lon = cur.getString(cur.getColumnIndex("Lon"));


                data.add(d);

                cur.moveToNext();
            }
            cur.close();
            C.close();
          return data;
        }

     public List<Member_DataModel> SelectAll_MigMember(Context context, String SQL)
     {
         Connection C = new Connection(context);
         List<Member_DataModel> data = new ArrayList<Member_DataModel>();
         Member_DataModel d = new Member_DataModel();
         Cursor cur = C.ReadData(SQL);

         cur.moveToFirst();
         while(!cur.isAfterLast())
         {
             d = new Member_DataModel();
             d._Vill = cur.getString(cur.getColumnIndex("Vill"));
             d._Bari = cur.getString(cur.getColumnIndex("Bari"));
             d._HH = cur.getString(cur.getColumnIndex("HH"));
             d._MSlNo = cur.getString(cur.getColumnIndex("MSlNo"));
             d._PNo = cur.getString(cur.getColumnIndex("PNo"));
             d._Name = cur.getString(cur.getColumnIndex("Name"));
             d._Rth = cur.getString(cur.getColumnIndex("Rth"));
             d._Sex = cur.getString(cur.getColumnIndex("Sex"));
             d._BDate = cur.getString(cur.getColumnIndex("BDate"));
             d._AgeY = cur.getString(cur.getColumnIndex("AgeY"));
             d._MoNo = cur.getString(cur.getColumnIndex("MoNo"));
             d._FaNo = cur.getString(cur.getColumnIndex("FaNo"));
             d._Edu = cur.getString(cur.getColumnIndex("Edu"));
             d._MS = cur.getString(cur.getColumnIndex("MS"));
             d._Pstat = cur.getString(cur.getColumnIndex("Pstat"));
             d._LmpDt = cur.getString(cur.getColumnIndex("LmpDt"));
             d._Ocp = cur.getString(cur.getColumnIndex("Ocp"));
             d._Sp1 = cur.getString(cur.getColumnIndex("Sp1"));
             d._Sp2 = cur.getString(cur.getColumnIndex("Sp2"));
             d._Sp3 = cur.getString(cur.getColumnIndex("Sp3"));
             d._Sp4 = cur.getString(cur.getColumnIndex("Sp4"));
             d._EnType = cur.getString(cur.getColumnIndex("EnType"));
             d._EnDate = cur.getString(cur.getColumnIndex("EnDate"));
             d._ExType = cur.getString(cur.getColumnIndex("ExType"));
             d._ExDate = cur.getString(cur.getColumnIndex("ExDate"));
             //d._PosMig = cur.getString(cur.getColumnIndex("PosMig"));
             //d._PosMigDate = cur.getString(cur.getColumnIndex("PosMigDate"));
             data.add(d);

             cur.moveToNext();
         }
         cur.close();
         C.close();
         return data;
     }
 }