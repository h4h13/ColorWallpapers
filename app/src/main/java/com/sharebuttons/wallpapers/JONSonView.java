package com.sharebuttons.wallpapers;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Monkey D Luffy on 7/28/2015.
 */
public class JONSonView extends TextView {
    public JONSonView(Context context) {
        super(context);
        init();
    }

    public JONSonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public JONSonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "fonst/WorkSans-Thin.ttf");
        setTypeface(typeface);
    }
}
