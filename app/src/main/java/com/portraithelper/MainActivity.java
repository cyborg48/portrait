package com.portraithelper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_PIC_REQUEST = 1337;
    private Bitmap portrait;
    private FirebaseVisionImage fbImage;
    private List<FirebaseVisionImage> faces;
    private Context context;
    FaceContours face;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        //Button b = findViewById(R.id.opencam);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ImageView imageview = findViewById(R.id.img); //sets imageview as the bitmap
            imageview.setImageBitmap(image);
            portrait = image;

            face = new FaceContours(portrait, this);

            /***

            FaceDetector detector = new FaceDetector.Builder(context)
                    .setTrackingEnabled(false)
                    .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                    .build();

            FirebaseVisionFaceDetectorOptions highAccuracyOpts =
                    new FirebaseVisionFaceDetectorOptions.Builder()
                            .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                            .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                            .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                            .build();

            // Real-time contour detection of multiple faces
            FirebaseVisionFaceDetectorOptions realTimeOpts =
                    new FirebaseVisionFaceDetectorOptions.Builder()
                            .setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS)
                            .build();

            Frame frame = new Frame.Builder().setBitmap(portrait).build();
            SparseArray<Face> faces = detector.detect(frame);

            FaceView overlay = findViewById(R.id.faceView);
            overlay.setContent(portrait, faces);
             ***/


        }

    }


    public void OpenCam (View view){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
    }
}
