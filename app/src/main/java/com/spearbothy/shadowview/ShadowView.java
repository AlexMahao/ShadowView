package com.spearbothy.shadowview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by mahao on 17-9-21.
 */

public class ShadowView extends FrameLayout {

    private int mOriginHeight;
    private int mOriginWidth;

    private int mDx;
    private int mDy; // y轴偏移
    private int mRadius; // 模糊程度

    private int mTopShadow;
    private int mLeftShadow;
    private int mRightShadow;
    private int mBottomShadow;

    private int mRectRadius;

    private Paint mShadowPaint;

    private boolean isMeasure = true;

    private MarginLayoutParams mParams;


    private boolean mCenterX = true; // 是否基于x轴居中
    private boolean mCenterY = false; // 是否基于y轴居中


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
//            Log.i("info", "height:" + getMeasuredHeight() + "width:" + getMeasuredWidth());
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (isMeasure) {
            mOriginHeight = h;
            mOriginWidth = w;
            isMeasure = false;

            mParams = new MarginLayoutParams((MarginLayoutParams) getLayoutParams());
            invalidate();
        }
    }

    public void setShadow(int dx, int dy, int radius) {
        if (mParams == null) {
            return;
        }
        mDx = dx;
        mDy = dy;
        mRadius = radius;
        calculateShadow();
        resetMargin();
        setPadding(mLeftShadow, mTopShadow, mRightShadow, mBottomShadow);
        invalidate();
    }

    public void setRadius(int radius) {
        mRectRadius = radius;
        invalidate();
    }

    public void calculateShadow() {
        if (Math.abs(mDy) > mRadius) {
            if (mDy > 0) {
                mTopShadow = 0;
                mBottomShadow = mRadius + mDy;
            } else {
                mTopShadow = mRadius - mDy;
                mBottomShadow = 0;
            }
        } else {
            mTopShadow = mRadius - mDy;
            mBottomShadow = mRadius + mDy;
        }

        if (mCenterX) {
            mLeftShadow = mRightShadow = Math.abs(mDx) + mRadius;
        } else if (Math.abs(mDx) > mRadius) {
            if (mDx > 0) {
                mLeftShadow = 0;
                mRightShadow = mRadius + mDx;
            } else {
                mLeftShadow = mRadius - mDx;
                mRightShadow = 0;
            }
        } else {
            mLeftShadow = mRadius - mDx;
            mRightShadow = mRadius + mDx;
        }
    }

    public void resetMargin() {
        int leftMargin = mParams.leftMargin;
        int rightMargin = mParams.rightMargin;
        int topMargin = mParams.topMargin;
        int bottomMargin = mParams.bottomMargin;

        MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
        if (!mCenterX) {
            leftMargin -= mLeftShadow;
            rightMargin -= mRightShadow;
        }
        if (!mCenterY) {
            topMargin -= mTopShadow;
            bottomMargin -= mBottomShadow;
        }
        params.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
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
