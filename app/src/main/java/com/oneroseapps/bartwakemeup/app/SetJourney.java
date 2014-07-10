package com.oneroseapps.bartwakemeup.app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class SetJourney extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_journey);


    }

    public void startJourney(View v){
        //defines
        String origin = "ASHB";
        String dest = "CIVC";
        //URL TO get response
        String url1 = "http://api.bart.gov/api/sched.aspx?cmd=arrive&orig="+origin+ "&dest="+dest+ "&time=now&date=now&key=ZQLM-UEUU-ID2Q-DT35";
        //this will call and get response from BART API



       /* URL workingURL;
        try {
            //typecast the url string as URL
            workingURL = new URL(url1);
            URLConnection conn = workingURL.openConnection();
            conn.connect();
            //getting input
            InputStream in = conn.getInputStream();
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = reader.readLine())!=null){
                stringBuilder.append(line);

            }
            Log.d("debug", "debuginputstream!!!" + stringBuilder.toString());

*/


        new execHttpAsync().execute(url1);

        //this will start the new activity
        Intent start = new Intent(getApplicationContext(), TimeLeftActivity.class);
        startActivity(start);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.set_journey, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

private class execHttpAsync extends AsyncTask<String, Void, HttpResponse>
{
    public String resultString;

    @Override
    protected HttpResponse doInBackground(String... params)
    {
        String url = params[0];

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        request.setHeader("Content-Type", "text/xml");
        HttpResponse response = null;
        try {
            response = httpClient.execute(request);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(HttpResponse result)
    {
        StringBuffer returned = new StringBuffer();

        InputStream content = null;
        try {
            content = result.getEntity().getContent();
            BufferedReader rd = new BufferedReader(new InputStreamReader(content, "UTF-8"));
            String line;
            while ((line = rd.readLine()) != null)
            {
                String endOfLine = "";
                returned.append(line + endOfLine);
            }
            content.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



        String retVal = returned.toString();

        try
        {
            String header = retVal.substring(0, 1);
            if (!header.equals("<"))
            {
                retVal = retVal.replace(header, "");
            }
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }

        resultString = returned.toString();
        Log.d("Debug","XXXXXXXXX"+resultString);
    }

}


}
