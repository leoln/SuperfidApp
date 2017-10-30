package br.com.bhl.superfid.util;

import android.util.Log;

import java.io.IOException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class WebClient {

    private static final String BASE_URL = "https://superfidweb.herokuapp.com";

    public static String post(String caminho, String json) throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + caminho);

        MediaType mediaType =
                MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, json);

        builder.post(body);
        Request request = builder.build();
        Response response = null;

        try {
            response = client.newCall(request).execute();
        } catch (Exception e) {
            String s = e.getMessage();
            Log.v("ERRO POST", s);
        }

        String jsonDeResposta = response.body().string();

        return jsonDeResposta;

    }

    public static String get(String caminho, String valor) throws IOException {

        URL url = new URL(BASE_URL + caminho + valor);

        Log.v("URL", BASE_URL + caminho + valor);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();
        String jsonDeResposta = "";

        try {
            Response response = client.newCall(request).execute();
            jsonDeResposta = response.body().string();
        } catch( Exception e) {
            Log.e("ERROJSON", e.getMessage());
        }

        Log.v("JSONRESP", jsonDeResposta);

        return jsonDeResposta;
    }

}
