package com.example.kai.verschachtelt.dataHandling;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Kai on 14.08.2016.
 */
public class PuzzleDownloadTask extends AsyncTask<String, Integer, String> {

    private DownloadListener listener;

    public PuzzleDownloadTask(DownloadListener listener) {
        this.listener = listener;
    }

    /**
     * Takes an Web address and downloads a String from that.
     * @param params
     * @return
     */
    @Override
    protected String doInBackground(String... params) {
        String jsonString = "";

        try {
            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    jsonString += line;
                }
                br.close();
                is.close();
                conn.disconnect();
            } else {
                throw new IllegalStateException("HTTP response: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        listener.onDownloadFinished(result);
    }
}
