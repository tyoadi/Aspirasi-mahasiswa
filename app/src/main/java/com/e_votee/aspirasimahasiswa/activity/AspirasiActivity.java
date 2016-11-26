package com.e_votee.aspirasimahasiswa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.e_votee.aspirasimahasiswa.R;
import com.e_votee.aspirasimahasiswa.aspirasi.BaakAspiration;

public class AspirasiActivity extends AppCompatActivity {

    private RelativeLayout mBaakBtn;
    private RelativeLayout mDosenBtn;
    private RelativeLayout mJurusanBtn;
    private RelativeLayout mForumBtn;
    private RelativeLayout mLembagaBtn;
    private RelativeLayout mHimpunanBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aspirasi);

        mBaakBtn = (RelativeLayout) findViewById(R.id.baakBtn);
        mDosenBtn = (RelativeLayout) findViewById(R.id.dosenBtn);
        mJurusanBtn = (RelativeLayout) findViewById(R.id.jurusanBtn);
        mForumBtn = (RelativeLayout) findViewById(R.id.forumBtn);
        mLembagaBtn = (RelativeLayout) findViewById(R.id.lembagaBtn);
        mHimpunanBtn = (RelativeLayout) findViewById(R.id.himpunanBtn);

        mBaakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AspirasiActivity.this, BaakAspiration.class));
            }
        });

     }
}
