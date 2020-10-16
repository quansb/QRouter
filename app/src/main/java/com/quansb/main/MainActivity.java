package com.quansb.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.quansb.annotation.QRouter;
import com.quansb.main.R;
import com.quansb.qrouter.RouterManager;

@QRouter(path = "com/quansb/main", name = "MainActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RouterManager.getInstance().startActivity(this, "app", "MineActivity");
    }
}