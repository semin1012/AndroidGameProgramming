package kr.ac.tukorea.ge.DontStop.framework.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.viewbinding.BuildConfig;

import java.security.AccessController;
import java.util.ArrayList;

import kr.ac.tukorea.ge.DontStop.DontStop.game.MainScene;
import kr.ac.tukorea.ge.DontStop.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.DontStop.framework.scene.BaseScene;

/**
 * TODO: document your custom view class.
 */
public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();
    public static Resources res;
    public static GameView view;
    //    private Ball ball1, ball2;
    protected Paint fpsPaint;
    protected Paint borderPaint;

    protected boolean running;
    public Context context;

    public GameView(Context context) {
        super(context);
        this.context = context;
        init(null, 0);
    }
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }
    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public static void clear() {
        view = null;
        res = null;
    }

    public void setFullScreen() {
        setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
    private void init(AttributeSet attrs, int defStyle) {
        GameView.view = this;
        GameView.res = getResources();

        running = true;
        Choreographer.getInstance().postFrameCallback(this);

        if (BuildConfig.DEBUG) {
            fpsPaint = new Paint();
            fpsPaint.setColor(Color.BLUE);
            fpsPaint.setTextSize(100f);

            borderPaint = new Paint();
            borderPaint.setColor(Color.RED);
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setStrokeWidth(0.1f);
        }
        setFullScreen();
    }

    private long previousNanos;
    @Override
    public void doFrame(long nanos) {
        BaseScene scene = BaseScene.getTopScene();
        if (scene != null) {
            scene.update(nanos);
        }
        invalidate();
        if (running) {
            Choreographer.getInstance().postFrameCallback(this);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float view_ratio = (float)w / (float)h;
        float game_ratio = Metrics.game_width / Metrics.game_height;
        if (view_ratio > game_ratio) {
            Metrics.x_offset = (int) ((w - h * game_ratio) / 2);
            Metrics.y_offset = 0;
            Metrics.scale = h / Metrics.game_height;
        } else {
            Metrics.x_offset = 0;
            Metrics.y_offset = (int)((h - w / game_ratio) / 2);
            Metrics.scale = w / Metrics.game_width;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(Metrics.x_offset, Metrics.y_offset);
        canvas.scale(Metrics.scale, Metrics.scale);
        BaseScene scene = BaseScene.getTopScene();

        if (scene != null) {
            if (scene.clipsRect()) {
                canvas.clipRect(0, 0, Metrics.game_width, Metrics.game_height);
            }
            scene.draw(canvas);
        }

        if (BuildConfig.DEBUG) {
            canvas.drawRect(0, 0, Metrics.game_width, Metrics.game_height, borderPaint);
        }
        canvas.restore();

        if (BuildConfig.DEBUG && BaseScene.frameTime > 0) {
            int fps = (int) (1.0f / BaseScene.frameTime);
            int count = (scene != null) ? scene.count() : 0;
            canvas.drawText("FPS: " + fps + " objs: " + count, 100f, 200f, fpsPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = BaseScene.getTopScene().onTouchEvent(event);
        if (handled) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void pauseGame() {
        running = false;
    }

    public Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }
    public void resumeGame() {
        if (running) {
            return;
        }
        previousNanos = 0;
        running = true;
        Choreographer.getInstance().postFrameCallback(this);
    }
    public boolean handleBackKey() {
        BaseScene topScene = BaseScene.getTopScene();
        if (topScene == null) return false;
        return topScene.handleBackKey();
    }
}