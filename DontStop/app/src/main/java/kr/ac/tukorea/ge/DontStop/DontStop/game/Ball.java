package kr.ac.tukorea.ge.DontStop.DontStop.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.ge.DontStop.DontStop.R;
import kr.ac.tukorea.ge.DontStop.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.DontStop.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.DontStop.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.DontStop.framework.res.Sound;
import kr.ac.tukorea.ge.DontStop.framework.scene.BaseScene;
import kr.ac.tukorea.ge.DontStop.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.DontStop.framework.view.Metrics;

public class Ball extends MapObject {
    public Type type;
    protected float fps = 32;
    private float flyingSpeed;
    private static final float FLYING_POWER = 9.0f;
    private static final float GRAVITY = 17.0f;
    protected long createdOn = System.currentTimeMillis();
    public static final float INSET = 0.03f;
    private RectF collisionRect = new RectF();

    private BaseScene scene = BaseScene.getTopScene();

    public enum Type {
        SWORD, WIZARD, ARCHER;
        int resId() { return resIds[this.ordinal()]; }

        static int[] resIds = {
                R.mipmap.effect01,
                R.mipmap.effect02,
                R.mipmap.effect04,
        };
    }

    //    protected Rect[] srcRects
    protected static Rect[][] srcRects = {
            makeRects(0, 1, 2, 3, 4,
                    100, 101, 102, 103, 104,
                    200, 201, 202, 203, 204,
                    300, 301, 302, 303, 304,
                    400, 401, 402, 403, 404),
            makeRects(0, 1, 2, 3, 4,
                    100, 101, 102, 103, 104,
                    200, 201, 202, 203, 204,
                    300, 301, 302, 303, 304),
            makeRects(0, 1, 2, 3, 4,
                    100, 101, 102, 103, 104,
                    200, 201, 202, 203, 204,
                    300, 301, 302, 303, 304),
    };

    protected static float[][] edgeInsetRatios = {
            { 0.0f, 0.0f, 0.0f, 0.0f },
            { 0.0f, 0.0f, 0.0f, 0.0f },
            { 0.0f, 0.0f, 0.0f, 0.0f },
    };


    public Ball() {
        super(MainScene.Layer.attackBall);
        setBitmapResource(R.mipmap.effect01);
        width = height = 2;
        //setBitmapResource(type.resId());
    }

    public static Ball get(float left, float top, Type type) {
        Ball item = (Ball) RecycleBin.get(Ball.class);
        if (item == null) {
            item = new Ball();
        }
        if ( left == 0 && top == 0 ) {
            item.init(2, 5, type);
        }
        else { item.init(left, top - 0.5f, type); };
        return item;
    }

    public void init(float left, float top, Type type) {
        createdOn = System.currentTimeMillis();
        dstRect.set(0, top, 0 + width, top + height);
        this.type = type;
        setBitmapResource(type.resId());
        frameRate = 3.f;
        movable = true;
        Sound.playEffect(R.raw.fire);
    }


    float frameRate = 3.f;
    boolean movable = true;
    @Override
    public void update() {
        super.update();
        float[] insets = edgeInsetRatios[type.ordinal()];
        collisionRect.set(
                dstRect.left + width * insets[0],
                dstRect.top + height * insets[1],
                dstRect.right - width * insets[2],
                dstRect.bottom - height * insets[3]);

        if ( movable ) {
            float dx = flyingSpeed * BaseScene.frameTime;
            frameRate -= BaseScene.frameTime * 2.5f;
            flyingSpeed = 9 * frameRate;
            dstRect.offset(dx, 0);
        }
        if ( dstRect.left >= 11 ) {
            movable = false;
            dstRect.offset(-100, 0);
            //scene.remove(MainScene.Layer.attackBall, this);
        }


    }

    protected static Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = (idx % 100) * 192;
            int t = (idx / 100) * 190;
            rects[i] = new Rect(l, t, l + 190, t + 188);
        }
        return rects;
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        Rect[] rects = srcRects[type.ordinal()];
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
        return MainScene.Layer.attackBall;
    }
}
