package com.cagataygul.ortaksison;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText name,surname,email,phonenumber,username,password;
    private ImageView registimage;
    private Spinner spinner;

  //  private ProgressBar progressBar;
    private DatabaseReference databaseUser;
    private Double longitude, latitude;
    MapsActivityAfterLogin obj = new MapsActivityAfterLogin();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth= FirebaseAuth.getInstance();
        databaseUser= FirebaseDatabase.getInstance().getReference("User");
        name=(EditText) findViewById(R.id.nametext);
        surname=(EditText) findViewById(R.id.surnametext);
       // progressBar=(ProgressBar) findViewById(R.id.progressBar);
        email=(EditText) findViewById(R.id.emailtext);
        phonenumber=(EditText) findViewById(R.id.phonenumbertext);
        username=(EditText) findViewById(R.id.usernametext);
        password=(EditText) findViewById(R.id.passwordtext);
        registimage = (ImageView) findViewById(R.id.registimage);
        spinner=(Spinner) findViewById(R.id.spinner);




    }
    public void register (View view){

         String emailfinal = email.getText().toString();
         String passwordfinal = password.getText().toString();
        if(emailfinal.isEmpty()||passwordfinal.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please fill in fields",Toast.LENGTH_LONG).show();
        }
        else {
          //  progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(emailfinal,passwordfinal)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Register Completed",Toast.LENGTH_LONG).show();




                                        Intent rgn = new Intent(Register.this,LogInActivity.class);
                                        startActivity(rgn);
                                        overridePendingTransition(R.anim.right,R.anim.out);









                            }
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });



        }
        userInformations();
    }


    private void userInformations(){
        final String emaildatabase = email.getText().toString();
        final String passworddatabase = password.getText().toString();
        final String namedatabase = name.getText().toString();
        final String surnamedatabase = surname.getText().toString();
        final String phonenumberdatabase = phonenumber.getText().toString();
        final String spinnerdatabase=spinner.getSelectedItem().toString();
        final String usernamedatabase = username.getText().toString();
        final Double longitude=34.12;
        final Double latitude=33.12;
        final String status="offline";
        final String destination="";
        final String time="";

        String id = databaseUser.push().getKey();
        User user = new User(id,emaildatabase ,passworddatabase,namedatabase,surnamedatabase,phonenumberdatabase,spinnerdatabase,usernamedatabase,latitude,longitude,status,destination,time);
        databaseUser.child(id).setValue(user);

    }

}
