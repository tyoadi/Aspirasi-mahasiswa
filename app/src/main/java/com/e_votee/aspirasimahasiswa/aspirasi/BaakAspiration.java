package com.e_votee.aspirasimahasiswa.aspirasi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e_votee.aspirasimahasiswa.R;
import com.e_votee.aspirasimahasiswa.activity.AspirasiActivity;
import com.e_votee.aspirasimahasiswa.temp.Constant;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BaakAspiration extends AppCompatActivity {

    private EditText mAspirasi;
    private EditText mSolution;

    private Button mSubmitAspiration;

    private ProgressDialog mProgress;

    public static final okhttp3.MediaType FORM_DATA_TYPE
            = okhttp3.MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    //URL derived from form URL
    public static final String URL="https://docs.google.com/forms/d/e/1FAIpQLSf5C0mlnUFd_DlhUqi6SZ_M95lpsF7ilOWhS0Ny_F-ztNjiNQ/formResponse";
    //input element ids found from the live form page
    public static final String NPM_KEY="entry.1931729208";
    public static final String NAME_KEY="entry.713967174";
    public static final String PHONE_KEY="entry.493331368";
    public static final String JURUSAN_KEY="entry.1121051359";
    public static final String ASPIRASI_KEY="entry.992974624";
    public static final String SOLUSI_KEY="entry.592585900";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baak_aspiration);

        mProgress = new ProgressDialog(this);

        mAspirasi = (EditText) findViewById(R.id.baakAspirasi);
        mSolution = (EditText) findViewById(R.id.baakSolution);

        mSubmitAspiration = (Button) findViewById(R.id.btnSubmitAspirasi);

        mSubmitAspiration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // alaret diaolog confirm send data
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(BaakAspiration.this);
                // Setting Dialog Title
                alertDialog.setTitle("Confirm SEND Aspiration !!! ");
                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want to send this Aspiration ?");
                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        sendData();
                    }
                });
                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                // Showing Alert Message
                alertDialog.show();
            }
        });
    }

    private void sendData() {

        mProgress.setMessage("Sending Data Aspiration ...");
        mProgress.show();

        SharedPreferences pref = getSharedPreferences(Constant.PREFS_NAME, 0);
        String npm = pref.getString(Constant.Npm,"");
        String name = pref.getString(Constant.Name,"");
        String phone = pref.getString(Constant.Phone,"");
        String jurusan = pref.getString(Constant.Jurusan,"");

        String aspirasi = mAspirasi.getText().toString().trim();
        String solusi = mSolution.getText().toString().trim();

        if(TextUtils.isEmpty(npm) && TextUtils.isEmpty(name) && TextUtils.isEmpty(phone)
                && TextUtils.isEmpty(jurusan) && TextUtils.isEmpty(aspirasi) && TextUtils.isEmpty(solusi))
        {

            Toast.makeText(BaakAspiration.this, "Please input all field", Toast.LENGTH_LONG).show();

        } else {

            PostDataTask postDataTask = new PostDataTask();
            postDataTask.execute(URL, npm, name, phone, jurusan, aspirasi, solusi);
        }

    }

    private class PostDataTask extends AsyncTask<String, Void, Boolean> {


        @Override
        protected Boolean doInBackground(String... params) {

            Boolean result = true;
            String url = params[0];
            String npm = params[1];
            String name = params[2];
            String phone = params[3];
            String jurusan = params[4];
            String aspirasi = params[5];
            String solusi = params[6];

            String postBody = "";

            try {

                postBody = NPM_KEY+"=" + URLEncoder.encode(npm,"UTF-8") +
                        "&" + NAME_KEY + "=" + URLEncoder.encode(name,"UTF-8") +
                        "&" + PHONE_KEY + "=" + URLEncoder.encode(phone,"UTF-8") +
                        "&" + JURUSAN_KEY + "=" + URLEncoder.encode(jurusan,"UTF-8") +
                        "&" + ASPIRASI_KEY + "=" + URLEncoder.encode(aspirasi,"UTF-8") +
                        "&" + SOLUSI_KEY + "=" + URLEncoder.encode(solusi,"UTF-8");

            } catch (UnsupportedEncodingException e) {

                result = false;

            }

            try {

                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(FORM_DATA_TYPE, postBody);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();

            } catch (IOException e) {

                result = false;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if(result == true) {

                Toast.makeText(BaakAspiration.this, "Message Succcessfully send!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(BaakAspiration.this, AspirasiActivity.class));

                mProgress.dismiss();

            } else {

                Toast.makeText(BaakAspiration.this, "There was some error in sending message. " +
                        "Please try again after some time.",Toast.LENGTH_LONG).show();

                mProgress.dismiss();

            }
        }
    }
}
