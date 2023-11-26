package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.example.minesweeper.logic.MinesweeperGame;
import com.example.minesweeper.ui.Matchfield;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private Paint paint = new Paint();
    private Matchfield matchfield;
    private ImageView imageView;
    private Handler handler = new Handler();
    private boolean longPressDetected = false;
    private int lastTouchX;
    private int lastTouchY;

    private Runnable longPressRunnable = new Runnable() {
        @Override
        public void run() {
            longPressDetected = true;
            handleLongPress(lastTouchX, lastTouchY);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bitmap bitmap = Bitmap.createBitmap(16 * 80, 16 * 80, Bitmap.Config.ARGB_8888);
        matchfield = new Matchfield(bitmap, paint);
        matchfield.drawField();

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
        imageView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // Wenn das Spiel beendet ist, ignoriere weitere Touch-Events
        if (matchfield.isGameEnd()) {
            return false;
        }

        lastTouchX = (int) event.getX();
        lastTouchY = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                longPressDetected = false;
                handler.postDelayed(longPressRunnable, 1000); // 1000 Millisekunden = 1 Sekunde
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (!longPressDetected) {
                    handler.removeCallbacks(longPressRunnable);
                    handleShortPress(lastTouchX, lastTouchY);
                }
                break;
        }
        return true;
    }


    private void handleShortPress(int x, int y) {
        matchfield.Click(x, y);
        imageView.postInvalidate();
        Log.d("Touch", "Kurzes Drücken bei: (" + x + "," + y + ")");
    }

    private void handleLongPress(int x, int y) {
        matchfield.longClick(x, y);
        imageView.postInvalidate();
        Log.d("Touch", "Langes Drücken erkannt bei: (" + x + "," + y + ")");
    }
}
