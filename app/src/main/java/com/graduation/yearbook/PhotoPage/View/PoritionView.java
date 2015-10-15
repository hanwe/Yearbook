package com.graduation.yearbook.PhotoPage.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by sinopac on 2015/8/9.
 */
public class PoritionView extends View
{
    private Bitmap showPic = null;
    private int startX = 0;
    private int startY = 0;

    public PoritionView(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        canvas.drawBitmap(showPic, startX, startY, null);
    }

    public void setBitmapShow(Bitmap b, int x, int y)
    {
        showPic = b;
        startX = x;
        startY = y;
    }
}
