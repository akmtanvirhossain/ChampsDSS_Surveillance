package org.icddrb.champsdsssur;

import android.content.Context;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;
 public class PregScreening_DataModel{

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
        private String _PregnancyID = "";
        public String getPregnancyID(){
              return _PregnancyID;
         }
        public void setPregnancyID(String newValue){
              _PregnancyID = newValue;
         }
        private String _Rnd = "";
        public String getRnd(){
              return _Rnd;
         }
        public void setRnd(String newValue){
              _Rnd = newValue;
         }
        private String _PhoneNo = "";
        public String getPhoneNo(){
              return _PhoneNo;
         }
        public void setPhoneNo(String newValue){
              _PhoneNo = newValue;
         }
        private String _InfoSource = "";
        public String getInfoSource(){
              return _InfoSource;
         }
        public void setInfoSource(String newValue){
              _InfoSource = newValue;
         }
        private String _PregNotiDate = "";
        public String getPregNotiDate(){
              return _PregNotiDate;
         }
        public void setPregNotiDate(String newValue){
              _PregNotiDate = newValue;
         }
        private String _PregConCriteria = "";
        public String getPregConCriteria(){
              return _PregConCriteria;
         }
        public void setPregConCriteria(String newValue){
              _PregConCriteria = newValue;
         }
        private String _Eligible = "";
        public String getEligible(){
              return _Eligible;
         }
        public void setEligible(String newValue){
              _Eligible = newValue;
         }
        private String _EligibleDate = "";
        public String getEligibleDate(){
              return _EligibleDate;
         }
        public void setEligibleDate(String newValue){
              _EligibleDate = newValue;
         }
        private String _EnrollDate = "";
        public String getEnrollDate(){
              return _EnrollDate;
         }
        public void setEnrollDate(String newValue){
              _EnrollDate = newValue;
         }
        private String _PrevPregHis = "";
        public String getPrevPregHis(){
              return _PrevPregHis;
         }
        public void setPrevPregHis(String newValue){
              _PrevPregHis = newValue;
         }
        private String _StillBirth = "";
        public String getStillBirth(){
              return _StillBirth;
         }
        public void setStillBirth(String newValue){
              _StillBirth = newValue;
         }
        private String _StillBirthNo = "";
        public String getStillBirthNo(){
              return _StillBirthNo;
         }
        public void setStillBirthNo(String newValue){
              _StillBirthNo = newValue;
         }
        private String _MiscAbor = "";
        public String getMiscAbor(){
              return _MiscAbor;
         }
        public void setMiscAbor(String newValue){
              _MiscAbor = newValue;
         }
        private String _MiscAborNo = "";
        public String getMiscAborNo(){
              return _MiscAborNo;
         }
        public void setMiscAborNo(String newValue){
              _MiscAborNo = newValue;
         }
        private String _LastPregresult = "";
        public String getLastPregresult(){
              return _LastPregresult;
         }
        public void setLastPregresult(String newValue){
              _LastPregresult = newValue;
         }
        private String _DelDate = "";
        public String getDelDate(){
              return _DelDate;
         }
        public void setDelDate(String newValue){
              _DelDate = newValue;
         }
        private String _CesaDel = "";
        public String getCesaDel(){
              return _CesaDel;
         }
        public void setCesaDel(String newValue){
              _CesaDel = newValue;
         }
        private String _CesaDelNo = "";
        public String getCesaDelNo(){
              return _CesaDelNo;
         }
        public void setCesaDelNo(String newValue){
              _CesaDelNo = newValue;
         }
        private String _ObtEstiDelDate = "";
        public String getObtEstiDelDate(){
              return _ObtEstiDelDate;
         }
        public void setObtEstiDelDate(String newValue){
              _ObtEstiDelDate = newValue;
         }
        private String _UnreliLMP = "";
        public String getUnreliLMP(){
              return _UnreliLMP;
         }
        public void setUnreliLMP(String newValue){
              _UnreliLMP = newValue;
         }

        private String _LMPDate = "";
        public String getLMPDate(){
              return _LMPDate;
         }
        public void setLMPDate(String newValue){
              _LMPDate = newValue;
         }

        private String _UltraTrime = "";
        public String getUltraTrime(){
              return _UltraTrime;
         }
        public void setUltraTrime(String newValue){
              _UltraTrime = newValue;
         }
        private String _OthSpec = "";
        public String getOthSpec(){
              return _OthSpec;
         }
        public void setOthSpec(String newValue){
              _OthSpec = newValue;
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

        String TableName = "PregScreening";

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
        Connection C;

        private String SaveData(Context context)
        {
            String response = "";
            C = new Connection(context);
            String SQL = "";
            try
              {
                 SQL = "Insert into "+ TableName +" (Vill,Bari,HH,MSlNo,PNo,EvType,EvDate,PregnancyID,Rnd,PhoneNo,InfoSource,PregNotiDate,PregConCriteria,Eligible,EligibleDate,EnrollDate,PrevPregHis,StillBirth,StillBirthNo,MiscAbor,MiscAborNo,LastPregresult,DelDate,CesaDel,CesaDelNo,ObtEstiDelDate,UnreliLMP,LMPDate,UltraTrime,OthSpec,StartTime,EndTime,DeviceID,EntryUser,Lat,Lon,EnDt,Upload,modifyDate)Values('"+ _Vill +"', '"+ _Bari +"', '"+ _HH +"', '"+ _MSlNo +"', '"+ _PNo +"', '"+ _EvType +"', '"+ _EvDate +"', '"+ _PregnancyID +"', '"+ _Rnd +"', '"+ _PhoneNo +"', '"+ _InfoSource +"', '"+ _PregNotiDate +"', '"+ _PregConCriteria +"', '"+ _Eligible +"', '"+ _EligibleDate +"', '"+ _EnrollDate +"', '"+ _PrevPregHis +"', '"+ _StillBirth +"', '"+ _StillBirthNo +"', '"+ _MiscAbor +"', '"+ _MiscAborNo +"', '"+ _LastPregresult +"', '"+ _DelDate +"', '"+ _CesaDel +"', '"+ _CesaDelNo +"', '"+ _ObtEstiDelDate +"', '"+ _UnreliLMP +"', '"+ _LMPDate +"', '"+ _UltraTrime +"', '"+ _OthSpec +"', '"+ _StartTime +"', '"+ _EndTime +"', '"+ _DeviceID +"', '"+ _EntryUser +"', '"+ _Lat +"', '"+ _Lon +"', '"+ _EnDt +"', '"+ _Upload +"', '"+ _modifyDate +"')";
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
                 SQL = "Update "+ TableName +" Set Upload='2',modifyDate='" + _modifyDate + "' ,Vill = '"+ _Vill +"',Bari = '"+ _Bari +"',HH = '"+ _HH +"',MSlNo = '"+ _MSlNo +"',PNo = '"+ _PNo +"',EvType = '"+ _EvType +"',EvDate = '"+ _EvDate +"',PregnancyID = '"+ _PregnancyID +"',Rnd = '"+ _Rnd +"',PhoneNo = '"+ _PhoneNo +"',InfoSource = '"+ _InfoSource +"',PregNotiDate = '"+ _PregNotiDate +"',PregConCriteria = '"+ _PregConCriteria +"',Eligible = '"+ _Eligible +"',EligibleDate = '"+ _EligibleDate +"',EnrollDate = '"+ _EnrollDate +"',PrevPregHis = '"+ _PrevPregHis +"',StillBirth = '"+ _StillBirth +"',StillBirthNo = '"+ _StillBirthNo +"',MiscAbor = '"+ _MiscAbor +"',MiscAborNo = '"+ _MiscAborNo +"',LastPregresult = '"+ _LastPregresult +"',DelDate = '"+ _DelDate +"',CesaDel = '"+ _CesaDel +"',CesaDelNo = '"+ _CesaDelNo +"',ObtEstiDelDate = '"+ _ObtEstiDelDate +"',UnreliLMP = '"+ _UnreliLMP +"',LMPDate = '"+ _LMPDate +"',UltraTrime = '"+ _UltraTrime +"',OthSpec = '"+ _OthSpec +"'  Where Vill='"+ _Vill +"' and Bari='"+ _Bari +"' and HH='"+ _HH +"' and MSlNo='"+ _MSlNo +"' and EvType='"+ _EvType +"' and EvDate='"+ _EvDate +"' and Rnd='"+ _Rnd +"'";
                 C.Save(SQL);
                 C.close();
              }
              catch(Exception  e)
              {
                 response = e.getMessage();
              }
           return response;
        }


        public List<PregScreening_DataModel> SelectAll(Context context, String SQL)
        {
            Connection C = new Connection(context);
            List<PregScreening_DataModel> data = new ArrayList<PregScreening_DataModel>();
            PregScreening_DataModel d = new PregScreening_DataModel();
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {
                d = new PregScreening_DataModel();
                d._Vill = cur.getString(cur.getColumnIndex("Vill"));
                d._Bari = cur.getString(cur.getColumnIndex("Bari"));
                d._HH = cur.getString(cur.getColumnIndex("HH"));
                d._MSlNo = cur.getString(cur.getColumnIndex("MSlNo"));
                d._PNo = cur.getString(cur.getColumnIndex("PNo"));
                d._EvType = cur.getString(cur.getColumnIndex("EvType"));
                d._EvDate = cur.getString(cur.getColumnIndex("EvDate"));
                d._PregnancyID = cur.getString(cur.getColumnIndex("PregnancyID"));
                d._Rnd = cur.getString(cur.getColumnIndex("Rnd"));
                d._PhoneNo = cur.getString(cur.getColumnIndex("PhoneNo"));
                d._InfoSource = cur.getString(cur.getColumnIndex("InfoSource"));
                d._PregNotiDate = cur.getString(cur.getColumnIndex("PregNotiDate"));
                d._PregConCriteria = cur.getString(cur.getColumnIndex("PregConCriteria"));
                d._Eligible = cur.getString(cur.getColumnIndex("Eligible"));
                d._EligibleDate = cur.getString(cur.getColumnIndex("EligibleDate"));
                d._EnrollDate = cur.getString(cur.getColumnIndex("EnrollDate"));
                d._PrevPregHis = cur.getString(cur.getColumnIndex("PrevPregHis"));
                d._StillBirth = cur.getString(cur.getColumnIndex("StillBirth"));
                d._StillBirthNo = cur.getString(cur.getColumnIndex("StillBirthNo"));
                d._MiscAbor = cur.getString(cur.getColumnIndex("MiscAbor"));
                d._MiscAborNo = cur.getString(cur.getColumnIndex("MiscAborNo"));
                d._LastPregresult = cur.getString(cur.getColumnIndex("LastPregresult"));
                d._DelDate = cur.getString(cur.getColumnIndex("DelDate"));
                d._CesaDel = cur.getString(cur.getColumnIndex("CesaDel"));
                d._CesaDelNo = cur.getString(cur.getColumnIndex("CesaDelNo"));
                d._ObtEstiDelDate = cur.getString(cur.getColumnIndex("ObtEstiDelDate"));
                d._UnreliLMP = cur.getString(cur.getColumnIndex("UnreliLMP"));
                d._LMPDate = cur.getString(cur.getColumnIndex("LMPDate"));
                d._UltraTrime = cur.getString(cur.getColumnIndex("UltraTrime"));
                d._OthSpec = cur.getString(cur.getColumnIndex("OthSpec"));
                data.add(d);

                cur.moveToNext();
            }
            cur.close();
          return data;
        }
 }