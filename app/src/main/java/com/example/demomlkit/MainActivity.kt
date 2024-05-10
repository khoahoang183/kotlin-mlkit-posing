package com.example.demomlkit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.accurate.AccuratePoseDetectorOptions
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import java.io.ByteArrayOutputStream
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    // Accurate pose detector on static images, when depending on the pose-detection-accurate sdk
    val options = AccuratePoseDetectorOptions.Builder()
        .setDetectorMode(AccuratePoseDetectorOptions.SINGLE_IMAGE_MODE)
        .build()

    val poseDetector = PoseDetection.getClient(options)

    private lateinit var resultImg: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resultImg = findViewById(R.id.resultImg)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.img_gu3)
        val image = InputImage.fromBitmap(bitmap, 0)

        poseDetector.process(image).addOnSuccessListener { pose ->
            // Task completed successfully
            val allPoseLandmarks = pose.allPoseLandmarks
            val leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
            val rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
            val leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
            val rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)
            val leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)
            val rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)
            val leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP)
            val rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP)
            val leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE)
            val rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE)
            val leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE)
            val rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE)
            val leftPinky = pose.getPoseLandmark(PoseLandmark.LEFT_PINKY)
            val rightPinky = pose.getPoseLandmark(PoseLandmark.RIGHT_PINKY)
            val leftIndex = pose.getPoseLandmark(PoseLandmark.LEFT_INDEX)
            val rightIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX)
            val leftThumb = pose.getPoseLandmark(PoseLandmark.LEFT_THUMB)
            val rightThumb = pose.getPoseLandmark(PoseLandmark.RIGHT_THUMB)
            val leftHeel = pose.getPoseLandmark(PoseLandmark.LEFT_HEEL)
            val rightHeel = pose.getPoseLandmark(PoseLandmark.RIGHT_HEEL)
            val leftFootIndex = pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX)
            val rightFootIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX)
            val nose = pose.getPoseLandmark(PoseLandmark.NOSE)
            val leftEyeInner = pose.getPoseLandmark(PoseLandmark.LEFT_EYE_INNER)
            val leftEye = pose.getPoseLandmark(PoseLandmark.LEFT_EYE)
            val leftEyeOuter = pose.getPoseLandmark(PoseLandmark.LEFT_EYE_OUTER)
            val rightEyeInner = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_INNER)
            val rightEye = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE)
            val rightEyeOuter = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_OUTER)
            val leftEar = pose.getPoseLandmark(PoseLandmark.LEFT_EAR)
            val rightEar = pose.getPoseLandmark(PoseLandmark.RIGHT_EAR)
            val leftMouth = pose.getPoseLandmark(PoseLandmark.LEFT_MOUTH)
            val rightMouth = pose.getPoseLandmark(PoseLandmark.RIGHT_MOUTH)

            val drawBitmap = drawAllPose(
                bitmap,
                pose,
                leftShoulder?.position?.x!!,
                leftShoulder?.position?.y!!,
                rightShoulder?.position?.x!!,
                rightShoulder?.position?.y!!,
                leftElbow?.position?.x!!,
                leftElbow?.position?.y!!,
                rightElbow?.position?.x!!,
                rightElbow?.position?.y!!,
                leftWrist?.position?.x!!,
                leftWrist?.position?.y!!,
                rightWrist?.position?.x!!,
                rightWrist?.position?.y!!,
                leftHip?.position?.x!!,
                leftHip?.position?.y!!,
                rightHip?.position?.x!!,
                rightHip?.position?.y!!,
                leftKnee?.position?.x!!,
                leftKnee?.position?.y!!,
                rightKnee?.position?.x!!,
                rightKnee?.position?.y!!,
                leftAnkle?.position?.x!!,
                leftAnkle?.position?.y!!,
                rightAnkle?.position?.x!!,
                rightAnkle?.position?.y!!,
            )

            resultImg.setImageBitmap(drawBitmap)

        }
    }

    private fun drawAllPose(
        bitmap: Bitmap,
        pose: Pose,
        lShoulderX: Float, lShoulderY: Float, rShoulderX: Float, rShoulderY: Float,
        lElbowX: Float, lElbowY: Float, rElbowX: Float, rElbowY: Float,
        lWristX: Float, lWristY: Float, rWristX: Float, rWristY: Float,
        lHipX: Float, lHipY: Float, rHipX: Float, rHipY: Float,
        lKneeX: Float, lKneeY: Float, rKneeX: Float, rKneeY: Float,
        lAnkleX: Float, lAnkleY: Float, rAnkleX: Float, rAnkleY: Float
    ): Bitmap {
        val paint = Paint()
        paint.color = Color.YELLOW
        val strokeWidth = 5.0f
        paint.strokeWidth = strokeWidth
        val drawBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)

        val canvas = Canvas(drawBitmap)
        canvas.drawBitmap(bitmap, 0f, 0f, null)


        val between2Shoulder = Math.abs(lShoulderX - rShoulderX)
        val isXoayNgang: Boolean = between2Shoulder < 100

        canvas.drawLine(
            lShoulderX,
            lShoulderY,
            rShoulderX,
            rShoulderY,
            paint
        ) // Left Shoulder to Right Shoulder

        canvas.drawLine(
            rShoulderX,
            rShoulderY,
            rElbowX,
            rElbowY,
            paint
        ) // Right Shoulder to Right Elbow

        canvas.drawLine(rElbowX, rElbowY, rWristX, rWristY, paint) // Right Elbow to Right Wrist
        canvas.drawLine(
            lShoulderX,
            lShoulderY,
            lElbowX,
            lElbowY,
            paint
        ) // Left Shoulder to Left Elbow
        canvas.drawLine(lElbowX, lElbowY, lWristX, lWristY, paint) // Left Elbow to Left Wrist
        canvas.drawLine(rShoulderX, rShoulderY, rHipX, rHipY, paint) // Right Shoulder to Right Hip
        canvas.drawLine(lShoulderX, lShoulderY, lHipX, lHipY, paint) // Left Shoulder to Left Hip
        canvas.drawLine(lHipX, lHipY, rHipX, rHipY, paint) // Hip
        canvas.drawLine(rHipX, rHipY, rKneeX, rKneeY, paint) // Right Hip To Right Foot Knee
        canvas.drawLine(lHipX, lHipY, lKneeX, lKneeY, paint) // Left Hip to Left Foot Knee
        canvas.drawLine(rKneeX, rKneeY, rAnkleX, rAnkleY, paint) // Right Foot Knee to Right Ankle
        canvas.drawLine(lKneeX, lKneeY, lAnkleX, lAnkleY, paint) // Left Foot Knee to Left Ankle


        // customize
        val leftPinky = pose.getPoseLandmark(PoseLandmark.LEFT_PINKY)
        val rightPinky = pose.getPoseLandmark(PoseLandmark.RIGHT_PINKY)
        val leftIndex = pose.getPoseLandmark(PoseLandmark.LEFT_INDEX)
        val rightIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX)
        val leftThumb = pose.getPoseLandmark(PoseLandmark.LEFT_THUMB)
        val rightThumb = pose.getPoseLandmark(PoseLandmark.RIGHT_THUMB)
        val leftHeel = pose.getPoseLandmark(PoseLandmark.LEFT_HEEL)
        val rightHeel = pose.getPoseLandmark(PoseLandmark.RIGHT_HEEL)
        val leftFootIndex = pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX)
        val rightFootIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX)
        val nose = pose.getPoseLandmark(PoseLandmark.NOSE)
        val leftEyeInner = pose.getPoseLandmark(PoseLandmark.LEFT_EYE_INNER)
        val leftEye = pose.getPoseLandmark(PoseLandmark.LEFT_EYE)
        val leftEyeOuter = pose.getPoseLandmark(PoseLandmark.LEFT_EYE_OUTER)
        val rightEyeInner = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_INNER)
        val rightEye = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE)
        val rightEyeOuter = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_OUTER)
        val leftEar = pose.getPoseLandmark(PoseLandmark.LEFT_EAR)
        val rightEar = pose.getPoseLandmark(PoseLandmark.RIGHT_EAR)
        val leftMouth = pose.getPoseLandmark(PoseLandmark.LEFT_MOUTH)
        val rightMouth = pose.getPoseLandmark(PoseLandmark.RIGHT_MOUTH)

//        val paintRed = Paint()
//        paintRed.color = Color.RED
//        paintRed.strokeWidth = 10f
//
////        canvas.drawLine(
////            nose?.position?.x!!,
////            nose?.position?.y!!,
////            (lAnkleX + rAnkleX) / 2,
////            (rAnkleY + lAnkleY) / 2,
////            paint
////        ) // Left Shoulder to Right Shoulder
//
//        if (isXoayNgang) {
//            if (lShoulderX > rShoulderX)
////                canvas.drawLine(
////                    lShoulderX,
////                    lShoulderY,3
////                    lAnkleX,
////                    lAnkleY,
////                    paint
////                ) // Left Shoulder to Right Shoulder
//
//                canvas.drawLine(
//                    lShoulderX,
//                    lShoulderY,
//                    lShoulderX,
//                    lAnkleY,
//                    paintRed
//                ) // Left Shoulder to Right Shoulder
//            else
//                canvas.drawLine(
//                    rShoulderX,
//                    rShoulderY,
//                    rAnkleX,
//                    rAnkleY,
//                    paintRed
//                ) // Left Shoulder to Right Shoulder
//        } else {
//            canvas.drawLine(
//                nose?.position?.x!!,
//                nose?.position?.y!!,
//                (lAnkleX + rAnkleX) / 2,
//                (rAnkleY + lAnkleY) / 2,
//                paintRed
//            ) // Left Shoulder to Right Shoulder
//        }

                val paintRed = Paint()
        paintRed.color = Color.RED
        paintRed.strokeWidth = 30f
        canvas.drawLine(leftEar?.position?.x!!, leftEar?.position?.y!!, lShoulderX, lShoulderY, paintRed) // Left Elbow to Left Wrist
        canvas.drawLine( lShoulderX, lShoulderY, lHipX, lHipY, paintRed) // Left Elbow to Left Wrist

        return drawBitmap
    }
}