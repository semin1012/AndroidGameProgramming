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
import kr.ac.tukorea.ge.DontStop.framework.view.Metrics;

public class Ball extends AnimSprite implements IBoxCollidable {
    private Player.Type type;
    private float flyingSpeed;
    private static final float FLYING_POWER = 9.0f;
    private static final float GRAVITY = 17.0f;
    private RectF collisionRect = new RectF();

    public enum Type {
        SWORD, WIZARD, ARCHER;
        int resId() { return resIds[this.ordinal()]; }
        static int[] resIds = {
                R.mipmap.maple001,
                R.mipmap.maple002,
                R.mipmap.maple003
        };
    }

    private int coinNum = 0;

    public Ball() {
        super(Player.Type.SWORD.resId(), 2.0f, 3.0f, 2.0f, 2.0f, 8, 1);
        //setBitmapResource(type.resId());
    }

    //    protected Rect[] srcRects
    protected static Rect[][] srcRects = {
            makeRects(100, 101, 102, 103),      // State.running
            makeRects(0),                       // State.jump
            makeRects(0),                       // State.doubleJump
            makeRects(0),                       // State.falling
            makeRects(300, 301, 302, 303, 304), // State.idle
            makeRects(400, 401, 402, 403, 404, 405), // State.attack
    };
    protected static float[][] edgeInsetRatios = {
            { -0.1f, 0.01f, 0.1f, 0.0f }, // State.running
            { -0.0f, 0.0f, 0.1f, 0.0f },  // State.jump
            { -0.0f, 0.1f, 0.1f, 0.0f },  // State.doubleJump
            { -0.1f, 0.01f, 0.2f, 0.0f }, // State.falling
            { -0.1f, 0.01f, 0.1f, 0.0f }, // State.idle
            { -0.1f, 0.01f, 0.1f, 0.0f },
    };
    protected static Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = 72 + (idx % 100) * 340;
            int t = 130 + (idx / 100) * 340;
            rects[i] = new Rect(l, t, l + 210, t + 195);
        }
        return rects;
    }

    @Override
    public void update() {

    }


    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        //Rect[] rects = srcRects[state.ordinal()];
        //int frameIndex = Math.round(time * fps) % rects.length;
        //canvas.drawBitmap(bitmap, rects[frameIndex], dstRect, null);
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }
}
