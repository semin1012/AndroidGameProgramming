package kr.ac.tukorea.ge.DontStop.DontStop.game;

import android.graphics.Canvas;

import java.util.ArrayList;

import kr.ac.tukorea.ge.DontStop.DontStop.R;
import kr.ac.tukorea.ge.DontStop.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.DontStop.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.DontStop.framework.res.Sound;
import kr.ac.tukorea.ge.DontStop.framework.scene.BaseScene;
import kr.ac.tukorea.ge.DontStop.framework.util.CollisionHelper;

public class CollisionChecker implements IGameObject {
    private Player player;
    private Ball attackBall;

    public CollisionChecker(Player player) {
        this.player = player;
    }
    public CollisionChecker(Ball ball) { this.attackBall = ball; }

    @Override
    public void update() {
        // 플레이어와 코인
        MainScene scene = (MainScene) BaseScene.getTopScene();
        ArrayList<IGameObject> Coinitems = scene.getObjectsAt(MainScene.Layer.coin);
        for (int i = Coinitems.size() - 1; i >= 0; i--) {
            IGameObject gobj = Coinitems.get(i);
            if (!(gobj instanceof IBoxCollidable)) {
                continue;
            }
            if (CollisionHelper.collides(player, (IBoxCollidable) gobj)) {
                if (gobj.getClass() == Coin.class) {
                    scene.remove(MainScene.Layer.coin, gobj);
                    int score = scene.score.getScore();
                    scene.score.add(10);
                    Sound.playEffect(R.raw.coin);
                }
            }
        }

        ArrayList<IGameObject> obstacles = scene.getObjectsAt(MainScene.Layer.obstacle);
        for (int i = obstacles.size() - 1; i >= 0; i--) {
            IGameObject gobj = obstacles.get(i);
            if (!(gobj instanceof IBoxCollidable)) {
                continue;
            }
            if (CollisionHelper.collides(player, (IBoxCollidable) gobj)) {
                if (gobj.getClass() == Obstacle.class) {
                    scene.remove(MainScene.Layer.obstacle, gobj);
                    player.death();
                    Sound.playEffect(R.raw.hit);
                }
            }
        }


        ArrayList<IGameObject> balls = scene.getObjectsAt(MainScene.Layer.attackBall);
        for (int i = obstacles.size() - 1; i >= 0; i--) {
            IGameObject gobj = obstacles.get(i);
            if (!(gobj instanceof IBoxCollidable)) {
                continue;
            }
            for (int j = balls.size() - 1; j >= 0; j--) {
                IGameObject bobj = balls.get(j);
                if (!(bobj instanceof IBoxCollidable)) {
                    continue;
                }
                if (CollisionHelper.collides((IBoxCollidable) bobj, (IBoxCollidable) gobj)) {
                    if (gobj.getClass() == Obstacle.class && bobj.getClass() == Ball.class) {
                        if  ( ((Ball) bobj).type == Ball.Type.SWORD ) {
                            if ( ((Obstacle) gobj).type == Obstacle.Type.T_THUNDER ) {
                                ((Ball) bobj).dstRect.offset(-100, 0);
                                scene.remove(MainScene.Layer.obstacle, gobj);
                                scene.remove(MainScene.Layer.attackBall, bobj);
                                int score = scene.score.getScore();
                                scene.score.add(50);
                                Sound.playEffect(R.raw.hit);
                            }
                        }
                        if  ( ((Ball) bobj).type == Ball.Type.ARCHER ) {
                            if ( ((Obstacle) gobj).type == Obstacle.Type.T_STEM ) {
                                ((Ball) bobj).dstRect.offset(-100, 0);
                                scene.remove(MainScene.Layer.obstacle, gobj);
                                scene.remove(MainScene.Layer.attackBall, bobj);
                                int score = scene.score.getScore();
                                scene.score.add(50);
                                Sound.playEffect(R.raw.hit);
                            }
                        }
                        if  ( ((Ball) bobj).type == Ball.Type.WIZARD ) {
                            if ( ((Obstacle) gobj).type == Obstacle.Type.T_TREE ) {
                                ((Ball) bobj).dstRect.offset(-100, 0);
                                scene.remove(MainScene.Layer.obstacle, gobj);
                                scene.remove(MainScene.Layer.attackBall, bobj);
                                int score = scene.score.getScore();
                                scene.score.add(50);
                                Sound.playEffect(R.raw.hit);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {}
}
