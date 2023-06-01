package kr.ac.tukorea.ge.DontStop.DontStop.game;

import android.content.Context;

import kr.ac.tukorea.ge.DontStop.DontStop.R;
import kr.ac.tukorea.ge.DontStop.framework.objects.Button;
import kr.ac.tukorea.ge.DontStop.framework.scene.BaseScene;
import kr.ac.tukorea.ge.DontStop.framework.view.Metrics;

public class MainScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Player player;

    public enum Layer {
        bg, platform, coin, player, ui, touch, controller, item, COUNT
    }
    public MainScene(Context context) {
        Metrics.setGameSize(16.0f, 9.0f);
        initLayers(Layer.COUNT);

        add(Layer.bg, new HorzScrollBackground(R.mipmap.dontstop_bg_1, -0.2f));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.dontstop_bg_2, -0.4f));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.dontstop_bg_3, -0.6f));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.dontstop_bg_4, -0.8f));

        player = new Player();
        add(Layer.player, player);

        Button jumpBnt = new Button(R.mipmap.btn_jump_no, 14.5f, 8.0f, 2.0f, 1.5f, false, new Button.Callback() {
            @Override
            public boolean onTouch() {
                player.jump();
                return true;
            }
        });

        add(Layer.touch, jumpBnt);
        add(Layer.touch, new Button(R.mipmap.btn_attack_no, 12.5f, 8.0f, 2.0f, 1.5f, true, new Button.Callback() {
            @Override
            public boolean onTouch() {
                player.attack();
                return true;
            }
        }));


        // 캐릭터 변경
        add(Layer.touch, new Button(R.mipmap.attack_button01, 1.1f, 8.0f, 1.9f, 1.9f, false, new Button.Callback() {
            @Override
            public boolean onTouch() {
                player.changeCharacter(Player.Type.CAPTINE);
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.attack_button02, 3.0f, 8.0f, 1.9f, 1.9f, false, new Button.Callback() {
            @Override
            public boolean onTouch() {
                player.changeCharacter(Player.Type.PEPPERMINT);
                return true;
            }
        }));


        add(Layer.controller, new MapLoader(context));
        add(Layer.controller, new CollisionChecker(player));
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            player.jump();
//        }
//        return super.onTouchEvent(event);
//    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }
}
