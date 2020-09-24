package com.example.qr_scanner

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun startBarcodeReader(view : View){
        // IntentIntegrator를 통해 카메라를 띄워 QRCODE를 스캔할 수 있음
        IntentIntegrator(this).initiateScan()
    }

    fun startBarcodeReaderCustom(view : View){
        val intentgrator = IntentIntegrator(this)
        intentgrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE) // 특정 규격의 바코드만 지정하고자 할 떄 사용 , 설정하지 않으면 그냥 모든 qr코드를 인식해버리게 됨
        intentgrator.setPrompt("QR 코드를 스캔하여 주세요") // 스캔할 떄 하단의 문구를 custom
        intentgrator.setCameraId(0) // 0 : 후면카메라 , 1: 전면카메라
        intentgrator.setBeepEnabled(true) // 인식시 삡 소리내는거 custom
        intentgrator.setBarcodeImageEnabled(true) // onActivityResult에서 image또한 bitmap으로 반환되어 온다
        intentgrator.initiateScan()
    }

    fun startBarcodeReaderCustomActivity(view : View){
        val intentgrator = IntentIntegrator(this)
        intentgrator.setBarcodeImageEnabled(true)
        intentgrator.captureActivity = MyBarcodeReaderActivity::class.java
        intentgrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        if(result != null){
            if(result.contents != null){
                Toast.makeText(this@MainActivity,"Scanned",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity,"Cancel",Toast.LENGTH_SHORT).show()
            }
            if(result.barcodeImagePath != null){
                val bitmap = BitmapFactory.decodeFile(result.barcodeImagePath)
                scannedBitmap.setImageBitmap(bitmap)
            }
        } else {

        }
    }
}