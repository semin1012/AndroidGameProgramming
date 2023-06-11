package kr.ac.tukorea.ge.DontStop.DontStop.game;

import kr.ac.tukorea.ge.DontStop.DontStop.R;
import kr.ac.tukorea.ge.DontStop.framework.objects.Button;
import kr.ac.tukorea.ge.DontStop.framework.objects.Sprite;
import kr.ac.tukorea.ge.DontStop.framework.scene.BaseScene;

public class PausedScene extends BaseScene {
    private float angle;

    public enum Layer {
        bg, title, touch, COUNT
    }
    public PausedScene() {
        initLayers(Layer.COUNT);
        add(Layer.bg, new Sprite(R.mipmap.score_png, 16.0f, 9.0f, 16, 9));
//        add(Layer.touch, new Button(R.mipmap.btn_resume_n, 8f, 3.5f, 2.667f, 1f, new Button.Callback() {
//            @Override
//            public boolean onTouch(Button.Action action) {
//                if (action == Button.Action.pressed) {
//                    popScene();
//                }
//                return false;
//            }
//        }));
//        add(Layer.touch, new Button(R.mipmap.btn_exit_n, 8f, 5.5f, 2.667f, 1f, new Button.Callback() {
//            @Override
//            public boolean onTouch(Button.Action action) {
//                if (action == Button.Action.pressed) {
//                    finishActivity();
//                }
//                return false;
//            }
//        }));
    }

//    @Override
//    public boolean isTransparent() {
//        return true;
//    }

    @Override
    public void update(long elapsedNanos) {
        super.update(elapsedNanos);
        angle += BaseScene.frameTime * Math.PI / 4;
        float x = (float) (8.0f + 4.0f * Math.cos(angle));
        float y = (float) (4.5f + 2.0f * Math.sin(angle));
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }
}
