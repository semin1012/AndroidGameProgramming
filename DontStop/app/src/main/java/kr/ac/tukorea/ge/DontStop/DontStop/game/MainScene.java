package kr.ac.tukorea.ge.DontStop.DontStop.game;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.ge.DontStop.DontStop.R;
import kr.ac.tukorea.ge.DontStop.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.DontStop.framework.objects.Button;
import kr.ac.tukorea.ge.DontStop.framework.objects.Score;
import kr.ac.tukorea.ge.DontStop.framework.objects.Sprite;
import kr.ac.tukorea.ge.DontStop.framework.res.Sound;
import kr.ac.tukorea.ge.DontStop.framework.scene.BaseScene;
import kr.ac.tukorea.ge.DontStop.framework.view.GameView;
import kr.ac.tukorea.ge.DontStop.framework.view.Metrics;

public class MainScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Player player;
    private final Button attackBnt;
    public Score score;
    int playerCharacterNum = 0;

    Handler handler = new Handler();

    Ball ball;
    public enum Layer {
        bg, platform, coin, obstacle, player, ui,  controller, item, attackBall, score, touch, COUNT
    }
    public MainScene(Context context) {
        Metrics.setGameSize(16.0f, 9.0f);
        initLayers(Layer.COUNT);

        add(Layer.bg, new HorzScrollBackground(R.mipmap.dontstop_bg_1, -0.2f));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.dontstop_bg_2, -0.4f));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.dontstop_bg_3, -0.6f));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.dontstop_bg_4, -0.8f));



        // 0 - 전사, 1 - 마법사, 2 - 궁수
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

        attackBnt =  new Button(R.mipmap.btn_attack_no, 12.5f, 8.0f, 2.0f, 1.5f, true, new Button.Callback() {
            @Override
            public boolean onTouch() {
                player.attack();
                handler.removeCallbacksAndMessages(null);

                MainScene scene = (MainScene) BaseScene.getTopScene();

                Ball.Type type = Ball.Type.SWORD;
                // 플레이어가 현재 sword 라면
                if ( playerCharacterNum == 0 ) {
                    type = Ball.Type.SWORD;
                    Log.d(TAG, "Num = 0 ");
                }
                else if ( playerCharacterNum == 2 ) {
                    Log.d(TAG, "Num = 2 ");
                    type = Ball.Type.ARCHER;
                }
                else if ( playerCharacterNum == 1 ) {
                    type = Ball.Type.WIZARD;
                    Log.d(TAG, "Num = 1 ");
                }

                ball = Ball.get(2, player.y, type);
                scene.add(MainScene.Layer.attackBall, ball);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ball = null;
                    }
                }, 700); //딜레이 타임 조절

                return true;
            }
        });

        add(Layer.touch, attackBnt);


        // 캐릭터 변경
        add(Layer.touch, new Button(R.mipmap.attack_button_maple02, 1.1f, 8.1f, 1.7f, 1.7f, false, new Button.Callback() {
            @Override
            public boolean onTouch() {
                player.changeCharacter(Player.Type.SWORD);
                attackBnt.setButtonInit();
                playerCharacterNum = 0;
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.attack_button_maple01, 2.8f, 8.1f, 1.7f, 1.7f, false, new Button.Callback() {
            @Override
            public boolean onTouch() {
                player.changeCharacter(Player.Type.WIZARD);
                attackBnt.setButtonInit();
                playerCharacterNum = 1;
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.attack_button_maple03, 4.5f, 8.1f, 1.7f, 1.7f, false, new Button.Callback() {
            @Override
            public boolean onTouch() {
                player.changeCharacter(Player.Type.ARCHER);
                attackBnt.setButtonInit();
                playerCharacterNum = 2;
                return true;
            }
        }));

        add(Layer.controller, new MapLoader(context));
        add(Layer.controller, new CollisionChecker(player));

        score = new Score(R.mipmap.gold_number, 15.5f, 1.0f, 0.8f);
        add(Layer.ui, score);
    }
    @Override
    protected void onStart() {
       Sound.playMusic(R.raw.dragon_dream);

    }

    public void getScoreActivity() {
        Sound.stopMusic();
        Sound.playMusic(R.raw.aquarium);
        MainScene scene = (MainScene) BaseScene.getTopScene();
        ArrayList<IGameObject> touchs = scene.getObjectsAt(MainScene.Layer.touch);
        for (int i = touchs.size() - 1; i >= 0; i--) {
            IGameObject gobj = touchs.get(i);
            remove(Layer.touch, gobj);
        }
        add(Layer.score, new HorzScrollBackground(R.mipmap.ovenbreak0006_tm003_bg1, -0.2f));
        add(Layer.score, new HorzScrollBackground(R.mipmap.ovenbreak0006_tm003_bg2, -0.4f));
        add(Layer.score, new HorzScrollBackground(R.mipmap.ovenbreak0006_tm003_bg3, -0.6f));
        add(Layer.score, new HorzScrollBackground(R.mipmap.score_png, -0.0f));
        score.right = 12.f;
        score.top = 3.5f;
        score.dstCharHeight = 2.f;
        score.dstCharWidth = 2.f;
        add(Layer.score, score);
       // add(Layer.score, new Sprite(R.mipmap.score_png, 8.0f, 4.5f, 16, 9));
        add(Layer.touch, new Button(R.mipmap.again_btn, 14.5f, 8.0f, 3.f, 2.f, false, new Button.Callback() {
            @Override
            public boolean onTouch() {
                MainScene scene = (MainScene) BaseScene.getTopScene();
                ArrayList<IGameObject> obstacles = scene.getObjectsAt(MainScene.Layer.obstacle);
                // 다른 오브젝트들이 움직이지 않도록 스피드 조정
                for (int i = obstacles.size() - 1; i >= 0; i--) {
                    IGameObject gobj = obstacles.get(i);
                    ((MapObject) gobj).SPEED = 3.5f;
                }
                GameView.view.getActivity().finishAndRemoveTask();
                return true;
            }
        }));
    }

    @Override
    protected void onEnd() {
       Sound.stopMusic();
    }

    @Override
    protected void onPause() {
        Sound.pauseMusic();
    }

    @Override
    protected void onResume() {
        Sound.resumeMusic();
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

    @Override
    public boolean handleBackKey() {
        new PausedScene().pushScene();
        return true;
    }
}
