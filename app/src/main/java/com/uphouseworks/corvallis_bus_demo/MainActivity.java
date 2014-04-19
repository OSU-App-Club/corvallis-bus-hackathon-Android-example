package com.uphouseworks.corvallis_bus_demo;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Iterator;
import java.util.TreeSet;


public class MainActivity extends ActionBarActivity implements View.OnClickListener
{

    private Button routes;
    private Button stops;
    private static boolean isWorking;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isWorking = false;
        routes = (Button)findViewById(R.id.routes_button);
        stops = (Button)findViewById(R.id.stops_button);
        routes.setOnClickListener(this);
        stops.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.routes_button:
                retrieveAndShowJson("http://www.corvallis-bus.appspot.com/routes", routes, new String[]{"Name", "AdditionalName", "Description", "Direction"}, "routes", null);
                break;

            case R.id.stops_button:
                retrieveAndShowJson("http://www.corvallis-bus.appspot.com/stops", stops, new String[]{"Name", "Road", "ID"}, "stops", new String[]{"NW Harrison Blvd"});
                break;
        }
    }

    /**
     * ReadJson now calls to an Async process, keeps the main thread free!
     * We listen for the reply to our request through the onResponseReceived callback
     * When the asynctask is done this will be called, and we will see the results on screen!
     * @param url --Target url where our json will be coming from
     */
    public void retrieveAndShowJson(String url, final Button tgtButton, String[] jsonSearchList, final String requestType, final String[] additionalParams)
    {
        if(url != null && !isWorking)
        {
            final String buttonTxt = tgtButton.getText().toString();
            RetrieveJson rt = new RetrieveJson(jsonSearchList, requestType, additionalParams)
            {
                @Override
                public void onResponseReceived(TreeSet result)
                {
                    int size = result.size();
                    String[] treeToArray = new String[size];
                    Iterator i = result.iterator();
                    int counter=0;
                    while(i.hasNext())
                    {
                       treeToArray[counter++]=i.next().toString();
                    }
                    tgtButton.setText(buttonTxt);
                    ListView lv = (ListView)findViewById(R.id.bus_list_view);
                    lv.setAdapter(new BusListAdapter(getApplication(), treeToArray));
                    isWorking = false;
                }
            };
            tgtButton.setText("working...");
            rt.execute(url);
            isWorking = true;
        }
    }

    private class BusListAdapter extends ArrayAdapter<String>
    {
        private String[] givenValues;

        public BusListAdapter(Context context, String[] vals)
        {
            super(context, R.layout.bus_item, vals);
            givenValues = vals;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater li = (LayoutInflater)getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = li.inflate(R.layout.bus_item, null);
            TextView t1 = (TextView)v.findViewById(R.id.bus_text1);

            t1.setText(givenValues[position]);

            //this is a touchy thing here.
            //By default if you override this method it will automatically add the following return statement,
            //HOWEVER, in this case, this is incorrect, we are trying to add a subview, not the original ListView,
            //so we must return our individually inflated View
            //--->return super.getView(position, convertView, parent);
            return v;
        }
    }

}
