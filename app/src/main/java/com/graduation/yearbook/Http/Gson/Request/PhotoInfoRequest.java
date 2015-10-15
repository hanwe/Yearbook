package com.graduation.yearbook.Http.Gson.Request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanwe on 15/8/25.
 */
public class PhotoInfoRequest
{
    @SerializedName("APPVersion")
    public String APPVersion;

    @SerializedName("AlbumID")
    public String ID;

    public DeviceInfo DeviceInfo;

}