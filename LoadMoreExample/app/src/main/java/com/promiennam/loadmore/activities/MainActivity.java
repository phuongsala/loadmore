package com.promiennam.loadmore.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.promiennam.loadmore.R;
import com.promiennam.loadmore.adapters.LoadMoreAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);
        // init data
        List<LoadMoreAdapter.Item> itemList = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            itemList.add(new LoadMoreAdapter.Item(LoadMoreAdapter.CHILD, "Phuong Nguyen BTW", "Male"));
        }
        // init adapter
        LoadMoreAdapter adapter = new LoadMoreAdapter(this, itemList, recyclerView);
        // set adapter
        recyclerView.setAdapter(adapter);

    }
}
