package kr.ac.tukorea.ge.DontStop.DontStop.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Random;

import kr.ac.tukorea.ge.DontStop.DontStop.R;
import kr.ac.tukorea.ge.DontStop.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.DontStop.framework.scene.RecycleBin;

public class Coin extends MapObject {
    protected static final long createdOn = System.currentTimeMillis();;
    protected float fps = 32;
    private static final int ITEMS_IN_A_ROW = 30;
    private static final int SIZE = 66;
    private static final int BORDER = 2;
    public static final float INSET = 0.03f;
    protected RectF collisionRect = new RectF();
    protected static Rect[][] srcRects = {
            makeRects(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23),      // State.running
    };
    Coin() {
        super(MainScene.Layer.coin);
        setBitmapResource(R.mipmap.coin2);
        width = height = 1;
    }
    public static Coin get(float left, float top) {
        Coin item = (Coin) RecycleBin.get(Coin.class);
        if (item == null) {
            item = new Coin();
        }
        item.init(left, top);
        return item;
    }

    private void init(float left, float top) {
        //setSrcRect(index);
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

    protected static Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = (idx % 100) * 30;
            int t = (idx / 100) * 30;
            rects[i] = new Rect(l, t, l + 30, t + 30);
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