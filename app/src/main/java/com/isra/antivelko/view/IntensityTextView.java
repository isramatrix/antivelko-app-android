package com.isra.antivelko.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.Locale;

public class IntensityTextView extends AppCompatTextView {

    public IntensityTextView(Context context) {
        super(context);
    }

    public IntensityTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IntensityTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setIntensityText(final double intensity)
    {
        post(new Runnable() {
            @Override
            public void run() {
                setText(String.format(Locale.getDefault(), "%.2f dB", intensity));
            }
        });
    }
}
