
 package org.icddrb.champsdsssur;


 //Android Manifest Code
 //<activity android:name=".Events" android:label="Events" />

 import android.app.Activity;
 import android.app.AlertDialog;
 import android.app.DatePickerDialog;
 import android.app.Dialog;
 import android.app.TimePickerDialog;
 import android.content.Context;
 import android.content.DialogInterface;
 import android.content.Intent;
 import android.database.Cursor;
 import android.location.Location;
 import android.location.LocationListener;
 import android.location.LocationManager;
 import android.net.Uri;
 import android.os.Bundle;
 import android.provider.Settings;
 import android.text.Editable;
 import android.text.InputFilter;
 import android.text.TextWatcher;
 import android.view.Gravity;
 import android.view.KeyEvent;
 import android.view.LayoutInflater;
 import android.view.MotionEvent;
 import android.view.View;
 import android.view.ViewGroup;
 import android.view.Window;
 import android.view.WindowManager;
 import android.widget.AdapterView;
 import android.widget.ArrayAdapter;
 import android.widget.BaseAdapter;
 import android.widget.Button;
 import android.widget.CheckBox;
 import android.widget.DatePicker;
 import android.widget.EditText;
 import android.widget.ImageButton;
 import android.widget.LinearLayout;
 import android.widget.ListView;
 import android.widget.RadioButton;
 import android.widget.RadioGroup;
 import android.widget.SimpleAdapter;
 import android.widget.Spinner;
 import android.widget.TextView;
 import android.widget.TimePicker;

 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.List;

 import Common.Connection;
 import Common.Global;
 import Utility.MySharedPreferences;

 public class Events_NewMem extends Activity {
    boolean networkAvailable=false;
    Location currentLocation;
    double currentLatitude,currentLongitude;

     SimpleAdapter eList;
     ArrayList<HashMap<String, String>> evmylist = new ArrayList<HashMap<String, String>>();


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
         TextView lblHeading;
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
         LinearLayout secMSlNo;
         View lineMSlNo;
         TextView VlblMSlNo;
         EditText txtMSlNo;
         LinearLayout secPNo;
         View linePNo;
         TextView VlblPNo;
         EditText txtPNo;
         LinearLayout secEvType;
         View lineEvType;
         TextView VlblEvType;
         Spinner spnEvType;
         LinearLayout secEvDate;
         View lineEvDate;
         TextView VlblEvDate;
         EditText dtpEvDate;
         LinearLayout secInfo1;
         View lineInfo1;
         TextView VlblInfo1;
         EditText txtInfo1;
         Spinner spnInfo1;
         Spinner spnInfo2;
         Spinner spnInfo3;
         Spinner spnInfo4;
         LinearLayout secInfo2;
         View lineInfo2;
         TextView VlblInfo2;
         TextView VlblOth;
         EditText txtInfo2;
         LinearLayout secInfo3;
         View lineInfo3;
         TextView VlblInfo3;
         EditText txtInfo3;
         LinearLayout secInfo4;
         View lineInfo4;
         TextView VlblInfo4;
         EditText txtInfo4;
         LinearLayout secVDate;
         View lineVDate;
         TextView VlblVDate;
         EditText dtpVDate;
         LinearLayout secRnd;
         View lineRnd;
         TextView VlblRnd;
         EditText txtRnd;
         CheckBox chkNeedReview;
//        LinearLayout formMember;


     //Member
     LinearLayout secName;
     View lineName;
     TextView VlblName;
     EditText txtName;



    static String TableName;

    static String STARTTIME = "";
    static String DEVICEID  = "";
    static String ENTRYUSER = "";
    MySharedPreferences sp;

    Bundle IDbundle;
    static String VILL = "";
    static String BARI = "";
    static String HH = "";
    static String MSLNO = "";
    static String EVDATE = "";

    static String EVTYPE = "";
    static String OLDNEWHH = "";

     static String ROUNDNO = "";
     static String CLUSTER = "";
     static String BLOCK   = "";

 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.events_newmem);
         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = sp.getValue(this, "deviceid");
         ENTRYUSER = sp.getValue(this, "userid");
         CLUSTER   = sp.getValue(this, "cluster");
         BLOCK     = sp.getValue(this, "block");
         ROUNDNO   = sp.getValue(this, "roundno");

         IDbundle = getIntent().getExtras();
         VILL = IDbundle.getString("Vill");
         BARI = IDbundle.getString("Bari");
         HH = IDbundle.getString("HH");
         MSLNO = IDbundle.getString("MSlNo");
         EVTYPE = IDbundle.getString("EvType");
         EVDATE = IDbundle.getString("EvDate");
         OLDNEWHH = IDbundle.getString("OldNew");

         TableName = "Events";

         //turnGPSOn();

         //GPS Location
         //FindLocation();
         // Double.toString(currentLatitude);
         // Double.toString(currentLongitude);
         final Dialog dialog = new Dialog(Events_NewMem.this);

         lblHeading = (TextView)findViewById(R.id.lblHeading);
//         formMember = (LinearLayout)findViewById(R.id.formMember);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(Events_NewMem.this);
                 adb.setTitle("Close");
                 adb.setMessage("Do you want to close this form[Yes/No]?");
                 adb.setNegativeButton("No", null);
                 adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         finish();
                     }});
                 adb.show();
             }});

         spnInfo1 = (Spinner)findViewById(R.id.spnInfo1);
         spnInfo2 = (Spinner)findViewById(R.id.spnInfo2);
         spnInfo3 = (Spinner)findViewById(R.id.spnInfo3);
         spnInfo4 = (Spinner)findViewById(R.id.spnInfo4);

         secVill=(LinearLayout)findViewById(R.id.secVill);
         lineVill=(View)findViewById(R.id.lineVill);
         VlblVill=(TextView) findViewById(R.id.VlblVill);
         txtVill=(EditText) findViewById(R.id.txtVill);
         secBari=(LinearLayout)findViewById(R.id.secBari);
         lineBari=(View)findViewById(R.id.lineBari);
         VlblBari=(TextView) findViewById(R.id.VlblBari);
         txtBari=(EditText) findViewById(R.id.txtBari);

         secHH=(LinearLayout)findViewById(R.id.secHH);
         lineHH=(View)findViewById(R.id.lineHH);
         VlblHH=(TextView) findViewById(R.id.VlblHH);
         txtHH=(EditText) findViewById(R.id.txtHH);

         secMSlNo=(LinearLayout)findViewById(R.id.secMSlNo);
         lineMSlNo=(View)findViewById(R.id.lineMSlNo);
         VlblMSlNo=(TextView) findViewById(R.id.VlblMSlNo);
         txtMSlNo=(EditText) findViewById(R.id.txtMSlNo);

         if (OLDNEWHH.equals("new"))
             txtMSlNo.setText(MemNo(VILL,BARI,HH));
         else
             txtMSlNo.setText(MSLNO);
         txtMSlNo.setEnabled(false);

         secName=(LinearLayout)findViewById(R.id.secName);
         VlblName   =(TextView) findViewById(R.id.VlblName);
         txtName=(EditText) findViewById(R.id.txtName);


         secPNo=(LinearLayout)findViewById(R.id.secPNo);
         linePNo=(View)findViewById(R.id.linePNo);
         VlblPNo=(TextView) findViewById(R.id.VlblPNo);
         txtPNo=(EditText) findViewById(R.id.txtPNo);
         txtPNo.setText(VILL.toString()+BARI.toString()+HH.toString()+txtMSlNo.getText().toString());

         secEvType=(LinearLayout)findViewById(R.id.secEvType);
         lineEvType=(View)findViewById(R.id.lineEvType);
         VlblEvType=(TextView) findViewById(R.id.VlblEvType);
         spnEvType=(Spinner) findViewById(R.id.spnEvType);

         chkNeedReview = (CheckBox)findViewById(R.id.chkNeedReview);

         final Spinner EvType = (Spinner)findViewById(R.id.spnEvType);



         String Sex = "";
         String MS  = "";
         String PS  = "";
         int MAge   = 0;

         //New Member

         if (OLDNEWHH.equals("new")) {
             EvType.setAdapter(C.getArrayAdapter("Select distinct '  'EV from EventCode union SELECT (EvType||'-'||EvName)Ev FROM EventCode where EvType in('20','21','22','23','25')"));

             String ECode = spnEvType.getSelectedItem().toString().substring(0, 2);

             if(ECode.equals("21") )
             {
                 dtpEvDate.setVisibility(View.VISIBLE);
                 txtInfo1.setVisibility(View.GONE);
                 txtInfo2.setVisibility(View.GONE);
                 txtInfo3.setVisibility(View.GONE);
                 txtInfo4.setVisibility(View.GONE);
                 dtpVDate.setVisibility(View.VISIBLE);
             }
             else if(ECode.equals("22"))
             {
                 dtpEvDate.setVisibility(View.VISIBLE);
                 txtInfo1.setVisibility(View.GONE);
                 txtInfo2.setVisibility(View.GONE);
                 txtInfo3.setVisibility(View.GONE);
                 txtInfo4.setVisibility(View.GONE);
                 dtpVDate.setVisibility(View.VISIBLE);
             }
             else if(ECode.equals("23"))
             {
                 dtpEvDate.setVisibility(View.VISIBLE);
                 txtInfo1.setVisibility(View.GONE);
                 txtInfo2.setVisibility(View.GONE);
                 txtInfo3.setVisibility(View.GONE);
                 txtInfo4.setVisibility(View.GONE);
                 dtpVDate.setVisibility(View.VISIBLE);
             }

         }

         //Old Member
         if (OLDNEWHH.equals("old"))
         {
             Cursor cur = C.ReadData("Select cast(((julianday(date('now'))-julianday(bdate))/365.25)as int)MAge,Sex,MS from tmpMember where  vill || bari || hh || MSLno='"+ (VILL+BARI+HH+MSLNO) +"'");
             cur.moveToFirst();
             while(!cur.isAfterLast())
             {
                 MAge = Integer.valueOf(cur.getString(0));
                 Sex  = cur.getString(1).toString();
                 MS   = cur.getString(2).toString();
                 cur.moveToNext();
             }
             cur.close();

             //1-Sex wise event
             if(Sex.equals("1"))
             {
                 //MS
                 if(MS.equals("30"))
                     EvType.setAdapter(C.getArrayAdapter("Select distinct '  'EV from EventCode union SELECT (EvType||'-'||EVName)Ev FROM EventCode where EvType not in('20','21','22','23','24','25','26','30','32','33','34','40','41','42','43','44','49','56','57','63')"));
                 else if(MS.equals("31"))
                     EvType.setAdapter(C.getArrayAdapter("Select distinct '  'EV from EventCode union SELECT (EvType||'-'||EVName)Ev FROM EventCode where EvType not in('20','21','22','23','24','25','26','30','31','40','41','42','43','44','49','56','57')"));
                 else if(MS.equals("33"))
                     EvType.setAdapter(C.getArrayAdapter("Select distinct '  'EV from EventCode union SELECT (EvType||'-'||EVName)Ev FROM EventCode where EvType not in('20','21','22','23','24','25','26','30','32','33','34','40','41','42','43','44','49','56','57')"));

                 else
                     EvType.setAdapter(C.getArrayAdapter("Select distinct '  'EV from EventCode union SELECT (EvType||'-'||EVName)Ev FROM EventCode where EvType not in('20','21','22','23','24','25','26','30','40','41','42','43','44','49','56','57')"));
             }
             else if(Sex.equals("2"))
             {
                 //MS
                 if(MS.equals("30"))
                     EvType.setAdapter(C.getArrayAdapter("Select distinct '  'EV from EventCode union SELECT (EvType||'-'||EVName)Ev FROM EventCode where EvType not in('20','21','22','23','24','25','26','30','32','33','34','41','42','43','44','56','57','63')"));
                     //married and pregnant
                 else if(MS.equals("31") & PS.equals("41"))
                     EvType.setAdapter(C.getArrayAdapter("Select distinct '  'EV from EventCode union SELECT (EvType||'-'||EVName)Ev FROM EventCode where EvType not in('20','21','22','23','24','25','26','30','31','41','43','44','56','57')"));
                     //married and not pregnant
                 else if(MS.equals("31") & !PS.equals("41"))
                     EvType.setAdapter(C.getArrayAdapter("Select distinct '  'EV from EventCode union SELECT (EvType||'-'||EVName)Ev FROM EventCode where EvType not in('20','21','22','23','24','25','26','30','31','42','43','44','56','57')"));
                     //widowed but pregnant
                 else if(MS.equals("33"))
                     EvType.setAdapter(C.getArrayAdapter("Select distinct '  'EV from EventCode union SELECT (EvType||'-'||EVName)Ev FROM EventCode where EvType not in('20','21','22','23','24','25','26','30','32','33','34','41','43','44','56','57')"));
                     //widowed not pregnant
                 else if(MS.equals("33"))
                     EvType.setAdapter(C.getArrayAdapter("Select distinct '  'EV from EventCode union SELECT (EvType||'-'||EVName)Ev FROM EventCode where EvType not in('20','21','22','23','24','25','26','30','32','33','34','41','42','43','44','56','57')"));

                 else
                     EvType.setAdapter(C.getArrayAdapter("Select distinct '  'EV from EventCode union SELECT (EvType||'-'||EVName)Ev FROM EventCode where EvType not in('20','21','22','23','24','25','26','30','43','44','56','57')"));
             }
         }

         secEvDate=(LinearLayout)findViewById(R.id.secEvDate);
         lineEvDate=(View)findViewById(R.id.lineEvDate);
         VlblEvDate=(TextView) findViewById(R.id.VlblEvDate);
         dtpEvDate=(EditText) findViewById(R.id.dtpEvDate);
         secInfo1=(LinearLayout)findViewById(R.id.secInfo1);
         lineInfo1=(View)findViewById(R.id.lineInfo1);
         VlblInfo1=(TextView) findViewById(R.id.VlblInfo1);
         txtInfo1=(EditText) findViewById(R.id.txtInfo1);
         secInfo2=(LinearLayout)findViewById(R.id.secInfo2);

         txtInfo1.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {

             }

             @Override
             public void afterTextChanged(Editable s) {
                 if (Integer.valueOf(txtInfo1.getText().toString().length() == 0 ? "77" : txtInfo1.getText().toString()) == 77)
                 {
                     String EVCODE = spnEvType.getSelectedItem().toString().length()==0 ? "" : spnEvType.getSelectedItem().toString().split("-")[0];

//                     if(txtInfo1.getText().toString().equals("77"))
//                     {
//                         txtInfo2.setVisibility(View.GONE);
//                         secInfo2.setVisibility(View.GONE);
//                     }
//                     else
//                     {
//                         txtInfo2.setVisibility(View.VISIBLE);
//                         secInfo2.setVisibility(View.VISIBLE);
//                     }
                     if(txtInfo1.getText().toString().equals("77") & EVCODE.equals("21"))
                     {
                         txtInfo2.setVisibility(View.GONE);
                         spnInfo2.setVisibility(View.GONE);
                         secInfo2.setVisibility(View.GONE);
                     }
                     else if(EVCODE.equals("21"))
                     {
                         secEvDate.setVisibility(View.VISIBLE);
                         spnInfo2.setVisibility(View.VISIBLE);
                         secInfo2.setVisibility(View.VISIBLE);
                         txtInfo2.setVisibility(View.GONE);
                     }
                     else
                     {
                         txtInfo2.setVisibility(View.GONE);
                         spnInfo2.setVisibility(View.GONE);
                         secInfo2.setVisibility(View.GONE);
                     }

                 }
             }
         });


         lineInfo2=(View)findViewById(R.id.lineInfo2);
         VlblInfo2=(TextView) findViewById(R.id.VlblInfo2);
         VlblOth=(TextView) findViewById(R.id.VlblOth);
         txtInfo2=(EditText) findViewById(R.id.txtInfo2);
         secInfo3=(LinearLayout)findViewById(R.id.secInfo3);
         lineInfo3=(View)findViewById(R.id.lineInfo3);
         VlblInfo3=(TextView) findViewById(R.id.VlblInfo3);
         txtInfo3=(EditText) findViewById(R.id.txtInfo3);
         secInfo4=(LinearLayout)findViewById(R.id.secInfo4);
         lineInfo4=(View)findViewById(R.id.lineInfo4);
         VlblInfo4=(TextView) findViewById(R.id.VlblInfo4);
         txtInfo4=(EditText) findViewById(R.id.txtInfo4);
         secVDate=(LinearLayout)findViewById(R.id.secVDate);
         lineVDate=(View)findViewById(R.id.lineVDate);
         VlblVDate=(TextView) findViewById(R.id.VlblVDate);
         dtpVDate=(EditText) findViewById(R.id.dtpVDate);
         secRnd=(LinearLayout)findViewById(R.id.secRnd);
         lineRnd=(View)findViewById(R.id.lineRnd);
         VlblRnd=(TextView) findViewById(R.id.VlblRnd);
         txtRnd=(EditText) findViewById(R.id.txtRnd);


         dtpEvDate.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 final int DRAWABLE_RIGHT  = 2;
                 if(event.getAction() == MotionEvent.ACTION_UP) {
                     if(event.getRawX() >= (dtpEvDate.getRight() - dtpEvDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                         VariableID = "btnEvDate"; showDialog(DATE_DIALOG);
                         return true;
                     }
                 }
                 return false;
             }
         });
         dtpVDate.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 final int DRAWABLE_RIGHT  = 2;
                 if(event.getAction() == MotionEvent.ACTION_UP) {
                     if(event.getRawX() >= (dtpVDate.getRight() - dtpVDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                         VariableID = "btnVDate"; showDialog(DATE_DIALOG);
                         return true;
                     }
                 }
                 return false;
             }
         });

         spnEvType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                 String EVCODE = spnEvType.getSelectedItem().toString().length()==0 ? "" : spnEvType.getSelectedItem().toString().split("-")[0];
//                 String CodeList = spnInfo4.getSelectedItem().toString().length()==0 ? "" : spnInfo4.getSelectedItem().toString().split("-")[0];

//                 formMember.setVisibility(View.GONE);
                 secInfo1.setVisibility(View.GONE);
                 secInfo2.setVisibility(View.GONE);
                 secInfo3.setVisibility(View.GONE);
                 secInfo4.setVisibility(View.GONE);

                 if(EVCODE.equals("12"))
                 {
                     dtpEvDate.setText(Global.DateNowDMY());
                     DataSearchMember(VILL,BARI,HH,MSLNO,"tmpMember");
                 }
                 else if(EVCODE.equals("20"))
                 {
                     dtpEvDate.setText(Global.DateNowDMY());
                     secName.setVisibility(View.VISIBLE);
                 }
                 else if(EVCODE.equals("21"))
                 {
                     dtpEvDate.setText("");
                     secInfo1.setVisibility(View.VISIBLE);
                     VlblInfo1.setText("কারন/স্বামী বা স্ত্রীর বয়স");
                     secInfo2.setVisibility(View.VISIBLE);
                     VlblInfo2.setText("ঘটনার কারন");
                     VlblOth.setVisibility(View.VISIBLE);
                     txtInfo1.setVisibility(View.VISIBLE);
                     spnInfo2.setVisibility(View.VISIBLE);
                     secName.setVisibility(View.VISIBLE);
                     txtInfo2.setVisibility(View.GONE);
                     spnInfo1.setVisibility(View.GONE);
                     spnInfo2.setAdapter(C.getArrayAdapter("Select distinct '  'EV from EventCode union SELECT (EvType||'-'||EVName)Ev FROM EventCode where EvType in('31','32','33','34')"));
                     txtInfo1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.valueOf(2))});

                     if(txtInfo1.getText().length()==0)
                     {
                         if(txtInfo1.getText().toString().equals("77") & EVCODE.equals("21"))
                         {
                             txtInfo2.setVisibility(View.GONE);
                             spnInfo2.setVisibility(View.GONE);
                             secInfo2.setVisibility(View.GONE);
                         }
                         else if(EVCODE.equals("21"))
                         {
                            secEvDate.setVisibility(View.VISIBLE);
                            secInfo2.setVisibility(View.VISIBLE);
                            spnInfo2.setVisibility(View.VISIBLE);
                            txtInfo2.setVisibility(View.GONE);
                         }
                        else
                        {
                            txtInfo2.setVisibility(View.GONE);
                            spnInfo2.setVisibility(View.GONE);
                            secInfo2.setVisibility(View.GONE);
                        }
                     }
                 }

                 //Internal Movement
                 else if(EVCODE.equals("22"))
                 {
                     dtpEvDate.setText("");
                     secInfo1.setVisibility(View.VISIBLE);
                     secName.setVisibility(View.VISIBLE);
                     VlblOth.setVisibility(View.GONE);
                     VlblInfo1.setText("পূর্বের খানা নাম্বার");
                     txtInfo1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.valueOf(11))});
                     MigrationForm(dialog,"52");
                 }
                 else if(EVCODE.equals("23"))
                 {
                     dtpEvDate.setText("");
                     secInfo1.setVisibility(View.VISIBLE);
                     secName.setVisibility(View.VISIBLE);
                     VlblOth.setVisibility(View.GONE);
                     VlblInfo1.setText("মূর্বের খানা নাম্বার");
                     txtInfo1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.valueOf(11))});
                     MigrationForm(dialog,"53");
                 }

                 //Birth
                 else if(EVCODE.equals("25")){
                     dtpEvDate.setText("");
                     secInfo1.setVisibility(View.VISIBLE);
                     spnInfo1.setVisibility(View.VISIBLE); //Mother serial no
                     secName.setVisibility(View.VISIBLE);
                     spnInfo1.setAdapter(C.getArrayAdapter("Select '' union Select MSlNo||'-'||Name from tmpMember Where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'and Sex='2' and MS<>'30' union Select '00-এই খানার সদস্য নয়'"));
                     VlblInfo1.setText("মায়ের সিরিয়াল নম্বর");
                     VlblOth.setVisibility(View.GONE);
                     txtInfo1.setVisibility(View.GONE);
                     txtInfo2.setVisibility(View.GONE);
                     secInfo2.setVisibility(View.GONE);
                     spnInfo2.setVisibility(View.GONE);
                 }
                 //Marital Status
                 else if(EVCODE.equals("31")){
                     dtpEvDate.setText("");
                     secInfo1.setVisibility(View.VISIBLE);
                     VlblInfo1.setText("স্বামী/স্ত্রীর বয়স");

                 }else if(EVCODE.equals("32")){
                     dtpEvDate.setText("");
                     secInfo1.setVisibility(View.VISIBLE);
                     spnInfo1.setVisibility(View.GONE);
                     txtInfo1.setVisibility(View.VISIBLE);
                     VlblInfo1.setText("স্বামী/স্ত্রীর বয়স");

                 }else if(EVCODE.equals("33")){
                     dtpEvDate.setText("");
                     secInfo1.setVisibility(View.VISIBLE);
                     spnInfo1.setVisibility(View.GONE);
                     txtInfo1.setVisibility(View.VISIBLE);
                     VlblInfo1.setText("স্বামী/স্ত্রীর বয়স");

                 }else if(EVCODE.equals("34")){
                     dtpEvDate.setText("");
                     secInfo1.setVisibility(View.VISIBLE);
                     secInfo1.setVisibility(View.VISIBLE);
                     spnInfo1.setVisibility(View.GONE);
                     txtInfo1.setVisibility(View.VISIBLE);
                     VlblInfo1.setText("স্বামী/স্ত্রীর বয়স");

                 }
                 //Pregnancy Information
                 else if(EVCODE.equals("40"))
                 {
                     dtpEvDate.setText(Global.DateNowDMY());
                 }
                 else if(EVCODE.equals("41"))
                 {
                     dtpEvDate.setText("");
                 }
                 else if(EVCODE.equals("42"))
                 {
                     dtpEvDate.setText("");
                     secInfo1.setVisibility(View.VISIBLE);
                     secInfo2.setVisibility(View.VISIBLE);
                     secInfo3.setVisibility(View.VISIBLE);
                     secInfo4.setVisibility(View.VISIBLE);

                 }
                 else if(EVCODE.equals("49"))
                 {
                     dtpEvDate.setText(Global.DateNowDMY());
                 }
                 //Migration out
                 else if(EVCODE.equals("51")){
                     dtpEvDate.setText("");
                 }else if(EVCODE.equals("52")){
                     dtpEvDate.setText("");
                 }else if(EVCODE.equals("53")){
                     dtpEvDate.setText("");
                 }else if(EVCODE.equals("55")){
                     dtpEvDate.setText("");
                 }
                 //Mothers Serial Number
                 else if(EVCODE.equals("61"))
                 {
                     dtpEvDate.setText(Global.DateNowDMY());
                     secInfo1.setVisibility(View.VISIBLE);
                     secInfo2.setVisibility(View.VISIBLE);
                     VlblInfo2.setVisibility(View.VISIBLE);
                     txtInfo1.setVisibility(View.GONE);
                     txtInfo2.setVisibility(View.GONE);
                     VlblInfo1.setText("নতুন সিরিয়াল");
                     VlblInfo2.setText("পূর্বের সিরিয়াল");
                     spnInfo1.setVisibility(View.VISIBLE);
                     spnInfo2.setVisibility(View.VISIBLE);

                     String PMoNo= C.ReturnSingleValue("Select MoNo from tmpMember where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='" + MSLNO +"'");

                     if(EVCODE.equals("61"))
                     {
                         spnInfo1.setAdapter(C.getArrayAdapter("Select '' union Select MSlNo||'-'||Name from tmpMember Where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'and Sex='2' and MS<>'30' union Select '00-এই খানার সদস্য নয়'"));

                         if  (!PMoNo.equals("00")) {
                            spnInfo2.setAdapter(C.getArrayAdapter("Select MSlNo||'-'||Name from tmpMember where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and MSlNo='" + PMoNo.toString() + "'"));
                        }
                         if  (PMoNo.equals("00")) {
                             spnInfo2.setAdapter(C.getArrayAdapter("Select '00-এই খানার সদস্য নয়'"));
                         }
                     }
                 }
                 //Father Serial Number
                 else if(EVCODE.equals("62"))
                 {
                     dtpEvDate.setText(Global.DateNowDMY());
                     secInfo1.setVisibility(View.VISIBLE);
                     secInfo2.setVisibility(View.VISIBLE);
                     VlblInfo2.setVisibility(View.VISIBLE);
                     txtInfo1.setVisibility(View.GONE);
                     txtInfo2.setVisibility(View.GONE);
                     VlblInfo1.setText("নতুন সিরিয়াল");
                     VlblInfo2.setText("পূর্বের সিরিয়াল");
                     spnInfo1.setVisibility(View.VISIBLE);
                     spnInfo2.setVisibility(View.VISIBLE);

                     String PFoNo= C.ReturnSingleValue("Select FaNo from tmpMember where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='" + MSLNO +"'");

                     if(EVCODE.equals("62"))
                     {
                         spnInfo1.setAdapter(C.getArrayAdapter("Select '' union Select MSlNo||'-'||Name from tmpMember Where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'and Sex='1' and MS<>'30' union Select '00-এই খানার সদস্য নয়'"));

                         if  (!PFoNo.equals("00")) {
                             spnInfo2.setAdapter(C.getArrayAdapter("Select MSlNo||'-'||Name from tmpMember where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and MSlNo='" + PFoNo.toString() + "'"));
                         }
                         if  (PFoNo.equals("00")) {
                             spnInfo2.setAdapter(C.getArrayAdapter("Select '00-এই খানার সদস্য নয়'"));
                         }
                     }

                 }
                 //Spouses Serial Number
                 else if(EVCODE.equals("63"))
                 {
                     dtpEvDate.setText(Global.DateNowDMY());
                     secInfo1.setVisibility(View.VISIBLE);
                     secInfo2.setVisibility(View.VISIBLE);
                     VlblInfo2.setVisibility(View.VISIBLE);
                     txtInfo1.setVisibility(View.GONE);
                     txtInfo2.setVisibility(View.GONE);
                     VlblInfo1.setText("নতুন সিরিয়াল");
                     VlblInfo2.setText("পূর্বের সিরিয়াল");
                     spnInfo1.setVisibility(View.VISIBLE);
                     spnInfo2.setVisibility(View.VISIBLE);

                     String PSpNo= C.ReturnSingleValue("Select Sp1 from tmpMember where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='" + MSLNO +"'");

                     if(EVCODE.equals("63"))
                     {
                         spnInfo1.setAdapter(C.getArrayAdapter("Select '' union Select MSlNo||'-'||Name from tmpMember Where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'and MS='31' union Select '00-এই খানার সদস্য নয়'"));

                         if  (!PSpNo.equals("00"))
                         {
                             spnInfo2.setAdapter(C.getArrayAdapter("Select MSlNo||'-'||Name from Member where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and MSlNo='" + PSpNo.toString() + "'"));
                         }
                         if  (PSpNo.equals("00"))
                         {
                             spnInfo2.setAdapter(C.getArrayAdapter("Select '00-এই খানার সদস্য নয়'"));
                         }
                     }
                 }
                 else if(EVCODE.equals("64") | EVCODE.equals("71") | EVCODE.equals("72"))
                 {
                     secInfo1.setVisibility(View.VISIBLE);
                     spnInfo1.setVisibility(View.VISIBLE);
                     txtInfo1.setVisibility(View.GONE);
                     txtInfo2.setVisibility(View.GONE);
                     VlblInfo2.setVisibility(View.GONE);
                     secVDate.setVisibility(View.GONE);
                     secInfo2.setVisibility(View.GONE);

                     if(EVCODE.equals("64")) {
                         VlblInfo1.setText("সম্পর্কের কোড");
                         spnInfo1.setAdapter(C.getArrayAdapter("Select distinct ' 'Name union SELECT Name FROM RTH"));
                     }
                     else if(EVCODE.equals("71"))
                     {
                         VlblInfo1.setText("শিক্ষার কোড");
                         spnInfo1.setAdapter(C.getArrayAdapter("Select distinct ' 'Name union SELECT Name FROM EDU"));
                     }
                     else if(EVCODE.equals("72"))
                         VlblInfo1.setText("পেশার কোড");
                         spnInfo1.setAdapter(C.getArrayAdapter("Select distinct ' 'Name union SELECT Name FROM OCP"));
                 }
                 else
                 {
//                     formMember.setVisibility(View.GONE);
                 }

                 if(EVCODE.equals("12") | EVCODE.equals("40") | EVCODE.equals("49"))
                 {
                     secEvDate.setVisibility(View.GONE);
                 }
                 else
                 {
                     secEvDate.setVisibility(View.VISIBLE);
                 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> parentView) {
                 // your code here
             }

         });

        // MemberInitialize();

         //Hide all skip variables
         secEvDate.setVisibility(View.GONE);
         lineEvDate.setVisibility(View.GONE);
         secInfo1.setVisibility(View.GONE);
         lineInfo1.setVisibility(View.GONE);
         secInfo2.setVisibility(View.GONE);
         lineInfo2.setVisibility(View.GONE);
         secInfo3.setVisibility(View.GONE);
         lineInfo3.setVisibility(View.GONE);
         secInfo4.setVisibility(View.GONE);
         lineInfo4.setVisibility(View.GONE);
         secVDate.setVisibility(View.GONE);
         lineVDate.setVisibility(View.GONE);
         secRnd.setVisibility(View.GONE);
         lineRnd.setVisibility(View.GONE);
         VlblOth.setVisibility(View.GONE);
         spnInfo1.setVisibility(View.GONE);
         spnInfo2.setVisibility(View.GONE);
         secName.setVisibility(View.GONE);

         txtVill.setEnabled(false);
         txtBari.setEnabled(false);
         txtHH.setEnabled(false);
         txtMSlNo.setEnabled(false);
         txtRnd.setEnabled(false);
         txtPNo.setEnabled(false);

         txtVill.setText(VILL);
         txtVill.setFocusable(false);
         txtBari.setText(BARI);
         txtBari.setFocusable(false);
         txtHH.setText(HH);
         txtRnd.setText(ROUNDNO);
         txtRnd.setFocusable(false);

         Button cmdSave = (Button) findViewById(R.id.cmdSave1);
         cmdSave.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 DataSave();
             }});
     }
   catch(Exception  e)
   {
       Connection.MessageBox(Events_NewMem.this, e.getMessage());
       return;
   }
 }

     private void DataSave()
     {
         try
         {
             String Household = VILL+BARI+HH;


             //====================================================================================================================
             String DV="";
             EVTYPE = spnEvType.getSelectedItemPosition()==0? "" : spnEvType.getSelectedItem().toString().split("-")[0];

             String VDate  = C.ReturnSingleValue("select VDate from tmpVisits Where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH + "' and Rnd='"+ ROUNDNO +"'");

              if(spnEvType.getSelectedItemPosition()==0  & spnEvType.isShown())
             {
                 Connection.MessageBox(Events_NewMem.this, "ঘটনার ধরন খালি রাখা যাবেনা");
                 spnEvType.requestFocus();
                 return;
             }

             String EDT = Global.DateValidate(dtpEvDate.getText().toString());

             Integer ECode = Integer.parseInt(spnEvType.getSelectedItem().toString().substring(0, 2));

             if (ECode == 12)
             {

             }
             else if ((ECode >= 20 & ECode <= 25) | (ECode >= 30 & ECode <= 34) | (ECode >= 51 & ECode <= 56))
             {
                 if(EDT.length()!=0)
                 {
                     Connection.MessageBox(Events_NewMem.this, EDT);
                     return;
                 }

                 //migration - in
                 //-----------------------------------------------------------------------------------------------
                 if(ECode == 21)
                 {
                     DV = Global.DateValidate(dtpEvDate.getText().toString());
                     if(DV.length()!=0 & secEvDate.isShown())
                     {
                         Connection.MessageBox(Events_NewMem.this, "This is not a valid date of events");
                         dtpEvDate.requestFocus();
                         return;
                     }
                     String ED = Global.DateValidate(dtpEvDate.getText().toString());
                     if(txtInfo1.getText().toString().length()==0)
                     {
                         Connection.MessageBox(Events_NewMem.this, "কারন/স্বামী বা স্ত্রীর বয়স খালি রাখা যাবে না");
                         return;
                     }
                     else if(spnInfo2.getSelectedItemPosition()==0  & spnInfo2.isShown())
                     {
                         Connection.MessageBox(Events_NewMem.this, "কারন খালি রাখা যাবে না");
                         spnInfo2.requestFocus();
                         return;
                     }
                     if(txtName.getText().toString().length()==0 & txtName.isShown())
                     {
                         Connection.MessageBox(Events_NewMem.this, "খানার সদস্যদের নাম খালি রাখা যাবেনা.");
                         txtName.requestFocus();
                         return;
                     }

                     else if(!txtInfo1.getText().toString().equals("77") & ED.length()!=0)
                     {
                         Connection.MessageBox(Events_NewMem.this, ED);
                         return;
                     }
                     else if(!txtInfo1.getText().toString().equals("77") &
                             (!Connection.SelectedSpinnerValue(spnInfo2.getSelectedItem().toString(), "-").equals("31") &
                              !Connection.SelectedSpinnerValue(spnInfo2.getSelectedItem().toString(), "-").equals("32") &
                              !Connection.SelectedSpinnerValue(spnInfo2.getSelectedItem().toString(), "-").equals("33") &
                              !Connection.SelectedSpinnerValue(spnInfo2.getSelectedItem().toString(), "-").equals("34")))
                     {
                         Connection.MessageBox(Events_NewMem.this, "সদস্য কি কারনে এ খানায় আসল সেটি অবশ্যই ৩১/৩২/৩৩/৩৪ হতে হবে।");
                         return;
                     }
                 }


                 else if(ECode == 25)
                 {
                     DV = Global.DateValidate(dtpEvDate.getText().toString());
                     if(DV.length()!=0 & secEvDate.isShown())
                     {
                         Connection.MessageBox(Events_NewMem.this, "ইভেন্ট তারিখ খালি রাখা যাবে না");
                         dtpEvDate.requestFocus();
                         return;
                     }

                     else if(spnInfo1.getSelectedItemPosition()==0  & spnInfo1.isShown())
                     {
                         Connection.MessageBox(Events_NewMem.this, "মায়ের নাম্বার খালি রাখা যাবে না");
                         txtInfo1.requestFocus();
                         return;
                     }
                     if(txtName.getText().toString().length()==0 & txtName.isShown())
                     {
                         Connection.MessageBox(Events_NewMem.this, "সদস্যদের নাম খালি রাখা যাবেনা.");
                         txtName.requestFocus();
                         return;
                     }
                     String PregResult = C.ReturnSingleValue("Select EVType from Events Where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and Rnd='"+ ROUNDNO + "' and EVType='42'");

                     if(!PregResult.equals("42"))
                     {
                         Connection.MessageBox(Events_NewMem.this, "এই খানায় কোন মহিলার ইভেনট ৪২ ঘটান হয়নি, আগে ৪২ হবে তারপর ২৫ হবে.");
                         spnInfo2.requestFocus();
                         return;
                     }
                 }
             }
//-----------------------------------------------------------------------------------------------------------------------------------------
             //Member Validation Check

                 if(txtName.getText().toString().length()==0 & secName.isShown())
                 {
                     Connection.MessageBox(Events_NewMem.this, "সদস্যদের নাম খালি রাখা যাবেনা.");
                     txtName.requestFocus();
                     return;
                 }


             String MSL = "";

             Events_DataModel objSave = new Events_DataModel();
             objSave.setVill(txtVill.getText().toString());
             objSave.setBari(txtBari.getText().toString());
             objSave.setHH(txtHH.getText().toString());

             if(EVTYPE.equals("20")|EVTYPE.equals("21")|EVTYPE.equals("22")|EVTYPE.equals("23")|EVTYPE.equals("25"))
             {
                 MSL = MemNo(VILL, BARI, HH);
             }
             else
             {
                 MSL = txtMSlNo.getText().toString();
             }

             objSave.setMSlNo(MSL);
             objSave.setPNo(txtPNo.getText().toString());
             objSave.setEvType((spnEvType.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnEvType.getSelectedItem().toString(), "-")));
             objSave.setEvDate(dtpEvDate.getText().toString().length() > 0 ? Global.DateConvertYMD(dtpEvDate.getText().toString()) : dtpEvDate.getText().toString());

             if(EVTYPE.equals("25"))
             {
                 objSave.setInfo1(spnInfo1.getSelectedItem().toString().split("-")[0]);
             }
             else if(EVTYPE.equals("20") | EVTYPE.equals("22") |EVTYPE.equals("23"))
             {
                 objSave.setInfo1(txtInfo1.getText().toString());
                 objSave.setInfo2(txtInfo2.getText().toString());
                 objSave.setInfo3(txtInfo3.getText().toString());
                 objSave.setInfo4(txtInfo4.getText().toString());
             }
             else if(EVTYPE.equals("21"))
             {
                 objSave.setInfo1(txtInfo1.getText().toString());
                 objSave.setInfo2(spnInfo2.getSelectedItem().toString().split("-")[0]);
                 objSave.setInfo3(txtInfo3.getText().toString());
                 objSave.setInfo4(txtInfo4.getText().toString());
             }

             objSave.setVDate(dtpVDate.getText().toString().length() > 0 ? Global.DateConvertYMD(dtpVDate.getText().toString()) : dtpVDate.getText().toString());
             objSave.setRnd(txtRnd.getText().toString());
             objSave.setEnDt(Global.DateTimeNowYMDHMS());
             objSave.setStartTime(STARTTIME);
             objSave.setEndTime(g.CurrentTime24());
             objSave.setDeviceID(DEVICEID);
             objSave.setEntryUser(ENTRYUSER); //from data entry user list
             objSave.setmodifyDate(Global.DateTimeNowYMDHMS());
             //objSave.setLat(Double.toString(currentLatitude));
             //objSave.setLon(Double.toString(currentLongitude));

             String SQL1 = "";
             String SQL2 = "";
             String SQL3 = "";

             //Save Events
             SQL1 = objSave.TransactionSQL(this);

             //Save Member

             if(EVTYPE.equals("12")|EVTYPE.equals("20")|EVTYPE.equals("21")|EVTYPE.equals("22")|EVTYPE.equals("23") |EVTYPE.equals("25")) {
                 SQL2 = DataSaveMember(MSL);
             }
             //Update Member Data

             if(EVTYPE.equals("12"))
             {

             }else if(EVTYPE.equals("20"))
             {

             }else if(EVTYPE.equals("21"))
             {

             }
             //Internal Movement
             else if(EVTYPE.equals("22"))
             {

             }else if(EVTYPE.equals("23"))
             {

             }
             //Birth
             else if(EVTYPE.equals("25"))
             {

             }

             //Transaction Process
             String status = C.TransactionDataInsert(SQL1,SQL2,SQL3,"");

             if(status.length()==0) {
                 Intent returnIntent = new Intent();
                 returnIntent.putExtra("res", "");
                 setResult(Activity.RESULT_OK, returnIntent);
                 Connection.MessageBox(Events_NewMem.this, "Saved Successfully");
                 finish();
             }
             else
             {
                 Connection.MessageBox(Events_NewMem.this, status);
                 return;
             }

         }
         catch(Exception  e)
         {
             Connection.MessageBox(Events_NewMem.this, e.getMessage());
             return;
         }
     }

     private void DataSearch(String Vill, String Bari, String HH, String MSlNo, String EvType, String EvDate, String Rnd)
     {
         try
         {
             RadioButton rb;
             Events_DataModel d = new Events_DataModel();
             String SQL = "Select * from "+ TableName +"  Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"' and MSlNo='"+ MSlNo +"' and EvType='"+ EvType +"' and EvDate='"+ EvDate +"' and Rnd='"+ Rnd +"'";
             List<Events_DataModel> data = d.SelectAll(this, SQL);

             for(Events_DataModel item : data){
                 txtVill.setText(item.getVill());
                 txtBari.setText(item.getBari());
                 txtHH.setText(item.getHH());
                 txtMSlNo.setText(item.getMSlNo());
                 txtPNo.setText(item.getPNo());
                 spnEvType.setSelection(Global.SpinnerItemPositionAnyLength(spnEvType, item.getEvType()));
                 dtpEvDate.setText(item.getEvDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getEvDate()));
                 txtInfo1.setText(item.getInfo1());
                 txtInfo2.setText(item.getInfo2());
                 txtInfo3.setText(item.getInfo3());
                 txtInfo4.setText(item.getInfo4());
                 dtpVDate.setText(item.getVDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getVDate()));
                 txtRnd.setText(item.getRnd());
             }
         }
         catch(Exception  e)
         {
             Connection.MessageBox(Events_NewMem.this, e.getMessage());
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

     private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
         public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
             mYear = year; mMonth = monthOfYear+1; mDay = dayOfMonth;
             EditText dtpDate;


             dtpDate = (EditText)findViewById(R.id.dtpEvDate);
             if (VariableID.equals("btnEvDate"))
             {
                 dtpDate = (EditText)findViewById(R.id.dtpEvDate);
             }
             else if (VariableID.equals("btnVDate"))
             {
                 dtpDate = (EditText)findViewById(R.id.dtpVDate);
             }
             else if (VariableID.equals("btnBDate"))
             {
                 dtpDate = (EditText)findViewById(R.id.dtpBDate);
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
//         locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
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
     private String MemNo(String VILL,String BARI,String HH)
     {
         String M = C.ReturnSingleValue("Select cast(ifnull(max(MSlNo),0)+1 as varchar(2))MemNo from tmpMember where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"'");
         M = Global.Right("0"+M,2);
         return M;
     }

     private String DataSaveMember(String MemberSl)
     {
         String SQL = "";
         try
         {
//             String DV="";
//             RadioButton rb;

             Member_DataModel objSave = new Member_DataModel();
             objSave.setVill(txtVill.getText().toString());
             objSave.setBari(txtBari.getText().toString());
             objSave.setHH(txtHH.getText().toString());
             objSave.setMSlNo(MemberSl);
             objSave.setName(txtName.getText().toString());
             objSave.setPNo(txtPNo.getText().toString());

             if(EVTYPE.equals("20")|EVTYPE.equals("21")|EVTYPE.equals("22")|EVTYPE.equals("23")|EVTYPE.equals("25")) {
                 objSave.setEnType(spnEvType.getSelectedItem().toString().split("-")[0]);
                 objSave.setEnDate(dtpEvDate.getText().toString().length() > 0 ? Global.DateConvertYMD(dtpEvDate.getText().toString()) : dtpEvDate.getText().toString());
                 objSave.setExType("");
                 objSave.setExDate("");
             }

             objSave.setStartTime(STARTTIME);
             objSave.setEndTime(g.CurrentTime24());
             objSave.setDeviceID(DEVICEID);
             objSave.setEntryUser(ENTRYUSER); //from data entry user list
             objSave.setNeedReview("2");
             SQL = objSave.TransactionSQL(this);

         }
         catch(Exception  e)
         {
             SQL = e.getMessage();
         }
         return SQL;
     }


     public void DataSearchMember(String Vill, String Bari, String HH, String MSlNo, String TableName)
     {
         try
         {
             RadioButton rb;
             Member_DataModel d = new Member_DataModel();
             String SQL = "Select * from "+ TableName +"  Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"' and MSlNo='"+ MSlNo +"'";
             List<Member_DataModel> data = d.SelectAll(this, SQL);
             for(Member_DataModel item : data){
                 txtVill.setText(item.getName());
                 txtBari.setText(item.getName());
                 txtHH  .setText(item.getName());
                 txtMSlNo.setText(item.getName());
                 txtName.setText(item.getName());

             }
         }
         catch(Exception  e)
         {
             Connection.MessageBox(Events_NewMem.this, e.getMessage());
             return;
         }
     }

     private boolean isAgeDifferenceWithParentsValid(String VILL, String BARI,String HH, String fatherSL, String motherSL, String Age) {
         // Cursor fCursor,mCursor;
         String fAge = C.ReturnSingleValue("select AgeY from MEMBER where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and MSlNo='" + fatherSL + "'");
         String mAge = C.ReturnSingleValue("select AgeY from MEMBER where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and MSlNo='" + motherSL + "'");

         if (fAge.isEmpty()) {
             fAge = "0";

         }
         if (mAge.isEmpty()) {
             mAge = "0";
         }
         if (fAge.equals("0") | mAge.equals("0")) {
             return true;
         }

         int cAge = Integer.valueOf(Age);
         return !(((Integer.valueOf(fAge) - cAge) < 12) | ((Integer.valueOf(mAge) - cAge) < 12));
     }
     private boolean isHhHeadValid(String VILL, String BARI,String HH, String MSLNO) {
         Cursor cursor;
         cursor = C.ReadData("select * from Member where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and Rth ='01' and MSlNo !='" + MSLNO + "'");
         if (cursor.getCount() > 0) {

             cursor.close();
             return false;
         } else {
             cursor.close();
             return true;
         }
     }


     private void MigrationForm(final Dialog d, final String EvCode)
     {
         try
         {
             final Dialog dialog = new Dialog(Events_NewMem.this);

             dialog .requestWindowFeature(Window.FEATURE_NO_TITLE);
             dialog.setContentView(R.layout.migration);
             dialog.setCanceledOnTouchOutside(false);
             dialog.setCancelable(true);
             Window window = dialog.getWindow();
             WindowManager.LayoutParams wlp = window.getAttributes();

             wlp.gravity = Gravity.TOP;
             wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
             window.setAttributes(wlp);


             TextView lblMigTitle = (TextView)dialog.findViewById(R.id.lblMigTitle);
             final Spinner VillageList = (Spinner)dialog.findViewById(R.id.VillageList);
             final TextView lblMigParameter = (TextView)dialog.findViewById(R.id.lblMigParameter);

             //from different village
             if(EvCode.equals("52"))
             {
                 VillageList.setAdapter(C.getArrayAdapter("select VCode||' - '||vname from Village order by vname asc"));
                 lblMigTitle.setText("Internal Mig.-In");
             }
             //should be same village
             else if(EvCode.equals("53"))
             {
                 VillageList.setAdapter(C.getArrayAdapter("select VCode||' - '||vname from Village where cluster='"+ CLUSTER +"' and VCode='"+ txtVill.getText().toString() +"' order by vname asc"));
                 lblMigTitle.setText("Split-In");
             }

             final EditText txtMember = (EditText)dialog.findViewById(R.id.txtMember);

             final ListView evlist = (ListView)dialog.findViewById(R.id.lstMigration);
             View header = getLayoutInflater().inflate(R.layout.migrationheading, null);
             evlist.addHeaderView(header);

             VillageList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                 @Override
                 public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                     String V = "";
                     V = Global.Left(VillageList.getSelectedItem().toString(),3);

                     MigrationData(V, EvCode,txtMember.getText().toString(),dialog,evlist,"no");
                 }

                 @Override
                 public void onNothingSelected(AdapterView<?> parentView) {
                     // your code here
                 }

             });

             txtMember.addTextChangedListener(new TextWatcher(){
                 public void afterTextChanged(Editable s) {}
                 public void beforeTextChanged(CharSequence s, int start, int count, int after){}
                 public void onTextChanged(CharSequence s, int start, int before, int count){
                     String V = Global.Left(VillageList.getSelectedItem().toString(),3);
                     //need to consider Same cluster/different cluster
                     MigrationData(V, EvCode,txtMember.getText().toString(),dialog,evlist,"yes");
                 }});

             Button cmdMigListClose = (Button)dialog.findViewById(R.id.cmdMigListClose);
             cmdMigListClose.setOnClickListener(new View.OnClickListener() {
                 public void onClick(View arg0) {
                     dialog.dismiss();
                 }
             });
             Button cmdMigListOk = (Button)dialog.findViewById(R.id.cmdMigListOk);
             cmdMigListOk.setOnClickListener(new View.OnClickListener() {
                 public void onClick(View arg0) {

                     if(g.getPNo().trim().length()==0)
                     {
                         Connection.MessageBox(Events_NewMem.this, " সদস্য সিলেক্ট করা হয়নি, প্রথমে তালিকা থেকে সদস্য সিলেক্ট করুন।");
                         return;
                     }

                     String[] migdata = Connection.split(lblMigParameter.getText().toString(),'^');

                     txtPNo.setText(migdata[4]);
                     dtpEvDate.setText(migdata[5]);
                     secInfo1.setVisibility(View.VISIBLE);
                     txtInfo1.setText(migdata[0]+migdata[1]+migdata[2]);
                     g.setPNo(migdata[4]);

//                     formMember.setVisibility(View.VISIBLE);
                     DataSearchMigMember(migdata[0],migdata[1],migdata[2],migdata[3],"migMember",spnEvType.getSelectedItem().toString().split("-")[0],migdata[5]);

                     dialog.dismiss();
                 }
             });

             dialog.show();
         }
         catch(Exception  e)
         {
             Connection.MessageBox(Events_NewMem.this, e.getMessage());
             return;
         }
     }

     private void MigrationData(String Village,String EvCode, String Name,final Dialog d,final ListView evlist, String Search)
     {
         String SQL = "";
         SQL = "Select vill as vill,bari as bari, hh as hh, mslno Sno,Pno as Pno,Name as Name,ExDate as ExDate from migMember where Vill='"+ Village +"' and ExType='"+ EvCode +"' and (Name like('"+ Name +"%') or PNo like('"+ Name +"%'))order by name asc";


         if(EvCode.equals("53"))
         {

         }
         else if(EvCode.equals("52"))
         {

         }
         Cursor cur1 = C.ReadData(SQL);

         cur1.moveToFirst();
         evmylist.clear();
         eList = null;
         evlist.setAdapter(null);

         while(!cur1.isAfterLast())
         {
             HashMap<String, String> map = new HashMap<String, String>();
             map.put("vill",   cur1.getString(cur1.getColumnIndex("vill")));
             map.put("bari",   cur1.getString(cur1.getColumnIndex("bari")));
             map.put("hh",   cur1.getString(cur1.getColumnIndex("hh")));
             map.put("sno",  cur1.getString(cur1.getColumnIndex("Sno")));
             map.put("pno",  cur1.getString(cur1.getColumnIndex("Pno")));
             map.put("name", cur1.getString(cur1.getColumnIndex("Name")));
             map.put("exdate", Global.DateConvertDMY(cur1.getString(cur1.getColumnIndex("ExDate"))));

             evmylist.add(map);

             /*eList = new SimpleAdapter(Events.this, evmylist, R.layout.migrationrow,
                     new String[] {"sno","pno","name","exdate"},
                     new int[] {R.id.m_sno,R.id.m_pno,R.id.m_name,R.id.m_exdate});
             evlist.setAdapter(eList);*/

             cur1.moveToNext();
         }
         dataAdapter = new SimpleAdapter(Events_NewMem.this, evmylist, R.layout.migrationrow,
                 new String[] {"sno","pno","name","exdate"},
                 new int[] {R.id.m_sno,R.id.m_pno,R.id.m_name,R.id.m_exdate});
         //evlist.setAdapter(eList);
         evlist.setAdapter(new Events_NewMem.MigrationListAdapter(this, dataAdapter, d));
         cur1.close();
     }

     public class MigrationListAdapter extends BaseAdapter
     {
         private Context context;
         private SimpleAdapter dataAdap;
         private Dialog dialog;

         public MigrationListAdapter(Context c, SimpleAdapter da, Dialog dg){
             context = c;
             dataAdap = da;
             dialog = dg;
         }

         public int getCount() {
             return dataAdap.getCount();
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
                 convertView = inflater.inflate(R.layout.migrationrow, null);
             }


             final HashMap<String, String> o = (HashMap<String, String>) dataAdap.getItem(position);

             TextView m_sno   = (TextView)convertView.findViewById(R.id.m_sno);
             TextView m_pno   = (TextView)convertView.findViewById(R.id.m_pno);
             TextView m_name  = (TextView)convertView.findViewById(R.id.m_name);
             TextView m_exdate= (TextView)convertView.findViewById(R.id.m_exdate);
             LinearLayout secRow = (LinearLayout)convertView.findViewById(R.id.secRow);

             m_sno.setText(o.get("sno").toString());
             m_pno.setText(o.get("pno").toString());
             m_name.setText(o.get("name").toString());
             m_exdate.setText(Global.DateConvertDMY(o.get("exdate").toString()));

             final TextView lblName = (TextView)dialog.findViewById(R.id.lblName);
             final TextView lblMigParameter = (TextView)dialog.findViewById(R.id.lblMigParameter);

             secRow.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     lblName.setText("Name: "+ o.get("name") +" [Outdate: "+ o.get("exdate").toString() + "]");
                     lblMigParameter.setText(o.get("vill")+"^"+o.get("bari")+"^"+o.get("hh")+"^"+o.get("sno")+"^"+o.get("pno")+"^"+o.get("exdate"));
                     g.setPNo(o.get("pno").toString());



                     //*txtPNo.setText(o.get("pno"));
                     /*dtpEvDate.setText(Global.DateConvertDMY(o.get("exdate").toString()));
                     secInfo1.setVisibility(View.VISIBLE);
                     txtInfo1.setText(o.get("hh"));
                     g.setPNo(o.get("pno").toString());
                     formMember.setVisibility(View.VISIBLE);
                     DataSearchMember(o.get("vill"),o.get("bari"),o.get("hh"),o.get("sno"));*/
                 }
             });

            /* secRow.setOnClickListener(new View.OnClickListener() {
                 public void onClick(View v) {
                     txtPNo.setText(o.get("pno"));
                     dtpEvDate.setText(Global.DateConvertDMY(o.get("exdate").toString()));
                     secInfo1.setVisibility(View.VISIBLE);
                     txtInfo1.setText(o.get("hh"));
                     g.setPNo(o.get("pno").toString());
                     formMember.setVisibility(View.VISIBLE);
                     DataSearchMember(o.get("vill"),o.get("bari"),o.get("hh"),o.get("sno"));

                     //lblName.setText(o.get("name").toString());
                 }
             });*/
             /*m_pno.setOnClickListener(new View.OnClickListener() {
                 public void onClick(View v) {
                     g.setPNo(o.get("pno").toString());
                     //lblName.setText(o.get("name").toString());
                 }
             });*/
             /*m_name.setOnClickListener(new View.OnClickListener() {
                 public void onClick(View v) {
                     lblName.setText("Name: "+ o.get("name") +" [Outdate: "+ Global.DateConvertDMY(o.get("exdate").toString()) + "]");
                     g.setPNo(o.get("pno").toString());
                     //lblName.setText(o.get("name").toString());
                 }
             });*/
             /*m_exdate.setOnClickListener(new View.OnClickListener() {
                 public void onClick(View v) {
                     g.setPNo(o.get("pno").toString());
                     //lblName.setText(o.get("name").toString());
                 }
             });*/

             return convertView;
         }
     }


     public void DataSearchMigMember(String Vill, String Bari, String HH, String MSlNo, String TableName, String EnType, String EnDate)
     {
         try
         {
             RadioButton rb;
             Member_DataModel d = new Member_DataModel();
             String SQL = "Select * from "+ TableName +"  Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"' and MSlNo='"+ MSlNo +"'";
             List<Member_DataModel> data = d.SelectAll_MigMember(this, SQL);
             for(Member_DataModel item : data){

                 txtName.setText(item.getName());

                 /*spnRth.setSelection(0);
                 String[] d_rdogrpSex = new String[] {"1","2"};
                 for (int i = 0; i < d_rdogrpSex.length; i++)
                 {
                     if (item.getSex().equals(String.valueOf(d_rdogrpSex[i])))
                     {
                         rb = (RadioButton)rdogrpSex.getChildAt(i);
                         rb.setChecked(true);
                     }
                 }
                 dtpBDate.setText(item.getBDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getBDate()));
                 txtAgeY.setText(item.getAgeY());
                 spnMoNo.setSelection(0);
                 spnFaNo.setSelection(0);
                 spnEdu.setSelection(Global.SpinnerItemPositionAnyLength(spnEdu, item.getEdu()));
                 spnMS.setSelection(Global.SpinnerItemPositionAnyLength(spnMS, item.getMS()));
                 spnOcp.setSelection(Global.SpinnerItemPositionAnyLength(spnOcp, item.getOcp()));
                 spnSp1.setSelection(0);
                 spnSp2.setSelection(0);
                 spnSp3.setSelection(0);
                 spnSp4.setSelection(0);
                 txtEnType.setText(EnType);
                 dtpEnDate.setText(EnDate);
                 txtExType.setText("");
                 dtpExDate.setText("");
                 if(item.getNeedReview().equals("1")) chkNeedReview.setChecked(true); else chkNeedReview.setChecked(false);*/
             }
         }
         catch(Exception  e)
         {
             Connection.MessageBox(Events_NewMem.this, e.getMessage());
             return;
         }
     }
 }