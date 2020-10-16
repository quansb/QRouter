package com.quansb.qrouter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.quansb.annotation.QRouter;

@QRouter(path = "com.quansb.qrouter",name = "MineActivity")
public class MineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);

    }
}