package com.cagataygul.ortaksison;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.ResolutionDimension;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class LogInActivity extends AppCompatActivity {
   FirebaseAuth auth;
    private ImageView registerImage;
    private ImageView loginImage;
    private EditText username ;
    private EditText password;
    private ImageView img;
    private TextView resetpasswordtext;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userref = database.getReference("User");
    DatabaseReference userchangedref;

    String id;
    String currentUser;
    FirebaseUser user ;
    MapsActivityAfterLogin mapsActivityAfterLogin = new MapsActivityAfterLogin();
    //firebase
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseAuth fr ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        img= (ImageView) findViewById(R.id.imageView4);

        loginImage= (ImageView) findViewById(R.id.imageView);
        username= (EditText) findViewById(R.id.username);
        password= (EditText) findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();
        resetpasswordtext=(TextView) findViewById(R.id.resettext);
        resetpasswordtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentre =new Intent(LogInActivity.this,ResetPassword.class);
                startActivity(intentre);
            }
        });



    }

    public  void register (View view){
        Intent intent =new Intent(LogInActivity.this,Register.class);
        startActivity(intent);
    }



    public  void login (View view){


   if(username.getText().toString().isEmpty()|| password.getText().toString().isEmpty()){
       Toast.makeText(getApplicationContext(),"Please fill in fields   ",Toast.LENGTH_LONG).show();

   }
     else{

         auth.signInWithEmailAndPassword(username.getText().toString(),password.getText().toString())
                 .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful()){

                             Toast.makeText(getApplicationContext(),"Login successfully",Toast.LENGTH_LONG).show();

                                     Intent intentafter =new Intent(LogInActivity.this,UserOptions.class);
                                     startActivity(intentafter);
                                     overridePendingTransition(R.anim.right,R.anim.out);



                         }

                     }
                 }).addOnFailureListener(this,new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toast.makeText(LogInActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
             }
         });


   }

    }
}
