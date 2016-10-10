package com.mmf.circleprogress.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.mmf.circleprogress.R;


/**
 * @author MMF
 * @ClassName: SpringProgressView
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2016-5-26
 */
public class SpringProgressView extends View {
    private Context context;
    /**
     * 分段颜色
     */
    private int[] SECTION_COLORS = new int[3];
    /**
     * 进度条最大值
     */
    private float maxCount;
    /**
     * 进度条当前值
     */
    private float currentCount;
    /**
     * 画笔
     */
    private Paint mPaint;
    private int mWidth, mHeight, height;

    public SpringProgressView(Context context, AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        this.context = context;
    }

    public SpringProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        this.context = context;
    }

    public SpringProgressView(Context context) {
        super(context);
        initView(context);
        this.context = context;
    }

    private void initView(Context context) {
        SECTION_COLORS[0] = context.getResources().getColor(R.color.blue1);
        SECTION_COLORS[1] = context.getResources().getColor(R.color.blue2);
        SECTION_COLORS[2] = context.getResources().getColor(R.color.blue);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        height = mHeight / 5;
        Paint textpaint = new Paint();
//		textpaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size));
        textpaint.setTextSize(height * 3 / 2);
        drawBg(canvas);
        float section = (currentCount) / maxCount;
       int padding = getResources().getDimensionPixelSize(R.dimen.line_width);
        RectF rectProgressBg = new RectF(height * 2, height * 2 + padding, (mWidth - padding * height) * section,
                height * 3 - padding);

        if (section <= 1.0f / 3.0f) {
            if (section != 0.0f) {
                mPaint.setColor(SECTION_COLORS[0]);
            } else {
                mPaint.setColor(Color.TRANSPARENT);
            }
        } else {
            int count = (section <= 1.0f / 3.0f * 2) ? 2 : 3;
            int[] colors = new int[count];
            System.arraycopy(SECTION_COLORS, 0, colors, 0, count);
            float[] positions = new float[count];
            if (count == 2) {
                positions[0] = 0.0f;
                positions[1] = 1.0f - positions[0];
            } else {
                positions[0] = 0.0f;
                positions[1] = (maxCount / 3) / currentCount;
                positions[2] = 1.0f - positions[0] * 2;
            }
            positions[positions.length - 1] = 1.0f;
            LinearGradient shader = new LinearGradient(height * 2, height * 2 + padding, (mWidth)
                    * section - height * 2, height * 3 - padding, colors, null, Shader.TileMode.MIRROR);
            mPaint.setShader(shader);
        }
        canvas.drawRoundRect(rectProgressBg, 0, 0, mPaint);
        Paint paint = new Paint(); // 设置一个笔刷大小是3的黄色的画笔
        paint.setColor(Color.YELLOW);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);
        if (currentCount >= 1) {
            paint.setColor(SECTION_COLORS[0]);
            RectF rect = new RectF(padding, height + padding, height * 3 - padding, height * 4 - padding);
            canvas.drawArc(rect, // 弧线所使用的矩形区域大小
                    0, // 开始角度
                    360, // 扫过的角度
                    false, // 是否使用中心
                    paint);
            textpaint.setColor(context.getResources().getColor(R.color.white));
        } else {
            textpaint.setColor(context.getResources().getColor(R.color.gray));
        }
        textpaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("0", (int) (height * 1.5), height * 3, textpaint);
        if (currentCount == maxCount) {
            paint.setColor(SECTION_COLORS[2]);
            RectF rect1 = new RectF(mWidth - height * 3 + padding, height + padding, mWidth - padding, height * 4 - padding);
            canvas.drawArc(rect1, // 弧线所使用的矩形区域大小
                    0, // 开始角度
                    360, // 扫过的角度
                    false, // 是否使用中心
                    paint);
            textpaint.setColor(context.getResources().getColor(R.color.white));
        } else {
            textpaint.setColor(context.getResources().getColor(R.color.gray));
        }
        canvas.drawText(String.valueOf((int) maxCount), mWidth - (int) (height * 1.5), height * 3, textpaint);

        Bitmap bitmap = BitmapFactory.decodeResource(this.getContext()
                .getResources(), R.mipmap.icon_chi);
        if (currentCount == 0) {
            drawImage(canvas, bitmap, 0, height / 2,
                    height * 3, height * 3, 0, 0, paint);
        } else {
            drawImage(canvas, bitmap, (int) ((mWidth - 3 * height) * section), height / 2,
                    height * 3, height * 3, 0, 0, paint);
        }


        textpaint.setTextAlign(Paint.Align.LEFT);
        textpaint.setColor(context.getResources().getColor(R.color.black_25));
        if ((mWidth) * section < mWidth / 2) {
            canvas.drawText((int) currentCount + "项", ((int) ((mWidth - 2 * height) * section)) + height * 3, height * 4 + height / 2, textpaint);
        } else {
            canvas.drawText((int) currentCount + "项", (int) ((mWidth - 3 * height) * section) - height * 3, height * 4 + height / 2, textpaint);
        }

    }


    public void drawBg(Canvas canvas) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(context.getResources().getColor(R.color.app_bg));
        RectF rectBlackBg = new RectF(height / 2, height * 2, mWidth - height / 2, height * 3);
        canvas.drawRoundRect(rectBlackBg, 0, 0, mPaint);

        Paint paint = new Paint(); // 设置一个笔刷大小是3的黄色的画笔
        paint.setColor(context.getResources().getColor(R.color.app_bg));
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);
        RectF rect = new RectF(0, height, height * 3, height * 4);

        canvas.drawArc(rect, // 弧线所使用的矩形区域大小
                0, // 开始角度
                360, // 扫过的角度
                false, // 是否使用中心
                paint);
        RectF rect1 = new RectF(mWidth - height * 3, height, mWidth, height * 4);
        canvas.drawArc(rect1, // 弧线所使用的矩形区域大小
                0, // 开始角度
                360, // 扫过的角度
                false, // 是否使用中心
                paint);
    }

    public void drawImage(Canvas canvas, Bitmap blt, int x, int y, int w,
                          int h, int bx, int by, Paint paint) { // x,y表示绘画的起点，
        Rect src = new Rect();// 图片
        Rect dst = new Rect();// 屏幕位置及尺寸
        // src 这个是表示绘画图片的大小
        src.left = bx; // 0,0
        src.top = by;
        src.right = bx + w;// mBitDestTop.getWidth();,这个是桌面图的宽度，
        src.bottom = by + h;// mBitDestTop.getHeight()/2;// 这个是桌面图的高度的一半
        // 下面的 dst 是表示 绘画这个图片的位置
        dst.left = x; // miDTX,//这个是可以改变的，也就是绘图的起点X位置
        dst.top = y; // mBitQQ.getHeight();//这个是QQ图片的高度。 也就相当于 桌面图片绘画起点的Y坐标
        dst.right = x + w; // miDTX + mBitDestTop.getWidth();// 表示需绘画的图片的右上角
        dst.bottom = y + h; // mBitQQ.getHeight() +
        // mBitDestTop.getHeight();//表示需绘画的图片的右下角
        canvas.drawBitmap(blt, null, dst, null);// 这个方法 第一个参数是图片原来的大小，第二个参数是
        // 绘画该图片需显示多少。也就是说你想绘画该图片的某一些地方，而不是全部图片，第三个参数表示该图片绘画的位置

        src = null;
        dst = null;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY
                || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = 0;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST
                || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = dipToPx(15);
        } else {
            mHeight = heightSpecSize;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    private int dipToPx(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /***
     * 设置最大的进度值
     *
     * @param maxCount
     */
    public void setMaxCount(float maxCount) {
        this.maxCount = maxCount;
        this.currentCount = currentCount > maxCount ? maxCount : currentCount;
        invalidate();
    }

    /***
     * 设置当前的进度值
     *
     * @param currentCount
     */
    public void setCurrentCount(float currentCount) {
        this.currentCount = currentCount > maxCount ? maxCount : currentCount;
        invalidate();
    }

}