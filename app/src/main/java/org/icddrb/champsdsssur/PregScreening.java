
 package org.icddrb.champsdsssur;


 //Android Manifest Code
 //<activity android:name=".PregScreening" android:label="PregScreening" />
 import java.text.ParseException;
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
 import android.view.KeyEvent;
 import android.os.Bundle;
 import android.view.Menu;
 import android.view.MenuInflater;
 import android.view.MenuItem;
 import android.view.View;
 import android.view.MotionEvent;
 import android.view.View.OnFocusChangeListener;
 import android.view.ViewGroup;
 import android.view.LayoutInflater;
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
 import android.widget.CompoundButton;
 import android.graphics.Color;
 import Utility.*;
 import Common.*;

 public class PregScreening extends Activity {
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
         TextView lblHeading;
         LinearLayout seclbl01;
         View linelbl01;
         EditText txtHHNo;
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
         Spinner spnMSlNo;
         EditText txtMSlNo;
         LinearLayout secPNo;
         View linePNo;
         TextView VlblPNo;
         EditText txtPNo;
         LinearLayout secEvType;
         View lineEvType;
         TextView VlblEvType;
         EditText txtEvType;
         LinearLayout secEvDate;
         View lineEvDate;
         TextView VlblEvDate;
         EditText dtpEvDate;
         LinearLayout secPregnancyID;
         View linePregnancyID;
         TextView VlblPregnancyID;
         EditText txtPregnancyID;
         LinearLayout secRnd;
         View lineRnd;
         TextView VlblRnd;
         EditText txtRnd;
         LinearLayout secPhoneNo;
         View linePhoneNo;
         TextView VlblPhoneNo;
         EditText txtPhoneNo;
         LinearLayout secInfoSource;
         View lineInfoSource;
         TextView VlblInfoSource;
         RadioGroup rdogrpInfoSource;
         
         RadioButton rdoInfoSource1;
         RadioButton rdoInfoSource2;
         LinearLayout secPregNotiDate;
         View linePregNotiDate;
         TextView VlblPregNotiDate;
         EditText dtpPregNotiDate;
         LinearLayout secPregConCriteria;
         View linePregConCriteria;
         TextView VlblPregConCriteria;
         Spinner spnPregConCriteria;
         LinearLayout seclbl02;
         View linelbl02;
         LinearLayout secEligible;
         View lineEligible;
         TextView VlblEligible;
         RadioGroup rdogrpEligible;
         
         RadioButton rdoEligible1;
         RadioButton rdoEligible2;
         LinearLayout secEligibleDate;
         View lineEligibleDate;
         TextView VlblEligibleDate;
         EditText dtpEligibleDate;
         LinearLayout secEnrollDate;
         View lineEnrollDate;
         TextView VlblEnrollDate;
         EditText dtpEnrollDate;
         LinearLayout seclbl03;
         View linelbl03;
         LinearLayout seclbl04;
         View linelbl04;
         LinearLayout secPrevPregHis;
         View linePrevPregHis;
         TextView VlblPrevPregHis;
         RadioGroup rdogrpPrevPregHis;
         
         RadioButton rdoPrevPregHis1;
         RadioButton rdoPrevPregHis2;
         LinearLayout secStillBirth;
         View lineStillBirth;
         TextView VlblStillBirth;
         Spinner spnStillBirth;
         LinearLayout secStillBirthNo;
         View lineStillBirthNo;
         TextView VlblStillBirthNo;
         EditText txtStillBirthNo;
         LinearLayout secMiscAbor;
         View lineMiscAbor;
         TextView VlblMiscAbor;
         Spinner spnMiscAbor;
         LinearLayout secMiscAborNo;
         View lineMiscAborNo;
         TextView VlblMiscAborNo;
         EditText txtMiscAborNo;
         LinearLayout secLastPregresult;
         View lineLastPregresult;
         TextView VlblLastPregresult;
         RadioGroup rdogrpLastPregresult;
         
         RadioButton rdoLastPregresult1;
         RadioButton rdoLastPregresult2;
         LinearLayout secDelDate;
         View lineDelDate;
         TextView VlblDelDate;
         EditText dtpDelDate;
         LinearLayout secCesaDel;
         View lineCesaDel;
         TextView VlblCesaDel;
         Spinner spnCesaDel;
         LinearLayout secCesaDelNo;
         View lineCesaDelNo;
         TextView VlblCesaDelNo;
         EditText txtCesaDelNo;
         LinearLayout secObtEstiDelDate;
         View lineObtEstiDelDate;
         TextView VlblObtEstiDelDate;
         Spinner spnObtEstiDelDate;
         TextView VlblUnreliLMP;
         CheckBox chkUnreliLMP;
         LinearLayout secLMPDate;
         View lineLMPDate;
         TextView VlblLMPDate;
         EditText dtpLMPDate;
         LinearLayout secUltraTrime;
         View lineUltraTrime;
         TextView VlblUltraTrime;
         RadioGroup rdogrpUltraTrime;
         
         RadioButton rdoUltraTrime1;
         RadioButton rdoUltraTrime2;
         RadioButton rdoUltraTrime3;
         RadioButton rdoUltraTrime4;
         LinearLayout secOthSpec;
         View lineOthSpec;
         TextView VlblOthSpec;
         EditText txtOthSpec;

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
    static String EVTYPE = "";
    static String EVDATE = "";
    static String RND = "";
    static String PNo  = "";
    static String EventType ="";
    static String EventDate="";
    static String PregnancyID="";

    boolean dataSeatch = false;
 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.pregscreening);
//         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = sp.getValue(this, "deviceid");
         ENTRYUSER = sp.getValue(this, "userid");

         IDbundle = getIntent().getExtras();
         VILL = IDbundle.getString("Vill");
         BARI = IDbundle.getString("Bari");
         HH = IDbundle.getString("HH");
         MSLNO = IDbundle.getString("MSlNo");
         EVTYPE = IDbundle.getString("EvType");
         EVDATE = IDbundle.getString("EvDate");
         PregnancyID =IDbundle.getString("PregnancyID");
//         RND = IDbundle.getString("Rnd");
         sp = new MySharedPreferences();
         RND = sp.getValue(this,"roundno");
         TableName = "PregScreening";

         //turnGPSOn();

         //GPS Location
         //FindLocation();
         // Double.toString(currentLatitude);
         // Double.toString(currentLongitude);
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(PregScreening.this);
                 adb.setTitle("Close");
                 adb.setMessage("Do you want to close this form[Yes/No]?");
                 adb.setNegativeButton("No", null);
                 adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         finish();
                     }});
                 adb.show();
             }});

         txtHHNo = (EditText) findViewById(R.id.txtHHNo);
         seclbl01=(LinearLayout)findViewById(R.id.seclbl01);
         linelbl01=(View)findViewById(R.id.linelbl01);
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

//         secMSlNo=(LinearLayout)findViewById(R.id.secMSlNo);
//         lineMSlNo=(View)findViewById(R.id.lineMSlNo);
//         VlblMSlNo=(TextView) findViewById(R.id.VlblMSlNo);
         txtMSlNo=(EditText) findViewById(R.id.txtMSlNo);

//         secMSlNo=(LinearLayout)findViewById(R.id.secMSlNo);
//         lineMSlNo=(View)findViewById(R.id.lineMSlNo);
//         VlblMSlNo=(TextView) findViewById(R.id.VlblMSlNo);
//         spnMSlNo=(Spinner) findViewById(R.id.spnMSlNo);
//         spnMSlNo.setAdapter(C.getArrayAdapter("Select '' union Select MSlNo||'-'||Name from tmpMember Where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH + "' and (julianday(EnDate)-julianday(BDate))<=18262 and Sex='2' and MS<>'30' and Pstat='41'"));

         secPNo=(LinearLayout)findViewById(R.id.secPNo);
         linePNo=(View)findViewById(R.id.linePNo);
         VlblPNo=(TextView) findViewById(R.id.VlblPNo);
         txtPNo=(EditText) findViewById(R.id.txtPNo);

//         spnMSlNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                 if(spnMSlNo.getSelectedItemPosition()==0){
//                     txtPregnancyID.setText("");
//                     txtPregnancyID.setSelection(0);
//                 }else{
//                     String MSL = spnMSlNo.getSelectedItemPosition()==0?"":Global.Left(spnMSlNo.getSelectedItem().toString(),2);
////                     String PNo= C.ReturnSingleValue("Select PNo from tmpMember where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ MSL +"'");
//                     String EventType= C.ReturnSingleValue("Select EvType from Events where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ MSL +"'and EvType='41'");
//                     String EventDate= C.ReturnSingleValue("Select EvDate from Events where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ MSL +"'and EvType='41'");
//                     txtPregnancyID.setText(VILL.toString()+BARI.toString()+HH.toString()+ MSL + EventType + EventDate);
//                     txtPregnancyID.setText(txtPregnancyID.getText().toString().replace("-",""));
//                     txtPregnancyID.setFocusable(false);
//                     secPhoneNo.setVisibility(View.VISIBLE);
//                     secInfoSource.setVisibility(View.VISIBLE);
//
//                     if(dataSeatch == true)
//                         DataSearch(VILL,BARI,HH,MSL,EventType,EventDate,RND);
//                 }
//             }
//             public void onNothingSelected(AdapterView<?> parentView) {
//
//             }
//
//         });

         secPNo=(LinearLayout)findViewById(R.id.secPNo);
         linePNo=(View)findViewById(R.id.linePNo);
         VlblPNo=(TextView) findViewById(R.id.VlblPNo);
         txtPNo=(EditText) findViewById(R.id.txtPNo);
         secEvType=(LinearLayout)findViewById(R.id.secEvType);
         lineEvType=(View)findViewById(R.id.lineEvType);
         VlblEvType=(TextView) findViewById(R.id.VlblEvType);
         txtEvType=(EditText) findViewById(R.id.txtEvType);
         secEvDate=(LinearLayout)findViewById(R.id.secEvDate);
         lineEvDate=(View)findViewById(R.id.lineEvDate);
         VlblEvDate=(TextView) findViewById(R.id.VlblEvDate);
         dtpEvDate=(EditText) findViewById(R.id.dtpEvDate);
         secPregnancyID=(LinearLayout)findViewById(R.id.secPregnancyID);
         linePregnancyID=(View)findViewById(R.id.linePregnancyID);
         VlblPregnancyID=(TextView) findViewById(R.id.VlblPregnancyID);
         txtPregnancyID=(EditText) findViewById(R.id.txtPregnancyID);
         secRnd=(LinearLayout)findViewById(R.id.secRnd);
         lineRnd=(View)findViewById(R.id.lineRnd);
         VlblRnd=(TextView) findViewById(R.id.VlblRnd);
         txtRnd=(EditText) findViewById(R.id.txtRnd);
         secPhoneNo=(LinearLayout)findViewById(R.id.secPhoneNo);
         linePhoneNo=(View)findViewById(R.id.linePhoneNo);
         VlblPhoneNo=(TextView) findViewById(R.id.VlblPhoneNo);
         txtPhoneNo=(EditText) findViewById(R.id.txtPhoneNo);
         secInfoSource=(LinearLayout)findViewById(R.id.secInfoSource);
         lineInfoSource=(View)findViewById(R.id.lineInfoSource);
         VlblInfoSource = (TextView) findViewById(R.id.VlblInfoSource);
         rdogrpInfoSource = (RadioGroup) findViewById(R.id.rdogrpInfoSource);
         
         rdoInfoSource1 = (RadioButton) findViewById(R.id.rdoInfoSource1);
         rdoInfoSource2 = (RadioButton) findViewById(R.id.rdoInfoSource2);
         rdogrpInfoSource.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
         @Override
         public void onCheckedChanged(RadioGroup radioGroup,int radioButtonID) {
             String rbData = "";
             RadioButton rb;
             String[] d_rdogrpInfoSource = new String[] {"1","2"};
             for (int i = 0; i < rdogrpInfoSource.getChildCount(); i++)
             {
               rb = (RadioButton)rdogrpInfoSource.getChildAt(i);
               if (rb.isChecked()) rbData = d_rdogrpInfoSource[i];
             }

             if(rbData.equalsIgnoreCase("1"))
             {
                    secStillBirthNo.setVisibility(View.GONE);
                    lineStillBirthNo.setVisibility(View.GONE);
                    secPregNotiDate.setVisibility(View.GONE);
                    linePregNotiDate.setVisibility(View.GONE);
                    dtpPregNotiDate.setText("");
                    secPregConCriteria.setVisibility(View.GONE);
                    linePregConCriteria.setVisibility(View.GONE);
                    spnPregConCriteria.setSelection(0);
                    seclbl02.setVisibility(View.GONE);
                    linelbl02.setVisibility(View.GONE);
                    secEligible.setVisibility(View.GONE);
                    lineEligible.setVisibility(View.GONE);
                    rdogrpEligible.clearCheck();
                    secEligibleDate.setVisibility(View.GONE);
                    lineEligibleDate.setVisibility(View.GONE);
                    dtpEligibleDate.setText("");
                    secEnrollDate.setVisibility(View.GONE);
                    lineEnrollDate.setVisibility(View.GONE);
                    dtpEnrollDate.setText("");
                    seclbl03.setVisibility(View.GONE);
                    linelbl03.setVisibility(View.GONE);
                    seclbl04.setVisibility(View.GONE);
                    linelbl04.setVisibility(View.GONE);
                    secPrevPregHis.setVisibility(View.VISIBLE);
                    linePrevPregHis.setVisibility(View.VISIBLE);
//                    secPrevPregHis.setVisibility(View.GONE);
//                    linePrevPregHis.setVisibility(View.GONE);
//                    rdogrpPrevPregHis.clearCheck();
             }
             else if(rbData.equalsIgnoreCase("2"))
             {
                    secPregNotiDate.setVisibility(View.VISIBLE);
                    linePregNotiDate.setVisibility(View.VISIBLE);
                    secPregConCriteria.setVisibility(View.VISIBLE);
                    linePregConCriteria.setVisibility(View.VISIBLE);
                    seclbl02.setVisibility(View.VISIBLE);
                    linelbl02.setVisibility(View.VISIBLE);
                    secEligible.setVisibility(View.VISIBLE);
                    lineEligible.setVisibility(View.VISIBLE);

                    secEligibleDate.setVisibility(View.GONE);
                    lineEligibleDate.setVisibility(View.GONE);
                    secEnrollDate.setVisibility(View.GONE);
                    lineEnrollDate.setVisibility(View.GONE);
                    seclbl03.setVisibility(View.GONE);
                    linelbl03.setVisibility(View.GONE);
                    seclbl04.setVisibility(View.GONE);
                    linelbl04.setVisibility(View.GONE);
                    secPrevPregHis.setVisibility(View.GONE);
                    linePrevPregHis.setVisibility(View.GONE);
                    secObtEstiDelDate.setVisibility(View.GONE);
                    lineObtEstiDelDate.setVisibility(View.GONE);
                    secStillBirthNo.setVisibility(View.GONE);
                    lineStillBirthNo.setVisibility(View.GONE);
                    secMiscAborNo.setVisibility(View.GONE);
                    lineMiscAborNo.setVisibility(View.GONE);
             }
            }
         public void onNothingSelected(AdapterView<?> adapterView) {
             return;
            } 
         }); 
         secPregNotiDate=(LinearLayout)findViewById(R.id.secPregNotiDate);
         linePregNotiDate=(View)findViewById(R.id.linePregNotiDate);
         VlblPregNotiDate=(TextView) findViewById(R.id.VlblPregNotiDate);
         dtpPregNotiDate=(EditText) findViewById(R.id.dtpPregNotiDate);
         secPregConCriteria=(LinearLayout)findViewById(R.id.secPregConCriteria);
         linePregConCriteria=(View)findViewById(R.id.linePregConCriteria);
         VlblPregConCriteria=(TextView) findViewById(R.id.VlblPregConCriteria);
         spnPregConCriteria=(Spinner) findViewById(R.id.spnPregConCriteria);
         List<String> listPregConCriteria = new ArrayList<String>();
         
         listPregConCriteria.add("");
         listPregConCriteria.add("1-শেষ মাসিক 6 সপ্তাহ আগে বা তার চেয়ে বেশি সময়");
         listPregConCriteria.add("2-গর্ভবতী পেট দৃশ্যমান");
         listPregConCriteria.add("3-গর্ভবতী মহিলা ভ্রূণের নড়াচড়া অনুভব করেছে");
         listPregConCriteria.add("4-বাড়িতে গর্ভাবস্থা পরীক্ষা পজেটিভ");
         listPregConCriteria.add("5-শারীরিক পরীক্ষা");
         listPregConCriteria.add("6-কানের সাহায্যে হৃদ্পরীক্ষা");
         listPregConCriteria.add("7-আল্ট্রাসাউন্ড");
         listPregConCriteria.add("8-ফেসেলিটিতে পজেটিভ গর্ভাবস্থা পরীক্ষা");
         ArrayAdapter<String> adptrPregConCriteria= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listPregConCriteria);
         spnPregConCriteria.setAdapter(adptrPregConCriteria);

         seclbl02=(LinearLayout)findViewById(R.id.seclbl02);
         linelbl02=(View)findViewById(R.id.linelbl02);
         secEligible=(LinearLayout)findViewById(R.id.secEligible);
         lineEligible=(View)findViewById(R.id.lineEligible);
         VlblEligible = (TextView) findViewById(R.id.VlblEligible);
         rdogrpEligible = (RadioGroup) findViewById(R.id.rdogrpEligible);
         
         rdoEligible1 = (RadioButton) findViewById(R.id.rdoEligible1);
         rdoEligible2 = (RadioButton) findViewById(R.id.rdoEligible2);
         rdogrpEligible.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
         @Override
         public void onCheckedChanged(RadioGroup radioGroup,int radioButtonID) {
             String rbData = "";
             RadioButton rb;
             String[] d_rdogrpEligible = new String[] {"1","2"};
             for (int i = 0; i < rdogrpEligible.getChildCount(); i++)
             {
               rb = (RadioButton)rdogrpEligible.getChildAt(i);
               if (rb.isChecked()) rbData = d_rdogrpEligible[i];
             }

             if(rbData.equalsIgnoreCase("2"))
             {
                    secEligibleDate.setVisibility(View.GONE);
                    lineEligibleDate.setVisibility(View.GONE);
                    dtpEligibleDate.setText("");
                    secEnrollDate.setVisibility(View.GONE);
                    lineEnrollDate.setVisibility(View.GONE);
                    dtpEnrollDate.setText("");
                    seclbl03.setVisibility(View.GONE);
                    linelbl03.setVisibility(View.GONE);
                    seclbl04.setVisibility(View.GONE);
                    linelbl04.setVisibility(View.GONE);
                    secPrevPregHis.setVisibility(View.GONE);
                    linePrevPregHis.setVisibility(View.GONE);
                    rdogrpPrevPregHis.clearCheck();
                    secStillBirth.setVisibility(View.GONE);
                    lineStillBirth.setVisibility(View.GONE);
                    spnStillBirth.setSelection(0);
                    secStillBirthNo.setVisibility(View.GONE);
                    lineStillBirthNo.setVisibility(View.GONE);
                    txtStillBirthNo.setText("");
                    secMiscAbor.setVisibility(View.GONE);
                    lineMiscAbor.setVisibility(View.GONE);
                    spnMiscAbor.setSelection(0);
                    secMiscAborNo.setVisibility(View.GONE);
                    lineMiscAborNo.setVisibility(View.GONE);
                    txtMiscAborNo.setText("");
                    secLastPregresult.setVisibility(View.GONE);
                    lineLastPregresult.setVisibility(View.GONE);
                    rdogrpLastPregresult.clearCheck();
                    secDelDate.setVisibility(View.GONE);
                    lineDelDate.setVisibility(View.GONE);
                    dtpDelDate.setText("");
                    secCesaDel.setVisibility(View.GONE);
                    lineCesaDel.setVisibility(View.GONE);
                    spnCesaDel.setSelection(0);
                    secCesaDelNo.setVisibility(View.GONE);
                    lineCesaDelNo.setVisibility(View.GONE);
                    txtCesaDelNo.setText("");
                    secObtEstiDelDate.setVisibility(View.GONE);
                    lineObtEstiDelDate.setVisibility(View.GONE);
                    spnObtEstiDelDate.setSelection(0);
                    chkUnreliLMP.setChecked(false);
                    secLMPDate.setVisibility(View.GONE);
                    lineLMPDate.setVisibility(View.GONE);
                    dtpLMPDate.setText("");
                    secUltraTrime.setVisibility(View.GONE);
                    lineUltraTrime.setVisibility(View.GONE);
                    rdogrpUltraTrime.clearCheck();
                    secOthSpec.setVisibility(View.GONE);
                    lineOthSpec.setVisibility(View.GONE);
                    txtOthSpec.setText("");
             }
             else if(rbData.equalsIgnoreCase("1"))
             {
                    secEligibleDate.setVisibility(View.VISIBLE);
                    lineEligibleDate.setVisibility(View.VISIBLE);
                    secEnrollDate.setVisibility(View.VISIBLE);
                    lineEnrollDate.setVisibility(View.VISIBLE);

                    seclbl03.setVisibility(View.VISIBLE);
                    linelbl03.setVisibility(View.VISIBLE);
                    seclbl04.setVisibility(View.VISIBLE);
                    linelbl04.setVisibility(View.VISIBLE);
                    secPrevPregHis.setVisibility(View.VISIBLE);
                    linePrevPregHis.setVisibility(View.VISIBLE);

                    secStillBirth.setVisibility(View.GONE);
                    lineStillBirth.setVisibility(View.GONE);
                    secStillBirthNo.setVisibility(View.GONE);
                    lineStillBirthNo.setVisibility(View.GONE);
                    secMiscAbor.setVisibility(View.GONE);
                    lineMiscAbor.setVisibility(View.GONE);
                    secMiscAborNo.setVisibility(View.GONE);
                    lineMiscAborNo.setVisibility(View.GONE);
                    secLastPregresult.setVisibility(View.GONE);
                    lineLastPregresult.setVisibility(View.GONE);
                    secDelDate.setVisibility(View.GONE);
                    lineDelDate.setVisibility(View.GONE);
                    secCesaDel.setVisibility(View.GONE);
                    lineCesaDel.setVisibility(View.GONE);
                    secCesaDelNo.setVisibility(View.GONE);
                    lineCesaDelNo.setVisibility(View.GONE);
                    secObtEstiDelDate.setVisibility(View.VISIBLE);
                    lineObtEstiDelDate.setVisibility(View.VISIBLE);

                    chkUnreliLMP.setChecked(false);
                    secLMPDate.setVisibility(View.GONE);
                    lineLMPDate.setVisibility(View.GONE);
                    dtpLMPDate.setText("");
                    secUltraTrime.setVisibility(View.GONE);
                    lineUltraTrime.setVisibility(View.GONE);
                    rdogrpUltraTrime.clearCheck();
                    secOthSpec.setVisibility(View.GONE);
                    lineOthSpec.setVisibility(View.GONE);
                    txtOthSpec.setText("");
             }
            }
         public void onNothingSelected(AdapterView<?> adapterView) {
             return;
            } 
         }); 
         secEligibleDate=(LinearLayout)findViewById(R.id.secEligibleDate);
         lineEligibleDate=(View)findViewById(R.id.lineEligibleDate);
         VlblEligibleDate=(TextView) findViewById(R.id.VlblEligibleDate);
         dtpEligibleDate=(EditText) findViewById(R.id.dtpEligibleDate);
         secEnrollDate=(LinearLayout)findViewById(R.id.secEnrollDate);
         lineEnrollDate=(View)findViewById(R.id.lineEnrollDate);
         VlblEnrollDate=(TextView) findViewById(R.id.VlblEnrollDate);
         dtpEnrollDate=(EditText) findViewById(R.id.dtpEnrollDate);
         seclbl03=(LinearLayout)findViewById(R.id.seclbl03);
         linelbl03=(View)findViewById(R.id.linelbl03);
         seclbl04=(LinearLayout)findViewById(R.id.seclbl04);
         linelbl04=(View)findViewById(R.id.linelbl04);
         secPrevPregHis=(LinearLayout)findViewById(R.id.secPrevPregHis);
         linePrevPregHis=(View)findViewById(R.id.linePrevPregHis);
         VlblPrevPregHis = (TextView) findViewById(R.id.VlblPrevPregHis);
         rdogrpPrevPregHis = (RadioGroup) findViewById(R.id.rdogrpPrevPregHis);
         
         rdoPrevPregHis1 = (RadioButton) findViewById(R.id.rdoPrevPregHis1);
         rdoPrevPregHis2 = (RadioButton) findViewById(R.id.rdoPrevPregHis2);
         rdogrpPrevPregHis.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
         @Override
         public void onCheckedChanged(RadioGroup radioGroup,int radioButtonID) {
             String rbData = "";
             RadioButton rb;
             String[] d_rdogrpPrevPregHis = new String[] {"1","2"};
             for (int i = 0; i < rdogrpPrevPregHis.getChildCount(); i++)
             {
               rb = (RadioButton)rdogrpPrevPregHis.getChildAt(i);
               if (rb.isChecked()) rbData = d_rdogrpPrevPregHis[i];
             }

             if(rbData.equalsIgnoreCase("2"))
             {
                    secStillBirth.setVisibility(View.GONE);
                    lineStillBirth.setVisibility(View.GONE);
                    spnStillBirth.setSelection(0);
                    secStillBirthNo.setVisibility(View.GONE);
                    lineStillBirthNo.setVisibility(View.GONE);
                    txtStillBirthNo.setText("");
                    secMiscAbor.setVisibility(View.GONE);
                    lineMiscAbor.setVisibility(View.GONE);
                    spnMiscAbor.setSelection(0);
                    secMiscAborNo.setVisibility(View.GONE);
                    lineMiscAborNo.setVisibility(View.GONE);
                    txtMiscAborNo.setText("");
                    secLastPregresult.setVisibility(View.GONE);
                    lineLastPregresult.setVisibility(View.GONE);
                    rdogrpLastPregresult.clearCheck();
                    secDelDate.setVisibility(View.GONE);
                    lineDelDate.setVisibility(View.GONE);
                    dtpDelDate.setText("");
                    secCesaDel.setVisibility(View.GONE);
                    lineCesaDel.setVisibility(View.GONE);
                    spnCesaDel.setSelection(0);
                    secCesaDelNo.setVisibility(View.GONE);
                    lineCesaDelNo.setVisibility(View.GONE);
                    txtCesaDelNo.setText("");
                    secObtEstiDelDate.setVisibility(View.VISIBLE);
                    lineObtEstiDelDate.setVisibility(View.VISIBLE);
             }
             else if(rbData.equalsIgnoreCase("1"))
             {
                    secStillBirth.setVisibility(View.VISIBLE);
                    lineStillBirth.setVisibility(View.VISIBLE);
//                    secStillBirthNo.setVisibility(View.VISIBLE);
//                    lineStillBirthNo.setVisibility(View.VISIBLE);
                    secMiscAbor.setVisibility(View.VISIBLE);
                    lineMiscAbor.setVisibility(View.VISIBLE);
                    secLastPregresult.setVisibility(View.VISIBLE);
                    lineLastPregresult.setVisibility(View.VISIBLE);
                    secDelDate.setVisibility(View.VISIBLE);
                    lineDelDate.setVisibility(View.VISIBLE);
                    secCesaDel.setVisibility(View.VISIBLE);
                    lineCesaDel.setVisibility(View.VISIBLE);
//                    secCesaDelNo.setVisibility(View.VISIBLE);
//                    lineCesaDelNo.setVisibility(View.VISIBLE);
                    secObtEstiDelDate.setVisibility(View.VISIBLE);
                    lineObtEstiDelDate.setVisibility(View.VISIBLE);
             }
            }
         public void onNothingSelected(AdapterView<?> adapterView) {
             return;
            } 
         }); 
         secStillBirth=(LinearLayout)findViewById(R.id.secStillBirth);
         lineStillBirth=(View)findViewById(R.id.lineStillBirth);
         VlblStillBirth=(TextView) findViewById(R.id.VlblStillBirth);
         spnStillBirth=(Spinner) findViewById(R.id.spnStillBirth);
         List<String> listStillBirth = new ArrayList<String>();
         
         listStillBirth.add("");
         listStillBirth.add("1-হ্যাঁ");
         listStillBirth.add("2-না");
         listStillBirth.add("9-অজানা");
         ArrayAdapter<String> adptrStillBirth= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listStillBirth);
         spnStillBirth.setAdapter(adptrStillBirth);

         spnStillBirth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
             String spnData = "";
             if (spnStillBirth.getSelectedItem().toString().length() != 0)
             {
                 spnData = Connection.SelectedSpinnerValue(spnStillBirth.getSelectedItem().toString(), "-");
             }
                 if(spnData.equalsIgnoreCase("2"))
                 {
                    secStillBirthNo.setVisibility(View.GONE);
                    lineStillBirthNo.setVisibility(View.GONE);
                    txtStillBirthNo.setText("");
                 }
                 else if(spnData.equalsIgnoreCase("9"))
                 {
                    secStillBirthNo.setVisibility(View.GONE);
                    lineStillBirthNo.setVisibility(View.GONE);
                    txtStillBirthNo.setText("");
                 }
                 else if(spnData.equalsIgnoreCase("1"))
                 {
                    secStillBirthNo.setVisibility(View.VISIBLE);
                    lineStillBirthNo.setVisibility(View.VISIBLE);
                 }
             }
             @Override
             public void onNothingSelected(AdapterView<?> parentView) {
             }
         });
         secStillBirthNo=(LinearLayout)findViewById(R.id.secStillBirthNo);
         lineStillBirthNo=(View)findViewById(R.id.lineStillBirthNo);
         VlblStillBirthNo=(TextView) findViewById(R.id.VlblStillBirthNo);
         txtStillBirthNo=(EditText) findViewById(R.id.txtStillBirthNo);
         secMiscAbor=(LinearLayout)findViewById(R.id.secMiscAbor);
         lineMiscAbor=(View)findViewById(R.id.lineMiscAbor);
         VlblMiscAbor=(TextView) findViewById(R.id.VlblMiscAbor);
         spnMiscAbor=(Spinner) findViewById(R.id.spnMiscAbor);
         List<String> listMiscAbor = new ArrayList<String>();
         
         listMiscAbor.add("");
         listMiscAbor.add("1-হ্যাঁ");
         listMiscAbor.add("2-না");
         listMiscAbor.add("9-অজানা");
         ArrayAdapter<String> adptrMiscAbor= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listMiscAbor);
         spnMiscAbor.setAdapter(adptrMiscAbor);

         spnMiscAbor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
             String spnData = "";
             if (spnMiscAbor.getSelectedItem().toString().length() != 0)
             {
                 spnData = Connection.SelectedSpinnerValue(spnMiscAbor.getSelectedItem().toString(), "-");
             }
                 if(spnData.equalsIgnoreCase("2"))
                 {
                    secMiscAborNo.setVisibility(View.GONE);
                    lineMiscAborNo.setVisibility(View.GONE);
                    txtMiscAborNo.setText("");
                 }
                 else if(spnData.equalsIgnoreCase("9"))
                 {
                    secMiscAborNo.setVisibility(View.GONE);
                    lineMiscAborNo.setVisibility(View.GONE);
                    txtMiscAborNo.setText("");
                 }
                 else if(spnData.equalsIgnoreCase("1"))
                 {
                    secMiscAborNo.setVisibility(View.VISIBLE);
                    lineMiscAborNo.setVisibility(View.VISIBLE);
                 }
             }
             @Override
             public void onNothingSelected(AdapterView<?> parentView) {
             }
         });
         secMiscAborNo=(LinearLayout)findViewById(R.id.secMiscAborNo);
         lineMiscAborNo=(View)findViewById(R.id.lineMiscAborNo);
         VlblMiscAborNo=(TextView) findViewById(R.id.VlblMiscAborNo);
         txtMiscAborNo=(EditText) findViewById(R.id.txtMiscAborNo);
         secLastPregresult=(LinearLayout)findViewById(R.id.secLastPregresult);
         lineLastPregresult=(View)findViewById(R.id.lineLastPregresult);
         VlblLastPregresult = (TextView) findViewById(R.id.VlblLastPregresult);
         rdogrpLastPregresult = (RadioGroup) findViewById(R.id.rdogrpLastPregresult);
         
         rdoLastPregresult1 = (RadioButton) findViewById(R.id.rdoLastPregresult1);
         rdoLastPregresult2 = (RadioButton) findViewById(R.id.rdoLastPregresult2);
         secDelDate=(LinearLayout)findViewById(R.id.secDelDate);
         lineDelDate=(View)findViewById(R.id.lineDelDate);
         VlblDelDate=(TextView) findViewById(R.id.VlblDelDate);
         dtpDelDate=(EditText) findViewById(R.id.dtpDelDate);
         secCesaDel=(LinearLayout)findViewById(R.id.secCesaDel);
         lineCesaDel=(View)findViewById(R.id.lineCesaDel);
         VlblCesaDel=(TextView) findViewById(R.id.VlblCesaDel);
         spnCesaDel=(Spinner) findViewById(R.id.spnCesaDel);
         List<String> listCesaDel = new ArrayList<String>();
         
         listCesaDel.add("");
         listCesaDel.add("1-হ্যাঁ");
         listCesaDel.add("2-না");
         listCesaDel.add("9-অজানা");
         listCesaDel.add("8-প্রযোজ্য নয়");
         ArrayAdapter<String> adptrCesaDel= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listCesaDel);
         spnCesaDel.setAdapter(adptrCesaDel);

         spnCesaDel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                 String spnData = "";
                 if (spnCesaDel.getSelectedItem().toString().length() != 0)
                 {
                     spnData = Connection.SelectedSpinnerValue(spnCesaDel.getSelectedItem().toString(), "-");
                 }
                 if(spnData.equalsIgnoreCase("2"))
                 {
                     secCesaDelNo.setVisibility(View.GONE);
                     lineCesaDelNo.setVisibility(View.GONE);
                     txtCesaDelNo.setText("");
                 }
                 else if(spnData.equalsIgnoreCase("9"))
                 {
                     secCesaDelNo.setVisibility(View.GONE);
                     lineCesaDelNo.setVisibility(View.GONE);
                     txtCesaDelNo.setText("");
                 }
                 else if(spnData.equalsIgnoreCase("1"))
                 {
                     secCesaDelNo.setVisibility(View.VISIBLE);
                     lineCesaDelNo.setVisibility(View.VISIBLE);
                 }
             }
             @Override
             public void onNothingSelected(AdapterView<?> parentView) {
             }
         });

         secCesaDelNo=(LinearLayout)findViewById(R.id.secCesaDelNo);
         lineCesaDelNo=(View)findViewById(R.id.lineCesaDelNo);
         VlblCesaDelNo=(TextView) findViewById(R.id.VlblCesaDelNo);
         txtCesaDelNo=(EditText) findViewById(R.id.txtCesaDelNo);
         secObtEstiDelDate=(LinearLayout)findViewById(R.id.secObtEstiDelDate);
         lineObtEstiDelDate=(View)findViewById(R.id.lineObtEstiDelDate);
         VlblObtEstiDelDate=(TextView) findViewById(R.id.VlblObtEstiDelDate);
         spnObtEstiDelDate=(Spinner) findViewById(R.id.spnObtEstiDelDate);
         List<String> listObtEstiDelDate = new ArrayList<String>();
         
         listObtEstiDelDate.add("");
         listObtEstiDelDate.add("1-LMP দ্বারা");
         listObtEstiDelDate.add("2-ক্লিনিকাল পরীক্ষা দ্বারা");
         listObtEstiDelDate.add("3-আলট্রাসাউন্ড দ্বারা");
         listObtEstiDelDate.add("4-অন্যান্য");
         ArrayAdapter<String> adptrObtEstiDelDate= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listObtEstiDelDate);
         spnObtEstiDelDate.setAdapter(adptrObtEstiDelDate);

         spnObtEstiDelDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
             String spnData = "";
             if (spnObtEstiDelDate.getSelectedItem().toString().length() != 0)
             {
                 spnData = Connection.SelectedSpinnerValue(spnObtEstiDelDate.getSelectedItem().toString(), "-");
             }
                 if(spnData.equalsIgnoreCase("1"))
                 {
                    chkUnreliLMP.setChecked(false);
                    secLMPDate.setVisibility(View.VISIBLE);
                    lineLMPDate.setVisibility(View.VISIBLE);
                    dtpLMPDate.setText("");
                    secUltraTrime.setVisibility(View.GONE);
                    lineUltraTrime.setVisibility(View.GONE);
                    rdogrpUltraTrime.clearCheck();
                    secOthSpec.setVisibility(View.GONE);
                    lineOthSpec.setVisibility(View.GONE);
                    txtOthSpec.setText("");
                 }
                 else if(spnData.equalsIgnoreCase("2"))
                 {
                    chkUnreliLMP.setChecked(false);
                    secLMPDate.setVisibility(View.GONE);
                    lineLMPDate.setVisibility(View.GONE);
                    dtpLMPDate.setText("");
                    secUltraTrime.setVisibility(View.GONE);
                    lineUltraTrime.setVisibility(View.GONE);
                    rdogrpUltraTrime.clearCheck();
                    secOthSpec.setVisibility(View.GONE);
                    lineOthSpec.setVisibility(View.GONE);
                    txtOthSpec.setText("");
                 }
                 else if(spnData.equalsIgnoreCase("3"))
                 {
                    chkUnreliLMP.setChecked(false);
                    secLMPDate.setVisibility(View.GONE);
                    lineLMPDate.setVisibility(View.GONE);
                    dtpLMPDate.setText("");
                    secUltraTrime.setVisibility(View.VISIBLE);
                    lineUltraTrime.setVisibility(View.VISIBLE);
                    rdogrpUltraTrime.clearCheck();
                    secOthSpec.setVisibility(View.GONE);
                    lineOthSpec.setVisibility(View.GONE);
                    txtOthSpec.setText("");
                 }
                 else if(spnData.equalsIgnoreCase("4"))
                 {
                    chkUnreliLMP.setChecked(false);
                    secLMPDate.setVisibility(View.GONE);
                    lineLMPDate.setVisibility(View.GONE);
                    dtpLMPDate.setText("");
                    secUltraTrime.setVisibility(View.GONE);
                    lineUltraTrime.setVisibility(View.GONE);
                    rdogrpUltraTrime.clearCheck();
                    secOthSpec.setVisibility(View.VISIBLE);
                    lineOthSpec.setVisibility(View.VISIBLE);
                    txtOthSpec.setText("");
                 }
//                 else
//                 {
//                    secLMPDate.setVisibility(View.VISIBLE);
//                    lineLMPDate.setVisibility(View.VISIBLE);
//                    secUltraTrime.setVisibility(View.VISIBLE);
//                    lineUltraTrime.setVisibility(View.VISIBLE);
//                    secOthSpec.setVisibility(View.VISIBLE);
//                    lineOthSpec.setVisibility(View.VISIBLE);
//                 }
             }
             @Override
             public void onNothingSelected(AdapterView<?> parentView) {
             }
         });
         VlblUnreliLMP=(TextView) findViewById(R.id.VlblUnreliLMP);
         chkUnreliLMP=(CheckBox) findViewById(R.id.chkUnreliLMP);
         secLMPDate=(LinearLayout)findViewById(R.id.secLMPDate);
         lineLMPDate=(View)findViewById(R.id.lineLMPDate);
         VlblLMPDate=(TextView) findViewById(R.id.VlblLMPDate);
         dtpLMPDate=(EditText) findViewById(R.id.dtpLMPDate);
         secUltraTrime=(LinearLayout)findViewById(R.id.secUltraTrime);
         lineUltraTrime=(View)findViewById(R.id.lineUltraTrime);
         VlblUltraTrime = (TextView) findViewById(R.id.VlblUltraTrime);
         rdogrpUltraTrime = (RadioGroup) findViewById(R.id.rdogrpUltraTrime);
         
         rdoUltraTrime1 = (RadioButton) findViewById(R.id.rdoUltraTrime1);
         rdoUltraTrime2 = (RadioButton) findViewById(R.id.rdoUltraTrime2);
         rdoUltraTrime3 = (RadioButton) findViewById(R.id.rdoUltraTrime3);
         rdoUltraTrime4 = (RadioButton) findViewById(R.id.rdoUltraTrime4);
         secOthSpec=(LinearLayout)findViewById(R.id.secOthSpec);
         lineOthSpec=(View)findViewById(R.id.lineOthSpec);
         VlblOthSpec=(TextView) findViewById(R.id.VlblOthSpec);
         txtOthSpec=(EditText) findViewById(R.id.txtOthSpec);


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
         dtpPregNotiDate.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 final int DRAWABLE_RIGHT  = 2;
                 if(event.getAction() == MotionEvent.ACTION_UP) {
                     if(event.getRawX() >= (dtpPregNotiDate.getRight() - dtpPregNotiDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                         VariableID = "btnPregNotiDate"; showDialog(DATE_DIALOG);
                      return true;
                     }
                 }
                 return false;
             }
         });
         dtpEligibleDate.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 final int DRAWABLE_RIGHT  = 2;
                 if(event.getAction() == MotionEvent.ACTION_UP) {
                     if(event.getRawX() >= (dtpEligibleDate.getRight() - dtpEligibleDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                         VariableID = "btnEligibleDate"; showDialog(DATE_DIALOG);
                      return true;
                     }
                 }
                 return false;
             }
         });
         dtpEnrollDate.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 final int DRAWABLE_RIGHT  = 2;
                 if(event.getAction() == MotionEvent.ACTION_UP) {
                     if(event.getRawX() >= (dtpEnrollDate.getRight() - dtpEnrollDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                         VariableID = "btnEnrollDate"; showDialog(DATE_DIALOG);
                      return true;
                     }
                 }
                 return false;
             }
         });
         dtpDelDate.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 final int DRAWABLE_RIGHT  = 2;
                 if(event.getAction() == MotionEvent.ACTION_UP) {
                     if(event.getRawX() >= (dtpDelDate.getRight() - dtpDelDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                         VariableID = "btnDelDate"; showDialog(DATE_DIALOG);
                      return true;
                     }
                 }
                 return false;
             }
         });
         dtpLMPDate.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 final int DRAWABLE_RIGHT  = 2;
                 if(event.getAction() == MotionEvent.ACTION_UP) {
                     if(event.getRawX() >= (dtpLMPDate.getRight() - dtpLMPDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                         VariableID = "btnEstiDelDate"; showDialog(DATE_DIALOG);
                      return true;
                     }
                 }
                 return false;
             }
         });

         //Hide all skip variables
         secPhoneNo.setVisibility(View.GONE);
         linePhoneNo.setVisibility(View.GONE);
         secInfoSource.setVisibility(View.GONE);
         lineInfoSource.setVisibility(View.GONE);
         secPregNotiDate.setVisibility(View.GONE);
         linePregNotiDate.setVisibility(View.GONE);
         secPregConCriteria.setVisibility(View.GONE);
         linePregConCriteria.setVisibility(View.GONE);
         seclbl02.setVisibility(View.GONE);
         linelbl02.setVisibility(View.GONE);
         secEligible.setVisibility(View.GONE);
         lineEligible.setVisibility(View.GONE);
         secEligibleDate.setVisibility(View.GONE);
         lineEligibleDate.setVisibility(View.GONE);
         secEnrollDate.setVisibility(View.GONE);
         lineEnrollDate.setVisibility(View.GONE);
         seclbl03.setVisibility(View.GONE);
         linelbl03.setVisibility(View.GONE);
         seclbl04.setVisibility(View.GONE);
         linelbl04.setVisibility(View.GONE);
         secPrevPregHis.setVisibility(View.GONE);
         linePrevPregHis.setVisibility(View.GONE);
         secEligibleDate.setVisibility(View.GONE);
         lineEligibleDate.setVisibility(View.GONE);
         secEnrollDate.setVisibility(View.GONE);
         lineEnrollDate.setVisibility(View.GONE);
         seclbl03.setVisibility(View.GONE);
         linelbl03.setVisibility(View.GONE);
         seclbl04.setVisibility(View.GONE);
         linelbl04.setVisibility(View.GONE);
         secPrevPregHis.setVisibility(View.GONE);
         linePrevPregHis.setVisibility(View.GONE);
         secStillBirth.setVisibility(View.GONE);
         lineStillBirth.setVisibility(View.GONE);
         secStillBirthNo.setVisibility(View.GONE);
         lineStillBirthNo.setVisibility(View.GONE);
         secMiscAbor.setVisibility(View.GONE);
         lineMiscAbor.setVisibility(View.GONE);
         secMiscAborNo.setVisibility(View.GONE);
         lineMiscAborNo.setVisibility(View.GONE);
         secLastPregresult.setVisibility(View.GONE);
         lineLastPregresult.setVisibility(View.GONE);
         secDelDate.setVisibility(View.GONE);
         lineDelDate.setVisibility(View.GONE);
         secCesaDel.setVisibility(View.GONE);
         lineCesaDel.setVisibility(View.GONE);
         secCesaDelNo.setVisibility(View.GONE);
         lineCesaDelNo.setVisibility(View.GONE);
         secObtEstiDelDate.setVisibility(View.GONE);
         lineObtEstiDelDate.setVisibility(View.GONE);
         secLMPDate.setVisibility(View.GONE);
         lineLMPDate.setVisibility(View.GONE);
         secUltraTrime.setVisibility(View.GONE);
         lineUltraTrime.setVisibility(View.GONE);
         secOthSpec.setVisibility(View.GONE);
         lineOthSpec.setVisibility(View.GONE);
         secStillBirth.setVisibility(View.GONE);
         lineStillBirth.setVisibility(View.GONE);
         secStillBirthNo.setVisibility(View.GONE);
         lineStillBirthNo.setVisibility(View.GONE);
         secMiscAbor.setVisibility(View.GONE);
         lineMiscAbor.setVisibility(View.GONE);
         secMiscAborNo.setVisibility(View.GONE);
         lineMiscAborNo.setVisibility(View.GONE);
         secLastPregresult.setVisibility(View.GONE);
         lineLastPregresult.setVisibility(View.GONE);
         secDelDate.setVisibility(View.GONE);
         lineDelDate.setVisibility(View.GONE);
         secCesaDel.setVisibility(View.GONE);
         lineCesaDel.setVisibility(View.GONE);
         secCesaDelNo.setVisibility(View.GONE);
         lineCesaDelNo.setVisibility(View.GONE);
         secStillBirthNo.setVisibility(View.GONE);
         lineStillBirthNo.setVisibility(View.GONE);
         secStillBirthNo.setVisibility(View.GONE);
         lineStillBirthNo.setVisibility(View.GONE);
         secMiscAborNo.setVisibility(View.GONE);
         lineMiscAborNo.setVisibility(View.GONE);
         secMiscAborNo.setVisibility(View.GONE);
         lineMiscAborNo.setVisibility(View.GONE);
         secLMPDate.setVisibility(View.GONE);
         lineLMPDate.setVisibility(View.GONE);
         secUltraTrime.setVisibility(View.GONE);
         lineUltraTrime.setVisibility(View.GONE);
         secOthSpec.setVisibility(View.GONE);
         lineOthSpec.setVisibility(View.GONE);
         secLMPDate.setVisibility(View.GONE);
         lineLMPDate.setVisibility(View.GONE);
         secUltraTrime.setVisibility(View.GONE);
         lineUltraTrime.setVisibility(View.GONE);
         secOthSpec.setVisibility(View.GONE);
         lineOthSpec.setVisibility(View.GONE);
         secLMPDate.setVisibility(View.GONE);
         lineLMPDate.setVisibility(View.GONE);
         secUltraTrime.setVisibility(View.GONE);
         lineUltraTrime.setVisibility(View.GONE);
         secOthSpec.setVisibility(View.GONE);
         lineOthSpec.setVisibility(View.GONE);
         secLMPDate.setVisibility(View.GONE);
         lineLMPDate.setVisibility(View.GONE);
         secUltraTrime.setVisibility(View.GONE);
         lineUltraTrime.setVisibility(View.GONE);
         secOthSpec.setVisibility(View.GONE);
         lineOthSpec.setVisibility(View.GONE);

         txtVill.setEnabled(false);
         txtBari.setEnabled(false);
         txtHH.setEnabled(false);

         txtVill.setText(VILL);
         txtVill.setFocusable(false);
         txtBari.setText(BARI);
         txtBari.setFocusable(false);
         txtHH.setText(HH);
         txtHH.setFocusable(false);
         txtMSlNo.setText(MSLNO);
         txtMSlNo.setEnabled(false);

         String EventType= C.ReturnSingleValue("Select Pstat from tmpMember where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ MSLNO +"'and Pstat='41'");
         String EventDate= C.ReturnSingleValue("Select LmpDt from tmpMember where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ MSLNO +"'and Pstat='41'");

         txtPregnancyID.setText(VILL.toString()+BARI.toString()+HH.toString()+ MSLNO.toString() + EventType + EventDate);
         txtPregnancyID.setText(txtPregnancyID.getText().toString().replace("-",""));
         txtPregnancyID.setFocusable(false);
         secPhoneNo.setVisibility(View.VISIBLE);
         secInfoSource.setVisibility(View.VISIBLE);
         txtPregnancyID.setEnabled(false);

         txtHHNo.setText(VILL+"-"+BARI+"-"+HH);
         txtHHNo.setEnabled(false);
         txtRnd.setEnabled(false);
         txtRnd.setText(RND);

         Button cmdSave = (Button) findViewById(R.id.cmdSave);
        cmdSave.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) { 
            DataSave();
        }});
     }
     catch(Exception  e)
     {
         Connection.MessageBox(PregScreening.this, e.getMessage());
         return;
     }
 }
     protected void onResume() {
         super.onResume();
         dataSeatch = true;
     }
 private void DataSave()
 {
   try
     {
         String DV="";
//         String MSL = txtMSlNo.toString();
         String PNo= C.ReturnSingleValue("Select PNo from tmpMember where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ MSLNO +"'");
         String EventType= C.ReturnSingleValue("Select Pstat from tmpMember where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ MSLNO +"'and Pstat='41'");
         String EventDate= C.ReturnSingleValue("Select LmpDt from tmpMember where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"' and MSlNo='"+ MSLNO +"'and Pstat='41'");

         if(txtVill.getText().toString().length()==0 & secVill.isShown())
           {
             Connection.MessageBox(PregScreening.this, "Required field: গ্রাম.");
             txtVill.requestFocus(); 
             return;	
           }
         else if(txtBari.getText().toString().length()==0 & secBari.isShown())
           {
             Connection.MessageBox(PregScreening.this, "Required field: বাড়ি.");
             txtBari.requestFocus(); 
             return;	
           }
         else if(txtHH.getText().toString().length()==0 & secHH.isShown())
           {
             Connection.MessageBox(PregScreening.this, "Required field: খানা.");
             txtHH.requestFocus(); 
             return;	
           }
         else if(txtMSlNo.getText().toString().length()==0  & txtMSlNo.isShown())
           {
             Connection.MessageBox(PregScreening.this, "Required field: সিরিয়াল নং.");
               txtMSlNo.requestFocus();
             return;	
           }
         else if(txtPNo.getText().toString().length()==0 & secPNo.isShown())
           {
             Connection.MessageBox(PregScreening.this, "Required field: PNo.");
             txtPNo.requestFocus(); 
             return;	
           }
         else if(txtEvType.getText().toString().length()==0 & secEvType.isShown())
           {
             Connection.MessageBox(PregScreening.this, "Required field: ইভেন্ট এর ধরন.");
             txtEvType.requestFocus(); 
             return;	
           }
         DV = Global.DateValidate(dtpEvDate.getText().toString());
         if(DV.length()!=0 & secEvDate.isShown())
           {
             Connection.MessageBox(PregScreening.this, DV);
             dtpEvDate.requestFocus(); 
             return;	
           }
         else if(txtPregnancyID.getText().toString().length()==0 & secPregnancyID.isShown())
           {
             Connection.MessageBox(PregScreening.this, "Required field: .");
             txtPregnancyID.requestFocus(); 
             return;	
           }
         else if(txtRnd.getText().toString().length()==0 & secRnd.isShown())
           {
             Connection.MessageBox(PregScreening.this, "Required field: রাউনড নং.");
             txtRnd.requestFocus(); 
             return;	
           }
         else if(txtPhoneNo.getText().toString().length()==0 & secPhoneNo.isShown())
           {
             Connection.MessageBox(PregScreening.this, "Required field: র্গভবতী মহলিা / নতুন মার ফোন নম্বর.");
             txtPhoneNo.requestFocus(); 
             return;	
           }
         
         else if(!rdoInfoSource1.isChecked() & !rdoInfoSource2.isChecked() & secInfoSource.isShown())
           {
              Connection.MessageBox(PregScreening.this, "Select anyone options from (তথ্যের উৎস).");
              rdoInfoSource1.requestFocus();
              return;
           }
         DV = Global.DateValidate(dtpPregNotiDate.getText().toString());
         if(DV.length()!=0 & secPregNotiDate.isShown())
           {
             Connection.MessageBox(PregScreening.this, DV);
             dtpPregNotiDate.requestFocus(); 
             return;	
           }
         else if(spnPregConCriteria.getSelectedItemPosition()==0  & secPregConCriteria.isShown())
           {
             Connection.MessageBox(PregScreening.this, "Required field: স্ক্রীনিং এর সময়  নিম্নোক্ত মাপকাঠিগুলির মধ্যে কোনটি গর্ভাবস্থা নিশ্চিত করেছে?.");
             spnPregConCriteria.requestFocus(); 
             return;	
           }
         
         else if(!rdoEligible1.isChecked() & !rdoEligible2.isChecked() & secEligible.isShown())
           {
              Connection.MessageBox(PregScreening.this, "Select anyone options from (মহিলাকি প্রেগন্যান্সি সারভিলেনছে অন্তর্ভুক্ত হবার যজ্ঞ).");
              rdoEligible1.requestFocus();
              return;
           }
         DV = Global.DateValidate(dtpEligibleDate.getText().toString());
         if(DV.length()!=0 & secEligibleDate.isShown())
           {
             Connection.MessageBox(PregScreening.this, DV);
             dtpEligibleDate.requestFocus(); 
             return;	
           }
         DV = Global.DateValidate(dtpEnrollDate.getText().toString());
         if(DV.length()!=0 & secEnrollDate.isShown())
           {
             Connection.MessageBox(PregScreening.this, DV);
             dtpEnrollDate.requestFocus(); 
             return;	
           }
         
         else if(!rdoPrevPregHis1.isChecked() & !rdoPrevPregHis2.isChecked() & secPrevPregHis.isShown())
           {
              Connection.MessageBox(PregScreening.this, "Select anyone options from (এই গর্ভের আগে আপনি কি গর্ভবতী হয়ে ছিলেন ?).");
              rdoPrevPregHis1.requestFocus();
              return;
           }
         else if(spnStillBirth.getSelectedItemPosition()==0  & secStillBirth.isShown())
           {
             Connection.MessageBox(PregScreening.this, "Required field: এই গর্ভাবস্থাকে বাদ দিয়ে, আপনি কি কখনো একটি শিশুর জন্ম দিয়েছেন যিনি ডেলিভারিতে জীবিত ছিলেন না.");
             spnStillBirth.requestFocus(); 
             return;	
           }
         else if(txtStillBirthNo.getText().toString().length()==0 & secStillBirthNo.isShown())
           {
             Connection.MessageBox(PregScreening.this, "Required field: মৃত জন্মের সংখ্যা.");
             txtStillBirthNo.requestFocus(); 
             return;	
           }
         else if(Integer.valueOf(txtStillBirthNo.getText().toString().length()==0 ? "1" : txtStillBirthNo.getText().toString()) < 1 || Integer.valueOf(txtStillBirthNo.getText().toString().length()==0 ? "11" : txtStillBirthNo.getText().toString()) > 11)
           {
             Connection.MessageBox(PregScreening.this, "Value should be between 1 and 11(মৃত জন্মের সংখ্যা).");
             txtStillBirthNo.requestFocus(); 
             return;	
           }
         else if(spnMiscAbor.getSelectedItemPosition()==0  & secMiscAbor.isShown())
           {
             Connection.MessageBox(PregScreening.this, "Required field: এই গর্ভাবস্থা বাদ দিয়ে, আপনার কি গর্ভাবস্থার প্রথম 7 মাসের গর্ভপাত বা এবরসন আছে?.");
             spnMiscAbor.requestFocus(); 
             return;	
           }
         else if(txtMiscAborNo.getText().toString().length()==0 & secMiscAborNo.isShown())
           {
             Connection.MessageBox(PregScreening.this, "Required field: গর্ভপাতের সংখ্যা.");
             txtMiscAborNo.requestFocus(); 
             return;	
           }
         else if(Integer.valueOf(txtMiscAborNo.getText().toString().length()==0 ? "1" : txtMiscAborNo.getText().toString()) < 1 || Integer.valueOf(txtMiscAborNo.getText().toString().length()==0 ? "11" : txtMiscAborNo.getText().toString()) > 11)
           {
             Connection.MessageBox(PregScreening.this, "Value should be between 1 and 11(গর্ভপাতের সংখ্যা).");
             txtMiscAborNo.requestFocus(); 
             return;	
           }
         
         else if(!rdoLastPregresult1.isChecked() & !rdoLastPregresult2.isChecked() & secLastPregresult.isShown())
           {
              Connection.MessageBox(PregScreening.this, "Select anyone options from (আপনার শেষ গর্ভের ফলাফল কি জীবিত জন্ম ছিল ?).");
              rdoLastPregresult1.requestFocus();
              return;
           }
         DV = Global.DateValidate(dtpDelDate.getText().toString());
         if(DV.length()!=0 & secDelDate.isShown())
           {
             Connection.MessageBox(PregScreening.this, DV);
             dtpDelDate.requestFocus(); 
             return;	
           }
         else if(spnCesaDel.getSelectedItemPosition()==0  & secCesaDel.isShown())
           {
             Connection.MessageBox(PregScreening.this, "Required field: আপনার কি কোন সিজারিয়ান বিভাগের ডেলিভারি আছে ?.");
             spnCesaDel.requestFocus(); 
             return;	
           }
         else if(txtCesaDelNo.getText().toString().length()==0 & secCesaDelNo.isShown())
           {
             Connection.MessageBox(PregScreening.this, "Required field: কতগুলো.");
             txtCesaDelNo.requestFocus(); 
             return;	
           }
         else if(Integer.valueOf(txtCesaDelNo.getText().toString().length()==0 ? "1" : txtCesaDelNo.getText().toString()) < 1 || Integer.valueOf(txtCesaDelNo.getText().toString().length()==0 ? "5" : txtCesaDelNo.getText().toString()) > 5)
           {
             Connection.MessageBox(PregScreening.this, "Value should be between 1 and 5(কতগুলো).");
             txtCesaDelNo.requestFocus(); 
             return;	
           }
         else if(spnObtEstiDelDate.getSelectedItemPosition()==0  & secObtEstiDelDate.isShown())
           {
             Connection.MessageBox(PregScreening.this, "Required field: কিভাবে সম্ভাব্য প্রসবের তারিখ পাওয়া গিয়াছে.");
             spnObtEstiDelDate.requestFocus(); 
             return;	
           }
         DV = Global.DateValidate(dtpLMPDate.getText().toString());
         if(DV.length()!=0 & secLMPDate.isShown())
           {
             Connection.MessageBox(PregScreening.this, DV);
             dtpLMPDate.requestFocus(); 
             return;	
           }
         
         else if(!rdoUltraTrime1.isChecked() & !rdoUltraTrime2.isChecked() & !rdoUltraTrime3.isChecked() & !rdoUltraTrime4.isChecked() & secUltraTrime.isShown())
           {
              Connection.MessageBox(PregScreening.this, "Select anyone options from (আলট্রাসাউন্ড দ্বারা: কোন ত্রৈমাসিকে প্রথম আলট্রাসাউন্ড করা হয়).");
              rdoUltraTrime1.requestFocus();
              return;
           }
         else if(txtOthSpec.getText().toString().length()==0 & secOthSpec.isShown())
           {
             Connection.MessageBox(PregScreening.this, "Required field: অন্যান্য নির্দিষ্ট করুন.");
             txtOthSpec.requestFocus(); 
             return;	
           }
 
         String SQL = "";
         RadioButton rb;

         PregScreening_DataModel objSave = new PregScreening_DataModel();
         objSave.setVill(txtVill.getText().toString());
         objSave.setBari(txtBari.getText().toString());
         objSave.setHH(txtHH.getText().toString());
         objSave.setMSlNo(txtMSlNo.getText().toString());
//         objSave.setMSlNo(spnMSlNo.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnMSlNo.getSelectedItem().toString(), "-"));
         objSave.setPNo(PNo);
         objSave.setEvType(EventType);
         objSave.setEvDate(EventDate);
//         objSave.setEvDate(dtpEvDate.getText().toString().length() > 0 ? Global.DateConvertYMD(dtpEvDate.getText().toString()) : dtpEvDate.getText().toString());
         objSave.setPregnancyID(txtPregnancyID.getText().toString());
         objSave.setRnd(RND);
         objSave.setPhoneNo(txtPhoneNo.getText().toString());
         String[] d_rdogrpInfoSource = new String[] {"1","2"};
         objSave.setInfoSource("");
         for (int i = 0; i < rdogrpInfoSource.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpInfoSource.getChildAt(i);
             if (rb.isChecked()) objSave.setInfoSource(d_rdogrpInfoSource[i]);
         }

         objSave.setPregNotiDate(dtpPregNotiDate.getText().toString().length() > 0 ? Global.DateConvertYMD(dtpPregNotiDate.getText().toString()) : dtpPregNotiDate.getText().toString());
         objSave.setPregConCriteria((spnPregConCriteria.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnPregConCriteria.getSelectedItem().toString(), "-")));
         String[] d_rdogrpEligible = new String[] {"1","2"};
         objSave.setEligible("");
         for (int i = 0; i < rdogrpEligible.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpEligible.getChildAt(i);
             if (rb.isChecked()) objSave.setEligible(d_rdogrpEligible[i]);
         }

         objSave.setEligibleDate(dtpEligibleDate.getText().toString().length() > 0 ? Global.DateConvertYMD(dtpEligibleDate.getText().toString()) : dtpEligibleDate.getText().toString());
         objSave.setEnrollDate(dtpEnrollDate.getText().toString().length() > 0 ? Global.DateConvertYMD(dtpEnrollDate.getText().toString()) : dtpEnrollDate.getText().toString());
         String[] d_rdogrpPrevPregHis = new String[] {"1","2"};
         objSave.setPrevPregHis("");
         for (int i = 0; i < rdogrpPrevPregHis.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpPrevPregHis.getChildAt(i);
             if (rb.isChecked()) objSave.setPrevPregHis(d_rdogrpPrevPregHis[i]);
         }

         objSave.setStillBirth((spnStillBirth.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnStillBirth.getSelectedItem().toString(), "-")));
         objSave.setStillBirthNo(txtStillBirthNo.getText().toString());
         objSave.setMiscAbor((spnMiscAbor.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnMiscAbor.getSelectedItem().toString(), "-")));
         objSave.setMiscAborNo(txtMiscAborNo.getText().toString());
         String[] d_rdogrpLastPregresult = new String[] {"1","2"};
         objSave.setLastPregresult("");
         for (int i = 0; i < rdogrpLastPregresult.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpLastPregresult.getChildAt(i);
             if (rb.isChecked()) objSave.setLastPregresult(d_rdogrpLastPregresult[i]);
         }

         objSave.setDelDate(dtpDelDate.getText().toString().length() > 0 ? Global.DateConvertYMD(dtpDelDate.getText().toString()) : dtpDelDate.getText().toString());
         objSave.setCesaDel((spnCesaDel.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnCesaDel.getSelectedItem().toString(), "-")));
         objSave.setCesaDelNo(txtCesaDelNo.getText().toString());
         objSave.setObtEstiDelDate((spnObtEstiDelDate.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnObtEstiDelDate.getSelectedItem().toString(), "-")));
         objSave.setUnreliLMP((chkUnreliLMP.isChecked()?"1":(secLMPDate.isShown()?"2":"")));
         objSave.setLMPDate(dtpLMPDate.getText().toString().length() > 0 ? Global.DateConvertYMD(dtpLMPDate.getText().toString()) : dtpLMPDate.getText().toString());
         String[] d_rdogrpUltraTrime = new String[] {"1","2","3","4"};
         objSave.setUltraTrime("");
         for (int i = 0; i < rdogrpUltraTrime.getChildCount(); i++)
         {
             rb = (RadioButton)rdogrpUltraTrime.getChildAt(i);
             if (rb.isChecked()) objSave.setUltraTrime(d_rdogrpUltraTrime[i]);
         }

         objSave.setOthSpec(txtOthSpec.getText().toString());
         objSave.setEnDt(Global.DateTimeNowYMDHMS());
         objSave.setStartTime(STARTTIME);
         objSave.setEndTime(g.CurrentTime24());
         objSave.setDeviceID(DEVICEID);
         objSave.setEntryUser(ENTRYUSER); //from data entry user list
         objSave.setmodifyDate(Global.DateTimeNowYMDHMS());
         //objSave.setLat(Double.toString(currentLatitude));
         //objSave.setLon(Double.toString(currentLongitude));

         String status = objSave.SaveUpdateData(this);
         if(status.length()==0) {
             Intent returnIntent = new Intent();
             returnIntent.putExtra("res", "");
             setResult(Activity.RESULT_OK, returnIntent);

             Connection.MessageBox(PregScreening.this, "Saved Successfully");
             finish();
         }
         else{
             Connection.MessageBox(PregScreening.this, status);
             return;
         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(PregScreening.this, e.getMessage());
         return;
     }
 }

// private void DataSearch(String PregnancyID)
     private void DataSearch(String Vill, String Bari, String HH, String MSlNo, String EvType, String EvDate, String Rnd)
     {
       try
        {
     
           RadioButton rb;
           PregScreening_DataModel d = new PregScreening_DataModel();
           String SQL = "Select * from "+ TableName +"  Where PregnancyID='"+ PregnancyID +"'";
//            String SQL = "Select * from "+ TableName +"  Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"' and MSlNo='"+ MSlNo +"' and EvType='"+ EvType +"' and EvDate='"+ EvDate +"' and Rnd='"+ Rnd +"'";
           List<PregScreening_DataModel> data = d.SelectAll(this, SQL);
           for(PregScreening_DataModel item : data){
//             txtVill.setText(item.getVill());
//             txtBari.setText(item.getBari());
//             txtHH.setText(item.getHH());
//             spnMSlNo.setSelection(Global.SpinnerItemPositionAnyLength(spnMSlNo, item.getStillBirth()));
             txtPNo.setText(item.getPNo());
//             txtEvType.setText(item.getEvType());
//             dtpEvDate.setText(item.getEvDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getEvDate()));
             txtPregnancyID.setText(item.getPregnancyID());
//             txtRnd.setText(item.getRnd());
             txtPhoneNo.setText(item.getPhoneNo());
             String[] d_rdogrpInfoSource = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpInfoSource.length; i++)
             {
                 if (item.getInfoSource().equals(String.valueOf(d_rdogrpInfoSource[i])))
                 {
                     rb = (RadioButton)rdogrpInfoSource.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             dtpPregNotiDate.setText(item.getPregNotiDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getPregNotiDate()));
             spnPregConCriteria.setSelection(Global.SpinnerItemPositionAnyLength(spnPregConCriteria, item.getPregConCriteria()));
             String[] d_rdogrpEligible = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpEligible.length; i++)
             {
                 if (item.getEligible().equals(String.valueOf(d_rdogrpEligible[i])))
                 {
                     rb = (RadioButton)rdogrpEligible.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             dtpEligibleDate.setText(item.getEligibleDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getEligibleDate()));
             dtpEnrollDate.setText(item.getEnrollDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getEnrollDate()));
             String[] d_rdogrpPrevPregHis = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpPrevPregHis.length; i++)
             {
                 if (item.getPrevPregHis().equals(String.valueOf(d_rdogrpPrevPregHis[i])))
                 {
                     rb = (RadioButton)rdogrpPrevPregHis.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             spnStillBirth.setSelection(Global.SpinnerItemPositionAnyLength(spnStillBirth, item.getStillBirth()));
             txtStillBirthNo.setText(item.getStillBirthNo());
             spnMiscAbor.setSelection(Global.SpinnerItemPositionAnyLength(spnMiscAbor, item.getMiscAbor()));
             txtMiscAborNo.setText(item.getMiscAborNo());
             String[] d_rdogrpLastPregresult = new String[] {"1","2"};
             for (int i = 0; i < d_rdogrpLastPregresult.length; i++)
             {
                 if (item.getLastPregresult().equals(String.valueOf(d_rdogrpLastPregresult[i])))
                 {
                     rb = (RadioButton)rdogrpLastPregresult.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             dtpDelDate.setText(item.getDelDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getDelDate()));
             spnCesaDel.setSelection(Global.SpinnerItemPositionAnyLength(spnCesaDel, item.getCesaDel()));
             txtCesaDelNo.setText(item.getCesaDelNo());
             spnObtEstiDelDate.setSelection(Global.SpinnerItemPositionAnyLength(spnObtEstiDelDate, item.getObtEstiDelDate()));
             if(item.getUnreliLMP().equals("1"))
             {
                chkUnreliLMP.setChecked(true);
             }
             else if(item.getUnreliLMP().equals("2"))
             {
                chkUnreliLMP.setChecked(false);
             }
             dtpLMPDate.setText(item.getLMPDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getLMPDate()));
             String[] d_rdogrpUltraTrime = new String[] {"1","2","3","4"};
             for (int i = 0; i < d_rdogrpUltraTrime.length; i++)
             {
                 if (item.getUltraTrime().equals(String.valueOf(d_rdogrpUltraTrime[i])))
                 {
                     rb = (RadioButton)rdogrpUltraTrime.getChildAt(i);
                     rb.setChecked(true);
                 }
             }
             txtOthSpec.setText(item.getOthSpec());
           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(PregScreening.this, e.getMessage());
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
             else if (VariableID.equals("btnPregNotiDate"))
              {
                  dtpDate = (EditText)findViewById(R.id.dtpPregNotiDate);
              }
             else if (VariableID.equals("btnEligibleDate"))
              {
                  dtpDate = (EditText)findViewById(R.id.dtpEligibleDate);
              }
             else if (VariableID.equals("btnEnrollDate"))
              {
                  dtpDate = (EditText)findViewById(R.id.dtpEnrollDate);
              }
             else if (VariableID.equals("btnDelDate"))
              {
                  dtpDate = (EditText)findViewById(R.id.dtpDelDate);
              }
             else if (VariableID.equals("btnEstiDelDate"))
              {
                  dtpDate = (EditText)findViewById(R.id.dtpLMPDate);
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


//          tpTime.setText(new StringBuilder().append(Global.Right("00"+hour,2)).append(":").append(Global.Right("00"+minute,2)));

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
//  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
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