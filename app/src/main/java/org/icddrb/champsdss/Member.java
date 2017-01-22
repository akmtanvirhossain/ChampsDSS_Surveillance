
 package org.icddrb.champsdss;


 //Android Manifest Code
 //<activity android:name=".Member" android:label="Member" />
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
 import Common.*;

 public class Member extends Activity {
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
         LinearLayout seclbl1;
         View linelbl1;
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
         EditText txtMoNo;
         LinearLayout secFaNo;
         View lineFaNo;
         TextView VlblFaNo;
         EditText txtFaNo;
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
         EditText txtSp1;
         LinearLayout secSp2;
         View lineSp2;
         TextView VlblSp2;
         EditText txtSp2;
         LinearLayout secSp3;
         View lineSp3;
         TextView VlblSp3;
         EditText txtSp3;
         LinearLayout secSp4;
         View lineSp4;
         TextView VlblSp4;
         EditText txtSp4;
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

    static String TableName;

    static String STARTTIME = "";
    static String DEVICEID  = "";
    static String ENTRYUSER = "";

    Bundle IDbundle;
    static String VILL = "";
    static String BARI = "";
    static String HH = "";
    static String MSLNO = "";

 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.member);
         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = g.getDeviceNo();
         ENTRYUSER = g.getUserId();

         IDbundle = getIntent().getExtras();
         VILL = IDbundle.getString("Vill");
         BARI = IDbundle.getString("Bari");
         HH = IDbundle.getString("HH");
         MSLNO = IDbundle.getString("MSlNo");

         TableName = "Member";

         //turnGPSOn();

         //GPS Location
         //FindLocation();
         // Double.toString(currentLatitude);
         // Double.toString(currentLongitude);
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(Member.this);
                 adb.setTitle("Close");
                 adb.setMessage("Do you want to close this form[Yes/No]?");
                 adb.setNegativeButton("No", null);
                 adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         finish();
                     }});
                 adb.show();
             }});


         seclbl1=(LinearLayout)findViewById(R.id.seclbl1);
         linelbl1=(View)findViewById(R.id.linelbl1);
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
         secPNo=(LinearLayout)findViewById(R.id.secPNo);
         linePNo=(View)findViewById(R.id.linePNo);
         VlblPNo=(TextView) findViewById(R.id.VlblPNo);
         txtPNo=(EditText) findViewById(R.id.txtPNo);
         secName=(LinearLayout)findViewById(R.id.secName);
         lineName=(View)findViewById(R.id.lineName);
         VlblName=(TextView) findViewById(R.id.VlblName);
         txtName=(EditText) findViewById(R.id.txtName);
         secRth=(LinearLayout)findViewById(R.id.secRth);
         lineRth=(View)findViewById(R.id.lineRth);
         VlblRth=(TextView) findViewById(R.id.VlblRth);
         spnRth=(Spinner) findViewById(R.id.spnRth);
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
         listRth.add("08-খানা প্রধানের নাতি/নাতটি");
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
         secAgeY=(LinearLayout)findViewById(R.id.secAgeY);
         lineAgeY=(View)findViewById(R.id.lineAgeY);
         VlblAgeY=(TextView) findViewById(R.id.VlblAgeY);
         txtAgeY=(EditText) findViewById(R.id.txtAgeY);
         secMoNo=(LinearLayout)findViewById(R.id.secMoNo);
         lineMoNo=(View)findViewById(R.id.lineMoNo);
         VlblMoNo=(TextView) findViewById(R.id.VlblMoNo);
         txtMoNo=(EditText) findViewById(R.id.txtMoNo);
         secFaNo=(LinearLayout)findViewById(R.id.secFaNo);
         lineFaNo=(View)findViewById(R.id.lineFaNo);
         VlblFaNo=(TextView) findViewById(R.id.VlblFaNo);
         txtFaNo=(EditText) findViewById(R.id.txtFaNo);
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
         listMS.add("31-বর্তমানে বিবাহিতা");
         listMS.add("32-তালাকপ্রাপ্তা (আইনগত ভাবে তালাকপ্রাপ্তা)");
         listMS.add("33-বিধবা (স্বামী মৃত-বর্তমানে কোন বৈবাহিক সম্পর্ক নাই)");
         listMS.add("34-বিচ্ছিন্না");
         ArrayAdapter<String> adptrMS= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listMS);
         spnMS.setAdapter(adptrMS);

         secOcp=(LinearLayout)findViewById(R.id.secOcp);
         lineOcp=(View)findViewById(R.id.lineOcp);
         VlblOcp=(TextView) findViewById(R.id.VlblOcp);
         spnOcp=(Spinner) findViewById(R.id.spnOcp);
         List<String> listOcp = new ArrayList<String>();
         
         listOcp.add("");
         listOcp.add("01-বেকার");
         listOcp.add("02-ছাত্র (চাকুরী করে না)");
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
         ArrayAdapter<String> adptrOcp= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listOcp);
         spnOcp.setAdapter(adptrOcp);

         seclblSpsl=(LinearLayout)findViewById(R.id.seclblSpsl);
         linelblSpsl=(View)findViewById(R.id.linelblSpsl);
         secSp1=(LinearLayout)findViewById(R.id.secSp1);
         lineSp1=(View)findViewById(R.id.lineSp1);
         VlblSp1=(TextView) findViewById(R.id.VlblSp1);
         txtSp1=(EditText) findViewById(R.id.txtSp1);
         secSp2=(LinearLayout)findViewById(R.id.secSp2);
         lineSp2=(View)findViewById(R.id.lineSp2);
         VlblSp2=(TextView) findViewById(R.id.VlblSp2);
         txtSp2=(EditText) findViewById(R.id.txtSp2);
         secSp3=(LinearLayout)findViewById(R.id.secSp3);
         lineSp3=(View)findViewById(R.id.lineSp3);
         VlblSp3=(TextView) findViewById(R.id.VlblSp3);
         txtSp3=(EditText) findViewById(R.id.txtSp3);
         secSp4=(LinearLayout)findViewById(R.id.secSp4);
         lineSp4=(View)findViewById(R.id.lineSp4);
         VlblSp4=(TextView) findViewById(R.id.VlblSp4);
         txtSp4=(EditText) findViewById(R.id.txtSp4);
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



         //Hide all skip variables


        Button cmdSave = (Button) findViewById(R.id.cmdSave);
        cmdSave.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) { 
            DataSave();
        }});
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Member.this, e.getMessage());
         return;
     }
 }

 private void DataSave()
 {
   try
     {
 
         String DV="";

         if(txtVill.getText().toString().length()==0 & secVill.isShown())
           {
             Connection.MessageBox(Member.this, "Required field: গ্রাম.");
             txtVill.requestFocus(); 
             return;	
           }
         else if(txtBari.getText().toString().length()==0 & secBari.isShown())
           {
             Connection.MessageBox(Member.this, "Required field: বাড়ি.");
             txtBari.requestFocus(); 
             return;	
           }
         else if(txtHH.getText().toString().length()==0 & secHH.isShown())
           {
             Connection.MessageBox(Member.this, "Required field: খানা.");
             txtHH.requestFocus(); 
             return;	
           }
         else if(txtMSlNo.getText().toString().length()==0 & secMSlNo.isShown())
           {
             Connection.MessageBox(Member.this, "Required field: সিরিয়াল নং.");
             txtMSlNo.requestFocus(); 
             return;	
           }
         else if(Integer.valueOf(txtMSlNo.getText().toString().length()==0 ? "1" : txtMSlNo.getText().toString()) < 1 || Integer.valueOf(txtMSlNo.getText().toString().length()==0 ? "30" : txtMSlNo.getText().toString()) > 30)
           {
             Connection.MessageBox(Member.this, "Value should be between 1 and 30(সিরিয়াল নং).");
             txtMSlNo.requestFocus(); 
             return;	
           }
         else if(txtPNo.getText().toString().length()==0 & secPNo.isShown())
           {
             Connection.MessageBox(Member.this, "Required field: PNo.");
             txtPNo.requestFocus(); 
             return;	
           }
         else if(txtName.getText().toString().length()==0 & secName.isShown())
           {
             Connection.MessageBox(Member.this, "Required field: খানার সদস্যদের নাম (খানা প্রধানের নাম দিয়ে শুরু করুন).");
             txtName.requestFocus(); 
             return;	
           }
         else if(spnRth.getSelectedItemPosition()==0  & secRth.isShown())
           {
             Connection.MessageBox(Member.this, "Required field: খানা প্রধানের সাথে সম্পর্ক.");
             spnRth.requestFocus(); 
             return;	
           }
         
         else if(!rdoSex1.isChecked() & !rdoSex2.isChecked() & secSex.isShown())
           {
              Connection.MessageBox(Member.this, "Select anyone options from (লিঙ্গ).");
              rdoSex1.requestFocus();
              return;
           }
         DV = Global.DateValidate(dtpBDate.getText().toString());
         if(DV.length()!=0 & secBDate.isShown())
           {
             Connection.MessageBox(Member.this, DV);
             dtpBDate.requestFocus(); 
             return;	
           }
         else if(txtAgeY.getText().toString().length()==0 & secAgeY.isShown())
           {
             Connection.MessageBox(Member.this, "Required field: বয়স (পূর্ণ বছরে).");
             txtAgeY.requestFocus(); 
             return;	
           }
         else if(Integer.valueOf(txtAgeY.getText().toString().length()==0 ? "0" : txtAgeY.getText().toString()) < 0 || Integer.valueOf(txtAgeY.getText().toString().length()==0 ? "110" : txtAgeY.getText().toString()) > 110)
           {
             Connection.MessageBox(Member.this, "Value should be between 0 and 110(বয়স (পূর্ণ বছরে)).");
             txtAgeY.requestFocus(); 
             return;	
           }
         else if(txtMoNo.getText().toString().length()==0 & secMoNo.isShown())
           {
             Connection.MessageBox(Member.this, "Required field: মায়ের সিরিয়াল নম্বর.");
             txtMoNo.requestFocus(); 
             return;	
           }
         else if(Integer.valueOf(txtMoNo.getText().toString().length()==0 ? "0" : txtMoNo.getText().toString()) < 0 || Integer.valueOf(txtMoNo.getText().toString().length()==0 ? "99" : txtMoNo.getText().toString()) > 99)
           {
             Connection.MessageBox(Member.this, "Value should be between 0 and 99(মায়ের সিরিয়াল নম্বর).");
             txtMoNo.requestFocus(); 
             return;	
           }
         else if(txtFaNo.getText().toString().length()==0 & secFaNo.isShown())
           {
             Connection.MessageBox(Member.this, "Required field: বাবার সিরিয়াল নম্বর.");
             txtFaNo.requestFocus(); 
             return;	
           }
         else if(Integer.valueOf(txtFaNo.getText().toString().length()==0 ? "0" : txtFaNo.getText().toString()) < 0 || Integer.valueOf(txtFaNo.getText().toString().length()==0 ? "99" : txtFaNo.getText().toString()) > 99)
           {
             Connection.MessageBox(Member.this, "Value should be between 0 and 99(বাবার সিরিয়াল নম্বর).");
             txtFaNo.requestFocus(); 
             return;	
           }
         else if(spnEdu.getSelectedItemPosition()==0  & secEdu.isShown())
           {
             Connection.MessageBox(Member.this, "Required field: সর্বোচ্চ শ্রেণি পাশ.");
             spnEdu.requestFocus(); 
             return;	
           }
         else if(spnMS.getSelectedItemPosition()==0  & secMS.isShown())
           {
             Connection.MessageBox(Member.this, "Required field: বৈবাহিক অবস্থা.");
             spnMS.requestFocus(); 
             return;	
           }
         else if(spnOcp.getSelectedItemPosition()==0  & secOcp.isShown())
           {
             Connection.MessageBox(Member.this, "Required field: পেশা.");
             spnOcp.requestFocus(); 
             return;	
           }
         else if(txtSp1.getText().toString().length()==0 & secSp1.isShown())
           {
             Connection.MessageBox(Member.this, "Required field: সিরিয়াল নম্বর ১.");
             txtSp1.requestFocus(); 
             return;	
           }
         else if(Integer.valueOf(txtSp1.getText().toString().length()==0 ? "00" : txtSp1.getText().toString()) < 00 || Integer.valueOf(txtSp1.getText().toString().length()==0 ? "30" : txtSp1.getText().toString()) > 30)
           {
             Connection.MessageBox(Member.this, "Value should be between 00 and 30(সিরিয়াল নম্বর ১).");
             txtSp1.requestFocus(); 
             return;	
           }
         else if(txtSp2.getText().toString().length()==0 & secSp2.isShown())
           {
             Connection.MessageBox(Member.this, "Required field: সিরিয়াল নম্বর ২.");
             txtSp2.requestFocus(); 
             return;	
           }
         else if(Integer.valueOf(txtSp2.getText().toString().length()==0 ? "00" : txtSp2.getText().toString()) < 00 || Integer.valueOf(txtSp2.getText().toString().length()==0 ? "30" : txtSp2.getText().toString()) > 30)
           {
             Connection.MessageBox(Member.this, "Value should be between 00 and 30(সিরিয়াল নম্বর ২).");
             txtSp2.requestFocus(); 
             return;	
           }
         else if(txtSp3.getText().toString().length()==0 & secSp3.isShown())
           {
             Connection.MessageBox(Member.this, "Required field: সিরিয়াল নম্বর ৩.");
             txtSp3.requestFocus(); 
             return;	
           }
         else if(Integer.valueOf(txtSp3.getText().toString().length()==0 ? "00" : txtSp3.getText().toString()) < 00 || Integer.valueOf(txtSp3.getText().toString().length()==0 ? "30" : txtSp3.getText().toString()) > 30)
           {
             Connection.MessageBox(Member.this, "Value should be between 00 and 30(সিরিয়াল নম্বর ৩).");
             txtSp3.requestFocus(); 
             return;	
           }
         else if(txtSp4.getText().toString().length()==0 & secSp4.isShown())
           {
             Connection.MessageBox(Member.this, "Required field: সিরিয়াল নম্বর ৪.");
             txtSp4.requestFocus(); 
             return;	
           }
         else if(Integer.valueOf(txtSp4.getText().toString().length()==0 ? "00" : txtSp4.getText().toString()) < 00 || Integer.valueOf(txtSp4.getText().toString().length()==0 ? "30" : txtSp4.getText().toString()) > 30)
           {
             Connection.MessageBox(Member.this, "Value should be between 00 and 30(সিরিয়াল নম্বর ৪).");
             txtSp4.requestFocus(); 
             return;	
           }
         else if(txtEnType.getText().toString().length()==0 & secEnType.isShown())
           {
             Connection.MessageBox(Member.this, "Required field: EnType.");
             txtEnType.requestFocus(); 
             return;	
           }
         else if(Integer.valueOf(txtEnType.getText().toString().length()==0 ? "20" : txtEnType.getText().toString()) < 20 || Integer.valueOf(txtEnType.getText().toString().length()==0 ? "25" : txtEnType.getText().toString()) > 25)
           {
             Connection.MessageBox(Member.this, "Value should be between 20 and 25(EnType).");
             txtEnType.requestFocus(); 
             return;	
           }
         DV = Global.DateValidate(dtpEnDate.getText().toString());
         if(DV.length()!=0 & secEnDate.isShown())
           {
             Connection.MessageBox(Member.this, DV);
             dtpEnDate.requestFocus(); 
             return;	
           }
         else if(txtExType.getText().toString().length()==0 & secExType.isShown())
           {
             Connection.MessageBox(Member.this, "Required field: ExType.");
             txtExType.requestFocus(); 
             return;	
           }
         else if(Integer.valueOf(txtExType.getText().toString().length()==0 ? "51" : txtExType.getText().toString()) < 51 || Integer.valueOf(txtExType.getText().toString().length()==0 ? "56" : txtExType.getText().toString()) > 56)
           {
             Connection.MessageBox(Member.this, "Value should be between 51 and 56(ExType).");
             txtExType.requestFocus(); 
             return;	
           }
         DV = Global.DateValidate(dtpExDate.getText().toString());
         if(DV.length()!=0 & secExDate.isShown())
           {
             Connection.MessageBox(Member.this, DV);
             dtpExDate.requestFocus(); 
             return;	
           }
 
         String SQL = "";
         RadioButton rb;

         Member_DataModel objSave = new Member_DataModel();
         objSave.setVill(txtVill.getText().toString());
         objSave.setBari(txtBari.getText().toString());
         objSave.setHH(txtHH.getText().toString());
         objSave.setMSlNo(txtMSlNo.getText().toString());
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
         objSave.setMoNo(txtMoNo.getText().toString());
         objSave.setFaNo(txtFaNo.getText().toString());
         objSave.setEdu((spnEdu.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnEdu.getSelectedItem().toString(), "-")));
         objSave.setMS((spnMS.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnMS.getSelectedItem().toString(), "-")));
         objSave.setOcp((spnOcp.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnOcp.getSelectedItem().toString(), "-")));
         objSave.setSp1(txtSp1.getText().toString());
         objSave.setSp2(txtSp2.getText().toString());
         objSave.setSp3(txtSp3.getText().toString());
         objSave.setSp4(txtSp4.getText().toString());
         objSave.setEnType(txtEnType.getText().toString());
         objSave.setEnDate(dtpEnDate.getText().toString().length() > 0 ? Global.DateConvertYMD(dtpEnDate.getText().toString()) : dtpEnDate.getText().toString());
         objSave.setExType(txtExType.getText().toString());
         objSave.setExDate(dtpExDate.getText().toString().length() > 0 ? Global.DateConvertYMD(dtpExDate.getText().toString()) : dtpExDate.getText().toString());
         objSave.setEnDt(Global.DateTimeNowYMDHMS());
         objSave.setStartTime(STARTTIME);
         objSave.setEndTime(g.CurrentTime24());
         objSave.setDeviceID(DEVICEID);
         objSave.setEntryUser(ENTRYUSER); //from data entry user list
         //objSave.setLat(Double.toString(currentLatitude));
         //objSave.setLon(Double.toString(currentLongitude));

         String status = objSave.SaveUpdateData(this);
         if(status.length()==0) {
             Intent returnIntent = new Intent();
             returnIntent.putExtra("res", "");
             setResult(Activity.RESULT_OK, returnIntent);

             Connection.MessageBox(Member.this, "Saved Successfully");
         }
         else{
             Connection.MessageBox(Member.this, status);
             return;
         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Member.this, e.getMessage());
         return;
     }
 }

 private void DataSearch(String Vill, String Bari, String HH, String MSlNo)
     {
       try
        {
     
           RadioButton rb;
           Member_DataModel d = new Member_DataModel();
           String SQL = "Select * from "+ TableName +"  Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"' and MSlNo='"+ MSlNo +"'";
           List<Member_DataModel> data = d.SelectAll(this, SQL);
           for(Member_DataModel item : data){
             txtVill.setText(item.getVill());
             txtBari.setText(item.getBari());
             txtHH.setText(item.getHH());
             txtMSlNo.setText(item.getMSlNo());
             txtPNo.setText(item.getPNo());
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
             txtMoNo.setText(item.getMoNo());
             txtFaNo.setText(item.getFaNo());
             spnEdu.setSelection(Global.SpinnerItemPositionAnyLength(spnEdu, item.getEdu()));
             spnMS.setSelection(Global.SpinnerItemPositionAnyLength(spnMS, item.getMS()));
             spnOcp.setSelection(Global.SpinnerItemPositionAnyLength(spnOcp, item.getOcp()));
             txtSp1.setText(item.getSp1());
             txtSp2.setText(item.getSp2());
             txtSp3.setText(item.getSp3());
             txtSp4.setText(item.getSp4());
             txtEnType.setText(item.getEnType());
             dtpEnDate.setText(item.getEnDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getEnDate()));
             txtExType.setText(item.getExType());
             dtpExDate.setText(item.getExDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getExDate()));
           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(Member.this, e.getMessage());
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


              dtpDate = (EditText)findViewById(R.id.dtpBDate);
             if (VariableID.equals("btnBDate"))
              {
                  dtpDate = (EditText)findViewById(R.id.dtpBDate);
              }
             else if (VariableID.equals("btnEnDate"))
              {
                  dtpDate = (EditText)findViewById(R.id.dtpEnDate);
              }
             else if (VariableID.equals("btnExDate"))
              {
                  dtpDate = (EditText)findViewById(R.id.dtpExDate);
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
  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
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