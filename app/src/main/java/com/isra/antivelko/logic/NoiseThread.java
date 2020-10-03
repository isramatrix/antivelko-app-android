package com.isra.antivelko.logic;

import android.media.MediaRecorder;

public class NoiseThread extends Thread {

    private final OnNoiseFetch onNoiseFetch;

    private final MediaRecorder mediaRecorder;

    public NoiseThread(OnNoiseFetch onNoiseFetch, MediaRecorder recorder)
    {
        this.onNoiseFetch = onNoiseFetch;
        this.mediaRecorder = recorder;
    }

    @Override
    public void run()
    {
        while (true)
        {
            int amplitude = mediaRecorder.getMaxAmplitude();
            if (amplitude > 10) onNoiseFetch.fetch(20 * Math.log10(amplitude));
        }
    }
}
