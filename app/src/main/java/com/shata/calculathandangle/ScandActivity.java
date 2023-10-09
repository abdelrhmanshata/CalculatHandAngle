package com.shata.calculathandangle;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class ScandActivity extends Activity {

    private static final int MAX_BITMAP_SIZE = 100 * 1024 * 1024; // 100 MB

    DrawableImageView choosenImageView;
    Button AddPoint, undoPoint;
    static TextView pointClickX;
    static TextView pointClickY;
    static TextView textViewX;
    static TextView textViewY;
    TextView numOfPoint;
    Bitmap bmp;
    Bitmap alteredBitmap;
    String ImageName = "";
    Uri imageFileUri;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scand);

        AddPoint = findViewById(R.id.btnAdd);
        undoPoint = findViewById(R.id.btnUndo);
        numOfPoint = findViewById(R.id.numberOfPoint);
        pointClickX = findViewById(R.id.pointClickX);
        pointClickY = findViewById(R.id.pointClickY);
        textViewX = findViewById(R.id.tvX);
        textViewY = findViewById(R.id.tvY);

        choosenImageView = (DrawableImageView) this.findViewById(R.id.ChoosenImageView);
        numOfPoint.setText("");

        imageFileUri = Uri.parse(getIntent().getStringExtra("ImageURI"));
        getImageGallary(imageFileUri);
        ImageName = imageFileUri.getPath().substring(imageFileUri.getPath().lastIndexOf('/') + 1);

        AddPoint.setOnClickListener(v -> {
            if (choosenImageView.listPoint.size() <= 5) {
                choosenImageView.savePoint();
                numOfPoint.setText("" + choosenImageView.listPoint.size());
                if (choosenImageView.listPoint.size() == 6) {
                    AddPoint.setText("Result");
                    AddPoint.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_circle_outline_24, 0, 0, 0);
                } else {
                    AddPoint.setText("Add Point");
                    AddPoint.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_add_circle_outline_24, 0, 0, 0);
                }
            } else {
                ResultFromImage();
            }
        });

        undoPoint.setOnClickListener(v -> {
            if (choosenImageView.listPoint.size() != 0) {
                choosenImageView.undoPoints();
                numOfPoint.setText("" + choosenImageView.listPoint.size());
                if (choosenImageView.listPoint.size() == 6) {
                    AddPoint.setText("Result");
                    AddPoint.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_circle_outline_24, 0, 0, 0);
                } else {
                    AddPoint.setText("Add Point");
                    AddPoint.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_add_circle_outline_24, 0, 0, 0);
                }
            }
        });
    }

    private void ResultFromImage() {
        CalculateAngle calculateAngle = new CalculateAngle(choosenImageView.getListPoint());
        ThirtyActivity.angle = calculateAngle;
        ThirtyActivity.ImageName = ImageName;
        startActivity(new Intent(this, ThirtyActivity.class));
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        //finish();
    }

    public void getImageGallary(Uri imageFileUri) {
        try {
            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = true;
            bmp = BitmapFactory
                    .decodeStream(
                            getContentResolver().openInputStream(
                                    imageFileUri), null, bmpFactoryOptions);

            bmpFactoryOptions.inJustDecodeBounds = false;
            bmp = BitmapFactory
                    .decodeStream(
                            getContentResolver().openInputStream(
                                    imageFileUri), null, bmpFactoryOptions);

            int bitmapSize = bmp.getByteCount();
            if (bitmapSize > MAX_BITMAP_SIZE) {
                throw new RuntimeException("Canvas: trying to draw too large(" + bitmapSize + "bytes) bitmap.");
            }

            alteredBitmap = Bitmap.createBitmap(bmp.getWidth(),
                    bmp.getHeight(), bmp.getConfig());
            choosenImageView.setNewImage(alteredBitmap, bmp);

        } catch (Exception e) {
            Log.v("ERROR", e.toString());
            Toasty.error(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ScandActivity.this, First_Activity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        return;
    }

}