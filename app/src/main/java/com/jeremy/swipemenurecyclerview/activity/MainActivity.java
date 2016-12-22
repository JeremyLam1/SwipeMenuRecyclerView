package com.jeremy.swipemenurecyclerview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.jeremy.swipemenurecyclerview.R;
import com.jeremy.swipemenurecyclerview.SwipeMenuAdapter;
import com.jeremy.swipemenurecyclerview.view.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            list.add("item " + i);
        }

        SwipeMenuRecyclerView rvList = (SwipeMenuRecyclerView) findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setAdapter(new SwipeMenuAdapter(MainActivity.this, list));

    }
}
