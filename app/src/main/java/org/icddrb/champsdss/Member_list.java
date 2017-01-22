package org.icddrb.champsdss;
//Android Manifest Code
 //<activity android:name=".Member_list" android:label="Member: List" />
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

 public class Member_list extends Activity {
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
         setContentView(R.layout.member_list);
         C = new Connection(this);
         g = Global.getInstance();
         STARTTIME = g.CurrentTime24();

         TableName = "Member";
         lblHeading = (TextView)findViewById(R.id.lblHeading);
         lblHeading.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 final int DRAWABLE_RIGHT  = 2;
                 if(event.getAction() == MotionEvent.ACTION_DOWN) {
                     if(event.getRawX() >= (lblHeading.getRight() - lblHeading.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                         AlertDialog.Builder adb = new AlertDialog.Builder(Member_list.this);
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
                 AlertDialog.Builder adb = new AlertDialog.Builder(Member_list.this);
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
                         Intent intent = new Intent(getApplicationContext(), Member.class);
                         intent.putExtras(IDbundle);
                         startActivityForResult(intent, 1);

             }});


        DataSearch(VILL, BARI, HH, MSLNO);


     }
     catch(Exception  e)
     {
         Connection.MessageBox(Member_list.this, e.getMessage());
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
     
           Member_DataModel d = new Member_DataModel();
             String SQL = "Select * from "+ TableName +"  Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"' and MSlNo='"+ MSlNo +"'";
             List<Member_DataModel> data = d.SelectAll(this, SQL);
             dataList.clear();

             dataAdapter = null;

             ListView list = (ListView)findViewById(R.id.lstData);
             HashMap<String, String> map;

             for(Member_DataModel item : data){
                 map = new HashMap<String, String>();
                 map.put("Vill", item.getVill());
                 map.put("Bari", item.getBari());
                 map.put("HH", item.getHH());
                 map.put("MSlNo", item.getMSlNo());
                 map.put("PNo", item.getPNo());
                 map.put("Name", item.getName());
                 map.put("Rth", item.getRth());
                 map.put("Sex", item.getSex());
                 map.put("BDate", item.getBDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getBDate()));
                 map.put("AgeY", item.getAgeY());
                 map.put("MoNo", item.getMoNo());
                 map.put("FaNo", item.getFaNo());
                 map.put("Edu", item.getEdu());
                 map.put("MS", item.getMS());
                 map.put("Ocp", item.getOcp());
                 map.put("Sp1", item.getSp1());
                 map.put("Sp2", item.getSp2());
                 map.put("Sp3", item.getSp3());
                 map.put("Sp4", item.getSp4());
                 map.put("EnType", item.getEnType());
                 map.put("EnDate", item.getEnDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getEnDate()));
                 map.put("ExType", item.getExType());
                 map.put("ExDate", item.getExDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getExDate()));
                 dataList.add(map);
             }
             dataAdapter = new SimpleAdapter(Member_list.this, dataList, R.layout.member_list,new String[] {"rowsec"},
                           new int[] {R.id.secListRow});
             list.setAdapter(new DataListAdapter(this, dataAdapter));
        }
        catch(Exception  e)
        {
            Connection.MessageBox(Member_list.this, e.getMessage());
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
 	        convertView = inflater.inflate(R.layout.member_row, null); 
 	        }
         LinearLayout   secListRow = (LinearLayout)convertView.findViewById(R.id.secListRow);

         final TextView Vill = (TextView)convertView.findViewById(R.id.Vill);
         final TextView Bari = (TextView)convertView.findViewById(R.id.Bari);
         final TextView HH = (TextView)convertView.findViewById(R.id.HH);
         final TextView MSlNo = (TextView)convertView.findViewById(R.id.MSlNo);
         final TextView PNo = (TextView)convertView.findViewById(R.id.PNo);
         final TextView Name = (TextView)convertView.findViewById(R.id.Name);
         final TextView Rth = (TextView)convertView.findViewById(R.id.Rth);
         final TextView Sex = (TextView)convertView.findViewById(R.id.Sex);
         final TextView BDate = (TextView)convertView.findViewById(R.id.BDate);
         final TextView AgeY = (TextView)convertView.findViewById(R.id.AgeY);
         final TextView MoNo = (TextView)convertView.findViewById(R.id.MoNo);
         final TextView FaNo = (TextView)convertView.findViewById(R.id.FaNo);
         final TextView Edu = (TextView)convertView.findViewById(R.id.Edu);
         final TextView MS = (TextView)convertView.findViewById(R.id.MS);
         final TextView Ocp = (TextView)convertView.findViewById(R.id.Ocp);
         final TextView Sp1 = (TextView)convertView.findViewById(R.id.Sp1);
         final TextView Sp2 = (TextView)convertView.findViewById(R.id.Sp2);
         final TextView Sp3 = (TextView)convertView.findViewById(R.id.Sp3);
         final TextView Sp4 = (TextView)convertView.findViewById(R.id.Sp4);
         final TextView EnType = (TextView)convertView.findViewById(R.id.EnType);
         final TextView EnDate = (TextView)convertView.findViewById(R.id.EnDate);
         final TextView ExType = (TextView)convertView.findViewById(R.id.ExType);
         final TextView ExDate = (TextView)convertView.findViewById(R.id.ExDate);

         final HashMap<String, String> o = (HashMap<String, String>) dataAdap.getItem(position);
         Vill.setText(o.get("Vill"));
         Bari.setText(o.get("Bari"));
         HH.setText(o.get("HH"));
         MSlNo.setText(o.get("MSlNo"));
         PNo.setText(o.get("PNo"));
         Name.setText(o.get("Name"));
         Rth.setText(o.get("Rth"));
         Sex.setText(o.get("Sex"));
         BDate.setText(o.get("BDate"));
         AgeY.setText(o.get("AgeY"));
         MoNo.setText(o.get("MoNo"));
         FaNo.setText(o.get("FaNo"));
         Edu.setText(o.get("Edu"));
         MS.setText(o.get("MS"));
         Ocp.setText(o.get("Ocp"));
         Sp1.setText(o.get("Sp1"));
         Sp2.setText(o.get("Sp2"));
         Sp3.setText(o.get("Sp3"));
         Sp4.setText(o.get("Sp4"));
         EnType.setText(o.get("EnType"));
         EnDate.setText(o.get("EnDate"));
         ExType.setText(o.get("ExType"));
         ExDate.setText(o.get("ExDate"));

         secListRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               //Write your code here
               Bundle IDbundle = new Bundle();
               IDbundle.putString("Vill", o.get("Vill"));
               IDbundle.putString("Bari", o.get("Bari"));
               IDbundle.putString("HH", o.get("HH"));
               IDbundle.putString("MSlNo", o.get("MSlNo"));
               Intent f1;
               f1 = new Intent(getApplicationContext(), Member.class);
               f1.putExtras(IDbundle);
               startActivity(f1);
            }
          });


         return convertView;
       }
 }


}