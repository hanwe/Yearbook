package com.graduation.yearbook.Http.HttpProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.graduation.yearbook.Http.Gson.Request.DeviceInfo;
import com.graduation.yearbook.Http.Gson.Request.PhotoInfoRequest;
import com.graduation.yearbook.Interface.ApiService;
import com.graduation.yearbook.R;
import com.graduation.yearbook.application.BaseApplication;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

/**
 * Created by hanwe on 15/8/28.
 */
public class HttpProvider
{
    private static HttpProvider instance;
    private String apiUrl = "";
    private ApiService apiService;
    private GsonBuilder gsonBuilder = new GsonBuilder();

    public static HttpProvider getInstance()
    {
        if (instance == null)
        {
            instance = new HttpProvider();
        }

        return instance;
    }

    private HttpProvider()
    {
        apiUrl = BaseApplication.getContext().getResources().getString(R.string.URLDomain);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(new OkClient(HttpClientHelper.getOkHttpClient()))
                .setErrorHandler(new RetrofitErrorHandler())
                .setEndpoint(apiUrl)
                .build();

        apiService = restAdapter.create(ApiService.class);
    }

    public Response forgetPassword(String aPPVersion,
                                   DeviceInfo deviceInfo,
                                   String albumID) throws Exception
    {
        PhotoInfoRequest photoInfoRequest = new PhotoInfoRequest();
        photoInfoRequest.APPVersion = aPPVersion;
        photoInfoRequest.DeviceInfo = deviceInfo;
        photoInfoRequest.ID = albumID;

        String jsonString = new Gson().toJson(photoInfoRequest);
        TypedInput in = new TypedByteArray("application/json", jsonString.getBytes("UTF-8"));

        Response response = apiService.photoInfo(in);
        return response;
    }
}
