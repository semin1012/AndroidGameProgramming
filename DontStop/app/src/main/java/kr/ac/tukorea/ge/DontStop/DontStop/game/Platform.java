package kr.ac.tukorea.ge.DontStop.DontStop.game;

import androidx.annotation.NonNull;

import java.util.Random;

import kr.ac.tukorea.ge.DontStop.DontStop.R;
import kr.ac.tukorea.ge.DontStop.framework.scene.RecycleBin;

public class Platform extends MapObject {
    private Type type;

    public boolean canPass() {
        return type != Type.T_LONG;
    }
    public enum Type {
        T_LONG, T_SHORT, COUNT;
        int resId() { return resIds[this.ordinal()]; }
        float width() { return widths[this.ordinal()]; }
        int height() { return heights[this.ordinal()]; }
        static int[] resIds = {
                R.mipmap.platform01,
                R.mipmap.platform02,
        };
        static float[] widths = { 3, 2 };
        static int[] heights = { 1, 1 };
        static Type random(Random random) {
            return Type.values()[random.nextInt(2)];
        }
    }
    private Platform() {}
    public static Platform get(Type type, float left, float top) {
        Platform platform = (Platform) RecycleBin.get(Platform.class);
        if (platform == null) {
            platform = new Platform();
        }
        platform.init(type, left, top);
        return platform;
    }
    public void init(Type type, float left, float top) {
        this.type = type;
        setBitmapResource(type.resId());
        width = type.width();
        height = type.height();
        // Platform 은 x,y 를 사용하지 않고 dstRect 만을 사용하도록 한다.
        dstRect.set(left, top, left + width, top + height);
    }

    @Override
    protected MainScene.Layer getLayer() {
        return MainScene.Layer.platform;
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + System.identityHashCode(this) + "(" + width + "x" + height + ")";
    }
}
