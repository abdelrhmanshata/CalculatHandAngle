package com.shata.calculathandangle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;


public class DrawableImageView extends androidx.appcompat.widget.AppCompatImageView implements View.OnTouchListener {

    float downx = 0;
    float downy = 0;
    float upx = 0;
    float upy = 0;

    ArrayList<HashMap> listPoint;

    Canvas canvas;
    Paint paint;
    Matrix matrix;

    Bitmap alteredBitmap;
    Bitmap bmp;

    public DrawableImageView(Context context) {
        super(context);
        setOnTouchListener(this);
        listPoint = new ArrayList<>();
    }

    public DrawableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        listPoint = new ArrayList<>();
    }

    public DrawableImageView(Context context, AttributeSet attrs,
                             int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
        listPoint = new ArrayList<>();
    }

    public void setNewImage(Bitmap alteredBitmap, Bitmap bmp) {
        canvas = new Canvas(alteredBitmap);
        paint = new Paint();
        listPoint = new ArrayList<>();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        matrix = new Matrix();
        canvas.drawBitmap(bmp, matrix, paint);
        setImageBitmap(alteredBitmap);
        this.alteredBitmap = alteredBitmap;
        this.bmp = bmp;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                clearPoint();
                downx = getPointerCoords(event)[0];//event.getX();
                downy = getPointerCoords(event)[1];//event.getY();
                canvas.drawCircle(downx, downy, 30, paint);

                ScandActivity.textViewX.setText(" X : ");
                ScandActivity.pointClickX.setText(""+downx);
                ScandActivity.textViewY.setText(" Y : ");
                ScandActivity.pointClickY.setText(""+downy);

                invalidate();
                drawPoint();
               break;
           /* case MotionEvent.ACTION_MOVE:
                downx = getPointerCoords(event)[0];//event.getX();
                downy = getPointerCoords(event)[1];//event.getY();
                ScandActivity.pointClick.setText("X : "+downx+"\n"+"Y : "+downy);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                ScandActivity.pointClick.setText("");
                invalidate();
                break;*/
            default:
                break;
        }
        return true;
    }

    final float[] getPointerCoords(MotionEvent e) {
        final int index = e.getActionIndex();
        final float[] coords = new float[]{e.getX(index), e.getY(index)};
        Matrix matrix = new Matrix();
        getImageMatrix().invert(matrix);
        matrix.postTranslate(getScrollX(), getScrollY());
        matrix.mapPoints(coords);
        return coords;
    }

    public void clearPoints() {
        canvas = new Canvas(this.alteredBitmap);
        paint = new Paint();
        listPoint = new ArrayList<>();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        matrix = new Matrix();
        canvas.drawBitmap(this.bmp, matrix, paint);
    }

    public void undoPoints() {
        listPoint.remove(listPoint.size()-1);
        undoAllPoints();
        drawPoint();
        drawLine();
    }

    public void undoAllPoints() {
        canvas = new Canvas(this.alteredBitmap);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        matrix = new Matrix();
        canvas.drawBitmap(this.bmp, matrix, paint);
    }

    public void clearPoint() {
        canvas = new Canvas(this.alteredBitmap);
        canvas.drawBitmap(this.bmp, matrix, paint);
    }

    public void savePoint() {
        HashMap map = new HashMap();
        map.put("X", downx);
        map.put("Y", downy);
        listPoint.add(map);
        drawLine();
    }

    public void drawPoint() {
        paint.setColor(Color.GREEN);
        for (int i = 0; i < listPoint.size(); i++) {
            canvas.drawCircle((Float) listPoint.get(i).get("X"), (Float) listPoint.get(i).get("Y"), 30, paint);
            invalidate();
        }
        paint.setColor(Color.RED);
        drawLine();
    }

    public void drawLine() {
        paint.setStrokeWidth(10);
        for (int i = 1; i < listPoint.size(); i++) {
            canvas.drawLine((Float) listPoint.get(0).get("X"),(Float) listPoint.get(0).get("Y"),(Float) listPoint.get(i).get("X"), (Float) listPoint.get(i).get("Y"), paint);
            invalidate();
        }
        paint.setStrokeWidth(5);
    }
    public ArrayList<HashMap> getListPoint() {
        return listPoint;
    }
}