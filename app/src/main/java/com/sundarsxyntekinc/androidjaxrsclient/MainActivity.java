package com.sundarsxyntekinc.androidjaxrsclient;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView jaxrs;
     String url = "http://10.1.16.44:8080/RESTful_Jersey_Hello_World/rest/hello";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find the Text View
        jaxrs = (TextView) findViewById(R.id.jaxrs);

        new myAsyncTask().execute(url);



    }


    private class myAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... urls) {


            // params comes from the execute() call: params[0] is the url.
            try {
                Log.e("URL::::::",urls[0]);
                return sendGet(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            jaxrs.setText(s);

        }
    }

    private String sendGet(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type","text/plain");
            conn.setRequestProperty("Content-Type","application/xml");
            //conn.setRequestProperty("charset", "utf-8");
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("Response", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        //thread.start();
    }



    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

   /* private static String read(InputStream instream) {
        StringBuilder sb = null;
        try {
            sb = new StringBuilder();
            BufferedReader r = new BufferedReader(new InputStreamReader(
                    instream));
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                sb.append(line);
            }

            instream.close();

        } catch (IOException e) {
        }
        return sb.toString();

    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
