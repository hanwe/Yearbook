package com.graduation.yearbook.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.graduation.yearbook.PhotoPage.PhotoViewer;
import com.graduation.yearbook.QRScan;
import com.graduation.yearbook.R;

/**
 * Created by hanwe on 2015/4/30.
 */
public class ChoseFunction extends AlertDialog
{
    private Button bu_scan, bu_login;
    private Context m_context;

    public ChoseFunction(Context context, int them)
    {
        super(context, them);

        m_context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.album_chose_dialog);

        bu_scan = (Button) findViewById(R.id.album_bu_scan);
        bu_scan.setOnClickListener(mOnClickListener);

        bu_login = (Button) findViewById(R.id.album_bu_login);
        bu_login.setOnClickListener(mOnClickListener);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.album_bu_scan:
                {
                    Intent intent = new Intent();
                    intent.setClass(m_context, QRScan.class);
                    m_context.startActivity(intent);
                    break;
                }
                case R.id.album_bu_login:
//                {
//                    Intent intent = new Intent();
//                    intent.setClass(m_context, PhotoViewer.class);
//                    m_context.startActivity(intent);
//                    finish();

                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(m_context, PhotoViewer.class);
                    //                    intent.putExtra("ScanInfo", sym.getData());
//                    intent.putExtra("ScanInfo", "WFjlnIvlsI82MDE%3D");
                    m_context.startActivity(intent); //這行一定要放在這函式的最後

                    break;
            }
        }

    };
}
