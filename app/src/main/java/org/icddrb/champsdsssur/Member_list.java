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
 import android.view.Menu;
 import android.view.MenuInflater;
 import android.view.MenuItem;
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
 import android.widget.RadioButton;
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
    private String ErrMsg;
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

    Button btnErrorCheck;
    Button btnMemberName;
    Button btnSES;
    Button btnPregHis;
    Button btncanceltran;
    Button btnprocess;
    Button btnNote;

    MySharedPreferences sp;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.processtran, menu);
        return true;
    }

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
         lblBariName.setText(": "+IDbundle.getString("BariName"));
//         lblBariName.setText(": "+BARI+", "+IDbundle.getString("BariName"));

         txtBari.setText(BARI);
         txtHH.setText(HH);
         txtVill.setText(VILL+"-"+BARI+"-"+HH);
         txtRnd.setText(ROUNDNO);

         LinearLayout secMenu;
         secMenu= (LinearLayout) findViewById(R.id.secMenu);
         secMenu.setVisibility(View.VISIBLE);

         TableName = "tmpMember";
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         btncanceltran = (Button) findViewById(R.id.btncanceltran);
         btncanceltran.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {

//                 C.Save("Delete from tmpHousehold where Vill||Bari||HH='"+ (VILL+BARI+HH) +"'");
//                 C.Save("Delete from tmpVisits where Vill||Bari||HH='"+ (VILL+BARI+HH) +"'");
//                 C.Save("Delete from tmpMember where Vill||Bari||HH='"+ (VILL+BARI+HH) +"'");
//                 C.Save("Delete from tmpSES where Vill||Bari||HH='"+ (VILL+BARI+HH) +"'");
//                 C.Save("Delete from tmpPregHis where Vill||Bari||HH='"+ (VILL+BARI+HH) +"'");
//                 C.Save("Delete from tmpEvents where Vill||Bari||HH='"+ (VILL+BARI+HH) +"'");
//
//                 g.setBariCode("");
//                 g.setHouseholdNo("");

                 AlertDialog.Builder adb = new AlertDialog.Builder(Member_list.this);
                 adb.setTitle("বাহির");
                 adb.setMessage("আপনি কি খানা সদস্যদের তালিকা থেকে বের হতে চান [হ্যাঁ/না]?");
                 adb.setNegativeButton("না", null);
                 adb.setPositiveButton("হ্যাঁ", new AlertDialog.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {

                         C.Save("Delete from tmpHousehold where Vill||Bari||HH='"+ (VILL+BARI+HH) +"'");
                         C.Save("Delete from tmpVisits where Vill||Bari||HH='"+ (VILL+BARI+HH) +"'");
                         C.Save("Delete from tmpMember where Vill||Bari||HH='"+ (VILL+BARI+HH) +"'");
                         C.Save("Delete from tmpSES where Vill||Bari||HH='"+ (VILL+BARI+HH) +"'");
                         C.Save("Delete from tmpPregHis where Vill||Bari||HH='"+ (VILL+BARI+HH) +"'");
                         C.Save("Delete from tmpEvents where Vill||Bari||HH='"+ (VILL+BARI+HH) +"'");

                         g.setBariCode("");
                         g.setHouseholdNo("");

                         Intent returnIntent = new Intent();
                         returnIntent.putExtra("res", "hh");
                         setResult(Activity.RESULT_OK, returnIntent);
                         finish();
                     }});
                 adb.show();
             }});

         btnprocess = (Button) findViewById(R.id.btnprocess);
         btnprocess.setTextColor(Color.BLUE);
         btnprocess.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(Member_list.this);
                 adb.setTitle("Process Transaction");
//                 adb.setMessage("Do you want to process current transaction[Yes/No]?");
                 adb.setMessage("আপনি কি র্বতমান কাজ সংরক্ষণ করতে চান [হ্যাঁ/না]?");
                 adb.setNegativeButton("না", null);
                 adb.setPositiveButton("হ্যাঁ", new AlertDialog.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {

                         String Status = ProcessTransaction(VILL,BARI,HH,ROUNDNO);

                         if(Status.length()>0)
                         {
                             Connection.MessageBox(Member_list.this,Status);
                             return;
                         }
                         else
                         {
                             //Table Name: Visits
                             VisitsDataTransfer(VILL, BARI, HH, ROUNDNO);

                             //Table Name: Household
                             HouseholdDataTransfer(VILL, BARI, HH);

                             //Table Name: Member
                             MemberDataTransfer(VILL, BARI, HH);

                             //Table Name: SES
                             SESDataTransfer(VILL, BARI, HH);

                             //Table Name: PregHis
                             PreHisDataTransfer(VILL, BARI, HH);

                             //Table Name: Events
                             EventDataTransfer(VILL, BARI, HH);

                             C.Save("Delete from tmpHousehold where Vill||Bari||HH='" + (VILL + BARI + HH) + "'");
                             C.Save("Delete from tmpVisits where Vill||Bari||HH='" + (VILL + BARI + HH) + "'");
                             C.Save("Delete from tmpMember where Vill||Bari||HH='" + (VILL + BARI + HH) + "'");
                             C.Save("Delete from tmpSES where Vill||Bari||HH='" + (VILL + BARI + HH) + "'");
                             C.Save("Delete from tmpPregHis where Vill||Bari||HH='" + (VILL + BARI + HH) + "'");
                             C.Save("Delete from tmpEvents where Vill||Bari||HH='" + (VILL + BARI + HH) + "'");

                             String totalMem = C.ReturnSingleValue("Select count(*)Total from Member where Vill||Bari||HH='" + (VILL + BARI + HH) + "' and length(ExType)=0");
                             C.Save("Update Household set TotMem='"+ totalMem +"' where Vill||Bari||HH='" + (VILL + BARI + HH) + "'");
                             finish();

                             Intent returnIntent = new Intent();
                             returnIntent.putExtra("res", "mem");
                             setResult(Activity.RESULT_OK, returnIntent);
                         }
                     }

                 });
                 adb.show();
             }});

         btnNote = (Button) findViewById(R.id.btnNote);
         btnNote.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 VisitNoteForm(VILL, BARI, HH);

             }});

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 String infoMiss = C.ReturnSingleValue("Select count(*)TotalMiss from tmpMember where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and length(Sex)=0");

                 if (Integer.valueOf(infoMiss) > 0) {
                     Connection.MessageBox(Member_list.this, infoMiss + " জন সদস্যের তথ্য আপডেট করা হয় নাই");
                     return;
                 }

                 if (!C.Existence("Select PNo from tmpMember where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'")) {
                     Connection.MessageBox(Member_list.this, "কমপক্ষে একজন সদস্য এন্ট্রি করতে হবে.");
                     return;
                 }
                 if(!C.Existence("Select Rth from tmpMember  where rtrim(Rth)='01' and Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH +"'"))
                 {
                     Connection.MessageBox(Member_list.this, "খানায় কমপক্ষে একজন খানা প্রধান থাকতে হবে।");
                     return;
                 }
                 if (!C.Existence("Select VStatus from tmpSES where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'")) {
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


         btnSES = (Button) findViewById(R.id.btnSES);
         btnSES.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (!C.Existence("Select PNo from tmpMember where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'")) {
                     Connection.MessageBox(Member_list.this, "Required: কমপক্ষে একজন সদস্য এন্ট্রি করতে হবে.");
                     return;
                 }
                 if(!C.Existence("Select Rth from tmpMember  where rtrim(Rth)='01' and Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH +"'"))
                 {
                     Connection.MessageBox(Member_list.this, "খানায় কমপক্ষে একজন খানা প্রধান থাকতে হবে।");
                     return;
                 }
                 String infoMiss = C.ReturnSingleValue("Select count(*)TotalMiss from tmpMember where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and length(Sex)=0");

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
                 if (!C.Existence("Select PNo from tmpMember where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'")) {
                     Connection.MessageBox(Member_list.this, "Required: কমপক্ষে একজন সদস্য এন্ট্রি করতে হবে.");
                     return;
                 }

                 if(!C.Existence("Select Rth from tmpMember  where rtrim(Rth)='01' and Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH +"'"))
                 {
                     Connection.MessageBox(Member_list.this, "খানায় কমপক্ষে একজন খানা প্রধান থাকতে হবে।");
                     return;
                 }

                 if(!C.Existence("Select Sex from tmpMember  where Sex='2' and Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH +"'"))
                 {
                     Connection.MessageBox(Member_list.this, "খানায় কমপক্ষে একজন মহিলা সদস্য এন্ট্রি করতে হবে.");
                     return;
                 }

                 if(!C.Existence("Select Sex, AgeY,MS from tmpMember Where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH + "' and (julianday('now')-julianday(BDate))<=18262 and Sex='2' and MS<>'30'"))
                 {
                     Connection.MessageBox(Member_list.this, "খানায় ৫০ বছরের কম বয়সের কখনও বিবাহ হয়েছে এমন মহিলা নেই .");
                     return;
                 }

                 String infoMiss = C.ReturnSingleValue("Select count(*)TotalMiss from tmpMember where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and length(Sex)=0");

                 if (Integer.valueOf(infoMiss) > 0) {
                     Connection.MessageBox(Member_list.this, infoMiss + " জন সদস্যের তথ্য আপডেট করা হয় নাই");
                     return;
                 }

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
                 Intent f1 = new Intent(getApplicationContext(),Events_NewMem.class);
                 IDbundle.putString("Vill", VILL);
                 IDbundle.putString("Bari", BARI);
                 IDbundle.putString("HH", HH);
                 IDbundle.putString("roundno",ROUNDNO);
                 IDbundle.putString("OldNew", "new");
                 f1.putExtras(IDbundle);
                 startActivityForResult(f1, 1);
             }

         });

         btnErrorCheck = (Button) findViewById(R.id.btnErrorCheck);
         btnErrorCheck.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent f1 = new Intent(getApplicationContext(),Member_list.class);
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
         Button btnErrorCheck = (Button)findViewById(R.id.btnErrorCheck);

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

         DataRetrieve(VILL+BARI+HH, true,"active");

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

         btnErrorCheck.setOnClickListener(new View.OnClickListener() {
             public void onClick(View arg0) {

                 ErrorList(VILL,BARI,HH);

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
    private void ErrorList(final String Vill, final String Bari, final String HH)
    {
        try
        {
            final Dialog dialog = new Dialog(Member_list.this);

            dialog .requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.errorlist);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);

            Window window = dialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();

            wlp.gravity = Gravity.TOP;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);

            final ListView evlist = (ListView)dialog.findViewById(R.id.LstError);
            View header = getLayoutInflater().inflate(R.layout.errorlistheading, null);
            evlist.addHeaderView(header);
            Cursor cur = C.ReadData("select D.vill,D.bari,D.hh,M.Name,D.mslno,D.note,D.status from DataCorrectionNote D left outer join Member M on D.vill||D.bari||D.hh||D.Mslno=M.vill||M.bari||M.hh||M.Mslno Where d.Vill='"+ Vill +"' and d.Bari='"+ Bari +"' and d.HH='"+ HH +"' and status='2' order by D.vill,D.bari,D.hh,D.Mslno asc");

            cur.moveToFirst();
            evmylist.clear();
            eList = null;
            evlist.setAdapter(null);

            while(!cur.isAfterLast())
            {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("vill", cur.getString(cur.getColumnIndex("Vill")));
                map.put("bari", cur.getString(cur.getColumnIndex("Bari")));
                map.put("hh", cur.getString(cur.getColumnIndex("HH")));
                map.put("name", cur.getString(cur.getColumnIndex("Name")));
                map.put("mslno", cur.getString(cur.getColumnIndex("MSlNo")));
                map.put("status", cur.getString(cur.getColumnIndex("Status")));
                map.put("note", cur.getString(cur.getColumnIndex("Note")));

                evmylist.add(map);

                eList = new SimpleAdapter(Member_list.this, evmylist, R.layout.errorlistrow,
                        new String[] {"vill","bari","hh","name","mslno","status","note"},
                        new int[] {R.id.v_vill,R.id.v_bari,R.id.v_hh,R.id.v_Name,R.id.v_Mslno,R.id.v_Status,R.id.v_note});
                evlist.setAdapter(new ErrorListAdapter(this,dialog,evlist));
                cur.moveToNext();
            }
            cur.close();

            Button cmdErrorListClose = (Button)dialog.findViewById(R.id.cmdErrorListClose);
            cmdErrorListClose.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    if (C.Existence("select D.status from DataCorrectionNote D Where d.Vill='"+ VILL +"' and d.Bari='"+ BARI +"' and d.HH='"+ HH +"' and status='2'")) {
                        btnErrorCheck.setBackgroundResource(R.drawable.button_style_red);
                        btnErrorCheck.setTextColor(Color.WHITE);
                    }
                    else{
                        btnErrorCheck.setBackgroundResource(R.drawable.button_style);
                    }
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

    public class ErrorListAdapter extends BaseAdapter
    {
        private Context context;
        Dialog dg;
        ListView lv;
        public ErrorListAdapter(Context c,Dialog d,ListView Errorlist){
            context = c;
            dg=d;
            lv=Errorlist;
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
                convertView = inflater.inflate(R.layout.errorlistrow, null);
            }

            final HashMap<String, String> o = (HashMap<String, String>) eList.getItem(position);

            TextView v_vill=(TextView)convertView.findViewById(R.id.v_vill);
            TextView v_bari=(TextView)convertView.findViewById(R.id.v_bari);
            TextView v_hh=(TextView)convertView.findViewById(R.id.v_hh);
            TextView v_Name=(TextView)convertView.findViewById(R.id.v_Name);
            TextView v_Mslno=(TextView)convertView.findViewById(R.id.v_Mslno);
            TextView v_note=(TextView)convertView.findViewById(R.id.v_note);
            final TextView v_status=(TextView)convertView.findViewById(R.id.v_Status);

            v_vill.setText(o.get("vill").toString());
            v_bari.setText(o.get("bari").toString());
            v_hh.setText((o.get("hh").toString()));
            v_Name.setText(o.get("name").toString());
            v_Mslno.setText(o.get("mslno").toString());
            v_status.setText(o.get("status").toString());
            v_note.setText(o.get("note").toString());

            final Button cmdErroeListUpdate = (Button)convertView.findViewById(R.id.cmdErrorListUpdate);

            cmdErroeListUpdate.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(Member_list.this);
                    adb.setTitle("List Update");
                    adb.setMessage("সদস্য নং "+ o.get("mslno").toString() +" এর তথ্য কি আপডেট করা হয়েছে [হ্যাঁ/না]?");

                    adb.setNegativeButton("না", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog1, int which) {

                        }});

                    adb.setPositiveButton("হ্যাঁ", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog1, int which) {
                            String HH = o.get("vill")+o.get("bari")+o.get("hh");
                            String SN = o.get("mslno").toString();
                            String ST = o.get("status").toString();
                            String Note = o.get("note").toString();//
                            C.Save("Update DataCorrectionNote set Status='1',ClearDate='"+Global.DateTimeNowYMDHMS()+"',Upload='2' where vill||bari||hh='"+ HH +"' and mslno='"+ SN + "' and note='"+ Note +"'");
                            cmdErroeListUpdate.setEnabled(false);
                            cmdErroeListUpdate.setText("Solve");
                            v_status.setText("1");
                        }});
                    adb.show();
                    return;
                }});

            return convertView;
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
            cur1 = C.ReadData("select Vill,Bari,HH,mslno,pno as pno,evtype,evdate,ifnull(info1,'')info1,ifnull(info2,'')info2,ifnull(info3,'')info3,ifnull(info4,'')info4,Rnd from tmpEvents where Vill||Bari||HH='"+ Household +"' order by   MslNo,Rnd,evtype");
        }
        else if(Status.equals("all"))
        {
            cur1 = C.ReadData("select Vill,Bari,HH,mslno,pno as pno,evtype,evdate,ifnull(info1,'')info1,ifnull(info2,'')info2,ifnull(info3,'')info3,ifnull(info4,'')info4,Rnd from Events where Vill||Bari||HH='"+ Household +"' order by   MslNo,Rnd,evtype");
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
            map.put("vill", cur1.getString(cur1.getColumnIndex("Vill")));
            map.put("bari", cur1.getString(cur1.getColumnIndex("Bari")));
            map.put("hh", cur1.getString(cur1.getColumnIndex("HH")));

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
                    adb.setMessage("সদস্যের নাম্বারঃ "+ o.get("mslno").toString() +" এবং ইভেন্ট কোডঃ "+ o.get("evtype").toString() +" কি মুছে ফেলতে চান [হ্যাঁ/না]?");

                    adb.setNegativeButton("না", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog1, int which) {

                        }});

                    adb.setPositiveButton("হ্যাঁ", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog1, int which) {
                            String HH = o.get("vill")+o.get("bari")+o.get("hh");
                            String SN = o.get("mslno").toString();
                            String EV = o.get("evtype").toString();
                            String EVD= o.get("evdate").toString();

                            //event specific update
                            if(EV.equals("12"))
                            {
                                C.Save("Delete from tmpEvents where vill||bari||hh='"+ HH +"' and MSlNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"' and Rnd='"+ o.get("rnd") +"'");
                            }
                            else if(EV.equals("20") | EV.equals("21") | EV.equals("22") | EV.equals("23"))
                            {
                                C.Save("Delete from tmpMember where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"'");
                                C.Save("Delete from tmpPregHis where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"'");
                                C.Save("Delete from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"'");

                            }
                            else if(EV.equals("25"))
                            {

                            }
                            else if(EV.equals("31") | EV.equals("32") | EV.equals("33") | EV.equals("34"))
                            {
                                String PMS = C.ReturnSingleValue("Select Info2 from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"'");
                                C.Save("delete from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"' and Rnd='"+ o.get("rnd") +"'");
                                C.Save("Update tmpMember set MS='"+ PMS +"' where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"'");
                            }
                            else if(EV.equals("40") | EV.equals("49"))
                            {
                                C.Save("Delete from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"' and Rnd='"+ o.get("rnd") +"'");
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
                                C.Save("delete from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"' and Rnd='"+ o.get("rnd") +"'");
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
                                C.Save("delete from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"' and Rnd='"+ o.get("rnd") +"'");
                                C.Save("Update tmpMember set ExType='',ExDate='' where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"'");
                            }
                            else if(EV.equals("54") | EV.equals("57"))
                            {
                                C.Save("delete from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"'");
                                C.Save("Update tmpMember set PosMig='',PosMigDate='' where  vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"'");
                            }
                            else if(EV.equals("61"))
                            {
                                String PMono = C.ReturnSingleValue("Select Info2 from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"' and Rnd='"+ o.get("rnd") +"'");
                                C.Save("delete from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"' and Rnd='"+ o.get("rnd") +"'");
                                C.Save("Update tmpMember set MoNo='"+ PMono +"' where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"'");
                            }
                            else if(EV.equals("62"))
                            {
                                String PFano = C.ReturnSingleValue("Select Info2 from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"' and Rnd='"+ o.get("rnd") +"'");
                                C.Save("delete from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"' and Rnd='"+ o.get("rnd") +"'");
                                C.Save("Update tmpMember set FaNo='"+ PFano +"' where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"'");
                            }
                            else if(EV.equals("63"))
                            {

                            }
                            else if(EV.equals("64"))
                            {
                                String PRth = C.ReturnSingleValue("Select Info2 from tmpEvents where vill||bari||hh='"+ HH +"' and Mslno='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"' and Rnd='"+ o.get("rnd") +"'");
                                C.Save("delete from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"' and Rnd='"+ o.get("rnd") +"'");
                                C.Save("Update tmpMember set Rth='"+ PRth +"' where vill||bari||hh='"+ HH +"' and Mslno='"+ SN +"'");
                            }
                            else if(EV.equals("71"))
                            {
                                String PEdu = C.ReturnSingleValue("Select Info2 from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"' and Rnd='"+ o.get("rnd") +"'");
                                C.Save("delete from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"' and Rnd='"+ o.get("rnd") +"'");
                                C.Save("Update tmpMember set Edu='"+ PEdu +"' where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"'");
                            }
                            else if(EV.equals("72"))
                            {
                                String POcp = C.ReturnSingleValue("Select Info2 from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"' and Rnd='"+ o.get("rnd") +"'");
                                C.Save("delete from tmpEvents where vill||bari||hh='"+ HH +"' and MslNo='"+ SN +"' and EvType='"+ EV +"' and EvDate='"+ EVD +"' and Rnd='"+ o.get("rnd") +"'");
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
                    adb.setMessage("সদস্যের নাম্বারঃ "+ o.get("mslno").toString() +" এবং ইভেন্ট কোডঃ "+ o.get("evtype").toString() +" কি আপডেট করতে চান [হ্যাঁ/না]?");

                    adb.setNegativeButton("না", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog1, int which) {

                        }});

                    adb.setPositiveButton("হ্যাঁ", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog1, int which) {
                            //Show event update form
//                            UpdateEventForm(o.get("evtype"),o.get("evdate"), VILL, BARI, HH, o.get("mslno"),o.get("pno"),lv,o.get("info3"));

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
                    cmdEvListDel.setEnabled(false);
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
                SQLStr = "Select  (case when m.vill is null then 'n' else 'o' end)as NewOld, t.Vill, t.Bari, t.Hh, t.mslno, t.Pno, t.Name, t.Rth, t.Sex, t.BDate, Cast(((julianday(date('now'))-julianday(t.BDate))/365.25) as int) as AgeY,Cast(((julianday(t.ExDate)-julianday(t.BDate))/365.25) as int) as DeathAge, t.Mono, t.Fano, t.Edu,";
                SQLStr += " t.Ms, t.Pstat, t.LmpDt, t.Sp1, t.Sp2, t.Sp3, t.Sp4, t.Ocp, t.EnType, t.EnDate,";
                SQLStr += " (case when cast(strftime('%Y', ifnull(t.ExDate,'')) as int)>=2014 and t.ExType='55' then '1' else '2' end)as deathrep,";
                SQLStr += " ifnull(t.ExType,'')ExType,ifnull(t.ExDate,'')ExDate,cast(strftime('%Y', ifnull(t.ExDate,'')) as int)ExYear,ifnull(t.PosMig,'')PosMig,ifnull(t.PosMigDate,'')PosMigDate from tmpMember t";
                SQLStr += " left outer join Member m on t.vill||t.bari||t.hh||t.MslNo = m.vill||m.bari||m.hh||m.MslNo";
                SQLStr += " where t.vill||t.bari||t.hh='"+ HH +"' and (length(t.extype)=0 or t.extype is null) order by cast(t.MslNo as int) asc";
            }
            else if(ActiveOrAll.equalsIgnoreCase("all"))
            {
                SQLStr = "Select  (case when m.vill is null then 'n' else 'o' end)as NewOld, t.Vill, t.Bari, t.Hh, t.mslno, t.Pno, t.Name, t.Rth, t.Sex, t.BDate, Cast(((julianday(date('now'))-julianday(t.BDate))/365.25) as int) as AgeY, Cast(((julianday(t.ExDate)-julianday(t.BDate))/365.25) as int) as DeathAge, t.Mono, t.Fano, t.Edu,";
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
            if(heading ==true)
            {
//                View header = getLayoutInflater().inflate(R.layout.memberheading, null);
//                list.addHeaderView(header);
            }

            int i=0;
            while(!cur1.isAfterLast())
            {
                HashMap<String, String> map = new HashMap<String, String>();

                if(i==0)
                {
                    //View header = getLayoutInflater().inflate(R.layout.membereventsheading, null);
                    //list.addHeaderView(header);
                }
//                map.put("newold", cur1.getString(cur1.getColumnIndex("NewOld")));
//                map.put("vill", cur1.getString(cur1.getColumnIndex("Vill")));
//                map.put("bari", cur1.getString(cur1.getColumnIndex("Bari")));
//                map.put("hh", cur1.getString(cur1.getColumnIndex("Hh")));
                map.put("MSlNo", cur1.getString(cur1.getColumnIndex("MSlNo")));
                map.put("Name", cur1.getString(cur1.getColumnIndex("Name")));
                map.put("PNo", cur1.getString(cur1.getColumnIndex("PNo")));
                map.put("Rth", cur1.getString(cur1.getColumnIndex("Rth")));
                map.put("Sex", cur1.getString(cur1.getColumnIndex("Sex")));
                map.put("BDate", cur1.getString(cur1.getColumnIndex("BDate")));
                map.put("AgeY", cur1.getString(cur1.getColumnIndex("AgeY")));
                map.put("MoNo", cur1.getString(cur1.getColumnIndex("MoNo")));
                map.put("FaNo", cur1.getString(cur1.getColumnIndex("FaNo")));
                map.put("Edu", cur1.getString(cur1.getColumnIndex("Edu")));
                map.put("MS", cur1.getString(cur1.getColumnIndex("MS")));
                map.put("Pstat", cur1.getString(cur1.getColumnIndex("Pstat")));
                map.put("LmpDt", cur1.getString(cur1.getColumnIndex("LmpDt")));
                map.put("Ocp", cur1.getString(cur1.getColumnIndex("Ocp")));
                map.put("Sp1", cur1.getString(cur1.getColumnIndex("Sp1")));
                map.put("Sp2", cur1.getString(cur1.getColumnIndex("Sp2")));
                map.put("Sp3", cur1.getString(cur1.getColumnIndex("Sp3")));
                map.put("Sp4", cur1.getString(cur1.getColumnIndex("Sp4")));
                map.put("EnType", cur1.getString(cur1.getColumnIndex("EnType")));
                map.put("EnDate", cur1.getString(cur1.getColumnIndex("EnDate")));
                map.put("ExType", cur1.getString(cur1.getColumnIndex("ExType")));
                map.put("ExDate", cur1.getString(cur1.getColumnIndex("ExDate")));
                map.put("PosMig", cur1.getString(cur1.getColumnIndex("PosMig")));
                map.put("PosMigDate", cur1.getString(cur1.getColumnIndex("PosMigDate")));
//                map.put("exyear", cur1.getString(cur1.getColumnIndex("ExYear")));
//                map.put("deathage", cur1.getString(cur1.getColumnIndex("DeathAge")));
//                map.put("deathrep", cur1.getString(cur1.getColumnIndex("deathrep")));

                mylist.add(map);
                mSchedule = new SimpleAdapter(Member_list.this, mylist, R.layout.memberheading,new String[] {"MSlNo","Name","PNo","Rth","Sex","BDate","AgeY","MoNo","FaNo","Edu","MS","Pstat","LmpDt","Ocp","Sp1","Sp2","Sp3","Sp4","EnType","EnDate","ExType","ExDate","PosMig","PosMigDate"},
                        new int[] {R.id.MSlNo,R.id.Name,R.id.PNo,R.id.Rth,R.id.Sex,R.id.BDate,R.id.AgeY,R.id.MoNo,R.id.FaNo,R.id.Edu,R.id.MS,R.id.Pstat,R.id.LmpDt,R.id.Ocp,R.id.Sp1,R.id.Sp2,R.id.Sp3,R.id.Sp4,R.id.EnType,R.id.EnDate,R.id.ExType,R.id.ExDate,R.id.PosMig,R.id.PosMigDate});
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
            TextView MSlNo= (TextView) convertView.findViewById(R.id.MSlNo);
            TextView Name= (TextView) convertView.findViewById(R.id.Name);
            TextView PNo= (TextView) convertView.findViewById(R.id.PNo);
            TextView Rth= (TextView) convertView.findViewById(R.id.Rth);
            TextView Sex= (TextView) convertView.findViewById(R.id.Sex);
            TextView BDate= (TextView) convertView.findViewById(R.id.BDate);
            TextView AgeY= (TextView) convertView.findViewById(R.id.AgeY);
            TextView MoNo= (TextView) convertView.findViewById(R.id.MoNo);
            TextView FaNo= (TextView) convertView.findViewById(R.id.FaNo);
            TextView Edu= (TextView) convertView.findViewById(R.id.Edu);
            TextView MS= (TextView) convertView.findViewById(R.id.MS);
            TextView Pstat= (TextView) convertView.findViewById(R.id.Pstat);
            TextView LmpDt= (TextView) convertView.findViewById(R.id.LmpDt);
            TextView Ocp= (TextView) convertView.findViewById(R.id.Ocp);
            TextView Sp1= (TextView) convertView.findViewById(R.id.Sp1);
            TextView Sp2= (TextView) convertView.findViewById(R.id.Sp2);
            TextView Sp3= (TextView) convertView.findViewById(R.id.Sp3);
            TextView Sp4= (TextView) convertView.findViewById(R.id.Sp4);
            TextView EnType= (TextView) convertView.findViewById(R.id.EnType);
            TextView EnDate= (TextView) convertView.findViewById(R.id.EnDate);
            TextView ExType= (TextView) convertView.findViewById(R.id.ExType);
            TextView ExDate= (TextView) convertView.findViewById(R.id.ExDate);
            TextView PosMig= (TextView) convertView.findViewById(R.id.PosMig);
            TextView PosMigDate= (TextView) convertView.findViewById(R.id.PosMigDate);

            final HashMap<String, String> o = (HashMap<String, String>) mSchedule.getItem(position);

            MSlNo.setText(o.get("MSlNo"));
            Name.setText(o.get("Name"));
            PNo.setText(o.get("Pno"));
            Rth.setText(o.get("Rth"));
            Sex.setText(o.get("Sex"));
            BDate.setText(Global.DateConvertDMY(o.get("BDate")));
            AgeY.setText(o.get("AgeY"));
            MoNo.setText(o.get("MoNo"));
            FaNo.setText(o.get("FaNo"));
            Edu.setText(o.get("Edu"));
            MS.setText(o.get("MS"));

            Pstat.setText(o.get("Pstat"));

            if(o.get("LmpDt")==null | o.get("LmpDt").trim().length()==0)
                LmpDt.setText(o.get("LmpDt"));
            else
                LmpDt.setText(Global.DateConvertDMY(o.get("LmpDt")));

            Ocp.setText(o.get("Ocp"));
            Sp1.setText(o.get("Sp1"));
            Sp2.setText(o.get("Sp2"));
            Sp3.setText(o.get("Sp3"));
            Sp4.setText(o.get("Sp4"));
            EnType.setText(o.get("EnType"));
            EnDate.setText(Global.DateConvertDMY(o.get("EnDate")));
            ExType.setText(o.get("ExType"));

            if(o.get("ExDate")==null | o.get("ExDate").trim().length()==0)
                ExDate.setText(o.get("ExDate"));
            else
                ExDate.setText(Global.DateConvertDMY(o.get("ExDate")));

             //show only if possible migration
            if(o.get("PosMig").equals("54"))
                PosMig.setText(o.get("PosMig"));
            else
                PosMig.setText("");

            if(o.get("PosMigDate")==null | o.get("PosMigDate").trim().length()==0)
                PosMigDate.setText(o.get("PosMigDate"));
            else
                PosMigDate.setText(Global.DateConvertDMY(o.get("PosMigDate")));

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
                MSlNo.setTextColor(Color.BLUE);
                PNo.setTextColor(Color.BLUE);
                Name.setTextColor(Color.BLUE);
                Rth.setTextColor(Color.BLUE);
                Sex.setTextColor(Color.BLUE);
                BDate.setTextColor(Color.BLUE);
                MoNo.setTextColor(Color.BLUE);
                FaNo.setTextColor(Color.BLUE);
                Edu.setTextColor(Color.BLUE);
                MS.setTextColor(Color.BLUE);
                Pstat.setTextColor(Color.BLUE);
                LmpDt.setTextColor(Color.BLUE);
                Sp1.setTextColor(Color.BLUE);
                Sp2.setTextColor(Color.BLUE);
                Sp3.setTextColor(Color.BLUE);
                Sp4.setTextColor(Color.BLUE);
                Ocp.setTextColor(Color.BLUE);
                EnType.setTextColor(Color.BLUE);
                EnDate.setTextColor(Color.BLUE);
                ExType.setTextColor(Color.BLUE);
                ExDate.setTextColor(Color.BLUE);
                PosMig.setTextColor(Color.BLUE);
                PosMigDate.setTextColor(Color.BLUE);
            }
            else
            {
                MSlNo.setTextColor(Color.RED);
                PNo.setTextColor(Color.RED);
                Name.setTextColor(Color.RED);
                Rth.setTextColor(Color.RED);
                Sex.setTextColor(Color.RED);
                BDate.setTextColor(Color.RED);
                MoNo.setTextColor(Color.RED);
                FaNo.setTextColor(Color.RED);
                Edu.setTextColor(Color.RED);
                MS.setTextColor(Color.RED);
                Pstat.setTextColor(Color.RED);
                LmpDt.setTextColor(Color.RED);
                Sp1.setTextColor(Color.RED);
                Sp2.setTextColor(Color.RED);
                Sp3.setTextColor(Color.RED);
                Sp4.setTextColor(Color.RED);
                Ocp.setTextColor(Color.RED);
                EnType.setTextColor(Color.RED);
                EnDate.setTextColor(Color.RED);
                ExType.setTextColor(Color.RED);
                ExDate.setTextColor(Color.RED);
            }

            if(o.get("newold").equals("n"))
            {
                MSlNo.setTextColor(Color.GREEN);
                PNo.setTextColor(Color.GREEN);
                Name.setTextColor(Color.GREEN);
                Rth.setTextColor(Color.GREEN);
                Sex.setTextColor(Color.GREEN);
                BDate.setTextColor(Color.GREEN);
                MoNo.setTextColor(Color.GREEN);
                FaNo.setTextColor(Color.GREEN);
                Edu.setTextColor(Color.GREEN);
                MS.setTextColor(Color.GREEN);
                Pstat.setTextColor(Color.GREEN);
                LmpDt.setTextColor(Color.GREEN);
                Sp1.setTextColor(Color.GREEN);
                Sp2.setTextColor(Color.GREEN);
                Sp3.setTextColor(Color.GREEN);
                Sp4.setTextColor(Color.GREEN);
                Ocp.setTextColor(Color.GREEN);
                EnType.setTextColor(Color.GREEN);
                EnDate.setTextColor(Color.GREEN);
                ExType.setTextColor(Color.GREEN);
                ExDate.setTextColor(Color.GREEN);
            }

            if(o.get("ExType").trim().equals("55"))
            {
                if(o.get("deathrep").equals("1"))
                {
                    Name.setBackgroundColor(Color.RED);
                    Name.setTextColor( Color.WHITE );
                }
                else
                {
                    Name.setBackgroundColor(Color.WHITE);
                    Name.setTextColor( Color.RED );
                }
            }

            final AlertDialog.Builder adb = new AlertDialog.Builder(Member_list.this);
            final TableLayout memtab = (TableLayout)convertView.findViewById(R.id.memtab);
            final EditText lblMslNo = (EditText)convertView.findViewById(R.id.txtMSlNo);
            final TextView lblName = (TextView)convertView.findViewById(R.id.lblName);



            memtab.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v) {
                    if(o.get("ExType").trim().length()==0)
                    {
                        if(o.get("PosMigDate")==null | o.get("PosMigDate").trim().length()==0)
                        {
//                            ShowEventForm(o.get("vill"),o.get("bari"),o.get("hh"),o.get("sno"),o.get("pno"),o.get("name"));
                        }
                        else
                        {
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
                        if(o.get("ExType").trim().equals("55") & Integer.valueOf(o.get("exyear"))>=2014)
                        {
                            if(Integer.valueOf(o.get("deathage"))>=13 & Integer.valueOf(o.get("deathage"))<=49 & o.get("Sex").equals("2"))
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
        if (C.Existence("Select VStatus from tmpSES where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'")) {
            btnSES.setBackgroundResource(R.drawable.button_style_green);
            btnSES.setTextColor(Color.BLACK);
        }
        else{
            btnSES.setBackgroundResource(R.drawable.button_style);
        }

        if (C.Existence("Select VStatus from tmpSES where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH +"' and VStatus <>'1'")) {
            btnSES.setBackgroundResource(R.drawable.button_style_blue);
            btnSES.setTextColor(Color.WHITE);
        }

        String TotRh = C.ReturnSingleValue("Select Count(*)TotRWO from tmpMember Where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH + "' and (julianday(EnDate)-julianday(BDate))<=18262 and Sex='2' and MS<>'30'");
        String PregHis = C.ReturnSingleValue("Select count(*)Total from tmpPregHis Where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH + "'");

        if (Integer.valueOf(TotRh)>0 & Integer.valueOf(TotRh) == Integer.valueOf(PregHis))
        {
            //btnPregHis.setBackgroundColor(Color.GREEN);
            btnPregHis.setBackgroundResource(R.drawable.button_style_green);
            btnPregHis.setTextColor(Color.BLACK);
        }
        else{
            btnPregHis.setBackgroundResource(R.drawable.button_style);
        }

        if (C.Existence("Select VStatus from tmpPregHis where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH +"' and VStatus <>'1'")) {
            btnPregHis.setBackgroundResource(R.drawable.button_style_blue);
            btnPregHis.setTextColor(Color.WHITE);
        }

//        String Note1 = C.ReturnSingleValue("Select Note from DataCorrectionNote Where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH + "'");
        if (C.Existence("select D.status from DataCorrectionNote D Where d.Vill='"+ VILL +"' and d.Bari='"+ BARI +"' and d.HH='"+ HH + "' and status='2'")) {
            btnErrorCheck.setBackgroundResource(R.drawable.button_style_red);
            btnErrorCheck.setTextColor(Color.WHITE);
        }
        else{
            btnErrorCheck.setBackgroundResource(R.drawable.button_style);
        }
    }
 private void DataSearch(String Vill, String Bari, String HH )
     {
       try
        {
             Member_DataModel d = new Member_DataModel();
             String SQL = "Select Vill, Bari, HH, MSlNo, PNo, Name, Rth, Sex, BDate,Cast(((julianday(date('now'))-julianday(BDate))/365.25) as int) as AgeY, MoNo, FaNo, Edu, MS, Ocp, Sp1, Sp2, Sp3, Sp4, Pstat, LmpDt, EnType, EnDate, ExType, ExDate, NeedReview, PosMig, PosMigDate, StartTime, EndTime, DeviceID, EntryUser, Lat, Lon, EnDt, Upload, modifyDate from "+ TableName +"  Where Vill='"+ VILL +"' and Bari='"+ BARI +"' and HH='"+ HH +"'";
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
                 map.put("needreview", item.getNeedReview());
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

         /*if (o.get("Rth").length() == 0)
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
         }*/
         ImageView review = (ImageView)convertView.findViewById(R.id.review);
         ImageView card = (ImageView)convertView.findViewById(R.id.card);

         final TextView lblReview = (TextView) findViewById(R.id.VlblReview);
         final TextView lblcard = (TextView) findViewById(R.id.VlblCCard);

         delMember.setVisibility(View.INVISIBLE);
         if (o.get("Rth").length() == 0)
         {
             MSlNo.setTextColor(Color.RED);
             Name.setTextColor(Color.RED);
             delMember.setVisibility(View.VISIBLE);
         }
         else if(o.get("ExType").trim().length()==0 & (o.get("PosMig").trim().length()==0 | !o.get("PosMig").trim().equals("54")))
         {
             MSlNo.setTextColor(Color.BLACK);
             PNo.setTextColor(Color.BLACK);
             Name.setTextColor(Color.BLACK);
             Rth.setTextColor(Color.BLACK);
             Sex.setTextColor(Color.BLACK);
             BDate.setTextColor(Color.BLACK);
             AgeY.setTextColor(Color.BLACK);
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
             MSlNo.setTextColor(Color.BLUE);
             PNo.setTextColor(Color.BLUE);
             Name.setTextColor(Color.BLUE);
             Rth.setTextColor(Color.BLUE);
             Sex.setTextColor(Color.BLUE);
             BDate.setTextColor(Color.BLUE);
             AgeY.setTextColor(Color.BLUE);
             MoNo.setTextColor(Color.BLUE);
             FaNo.setTextColor(Color.BLUE);
             Edu.setTextColor(Color.BLUE);
             MS.setTextColor(Color.BLUE);
             Pstat.setTextColor(Color.BLUE);
             LmpDt.setTextColor(Color.BLUE);
             Sp1.setTextColor(Color.BLUE);
             Sp2.setTextColor(Color.BLUE);
             Sp3.setTextColor(Color.BLUE);
             Sp4.setTextColor(Color.BLUE);
             Ocp.setTextColor(Color.BLUE);
             EnType.setTextColor(Color.BLUE);
             EnDate.setTextColor(Color.BLUE);
             ExType.setTextColor(Color.BLUE);
             ExDate.setTextColor(Color.BLUE);
             PosMig.setTextColor(Color.BLUE);
             PosMigDate.setTextColor(Color.BLUE);
         }
         else if(o.get("ExType").trim().length()>0)
         {
             MSlNo.setTextColor(Color.RED);
             PNo.setTextColor(Color.RED);
             Name.setTextColor(Color.RED);
             Rth.setTextColor(Color.RED);
             Sex.setTextColor(Color.RED);
             BDate.setTextColor(Color.RED);
             AgeY.setTextColor(Color.RED);
             MoNo.setTextColor(Color.RED);
             FaNo.setTextColor(Color.RED);
             Edu.setTextColor(Color.RED);
             MS.setTextColor(Color.RED);
             Pstat.setTextColor(Color.RED);
             LmpDt.setTextColor(Color.RED);
             Sp1.setTextColor(Color.RED);
             Sp2.setTextColor(Color.RED);
             Sp3.setTextColor(Color.RED);
             Sp4.setTextColor(Color.RED);
             Ocp.setTextColor(Color.RED);
             EnType.setTextColor(Color.RED);
             EnDate.setTextColor(Color.RED);
             ExType.setTextColor(Color.RED);
             ExDate.setTextColor(Color.RED);
         }
//-----------------------------------------------------------------------------------------------
         String ChildCard = C.ReturnSingleValue("select Process from ChildCardRequest where Vill||Bari||HH='"+ (o.get("Vill")+o.get("Bari")+o.get("HH")) +"' and MSlNo='"+ o.get("MSlNo") +"'");
         String MemReview = C.ReturnSingleValue("select NeedReview from tmpMember where Vill||Bari||HH='"+ (o.get("Vill")+o.get("Bari")+o.get("HH")) +"' and MSlNo='"+ o.get("MSlNo") +"'");

         if (o.get("ExType").trim().length()==0 & o.get("PosMig").trim().length()==0 & o.get("needreview").equals("1"))
         {
             review.setVisibility(View.VISIBLE);
             lblReview.setVisibility(View.VISIBLE);
         }
         if (o.get("ExType").trim().length()==0 & o.get("PosMig").trim().length()==0 & o.get("needreview").equals("1") & ChildCard.equals("2"))
         {
            review.setVisibility(View.VISIBLE);
            card.setVisibility(View.VISIBLE);
            lblReview.setVisibility(View.VISIBLE);
            lblcard.setVisibility(View.VISIBLE);
            Vill.setVisibility(View.GONE);
         }
         if (o.get("ExType").trim().length()==0 & o.get("PosMig").trim().length()==0 & o.get("needreview").equals("2") & ChildCard.equals("2"))
         {
             review.setVisibility(View.GONE);
//             lblReview.setVisibility(View.GONE);
             card.setVisibility(View.VISIBLE);
             lblcard.setVisibility(View.VISIBLE);
             Vill.setVisibility(View.VISIBLE);
             Vill.setText("");
         }
//-----------------------------------------------------------------------------------------------
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
                if(o.get("ExType").toString().length()>0){
                    Connection.MessageBox(Member_list.this,"সদস্য বর্তমানে সক্রিয় নয়।");
                    return;
                }else if(o.get("PosMig").equals("54")){
                    AlertDialog.Builder adb = new AlertDialog.Builder(Member_list.this);
                    adb.setTitle("54: মুছে ফেলা");
                    adb.setMessage("আপনি কি সদস্যঃ "+ o.get("Name") +" এর 54 ইভেন্ট  মুছে ফেলতে চান[হ্যাঁ/না]?");
                    adb.setNegativeButton("না", null);
                    adb.setPositiveButton("হ্যাঁ", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            C.Save("Update tmpMember set PosMig='',PosMigDate='' where Vill='" + o.get("Vill") + "' and Bari='" + o.get("Bari") + "' and HH='" + o.get("HH") + "' and MSlNo='" + o.get("MSlNo") + "'");
                            DataSearch(o.get("Vill"),o.get("Bari"),o.get("HH"));
                        }
                    });
                    adb.show();
                    return;
                }

               //Write your code here
                Bundle IDbundle = new Bundle();
                IDbundle.putString("Vill", o.get("Vill"));
                IDbundle.putString("Bari", o.get("Bari"));
                IDbundle.putString("HH", o.get("HH"));
                IDbundle.putString("MSlNo", o.get("MSlNo"));
                IDbundle.putString("roundno",ROUNDNO);
                IDbundle.putString("endate",o.get("EnDate"));
                IDbundle.putString("pno",o.get("PNo"));
                IDbundle.putString("name",o.get("Name"));

                if (o.get("Rth").length() == 0)
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
                      C.Save("Delete from tmpEvents where Vill='" + o.get("Vill") + "' and Bari='" + o.get("Bari") + "' and HH='" + o.get("HH") + "' and MSlNo='" + o.get("MSlNo") + "'");
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

     //data transfer to main tables
     //***********************************************************************************************
     private String FinalDataProcess(String Household, String Rnd)
     {
         String Err = "";
         try
         {
             //C.Save("Delete from PregHis where vill||bari||hh='"+ Household +"'");
             C.Save("Delete from SES where vill||bari||hh='"+ Household +"'");

             //Search Maximun PNo from Member table
             //-- ---------------------------------------
             String CP = C.ReturnSingleValue("Select (ifnull(max(cast(substr(pno,4,8)as int)),0)+1)MaxPno from Member where substr(pno,1,3)='"+ Global.Left(Household, 3)  +"' group by substr(pno,1,3)");
             int CPN = Integer.parseInt(CP.length()==0?"1":CP);

             Cursor cur = C.ReadData("Select Rth,Sno,PNo,ExType from tTrans where status='m' and vill||bari||hh='"+ Household +"' and length(PNo)=0 order by cast(SNO as int) asc");
             cur.moveToFirst();
             String CPNo = "";
             while(!cur.isAfterLast())
             {
                 //Generate Permanent no(PNo) for member, preghis, events table--------------------------
                 CPNo = Global.Left(Household, 3) + Global.Right("00000"+String.valueOf(CPN),5);
                 //-- --------------------------------------------------------------------------------
                 C.Save("update tTrans set PNo='"+ CPNo +"' where Status='m' and vill||bari||hh ='"+ Household +"' and SNo='"+ cur.getString(cur.getColumnIndex("Sno")) +"'");
                 C.Save("update tTrans set PNo='"+ CPNo +"' where Status='e' and vill||bari||hh ='"+ Household +"' and SNo='"+ cur.getString(cur.getColumnIndex("Sno")) +"'");
                 C.Save("update tTrans set PNo='"+ CPNo +"' where Status='p' and vill||bari||hh ='"+ Household +"' and SNo='"+ cur.getString(cur.getColumnIndex("Sno")) +"'");
                 C.Save("update ImmunizationTemp set PNo='"+ CPNo +"' Where vill||bari||hh ='"+ Household +"' and SNo='"+ cur.getString(cur.getColumnIndex("Sno")) +"'");

                 CPN += 1;
                 //-- --------------------------------------------------------------------------------
                 cur.moveToNext();
             }
             cur.close();

             //Household Table
             //-- ---------------------------------------
             String SQL="";
             String Head = "";
             //***** need current household head name
             if(C.Existence("Select vill from Household where vill||bari||hh='"+ Household +"'"))
             {
                 Head = C.ReturnSingleValue("select name from tTrans where status='m' and vill||bari||hh='"+ Household +"' and Rth='01' and (length(extype)=0 or extype is null)");
                 Cursor curH = C.ReadData("Select ContactNo,ifnull(ExType,'')as ExType,ifnull(ExDate,'')as ExDate,HHHead,Note from tTrans where status='h' and vill||bari||hh='"+ Household +"'");
                 curH.moveToFirst();
                 while(!curH.isAfterLast())
                 {
                     SQL = "Update Household set upload='2',";
                     SQL += " ExType='"+ curH.getString(curH.getColumnIndex("ExType")) +"',";
                     SQL += " Note='"+ curH.getString(curH.getColumnIndex("Note")) +"',";
                     SQL += " ContactNo='"+ curH.getString(curH.getColumnIndex("ContactNo")) +"',";
                     if(Head != null & Head.length()!=0)
                     {
                         SQL += " ExDate='"+ curH.getString(curH.getColumnIndex("ExDate")) +"',";
                         SQL += " HHHead='"+ Head +"'";
                     }
                     else
                     {
                         SQL += " ExDate='"+ curH.getString(curH.getColumnIndex("ExDate")) +"'";
                     }

                     SQL += " where vill||bari||hh='"+ Household +"'";
                     C.Save(SQL);
                     curH.moveToNext();
                 }
                 curH.close();

                 C.Save(SQL);
             }
             else
             {
                 Head = C.ReturnSingleValue("select name from tTrans where status='m' and vill||bari||hh='"+ Household +"' and Rth='01' and (length(extype)=0 or extype is null)");

                 SQL = "Insert into Household";
                 SQL += "(Vill, Bari, Hh, Pno, EnType, EnDate, ExType, ExDate, Rel, HHHead, Clust, Block, EnDt, Rnd,Upload,Note,ContactNo)";
                 SQL += " select h.Vill, h.Bari, h.Hh, h.Pno, h.EnType, h.EnDate, h.ExType, h.ExDate, h.Rel, '"+ Head +"', h.Clust, h.Block, h.EnterDt,h.Rnd,'2',Note,ContactNo";
                 SQL += " from tTrans h where h.status='h' and h.vill||h.bari||h.hh='"+ Household +"'";

                 C.Save(SQL);
             }

             //-- -Member Table-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
             SQL = "Select (case when m.vill is null then 'n' else 'o' end)as NewOld, t.Vill, t.Bari, t.Hh, t.Sno, t.Pno, t.Name, t.Rth, t.Sex, t.BDate, t.Age, t.Mono, t.Fano, t.Edu,";
             SQL += " t.Ms, t.Pstat, t.LmpDt, t.Sp1, t.Sp2, t.Sp3, t.Sp4, t.Ocp, t.EnType, t.EnDate,";
             SQL += " t.ExType, t.ExDate, t.PageNo, t.Status, t.Upload,t.PosMig,t.PosMigDate from tTrans t";
             SQL += " left outer join member m on t.vill||t.bari||t.hh||t.sno = m.vill||m.bari||m.hh||m.sno";
             SQL += " where t.status='m' and t.vill||t.bari||t.hh='"+ Household +"' order by cast(t.SNo as int) asc";

             Cursor curM = C.ReadData(SQL);
             curM.moveToFirst();
             while(!curM.isAfterLast())
             {
                 if(curM.getString(curM.getColumnIndex("NewOld")).equals("n"))
                 {
                     SQL = "Insert into Member";
                     SQL += " (Vill, Bari, Hh, Sno, Pno, Name, Rth, Sex, BDate, Age, Mono, Fano, Edu, Ms, Pstat, LmpDt, Sp1, Sp2, Sp3, Sp4, Ocp, EnType, EnDate, ExType, ExDate, PageNo, Status, Upload,PosMig,PosMigDate)";
                     SQL += " Select Vill, Bari, Hh, Sno, Pno, Name, Rth, Sex, BDate, Age, Mono, Fano, Edu, Ms, Pstat, LmpDt, Sp1, Sp2, Sp3, Sp4, Ocp, EnType, EnDate, ExType, ExDate, PageNo, Status, '2',PosMig,PosMigDate";
                     SQL += " from tTrans where vill||bari||hh='"+ Household +"' and Status='m' and SNo='"+ curM.getString(curM.getColumnIndex("Sno")) +"'";

                     C.Save(SQL);
                 }
                 else if(curM.getString(curM.getColumnIndex("NewOld")).equals("o"))
                 {
                     SQL = "Update Member Set ";
                     SQL += " Name='"+ curM.getString(curM.getColumnIndex("Name")) +"',";
                     SQL += " Rth='"+ curM.getString(curM.getColumnIndex("Rth")) +"',";
                     SQL += " Sex='"+ curM.getString(curM.getColumnIndex("Sex")) +"',";
                     SQL += " BDate='"+ curM.getString(curM.getColumnIndex("BDate")) +"',";
                     SQL += " Mono='"+ curM.getString(curM.getColumnIndex("Mono")) +"',";
                     SQL += " Fano='"+ curM.getString(curM.getColumnIndex("Fano")) +"',";
                     SQL += " Edu='"+ curM.getString(curM.getColumnIndex("Edu")) +"',";
                     SQL += " Ms='"+ curM.getString(curM.getColumnIndex("Ms")) +"',";
                     SQL += " Pstat='"+ curM.getString(curM.getColumnIndex("Pstat")) +"',";
                     SQL += " LmpDt='"+ curM.getString(curM.getColumnIndex("LmpDt")) +"',";
                     SQL += " Sp1='"+ curM.getString(curM.getColumnIndex("Sp1")) +"',";
                     SQL += " Sp2='"+ curM.getString(curM.getColumnIndex("Sp2")) +"',";
                     SQL += " Sp3='"+ curM.getString(curM.getColumnIndex("Sp3")) +"',";
                     SQL += " Sp4='"+ curM.getString(curM.getColumnIndex("Sp4")) +"',";
                     SQL += " Ocp='"+ curM.getString(curM.getColumnIndex("Ocp")) +"',";
                     SQL += " EnType='"+ curM.getString(curM.getColumnIndex("EnType")) +"',";
                     SQL += " EnDate='"+ curM.getString(curM.getColumnIndex("EnDate")) +"',";
                     SQL += " ExType='"+ curM.getString(curM.getColumnIndex("ExType")) +"',";
                     SQL += " ExDate='"+ curM.getString(curM.getColumnIndex("ExDate")) +"',";
                     SQL += " Status='C',";
                     SQL += " Upload='2',";
                     SQL += " PosMig='"+ curM.getString(curM.getColumnIndex("PosMig")) +"',";
                     SQL += " PosMigDate='"+ curM.getString(curM.getColumnIndex("PosMigDate")) +"'";

                     SQL += " where  vill||bari||hh='"+ Household +"' and SNo='"+ curM.getString(curM.getColumnIndex("Sno")) +"'";

                     C.Save(SQL);
                 }
                 curM.moveToNext();
             }
             curM.close();



             //-- -SES Table-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
             SQL = "Insert into SES";
             SQL += "(Vill, Bari, Hh, SESNo, Visit, Q015a, Q015b, Q015c, Q016a, Q016b, Q016c, Q017, Q018, Q019a, Q019b, Q019c, Q019d, Q019e, Q019f, Q019g, Q019h, Q019i, Q019j, Q019k, Q019l, Q019m, Q019n, Q019o, Q019p, Q019q, Q019r, Q019s, Q019t, Q019u, Q019v, Q019w, Q019x, Q019y, Q019z, Q020a, Q020b, Q020c, Q020d, Q020e, Q020f, Q020g, Q020h, Q021, Q022a, Q022b, Q022c, Q023a, Q023b, Q024a, Q024b, Q025a, Q025b, Q026, Q027a, Q027b, Q027c, Q027d, Q027e, Q027f, Q027g, Q027h, Q027i, Q027j, Q027y, Q027z, Q028a, Q028b, Q028c, Q028d, Q028e, Q028y, Q029, Q030a, Q030b, Q030c, Q030d, Q030e, Q030f, Q030g, Q030h, Q030z, Q031, Vdate, Rnd, PageNo, Status, Upload, Lat, Lon)";
             SQL += " Select Vill, Bari, Hh, SESNo, Visit, Q015a, Q015b, Q015c, Q016a, Q016b, Q016c, Q017, Q018, Q019a, Q019b, Q019c, Q019d, Q019e, Q019f, Q019g, Q019h, Q019i, Q019j, Q019k, Q019l, Q019m, Q019n, Q019o, Q019p, Q019q, Q019r, Q019s, Q019t, Q019u, Q019v, Q019w, Q019x, Q019y, Q019z, Q020a, Q020b, Q020c, Q020d, Q020e, Q020f, Q020g, Q020h, Q021, Q022a, Q022b, Q022c, Q023a, Q023b, Q024a, Q024b, Q025a, Q025b, Q026, Q027a, Q027b, Q027c, Q027d, Q027e, Q027f, Q027g, Q027h, Q027i, Q027j, Q027y, Q027z, Q028a, Q028b, Q028c, Q028d, Q028e, Q028y, Q029, Q030a, Q030b, Q030c, Q030d, Q030e, Q030f, Q030g, Q030h, Q030z, Q031, Vdate, Rnd, ifnull(PageNo,'')PageNo, Status, '2', ifnull(Lat,'')Lat, ifnull(Lon,'')Lon";
             SQL += " from ttrans where status='s' and vill||bari||hh='"+ Household +"'";

             C.Save(SQL);

             //-- -Pregnancy Info. Table--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
             //SQL = "Insert into PregHis(Vill, Bari, Hh, Sno, Pno, Visit, MarM, MarY, Births, LiveHh, SLiveHh, DLiveHh, LiveOut, SLiveOut, DLiveOut, Died, SDied, DDied, Abor, TAbor, TotPreg, Vdate, Rnd, PageNo, Status, Upload, Lat, Lon)";
             //SQL += " select Vill, Bari, Hh, Sno, Pno, Visit, MarM, MarY, Births, LiveHh, SLiveHh, DLiveHh, LiveOut, SLiveOut, DLiveOut, Died, SDied, DDied, Abor, TAbor, TotPreg, Vdate, Rnd, ifnull(PageNo,'')PageNo, ifnull(Status,'C')Status, ifnull(Upload,'2')Upload, ifnull(Lat,'')Lat, ifnull(Lon,'')Lon";
             //SQL += " from tTrans t where t.status='p' and t.vill||t.bari||t.hh='"+ Household +"'";

             //C.Save(SQL);

             //Pregnancy History: 03 05 2016
             Cursor pHis = C.ReadData("select Vill, Bari, Hh, Sno, Pno, Visit, MarM, MarY, Births, LiveHh, SLiveHh, DLiveHh, LiveOut, SLiveOut, DLiveOut, Died, SDied, DDied, Abor, TAbor, TotPreg, Vdate as vdate, Rnd, PageNo, Status from tTrans where status='p' and vill||bari||hh='"+ Household +"'");
             pHis.moveToFirst();
             while(!pHis.isAfterLast())
             {
                 if(C.Existence("select Vill, Bari, Hh,SNo,PNo from PregHis where vill||bari||hh='"+ Household +"' and SNo='"+ pHis.getString(pHis.getColumnIndex("Sno")) +"'"))
                 {
                     SQL = "Update PregHis set Upload='2',";
                     SQL += "Visit='"+ pHis.getString(pHis.getColumnIndex("Visit")) +"',";
                     SQL += "MarM='"+ pHis.getString(pHis.getColumnIndex("MarM")) +"',";
                     SQL += "MarY='"+ pHis.getString(pHis.getColumnIndex("MarY")) +"',";
                     SQL += "Births='"+ pHis.getString(pHis.getColumnIndex("Births")) +"',";
                     SQL += "LiveHh='"+ pHis.getString(pHis.getColumnIndex("LiveHh")) +"',";
                     SQL += "SLiveHh='"+ pHis.getString(pHis.getColumnIndex("SLiveHh")) +"',";
                     SQL += "DLiveHh='"+ pHis.getString(pHis.getColumnIndex("DLiveHh")) +"',";
                     SQL += "LiveOut='"+ pHis.getString(pHis.getColumnIndex("LiveOut")) +"',";
                     SQL += "SLiveOut='"+ pHis.getString(pHis.getColumnIndex("SLiveOut")) +"',";
                     SQL += "DLiveOut='"+ pHis.getString(pHis.getColumnIndex("DLiveOut")) +"',";
                     SQL += "Died='"+ pHis.getString(pHis.getColumnIndex("Died")) +"',";
                     SQL += "SDied='"+ pHis.getString(pHis.getColumnIndex("SDied")) +"',";
                     SQL += "DDied='"+ pHis.getString(pHis.getColumnIndex("DDied")) +"',";
                     SQL += "Abor='"+ pHis.getString(pHis.getColumnIndex("Abor")) +"',";
                     SQL += "TAbor='"+ pHis.getString(pHis.getColumnIndex("TAbor")) +"',";
                     SQL += "TotPreg='"+ pHis.getString(pHis.getColumnIndex("TotPreg")) +"',";
                     SQL += "Vdate='"+ pHis.getString(pHis.getColumnIndex("vdate")) +"'";
                     SQL += " Where vill||bari||hh='"+ Household +"' and SNo='"+ pHis.getString(pHis.getColumnIndex("Sno")) +"'";
                     C.Save( SQL );
                 }
                 else
                 {
                     SQL = "Insert into PregHis(Vill, Bari, Hh, Sno, Pno, Visit, MarM, MarY, Births, LiveHh, SLiveHh, DLiveHh, LiveOut, SLiveOut, DLiveOut, Died, SDied, DDied, Abor, TAbor, TotPreg, Vdate, Rnd, PageNo, Status, Upload, Lat, Lon)";
                     SQL += " select Vill, Bari, Hh, Sno, Pno, Visit, MarM, MarY, Births, LiveHh, SLiveHh, DLiveHh, LiveOut, SLiveOut, DLiveOut, Died, SDied, DDied, Abor, TAbor, TotPreg, Vdate, Rnd, ifnull(PageNo,'')PageNo, ifnull(Status,'C')Status, ifnull(Upload,'2')Upload, ifnull(Lat,'')Lat, ifnull(Lon,'')Lon";
                     SQL += " from tTrans t where t.status='p' and t.vill||t.bari||t.hh='"+ Household +"' and t.sno='"+ pHis.getString(pHis.getColumnIndex("Sno")) +"' ";
                     C.Save( SQL );
                 }

                 pHis.moveToNext();
             }
             pHis.close();


             //-- -Visit Table-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
             if(C.Existence("Select vill from Visits where vill||bari||hh='"+ Household +"' and Rnd ='"+ Rnd +"'")==true)
             {
                 Cursor curV = C.ReadData("select Vill, Bari, Hh, ifnull(Resp,'')Resp, Dma, EnterDt, Vdate, Rnd, Lat, Lon,ExType,ExDate,Note from tTrans where status='v' and vill||bari||hh='"+ Household +"' and Rnd ='"+ Rnd +"'");
                 curV.moveToFirst();
                 while(!curV.isAfterLast())
                 {
                     SQL = "Update Visits set Upload='2',";
                     SQL += " Rsno='"+ curV.getString(curV.getColumnIndex("Resp")) +"',";
                     SQL += " VDate='"+ curV.getString(curV.getColumnIndex("VDate")) +"',"; //date of visit
                     SQL += " Note='"+ curV.getString(curV.getColumnIndex("Note")) +"',";
                     SQL += " Dma='"+ curV.getString(curV.getColumnIndex("Dma")) +"'"; //DC code
                     SQL += " where vill||bari||hh='"+ Household +"' and Rnd='"+ Rnd +"'";
                     C.Save(SQL);

                     if(curV.getString(curV.getColumnIndex("Resp")).equals("77"))
                     {
                         SQL = "Update Household set Upload='2',ExType='"+ curV.getString(curV.getColumnIndex("ExType")) +"',ExDate='"+ curV.getString(curV.getColumnIndex("ExDate")) +"' where vill||bari||hh='"+ Household +"'";
                         C.Save(SQL);
                     }


                     //update temp table: 16 may 2016
                     SQL = "Update Visits_temp set ";
                     SQL += " Rsno='"+ curV.getString(curV.getColumnIndex("Resp")) +"',";
                     SQL += " VDate='"+ curV.getString(curV.getColumnIndex("VDate")) +"'"; //date of visit
                     SQL += " where vill||bari||hh='"+ Household +"' and Rnd='"+ Rnd +"'";
                     C.Save(SQL);

                     curV.moveToNext();
                 }
                 curV.close();
             }
             else
             {
                 Cursor curV = C.ReadData("select ifnull(Resp,'')Resp,ExType,ExDate from tTrans where status='v' and vill||bari||hh='"+ Household +"' and Rnd ='"+ Rnd +"'");
                 curV.moveToFirst();
                 while(!curV.isAfterLast())
                 {
                     if(curV.getString(curV.getColumnIndex("Resp")).equals("77"))
                     {
                         SQL = "Update Household set Upload='2',ExType='"+ curV.getString(curV.getColumnIndex("ExType")) +"',ExDate='"+ curV.getString(curV.getColumnIndex("ExDate")) +"' where vill||bari||hh='"+ Household +"'";
                         C.Save(SQL);
                     }
                     curV.moveToNext();
                 }
                 curV.close();

                 SQL = "Insert into Visits(Vill, Bari, Hh, Rsno, Dma, EnterDt, Vdate, Rnd, Lat, Lon,LatNet,LonNet,Upload,Note)";
                 SQL += " Select Vill, Bari, Hh, Resp, Dma, EnterDt, Vdate, Rnd, Lat, Lon,LatNet,LonNet,'2',Note from tTrans where status='v' and vill||bari||hh='"+ Household +"'";
                 C.Save(SQL);

                 //update temp data
                 SQL = "Insert into Visits_temp(Vill, Bari, Hh, Rsno, Vdate, Rnd)";
                 SQL += " Select Vill, Bari, Hh, Resp, Vdate, Rnd from tTrans where status='v' and vill||bari||hh='"+ Household +"'";
                 C.Save(SQL);

             }

             //-- -Event Table----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
             SQL = "Insert into Events";
             SQL += " (Vill,Bari,Hh,Pno,Sno,EvType,EvDate,Info1,Info2,Info3,Info4,VDate,Rnd,Upload)";
             SQL += " Select Vill,Bari,HH,PNo,SNo,EvType,EvDate,ifnull(Info1,'')Info1,ifnull(Info2,'')Info2,ifnull(Info3,'')Info3,ifnull(Info4,'')Info4,VDate,Rnd,'2'";
             SQL += " from tTrans where Status='e' and vill||bari||hh='"+ Household +"'";
             C.Save(SQL);


             //Need to create/remove a record in migration database
             String migSQL="";
             String evtype="";
             Cursor curMig = C.ReadData("select t.EvType evtype,(t.vill||t.bari||t.HH) hh,t.SNo sno,t.PNo pno,m.Name name,t.EvDate exdate from tTrans t,Member m where t.vill||t.bari||t.hh=m.vill||m.bari||m.hh and t.sno=m.sno and t.status='e' and t.vill||t.bari||t.hh='"+ Household +"' and t.Rnd ='"+ Rnd +"' and t.EvType in('52','22','53','23')");
             curMig.moveToFirst();
             while(!curMig.isAfterLast())
             {
                 evtype = curMig.getString(curMig.getColumnIndex("evtype")).toString();

                 //insert data in to migration table
                 if(evtype.equals("52") | evtype.equals("53"))
                 {
                     migSQL = "Insert into MigDatabase(ExType,HH,SNo,PNo,Name,ExDate)Values(";
                     migSQL += "'"+ curMig.getString(curMig.getColumnIndex("evtype")) +"',";
                     migSQL += "'"+ curMig.getString(curMig.getColumnIndex("hh")) +"',";
                     migSQL += "'"+ curMig.getString(curMig.getColumnIndex("sno")) +"',";
                     migSQL += "'"+ curMig.getString(curMig.getColumnIndex("pno")) +"',";
                     migSQL += "'"+ curMig.getString(curMig.getColumnIndex("name")) +"',";
                     migSQL += "'"+ curMig.getString(curMig.getColumnIndex("exdate")) +"')";
                     C.Save(migSQL);
                 }

                 //remove data in to migration table
                 else if(evtype.equals("22"))
                 {
                     C.Save("Delete from MigDatabase where pno='"+ curMig.getString(curMig.getColumnIndex("pno")) +"' and extype='52'");
                 }

                 //remove data in to migration table
                 else if(evtype.equals("23"))
                 {
                     C.Save("Delete from MigDatabase where pno='"+ curMig.getString(curMig.getColumnIndex("pno")) +"' and extype='53'");
                 }
                 curMig.moveToNext();
             }
             curMig.close();

             //-- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

             C.Save("Delete from tTrans where vill||bari||hh='"+ Household +"'");

         }
         catch(Exception ex)
         {
             Err = ex.getMessage();
         }

         return Err;
     }

 }

    private String ProcessTransaction(String Vill, String Bari, String HH, String Rnd)
    {
        String SQLS = "";
        String Household = Vill + Bari + HH;
        ErrMsg = "";

        String TotMember = C.ReturnSingleValue("Select COUNT(*)TotMember from Member m where length(exType)=0  and m.Vill='"+ VILL +"' and m.Bari='"+ BARI +"'and m.HH='"+ HH +"' group by m.Vill,m.Bari,m.HH");
        C.Save("Update Household set TotMem='"+TotMember+"' where vill||bari||hh='"+ HH +"'");

        if(!g.getRsNo().equals("77"))
        {
            //household member available/not
            if(!C.Existence("Select vill from tmpMember where vill||bari||hh='"+ Household +"' and (extype is null or length(extype)=0)"))
            {
                ErrMsg += "\n-> খানায় কমপক্ষে একজন সদস্য সক্রিয় থাকতে হবে।";
            }
            //at least one household head should be available
            if(!C.Existence("select vill from tmpMember where vill||bari||hh='"+ Household +"' and rth='01' and (extype is null or length(extype)=0)"))
            {
                ErrMsg += "\n-> খানায় একজন খানা প্রধান অবশ্যই থাকতে হবে।";
            }
            //only one active household head applicable
            if(C.Existence("select count(*) from tmpMember where vill||bari||hh='"+ Household +"' and rth='01' and (extype is null or length(extype)=0) group by vill||bari||hh having count(*)>1"))
            {
                ErrMsg += "\n-> খানায় একের বেশী খানা প্রধান থাকতে পারে না।";
            }

            String infoMiss = C.ReturnSingleValue("Select count(*)TotalMiss from tmpMember where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "' and length(Sex)=0");

            if (Integer.valueOf(infoMiss) > 0)
            {
                ErrMsg += "\n-> খানার সদস্য তথ্য আপডেট করা বাকি আছে";
            }

            if (!C.Existence("Select PNo from tmpMember where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'")) {
                ErrMsg += "\n-> কমপক্ষে একজন সদস্য এন্ট্রি করতে হবে.";
            }

            if (!C.Existence("Select VStatus from tmpSES where Vill='" + VILL + "' and Bari='" + BARI + "' and HH='" + HH + "'")) {
                ErrMsg += "\n-> খানার তথ্য  খালি রাখা যাবেনা";
            }
        }

        //Not pregnant event(40) missing
        SQLS = "SELECT M.MslNo as sno,M.NAME as name";
        SQLS += " FROM tmpMember M WHERE  M.VILL||M.BARI||M.HH='"+ Household +"' AND M.MS='31' AND cast((julianday(date('now'))-julianday(bdate))/365.25 as int)<50  ";
        SQLS += " AND M.SEX='2' AND ifnull(M.PSTAT,'0')<>'41' AND (EXTYPE IS NULL OR LENGTH(EXTYPE)=0) and (posmig IS NULL OR LENGTH(posmig)=0) AND NOT EXISTS";
        SQLS += " (SELECT VILL,BARI,HH,MSlNo,PNO,EVTYPE,RND FROM tmpEvents WHERE vill||bari||HH=m.vill||m.bari||M.HH AND MSlNo=M.MSlNo AND EVTYPE IN('40','49') AND RND='"+ ROUNDNO +"'";
        SQLS += " UNION SELECT VILL,BARI,HH,MSlNo,PNO,EVTYPE,RND FROM EVENTS WHERE EVTYPE IN('40','49') AND RND='"+ ROUNDNO +"' AND PNO=M.PNO)";

        Cursor cur40 = C.ReadData(SQLS);
        cur40.moveToFirst();
        while(!cur40.isAfterLast())
        {
            ErrMsg += "\n-> ইভেন্ট ৪০/৪৯ ঘটানো হয় নাই (সিরিয়াল নাম্বার= "+  cur40.getString(cur40.getColumnIndex("sno")) +" নাম= "+ cur40.getString(cur40.getColumnIndex("name")) +" ).";

            cur40.moveToNext();
        }
        cur40.close();

        //Pregnancy history missing
        SQLS  = "select MSlNo as sno, (case when pno is null or length(pno)=0 then 'pno' else pno end)as pno, t.Name as name from tmpMember t where ";
        SQLS += " t.Vill||t.Bari||t.Hh='"+ Household +"' and length(extype)=0 and length(posmig)=0";
        SQLS += " and t.Sex='2' and t.ms<>'30' and ((julianday(date('now'))-julianday(t.bdate))/365.25)<50";

        Cursor curphis = C.ReadData(SQLS);
        curphis.moveToFirst();
        while(!curphis.isAfterLast())
        {
            if(!C.Existence("select vill from tmpPregHis where vill||bari||hh='"+ Household +"' and MSlNo='"+ curphis.getString(curphis.getColumnIndex("sno")) +"'") & !C.Existence("select vill from PregHis where pno='"+ curphis.getString(curphis.getColumnIndex("pno")) +"'"))
            {
                ErrMsg += "\n-> গর্ভের ইতিহাস সংগ্রহ করা হয় নাই (সিরিয়াল নাম্বার= "+  curphis.getString(curphis.getColumnIndex("sno")) +" নাম= "+ curphis.getString(curphis.getColumnIndex("name")) +" ).";
            }
            curphis.moveToNext();
        }
        curphis.close();

    //==================================================================================================

        //Member is not eligible for the given marital status
        SQLS  = "select MSlNo as sno, (case when pno is null or length(pno)=0 then 'pno' else pno end)as pno, t.Name as name from tmpMember t where ";
        SQLS += " t.Vill||t.Bari||t.Hh='"+ Household +"' and length(t.extype)=0 and length(t.posmig)=0";
        SQLS += " and t.ms<>'30' and ((julianday(date('now'))-julianday(t.bdate))/365.25)<10";
        Cursor CR = C.ReadData(SQLS);
        CR.moveToFirst();
        while(!CR.isAfterLast())
        {
            ErrMsg += "\n-> বয়স ১০ এর নিচে হলে বৈবাহিক অবস্থা কখনও বিয়ে হয়নি হবে(সিরিয়াল নাম্বার= "+  CR.getString(CR.getColumnIndex("sno")) +" নাম= "+ CR.getString(CR.getColumnIndex("name")) +" ).";

            CR.moveToNext();
        }
        CR.close();

        //Mother number is available but mother not available in member list

        SQLS  = "select MSlNo as sno, (case when pno is null or length(pno)=0 then 'pno' else pno end)as pno, t.Name as name from tmpMember t where ";
        SQLS += " t.Vill||t.Bari||t.Hh='"+ Household +"' and length(t.extype)=0 and length(t.posmig)=0";
        SQLS += " and t.mono <>'00' and not exists";
        SQLS += " (select * from tmpMember where VILL||BARI||hh=t.Vill||t.Bari||t.Hh and Mslno=t.mono and sex='2')";

        Cursor CRMoNo = C.ReadData(SQLS);
        CRMoNo.moveToFirst();
        while(!CRMoNo.isAfterLast())
        {
            ErrMsg += "\n-> মা এর সিরিয়াল নং ঠিক নাই  (সিরিয়াল নাম্বার= "+  CRMoNo.getString(CRMoNo.getColumnIndex("sno")) +" নাম= "+ CRMoNo.getString(CRMoNo.getColumnIndex("name")) +" ).";

            CRMoNo.moveToNext();
        }
        CRMoNo.close();

        //Father number is available but Father not available in Member list

        SQLS  = "select MSlNo as sno, (case when pno is null or length(pno)=0 then 'pno' else pno end)as pno, t.Name as name from tmpMember t where ";
        SQLS += " t.Vill||t.Bari||t.Hh='"+ Household +"' and length(t.extype)=0 and length(t.posmig)=0";
        SQLS += " and t.fano <>'00' and not exists";
        SQLS += " (select * from tmpMember where VILL||BARI||hh=t.Vill||t.Bari||t.Hh and Mslno=t.fano and sex='1')";

        Cursor CRFaNo = C.ReadData(SQLS);
        CRFaNo.moveToFirst();
        while(!CRFaNo.isAfterLast())
        {
            ErrMsg += "\n-> বাবার সিরিয়াল নং ঠিক নাই (সিরিয়াল নাম্বার= "+  CRFaNo.getString(CRFaNo.getColumnIndex("sno")) +" নাম= "+ CRFaNo.getString(CRFaNo.getColumnIndex("name")) +" ).";

            CRFaNo.moveToNext();
        }
        CRFaNo.close();

        //Child of household head but father/mother serial missing

        SQLS  = "select MSlNo as sno, (case when pno is null or length(pno)=0 then 'pno' else pno end)as pno, t.Name as name from tmpMember t where ";
        SQLS += " t.Vill||t.Bari||t.Hh='"+ Household +"' and length(t.extype)=0 and length(t.posmig)=0";
        SQLS += " and t.rth='03'";
        SQLS += " and (cast(t.fano as int)=0 and cast(t.mono as int)=0) and exists";
        SQLS += " (select * from tmpMember where VILL||BARI||hh=t.Vill||t.Bari||t.Hh and rth='01' and length(extype)=0)";

        Cursor CRMoNoFaNo = C.ReadData(SQLS);
        CRMoNoFaNo.moveToFirst();
        while(!CRMoNoFaNo.isAfterLast())
        {
            ErrMsg += "\n-> খানা প্রধানের সাথে সম্পর্ক  ছেলে/মেয়ে হলে বাবা/মা এর সিরিয়াল নং ০০ হবেনা (সিরিয়াল নাম্বার= "+  CRMoNoFaNo.getString(CRMoNoFaNo.getColumnIndex("sno")) +" নাম= "+ CRMoNoFaNo.getString(CRMoNoFaNo.getColumnIndex("name")) +" ).";

            CRMoNoFaNo.moveToNext();
        }
        CRMoNoFaNo.close();

        //Professional but education < 10 class passed
//        SQLS  = "select MSlNo as sno, (case when pno is null or length(pno)=0 then 'pno' else pno end)as pno, t.Name as name from tmpMember t where ";
//        SQLS += " t.Vill||t.Bari||t.Hh='"+ Household +"' and length(t.extype)=0 and length(t.posmig)=0";
//        SQLS += " and t.ocp='34' and t.edu<'05'";
//
//        Cursor CROcp = C.ReadData(SQLS);
//        CROcp.moveToFirst();
//        while(!CROcp.isAfterLast())
//        {
//            ErrMsg += "\n-> পেশা কোড পেশাজীবি হলে সর্বোচ্চ শ্রেণি পাশ 0৬ এর নিচে হবেনা (সিরিয়াল নাম্বার= "+  CROcp.getString(CROcp.getColumnIndex("sno")) +" নাম= "+ CROcp.getString(CROcp.getColumnIndex("name")) +" ).";
//            CROcp.moveToNext();
//        }
//        CROcp.close();

        // Add on 23_07_2017-------------------------------------------------------------------------------

        //Occupation (31): Intellectual but education =00
        SQLS  = "select MSlNo as sno, (case when pno is null or length(pno)=0 then 'pno' else pno end)as pno, t.Name as name from tmpMember t where ";
        SQLS += " t.Vill||t.Bari||t.Hh='"+ Household +"' and length(t.extype)=0 and length(t.posmig)=0";
        SQLS += " and t.ocp='31' and t.edu='00'";

        Cursor CROcp1 = C.ReadData(SQLS);
        CROcp1.moveToFirst();
        while(!CROcp1.isAfterLast())
        {
            ErrMsg += "\n-> পেশা মেধাসম্পন্ন এর জন্য শিক্ষা 00 হতে পারে না। (সিরিয়াল নাম্বার= "+  CROcp1.getString(CROcp1.getColumnIndex("sno")) +" নাম= "+ CROcp1.getString(CROcp1.getColumnIndex("name")) +" ).";
            CROcp1.moveToNext();
        }
        CROcp1.close();

        //Housewife but male person
        SQLS  = "select MSlNo as sno, (case when pno is null or length(pno)=0 then 'pno' else pno end)as pno, t.Name as name from tmpMember t where ";
        SQLS += " t.Vill||t.Bari||t.Hh='"+ Household +"' and length(t.extype)=0 and length(t.posmig)=0";
        SQLS += " and t.ocp='03' and t.sex='1'";

        Cursor CRHHWife = C.ReadData(SQLS);
        CRHHWife.moveToFirst();
        while(!CRHHWife.isAfterLast())
        {
            ErrMsg += "\n-> পেশা গৃহিনী হলে সদস্য পুরুষ হবেনা(সিরিয়াল নাম্বার= "+  CRHHWife.getString(CRHHWife.getColumnIndex("sno")) +" এবং নাম= "+ CRHHWife.getString(CRHHWife.getColumnIndex("name")) +" ).";
            CRHHWife.moveToNext();
        }
        CRHHWife.close();

        // Add on 01_09_2017-------------------------------------------------------------------------------
        //Delivery: Live birth delivery occured but child dose not exist
        SQLS  = "select a.MSlNo as sno,(case when a.pno is null or length(a.pno)=0 then 'pno' else a.pno end)as pno from tmpevents a left join ";
        SQLS += " (select e.*,mo.mono from tmpevents e,tmpMember mo where e.vill||e.bari||e.hh=mo.vill||mo.bari||mo.hh and e.Mslno=mo.Mslno and evtype='25') b";
        SQLS += " on a.vill||a.bari||a.hh||a.Mslno=b.vill||b.bari||b.hh||b.mono";
        SQLS += " where a.evtype='42' and a.info1 in('12', '22', '23', '32', '33', '34') and case when a.vill||a.bari||a.hh||a.Mslno=b.vill||b.bari||b.hh||b.mono then 1 else 0 end=0";

        Cursor CRLBMiss = C.ReadData(SQLS);
        CRLBMiss.moveToFirst();
        while(!CRLBMiss.isAfterLast())
        {
            ErrMsg += "\n-> জীবিত জন্ম হয়েছে কিন্তু ইভেন্ট ২৫ ঘটানো হয়নি (সিরিয়াল নাম্বার= "+  CRLBMiss.getString(CRLBMiss.getColumnIndex("sno")) +" ).";
            CRLBMiss.moveToNext();
        }
        CRLBMiss.close();

        //Birth & Event Date: Birth date not same Event Date
        SQLS  = "select a.MSlNo as sno,(case when a.pno is null or length(a.pno)=0 then 'pno' else a.pno end)as pno,d.name as name from tmpevents a left join ";
        SQLS += " tmpMember d ";
        SQLS += " on a.pno=d.pno";
        SQLS += " where d.bdate<>a.evdate and a.evtype='25'";

        Cursor CRBDate = C.ReadData(SQLS);
        CRBDate.moveToFirst();
        while(!CRBDate.isAfterLast())
        {
            ErrMsg += "\n-> সদস্যের জন্ম তারিখ এবং ঘটনার তারিখ এক নয় (সিরিয়াল নাম্বার= "+  CRBDate.getString(CRBDate.getColumnIndex("sno"))  +" এবং নাম= "+ CRBDate.getString(CRBDate.getColumnIndex("name")) +" ).";
            CRBDate.moveToNext();
        }
        CRBDate.close();

        //Member Spouse marital status Unmaried ============================================
        SQLS  = "select a.MSlNo as sno,(case when a.pno is null or length(a.pno)=0 then 'pno' else a.pno end)as pno,a.name as name from tmpMember a left join ";
        SQLS += " tmpMember d ";
        SQLS += "on a.vill||a.bari||a.hh||a.Sp1=d.vill||d.bari||d.hh||d.MSlNo and d.MS='30'";
        SQLS += " where length(d.extype)=0";

        Cursor CRSPsl = C.ReadData(SQLS);
        CRSPsl.moveToFirst();
        while(!CRSPsl.isAfterLast())
        {
            ErrMsg += "\n-> সিরিয়াল নাম্বার= "+  CRSPsl.getString(CRSPsl.getColumnIndex("sno"))  +" এবং নাম= "+ CRSPsl.getString(CRSPsl.getColumnIndex("name")) +"এই সদস্যের যে স্বামী/স্ত্রী তার বৈবাহিক অবস্থা অবিবাহিত আছে ).";
            CRSPsl.moveToNext();
        }
        CRSPsl.close();

        //Member Spouse Sex Same ============================================
        SQLS  = "select a.MSlNo as sno,(case when a.pno is null or length(a.pno)=0 then 'pno' else a.pno end)as pno,a.name as name from tmpMember a left join ";
        SQLS += " tmpMember d ";
        SQLS += "on a.vill||a.bari||a.hh||a.Sp1=d.vill||d.bari||d.hh||d.MSlNo and d.sex=a.sex";
        SQLS += " where length(d.extype)=0";

        Cursor CRSpSex = C.ReadData(SQLS);
        CRSpSex.moveToFirst();
        while(!CRSpSex.isAfterLast())
        {
            ErrMsg += "\n-> সিরিয়াল নাম্বার= "+  CRSpSex.getString(CRSpSex.getColumnIndex("sno"))  +" এবং নাম= "+ CRSpSex.getString(CRSpSex.getColumnIndex("name")) +"এই সদস্যের যে স্বামী/স্ত্রী তার লিঙ্গ একই ).";
            CRSpSex.moveToNext();
        }
        CRSpSex.close();

        //Member Spouse serial 00 ============================================
        SQLS  = "select a.MSlNo as sno,(case when a.pno is null or length(a.pno)=0 then 'pno' else a.pno end)as pno,a.name as name from tmpMember a left join ";
        SQLS += " tmpMember d ";
        SQLS += "on a.vill||a.bari||a.hh||a.Sp1=d.vill||d.bari||d.hh||d.MSlNo and d.sp1='00'";
        SQLS += " where length(d.extype)=0";

        Cursor CRSPsl1 = C.ReadData(SQLS);
        CRSPsl1.moveToFirst();
        while(!CRSPsl1.isAfterLast())
        {
            ErrMsg += "\n-> সিরিয়াল নাম্বার= "+  CRSPsl1.getString(CRSPsl1.getColumnIndex("sno"))  +" এবং নাম= "+ CRSPsl1.getString(CRSPsl1.getColumnIndex("name")) +"এই সদস্যের যে স্বামী/স্ত্রী তার (স্বামী/স্ত্রীর সিরিয়াল1) নাম্বার 00 আছে  ).";
            CRSPsl1.moveToNext();
        }
        CRSPsl1.close();

        //Member Spouse serial Blank ============================================
        SQLS  = "select a.MSlNo as sno,(case when a.pno is null or length(a.pno)=0 then 'pno' else a.pno end)as pno,a.name as name from tmpMember a left join ";
        SQLS += " tmpMember d ";
        SQLS += "on a.vill||a.bari||a.hh||a.Sp1=d.vill||d.bari||d.hh||d.MSlNo and d.sp1=''";
        SQLS += " where length(d.extype)=0";

        Cursor CRSPsl2 = C.ReadData(SQLS);
        CRSPsl2.moveToFirst();
        while(!CRSPsl2.isAfterLast())
        {
            ErrMsg += "\n-> সিরিয়াল নাম্বার= "+  CRSPsl2.getString(CRSPsl2.getColumnIndex("sno"))  +" এবং নাম= "+ CRSPsl2.getString(CRSPsl2.getColumnIndex("name")) + " এই সদস্যের যে স্বামী/স্ত্রী তার (স্বামী/স্ত্রীর সিরিয়াল1) নাম্বার খালি আছে ).";
            CRSPsl2.moveToNext();
        }
        CRSPsl2.close();

        //Husband/Wife of Household Head but Sp sl 00============================================
        SQLS  = "select a.MSlNo as sno,(case when a.pno is null or length(a.pno)=0 then 'pno' else a.pno end)as pno,a.name as name from tmpMember a ";
        SQLS += " where a.MS='31' and a.sp1='00' and length(a.extype)=0 and a.rth='02'";

        Cursor CRSPsl3 = C.ReadData(SQLS);
        CRSPsl3.moveToFirst();
        while(!CRSPsl3.isAfterLast())
        {
            ErrMsg += "\n-> সিরিয়াল নাম্বার= "+  CRSPsl3.getString(CRSPsl3.getColumnIndex("sno"))  +"  নাম= "+ CRSPsl3.getString(CRSPsl3.getColumnIndex("name")) + "খানা প্রধানের স্বামী/স্ত্রী কিন্তু তার (স্বামী/স্ত্রীর সিরিয়াল1) নাম্বার 00 আছে ).";
            CRSPsl3.moveToNext();
        }
        CRSPsl3.close();

        //Marital status can not be 30 for the following member's============================================
        SQLS  = "select a.MSlNo as sno,(case when a.pno is null or length(a.pno)=0 then 'pno' else a.pno end)as pno,a.name as name from tmpMember a ";
        SQLS += " where a.rth in('02','04','07','10','11','15','17') and a.MS='30'";

        Cursor CRSMS = C.ReadData(SQLS);
        CRSMS.moveToFirst();
        while(!CRSMS.isAfterLast())
        {
            ErrMsg += "\n-> সিরিয়াল নাম্বার= "+  CRSMS.getString(CRSMS.getColumnIndex("sno"))  +"  নাম= "+ CRSMS.getString(CRSMS.getColumnIndex("name")) + " এই সদস্যের সম্পর্ক কোড এবং বৈবাহিক অবস্থার কোড চেক করুন).";
            CRSMS.moveToNext();
        }
        CRSMS.close();


        //Father number is available but father is not in member list

//        SQLS  = "select MSlNo as sno, (case when pno is null or length(pno)=0 then 'pno' else pno end)as pno, t.Name as name from tmpMember t where ";
//        SQLS += " t.Vill||t.Bari||t.Hh='"+ Household +"' and length(extype)=0 and length(posmig)=0";
//        SQLS += " and t.rth='01' ";
//        SQLS += " and length(t.extype)=0 and cast(t.fano as int)<>0 and not exists";
//        SQLS += " (select * from tmpMember where vill||bari||hh=t.vill||t.Bari||t.hh and Mslno=t.fano and rth='04' and sex='1' and length(extype)=0)";
//
//        Cursor HeadFaNo = C.ReadData(SQLS);
//        HeadFaNo.moveToFirst();
//        while(!HeadFaNo.isAfterLast())
//        {
//            ErrMsg += "\n-> খানা প্রধানের বাবার সিরিয়াল নং সঠিক নয়  (সিরিয়াল নাম্বার= "+  HeadFaNo.getString(HeadFaNo.getColumnIndex("sno")) +" নাম= "+ HeadFaNo.getString(HeadFaNo.getColumnIndex("name")) +" ).";
//            HeadFaNo.moveToNext();
//        }
//        HeadFaNo.close();

        //Father of household head is present (04), but father serial no is missing

//        SQLS  = "select MSlNo as sno, (case when pno is null or length(pno)=0 then 'pno' else pno end)as pno, t.Name as name from tmpMember t where ";
//        SQLS += " t.Vill||t.Bari||t.Hh='"+ Household +"' and length(extype)=0 and length(posmig)=0";
//        SQLS += " and t.rth='01' and exists";
//        SQLS += " (select * from tmpMember where vill||bari||hh=t.vill||t.Bari||t.hh and rth='04' and sex='1' and length(extype)=0)";
//        SQLS += " and cast(t.fano as int)=0)";
//
//        Cursor CRHeadFaNo = C.ReadData(SQLS);
//        CRHeadFaNo.moveToFirst();
//        while(!CRHeadFaNo.isAfterLast())
//        {
//            ErrMsg += "\n-> খানা প্রধানের বাবা খানায় উপস্থিত থাকলে বাবার সিরিয়াল নং ০০ হবেনা (সিরিয়াল নাম্বার= "+  CRHeadFaNo.getString(CRHeadFaNo.getColumnIndex("sno")) +" নাম= "+ CRHeadFaNo.getString(CRHeadFaNo.getColumnIndex("name")) +" ).";
//
//            CRHeadFaNo.moveToNext();
//        }
//        CRHeadFaNo.close();

        //Mother of household head is present (04), but Mother serial no is missing

//        SQLS  = "select MSlNo as sno, (case when pno is null or length(pno)=0 then 'pno' else pno end)as pno, t.Name as name from tmpMember t where ";
//        SQLS += " t.Vill||t.Bari||t.Hh='"+ Household +"' and length(extype)=0 and length(posmig)=0";
//        SQLS += " and t.rth='01' and exists";
//        SQLS += " (select * from tmpMember where vill||bari||hh=t.vill||t.Bari||t.hh and rth='04' and sex='2' and length(extype)=0)";
//        SQLS += " and cast(t.mono as int)=0)";
//
//        Cursor CRHeadMoNo = C.ReadData(SQLS);
//        CRHeadMoNo.moveToFirst();
//        while(!CRHeadMoNo.isAfterLast())
//        {
//            ErrMsg += "\n-> খানা প্রধানের মা খানায় উপস্থিত থাকলে মায়ের সিরিয়াল নং ০০ হবেনা (সিরিয়াল নাম্বার= "+  CRHeadMoNo.getString(CRHeadMoNo.getColumnIndex("sno")) +" নাম= "+ CRHeadMoNo.getString(CRHeadMoNo.getColumnIndex("name")) +" ).";
//
//            CRHeadMoNo.moveToNext();
//        }
//        CRHeadMoNo.close();

         //age of last SES collection
//        if(C.Existence("select vill from tmpSES where vill||bari||hh='"+ Household +"'"))
//        {
//            //***need to collect ses again from 22 rnd
//            //int sesage = Integer.parseInt(C.ReturnSingleValue("select cast((julianday(date('now'))-julianday(vdate))/365.25 as int)ageses from tTrans where status='s' and vill||bari||hh='"+ Household +"' order by sesno desc limit 1"));
//            //if(sesage >= 3)
//            //	ErrMsg += "-> SES এর বয়স ৩ বছরের বেশী হয়েছে, আবার সংগ্রহ করতে হবে।\n";
//        }
//        else
//        {
//            ErrMsg += "\n-> SES এর তথ্য সংগ্রহ করতে হবে।";
//        }


        //Stop process if any error have
         /*if (ErrMsg.length()!=0)
         {
             return ErrMsg;
         }
         else
         {
             return FinalDataProcess(Household, Rnd);
         }*/

        return ErrMsg;
    }

 //Data Transfer to Main Table
 //************************************************
    //Table Name: Visits
    private void VisitsDataTransfer(String Vill, String Bari, String HH, String Rnd)
    {
        Visits_DataModel d = new Visits_DataModel();
        String SQL = "Select * from tmpVisits  Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"' and Rnd='"+ Rnd +"'";
        List<Visits_DataModel> data = d.SelectAll(this, SQL);
        for(Visits_DataModel item : data){
            Visits_DataModel_Main objSave = new Visits_DataModel_Main();
            objSave.setVill(Vill);
            objSave.setBari(Bari);
            objSave.setHH(HH);
            objSave.setVDate(item.getVDate());
            objSave.setVStatus(item.getVStatus());
            objSave.setVStatusOth(item.getVStatusOth());
            objSave.setResp(item.getResp());
            objSave.setRnd(item.getRnd());

            objSave.setStartTime(item.getStartTime());
            objSave.setEndTime(item.getEndTime());
            objSave.setDeviceID(item.getDeviceID());
            objSave.setEntryUser(item.getEntryUser());
            objSave.setLat(MySharedPreferences.getValue(Member_list.this,"lat"));
            objSave.setLon(MySharedPreferences.getValue(Member_list.this,"lon"));

//            objSave.setLat(item.getLat());
//            objSave.setLon(item.getLon());
            objSave.setEnDt(item.getEnDt());

            String status = objSave.SaveUpdateData(this);
        }
    }

    //Table Name: Household
    private void HouseholdDataTransfer(String Vill, String Bari, String HH)
    {
        Household_DataModel d = new Household_DataModel();
        String SQL = "Select * from tmpHousehold  Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"'";
        List<Household_DataModel> data = d.SelectAll(this, SQL);
        for(Household_DataModel item : data){
            Household_DataModel_Main objSave = new Household_DataModel_Main();
            objSave.setVill(Vill);
            objSave.setBari(Bari);
            objSave.setHH(HH);
            objSave.setReligion(item.getReligion());
            objSave.setMobileNo1(item.getMobileNo1());
            objSave.setMobileNo2(item.getMobileNo2());
            objSave.setHHHead(item.getHHHead());
            objSave.setTotMem(item.getTotMem());
            objSave.setTotRWo(item.getTotRWo());
            objSave.setEnType(item.getEnType());
            objSave.setEnDate(item.getEnDate());
            objSave.setExType(item.getExType());
            objSave.setExDate(item.getExDate());
            objSave.setRnd(item.getRnd());

            objSave.setEnDt(item.getEnDt());
            objSave.setStartTime(item.getStartTime());
            objSave.setEndTime(item.getEndTime());
            objSave.setDeviceID(item.getDeviceID());
            objSave.setEntryUser(item.getEntryUser());
            objSave.setLat(MySharedPreferences.getValue(Member_list.this,"lat"));
            objSave.setLon(MySharedPreferences.getValue(Member_list.this,"lon"));

//            objSave.setLat(item.getLat());
//            objSave.setLon(item.getLon());
            objSave.setEnDt(item.getEnDt());

            String status = objSave.SaveUpdateData(this);
        }
    }

    //Table Name: Member
    private void MemberDataTransfer(String Vill, String Bari, String HH)
    {
        Member_DataModel d = new Member_DataModel();
        String SQL = "Select * from tmpMember  Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"'";
        List<Member_DataModel> data = d.SelectAll(this, SQL);
        for(Member_DataModel item : data){
            Member_DataModel_Main objSave = new Member_DataModel_Main();
            objSave.setVill(Vill);
            objSave.setBari(Bari);
            objSave.setHH(HH);
            objSave.setMSlNo(item.getMSlNo());
            objSave.setPNo(item.getPNo());
            objSave.setName(item.getName());
            objSave.setRth(item.getRth());
            objSave.setSex(item.getSex());
            objSave.setBDate(item.getBDate());
            objSave.setAgeY(item.getAgeY());
            objSave.setMoNo(item.getMoNo());
            objSave.setFaNo(item.getFaNo());
            objSave.setEdu(item.getEdu());
            objSave.setMS(item.getMS());
            objSave.setOcp(item.getOcp());
            objSave.setSp1(item.getSp1());
            objSave.setSp2(item.getSp2());
            objSave.setSp3(item.getSp3());
            objSave.setSp4(item.getSp4());

            objSave.setPstat(item.getPstat());
            objSave.setLmpDt(item.getLmpDt());

            objSave.setEnType(item.getEnType());
            objSave.setEnDate(item.getEnDate());
            objSave.setExType(item.getExType());
            objSave.setExDate(item.getExDate());
            objSave.setPosMig(item.getPosMig());
            objSave.setPosMigDate(item.getPosMigDate());
            objSave.setNeedReview(item.getNeedReview());

            objSave.setEnDt(item.getEnDt());
            objSave.setStartTime(item.getStartTime());
            objSave.setEndTime(item.getEndTime());
            objSave.setDeviceID(item.getDeviceID());
            objSave.setEntryUser(item.getEntryUser()); //from data entry user list
            objSave.setLat(item.getLat());
            objSave.setLon(item.getLon());

            String status = objSave.SaveUpdateData(this);
        }
    }

    //Table Name: SES
    private void SESDataTransfer(String Vill, String Bari, String HH)
    {
        SES_DataModel d = new SES_DataModel();
        String SQL = "Select * from tmpSES  Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"'";
        List<SES_DataModel> data = d.SelectAll(this, SQL);
        for(SES_DataModel item : data){
            SES_DataModel_Main objSave = new SES_DataModel_Main();
            objSave.setVill(item.getVill());
            objSave.setBari(item.getBari());
            objSave.setHH(item.getHH());
            objSave.setSESNo(item.getSESNo());
            objSave.setVDate(item.getVDate());
            objSave.setVStatus(item.getVStatus());
            objSave.setVStatusOth(item.getVStatusOth());
            objSave.setRnd(item.getRnd());
            objSave.setWSDrink(item.getWSDrink());
            objSave.setWSDrinkOth(item.getWSDrinkOth());
            objSave.setWSCook(item.getWSCook());
            objSave.setWSCookOth(item.getWSCookOth());
            objSave.setWSWash(item.getWSWash());
            objSave.setWSWashOth(item.getWSWashOth());
            objSave.setLatrine(item.getLatrine());
            objSave.setLatrineOth(item.getLatrineOth());

            objSave.setElectricity(item.getElectricity());
            objSave.setRadio(item.getRadio());
            objSave.setTV(item.getTV());
            objSave.setMobile(item.getMobile());
            objSave.setTelephone(item.getTelephone());
            objSave.setRefrige(item.getRefrige());
            objSave.setWatch(item.getWatch());
            objSave.setElecFan(item.getElecFan());
            objSave.setRickVan(item.getRickVan());
            objSave.setBicycle(item.getBicycle());
            objSave.setMotCycle(item.getMotCycle());
            objSave.setComputer(item.getComputer());
            objSave.setBuffalo(item.getBuffalo());
            objSave.setBull(item.getBull());
            objSave.setGoat(item.getGoat());
            objSave.setChicken(item.getChicken());
            objSave.setPigeon(item.getPigeon());
            objSave.setRoof(item.getRoof());
            objSave.setRoofOth(item.getRoofOth());
            objSave.setWall(item.getWall());
            objSave.setWallOth(item.getWallOth());
            objSave.setFloor(item.getFloor());
            objSave.setFloorOth(item.getFloorOth());
            objSave.setHomestead(item.getHomestead());
            objSave.setHomesteadOth(item.getHomesteadOth());
            objSave.setOthLand(item.getOthLand());
            objSave.setRnd(item.getRnd());

            objSave.setEnDt(item.getEnDt());
            objSave.setStartTime(item.getStartTime());
            objSave.setEndTime(item.getEndTime());
            objSave.setDeviceID(item.getDeviceID());
            objSave.setEntryUser(item.getEntryUser()); //from data entry user list
            objSave.setLat(item.getLat());
            objSave.setLon(item.getLon());

            String status = objSave.SaveUpdateData(this);
        }
    }

    //Table Name: PregHis
    private void PreHisDataTransfer(String Vill, String Bari, String HH)
    {
        PregHis_DataModel d = new PregHis_DataModel();
        String SQL = "Select * from tmpPregHis  Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"'";
        List<PregHis_DataModel> data = d.SelectAll(this, SQL);
        for(PregHis_DataModel item : data){
            PregHis_DataModel_Main objSave = new PregHis_DataModel_Main();
            objSave.setVill(item.getVill());
            objSave.setBari(item.getBari());
            objSave.setHH(item.getHH());
            objSave.setMSlNo(item.getMSlNo());
            objSave.setPNo(item.getPNo());
            objSave.setVDate(item.getVDate());
            objSave.setVStatus(item.getVStatus());
            objSave.setVStatusOth(item.getVStatusOth());
            objSave.setMarriageStatus(item.getMarriageStatus());
            objSave.setMarMon(item.getMarMon());
            objSave.setMarYear(item.getMarYear());
            objSave.setMarDK(item.getMarDK());
            objSave.setGaveBirth(item.getGaveBirth());

            objSave.setChildLivWWo(item.getChildLivWWo());
            objSave.setSonLivWWo(item.getSonLivWWo());
            objSave.setDaugLivWWo(item.getDaugLivWWo());

            objSave.setChldLivOut(item.getChldLivOut());
            objSave.setSonLivOut(item.getSonLivOut());
            objSave.setDaugLivOut(item.getDaugLivOut());
            objSave.setChldDie(item.getChldDie());
            objSave.setBoyDied(item.getBoyDied());
            objSave.setGirlDied(item.getGirlDied());
            objSave.setNotLivBrth(item.getNotLivBrth());
            objSave.setTotLB(item.getTotLB());
            objSave.setTotPregOut(item.getTotPregOut());
            objSave.setCurPreg(item.getCurPreg());
            objSave.setLMPDate(item.getLMPDate());
            objSave.setEnDt(item.getEnDt());
            objSave.setStartTime(item.getStartTime());
            objSave.setEndTime(item.getEndTime());
            objSave.setDeviceID(item.getDeviceID());
            objSave.setEntryUser(item.getEntryUser()); //from data entry user list
            objSave.setLat(item.getLat());
            objSave.setLon(item.getLon());

            String status = objSave.SaveUpdateData(this);
        }
    }
    //Table Name: Events
    private void EventDataTransfer(String Vill, String Bari, String HH)
    {
        Events_DataModel d = new Events_DataModel();
        String SQL = "Select * from tmpEvents  Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"'";
        List<Events_DataModel> data = d.SelectAll(this, SQL);
        for(Events_DataModel item : data){
            Events_DataModel_Main objSave = new Events_DataModel_Main();
            objSave.setVill(item.getVill());
            objSave.setBari(item.getBari());
            objSave.setHH(item.getHH());
            objSave.setMSlNo(item.getMSlNo());
            objSave.setPNo(item.getPNo());
            objSave.setEvType(item.getEvType());
            objSave.setEvDate(item.getEvDate());
            objSave.setInfo1(item.getInfo1());
            objSave.setInfo2(item.getInfo2());
            objSave.setInfo3(item.getInfo3());
            objSave.setInfo4(item.getInfo4());
            objSave.setVDate(item.getVDate());
            objSave.setRnd(item.getRnd());
            objSave.setEnDt(item.getEnDt());
            objSave.setStartTime(item.getStartTime());
            objSave.setEndTime(item.getEndTime());
            objSave.setDeviceID(item.getDeviceID());
            objSave.setEntryUser(item.getEntryUser());
            objSave.setLat(item.getLat());
            objSave.setLon(item.getLon());

            String status = objSave.SaveUpdateData(this);

            //Release from migration table
            if(item.getEvType().equals("22")){
                C.Save("Delete from migMember where pno='"+ item.getPNo() +"' and extype='52'");
            }else if(item.getEvType().equals("23")){
                C.Save("Delete from migMember where pno='"+ item.getPNo() +"' and extype='53'");
            }

            //Prepare migration database for Member
            else if(item.getEvType().equals("52") | item.getEvType().equals("53")){
                Member_DataModel d1 = new Member_DataModel();
                String SQL1 = "Select * from tmpMember  Where Vill='"+ item.getVill() +"' and Bari='"+ item.getBari() +"' and HH='"+ item.getHH() +"' and MSlNo='"+ item.getMSlNo() +"'";
                List<Member_DataModel> data1 = d1.SelectAll(this, SQL1);
                for(Member_DataModel item1 : data1) {
                    MigrationMember_DataModel objSave1 = new MigrationMember_DataModel();
                    objSave1.setVill(item1.getVill());
                    objSave1.setBari(item1.getBari());
                    objSave1.setHH(item1.getHH());
                    objSave1.setMSlNo(item1.getMSlNo());
                    objSave1.setPNo(item1.getPNo());
                    objSave1.setName(item1.getName());
                    objSave1.setRth(item1.getRth());
                    objSave1.setSex(item1.getSex());
                    objSave1.setBDate(item1.getBDate());
                    objSave1.setAgeY(item1.getAgeY());
                    objSave1.setMoNo(item1.getMoNo());
                    objSave1.setFaNo(item1.getFaNo());
                    objSave1.setEdu(item1.getEdu());
                    objSave1.setMS(item1.getMS());
                    objSave1.setOcp(item1.getOcp());
                    objSave1.setSp1(item1.getSp1());
                    objSave1.setSp2(item1.getSp2());
                    objSave1.setSp3(item1.getSp3());
                    objSave1.setSp4(item1.getSp4());
                    objSave1.setEnType(item1.getEnType());
                    objSave1.setEnDate(item1.getEnDate());
                    objSave1.setExType(item1.getExType());
                    objSave1.setExDate(item1.getExDate());

                    objSave1.setPstat(item1.getPstat());
                    objSave1.setLmpDt(item1.getLmpDt());


                    String status1 = objSave1.SaveUpdateData(this);
                }
            }

            //Pregnancy History Data Transfer
            PregHis_DataModel ph = new PregHis_DataModel();
            String SQL1 = "Select * from tmpPregHis  Where Vill='"+ item.getVill() +"' and Bari='"+ item.getBari() +"' and HH='"+ item.getHH() +"' and MSlNo='"+ item.getMSlNo() +"'";
            List<PregHis_DataModel> dataPH = ph.SelectAll(this, SQL1);
            for(PregHis_DataModel itemPH : dataPH) {
                MigrationPregHis_DataModel objSavePH = new MigrationPregHis_DataModel();
                objSavePH.setVill(itemPH.getVill());
                objSavePH.setBari(itemPH.getBari());
                objSavePH.setHH(itemPH.getHH());
                objSavePH.setMSlNo(itemPH.getMSlNo());
                objSavePH.setPNo(itemPH.getPNo());
                objSavePH.setVDate(itemPH.getVDate());
                objSavePH.setVStatus(itemPH.getVStatus());
                objSavePH.setVStatusOth(itemPH.getVStatusOth());
                objSavePH.setMarriageStatus(itemPH.getMarriageStatus());
                objSavePH.setMarMon(itemPH.getMarMon());
                objSavePH.setMarYear(itemPH.getMarYear());
                objSavePH.setMarDK(itemPH.getMarDK());
                objSavePH.setGaveBirth(itemPH.getGaveBirth());
                objSavePH.setChildLivWWo(itemPH.getChildLivWWo());
                objSavePH.setSonLivWWo(itemPH.getSonLivWWo());
                objSavePH.setDaugLivWWo(itemPH.getDaugLivWWo());
                objSavePH.setChldLivOut(itemPH.getChldLivOut());
                objSavePH.setSonLivOut(itemPH.getSonLivOut());
                objSavePH.setDaugLivOut(itemPH.getDaugLivOut());
                objSavePH.setChldDie(itemPH.getChldDie());
                objSavePH.setBoyDied(itemPH.getBoyDied());
                objSavePH.setGirlDied(itemPH.getGirlDied());
                objSavePH.setNotLivBrth(itemPH.getNotLivBrth());
                objSavePH.setTotLB(itemPH.getTotLB());
                objSavePH.setTotPregOut(itemPH.getTotPregOut());
                objSavePH.setCurPreg("");
                objSavePH.setLMPDate("");
                objSavePH.setEnDt(Global.DateTimeNowYMDHMS());
                objSavePH.setStartTime(STARTTIME);
                objSavePH.setEndTime(g.CurrentTime24());
                objSavePH.setDeviceID(DEVICEID);
                objSavePH.setEntryUser(ENTRYUSER); //from data entry user list
                //objSavePH.setLat(Double.toString(currentLatitude));
                //objSavePH.setLon(Double.toString(currentLongitude));

                String statusPH = objSavePH.SaveUpdateData(this);
            }
        }
    }



    private void VisitNoteForm(final String Vill, final String Bari, final String HH)
    {
        try
        {
            final Dialog dialog = new Dialog(Member_list.this);
            dialog.setTitle("Visit Note");
            //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.visitnote);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);

            Window window = dialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();

            wlp.gravity = Gravity.TOP;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);

            final TextView txtVisitNote = (TextView)dialog.findViewById(R.id.txtVisitNote);
            txtVisitNote.setText(C.ReturnSingleValue("Select note from Household where vill||bari||hh = '"+ (Vill+Bari+HH) +"'"));

            Button cmdVisitNoteSave = (Button)dialog.findViewById(R.id.cmdVisitNoteSave);
            cmdVisitNoteSave.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    C.Save("Update Household set Note='"+ txtVisitNote.getText() +"' where Vill||bari||hh='"+ (Vill+Bari+HH) +"'");
                    dialog.dismiss();
                }
            });

            Button cmdImmClose = (Button)dialog.findViewById(R.id.cmdVisitNoteClose);
            cmdImmClose.setOnClickListener(new View.OnClickListener() {
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
}