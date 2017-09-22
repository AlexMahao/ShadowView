package com.spearbothy.shadowview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private ShadowView mShadowView;
    private SeekBar mDx;
    private SeekBar mDy;
    private SeekBar mRadius;
    private SeekBar mRectRadius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShadowView = (ShadowView) findViewById(R.id.shadow);

        mDx = (SeekBar) findViewById(R.id.dx);
        mDy = (SeekBar) findViewById(R.id.dy);
        mRadius = (SeekBar) findViewById(R.id.radius);
        mRectRadius = (SeekBar) findViewById(R.id.rect_radius);

        mDx.setOnSeekBarChangeListener(this);

        mDy.setOnSeekBarChangeListener(this);

        mRadius.setOnSeekBarChangeListener(this);

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
        mShadowView.setShadow(mDx.getProgress(), mDy.getProgress(), mRadius.getProgress());
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
