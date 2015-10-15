package com.graduation.yearbook.Http.Gson.Response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hanwe on 15/8/25.
 */
public class PhotoInfoResponse
{
    @SerializedName("ServerStatus")
    public String ServerStatus;

    @SerializedName("APPVersion")
    public String APPVersion;

    public AlbumInfo AlbumInfo;


}