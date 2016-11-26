package com.e_votee.aspirasimahasiswa.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e_votee.aspirasimahasiswa.R;
import com.e_votee.aspirasimahasiswa.temp.Constant;

public class MainActivity extends AppCompatActivity {

    private EditText etNpm;
    private EditText etName;
    private EditText etPhone;
    private EditText etJurusan;

    private Button btnId;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgress = new ProgressDialog(this);

        etNpm = (EditText) findViewById(R.id.npmID);
        etName = (EditText) findViewById(R.id.nameID);
        etPhone = (EditText) findViewById(R.id.phoneID);
        etJurusan = (EditText) findViewById(R.id.jurusanID);

        btnId = (Button) findViewById(R.id.btnSubmit);

        btnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String npm = etNpm.getText().toString().trim();
                String name = etName.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String jurusan = etJurusan.getText().toString().trim();

                if (npm.isEmpty()&&name.isEmpty()&&phone.isEmpty()&&jurusan.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Input All Field", Toast.LENGTH_LONG).show();

                } else {

                    mProgress.setMessage("Please Wait ...");
                    mProgress.show();

                    SharedPreferences pref = getSharedPreferences(Constant.PREFS_NAME, 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(Constant.Npm, npm);
                    editor.putString(Constant.Name, name);
                    editor.putString(Constant.Phone, phone);
                    editor.putString(Constant.Jurusan, jurusan);
                    editor.apply();

                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            startActivity(new Intent(MainActivity.this, AspirasiActivity.class));
                            finish();
                        }
                    }, 4000);

                }

            }
        });

    }
}
