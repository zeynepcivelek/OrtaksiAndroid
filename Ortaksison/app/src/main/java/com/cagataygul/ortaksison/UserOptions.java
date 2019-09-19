package com.cagataygul.ortaksison;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UserOptions extends AppCompatActivity {
    private Spinner spinnerdestination1;
    private Spinner spinnerhour1;
    private Spinner spinnerminute1;
    FirebaseUser user;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userref = database.getReference("User");
    DatabaseReference userchangedref;
    DatabaseReference userchangedref2;
    String currentUser;
    String id ;
    TextView hellouser;
    String destinationdatabase;
    String timedatabase;
    Button btn;


     private Toolbar tolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_options);
        spinnerdestination1 = (Spinner) findViewById(R.id.spinnerdestination);
        spinnerhour1 = (Spinner) findViewById(R.id.spinnerhour);
        spinnerminute1 = (Spinner) findViewById(R.id.spinnerminute);
        btn= (Button) findViewById(R.id.slcbtn);
        hellouser=(TextView) findViewById(R.id.userssnamees1);
        user = FirebaseAuth.getInstance().getCurrentUser();
        currentUser = user.getEmail().toString();
        tolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tolbar);
        getSupportActionBar().setTitle("");
         if(currentUser==null)
         {
             sendToStart();
         }



         btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 destinationdatabase = spinnerdestination1.getSelectedItem().toString();
                 timedatabase = spinnerhour1.getSelectedItem().toString() + " " + spinnerminute1.getSelectedItem().toString();
                 userref.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                         for (DataSnapshot ds : dataSnapshot.getChildren()) {
                             HashMap<String, String> hmap = (HashMap<String, String>) ds.getValue();
                             if (hmap.get("email").equalsIgnoreCase(currentUser)) {
                                 id = hmap.get("id");

                             }

                         }

                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 });

                 if (id != null) {
                     userchangedref = database.getReference("User/" + id + "/destination");
                     userchangedref.setValue(destinationdatabase);
                     userchangedref2 = database.getReference("User/" + id + "/time");
                     userchangedref2.setValue(timedatabase);



                             Intent intentafterlogin =new Intent(UserOptions.this,MapsActivityAfterLogin.class);
                             startActivity(intentafterlogin);






                 }
             }
         });


    }

    private void sendToStart() {

        Intent intent =new Intent(UserOptions.this,LogInActivity.class);
        startActivity(intent);
        finish();
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
             userref.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     for(DataSnapshot ds:dataSnapshot.getChildren()){
                         HashMap<String,String> hmap = (HashMap<String, String>) ds.getValue();
                         HashMap<String,Double> hmap2 = (HashMap<String, Double>) ds.getValue();
                         if( hmap.get("email").equalsIgnoreCase(currentUser)) {
                             id = hmap.get("id");
                             hellouser.setText("Welcome "+hmap.get("name").toString());

                         }

                     }
                     if(id!=null){
                         userchangedref=  database.getReference("User/"+id+"/status");
                         userchangedref.setValue("offline");


                     }
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {

                 }
             });
             FirebaseAuth.getInstance().signOut();
             sendToStart();
         }

        return true;
    }






}