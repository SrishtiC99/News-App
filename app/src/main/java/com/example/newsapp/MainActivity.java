package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isConnected()==false){
            TextView emptyView = (TextView)findViewById(R.id.no_view);
            emptyView.setText(R.string.no_internet);
        }
        
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.pager);
        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Android"));
        tabLayout.addTab(tabLayout.newTab().setText("Technology"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        listView = (ListView)findViewById(R.id.list);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        // It is used to join TabLayout with ViewPager.
        tabLayout.setupWithViewPager(viewPager);
        Button button = (Button)findViewById(R.id.search_button);
        EditText editText = (EditText) findViewById(R.id.query);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchValue  = editText.getText().toString();
                Intent intent = new Intent(MainActivity.this,Search.class);
                intent.putExtra("msg_key",searchValue);
                MainActivity.this.startActivity(intent);
            }
        });
    }

    public boolean isConnected(){
        // Get a reference to the ConnectivityManager to check state of network connectivity
        android.net.ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) return true;
        return false;
    }
}