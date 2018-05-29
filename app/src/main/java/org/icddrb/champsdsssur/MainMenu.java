package org.icddrb.champsdsssur;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import Common.Connection;
import Common.Global;
import Common.Tran_Download;
import Utility.MySharedPreferences;

public class MainMenu extends Activity {

    static String DEVICEID = "";
    Button cmdDataSync;
    Button cmdHHInterview;
    Button cmdMember;

    Connection C;
    Global g;
    Spinner spnCluster;
    Spinner spnBlock;
    Spinner spnRound;
    MySharedPreferences sp;
    ProgressDialog progDailog;
    int jumpTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            //getLayoutInflater().inflate(R.layout.main_menu, frameLayout);
            setContentView(R.layout.main_menu);
            C = new Connection(this);
            g = Global.getInstance();
            sp = new MySharedPreferences();

            turnGPSOn();
            Intent gpsService = new Intent(this, GPSService.class);
            startService(gpsService);

            DEVICEID = g.getDeviceNo();
            /*
            cmdHHInterview = (Button) findViewById(R.id.cmdHHInterview);
            cmdHHInterview.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    finish();
                    Intent f1 = new Intent(getApplicationContext(), Household_list.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("village", "");
                    bundle.putString("VCode", "");
                    //f1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    f1.putExtras(bundle);
                    startActivity(f1);
                    //startActivity(new Intent(MainMenu.this, Household_list.class));
                }
            });
            */

            spnRound = (Spinner)findViewById(R.id.spnRound);
            spnRound.setAdapter(C.getArrayAdapter("Select RoundNo from RoundVisit order by RoundNo desc limit 4"));
            spnCluster = (Spinner)findViewById(R.id.spnCluster);
            spnCluster.setAdapter(C.getArrayAdapter("Select distinct Cluster from Baris order by cast(Cluster as int)"));
            spnBlock = (Spinner)findViewById(R.id.spnBlock);
            spnBlock.setAdapter(C.getArrayAdapter("Select distinct Block from Baris order by Cast(Block as int)"));

            /*String[] RCB = C.ReturnSingleValue("Select RoundNo||'-'||Cluster||'-'||Block from LastRoundBlock").split("-");
            spnRound.setSelection(Global.SpinnerItemPositionAnyLength(spnRound,RCB[0]));
            spnCluster.setSelection(Global.SpinnerItemPositionAnyLength(spnCluster,RCB[1]));
            spnBlock.setSelection(Global.SpinnerItemPositionAnyLength(spnBlock,RCB[2]));*/

            cmdHHInterview = (Button) findViewById(R.id.cmdHHInterview);
            cmdHHInterview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sp.save(MainMenu.this,"cluster",spnCluster.getSelectedItem().toString());
                    sp.save(MainMenu.this,"block",spnBlock.getSelectedItem().toString());
                    sp.save(MainMenu.this,"roundno",spnRound.getSelectedItem().toString());

                    C.Save("Delete from LastRoundBlock");
                    C.Save("Insert into LastRoundBlock values('"+ spnRound.getSelectedItem().toString() +"','"+ spnCluster.getSelectedItem().toString() +"','"+ spnBlock.getSelectedItem().toString() +"')");

                    Bundle IDbundle = new Bundle();
                    IDbundle.putString("Village", "");
                    IDbundle.putString("VCode", "");
                    IDbundle.putString("roundno", spnRound.getSelectedItem().toString());
                    IDbundle.putString("cluster", spnCluster.getSelectedItem().toString());
                    IDbundle.putString("block", spnBlock.getSelectedItem().toString());
                    finish();
                    Intent f1;
                    f1 = new Intent(getApplicationContext(), Household_list.class);
                    f1.putExtras(IDbundle);
                    startActivity(f1);
                    //startActivity(new Intent(MainMenu.this, HouseholdIndex1.class));
                }
            });


            cmdDataSync = (Button) findViewById(R.id.cmdDataSync);
            cmdDataSync.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    //Check for Internet connectivity
                    if (Connection.haveNetworkConnection(MainMenu.this)) {
                    } else {
                        Connection.MessageBox(MainMenu.this, "ডেটা সিঙ্ক করার জন্য  ইন্টারনেট সংযোগ নাই.");
                        return;
                    }

                    AlertDialog.Builder adb = new AlertDialog.Builder(MainMenu.this);
                    adb.setTitle("Data Sync");
                    adb.setMessage("আপনি ডেটা সিঙ্ক করতে চান[হ্যাঁ/না]?");
                    adb.setNegativeButton("না", null);
                    adb.setPositiveButton("হ্যাঁ", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            C = new Connection(MainMenu.this);
                            //final ProgressDialog progDailog = ProgressDialog.show(MainMenu.this, "", "Please Wait . . .", true);
                            progDailog = new ProgressDialog(MainMenu.this);
                            progDailog.setMessage("Syncing database, Please Wait . . .");
                            progDailog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            progDailog.setIcon(R.drawable.champsicon);
                            progDailog.setIndeterminate(false);
                            progDailog.setCancelable(false);
                            progDailog.setProgress(0);
                            progDailog.show();

                            new Thread() {
                                public void run() {
                                    try {
                                        String r = C.ExecuteCommandOnServer("Insert into UploadMonitor(DeviceID)Values('"+ DEVICEID +"')");

                                        List<String> tableList = new ArrayList<String>();
                                        tableList.add("Baris");
                                        tableList.add("Household");
                                        tableList.add("Visits");
                                        tableList.add("Member");
                                        tableList.add("SES");
                                        tableList.add("PregHis");
                                        tableList.add("Events");
                                        tableList.add("ChildCardRequest");
//                                        tableList.add("DataCorrectionNote");
                                        tableList.add("Member_SB");

                                        C.Sync_Upload(tableList, progDailog, progressHandler);

                                        if(!isMyServiceRunning(Sync_Service.class)) {
                                            Intent syncService = new Intent(MainMenu.this, Sync_Service.class);
                                            startService(syncService);
                                        }

                                    } catch (Exception e) {

                                    }
                                    progDailog.dismiss();
                                }
                            }.start();
                        }
                    });
                    adb.show();
                }
                Handler progressHandler = new Handler() {
                    public void handleMessage(Message msg) {
                        progDailog.setMessage(Global.getInstance().getProgressMessage());
                        progDailog.incrementProgressBy(jumpTime);
                    }
                };
            });

            Button cmdExit = (Button) findViewById(R.id.cmdExit);
            cmdExit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(MainMenu.this);
                    adb.setTitle("বাহির");
                    adb.setMessage("আপনি কি এই সিস্টেম থেকে বের হতে চান [হ্যাঁ/না]?");
                    adb.setNegativeButton("না", null);
                    adb.setPositiveButton("হ্যাঁ", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            /*BroadcastReceiver rec = new BroadcastReceiver()
                            {
                                String lat = "";
                                String lon = "";
                                @Override
                                public void onReceive(Context context, Intent intent)
                                {
                                    String action = intent.getAction();
                                    if(action.equals("gps_data")){
                                        lat = intent.getExtras().getString("Latitude");
                                        lon = intent.getExtras().getString("Longitude");
                                    }
                                }
                            };
                            IntentFilter filter = new IntentFilter("gps_data");
                            registerReceiver(rec, filter);*/

                            Intent gpsService = new Intent(MainMenu.this, GPSService.class);
                            stopService(gpsService);
                            finish();
                        }
                    });
                    adb.show();
                }
            });



            /* Start Usage of SharedPreferences class */
            /*
            final MySharedPreferences mySharedPreferences = new MySharedPreferences();
            final EditText txtSPrefInput = (EditText) findViewById(R.id.txtSPrefInput);
            final EditText txtSPrefOutput = (EditText) findViewById(R.id.txtSPrefOutput);
            Button cmdSPrefSave = (Button) findViewById(R.id.cmdSPrefSave);
            cmdSPrefSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mySharedPreferences.save(MainMenu.this, "KeyName", txtSPrefInput.getText().toString());
                }
            });
            Button cmdSPrefGet = (Button) findViewById(R.id.cmdSPrefGet);
            cmdSPrefGet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String value = mySharedPreferences.getValue(MainMenu.this, "KeyName");
                    txtSPrefOutput.setText(value);
                }
            });
            Button cmdSPrefRemove = (Button) findViewById(R.id.cmdSPrefRemove);
            cmdSPrefRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mySharedPreferences.removeValue(MainMenu.this, "KeyName");
                    txtSPrefOutput.setText("Removed");
                }
            });
            Button cmdSPrefClear = (Button) findViewById(R.id.cmdSPrefClear);
            cmdSPrefClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mySharedPreferences.clearSharedPreference(MainMenu.this);
                }
            });
            */
            /* End Usage of SharedPreferences class */


        } catch (Exception ex)
        {
            Connection.MessageBox(MainMenu.this,ex.getMessage());
        }
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



    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
