package org.icddrb.champsdsssur;
//Android Manifest Code
 //<activity android:name=".Household_list" android:label="Household: List" />
 import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
 import android.database.Cursor;
 import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
 import android.widget.Toast;

 import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Common.Connection;
import Common.Global;
 import Utility.MySharedPreferences;

public class Household_list extends Activity  {
    boolean networkAvailable=false;
    Location currentLocation;
    double currentLatitude,currentLongitude;
     Spinner spnVill;
     Spinner spnBari;
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
    ArrayList<HashMap<String, String>> mylist;
    SimpleAdapter mSchedule;
    TextView lblHeading;
    Button btnAdd;
    Button btnRefresh;

     Bundle IDbundle;
     private static String CurrentVillage;
     private static String CurrentVCode;

    static String STARTTIME = "";
    static String VILL = "";
    static String BARI = "";
    static String HH = "";
    static String DEVICEID  = "";
    static String ENTRYUSER = "";

    static String BName = "";
    static String ROUNDNO = "";
    static String CLUSTER = "";
    static String BLOCK   = "";
    MySharedPreferences sp;

 public void onCreate(Bundle savedInstanceState)
 {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.household_list);
         C = new Connection(this);
         g = Global.getInstance();
         STARTTIME = g.CurrentTime24();
         DEVICEID  = g.getDeviceNo();
         ENTRYUSER = g.getUserId();

         IDbundle = getIntent().getExtras();
         CurrentVillage = IDbundle.getString("Village");
         CurrentVCode   = IDbundle.getString("VCode");
         BName=IDbundle.getString("BariName");
         HH = IDbundle.getString("HH");

         //ROUNDNO        = IDbundle.getString("roundno");
         //CLUSTER        = IDbundle.getString("cluster");
         //BLOCK          = IDbundle.getString("block");

         sp = new MySharedPreferences();
         ROUNDNO = sp.getValue(this,"roundno");
         CLUSTER = sp.getValue(this,"cluster");
         BLOCK = sp.getValue(this,"block");

         TableName = "Household";
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(Household_list.this);
                 adb.setTitle("Close");
                 adb.setMessage("আপনি কি খানার তালিকা থেকে বের হতে চান [হ্যাঁ/না]?");
                 adb.setNegativeButton("না", null);
                 adb.setPositiveButton("হ্যাঁ", new AlertDialog.OnClickListener()
                 {
                     public void onClick(DialogInterface dialog, int which) {
                         /*String V = spnVill.getSelectedItemPosition()==0?"": Global.Left(spnVill.getSelectedItem().toString(),3);
                         String B = spnBari.getSelectedItemPosition()==0?"": Global.Left(spnBari.getSelectedItem().toString(),4);
                         C.Save("Delete from LastVillBari");
                         C.Save("Insert into LastVillBari(Vill, Bari)Values('"+ V +"','"+ B +"')");*/

                         finish();
                         startActivity(new Intent(Household_list.this, MainMenu.class));
                     }});
                 adb.show();
             }});


         spnVill = (Spinner)findViewById(R.id.spnVill);
         spnVill.setAdapter(C.getArrayAdapter("Select distinct v.VCode||'-'||v.VName from Baris b inner join Village v on b.Vill=v.VCode where b.Cluster='"+ CLUSTER +"' and b.Block='"+ BLOCK +"'"));
         spnVill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                 spnBari.setAdapter(C.getArrayAdapter("Select '.All Bari' union Select Bari||'-'||BariName from Baris where Vill='"+ spnVill.getSelectedItem().toString().split("-")[0] +"' and Cluster='"+ CLUSTER +"' and Block='"+ BLOCK +"'"));
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

         spnBari = (Spinner)findViewById(R.id.spnBari);
         spnBari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                 String V = spnVill.getSelectedItem().toString().split("-")[0];
                 String B = spnBari.getSelectedItem().toString().equalsIgnoreCase(".all bari")?"":spnBari.getSelectedItem().toString().split("-")[0];
                 DataSearch(CLUSTER, BLOCK,V,B);

                 /*if(spnBari.getSelectedItem().toString().trim().equalsIgnoreCase(".all bari"))
                 {
                     DataSearch(CLUSTER, BLOCK,"");
                 }
                 else
                 {
                     String[] B = Connection.split(spnBari.getSelectedItem().toString(),'-');
                     BARI = B[0];
                     DataSearch(CLUSTER,BLOCK,BARI);
                 }*/
             }

             @Override
             public void onNothingSelected(AdapterView<?> parentView) {

             }
         });

         Button cmdBari = (Button)findViewById(R.id.cmdBari);
         cmdBari.setOnClickListener(new View.OnClickListener() {
             public void onClick(View arg0) {

                 String V = Connection.SelectedSpinnerValue(spnVill.getSelectedItem().toString(),"-");
                 Bundle IDbundle = new Bundle();
                 IDbundle.putString("Vill", V);
                 IDbundle.putString("Bari", "");
                 IDbundle.putString("cluster", CLUSTER);
                 IDbundle.putString("block", BLOCK);
                 Intent intent = new Intent(getApplicationContext(),Baris.class);
                 intent.putExtras(IDbundle);
                 startActivityForResult(intent, 1);
             }
         });

         Button cmdUpdateBari = (Button)findViewById(R.id.cmdUpdateBari);
         cmdUpdateBari.setOnClickListener(new View.OnClickListener() {
             public void onClick(View arg0) {
                 if(spnBari.getSelectedItemPosition()==0){
                     Connection.MessageBox(Household_list.this,"বাড়ির তালিকা থেকে সঠিক বাড়ির নাম নির্বাচন করুন.");
                     return;
                 }
                 String V = Connection.SelectedSpinnerValue(spnVill.getSelectedItem().toString(),"-");
                 String B = Connection.SelectedSpinnerValue(spnBari.getSelectedItem().toString(),"-");
                 Bundle IDbundle = new Bundle();
                 IDbundle.putString("Vill", V);
                 IDbundle.putString("Bari", B);
                 IDbundle.putString("cluster", CLUSTER);
                 IDbundle.putString("block", BLOCK);
                 Intent intent = new Intent(getApplicationContext(),Baris.class);
                 intent.putExtras(IDbundle);
                 startActivityForResult(intent, 1);
             }
         });

         Button cmdHH= (Button) findViewById((R.id.cmdHH));
         cmdHH.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(spnBari.getSelectedItemPosition()==0){
                     Connection.MessageBox(Household_list.this,"বাড়ির তালিকা থেকে সঠিক বাড়ির নাম নির্বাচন করুন.");
                     return;
                 }
                 String V = Connection.SelectedSpinnerValue(spnVill.getSelectedItem().toString(),"-");
                 String B = Connection.SelectedSpinnerValue(spnBari.getSelectedItem().toString(),"-");
                 IDbundle.putString("Vill", V);
                 IDbundle.putString("Bari", B);
                 IDbundle.putString("HH", "");
                 IDbundle.putString("OldNew", "new");
                 IDbundle.putString("BariName",spnBari.getSelectedItem().toString().split("-")[1]);

                 Intent intent = new Intent(getApplicationContext(),Household_Visit.class);
                 intent.putExtras(IDbundle);
                 startActivityForResult(intent, 1);
             }
         });

         Button cmdGPS= (Button) findViewById((R.id.cmdGPS));
         cmdGPS.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(spnVill.getSelectedItemPosition()==0){
                     Connection.MessageBox(Household_list.this,"গ্রামের তালিকা থেকে সঠিক গ্রামের নাম নির্বাচন করুন.");
                     return;
                 }

                 String V = Connection.SelectedSpinnerValue(spnVill.getSelectedItem().toString(),"-");
                 String B = Connection.SelectedSpinnerValue(spnBari.getSelectedItem().toString(),"-");
                 IDbundle.putString("village", V);
                 IDbundle.putString("bari", B);

                 Intent intent1 = new Intent(getApplicationContext(),map.IconGeneratorDemoActivity.class);
                 intent1.putExtras(IDbundle);
                 startActivity(intent1);
             }
         });

         String V = spnVill.getSelectedItem().toString().split("-")[0];
         DataSearch(CLUSTER, BLOCK,V,"");

     }
     catch(Exception  e)
     {
         Connection.MessageBox(Household_list.this, e.getMessage());
         return;
     }
 }

    private void HHListForm(final String VILL, final String BARI) {
        try {
            final Dialog dialog = new Dialog(Household_list.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.hh_list);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);

            Window window = dialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();

            wlp.gravity = Gravity.TOP;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);

            final TextView txtVill = (TextView) dialog.findViewById(R.id.txtVill);
            final TextView txtBari = (TextView) dialog.findViewById(R.id.txtBari);
            final TextView txtHH = (TextView) dialog.findViewById(R.id.txtHH);
            final TextView txtName = (TextView) dialog.findViewById(R.id.txtName);

            txtVill.setText(VILL);
            txtBari.setText(BARI);

            txtVill.setEnabled(false);
            txtBari.setEnabled(false);
            txtHH.setEnabled(false);

            txtHH.setText(HHSerial(VILL, BARI));

            Button cmdContactNoSave = (Button) dialog.findViewById(R.id.cmdSave);
            cmdContactNoSave.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {

                    if (txtName.getText().toString().length() == 0) {
                        Connection.MessageBox(Household_list.this, "Required field: খানা প্রধানের নাম");
                        txtName.requestFocus();
                        return;
                    }
                    //***
                    Household_DataModel objSave = new Household_DataModel();
                    objSave.setVill(txtVill.getText().toString());
                    objSave.setBari(txtBari.getText().toString());
                    objSave.setHH(txtHH.getText().toString());
                    objSave.setHHHead(txtName.getText().toString());

                    objSave.setEnDt(Global.DateTimeNowYMDHMS());
                    objSave.setStartTime(STARTTIME);
                    objSave.setEndTime(g.CurrentTime24());
                    objSave.setDeviceID(DEVICEID);
                    objSave.setEntryUser(ENTRYUSER); //from data entry user list
                    String status = objSave.SaveUpdateData(Household_list.this);

                    DataSearch(CLUSTER, BLOCK,txtVill.getText().toString(),txtBari.getText().toString());
                    //txtHH.setText(HHSerial(VILL,BARI));
                    //txtName.setText("");
                    //txtName.requestFocus();
                    dialog.dismiss();
                }
            });

            ImageButton cmdBack = (ImageButton) dialog.findViewById(R.id.cmdBack);
            cmdBack.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                }});

            dialog.show();
        } catch (Exception e) {
            Connection.MessageBox(Household_list.this, e.getMessage());
            return;
        }
    }
    private String HHSerial(String VILL, String BARI)
    {
        String H = C.ReturnSingleValue("Select (ifnull(max(cast(HH as int)),0)+1)serial from Household where Vill='" + VILL + "' and Bari='" + BARI + "'");
        H = Global.Right("0"+H,2);
        return H;
    }

 @Override
 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);
     if (resultCode == Activity.RESULT_CANCELED) {
         //Write your code if there's no result
     } else {
         if(data.getExtras().getString("res").equals("bari")) {
             //if (spnVill.getSelectedItemPosition() == 0) return;
             String[] V = Connection.split(spnVill.getSelectedItem().toString(), '-');
             spnBari.setAdapter(C.getArrayAdapter("Select '.All Bari' union Select Bari||'-'||BariName from Baris where Vill='"+ V[0] +"' and Cluster='"+ CLUSTER +"' and Block='"+ BLOCK +"'"));
             spnBari.setSelection(Global.SpinnerItemPositionAnyLength(spnBari,data.getExtras().getString("bid")));
         }else if(data.getExtras().getString("res").equals("hh")) {
             String V = spnVill.getSelectedItem().toString().split("-")[0];
             String B = spnBari.getSelectedItem().toString().equalsIgnoreCase(".all bari")?"":spnBari.getSelectedItem().toString().split("-")[0];

             DataSearch(CLUSTER, BLOCK, V, B);
         }
     }
 }

 public void DataSearch(String Cluster, String Block, String Vill, String Bari)
     {
       try
        {
           Household_DataModel d = new Household_DataModel();
            String SQL ;

            SQL = "Select h.Vill, h.Bari,h.HH, Religion, MobileNo1, MobileNo2, HHHead, ifnull(TotMem,'0')TotMem,TotRWo, h.EnType, h.EnDate, h.ExType, h.ExDate, ifnull(h.Note,'')Note,h.Rnd,b.BariName,";
            SQL += " ifnull(v.VStatus,'') as vstatus,ifnull(v.vstatusoth,'') vstatusoth,ifnull(v.Resp,'') as resp";
            SQL += " from Baris b inner join Household h on b.Vill=h.Vill and b.Bari=h.Bari";
            SQL += " left outer join Visits v on h.Vill=v.Vill and h.Bari=v.Bari and h.HH=v.HH and (case when v.Resp='77' then '"+ ROUNDNO +"' else v.Rnd end)='"+ ROUNDNO +"'";
            SQL += " Where b.Cluster='"+ Cluster +"' and b.Block='"+ Block +"' and b.Vill='"+ Vill +"' and b.Bari Like('%"+ Bari +"%')";

            List<Household_DataModel> data = d.SelectAllVisit(this, SQL);
            dataList.clear();

            dataAdapter = null;

             ListView list = (ListView)findViewById(R.id.lstData);
             HashMap<String, String> map;
             Integer i = 0;
             int totalHH=0;
             for(Household_DataModel item : data){
                 map = new HashMap<String, String>();
                 map.put("Vill", item.getVill());
                 map.put("Bari", item.getBari());
                 map.put("HH", item.getHH());
                 map.put("Religion", item.getReligion());
                 map.put("MobileNo1", item.getMobileNo1());
                 map.put("MobileNo2", item.getMobileNo2());
                 map.put("HHHead", item.getHHHead());
                 map.put("TotMem", item.getTotMem());
                 map.put("TotRWo", item.getTotRWo());
                 map.put("EnType", item.getEnType());
                 map.put("EnDate", item.getEnDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getEnDate()));
                 map.put("ExType", item.getExType());
                 map.put("ExDate", item.getExDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getExDate()));
                 map.put("Rnd", item.getRnd());
                 map.put("Note", item.getNote());
                 map.put("sl", i.toString());

                 map.put("vstatus",item.getVStatus());
                 map.put("vstatusoth",item.getVStatusOth());
                 map.put("resp",item.getResp());

                 i+=1;
                 totalHH+=1;
                 dataList.add(map);
             }
             lblHeading.setText("খানার তালিকা (মোট খানা: "+totalHH +" )");
             dataAdapter = new SimpleAdapter(Household_list.this, dataList, R.layout.household_list,new String[] {"rowsec"},
                           new int[] {R.id.secListRow});
             list.setAdapter(new DataListAdapter(this, dataAdapter));
            //Utility.setListViewHeightBasedOnChildren(list);
        }
        catch(Exception  e)
        {
            Connection.MessageBox(Household_list.this, e.getMessage());
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
 	        convertView = inflater.inflate(R.layout.household_row, null);
         }
         LinearLayout   secListRow = (LinearLayout)convertView.findViewById(R.id.secListRow);

         final TextView Bari= (TextView) convertView.findViewById(R.id.Bari);
         final TextView BariN= (TextView) convertView.findViewById(R.id.BariN);
         final TextView HH     = (TextView)convertView.findViewById(R.id.HH);
         final TextView HHHead = (TextView)convertView.findViewById(R.id.HHHead);
         final TextView Visit  = (TextView)convertView.findViewById(R.id.Visit);
         final TextView VNote = (TextView)convertView.findViewById(R.id.VisitNote);
         final TextView TotMem = (TextView)convertView.findViewById(R.id.TotMem);

         final LinearLayout secRowVStatus = (LinearLayout)convertView.findViewById(R.id.secRowVStatus);
         final TextView lblVStatus = (TextView)convertView.findViewById(R.id.lblVStatus);

         final HashMap<String, String> o = (HashMap<String, String>) dataAdap.getItem(position);

         Bari.setText(o.get("Bari"));

         final String BName=C.ReturnSingleValue("Select BariName from Baris where Vill='"+  o.get("Vill")  +"' AND Bari='"+  o.get("Bari")  +"'");
         BariN.setText(BName);

         HH.setText(o.get("HH"));
         HHHead.setText(o.get("HHHead"));
         String VisitNote=C.ReturnSingleValue("Select Note from Visits where Vill='"+  o.get("Vill")  +"' AND Bari='"+  o.get("Bari")  +"' AND HH='"+ o.get("HH") +"'");
         VNote.setText(VisitNote);

         secRowVStatus.setVisibility(View.GONE);
         final String resp = o.get("resp").toString().length()==0?"":o.get("resp").toString();
         if(resp.length()==0){
             Bari.setTextColor(Color.RED);
             if(o.get("Note").toString().length()>0){
                 secRowVStatus.setVisibility(View.VISIBLE);
                 lblVStatus.setText(o.get("Note"));
             }
         }else if(Integer.parseInt(resp)>=1 & Integer.parseInt(resp)<=70){
             Bari.setTextColor(Color.GREEN);
             if(o.get("Note").toString().length()>0){
                 secRowVStatus.setVisibility(View.VISIBLE);
                 lblVStatus.setText(o.get("Note"));
             }
         }else if(resp.equals("00")){
             Bari.setTextColor(Color.GREEN);
             secRowVStatus.setVisibility(View.VISIBLE);
             lblVStatus.setText("অনিবার্য পরিস্থিতির কারণে পরিদর্শন করা হয়নি, "+ o.get("Note"));
         }else if(resp.equals("77")){
             Bari.setTextColor(Color.GREEN);
             secRowVStatus.setVisibility(View.VISIBLE);
             lblVStatus.setText("সমগ্র পরিবার অন্যত্র  চলেগেছে, "+ o.get("Note"));
         }else if(resp.equals("88")){
             Bari.setTextColor(Color.GREEN);
             secRowVStatus.setVisibility(View.VISIBLE);
             lblVStatus.setText("ইন্টারভিউ দিতে রাজী নয়, "+ o.get("Note"));
         }else if(resp.equals("99")){
             Bari.setTextColor(Color.GREEN);
             secRowVStatus.setVisibility(View.VISIBLE);
             lblVStatus.setText("খানার সকল সদস্য অনুপস্থিত, "+ o.get("Note"));
         }else if(o.get("TotMem").equals("0")||o.get("TotMem").length()==0){
             secRowVStatus.setVisibility(View.VISIBLE);
             lblVStatus.setText("বেজলাইনে অনুপস্থিত");
         }

         TotMem.setText(o.get("TotMem"));

         if(Integer.valueOf(o.get("sl"))%2==0) {
             secListRow.setBackgroundColor(Color.parseColor("#F3F3F3"));
         }
         else {
             secListRow.setBackgroundColor(Color.parseColor("#FFFFFF"));
         }

         secListRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               //Write your code here
               Bundle IDbundle = new Bundle();
               IDbundle.putString("Vill", o.get("Vill"));
               IDbundle.putString("Bari", o.get("Bari"));
               IDbundle.putString("HH", o.get("HH"));
               IDbundle.putString("HHHead",o.get("HHHead"));
               IDbundle.putString("BariName",BName);
               IDbundle.putString("roundno",ROUNDNO);
               IDbundle.putString("cluster",CLUSTER);
               IDbundle.putString("block",BLOCK);
               IDbundle.putString("OldNew", "old");
                IDbundle.putString("resp", resp);
               IDbundle.putString("totalmem", o.get("TotMem").length()==0?"0":o.get("TotMem"));
               Intent f1;
               f1 = new Intent(getApplicationContext(), Household_Visit.class);
               f1.putExtras(IDbundle);
               startActivityForResult(f1, 1);
            }
          });

//         delHousehold.setOnClickListener(new View.OnClickListener() {
//             public void onClick(View v) {
//                 AlertDialog.Builder adb = new AlertDialog.Builder(Household_list.this);
//                 adb.setTitle("মুছে ফেলা");
//                 adb.setMessage("আপনি কি খানা প্রধান "+ o.get("HHHead") +" এর খানার তথ্য  মুছে ফেলতে চান[হ্যাঁ/না]?");
//                 adb.setNegativeButton("না", null);
//                 adb.setPositiveButton("হ্যাঁ", new AlertDialog.OnClickListener() {
//                     public void onClick(DialogInterface dialog, int which) {
//                         C.Save("Delete from Household where Vill='" + o.get("Vill") + "' and Bari='" + o.get("Bari") + "' and HH='" + o.get("HH") + "'");
//                         DataSearch(CLUSTER, BLOCK,o.get("Bari"));
//                     }
//                 });
//                 adb.show();
//             }
//         });
         return convertView;
       }

 }

}