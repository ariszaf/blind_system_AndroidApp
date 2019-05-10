package com.example.psilos.automationsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
//http://stackoverflow.com/questions/7860603/how-to-edit-multiline-strings-in-android-strings-xml-file
/**
 * The type About activity.
 */
public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_about);

        ImageView mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView.setImageResource(R.drawable.logo);
    }
}