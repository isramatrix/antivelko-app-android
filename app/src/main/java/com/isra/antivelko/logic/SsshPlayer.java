package com.isra.antivelko.logic;

import android.content.Context;
import android.media.MediaPlayer;

import com.isra.antivelko.R;

import java.util.Timer;
import java.util.TimerTask;

public class SsshPlayer {

    private final Context context;

    private MediaPlayer player;

    private double attempts = 0;

    private boolean isPlaying;

    public SsshPlayer(Context context)
    {
        this.context = context;
        this.player = MediaPlayer.create(context, R.raw.shh);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (++attempts == 3) makeStop();
            }
        }, 0, 1000);

    }

    public void hit()
    {
        attempts = 0;
        player.start();
    }

    private void makeStop()
    {
        player.stop();
        this.player = MediaPlayer.create(context, R.raw.shh);
    }
}
