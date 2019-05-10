package app.view.custom.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.nfc.Tag;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * 手指在移动范围时小球跟着手指移动
 */
public class EventMoveView extends View {

    private static final String TAG = "EventMoveView";

    private Paint mAreaPaint;  // 移动区域画笔
    private Paint mBallPaint;  // 小球画笔

    private int mAreaRadius = 500; //移动区域的半径
    private int mBallRadius = 50;  //小球的半径

    private float mCenterX; //中心坐标X轴
    private float mCenterY; //中心坐标Y轴

    private float mBallX; //小球的坐标
    private float mBallY; //小球的坐标

    private float mDist;    //当前点击的坐标距离中心坐标的距离

    private float MOVE_OFFSET; //偏移量

    public EventMoveView(Context context) {
        this(context, null);
    }

    public EventMoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {

        mAreaPaint = new Paint();
        mAreaPaint.setColor(Color.RED);
        mAreaPaint.setAntiAlias(true); // 抗锯齿
        mAreaPaint.setStrokeWidth(10);  // 设置线条宽度
        mAreaPaint.setStyle(Paint.Style.STROKE); // 描边效果

        mBallPaint = new Paint();
        mBallPaint.setAntiAlias(true); // 抗锯齿
        mBallPaint.setStyle(Paint.Style.FILL);   // 实心
        mBallPaint.setColor(Color.BLUE);

        MOVE_OFFSET = mAreaRadius / 4;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterX = getWidth() / 2;
        mCenterY = getHeight() / 2;

        mBallX = mCenterX;
        mBallY = mCenterY;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 首先我们要调用超类的onMeasure借口
        // 原因是我们自己去实现一个方法获得长度、宽度太麻烦了
        // 使用超类的的方法非常方便而且让复杂的细节可控
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 在这里我们不能使用getWidth()和getHeight()。
        // 因为这两个方法只能在View的布局完成后才能使用，而一个View的绘制过程是先绘制元素，再绘制Layout
        // 所以我们必须使用getMeasuredWidth()和getMeasuredHeight()
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "------------------------->");
        // 画可移动的区域-大圆
        canvas.drawCircle(mCenterX, mCenterY, mAreaRadius, mAreaPaint);

        // 画小球
        canvas.drawCircle(mBallX, mBallY, mBallRadius, mBallPaint);
    }

    /**
     * 重写该方法时小球跟随手指移动
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 计算当前手指点击的坐标距离中心坐标的距离
                mDist = (float) Math.hypot(event.getX() - mCenterX, event.getY() - mCenterY);

                // 判断是否在可移动范围内
                if (mDist > mAreaRadius) { // 距离不在大圆半径内
                    Toast.makeText(getContext(), "不在范围内！", Toast.LENGTH_SHORT).show();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // 计算当前手指点击的坐标距离中心坐标的距离
                mDist = (float) Math.hypot(event.getX() - mCenterX, event.getY() - mCenterY);

                // 判断是否在可移动范围内
                if (mDist < (mAreaRadius - mBallRadius)) {
                    mBallX = event.getX();
                    mBallY = event.getY();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                // 抬起时让小球恢复原始状态
                mBallX = mCenterX;
                mBallY = mCenterY;
                invalidate();
                break;
        }
        return true;
    }
}
