package com.shata.calculathandangle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class ThirtyActivity extends AppCompatActivity {

    ImageButton ChooseImage;
    TextView Angle1, Angle2, Angle3, Angle4;
    Button SaveImage;
    DrawableImageView choosenImageView;

    String directory_path = Environment.getExternalStorageDirectory().getPath() + "/";
    SQLiteToExcel sqliteToExcel;
    static String ImageName = "";
    private Hand_DataBase handDataBase;

    static CalculateAngle angle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirty);

        handDataBase = new Hand_DataBase(ThirtyActivity.this);
        //
        File root = new File(directory_path);
        if (!root.exists()) {
            root.mkdirs();
        }

        ChooseImage = findViewById(R.id.ChooseImage);
        Angle1 = findViewById(R.id.tvAngel1);
        Angle2 = findViewById(R.id.tvAngel2);
        Angle3 = findViewById(R.id.tvAngel3);
        Angle4 = findViewById(R.id.tvAngel4);
        SaveImage = findViewById(R.id.btnSave);

        choosenImageView = new DrawableImageView(this);
        ResultFromImage(angle);

        SaveImage.setOnClickListener(v -> {

            Model_Hand hand = new Model_Hand();

            //CalculateAngle angle = new CalculateAngle(choosenImageView.getListPoint());
            ArrayList<Double> listCos = angle.getResultAngleCos();
            hand.setImageName(ImageName.trim());
            hand.setAngle1(listCos.get(0).toString().trim());
            hand.setAngle2(listCos.get(1).toString().trim());
            hand.setAngle3(listCos.get(2).toString().trim());
            hand.setAngle4(listCos.get(3).toString().trim());

           /* //CalculateAngle angle = new CalculateAngle(choosenImageView.getListPoint());
            ArrayList<Double> listTan = angle.getResultAngleTan();
            hand.setImageName(ImageName.trim());
            hand.setAngle1(listTan.get(0).toString().trim());
            hand.setAngle2(listTan.get(1).toString().trim());
            hand.setAngle3(listTan.get(2).toString().trim());
            hand.setAngle4(listTan.get(3).toString().trim());*/

            boolean Check = handDataBase.cheeckHand(hand.getImageName());

            if (Check) {
                Toasty.warning(ThirtyActivity.this, "This NameImage Is Found", Toast.LENGTH_SHORT).show();
            } else {
                boolean Result = handDataBase.insert(hand);
                if (Result) {
                    Toasty.success(ThirtyActivity.this, " Add Successful ", Toast.LENGTH_SHORT).show();
                    ExelSheet();
                } else
                    Toasty.warning(ThirtyActivity.this, " Add failed ", Toast.LENGTH_SHORT).show();
            }
        });

        ChooseImage.setOnClickListener(v -> {
            startActivity(new Intent(ThirtyActivity.this, First_Activity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        });

    }

    private void ResultFromImage(CalculateAngle angle) {
        //CalculateAngle angle = new CalculateAngle(choosenImageView.getListPoint());

        ArrayList<Double> listCos = angle.getResultAngleCos();
//        ArrayList<Double> listTan = angle.getResultAngleTan();
        Angle1.setText(" " + listCos.get(0).floatValue());
        Angle2.setText(" " + listCos.get(1).floatValue());
        Angle3.setText(" " + listCos.get(2).floatValue());
        Angle4.setText(" " + listCos.get(3).floatValue());
    }

    private void ExelSheet() {
        // Export SQLite DB as EXCEL FILE
        sqliteToExcel = new SQLiteToExcel(getApplicationContext(), handDataBase.DB_Name, directory_path);
        sqliteToExcel.exportAllTables("Hand_Angle.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted(String filePath) {
                Toasty.success(ThirtyActivity.this, "Successfully Exported \n Hand_Angle.xls", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Toasty.error(ThirtyActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Error", "onError: " + e.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        return;
    }
}