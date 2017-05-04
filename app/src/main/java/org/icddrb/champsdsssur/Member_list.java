package org.icddrb.champsdsssur;
//Android Manifest Code
 //<activity android:name=".Member_list" android:label="Member: List" />
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
 import android.text.Editable;
 import android.text.TextWatcher;
 import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
 import android.widget.AdapterView;
 import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
 import android.widget.ImageView;
 import android.widget.LinearLayout;
import android.widget.ListView;
 import android.widget.RadioGroup;
 import android.widget.SimpleAdapter;
 import android.widget.Spinner;
 import android.widget.TableLayout;
 import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Common.Connection;
import Common.Global;
import Common.Utility;
import Utility.*;

public class Member_list extends Activity {
    boolean networkAvailable=false;
    Location currentLocation; 
    double currentLatitude,currentLongitude;
    SimpleAdapter eList;
    SimpleAdapter mSchedule;
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
    ArrayList<HashMap<String, String>> mylist   = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> evmylist = new ArrayList<HashMap<String, String>>();
    static String TableName;

    TextView lblHeading;
    Button btnAdd;
    Button btnRefresh;

    EditText txtVill;
    EditText txtBari;
    EditText txtHH;

    static String DEVICEID  = "";
    static String ENTRYUSER = "";

    Bundle IDbundle;
    private static String CurrentVillage;
    private static String CurrentVCode;

    static String STARTTIME = "";
    static String VILL = "";
    static String BARI = "";
    static String BName = "";
    static String HH = "";
    static String MSLNO = "";
    static String ROUNDNO = "";
    static String CLUSTER = "";
    static String BLOCK   = "";

    Button btnMemberName;
    Button btnSES;
    Button btnPregHis;
    MySharedPreferences sp;

 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.member_list);
         C = new Connection(this);
         g = Global.getInstance();
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

         STARTTIME = g.CurrentTime24();
         DEVICEID  = g.getDeviceNo();
         ENTRYUSER = g.getUserId();

         IDbundle=getIntent().getExtras();
         VILL = IDbundle.getString("Vill");
         BARI = IDbundle.getString("Bari");
         BName=IDbundle.getString("BariName");
         HH = IDbundle.getString("HH");
         MSLNO = IDbundle.getString("MSlNo");

         sp = new MySharedPreferences();
         ROUNDNO = sp.getValue(this,"roundno");
         CLUSTER = sp.getValue(this,"cluster");
         BLOCK = sp.getValue(this,"block");

         /*ROUNDNO        = IDbundle.getString("roundno");
         CLUSTER        = IDbundle.getString("cluster");
         BLOCK          = IDbundle.getString("block");*/


         final TextView txtVill = (TextView) findViewById(R.id.txtVill);
         final TextView txtBari = (TextView) findViewById(R.id.txtBari);
         final TextView txtHH = (TextView) findViewById(R.id.txtHH);
         final TextView txtRnd = (TextView) findViewById(R.id.txtRnd);

         TextView lblHHHead=(TextView)findViewById(R.id.lblHHHead);
         lblHHHead.setText(": "+IDbundle.getString("HHHead"));

         TextView lblVillName=(TextView)findViewById(R.id.lblVillName);
         TextView lblBariName=(TextView)findViewById(R.id.lblBariName);

         final String VillName=C.ReturnSingleValue("Select VName from Village where VCode='"+  VILL  +"'");
         lblVillName.setText(": "+ VillName);
         lblBariName.setText(": "+BARI+", "+IDbundle.getString("BariName"));

//         txtVill.setText(VILL);
         txtBari.setText(BARI);
         txtHH.setText(HH);
         txtVill.setText(VILL+"-"+BARI+"-"+HH);
         txtRnd.setText(ROUNDNO);

         LinearLayout secMenu;
         secMenu= (LinearLayout) findViewById(R.id.secMenu);
         secMenu.setVisibility(View.VISIBLE);

         TableName = "tmpMember";
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 String infoMiss = C.ReturnSingleValue("Select count(*)TotalMiss from Member where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and length(Sex)=0");

                 if (Integer.valueOf(infoMiss) > 0) {
                     Connection.MessageBox(Member_list.this, infoMiss + " জন সদস্যের তথ্য আপডেট করা হয় নাই");
                     return;
                 }

                 if (!C.Existence("Select PNo from Member where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'")) {
                     Connection.MessageBox(Member_list.this, "কমপক্ষে একজন সদস্য এন্ট্রি করতে হবে.");
                     return;
                 }
                 if(!C.Existence("Select Rth from Member  where Rth='01' and Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH +"'"))
                 {
                     Connection.MessageBox(Member_list.this, "খানায় কমপক্ষে একজন খানা প্রধান থাকতে হবে।");
                     return;
                 }
                 if (!C.Existence("Select VStatus from SES where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'")) {
                     Connection.MessageBox(Member_list.this, "Required: খানার তথ্য  খালি রাখা যাবেনা ");
                     return;
                 }
                 AlertDialog.Builder adb = new AlertDialog.Builder(Member_list.this);
                 adb.setTitle("Close");
                 adb.setMessage("আপনি কি এই ফরম থেকে বের হতে চান [হ্যাঁ/না]?");
                 adb.setNegativeButton("না", null);
                 adb.setPositiveButton("হ্যাঁ", new AlertDialog.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         /*Bundle IDbundle = new Bundle();
                         IDbundle.putString("Vill", VILL);
                         IDbundle.putString("Bari", BARI);
                         IDbundle.putString("HH", HH);
                         *//*
                         Intent intent = new Intent(getApplicationContext(), Household_list.class);
                         intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                         //intent.putExtras(IDbundle);
                         getApplicationContext().startActivity(intent);
                         finish();
//                        startActivity(new Intent(Member_list.this, Household_list.class));
                         */
                         Intent returnIntent = new Intent();
                         returnIntent.putExtra("res", "hh");
                         setResult(Activity.RESULT_OK, returnIntent);
                         finish();
                     }});
                 adb.show();
             }});

         btnRefresh = (Button) findViewById(R.id.btnRefresh);
         btnRefresh.setOnClickListener(new View.OnClickListener() {

             public void onClick(View view) {
                   //write your code here
                 DataSearch(VILL, BARI, HH);
             }});

         btnSES = (Button) findViewById(R.id.btnSES);
         btnSES.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (!C.Existence("Select PNo from Member where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'")) {
                     Connection.MessageBox(Member_list.this, "Required: কমপক্ষে একজন সদস্য এন্ট্রি করতে হবে.");
                     return;
                 }
                 if(!C.Existence("Select Rth from Member  where Rth='01' and Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH +"'"))
                 {
                     Connection.MessageBox(Member_list.this, "খানায় কমপক্ষে একজন খানা প্রধান থাকতে হবে।");
                     return;
                 }
                 String infoMiss = C.ReturnSingleValue("Select count(*)TotalMiss from Member where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and length(Sex)=0");

                 if (Integer.valueOf(infoMiss) > 0) {
                     Connection.MessageBox(Member_list.this, infoMiss + " জন সদস্যের তথ্য আপডেট করা হয় নাই");
                     return;
                 }

//                 Toast.makeText(Member_list.this, "Vill:"+VILL+"/n Bari:"+BARI+"/n HH:"+HH, Toast.LENGTH_SHORT).show();
                 Intent f1;
                 f1 = new Intent(getApplicationContext(), SES.class);
                 IDbundle.putString("Vill", VILL);
                 IDbundle.putString("Bari", BARI);
                 IDbundle.putString("HH", HH);
                 f1.putExtras(IDbundle);
                 startActivityForResult(f1, 1);

             }
         });
         btnPregHis = (Button) findViewById(R.id.btnPregHis);
         btnPregHis.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (!C.Existence("Select PNo from Member where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'")) {
                     Connection.MessageBox(Member_list.this, "Required: কমপক্ষে একজন সদস্য এন্ট্রি করতে হবে.");
                     return;
                 }
                 if(!C.Existence("Select Rth from Member  where Rth='01' and Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH +"'"))
                 {
                     Connection.MessageBox(Member_list.this, "খানায় কমপক্ষে একজন খানা প্রধান থাকতে হবে।");
                     return;
                 }

                 if(!C.Existence("Select Sex from Member  where Sex='2' and Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH +"'"))
                 {
                     Connection.MessageBox(Member_list.this, "খানায় কমপক্ষে একজন মহিলা সদস্য এন্ট্রি করতে হবে.");
                     return;
                 }

                 if(!C.Existence("Select Sex, AgeY,MS from Member Where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH + "' and (julianday('now')-julianday(BDate))<=18262 and Sex='2' and MS<>'30'"))
                 {
                     Connection.MessageBox(Member_list.this, "খানায় ৫০ বছরের কম বয়সের কখনও বিবাহ হয়েছে এমন মহিলা নেই .");
                     return;
                 }
                 String infoMiss = C.ReturnSingleValue("Select count(*)TotalMiss from Member where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and length(Sex)=0");

                 if (Integer.valueOf(infoMiss) > 0) {

                     Connection.MessageBox(Member_list.this, infoMiss + " জন সদস্যের তথ্য আপডেট করা হয় নাই");

                     return;
                 }
//                 Toast.makeText(Member_list.this, "Vill:"+VILL+"/Bari:"+BARI+"/HH:"+HH, Toast.LENGTH_SHORT).show();
                 Intent f1;
                 f1 = new Intent(getApplicationContext(), PregHis.class);
                 IDbundle.putString("Vill", VILL);
                 IDbundle.putString("Bari", BARI);
                 IDbundle.putString("HH", HH);
                 f1.putExtras(IDbundle);
                 startActivityForResult(f1, 1);

             }
         });
         btnMemberName = (Button) findViewById(R.id.btnMemberName);
         btnMemberName.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent f1;
                 f1 = new Intent(getApplicationContext(),Events_NewMem.class);
                 IDbundle.putString("Vill", VILL);
                 IDbundle.putString("Bari", BARI);
                 IDbundle.putString("HH", HH);
                 IDbundle.putString("roundno",ROUNDNO);
                 IDbundle.putString("OldNew", "new");
                 f1.putExtras(IDbundle);
                 startActivityForResult(f1, 1);
             }

         });
         Button cmdEvList = (Button)findViewById(R.id.cmdEvList);
         Button cmdVisitList = (Button)findViewById(R.id.cmdVisitList);

//         final RadioGroup roMemberOption =(RadioGroup)findViewById(R.id.roMemberOption);
//         roMemberOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//             public void onCheckedChanged(RadioGroup arg0, int id) {
//                 if(id == R.id.roActiveMember)
//                 {
//                     DataRetrieve(VILL+BARI+HH, false,"active");
//                 }
//                 else if(id == R.id.roAllMember)
//                 {
//                     DataRetrieve(VILL+BARI+HH, false,"all");
//                 }
//             }});

//         DataRetrieve(VILL+BARI+HH, true,"active");

         cmdEvList.setOnClickListener(new View.OnClickListener() {
             public void onClick(View arg0) {
                 ShowEventList(VILL,BARI,HH);
                 //Intent f11 = new Intent(getApplicationContext(),EventList.class);
                 //startActivity(f11);
             }
         });

         cmdVisitList.setOnClickListener(new View.OnClickListener() {
             public void onClick(View arg0) {
                 VisitList(VILL,BARI,HH);
             }
         });

         txtVill.setEnabled(false);
         txtBari.setEnabled(false);
         txtHH.setEnabled(false);
         txtRnd.setEnabled(false);

         DataSearch(VILL,BARI,HH);
         DataStatus();
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
         DataSearch(VILL,BARI,HH);
         DataStatus();
     }
 }

    //***************************************************************************************************************************
    private void VisitList(final String Vill, final String Bari, final String HH)
    {
        try
        {
            final Dialog dialog = new Dialog(Member_list.this);

            dialog .requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.visitlist);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);

            Window window = dialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();

            wlp.gravity = Gravity.TOP;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);

            final ListView evlist = (ListView)dialog.findViewById(R.id.lstVisit);
            View header = getLayoutInflater().inflate(R.layout.visitlistheading, null);
            evlist.addHeaderView(header);
            Cursor cur1 = C.ReadData("select Rnd , Resp, VDate from Visits Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"' order by cast(Rnd as int) desc");

            cur1.moveToFirst();
            evmylist.clear();
            eList = null;
            evlist.setAdapter(null);

            while(!cur1.isAfterLast())
            {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("rnd", cur1.getString(cur1.getColumnIndex("Rnd")));
                map.put("Resp", cur1.getString(cur1.getColumnIndex("Resp")));
                map.put("VDate", Global.DateConvertDMY(cur1.getString(cur1.getColumnIndex("VDate"))));

                evmylist.add(map);

                eList = new SimpleAdapter(Member_list.this, evmylist, R.layout.visitlistrow,
                        new String[] {"rnd","Resp","VDate"},
                        new int[] {R.id.v_rnd,R.id.v_rsno,R.id.v_vdate});
                evlist.setAdapter(eList);

                cur1.moveToNext();
            }
            cur1.close();

            Button cmdVisitListClose = (Button)dialog.findViewById(R.id.cmdVisitListClose);
            cmdVisitListClose.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
        catch(Exception  e)
        {
            Connection.MessageBox(Member_list.this, e.getMessage());
            return;
        }
    }

    private void ShowEventList(final String VILL, final String BARI, final String HH)
    {
        try
        {
            final Dialog dialog = new Dialog(Member_list.this);
            dialog.setTitle("ঘটনার তালিকা");
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.eventlistpopup);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);

            Window window = dialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();

            wlp.gravity = Gravity.TOP;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);


            final ListView evlist = (ListView)dialog.findViewById(R.id.lstEvent);
            View header = getLayoutInflater().inflate(R.layout.eventlistheading, null);
            evlist.addHeaderView(header);

            final RadioGroup roEventList =(RadioGroup)dialog.findViewById(R.id.roEventList);
            roEventList.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup arg0, int id) {
                    if(id == R.id.roCurrentEvent)
                    {
                        EventDataList(dialog, VILL+BARI+HH, "current",evlist);
                    }
                    else if(id == R.id.roAllEvent)
                    {
                        EventDataList(dialog, VILL+BARI+HH, "all",evlist);
                    }
                }});
            EventDataList(dialog, VILL+BARI+HH, "current",evlist);



            Button cmdEvListClose = (Button)dialog.findViewById(R.id.cmdEvListClose);
            cmdEvListClose.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
        catch(Exception  e)
        {
            Connection.MessageBox(Member_list.this, e.getMessage());
            return;
        }
    }

    private void EventDataList(Dialog d, String Household,String Status, ListView evList)
    {
        Cursor cur1 = null;

        if(Status.equals("current"))
        {
            cur1 = C.ReadData("select mslno,pno as pno,evtype,evdate,ifnull(info1,'')info1,ifnull(info2,'')info2,ifnull(info3,'')info3,ifnull(info4,'')info4,Rnd from tmpEvents where Vill||Bari||HH='"+ Household +"' order by   MslNo,Rnd,evtype");
        }
        else if(Status.equals("all"))
        {
            cur1 = C.ReadData("select mslno,pno as pno,evtype,evdate,ifnull(info1,'')info1,ifnull(info2,'')info2,ifnull(info3,'')info3,ifnull(info4,'')info4,Rnd from Events where Vill||Bari||HH='"+ Household +"' order by   MslNo,Rnd,evtype");
        }

        if(cur1.getCount()==0)
        {
            evList.setAdapter(null);
            cur1.close();
            return;
        }

        cur1.moveToFirst();
        evmylist.clear();

        int i=0;

        while(!cur1.isAfterLast())
        {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("mslno", cur1.getString(cur1.getColumnIndex("MSlNo")));
            map.put("pno", cur1.getString(cur1.getColumnIndex("pno")));
            map.put("evtype", cur1.getString(cur1.getColumnIndex("EvType")));
            map.put("evdate", cur1.getString(cur1.getColumnIndex("EvDate")));
            map.put("info1", cur1.getString(cur1.getColumnIndex("info1")));
            map.put("info2", cur1.getString(cur1.getColumnIndex("info2")));
            map.put("info3", cur1.getString(cur1.getColumnIndex("info3")));
            map.put("info4", cur1.getString(cur1.getColumnIndex("info4")));
            map.put("rnd", cur1.getString(cur1.getColumnIndex("Rnd")));
            map.put("status", Status);
            evmylist.add(map);

            eList = new SimpleAdapter(Member_list.this, evmylist, R.layout.eventlistrow,
                    new String[] {"mslno"},
                    new int[] {R.id.e_MslNo});
            evList.setAdapter(new EventListAdapter(this,d,evList));
            i+=1;
            cur1.moveToNext();
        }
        cur1.close();
    }

    public class EventListAdapter extends BaseAdapter
    {
        private Context context;
        Dialog dg;
        ListView lv;
        public EventListAdapter(Context c,Dialog d,ListView evlist){
            context = c;
            dg=d;
            lv=evlist;
        }

        public int getCount() {
            return eList.getCount();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }


        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.eventlistrow, null);
            }

            final HashMap<String, String> o = (HashMap<String, String>) eList.getItem(position);

            TextView e_MslNo=(TextView)convertView.findViewById(R.id.e_MslNo);
            TextView e_evtype=(TextView)convertView.findViewById(R.id.e_evtype);
            TextView e_evdate=(TextView)convertView.findViewById(R.id.e_evdate);
            TextView e_info1=(TextView)convertView.findViewById(R.id.e_info1);
            TextView e_info2=(TextView)convertView.findViewById(R.id.e_info2);
            TextView e_info3=(TextView)convertView.findViewById(R.id.e_info3);
            TextView e_info4=(TextView)convertView.findViewById(R.id.e_info4);
            TextView e_round=(TextView)convertView.findViewById(R.id.e_round);

            e_MslNo.setText(o.get("mslno").toString());
            e_evtype.setText(o.get("evtype").toString());
            e_evdate.setText(Global.DateConvertDMY(o.get("evdate").toString()));
            e_info1.setText(o.get("info1").toString());
            e_info2.setText(o.get("info2").toString());
            e_info3.setText(o.get("info3").toString());
            e_info4.setText(o.get("info4").toString().trim().length()==0?o.get("info4").toString():Global.DateConvertDMY(o.get("info4").toString()));
            e_round.setText(o.get("rnd"));

            Button cmdEvListDel = (Button)convertView.findViewById(R.id.cmdEvListDel);
            Button cmdEvListEdit = (Button)convertView.findViewById(R.id.cmdEvListEdit);

            cmdEvListDel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(Member_list.this);
                    adb.setTitle("Event Delete");
                    adb.setMessage("সদস্যের নাম্বারঃ "+ o.get("MslNo").toString() +" এবং ইভেন্ট কোডঃ "+ o.get("evtype").toString() +" কি মুছে ফেলতে চান [Yes/No]?");

                    adb.setNegativeButton("No", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog1, int which) {

                        }});

                    adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog1, int which) {
                            String HH = VILL+BARI+txtHH;
                            String SN = o.get("Mslno").toString();
                            String EV = o.get("evtype").toString();
                            String EVD= o.get("evdate").toString();

                            //event specific update
                            if(EV.equals("12"))
                            {
                                C.Save("Delete from tmpEvents where vill||bari||hh='"+ HH +"' and MSlNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"'");
                            }
                            else if(EV.equals("21") | EV.equals("22") | EV.equals("23"))
                            {

                                C.Save("Delete from tmpMember where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"'");
                                C.Save("Delete from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"'");
                            }
                            else if(EV.equals("25"))
                            {

                            }
                            else if(EV.equals("31") | EV.equals("32") | EV.equals("33") | EV.equals("34"))
                            {
                                String PMS = C.ReturnSingleValue("Select Info2 from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"'");
                                C.Save("delete from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"'");
                                C.Save("Update tmpMember set MS='"+ PMS +"' where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"'");
                            }
                            else if(EV.equals("40") | EV.equals("49"))
                            {
                                C.Save("Delete from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"'");
                            }
                            else if(EV.equals("41"))
                            {
                                //Check 42 event available or not
                                if(C.Existence("select vill from tmpEvents where EvType='42' and vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and info4='"+ EVD +"'"))
                                {
                                    Connection.MessageBox(Member_list.this, "এই গর্ভের ডেলিভারি হয়ে গেছে, প্রথমে ইভেন্ট ৪২ মুছতে হবে এবং তারপর ৪১। ।।  .");
                                    return;
                                }

                                //Update from temporary table
                                C.Save("delete from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"'");
                                C.Save("Update tmpMember set PStat='',LMPDt='' where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"'");

                                //Transfer events from main to UpdateEvents table
//                                String SQL = "Insert into UpdateEvents(Vill, Bari, Hh, Pno, Sno, EvType, EvDate, Info1, Info2, Info3, Info4, Vdate, Rnd, Upload)";
//                                SQL += "Select Vill, Bari, Hh, Pno, Sno, EvType, EvDate, Info1, Info2, Info3, Info4, Vdate, Rnd, Upload from Events where";
//                                SQL += " Vill||Bari||HH = '"+ HH +"' and ";
//                                SQL += " SNo = '"+ SN +"' and EvType='41' and EvDate='"+ EVD +"'";
//                                C.Save(SQL);

                                //Update from main table
                                C.Save("delete from Events where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='41' and EvDate='"+ EVD +"'");
                                C.Save("Update Member set PStat='',LMPDt='' where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"'");

                            }
                            else if(EV.equals("42"))
                            {
                                String LMP = o.get("info4");
                            }
                            else if(EV.equals("51") | EV.equals("52") | EV.equals("53") | EV.equals("55"))
                            {
                                C.Save("delete from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"'");
                                C.Save("Update tmpMember set ExType='',ExDate='' where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"'");
                            }
                            else if(EV.equals("54") | EV.equals("57"))
                            {
                                C.Save("delete from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"'");
                                C.Save("Update tmpMember set PosMig='',PosMigDate='' where  vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"'");
                            }
                            else if(EV.equals("61"))
                            {
                                String PMono = C.ReturnSingleValue("Select Info2 from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"'");
                                C.Save("delete from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"'");
                                C.Save("Update tmpMember set MoNo='"+ PMono +"' where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"'");
                            }
                            else if(EV.equals("62"))
                            {
                                String PFano = C.ReturnSingleValue("Select Info2 from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"'");
                                C.Save("delete from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"'");
                                C.Save("Update tmpMember set FaNo='"+ PFano +"' where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"'");
                            }
                            else if(EV.equals("63"))
                            {

                            }
                            else if(EV.equals("64"))
                            {
                                String PRth = C.ReturnSingleValue("Select Info2 from tmpEvents where vill||bari||hh='"+ HH +"' and Mslno='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"'");
                                C.Save("delete from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"'");
                                C.Save("Update tmpMember set Rth='"+ PRth +"' where vill||bari||hh='"+ HH +"' and Mslno='"+ SN +"'");
                            }
                            else if(EV.equals("71"))
                            {
                                String PEdu = C.ReturnSingleValue("Select Info2 from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"'");
                                C.Save("delete from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"'");
                                C.Save("Update tmpMember set Edu='"+ PEdu +"' where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"'");
                            }
                            else if(EV.equals("72"))
                            {
                                String POcp = C.ReturnSingleValue("Select Info2 from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"'");
                                C.Save("delete from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"'");
                                C.Save("Update tmpMember set Ocp='"+ POcp +"' where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"'");
                            }

                            EventDataList(dg, HH, "current",lv);
                            DataRetrieve(VILL+BARI+HH,false,"active");
                        }});

                    adb.show();
                }
            });

            cmdEvListEdit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(Member_list.this);
                    adb.setTitle("Event Update");
                    adb.setMessage("সদস্যের নাম্বারঃ "+ o.get("MslNo").toString() +" এবং ইভেন্ট কোডঃ "+ o.get("evtype").toString() +" কি আপডেট করতে চান [Yes/No]?");

                    adb.setNegativeButton("No", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog1, int which) {

                        }});

                    adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog1, int which) {
                            //Show event update form
//                            UpdateEventForm(o.get("evtype"),o.get("evdate"), VILL, BARI, HH, o.get("MslNo"),o.get("pno"),lv,o.get("info3"));

                        }});
                    adb.show();
                }});


            if(o.get("status").equals("current"))
            {
                cmdEvListDel.setEnabled(true);
                cmdEvListEdit.setEnabled(false);
            }
            else
            {
                if(o.get("evtype").toString().equals("41"))
                {
                    cmdEvListDel.setEnabled(true);
                    cmdEvListEdit.setEnabled(true);
                }
                else if(o.get("evtype").toString().equals("31"))
                {
                    cmdEvListDel.setEnabled(false);
                    cmdEvListEdit.setEnabled(true);
                }
                else if(o.get("evtype").toString().equals("32"))
                {
                    cmdEvListDel.setEnabled(false);
                    cmdEvListEdit.setEnabled(true);
                }
                else if(o.get("evtype").toString().equals("33"))
                {
                    cmdEvListDel.setEnabled(false);
                    cmdEvListEdit.setEnabled(true);
                }
                else if(o.get("evtype").toString().equals("34"))
                {
                    cmdEvListDel.setEnabled(false);
                    cmdEvListEdit.setEnabled(true);
                }
                else
                {
                    cmdEvListDel.setEnabled(false);
                    cmdEvListEdit.setEnabled(false);
                }
            }

            return convertView;
        }
    }

    //Retrieve member list
    //***************************************************************************************************************************
    public void DataRetrieve(String HH, Boolean heading, String ActiveOrAll)
    {
        try
        {
            String SQLStr = "";

            if(ActiveOrAll.equalsIgnoreCase("active"))
            {
                SQLStr = "Select  (case when m.vill is null then 'n' else 'o' end)as NewOld, t.Vill, t.Bari, t.Hh, t.mslno, t.Pno, t.Name, t.Rth, t.Sex, t.BDate, Cast(((julianday(date('now'))-julianday(t.BDate))/365.25) as int) as Age,Cast(((julianday(t.ExDate)-julianday(t.BDate))/365.25) as int) as DeathAge, t.Mono, t.Fano, t.Edu,";
                SQLStr += " t.Ms, t.Pstat, t.LmpDt, t.Sp1, t.Sp2, t.Sp3, t.Sp4, t.Ocp, t.EnType, t.EnDate,";
                SQLStr += " (case when cast(strftime('%Y', ifnull(t.ExDate,'')) as int)>=2014 and t.ExType='55' then '1' else '2' end)as deathrep,";
                SQLStr += " ifnull(t.ExType,'')ExType,ifnull(t.ExDate,'')ExDate,cast(strftime('%Y', ifnull(t.ExDate,'')) as int)ExYear,ifnull(t.PosMig,'')PosMig,ifnull(t.PosMigDate,'')PosMigDate from tmpMember t";
                SQLStr += " left outer join Member m on t.vill||t.bari||t.hh||t.MslNo = m.vill||m.bari||m.hh||m.MslNo";
                SQLStr += " where t.vill||t.bari||t.hh='"+ HH +"' and (length(t.extype)=0 or t.extype is null) order by cast(t.MslNo as int) asc";
            }
            else if(ActiveOrAll.equalsIgnoreCase("all"))
            {
                SQLStr = "Select  (case when m.vill is null then 'n' else 'o' end)as NewOld, t.Vill, t.Bari, t.Hh, t.mslno, t.Pno, t.Name, t.Rth, t.Sex, t.BDate, Cast(((julianday(date('now'))-julianday(t.BDate))/365.25) as int) as Age, Cast(((julianday(t.ExDate)-julianday(t.BDate))/365.25) as int) as DeathAge, t.Mono, t.Fano, t.Edu,";
                SQLStr += " t.Ms, t.Pstat, t.LmpDt, t.Sp1, t.Sp2, t.Sp3, t.Sp4, t.Ocp, t.EnType, t.EnDate,";
                SQLStr += " (case when cast(strftime('%Y', ifnull(t.ExDate,'')) as int)>=2014 and t.ExType='55' then '1' else '2' end)as deathrep,";
                SQLStr += " ifnull(t.ExType,'')ExType,ifnull(t.ExDate,'')ExDate,cast(strftime('%Y', ifnull(t.ExDate,'')) as int)ExYear,ifnull(t.PosMig,'')PosMig,ifnull(t.PosMigDate,'')PosMigDate from tmpMember t";
                SQLStr += " left outer join member m on t.vill||t.bari||t.hh||t.MslNo = m.vill||m.bari||m.hh||m.MslNo";
                SQLStr += " where t.vill||t.bari||t.hh='"+ HH +"' order by cast(t.MslNo as int) asc";
            }


            Cursor cur1 = C.ReadData(SQLStr);

            cur1.moveToFirst();
            mylist.clear();

            ListView list = (ListView) findViewById(R.id.lstData);
//            if(heading ==true)
//            {
//                View header = getLayoutInflater().inflate(R.layout.memberheading, null);
//                list.addHeaderView(header);
//            }

            int i=0;
            while(!cur1.isAfterLast())
            {
                HashMap<String, String> map = new HashMap<String, String>();

                if(i==0)
                {
                    //View header = getLayoutInflater().inflate(R.layout.membereventsheading, null);
                    //list.addHeaderView(header);
                }
                map.put("newold", cur1.getString(cur1.getColumnIndex("NewOld")));
                map.put("vill", cur1.getString(cur1.getColumnIndex("Vill")));
                map.put("bari", cur1.getString(cur1.getColumnIndex("Bari")));
                map.put("hh", cur1.getString(cur1.getColumnIndex("Hh")));
                map.put("mslno", cur1.getString(cur1.getColumnIndex("MslNo")));
                map.put("pno", cur1.getString(cur1.getColumnIndex("Pno")));
                map.put("name", cur1.getString(cur1.getColumnIndex("Name")));
                map.put("rth", cur1.getString(cur1.getColumnIndex("Rth")));
                map.put("sex", cur1.getString(cur1.getColumnIndex("Sex")));
                map.put("dob", cur1.getString(cur1.getColumnIndex("BDate")));
                map.put("age", cur1.getString(cur1.getColumnIndex("Age")));
                map.put("mono", cur1.getString(cur1.getColumnIndex("Mono")));
                map.put("fano", cur1.getString(cur1.getColumnIndex("Fano")));
                map.put("edu", cur1.getString(cur1.getColumnIndex("Edu")));
                map.put("ms", cur1.getString(cur1.getColumnIndex("Ms")));
                map.put("pstat", cur1.getString(cur1.getColumnIndex("Pstat")));
                map.put("lmpdt", cur1.getString(cur1.getColumnIndex("LmpDt")));
                map.put("ocp", cur1.getString(cur1.getColumnIndex("Ocp")));
                map.put("sp1", cur1.getString(cur1.getColumnIndex("Sp1")));
                map.put("sp2", cur1.getString(cur1.getColumnIndex("Sp2")));
                map.put("sp3", cur1.getString(cur1.getColumnIndex("Sp3")));
                map.put("sp4", cur1.getString(cur1.getColumnIndex("Sp4")));
                map.put("entype", cur1.getString(cur1.getColumnIndex("EnType")));
                map.put("endate", cur1.getString(cur1.getColumnIndex("EnDate")));
                map.put("extype", cur1.getString(cur1.getColumnIndex("ExType")));
                map.put("exdate", cur1.getString(cur1.getColumnIndex("ExDate")));
                map.put("posmig", cur1.getString(cur1.getColumnIndex("PosMig")));
                map.put("posmigdate", cur1.getString(cur1.getColumnIndex("PosMigDate")));
                map.put("exyear", cur1.getString(cur1.getColumnIndex("ExYear")));
                map.put("deathage", cur1.getString(cur1.getColumnIndex("DeathAge")));
                map.put("deathrep", cur1.getString(cur1.getColumnIndex("deathrep")));

                mylist.add(map);
                mSchedule = new SimpleAdapter(Member_list.this, mylist, R.layout.memberheading,new String[] {"mslno","name","pno","rth","sex","dob","age","mono","fano","edu","ms","pstat","lmpdt","ocp","sp1","sp2","sp3","sp4","entype","endate","extype","exdate","posmig","posmigdate"},
                        new int[] {R.id.MSlNo,R.id.name,R.id.pno,R.id.rth,R.id.sex,R.id.bdate,R.id.AgeY,R.id.mono,R.id.fano,R.id.edu,R.id.ms,R.id.pstat,R.id.lmpdt,R.id.ocp,R.id.sp1,R.id.sp2,R.id.sp3,R.id.sp4,R.id.entype,R.id.endate,R.id.extype,R.id.exdate,R.id.posmig,R.id.posmigdate});
                list.setAdapter(new MemberListAdapter(this));

                i+=1;
                cur1.moveToNext();
            }
            cur1.close();


        }
        catch(Exception  e)
        {
            Connection.MessageBox(Member_list.this, e.getMessage());
        }
    }

    public class MemberListAdapter extends BaseAdapter
    {
        private Context context;

        public MemberListAdapter(Context c){
            context = c;
        }

        public int getCount() {
            return mSchedule.getCount();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }


        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.memberheading, null);
            }
            TextView mslno= (TextView) convertView.findViewById(R.id.MSlNo);
            TextView name= (TextView) convertView.findViewById(R.id.name);
            TextView pno= (TextView) convertView.findViewById(R.id.pno);
            TextView rth= (TextView) convertView.findViewById(R.id.rth);
            TextView sex= (TextView) convertView.findViewById(R.id.sex);
            TextView bdate= (TextView) convertView.findViewById(R.id.bdate);
            TextView age= (TextView) convertView.findViewById(R.id.AgeY);
            TextView mono= (TextView) convertView.findViewById(R.id.mono);
            TextView fano= (TextView) convertView.findViewById(R.id.fano);
            TextView edu= (TextView) convertView.findViewById(R.id.edu);
            TextView ms= (TextView) convertView.findViewById(R.id.ms);
            TextView pstat= (TextView) convertView.findViewById(R.id.pstat);
            TextView lmpdt= (TextView) convertView.findViewById(R.id.lmpdt);
            TextView ocp= (TextView) convertView.findViewById(R.id.ocp);
            TextView sp1= (TextView) convertView.findViewById(R.id.sp1);
            TextView sp2= (TextView) convertView.findViewById(R.id.sp2);
            TextView sp3= (TextView) convertView.findViewById(R.id.sp3);
            TextView sp4= (TextView) convertView.findViewById(R.id.sp4);
            TextView entype= (TextView) convertView.findViewById(R.id.entype);
            TextView endate= (TextView) convertView.findViewById(R.id.endate);
            TextView extype= (TextView) convertView.findViewById(R.id.extype);
            TextView exdate= (TextView) convertView.findViewById(R.id.exdate);
            TextView posmig= (TextView) convertView.findViewById(R.id.posmig);
            TextView posmigdate= (TextView) convertView.findViewById(R.id.posmigdate);

            final ImageButton delMember = (ImageButton) convertView.findViewById(R.id.delMember);

            final HashMap<String, String> o = (HashMap<String, String>) mSchedule.getItem(position);

            mslno.setText(o.get("mslno"));
            name.setText(o.get("name"));
            pno.setText(o.get("pno"));
            rth.setText(o.get("rth"));
            sex.setText(o.get("sex"));
            bdate.setText(Global.DateConvertDMY(o.get("dob")));
            age.setText(o.get("age"));
            mono.setText(o.get("mono"));
            fano.setText(o.get("fano"));
            edu.setText(o.get("edu"));
            ms.setText(o.get("ms"));
            pstat.setText(o.get("pstat"));
            if(o.get("lmpdt")==null | o.get("lmpdt").trim().length()==0)
                lmpdt.setText(o.get("lmpdt"));
            else
                lmpdt.setText(Global.DateConvertDMY(o.get("lmpdt")));
            ocp.setText(o.get("ocp"));
            sp1.setText(o.get("sp1"));
            sp2.setText(o.get("sp2"));
            sp3.setText(o.get("sp3"));
            sp4.setText(o.get("sp4"));
            entype.setText(o.get("entype"));
            endate.setText(Global.DateConvertDMY(o.get("endate")));
            extype.setText(o.get("extype"));
            if(o.get("exdate")==null | o.get("exdate").trim().length()==0)
                exdate.setText(o.get("exdate"));
            else
                exdate.setText(Global.DateConvertDMY(o.get("exdate")));

            if (o.get("PNo").length() == 0)
            {
                mslno.setTextColor(Color.RED);
                name.setTextColor(Color.RED);
                delMember.setVisibility(View.VISIBLE);
            }
            else
            {
                mslno.setTextColor(Color.BLACK);
                mslno.setTextColor(Color.BLACK);
                delMember.setVisibility(View.INVISIBLE);
            }

            //show only if possible migration
            if(o.get("posmig").equals("54"))
                posmig.setText(o.get("posmig"));
            else
                posmig.setText("");

            if(o.get("posmigdate")==null | o.get("posmigdate").trim().length()==0)
                posmigdate.setText(o.get("posmigdate"));
            else
                posmigdate.setText(Global.DateConvertDMY(o.get("posmigdate")));

            if(o.get("extype").trim().length()==0 & (o.get("posmig").trim().length()==0 | !o.get("posmig").trim().equals("54")))
            {
                mslno.setTextColor(Color.BLACK);
                pno.setTextColor(Color.BLACK);
                name.setTextColor(Color.BLACK);
                rth.setTextColor(Color.BLACK);
                sex.setTextColor(Color.BLACK);
                bdate.setTextColor(Color.BLACK);
                mono.setTextColor(Color.BLACK);
                fano.setTextColor(Color.BLACK);
                edu.setTextColor(Color.BLACK);
                ms.setTextColor(Color.BLACK);
                pstat.setTextColor(Color.BLACK);
                lmpdt.setTextColor(Color.BLACK);
                sp1.setTextColor(Color.BLACK);
                sp2.setTextColor(Color.BLACK);
                sp3.setTextColor(Color.BLACK);
                sp4.setTextColor(Color.BLACK);
                ocp.setTextColor(Color.BLACK);
                entype.setTextColor(Color.BLACK);
                endate.setTextColor(Color.BLACK);
                extype.setTextColor(Color.BLACK);
                exdate.setTextColor(Color.BLACK);
            }
            else if(o.get("extype").trim().length()==0 & o.get("posmig").trim().equals("54"))
            {
                mslno.setTextColor(Color.BLUE);
                pno.setTextColor(Color.BLUE);
                name.setTextColor(Color.BLUE);
                rth.setTextColor(Color.BLUE);
                sex.setTextColor(Color.BLUE);
                bdate.setTextColor(Color.BLUE);
                mono.setTextColor(Color.BLUE);
                fano.setTextColor(Color.BLUE);
                edu.setTextColor(Color.BLUE);
                ms.setTextColor(Color.BLUE);
                pstat.setTextColor(Color.BLUE);
                lmpdt.setTextColor(Color.BLUE);
                sp1.setTextColor(Color.BLUE);
                sp2.setTextColor(Color.BLUE);
                sp3.setTextColor(Color.BLUE);
                sp4.setTextColor(Color.BLUE);
                ocp.setTextColor(Color.BLUE);
                entype.setTextColor(Color.BLUE);
                endate.setTextColor(Color.BLUE);
                extype.setTextColor(Color.BLUE);
                exdate.setTextColor(Color.BLUE);
                posmig.setTextColor(Color.BLUE);
                posmigdate.setTextColor(Color.BLUE);

            }
            else
            {
                mslno.setTextColor(Color.RED);
                pno.setTextColor(Color.RED);
                name.setTextColor(Color.RED);
                rth.setTextColor(Color.RED);
                sex.setTextColor(Color.RED);
                bdate.setTextColor(Color.RED);
                mono.setTextColor(Color.RED);
                fano.setTextColor(Color.RED);
                edu.setTextColor(Color.RED);
                ms.setTextColor(Color.RED);
                pstat.setTextColor(Color.RED);
                lmpdt.setTextColor(Color.RED);
                sp1.setTextColor(Color.RED);
                sp2.setTextColor(Color.RED);
                sp3.setTextColor(Color.RED);
                sp4.setTextColor(Color.RED);
                ocp.setTextColor(Color.RED);
                entype.setTextColor(Color.RED);
                endate.setTextColor(Color.RED);
                extype.setTextColor(Color.RED);
                exdate.setTextColor(Color.RED);
            }

            if(o.get("newold").equals("n"))
            {
                mslno.setTextColor(Color.GREEN);
                pno.setTextColor(Color.GREEN);
                name.setTextColor(Color.GREEN);
                rth.setTextColor(Color.GREEN);
                sex.setTextColor(Color.GREEN);
                bdate.setTextColor(Color.GREEN);
                mono.setTextColor(Color.GREEN);
                fano.setTextColor(Color.GREEN);
                edu.setTextColor(Color.GREEN);
                ms.setTextColor(Color.GREEN);
                pstat.setTextColor(Color.GREEN);
                lmpdt.setTextColor(Color.GREEN);
                sp1.setTextColor(Color.GREEN);
                sp2.setTextColor(Color.GREEN);
                sp3.setTextColor(Color.GREEN);
                sp4.setTextColor(Color.GREEN);
                ocp.setTextColor(Color.GREEN);
                entype.setTextColor(Color.GREEN);
                endate.setTextColor(Color.GREEN);
                extype.setTextColor(Color.GREEN);
                exdate.setTextColor(Color.GREEN);
            }

            if(o.get("extype").trim().equals("55"))
            {
                if(o.get("deathrep").equals("1"))
                {
                    name.setBackgroundColor(Color.RED);
                    name.setTextColor( Color.WHITE );
                }
                else
                {
                    name.setBackgroundColor(Color.WHITE);
                    name.setTextColor( Color.RED );
                }
            }


            final AlertDialog.Builder adb = new AlertDialog.Builder(Member_list.this);
            final TableLayout memtab = (TableLayout)convertView.findViewById(R.id.memtab);
            final EditText lblMslNo = (EditText)convertView.findViewById(R.id.txtMSlNo);
            final TextView lblName = (TextView)convertView.findViewById(R.id.lblName);



            memtab.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(o.get("extype").trim().length()==0)
                    {
                        if(o.get("posmigdate")==null | o.get("posmigdate").trim().length()==0)
                        {
//                            ShowEventForm(o.get("vill"),o.get("bari"),o.get("hh"),o.get("sno"),o.get("pno"),o.get("name"));
                        }
                        else
                        {
                            //21 May 2014
                            AlertDialog.Builder adb = new AlertDialog.Builder(Member_list.this);
                            adb.setTitle("Possible Migration");
                            adb.setMessage("আপনি কি ["+ o.get("name") +"]এর 54 Event এর তথ্য মুছে ফেলতে চান [Yes/No]?");

                            adb.setNegativeButton("No", new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog1, int which) {

                                }});

                            adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog1, int which) {
                                    C.Save("Update tmpMember Set PosMig='',PosMigDate='' where vill||bari||hh='"+ (o.get("vill")+o.get("bari")+o.get("hh")) +"' and MslNo='"+ o.get("MslNo") +"'");

                                    DataRetrieve((o.get("vill")+o.get("bari")+o.get("hh")),false,"active");
                                }});

                            adb.show();
                        }

                    }
                    else
                    {
                        if(o.get("extype").trim().equals("55") & Integer.valueOf(o.get("exyear"))>=2014)
                        {
                            if(Integer.valueOf(o.get("deathage"))>=13 & Integer.valueOf(o.get("deathage"))<=49 & o.get("sex").equals("2"))
                            {
                                g.setPregOnDeath("1");
                            }
                            else
                            {
                                g.setPregOnDeath("2");
                            }
                            g.setmemSlNo( o.get("MslNo") );
//                            Intent f = new Intent(getApplicationContext(),Death.class);
//                            startActivity( f );
                        }
                        else
                        {
                            Connection.MessageBox(Member_list.this, "সদস্য এ খানায় সক্রিয় নয়.");
                            return;
                        }
                    }
                }
            });

            //21 may 2016
//            ImageView imgPreg = (ImageView)convertView.findViewById(R.id.imgPreg);
//            if(o.get("pstat").equals("41"))
//                imgPreg.setVisibility(View.VISIBLE);
//            else
//                imgPreg.setVisibility(View.INVISIBLE);
//
            return convertView;
        }
    }

    private void DataStatus()
    {
        if (C.Existence("Select VStatus from SES where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'")) {
            btnSES.setBackgroundColor(Color.GREEN);
        }
        else{
            btnSES.setBackgroundResource(R.drawable.button_style);
        }

        String TotRh = C.ReturnSingleValue("Select Count(*)TotRWO from Member Where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH + "' and (julianday(EnDate)-julianday(BDate))<=18262 and Sex='2' and MS<>'30'");
        String PregHis = C.ReturnSingleValue("Select count(*)Total from PregHis Where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH + "'");

        if (Integer.valueOf(TotRh)>0 & Integer.valueOf(TotRh) == Integer.valueOf(PregHis))
        {
            btnPregHis.setBackgroundColor(Color.GREEN);
        }
        else{
            btnPregHis.setBackgroundResource(R.drawable.button_style);
        }
    }
 private void DataSearch(String Vill, String Bari, String HH )
     {
       try
        {
           Member_DataModel d = new Member_DataModel();
             String SQL = "Select * from "+ TableName +"  Where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"'";
             List<Member_DataModel> data = d.SelectAll(this, SQL);
             dataList.clear();

             dataAdapter = null;

             ListView list = (ListView)findViewById(R.id.lstData);
             HashMap<String, String> map;
            Integer i = 0;
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
                 map.put("Pstat", item.getPstat());
                 map.put("LmpDt", item.getLmpDt().toString().length()==0 ? "" : Global.DateConvertDMY(item.getLmpDt()));
                 map.put("Ocp", item.getOcp());
                 map.put("Sp1", item.getSp1());
                 map.put("Sp2", item.getSp2());
                 map.put("Sp3", item.getSp3());
                 map.put("Sp4", item.getSp4());
                 map.put("EnType", item.getEnType());
                 map.put("EnDate", item.getEnDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getEnDate()));
                 map.put("ExType", item.getExType());
                 map.put("ExDate", item.getExDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getExDate()));
                 map.put("PosMig", item.getPosMig());
                 map.put("PosMigDate", item.getPosMigDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getPosMigDate()));

                 map.put("sl", i.toString());
                 i+=1;
                 dataList.add(map);
             }
             dataAdapter = new SimpleAdapter(Member_list.this, dataList, R.layout.member_list,new String[] {"rowsec"},
                           new int[] {R.id.secListRow});
             list.setAdapter(new DataListAdapter(this, dataAdapter));
            Utility.setListViewHeightBasedOnChildren(list);
        }
        catch(Exception  e)
        {
            Connection.MessageBox(Member_list.this, e.getMessage());
            return;
        }
     }

     private void MemberNameForm(final String VILL, final String BARI , final String HH) {
         try {
             final Dialog dialog = new Dialog(Member_list.this);
             dialog.setTitle("Member Name Form");
             dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
             dialog.setContentView(R.layout.member_name);
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
             final TextView txtMSlNo = (TextView) dialog.findViewById(R.id.txtMSlNo);
             final TextView txtName = (TextView) dialog.findViewById(R.id.txtName);

             txtName.requestFocus();
             final  TextView txtNameHint=(TextView)dialog.findViewById(R.id.txtNameHint);

             txtVill.setText(VILL);
             txtBari.setText(BARI);
             txtHH.setText(HH);

             txtVill.setEnabled(false);
             txtBari.setEnabled(false);
             txtHH.setEnabled(false);
             txtMSlNo.setEnabled(false);

             txtMSlNo.setText(MemberSerial(VILL, BARI,HH));

             if(txtMSlNo.getText().toString().equals("01"))
             {
                 txtName.setText(IDbundle.getString("HHHead"));
                 txtNameHint.setVisibility(View.VISIBLE);
             }else
             {
                 txtNameHint.setVisibility(View.GONE);
             }


             Button cmdContactNoSave = (Button) dialog.findViewById(R.id.cmdSave);
             cmdContactNoSave.setOnClickListener(new View.OnClickListener() {
                 public void onClick(View arg0) {

                     if (txtName.getText().toString().length() == 0)
                     {
                         Connection.MessageBox(Member_list.this, "Required field: সদস্যের নাম খালি রাখা যাবেনা ।");
                         txtName.requestFocus();
                         return;
                     }

                     String TotalMember = C.ReturnSingleValue("Select TotMem from Household Where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH + "'");

                     if(txtMSlNo.getText().toString().equals(TotalMember))
                     {
                         AlertDialog.Builder adb = new AlertDialog.Builder(Member_list.this);
                         adb.setTitle("Close");
                         adb.setMessage("খানা নিবন্ধন ফর্মে মোট সদস্য  " + TotalMember +  " জন, আপনি কি আরো সদস্য  অন্তর্ভুক্ত করতে চান [হ্যাঁ/না]?");
                         adb.setPositiveButton("হ্যাঁ", null);
                         adb.setNegativeButton("না", new AlertDialog.OnClickListener() {
                             public void onClick(DialogInterface dialog, int which) {
                                 finish();
                                 Intent intent = new Intent(getApplicationContext(),Member_list.class);
                                 intent.putExtras(IDbundle);
                                 startActivityForResult(intent, 1);

                             }});
                         adb.show();

                     }
                     //***
                     Member_DataModel objSave = new Member_DataModel();
                     objSave.setVill(txtVill.getText().toString());
                     objSave.setBari(txtBari.getText().toString());
                     objSave.setHH(txtHH.getText().toString());
                     objSave.setMSlNo(txtMSlNo.getText().toString());
                     objSave.setName(txtName.getText().toString());
                     objSave.setEnDt(Global.DateTimeNowYMDHMS());
                     objSave.setStartTime(STARTTIME);
                     objSave.setEndTime(g.CurrentTime24());
                     objSave.setDeviceID(DEVICEID);
                     objSave.setEntryUser(ENTRYUSER); //from data entry user list
                     String status = objSave.SaveUpdateData(Member_list.this);

                     DataSearch(VILL,BARI,HH);
                     txtMSlNo.setText(MemberSerial(VILL,BARI,HH));
                     txtName.setText("");
                     txtName.requestFocus();
                     if(txtMSlNo.getText().toString().equals("01"))
                     {
                         txtNameHint.setVisibility(View.VISIBLE);
//
                     }else
                     {
                         txtNameHint.setVisibility(View.GONE);
                     }

                 }
             });

            ImageButton cmdBack = (ImageButton)dialog.findViewById(R.id.cmdBack);
            cmdBack.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    dialog.dismiss();
                }
            });


             dialog.show();
         } catch (Exception e) {
             Connection.MessageBox(Member_list.this, e.getMessage());
             return;
         }
     }

     private String MemberSerial(String VILL, String BARI , String HH)
     {
         String M = C.ReturnSingleValue("Select (ifnull(max(cast(MSlNo as int)),0)+1)serial from tmpMember where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'");
         M = Global.Right("0"+M,2);
         return M;
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
         final TextView Pstat = (TextView)convertView.findViewById(R.id.Pstat);
         final TextView LmpDt = (TextView)convertView.findViewById(R.id.LmpDt);
         final TextView Ocp = (TextView)convertView.findViewById(R.id.Ocp);
         final TextView Sp1 = (TextView)convertView.findViewById(R.id.Sp1);
         final TextView Sp2 = (TextView)convertView.findViewById(R.id.Sp2);
         final TextView Sp3 = (TextView)convertView.findViewById(R.id.Sp3);
         final TextView Sp4 = (TextView)convertView.findViewById(R.id.Sp4);
         final TextView EnType = (TextView)convertView.findViewById(R.id.EnType);
         final TextView EnDate = (TextView)convertView.findViewById(R.id.EnDate);
         final TextView ExType = (TextView)convertView.findViewById(R.id.ExType);
         final TextView ExDate = (TextView)convertView.findViewById(R.id.ExDate);
         final TextView PosMig = (TextView)convertView.findViewById(R.id.PosMig);
         final TextView PosMigDate = (TextView)convertView.findViewById(R.id.PosMigDate);

         final ImageButton delMember = (ImageButton) convertView.findViewById(R.id.delMember);

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
         Pstat.setText(o.get("Pstat"));
         LmpDt.setText(o.get("LmpDt"));
         Ocp.setText(o.get("Ocp"));
         Sp1.setText(o.get("Sp1"));
         Sp2.setText(o.get("Sp2"));
         Sp3.setText(o.get("Sp3"));
         Sp4.setText(o.get("Sp4"));
         EnType.setText(o.get("EnType"));
         EnDate.setText(o.get("EnDate"));
         ExType.setText(o.get("ExType"));
         ExDate.setText(o.get("ExDate"));
         PosMig.setText(o.get("PosMig"));
         PosMigDate.setText(o.get("PosMigDate"));

         if (o.get("PNo").length() == 0)
         {
             MSlNo.setTextColor(Color.RED);
             Name.setTextColor(Color.RED);
             delMember.setVisibility(View.VISIBLE);
         }
         else
         {
             MSlNo.setTextColor(Color.BLACK);
             Name.setTextColor(Color.BLACK);
             delMember.setVisibility(View.INVISIBLE);
         }
         if(o.get("ExType").trim().length()==0 & (o.get("PosMig").trim().length()==0 | !o.get("PosMig").trim().equals("54")))
         {
             MSlNo.setTextColor(Color.BLACK);
             PNo.setTextColor(Color.BLACK);
             Name.setTextColor(Color.BLACK);
             Rth.setTextColor(Color.BLACK);
             Sex.setTextColor(Color.BLACK);
             BDate.setTextColor(Color.BLACK);
             MoNo.setTextColor(Color.BLACK);
             FaNo.setTextColor(Color.BLACK);
             Edu.setTextColor(Color.BLACK);
             MS.setTextColor(Color.BLACK);
             Pstat.setTextColor(Color.BLACK);
             LmpDt.setTextColor(Color.BLACK);
             Sp1.setTextColor(Color.BLACK);
             Sp2.setTextColor(Color.BLACK);
             Sp3.setTextColor(Color.BLACK);
             Sp4.setTextColor(Color.BLACK);
             Ocp.setTextColor(Color.BLACK);
             EnType.setTextColor(Color.BLACK);
             EnDate.setTextColor(Color.BLACK);
             ExType.setTextColor(Color.BLACK);
             ExDate.setTextColor(Color.BLACK);
         }
         else if(o.get("ExType").trim().length()==0 & o.get("PosMig").trim().equals("54"))
         {
             MSlNo.setTextColor(Color.BLACK);
             PNo.setTextColor(Color.BLACK);
             Name.setTextColor(Color.BLACK);
             Rth.setTextColor(Color.BLACK);
             Sex.setTextColor(Color.BLACK);
             BDate.setTextColor(Color.BLACK);
             MoNo.setTextColor(Color.BLACK);
             FaNo.setTextColor(Color.BLACK);
             Edu.setTextColor(Color.BLACK);
             MS.setTextColor(Color.BLACK);
             Pstat.setTextColor(Color.BLACK);
             LmpDt.setTextColor(Color.BLACK);
             Sp1.setTextColor(Color.BLACK);
             Sp2.setTextColor(Color.BLACK);
             Sp3.setTextColor(Color.BLACK);
             Sp4.setTextColor(Color.BLACK);
             Ocp.setTextColor(Color.BLACK);
             EnType.setTextColor(Color.BLACK);
             EnDate.setTextColor(Color.BLACK);
             ExType.setTextColor(Color.BLACK);
             ExDate.setTextColor(Color.BLACK);
             PosMig.setTextColor(Color.BLUE);
             PosMigDate.setTextColor(Color.BLUE);
         }
         else
         {
             MSlNo.setTextColor(Color.BLACK);
             PNo.setTextColor(Color.BLACK);
             Name.setTextColor(Color.BLACK);
             Rth.setTextColor(Color.BLACK);
             Sex.setTextColor(Color.BLACK);
             BDate.setTextColor(Color.BLACK);
             MoNo.setTextColor(Color.BLACK);
             FaNo.setTextColor(Color.BLACK);
             Edu.setTextColor(Color.BLACK);
             MS.setTextColor(Color.BLACK);
             Pstat.setTextColor(Color.BLACK);
             LmpDt.setTextColor(Color.BLACK);
             Sp1.setTextColor(Color.BLACK);
             Sp2.setTextColor(Color.BLACK);
             Sp3.setTextColor(Color.BLACK);
             Sp4.setTextColor(Color.BLACK);
             Ocp.setTextColor(Color.BLACK);
             EnType.setTextColor(Color.BLACK);
             EnDate.setTextColor(Color.BLACK);
             ExType.setTextColor(Color.BLACK);
             ExDate.setTextColor(Color.BLACK);
         }
//
//         if(o.get("newold").equals("n"))
//         {
//             MSlNo.setTextColor(Color.BLACK);
//             PNo.setTextColor(Color.BLACK);
//             Name.setTextColor(Color.BLACK);
//             Rth.setTextColor(Color.BLACK);
//             Sex.setTextColor(Color.BLACK);
//             BDate.setTextColor(Color.BLACK);
//             MoNo.setTextColor(Color.BLACK);
//             FaNo.setTextColor(Color.BLACK);
//             Edu.setTextColor(Color.BLACK);
//             MS.setTextColor(Color.BLACK);
//             Pstat.setTextColor(Color.BLACK);
//             LmpDt.setTextColor(Color.BLACK);
//             Sp1.setTextColor(Color.BLACK);
//             Sp2.setTextColor(Color.BLACK);
//             Sp3.setTextColor(Color.BLACK);
//             Sp4.setTextColor(Color.BLACK);
//             Ocp.setTextColor(Color.BLACK);
//             EnType.setTextColor(Color.BLACK);
//             EnDate.setTextColor(Color.BLACK);
//             ExType.setTextColor(Color.BLACK);
//             ExDate.setTextColor(Color.BLACK);
//         }
         if(Integer.valueOf(o.get("sl"))%2==0)
         {
             secListRow.setBackgroundColor(Color.parseColor("#F3F3F3"));
             delMember.setBackgroundColor(Color.parseColor("#F3F3F3"));
         }
         else
         {
             secListRow.setBackgroundColor(Color.parseColor("#FFFFFF"));
             delMember.setBackgroundColor(Color.parseColor("#FFFFFF"));
         }

         secListRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               //Write your code here
               Bundle IDbundle = new Bundle();
               IDbundle.putString("Vill", o.get("Vill"));
               IDbundle.putString("Bari", o.get("Bari"));
               IDbundle.putString("HH", o.get("HH"));
               IDbundle.putString("MSlNo", o.get("MSlNo"));
               IDbundle.putString("roundno",ROUNDNO);

                if (o.get("PNo").length() == 0)
                {
                    IDbundle.putString("OldNew", "new");
                }
                else
                IDbundle.putString("OldNew", "old");

               Intent f1;
               f1 = new Intent(getApplicationContext(), Events.class);
               f1.putExtras(IDbundle);
               //startActivity(f1);
                startActivityForResult(f1, 1);
            }
          });


         delMember.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(Member_list.this);
                 adb.setTitle("মুছে ফেলা");
                 adb.setMessage("আপনি কি সদস্যঃ "+ o.get("Name") +" এর তথ্য  মুছে ফেলতে চান[হ্যাঁ/না]?");
                 adb.setNegativeButton("না", null);
                 adb.setPositiveButton("হ্যাঁ", new AlertDialog.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                      C.Save("Delete from tmpMember where Vill='" + o.get("Vill") + "' and Bari='" + o.get("Bari") + "' and HH='" + o.get("HH") + "' and MSlNo='" + o.get("MSlNo") + "'");
                         DataSearch(o.get("Vill"),o.get("Bari"),o.get("HH"));
                     }
                 });
                 adb.show();

             }
         });

         ImageView imgPreg = (ImageView)convertView.findViewById(R.id.imgPreg);
         if(o.get("Pstat").equals("41"))
             imgPreg.setVisibility(View.VISIBLE);
         else
             imgPreg.setVisibility(View.INVISIBLE);

         return convertView;

       }

 }


}