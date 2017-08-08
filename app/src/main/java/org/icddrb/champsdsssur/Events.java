
 package org.icddrb.champsdsssur;


 //Android Manifest Code
 //<activity android:name=".Events" android:label="Events" />
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.List;
 import android.app.*;
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
 import android.provider.Settings;
 import android.provider.Telephony;
 import android.text.Editable;
 import android.text.InputFilter;
 import android.text.TextWatcher;
 import android.view.Gravity;
 import android.view.KeyEvent;
 import android.os.Bundle;
 import android.view.View;
 import android.view.MotionEvent;
 import android.view.ViewGroup;
 import android.view.LayoutInflater;
 import android.view.Window;
 import android.view.WindowManager;
 import android.widget.AdapterView;
 import android.widget.Button;
 import android.widget.CheckBox;
 import android.widget.DatePicker;
 import android.widget.EditText;
 import android.widget.ImageButton;
 import android.widget.LinearLayout;
 import android.widget.RadioButton;
 import android.widget.RadioGroup;
 import android.widget.ListView;
 import android.widget.SimpleAdapter;
 import android.widget.BaseAdapter;
 import android.widget.Spinner;
 import android.widget.TextView;
 import android.widget.TimePicker;
 import android.widget.ArrayAdapter;

 import org.joda.time.convert.Converter;

 import Utility.*;
 import Common.*;

 public class Events extends Activity {
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
        LinearLayout formMember;


     //Member
     LinearLayout secName;
     View lineName;
     TextView VlblName;
     EditText txtName;
     LinearLayout secRth;
     View lineRth;
     TextView VlblRth;
     Spinner spnRth;
     LinearLayout secSex;
     View lineSex;
     TextView VlblSex;
     RadioGroup rdogrpSex;

     RadioButton rdoSex1;
     RadioButton rdoSex2;
     LinearLayout secBDate;
     View lineBDate;
     TextView VlblBDate;
     EditText dtpBDate;
     LinearLayout secAgeY;
     View lineAgeY;
     TextView VlblAgeY;
     EditText txtAgeY;
     LinearLayout secMoNo;
     View lineMoNo;
     TextView VlblMoNo;
     Spinner spnMoNo;

     LinearLayout secFaNo;
     View lineFaNo;
     TextView VlblFaNo;
     Spinner spnFaNo;

     LinearLayout secEdu;
     View lineEdu;
     TextView VlblEdu;
     Spinner spnEdu;
     LinearLayout secMS;
     View lineMS;
     TextView VlblMS;
     Spinner spnMS;

     LinearLayout secOcp;
     View lineOcp;
     TextView VlblOcp;
     Spinner spnOcp;

     LinearLayout seclblSpsl;
     View linelblSpsl;

     LinearLayout secSp1;
     View lineSp1;
     TextView VlblSp1;
     Spinner spnSp1;

     LinearLayout secSp2;
     View lineSp2;
     TextView VlblSp2;
     Spinner spnSp2;

     LinearLayout secSp3;
     View lineSp3;
     TextView VlblSp3;
     Spinner spnSp3;
     LinearLayout secSp4;
     View lineSp4;
     TextView VlblSp4;
     Spinner spnSp4;

     LinearLayout secEnType;
     View lineEnType;
     TextView VlblEnType;
     EditText txtEnType;
     LinearLayout secEnDate;
     View lineEnDate;
     TextView VlblEnDate;
     EditText dtpEnDate;
     LinearLayout secExType;
     View lineExType;
     TextView VlblExType;
     EditText txtExType;
     LinearLayout secExDate;
     View lineExDate;
     TextView VlblExDate;
     EditText dtpExDate;
     //TextView dtpVDate;
     CheckBox chkNeedReview;
     //Member
     TextView lblName;


    static String TableName;

    static String STARTTIME = "";
    static String DEVICEID  = "";
    static String ENTRYUSER = "";
     static String EvType="";
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
     static String ENDATE = "";
    static String PNO = "";
     static String VISITDATE = "";

 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.events);
         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = sp.getValue(this, "deviceid");
         ENTRYUSER = sp.getValue(this, "userid");
         CLUSTER   = sp.getValue(this, "cluster");
         BLOCK     = sp.getValue(this, "block");
         ROUNDNO   = sp.getValue(this, "roundno");
         VISITDATE = sp.getValue(this, "visitdate");

         IDbundle = getIntent().getExtras();
         VILL = IDbundle.getString("Vill");
         BARI = IDbundle.getString("Bari");
         HH = IDbundle.getString("HH");
         MSLNO = IDbundle.getString("MSlNo");
         EVTYPE = IDbundle.getString("EvType");
         EVDATE = IDbundle.getString("EvDate");
         //ROUNDNO = IDbundle.getString("roundno");
         OLDNEWHH = IDbundle.getString("OldNew");
         ENDATE = IDbundle.getString("endate");
         PNO = IDbundle.getString("pno");

         TableName = "tmpEvents";

         //turnGPSOn();

         //GPS Location
         //FindLocation();
         // Double.toString(currentLatitude);
         // Double.toString(currentLongitude);
         final Dialog dialog = new Dialog(Events.this);

         lblHeading = (TextView)findViewById(R.id.lblHeading);
         formMember = (LinearLayout)findViewById(R.id.formMember);
         lblName = (TextView)findViewById(R.id.lblName);
         lblName.setText(IDbundle.getString("name"));

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(Events.this);
                 adb.setTitle("Close");
                 adb.setMessage("আপনি কি এই ফরম থেকে বের হতে চান [হ্যাঁ/না]?");
                 adb.setNegativeButton("না", null);
                 adb.setPositiveButton("হ্যাঁ", new AlertDialog.OnClickListener() {
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

         if(MSLNO.length()==0)
            txtMSlNo.setText(MemNo(VILL, BARI, HH));
         else
             txtMSlNo.setText(MSLNO);

         txtMSlNo.setEnabled(false);

         secPNo=(LinearLayout)findViewById(R.id.secPNo);
         linePNo=(View)findViewById(R.id.linePNo);
         VlblPNo=(TextView) findViewById(R.id.VlblPNo);
         txtPNo=(EditText) findViewById(R.id.txtPNo);
         //txtPNo.setText(VILL.toString()+BARI.toString()+HH.toString()+txtMSlNo.getText().toString());
         txtPNo.setText(PNO);

         secEvType=(LinearLayout)findViewById(R.id.secEvType);
         lineEvType=(View)findViewById(R.id.lineEvType);
         VlblEvType=(TextView) findViewById(R.id.VlblEvType);
         spnEvType=(Spinner) findViewById(R.id.spnEvType);
         final Spinner EvType = (Spinner)findViewById(R.id.spnEvType);

         String Sex = "";
         String MS  = "";
         String PS  = "";
         int MAge   = 0;

         //New Member

         if (OLDNEWHH.equals("new"))
         {
             EvType.setAdapter(C.getArrayAdapter("Select distinct '  'EV from EventCode union SELECT (EvType||'-'||EvName)Ev FROM EventCode where EvType in('20','21','22','23','25')"));

             String Code = C.ReturnSingleValue("Select EnType from tmpMember where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and MSlNo='" + MSLNO + "'");
             spnEvType.setAdapter(C.getArrayAdapter("SELECT distinct (EvType||'-'||EvName) FROM EventCode Where EvType='" + Code + "'"));

             if(EvType.equals("21"))
             {
                 txtInfo1.setVisibility(View.GONE);
                 txtInfo2.setVisibility(View.GONE);
                 txtInfo3.setVisibility(View.GONE);
                 txtInfo4.setVisibility(View.GONE);
                 dtpVDate.setVisibility(View.VISIBLE);
             }
             else if(EvType.equals("22"))
             {
//                 dtpEvDate.setVisibility(View.VISIBLE);
                 txtInfo1.setVisibility(View.GONE);
                 txtInfo2.setVisibility(View.GONE);
                 txtInfo3.setVisibility(View.GONE);
                 txtInfo4.setVisibility(View.GONE);
                 dtpVDate.setVisibility(View.VISIBLE);
                 formMember.setVisibility(View.VISIBLE);
             }
             else if(EvType.equals("23"))
             {
//                 dtpEvDate.setVisibility(View.VISIBLE);
                 txtInfo1.setVisibility(View.GONE);
                 txtInfo2.setVisibility(View.GONE);
                 txtInfo3.setVisibility(View.GONE);
                 txtInfo4.setVisibility(View.GONE);
                 dtpVDate.setVisibility(View.VISIBLE);
                 formMember.setVisibility(View.VISIBLE);
             }
             else if(EvType.equals("25")){
                 dtpEvDate.setText("");
                 secInfo1.setVisibility(View.VISIBLE); //Mother serial no
                 VlblInfo1.setText("মায়ের সিরিয়াল নম্বর");
                 VlblOth.setVisibility(View.GONE);
                 secInfo2.setVisibility(View.GONE);
                 txtInfo2.setVisibility(View.GONE);
                 spnInfo1.setVisibility(View.GONE);
                 spnInfo2.setVisibility(View.GONE);

                 formMember.setVisibility(View.VISIBLE);
             }
         }

         //Old Member
         else if (OLDNEWHH.equals("old"))
         {
         /*    Cursor cur = C.ReadData("Select cast(((julianday(date('now'))-julianday(bdate))/365.25)as int)MAge,Sex,MS from tmpMember where  vill || bari || hh || MSLno='"+ (VILL+BARI+HH+MSLNO) +"'");
             cur.moveToFirst();
             while(!cur.isAfterLast())
             {
                 MAge = Integer.valueOf(cur.getString(0));
                 Sex  = cur.getString(1).toString();
                 MS   = cur.getString(2).toString();
                 cur.moveToNext();
             }
             cur.close();*/
             String[] temp = Connection.split(C.ReturnSingleValue("Select cast(cast(((julianday(date('now'))-julianday(bdate))/365.25)as int) as text)||','||Sex||','||MS||','||ifnull(PStat,'') from tmpMember where  vill || bari || hh || MSLno='"+ (VILL+BARI+HH+MSLNO) +"'"),',');
             MAge = Integer.parseInt(temp[0]);
             Sex  = temp[1];
             MS   = temp[2];
             PS   = temp[3];

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
                     EvType.setAdapter(C.getArrayAdapter("Select distinct '  'EV from EventCode union SELECT (EvType||'-'||EVName)Ev FROM EventCode where EvType not in('20','21','22','23','24','25','26','30','31','40','49','41','43','44','56','57')"));
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

                 formMember.setVisibility(View.GONE);
                 secInfo1.setVisibility(View.GONE);
                 txtInfo1.setText("");
                 spnInfo1.setSelection(0);
                 secInfo2.setVisibility(View.GONE);
                 txtInfo2.setText("");
                 spnInfo2.setSelection(0);
                 secInfo3.setVisibility(View.GONE);
                 txtInfo3.setText("");
                 spnInfo3.setSelection(0);
                 secInfo4.setVisibility(View.GONE);
                 txtInfo4.setText("");
                 spnInfo4.setSelection(0);

                 if(EVCODE.equals("12"))
                 {
                     dtpEvDate.setText(Global.DateNowDMY());
                     dtpEvDate.setEnabled(false);
                     formMember.setVisibility(View.VISIBLE);
                     DataSearchMember(VILL,BARI,HH,MSLNO,"tmpMember");
                 }
                 else if(EVCODE.equals("20"))
                 {
                     dtpEvDate.setText(Global.DateNowDMY());
                     dtpEvDate.setEnabled(false);
                     formMember.setVisibility(View.VISIBLE);
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
                     txtInfo2.setVisibility(View.GONE);
                     spnInfo1.setVisibility(View.GONE);
                     spnInfo2.setAdapter(C.getArrayAdapter("Select distinct '  'EV from EventCode union SELECT (EvType||'-'||EVName)Ev FROM EventCode where EvType in('30','31','32','33','34')"));

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
                     formMember.setVisibility(View.VISIBLE);
                     DataSearch(VILL,BARI,HH,MSLNO,EVCODE,EVDATE,ROUNDNO);
                 }

                 //Internal Movement
                 else if(EVCODE.equals("22"))
                 {
                     dtpEvDate.setText(ENDATE);
                     dtpEvDate.setEnabled(false);
                     secInfo1.setVisibility(View.VISIBLE);
                     VlblOth.setVisibility(View.GONE);
                     VlblInfo1.setText("পূর্বের খানা নাম্বার");
                     txtInfo1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.valueOf(11))});
                     formMember.setVisibility(View.VISIBLE);
                     DataSearchMigMemberNew("52",Global.DateConvertYMD(ENDATE),txtPNo.getText().toString());
                 }
                 else if(EVCODE.equals("23"))
                 {
                     dtpEvDate.setText(ENDATE);
                     dtpEvDate.setEnabled(false);
                     secInfo1.setVisibility(View.VISIBLE);
                     VlblOth.setVisibility(View.GONE);
                     VlblInfo1.setText("মূর্বের খানা নাম্বার");
                     formMember.setVisibility(View.VISIBLE);
                     DataSearchMigMemberNew("53",Global.DateConvertYMD(ENDATE),txtPNo.getText().toString());
                 }

                 //Birth
                 else if(EVCODE.equals("25"))
                 {
                     dtpEvDate.setText(ENDATE);
                     dtpEvDate.setEnabled(false);
                     secInfo1.setVisibility(View.VISIBLE); //Mother serial no
                     spnInfo1.setVisibility(View.VISIBLE);

                     String MoNo = C.ReturnSingleValue("Select Info1 from tmpEvents where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and MSlNo='" + MSLNO + "'");
                     spnInfo1.setAdapter(C.getArrayAdapter("Select MSlNo||'-'||Name from Member where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and MSlNo='" + MoNo.toString() + "'"));

                     VlblInfo1.setText("মায়ের সিরিয়াল নম্বর");
                     VlblOth.setVisibility(View.GONE);
                     secInfo2.setVisibility(View.GONE);
                     txtInfo1.setVisibility(View.GONE);
                     txtInfo2.setVisibility(View.GONE);
                     spnInfo2.setVisibility(View.GONE);
                     formMember.setVisibility(View.VISIBLE);
                 }
                 //Marital Status
                 else if(EVCODE.equals("31"))
                 {
                     dtpEvDate.setText("");
                     dtpEvDate.setEnabled(true);
                     secInfo1.setVisibility(View.VISIBLE);
                     spnInfo1.setVisibility(View.GONE);
                     txtInfo1.setVisibility(View.VISIBLE);
                     VlblInfo1.setText("স্বামী/স্ত্রীর বয়স");
                     txtInfo1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.valueOf(2))});
                 }
                 else if(EVCODE.equals("32")){
                     dtpEvDate.setText("");
                     dtpEvDate.setEnabled(true);
                     secInfo1.setVisibility(View.VISIBLE);
                     spnInfo1.setVisibility(View.GONE);
                     txtInfo1.setVisibility(View.VISIBLE);
                     VlblInfo1.setText("স্বামী/স্ত্রীর বয়স");
                     txtInfo1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.valueOf(2))});

                 }else if(EVCODE.equals("33")){
                     dtpEvDate.setText("");
                     dtpEvDate.setEnabled(true);
                     secInfo1.setVisibility(View.VISIBLE);
                     spnInfo1.setVisibility(View.GONE);
                     txtInfo1.setVisibility(View.VISIBLE);
                     VlblInfo1.setText("স্বামী/স্ত্রীর বয়স");
                     txtInfo1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.valueOf(2))});

                 }else if(EVCODE.equals("34")){
                     dtpEvDate.setText("");
                     dtpEvDate.setEnabled(true);
                     secInfo1.setVisibility(View.VISIBLE);
                     secInfo1.setVisibility(View.VISIBLE);
                     spnInfo1.setVisibility(View.GONE);
                     txtInfo1.setVisibility(View.VISIBLE);
                     VlblInfo1.setText("স্বামী/স্ত্রীর বয়স");
                     txtInfo1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.valueOf(2))});
                 }

                 //Pregnancy Information
                 else if(EVCODE.equals("40")){
                     dtpEvDate.setText(Global.DateNowDMY());
                 }else if(EVCODE.equals("41")){
                     dtpEvDate.setText("");
                 }else if(EVCODE.equals("42")){
                     dtpEvDate.setText("");
                     secInfo1.setVisibility(View.VISIBLE);
                     txtInfo1.setVisibility(View.GONE);
                     spnInfo1.setVisibility(View.VISIBLE);
                     VlblInfo1.setText("গর্ভের ফলাফল");
                     spnInfo1.setAdapter(C.getArrayAdapter("Select '' Union Select OutResult from POR"));

                     secInfo2.setVisibility(View.VISIBLE);
                     txtInfo2.setVisibility(View.GONE);
                     spnInfo2.setVisibility(View.VISIBLE);
                     VlblInfo2.setText("ফলাফলের স্থান");
                     spnInfo2.setAdapter(C.getArrayAdapter("Select '' Union Select Code||'-'||Place from POP"));

                     secInfo3.setVisibility(View.VISIBLE);
                     txtInfo3.setVisibility(View.GONE);
                     spnInfo3.setVisibility(View.VISIBLE);
                     VlblInfo3.setText("সহায়তাকারী");
                     spnInfo3.setAdapter(C.getArrayAdapter("Select '' Union Select Code||'-'||Atten from POA"));

                     secInfo4.setVisibility(View.GONE);
                     txtInfo4.setVisibility(View.GONE);
                     txtInfo4.setText(C.ReturnSingleValue("select LMPDt from tmpMember where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MslNo='"+ MSLNO +"'"));
                     spnInfo4.setVisibility(View.VISIBLE);

                 }else if(EVCODE.equals("49")){
                     dtpEvDate.setText(Global.DateNowDMY());
                 }
                 //Migration out
                 else if(EVCODE.equals("51")){
                     dtpEvDate.setText("");
                 }else if(EVCODE.equals("52")){
                     dtpEvDate.setText("");
                 }else if(EVCODE.equals("53")){
                     dtpEvDate.setText("");
                 }else if(EVCODE.equals("54")){
                     dtpEvDate.setText(Global.DateNowDMY());
                 }else if(EVCODE.equals("55")){
                     dtpEvDate.setText("");
                 }
                 //Mothers Serial Number
                 else if(EVCODE.equals("61"))
                 {
                     dtpEvDate.setText("");
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
                            spnInfo2.setAdapter(C.getArrayAdapter("Select MSlNo||'-'||Name from Member where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and MSlNo='" + PMoNo.toString() + "'"));
                        }
                         if  (PMoNo.equals("00")) {
                             spnInfo2.setAdapter(C.getArrayAdapter("Select '00-এই খানার সদস্য নয়'"));
                         }
                     }
                 }
                 //Father Serial Number
                 else if(EVCODE.equals("62"))
                 {
                     dtpEvDate.setText("");
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
                             spnInfo2.setAdapter(C.getArrayAdapter("Select MSlNo||'-'||Name from Member where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and MSlNo='" + PFoNo.toString() + "'"));
                         }
                         if  (PFoNo.equals("00")) {
                             spnInfo2.setAdapter(C.getArrayAdapter("Select '00-এই খানার সদস্য নয়'"));
                         }
                     }
                 }

                 //Spouses Serial Number
                 else if(EVCODE.equals("63"))
                 {
                     dtpEvDate.setText("");
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

                 else if(EVCODE.equals("64") | EVCODE.equals("71") | EVCODE.equals("72")) {
                     dtpEvDate.setText("");
                     secInfo1.setVisibility(View.VISIBLE);
                     spnInfo1.setVisibility(View.VISIBLE);
                     txtInfo1.setVisibility(View.GONE);
                     txtInfo2.setVisibility(View.GONE);
                     VlblInfo2.setVisibility(View.GONE);
                     secVDate.setVisibility(View.GONE);
                     secInfo2.setVisibility(View.GONE);

                     if (EVCODE.equals("64")) {
                         VlblInfo1.setText("সম্পর্কের কোড");
                         spnInfo1.setAdapter(C.getArrayAdapter("Select distinct ' 'Name union SELECT Code||'-'||Name FROM RTH"));
                         txtInfo2.setText(C.ReturnSingleValue("select Rth from tmpMember where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MslNo='"+ MSLNO +"'"));
                     } else if (EVCODE.equals("71")) {
                         VlblInfo1.setText("শিক্ষার কোড");
                         spnInfo1.setAdapter(C.getArrayAdapter("Select distinct ' 'Name union SELECT Code||'-'||Name FROM EDU"));
                         txtInfo2.setText(C.ReturnSingleValue("select Edu from tmpMember where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MslNo='"+ MSLNO +"'"));

                     } else if (EVCODE.equals("72")) {
                         VlblInfo1.setText("পেশার কোড");
                         spnInfo1.setAdapter(C.getArrayAdapter("Select distinct ' 'Name union SELECT Code||'-'||Name FROM OCP"));
                         txtInfo2.setText(C.ReturnSingleValue("select Ocp from tmpMember where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MslNo='"+ MSLNO +"'"));
                     }
                 }
                 else
                 {
                     formMember.setVisibility(View.GONE);
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

         MemberInitialize();

         //Hide all skip variables
         //secEvDate.setVisibility(View.GONE);
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

         Button cmdSave = (Button) findViewById(R.id.cmdSave);
         cmdSave.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 DataSave();
             }});
     }
   catch(Exception  e)
   {
       Connection.MessageBox(Events.this, e.getMessage());
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

             DV = Global.DateValidate(dtpEvDate.getText().toString());
             if(DV.length()!=0 & secEvDate.isShown())
             {
                 Connection.MessageBox(Events.this, "ঘটনার তারিখ সঠিক নয়");
                 dtpEvDate.requestFocus();
                 return;
             }
             String EV = "";
             if(spnEvType.getCount()==1)
             {
                 EV = spnEvType.getSelectedItem().toString().split("-")[0];
             }
             else {
                 EV = (spnEvType.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnEvType.getSelectedItem().toString(), "-"));
             }

             if(!EV.equals("42"))
             {
                 if (txtInfo1.getText().toString().length()==0  & txtInfo1.isShown())
                 {
                     Connection.MessageBox(Events.this, "স্বামী/স্ত্রীর বয়স খালি রাখা যাবেনা.");
                     txtInfo1.requestFocus();
                     return;
                 }
                 if (txtInfo2.getText().toString().length()==0  & txtInfo2.isShown())
                 {
                     Connection.MessageBox(Events.this, "Required field: খালি রাখা যাবেনা.");
                     txtInfo2.requestFocus();
                     return;
                 }
                 else if (txtInfo3.getText().toString().length()==0  & txtInfo3.isShown())
                 {
                     Connection.MessageBox(Events.this, "Required field: খালি রাখা যাবেনা.");
                     txtInfo3.requestFocus();
                     return;
                 }
             }
             else if(EV.equals("42"))
             {
                 if (spnInfo1.getSelectedItemPosition()==0  & spnInfo1.isShown())
                 {
                     Connection.MessageBox(Events.this, "গর্ভের ফলাফল খালি রাখা যাবেনা");
                     spnInfo1.requestFocus();
                     return;
                 }
                 if (spnInfo2.getSelectedItemPosition()==0  & spnInfo2.isShown())
                 {
                     Connection.MessageBox(Events.this, "ফলাফলের স্থান খালি রাখা যাবেনা");
                     spnInfo2.requestFocus();
                     return;
                 } else if (spnInfo3.getSelectedItemPosition()==0  & spnInfo3.isShown())
                 {
                     Connection.MessageBox(Events.this, "উপস্থিত ছিলেন খালি রাখা যাবেনা");
                     spnInfo3.requestFocus();
                     return;
                 }
             }

             EVTYPE = spnEvType.getSelectedItem().toString().split("-")[0];

             String VDate  = C.ReturnSingleValue("select VDate from tmpVisits Where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH + "' and Rnd='"+ ROUNDNO +"'");
             String EnDate  = C.ReturnSingleValue("select EnDate from tmpMember Where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH + "' and Mslno='"+ MSLNO + "'");
             String EvDate = Global.DateConvertYMD(dtpEvDate.getText().toString());
             String Code   = txtInfo1.getText().toString();
             String SpNo   = txtInfo1.getText().toString();

             String CodeList = "";

             String EDT = Global.DateValidate(dtpEvDate.getText().toString());
             SimpleDateFormat evdateformat = new SimpleDateFormat("dd/MM/yyyy");
             SimpleDateFormat endateformat = new SimpleDateFormat("yyyy-MM-dd");

             Integer ECode = Integer.parseInt(spnEvType.getSelectedItem().toString().substring(0, 2));

             String Agey = C.ReturnSingleValue("select AgeY from tmpMember where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and MSlNo='" + MSLNO + "'");

             Date evd;
             if(ECode == 12 | ECode == 40 | ECode == 49)
                 evd = evdateformat.parse(Global.DateConvertDMY(VDate));
             else
                 evd = evdateformat.parse(dtpEvDate.getText().toString());


            //----------------------------------------------------------------------------------------------------------------------------

             if (ECode == 12)
             {

             }
             else if ((ECode >= 20 & ECode <= 25) | (ECode >= 30 & ECode <= 34) | (ECode >= 51 & ECode <= 56))
             {
                 if(EDT.length()!=0)
                 {
                     Connection.MessageBox(Events.this, EDT);
                     return;
                 }

                 //migration - in
                 //-----------------------------------------------------------------------------------------------
                 if(ECode == 21)
                 {
                     String ED = Global.DateValidate(dtpEvDate.getText().toString());
                     if(txtInfo1.getText().toString().length()==0)
                     {
                         Connection.MessageBox(Events.this, "Reason/Spouse's age খালি রাখা যাবে না।");
                         return;
                     }
                     else if(!txtInfo1.getText().toString().equals("77") & ED.length()!=0)
                     {
                         Connection.MessageBox(Events.this, ED);
                         return;
                     }
                     else if(!txtInfo1.getText().toString().equals("77") &
                             (!txtInfo2.getText().toString().equals("31") &
                              !txtInfo2.getText().toString().equals("32") &
                              !txtInfo2.getText().toString().equals("33") &
                              !txtInfo2.getText().toString().equals("34")))
                     {
                         Connection.MessageBox(Events.this, "সদস্য কি কারনে এ খানায় আসল সেটি অবশ্যই ৩১/৩২/৩৩/৩৪ হতে হবে।");
                         return;
                     }
                     //**reason date should be less than or equal in migration date
                 }

                 //marital status
                 //-----------------------------------------------------------------------------------------------
                 else if((ECode >= 31 & ECode <= 34))
                 {
                     if(Integer.valueOf(Agey) < 10)
                     {
                         Connection.MessageBox(Events.this, "সদস্যের বয়স ১০ এর কম হলে ইভেন্ট ৩১,৩২,৩৩,৩৪ হতে পারে না।"); return;
                     }
                     else if(txtInfo1.getText().toString().length()==0)
                     {
                         Connection.MessageBox(Events.this, "স্বামী/স্ত্রী এর বয়স খালি রাখা যাবে না।");
                         return;
                     }
                     else if(Integer.parseInt(txtInfo1.getText().toString()) < 10)
                     {
                         Connection.MessageBox(Events.this, "স্বামী/স্ত্রী এর বয়স অবশ্যই ১০ বছরের সমান/বেশী হতে হবে।");
                         return;
                     }
                 }
             }

             //==========================================================================================================================================================
             //Member Validation Check
             if(EVTYPE.equals("12")|EVTYPE.equals("20")|EVTYPE.equals("21") |EVTYPE.equals("22") |EVTYPE.equals("23") |EVTYPE.equals("25"))
             {
                 String ED = "";
                 if (EVTYPE.equals("12") | EVTYPE.equals("40") | EVTYPE.equals("49"))
                 {
                     ED = VDate;
                 }
                 else
                 {
                     ED = EvDate;
                 }

                 //(Temporary Table) check the information is available or not
                 if(!EVTYPE.equals("12") & !EVTYPE.equals("20")  & !EVTYPE.equals("21") & !EVTYPE.equals("22") & !EVTYPE.equals("23") & !EVTYPE.equals("25") & C.Existence("Select * from tmpEvents where vill||Bari||hh='"+ Household +"' and MSlNo='"+ MSLNO +"' and EvType='"+ EVTYPE.toString() +"' and EvDate='"+ ED +"' and Rnd='"+ ROUNDNO +"'"))
                 {
                     Connection.MessageBox(Events.this, "ইভেন্ট কোড ("+ EVTYPE +") রউন্ড নাম্বার "+ ROUNDNO +" এ ঘটানো হয়েছে।");
                     return;
                 }

                 //(Event Table) check the information is available or not
                 if(!EVTYPE.equals("12") & !EVTYPE.equals("20") & !EVTYPE.equals("21") & !EVTYPE.equals("22") & !EVTYPE.equals("23") & !EVTYPE.equals("25") & C.Existence("Select * from Events where vill||Bari||hh='"+ Household +"' and MSlNo='"+ MSLNO +"' and EvType='"+ EVTYPE.toString() +"' and EvDate='"+ ED +"' and Rnd='"+ ROUNDNO +"'"))
                 {
                     Connection.MessageBox(Events.this, "ইভেন্ট কোড ("+ EVTYPE +") রউন্ড নাম্বার "+ ROUNDNO +" এ ঘটানো হয়েছে।");
                     return;
                 }

                 if(txtName.getText().toString().length()==0 & secName.isShown())
                 {
                     Connection.MessageBox(Events.this, "প্রশ্ন ৩: খানার সদস্যদের নাম খালি রাখা যাবেনা.");
                     txtName.requestFocus();
                     return;
                 }
                 else if(spnRth.getSelectedItemPosition()==0  & secRth.isShown())
                 {
                     Connection.MessageBox(Events.this, "প্রশ্ন ৪: খানা প্রধানের সাথে সম্পর্ক খালি রাখা যাবেনা.");
                     spnRth.requestFocus();
                     return;
                 }
                 else if(!rdoSex1.isChecked() & !rdoSex2.isChecked() & secSex.isShown())
                 {
                     Connection.MessageBox(Events.this, "প্রশ্ন ৫: পুরুষ/মহিলা থেকে একটি অপশন নির্বাচন করুন");
                     rdoSex1.requestFocus();
                     return;
                 }
                 DV = Global.DateValidate(dtpBDate.getText().toString());
                 if(DV.length()!=0 & secBDate.isShown())
                 {
                     Connection.MessageBox(Events.this, "জন্ম তারিখ সঠিক নয়।");
                     dtpBDate.requestFocus();
                     return;
                 }
                 else if(txtAgeY.getText().toString().length()==0 & secAgeY.isShown())
                 {
                     Connection.MessageBox(Events.this, "প্রশ্ন ৭: বয়স (পূর্ণ বছরে) খালি রাখা যাবেনা.");
                     txtAgeY.requestFocus();
                     return;
                 }

                 int ageday = Global.DateDifferenceDays(dtpEvDate.getText().toString(),dtpBDate.getText().toString());
                 int ageyear = Integer.parseInt(txtAgeY.getText().toString().length()==0?"0":txtAgeY.getText().toString());

                 Double  D=ageday/365.25;
                 int i = Integer.valueOf(D.intValue());

                 if(i!=ageyear)
                 {
                     Connection.MessageBox(Events.this, "বয়স এর সাথে জন্মতারিখ মিল নেই, বয়স "+ i +" বছর হতে হবে।");
                     return;
                 }
                 else if(Integer.valueOf(txtAgeY.getText().toString().length()==0 ? "0" : txtAgeY.getText().toString()) < 0 || Integer.valueOf(txtAgeY.getText().toString().length()==0 ? "110" : txtAgeY.getText().toString()) > 110)
                 {
                     Connection.MessageBox(Events.this, "সদস্যের বয়স ০ থেকে ১১০ এর ভিতরে হতে হবে।");
                     txtAgeY.requestFocus();
                     return;
                 }
                 else if(spnMoNo.getSelectedItemPosition()==0  & spnMoNo.isShown())
                 {
                     Connection.MessageBox(Events.this, "প্রশ্ন ৮: মায়ের সিরিয়াল নম্বর খালি রাখা যাবেনা");
                     spnMoNo.requestFocus();
                     return;
                 }
                 else if(spnFaNo.getSelectedItemPosition()==0  & spnFaNo.isShown())
                 {
                     Connection.MessageBox(Events.this, "প্রশ্ন ৯: বাবার সিরিয়াল নম্বর খালি রাখা যাবেনা");
                     spnFaNo.requestFocus();
                     return;
                 }
                 else if(spnEdu.getSelectedItemPosition()==0  & secEdu.isShown())
                 {
                     Connection.MessageBox(Events.this, "প্রশ্ন ১০: সর্বোচ্চ শ্রেণি পাশ খালি রাখা যাবেনা.");
                     spnEdu.requestFocus();
                     return;
                 }
                 else if(spnMS.getSelectedItemPosition()==0  & secMS.isShown())
                 {
                     Connection.MessageBox(Events.this, "প্রশ্ন ১১: বৈবাহিক অবস্থা খালি রাখা যাবেনা.");
                     spnMS.requestFocus();
                     return;
                 }
                 else if(spnOcp.getSelectedItemPosition()==0  & secOcp.isShown())
                 {
                     Connection.MessageBox(Events.this, "প্রশ্ন ১২: পেশা খালি রাখা যাবেনা.");
                     spnOcp.requestFocus();
                     return;
                 }
                 else if(spnSp1.getSelectedItemPosition()==0  & spnSp1.isShown())
                 {
                     Connection.MessageBox(Events.this, "প্রশ্ন ১৩: সিরিয়াল নম্বর ১ খালি রাখা যাবেনা.");
                     spnSp1.requestFocus();
                     return;
                 }

                 String[] RTH = spnRth.getSelectedItem().toString().split("-");
                 String[] MS = spnMS.getSelectedItem().toString().split("-");
                 String[] Ocp = spnOcp.getSelectedItem().toString().split("-");
                 String[] Sp1 = spnSp1.getSelectedItem().toString().split("-");
                 String[] MoSl = spnMoNo.getSelectedItem().toString().split("-");
                 String[] FaSl = spnFaNo.getSelectedItem().toString().split("-");
                 String[] Edu = spnEdu.getSelectedItem().toString().split("-");

                 if ((RTH[0].equals("02") | RTH[0].equals("04") | RTH[0].equals("07") | RTH[0].equals("10") | RTH[0].equals("11") | RTH[0].equals("15") | RTH[0].equals("17")) & MS[0].equals("30")) {
                     Connection.MessageBox(Events.this, "খানা প্রধানের সাথে সম্পর্ক  ০২, ০৪, ০৭, ১০, ১১, ১৫, ১৭ হলে বৈবাহিক অবস্থা অবিবাহিত হতে পারে না.");
                     spnRth.requestFocus();
                     return;
                 }
                 else if ((RTH[0].equals("02")) & MS[0].equals("32"))
                 {
                     Connection.MessageBox(Events.this, "খানা প্রধানের সাথে সম্পর্ক  ০২-খানা প্রধানের স্বামী/স্ত্রী হলে বৈবাহিক অবস্থা ৩১-বর্তমানে বিবাহিতা/বিবাহিত হবে.");
                     spnRth.requestFocus();
                     return;
                 }
                 else if ((RTH[0].equals("02")) & MS[0].equals("33")) {
                     Connection.MessageBox(Events.this, "খানা প্রধানের সাথে সম্পর্ক  ০২-খানা প্রধানের স্বামী/স্ত্রী হলে বৈবাহিক অবস্থা ৩১-বর্তমানে বিবাহিতা/বিবাহিত হবে.");
                     spnRth.requestFocus();
                     return;
                 }
                 else if ((RTH[0].equals("02")) & MS[0].equals("34")) {
                     Connection.MessageBox(Events.this, "খানা প্রধানের সাথে সম্পর্ক  ০২-খানা প্রধানের স্বামী/স্ত্রী হলে বৈবাহিক অবস্থা ৩১-বর্তমানে বিবাহিতা/বিবাহিত হবে.");
                     spnRth.requestFocus();
                     return;
                 }
                 else if ((RTH[0].equals("02")) & Sp1[0].equals("00")) {
                     Connection.MessageBox(Events.this, "প্রশ্ন ৪. খানা প্রধানের স্বামী/স্ত্রী হলে, প্রশ্ন ১৩. ১ম স্বামী/স্ত্রী  সিরিয়াল 00 হবেনা.");
                     spnRth.requestFocus();
                     return;
                 }
                 else if(txtMSlNo.getText().toString().equalsIgnoreCase(Connection.SelectedSpinnerValue(spnFaNo.getSelectedItem().toString(), "-")))
                 {
                     Connection.MessageBox(Events.this, "প্রশ্ন ৯. বাবার সিরিয়াল ও এই সদস্যের সিরিয়াল একই হবে না");
                     spnFaNo.requestFocus();
                     return;
                 }
                 else if(txtMSlNo.getText().toString().equalsIgnoreCase(Connection.SelectedSpinnerValue(spnMoNo.getSelectedItem().toString(), "-")))
                 {
                     Connection.MessageBox(Events.this, "প্রশ্ন ৮. মায়ের সিরিয়াল ও এই সদস্যের সিরিয়াল একই হবে না");
                     spnMoNo.requestFocus();
                     return;
                 }
                 else if ((RTH[0].equals("04") & Integer.valueOf(txtAgeY.getText().toString().length() == 0 ? "0" : txtAgeY.getText().toString()) < 15))
                 {
                     Connection.MessageBox(Events.this, "খানা প্রধানের মা/বাবা এর বয়স অবশ্যই ১৫ বছরের বেশী হবে");
                     txtAgeY.requestFocus();
                     return;
                 }
                 else if ((RTH[0].equals("02") & Integer.valueOf(txtAgeY.getText().toString().length() == 0 ? "0" : txtAgeY.getText().toString()) < 12))
                 {
                     Connection.MessageBox(Events.this, "খানা প্রধানের স্বামী অথবা স্ত্রী  বয়স অবশ্যই ১৫ বছরের বেশী হবে");
                     txtAgeY.requestFocus();
                     return;
                 }
                 else if ((RTH[0].equals("07") & Integer.valueOf(txtAgeY.getText().toString().length() == 0 ? "0" : txtAgeY.getText().toString()) < 15))
                 {
                     Connection.MessageBox(Events.this, "খানা প্রধানের দাদা/দাদি/নানা/নানি এর বয়স অবশ্যই ১৫ বছরের বেশী হবে");
                     txtAgeY.requestFocus();
                     return;
                 }
                 else if ((RTH[0].equals("10") & Integer.valueOf(txtAgeY.getText().toString().length() == 0 ? "0" : txtAgeY.getText().toString()) < 15))
                 {
                     Connection.MessageBox(Events.this, "খানা প্রধানের শ্বশুর/শাশুড়ী এর বয়স অবশ্যই ১৫ বছরের বেশী হবে");
                     txtAgeY.requestFocus();
                     return;
                 }
                 else if ((RTH[0].equals("01") & Integer.valueOf(txtAgeY.getText().toString().length() == 0 ? "0" : txtAgeY.getText().toString()) < 12))
                 {
                     Connection.MessageBox(Events.this, "খানা প্রধানের এর বয়স অবশ্যই ১২ বছরের বেশী হবে");
                     txtAgeY.requestFocus();
                     return;
                 }
                 else if (!isAgeDifferenceWithParentsValid(txtVill.getText().toString(),txtBari.getText().toString(),txtHH.getText().toString(),
                         Connection.SelectedSpinnerValue(spnFaNo.getSelectedItem().toString(), "-"),
                         Connection.SelectedSpinnerValue(spnMoNo.getSelectedItem().toString(), "-"),
                         txtAgeY.getText().toString())) {
                     Connection.MessageBox(Events.this, "পিতা মাতার সাথে এই সদস্যের বয়স এর পার্থক্য  ঠিক নেই, পিতা আথবা মাতার বয়স দেখুন");
                     txtAgeY.requestFocus();
                     return;
                 }

//                 else if(C.Existence("select count(*) from tmpMember where vill||bari||hh='"+ Household +"' and rth='01' and (extype is null or length(extype)=0) group by vill||bari||hh having count(*)>1"))
//                 {
//                     Connection.MessageBox(Events.this, "এক খানায় ২ জন খানা প্রধান হতে পারেনা");
//                     txtName.requestFocus();
//                     return;
//                 }
                 if(Connection.SelectedSpinnerValue(spnFaNo.getSelectedItem().toString(), "-").equalsIgnoreCase("00") ||(Connection.SelectedSpinnerValue(spnMoNo.getSelectedItem().toString(), "-").equalsIgnoreCase("00")))
                 {

                 }
                 else
                 {
                     if (Connection.SelectedSpinnerValue(spnFaNo.getSelectedItem().toString(), "-").equalsIgnoreCase(Connection.SelectedSpinnerValue(spnMoNo.getSelectedItem().toString(), "-")) & spnMoNo.isShown()) {
                         Connection.MessageBox(Events.this, "প্রশ্ন ৮ এবং ৯. পিতার সিরিয়াল ও মাতার সিরিয়াল একই হবে না");
                         spnFaNo.requestFocus();
                         return;
                     }
                 }

                 String s[]=spnMoNo.getSelectedItem().toString().split("-");
                 String MoSex= C.ReturnSingleValue("Select Sex from tmpMember where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ s[0] + "'");
                 String MoMS= C.ReturnSingleValue("Select MS from tmpMember where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ s[0] + "'");

                 if ((MoSex.equals("1")))
                 {
                     Connection.MessageBox(Events.this, "প্রশ্ন ৮. সদস্যের মা পুরুষ হবে না ");
                     spnRth.requestFocus();
                     return;
                 }
                 if ((MoMS.equals("30")))
                 {
                     Connection.MessageBox(Events.this, "সদস্যের মা '"+ spnMoNo.getSelectedItem() +"' এর বৈবাহিক অবস্থা  ৩০-কখনও বিয়ে হয়নি দেয়া আছে");
                     spnRth.requestFocus();
                     return;
                 }

                 String f[]=spnFaNo.getSelectedItem().toString().split("-");
                 String FoSex= C.ReturnSingleValue("Select Sex from tmpMember where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ f[0] + "'");
                 String FaMS= C.ReturnSingleValue("Select MS from tmpMember where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ f[0] + "'");

                 if ((FoSex.equals("2")))
                 {
                     Connection.MessageBox(Events.this, "প্রশ্ন ৯. সদস্যের বাবা মহিলা হবে না ");
                     spnRth.requestFocus();
                     return;
                 }
                 if ((FaMS.equals("30")))
                 {
                     Connection.MessageBox(Events.this, "সদস্যের বাবা '"+ spnFaNo.getSelectedItem() +"' এর বৈবাহিক অবস্থা  ৩০-কখনও বিয়ে হয়নি দেয়া আছে");
                     spnRth.requestFocus();
                     return;
                 }
                 //-----------------------------------------------------------------------------------------------------
                 if (txtMSlNo.getText().toString().equalsIgnoreCase(Connection.SelectedSpinnerValue(spnSp1.getSelectedItem().toString(), "-"))& spnSp1.isShown()) {
                     Connection.MessageBox(Events.this, "প্রশ্ন ১৩) ১ম স্বামী/স্ত্রী  সিরিয়াল এবং এই সদস্যের সিরিয়াল একই হবে না");
                     spnSp1.requestFocus();
                     return;
                 }
                 else if (txtMSlNo.getText().toString().equalsIgnoreCase(Connection.SelectedSpinnerValue(spnSp2.getSelectedItem().toString(), "-"))& spnSp2.isShown()) {
                     Connection.MessageBox(Events.this, "প্রশ্ন ১৪) ২য় স্বামী/স্ত্রী  এবং এই সদস্যের সিরিয়াল একই হবে না");
                     spnSp2.requestFocus();
                     return;
                 }
                 else if (txtMSlNo.getText().toString().equalsIgnoreCase(Connection.SelectedSpinnerValue(spnSp3.getSelectedItem().toString(), "-"))& spnSp3.isShown()) {
                     Connection.MessageBox(Events.this, "প্রশ্ন ১৫) ৩য় স্বামী/স্ত্রী  এবং এই সদস্যের সিরিয়াল একই হবে না");
                     spnSp3.requestFocus();
                     return;
                 }
                 else if (txtMSlNo.getText().toString().equalsIgnoreCase(Connection.SelectedSpinnerValue(spnSp4.getSelectedItem().toString(), "-"))& spnSp4.isShown()) {
                     Connection.MessageBox(Events.this, "প্রশ্ন ১৬) ৪র্থ স্বামী/স্ত্রী  এবং এই সদস্যের সিরিয়াল একই হবে না");
                     spnSp4.requestFocus();
                     return;
                 }

                 //17 Mar 2017
                 else if (!Connection.SelectedSpinnerValue(spnSp1.getSelectedItem().toString(), "-").equals("00") & Connection.SelectedSpinnerValue(spnFaNo.getSelectedItem().toString(), "-").equalsIgnoreCase(Connection.SelectedSpinnerValue(spnSp1.getSelectedItem().toString(), "-"))& spnSp1.isShown()) {
                     Connection.MessageBox(Events.this, "প্রশ্ন ৯) বাবার সিরিয়াল এবং ১ম স্বামী/স্ত্রী  সিরিয়াল একই হবে না");
                     spnFaNo.requestFocus();
                     return;
                 }
                 else if (!Connection.SelectedSpinnerValue(spnSp1.getSelectedItem().toString(), "-").equals("00") & Connection.SelectedSpinnerValue(spnMoNo.getSelectedItem().toString(), "-").equalsIgnoreCase(Connection.SelectedSpinnerValue(spnSp1.getSelectedItem().toString(), "-"))& spnSp1.isShown()) {
                     Connection.MessageBox(Events.this, "প্রশ্ন ৮) মায়ের সিরিয়াল এবং ১ম স্বামী/স্ত্রী  সিরিয়াল একই হবে না");
                     spnSp1.requestFocus();
                     return;
                 }

                 else if (Connection.SelectedSpinnerValue(spnSp1.getSelectedItem().toString(), "-").equalsIgnoreCase(Connection.SelectedSpinnerValue(spnSp2.getSelectedItem().toString(), "-"))& spnSp2.isShown()) {
                     Connection.MessageBox(Events.this, "প্রশ্ন ১৩) ১ম স্বামী/স্ত্রী সিরিয়াল নং  এবং প্রশ্ন ১৪) ২য় স্বামী/স্ত্রী সিরিয়াল নং একই হবে না");
                     spnSp1.requestFocus();
                     return;
                 }
                 else if (Connection.SelectedSpinnerValue(spnSp2.getSelectedItem().toString(), "-").equalsIgnoreCase(Connection.SelectedSpinnerValue(spnSp3.getSelectedItem().toString(), "-"))& spnSp3.isShown()) {
                     Connection.MessageBox(Events.this, "প্রশ্ন ১৪) ২য় স্বামী/স্ত্রী সিরিয়াল নং  এবং প্রশ্ন ১৫) ৩য় স্বামী/স্ত্রী সিরিয়াল নং একই হবে না");
                     spnSp1.requestFocus();
                     return;
                 }
                 else if (Connection.SelectedSpinnerValue(spnSp3.getSelectedItem().toString(), "-").equalsIgnoreCase(Connection.SelectedSpinnerValue(spnSp4.getSelectedItem().toString(), "-"))& spnSp4.isShown()) {
                     Connection.MessageBox(Events.this, "প্রশ্ন ১৫) ৩য় স্বামী/স্ত্রী সিরিয়াল নং  এবং প্রশ্ন ১৬) ৪র্থ স্বামী/স্ত্রী সিরিয়াল নং একই হবে না");
                     spnSp1.requestFocus();
                     return;
                 }
                 //------------------------Add on 18_07_17-----------------------------------------------------------------------------
                 else if ((MS[0].equals("31") & Integer.valueOf(txtAgeY.getText().toString().length() == 0 ? "0" : txtAgeY.getText().toString()) < 10))
                 {
                     Connection.MessageBox(Events.this, "সদস্যের বৈবাহিক অবস্থা ৩১ হবেনা, সদস্যের  বয়স ১০ বছরের নিচে");
                     txtAgeY.requestFocus();
                     return;
                 }

                 else if ((Ocp[0].equals("31")) & Edu[0].equals("00")) {
                     Connection.MessageBox(Events.this, "পেশা মেধা সম্পন্ন হলে সর্বোচ্চ শ্রেণি পাশ ০০ হবেনা.");
                     spnEdu.requestFocus();
                     return;
                 }
                 else if ((Ocp[0].equals("34")) & Edu[0].equals("00")) {
                     Connection.MessageBox(Events.this, "পেশা পেশাজীবি হলে সর্বোচ্চ শ্রেণি পাশ ০০ হবেনা.");
                     spnEdu.requestFocus();
                     return;
                 }
                 else if ((RTH[0].equals("03")) & MoSl[0].equals("00") & FaSl[0].equals("00")) {
                     Connection.MessageBox(Events.this, "খানা প্রধানের সাথে সম্পর্ক  ছেলে/মেয়ে হলে বাবা এবং মা এর সিরিয়াল নং ০০ হবেনা.");
                     spnRth.requestFocus();
                     return;
                 }

                 else if ((Ocp[0].equals("03")) & rdoSex1.isChecked())
                 {
                     Connection.MessageBox(Events.this, "পেশা গৃহিনী হলে সদস্য পুরুষ হবেনা.");
                     spnRth.requestFocus();
                     return;
                 }
             }

             //----------------------------------------------------------------------------------------------------------------------------
             if (OLDNEWHH.equals("old"))
             {
                 if ((ECode >= 12 & ECode <= 72))
                 {
                     int age = 0;
                     String PStat = "";
                     String PMStatus = "";
                     String LMP = "";
                     String PMNo = "";
                     String PFNo = "";
                     String Sex = "";
                     String PRth = "";
                     String PEdu = "";
                     String POcp = "";

                     String sex = "";
                     String name = "";
                     String sp1 = "";
                     String sp2 = "";
                     String sp3 = "";
                     String sp4 = "";
                     String endate = "";

                     Cursor m = C.ReadData("Select rth,sex,ms,mono,fano,pstat,ifnull(lmpdt,'')lmpdt,edu,ocp,sp1,cast((julianday(date('now'))-julianday(bdate))/365.25 as int)age,ifnull(PStat,''),endate,name from tmpMember where Vill||Bari||HH='" + Household + "' and MslNo='" + MSLNO + "'");
                     m.moveToFirst();
                     while (!m.isAfterLast()) {
                         PRth = m.getString(0).toString();
                         Sex = m.getString(1).toString();
                         PMStatus = m.getString(2).toString();
                         PMNo = m.getString(3).toString();
                         PFNo = m.getString(4).toString();
                         LMP = m.getString(6).toString();
                         PEdu = m.getString(7).toString();
                         POcp = m.getString(8).toString();
                         sp1 = m.getString(9).toString();
                         age = Integer.parseInt(m.getString(10).toString());
                         PStat = m.getString(11).toString();
                         endate = m.getString(12).toString();
                         name = m.getString(13).toString();
                         m.moveToNext();
                     }
                     m.close();

                     if (ECode == 40 | ECode == 49) {
                         if (Sex.equals("1")) {
                             Connection.MessageBox(Events.this, "সদস্য অবশ্যই মহিলা হতে হবে।");
                             return;
                         } else if (!PMStatus.equals("31")) {
                             Connection.MessageBox(Events.this, "সদস্য অবশ্যই বিবাহিত হতে হবে।");
                             return;
                         } else if (age < 10 | age > 49) {
                             Connection.MessageBox(Events.this, "সদস্যের বয়স ১০ এর কম অথবা ৪৯ এর বেশী হলে ইভেন্ট ৪০/৪৯ প্রযোজ্য নয়।");
                             return;
                         } else if (PStat.equals("41")) {
                             Connection.MessageBox(Events.this, "সদস্য বর্তমানে গর্ভবতী, ইভেন্ট ৪০/৪৯ প্রযোজ্য নয়।");
                             return;
                         }
                     }
                     else if (ECode == 41)
                     {
                         if (EDT.length() != 0) {
                             Connection.MessageBox(Events.this, EDT);
                             return;
                         } else if (Sex.equals("1")) {
                             Connection.MessageBox(Events.this, "সদস্য অবশ্যই মহিলা হতে হবে।");
                             return;
                         } else if (!PMStatus.equals("31")) {
                             Connection.MessageBox(Events.this, "সদস্য অবশ্যই বিবাহিত হতে হবে।");
                             return;
                         } else if (age < 10) {
                             Connection.MessageBox(Events.this, "সদস্যের বয়স অবশ্যই ১০ বছরের বেশী হতে হবে।");
                             return;
                         }
                         //(Temporary Table) check the information is available or not
                         if(ECode==41  & C.Existence("Select * from tmpEvents where vill||Bari||hh='"+ Household +"' and MSlNo='"+ MSLNO +"' and EvType='"+ EVTYPE.toString() +"' and Rnd='"+ ROUNDNO +"'"))
                         {
                             Connection.MessageBox(Events.this, "ইভেন্ট কোড ("+ EVTYPE +") রউন্ড নাম্বার "+ ROUNDNO +" এ ঘটানো হয়েছে।");
                             return;
                         }

                         //(Event Table) check the information is available or not
                         if(ECode==41 & C.Existence("Select * from Events where vill||Bari||hh='"+ Household +"' and MSlNo='"+ MSLNO +"' and EvType='"+ EVTYPE.toString() +"' and Rnd='"+ ROUNDNO +"'"))
                         {
                             Connection.MessageBox(Events.this, "ইভেন্ট কোড ("+ EVTYPE +") রউন্ড নাম্বার "+ ROUNDNO +" এ ঘটানো হয়েছে।");
                             return;
                         }

                         //difference between lmp and visit date should be equal or greater than 40 days
                     }
                     else if (ECode == 42)
                     {
                         String EvDate1 = Global.DateConvertYMD(dtpEvDate.getText().toString());
                         /*if (spnInfo1.getSelectedItemPosition()==0) {
                             Connection.MessageBox(Events.this, "Required field: Info2.");
                             txtInfo2.requestFocus();
                             return;
                         }
                         if (txtInfo3.getText().toString().length() == 0 & secInfo3.isShown()) {
                             Connection.MessageBox(Events.this, "Required field: Info3.");
                             txtInfo3.requestFocus();
                             return;
                         } else if (txtInfo4.getText().toString().length() == 0 & secInfo4.isShown()) {
                             Connection.MessageBox(Events.this, "Required field: Info4.");
                             txtInfo4.requestFocus();
                             return;
                         }*/

                         String LMPDT = Global.DateValidate(dtpEvDate.getText().toString());
                         if (EDT.length() != 0) {
                             Connection.MessageBox(Events.this, EDT);
                             return;
                         } else if (LMPDT.length() != 0) {
                             Connection.MessageBox(Events.this, "Invalid LMP date.");
                             return;
                         } else if (!PStat.equals("41")) {
                             Connection.MessageBox(Events.this, "সদস্য অবশ্যই গর্ভবতী হতে হবে।");
                             return;
                         }

                         //difference between LMP and EDD Check
                         int outcode_difference = Global.DateDifferenceDays(Global.DateConvertDMY(EvDate1.toString()), Global.DateConvertDMY(LMP.toString()));
                         int outcome_result = Integer.valueOf(Global.Left(spnInfo1.getSelectedItem().toString(), 2));

                         if(outcode_difference < 0)
                         {
                             Connection.MessageBox(Events.this, "প্রসবের তারিখ অবশ্যই LMP " + LMP + "  এর তারিখের বেশী হতে হবে।");
                             return;
                         }
                         else if (outcome_result == 11 | outcome_result == 12 | outcome_result == 21 | outcome_result == 22 | outcome_result == 23 | outcome_result == 31 | outcome_result == 32 | outcome_result == 33 | outcome_result == 34)
                         {
                             if (outcode_difference < 180) {
                                 Connection.MessageBox(Events.this, "LMP " + LMP + " এবং প্রসবের তারিখের পার্থক্য ১৮০ দিনের বেশী হতে হবে।");
                                 return;
                             } else if (outcode_difference > 310) {
                                 Connection.MessageBox(Events.this, "LMP " + LMP + " এবং প্রসবের তারিখের পার্থক্য অবশ্যই ৩১০ দিনের বেশী হতে পারে না।");
                                 return;
                             }
                         }
                         else if (outcome_result == 1 | outcome_result == 2)
                         {
                             if (outcode_difference < 42) {
                                 Connection.MessageBox(Events.this, "LMP " + LMP + " এবং প্রসবের তারিখের পার্থক্য ৪২ দিনের কম হতে পারে না।");
                                 return;
                             } else if (outcode_difference > 180) {
                                 Connection.MessageBox(Events.this, "LMP " + LMP + " এবং প্রসবের তারিখের পার্থক্য ১৮০ দিনের বেশী হতে পারে না।");
                                 return;
                             }
                         }

                     }
                     else if (ECode == 25)
                     {

                     }
                     else if ((ECode >= 61 & ECode <= 64))
                     {
                         String Code1 = "";
                         String a = spnInfo1.getSelectedItemPosition() == 0 ? "" : spnInfo1.getSelectedItem().toString().split("-")[0];
                         Code1 = a;

                         if (ECode == 61) {
                             if (EDT.length() != 0) {
                                 Connection.MessageBox(Events.this, EDT);
                                 return;
                             } else if (ECode == 61 & Code1.length() == 0) {
                                 Connection.MessageBox(Events.this, "সদস্যের মায়ের সঠিক সিরিয়াল নাম্বার লিখুন");
                                 return;
                             }
                             if (MSLNO.equals(Code1)) {
                                 Connection.MessageBox(Events.this, "মায়ের সিরিয়াল নাম্বার এবং সদস্যের সিরিয়াল নাম্বার একই রকম হবে না।");
                                 return;
                             }
                             if (Connection.SelectedSpinnerValue(spnInfo1.getSelectedItem().toString(), "-").equalsIgnoreCase(Connection.SelectedSpinnerValue(spnInfo2.getSelectedItem().toString(), "-")) & spnInfo2.isShown()) {
                                 Connection.MessageBox(Events.this, "মায়ের বর্তমান সিরিয়াল নাম্বার এবং পূর্বের সিরিয়াল নাম্বার একই রকম হবে না।");
                                 return;
                             }
                         } else if (ECode == 62) {
                             if (EDT.length() != 0) {
                                 Connection.MessageBox(Events.this, EDT);
                                 return;
                             } else if (ECode == 62 & Code1.length() == 0) {
                                 Connection.MessageBox(Events.this, "সদস্যের বাবার সঠিক সিরিয়াল নাম্বার লিখুন।");
                                 return;
                             }
                             if (MSLNO.equals(Code1)) {
                                 Connection.MessageBox(Events.this, "বাবার সিরিয়াল নাম্বার এবং সদস্যের সিরিয়াল নাম্বার একই রকম হবে না।");
                                 return;
                             }
                             if (Connection.SelectedSpinnerValue(spnInfo1.getSelectedItem().toString(), "-").equalsIgnoreCase(Connection.SelectedSpinnerValue(spnInfo2.getSelectedItem().toString(), "-")) & spnInfo2.isShown()) {
                                 Connection.MessageBox(Events.this, "বাবার বর্তমান সিরিয়াল নাম্বার এবং পূর্বের সিরিয়াল নাম্বার একই রকম হবে না।");
                                 return;
                             }
                         }
                         else if (ECode == 63)
                         {
                             if (EDT.length() != 0) {
                                 Connection.MessageBox(Events.this, EDT);
                                 return;
                             } else if (Code1.length() == 0) {
                                 Connection.MessageBox(Events.this, "সঠিক স্বামী/স্ত্রী এর সিরিয়াল নাম্বার লিখুন(Code)।");
                                 return;
                             } else if (Code1.length() != 2) {
                                 Connection.MessageBox(Events.this, "সঠিক স্বামী/স্ত্রী এর সিরিয়াল নাম্বার ২ সংখ্যা হতে হবে(Code)।");
                                 return;
                             } else if (Sex.equals("1") & Code1.equals("00") & SpNo.length() == 0) {
                                 Connection.MessageBox(Events.this, "সঠিক স্ত্রী এর সিরিয়াল নাম্বার লিখুন(Spouse's No)।");
                                 return;
                             } else if (age < 10) {
                                 Connection.MessageBox(Events.this, "সদস্যের বয়স অবশ্যই ১০ বছরের বেশী হতে হবে।");
                                 return;
                             } else if (MSLNO.equals(Code1)) {
                                 Connection.MessageBox(Events.this, "স্বামী/স্ত্রী এর সিরিয়াল নাম্বার এবং সদস্যের সিরিয়াল নাম্বার একই রকম হবে না।");
                                 return;
                             } else if (Connection.SelectedSpinnerValue(spnInfo1.getSelectedItem().toString(), "-").equalsIgnoreCase(Connection.SelectedSpinnerValue(spnInfo2.getSelectedItem().toString(), "-")) & spnInfo2.isShown()) {
                                 Connection.MessageBox(Events.this, "স্বামী/স্ত্রী এর বর্তমান সিরিয়াল নাম্বার এবং পূর্বের সিরিয়াল নাম্বার একই রকম হবে না।");
                                 return;
                             }

                             //spouse's is not available in the member list
                             if (!Code1.equals("00") & !C.Existence("select vill from tmpMember where  vill||bari||hh='" + Household + "' and MslNo='" + Code1 + "'")) {
                                 Connection.MessageBox(Events.this, "স্বামী/স্ত্রী এর সিরিয়াল নাম্বার " + Code1 + " এই খানার তালিকায় নেই।");
                                 return;
                             } else if (C.Existence("select vill from tmpMember where  vill||bari||hh='" + Household + "' and Mslno='" + MSLNO + "' and (Sp1='" + Code1 + "' or Sp2='" + Code1 + "' or Sp3='" + Code1 + "' or Sp4='" + Code1 + "')")) {
                                 Connection.MessageBox(Events.this, "স্বামী/স্ত্রী এর সিরিয়াল নাম্বার " + Code1 + " পূর্বের নাম্বার এর সমান হবে না।");
                                 return;
                             }
                         }
                         else if (ECode == 64)
                         {
                             if (EDT.length() != 0) {
                                 Connection.MessageBox(Events.this, EDT);
                                 return;
                             } else if (ECode == 64 & spnInfo1.getSelectedItemPosition() == 0) {
                                 Connection.MessageBox(Events.this, "সদস্যের সঠিক সম্পর্ক কি লিখুন।");
                                 return;
                             } else {
                                 CodeList = Global.Left(spnInfo1.getSelectedItem().toString(), 2);
                             }

                             if (CodeList.equals("01") & C.Existence("Select * from tmpMember where hh='" + Household + "' and Rth='01' and (ExType is null or length(ExType)=0)") == true) {
                                 Connection.MessageBox(Events.this, "একই খানায় ২ জন খানা প্রধান থাকতে পারে না।");
                                 return;
                             } else if (CodeList.equals("01") & age < 10) {
                                 Connection.MessageBox(Events.this, "সদস্যের বয়স " + age + ",খানা প্রধান হতে হলে বয়স ১০ বছরের সমান/বেশী হতে হবে।");
                                 return;
                             }

                         }
                     }
                     else if (ECode == 71)
                     {
                         if (EDT.length() != 0)
                         {
                             Connection.MessageBox(Events.this, EDT);
                             return;
                         }
                         else if (ECode == 71 & spnInfo1.getSelectedItemPosition() == 0 & spnInfo1.isShown())
                         {
                             Connection.MessageBox(Events.this, "সদস্যের সঠিক শিক্ষাগত যোগ্যতা কি লিখুন।");
                             return;
                         }
                         else
                         {
                             CodeList = Global.Left(spnInfo1.getSelectedItem().toString(), 2);
                         }

                         //education
                         int eduP = Integer.valueOf(PEdu);
                         int edu = Integer.parseInt(CodeList);
                         if (edu >=0  & edu != 99)
                         {
                             if ((edu >= 1 & edu <= 18) & Math.abs(age - 4) < edu)
                             {
                                 Connection.MessageBox(Events.this, "শিক্ষার কোড " + Math.abs(age - 4) + " এর সমান অথবা কম হতে হবে।");
                                 return;
                             } else if (edu == 11 || edu == 13 || (edu >= 18 & edu <= 29)) {
                                 Connection.MessageBox(Events.this, "শিক্ষার কোড অবশ্যই 00-10,12,14,15,16,17,30,31,32,33,34,35,99 হতে হবে।");
                                 return;
                             }
                             else if (ECode == 71 & edu <= eduP )
                             {
                                 Connection.MessageBox(Events.this, "সদস্যের বর্তমান শিক্ষাগত যোগ্যতা আগের শিক্ষাগত যোগ্যতা " + eduP + "এর সমান বা কম হবেনা");
                                 return;
                             }
                             //age should not have < 4 years
                             //else if(age < 4 & edu > 0)
                             else if (age < 4 & (edu >= 1 & edu <= 18))
                             {
                                 Connection.MessageBox(Events.this, "সদস্যের বয়স শিক্ষার জন্য প্রযোজ্য নয়।");
                                 return;
                             }
                             //education should be consistent with age
                             else if ((edu >= 1 & edu <= 18) & (age - edu) < 4)
                             {
                                 Connection.MessageBox(Events.this, "সদস্যের বয়সের (" + age + " বছর) সাথে শিক্ষার কোড " + edu + " সঠিক নয়।");
                                 return;
                             }
                         }

                     }
                     else if (ECode == 72)
                     {
                         if (EDT.length() != 0) {
                             Connection.MessageBox(Events.this, EDT);
                             return;
                         } else if (ECode == 72 & spnInfo1.getSelectedItemPosition() == 0 & spnInfo1.isShown()) {
                             Connection.MessageBox(Events.this, "সদস্যের সঠিক পেশা কি লিখুন।");
                             return;
                         } else {
                             CodeList = Global.Left(spnInfo1.getSelectedItem().toString(), 2);
                         }

                         int edu = Integer.valueOf(PEdu);
                         int ocp = Integer.valueOf(CodeList);

                         //occupation
                         if (ocp >= 1) {
                             if (age < 12) {
                                 Connection.MessageBox(Events.this, "সদস্যের বয়স ১২ বছরের কম হলে ইভেন্ট ৭২ প্রযোজ্য নয়।");
                                 return;
                             }
                             //check education code should be greater 12 for occupation code 34
                             else if (edu < 12 & ocp == 34) {
                                 Connection.MessageBox(Events.this, "পেশার কোড ৩৪ এর জন্য শিক্ষার কোড অবশ্যই ১২ হতে হবে।");
                                 return;
                             }
                             //check education code should be greater 1 for occupation code 32
                             else if (edu < 1 & ocp == 32) {
                                 Connection.MessageBox(Events.this, "পেশার কোড ৩২ এর জন্য সদস্য অবশ্যই শিক্ষিত হতে হবে।");
                                 return;
                             }
                             //student
                             else if (ocp == 2 & edu == 0 & age > 30) {
                                 Connection.MessageBox(Events.this, "পেশার কোড ০২ এর জন্য শিক্ষার কোড ০০ সঠিক নয়।");
                                 return;
                             }
                             //age>40, ocp should not 02
                             else if (ocp == 2 & age > 40) {
                                 Connection.MessageBox(Events.this, "যাদের বয়স ৪০ বছরের বেশী তাদের পেশার কোড ০২ হতে পারে না।");
                                 return;
                             }
                             //check occupation event=03 for woman
                             else if (ocp == 3 & Sex.equals("1")) {
                                 Connection.MessageBox(Events.this, "পুরুষ লোকের পেশা ০৩ হতে পারে না।");
                                 return;
                             }
                             //Retired person but age < 30
                             else if (ocp == 6 & age < 30) {
                                 Connection.MessageBox(Events.this, "বয়স ৩০ এর কম হলে পেশা অবসরপ্রাপ্ত (চাকুরি করেন না) হতে পারে না।");
                                 return;
                             }
                             //Occupation (31), but education = 00
                             else if (ocp == 31 & edu == 0) {
                                 Connection.MessageBox(Events.this, "পেশা মেধাসম্পন্ন (হাতের কাজ নয়) এর জন্য শিক্ষা ০০ হতে পারে না।");
                                 return;
                             }
                             //Occupation (34), but education < 10
                             else if (ocp == 34 & edu < 10) {
                                 Connection.MessageBox(Events.this, "পেশা পেশাদার-ডাক্তার,কৃষি-কর্মকর্তা,শিক্ষক,ইঞ্জিনিয়ার(মেধাসম্পন্ন-হাতের কাজ নয়) এর জন্য শিক্ষা ১০ এর কম হতে পারে না।");
                                 return;
                             }
                         }
                     }
                 }
             }

             String SQL = "";
             RadioButton rb;
             //String MSL = "";

             Events_DataModel objSave = new Events_DataModel();
             objSave.setVill(txtVill.getText().toString());
             objSave.setBari(txtBari.getText().toString());
             objSave.setHH(txtHH.getText().toString());

             objSave.setMSlNo(txtMSlNo.getText().toString());
             objSave.setPNo(txtPNo.getText().toString());

             String EVT = "";
             if(spnEvType.getCount()==1) {
                 objSave.setEvType(spnEvType.getSelectedItem().toString().split("-")[0]);
                 EVT = spnEvType.getSelectedItem().toString().split("-")[0];
             }
             else {
                 objSave.setEvType((spnEvType.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnEvType.getSelectedItem().toString(), "-")));
                 EVT = (spnEvType.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnEvType.getSelectedItem().toString(), "-"));
             }

             objSave.setEvDate(dtpEvDate.getText().toString().length() > 0 ? Global.DateConvertYMD(dtpEvDate.getText().toString()) : dtpEvDate.getText().toString());

             if(EVT.equals("25"))
             {
                 objSave.setInfo1(spnInfo1.getSelectedItem().toString().split("-")[0]);
                 objSave.setInfo2(txtInfo2.getText().toString());
                 objSave.setInfo3(txtInfo3.getText().toString());
                 objSave.setInfo4(txtInfo4.getText().toString());
             }
             else if(EVT.equals("42"))
             {
                 objSave.setInfo1(spnInfo1.getSelectedItem().toString().split("-")[0]);
                 objSave.setInfo2(spnInfo2.getSelectedItem().toString().split("-")[0]);
                 objSave.setInfo3(spnInfo3.getSelectedItem().toString().split("-")[0]);
                 objSave.setInfo4(txtInfo4.getText().toString());
             }
             else if(EVT.equals("61"))
             {
                 objSave.setInfo1(spnInfo1.getSelectedItem().toString().split("-")[0]);
                 objSave.setInfo2(spnInfo2.getSelectedItem().toString().split("-")[0]);
                 objSave.setInfo3(txtInfo3.getText().toString());
                 objSave.setInfo4(txtInfo4.getText().toString());
             }
             else if(EVT.equals("62"))
             {
                 objSave.setInfo1(spnInfo1.getSelectedItem().toString().split("-")[0]);
                 objSave.setInfo2(spnInfo2.getSelectedItem().toString().split("-")[0]);
                 objSave.setInfo3(txtInfo3.getText().toString());
                 objSave.setInfo4(txtInfo4.getText().toString());
             }
             else if(EVT.equals("63"))
             {
                 objSave.setInfo1(spnInfo1.getSelectedItem().toString().split("-")[0]);
                 objSave.setInfo2(spnInfo2.getSelectedItem().toString().split("-")[0]);
                 objSave.setInfo3(txtInfo3.getText().toString());
                 objSave.setInfo4(txtInfo4.getText().toString());
             }
             else if(EVT.equals("64"))
             {
                 objSave.setInfo1(spnInfo1.getSelectedItem().toString().split("-")[0]);
                 objSave.setInfo2(txtInfo2.getText().toString());
                 objSave.setInfo3(txtInfo3.getText().toString());
                 objSave.setInfo4(txtInfo4.getText().toString());
             }
             else if(EVT.equals("71"))
             {
                 objSave.setInfo1(spnInfo1.getSelectedItem().toString().split("-")[0]);
                 objSave.setInfo2(txtInfo2.getText().toString());
                 objSave.setInfo3(txtInfo3.getText().toString());
                 objSave.setInfo4(txtInfo4.getText().toString());
            }
            else if(EVT.equals("72"))
            {
                 objSave.setInfo1(spnInfo1.getSelectedItem().toString().split("-")[0]);
                 objSave.setInfo2(txtInfo2.getText().toString());
                 objSave.setInfo3(txtInfo3.getText().toString());
                 objSave.setInfo4(txtInfo4.getText().toString());
             }
             else
             {
                 objSave.setInfo1(txtInfo1.getText().toString());
                 objSave.setInfo2(txtInfo2.getText().toString());
                 objSave.setInfo3(txtInfo3.getText().toString());
                 objSave.setInfo4(txtInfo4.getText().toString());
             }

             objSave.setVDate(VISITDATE);
             objSave.setRnd(txtRnd.getText().toString());
             objSave.setEnDt(Global.DateTimeNowYMDHMS());
             objSave.setStartTime(STARTTIME);
             objSave.setEndTime(g.CurrentTime24());
             objSave.setDeviceID(DEVICEID);
             objSave.setEntryUser(ENTRYUSER); //from data entry user list
             objSave.setmodifyDate(Global.DateTimeNowYMDHMS());
             objSave.setLat("");
             objSave.setLon("");

             String SQL1 = "";
             String SQL2 = "";
             String SQL3 = "";

             //Save Events
             SQL1 = objSave.TransactionSQL(this);

             //Save Member
             if(EVTYPE.equals("12")|EVTYPE.equals("20")|EVTYPE.equals("21")|EVTYPE.equals("22")|EVTYPE.equals("23") |EVTYPE.equals("25"))
             {
                 SQL2 = DataSaveMember(txtMSlNo.getText().toString());
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
             //Marital Status
             else if(EVTYPE.equals("31")|EVTYPE.equals("32")|EVTYPE.equals("33")|EVTYPE.equals("34"))
             {
                 SQL3 = "Update tmpMember set MS='"+ EVTYPE +"'";
                 SQL3 += " Where  Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ MSLNO + "'";
             }
             //Pregnancy Information
             else if(EVTYPE.equals("40")|EVTYPE.equals("49"))
             {
                 SQL3 = "Update tmpMember set PStat='"+ EVTYPE +"',LmpDt=''";
                 SQL3 += " Where  Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ MSLNO + "'";
             }
             else if(EVTYPE.equals("41"))
             {
                 SQL3 = "Update tmpMember set PStat='"+ EVTYPE +"',LmpDt='"+ Global.DateConvertYMD(dtpEvDate.getText().toString()) +"'";
                 SQL3 += " Where  Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ MSLNO + "'";

             }else if(EVTYPE.equals("42"))
             {
                 SQL3 = "Update tmpMember set PStat='',LmpDt='' ";
                 SQL3 += " Where  Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ MSLNO + "'";
             }
             //Migration out
             else if(EVTYPE.equals("51")|EVTYPE.equals("52")|EVTYPE.equals("53")|EVTYPE.equals("55")|EVTYPE.equals("56"))
             {
                 SQL3 = "Update tmpMember set ExType='"+ EVTYPE +"',ExDate='"+ Global.DateConvertYMD(dtpEvDate.getText().toString()) +"'";
                 SQL3 += " Where  Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ MSLNO + "'";
             }

             //Possible migration-out
             else if(EVTYPE.equals("54"))
             {
                 SQL3 = "Update tmpMember set PosMig='"+ EVTYPE +"',PosMigDate='"+ EvDate +"'";
                 SQL3 += " Where  Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ MSLNO + "'";
             }
             //Mother's serial no update
             else if(EVTYPE.equals("61"))
             {
                 SQL3 = "Update tmpMember set MoNo='"+ Connection.SelectedSpinnerValue(spnInfo1.getSelectedItem().toString(), "-") +"'";
                 SQL3 += " Where  Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ MSLNO + "'";

             }
             else if(EVTYPE.equals("62"))
             {
                 SQL3 = "Update tmpMember set FaNo='"+ Connection.SelectedSpinnerValue(spnInfo1.getSelectedItem().toString(), "-") +"'";
                 SQL3 += " Where  Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ MSLNO + "'";
             }
             else if(EVTYPE.equals("63"))
             {
                 SQL3 = "Update tmpMember set Sp1='"+ Connection.SelectedSpinnerValue(spnInfo1.getSelectedItem().toString(), "-") +"'";
                 SQL3 += " Where  Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ MSLNO + "'";
             }

             //Relation to head update
             else if(EVTYPE.equals("64"))
             {
                 SQL3 = "Update tmpMember set Rth='"+ Connection.SelectedSpinnerValue(spnInfo1.getSelectedItem().toString(), "-") +"'";
                 SQL3 += " Where  Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ MSLNO + "'";
             }
             //Education update
             else if(EVTYPE.equals("71"))
             {
                 SQL3 = "Update tmpMember set Edu='"+ Connection.SelectedSpinnerValue(spnInfo1.getSelectedItem().toString(), "-") +"'";
                 SQL3 += " Where  Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ MSLNO + "'";
             }
             //Occupation update
             else if(EVTYPE.equals("72"))
             {
                 SQL3 = "Update tmpMember set Ocp='"+ Connection.SelectedSpinnerValue(spnInfo1.getSelectedItem().toString(), "-") +"'";
                 SQL3 += " Where  Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ MSLNO + "'";
             }
             else
             {
                 formMember.setVisibility(View.GONE);
             }

             //Transaction Process
             String status = C.TransactionDataInsert(SQL1,SQL2,SQL3,"");

             if(status.length()==0) {
                 Intent returnIntent = new Intent();
                 returnIntent.putExtra("res", "");
                 setResult(Activity.RESULT_OK, returnIntent);

                 Connection.MessageBox(Events.this, "Saved Successfully");
                 finish();
             }
             else{
                 Connection.MessageBox(Events.this, status);
                 return;
             }

         }
         catch(Exception  e)
         {
             Connection.MessageBox(Events.this, e.getMessage());
             return;
         }
     }

     private void MemberInitialize()
     {
         spnEvType = (Spinner) findViewById(R.id.spnEvType);
         EvType = spnEvType.getSelectedItem().toString().split("-")[0];

         chkNeedReview = (CheckBox)findViewById(R.id.chkNeedReview);
         secName=(LinearLayout)findViewById(R.id.secName);
         VlblName=(TextView) findViewById(R.id.VlblName);
         txtName=(EditText) findViewById(R.id.txtName);
         secRth=(LinearLayout)findViewById(R.id.secRth);
         lineRth=(View)findViewById(R.id.lineRth);
         VlblRth=(TextView) findViewById(R.id.VlblRth);
         spnRth=(Spinner) findViewById(R.id.spnRth);

         if (MSLNO.equals("01")){
             spnRth.setAdapter(C.getArrayAdapter("Select '' union Select distinct '01-নিজেই খানা প্রধান'"));// Name from RTH where Code in('01')"));
         }
         else
         {
             List<String> listRth = new ArrayList<String>();

             listRth.add("");
             listRth.add("00-খানা প্রধানের সাথে স¤পর্ক নেই");
             listRth.add("01-নিজেই খানা প্রধান");
             listRth.add("02-খানা প্রধানের স্বামী/স্ত্রী");
             listRth.add("03-খানা প্রধানের ছেলে/মেয়ে");
             listRth.add("04-খানা প্রধানের মা/বাবা");
             listRth.add("05-খানা প্রধানের ভাই/বোন");
             listRth.add("06-খানা প্রধানের চাচা/ফুফু/মামা/খালা");
             listRth.add("07-খানা প্রধানের দাদা/দাদি/নানা/নানি");
             listRth.add("08-খানা প্রধানের নাতি/নাতনি");
             listRth.add("09-খানা প্রধানের শ্যালক/শ্যালিকা/দেবর/ননদ");
             listRth.add("10-খানা প্রধানের শ্বশুর/শাশুড়ী");
             listRth.add("11-খানা প্রধানের সৎ মা/বাবা");
             listRth.add("12-খানা প্রধানের সৎ ছেলে/মেয়ে");
             listRth.add("13-খানা প্রধানের পালিত ছেলে/মেয়ে");
             listRth.add("14-খানা প্রধানের সৎ ভাই/সৎবোন");
             listRth.add("15-খানা প্রধানের ছেলের বউ/মেয়ের জামাই");
             listRth.add("16-খানা প্রধানের ভাতিজা/ভাতিজী/ভাগ্নে/ভাগ্নী");
             listRth.add("17-খানা প্রধানের বোন জামাই/ভাবী");
             listRth.add("18-গৃহকর্মী");
             listRth.add("77-অন্যান্য সম্পর্ক যা উপরের তালিকায় অন্তর্ভূক্ত নয়");
             ArrayAdapter<String> adptrRth= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listRth);
             spnRth.setAdapter(adptrRth);
         }
         secSex=(LinearLayout)findViewById(R.id.secSex);
         lineSex=(View)findViewById(R.id.lineSex);
         VlblSex = (TextView) findViewById(R.id.VlblSex);
         rdogrpSex = (RadioGroup) findViewById(R.id.rdogrpSex);

         rdoSex1 = (RadioButton) findViewById(R.id.rdoSex1);
         rdoSex2 = (RadioButton) findViewById(R.id.rdoSex2);

         secBDate=(LinearLayout)findViewById(R.id.secBDate);
         lineBDate=(View)findViewById(R.id.lineBDate);
         VlblBDate=(TextView) findViewById(R.id.VlblBDate);
         dtpBDate=(EditText) findViewById(R.id.dtpBDate);

         String EnDate  = C.ReturnSingleValue("select EnDate from tmpMember Where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH + "' and Mslno='"+ MSLNO + "'");
         if (EvType.equals("25")){
             dtpBDate.setText(ENDATE);
         }

         secAgeY=(LinearLayout)findViewById(R.id.secAgeY);
         lineAgeY=(View)findViewById(R.id.lineAgeY);
         VlblAgeY=(TextView) findViewById(R.id.VlblAgeY);
         txtAgeY=(EditText) findViewById(R.id.txtAgeY);

         secMoNo = (LinearLayout) findViewById(R.id.secMoNo);
         lineMoNo = (View) findViewById(R.id.lineMoNo);
         VlblMoNo = (TextView) findViewById(R.id.VlblMoNo);
         spnMoNo = (Spinner) findViewById(R.id.spnMoNo);
         spnMoNo.setAdapter(C.getArrayAdapter("Select '' union Select MSlNo||'-'||Name from tmpMember Where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' union Select '00-এই খানার সদস্য নয়'"));
//         spnMoNo.setAdapter(C.getArrayAdapter("Select '' union Select MSlNo||'-'||Name from tmpMember Where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and Sex='2' and MS<>'30' union Select '00-এই খানার সদস্য নয়'"));

//         if (EvType.equals("25"))
//         {
//             String MoNo = C.ReturnSingleValue("Select Info1 from tmpEvents where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and MSlNo='" + MSLNO + "'");
//             spnMoNo.setAdapter(C.getArrayAdapter("Select MSlNo||'-'||Name from tmpMember where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and MSlNo='" + MoNo.toString() + "'"));
//         }
//         else {
//             spnMoNo.setAdapter(C.getArrayAdapter("Select '' union Select MSlNo||'-'||Name from tmpMember Where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and Sex='2' and MS<>'30' union Select '00-এই খানার সদস্য নয়'"));
//         }

         secFaNo = (LinearLayout) findViewById(R.id.secFaNo);
         lineFaNo = (View) findViewById(R.id.lineFaNo);
         VlblFaNo = (TextView) findViewById(R.id.VlblFaNo);
         spnFaNo = (Spinner) findViewById(R.id.spnFaNo);
         spnFaNo.setAdapter(C.getArrayAdapter("Select '' union Select MSlNo||'-'||Name from tmpMember Where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH + "' union Select '00-এই খানার সদস্য নয়'"));
//         spnFaNo.setAdapter(C.getArrayAdapter("Select '' union Select MSlNo||'-'||Name from tmpMember Where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH + "' and Sex='1' and MS<>'30' union Select '00-এই খানার সদস্য নয়'"));

//         secMoNo=(LinearLayout)findViewById(R.id.secMoNo);
//         lineMoNo=(View)findViewById(R.id.lineMoNo);
//         VlblMoNo=(TextView) findViewById(R.id.VlblMoNo);
//         txtMoNo=(EditText) findViewById(R.id.txtMoNo);

//         secFaNo=(LinearLayout)findViewById(R.id.secFaNo);
//         lineFaNo=(View)findViewById(R.id.lineFaNo);
//         VlblFaNo=(TextView) findViewById(R.id.VlblFaNo);
//         txtFaNo=(EditText) findViewById(R.id.txtFaNo);

         secEdu=(LinearLayout)findViewById(R.id.secEdu);
         lineEdu=(View)findViewById(R.id.lineEdu);
         VlblEdu=(TextView) findViewById(R.id.VlblEdu);
         spnEdu=(Spinner) findViewById(R.id.spnEdu);
         List<String> listEdu = new ArrayList<String>();

         listEdu.add("");
         listEdu.add("00-কোন শিক্ষাগত যোগ্যতা নেই");
         listEdu.add("01-প্রথম শ্রেণি পাশ");
         listEdu.add("02-দ্বিতীয় শ্রেণি পাশ");
         listEdu.add("03-তৃতীয় শ্রেণি পাশ");
         listEdu.add("04-চতুর্থ শ্রেণি পাশ");
         listEdu.add("05-পঞ্চম শ্রেণি পাশ");
         listEdu.add("06-ষষ্ঠ শ্রেণি পাশ");
         listEdu.add("07-সপ্তম শ্রেণি পাশ");
         listEdu.add("08-অষ্টম শ্রেণি পাশ");
         listEdu.add("09-নবম শ্রেণি পাশ");
         listEdu.add("10-এসএসসি /দাখিল পাশ");
         listEdu.add("12-এইচএসসি /আলিম পাশ");
         listEdu.add("14-বিএ/বিকম/বিএসসি /ফাজিল/ ডিপ্লোমা পাশ");
         listEdu.add("16-বিএ/বিকম/বিএসসি (সম্মান) অথবা বিএসসি ইঞ্জিনিয়ারিং পাশ");
         listEdu.add("17-এমএ/এমএসসি/এমকম/এমএসএস/এমবিবিএস /কামিল পাশ");
         listEdu.add("30-ব্রাক শিশু শ্রেণী পাশ");
         listEdu.add("31-ব্র্যাক প্রথম শ্রেণি পাশ");
         listEdu.add("32-ব্র্যাক ২য় শ্রেণি পাশ");
         listEdu.add("33-ব্র্যাক ৩য় শ্রেণি পাশ");
         listEdu.add("34-ব্র্যাক ৪র্থ  শ্রেণি পাশ");
         listEdu.add("35-ব্র্যাক ৫ম শ্রেণি পাশ");
         listEdu.add("99-জানিনা");
         ArrayAdapter<String> adptrEdu= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listEdu);
         spnEdu.setAdapter(adptrEdu);

         secMS=(LinearLayout)findViewById(R.id.secMS);
         lineMS=(View)findViewById(R.id.lineMS);
         VlblMS=(TextView) findViewById(R.id.VlblMS);
         spnMS=(Spinner) findViewById(R.id.spnMS);
         List<String> listMS = new ArrayList<String>();

         listMS.add("");
         listMS.add("30-কখনও বিয়ে হয়নি");
         listMS.add("31-বর্তমানে বিবাহিতা/বিবাহিত");
         listMS.add("32-তালাকপ্রাপ্তা/তালাকপ্রাপ্ত (আইনগত ভাবে তালাকপ্রাপ্তা)");
         listMS.add("33-বিধবা/বিপত্নীক (স্বামী/স্ত্রী মৃত-বর্তমানে কোন বৈবাহিক সম্পর্ক নাই)");
         listMS.add("34-বিচ্ছিন্না/বিচ্ছন্ন");
         ArrayAdapter<String> adptrMS= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listMS);
         spnMS.setAdapter(adptrMS);

         spnMS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 if (position != 2)
                 {
                     seclblSpsl.setVisibility(View.GONE);
                     spnSp1.setSelection(0);
                     spnSp1.setVisibility(View.GONE);
                     lineSp1.setVisibility(View.GONE);
                     secSp1.setVisibility(View.GONE);
                     lineSp1.setVisibility(View.GONE);

                     spnSp2.setSelection(0);
                     spnSp2.setVisibility(View.GONE);
                     lineSp2.setVisibility(View.GONE);
                     secSp2.setVisibility(View.GONE);
                     lineSp2.setVisibility(View.GONE);

                     spnSp3.setSelection(0);
                     spnSp3.setVisibility(View.GONE);
                     lineSp3.setVisibility(View.GONE);
                     secSp3.setVisibility(View.GONE);
                     lineSp3.setVisibility(View.GONE);

                     spnSp4.setSelection(0);
                     spnSp4.setVisibility(View.GONE);
                     lineSp4.setVisibility(View.GONE);
                     secSp4.setVisibility(View.GONE);
                     lineSp4.setVisibility(View.GONE);

                 } else {

                     seclblSpsl.setVisibility(View.VISIBLE);
                     spnSp1.setVisibility(View.VISIBLE);
                     lineSp1.setVisibility(View.VISIBLE);
                     secSp1.setVisibility(View.VISIBLE);
                     lineSp1.setVisibility(View.VISIBLE);

                     spnSp2.setVisibility(View.GONE);
                     lineSp2.setVisibility(View.GONE);
                     secSp2.setVisibility(View.GONE);
                     lineSp2.setVisibility(View.GONE);

                     spnSp3.setVisibility(View.GONE);
                     lineSp3.setVisibility(View.GONE);
                     secSp3.setVisibility(View.GONE);
                     lineSp3.setVisibility(View.GONE);

                     spnSp4.setVisibility(View.GONE);
                     lineSp4.setVisibility(View.GONE);
                     secSp4.setVisibility(View.GONE);
                     lineSp4.setVisibility(View.GONE);
                 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

         secOcp=(LinearLayout)findViewById(R.id.secOcp);
         lineOcp=(View)findViewById(R.id.lineOcp);
         VlblOcp=(TextView) findViewById(R.id.VlblOcp);
         spnOcp=(Spinner) findViewById(R.id.spnOcp);
         List<String> listOcp = new ArrayList<String>();

         listOcp.add("");
         listOcp.add("01-বেকার");
         listOcp.add("02-ছাত্র /ছাত্রী (চাকুরী করে না)");
         listOcp.add("03-গৃহিনী/গৃহস্থালীর কাজকর্ম করে (চাকুরী করে না)");
         listOcp.add("04-ভিক্ষুক (চাকুরী করে না)");
         listOcp.add("05-প্রতিবন্ধী (চাকুরী করে না)");
         listOcp.add("06-অবসরপ্রাপ্ত (চাকুরী করে না)");
         listOcp.add("11-অদক্ষ শ্রমিক: গৃহকর্মী, আয়া, গার্মেন্টস শ্রমিক (পরিচ্ছন্নতা/খাবার বিতরন/নিরাপত্তা/সাপ্লাই কাজ করে),  উৎপাদন/নির্মাণ শ্রমিক (যোগালী), বাস/টেম্পুর সহকারী, পাহারাদার, পরিচ্ছন্নতাকর্মী, পিওন, রিক্সা/ভ্যান/ঠেলাগাড়ি চালক, দিনমজুর, কৃষক, জেলে");
         listOcp.add("17-অন্যান্য অদক্ষ শ্রমিক");
         listOcp.add("21-দক্ষ শ্রমিক: গার্মেন্টস কর্মী (ড্রেস তৈরী ও প্রোডাকশনের কাজে নিয়োজিত), বাবুর্চি, উৎপাদন/নির্মাণ শ্রমিক, গাড়ি চালক");
         listOcp.add("22-অন্যান্য দক্ষ শ্রমিক: দর্জি, নাপিত, তাতীঁ, কাঠের মিস্ত্রি, গাড়ির মিস্ত্রি,পাম্পের মিস্ত্রি, রং মিস্ত্রি,কারিগর, মুচি");
         listOcp.add("27-অন্যান্য দক্ষ শ্রমিক: ২২  কোডে বর্ণিত দক্ষ শ্রমিক ছাড়া অন্য কেউ");
         listOcp.add("31-মেধা সম্পন্ন: প্রশাসনিক/করণিক পদে সরকারী, বেসরকারী, গার্মেন্টস অথবা ব্যক্তিগতপ্রতিষ্ঠানে চাকুরী, সরকারী/বেসরকারী খাতের অতিরিক্ত জনবল, বিক্রয় প্রতিনিধি");
         listOcp.add("32-মাঠপর্যায়ের স্বাস্থ্যকর্মী: গ্রাম্য ডাক্তার, হোমিওপ্যাথিক ডাক্তার, কবিরাজ, অদক্ষ দাই, ঔষধ বিক্রেতা, সরকারী/বেসরকারী গ্রাম্য স্বাস্থ্যকর্মী (মেধাসম্পন্ন)");
         listOcp.add("33-ব্যবসায়ী, নিজস্ব ব্যবসা (মেধাসম্পন্ন)");
         listOcp.add("34-পেশাজীবি:ডাক্তার, কৃষিবিদ, শিক্ষক, প্রকৌশলী (মেধাসম্পন্ন)");
         listOcp.add("37-অন্যান্য মেধা সম্পন্ন");
         listOcp.add("77-অন্যান্য");
         listOcp.add("99-জানিনা");
         ArrayAdapter<String> adptrOcp= new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item, listOcp);
         spnOcp.setAdapter(adptrOcp);

         seclblSpsl=(LinearLayout)findViewById(R.id.seclblSpsl);
         linelblSpsl=(View)findViewById(R.id.linelblSpsl);

         secSp1=(LinearLayout)findViewById(R.id.secSp1);
         lineSp1=(View)findViewById(R.id.lineSp1);
         VlblSp1=(TextView) findViewById(R.id.VlblSp1);
         spnSp1 = (Spinner) findViewById(R.id.spnSp1);
         spnSp1.setAdapter(C.getArrayAdapter("Select '' union Select MSlNo||'-'||Name from tmpMember Where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' union Select '00-এই খানার সদস্য নয়'"));

         secSp2=(LinearLayout)findViewById(R.id.secSp2);
         lineSp2=(View)findViewById(R.id.lineSp2);
         VlblSp2=(TextView) findViewById(R.id.VlblSp2);
         spnSp2=(Spinner) findViewById(R.id.spnSp2);
         spnSp2.setAdapter(C.getArrayAdapter("Select '' union Select MSlNo||'-'||Name from tmpMember Where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' union Select '00-এই খানার সদস্য নয়'"));


         secSp3=(LinearLayout)findViewById(R.id.secSp3);
         lineSp3=(View)findViewById(R.id.lineSp3);
         VlblSp3=(TextView) findViewById(R.id.VlblSp3);
         spnSp3=(Spinner) findViewById(R.id.spnSp3);
         spnSp3.setAdapter(C.getArrayAdapter("Select '' union Select MSlNo||'-'||Name from tmpMember Where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' union Select '00-এই খানার সদস্য নয়'"));

         secSp4=(LinearLayout)findViewById(R.id.secSp4);
         lineSp4=(View)findViewById(R.id.lineSp4);
         VlblSp4=(TextView) findViewById(R.id.VlblSp4);
         spnSp4=(Spinner) findViewById(R.id.spnSp4);
         spnSp4.setAdapter(C.getArrayAdapter("Select '' union Select MSlNo||'-'||Name from tmpMember Where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' union Select '00-এই খানার সদস্য নয়'"));

         secEnType=(LinearLayout)findViewById(R.id.secEnType);
         lineEnType=(View)findViewById(R.id.lineEnType);
         VlblEnType=(TextView) findViewById(R.id.VlblEnType);
         txtEnType=(EditText) findViewById(R.id.txtEnType);

         secEnDate=(LinearLayout)findViewById(R.id.secEnDate);
         lineEnDate=(View)findViewById(R.id.lineEnDate);
         VlblEnDate=(TextView) findViewById(R.id.VlblEnDate);
         dtpEnDate=(EditText) findViewById(R.id.dtpEnDate);

         secExType=(LinearLayout)findViewById(R.id.secExType);
         lineExType=(View)findViewById(R.id.lineExType);
         VlblExType=(TextView) findViewById(R.id.VlblExType);
         txtExType=(EditText) findViewById(R.id.txtExType);

         secExDate=(LinearLayout)findViewById(R.id.secExDate);
         lineExDate=(View)findViewById(R.id.lineExDate);
         VlblExDate=(TextView) findViewById(R.id.VlblExDate);
         dtpExDate=(EditText) findViewById(R.id.dtpExDate);

         dtpBDate.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 final int DRAWABLE_RIGHT  = 2;
                 if(event.getAction() == MotionEvent.ACTION_UP) {
                     if(event.getRawX() >= (dtpBDate.getRight() - dtpBDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                         VariableID = "btnBDate"; showDialog(DATE_DIALOG);
                         return true;
                     }
                 }
                 return false;
             }
         });
         dtpEnDate.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 final int DRAWABLE_RIGHT  = 2;
                 if(event.getAction() == MotionEvent.ACTION_UP) {
                     if(event.getRawX() >= (dtpEnDate.getRight() - dtpEnDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                         VariableID = "btnEnDate"; showDialog(DATE_DIALOG);
                         return true;
                     }
                 }
                 return false;
             }
         });
         dtpExDate.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 final int DRAWABLE_RIGHT  = 2;
                 if(event.getAction() == MotionEvent.ACTION_UP) {
                     if(event.getRawX() >= (dtpExDate.getRight() - dtpExDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                         VariableID = "btnExDate"; showDialog(DATE_DIALOG);
                         return true;
                     }
                 }
                 return false;
             }
         });


         spnSp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 String d = "";
                 if (position > 0)
                 {
                     String[] a = spnSp1.getSelectedItem().toString().split("-");
                     d = a[0];
                 }

                 if(d.equals("00") | d.length()==0) {
                     spnSp2.setVisibility(View.GONE);
                     lineSp2.setVisibility(View.GONE);
                     secSp2.setVisibility(View.GONE);
                     lineSp2.setVisibility(View.GONE);
                     spnSp2.setSelection(0);

                 }else{
                     spnSp2.setVisibility(View.VISIBLE);
                     lineSp2.setVisibility(View.VISIBLE);
                     secSp2.setVisibility(View.VISIBLE);
                     lineSp2.setVisibility(View.VISIBLE);
                 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });
         spnSp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 String d = "";
                 if (position > 0)
                 {
                     String[] a = spnSp2.getSelectedItem().toString().split("-");
                     d = a[0];
                 }

                 if(d.equals("00") | d.length()==0) {
                     spnSp3.setVisibility(View.GONE);
                     lineSp3.setVisibility(View.GONE);
                     secSp3.setVisibility(View.GONE);
                     lineSp3.setVisibility(View.GONE);
                     spnSp3.setSelection(0);
                 }else{
                     spnSp3.setVisibility(View.VISIBLE);
                     lineSp3.setVisibility(View.VISIBLE);
                     secSp3.setVisibility(View.VISIBLE);
                     lineSp3.setVisibility(View.VISIBLE);
                 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });
         spnSp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 String d = "";
                 if (position > 0)
                 {
                     String[] a = spnSp3.getSelectedItem().toString().split("-");
                     d = a[0];
                 }

                 if(d.equals("00") | d.length()==0) {
                     spnSp4.setVisibility(View.GONE);
                     lineSp4.setVisibility(View.GONE);
                     secSp4.setVisibility(View.GONE);
                     lineSp4.setVisibility(View.GONE);
                     spnSp4.setSelection(0);
                 }else{
                     spnSp4.setVisibility(View.VISIBLE);
                     lineSp4.setVisibility(View.VISIBLE);
                     secSp4.setVisibility(View.VISIBLE);
                     lineSp4.setVisibility(View.VISIBLE);
                 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

         //Hide all skip variables
         txtVill.setEnabled(false);
         txtBari.setEnabled(false);
         txtHH.setEnabled(false);
         txtPNo.setEnabled(false);
         //secSp1.setVisibility(View.GONE);
         secSp2.setVisibility(View.GONE);
         secSp3.setVisibility(View.GONE);
         secSp4.setVisibility(View.GONE);

         /*Button cmdSave = (Button) findViewById(R.id.cmdSave1);
         cmdSave.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 DataSave();
             }});*/

         DataSearchMember(VILL,BARI,HH,MSLNO,"tmpMember");

     }
     private String DataSaveMember(String MemberSl)
     {
         String SQL = "";
         try
         {
             String DV="";

             RadioButton rb;

             Member_DataModel objSave = new Member_DataModel();
             objSave.setVill(txtVill.getText().toString());
             objSave.setBari(txtBari.getText().toString());
             objSave.setHH(txtHH.getText().toString());
             objSave.setMSlNo(MemberSl);
             objSave.setPNo(txtPNo.getText().toString());
             objSave.setName(txtName.getText().toString());
             objSave.setRth((spnRth.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnRth.getSelectedItem().toString(), "-")));
             String[] d_rdogrpSex = new String[] {"1","2"};
             objSave.setSex("");

             for (int i = 0; i < rdogrpSex.getChildCount(); i++)
             {
                 rb = (RadioButton)rdogrpSex.getChildAt(i);
                 if (rb.isChecked()) objSave.setSex(d_rdogrpSex[i]);
             }

             objSave.setBDate(dtpBDate.getText().toString().length() > 0 ? Global.DateConvertYMD(dtpBDate.getText().toString()) : dtpBDate.getText().toString());
             objSave.setAgeY(txtAgeY.getText().toString());
             objSave.setMoNo((spnMoNo.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnMoNo.getSelectedItem().toString(), "-")));
             objSave.setFaNo((spnFaNo.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnFaNo.getSelectedItem().toString(), "-")));
             objSave.setEdu((spnEdu.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnEdu.getSelectedItem().toString(), "-")));
             objSave.setMS((spnMS.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnMS.getSelectedItem().toString(), "-")));
             objSave.setOcp((spnOcp.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnOcp.getSelectedItem().toString(), "-")));
             objSave.setSp1((spnSp1.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnSp1.getSelectedItem().toString(), "-")));
             objSave.setSp2((spnSp2.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnSp2.getSelectedItem().toString(), "-")));
             objSave.setSp3((spnSp3.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnSp3.getSelectedItem().toString(), "-")));
             objSave.setSp4((spnSp4.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnSp4.getSelectedItem().toString(), "-")));

             if(EVTYPE.equals("20")|EVTYPE.equals("21")|EVTYPE.equals("22")|EVTYPE.equals("23")|EVTYPE.equals("25")) {
                 objSave.setEnType(spnEvType.getSelectedItem().toString().split("-")[0]);
                 objSave.setEnDate(dtpEvDate.getText().toString().length() > 0 ? Global.DateConvertYMD(dtpEvDate.getText().toString()) : dtpEvDate.getText().toString());
                 objSave.setExType("");
                 objSave.setExDate("");
             }else if(EVTYPE.equals("12")) {
                 objSave.setEnType(txtEnType.getText().toString());
                 objSave.setEnDate(Global.DateConvertYMD(dtpEnDate.getText().toString()));
                 objSave.setExType("");
                 objSave.setExDate("");
             }
             //objSave.setExType("");
             //objSave.setExDate("");
             //objSave.setEnDt(Global.DateTimeNowYMDHMS());

             objSave.setStartTime(STARTTIME);
             objSave.setEndTime(g.CurrentTime24());
             objSave.setDeviceID(DEVICEID);
             objSave.setEntryUser(ENTRYUSER); //from data entry user list

             objSave.setNeedReview(chkNeedReview.isChecked()?"1":"2");
             //objSave.setLat(Double.toString(currentLatitude));
             //objSave.setLon(Double.toString(currentLongitude));

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
             /*txtVill.setText(item.getVill());
             txtBari.setText(item.getBari());
             txtHH.setText(item.getHH());
             txtMSlNo.setText(item.getMSlNo());*/
                 // txtPNo.setText(item.getPNo());
                 txtName.setText(item.getName());
                 spnRth.setSelection(Global.SpinnerItemPositionAnyLength(spnRth, item.getRth()));
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
                 spnMoNo.setSelection(Global.SpinnerItemPositionAnyLength(spnMoNo, item.getMoNo()));
                 spnFaNo.setSelection(Global.SpinnerItemPositionAnyLength(spnFaNo, item.getFaNo()));
                 spnEdu.setSelection(Global.SpinnerItemPositionAnyLength(spnEdu, item.getEdu()));
                 spnMS.setSelection(Global.SpinnerItemPositionAnyLength(spnMS, item.getMS()));
                 spnOcp.setSelection(Global.SpinnerItemPositionAnyLength(spnOcp, item.getOcp()));
                 spnSp1.setSelection(Global.SpinnerItemPositionAnyLength(spnSp1, item.getSp1()));
                 spnSp2.setSelection(Global.SpinnerItemPositionAnyLength(spnSp2, item.getSp2()));
                 spnSp3.setSelection(Global.SpinnerItemPositionAnyLength(spnSp3, item.getSp3()));
                 spnSp4.setSelection(Global.SpinnerItemPositionAnyLength(spnSp4, item.getSp4()));
                 txtEnType.setText(item.getEnType());
                 dtpEnDate.setText(item.getEnDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getEnDate()));
                 txtExType.setText(item.getExType());
                 dtpExDate.setText(item.getExDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getExDate()));
                 if(item.getNeedReview().equals("1")) chkNeedReview.setChecked(true); else chkNeedReview.setChecked(false);
             }
         }
         catch(Exception  e)
         {
             Connection.MessageBox(Events.this, e.getMessage());
             return;
         }
     }

     private void DataSearch(String Vill, String Bari, String HH, String MSlNo, String EvType, String EvDate, String Rnd)
     {
         try
         {
             RadioButton rb;
             Events_DataModel d = new Events_DataModel();
             String SQL = "Select * from tmpEvents  Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"' and MSlNo='"+ MSlNo +"' and EvType='"+ EvType +"' and Rnd='"+ Rnd +"'";
             List<Events_DataModel> data = d.SelectAll(this, SQL);
             for(Events_DataModel item : data){
                 txtVill.setText(item.getVill());
                 txtBari.setText(item.getBari());
                 txtHH.setText(item.getHH());
                 txtMSlNo.setText(item.getMSlNo());
                 txtPNo.setText(item.getPNo());
                 spnEvType.setSelection(Global.SpinnerItemPositionAnyLength(spnEvType, item.getEvType()));
                 dtpEvDate.setText(item.getEvDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getEvDate()));
                 dtpEvDate.setEnabled(false);
                 txtInfo1.setText(item.getInfo1());
                 txtInfo1.setEnabled(false);
                 txtInfo2.setText(item.getInfo2());
                 txtInfo2.setEnabled(false);
                 spnInfo2.setSelection(Global.SpinnerItemPositionAnyLength(spnInfo2, item.getInfo2()));
                 txtInfo3.setText(item.getInfo3());
                 txtInfo3.setEnabled(false);
                 txtInfo4.setText(item.getInfo4());
                 txtInfo4.setEnabled(false);
                 dtpVDate.setText(item.getVDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getVDate()));
                 txtRnd.setText(item.getRnd());
             }
         }
         catch(Exception  e)
         {
             Connection.MessageBox(Events.this, e.getMessage());
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
         String M = C.ReturnSingleValue("Select cast(ifnull(max(MSlNo),0)+1 as varchar(2))MemNo from Member where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"'");
         M = Global.Right("0"+M,2);
         return M;
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
             final Dialog dialog = new Dialog(Events.this);

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
                         Connection.MessageBox(Events.this, " সদস্য সিলেক্ট করা হয়নি, প্রথমে তালিকা থেকে সদস্য সিলেক্ট করুন।");
                         return;
                     }

                     String[] migdata = Connection.split(lblMigParameter.getText().toString(),'^');

                     txtPNo.setText(migdata[4]);
                     dtpEvDate.setText(migdata[5]);
                     secInfo1.setVisibility(View.VISIBLE);
                     txtInfo1.setText(migdata[0]+migdata[1]+migdata[2]);
                     g.setPNo(migdata[4]);

                     formMember.setVisibility(View.VISIBLE);
                     DataSearchMigMember(migdata[0],migdata[1],migdata[2],migdata[3],"migMember",spnEvType.getSelectedItem().toString().split("-")[0],migdata[5]);


                     dialog.dismiss();
                 }
             });

             dialog.show();
         }
         catch(Exception  e)
         {
             Connection.MessageBox(Events.this, e.getMessage());
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
         dataAdapter = new SimpleAdapter(Events.this, evmylist, R.layout.migrationrow,
                 new String[] {"sno","pno","name","exdate"},
                 new int[] {R.id.m_sno,R.id.m_pno,R.id.m_name,R.id.m_exdate});
         //evlist.setAdapter(eList);
         evlist.setAdapter(new Events.MigrationListAdapter(this, dataAdapter, d));
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

     public void DataSearchMigMemberNew(String EnType, String EnDate, String PNo)
     {
         try
         {
             RadioButton rb;
             Member_DataModel d = new Member_DataModel();
             txtInfo1.setText(C.ReturnSingleValue("Select Vill||Bari||HH from migMember  Where PNo='"+ PNO +"' and ExType='"+ EnType +"'"));
             txtInfo1.setEnabled(false);
             String SQL = "Select * from migMember  Where PNo='"+ PNO +"' and ExType='"+ EnType +"'";
             List<Member_DataModel> data1 = d.SelectAll_MigMember(this, SQL);
             for(Member_DataModel item : data1){

                 txtName.setText(item.getName());
                 spnRth.setSelection(0);
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
                 dtpBDate.setEnabled(false);

                 int ageday = Global.DateDifferenceDays(dtpEvDate.getText().toString(),dtpBDate.getText().toString());
                 Double  D=ageday/365.25;
                 int Age = Integer.valueOf(D.intValue());

                 String SL=Age+"";
                 int length=SL.length();
                 if(length==1)
                 {
                     SL="0"+SL;
                 }
                 txtAgeY.setText(SL);

//                 txtAgeY.setText(item.getAgeY());
                 txtAgeY.setEnabled(false);
                 spnMoNo.setSelection(0);
                 spnFaNo.setSelection(0);
                 spnEdu.setSelection(Global.SpinnerItemPositionAnyLength(spnEdu, item.getEdu()));
                 spnEdu.setEnabled(false);
                 spnMS.setSelection(Global.SpinnerItemPositionAnyLength(spnMS, item.getMS()));
                 spnMS.setEnabled(false);
                 spnOcp.setSelection(Global.SpinnerItemPositionAnyLength(spnOcp, item.getOcp()));
                 spnOcp.setEnabled(false);
                 spnSp1.setSelection(0);
                 spnSp2.setSelection(0);
                 spnSp3.setSelection(0);
                 spnSp4.setSelection(0);
                 txtEnType.setText(EnType);
                 dtpEnDate.setText(EnDate);
                 txtExType.setText("");
                 dtpExDate.setText("");
                 if(item.getNeedReview().equals("1")) chkNeedReview.setChecked(true); else chkNeedReview.setChecked(false);
             }
         }
         catch(Exception  e)
         {
             Connection.MessageBox(Events.this, e.getMessage());
             return;
         }
     }

     public void DataSearchMigMember(String Vill, String Bari, String HH, String MSlNo, String TableName, String EnType, String EnDate)
     {
         try
         {
             RadioButton rb;
             Member_DataModel d = new Member_DataModel();
             String[] data = Connection.split(C.ReturnSingleValue("Select Vill||'-'||Bari||'-'||HH||'-'||MSlNo HH from Events Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"' and MSlNo='"+ MSlNo +"' and EvType='"+ EnType +"' and EvDate='"+ EnDate +"'"),'-');
             String SQL = "Select * from "+ TableName +"  Where Vill='"+ data[0] +"' and Bari='"+ data[1] +"' and HH='"+ data[2] +"' and MSlNo='"+ data[3] +"'";
             List<Member_DataModel> data1 = d.SelectAll(this, SQL);
             for(Member_DataModel item : data1){

                 txtName.setText(item.getName());
                 spnRth.setSelection(0);
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
                 if(item.getNeedReview().equals("1")) chkNeedReview.setChecked(true); else chkNeedReview.setChecked(false);
             }
         }
         catch(Exception  e)
         {
             Connection.MessageBox(Events.this, e.getMessage());
             return;
         }
     }
 }