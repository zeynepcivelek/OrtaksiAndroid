package com.cagataygul.ortaksison;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    FirebaseUser fuser;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference dbref ;
    DatabaseReference dbref2;
    DatabaseReference userchangedref;
    TextView username;
    ImageButton btn_send ;
    EditText text_send;
    Intent intent;
    String currentUser;
    String userid;
    String id;
    String crid;

    MessageAdapter messageAdapter;
    List<Chat> mchat;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        username =(TextView) findViewById(R.id.chatusername);
        btn_send = (ImageButton) findViewById(R.id.btn_send);
        text_send= (EditText) findViewById(R.id.text_send);
        intent = getIntent();
        userid = intent.getStringExtra("userid");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        dbref = database.getReference("User");
        currentUser = fuser.getEmail().toString();


        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 for(DataSnapshot ds:dataSnapshot.getChildren()){
                             HashMap<String,String> hmap = (HashMap<String, String>) ds.getValue();
                            if( hmap.get("id").equalsIgnoreCase(userid)) {
                                username.setText(hmap.get("name"));
                                id = hmap.get("id");

                            }
                     if( hmap.get("email").equalsIgnoreCase(currentUser)) {
                         crid = hmap.get("id");

                         btn_send.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 String msg = text_send.getText().toString();
                                 if(!msg.equals(""))
                                 {
                                     SendMessage(crid,userid,msg);
                                 }
                                 else {
                                     Toast.makeText(MessageActivity.this,"You cant send empty message",Toast.LENGTH_SHORT).show();
                                 }
                                 text_send.setText("");
                             }
                         });
                     }
                     readMessage(crid,userid);
                        }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.Logoutmenu)
        {


            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }

        return true;
    }
    private void sendToStart() {

        Intent intent =new Intent(MessageActivity.this,LogInActivity.class);
        startActivity(intent);
        finish();
    }


    private void SendMessage( String sender, String receiver ,String message )
    {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hmap = new HashMap<>();
        hmap.put("sender",sender);
        hmap.put("receiver",receiver);
        hmap.put("message",message);
        databaseReference.child("Chats").push().setValue(hmap);
    }

    private void readMessage(final String myid, final String userid){
        mchat= new ArrayList<>();
        dbref2=FirebaseDatabase.getInstance().getReference().child("Chats");
        dbref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Chat chat = ds.getValue(Chat.class);
                    if(id!=null){
                        if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) || chat.getReceiver().equals(userid) && chat.getSender().equals(myid)) {
                            mchat.add(chat);
                        }
                        messageAdapter = new MessageAdapter(MessageActivity.this,mchat);
                        recyclerView.setAdapter(messageAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
