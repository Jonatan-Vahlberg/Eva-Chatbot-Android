package com.jonatan_vahlberg.eva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    private RealmResults<Message> messages;

    private RealmChangeListener realmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object o) {
            RecyclerView rV = findViewById(R.id.recyclerview);
            rV.getAdapter().notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        messages = realm.where(Message.class).findAll();

        initRecyclerView();


    }

    private void initRecyclerView(){

        RecyclerView rV = findViewById(R.id.recyclerview);

        //Create New Adapter Based on RecyclerViewAdapter
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,this.messages);
        //Set Adapter
        rV.setAdapter(adapter);

        //Set layouManager
        rV.setLayoutManager(new LinearLayoutManager(this));
    }
}
