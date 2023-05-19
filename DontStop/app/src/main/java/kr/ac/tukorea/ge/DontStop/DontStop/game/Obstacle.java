package kr.ac.tukorea.ge.DontStop.DontStop.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import androidx.annotation.NonNull;

import java.util.Random;

import kr.ac.tukorea.ge.DontStop.DontStop.R;
import kr.ac.tukorea.ge.DontStop.framework.scene.RecycleBin;

public class Obstacle extends MapObject {
    protected static final long createdOn = System.currentTimeMillis();;
    protected float fps = 32;
    private static final int SIZE = 66;
    private static final int BORDER = 2;
    public static final float INSET = 0.03f;
    protected RectF collisionRect = new RectF();
    protected static Rect[][] srcRects = {
            makeRects(0, 1, 2, 3, 4, 5, 6, 7),
            makeRects(100, 101, 102, 103),
            makeRects(200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210),
    };

    public enum Type {
        T_TREE;

        float width() { return widths[this.ordinal()]; }
        int height() { return heights[this.ordinal()]; }

        static float[] widths = { 3, 2 };
        static int[] heights = { 1, 1 };
        static Platform.Type random(Random random) {
            return Platform.Type.values()[random.nextInt(2)];
        }
    }

    Obstacle() {
        setBitmapResource(R.mipmap.obstance);
        width = height = 1;
    }
    public static Obstacle get(float left, float top) {
        Obstacle item = (Obstacle) RecycleBin.get(Obstacle.class);
        if (item == null) {
            item = new Obstacle();
        }
        item.init(left, top);
        return item;
    }

    private void init(float left, float top) {
        width = 3;
        height = 5;
        // Platform 은 x,y 를 사용하지 않고 dstRect 만을 사용하도록 한다.
        dstRect.set(left, top, left + width, top + height);
    }

    private void setSrcRect(int index) {
        //int x = index % ITEMS_IN_A_ROW;
        //int y = index / ITEMS_IN_A_ROW;
        //int left = x * (SIZE + BORDER) + BORDER;
        //int top = y * (SIZE + BORDER) + BORDER;
        //srcRect.set(left, top, left + SIZE, top + SIZE);
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

    protected static Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = (idx % 100) * 90;
            int t = (idx / 100) * 150;
            rects[i] = new Rect(l, t, l + 90, t + 150);
        }
        return rects;
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        Rect[] rects = srcRects[1];
        float time = (now - createdOn) / 3000.0f;
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
