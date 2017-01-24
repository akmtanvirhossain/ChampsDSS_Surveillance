
 package org.icddrb.champsdss;


 //Android Manifest Code
 //<activity android:name=".Baris" android:label="Baris" />
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

 public class Baris extends Activity {
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
         LinearLayout secCluster;
         View lineCluster;
         TextView VlblCluster;
         EditText txtCluster;
         LinearLayout secBlock;
         View lineBlock;
         TextView VlblBlock;
         Spinner spnBlock;
         LinearLayout secBariName;
         View lineBariName;
         TextView VlblBariName;
         EditText txtBariName;
         LinearLayout secBariLoc;
         View lineBariLoc;
         TextView VlblBariLoc;
         EditText txtBariLoc;

    static String TableName;

    static String STARTTIME = "";
    static String DEVICEID  = "";
    static String ENTRYUSER = "";

    Bundle IDbundle;
    static String VILL = "";
    static String BARI = "";

 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.baris);
         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = g.getDeviceNo();
         ENTRYUSER = g.getUserId();

         IDbundle = getIntent().getExtras();
         VILL = IDbundle.getString("Vill");
         BARI = IDbundle.getString("Bari");

         TableName = "Baris";

         //turnGPSOn();

         //GPS Location
         //FindLocation();
         // Double.toString(currentLatitude);
         // Double.toString(currentLongitude);
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(Baris.this);
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
         txtVill.setText(VILL);
         txtVill.setEnabled(false);
         secBari=(LinearLayout)findViewById(R.id.secBari);
         lineBari=(View)findViewById(R.id.lineBari);
         VlblBari=(TextView) findViewById(R.id.VlblBari);
         txtBari=(EditText) findViewById(R.id.txtBari);
         if(BARI.length()==0)
             txtBari.setText(NewBariNo(VILL));
         else
             txtBari.setText(BARI);

         txtBari.setEnabled(false);

         secCluster=(LinearLayout)findViewById(R.id.secCluster);
         lineCluster=(View)findViewById(R.id.lineCluster);
         VlblCluster=(TextView) findViewById(R.id.VlblCluster);
         txtCluster=(EditText) findViewById(R.id.txtCluster);
         secBlock=(LinearLayout)findViewById(R.id.secBlock);
         lineBlock=(View)findViewById(R.id.lineBlock);
         VlblBlock=(TextView) findViewById(R.id.VlblBlock);
         spnBlock=(Spinner) findViewById(R.id.spnBlock);
         List<String> listBlock = new ArrayList<String>();
         
         listBlock.add("");
         listBlock.add("01-b1");
         listBlock.add("02-B2");
         ArrayAdapter<String> adptrBlock= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listBlock);
         spnBlock.setAdapter(adptrBlock);

         secBariName=(LinearLayout)findViewById(R.id.secBariName);
         lineBariName=(View)findViewById(R.id.lineBariName);
         VlblBariName=(TextView) findViewById(R.id.VlblBariName);
         txtBariName=(EditText) findViewById(R.id.txtBariName);
         secBariLoc=(LinearLayout)findViewById(R.id.secBariLoc);
         lineBariLoc=(View)findViewById(R.id.lineBariLoc);
         VlblBariLoc=(TextView) findViewById(R.id.VlblBariLoc);
         txtBariLoc=(EditText) findViewById(R.id.txtBariLoc);



         //Hide all skip variables


        Button cmdSave = (Button) findViewById(R.id.cmdSave);
        cmdSave.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) { 
            DataSave();
        }});
         DataSearch(VILL, BARI);
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Baris.this, e.getMessage());
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
             Connection.MessageBox(Baris.this, "Required field: Village.");
             txtVill.requestFocus(); 
             return;	
           }
         else if(txtBari.getText().toString().length()==0 & secBari.isShown())
           {
             Connection.MessageBox(Baris.this, "Required field: Bari.");
             txtBari.requestFocus(); 
             return;	
           }
         /*else if(txtCluster.getText().toString().length()==0 & secCluster.isShown())
           {
             Connection.MessageBox(Baris.this, "Required field: Cluster.");
             txtCluster.requestFocus(); 
             return;	
           }
         else if(spnBlock.getSelectedItemPosition()==0  & secBlock.isShown())
           {
             Connection.MessageBox(Baris.this, "Required field: Block.");
             spnBlock.requestFocus(); 
             return;	
           }*/
         else if(txtBariName.getText().toString().length()==0 & secBariName.isShown())
           {
             Connection.MessageBox(Baris.this, "Required field: Bari Name.");
             txtBariName.requestFocus(); 
             return;	
           }
         else if(txtBariLoc.getText().toString().length()==0 & secBariLoc.isShown())
           {
             Connection.MessageBox(Baris.this, "Required field: Bari Location.");
             txtBariLoc.requestFocus(); 
             return;	
           }
 
         String SQL = "";
         RadioButton rb;

         Baris_DataModel objSave = new Baris_DataModel();
         objSave.setVill(txtVill.getText().toString());
         objSave.setBari(txtBari.getText().toString());
         //objSave.setCluster(txtCluster.getText().toString());
         //objSave.setBlock((spnBlock.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnBlock.getSelectedItem().toString(), "-")));
         objSave.setCluster("");
         objSave.setBlock("");
         objSave.setBariName(txtBariName.getText().toString());
         objSave.setBariLoc(txtBariLoc.getText().toString());
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

             Connection.MessageBox(Baris.this, "Saved Successfully");
         }
         else{
             Connection.MessageBox(Baris.this, status);
             return;
         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Baris.this, e.getMessage());
         return;
     }
 }

 private void DataSearch(String Vill, String Bari)
     {
       try
        {
     
           RadioButton rb;
           Baris_DataModel d = new Baris_DataModel();
           String SQL = "Select * from "+ TableName +"  Where Vill='"+ Vill +"' and Bari='"+ Bari +"'";
           List<Baris_DataModel> data = d.SelectAll(this, SQL);
           for(Baris_DataModel item : data){
             //txtVill.setText(item.getVill());
             //txtBari.setText(item.getBari());
             //txtCluster.setText(item.getCluster());
             //spnBlock.setSelection(Global.SpinnerItemPositionAnyLength(spnBlock, item.getBlock()));
             txtBariName.setText(item.getBariName());
             txtBariLoc.setText(item.getBariLoc());
           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(Baris.this, e.getMessage());
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


//        dtpDate = (EditText)findViewById(R.id.dtpEnDate);
//        if (VariableID.equals("btnEnDate"))
//        {
//            dtpDate = (EditText)findViewById(R.id.dtpEnDate);
//        }
//        else if (VariableID.equals("btnExDate"))
//        {
//            dtpDate = (EditText)findViewById(R.id.dtpExDate);
//        }
//        dtpDate.setText(new StringBuilder()
//                .append(Global.Right("00"+mDay,2)).append("/")
//                .append(Global.Right("00"+mMonth,2)).append("/")
//                .append(mYear));
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

     private String NewBariNo(String Vill)
     {
         String B = C.ReturnSingleValue("Select cast(ifnull(max(Bari),0)+1 as varchar(4))BariNo from Baris where Vill='"+ Vill +"'");
         B = Global.Right("000"+B,4);
         return B;
     }

}