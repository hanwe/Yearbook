package com.graduation.yearbook.Http.HttpProvider;

/**
 * Created by josephwang on 15/6/25.
 */
import com.graduation.yearbook.util.JLog;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

public class RetrofitErrorHandler implements ErrorHandler {

    @Override
    public Throwable handleError(RetrofitError cause) {
        if (cause != null) {
            JLog.d(JLog.JosephWang, "RetrofitErrorHandler getUrl " + cause.getUrl());
            JLog.d(JLog.JosephWang, "RetrofitErrorHandler getMessage " + cause.getMessage());
            if (cause.getBody() != null) {
                JLog.d(JLog.JosephWang, "RetrofitErrorHandler getMessage " + cause.getBody().toString());
            }
            if (cause.getResponse() != null) {
                JLog.d(JLog.JosephWang, "RetrofitErrorHandler getResponse getReason " + cause.getResponse().getReason());
            }
        }

        if (cause.isNetworkError()) {
            if (cause.getCause() instanceof SocketTimeoutException) {
                return new SocketTimeoutException();
            } else if (cause.getCause() instanceof UnknownHostException) {
                return new UnknownHostException();
            } else {
                return cause;
            }
        }
        return cause;
    }
}
