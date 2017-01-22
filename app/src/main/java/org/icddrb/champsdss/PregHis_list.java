package org.icddrb.champsdss;
//Android Manifest Code
 //<activity android:name=".PregHis_list" android:label="PregHis: List" />
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import android.app.*;
 import android.app.AlertDialog;
 import android.content.Context;
 import android.content.DialogInterface;
 import android.content.Intent;
 import android.database.Cursor;
 import android.location.Location;
 import android.view.KeyEvent;
 import android.os.Bundle;
 import android.view.Menu;
 import android.view.MenuInflater;
 import android.view.MenuItem;
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

 public class PregHis_list extends Activity {
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

    static String STARTTIME = "";
    static String VILL = "";
    static String BARI = "";
    static String HH = "";
    static String MSLNO = "";

 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.preghis_list);
         C = new Connection(this);
         g = Global.getInstance();
         STARTTIME = g.CurrentTime24();

         TableName = "PregHis";
         lblHeading = (TextView)findViewById(R.id.lblHeading);
         lblHeading.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 final int DRAWABLE_RIGHT  = 2;
                 if(event.getAction() == MotionEvent.ACTION_DOWN) {
                     if(event.getRawX() >= (lblHeading.getRight() - lblHeading.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                         AlertDialog.Builder adb = new AlertDialog.Builder(PregHis_list.this);
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
                 AlertDialog.Builder adb = new AlertDialog.Builder(PregHis_list.this);
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
                   DataSearch(VILL, BARI, HH, MSLNO);

             }});

         btnAdd   = (Button) findViewById(R.id.btnAdd);
         btnAdd.setOnClickListener(new View.OnClickListener() {

             public void onClick(View view) {
                         Bundle IDbundle = new Bundle();
                         IDbundle.putString("Vill", "");
                         IDbundle.putString("Bari", "");
                         IDbundle.putString("HH", "");
                         IDbundle.putString("MSlNo", "");
                         Intent intent = new Intent(getApplicationContext(), PregHis.class);
                         intent.putExtras(IDbundle);
                         startActivityForResult(intent, 1);

             }});


        DataSearch(VILL, BARI, HH, MSLNO);


     }
     catch(Exception  e)
     {
         Connection.MessageBox(PregHis_list.this, e.getMessage());
         return;
     }
 }
 
 @Override
 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);
     if (resultCode == Activity.RESULT_CANCELED) {
         //Write your code if there's no result
     } else {
         DataSearch(VILL, BARI, HH, MSLNO);
     }
 }

 private void DataSearch(String Vill, String Bari, String HH, String MSlNo)
     {
       try
        {
     
           PregHis_DataModel d = new PregHis_DataModel();
             String SQL = "Select * from "+ TableName +"  Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"' and MSlNo='"+ MSlNo +"'";
             List<PregHis_DataModel> data = d.SelectAll(this, SQL);
             dataList.clear();

             dataAdapter = null;

             ListView list = (ListView)findViewById(R.id.lstData);
             HashMap<String, String> map;

             for(PregHis_DataModel item : data){
                 map = new HashMap<String, String>();
                 map.put("Vill", item.getVill());
                 map.put("Bari", item.getBari());
                 map.put("HH", item.getHH());
                 map.put("MSlNo", item.getMSlNo());
                 map.put("PNo", item.getPNo());
                 map.put("VDate", item.getVDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getVDate()));
                 map.put("VStatus", item.getVStatus());
                 map.put("VStatusOth", item.getVStatusOth());
                 map.put("MarriageStatus", item.getMarriageStatus());
                 map.put("MarMon", item.getMarMon());
                 map.put("MarYear", item.getMarYear());
                 map.put("MarDK", item.getMarDK());
                 map.put("GaveBirth", item.getGaveBirth());
                 map.put("ChildLivWWo", item.getChildLivWWo());
                 map.put("SonLivWWo", item.getSonLivWWo());
                 map.put("DaugLivWWo", item.getDaugLivWWo());
                 map.put("ChldLivOut", item.getChldLivOut());
                 map.put("SonLivOut", item.getSonLivOut());
                 map.put("DaugLivOut", item.getDaugLivOut());
                 map.put("ChldDie", item.getChldDie());
                 map.put("BoyDied", item.getBoyDied());
                 map.put("GirlDied", item.getGirlDied());
                 map.put("NotLivBrth", item.getNotLivBrth());
                 map.put("TotLB", item.getTotLB());
                 map.put("TotPregOut", item.getTotPregOut());
                 map.put("CurPreg", item.getCurPreg());
                 map.put("LMPDate", item.getLMPDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getLMPDate()));
                 dataList.add(map);
             }
             dataAdapter = new SimpleAdapter(PregHis_list.this, dataList, R.layout.preghis_list,new String[] {"rowsec"},
                           new int[] {R.id.secListRow});
             list.setAdapter(new DataListAdapter(this, dataAdapter));
        }
        catch(Exception  e)
        {
            Connection.MessageBox(PregHis_list.this, e.getMessage());
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
 	        convertView = inflater.inflate(R.layout.preghis_row, null); 
 	        }
         LinearLayout   secListRow = (LinearLayout)convertView.findViewById(R.id.secListRow);

         final TextView Vill = (TextView)convertView.findViewById(R.id.Vill);
         final TextView Bari = (TextView)convertView.findViewById(R.id.Bari);
         final TextView HH = (TextView)convertView.findViewById(R.id.HH);
         final TextView MSlNo = (TextView)convertView.findViewById(R.id.MSlNo);
         final TextView PNo = (TextView)convertView.findViewById(R.id.PNo);
         final TextView VDate = (TextView)convertView.findViewById(R.id.VDate);
         final TextView VStatus = (TextView)convertView.findViewById(R.id.VStatus);
         final TextView VStatusOth = (TextView)convertView.findViewById(R.id.VStatusOth);
         final TextView MarriageStatus = (TextView)convertView.findViewById(R.id.MarriageStatus);
         final TextView MarMon = (TextView)convertView.findViewById(R.id.MarMon);
         final TextView MarYear = (TextView)convertView.findViewById(R.id.MarYear);
         final TextView MarDK = (TextView)convertView.findViewById(R.id.MarDK);
         final TextView GaveBirth = (TextView)convertView.findViewById(R.id.GaveBirth);
         final TextView ChildLivWWo = (TextView)convertView.findViewById(R.id.ChildLivWWo);
         final TextView SonLivWWo = (TextView)convertView.findViewById(R.id.SonLivWWo);
         final TextView DaugLivWWo = (TextView)convertView.findViewById(R.id.DaugLivWWo);
         final TextView ChldLivOut = (TextView)convertView.findViewById(R.id.ChldLivOut);
         final TextView SonLivOut = (TextView)convertView.findViewById(R.id.SonLivOut);
         final TextView DaugLivOut = (TextView)convertView.findViewById(R.id.DaugLivOut);
         final TextView ChldDie = (TextView)convertView.findViewById(R.id.ChldDie);
         final TextView BoyDied = (TextView)convertView.findViewById(R.id.BoyDied);
         final TextView GirlDied = (TextView)convertView.findViewById(R.id.GirlDied);
         final TextView NotLivBrth = (TextView)convertView.findViewById(R.id.NotLivBrth);
         final TextView TotLB = (TextView)convertView.findViewById(R.id.TotLB);
         final TextView TotPregOut = (TextView)convertView.findViewById(R.id.TotPregOut);
         final TextView CurPreg = (TextView)convertView.findViewById(R.id.CurPreg);
         final TextView LMPDate = (TextView)convertView.findViewById(R.id.LMPDate);

         final HashMap<String, String> o = (HashMap<String, String>) dataAdap.getItem(position);
         Vill.setText(o.get("Vill"));
         Bari.setText(o.get("Bari"));
         HH.setText(o.get("HH"));
         MSlNo.setText(o.get("MSlNo"));
         PNo.setText(o.get("PNo"));
         VDate.setText(o.get("VDate"));
         VStatus.setText(o.get("VStatus"));
         VStatusOth.setText(o.get("VStatusOth"));
         MarriageStatus.setText(o.get("MarriageStatus"));
         MarMon.setText(o.get("MarMon"));
         MarYear.setText(o.get("MarYear"));
         MarDK.setText(o.get("MarDK"));
         GaveBirth.setText(o.get("GaveBirth"));
         ChildLivWWo.setText(o.get("ChildLivWWo"));
         SonLivWWo.setText(o.get("SonLivWWo"));
         DaugLivWWo.setText(o.get("DaugLivWWo"));
         ChldLivOut.setText(o.get("ChldLivOut"));
         SonLivOut.setText(o.get("SonLivOut"));
         DaugLivOut.setText(o.get("DaugLivOut"));
         ChldDie.setText(o.get("ChldDie"));
         BoyDied.setText(o.get("BoyDied"));
         GirlDied.setText(o.get("GirlDied"));
         NotLivBrth.setText(o.get("NotLivBrth"));
         TotLB.setText(o.get("TotLB"));
         TotPregOut.setText(o.get("TotPregOut"));
         CurPreg.setText(o.get("CurPreg"));
         LMPDate.setText(o.get("LMPDate"));

         secListRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               //Write your code here
               Bundle IDbundle = new Bundle();
               IDbundle.putString("Vill", o.get("Vill"));
               IDbundle.putString("Bari", o.get("Bari"));
               IDbundle.putString("HH", o.get("HH"));
               IDbundle.putString("MSlNo", o.get("MSlNo"));
               Intent f1;
               f1 = new Intent(getApplicationContext(), PregHis.class);
               f1.putExtras(IDbundle);
               startActivity(f1);
            }
          });


         return convertView;
       }
 }


}