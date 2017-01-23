package org.icddrb.champsdss;
//Android Manifest Code
 //<activity android:name=".Visits_list" android:label="Visits: List" />
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

 public class Visits_list extends Activity {
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
 static String RND = "";

 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.visits_list);
         C = new Connection(this);
         g = Global.getInstance();
         StartTime = g.CurrentTime24();

         TableName = "Visits";
         lblHeading = (TextView)findViewById(R.id.lblHeading);
         lblHeading.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 final int DRAWABLE_RIGHT  = 2;
                 if(event.getAction() == MotionEvent.ACTION_DOWN) {
                     if(event.getRawX() >= (lblHeading.getRight() - lblHeading.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                         AlertDialog.Builder adb = new AlertDialog.Builder(Visits_list.this);
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
                 AlertDialog.Builder adb = new AlertDialog.Builder(Visits_list.this);
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
                   DataSearch(VILL, BARI, HH, RND);

             }});

         btnAdd   = (Button) findViewById(R.id.btnAdd);
         btnAdd.setOnClickListener(new View.OnClickListener() {

             public void onClick(View view) {
                         Bundle IDbundle = new Bundle();
                         IDbundle.putString("Vill", "");
                         IDbundle.putString("Bari", "");
                         IDbundle.putString("HH", "");
                         IDbundle.putString("Rnd", "");
                         Intent intent = new Intent(getApplicationContext(), Visits.class);
                         intent.putExtras(IDbundle);
                         startActivityForResult(intent, 1);

             }});


        DataSearch(VILL, BARI, HH, RND);


     }
     catch(Exception  e)
     {
         Connection.MessageBox(Visits_list.this, e.getMessage());
         return;
     }
 }
 
 @Override
 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);
     if (resultCode == Activity.RESULT_CANCELED) {
         //Write your code if there's no result
     } else {
         DataSearch(VILL, BARI, HH, RND);
     }
 }

 private void DataSearch(String Vill, String Bari, String HH, String Rnd)
     {
       try
        {
     
           Visits_DataModel d = new Visits_DataModel();
             String SQL = "Select * from "+ TableName +"  Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"' and Rnd='"+ Rnd +"'";
             List<Visits_DataModel> data = d.SelectAll(this, SQL);
             dataList.clear();

             dataAdapter = null;

             ListView list = (ListView)findViewById(R.id.lstData);
             HashMap<String, String> map;

             for(Visits_DataModel item : data){
                 map = new HashMap<String, String>();
                 map.put("Vill", item.getVill());
                 map.put("Bari", item.getBari());
                 map.put("HH", item.getHH());
                 map.put("VDate", item.getVDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getVDate()));
                 map.put("VStatus", item.getVStatus());
                 map.put("VStatusOth", item.getVStatusOth());
                 map.put("Resp", item.getResp());
                 map.put("Rnd", item.getRnd());
                 dataList.add(map);
             }
             dataAdapter = new SimpleAdapter(Visits_list.this, dataList, R.layout.visits_list,new String[] {"rowsec"},
                           new int[] {R.id.secListRow});
             list.setAdapter(new DataListAdapter(this, dataAdapter));
        }
        catch(Exception  e)
        {
            Connection.MessageBox(Visits_list.this, e.getMessage());
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
 	        convertView = inflater.inflate(R.layout.visits_row, null); 
 	        }
         LinearLayout   secListRow = (LinearLayout)convertView.findViewById(R.id.secListRow);

         final TextView Vill = (TextView)convertView.findViewById(R.id.Vill);
         final TextView Bari = (TextView)convertView.findViewById(R.id.Bari);
         final TextView HH = (TextView)convertView.findViewById(R.id.HH);
         final TextView VDate = (TextView)convertView.findViewById(R.id.VDate);
         final TextView VStatus = (TextView)convertView.findViewById(R.id.VStatus);
         final TextView VStatusOth = (TextView)convertView.findViewById(R.id.VStatusOth);
         final TextView Resp = (TextView)convertView.findViewById(R.id.Resp);
         final TextView Rnd = (TextView)convertView.findViewById(R.id.Rnd);

         final HashMap<String, String> o = (HashMap<String, String>) dataAdap.getItem(position);
         Vill.setText(o.get("Vill"));
         Bari.setText(o.get("Bari"));
         HH.setText(o.get("HH"));
         VDate.setText(o.get("VDate"));
         VStatus.setText(o.get("VStatus"));
         VStatusOth.setText(o.get("VStatusOth"));
         Resp.setText(o.get("Resp"));
         Rnd.setText(o.get("Rnd"));

         secListRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               //Write your code here
               Bundle IDbundle = new Bundle();
               IDbundle.putString("Vill", o.get("Vill"));
               IDbundle.putString("Bari", o.get("Bari"));
               IDbundle.putString("HH", o.get("HH"));
               IDbundle.putString("Rnd", o.get("Rnd"));
               Intent f1;
               f1 = new Intent(getApplicationContext(), Visits.class);
               f1.putExtras(IDbundle);
               startActivity(f1);
            }
          });


         return convertView;
       }
 }


}