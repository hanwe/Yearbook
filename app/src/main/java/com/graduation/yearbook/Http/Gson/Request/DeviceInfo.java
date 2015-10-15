package com.graduation.yearbook.Http.Gson.Request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hanwe on 15/8/25.
 */
public class DeviceInfo
{
    @SerializedName("Sys_Type")
    public String Sys_Type;

    @SerializedName("Dev_Type")
    public String Dev_Type;

    @SerializedName("OS_Ver")
    public String OS_Ver;

    @SerializedName("Phone_id")
    public String Phone_id;

}