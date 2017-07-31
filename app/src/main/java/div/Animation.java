package div;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.xicp.cjlhappiness.bluestart.R;
import java.util.Random;
import util.Date;

public class Animation extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Flake[]   mFlakes;
    private int           mViewWidth  = 200;
    private int           mViewHeight = 100;
    private int           mFlakeCount = 20;
    private int           mMinSize    = 50;
    private int           mMaxSize    = 70;
    private int           mSpeedX     = 1;
    private int           mSpeedY     = 10;
    private Bitmap        mSnowBitmap = null;
    private boolean       mStart      = false;

    public Animation(Context context) {
        this(context, null);
    }

    public Animation(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Animation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHolder();
        setZOrderOnTop(true);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Snow, defStyleAttr, 0);
        int cnt = array.getIndexCount();
        for (int i = 0; i < cnt; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
            case R.styleable.Snow_flakeCount:
                mFlakeCount = array.getInteger(attr, 20);
                break;
            case R.styleable.Snow_minSize:
                mMinSize = array.getInteger(attr, 50);
                break;
            case R.styleable.Snow_maxSize:
                mMaxSize = array.getInteger(attr, 70);
                break;
            case R.styleable.Snow_flakeSrc:
//                Integer srcId = array.getResourceId(attr, R.mipmap.snow_flake);
                mSnowBitmap   = BitmapFactory.decodeResource(getResources(), getResId());
                break;
            case R.styleable.Snow_speedX:
                mSpeedX = array.getInteger(attr, 1);
                break;
            case R.styleable.Snow_speedY:
                mSpeedY = array.getInteger(attr, 10);
                break;
            default:
                break;
            }
        }
        if (mMinSize > mMaxSize) {
            mMaxSize = mMinSize;
        }
        array.recycle();
    }

    private void initHolder() {
        mHolder = this.getHolder();
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        mHolder.addCallback(this);
    }

    private void initFlakes() {
        mFlakes = new Flake[mFlakeCount];
        for (int i = 0; i < mFlakes.length; i++) {
            boolean isRightDir = new Random().nextBoolean();
            mFlakes[i] = new Flake();
            mFlakes[i].setWidth(new Random().nextInt(mMaxSize-mMinSize) + mMinSize);
            mFlakes[i].setHeight(mFlakes[i].getWidth());
            mFlakes[i].setX(new Random().nextInt(mViewWidth));
            mFlakes[i].setY(-(new Random().nextInt(mViewHeight)));
            mFlakes[i].setSpeedY(new Random().nextInt(4) + mSpeedY);
            if (isRightDir) {
                mFlakes[i].setSpeedX(new Random().nextInt(2) + mSpeedX);
            }
            else {
                mFlakes[i].setSpeedX(-(new Random().nextInt(2) + mSpeedX));
            }
        }
    }

    private void updatePara() {
        int x;
        int y;
        for (Flake flake : mFlakes) {
            if (flake == null) {
                break;
            }
            x = flake.getX() + flake.getSpeedX();
            y = flake.getY() + flake.getSpeedY();
            if ((x > mViewWidth + flake.getWidth() || x < 0 - flake.getWidth())
                    || (y > mViewHeight + flake.getHeight())) {
                x = new Random().nextInt(mViewWidth);
                y = -flake.getHeight();
            }
            flake.setX(x);
            flake.setY(y);
        }
    }

    private void drawView() {
        if (mHolder == null) {
            return;
        }
        Canvas canvas = mHolder.lockCanvas();
        if (canvas == null) {
            return;
        }
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        drawFlag(canvas);
        mHolder.unlockCanvasAndPost(canvas);
    }

    private void drawFlag(Canvas canvas) {
        Rect  rect  = new Rect();
        Paint paint = new Paint();
        for (Flake flake : mFlakes) {
            rect.left   = flake.getX();
            rect.top    = flake.getY();
            rect.right  = rect.left + flake.getWidth();
            rect.bottom = rect.top  + flake.getHeight();
            canvas.drawBitmap(mSnowBitmap, null, rect, paint);
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        mStart = (visibility == VISIBLE);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initFlakes();
        start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode  = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            mViewWidth = (getPaddingStart() + mSnowBitmap.getWidth() + getPaddingEnd());
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            mViewHeight = (getPaddingTop() + mSnowBitmap.getHeight() + getPaddingBottom());
        }
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    public void start() {
        new Thread(){
            @Override
            public void run() {
                while (true) {
                    try {
                        if (mStart) {
                            updatePara();
                            drawView();
                        }
                        Thread.sleep(30);
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private int getResId(){
        int monthIndex = Date.getNowMonth();//2 3 4        5 6 7       8 9 10      11 1 2
        int resId;
        if (2 <= monthIndex && 5 > monthIndex)
            resId = R.mipmap.flower_flake;//春樱花
        else if(5 <= monthIndex && 8 > monthIndex)
            resId = R.mipmap.grass_flake;//夏蒲公英
        else if(8 <= monthIndex && 10 > monthIndex)
            resId = R.mipmap.leaf_flake;//秋枫叶
        else
            resId = R.mipmap.snow_flake;//冬雪花

        return resId;
    }

}
