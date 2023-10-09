package com.shata.calculathandangle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import es.dmoral.toasty.Toasty;

public class First_Activity extends AppCompatActivity {

    private static final int GALLERY_REQUEST = 1;

    Button mGallary;
    LinearLayout mlayoutSelectImage;
    private Hand_DataBase handDataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        // Permissions to WRITE_EXTERNAL_STORAGE
        ActivityCompat.requestPermissions(First_Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        handDataBase = new Hand_DataBase(First_Activity.this);


        mGallary = findViewById(R.id.btnGallary);
        mlayoutSelectImage = findViewById(R.id.layoutSelectImage);

        mGallary.setOnClickListener(v -> {
            selectImageFromGallery();
        });

        mlayoutSelectImage.setOnClickListener(v -> {
            selectImageFromGallery();
        });
    }

    private void selectImageFromGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_OK) {
                Uri imageFileUri = intent.getData();
                String ImageName = imageFileUri.getPath().substring(imageFileUri.getPath().lastIndexOf('/') + 1);
                boolean Check = handDataBase.cheeckHand(ImageName);
                if (Check) {
                    Toasty.warning(First_Activity.this, "This NameImage Is Found", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent1 = new Intent(First_Activity.this, ScandActivity.class);
                    intent1.putExtra("ImageURI", imageFileUri.toString());
                    startActivity(intent1);
                    this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    //finish();
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case GALLERY_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toasty.normal(First_Activity.this, "Permission denied to Write_External_Storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

}