package com.mmf.circleprogress.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.mmf.circleprogress.R;
import com.mmf.circleprogress.data.ListData;
import com.mmf.circleprogress.widget.CircleProgressView;
import com.mmf.circleprogress.widget.SpringProgressView;
import com.mmf.circleprogress.widget.fancycoverflow.FancyCoverFlow;
import com.mmf.circleprogress.widget.fancycoverflow.samples.shared.FancyCoverFlowSampleAdapter;


public class MainActivity extends AppCompatActivity {
    float progress = 3;
    CircleProgressView demoMpc;
    SpringProgressView sp_progress;

    private FancyCoverFlowSampleAdapter adapter;
    private FancyCoverFlow cinemaAllFilmGallery;

    private int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        demoMpc = (CircleProgressView) findViewById(R.id.demo_mpc);
        sp_progress = (SpringProgressView) findViewById(R.id.sp_progress);
        sp_progress.setMaxCount(20);
        sp_progress.setCurrentCount(16);
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


        cinemaAllFilmGallery = (FancyCoverFlow)findViewById(R.id.fancyCoverFlow);
        //设置电影显示的参数
        cinemaAllFilmGallery = (FancyCoverFlow)findViewById(R.id.fancyCoverFlow);
        cinemaAllFilmGallery.setUnselectedAlpha(1.0f);
        cinemaAllFilmGallery.setUnselectedSaturation(1.0f);
        cinemaAllFilmGallery.setUnselectedScale(0.70f);
        cinemaAllFilmGallery.setSpacing(10);
        cinemaAllFilmGallery.setMaxRotation(0);
        cinemaAllFilmGallery.setScaleDownGravity(0.5f);
        cinemaAllFilmGallery.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
        cinemaAllFilmGallery.setCallbackDuringFling(false);
        adapter = new FancyCoverFlowSampleAdapter(ListData.listUrl, this);
        cinemaAllFilmGallery.setAdapter(adapter);
        cinemaAllFilmGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (pos != position) {
                    pos = position;
                    adapter.changeSelected(position);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
       }


}

