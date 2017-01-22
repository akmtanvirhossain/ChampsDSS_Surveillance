package org.icddrb.champsdss;

import android.content.Context;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;
 public class SES_DataModel{

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
        private String _SESNo = "";
        public String getSESNo(){
              return _SESNo;
         }
        public void setSESNo(String newValue){
              _SESNo = newValue;
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
        private String _Rnd = "";
        public String getRnd(){
              return _Rnd;
         }
        public void setRnd(String newValue){
              _Rnd = newValue;
         }
        private String _WSDrink = "";
        public String getWSDrink(){
              return _WSDrink;
         }
        public void setWSDrink(String newValue){
              _WSDrink = newValue;
         }
        private String _WSDrinkOth = "";
        public String getWSDrinkOth(){
              return _WSDrinkOth;
         }
        public void setWSDrinkOth(String newValue){
              _WSDrinkOth = newValue;
         }
        private String _WSCook = "";
        public String getWSCook(){
              return _WSCook;
         }
        public void setWSCook(String newValue){
              _WSCook = newValue;
         }
        private String _WSCookOth = "";
        public String getWSCookOth(){
              return _WSCookOth;
         }
        public void setWSCookOth(String newValue){
              _WSCookOth = newValue;
         }
        private String _WSWash = "";
        public String getWSWash(){
              return _WSWash;
         }
        public void setWSWash(String newValue){
              _WSWash = newValue;
         }
        private String _WSWashOth = "";
        public String getWSWashOth(){
              return _WSWashOth;
         }
        public void setWSWashOth(String newValue){
              _WSWashOth = newValue;
         }
        private String _Latrine = "";
        public String getLatrine(){
              return _Latrine;
         }
        public void setLatrine(String newValue){
              _Latrine = newValue;
         }
        private String _LatrineOth = "";
        public String getLatrineOth(){
              return _LatrineOth;
         }
        public void setLatrineOth(String newValue){
              _LatrineOth = newValue;
         }
        private String _Electricity = "";
        public String getElectricity(){
              return _Electricity;
         }
        public void setElectricity(String newValue){
              _Electricity = newValue;
         }
        private String _Radio = "";
        public String getRadio(){
              return _Radio;
         }
        public void setRadio(String newValue){
              _Radio = newValue;
         }
        private String _TV = "";
        public String getTV(){
              return _TV;
         }
        public void setTV(String newValue){
              _TV = newValue;
         }
        private String _Mobile = "";
        public String getMobile(){
              return _Mobile;
         }
        public void setMobile(String newValue){
              _Mobile = newValue;
         }
        private String _Telephone = "";
        public String getTelephone(){
              return _Telephone;
         }
        public void setTelephone(String newValue){
              _Telephone = newValue;
         }
        private String _Refrige = "";
        public String getRefrige(){
              return _Refrige;
         }
        public void setRefrige(String newValue){
              _Refrige = newValue;
         }
        private String _Watch = "";
        public String getWatch(){
              return _Watch;
         }
        public void setWatch(String newValue){
              _Watch = newValue;
         }
        private String _ElecFan = "";
        public String getElecFan(){
              return _ElecFan;
         }
        public void setElecFan(String newValue){
              _ElecFan = newValue;
         }
        private String _RickVan = "";
        public String getRickVan(){
              return _RickVan;
         }
        public void setRickVan(String newValue){
              _RickVan = newValue;
         }
        private String _Bicycle = "";
        public String getBicycle(){
              return _Bicycle;
         }
        public void setBicycle(String newValue){
              _Bicycle = newValue;
         }
        private String _MotCycle = "";
        public String getMotCycle(){
              return _MotCycle;
         }
        public void setMotCycle(String newValue){
              _MotCycle = newValue;
         }
        private String _Computer = "";
        public String getComputer(){
              return _Computer;
         }
        public void setComputer(String newValue){
              _Computer = newValue;
         }
        private String _Buffalo = "";
        public String getBuffalo(){
              return _Buffalo;
         }
        public void setBuffalo(String newValue){
              _Buffalo = newValue;
         }
        private String _Bull = "";
        public String getBull(){
              return _Bull;
         }
        public void setBull(String newValue){
              _Bull = newValue;
         }
        private String _Goat = "";
        public String getGoat(){
              return _Goat;
         }
        public void setGoat(String newValue){
              _Goat = newValue;
         }
        private String _Chicken = "";
        public String getChicken(){
              return _Chicken;
         }
        public void setChicken(String newValue){
              _Chicken = newValue;
         }
        private String _Pigeon = "";
        public String getPigeon(){
              return _Pigeon;
         }
        public void setPigeon(String newValue){
              _Pigeon = newValue;
         }
        private String _Roof = "";
        public String getRoof(){
              return _Roof;
         }
        public void setRoof(String newValue){
              _Roof = newValue;
         }
        private String _RoofOth = "";
        public String getRoofOth(){
              return _RoofOth;
         }
        public void setRoofOth(String newValue){
              _RoofOth = newValue;
         }
        private String _Wall = "";
        public String getWall(){
              return _Wall;
         }
        public void setWall(String newValue){
              _Wall = newValue;
         }
        private String _WallOth = "";
        public String getWallOth(){
              return _WallOth;
         }
        public void setWallOth(String newValue){
              _WallOth = newValue;
         }
        private String _Floor = "";
        public String getFloor(){
              return _Floor;
         }
        public void setFloor(String newValue){
              _Floor = newValue;
         }
        private String _FloorOth = "";
        public String getFloorOth(){
              return _FloorOth;
         }
        public void setFloorOth(String newValue){
              _FloorOth = newValue;
         }
        private String _Homestead = "";
        public String getHomestead(){
              return _Homestead;
         }
        public void setHomestead(String newValue){
              _Homestead = newValue;
         }
        private String _HomesteadOth = "";
        public String getHomesteadOth(){
              return _HomesteadOth;
         }
        public void setHomesteadOth(String newValue){
              _HomesteadOth = newValue;
         }
        private String _OthLand = "";
        public String getOthLand(){
              return _OthLand;
         }
        public void setOthLand(String newValue){
              _OthLand = newValue;
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

        String TableName = "SES";

        public String SaveUpdateData(Context context)
        {
            String response = "";
            C = new Connection(context);
            String SQL = "";
            try
            {
                 if(C.Existence("Select * from "+ TableName +"  Where Vill='"+ _Vill +"' and Bari='"+ _Bari +"' and HH='"+ _HH +"' and SESNo='"+ _SESNo +"' "))
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
                 SQL = "Insert into "+ TableName +" (Vill,Bari,HH,SESNo,VDate,VStatus,VStatusOth,Rnd,WSDrink,WSDrinkOth,WSCook,WSCookOth,WSWash,WSWashOth,Latrine,LatrineOth,Electricity,Radio,TV,Mobile,Telephone,Refrige,Watch,ElecFan,RickVan,Bicycle,MotCycle,Computer,Buffalo,Bull,Goat,Chicken,Pigeon,Roof,RoofOth,Wall,WallOth,Floor,FloorOth,Homestead,HomesteadOth,OthLand,StartTime,EndTime,DeviceID,EntryUser,Lat,Lon,EnDt,Upload)Values('"+ _Vill +"', '"+ _Bari +"', '"+ _HH +"', '"+ _SESNo +"', '"+ _VDate +"', '"+ _VStatus +"', '"+ _VStatusOth +"', '"+ _Rnd +"', '"+ _WSDrink +"', '"+ _WSDrinkOth +"', '"+ _WSCook +"', '"+ _WSCookOth +"', '"+ _WSWash +"', '"+ _WSWashOth +"', '"+ _Latrine +"', '"+ _LatrineOth +"', '"+ _Electricity +"', '"+ _Radio +"', '"+ _TV +"', '"+ _Mobile +"', '"+ _Telephone +"', '"+ _Refrige +"', '"+ _Watch +"', '"+ _ElecFan +"', '"+ _RickVan +"', '"+ _Bicycle +"', '"+ _MotCycle +"', '"+ _Computer +"', '"+ _Buffalo +"', '"+ _Bull +"', '"+ _Goat +"', '"+ _Chicken +"', '"+ _Pigeon +"', '"+ _Roof +"', '"+ _RoofOth +"', '"+ _Wall +"', '"+ _WallOth +"', '"+ _Floor +"', '"+ _FloorOth +"', '"+ _Homestead +"', '"+ _HomesteadOth +"', '"+ _OthLand +"', '"+ _StartTime +"', '"+ _EndTime +"', '"+ _DeviceID +"', '"+ _EntryUser +"', '"+ _Lat +"', '"+ _Lon +"', '"+ _EnDt +"', '"+ _Upload +"')";
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
                 SQL = "Update "+ TableName +" Set Upload='2',Vill = '"+ _Vill +"',Bari = '"+ _Bari +"',HH = '"+ _HH +"',SESNo = '"+ _SESNo +"',VDate = '"+ _VDate +"',VStatus = '"+ _VStatus +"',VStatusOth = '"+ _VStatusOth +"',Rnd = '"+ _Rnd +"',WSDrink = '"+ _WSDrink +"',WSDrinkOth = '"+ _WSDrinkOth +"',WSCook = '"+ _WSCook +"',WSCookOth = '"+ _WSCookOth +"',WSWash = '"+ _WSWash +"',WSWashOth = '"+ _WSWashOth +"',Latrine = '"+ _Latrine +"',LatrineOth = '"+ _LatrineOth +"',Electricity = '"+ _Electricity +"',Radio = '"+ _Radio +"',TV = '"+ _TV +"',Mobile = '"+ _Mobile +"',Telephone = '"+ _Telephone +"',Refrige = '"+ _Refrige +"',Watch = '"+ _Watch +"',ElecFan = '"+ _ElecFan +"',RickVan = '"+ _RickVan +"',Bicycle = '"+ _Bicycle +"',MotCycle = '"+ _MotCycle +"',Computer = '"+ _Computer +"',Buffalo = '"+ _Buffalo +"',Bull = '"+ _Bull +"',Goat = '"+ _Goat +"',Chicken = '"+ _Chicken +"',Pigeon = '"+ _Pigeon +"',Roof = '"+ _Roof +"',RoofOth = '"+ _RoofOth +"',Wall = '"+ _Wall +"',WallOth = '"+ _WallOth +"',Floor = '"+ _Floor +"',FloorOth = '"+ _FloorOth +"',Homestead = '"+ _Homestead +"',HomesteadOth = '"+ _HomesteadOth +"',OthLand = '"+ _OthLand +"'  Where Vill='"+ _Vill +"' and Bari='"+ _Bari +"' and HH='"+ _HH +"' and SESNo='"+ _SESNo +"'";
                 C.Save(SQL);
                 C.close();
              }
              catch(Exception  e)
              {
                 response = e.getMessage();
              }
           return response;
        }


        public List<SES_DataModel> SelectAll(Context context, String SQL)
        {
            Connection C = new Connection(context);
            List<SES_DataModel> data = new ArrayList<SES_DataModel>();
            SES_DataModel d = new SES_DataModel();
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {
                d = new SES_DataModel();
                d._Vill = cur.getString(cur.getColumnIndex("Vill"));
                d._Bari = cur.getString(cur.getColumnIndex("Bari"));
                d._HH = cur.getString(cur.getColumnIndex("HH"));
                d._SESNo = cur.getString(cur.getColumnIndex("SESNo"));
                d._VDate = cur.getString(cur.getColumnIndex("VDate"));
                d._VStatus = cur.getString(cur.getColumnIndex("VStatus"));
                d._VStatusOth = cur.getString(cur.getColumnIndex("VStatusOth"));
                d._Rnd = cur.getString(cur.getColumnIndex("Rnd"));
                d._WSDrink = cur.getString(cur.getColumnIndex("WSDrink"));
                d._WSDrinkOth = cur.getString(cur.getColumnIndex("WSDrinkOth"));
                d._WSCook = cur.getString(cur.getColumnIndex("WSCook"));
                d._WSCookOth = cur.getString(cur.getColumnIndex("WSCookOth"));
                d._WSWash = cur.getString(cur.getColumnIndex("WSWash"));
                d._WSWashOth = cur.getString(cur.getColumnIndex("WSWashOth"));
                d._Latrine = cur.getString(cur.getColumnIndex("Latrine"));
                d._LatrineOth = cur.getString(cur.getColumnIndex("LatrineOth"));
                d._Electricity = cur.getString(cur.getColumnIndex("Electricity"));
                d._Radio = cur.getString(cur.getColumnIndex("Radio"));
                d._TV = cur.getString(cur.getColumnIndex("TV"));
                d._Mobile = cur.getString(cur.getColumnIndex("Mobile"));
                d._Telephone = cur.getString(cur.getColumnIndex("Telephone"));
                d._Refrige = cur.getString(cur.getColumnIndex("Refrige"));
                d._Watch = cur.getString(cur.getColumnIndex("Watch"));
                d._ElecFan = cur.getString(cur.getColumnIndex("ElecFan"));
                d._RickVan = cur.getString(cur.getColumnIndex("RickVan"));
                d._Bicycle = cur.getString(cur.getColumnIndex("Bicycle"));
                d._MotCycle = cur.getString(cur.getColumnIndex("MotCycle"));
                d._Computer = cur.getString(cur.getColumnIndex("Computer"));
                d._Buffalo = cur.getString(cur.getColumnIndex("Buffalo"));
                d._Bull = cur.getString(cur.getColumnIndex("Bull"));
                d._Goat = cur.getString(cur.getColumnIndex("Goat"));
                d._Chicken = cur.getString(cur.getColumnIndex("Chicken"));
                d._Pigeon = cur.getString(cur.getColumnIndex("Pigeon"));
                d._Roof = cur.getString(cur.getColumnIndex("Roof"));
                d._RoofOth = cur.getString(cur.getColumnIndex("RoofOth"));
                d._Wall = cur.getString(cur.getColumnIndex("Wall"));
                d._WallOth = cur.getString(cur.getColumnIndex("WallOth"));
                d._Floor = cur.getString(cur.getColumnIndex("Floor"));
                d._FloorOth = cur.getString(cur.getColumnIndex("FloorOth"));
                d._Homestead = cur.getString(cur.getColumnIndex("Homestead"));
                d._HomesteadOth = cur.getString(cur.getColumnIndex("HomesteadOth"));
                d._OthLand = cur.getString(cur.getColumnIndex("OthLand"));
                data.add(d);

                cur.moveToNext();
            }
            cur.close();
          return data;
        }
 }