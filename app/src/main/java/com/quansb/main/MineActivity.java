package com.quansb.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.quansb.annotation.QRouter;

@QRouter(path = "com.quansb.main", name = "MineActivity")
public class MineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);

    }
}