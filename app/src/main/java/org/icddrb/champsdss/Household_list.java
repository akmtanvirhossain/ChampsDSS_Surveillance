package org.icddrb.champsdss;
//Android Manifest Code
 //<activity android:name=".Household_list" android:label="Household: List" />
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import android.app.*;
 import android.app.AlertDialog;
 import android.content.Context;
 import android.content.DialogInterface;
 import android.content.Intent;
 import android.database.Cursor;
 import android.location.Location;
 import android.view.KeyEvent;
 import android.os.Bundle;
 import android.view.Menu;
 import android.view.MenuInflater;
 import android.view.MenuItem;
 import android.view.View;
 import android.view.MotionEvent;
 import android.view.ViewGroup;
 import android.view.LayoutInflater;
 import android.widget.AdapterView;
 import android.widget.LinearLayout;
 import android.widget.ListView;
 import android.widget.SimpleAdapter;
 import android.widget.BaseAdapter;
 import android.widget.TextView;
 import android.widget.Button;
 import android.widget.ImageButton;
 import Common.*;
 import android.view.Window;
 import android.view.WindowManager;
 import android.view.Gravity;
 import android.widget.Spinner;
 import android.widget.EditText;

 public class Household_list extends Activity {
    boolean networkAvailable=false;
    Location currentLocation; 
    double currentLatitude,currentLongitude;
     Spinner spnUnion;
     Spinner spnVill;
     Spinner spnBari;
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
    static String TableName;

    TextView lblHeading;
    Button btnAdd;
    Button btnRefresh;

     Bundle IDbundle;
     private static String CurrentVillage;
     private static String CurrentVCode;

    static String STARTTIME = "";
    static String VILL = "";
    static String BARI = "";
    static String HH = "";

 public void onCreate(Bundle savedInstanceState)
 {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.household_list);
         C = new Connection(this);
         g = Global.getInstance();
         STARTTIME = g.CurrentTime24();

         IDbundle       = getIntent().getExtras();
         CurrentVillage = IDbundle.getString("village");
         CurrentVCode   = IDbundle.getString("vcode");

         TableName = "Household";
         lblHeading = (TextView)findViewById(R.id.lblHeading);
         lblHeading.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 final int DRAWABLE_RIGHT  = 2;
                 if(event.getAction() == MotionEvent.ACTION_DOWN) {
                     if(event.getRawX() >= (lblHeading.getRight() - lblHeading.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                         AlertDialog.Builder adb = new AlertDialog.Builder(Household_list.this);
                         adb.setTitle("Close");
                         adb.setMessage("Do you want to close this form[Yes/No]?");
                         adb.setNegativeButton("No", null);
                         adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                             public void onClick(DialogInterface dialog, int which) {
                                 finish();
                             }});
                         adb.show();
                         return true;
                     }
                 }
                 return false;
             }
         });

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(Household_list.this);
                 adb.setTitle("Close");
                 adb.setMessage("Do you want to close this form[Yes/No]?");
                 adb.setNegativeButton("No", null);
                 adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         finish();
                         startActivity(new Intent(Household_list.this, MainMenu.class));
                     }});
                 adb.show();
             }});

        /* btnRefresh = (Button) findViewById(R.id.btnRefresh);
         btnRefresh.setOnClickListener(new View.OnClickListener() {

             public void onClick(View view) {
                   //write your code here
                   DataSearch(VILL, BARI, HH);

             }});
        */
         /*
         btnAdd   = (Button) findViewById(R.id.btnAdd);
         btnAdd.setOnClickListener(new View.OnClickListener() {

             public void onClick(View view) {
                         Bundle IDbundle = new Bundle();
                         IDbundle.putString("Vill", "");
                         IDbundle.putString("Bari", "");
                         IDbundle.putString("HH", "");
                         Intent intent = new Intent(getApplicationContext(), Household.class);
                         intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                         intent.putExtras(IDbundle);
                         getApplicationContext().startActivity(intent);
                         startActivityForResult(intent, 1);
             }});
        */
        //DataSearch(VILL, BARI, HH);

         spnUnion = (Spinner) findViewById(R.id.spnUnion);
         spnVill = (Spinner) findViewById(R.id.spnVill);
         spnBari = (Spinner) findViewById(R.id.spnBari);

         spnUnion.setAdapter(C.getArrayAdapter("Select distinct UnCode||'-'||UnName from Village order by UnCode"));
         spnUnion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                 String D = Connection.SelectedSpinnerValue(spnUnion.getSelectedItem().toString(), "-");
                 spnVill.setAdapter(C.getArrayAdapter("Select '' union Select distinct VCode||'-'||VName from Village where UnCode='" + D + "'"));
                 //DataSearch();
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

         spnUnion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                 String UN = Connection.SelectedSpinnerValue(spnUnion.getSelectedItem().toString(), "-");
                 spnVill.setAdapter(C.getArrayAdapter("Select '' union Select distinct VCode||'-'||VName from Village where UNCode='" + UN + "'"));
                 //DataSearch();
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

         spnVill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                 //DataSearch();
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

         spnBari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                 if(spnBari.getSelectedItemPosition()==0)
                 {

                 }
                 else if(spnBari.getSelectedItem().toString().trim().equalsIgnoreCase("all bari"))
                 {
                     g.setBariCode("");
                     //BlockList(false, "");
                 }
                 else
                 {
                     g.setBariCode(spnBari.getSelectedItem().toString());
                     //BlockList(false, Global.Left(spnBari.getSelectedItem().toString(),4));
                 }

             }

             @Override
             public void onNothingSelected(AdapterView<?> parentView) {

             }

         });

         final Spinner BariList = (Spinner)findViewById(R.id.spnBari);
         //BariList.setAdapter(C.getArrayAdapter("Select ' ' union Select ' All Bari' union select Bari||', '||BariName from baris b,mdssvill v where b.vill=v.vill and v.cluster='"+ g.getClusterCode() +"' and b.block='"+ g.getBlockCode() +"'"));
         BariList.setAdapter(C.getArrayAdapter("Select ' ' union Select ' All Bari' union select Bari||', '||BariName from baris b where b.cluster='"+ g.getClusterCode() +"' and b.block='"+ g.getBlockCode() +"'"));
         BariList.setSelection(2);

         Button cmdbari = (Button)findViewById(R.id.cmdBari);
         cmdbari.setOnClickListener(new View.OnClickListener() {
             public void onClick(View arg0) {
                 if (BariList.getSelectedItemPosition() == 0) return;
                 if (BariList.getSelectedItem().toString().trim().equalsIgnoreCase("all bari"))
                     return;

                 String CurrentBariNo = Global.Left(BariList.getSelectedItem().toString(), 4);
                 ShowBariForm(g.getDeviceNo(), CurrentBariNo, "u");
             }
         });
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Household_list.this, e.getMessage());
         return;
     }
 }
     public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {

             case R.id.mnuNewBari:
                 try
                 {
                     String CurrentBariNo="";
                     String LastBariNo = C.ReturnSingleValue("select Bari from Baris where vill=='"+ (g.getDeviceNo()) +"' order by Bari desc limit 1");
                     if(!Global.Left(LastBariNo, 1).matches("[a-zA-z]{1}"))
                     {
                         CurrentBariNo = Global.Right("000" + String.valueOf((Integer.parseInt(LastBariNo)+1)),4);
                     }
                     else if(Global.Left(LastBariNo, 1).matches("[a-zA-z]{1}"))
                     {
                         CurrentBariNo = Global.Left(LastBariNo, 1) + Global.Right("000" + String.valueOf((Integer.parseInt(Global.Right(LastBariNo,3))+1)),3);
                     }

                     ShowBariForm(g.getDeviceNo(),CurrentBariNo,"s");
                 }
                 catch(Exception ex)
                 {
                     Connection.MessageBox(Household_list.this, ex.getMessage());
                 }
                 return true;
             case R.id.mnuBack:
                 finish();
                 Intent f11 = new Intent(getApplicationContext(),Household_list.class);
                 startActivity(f11);
                 return true;
         }
         return false;
     }
     private void ShowBariForm(final String Vill,final String BariNo, final String Status)
     {
         //Status: u-update, s-save (new bari)

         final Dialog dialog = new Dialog(Household_list.this);
         dialog.setTitle("Bari Form");
         //dialog .requestWindowFeature(Window.FEATURE_NO_TITLE);
         dialog.setContentView(R.layout.baris);
         dialog.setCancelable(true);
         dialog.setCanceledOnTouchOutside(false);

         Window window = dialog.getWindow();
         WindowManager.LayoutParams wlp = window.getAttributes();

         wlp.gravity = Gravity.TOP;
         wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
         window.setAttributes(wlp);


         final Spinner BariList = (Spinner)findViewById(R.id.spnBari);
         final TextView txtCluster = (TextView)dialog.findViewById(R.id.txtCluster);
         final Spinner txtBlock = (Spinner)dialog.findViewById(R.id.spnBlock);
         final TextView txtVill = (TextView)dialog.findViewById(R.id.txtVill);
         final EditText txtBari = (EditText)dialog.findViewById(R.id.txtBari);
         final EditText txtBName = (EditText)dialog.findViewById(R.id.txtBariName);
         final EditText txtBLoc = (EditText)dialog.findViewById(R.id.txtBariLoc);
         final String BLOCK = g.getBlockCode();

         txtVill.setText(g.getVillageCode()+", "+CurrentVillage);
         txtBari.setText(BariNo);
         txtCluster.setText(g.getClusterCode());
         txtBlock.setAdapter(C.getArrayAdapter("Select distinct block from baris order by cast(block as int)"));
         //txtBlock.setText(g.getBlockCode());
         txtBlock.setSelection(Common.Global.SpinnerItemPosition(txtBlock, 2, g.getBlockCode()));
         if(Status.equalsIgnoreCase("u"))
         {
             txtBName.setText(BariList.getSelectedItem().toString().substring(6,BariList.getSelectedItem().toString().length()));
             txtBLoc.setText(C.ReturnSingleValue("Select BariLoc from Baris where Vill||Bari='"+ (Vill+BariNo) +"'"));
         }

         Button cmdBariSave = (Button)dialog.findViewById(R.id.cmdSave);
         cmdBariSave.setOnClickListener(new View.OnClickListener() {
             public void onClick(View arg0) {
                 if(txtBName.getText().length()==0)
                 {
                     Connection.MessageBox(Household_list.this, "বাড়ীর নাম খালি রাখা যাবে না।");
                     return;
                 }
                 else
                 {
                     try
                     {
                         String SQL="";
                         int selBari = BariList.getSelectedItemPosition();
                         if(Status.equalsIgnoreCase("s"))
                         {
                             SQL  = "Insert into Baris (Vill, Bari, Block, BariName,BariLoc,Xdec, Xmin, Xsec, Ydec, Ymin, Ysec,Status, EnDt,Upload)Values(";
                             SQL += "'"+ Vill +"','"+ txtBari.getText() +"','"+ "','"+ txtBName.getText() +"','"+ txtBLoc.getText() +"',";

                             C.Save(SQL);
                             C.Save("update baris set cluster=(select cluster from mdssvill where vill=baris.vill) where length(cluster)=0 or cluster is null");
                         }
                         else if(Status.equalsIgnoreCase("u"))
                         {
                             SQL  = "Update Baris Set ";
                             SQL += " BariName='"+ txtBName.getText() +"',BariLoc='"+ txtBLoc.getText() +"',Upload='2'";
                             SQL += " Where Vill='"+ Vill +"' and Bari='"+ txtBari.getText() +"'";
                             C.Save(SQL);

                         }

                         dialog.dismiss();
                         final Spinner BariList = (Spinner)findViewById(R.id.spnBari);
                         BariList.setAdapter(C.getArrayAdapter("Select ' ' union Select ' All Bari' union select Bari||', '||BariName from baris b,mdssvill v where b.vill=v.vill '"));

                         BariList.setSelection(selBari);
                         //BlockList(false, Global.Left(BariList.getSelectedItem().toString(),4));
                     }
                     catch(Exception ex)
                     {
                         Connection.MessageBox(Household_list.this, ex.getMessage());
                         return;
                     }

                 }
             }
         });

         //Button cmdBariClose = (Button)dialog.findViewById(R.id.cmdBariClose);
         //cmdBariClose.setOnClickListener(new View.OnClickListener() {
           //  public void onClick(View arg0) {
                 // TODO Auto-generated method stub
             //    dialog.cancel();
             //}
         //});

         dialog.show();
     }
 @Override
 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);
     if (resultCode == Activity.RESULT_CANCELED) {
         //Write your code if there's no result
     } else {
         //DataSearch(VILL, BARI, HH);
     }
 }


 private void DataSearch(String Vill, String Bari, String HH)
     {
       try
        {
     
           Household_DataModel d = new Household_DataModel();
             String SQL = "Select * from "+ TableName +"  Where Vill='"+ Vill +"' and Bari='"+ Bari +"' and HH='"+ HH +"'";
             List<Household_DataModel> data = d.SelectAll(this, SQL);
             dataList.clear();

             dataAdapter = null;

             ListView list = (ListView)findViewById(R.id.lstData);
             HashMap<String, String> map;

             for(Household_DataModel item : data){
                 map = new HashMap<String, String>();
                 map.put("Vill", item.getVill());
                 map.put("Bari", item.getBari());
                 map.put("HH", item.getHH());
                 map.put("Religion", item.getReligion());
                 map.put("MobileNo1", item.getMobileNo1());
                 map.put("MobileNo2", item.getMobileNo2());
                 map.put("HHHead", item.getHHHead());
                 map.put("TotMem", item.getTotMem());
                 map.put("TotRWo", item.getTotRWo());
                 map.put("EnType", item.getEnType());
                 map.put("EnDate", item.getEnDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getEnDate()));
                 map.put("ExType", item.getExType());
                 map.put("ExDate", item.getExDate().toString().length()==0 ? "" : Global.DateConvertDMY(item.getExDate()));
                 map.put("Rnd", item.getRnd());
                 dataList.add(map);
             }
             dataAdapter = new SimpleAdapter(Household_list.this, dataList, R.layout.household_list,new String[] {"rowsec"},
                           new int[] {R.id.secListRow});
             list.setAdapter(new DataListAdapter(this, dataAdapter));
        }
        catch(Exception  e)
        {
            Connection.MessageBox(Household_list.this, e.getMessage());
            return;
        }
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
 	        convertView = inflater.inflate(R.layout.household_row, null); 
 	        }
         LinearLayout   secListRow = (LinearLayout)convertView.findViewById(R.id.secListRow);

         final TextView Vill = (TextView)convertView.findViewById(R.id.Vill);
         final TextView Bari = (TextView)convertView.findViewById(R.id.Bari);
         final TextView HH = (TextView)convertView.findViewById(R.id.HH);
         final TextView Religion = (TextView)convertView.findViewById(R.id.Religion);
         final TextView MobileNo1 = (TextView)convertView.findViewById(R.id.MobileNo1);
         final TextView MobileNo2 = (TextView)convertView.findViewById(R.id.MobileNo2);
         final TextView HHHead = (TextView)convertView.findViewById(R.id.HHHead);
         final TextView TotMem = (TextView)convertView.findViewById(R.id.TotMem);
         final TextView TotRWo = (TextView)convertView.findViewById(R.id.TotRWo);
         final TextView EnType = (TextView)convertView.findViewById(R.id.EnType);
         final TextView EnDate = (TextView)convertView.findViewById(R.id.EnDate);
         final TextView ExType = (TextView)convertView.findViewById(R.id.ExType);
         final TextView ExDate = (TextView)convertView.findViewById(R.id.ExDate);
         final TextView Rnd = (TextView)convertView.findViewById(R.id.Rnd);

         final HashMap<String, String> o = (HashMap<String, String>) dataAdap.getItem(position);
         Vill.setText(o.get("Vill"));
         Bari.setText(o.get("Bari"));
         HH.setText(o.get("HH"));
         Religion.setText(o.get("Religion"));
         MobileNo1.setText(o.get("MobileNo1"));
         MobileNo2.setText(o.get("MobileNo2"));
         HHHead.setText(o.get("HHHead"));
         TotMem.setText(o.get("TotMem"));
         TotRWo.setText(o.get("TotRWo"));
         EnType.setText(o.get("EnType"));
         EnDate.setText(o.get("EnDate"));
         ExType.setText(o.get("ExType"));
         ExDate.setText(o.get("ExDate"));
         Rnd.setText(o.get("Rnd"));

         secListRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               //Write your code here
               Bundle IDbundle = new Bundle();
               IDbundle.putString("Vill", o.get("Vill"));
               IDbundle.putString("Bari", o.get("Bari"));
               IDbundle.putString("HH", o.get("HH"));
               Intent f1;
               f1 = new Intent(getApplicationContext(), Household.class);
               f1.putExtras(IDbundle);
               startActivity(f1);
            }
          });


         return convertView;
       }
 }


}