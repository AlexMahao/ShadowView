package com.spearbothy.shadowview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by mahao on 17-9-21.
 */

public class ShadowView extends FrameLayout {

    private int mOriginHeight;
    private int mOriginWidth;

    private int mDx;
    private int mDy;
    private int mRadius;

    private int mTopShadow;
    private int mLeftShadow;
    private int mRightShadow;
    private int mBottomShadow;

    private int mRectRadius;

    private Paint mShadowPaint;

    private boolean isMeasure = true;

    private MarginLayoutParams mParams;

    public ShadowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);//没有这句不显示

        setWillNotDraw(false);

        mShadowPaint = new Paint();
        mShadowPaint.setColor(Color.WHITE);
        mShadowPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isMeasure) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            setMeasuredDimension(mOriginWidth + mLeftShadow + mRightShadow, mOriginHeight + mTopShadow + mBottomShadow);
            // 计算margin

            MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();

            params.setMargins(mParams.leftMargin + (mRightShadow - mLeftShadow) / 2, mParams.topMargin - mTopShadow, mParams.rightMargin, mParams.bottomMargin - mBottomShadow);

        }
        Log.i("info", "onMeasure");
//        measure(widthMeasureSpec,heightMeasureSpec);

        Log.i("info", "mOriginHeight:" + getMeasuredHeight() + "mOriginWidth:" + getMeasuredWidth());
//        setMeasuredDimension(mOriginWidth + mLeftShadow + mRightShadow, mOriginHeight + mTopShadow + mBottomShadow);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("info", "onSizeChanged:" + "w:" + w + "h:" + h + "oldw:" + oldw + "oldh:" + oldh);
//        mOriginWidth = w;
//        mOriginHeight = h;
        if (isMeasure) {
            mOriginHeight = h;
            mOriginWidth = w;
            isMeasure = false;

            mParams = new MarginLayoutParams((MarginLayoutParams) getLayoutParams());
            invalidate();
        }
    }

    public void setShadow(int dx, int dy, int radius) {
        mDx = dx;
        mDy = dy;
        mRadius = radius;

        if (Math.abs(dy) > radius) {
            if (dy > 0) {
                mTopShadow = 0;
                mBottomShadow = radius + dy;
            } else {
                mTopShadow = radius - dy;
                mBottomShadow = 0;
            }
        } else {
            mTopShadow = radius - dy;
            mBottomShadow = radius + dy;
        }
        if (Math.abs(dx) > radius) {
            if (dx > 0) {
                mLeftShadow = 0;
                mRightShadow = radius + dx;
            } else {
                mLeftShadow = radius - dx;
                mRightShadow = 0;
            }
        } else {
            mLeftShadow = radius - dx;
            mRightShadow = radius + dx;
        }

        setPadding(mLeftShadow, mTopShadow, mRightShadow, mBottomShadow);

        invalidate();

        Log.i("info", "setShadow");
    }

    public void setRadius(int radius) {
        mRectRadius = radius;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 建立Paint 物件

        // 设定阴影(柔边, X 轴位移, Y 轴位移, 阴影颜色)
        mShadowPaint.setShadowLayer(mRadius, mDx, mDy, 0x2600A4A0);

        RectF rect = new RectF(mLeftShadow, mTopShadow, mLeftShadow + mOriginWidth, mTopShadow + mOriginHeight);

        // 实心矩形& 其阴影
        canvas.drawRoundRect(rect, mRectRadius, mRectRadius, mShadowPaint);
    }
}
