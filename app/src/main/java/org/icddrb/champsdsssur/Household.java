
 package org.icddrb.champsdsssur;


 //Android Manifest Code
 //<activity android:name=".Household" android:label="Household" />
 import java.util.ArrayList;
 import java.util.Calendar;
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
 import android.location.Location;
 import android.location.LocationListener;
 import android.location.LocationManager;
 import android.net.Uri;
 import android.provider.Settings;
 import android.view.KeyEvent;
 import android.os.Bundle;
 import android.view.View;
 import android.view.MotionEvent;
 import android.widget.Button;
 import android.widget.DatePicker;
 import android.widget.EditText;
 import android.widget.ImageButton;
 import android.widget.LinearLayout;
 import android.widget.RadioButton;
 import android.widget.SimpleAdapter;
 import android.widget.Spinner;
 import android.widget.TextView;
 import android.widget.TimePicker;
 import android.widget.ArrayAdapter;

 import Common.*;

 public class Household extends Activity {
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
         LinearLayout secTotMem;
         View lineTotMem;
         TextView VlblTotMem;
         EditText txtTotMem;
         LinearLayout secTotRWo;
         View lineTotRWo;
         TextView VlblTotRWo;
         EditText txtTotRWo;
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
         LinearLayout secRnd;
         View lineRnd;
         TextView VlblRnd;
         EditText txtRnd;

    static String TableName;

    static String STARTTIME = "";
    static String DEVICEID  = "";
    static String ENTRYUSER = "";

    Bundle IDbundle;
    static String VILL = "";
    static String BARI = "";
    static String HH = "";

 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.household);
         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = g.getDeviceNo();
         ENTRYUSER = g.getUserId();

         IDbundle = getIntent().getExtras();
         VILL = IDbundle.getString("Vill");
         BARI = IDbundle.getString("Bari");
         HH = IDbundle.getString("HH");

         TableName = "Household";

         //turnGPSOn();

         //GPS Location
         //FindLocation();
         // Double.toString(currentLatitude);
         // Double.toString(currentLongitude);
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(Household.this);
                 adb.setTitle("Close");
                 adb.setMessage("Do you want to close this form[Yes/No]?");
                 adb.setNegativeButton("No", null);
                 adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         finish();
                     }});
                 adb.show();
             }});


         seclbl02=(LinearLayout)findViewById(R.id.seclbl02);
         linelbl02=(View)findViewById(R.id.linelbl02);
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
         secReligion=(LinearLayout)findViewById(R.id.secReligion);
         lineReligion=(View)findViewById(R.id.lineReligion);
         VlblReligion=(TextView) findViewById(R.id.VlblReligion);
         spnReligion=(Spinner) findViewById(R.id.spnReligion);
         List<String> listReligion = new ArrayList<String>();
         
         listReligion.add("");
         listReligion.add("1-মুসলিম");
         listReligion.add("2-হিন্দু");
         listReligion.add("3-খ্রীষ্ট");
         listReligion.add("4-বুদ্ধ");
         listReligion.add("5-অন্যান্য");
         ArrayAdapter<String> adptrReligion= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listReligion);
         spnReligion.setAdapter(adptrReligion);

         secMobileNo1=(LinearLayout)findViewById(R.id.secMobileNo1);
         lineMobileNo1=(View)findViewById(R.id.lineMobileNo1);
         VlblMobileNo1=(TextView) findViewById(R.id.VlblMobileNo1);
         txtMobileNo1=(EditText) findViewById(R.id.txtMobileNo1);
         secMobileNo2=(LinearLayout)findViewById(R.id.secMobileNo2);
         lineMobileNo2=(View)findViewById(R.id.lineMobileNo2);
         VlblMobileNo2=(TextView) findViewById(R.id.VlblMobileNo2);
         txtMobileNo2=(EditText) findViewById(R.id.txtMobileNo2);
         secHHHead=(LinearLayout)findViewById(R.id.secHHHead);
         lineHHHead=(View)findViewById(R.id.lineHHHead);
         VlblHHHead=(TextView) findViewById(R.id.VlblHHHead);
         txtHHHead=(EditText) findViewById(R.id.txtHHHead);
         secTotMem=(LinearLayout)findViewById(R.id.secTotMem);
         lineTotMem=(View)findViewById(R.id.lineTotMem);
         VlblTotMem=(TextView) findViewById(R.id.VlblTotMem);
         txtTotMem=(EditText) findViewById(R.id.txtTotMem);
         secTotRWo=(LinearLayout)findViewById(R.id.secTotRWo);
        // lineTotRWo=(View)findViewById(R.id.lineTotRWo);
         VlblTotRWo=(TextView) findViewById(R.id.VlblTotRWo);
         txtTotRWo=(EditText) findViewById(R.id.txtTotRWo);
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
         secRnd=(LinearLayout)findViewById(R.id.secRnd);
         lineRnd=(View)findViewById(R.id.lineRnd);
         VlblRnd=(TextView) findViewById(R.id.VlblRnd);
         txtRnd=(EditText) findViewById(R.id.txtRnd);


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
         Connection.MessageBox(Household.this, e.getMessage());
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
             Connection.MessageBox(Household.this, "Required field: গ্রাম.");
             txtVill.requestFocus(); 
             return;	
           }
         else if(txtBari.getText().toString().length()==0 & secBari.isShown())
           {
             Connection.MessageBox(Household.this, "Required field: বাড়ি.");
             txtBari.requestFocus(); 
             return;	
           }
         else if(txtHH.getText().toString().length()==0 & secHH.isShown())
           {
             Connection.MessageBox(Household.this, "Required field: খানা.");
             txtHH.requestFocus(); 
             return;	
           }
         else if(spnReligion.getSelectedItemPosition()==0  & secReligion.isShown())
           {
             Connection.MessageBox(Household.this, "Required field: ধর্ম.");
             spnReligion.requestFocus(); 
             return;	
           }
         else if(txtMobileNo1.getText().toString().length()==0 & secMobileNo1.isShown())
           {
             Connection.MessageBox(Household.this, "Required field: ১ম মোবাইল নম্বর.");
             txtMobileNo1.requestFocus(); 
             return;	
           }
         else if(txtMobileNo2.getText().toString().length()==0 & secMobileNo2.isShown())
           {
             Connection.MessageBox(Household.this, "Required field: ২য় মোবাইল নম্বর.");
             txtMobileNo2.requestFocus(); 
             return;	
           }
         else if(txtHHHead.getText().toString().length()==0 & secHHHead.isShown())
           {
             Connection.MessageBox(Household.this, "Required field: খানা প্রধানের নাম.");
             txtHHHead.requestFocus(); 
             return;	
           }
         else if(txtTotMem.getText().toString().length()==0 & secTotMem.isShown())
           {
             Connection.MessageBox(Household.this, "Required field: মোট সদস্য সংখ্যা.");
             txtTotMem.requestFocus(); 
             return;	
           }
         else if(Integer.valueOf(txtTotMem.getText().toString().length()==0 ? "01" : txtTotMem.getText().toString()) < 01 || Integer.valueOf(txtTotMem.getText().toString().length()==0 ? "30" : txtTotMem.getText().toString()) > 30)
           {
             Connection.MessageBox(Household.this, "Value should be between 01 and 30(মোট সদস্য সংখ্যা).");
             txtTotMem.requestFocus(); 
             return;	
           }
         else if(txtTotRWo.getText().toString().length()==0 & secTotRWo.isShown())
           {
             Connection.MessageBox(Household.this, "Required field: মোট মহিলা.");
             txtTotRWo.requestFocus(); 
             return;	
           }
         else if(Integer.valueOf(txtTotRWo.getText().toString().length()==0 ? "1" : txtTotRWo.getText().toString()) < 1 || Integer.valueOf(txtTotRWo.getText().toString().length()==0 ? "10" : txtTotRWo.getText().toString()) > 10)
           {
             Connection.MessageBox(Household.this, "Value should be between 1 and 10(মোট মহিলা).");
             txtTotRWo.requestFocus(); 
             return;	
           }
         else if(txtEnType.getText().toString().length()==0 & secEnType.isShown())
           {
             Connection.MessageBox(Household.this, "Required field: তালিকাভুক্তির ধরন.");
             txtEnType.requestFocus(); 
             return;	
           }
         else if(Integer.valueOf(txtEnType.getText().toString().length()==0 ? "20" : txtEnType.getText().toString()) < 20 || Integer.valueOf(txtEnType.getText().toString().length()==0 ? "25" : txtEnType.getText().toString()) > 25)
           {
             Connection.MessageBox(Household.this, "Value should be between 20 and 25(তালিকাভুক্তির ধরন).");
             txtEnType.requestFocus(); 
             return;	
           }
         DV = Global.DateValidate(dtpEnDate.getText().toString());
         if(DV.length()!=0 & secEnDate.isShown())
           {
             Connection.MessageBox(Household.this, DV);
             dtpEnDate.requestFocus(); 
             return;	
           }
         else if(txtExType.getText().toString().length()==0 & secExType.isShown())
           {
             Connection.MessageBox(Household.this, "Required field: ExType.");
             txtExType.requestFocus(); 
             return;	
           }
         else if(Integer.valueOf(txtExType.getText().toString().length()==0 ? "51" : txtExType.getText().toString()) < 51 || Integer.valueOf(txtExType.getText().toString().length()==0 ? "56" : txtExType.getText().toString()) > 56)
           {
             Connection.MessageBox(Household.this, "Value should be between 51 and 56(ExType).");
             txtExType.requestFocus(); 
             return;	
           }
         DV = Global.DateValidate(dtpExDate.getText().toString());
         if(DV.length()!=0 & secExDate.isShown())
           {
             Connection.MessageBox(Household.this, DV);
             dtpExDate.requestFocus(); 
             return;	
           }
         else if(txtRnd.getText().toString().length()==0 & secRnd.isShown())
           {
             Connection.MessageBox(Household.this, "Required field: রাউন্ড.");
             txtRnd.requestFocus(); 
             return;	
           }
         else if(Integer.valueOf(txtRnd.getText().toString().length()==0 ? "1" : txtRnd.getText().toString()) < 1 || Integer.valueOf(txtRnd.getText().toString().length()==0 ? "99" : txtRnd.getText().toString()) > 99)
           {
             Connection.MessageBox(Household.this, "Value should be between 1 and 99(রাউন্ড).");
             txtRnd.requestFocus(); 
             return;	
           }
 
         String SQL = "";
         RadioButton rb;

         Household_DataModel objSave = new Household_DataModel();
         objSave.setVill(txtVill.getText().toString());
         objSave.setBari(txtBari.getText().toString());
         objSave.setHH(txtHH.getText().toString());
         objSave.setReligion((spnReligion.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnReligion.getSelectedItem().toString(), "-")));
         objSave.setMobileNo1(txtMobileNo1.getText().toString());
         objSave.setMobileNo2(txtMobileNo2.getText().toString());
         objSave.setHHHead(txtHHHead.getText().toString());
         objSave.setTotMem(txtTotMem.getText().toString());
         objSave.setTotRWo(txtTotRWo.getText().toString());
         objSave.setEnType(txtEnType.getText().toString());
         objSave.setEnDate(dtpEnDate.getText().toString().length() > 0 ? Global.DateConvertYMD(dtpEnDate.getText().toString()) : dtpEnDate.getText().toString());
         objSave.setExType(txtExType.getText().toString());
         objSave.setExDate(dtpExDate.getText().toString().length() > 0 ? Global.DateConvertYMD(dtpExDate.getText().toString()) : dtpExDate.getText().toString());
         objSave.setRnd(txtRnd.getText().toString());
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

             Connection.MessageBox(Household.this, "Saved Successfully");
         }
         else{
             Connection.MessageBox(Household.this, status);
             return;
         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Household.this, e.getMessage());
         return;
     }
 }

 private void DataSearch(String Vill, String Bari, String HH)
     {
       try
        {
           RadioButton rb;
           Household_DataModel d = new Household_DataModel();
           String SQL = "Select * from "+ TableName +"  Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"'";
           List<Household_DataModel> data = d.SelectAll(this, SQL);
           for(Household_DataModel item : data){
             txtVill.setText(item.getVill());
             txtBari.setText(item.getBari());
             txtHH.setText(item.getHH());
             spnReligion.setSelection(Global.SpinnerItemPositionAnyLength(spnReligion, item.getReligion()));
             txtMobileNo1.setText(item.getMobileNo1());
             txtMobileNo2.setText(item.getMobileNo2());
             txtHHHead.setText(item.getHHHead());
             txtTotMem.setText(item.getTotMem());
             txtTotRWo.setText(item.getTotRWo());
             txtEnType.setText(item.getEnType());
             dtpEnDate.setText(item.getEnDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getEnDate()));
             txtExType.setText(item.getExType());
             dtpExDate.setText(item.getExDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getExDate()));
             txtRnd.setText(item.getRnd());
           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(Household.this, e.getMessage());
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


              dtpDate = (EditText)findViewById(R.id.dtpEnDate);
             if (VariableID.equals("btnEnDate"))
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