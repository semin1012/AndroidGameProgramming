package kr.ac.tukorea.ge.DontStop.DontStop.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;

import kr.ac.tukorea.ge.DontStop.DontStop.R;
import kr.ac.tukorea.ge.DontStop.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.DontStop.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.DontStop.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.DontStop.framework.scene.BaseScene;
import kr.ac.tukorea.ge.DontStop.framework.view.Metrics;

public class Player extends AnimSprite implements IBoxCollidable {
    private float jumpSpeed;
    private static final float JUMP_POWER = 9.0f;
    private static final float GRAVITY = 17.0f;
    private RectF collisionRect = new RectF();

    public Player() {
        super(R.mipmap.cookie002, 2.0f, 3.0f, 2.0f, 2.0f, 8, 1);
        fixCollisionRect();
    }

    protected enum State {
        running, jump, doubleJump, falling, idle, COUNT
    }
//    protected Rect[] srcRects
    protected static Rect[][] srcRects = {
            makeRects(100, 101, 102, 103),      // State.running
            makeRects(1, 2),                    // State.jump
            makeRects(1, 2, 3, 4, 5),           // State.doubleJump
            makeRects(5),                       // State.falling
            makeRects(300, 301, 302, 303, 304), // State.idle
    };
    protected static float[][] edgeInsetRatios = {
            { -0.1f, 0.01f, 0.1f, 0.0f }, // State.running
            { -0.0f, 0.0f, 0.1f, 0.0f }, // State.jump
            { -0.0f, 0.1f, 0.1f, 0.0f }, // State.doubleJump
            { -0.1f, 0.01f, 0.2f, 0.0f }, // State.falling
            { -0.1f, 0.01f, 0.1f, 0.0f }, // State.idle
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
        switch (state) {
        case jump:
        case doubleJump:
        case falling:
            float dy = jumpSpeed * BaseScene.frameTime;
            jumpSpeed += GRAVITY * BaseScene.frameTime;
            if (jumpSpeed >= 0) { // 낙하하고 있다면 발밑에 땅이 있는지 확인한다
                float foot = collisionRect.bottom;
                float floor = findNearestPlatformTop(foot);
                if (foot + dy >= floor) {
                    dy = floor - foot;
                    state = State.running;
                }
            }
            y += dy;
            //fixDstRect();
            dstRect.offset(0, dy);
            fixCollisionRect();
            break;
        case running:
            float foot = collisionRect.bottom;
            float floor = findNearestPlatformTop(foot);
            if (foot < floor) {
                state = State.falling;
                jumpSpeed = 0;
            }
            break;
        case idle:
            break;
        }
    }

    private float findNearestPlatformTop(float foot) {
        Platform platform = findNearestPlatform(foot);
        if (platform == null) return Metrics.game_height;
        return platform.getCollisionRect().top;
    }

    private Platform findNearestPlatform(float foot) {
        Platform nearest = null;
        MainScene scene = (MainScene) BaseScene.getTopScene();
        ArrayList<IGameObject> platforms = scene.getObjectsAt(MainScene.Layer.platform);
        float top = Metrics.game_height;
        for (IGameObject obj: platforms) {
            Platform platform = (Platform) obj;
            RectF rect = platform.getCollisionRect();
            if (rect.left > x || x > rect.right) {
                continue;
            }
            //Log.d(TAG, "foot:" + foot + " platform: " + rect);
            if (rect.top < foot) {
                continue;
            }
            if (top > rect.top) {
                top = rect.top;
                nearest = platform;
            }
            //Log.d(TAG, "top=" + top + " gotcha:" + platform);
        }
        return nearest;
    }

    private void fixCollisionRect() {
        float[] insets = edgeInsetRatios[state.ordinal()];
        collisionRect.set(
                dstRect.left + width * insets[0],
                dstRect.top + height * insets[1],
                dstRect.right - width * insets[2],
                dstRect.bottom - height * insets[3]);
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        Rect[] rects = srcRects[state.ordinal()];
        int frameIndex = Math.round(time * fps) % rects.length;
        canvas.drawBitmap(bitmap, rects[frameIndex], dstRect, null);
    }

    protected State state = State.running;
    public void jump() {
        if (state == State.running) {
            state = State.jump;
            jumpSpeed = -JUMP_POWER;
        } else if (state == State.jump) {
            jumpSpeed = -JUMP_POWER;
            state = State.doubleJump;
        }
    }
    public void fall() {
        if (state != State.running) return;
        float foot = collisionRect.bottom;
        Platform platform = findNearestPlatform(foot);
        if (platform == null) return;
        if (!platform.canPass()) return;
        state = State.falling;
        dstRect.offset(0, 0.001f);
        collisionRect.offset(0, 0.001f);
        jumpSpeed = 0;
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }
}
