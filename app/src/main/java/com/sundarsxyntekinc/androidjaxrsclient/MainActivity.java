package com.sundarsxyntekinc.androidjaxrsclient;

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

public class MainActivity extends AppCompatActivity {

    TextView jaxrs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find the Text View
        jaxrs = (TextView) findViewById(R.id.jaxrs);



    }

    Thread thread = new Thread(new Runnable(){
        @Override
        public void run() {
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet request = new HttpGet(
                        "http://10.1.16.44:8080/RESTful_Jersey_Hello_World/rest/hello");

                //request.addHeader("Accept", "text/html");
                //	request.addHeader("Accept", "text/xml");
                request.addHeader("Accept", "text/plain");
                HttpResponse response = httpclient.execute(request);
                HttpEntity entity = response.getEntity();
                Log.e("Response-=-=---=-=",response.getEntity().toString());
                InputStream instream = entity.getContent();
                String jaxrsmessage = read(instream);
                jaxrs.setText(jaxrsmessage);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

    @Override
    protected void onStart() {
        super.onStart();

        thread.start();
    }



    private static String read(InputStream instream) {
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

    }


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
