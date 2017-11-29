
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
 import android.widget.Toast;

 import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import Common.Connection;
import Common.Global;
 import Utility.MySharedPreferences;

 import static org.icddrb.champsdsssur.R.id.Bari;
 import static org.icddrb.champsdsssur.R.id.BariN;
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
         EditText txtHHNo;
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
     static String TOTALMEM = "0";
     ListView list;
     ImageButton btnVDate;
     EditText VisitDate;
     Spinner BariList;
     MySharedPreferences sp;

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
//       TOTALMEM = IDbundle.getString("totalmem");
       RsNo     = IDbundle.getString("resp");

       sp = new MySharedPreferences();
       ROUNDNO = sp.getValue(this,"roundno");
       CLUSTER = sp.getValue(this,"cluster");
       BLOCK = sp.getValue(this,"block");

       /*ROUNDNO = IDbundle.getString("roundno");
       CLUSTER = IDbundle.getString("cluster");
       BLOCK = IDbundle.getString("block");*/

       OLDNEWHH = IDbundle.getString("OldNew");

       String TotMember = C.ReturnSingleValue("Select COUNT(*)TotMember from Member m where length(exType)=0  and m.Vill='"+ VILL +"' and m.Bari='"+ BARI +"'and m.HH='"+ HH +"' group by m.Vill,m.Bari,m.HH");
       TOTALMEM = TotMember;

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
               adb.setTitle("বাহির");
               adb.setMessage("আপনি কি খানা পরিদর্শন ফরম থেকে বের হতে চান [হ্যাঁ/না]?");
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

//       secVill = (LinearLayout) findViewById(R.id.secVill);
//       lineVill = (View) findViewById(R.id.lineVill);
//       VlblVill = (TextView) findViewById(R.id.VlblVill);
//       txtVill = (EditText) findViewById(R.id.txtVill);
       txtHHNo = (EditText) findViewById(R.id.txtHHNo);

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


       secVStatusOth = (LinearLayout) findViewById(R.id.secVStatusOth);
       lineVStatusOth = (View) findViewById(R.id.lineVStatusOth);
       VlblVStatusOth = (TextView) findViewById(R.id.VlblVStatusOth);
       txtVStatusOth = (EditText) findViewById(R.id.txtVStatusOth);

       secResp = (LinearLayout) findViewById(R.id.secResp);
       lineResp = (View) findViewById(R.id.lineResp);
       VlblResp = (TextView) findViewById(R.id.VlblResp);
       spnResp = (Spinner) findViewById(R.id.spnResp);
//       spnResp.setAdapter(C.getArrayAdapter("Select '' union Select MSlNo||'-'||Name from Member Where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'and ((julianday(date('now'))-julianday(bdate))/365.25)>10 and (extype is null or length(extype)=0)"));

       secNote = (LinearLayout) findViewById(R.id.secNote);
       lineNote = (View) findViewById(R.id.lineNote);
       VlblNote = (TextView) findViewById(R.id.VlblNote);
       txtNote = (EditText) findViewById(R.id.txtNote);

       txtBari.setText(BARI);
       txtBari.setFocusable(false);

       if (HH.equals("")) {
           HH = HHSerial();
       }
       txtHH.setText(HH);
       txtHH.setFocusable(false);

       txtHHNo.setText(VILL+"-"+BARI+"-"+HH);
       txtHHNo.setEnabled(false);
       txtRnd.setEnabled(false);


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


       String SQL = "";
       //g.setRsNo("");

         if (OLDNEWHH.equals("old"))
         {

             lblHeading1.setVisibility(View.GONE);
             lblHeading.setVisibility(View.VISIBLE);

             //Only eligible member from tmpMember Table
             String R = RsNo.length() == 0?"0":RsNo;
             if(Integer.valueOf(R)>=1 & Integer.valueOf(R)<=76)
             {
                 SQL  =" Select ' ' union";
                 SQL +=" Select '77-সমগ্র পরিবার অন্যত্র  চলেগেছে' union";
                 SQL +=" Select (MSlNo||'-'||Name)  from Member where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and ((julianday(date('now'))-julianday(BDate))/365.25)>10 and (ExType is null or length(ExType)=0)";
             }
//             else if(TOTALMEM.equals("0") & (RsNo.equals("77")|RsNo.equals("88")|RsNo.equals("99")))
             else if(TOTALMEM.equals("0") & (RsNo.equals("77")|RsNo.equals("88")))
             {
                 SQL = " Select ' ' union";
                 SQL += " Select (MSlNo||'-'||Name)  from Member where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and ((julianday(date('now'))-julianday(BDate))/365.25)>10 and (ExType is null or length(ExType)=0) union";
                 SQL += " Select '00-অনিবার্য পরিস্থিতির কারণে পরিদর্শন করা হয়নি' union";
                 SQL += " Select '77-সমগ্র পরিবার অন্যত্র  চলেগেছে' union";
                 SQL += " Select '88-ইন্টারভিউ দিতে রাজী নয়' union";
                 SQL += " Select '99-খানার সকল সদস্য অনুপস্থিত'";
             }
             else if(TOTALMEM.equals("0") | TOTALMEM.equals(""))
             {
                 SQL = " Select ' ' union";
                 SQL += " Select '01-Member 1' union";
                 SQL += " Select '02-Member 2' union";
                 SQL += " Select '03-Member 3' union";
                 SQL += " Select '04-Member 4' union";
                 SQL += " Select '05-Member 5' union";
                 SQL += " Select '06-Member 6' union";
                 SQL += " Select '00-অনিবার্য পরিস্থিতির কারণে পরিদর্শন করা হয়নি' union";
                 SQL += " Select '88-ইন্টারভিউ দিতে রাজী নয়' union";
                 SQL += " Select '99-খানার সকল সদস্য অনুপস্থিত'";
             }
             else
             {
                 SQL = " Select ' ' union";
                 SQL += " Select (MSlNo||'-'||Name)  from Member where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and ((julianday(date('now'))-julianday(BDate))/365.25)>10 and (ExType is null or length(ExType)=0) union";
                 SQL += " Select '00-অনিবার্য পরিস্থিতির কারণে পরিদর্শন করা হয়নি' union";
                 SQL += " Select '77-সমগ্র পরিবার অন্যত্র  চলেগেছে' union";
                 SQL += " Select '88-ইন্টারভিউ দিতে রাজী নয়' union";
                 SQL += " Select '99-খানার সকল সদস্য অনুপস্থিত'";
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

         /*for(int i=1;i<spnResp.getCount();i++)
         {
             if(RsNo.equals(Global.Left(spnResp.getItemAtPosition(i).toString(),2)))
             {
                 spnResp.setSelection(i);
                 i=spnResp.getCount();
             }
         }*/

       DataSearch(VILL, BARI, HH);
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Household_Visit.this, e.getMessage());
         return;
     }
 }
     private String HHSerial()
     {
         String SL = C.ReturnSingleValue("Select (ifnull(max(cast(HH as int)),0)+1)SL from Household where Vill='"+ VILL +"' and Bari='"+BARI+"'"); //where ParticipantID='"+ ParticipantID +"'");
         return Global.Right("00"+SL,2);
     }

 private void DataSave()
 {
   try
     {
         String DV="";

         if(txtHHHead.getText().toString().length()==0 & secHHHead.isShown())
         {
             Connection.MessageBox(Household_Visit.this, "খানা প্রধানের নাম খালি রাখা যাবেনা.");
             txtHHHead.requestFocus();
             return;
         }
         else if(spnReligion.getSelectedItemPosition()==0  & secReligion.isShown())
           {
             Connection.MessageBox(Household_Visit.this, "ধর্ম খালি রাখা যাবেনা.");
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
             Connection.MessageBox(Household_Visit.this, "উত্তরদাতা খালি রাখা যাবেনা.");
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
         sp.save(this,"visitdate", Global.DateConvertYMD(dtpVDate.getText().toString()));

        //*************************************************************************************

         if(OLDNEWHH.equalsIgnoreCase("old"))
         {
             final int Resp = Integer.parseInt(Global.Left(spnResp.getSelectedItem().toString(),2));
             g.setRsNo(Global.Left(spnResp.getSelectedItem().toString(),2));

             if(Resp >= 1 & Resp <= 76)
             {
                 String SQL = "";
                 TransferDataToTemp();

                 if (!C.Existence("Select * from tmpVisits where vill||bari||hh='" + VILL + BARI + HH + "' and Rnd='" + ROUNDNO + "'")) {
                     SQL = "Insert into tmpVisits(Vill, Bari, HH, VDate, Resp, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, Note)Values(";
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
                     SQL += "'" + MySharedPreferences.getValue(Household_Visit.this,"lat") + "',"; // Lat
                     SQL += "'" + MySharedPreferences.getValue(Household_Visit.this,"lon") + "',"; // Lon

//                     SQL += "'" + Double.toString(currentLatitude) + "',"; // Lat
//                     SQL += "'" + Double.toString(currentLongitude) + "',"; // Lon
                     SQL += "'" + Global.DateTimeNowYMDHMS() + "',"; //EnDt Date
                     SQL += "'" + 2 + "',"; //Upload
                     SQL += "'" + txtNote.getText() + "')";
                 } else {
                     SQL = "Update tmpVisits set upload='2',";
                     SQL += " Resp='" + Global.Left(spnResp.getSelectedItem().toString(), 2) + "',";
                     SQL += " VDate='" + Global.DateConvertYMD(dtpVDate.getText().toString()) + "',"; //date of visit
                     SQL += " Note='" + txtNote.getText() + "',";
                     SQL += " EntryUser='" + ENTRYUSER + "'"; //EntryUser code
                     SQL += " where vill||bari||hh='" + VILL + BARI + HH + "' and Rnd='" + ROUNDNO + "'";
                 }
                 C.Save(SQL);

                 C.Save("Update tmpHousehold set upload='2',HHHead='" + txtHHHead.getText() + "',Note='" + txtNote.getText() + "',MobileNo1='" + txtMobileNo1.getText() + "',MobileNo2='" + txtMobileNo2.getText() + "',Religion='" + Global.Left(spnReligion.getSelectedItem().toString(), 1) + "' where vill||bari||hh='" + (VILL + BARI + HH) + "'");

                 finish();
                 /*Intent f1;
                 f1 = new Intent(getApplicationContext(), Member_list.class);
                 f1.putExtras(IDbundle);
                 startActivity(f1);*/
                 Intent f1;
                 f1 = new Intent(getApplicationContext(), Member_list.class);
                 f1.putExtras(IDbundle);
                 startActivityForResult(f1, 2);

                /* AlertDialog.Builder adb = new AlertDialog.Builder(Household_Visit.this);
                 adb.setTitle("Close");
                 adb.setMessage("এই খানায় কি কোন ধরনের ইভেন্ট পরিবর্তন হয়েছে[হ্যাঁ/না]?");

                 //have no events
                 //-----------------------------------------------------------------
                 adb.setNegativeButton("না", new AlertDialog.OnClickListener()
                 {
                     public void onClick(DialogInterface dialog, int which)
                     {
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
                                 SQL += "'" + Global.DateTimeNowYMDHMS() + "',"; //EnDt Date
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


                 //have events
                 //-----------------------------------------------------------------
                 adb.setPositiveButton("হ্যাঁ", new AlertDialog.OnClickListener()
                 {
                     public void onClick(DialogInterface dialog, int which)
                     {
                         String SQL = "";
                         TransferDataToTemp();

                             if (!C.Existence("Select * from tmpVisits where vill||bari||hh='" + VILL + BARI + HH + "' and Rnd='" + ROUNDNO + "'")) {
                                 SQL = "Insert into tmpVisits(Vill, Bari, HH, VDate, Resp, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, Note)Values(";
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
                                 SQL += "'" + Global.DateTimeNowYMDHMS() + "',"; //EnDt Date
                                 SQL += "'" + 2 + "',"; //Upload
                                 SQL += "'" + txtNote.getText() + "')";
                             } else {
                                 SQL = "Update tmpVisits set upload='2',";
                                 SQL += " Resp='" + Global.Left(spnResp.getSelectedItem().toString(), 2) + "',";
                                 SQL += " VDate='" + Global.DateConvertYMD(dtpVDate.getText().toString()) + "',"; //date of visit
                                 SQL += " Note='" + txtNote.getText() + "',";
                                 SQL += " EntryUser='" + ENTRYUSER + "'"; //EntryUser code
                                 SQL += " where vill||bari||hh='" + VILL + BARI + HH + "' and Rnd='" + ROUNDNO + "'";
                             }
                             C.Save(SQL);

                             C.Save("Update tmpHousehold set upload='2',HHHead='" + txtHHHead.getText() + "',Note='" + txtNote.getText() + "',MobileNo1='" + txtMobileNo1.getText() + "',MobileNo2='" + txtMobileNo2.getText() + "',Religion='" + Global.Left(spnReligion.getSelectedItem().toString(), 1) + "' where vill||bari||hh='" + (VILL + BARI + HH) + "'");


                             finish();
                         Intent f1;
                         f1 = new Intent(getApplicationContext(), Member_list.class);
                         f1.putExtras(IDbundle);
                         startActivity(f1);
                         //startActivityForResult(f1, 1);
                     }
                 });

                 adb.show();*/
             }

             else if(Resp == 77)
             {
                 String SQL = "";
                 TransferDataToTemp();

                 if (!C.Existence("Select * from tmpVisits where vill||bari||hh='" + VILL + BARI + HH + "' and Rnd='" + ROUNDNO + "'")) {
                     SQL = "Insert into tmpVisits(Vill, Bari, HH, VDate, Resp, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, Note)Values(";
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
                     SQL += "'" + MySharedPreferences.getValue(Household_Visit.this,"lat") + "',"; // Lat
                     SQL += "'" + MySharedPreferences.getValue(Household_Visit.this,"lon") + "',"; // Lon

//                     SQL += "'" + Double.toString(currentLatitude) + "',"; // Lat
//                     SQL += "'" + Double.toString(currentLongitude) + "',"; // Lon
                     SQL += "'" + Global.DateTimeNowYMDHMS() + "',"; //EnDt Date
                     SQL += "'" + 2 + "',"; //Upload
                     SQL += "'" + txtNote.getText() + "')";
                 } else {
                     SQL = "Update tmpVisits set upload='2',";
                     SQL += " Resp='" + Global.Left(spnResp.getSelectedItem().toString(), 2) + "',";
                     SQL += " VDate='" + Global.DateConvertYMD(dtpVDate.getText().toString()) + "',"; //date of visit
                     SQL += " Note='" + txtNote.getText() + "',";
                     SQL += " EntryUser='" + ENTRYUSER + "'"; //EntryUser code
                     SQL += " where vill||bari||hh='" + VILL + BARI + HH + "' and Rnd='" + ROUNDNO + "'";
                 }
                 C.Save(SQL);

                 C.Save("Update tmpHousehold set upload='2',HHHead='" + txtHHHead.getText() + "',Note='" + txtNote.getText() + "',MobileNo1='" + txtMobileNo1.getText() + "',MobileNo2='" + txtMobileNo2.getText() + "',Religion='" + Global.Left(spnReligion.getSelectedItem().toString(), 1) + "' where vill||bari||hh='" + (VILL + BARI + HH) + "'");


                 finish();
                 /*Intent f1;
                 f1 = new Intent(getApplicationContext(), Member_list.class);
                 f1.putExtras(IDbundle);
                 startActivity(f1);*/

                 Intent f1;
                 f1 = new Intent(getApplicationContext(), Member_list.class);
                 f1.putExtras(IDbundle);
                 startActivityForResult(f1, 2);
             }
             else if(Resp == 0 | Resp == 88 | Resp == 99)
             {
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
                         SQL += "'" + MySharedPreferences.getValue(Household_Visit.this,"lat") + "',"; // Lat
                         SQL += "'" + MySharedPreferences.getValue(Household_Visit.this,"lon") + "',"; // Lon

//                         SQL += "'" + Double.toString(currentLatitude) + "',"; // Lat
//                         SQL += "'" + Double.toString(currentLongitude) + "',"; // Lon
                         SQL += "'" + Global.DateTimeNowYMDHMS() + "',"; //EnDt Date
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

                 } catch (Exception ex) {
                     Connection.MessageBox(Household_Visit.this, ex.getMessage());
                     return;
                 }
                 Intent returnIntent = new Intent();
                 returnIntent.putExtra("res", "hh");
                 setResult(Activity.RESULT_OK, returnIntent);
                 Connection.MessageBox(Household_Visit.this, "Saved Successfully");
                 finish();
             }
          }

         //For New household--------------------------------------------------------------
         else  if(OLDNEWHH.equalsIgnoreCase("new"))
         {
             final int Resp = Integer.parseInt(Global.Left(spnResp.getSelectedItem().toString(),2));
                g.setRsNo(String.valueOf(Resp));

                 String SQL = "";
                 try {
                     //save visit then continue
                     if (!C.Existence("Select * from tmpVisits where  vill||bari||hh='" + Vill + BARI + HH + "' and Rnd='" + ROUNDNO + "'")) {
                         SQL = "Insert into tmpVisits(Vill, Bari, HH, VDate, Resp, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, Note)Values(";
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
                         SQL += "'" + MySharedPreferences.getValue(Household_Visit.this,"lat") + "',"; // Lat
                         SQL += "'" + MySharedPreferences.getValue(Household_Visit.this,"lon") + "',"; // Lon

//                         SQL += "'" + Double.toString(currentLatitude) + "',"; // Lat
//                         SQL += "'" + Double.toString(currentLongitude) + "',"; // Lon
                         SQL += "'" + Global.DateTimeNowYMDHMS() + "',"; //EnDt Date
                         SQL += "'" + 2 + "',"; //Upload
                         SQL += "'" + txtNote.getText() + "')";
                     } else {
                         SQL = "Update tmpVisits set upload='2',";
                         SQL += " Resp='" + Global.Left(spnResp.getSelectedItem().toString(), 2) + "',";
                         SQL += " VDate='" + Global.DateConvertYMD(dtpVDate.getText().toString()) + "',"; //date of visit
                         SQL += " Note='" + txtNote.getText() + "',";
                         SQL += " EntryUser='" + ENTRYUSER + "'"; //EntryUser code
                         SQL += " where vill||bari||hh='" + VILL + BARI + HH + "' and Rnd='" + ROUNDNO + "'";
                     }
                     C.Save(SQL);

                     String SQLSTR = "";
                     //------------------------------------------------------------------
                     SQLSTR = "Insert into tmpHousehold";
                     SQLSTR += "(Vill, Bari, HH, EnType, EnDate, ExType, ExDate, Religion, MobileNo1, MobileNo2, HHHead, Rnd,StartTime, EndTime, DeviceID, EntryUser, Lat, Lon,EnDt,Upload)Values(";
                     SQLSTR += "'" + VILL + "',";
                     SQLSTR += "'" + BARI + "',";
                     SQLSTR += "'" + HH + "',";
                     SQLSTR += "'',"; //EnType
                     SQLSTR += "'"+ Global.DateConvertYMD(dtpVDate.getText().toString()) +"',";
                     SQLSTR += "'',"; //ExType
                     SQLSTR += "'',";
                     SQLSTR += "'" + Global.Left(spnReligion.getSelectedItem().toString(), 1) + "',"; //Religion
                     SQLSTR += "'" + txtMobileNo1.getText() + "',";
                     SQLSTR += "'" + txtMobileNo2.getText() + "',";
                     SQLSTR += "'" + txtHHHead.getText() + "',";
                     SQLSTR += "'" + ROUNDNO + "',"; //round
                     SQLSTR += "'" + STARTTIME + "',"; //StartTime
                     SQLSTR += "'" + g.CurrentTime24() + "',"; //EndTime
                     SQLSTR += "'" + DEVICEID + "',"; //DeviceID
                     SQLSTR += "'" + ENTRYUSER + "',"; //EntryUser code
                     SQLSTR += "'" + MySharedPreferences.getValue(Household_Visit.this,"lat") + "',"; // Lat
                     SQLSTR += "'" + MySharedPreferences.getValue(Household_Visit.this,"lon") + "',"; // Lon

//                     SQLSTR += "'" + Double.toString(currentLatitude) + "',"; // Lat
//                     SQLSTR += "'" + Double.toString(currentLongitude) + "',"; // Lon
                     SQLSTR += "'" + Global.DateTimeNowYMDHMS() + "',"; //EnDt Date
                     SQLSTR += "'" + 2 + "')"; //Upload

                     C.Save(SQLSTR);

                     Bundle IDbundle = new Bundle();
                     IDbundle.putString("Vill", VILL);
                     IDbundle.putString("Bari", BARI);
                     IDbundle.putString("BariName", BName);
                     IDbundle.putString("HHHead",txtHHHead.getText().toString());
                     IDbundle.putString("HH", HH);
                     IDbundle.putString("totalmember", "0");
                     IDbundle.putString("VDate", dtpVDate.getText().toString());

                     g.setMigVillage("");
                     g.setHouseholdNo(HH);
                     /*Intent f2 = new Intent(getApplicationContext(),Member_list.class);
                     f2.putExtras(IDbundle);
                     startActivity(f2);*/

                     Intent f1;
                     f1 = new Intent(getApplicationContext(), Member_list.class);
                     f1.putExtras(IDbundle);
                     startActivityForResult(f1, 2);

                 } catch (Exception ex) {
                     Connection.MessageBox(Household_Visit.this, ex.getMessage());
                     return;
                 }

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
//             txtVill.setText(item.getVill());
             txtBari.setText(item.getBari());
             txtHH.setText(item.getHH());
             spnReligion.setSelection(Global.SpinnerItemPositionAnyLength(spnReligion, item.getReligion()));
             txtMobileNo1.setText(item.getMobileNo1());
             txtMobileNo2.setText(item.getMobileNo2());
             txtHHHead.setText(item.getHHHead());
             txtNote.setText(item.getNote());
//             txtTotMem.setText(item.getTotMem());
//             txtTotRWo.setText(item.getTotRWo());

             //*********************************************sakib*********************************************

               Visits_DataModel d1 = new Visits_DataModel();
               String SQL1 = "Select Vill,Bari,HH,VDate,ifnull(VStatus,'')VStatus,ifnull(VStatusOth,'')VStatusOth,Note,ifnull(Resp,'')Resp,Rnd,StartTime,Endtime,DeviceID,EntryUser,Lat,Lon,EnDt from Visits Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"' and Rnd='"+ ROUNDNO +"'";
               List<Visits_DataModel> data1 = d1.SelectAll(this, SQL1);
               for(Visits_DataModel item1 : data1) {
//                   txtVill.setText(item1.getVill());
                   txtBari.setText(item1.getBari());
                   txtHH.setText(item1.getHH());
                   dtpVDate.setText(item1.getVDate().toString().length() == 0 ? "" : Global.DateConvertDMY(item1.getVDate()));
                   spnVStatus.setSelection(Global.SpinnerItemPositionAnyLength(spnVStatus, item1.getVStatus()));
                   txtVStatusOth.setText(item1.getVStatusOth());
                   //txtNote.setText(item1.getNote());
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


 private void TransferDataToTemp()
 {
     C.Save("Delete from tmpHousehold");
     C.Save("Delete from tmpVisits");
     C.Save("Delete from tmpMember");
     C.Save("Delete from tmpSES");
     C.Save("Delete from tmpPregHis");
     C.Save("Delete from tmpEvents");

     //-- -tmpHousehold Information-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     String SQL = "";
     SQL = "Insert into tmpHousehold(Vill, Bari, HH, Religion, MobileNo1, MobileNo2, HHHead, TotMem, TotRWo, EnType, EnDate, ExType, ExDate, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, Note)";
     SQL += " Select Vill, Bari, HH, Religion, MobileNo1, MobileNo2, HHHead, TotMem, TotRWo, EnType, EnDate, ExType, ExDate, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, Note from Household";
     SQL += " where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'";
     C.Save(SQL);

     //-- -tmpVisits Information-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     SQL = " Insert into tmpVisits";
     SQL += " (Vill, Bari, HH, VDate, VStatus, VStatusOth, Resp, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload)";
     SQL += " Select Vill, Bari, HH, VDate, VStatus, VStatusOth, Resp, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload from Visits";
     SQL += " where  Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'";
     C.Save(SQL);

     //-- -tmpMember Information-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     SQL = " Insert into tmpMember";
     SQL += " (Vill, Bari, HH, MSlNo, PNo, Name, Rth, Sex, BDate, AgeY, MoNo, FaNo, Edu, MS,Pstat,LmpDt, Ocp, Sp1, Sp2, Sp3, Sp4, EnType, EnDate, ExType, ExDate,PosMig,PosMigDate, NeedReview, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload)";
     SQL += " Select Vill, Bari, HH, MSlNo, PNo, Name, Rth, Sex, BDate, AgeY, MoNo, FaNo, Edu, MS,Pstat,LmpDt, Ocp, Sp1, Sp2, Sp3, Sp4, EnType, EnDate, ExType, ExDate,PosMig,PosMigDate, NeedReview, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload from Member";
     SQL += " where  Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'";
     C.Save(SQL);

     //-- -tmpSES Information-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     SQL = " Insert into tmpSES";
     SQL += " (Vill, Bari, HH, SESNo, VDate, VStatus, VStatusOth, Rnd, WSDrink, WSDrinkOth, WSCook, WSCookOth, WSWash, WSWashOth, Latrine, LatrineOth,";
     SQL += " Electricity, Radio, TV, Mobile, Telephone, Refrige, Watch, ElecFan, RickVan, Bicycle, MotCycle, Computer, Buffalo, Bull, Goat, Chicken, Pigeon,";
     SQL += " Roof, RoofOth, Wall, WallOth, Floor, FloorOth, Homestead, HomesteadOth, OthLand, StartTime, EndTime,";
     SQL += " DeviceID, EntryUser, Lat, Lon, EnDt, Upload)";

     SQL += " Select Vill, Bari, HH, SESNo, VDate, VStatus, VStatusOth, Rnd, WSDrink, WSDrinkOth, WSCook, WSCookOth, WSWash, WSWashOth, Latrine, LatrineOth,";
     SQL += " Electricity, Radio, TV, Mobile, Telephone, Refrige, Watch, ElecFan, RickVan, Bicycle, MotCycle, Computer, Buffalo, Bull, Goat, Chicken, Pigeon,";
     SQL += " Roof, RoofOth, Wall, WallOth, Floor, FloorOth, Homestead, HomesteadOth, OthLand, StartTime, EndTime,";
     SQL += " DeviceID, EntryUser, Lat, Lon, EnDt, Upload from SES";
     SQL += " where  Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'";
     C.Save(SQL);

     //-- -tmpPregHis. History-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     SQL = " Insert into tmpPregHis";
     SQL += " (Vill, Bari, HH, MSlNo, PNo, VDate, VStatus, VStatusOth, MarriageStatus, MarMon, MarYear, MarDK, GaveBirth, ChildLivWWo,";
     SQL += " SonLivWWo, DaugLivWWo, ChldLivOut, SonLivOut, DaugLivOut, ChldDie, BoyDied, GirlDied, NotLivBrth, TotLB, TotPregOut,";
     SQL += " CurPreg, LMPDate, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload)";

     SQL += " Select Vill, Bari, HH, MSlNo, PNo, VDate, VStatus, VStatusOth, MarriageStatus, MarMon, MarYear, MarDK, GaveBirth, ChildLivWWo,";
     SQL += " SonLivWWo, DaugLivWWo, ChldLivOut, SonLivOut, DaugLivOut, ChldDie, BoyDied, GirlDied, NotLivBrth, TotLB, TotPregOut,";
     SQL += " CurPreg, LMPDate, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload from PregHis";
     SQL += " where  Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'";

     C.Save(SQL);

     //-- -tmpEvents Information-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     /*SQL = " Insert into tmpEvents";
     SQL += " (Vill, Bari, HH, MSlNo, PNo, EvType, EvDate, Info1, Info2, Info3, Info4, VDate, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload)";
     SQL += " Select Vill, Bari, HH, MSlNo, PNo, EvType, EvDate, Info1, Info2, Info3, Info4, VDate, Rnd, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload from Events";
     SQL += " where  Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'";
     C.Save(SQL);*/
 }


}