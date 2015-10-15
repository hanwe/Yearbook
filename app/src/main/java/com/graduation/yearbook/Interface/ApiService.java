package com.graduation.yearbook.Interface;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.mime.TypedInput;

/**
 * Created by hanwe on 15/8/28.
 */
public interface ApiService
{
    @POST("/Download")
    Response photoInfo(@Body TypedInput photoInfoRequest);
}
