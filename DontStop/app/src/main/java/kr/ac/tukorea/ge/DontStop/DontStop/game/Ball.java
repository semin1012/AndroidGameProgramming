package kr.ac.tukorea.ge.DontStop.DontStop.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;

import java.util.ArrayList;

import kr.ac.tukorea.ge.DontStop.DontStop.R;
import kr.ac.tukorea.ge.DontStop.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.DontStop.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.DontStop.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.DontStop.framework.scene.BaseScene;
import kr.ac.tukorea.ge.DontStop.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.DontStop.framework.view.Metrics;

public class Ball extends MapObject {
    private Player.Type type;
    protected float fps = 32;
    private float flyingSpeed;
    private static final float FLYING_POWER = 9.0f;
    private static final float GRAVITY = 17.0f;
    protected static final long createdOn = System.currentTimeMillis();;
    public static final float INSET = 0.03f;
    private RectF collisionRect = new RectF();

    public enum Type {
        SWORD, WIZARD, ARCHER;
        int resId() { return resIds[this.ordinal()]; }
        static int[] resIds = {
                R.mipmap.effect01,
        };
    }

    //    protected Rect[] srcRects
    protected static Rect[][] srcRects = {
            makeRects(0, 1, 2, 3, 4,
                    100, 101, 102, 103, 104,
                    200, 201, 202, 203, 204,
                    300, 301, 302, 303, 304,
                    400, 401, 402, 403, 404),

    };

    public Ball() {
        setBitmapResource(R.mipmap.effect01);
        width = height = 1;
        //setBitmapResource(type.resId());
    }

    public static Ball get(float left, float top) {
        Ball item = (Ball) RecycleBin.get(Ball.class);
        if (item == null) {
            item = new Ball();
        }
        item.init(left, top);
        return item;
    }

    private void init(float left, float top) {
        dstRect.set(left, top, left + width, top + height);
    }


    @Override
    public void update() {
        super.update();
        collisionRect.set(
                dstRect.left + width * INSET,
                dstRect.top + height * INSET,
                dstRect.right - width * INSET,
                dstRect.bottom - height * INSET);
    }


    protected static float[][] edgeInsetRatios = {
            { -0.1f, 0.01f, 0.1f, 0.0f },
    };
    protected static Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = (idx % 100) * 192;
            int t = (idx / 100) * 192;
            rects[i] = new Rect(l, t, l + 192, t + 192);
        }
        return rects;
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        Rect[] rects = srcRects[0];
        float time = (now - createdOn) / 1000.0f;
        int frameIndex = Math.round(time * fps) % rects.length;

        canvas.drawBitmap(bitmap, rects[frameIndex], dstRect, null);
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }


    @Override
    protected MainScene.Layer getLayer() {
        return MainScene.Layer.coin;
    }
}
