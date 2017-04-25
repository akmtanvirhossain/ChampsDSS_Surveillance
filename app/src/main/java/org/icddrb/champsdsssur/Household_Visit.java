
 package org.icddrb.champsdsssur;


 //Android Manifest Code
 //<activity android:name=".Household" android:label="Household" />

 import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
 import android.database.Cursor;
 import android.graphics.Color;
 import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
 import android.widget.ListView;
 import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import Common.Connection;
import Common.Global;
 import Utility.MySharedPreferences;

 import static org.icddrb.champsdsssur.R.id.Bari;
 import static org.icddrb.champsdsssur.R.id.HHHead;
 import static org.icddrb.champsdsssur.R.id.Rnd;
 import static org.icddrb.champsdsssur.R.id.Vill;
// import static org.icddrb.champsdsssur.R.id.Note;

 public class Household_Visit extends Activity {
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
     ArrayList<HashMap<String, String>> mylist;
         TextView lblHeading;
         TextView lblHeading1;

         LinearLayout seclbl02;
         View linelbl02;
         LinearLayout seclbl01;
         View linelbl01;
         LinearLayout secVill;
         View lineVill;
         TextView VlblVill;
         EditText txtVill;
         LinearLayout secBari;
         View lineBari;
         TextView VlblBari;
         EditText txtBari;
         LinearLayout secHH;
         View lineHH;
         TextView VlblHH;
         EditText txtHH;
         LinearLayout secReligion;
         View lineReligion;
         TextView VlblReligion;
         Spinner spnReligion;
         LinearLayout secMobileNo1;
         View lineMobileNo1;
         TextView VlblMobileNo1;
         EditText txtMobileNo1;
         LinearLayout secMobileNo2;
         View lineMobileNo2;
         TextView VlblMobileNo2;
         EditText txtMobileNo2;

         LinearLayout secHHHead;
         View lineHHHead;
         TextView VlblHHHead;
         EditText txtHHHead;

//         LinearLayout secTotMem;
//         View lineTotMem;
//         TextView VlblTotMem;
//         EditText txtTotMem;
//         LinearLayout secTotRWo;
//         View lineTotRWo;
//         TextView VlblTotRWo;
//         EditText txtTotRWo;

//         LinearLayout secEnType;
//         View lineEnType;
//         TextView VlblEnType;
//         EditText txtEnType;
//         LinearLayout secEnDate;
//         View lineEnDate;
//         TextView VlblEnDate;
//         EditText dtpEnDate;
//         LinearLayout secExType;
//         View lineExType;
//         TextView VlblExType;
//         EditText txtExType;
//         LinearLayout secExDate;
//         View lineExDate;
//         TextView VlblExDate;
//         EditText dtpExDate;
         LinearLayout secRnd;
         View lineRnd;
         TextView VlblRnd;
         EditText txtRnd;

     //***********************sakib****************************************
         Button btnPlusMobile1;
         Button btnMinusMobile1;
         EditText dtpVDt;

         LinearLayout secVDate;
         View lineVDate;
         TextView VlblVDate;
         EditText dtpVDate;
         LinearLayout secVStatus;
         View lineVStatus;
         TextView VlblVStatus;
         Spinner spnVStatus;

         LinearLayout secVStatusOth;
         View lineVStatusOth;
         TextView VlblVStatusOth;
         EditText txtVStatusOth;

         LinearLayout secResp;
         View lineResp;
         TextView VlblResp;
         Spinner spnResp;

         LinearLayout secNote;
         View lineNote;
         TextView VlblNote;
         EditText txtNote;
     //***********************sakib********************************************

    static String TableName;

    static String STARTTIME = "";
    static String DEVICEID  = "";
    static String ENTRYUSER = "";

    Bundle IDbundle;
    static String VILL = "";
    static String BARI = "";
    static String BName = "";
    static String HH = "";
    static String ROUNDNO = "";
    static String CLUSTER = "";
    static String BLOCK   = "";

     static String RsNo = "";
     static String OLDNEWHH = "";

     ListView list;
     ImageButton btnVDate;
     EditText VisitDate;
     Spinner BariList;
 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try {
       setContentView(R.layout.household_visit);

       C = new Connection(this);
       g = Global.getInstance();

       STARTTIME = g.CurrentTime24();
       DEVICEID = g.getDeviceNo();
       ENTRYUSER = g.getUserId();

       IDbundle = getIntent().getExtras();
       VILL = IDbundle.getString("Vill");
       BARI = IDbundle.getString("Bari");
       BName = IDbundle.getString("BariName");
       HH = IDbundle.getString("HH");

       ROUNDNO = IDbundle.getString("roundno");
       CLUSTER = IDbundle.getString("cluster");
       BLOCK = IDbundle.getString("block");
       OLDNEWHH = IDbundle.getString("OldNew");

       TableName = "Household";

       //turnGPSOn();

       //GPS Location
       //FindLocation();
       // Double.toString(currentLatitude);
       // Double.toString(currentLongitude);

       lblHeading = (TextView) findViewById(R.id.lblHeading);
       lblHeading1 = (TextView) findViewById(R.id.lblHeading1);

       ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
       cmdBack.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               AlertDialog.Builder adb = new AlertDialog.Builder(Household_Visit.this);
               adb.setTitle("Close");
               adb.setMessage("আপনি কি এই ফরম থেকে বের হতে চান [হ্যাঁ/না]?");
               adb.setNegativeButton("না", null);
               adb.setPositiveButton("হ্যাঁ", new AlertDialog.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                       finish();
                   }
               });
               adb.show();
           }
       });

       seclbl02 = (LinearLayout) findViewById(R.id.seclbl02);
       linelbl02 = (View) findViewById(R.id.linelbl02);
       seclbl01 = (LinearLayout) findViewById(R.id.seclbl01);
       linelbl01 = (View) findViewById(R.id.linelbl01);
       secVill = (LinearLayout) findViewById(R.id.secVill);
       lineVill = (View) findViewById(R.id.lineVill);
       VlblVill = (TextView) findViewById(R.id.VlblVill);
       txtVill = (EditText) findViewById(R.id.txtVill);
       secBari = (LinearLayout) findViewById(R.id.secBari);
       lineBari = (View) findViewById(R.id.lineBari);
       VlblBari = (TextView) findViewById(R.id.VlblBari);
       txtBari = (EditText) findViewById(R.id.txtBari);
       secHH = (LinearLayout) findViewById(R.id.secHH);
       lineHH = (View) findViewById(R.id.lineHH);
       VlblHH = (TextView) findViewById(R.id.VlblHH);
       txtHH = (EditText) findViewById(R.id.txtHH);
       secReligion = (LinearLayout) findViewById(R.id.secReligion);
       lineReligion = (View) findViewById(R.id.lineReligion);
       VlblReligion = (TextView) findViewById(R.id.VlblReligion);
       spnReligion = (Spinner) findViewById(R.id.spnReligion);
       List<String> listReligion = new ArrayList<String>();

       listReligion.add("");
       listReligion.add("1-মুসলিম");
       listReligion.add("2-হিন্দু");
       listReligion.add("3-খ্রীষ্ট");
       listReligion.add("4-বুদ্ধ");
       listReligion.add("5-অন্যান্য");
       ArrayAdapter<String> adptrReligion = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listReligion);
       spnReligion.setAdapter(adptrReligion);

       secMobileNo1 = (LinearLayout) findViewById(R.id.secMobileNo1);
       lineMobileNo1 = (View) findViewById(R.id.lineMobileNo1);
       VlblMobileNo1 = (TextView) findViewById(R.id.VlblMobileNo1);
       txtMobileNo1 = (EditText) findViewById(R.id.txtMobileNo1);
       secMobileNo2 = (LinearLayout) findViewById(R.id.secMobileNo2);
       lineMobileNo2 = (View) findViewById(R.id.lineMobileNo2);
       VlblMobileNo2 = (TextView) findViewById(R.id.VlblMobileNo2);
       txtMobileNo2 = (EditText) findViewById(R.id.txtMobileNo2);

       secHHHead = (LinearLayout) findViewById(R.id.secHHHead);
       lineHHHead = (View) findViewById(R.id.lineHHHead);
       VlblHHHead = (TextView) findViewById(R.id.VlblHHHead);
       txtHHHead = (EditText) findViewById(R.id.txtHHHead);


//         secTotMem=(LinearLayout)findViewById(R.id.secTotMem);
//         lineTotMem=(View)findViewById(R.id.lineTotMem);
//         VlblTotMem=(TextView) findViewById(R.id.VlblTotMem);
//         txtTotMem=(EditText) findViewById(R.id.txtTotMem);
//         secTotRWo=(LinearLayout)findViewById(R.id.secTotRWo);
//         lineTotRWo=(View)findViewById(R.id.lineTotRWo);
//         VlblTotRWo=(TextView) findViewById(R.id.VlblTotRWo);
//         txtTotRWo=(EditText) findViewById(R.id.txtTotRWo);
//         secEnType=(LinearLayout)findViewById(R.id.secEnType);
//         lineEnType=(View)findViewById(R.id.lineEnType);
//         VlblEnType=(TextView) findViewById(R.id.VlblEnType);
//         txtEnType=(EditText) findViewById(R.id.txtEnType);
//         secEnDate=(LinearLayout)findViewById(R.id.secEnDate);
//         lineEnDate=(View)findViewById(R.id.lineEnDate);
//         VlblEnDate=(TextView) findViewById(R.id.VlblEnDate);
//         dtpEnDate=(EditText) findViewById(R.id.dtpEnDate);
//         secExType=(LinearLayout)findViewById(R.id.secExType);
//         lineExType=(View)findViewById(R.id.lineExType);
//         VlblExType=(TextView) findViewById(R.id.VlblExType);
//         txtExType=(EditText) findViewById(R.id.txtExType);
//         secExDate=(LinearLayout)findViewById(R.id.secExDate);
//         lineExDate=(View)findViewById(R.id.lineExDate);
//         VlblExDate=(TextView) findViewById(R.id.VlblExDate);
//         dtpExDate=(EditText) findViewById(R.id.dtpExDate);
       secRnd = (LinearLayout) findViewById(R.id.secRnd);
       lineRnd = (View) findViewById(R.id.lineRnd);
       VlblRnd = (TextView) findViewById(R.id.VlblRnd);
       txtRnd = (EditText) findViewById(R.id.txtRnd);


       //***********************sakib********************************************************
       secVDate = (LinearLayout) findViewById(R.id.secVDate);
       lineVDate = (View) findViewById(R.id.lineVDate);
       VlblVDate = (TextView) findViewById(R.id.VlblVDate);
       dtpVDate = (EditText) findViewById(R.id.dtpVDate);
       dtpVDate.setText(Global.DateNowDMY());


       secVStatus = (LinearLayout) findViewById(R.id.secVStatus);
       lineVStatus = (View) findViewById(R.id.lineVStatus);
       VlblVStatus = (TextView) findViewById(R.id.VlblVStatus);
       spnVStatus = (Spinner) findViewById(R.id.spnVStatus);
       List<String> listVStatus = new ArrayList<String>();

       listVStatus.add("");
       listVStatus.add("1-ইন্টারভিউ সফল হয়েছে");
       listVStatus.add("2-বাড়ি পরিদর্শনের সময় খানার কোন সদস্যকে বা উপযুক্ত কাউকে পাওয়া যায় নাই");
       listVStatus.add("3-অনেক দিনের জন্য খানার সকল সদস্য অনুপস্থিত");
       listVStatus.add("4-ইন্টারভিউ বাতিল");
       listVStatus.add("5-ইন্টারভিউ দিতে রাজী নয়");
       listVStatus.add("6-বাসা খালি অথবা ঠিকানাটি কোন বাসস্থানের নয়");
       listVStatus.add("7-বাসস্থানটি ধংসপ্রাপ্ত");
       listVStatus.add("8-বাসস্থানটি খুঁজে পাওয়া যায় নাই");
       listVStatus.add("9-অন্যান");
       ArrayAdapter<String> adptrVStatus = new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item, listVStatus);
       spnVStatus.setAdapter(adptrVStatus);

//       spnVStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//           @Override
//           public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//               if (spnVStatus.getSelectedItem().toString().length() == 0) return;
//               String spnData = Connection.SelectedSpinnerValue(spnVStatus.getSelectedItem().toString(), "-");

//               if (spnData.equalsIgnoreCase("1")) {
//                   secReligion.setVisibility(View.VISIBLE);
//                   lineReligion.setVisibility(View.VISIBLE);
//
//                   secMobileNo1.setVisibility(View.VISIBLE);
//                   lineMobileNo1.setVisibility(View.VISIBLE);
//
//                   secMobileNo2.setVisibility(View.GONE);
//                   lineMobileNo2.setVisibility(View.GONE);
//                   txtMobileNo2.setVisibility(View.GONE);
//
//                   secHHHead.setVisibility(View.VISIBLE);
//                   lineHHHead.setVisibility(View.VISIBLE);
//
//                   secResp.setVisibility(View.VISIBLE);
//                   lineResp.setVisibility(View.VISIBLE);
//
//               } else {
//                   secReligion.setVisibility(View.GONE);
//                   lineReligion.setVisibility(View.GONE);
//                   spnReligion.setSelection(0);
//
//                   secMobileNo1.setVisibility(View.GONE);
//                   lineMobileNo1.setVisibility(View.GONE);
//                   txtMobileNo1.setText("");
//
//                   secMobileNo2.setVisibility(View.GONE);
//                   lineMobileNo2.setVisibility(View.GONE);
//                   txtMobileNo2.setText("");
//
//                   secResp.setVisibility(View.GONE);
//                   lineResp.setVisibility(View.GONE);
//                   spnResp.setSelection(0);
//
//               }
//               if (!spnData.equalsIgnoreCase("9")) {
//                   secVStatusOth.setVisibility(View.GONE);
//                   lineVStatusOth.setVisibility(View.GONE);
//                   txtVStatusOth.setText("");
//               } else {
//                   secVStatusOth.setVisibility(View.VISIBLE);
//                   lineVStatusOth.setVisibility(View.VISIBLE);
//               }
//           }
//
//           @Override
//           public void onNothingSelected(AdapterView<?> parentView) {
//           }
//       });

       secVStatusOth = (LinearLayout) findViewById(R.id.secVStatusOth);
       lineVStatusOth = (View) findViewById(R.id.lineVStatusOth);
       VlblVStatusOth = (TextView) findViewById(R.id.VlblVStatusOth);
       txtVStatusOth = (EditText) findViewById(R.id.txtVStatusOth);

       secResp = (LinearLayout) findViewById(R.id.secResp);
       lineResp = (View) findViewById(R.id.lineResp);
       VlblResp = (TextView) findViewById(R.id.VlblResp);
       spnResp = (Spinner) findViewById(R.id.spnResp);
       spnResp.setAdapter(C.getArrayAdapter("Select '' union Select MSlNo||'-'||Name from Member Where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'and ((julianday(date('now'))-julianday(bdate))/365.25)>10 and (extype is null or length(extype)=0)"));

       secNote = (LinearLayout) findViewById(R.id.secNote);
       lineNote = (View) findViewById(R.id.lineNote);
       VlblNote = (TextView) findViewById(R.id.VlblNote);
       txtNote = (EditText) findViewById(R.id.txtNote);


       txtVill.setText(VILL);
       txtVill.setFocusable(false);
       txtBari.setText(BARI);
       txtBari.setFocusable(false);

       if (HH.equals("")) {
           HH = HHSerial();
       }
       txtHH.setText(HH);
       txtHH.setFocusable(false);


//         dtpEnDate.setOnTouchListener(new View.OnTouchListener() {
//             @Override
//             public boolean onTouch(View v, MotionEvent event) {
//                 final int DRAWABLE_RIGHT  = 2;
//                 if(event.getAction() == MotionEvent.ACTION_UP) {
//                     if(event.getRawX() >= (dtpEnDate.getRight() - dtpEnDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                         VariableID = "btnEnDate"; showDialog(DATE_DIALOG);
//                      return true;
//                     }
//                 }
//                 return false;
//             }
//         });
//         dtpExDate.setOnTouchListener(new View.OnTouchListener() {
//             @Override
//             public boolean onTouch(View v, MotionEvent event) {
//                 final int DRAWABLE_RIGHT  = 2;
//                 if(event.getAction() == MotionEvent.ACTION_UP) {
//                     if(event.getRawX() >= (dtpExDate.getRight() - dtpExDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                         VariableID = "btnExDate"; showDialog(DATE_DIALOG);
//                      return true;
//                     }
//                 }
//                 return false;
//             }
//         });

       dtpVDate.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {

               final int DRAWABLE_RIGHT = 2;
               if (event.getAction() == MotionEvent.ACTION_UP) {
                   if (event.getRawX() >= (dtpVDate.getRight() - dtpVDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                       VariableID = "dtpVDate";
                       showDialog(DATE_DIALOG);

                       return true;
                   }
               }
               return false;
           }
       });

       //Hide all skip variables
       secVStatusOth.setVisibility(View.GONE);
       secMobileNo2.setVisibility(View.GONE);
//         secHHHead.setVisibility(View.GONE);
//         secTotMem.setVisibility(View.GONE);
//         secTotRWo.setVisibility(View.GONE);

       //***********************added by sakib********************************************
       secMobileNo2.setVisibility(View.GONE);
       txtMobileNo2.setVisibility(View.GONE);

       btnPlusMobile1 = (Button) findViewById(R.id.btnPlusMobile1);
       btnPlusMobile1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               secMobileNo2.setVisibility(View.VISIBLE);
               txtMobileNo2.setVisibility(View.VISIBLE);
           }
       });

       btnMinusMobile1 = (Button) findViewById(R.id.btnMinusMobile1);
       btnMinusMobile1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               secMobileNo2.setVisibility(View.GONE);
               txtMobileNo2.setText("");
               txtMobileNo2.setVisibility(View.GONE);
           }
       });
       //***********************************************************************************************************

       Button cmdSave = (Button) findViewById(R.id.cmdSave);
       cmdSave.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               DataSave();
           }
       });

       DataSearch(VILL, BARI, HH);
       String SQL = "";
       g.setRsNo("");

         if (OLDNEWHH.equals("old"))
         {
             lblHeading1.setVisibility(View.GONE);
             lblHeading.setVisibility(View.VISIBLE);

             //Only eligible member from tmpMember Table
             String R = RsNo.length() == 0?"0":RsNo;
             if(Integer.valueOf(R)>=1 & Integer.valueOf(R)<=76)
             {
                 SQL  =" Select ' ' union";
                 SQL +=" Select '77-Entire household migrated-out' union";
                 SQL +=" Select (MSlNo||'-'||Name)  from Member where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and ((julianday(date('now'))-julianday(BDate))/365.25)>10 and (ExType is null or length(ExType)=0)";
             }
             else {

                 SQL = " Select ' ' union";
                 SQL += " Select '00-No Visit due to unavoidable situation' union";
                 SQL += " Select '77-Entire household migrated-out' union";
                 SQL += " Select '88-Refused to interview' union";
                 SQL += " Select '99-All adult members absent' union";
                 SQL += " Select (MSlNo||'-'||Name)  from Member where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and ((julianday(date('now'))-julianday(BDate))/365.25)>10 and (ExType is null or length(ExType)=0)";
             }
         }
         else if (OLDNEWHH.equals("new"))
         {
                 lblHeading1.setVisibility(View.VISIBLE);
                 lblHeading.setVisibility(View.GONE);

                 SQL = " Select ' ' union";
                 SQL += " Select '01-Member 1' union";
                 SQL += " Select '02-Member 2' union";
                 SQL += " Select '03-Member 3' union";
                 SQL += " Select '04-Member 4' union";
                 SQL += " Select '05-Member 5' union";
                 SQL += " Select '06-Member 6'";
         }
         txtRnd.setText(ROUNDNO);

         spnResp.setAdapter(C.getArrayAdapter(SQL));

         for(int i=1;i<spnResp.getCount();i++)
         {
             if(Global.Left(spnResp.getItemAtPosition(i).toString(), 2).equals(RsNo))
             {
                 spnResp.setSelection(i);
                 i=spnResp.getCount();
             }
         }


     }
     catch(Exception  e)
     {
         Connection.MessageBox(Household_Visit.this, e.getMessage());
         return;
     }
 }
     private String HHSerial()
     {
         String SL = C.ReturnSingleValue("Select (ifnull(max(cast(HH as int)),0)+1)SL from Household where Bari='"+BARI+"'"); //where ParticipantID='"+ ParticipantID +"'");
         int length=SL.length();
         String s = "";
         for(int i=0;i<2-length;i++)
         {
             s+="0";
         }
         SL=s+SL;
         return SL;
     }
     public void BlockList(Boolean heading, String BariCode)
     {
         final ListView list = (ListView) findViewById(R.id.lstData);
         mylist = new ArrayList<HashMap<String, String>>();
         HashMap<String, String> map;

         try
         {
             String BCode = ""; //BariCode.length()==0?"%":BariCode;
             String SQL = "";

             if(BariCode.length()!=0)
             {
                 SQL +="select b.bari,ifnull(h.hh,'')as hh,ifnull(h.hhhead,'')hhhead,count(m.vill)totalMem,b.vill,b.bariname,(case when v.rnd is null then '2' else '1' end)RoundVisit,";
                 SQL +=" ifnull(h.Religion,'')rel,ifnull(v.Resp,'')rsno,ifnull(v.vdate,'')vdate,count(case when m.posmig='54' then '1' else null end)posmig from ";
                 SQL +=" Baris b";
                 SQL +=" left outer join Household h on b.vill||b.bari=h.vill||h.bari";
                 SQL +=" left outer join Member m on h.vill||h.bari||h.hh=m.Vill||m.Bari||m.hh and length(m.extype)=0";
                 SQL +=" left outer join tmpVisits v on h.vill||h.bari||h.hh=v.Vill||v.Bari||v.hh";
                 SQL +=" where ";//vl.Cluster='"+ g.getClusterCode() +"' and";
                 SQL +=" b.Cluster='"+ g.getClusterCode() +"' and";
                 SQL +=" b.block='"+ g.getBlockCode() +"' and b.bari ='"+ BariCode +"'";
                 SQL +=" group by h.vill,h.bari,h.hh";
                 SQL +=" order by h.vill, h.Bari, h.HH";
             }
             else
             {
                 SQL +="select b.bari,ifnull(h.hh,'')as hh,ifnull(h.hhhead,'')hhhead,count(m.vill)totalMem,b.vill,b.bariname,(case when v.rnd is null then '2' else '1' end)RoundVisit,";
                 SQL +=" ifnull(h.Religion,'')rel,ifnull(v.Resp,'')rsno,ifnull(v.vdate,'')vdate,count(case when m.posmig='54' then '1' else null end)posmig from ";
                 SQL +=" Baris b";
                 SQL +=" left outer join Household h on b.vill||b.bari=h.vill||h.bari";
                 SQL +=" left outer join Member m on h.vill||h.bari||h.hh=m.Vill||m.Bari||m.hh and length(m.extype)=0";
                 SQL +=" left outer join tmpVisits v on h.vill||h.bari||h.hh=v.Vill||v.Bari||v.hh";
                 SQL +=" where ";//vl.Cluster='"+ g.getClusterCode() +"' and";
                 SQL +=" b.Cluster='"+ g.getClusterCode() +"' and";
                 SQL +=" b.block='"+ g.getBlockCode() +"'";
                 SQL +=" group by h.vill,h.bari,h.hh";
                 SQL +=" order by h.vill, h.Bari, h.HH";
             }
             Cursor cur=C.ReadData(SQL);

             cur.moveToFirst();
             if(heading==true)
             {
                 View header = getLayoutInflater().inflate(R.layout.household_list, null);
                 list.addHeaderView(header);
             }

             while(!cur.isAfterLast())
             {
                 map = new HashMap<String, String>();
                 map.put("bari", cur.getString(0));
                 map.put("hh",cur.getString(1));
                 map.put("hhhead", cur.getString(2));
                 map.put("totalmem", cur.getString(3));
                 map.put("vcode", cur.getString(4));
                 map.put("bariname", cur.getString(5));
                 map.put("visit", cur.getString(6));
                 map.put("rel", cur.getString(7));
                 map.put("rsno", cur.getString(8));
                 map.put("vdate", cur.getString(9));
                 map.put("posmig", cur.getString(10));

                 mylist.add(map);

                 cur.moveToNext();
             }
             cur.close();
//             mSchedule = new SimpleAdapter(this, mylist, R.layout.household_row,
//                     new String[] {"bari","hh", "hhhead"},
//                     new int[] {R.id.Bari, R.id.HH, R.id.HHHead});

//             list.setAdapter(new DataListAdapter(this));

         }
         catch(Exception e)
         {
             AlertDialog.Builder adb=new AlertDialog.Builder(Household_Visit.this);
             adb.setTitle("Message");
             adb.setMessage(e.getMessage());
             adb.setPositiveButton("Ok", null);
             adb.show();
         }

     }
 private void DataSave()
 {
   try
     {
         String DV="";

         if(txtVill.getText().toString().length()==0 & secVill.isShown())
           {
             Connection.MessageBox(Household_Visit.this, "Required field: গ্রাম.");
             txtVill.requestFocus();
             return;
           }
         else if(txtBari.getText().toString().length()==0 & secBari.isShown())
           {
             Connection.MessageBox(Household_Visit.this, "Required field: বাড়ি.");
             txtBari.requestFocus();
             return;
           }
         else if(txtHH.getText().toString().length()==0 & secHH.isShown())
           {
             Connection.MessageBox(Household_Visit.this, "Required field: খানা.");
             txtHH.requestFocus();
             return;
           }
         else if(txtHHHead.getText().toString().length()==0 & secHHHead.isShown())
         {
             Connection.MessageBox(Household_Visit.this, "Required field: খানা প্রধানের নাম.");
             txtHHHead.requestFocus();
             return;
         }
         else if(spnReligion.getSelectedItemPosition()==0  & secReligion.isShown())
           {
             Connection.MessageBox(Household_Visit.this, "Required field: ধর্ম.");
             spnReligion.requestFocus();
             return;
           }
         else if(txtMobileNo1.getText().toString().length()!=0 & txtMobileNo1.getText().toString().length()!=11 & secMobileNo1.isShown())
           {
             Connection.MessageBox(Household_Visit.this, "Required field: ১ম মোবাইল নম্বর ১১ সংখ্যা হতে হবে.");
             txtMobileNo1.requestFocus();
             return;
           }
         else if(txtMobileNo2.getText().toString().length()!=0 &txtMobileNo2.getText().toString().length()!=11  & secMobileNo2.isShown())
           {
             Connection.MessageBox(Household_Visit.this, "Required field: ২য় মোবাইল নম্বর ১১ সংখ্যা হতে হবে.");
             txtMobileNo2.requestFocus();
             return;
           }

         DV = Global.DateValidate(dtpVDate.getText().toString());
         if(DV.length()!=0 & secVDate.isShown())
           {
             Connection.MessageBox(Household_Visit.this, DV);
               dtpVDate.requestFocus();
             return;
           }
         else if(spnResp.getSelectedItemPosition()==0  & spnResp.isShown())
         {
             Connection.MessageBox(Household_Visit.this, "Required field: উত্তরদাতা.");
             spnResp.requestFocus();
             return;
         }

         if (txtMobileNo1.getText().toString().equals(txtMobileNo2.getText().toString()) & txtMobileNo2.isShown() & txtMobileNo1.getText().toString().length() != 0)
         {
             Connection.MessageBox(Household_Visit.this, "১ম মোবাইল নম্বর এবং ২য় মোবাইল নম্বর একই হবেনা");
             txtMobileNo2.requestFocus();
             return;
         }

         //*************************************************************************************

         if(OLDNEWHH.equalsIgnoreCase("old"))
         {

             final int Resp = Integer.parseInt(Global.Left(spnResp.getSelectedItem().toString(),2));
             g.setRsNo(Global.Left(spnResp.getSelectedItem().toString(),2));

             if(Resp >= 1 & Resp <= 76) {

                 AlertDialog.Builder adb = new AlertDialog.Builder(Household_Visit.this);
                 adb.setTitle("Close");
                 adb.setMessage("এই খানায় কি কোন ধরনের ইভেন্ট পরিবর্তন হয়েছে[হ্যাঁ/না]?");

                 //have no events
                 //-----------------------------------------------------------------
                 adb.setNegativeButton("না", new AlertDialog.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         //save visit then close
                         String SQL = "";
                         try {
                             if (!C.Existence("Select * from Visits where vill||bari||hh='" + VILL + BARI + HH + "' and Rnd='" + ROUNDNO + "'")) {
                                 SQL = "Insert into Visits(Vill, Bari, HH, VDate, Resp, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, Note)Values(";
                                 SQL += "'" + VILL + "',";
                                 SQL += "'" + BARI + "',";
                                 SQL += "'" + HH + "',";
                                 SQL += "'" + Global.DateConvertYMD(dtpVDate.getText().toString()) + "',"; //date of visit
                                 SQL += "'" + Global.Left(spnResp.getSelectedItem().toString(), 2) + "',"; //RespNo
                                 SQL += "'" + ROUNDNO + "',"; //round
                                 SQL += "'" + STARTTIME + "',"; //StartTime
                                 SQL += "'" + g.CurrentTime24() + "',"; //EndTime
                                 SQL += "'" + DEVICEID + "',"; //DeviceID
                                 SQL += "'" + ENTRYUSER + "',"; //EntryUser code
                                 SQL += "'" + Double.toString(currentLatitude) + "',"; // Lat
                                 SQL += "'" + Double.toString(currentLongitude) + "',"; // Lon
                                 SQL += "'" + Global.DateConvertYMD(dtpVDate.getText().toString()) + "',"; //EnDt Date
                                 SQL += "'" + 2 + "',"; //Upload
                                 SQL += "'" + txtNote.getText() + "')";
                             } else {
                                 SQL = "Update Visits set upload='2',";
                                 SQL += " Resp='" + Global.Left(spnResp.getSelectedItem().toString(), 2) + "',";
                                 SQL += " VDate='" + Global.DateConvertYMD(dtpVDate.getText().toString()) + "',"; //date of visit
                                 SQL += " Note='" + txtNote.getText() + "',";
                                 SQL += " EntryUser='" + ENTRYUSER + "'"; //EntryUser code
                                 SQL += " where vill||bari||hh='" + VILL + BARI + HH + "' and Rnd='" + ROUNDNO + "'";
                             }
                             C.Save(SQL);

                             C.Save("Update Household set upload='2',HHHead='" + txtHHHead.getText() + "',Note='" + txtNote.getText() + "',MobileNo1='" + txtMobileNo1.getText() + "',MobileNo2='" + txtMobileNo2.getText() + "',Religion='" + Global.Left(spnReligion.getSelectedItem().toString(), 1) + "' where vill||bari||hh='" + (VILL + BARI + HH) + "'");

//                         BlockList(false, Global.Left(BariList.getSelectedItem().toString(),4));
                         } catch (Exception ex) {
                             Connection.MessageBox(Household_Visit.this, ex.getMessage());
                             return;
                         }
                         dialog.cancel();

                         Intent returnIntent = new Intent();
                         returnIntent.putExtra("res", "hh");
                         setResult(Activity.RESULT_OK, returnIntent);
                         Connection.MessageBox(Household_Visit.this, "Saved Successfully");
                         finish();
                     }
                 });

//             adb.show();

                 //have events
                 //-----------------------------------------------------------------
                 adb.setPositiveButton("হ্যাঁ", new AlertDialog.OnClickListener()
                 {
                     public void onClick(DialogInterface dialog, int which) {
                         C.Save("Delete from tmpHousehold");
                         C.Save("Delete from tmpVisits");
                         C.Save("Delete from tmpMember");
                         C.Save("Delete from tmpSES");
                         C.Save("Delete from tmpPregHis");
                         C.Save("Delete from tmpEvents");

                         //-- -tmpHousehold Information-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                         String SQL = "";
                         SQL = "Insert into tmpHousehold(Vill, Bari, HH, Religion, MobileNo1, MobileNo2, HHHead, TotMem, TotRWo, EnType, EnDate, ExType, ExDate, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, UploadDT, modifyDate, Note)";
                         SQL += " Select Vill, Bari, HH, Religion, MobileNo1, MobileNo2, HHHead, TotMem, TotRWo, EnType, EnDate, ExType, ExDate, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, UploadDT, modifyDate, Note from Household";
                         SQL += " where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'";
                         C.Save(SQL);

                         //-- -tmpVisits Information-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                         SQL = " Insert into tmpVisits";
                         SQL += " (Vill, Bari, HH, VDate, VStatus, VStatusOth, VisitNo, Resp, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, UploadDT, modifyDate)";
                         SQL += " Select Vill, Bari, HH, VDate, VStatus, VStatusOth, VisitNo, Resp, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, UploadDT, modifyDate from Visits";
                         SQL += " where  Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'";
                         C.Save(SQL);

                         //-- -tmpMember Information-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                         SQL = " Insert into tmpMember";
                         SQL += " (Vill, Bari, HH, MSlNo, PNo, Name, Rth, Sex, BDate, AgeY, MoNo, FaNo, Edu, MS, Ocp, Sp1, Sp2, Sp3, Sp4, EnType, EnDate, ExType, ExDate, NeedReview, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, UploadDT, modifyDate)";
                         SQL += " Select Vill, Bari, HH, MSlNo, PNo, Name, Rth, Sex, BDate, AgeY, MoNo, FaNo, Edu, MS, Ocp, Sp1, Sp2, Sp3, Sp4, EnType, EnDate, ExType, ExDate, NeedReview, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, UploadDT, modifyDate from Member";
                         SQL += " where  Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'";
                         C.Save(SQL);

                         //-- -tmpSES Information-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                         SQL = " Insert into tmpSES";
                         SQL += " (Vill, Bari, HH, SESNo, VDate, VStatus, VStatusOth, Rnd, WSDrink, WSDrinkOth, WSCook, WSCookOth, WSWash, WSWashOth, Latrine, LatrineOth,";
                         SQL += " Electricity, Radio, TV, Mobile, Telephone, Refrige, Watch, ElecFan, RickVan, Bicycle, MotCycle, Computer, Buffalo, Bull, Goat, Chicken, Pigeon,";
                         SQL += " Roof, RoofOth, Wall, WallOth, Floor, FloorOth, Homestead, HomesteadOth, OthLand, StartTime, EndTime,";
                         SQL += " DeviceID, EntryUser, Lat, Lon, EnDt, Upload, UploadDT, modifyDate)";

                         SQL += " Select Vill, Bari, HH, SESNo, VDate, VStatus, VStatusOth, Rnd, WSDrink, WSDrinkOth, WSCook, WSCookOth, WSWash, WSWashOth, Latrine, LatrineOth,";
                         SQL += " Electricity, Radio, TV, Mobile, Telephone, Refrige, Watch, ElecFan, RickVan, Bicycle, MotCycle, Computer, Buffalo, Bull, Goat, Chicken, Pigeon,";
                         SQL += " Roof, RoofOth, Wall, WallOth, Floor, FloorOth, Homestead, HomesteadOth, OthLand, StartTime, EndTime,";
                         SQL += " DeviceID, EntryUser, Lat, Lon, EnDt, Upload, UploadDT, modifyDate from SES";
                         SQL += " where  Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'";
                         C.Save(SQL);

                         //-- -tmpPregHis. History-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                         SQL = " Insert into tmpPregHis";
                         SQL += " (Vill, Bari, HH, MSlNo, PNo, VDate, VStatus, VStatusOth, MarriageStatus, MarMon, MarYear, MarDK, GaveBirth, ChildLivWWo,";
                         SQL += " SonLivWWo, DaugLivWWo, ChldLivOut, SonLivOut, DaugLivOut, ChldDie, BoyDied, GirlDied, NotLivBrth, TotLB, TotPregOut,";
                         SQL += " CurPreg, LMPDate, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, UploadDT, modifyDate)";

                         SQL += " Select Vill, Bari, HH, MSlNo, PNo, VDate, VStatus, VStatusOth, MarriageStatus, MarMon, MarYear, MarDK, GaveBirth, ChildLivWWo,";
                         SQL += " SonLivWWo, DaugLivWWo, ChldLivOut, SonLivOut, DaugLivOut, ChldDie, BoyDied, GirlDied, NotLivBrth, TotLB, TotPregOut,";
                         SQL += " CurPreg, LMPDate, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, UploadDT, modifyDate from PregHis";
                         SQL += " where  Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'";

                         C.Save(SQL);

                         //-- -tmpEvents Information-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                         SQL = " Insert into tmpEvents";
                         SQL += " (Vill, Bari, HH, MSlNo, PNo, EvType, EvDate, Info1, Info2, Info3, Info4, VDate, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, UploadDT, modifyDate)";
                         SQL += " Select Vill, Bari, HH, MSlNo, PNo, EvType, EvDate, Info1, Info2, Info3, Info4, VDate, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, UploadDT, modifyDate from Events";
                         SQL += " where  Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'";
                         C.Save(SQL);

//                         dialog.cancel();

                         Intent returnIntent = new Intent();
                         returnIntent.putExtra("res", "hh");
                         setResult(Activity.RESULT_OK, returnIntent);

                         finish();
                         Intent f1;
                         f1 = new Intent(getApplicationContext(), Member_list.class);
                         f1.putExtras(IDbundle);
                         startActivityForResult(f1, 1);
//
                     }
                 });

                 adb.show();
             }
          }

         //For New household--------------------------------------------------------------
         else  if(OLDNEWHH.equalsIgnoreCase("new"))
         {
                 String SQL = "";
                 try
                 {
                     C.Save("delete from tmpVisits where Vill||Bari||HH = '"+ Vill+BARI+HH +"'");

                     //save visit then continue
                     if(!C.Existence("Select * from tmpVisits where  vill||bari||hh='"+ Vill+BARI+HH+"' and Rnd='"+ ROUNDNO +"'"))
                     {
                         SQL = "Insert into tmpVisits(Vill, Bari, Hh, Resp, EntryUser, EnDt, VDate, Rnd,Lat,Lon,upload,Note)Values(";
                         SQL += "'"+ VILL +"',";
                         SQL += "'"+ BARI +"',";
                         SQL += "'"+ HH +"',";
                         SQL += "'"+ Global.Left(spnResp.getSelectedItem().toString(),2) +"',";
                         SQL += "'"+ g.getUserId() +"',"; //DC code
                         SQL += "'"+ Global.DateConvertYMD(VisitDate.getText().toString()) +"',"; //enter date
                         SQL += "'"+ Global.DateConvertYMD(VisitDate.getText().toString()) +"',"; //date of visit
                         SQL += "'"+ ROUNDNO +"',"; //round
                         SQL += "'"+ Double.toString(currentLatitude) +"',";    //lat
                         SQL += "'"+ Double.toString(currentLongitude) +"',";   //lon
                         SQL += "'"+ txtNote.getText() +"')";
                     }
                     else
                     {
                         SQL = "Update tmpVisits set upload='2',";
                         SQL += " Resp='"+ Global.Left(spnResp.getSelectedItem().toString(),2) +"',";
                         SQL += " VDate='"+ Global.DateConvertYMD(VisitDate.getText().toString()) +"',"; //date of visit
                         SQL += " Note='"+ txtNote.getText() +"',";
                         SQL += " EntryUser='"+ g.getUserId() +"'"; //DC code
                         SQL += " where vill||bari||hh='"+ VILL+BARI+HH +"' and Rnd='"+ ROUNDNO +"'";
                     }
                     C.Save(SQL);

                     String SQLSTR="";
                     //------------------------------------------------------------------
                     SQLSTR = "Insert into tmpHousehold";
                     SQLSTR += "(Vill, Bari, HH, EnType, EnDate, ExType, ExDate, Religion, HHHead, EnDt,Rnd)Values(";
                     SQLSTR += "'"+ Vill +"',";
                     SQLSTR += "'"+ Bari +"',";
                     SQLSTR += "'"+ HH +"',";
                     SQLSTR += "'',"; //EnType
                     SQLSTR += "'',";
                     SQLSTR += "'',"; //ExType
                     SQLSTR += "'',";
                     SQLSTR += "'"+ Global.Left(spnReligion.getSelectedItem().toString(),1) +"',"; //Religion
                     SQLSTR += "'',"; //HH Head
                     SQLSTR += "'"+ Global.DateTimeNowYMDHMS() +"',"; //Enter Date
                     SQLSTR += "'"+ g.getRoundNumber() +"','2','')";   //Round number

                     C.Save(SQLSTR);
                     //------------------------------------------------------------------

                     g.setHouseholdNo(HH);
                 }
                 catch(Exception ex)
                 {
                     Connection.MessageBox(Household_Visit.this, ex.getMessage());
                     return;
                 }

                 //transfer data for events
//                 DataForEvents(Vill+Bari+HH,Rnd,OldNewHH);

                 //call member event form
                 Bundle IDbundle = new Bundle();
                 IDbundle.putString("vill", VILL);
                 IDbundle.putString("bari", BARI);
                 IDbundle.putString("bariname", BName);
                 IDbundle.putString("hhno", HH);
                 IDbundle.putString("vdate", VisitDate.getText().toString());

                 g.setMigVillage("");

                 Intent f11 = new Intent(getApplicationContext(),Member_list.class);
                 f11.putExtras(IDbundle);
                 startActivity(f11);

         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Household_Visit.this, e.getMessage());
         return;
     }
 }


 private void DataSearch(String Vill, String Bari, String HH)
     {
       try
        {
           RadioButton rb;
           Household_DataModel d = new Household_DataModel();
           String SQL = "Select * from "+ TableName +"  Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH + "'";
           List<Household_DataModel> data = d.SelectAll(this, SQL);
           for(Household_DataModel item : data){
             txtVill.setText(item.getVill());
             txtBari.setText(item.getBari());
             txtHH.setText(item.getHH());
             spnReligion.setSelection(Global.SpinnerItemPositionAnyLength(spnReligion, item.getReligion()));
             txtMobileNo1.setText(item.getMobileNo1());
             txtMobileNo2.setText(item.getMobileNo2());
             txtHHHead.setText(item.getHHHead());
//             txtTotMem.setText(item.getTotMem());
//             txtTotRWo.setText(item.getTotRWo());

             //*********************************************sakib*********************************************

               Visits_DataModel d1 = new Visits_DataModel();
               String SQL1 = "Select * from Visits Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"' and Rnd='"+ ROUNDNO +"'";
               List<Visits_DataModel> data1 = d1.SelectAll(this, SQL1);
               for(Visits_DataModel item1 : data1) {
                   txtVill.setText(item1.getVill());
                   txtBari.setText(item1.getBari());
                   txtHH.setText(item1.getHH());
                   dtpVDate.setText(item1.getVDate().toString().length() == 0 ? "" : Global.DateConvertDMY(item1.getVDate()));
                   spnVStatus.setSelection(Global.SpinnerItemPositionAnyLength(spnVStatus, item1.getVStatus()));
                   txtVStatusOth.setText(item1.getVStatusOth());
                   txtNote.setText(item1.getNote());
                   spnResp.setSelection(Global.SpinnerItemPositionAnyLength(spnResp, item1.getResp()));
               }

            //*********************************************sakib**********************************************
//             txtEnType.setText(item.getEnType());
//             dtpEnDate.setText(item.getEnDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getEnDate()));
//             txtExType.setText(item.getExType());
//             dtpExDate.setText(item.getExDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getExDate()));
//             txtRnd.setText(item.getRnd());
           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(Household_Visit.this, e.getMessage());
            return;
        }
     }

 protected Dialog onCreateDialog(int id) {
   final Calendar c = Calendar.getInstance();
   hour = c.get(Calendar.HOUR_OF_DAY);
   minute = c.get(Calendar.MINUTE);
   switch (id) {
       case DATE_DIALOG:
           return new DatePickerDialog(this, mDateSetListener,g.mYear,g.mMonth-1,g.mDay);
       case TIME_DIALOG:
           return new TimePickerDialog(this, timePickerListener, hour, minute,false);
       }
     return null;
 }
//
 private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
      mYear = year; mMonth = monthOfYear+1; mDay = dayOfMonth;
      EditText dtpDate;


              dtpDate = (EditText)findViewById(R.id.dtpEnDate);
             if (VariableID.equals("btnEnDate"))
              {
                  dtpDate = (EditText)findViewById(R.id.dtpEnDate);
              }
             else if (VariableID.equals("btnExDate"))
              {
                  dtpDate = (EditText)findViewById(R.id.dtpExDate);
              }
             else if(VariableID.equals("dtpVDate"))
             {
                 dtpDate= (EditText) findViewById(R.id.dtpVDate);
             }
      dtpDate.setText(new StringBuilder()
      .append(Global.Right("00"+mDay,2)).append("/")
      .append(Global.Right("00"+mMonth,2)).append("/")
      .append(mYear));
      }
  };

 private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
       hour = selectedHour; minute = selectedMinute;
       EditText tpTime;


          //tpTime.setText(new StringBuilder().append(Global.Right("00"+hour,2)).append(":").append(Global.Right("00"+minute,2)));

    }
  };


 //GPS Reading
 //.....................................................................................................
 public void FindLocation() {
 LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

 LocationListener locationListener = new LocationListener() {
     public void onLocationChanged(Location location) {
         updateLocation(location);
     }
     public void onStatusChanged(String provider, int status, Bundle extras) {
     }
     public void onProviderEnabled(String provider) {
     }
     public void onProviderDisabled(String provider) {
     }
   };
  //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
 }

 void updateLocation(Location location) {
     currentLocation  = location;
     currentLatitude  = currentLocation.getLatitude();
     currentLongitude = currentLocation.getLongitude();
 }


 // Method to turn on GPS
 public void turnGPSOn(){
     try
     {
         String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
         if(!provider.contains("gps")){ //if gps is disabled
             final Intent poke = new Intent();
             poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
             poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
             poke.setData(Uri.parse("3"));
             sendBroadcast(poke);
         }
     }
     catch (Exception e) {
     }
 }
 
 // Method to turn off the GPS
 public void turnGPSOff(){
     String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
 
     if(provider.contains("gps")){ //if gps is enabled
         final Intent poke = new Intent();
         poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
         poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
         poke.setData(Uri.parse("3"));
         sendBroadcast(poke);
     }
 }
 
 // turning off the GPS if its in on state. to avoid the battery drain.
 @Override
 protected void onDestroy() {
     // TODO Auto-generated method stub
     super.onDestroy();
     turnGPSOff();
 }
}