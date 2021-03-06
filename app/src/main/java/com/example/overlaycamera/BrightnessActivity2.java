package com.example.overlaycamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class BrightnessActivity2 extends AppCompatActivity {

    Button btnNext,btnBack;
    ImageView im_brightness;
    public static Bitmap bm;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brightness2);

        SeekBar sb_value = (SeekBar) findViewById(R.id.sb_brightness_bar2);
        im_brightness = (ImageView) findViewById(R.id.iv_set_image_brightness_2);
        im_brightness.setImageBitmap(CameraSide2.takenPicture);
        ImageView im_bag = findViewById(R.id.iv_set_bag_image_brightness_2);
        btnNext = findViewById(R.id.btn_next_brightness_2);
        btnBack = findViewById(R.id.btn_back_brightness_2);
        databaseHelper = new DatabaseHelper(this);

        im_bag.setLayoutParams(ImagePreview2.layoutParams);
        sb_value.setProgress(100);
        sb_value.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                im_brightness.setColorFilter(setBrightness(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View z = im_brightness;
                z.setDrawingCacheEnabled(true);
                z.buildDrawingCache();
                bm = Bitmap.createBitmap(z.getDrawingCache());
                boolean ok = databaseHelper.addData(getSharedPreferences("height_pref",0).getInt("current_section",1),2,BitMapToString(bm),ImagePreview2.layoutParams.height,
                        ImagePreview2.layoutParams.width,
                        ImagePreview2.layoutParams.leftMargin,
                        ImagePreview2.layoutParams.topMargin);
                if (ok){
                    startActivity(new Intent(BrightnessActivity2.this,CameraSide3.class));
                    Toast.makeText(BrightnessActivity2.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(BrightnessActivity2.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static PorterDuffColorFilter setBrightness(int progress) {
        if (progress >=    100)
        {
            int value = (int) (progress-100) * 200 / 100;

            return new PorterDuffColorFilter(Color.argb(value, 200, 200, 200), PorterDuff.Mode.SRC_OVER);

        }
        else
        {
            int value = (int) (100-progress) * 200 / 100;
            return new PorterDuffColorFilter(Color.argb(value, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);


        }
    }
}