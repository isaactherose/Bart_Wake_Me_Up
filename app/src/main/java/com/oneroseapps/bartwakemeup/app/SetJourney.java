package com.oneroseapps.bartwakemeup.app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;


public class SetJourney extends Activity {
    HashMap<String, String> stationList = new HashMap<String, String>();
    ArrayList<String> stationListSpinner = new ArrayList<String>();
    String toStation = null;
    String fromStation = null;
    String toStationAbbrev = null;
    String fromStationAbbrev = null;
    final static int RQS_1 = 1;
    public static String alarmTime = null;
    public static PendingIntent pendingIntent = null;
    Intent intentAlarm = null;
    //the spinner for progress
    private ProgressBar spinner;
    Intent timeLeftIntent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //this removed top bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_journey);
        intializeMap();
        //startJourney(null);

        //THIS WILL SET THE TO STATION SPINNER
        Spinner spinner1 = (Spinner) findViewById(R.id.stationToSelector);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.stationSpinnerList, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner1.setAdapter(adapter1);

        //create progress bar
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        //grabs the items selected
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                toStation = adapterView.getSelectedItem().toString();
                toStationAbbrev=convertToHashmap(toStation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        //THIS WILL SET THE FROM STATION SPINNER
        Spinner spinner2 = (Spinner) findViewById(R.id.stationFromSelector);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.stationSpinnerList, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);


        //grabs the items selected
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fromStation = adapterView.getSelectedItem().toString();

                fromStationAbbrev=convertToHashmap(fromStation);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public String convertToHashmap(String toStation) {
        String stationValue = stationList.get(toStation);
        stationValue.toString();
        Log.d("Debug","value of hashmap xxxxxxxx"+ stationValue);
        return stationValue;
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

        String origin = fromStationAbbrev;
        String dest = toStationAbbrev;
        //URL TO get response
        String url1 = "http://api.bart.gov/api/sched.aspx?cmd=depart&orig="+origin+ "&dest="+dest+ "&time=now&date=now&key=ZQLM-UEUU-ID2Q-DT35";
        //this will call and get response from BART API

        new execHttpAsync().execute(url1);
        spinner.setVisibility(View.VISIBLE);

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



    private class execHttpAsync extends AsyncTask<String, Void, String>
    {
        public String resultString;
        public ArrayList<String> startDestTimes;
        @Override
        protected String doInBackground(String... params)
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

            StringBuffer returned = new StringBuffer();

            InputStream content = null;
            try {
                content = response.getEntity().getContent();
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

            return returned.toString();
        }

        @Override
        protected void onPostExecute(String returned)
        {
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
            long timeToAlarm = getStartDestTimes(resultString);
            setAlarm(timeToAlarm);
        }

    }

    //this sets the alarm
    private void setAlarm(long timeToAlarm) {
        Log.d("Debug","XXXXXXXXX"+timeToAlarm);

        //Long time = new GregorianCalendar().getTimeInMillis()+60*1000;
        Long time = new GregorianCalendar().getTimeInMillis()+timeToAlarm;
        long diffHours = time / (60 * 60 * 1000) % 24;
        long diffMin = time / (60 * 1000) % 60;
        Log.d("Debug","Time for alarm"+diffHours+":"+diffMin);
        Intent intentAlarm = new Intent(getBaseContext(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,time,pendingIntent);
        startActivity(timeLeftIntent);
        Log.d("Debug","alarm set for:"+time);
    }

    private long getStartDestTimes(String httpResponse) {
        ArrayList<String> res = new ArrayList<String>();
        String[] response = httpResponse.split("trip");
        for(int i=1;i<response.length-1;i=i+2){
            String startTime = getTimeFromResponse(response[i],"origTimeMin=\"");
            res.add(startTime);
            Log.d("Debug","Train Start time "+startTime);

            String endTime = getTimeFromResponse(response[i],"destTimeMin=\"");
            res.add(endTime);
            Log.d("Debug","Reach time of the train "+endTime);
        }
        return findMinTime(res);
    }

    private String getTimeFromResponse(String response, String pattern){
        int startIndex = response.indexOf(pattern)+13;
        int endIndex = response.indexOf("\"",startIndex);
        String time = response.substring(startIndex, endIndex);
        return time;
    }

    //finds the closest arrival time from the current time
    private long findMinTime(ArrayList<String> res) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
        long min = 0;
        int minIndex=0;
        long diff=0;
        String currentTime=null;
        for(int i=0;i<res.size();i=i+2){
            String time = res.get(i);
            currentTime = dateFormat.format(Calendar.getInstance().getTime());
            Date startDate = null;
            Date curTime = null;
            try {
                startDate = dateFormat.parse(time);
                curTime = dateFormat.parse(currentTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            diff = curTime.getTime() - startDate.getTime();
            if(i==0  && diff>=0)
                min = diff;
            else{
                if(diff<min && diff>=0){
                    min = diff;
                    minIndex = i;
                }
            }

        }
        Date startTime = null;
        Date endTime = null;
        try {
            startTime = dateFormat.parse(currentTime);
            endTime = dateFormat.parse(res.get(minIndex+1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //alarmTime = res.get(minIndex+1);
        spinner.setVisibility(View.GONE);
        timeLeftIntent = new Intent(getApplicationContext(), TimeLeftActivity.class);
        timeLeftIntent.putExtra("DestinationTime",res.get(minIndex+1));

        long x = endTime.getTime() - startTime.getTime()-60*1*1000;
        Log.d("Debug", "XXXXXXXXX " + x);
        return x ;
    }
}
