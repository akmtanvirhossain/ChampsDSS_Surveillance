package org.icddrb.champsdsssur;
//Android Manifest Code
 //<activity android:name=".SES_list" android:label="SES: List" />
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import android.app.*;
 import android.app.AlertDialog;
 import android.content.Context;
 import android.content.DialogInterface;
 import android.content.Intent;
 import android.location.Location;
 import android.view.KeyEvent;
 import android.os.Bundle;
 import android.view.View;
 import android.view.MotionEvent;
 import android.view.ViewGroup;
 import android.view.LayoutInflater;
 import android.widget.LinearLayout;
 import android.widget.ListView;
 import android.widget.SimpleAdapter;
 import android.widget.BaseAdapter;
 import android.widget.TextView;
 import android.widget.Button;
 import android.widget.ImageButton;
 import Common.*;

 public class SES_list extends Activity {
    boolean networkAvailable=false;
    Location currentLocation; 
    double currentLatitude,currentLongitude; 
    //Disabled Back/Home key
    //--------------------------------------------------------------------------------------------------
    @Override 
    public boolean onKeyDown(int iKeyCode, KeyEvent event)
    {
        if(iKeyCode == KeyEvent.KEYCODE_BACK || iKeyCode == KeyEvent.KEYCODE_HOME) 
             { return false; }
        else { return true;  }
    }
    String VariableID;
    private int hour;
    private int minute;
    private int mDay;
    private int mMonth;
    private int mYear;
    static final int DATE_DIALOG = 1;
    static final int TIME_DIALOG = 2;

    Connection C;
    Global g;
    SimpleAdapter dataAdapter;
    ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
    static String TableName;

    TextView lblHeading;
    Button btnAdd;
    Button btnRefresh;

    String StartTime;
 static String VILL = "";
 static String BARI = "";
 static String HH = "";
 static String SESNO = "";

 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.ses_list);
         C = new Connection(this);
         g = Global.getInstance();
         StartTime = g.CurrentTime24();

         TableName = "SES";
         lblHeading = (TextView)findViewById(R.id.lblHeading);
         lblHeading.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 final int DRAWABLE_RIGHT  = 2;
                 if(event.getAction() == MotionEvent.ACTION_DOWN) {
                     if(event.getRawX() >= (lblHeading.getRight() - lblHeading.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                         AlertDialog.Builder adb = new AlertDialog.Builder(SES_list.this);
                         adb.setTitle("Close");
                         adb.setMessage("Do you want to close this form[Yes/No]?");
                         adb.setNegativeButton("No", null);
                         adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                             public void onClick(DialogInterface dialog, int which) {
                                 finish();
                             }});
                         adb.show();
                         return true;
                     }
                 }
                 return false;
             }
         });

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(SES_list.this);
                 adb.setTitle("Close");
                 adb.setMessage("Do you want to close this form[Yes/No]?");
                 adb.setNegativeButton("No", null);
                 adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         finish();
                     }});
                 adb.show();
             }});

         btnRefresh = (Button) findViewById(R.id.btnRefresh);
         btnRefresh.setOnClickListener(new View.OnClickListener() {

             public void onClick(View view) {
                   //write your code here
                   DataSearch(VILL, BARI, HH, SESNO);

             }});

         btnAdd   = (Button) findViewById(R.id.btnAdd);
         btnAdd.setOnClickListener(new View.OnClickListener() {

             public void onClick(View view) {
                         Bundle IDbundle = new Bundle();
                         IDbundle.putString("Vill", "");
                         IDbundle.putString("Bari", "");
                         IDbundle.putString("HH", "");
                         IDbundle.putString("SESNo", "");
                         Intent intent = new Intent(getApplicationContext(), SES.class);
                         intent.putExtras(IDbundle);
                         startActivityForResult(intent, 1);

             }});


        DataSearch(VILL, BARI, HH, SESNO);


     }
     catch(Exception  e)
     {
         Connection.MessageBox(SES_list.this, e.getMessage());
         return;
     }
 }
 
 @Override
 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);
     if (resultCode == Activity.RESULT_CANCELED) {
         //Write your code if there's no result
     } else {
         DataSearch(VILL, BARI, HH, SESNO);
     }
 }

 private void DataSearch(String Vill, String Bari, String HH, String SESNo)
     {
       try
        {
     
           SES_DataModel d = new SES_DataModel();
             String SQL = "Select * from "+ TableName +"  Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"' and SESNo='"+ SESNo +"'";
             List<SES_DataModel> data = d.SelectAll(this, SQL);
             dataList.clear();

             dataAdapter = null;

             ListView list = (ListView)findViewById(R.id.lstData);
             HashMap<String, String> map;

             for(SES_DataModel item : data){
                 map = new HashMap<String, String>();
                 map.put("Vill", item.getVill());
                 map.put("Bari", item.getBari());
                 map.put("HH", item.getHH());
                 map.put("SESNo", item.getSESNo());
                 map.put("VDate", item.getVDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getVDate()));
                 map.put("VStatus", item.getVStatus());
                 map.put("VStatusOth", item.getVStatusOth());
                 map.put("Rnd", item.getRnd());
                 map.put("WSDrink", item.getWSDrink());
                 map.put("WSDrinkOth", item.getWSDrinkOth());
                 map.put("WSCook", item.getWSCook());
                 map.put("WSCookOth", item.getWSCookOth());
                 map.put("WSWash", item.getWSWash());
                 map.put("WSWashOth", item.getWSWashOth());
                 map.put("Latrine", item.getLatrine());
                 map.put("LatrineOth", item.getLatrineOth());
                 map.put("Electricity", item.getElectricity());
                 map.put("Radio", item.getRadio());
                 map.put("TV", item.getTV());
                 map.put("Mobile", item.getMobile());
                 map.put("Telephone", item.getTelephone());
                 map.put("Refrige", item.getRefrige());
                 map.put("Watch", item.getWatch());
                 map.put("ElecFan", item.getElecFan());
                 map.put("RickVan", item.getRickVan());
                 map.put("Bicycle", item.getBicycle());
                 map.put("MotCycle", item.getMotCycle());
                 map.put("Computer", item.getComputer());
                 map.put("Buffalo", item.getBuffalo());
                 map.put("Bull", item.getBull());
                 map.put("Goat", item.getGoat());
                 map.put("Chicken", item.getChicken());
                 map.put("Pigeon", item.getPigeon());
                 map.put("Roof", item.getRoof());
                 map.put("RoofOth", item.getRoofOth());
                 map.put("Wall", item.getWall());
                 map.put("WallOth", item.getWallOth());
                 map.put("Floor", item.getFloor());
                 map.put("FloorOth", item.getFloorOth());
                 map.put("Homestead", item.getHomestead());
                 map.put("HomesteadOth", item.getHomesteadOth());
                 map.put("OthLand", item.getOthLand());
                 dataList.add(map);
             }
             dataAdapter = new SimpleAdapter(SES_list.this, dataList, R.layout.ses_list,new String[] {"rowsec"},
                           new int[] {R.id.secListRow});
             list.setAdapter(new DataListAdapter(this, dataAdapter));
        }
        catch(Exception  e)
        {
            Connection.MessageBox(SES_list.this, e.getMessage());
            return;
        }
     }


 public class DataListAdapter extends BaseAdapter 
 {
     private Context context;
     private SimpleAdapter dataAdap;

     public DataListAdapter(Context c, SimpleAdapter da){ context = c;  dataAdap = da; }
     public int getCount() {  return dataAdap.getCount();  }
     public Object getItem(int position) {  return position;  }
     public long getItemId(int position) {  return position;  }
     public View getView(final int position, View convertView, ViewGroup parent) {
         LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         if (convertView == null) {
 	        convertView = inflater.inflate(R.layout.ses_row, null); 
 	        }
         LinearLayout   secListRow = (LinearLayout)convertView.findViewById(R.id.secListRow);

         final TextView Vill = (TextView)convertView.findViewById(R.id.Vill);
         final TextView Bari = (TextView)convertView.findViewById(R.id.Bari);
         final TextView HH = (TextView)convertView.findViewById(R.id.HH);
         final TextView SESNo = (TextView)convertView.findViewById(R.id.SESNo);
         final TextView VDate = (TextView)convertView.findViewById(R.id.VDate);
         final TextView VStatus = (TextView)convertView.findViewById(R.id.VStatus);
         final TextView VStatusOth = (TextView)convertView.findViewById(R.id.VStatusOth);
         final TextView Rnd = (TextView)convertView.findViewById(R.id.Rnd);
         final TextView WSDrink = (TextView)convertView.findViewById(R.id.WSDrink);
         final TextView WSDrinkOth = (TextView)convertView.findViewById(R.id.WSDrinkOth);
         final TextView WSCook = (TextView)convertView.findViewById(R.id.WSCook);
         final TextView WSCookOth = (TextView)convertView.findViewById(R.id.WSCookOth);
         final TextView WSWash = (TextView)convertView.findViewById(R.id.WSWash);
         final TextView WSWashOth = (TextView)convertView.findViewById(R.id.WSWashOth);
         final TextView Latrine = (TextView)convertView.findViewById(R.id.Latrine);
         final TextView LatrineOth = (TextView)convertView.findViewById(R.id.LatrineOth);
         final TextView Electricity = (TextView)convertView.findViewById(R.id.Electricity);
         final TextView Radio = (TextView)convertView.findViewById(R.id.Radio);
         final TextView TV = (TextView)convertView.findViewById(R.id.TV);
         final TextView Mobile = (TextView)convertView.findViewById(R.id.Mobile);
         final TextView Telephone = (TextView)convertView.findViewById(R.id.Telephone);
         final TextView Refrige = (TextView)convertView.findViewById(R.id.Refrige);
         final TextView Watch = (TextView)convertView.findViewById(R.id.Watch);
         final TextView ElecFan = (TextView)convertView.findViewById(R.id.ElecFan);
         final TextView RickVan = (TextView)convertView.findViewById(R.id.RickVan);
         final TextView Bicycle = (TextView)convertView.findViewById(R.id.Bicycle);
         final TextView MotCycle = (TextView)convertView.findViewById(R.id.MotCycle);
         final TextView Computer = (TextView)convertView.findViewById(R.id.Computer);
         final TextView Buffalo = (TextView)convertView.findViewById(R.id.Buffalo);
         final TextView Bull = (TextView)convertView.findViewById(R.id.Bull);
         final TextView Goat = (TextView)convertView.findViewById(R.id.Goat);
         final TextView Chicken = (TextView)convertView.findViewById(R.id.Chicken);
         final TextView Pigeon = (TextView)convertView.findViewById(R.id.Pigeon);
         final TextView Roof = (TextView)convertView.findViewById(R.id.Roof);
         final TextView RoofOth = (TextView)convertView.findViewById(R.id.RoofOth);
         final TextView Wall = (TextView)convertView.findViewById(R.id.Wall);
         final TextView WallOth = (TextView)convertView.findViewById(R.id.WallOth);
         final TextView Floor = (TextView)convertView.findViewById(R.id.Floor);
         final TextView FloorOth = (TextView)convertView.findViewById(R.id.FloorOth);
         final TextView Homestead = (TextView)convertView.findViewById(R.id.Homestead);
         final TextView HomesteadOth = (TextView)convertView.findViewById(R.id.HomesteadOth);
         final TextView OthLand = (TextView)convertView.findViewById(R.id.OthLand);

         final HashMap<String, String> o = (HashMap<String, String>) dataAdap.getItem(position);
         Vill.setText(o.get("Vill"));
         Bari.setText(o.get("Bari"));
         HH.setText(o.get("HH"));
         SESNo.setText(o.get("SESNo"));
         VDate.setText(o.get("VDate"));
         VStatus.setText(o.get("VStatus"));
         VStatusOth.setText(o.get("VStatusOth"));
         Rnd.setText(o.get("Rnd"));
         WSDrink.setText(o.get("WSDrink"));
         WSDrinkOth.setText(o.get("WSDrinkOth"));
         WSCook.setText(o.get("WSCook"));
         WSCookOth.setText(o.get("WSCookOth"));
         WSWash.setText(o.get("WSWash"));
         WSWashOth.setText(o.get("WSWashOth"));
         Latrine.setText(o.get("Latrine"));
         LatrineOth.setText(o.get("LatrineOth"));
         Electricity.setText(o.get("Electricity"));
         Radio.setText(o.get("Radio"));
         TV.setText(o.get("TV"));
         Mobile.setText(o.get("Mobile"));
         Telephone.setText(o.get("Telephone"));
         Refrige.setText(o.get("Refrige"));
         Watch.setText(o.get("Watch"));
         ElecFan.setText(o.get("ElecFan"));
         RickVan.setText(o.get("RickVan"));
         Bicycle.setText(o.get("Bicycle"));
         MotCycle.setText(o.get("MotCycle"));
         Computer.setText(o.get("Computer"));
         Buffalo.setText(o.get("Buffalo"));
         Bull.setText(o.get("Bull"));
         Goat.setText(o.get("Goat"));
         Chicken.setText(o.get("Chicken"));
         Pigeon.setText(o.get("Pigeon"));
         Roof.setText(o.get("Roof"));
         RoofOth.setText(o.get("RoofOth"));
         Wall.setText(o.get("Wall"));
         WallOth.setText(o.get("WallOth"));
         Floor.setText(o.get("Floor"));
         FloorOth.setText(o.get("FloorOth"));
         Homestead.setText(o.get("Homestead"));
         HomesteadOth.setText(o.get("HomesteadOth"));
         OthLand.setText(o.get("OthLand"));

         secListRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               //Write your code here
               Bundle IDbundle = new Bundle();
               IDbundle.putString("Vill", o.get("Vill"));
               IDbundle.putString("Bari", o.get("Bari"));
               IDbundle.putString("HH", o.get("HH"));
               IDbundle.putString("SESNo", o.get("SESNo"));
               Intent f1;
               f1 = new Intent(getApplicationContext(), SES.class);
               f1.putExtras(IDbundle);
               startActivity(f1);
            }
          });


         return convertView;
       }
 }


}