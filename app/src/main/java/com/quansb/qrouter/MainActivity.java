package com.quansb.qrouter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.routers.RouterManager;
import com.quansb.annotation.QRouter;

@QRouter(path = "com/quansb/qrouter", name = "MainActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       RouterManager.getInstance().startActivity(this, "app", "MineActivity");
    }
}