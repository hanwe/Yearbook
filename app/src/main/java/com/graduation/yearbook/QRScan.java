package com.graduation.yearbook;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;

import com.graduation.yearbook.Compoment.CameraPreview;
import com.graduation.yearbook.PhotoPage.PhotoStart;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import java.net.URLEncoder;

/**
 * Created by hanwe on 2015/5/3.
 */
public class QRScan extends Activity
{
    private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;
    private ImageScanner scanner;

    private boolean barcodeScanned = false;
    private boolean previewing = true;


    static
    {
        System.loadLibrary("iconv");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scan);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();

        /* Instance barcode scanner */
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
        FrameLayout preview = (FrameLayout) findViewById(R.id.cameraPreview);
        preview.addView(mPreview);

        //        m_scanText = (TextView) findViewById(R.id.scanText);
        //        m_scanText.setText("Scanning...");
        //
        //        m_scanButton = (Button) findViewById(R.id.ScanButton);
        //
        //        m_scanButton.setOnClickListener(new View.OnClickListener()
        //        {
        //            public void onClick(View v)
        //            {
        //                if (barcodeScanned)
        //                {
        //                    barcodeScanned = false;
        //                    m_scanText.setText("Scanning...");
        //                    mCamera.setPreviewCallback(previewCb);
        //                    mCamera.startPreview();
        //                    previewing = true;
        //                    mCamera.autoFocus(autoFocusCB);
        //                }
        //            }
        //        });
    }

    public void onPause()
    {
        super.onPause();
        releaseCamera();
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance()
    {
        Camera c = null;
        try
        {
            c = Camera.open();
        }
        catch (Exception e)
        {
        }
        return c;
    }

    private void releaseCamera()
    {
        if (mCamera != null)
        {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Runnable doAutoFocus = new Runnable()
    {
        public void run()
        {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }
    };


    private PreviewCallback previewCb = new PreviewCallback()
    {
        public void onPreviewFrame(byte[] data, Camera camera)
        {
            Camera.Parameters parameters = camera.getParameters();
            Size size = parameters.getPreviewSize();

            Image barcode = new Image(size.width, size.height, "Y800");

//            Resources res = getResources();
//            Bitmap barcodeBmp = BitmapFactory.decodeResource(res, R.drawable.default_qrcode);
//
//
//            int width = barcodeBmp.getWidth();
//            int height = barcodeBmp.getHeight();
//            int[] pixels = new int[width * height];
//            barcodeBmp.getPixels(pixels, 0, width, 0, 0, width, height);
//
//            Image barcode = new Image(width, height, "RGB4");
//            barcode.setData(pixels);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
//            barcode.setData(baos.toByteArray());

            barcode.setData(data);

            int result = scanner.scanImage(barcode.convert("Y800"));


            if (result != 0)
            {
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();

                SymbolSet syms = scanner.getResults();
                for (Symbol sym : syms)
                {
                    barcodeScanned = true;
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(QRScan.this, PhotoStart.class);
                    //                    intent.putExtra("ScanInfo", sym.getData());
                    String strQueryURl = URLEncoder.encode("WFjlnIvlsI82MDE%3D");
                    intent.putExtra("ScanInfo", "WFjlnIvlsI82MDE%3D");
                    startActivity(intent); //這行一定要放在這函式的最後
                }
            }
        }
    };

    // Mimic continuous auto-focusing
    AutoFocusCallback autoFocusCB = new AutoFocusCallback()
    {
        public void onAutoFocus(boolean success, Camera camera)
        {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };


}
