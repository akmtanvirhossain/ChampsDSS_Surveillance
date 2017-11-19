package org.icddrb.champsdsssur;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Common.Connection;
import Common.Global;
import Common.ProjectSetting;


public class SettingForm_QA extends Activity {
    Connection C;
    Global g;
    //private ProgressDialog dialog;

    String Site   = "";
    String UserID = "";

    //Spinner spnCluster;
    //Spinner spnBlock;

    ProgressDialog progDailog;
    int jumpTime = 0;
    TextView lblHlblListing;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            setContentView(R.layout.devicesetting_qa);
            C = new Connection(this);
            g = Global.getInstance();

            if (!Connection.haveNetworkConnection(SettingForm_QA.this)) {
                Connection.MessageBox(SettingForm_QA.this, "Inernet connection is not available for device setting.");
                return;
            }
            lblHlblListing = (TextView)findViewById(R.id.lblHlblListing);
            if(ProjectSetting.InterviewType.equals(ProjectSetting.QAInterview)){
                lblHlblListing.setText("Device Setting (QA Interview)");
            }

            final Spinner spnUser = (Spinner)findViewById(R.id.spnUser);
            SpinnerItem(spnUser, "select DeviceId+'-'+DeviceName from DeviceList order by DeviceId");

            Button cmdSave = (Button)findViewById(R.id.cmdSave);
            cmdSave.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    try {
                        String SQLStr = "";

                        String[] User = spnUser.getSelectedItem().toString().split("-");
                        UserID = User[0];

                        String Setting = C.ReturnResult("Existence", "Select DeviceID from DeviceList where DeviceId='" + Connection.SelectedSpinnerValue(spnUser.getSelectedItem().toString(), "-") + "' and Setting='1'");
                        if (Setting.equals("2")) {
                            Connection.MessageBox(SettingForm_QA.this, "Device ID :" + spnUser.getSelectedItem().toString() + " is not allowed to configure a mobile device, contact with administrator.");
                            return;
                        }

                        String ResponseString = "Status:";

                        progDailog = new ProgressDialog(SettingForm_QA.this);
                        progDailog.setMessage("Rebuilding database, Please Wait . . .");
                        progDailog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progDailog.setIcon(R.drawable.champsicon);
                        progDailog.setIndeterminate(false);
                        progDailog.setCancelable(false);
                        progDailog.setProgress(0);
                        progDailog.show();
                        //progDailog = ProgressDialog.show(SettingForm.this, "", "Please Wait . . .", true);

                        new Thread() {
                            public void run() {
                                try {
                                    C.RebuildDatabase_QA(UserID, progDailog, progressHandler);

                                } catch (Exception e) {

                                }
                                progDailog.dismiss();

                                //Call Login Form
                                finish();
                                Intent f1 = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(f1);

                            }
                        }.start();
                    } catch (Exception ex) {
                        Connection.MessageBox(SettingForm_QA.this, ex.getMessage());
                        return;
                    }
                }

                        Handler progressHandler = new Handler() {
                            public void handleMessage(Message msg) {
                                progDailog.setMessage(Global.getInstance().getProgressMessage());
                                progDailog.incrementProgressBy(jumpTime);
                            }
                        };
            });
        }
        catch(Exception ex)
        {
            Connection.MessageBox(SettingForm_QA.this, ex.getMessage());
            return;
        }
    }

    private void SpinnerItem(Spinner SpinnerName, String SQL)
    {
        List<String> listItem = new ArrayList<String>();
        listItem = C.DataListJSON(SQL);
        ArrayAdapter<String> adptrList= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listItem);
        SpinnerName.setAdapter(adptrList);
    }
}