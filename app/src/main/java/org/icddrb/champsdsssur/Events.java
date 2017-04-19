
 package org.icddrb.champsdsssur;


 //Android Manifest Code
 //<activity android:name=".Events" android:label="Events" />
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
 import android.app.usage.UsageEvents;
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

 public class Events extends Activity {
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
         LinearLayout secInfo2;
         View lineInfo2;
         TextView VlblInfo2;
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
    static String RND = "";
    static String EVTYPE = "";

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

         IDbundle = getIntent().getExtras();
         VILL = IDbundle.getString("Vill");
         BARI = IDbundle.getString("Bari");
         HH = IDbundle.getString("HH");
         MSLNO = IDbundle.getString("MSlNo");
         EVTYPE = IDbundle.getString("EvType");
         EVDATE = IDbundle.getString("EvDate");
         RND = IDbundle.getString("Rnd");

         TableName = "Events";

         //turnGPSOn();

         //GPS Location
         //FindLocation();
         // Double.toString(currentLatitude);
         // Double.toString(currentLongitude);
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(Events.this);
                 adb.setTitle("Close");
                 adb.setMessage("Do you want to close this form[Yes/No]?");
                 adb.setNegativeButton("No", null);
                 adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         finish();
                     }});
                 adb.show();
             }});


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
             txtMSlNo.setText(MemNo(VILL,BARI,HH));
         else
             txtMSlNo.setText(MSLNO);

         txtMSlNo.setEnabled(false);

         secPNo=(LinearLayout)findViewById(R.id.secPNo);
         linePNo=(View)findViewById(R.id.linePNo);
         VlblPNo=(TextView) findViewById(R.id.VlblPNo);
         txtPNo=(EditText) findViewById(R.id.txtPNo);

         secEvType=(LinearLayout)findViewById(R.id.secEvType);
         lineEvType=(View)findViewById(R.id.lineEvType);
         VlblEvType=(TextView) findViewById(R.id.VlblEvType);
         spnEvType=(Spinner) findViewById(R.id.spnEvType);
         List<String> listEvType = new ArrayList<String>();

         listEvType.add("");
         listEvType.add("12-Correction");
         listEvType.add("21-Migration In");
         ArrayAdapter<String> adptrEvType= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listEvType);
         spnEvType.setAdapter(adptrEvType);

         spnEvType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                 String spnData = "";
                 if (spnEvType.getSelectedItem().toString().length() != 0)
                 {
                     spnData = Connection.SelectedSpinnerValue(spnEvType.getSelectedItem().toString(), "-");
                 }
                 if(spnData.equalsIgnoreCase("12"))
                 {
                     secEvDate.setVisibility(View.GONE);
                     lineEvDate.setVisibility(View.GONE);
                     dtpEvDate.setText("");
                     secInfo1.setVisibility(View.GONE);
                     lineInfo1.setVisibility(View.GONE);
                     txtInfo1.setText("");
                     secInfo2.setVisibility(View.GONE);
                     lineInfo2.setVisibility(View.GONE);
                     txtInfo2.setText("");
                     secInfo3.setVisibility(View.GONE);
                     lineInfo3.setVisibility(View.GONE);
                     txtInfo3.setText("");
                     secInfo4.setVisibility(View.GONE);
                     lineInfo4.setVisibility(View.GONE);
                     txtInfo4.setText("");
                     secVDate.setVisibility(View.GONE);
                     lineVDate.setVisibility(View.GONE);
                     dtpVDate.setText("");
                     secRnd.setVisibility(View.GONE);
                     lineRnd.setVisibility(View.GONE);
                     txtRnd.setText("");
                 }
                 else
                 {
                     secEvDate.setVisibility(View.VISIBLE);
                     lineEvDate.setVisibility(View.VISIBLE);
                     secInfo1.setVisibility(View.VISIBLE);
                     lineInfo1.setVisibility(View.VISIBLE);
                     secInfo2.setVisibility(View.VISIBLE);
                     lineInfo2.setVisibility(View.VISIBLE);
                     secInfo3.setVisibility(View.VISIBLE);
                     lineInfo3.setVisibility(View.VISIBLE);
                     secInfo4.setVisibility(View.VISIBLE);
                     lineInfo4.setVisibility(View.VISIBLE);
                     secVDate.setVisibility(View.VISIBLE);
                     lineVDate.setVisibility(View.VISIBLE);
                     secRnd.setVisibility(View.VISIBLE);
                     lineRnd.setVisibility(View.VISIBLE);
                 }
             }
             @Override
             public void onNothingSelected(AdapterView<?> parentView) {
             }
         });
         secEvDate=(LinearLayout)findViewById(R.id.secEvDate);
         lineEvDate=(View)findViewById(R.id.lineEvDate);
         VlblEvDate=(TextView) findViewById(R.id.VlblEvDate);
         dtpEvDate=(EditText) findViewById(R.id.dtpEvDate);
         secInfo1=(LinearLayout)findViewById(R.id.secInfo1);
         lineInfo1=(View)findViewById(R.id.lineInfo1);
         VlblInfo1=(TextView) findViewById(R.id.VlblInfo1);
         txtInfo1=(EditText) findViewById(R.id.txtInfo1);
         secInfo2=(LinearLayout)findViewById(R.id.secInfo2);
         lineInfo2=(View)findViewById(R.id.lineInfo2);
         VlblInfo2=(TextView) findViewById(R.id.VlblInfo2);
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

         txtVill.setEnabled(false);
         txtBari.setEnabled(false);
         txtHH.setEnabled(false);
         txtMSlNo.setEnabled(false);

         txtVill.setText(VILL);
         txtVill.setFocusable(false);
         txtBari.setText(BARI);
         txtBari.setFocusable(false);
         txtHH.setText(HH);

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

             String DV="";

             if(txtVill.getText().toString().length()==0 & secVill.isShown())
             {
                 Connection.MessageBox(Events.this, "Required field: গ্রাম.");
                 txtVill.requestFocus();
                 return;
             }
             else if(txtBari.getText().toString().length()==0 & secBari.isShown())
             {
                 Connection.MessageBox(Events.this, "Required field: বাড়ি.");
                 txtBari.requestFocus();
                 return;
             }
             else if(txtHH.getText().toString().length()==0 & secHH.isShown())
             {
                 Connection.MessageBox(Events.this, "Required field: খানা.");
                 txtHH.requestFocus();
                 return;
             }
             else if(txtMSlNo.getText().toString().length()==0 & secMSlNo.isShown())
             {
                 Connection.MessageBox(Events.this, "Required field: সিরিয়াল.");
                 txtMSlNo.requestFocus();
                 return;
             }
             else if(txtPNo.getText().toString().length()==0 & secPNo.isShown())
             {
                 Connection.MessageBox(Events.this, "Required field: PNo.");
                 txtPNo.requestFocus();
                 return;
             }
             else if(spnEvType.getSelectedItemPosition()==0  & secEvType.isShown())
             {
                 Connection.MessageBox(Events.this, "Required field: Event Type.");
                 spnEvType.requestFocus();
                 return;
             }
             DV = Global.DateValidate(dtpEvDate.getText().toString());
             if(DV.length()!=0 & secEvDate.isShown())
             {
                 Connection.MessageBox(Events.this, DV);
                 dtpEvDate.requestFocus();
                 return;
             }
             else if(txtInfo1.getText().toString().length()==0 & secInfo1.isShown())
             {
                 Connection.MessageBox(Events.this, "Required field: Info1.");
                 txtInfo1.requestFocus();
                 return;
             }
             else if(txtInfo2.getText().toString().length()==0 & secInfo2.isShown())
             {
                 Connection.MessageBox(Events.this, "Required field: Info2.");
                 txtInfo2.requestFocus();
                 return;
             }
             else if(txtInfo3.getText().toString().length()==0 & secInfo3.isShown())
             {
                 Connection.MessageBox(Events.this, "Required field: Info3.");
                 txtInfo3.requestFocus();
                 return;
             }
             else if(txtInfo4.getText().toString().length()==0 & secInfo4.isShown())
             {
                 Connection.MessageBox(Events.this, "Required field: Info4.");
                 txtInfo4.requestFocus();
                 return;
             }
             DV = Global.DateValidate(dtpVDate.getText().toString());
             if(DV.length()!=0 & secVDate.isShown())
             {
                 Connection.MessageBox(Events.this, DV);
                 dtpVDate.requestFocus();
                 return;
             }
             else if(txtRnd.getText().toString().length()==0 & secRnd.isShown())
             {
                 Connection.MessageBox(Events.this, "Required field: Round No.");
                 txtRnd.requestFocus();
                 return;
             }

             String SQL = "";
             RadioButton rb;

             Events_DataModel objSave = new Events_DataModel();
             objSave.setVill(txtVill.getText().toString());
             objSave.setBari(txtBari.getText().toString());
             objSave.setHH(txtHH.getText().toString());
             objSave.setMSlNo(txtMSlNo.getText().toString());
             objSave.setPNo(txtPNo.getText().toString());
             objSave.setEvType((spnEvType.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnEvType.getSelectedItem().toString(), "-")));
             objSave.setEvDate(dtpEvDate.getText().toString().length() > 0 ? Global.DateConvertYMD(dtpEvDate.getText().toString()) : dtpEvDate.getText().toString());
             objSave.setInfo1(txtInfo1.getText().toString());
             objSave.setInfo2(txtInfo2.getText().toString());
             objSave.setInfo3(txtInfo3.getText().toString());
             objSave.setInfo4(txtInfo4.getText().toString());
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

             String status = objSave.SaveUpdateData(this);
             if(status.length()==0) {
                 Intent returnIntent = new Intent();
                 returnIntent.putExtra("res", "");
                 setResult(Activity.RESULT_OK, returnIntent);

                 Connection.MessageBox(Events.this, "Saved Successfully");
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
     private String MemNo(String Vill,String Bari,String HH)
     {
         String M = C.ReturnSingleValue("Select cast(ifnull(max(MSlNo),0)+1 as varchar(2))MemNo from Member where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"'");
         M = Global.Right("0"+M,2);
         return M;
     }
 }