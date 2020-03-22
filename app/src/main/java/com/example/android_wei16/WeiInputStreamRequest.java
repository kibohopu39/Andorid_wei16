package com.example.android_wei16;

import androidx.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.Map;

//串流的資料為byte
public class WeiInputStreamRequest extends Request<byte[]> {
    private Response.Listener<byte[]> listener;
    private Map<String,String> responseHeader;
    private Map<String,String> params;


    public WeiInputStreamRequest(
            int method,
            String url,
            Map<String,String> params,
            Response.Listener<byte[]> listener,
            @Nullable Response.ErrorListener errorlistener
            ) {
        super(method, url, errorlistener);

        this.listener=listener;
        this.params=params;
    }

    @Override
    public Map<String, String> getParams() {//把參數調出來
        return params;
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {//解析網路回傳的資料
        responseHeader=response.headers;//取得Header
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(byte[] response) {
        listener.onResponse(response);
    }

}
