package com.cagataygul.ortaksison;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
 private ImageView reserbtn;
 private EditText mailtext;
 private FirebaseAuth fauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        reserbtn =(ImageView) findViewById(R.id.resetimgbtn);
        mailtext =(EditText) findViewById(R.id.editText);
        fauth = FirebaseAuth.getInstance();
        reserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremailreset = mailtext.getText().toString();
                if(TextUtils.isEmpty(useremailreset))
                {
                    Toast.makeText(ResetPassword.this,"Please Write your E-mail",Toast.LENGTH_LONG).show();
                }
                else
                {
                    fauth.sendPasswordResetEmail(useremailreset).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ResetPassword.this,"Please Check Your Email Account",Toast.LENGTH_LONG).show();
                                Intent intentres =new Intent(ResetPassword.this,LogInActivity.class);
                                startActivity(intentres);
                            }
                            else
                            {
                                Toast.makeText(ResetPassword.this,"Error",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
