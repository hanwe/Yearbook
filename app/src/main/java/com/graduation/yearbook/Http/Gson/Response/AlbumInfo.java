package com.graduation.yearbook.Http.Gson.Response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hanwe on 15/8/25.
 */
public class AlbumInfo
{
    @SerializedName("Name")
    public String Name;

    @SerializedName("Status")
    public String Status;

    @SerializedName("TotalPicCnt")
    public String TotalPicCnt;

    @SerializedName("ID")
    public String ID;

    @SerializedName("Version")
    public String Version;

    @SerializedName("ZIPCnt")
    public String ZIPCnt;


}