package org.icddrb.champsdsssur;

import android.content.Context;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;
 public class PregHis_DataModel{

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
        private String _MarriageStatus = "";
        public String getMarriageStatus(){
              return _MarriageStatus;
         }
        public void setMarriageStatus(String newValue){
              _MarriageStatus = newValue;
         }
        private String _MarMon = "";
        public String getMarMon(){
              return _MarMon;
         }
        public void setMarMon(String newValue){
              _MarMon = newValue;
         }
        private String _MarYear = "";
        public String getMarYear(){
              return _MarYear;
         }
        public void setMarYear(String newValue){
              _MarYear = newValue;
         }
        private String _MarDK = "";
        public String getMarDK(){
              return _MarDK;
         }
        public void setMarDK(String newValue){
              _MarDK = newValue;
         }
        private String _GaveBirth = "";
        public String getGaveBirth(){
              return _GaveBirth;
         }
        public void setGaveBirth(String newValue){
              _GaveBirth = newValue;
         }
        private String _ChildLivWWo = "";
        public String getChildLivWWo(){
              return _ChildLivWWo;
         }
        public void setChildLivWWo(String newValue){
              _ChildLivWWo = newValue;
         }
        private String _SonLivWWo = "";
        public String getSonLivWWo(){
              return _SonLivWWo;
         }
        public void setSonLivWWo(String newValue){
              _SonLivWWo = newValue;
         }
        private String _DaugLivWWo = "";
        public String getDaugLivWWo(){
              return _DaugLivWWo;
         }
        public void setDaugLivWWo(String newValue){
              _DaugLivWWo = newValue;
         }
        private String _ChldLivOut = "";
        public String getChldLivOut(){
              return _ChldLivOut;
         }
        public void setChldLivOut(String newValue){
              _ChldLivOut = newValue;
         }
        private String _SonLivOut = "";
        public String getSonLivOut(){
              return _SonLivOut;
         }
        public void setSonLivOut(String newValue){
              _SonLivOut = newValue;
         }
        private String _DaugLivOut = "";
        public String getDaugLivOut(){
              return _DaugLivOut;
         }
        public void setDaugLivOut(String newValue){
              _DaugLivOut = newValue;
         }
        private String _ChldDie = "";
        public String getChldDie(){
              return _ChldDie;
         }
        public void setChldDie(String newValue){
              _ChldDie = newValue;
         }
        private String _BoyDied = "";
        public String getBoyDied(){
              return _BoyDied;
         }
        public void setBoyDied(String newValue){
              _BoyDied = newValue;
         }
        private String _GirlDied = "";
        public String getGirlDied(){
              return _GirlDied;
         }
        public void setGirlDied(String newValue){
              _GirlDied = newValue;
         }
        private String _NotLivBrth = "";
        public String getNotLivBrth(){
              return _NotLivBrth;
         }
        public void setNotLivBrth(String newValue){
              _NotLivBrth = newValue;
         }
        private String _TotLB = "";
        public String getTotLB(){
              return _TotLB;
         }
        public void setTotLB(String newValue){
              _TotLB = newValue;
         }
        private String _TotPregOut = "";
        public String getTotPregOut(){
              return _TotPregOut;
         }
        public void setTotPregOut(String newValue){
              _TotPregOut = newValue;
         }
        private String _CurPreg = "";
        public String getCurPreg(){
              return _CurPreg;
         }
        public void setCurPreg(String newValue){
              _CurPreg = newValue;
         }
        private String _LMPDate = "";
        public String getLMPDate(){
              return _LMPDate;
         }
        public void setLMPDate(String newValue){
              _LMPDate = newValue;
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

        String TableName = "tmpPregHis";

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
                 SQL = "Insert into "+ TableName +" (Vill,Bari,HH,MSlNo,PNo,VDate,VStatus,VStatusOth,MarriageStatus,MarMon,MarYear,MarDK,GaveBirth,ChildLivWWo,SonLivWWo,DaugLivWWo,ChldLivOut,SonLivOut,DaugLivOut,ChldDie,BoyDied,GirlDied,NotLivBrth,TotLB,TotPregOut,CurPreg,LMPDate,StartTime,EndTime,DeviceID,EntryUser,Lat,Lon,EnDt,Upload)Values('"+ _Vill +"', '"+ _Bari +"', '"+ _HH +"', '"+ _MSlNo +"', '"+ _PNo +"', '"+ _VDate +"', '"+ _VStatus +"', '"+ _VStatusOth +"', '"+ _MarriageStatus +"', '"+ _MarMon +"', '"+ _MarYear +"', '"+ _MarDK +"', '"+ _GaveBirth +"', '"+ _ChildLivWWo +"', '"+ _SonLivWWo +"', '"+ _DaugLivWWo +"', '"+ _ChldLivOut +"', '"+ _SonLivOut +"', '"+ _DaugLivOut +"', '"+ _ChldDie +"', '"+ _BoyDied +"', '"+ _GirlDied +"', '"+ _NotLivBrth +"', '"+ _TotLB +"', '"+ _TotPregOut +"', '"+ _CurPreg +"', '"+ _LMPDate +"', '"+ _StartTime +"', '"+ _EndTime +"', '"+ _DeviceID +"', '"+ _EntryUser +"', '"+ _Lat +"', '"+ _Lon +"', '"+ _EnDt +"', '"+ _Upload +"')";
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
                 SQL = "Update "+ TableName +" Set Upload='2',Vill = '"+ _Vill +"',Bari = '"+ _Bari +"',HH = '"+ _HH +"',MSlNo = '"+ _MSlNo +"',PNo = '"+ _PNo +"',VDate = '"+ _VDate +"',VStatus = '"+ _VStatus +"',VStatusOth = '"+ _VStatusOth +"',MarriageStatus = '"+ _MarriageStatus +"',MarMon = '"+ _MarMon +"',MarYear = '"+ _MarYear +"',MarDK = '"+ _MarDK +"',GaveBirth = '"+ _GaveBirth +"',ChildLivWWo = '"+ _ChildLivWWo +"',SonLivWWo = '"+ _SonLivWWo +"',DaugLivWWo = '"+ _DaugLivWWo +"',ChldLivOut = '"+ _ChldLivOut +"',SonLivOut = '"+ _SonLivOut +"',DaugLivOut = '"+ _DaugLivOut +"',ChldDie = '"+ _ChldDie +"',BoyDied = '"+ _BoyDied +"',GirlDied = '"+ _GirlDied +"',NotLivBrth = '"+ _NotLivBrth +"',TotLB = '"+ _TotLB +"',TotPregOut = '"+ _TotPregOut +"',CurPreg = '"+ _CurPreg +"',LMPDate = '"+ _LMPDate +"'  Where Vill='"+ _Vill +"' and Bari='"+ _Bari +"' and HH='"+ _HH +"' and MSlNo='"+ _MSlNo +"'";
                 C.Save(SQL);
                 C.close();
              }
              catch(Exception  e)
              {
                 response = e.getMessage();
              }
           return response;
        }


        public List<PregHis_DataModel> SelectAll(Context context, String SQL)
        {
            Connection C = new Connection(context);
            List<PregHis_DataModel> data = new ArrayList<PregHis_DataModel>();
            PregHis_DataModel d = new PregHis_DataModel();
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {
                d = new PregHis_DataModel();
                d._Vill = cur.getString(cur.getColumnIndex("Vill"));
                d._Bari = cur.getString(cur.getColumnIndex("Bari"));
                d._HH = cur.getString(cur.getColumnIndex("HH"));
                d._MSlNo = cur.getString(cur.getColumnIndex("MSlNo"));
                d._PNo = cur.getString(cur.getColumnIndex("PNo"));
                d._VDate = cur.getString(cur.getColumnIndex("VDate"));
                d._VStatus = cur.getString(cur.getColumnIndex("VStatus"));
                d._VStatusOth = cur.getString(cur.getColumnIndex("VStatusOth"));
                d._MarriageStatus = cur.getString(cur.getColumnIndex("MarriageStatus"));
                d._MarMon = cur.getString(cur.getColumnIndex("MarMon"));
                d._MarYear = cur.getString(cur.getColumnIndex("MarYear"));
                d._MarDK = cur.getString(cur.getColumnIndex("MarDK"));
                d._GaveBirth = cur.getString(cur.getColumnIndex("GaveBirth"));
                d._ChildLivWWo = cur.getString(cur.getColumnIndex("ChildLivWWo"));
                d._SonLivWWo = cur.getString(cur.getColumnIndex("SonLivWWo"));
                d._DaugLivWWo = cur.getString(cur.getColumnIndex("DaugLivWWo"));
                d._ChldLivOut = cur.getString(cur.getColumnIndex("ChldLivOut"));
                d._SonLivOut = cur.getString(cur.getColumnIndex("SonLivOut"));
                d._DaugLivOut = cur.getString(cur.getColumnIndex("DaugLivOut"));
                d._ChldDie = cur.getString(cur.getColumnIndex("ChldDie"));
                d._BoyDied = cur.getString(cur.getColumnIndex("BoyDied"));
                d._GirlDied = cur.getString(cur.getColumnIndex("GirlDied"));
                d._NotLivBrth = cur.getString(cur.getColumnIndex("NotLivBrth"));
                d._TotLB = cur.getString(cur.getColumnIndex("TotLB"));
                d._TotPregOut = cur.getString(cur.getColumnIndex("TotPregOut"));
                d._CurPreg = cur.getString(cur.getColumnIndex("CurPreg"));
                d._LMPDate = cur.getString(cur.getColumnIndex("LMPDate"));

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
          return data;
        }
 }