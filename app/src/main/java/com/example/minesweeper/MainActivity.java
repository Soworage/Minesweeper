package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
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
    private int imageViewX;
    private int imageViewY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bitmap bitmap = Bitmap.createBitmap(16 * 80, 16 * 80, Bitmap.Config.ARGB_8888);
        matchfield = new Matchfield(bitmap, paint);
        matchfield.drawField();

        imageView = (ImageView) findViewById(R.id.imageView);  // Referenzieren des ImageView aus dem XML
        imageView.setImageBitmap(bitmap);
        imageView.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int touchX = (int) event.getX();
            int touchY = (int) event.getY();
            matchfield.Click(touchX, touchY);
            this.imageView.postInvalidate();

            Log.d("Touch", "Touched at Matchfield: (" + touchX+ "," + touchY + ")");
        }
        return true;
    }
}
