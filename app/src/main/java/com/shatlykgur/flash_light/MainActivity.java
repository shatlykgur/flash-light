package com.shatlykgur.flash_light;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.hardware.Camera;

public class MainActivity extends AppCompatActivity {

    private Button btn_switch;
    private Camera camera;
    private Camera.Parameters parameters;
    private boolean isFlashOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_switch = findViewById(R.id.btnSwitch);

        if (checkReadPermission()) {

         functionMain();   
            
        }else {

            startActivity(new Intent(this, Permission.class));

        }

    }

    private void functionMain() {

        btn_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isFlashOn) {
                    turnOffFlash();
                }else {
                    turnOnFlash();
                }

            }
        });

        getCamera();
        toggleImage();

    }

    private void turnOffFlash() {

        if (isFlashOn) {
            if (camera == null || parameters == null) {
                return;
            }

            parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.stopPreview();
            isFlashOn = false;
            toggleImage();
        }

    }

    private void turnOnFlash() {

        if (!isFlashOn) {
            if (camera == null || parameters == null) {
                return;
            }

            parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            camera.startPreview();
            isFlashOn = true;
            toggleImage();
        }

    }

    private void getCamera() {

        if (camera == null) {

            try {

                camera = Camera.open();
                parameters = camera.getParameters();

            } catch (RuntimeException e) {

            }

        }

    }

    private void toggleImage() {

        if (isFlashOn) {
            btn_switch.setBackgroundResource(R.drawable.onbutton);

        }else {
            btn_switch.setBackgroundResource(R.drawable.offbutton);

        }

    }

    private boolean checkReadPermission() {

        int result = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;

    }
}
