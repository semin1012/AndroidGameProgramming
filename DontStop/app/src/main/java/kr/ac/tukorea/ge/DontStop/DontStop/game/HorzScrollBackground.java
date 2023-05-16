package kr.ac.tukorea.ge.DontStop.DontStop.game;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.DontStop.framework.objects.Sprite;
import kr.ac.tukorea.ge.DontStop.framework.scene.BaseScene;
import kr.ac.tukorea.ge.DontStop.framework.view.Metrics;

public class HorzScrollBackground extends Sprite {
    private final float speed;
    public HorzScrollBackground(int bitmapResId, float speed) {
        setBitmapResource(bitmapResId);
        this.width = bitmap.getWidth() * Metrics.game_height / bitmap.getHeight();
        this.speed = speed;
    }
    @Override
    public void update() {
        x += speed * BaseScene.frameTime;
    }

    @Override
    public void draw(Canvas canvas) {
        float curr = x % width;
        if (curr > 0) curr -= width;
        while (curr < Metrics.game_width) {
            dstRect.set(curr, 0, curr + width, Metrics.game_height);
            canvas.drawBitmap(bitmap, null, dstRect, null);
            curr += width;
        }
    }}
