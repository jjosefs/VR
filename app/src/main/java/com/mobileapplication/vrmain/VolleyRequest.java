package com.mobileapplication.vrmain;

import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class VolleyRequest {
   static RequestQueue queue;
    static JsonObjectRequest jsonObjectRequest;
    public static String url;
    static JSONObject jObj;
    static String TAG = "VolleyRequest";
    private static myParserJSON parser;


    public static void startRequest(MapsActivity activity){
        parser = new myParserJSON(activity.myLocation, activity.destination);

        url = new GetDirections(parser.getLongAndLatLocation(activity.myLocation), parser.getLongAndLatAdress(activity.destination)).mhttpBuild();
        Log.d(TAG, "url: " + url);
    Cache cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024);
    Network network = new BasicNetwork(new HurlStack());
    queue = new RequestQueue(cache,network);
            queue.start();
    jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {

            //Add data to RecyclerView
            try {

                String jString = response.getString("geocoded_waypoints");
                Log.d(TAG, "JSON: " + jString);
                //JSONObject geo = response.getJSONObject("timeSerise");
                /*myParser parser = new myParser(activity);
                parser.getJSOnObj(jObj);
                Log.i(TAG, "Temperature parser: " + parser.getTempArray().toString());
                Log.i(TAG, "Time parser: " + parser.getTimeArray().toString());
                //Log.d(TAG, "Clod parser: " + TimeTemp.get(2));
                activity.initImageBitmaps(parser.getTimeArray(), parser.getTempArray(), parser.getCloudArray());
                parser.clearTemp();
                parser.clearTime();
                parser.clearCloud();*/


            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(TAG, "That didn´t work");
        }

    });
                queue.add(jsonObjectRequest);
                queue.cancelAll(true);


}
}
