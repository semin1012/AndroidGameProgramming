package kr.ac.tukorea.ge.DontStop.DontStop.game;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import java.util.HashMap;

import kr.ac.tukorea.ge.DontStop.DontStop.R;

public class SoundPlayer {
    public static final int DING_DONG = R.raw.when_the_morning_comes;
    public static final int SUCCESS = R.raw.when_the_morning_comes;

    private static SoundPool soundPool;
    private static HashMap<Integer, Integer> soundPoolMap;

    // sound media initialize
    public static void initSounds(Context context) {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();

        soundPoolMap = new HashMap(2);
        soundPoolMap.put(DING_DONG, soundPool.load(context, DING_DONG, 1));
        soundPoolMap.put(SUCCESS, soundPool.load(context, SUCCESS, 2));
    }

    public static void play(int raw_id){
        if( soundPoolMap.containsKey(raw_id) ) {
            soundPool.play(soundPoolMap.get(raw_id), 1, 1, 1, 0, 1f);
        }
    }

    public static void stop(int raw_id) {
        soundPool.stop(soundPoolMap.get(raw_id));
    }
}