package com.portraithelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionPoint;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceContour;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;

import java.util.List;

public class FaceContours {

    FirebaseVisionImage fbImage;

    List<FirebaseVisionPoint> all_points, face_oval, left_eyebrow_top, left_eyebrow_bottom, right_eyebrow_top,
    right_eyebrow_bottom, left_eye, right_eye, upper_lip_top, upper_lip_bottom, lower_lip_top,
    lower_lip_bottom, nose_bridge, nose_bottom;

    Rect bounds;
    float rotY;
    float rotZ;

    String TAG = "FACECONTOURS";

    public FaceContours(Bitmap portrait, Context context){

        FirebaseApp.initializeApp(context);

        FirebaseVisionFaceDetectorOptions options =
                new FirebaseVisionFaceDetectorOptions.Builder()
                        .setClassificationMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                        .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS)
                        //.setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                        //.setMinFaceSize(0.15f)
                        //.enableTracking()
                        .build();

        fbImage = FirebaseVisionImage.fromBitmap(portrait);

        FirebaseVisionFaceDetector detector = FirebaseVision.getInstance()
                .getVisionFaceDetector(options);

        Task<List<FirebaseVisionFace>> result =
                detector.detectInImage(fbImage)
                        .addOnSuccessListener(
                                new OnSuccessListener<List<FirebaseVisionFace>>() {
                                    @Override
                                    public void onSuccess(List<FirebaseVisionFace> faces) {
                                        // Task completed successfully
                                        // ...
                                        for (FirebaseVisionFace face : faces) {
                                            bounds = face.getBoundingBox();
                                            rotY = face.getHeadEulerAngleY();  // Head is rotated to the right rotY degrees
                                            Log.d(TAG, "Rot Y: " + rotY);
                                            rotZ = face.getHeadEulerAngleZ();  // Head is tilted sideways rotZ degrees
                                            Log.d(TAG, "Rot Z: " + rotZ);

                                            all_points = face.getContour(FirebaseVisionFaceContour.ALL_POINTS).getPoints();
                                            Log.d(TAG, "No. points: " + all_points.size());
                                            for(FirebaseVisionPoint point:all_points){
                                                Log.d(TAG, "Point: " + point);
                                            }

                                            face_oval = face.getContour(FirebaseVisionFaceContour.FACE).getPoints();

                                            left_eyebrow_top = face.getContour(FirebaseVisionFaceContour.FACE).getPoints();



                                            right_eyebrow_top = face.getContour(FirebaseVisionFaceContour.RIGHT_EYEBROW_TOP).getPoints();
                                            left_eyebrow_bottom = face.getContour(FirebaseVisionFaceContour.LEFT_EYEBROW_BOTTOM).getPoints();
                                            right_eyebrow_bottom = face.getContour(FirebaseVisionFaceContour.RIGHT_EYEBROW_BOTTOM).getPoints();
                                            left_eye = face.getContour(FirebaseVisionFaceContour.LEFT_EYE).getPoints();
                                            right_eye = face.getContour(FirebaseVisionFaceContour.RIGHT_EYE).getPoints();
                                            upper_lip_top = face.getContour(FirebaseVisionFaceContour.UPPER_LIP_TOP).getPoints();
                                            upper_lip_bottom = face.getContour(FirebaseVisionFaceContour.UPPER_LIP_BOTTOM).getPoints();
                                            lower_lip_top = face.getContour(FirebaseVisionFaceContour.LOWER_LIP_BOTTOM).getPoints();
                                            lower_lip_bottom = face.getContour(FirebaseVisionFaceContour.LOWER_LIP_BOTTOM).getPoints();
                                            nose_bridge = face.getContour(FirebaseVisionFaceContour.NOSE_BRIDGE).getPoints();
                                            nose_bottom = face.getContour(FirebaseVisionFaceContour.NOSE_BOTTOM).getPoints();

                                        }
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                    }
                                });

    }


}
