package com.example.dangillsa_kotlin

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.SystemClock
import android.util.Log
import com.example.dangillsa_kotlin.ObjectSuccessActivity
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.Rot90Op
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.detector.Detection
import org.tensorflow.lite.task.vision.detector.ObjectDetector

// ObjectDetectorHelper Class는 객체 감지를 수행하는 모델과 모델의 설정값, 그리고 결과를 처리하는 리스너 등을 포함한다.
class ObjectDetectorHelper(
    var threshold: Float = 0.5f,
    var numThreads: Int = 2,
    var maxResults: Int = 3,
    var currentDelegate: Int = 0,
    val context: Context,
    val objectDetectorListener: DetectorListener?
){

    // 변경 시 재설정할 수 있도록 변수여야 함
    // Object Detector(개체 디텍터)가 변경되지 않으면 lazy val를 사용
    private var objectDetector: ObjectDetector? = null

    init {
        setupObjectDetector()
    }

    fun clearObjectDetector() {
        objectDetector = null
    }


    // 객체 감지 모델을 설정하고 초기화하는 함수입니다.
    fun setupObjectDetector() {
        // 지정된 최대 결과 및 점수 임계값을 사용하여 디텍터의 기본 옵션 만들기
        val optionsBuilder =
            ObjectDetector.ObjectDetectorOptions.builder()
                .setScoreThreshold(threshold)
                .setMaxResults(maxResults)

        // 사용된 스레드 수를 포함한 일반 탐지 옵션 설정
        val baseOptionsBuilder = BaseOptions.builder().setNumThreads(numThreads)

        // 지정된 하드웨어를 사용하여 모델을 실행.
        // CPU로 기본 설정됨
        when (currentDelegate) {
            DELEGATE_CPU -> {
            }
            DELEGATE_GPU -> {
                if (CompatibilityList().isDelegateSupportedOnThisDevice) {
                    baseOptionsBuilder.useGpu()
                } else {
                    objectDetectorListener?.onError("이 디바이스에서는 GPU가 지원되지 않습니다.")
                }
            }
            DELEGATE_NNAPI -> {
                baseOptionsBuilder.useNnapi()
            }
        }

        optionsBuilder.setBaseOptions(baseOptionsBuilder.build())

        try {
            objectDetector =
                ObjectDetector.createFromFileAndOptions(
                    context,
                    "final_model.tflite",
                    optionsBuilder.build()
                )
        } catch (e: IllegalStateException) {
            objectDetectorListener?.onError(
                "개체 디텍터를 초기화하지 못했습니다. 자세한 내용은 오류 로그를 참조하십시오."
            )
            Log.e("Test", "TFLite가 오류와 함께 모델을 로드하지 못했습니다 : " + e.message)
        }
    }

    // 객체인식된 값을 서버에 저장, 문서화한다.
    private fun debugPrint(results: List<Detection>) {
//        val TAG = "TFLite - ODT"
        for ((i, obj) in results.withIndex()) {
//            val box = obj.boundingBox
//            Log.d(TAG, "Detected object:${i}")
//            Log.d(TAG,"boundingBox: (${box.left}, ${box.top} - (${box.right},${box.bottom}")
            for ((j, category) in obj.categories.withIndex()) {
                // ★ category label
                val labelname: String = category.label
                // score (퍼센트 단위)
                val confidence: Int = category.score.times(100).toInt()
                Log.d("Tag",labelname)
                Log.d("tag",confidence.toString())
                if (confidence >= 95) { // 정확도 95퍼라면 넘어감
                    val intent = Intent(context, ObjectSuccessActivity::class.java)
//                    (context as Activity).finish()
                    // 넘어가기 전 2초 대기
                    try{
                        Thread.sleep(2000)
                    } catch (e:InterruptedException){
                        e.printStackTrace()
                    }
                    // 화면전환
                    context.startActivity(intent)
                }
            }
        }
    }

    // 입력 이미지에서 객체 감지를 수행하는 함수
    fun detect(image: Bitmap, imageRotation: Int) {
        if (objectDetector == null) {
            setupObjectDetector()
        }

        // 추론 시간은 프로세스의 시작과 종료 시점의 시스템 시간 간의 차이
        var inferenceTime = SystemClock.uptimeMillis()

        // 이미지에 대한 전처리기
        // See https://www.tensorflow.org/lite/inference_with_metadata/lite_support#imageprocessor_architecture
        val imageProcessor =
            ImageProcessor.Builder()
                .add(Rot90Op(-imageRotation / 90))
                .build()
        val tensorImage = imageProcessor.process(TensorImage.fromBitmap(image))

        val results = objectDetector?.detect(tensorImage)
        inferenceTime = SystemClock.uptimeMillis() - inferenceTime

        if (results != null) {
            debugPrint(results)
        }

        objectDetectorListener?.onResults(
            results,
            inferenceTime,
            tensorImage.height,
            tensorImage.width
        )
    }

    interface DetectorListener {
        fun onObjectDetected(detectedObjects: List<Detection>)
        fun onError(error: String)
        fun onResults(
            results: MutableList<Detection>?,
            inferenceTime: Long,
            imageHeight: Int,
            imageWidth: Int
        )
    }

    companion object {
        const val DELEGATE_CPU = 0
        const val DELEGATE_GPU = 1
        const val DELEGATE_NNAPI = 2
    }
}
