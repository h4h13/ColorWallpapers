package com.sharebuttons.wallpapers;

import android.app.WallpaperManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;


public class WallpapersActivity extends AppCompatActivity implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener, View.OnTouchListener {

    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;

    private View mCurrentView;
    private int mCurrentColor;
    private TextView mColorTextView;
    private String mColorText;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpapers);

        initVariables();

        getPreviousColor();

        mDetector = new GestureDetectorCompat(this, this);
        mDetector.setOnDoubleTapListener(this);
        mCurrentView.setOnTouchListener(this);
    }


    private void initVariables() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mColorTextView = (TextView) findViewById(R.id.displayColor);
        mCurrentView = findViewById(R.id.content);
    }

    private void getPreviousColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(mSharedPreferences.getInt("previousColor", 0));
            getWindow().setNavigationBarColor(mSharedPreferences.getInt("previousColor", 0));
        }
        mColorTextView.setText(mSharedPreferences.getString("ColorText", "#Color"));
        mCurrentView.setBackgroundColor(mSharedPreferences.getInt("previousColor", 0));
    }

    private Bitmap getBitmapFromView(View current_view) {
        Bitmap bitmap = Bitmap.createBitmap(current_view.getWidth(), current_view.getHeight(), Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(mCurrentColor);
        return bitmap;
    }

    int[][] getColors;

    public void changeColor(View view) {
        Resources resources = getResources();
        getColors = new int[][]{
                resources.getIntArray(R.array.browns),
                resources.getIntArray(R.array.blue_greys),
                resources.getIntArray(R.array.oranges),
                resources.getIntArray(R.array.deep_purples),
                resources.getIntArray(R.array.teals),
                resources.getIntArray(R.array.light_greens),
                resources.getIntArray(R.array.ambers),
                resources.getIntArray(R.array.blues),
                resources.getIntArray(R.array.yellows),
                resources.getIntArray(R.array.reds),
                resources.getIntArray(R.array.cyans),
                resources.getIntArray(R.array.greens),
                resources.getIntArray(R.array.purples),
                resources.getIntArray(R.array.light_blues),
                resources.getIntArray(R.array.pinks),
                resources.getIntArray(R.array.limes),
                resources.getIntArray(R.array.indigos)};

        //Double array one get getColors int and 2nd one is inside int
        mCurrentColor = getColors[new Random().nextInt(getColors.length)] //first part of array
                [new Random().nextInt(10)]; //second part of array

        //convert String to Hex value
        mColorText = "#" + Integer.toHexString(mCurrentColor);

        //Set color value to text
        mColorTextView.setText(mColorText);

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("ColorText", mColorText);
        editor.putInt("previousColor", mCurrentColor);
        editor.apply();

        view.setBackgroundColor(mCurrentColor);

        //if Lollipop chage color of Status bar and NavigationBar color
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(mCurrentColor);
            getWindow().setNavigationBarColor(mCurrentColor);
        }

    }

    // Set Wallpaper
    public void setWallpaperImage(View view) {
        WallpaperManager localWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        try {
            localWallpaperManager.setBitmap(getBitmapFromView(mCurrentView));
            Toast.makeText(getApplicationContext(), getString(R.string.wallpaper_set), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Save Image to Mobile
    public void saveImage(View view) {
        Toast.makeText(getApplicationContext(), getString(R.string.image_saved), Toast.LENGTH_LONG).show();
        MediaStore.Images.Media.insertImage(getContentResolver(),
                getBitmapFromView(mCurrentView),
                getString(R.string.image_title),
                getString(R.string.image_description));
    }

    //Copy color
    public void copyColor(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("simpleColor", mColorText);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getApplication(), "Color Copied", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
        Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        Log.d(DEBUG_TAG, "onScroll: " + e1.toString() + e2.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.mDetector.onTouchEvent(motionEvent);
        return false;
    }
}
