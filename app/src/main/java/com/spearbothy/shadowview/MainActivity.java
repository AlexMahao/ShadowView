package com.spearbothy.shadowview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private ShadowView mShadowView;
    private SeekBar mDx;
    private SeekBar mDy;
    private SeekBar mRadius;
    private SeekBar mRectRadius;
    private View mRootView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShadowView = (ShadowView) findViewById(R.id.shadow);
        mRootView = findViewById(R.id.rootView);

        mDx = (SeekBar) findViewById(R.id.dx);
        mDy = (SeekBar) findViewById(R.id.dy);
        mRadius = (SeekBar) findViewById(R.id.radius);
        mRectRadius = (SeekBar) findViewById(R.id.rect_radius);

        mDx.setOnSeekBarChangeListener(this);
        mDx.setProgress(50);
        mDy.setOnSeekBarChangeListener(this);
        mDy.setProgress(50);
        mRadius.setOnSeekBarChangeListener(this);
        mRadius.setProgress(50);
        mRectRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mShadowView.setRadius(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        mShadowView.setShadow(mDx.getProgress() - 50, mDy.getProgress() - 50, mRadius.getProgress() - 50);
        mRootView.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
