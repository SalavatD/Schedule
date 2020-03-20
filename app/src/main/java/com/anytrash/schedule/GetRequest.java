package com.anytrash.schedule;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public abstract class GetRequest<T1, T3> extends AsyncTask<T1, Void, T3> {

    String getRequest(String string) throws IOException {
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(string);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.connect();
            bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        } finally {
            if (bufferedReader != null) bufferedReader.close();
        }
    }
}
