package com.graduation.yearbook.PhotoPage;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import com.google.gson.GsonBuilder;
import com.graduation.yearbook.DownloadFileAsync;
import com.graduation.yearbook.Http.Gson.Request.DeviceInfo;
import com.graduation.yearbook.Http.Gson.Response.PhotoInfoResponse;
import com.graduation.yearbook.Http.HttpProvider.HttpProvider;
import com.graduation.yearbook.R;
import com.graduation.yearbook.util.ui.DeviceUtil;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by hanwe on 2015/4/19.
 */
public class PhotoStart extends Activity
{
    private ProgressBar pro_download;
    private Button bu_Enter;
    private Intent intent;
    private String AlbumID = "";
    private Thread thread;
    private DeviceInfo deviceInfo = new DeviceInfo();
    private static final int SENDALBUMID = 0;
    private static final int DOWNLOAD_ZIP = 1;
    private Response response;
    private GsonBuilder gsonBuilder = new GsonBuilder();
    private PhotoInfoResponse result;

    private Handler handler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == SENDALBUMID)
            {
                PhotoInfoResponse photoInfoResponse = (PhotoInfoResponse) msg.obj;

                setDownload(photoInfoResponse);

            }
            else if (msg.what == DOWNLOAD_ZIP)
            {
                String strPath = Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName() + "/Files/";
                try
                {
                    bu_Enter.setText("下載中請稍候");
                    UnZipFolder(strPath + "DownLoad.zip", strPath);
                    bu_Enter.setText("請進入畫面");
                    bu_Enter.setOnClickListener(mOnClickListener);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.album_main_fist_body);

        pro_download = (ProgressBar) findViewById(R.id.album_firstin_probar);

        bu_Enter = (Button) findViewById(R.id.album_firstin_enter);
        bu_Enter.setOnClickListener(null);

        intent = this.getIntent();
        AlbumID = intent.getStringExtra("ScanInfo");

        getPhotoInfo();

    }

    public void getPhotoInfo()
    {
        thread = new Thread(runnable);
        thread.start();
    }

    private void setDownload(PhotoInfoResponse photoInfoResponse)
    {
        DownloadFileAsync m_DownloadFileAsync = new DownloadFileAsync(this, pro_download, handler, photoInfoResponse);

        if (!AlbumID.equals(""))
        {
            m_DownloadFileAsync.execute(getString(R.string.ZIPURL) +
                                        photoInfoResponse.AlbumInfo.ID + "&Version=" +
                                        photoInfoResponse.AlbumInfo.Version + "&File=" +
                                        photoInfoResponse.AlbumInfo.ZIPCnt);
//                m_DownloadFileAsync.execute("http://130.211.249.249:8080/JCAP/Download?ID=WFjlnIvlsI82MDE%3D&Version=20150801123000&File=1");

        }


    }

    public static void UnZipFolder(String zipFileString, String outPathString) throws Exception
    {
        android.util.Log.v("XZip", "UnZipFolder(String, String)");

        File f = new File(zipFileString);

        ZipInputStream inZip = new ZipInputStream(new java.io.FileInputStream(f));
        ZipEntry zipEntry;
        String szName = "";

        while ((zipEntry = inZip.getNextEntry()) != null)
        {
            szName = zipEntry.getName();

            if (zipEntry.isDirectory())
            {
                // get the folder name of the widget
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(outPathString + File.separator + szName);
                folder.mkdirs();

            }
            else
            {
                File file = new File(outPathString + szName);
                File fileDir = new File(file.getParent());
                fileDir.mkdirs();
                file.createNewFile();
                // get the output stream of the file
                java.io.FileOutputStream out = new java.io.FileOutputStream(file);
                int len;
                byte[] buffer = new byte[2048];
                // read (len) bytes into buffer
                while ((len = inZip.read(buffer)) != -1)
                {
                    // write (len) byte from buffer at the position 0
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }//end of while

        inZip.close();
        f.delete();
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent();
            intent.setClass(PhotoStart.this, PhotoViewer.class);
            startActivity(intent);
            finish();
        }
    };

    private Runnable runnable = new Runnable()
    {

        @Override
        public void run()
        {
            try
            {
                deviceInfo.Sys_Type = "Android";
                deviceInfo.Dev_Type = Build.BRAND;
                deviceInfo.OS_Ver = Build.VERSION.RELEASE;
                deviceInfo.Phone_id = UUID.randomUUID().toString();

                response = HttpProvider.getInstance().forgetPassword(DeviceUtil.getVersionName(PhotoStart.this), deviceInfo, AlbumID);
            }
            catch (Exception e)
            {
                e.getMessage();
            }
            finally
            {
                thread.interrupt();
                thread = null;

                String jsonString = new String(((TypedByteArray) response.getBody()).getBytes());
                result = gsonBuilder.create().fromJson(jsonString, PhotoInfoResponse.class);

                Message message = Message.obtain();
                message.obj = result;
                message.what = SENDALBUMID;

                handler.sendMessage(message);
            }
        }
    };

}
