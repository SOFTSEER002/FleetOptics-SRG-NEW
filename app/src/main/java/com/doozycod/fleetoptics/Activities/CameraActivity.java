package com.doozycod.fleetoptics.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.doozycod.fleetoptics.R;
import com.google.android.cameraview.CameraView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;

public class CameraActivity extends AppCompatActivity {
    private CameraView mCameraView;
    String deviceName, deviceMan;
    private File imageFile;
    String IMAGE_NAME = "temp.png";
    ImageView takePicture, switch_camera;
    int currentCameraId;
    Handler handler;
    Runnable runnable;

    private void initUI() {
        mCameraView = findViewById(R.id.camera);
        takePicture = findViewById(R.id.take_picture);


        deviceName = android.os.Build.MODEL;
        deviceMan = android.os.Build.MANUFACTURER;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);
        getSupportActionBar().hide();
        initUI();
        ClickListeners();
        Toast.makeText(this, "Please smile we will take a picture of yours in 5 seconds!", Toast.LENGTH_SHORT).show();
        if (mCameraView != null) {
            mCameraView.addCallback(mCallback);

        }

    }


    private void ClickListeners() {
        handler = new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                if (mCameraView != null) {
                    mCameraView.takePicture();
                }
            }
        }, 5000);

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacks(runnable);
                if (mCameraView != null) {
                    mCameraView.takePicture();
                }
            }
        });


    }


    @Override
    protected void onPause() {
        Log.d("onPuase", "onPause called");
        mCameraView.stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            mCameraView.start();
            if (mCameraView != null) {
                mCameraView.setFacing(CameraView.FACING_FRONT);
            }
        }
    }

    private CameraView.Callback mCallback = new CameraView.Callback() {

        @Override
        public void onCameraOpened(CameraView cameraView) {
            Log.d("MainActivity", "onCameraOpened");
        }

        @Override
        public void onCameraClosed(CameraView cameraView) {
            Log.d("MainActivity", "onCameraClosed");
        }

        @Override
        public void onPictureTaken(CameraView cameraView, final byte[] data) {
            Log.d("MainActivity", "onPictureTaken " + data.length);
           /* Toast.makeText(cameraView.getContext(), R.string.picture_taken, Toast.LENGTH_SHORT)
                    .show();*/
            Bitmap bp = BitmapFactory.decodeByteArray(data, 0, data.length);
            try {
                int facing = mCameraView.getFacing();
                // convert byte array into bitmap
                Bitmap loadedImage = null;
                Bitmap rotatedBitmap = null;
                loadedImage = BitmapFactory.decodeByteArray(data, 0, data.length);

                // rotate Image
                Matrix rotateMatrix = new Matrix();

                rotateMatrix.postRotate(getWindowManager().getDefaultDisplay().getRotation());

                rotatedBitmap = Bitmap.createBitmap(loadedImage, 0, 0,
                        loadedImage.getWidth(), loadedImage.getHeight(),
                        rotateMatrix, false);

                Bitmap finalImage;
                if (deviceMan.equals("vivo")) {
                    finalImage = Bitmap.createScaledBitmap(rotateImage(rotatedBitmap, -1), 600, 800, false);

                } else if (deviceMan.equals("OPPO")) {
                    finalImage = Bitmap.createScaledBitmap(rotateImage(rotatedBitmap, -1), 800, 600, false);

                } else {
                    if (facing == CameraView.FACING_BACK) {
                        finalImage = Bitmap.createScaledBitmap(rotateImage(rotatedBitmap, 90), 600, 800, false);
                    } else {
                        finalImage = Bitmap.createScaledBitmap(rotateImage(rotatedBitmap, 1), 800, 600, false);
                    }
                }


                String state = Environment.getExternalStorageState();
                File directory = null;
                if (state.contains(Environment.MEDIA_MOUNTED)) {
                    directory = new File(Environment.getExternalStorageDirectory() + "/FleetOptics/");
                } else {
                    directory = new File(Environment.getExternalStorageDirectory() + "/FleetOptics/");
                }

                boolean success = true;
                if (!directory.exists()) {
                    success = directory.mkdirs();
                }
                if (success) {

                    imageFile = new File(Environment.getExternalStorageDirectory() + "/FleetOptics/" + "visitor.png");
                    //deleting previous image
                    imageFile.createNewFile();
                } else {
                    Toast.makeText(getBaseContext(), "Image Not saved", Toast.LENGTH_SHORT).show();
                    return;
                }

                ByteArrayOutputStream ostream = new ByteArrayOutputStream();

                // save image into gallery
                finalImage = resize(finalImage, 800, 600);
                finalImage.compress(Bitmap.CompressFormat.JPEG, 100, ostream);

                FileOutputStream fout = new FileOutputStream(imageFile);
                fout.write(ostream.toByteArray());
                fout.close();
                ContentValues values = new ContentValues();

                values.put(MediaStore.Images.Media.DATE_TAKEN,
                        System.currentTimeMillis());
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                values.put(MediaStore.MediaColumns.DATA,
                        imageFile.getAbsolutePath());

                setResult(Activity.RESULT_OK); //add this
                if (!TextUtils.isEmpty(imageFile.getAbsolutePath())) {
                    Log.e("MainActivity", "onPictureTaken: " + "Image saved to gallery!" + imageFile.getAbsolutePath());

//                    uploadPhotos(imageFile.getAbsolutePath());
//                    Intent intent = new Intent(CameraActivity.this, VisitorRegisterActivity.class);
//                    intent.putExtra("picture", "yess");
//                    startActivity(intent);
                    finish();

                } else {
                    Log.e("MainActivity", "onPictureTaken: " + "Unable to save image!");

                }
//                        finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public static byte[] compresssImage(Bitmap b) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] compressedByteArray = stream.toByteArray();
        return compressedByteArray;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CameraActivity.this, CheckInVisitActivity.class));
        super.onBackPressed();
    }


    private Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }
}
