package kr.ac.tukorea.ge.DontStop.DontStop.game;

import android.graphics.Canvas;

import java.util.ArrayList;

import kr.ac.tukorea.ge.DontStop.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.DontStop.framework.interfaces.IGameObject;
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
                }
            }
        }

        ArrayList<IGameObject> obstacles = scene.getObjectsAt(MainScene.Layer.obstacle);
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
                                ((Ball) bobj).dstRect.top += 500.f;
                                ((Ball) bobj).dstRect.bottom += 500.f;
                                scene.remove(MainScene.Layer.obstacle, gobj);
                                scene.remove(MainScene.Layer.attackBall, bobj);
                                int score = scene.score.getScore();
                                scene.score.add(50);
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
