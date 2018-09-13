package org.icddrb.champsdsssur;
//Android Manifest Code
 //<activity android:name=".PregScreening_list" android:label="PregScreening: List" />
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

 public class PregScreening_list extends Activity {
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
    static String EVTYPE = "";
    static String EVDATE = "";
    static String RND = "";

 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.pregscreening_list);
         C = new Connection(this);
         g = Global.getInstance();
         STARTTIME = g.CurrentTime24();

         TableName = "PregScreening";
         lblHeading = (TextView)findViewById(R.id.lblHeading);
         lblHeading.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 final int DRAWABLE_RIGHT  = 2;
                 if(event.getAction() == MotionEvent.ACTION_DOWN) {
                     if(event.getRawX() >= (lblHeading.getRight() - lblHeading.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                         AlertDialog.Builder adb = new AlertDialog.Builder(PregScreening_list.this);
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
                 AlertDialog.Builder adb = new AlertDialog.Builder(PregScreening_list.this);
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
                   DataSearch(VILL, BARI, HH, MSLNO, EVTYPE, EVDATE, RND);

             }});

         btnAdd   = (Button) findViewById(R.id.btnAdd);
         btnAdd.setOnClickListener(new View.OnClickListener() {

             public void onClick(View view) {
                         Bundle IDbundle = new Bundle();
                         IDbundle.putString("Vill", "");
                         IDbundle.putString("Bari", "");
                         IDbundle.putString("HH", "");
                         IDbundle.putString("MSlNo", "");
                         IDbundle.putString("EvType", "");
                         IDbundle.putString("EvDate", "");
                         IDbundle.putString("Rnd", "");
                         Intent intent = new Intent(getApplicationContext(), PregScreening.class);
                         intent.putExtras(IDbundle);
                         startActivityForResult(intent, 1);

             }});


        DataSearch(VILL, BARI, HH, MSLNO, EVTYPE, EVDATE, RND);


     }
     catch(Exception  e)
     {
         Connection.MessageBox(PregScreening_list.this, e.getMessage());
         return;
     }
 }
 
 @Override
 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);
     if (resultCode == Activity.RESULT_CANCELED) {
         //Write your code if there's no result
     } else {
         DataSearch(VILL, BARI, HH, MSLNO, EVTYPE, EVDATE, RND);
     }
 }

 private void DataSearch(String Vill, String Bari, String HH, String MSlNo, String EvType, String EvDate, String Rnd)
     {
       try
        {
     
           PregScreening_DataModel d = new PregScreening_DataModel();
             String SQL = "Select * from "+ TableName +"  Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"' and MSlNo='"+ MSlNo +"' and EvType='"+ EvType +"' and EvDate='"+ EvDate +"' and Rnd='"+ Rnd +"'";
             List<PregScreening_DataModel> data = d.SelectAll(this, SQL);
             dataList.clear();

             dataAdapter = null;

             ListView list = (ListView)findViewById(R.id.lstData);
             HashMap<String, String> map;

             Integer i = 0;
             for(PregScreening_DataModel item : data){
                 map = new HashMap<String, String>();
                 map.put("Vill", item.getVill());
                 map.put("Bari", item.getBari());
                 map.put("HH", item.getHH());
                 map.put("MSlNo", item.getMSlNo());
                 map.put("PNo", item.getPNo());
                 map.put("EvType", item.getEvType());
                 map.put("EvDate", item.getEvDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getEvDate()));
                 map.put("PregnancyID", item.getPregnancyID());
                 map.put("Rnd", item.getRnd());
                 map.put("PhoneNo", item.getPhoneNo());
                 map.put("InfoSource", item.getInfoSource());
                 map.put("PregNotiDate", item.getPregNotiDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getPregNotiDate()));
                 map.put("PregConCriteria", item.getPregConCriteria());
                 map.put("Eligible", item.getEligible());
                 map.put("EligibleDate", item.getEligibleDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getEligibleDate()));
                 map.put("EnrollDate", item.getEnrollDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getEnrollDate()));
                 map.put("PrevPregHis", item.getPrevPregHis());
                 map.put("StillBirth", item.getStillBirth());
                 map.put("StillBirthNo", item.getStillBirthNo());
                 map.put("MiscAbor", item.getMiscAbor());
                 map.put("MiscAborNo", item.getMiscAborNo());
                 map.put("LastPregresult", item.getLastPregresult());
                 map.put("DelDate", item.getDelDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getDelDate()));
                 map.put("CesaDel", item.getCesaDel());
                 map.put("CesaDelNo", item.getCesaDelNo());
                 map.put("ObtEstiDelDate", item.getObtEstiDelDate());
                 map.put("UnreliLMP", item.getUnreliLMP());
                 map.put("LMPDate", item.getLMPDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getLMPDate()));
                 map.put("UltraTrime", item.getUltraTrime());
                 map.put("OthSpec", item.getOthSpec());
                 map.put("sl", i.toString());
                 i += 1;

                 dataList.add(map);
             }
             dataAdapter = new SimpleAdapter(PregScreening_list.this, dataList, R.layout.pregscreening_list,new String[] {"rowsec"},
                           new int[] {R.id.secListRow});
             list.setAdapter(new DataListAdapter(this, dataAdapter));
        }
        catch(Exception  e)
        {
            Connection.MessageBox(PregScreening_list.this, e.getMessage());
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
 	        convertView = inflater.inflate(R.layout.pregscreening_row, null); 
 	        }
         LinearLayout   secListRow = (LinearLayout)convertView.findViewById(R.id.secListRow);

         final TextView Vill = (TextView)convertView.findViewById(R.id.Vill);
         final TextView Bari = (TextView)convertView.findViewById(R.id.Bari);
         final TextView HH = (TextView)convertView.findViewById(R.id.HH);
         final TextView MSlNo = (TextView)convertView.findViewById(R.id.MSlNo);
         final TextView PNo = (TextView)convertView.findViewById(R.id.PNo);
         final TextView EvType = (TextView)convertView.findViewById(R.id.EvType);
         final TextView EvDate = (TextView)convertView.findViewById(R.id.EvDate);
         final TextView PregnancyID = (TextView)convertView.findViewById(R.id.PregnancyID);
         final TextView Rnd = (TextView)convertView.findViewById(R.id.Rnd);
         final TextView PhoneNo = (TextView)convertView.findViewById(R.id.PhoneNo);
         final TextView InfoSource = (TextView)convertView.findViewById(R.id.InfoSource);
         final TextView PregNotiDate = (TextView)convertView.findViewById(R.id.PregNotiDate);
         final TextView PregConCriteria = (TextView)convertView.findViewById(R.id.PregConCriteria);
         final TextView Eligible = (TextView)convertView.findViewById(R.id.Eligible);
         final TextView EligibleDate = (TextView)convertView.findViewById(R.id.EligibleDate);
         final TextView EnrollDate = (TextView)convertView.findViewById(R.id.EnrollDate);
         final TextView PrevPregHis = (TextView)convertView.findViewById(R.id.PrevPregHis);
         final TextView StillBirth = (TextView)convertView.findViewById(R.id.StillBirth);
         final TextView StillBirthNo = (TextView)convertView.findViewById(R.id.StillBirthNo);
         final TextView MiscAbor = (TextView)convertView.findViewById(R.id.MiscAbor);
         final TextView MiscAborNo = (TextView)convertView.findViewById(R.id.MiscAborNo);
         final TextView LastPregresult = (TextView)convertView.findViewById(R.id.LastPregresult);
         final TextView DelDate = (TextView)convertView.findViewById(R.id.DelDate);
         final TextView CesaDel = (TextView)convertView.findViewById(R.id.CesaDel);
         final TextView CesaDelNo = (TextView)convertView.findViewById(R.id.CesaDelNo);
         final TextView ObtEstiDelDate = (TextView)convertView.findViewById(R.id.ObtEstiDelDate);
         final TextView UnreliLMP = (TextView)convertView.findViewById(R.id.UnreliLMP);
         final TextView LMPDate = (TextView)convertView.findViewById(R.id.LMPDate);
         final TextView UltraTrime = (TextView)convertView.findViewById(R.id.UltraTrime);
         final TextView OthSpec = (TextView)convertView.findViewById(R.id.OthSpec);

         final HashMap<String, String> o = (HashMap<String, String>) dataAdap.getItem(position);
         Vill.setText(o.get("Vill"));
         Bari.setText(o.get("Bari"));
         HH.setText(o.get("HH"));
         MSlNo.setText(o.get("MSlNo"));
         PNo.setText(o.get("PNo"));
         EvType.setText(o.get("EvType"));
         EvDate.setText(o.get("EvDate"));
         PregnancyID.setText(o.get("PregnancyID"));
         Rnd.setText(o.get("Rnd"));
         PhoneNo.setText(o.get("PhoneNo"));
         InfoSource.setText(o.get("InfoSource"));
         PregNotiDate.setText(o.get("PregNotiDate"));
         PregConCriteria.setText(o.get("PregConCriteria"));
         Eligible.setText(o.get("Eligible"));
         EligibleDate.setText(o.get("EligibleDate"));
         EnrollDate.setText(o.get("EnrollDate"));
         PrevPregHis.setText(o.get("PrevPregHis"));
         StillBirth.setText(o.get("StillBirth"));
         StillBirthNo.setText(o.get("StillBirthNo"));
         MiscAbor.setText(o.get("MiscAbor"));
         MiscAborNo.setText(o.get("MiscAborNo"));
         LastPregresult.setText(o.get("LastPregresult"));
         DelDate.setText(o.get("DelDate"));
         CesaDel.setText(o.get("CesaDel"));
         CesaDelNo.setText(o.get("CesaDelNo"));
         ObtEstiDelDate.setText(o.get("ObtEstiDelDate"));
         UnreliLMP.setText(o.get("UnreliLMP"));
         LMPDate.setText(o.get("LMPDate"));
         UltraTrime.setText(o.get("UltraTrime"));
         OthSpec.setText(o.get("OthSpec"));

//         if (Integer.valueOf(o.get("sl")) % 2 == 0)
//         {
//             secListRow.setBackgroundColor(Color.parseColor("#F3F3F3"));
//         }
//         else
//         {
//             secListRow.setBackgroundColor(Color.parseColor("#FFFFFF"));
//         }

         secListRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               //Write your code here
               Bundle IDbundle = new Bundle();
               IDbundle.putString("Vill", o.get("Vill"));
               IDbundle.putString("Bari", o.get("Bari"));
               IDbundle.putString("HH", o.get("HH"));
               IDbundle.putString("MSlNo", o.get("MSlNo"));
               IDbundle.putString("EvType", o.get("EvType"));
               IDbundle.putString("EvDate", o.get("EvDate"));
               IDbundle.putString("Rnd", o.get("Rnd"));
               Intent f1;
               f1 = new Intent(getApplicationContext(), PregScreening.class);
               f1.putExtras(IDbundle);
               startActivity(f1);
            }
          });


         return convertView;
       }
 }


}