package com.jonatan_vahlberg.eva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    private RealmResults<Message> messages;
    private RecyclerView mRecycle;
    private EditText mEdit;
    private ImageButton mImageBtn;

    private RealmChangeListener realmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object o) {

            mRecycle.getAdapter().notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRealm();
        initLayout();



    }



    private void initRealm(){
        realm = Realm.getDefaultInstance();
        messages = realm.where(Message.class).findAll();
        realm.addChangeListener(realmChangeListener);
    }

    private void initLayout(){
        mEdit = findViewById(R.id.edit_text_send);
        mEdit.setOnEditorActionListener(editorActionListener);
        mImageBtn = findViewById(R.id.microphone_btn);
        initRecyclerView();
    }

    private void initRecyclerView(){
        mRecycle =findViewById(R.id.recyclerview);

        //Create New Adapter Based on RecyclerViewAdapter
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,this.messages);
        //Set Adapter
        mRecycle.setAdapter(adapter);

        //Set layoutManager
        mRecycle.setLayoutManager(new LinearLayoutManager(this));
    }

    private TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (actionId){
                case EditorInfo.IME_ACTION_DONE:
                    if(v.getText().toString().equals("")){
                        return true;
                    }
                    else{
                        sendmessage(v.getText().toString());
                        v.setText("");
                        return false;
                    }
                default:
                    return true;
            }
        }
    };

    private void sendmessage(String message) {
        realm.beginTransaction();
        Message newMessage = realm.createObject(Message.class,UUID.randomUUID().getMostSignificantBits());
        newMessage.setSender("Jonatan");
        newMessage.setMessage(message);
        realm.commitTransaction();

        realm.beginTransaction();
        Message newerMessage = realm.createObject(Message.class,UUID.randomUUID().getMostSignificantBits());
        newerMessage.setSender("EVA");
        newerMessage.setMessage("Yo My Boy");
        realm.commitTransaction();

    }
}
