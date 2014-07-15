package com.oneroseapps.bartwakemeup.app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;


public class SetJourney extends Activity {
    //station list
    HashMap<String, String> stationList = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //this removed top bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_journey);
        intializeMap();
        //startJourney(null);

    }

    void intializeMap(){
        stationList.put("12th St. Oakland City Center","12TH");
        stationList.put("16th St. Mission (SF)","16TH");
        stationList.put("19th St. Oakland","19TH");
        stationList.put("24th St. Mission (SF)","24TH");
        stationList.put("Ashby (Berkeley)","ASHB");
        stationList.put("Balboa Park (SF)","BALB");
        stationList.put("Bay Fair (San Leandro)","BAYF");
        stationList.put("Castro Valley","CAST");
        stationList.put("Civic Center (SF)","CIVC");
        stationList.put("Coliseum/Oakland Airport","COLS");
        stationList.put("Colma","COLM");
        stationList.put("Concord","CONC");
        stationList.put("Daly City","DALY");
        stationList.put("Downtown Berkeley","DBRK");
        stationList.put("Dublin/Pleasanton","DUBL");
        stationList.put("El Cerrito del Norte","DELN");
        stationList.put("El Cerrito Plaza","PLZA");
        stationList.put("Embarcadero (SF)","EMBR");
        stationList.put("Fremont","FRMT");
        stationList.put("Fruitvale (Oakland)","FTVL");
        stationList.put("Glen Park (SF)","GLEN");
        stationList.put("Hayward","HAYW");
        stationList.put("Lafayette","LAFY");
        stationList.put("Lake Merritt (Oakland)","LAKE");
        stationList.put("MacArthur (Oakland)","MCAR");
        stationList.put("Millbrae","MLBR");
        stationList.put("Montgomery St. (SF)","MONT");
        stationList.put("North Berkeley","NBRK");
        stationList.put("North Concord/Martinez","NCON");
        stationList.put("Orinda","ORIN");
        stationList.put("Pittsburg/Bay Point","PITT");
        stationList.put("Pleasant Hill","PHIL");
        stationList.put("Powell St. (SF)","POWL");
        stationList.put("Richmond","RICH");
        stationList.put("Rockridge (Oakland)","ROCK");
        stationList.put("San Bruno","SBRN");
        stationList.put("San Francisco Int'l Airport","SFIA");
        stationList.put("San Leandro","SANL");
        stationList.put("South Hayward","SHAY");
        stationList.put("South San Francisco","SSAN");
        stationList.put("Union City","UCTY");
        stationList.put("Walnut Creek","WCRK");
        stationList.put("West Dublin","WDUB");
        stationList.put("West Oakland","WOAK");

    }



    public void startJourney(View v){
        //defines
     String origin = "ASHB";
     String dest = "CIVC";
        //URL TO get response
     String url1 = "http://api.bart.gov/api/sched.aspx?cmd=depart&orig="+origin+ "&dest="+dest+ "&time=now&date=now&key=ZQLM-UEUU-ID2Q-DT35";
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
            BartXMLParser b = new BartXMLParser();
            ArrayList timeInfo = (ArrayList) b.parse(content);
            for(int i=0;i<timeInfo.size();i++){
                timeInfo.get(i);
            }
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
        } catch (XmlPullParserException e) {
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
