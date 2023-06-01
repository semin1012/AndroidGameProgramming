package kr.ac.tukorea.ge.DontStop.framework.objects;

import android.os.Handler;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.DontStop.DontStop.R;
import kr.ac.tukorea.ge.DontStop.framework.interfaces.ITouchable;
import kr.ac.tukorea.ge.DontStop.framework.res.BitmapPool;
import kr.ac.tukorea.ge.DontStop.framework.view.Metrics;

public class Button extends Sprite implements ITouchable {
    private static final String TAG = Button.class.getSimpleName();
    private final Callback callback;
    private boolean isAttack;
    private boolean bAttackTouch = true;

    public interface Callback {
        public boolean onTouch();
    }
    public Button(int bitmapResId, float cx, float cy, float width, float height, boolean isAttack, Callback callback) {
        super(bitmapResId, cx, cy, width, height);
        this.callback = callback;
        this.isAttack = isAttack;
    }

    protected void setBitmapResource(int bitmapResId) {
        bitmap = BitmapPool.get(bitmapResId);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = Metrics.toGameX(e.getX());
        float y = Metrics.toGameY(e.getY());
        if (!dstRect.contains(x, y)) {
            return false;
        }
        //Log.d(TAG, "Button.onTouch(" + System.identityHashCode(this) + ", " + e.getAction() + ", " + e.getX() + ", " + e.getY());
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            if ( isAttack == false ) {
                callback.onTouch();
            }
            else {
                // 터치 가능할 때만
                if (bAttackTouch == true ) {
                    bAttackTouch = false;
                    // 리소스 바꾸고 터치 이벤트를 전달한다
                    setBitmapResource(R.mipmap.btn_attack_touched);

                    callback.onTouch();

                    // 1초 뒤에 다시 누를 수 있다
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setBitmapResource(R.mipmap.btn_attack_no);
                            bAttackTouch = true;
                        }
                    }, 1000); //딜레이 타임 조절
                }
            }
        }
        return true;
    }
}
