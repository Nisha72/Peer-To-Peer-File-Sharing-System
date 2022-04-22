package com.example.sharetec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<com.example.sharetec.App> apps = new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.app_list);

        //Get App List
        PackageManager pm  = getApplicationContext().getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for(ApplicationInfo packageInfo: packages){

            String name;

            if((name = String.valueOf(pm.getApplicationLabel(packageInfo))).isEmpty()){
                name = packageInfo.packageName;
            }

            Drawable icon  = pm.getApplicationIcon(packageInfo);
            String apkPath = packageInfo.sourceDir;
            long apkSize = new File(packageInfo.sourceDir).length();


            apps.add(new com.example.sharetec.App(name,icon,apkPath,apkSize));


        }

        Collections.sort(apps, new Comparator<com.example.sharetec.App>() {
                    @Override
                    public int compare(com.example.sharetec.App app, com.example.sharetec.App t1) {
                        return app.getName().toLowerCase().compareTo(t1.getName().toLowerCase());
                    }
                }
        );


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        com.example.sharetec.AppAdapter appAdapter = new com.example.sharetec.AppAdapter(this,apps);
        recyclerView.setAdapter(appAdapter);



    }
}
