package com.mmf.circleprogress.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mmf.circleprogress.R;
import com.mmf.circleprogress.widget.CircleProgressView;


public class MainActivity extends AppCompatActivity {
    float progress = 3;
    CircleProgressView demoMpc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        demoMpc = (CircleProgressView) findViewById(R.id.demo_mpc);
        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        while (progress <= 100) {

                            try {
                                progress += 3;
                                runOnUiThread(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                demoMpc.setSmoothPercent(progress / 100f);
                                            }
                                        }
                                );
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }).start();
            }
        });

    }


}

