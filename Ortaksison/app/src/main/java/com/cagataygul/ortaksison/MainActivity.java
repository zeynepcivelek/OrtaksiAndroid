package com.cagataygul.ortaksison;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private  static int splashtime =10000;
    private ImageView img ;
    private ProgressBar prg;
    private int status=0;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        prg=(ProgressBar) findViewById(R.id.progrss) ;
        img = (ImageView) findViewById(R.id.imageView3);
        YoYo.with(Techniques.Bounce)
                .duration(700)
                .repeat(10).playOn(img);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (status<100){

                    status++;
                    android.os.SystemClock.sleep(50);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                         prg.setProgress(status);
                        }
                    });
                }

                Intent intent =new Intent(MainActivity.this,LogInActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right,R.anim.out);
            }
        }).start();



    }
}
