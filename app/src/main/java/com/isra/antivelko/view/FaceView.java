package com.isra.antivelko.view;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.annotation.ColorRes;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.isra.antivelko.R;

import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class FaceView extends AppCompatImageView {

    private int attempts;

    public FaceView(Context context) {
        super(context);
    }

    public FaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

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

        post(new Runnable() {
            @Override
            public void run() {
                setImageResource(R.drawable.sad);
                ((ConstraintLayout) getParent()).setBackgroundResource(R.color.red);
            }
        });
    }

    private void makeStop()
    {
        post(new Runnable() {
            @Override
            public void run() {
                setImageResource(R.drawable.happy);
                ((ConstraintLayout) getParent()).setBackgroundResource(R.color.normal);
            }
        });
    }
}
