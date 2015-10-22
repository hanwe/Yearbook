package com.graduation.yearbook;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;

import com.graduation.yearbook.Http.Gson.Response.PhotoInfoResponse;
import com.graduation.yearbook.PhotoPage.PhotoStart;

import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by hanwe on 2015/4/19.
 */
public class DownloadFileAsync extends AsyncTask<String, String, String>
{

    private Context m_Context;
    private ProgressBar m_ProgressBar;
    private Handler m_Handler;
    private String StrSuccess = "1", StrFaile = "0";
    private JSONObject jSONObject;
    private static final int DOWNLOAD_ZIP = 1;
    private PhotoInfoResponse photoInfoResponse;

    public DownloadFileAsync(PhotoStart context, ProgressBar pro_download, Handler handler, PhotoInfoResponse photoInfoResponse)
    {
        m_Context = context;
        m_ProgressBar = pro_download;
        m_Handler = handler;
        this.photoInfoResponse = photoInfoResponse;
    }

    @Override
    protected String doInBackground(String... params)
    {
        int count;
        try
        {
            URL url = new URL(params[0]);
            URLConnection conection = url.openConnection();
            conection.connect();
            // getting file length
            int lenghtOfFile = conection.getContentLength();

            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            // Output stream to write file
            File meta = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + m_Context.getApplicationContext().getPackageName());
            meta.mkdirs();
            File dir = new File(meta, "Files");
            dir.mkdirs(); //added
            File imageFile = new File(dir, "DownLoad.zip");
            imageFile.createNewFile(); //added
            OutputStream output = new FileOutputStream(imageFile);


            //            String strPath = Environment.getExternalStorageDirectory() + "/Android/data/" + m_Context.getApplicationContext().getPackageName() + "/Files/DownLoad.zip";
            //            OutputStream output = new FileOutputStream(strPath);

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1)
            {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                // writing data to file
                output.write(data, 0, count);
            }

            output.flush();

            // closing streams
            output.close();
            input.close();

            return StrSuccess;
        }
        catch (Exception e)
        {
            Log.e("Error: ", e.getMessage());
        }

        return StrFaile;
    }

    @Override
    protected void onProgressUpdate(String... values)
    {
        super.onProgressUpdate(values);
        m_ProgressBar.setProgress(Integer.parseInt(values[0]));
    }

    @Override
    protected void onPostExecute(String s)
    {
        if(s.equals(StrSuccess))
        {
            Message message = Message.obtain();
            message.obj = "Success";
            message.what = DOWNLOAD_ZIP;

            m_Handler.sendMessage(message);
        }
        else
        {

        }

    }
}
