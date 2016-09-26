package com.example.shreeda.atmos2k16.Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.BuildConfig;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import App.ControllerConstant;
import App.VolleySingleton;

import static android.support.multidex.BuildConfig.PACKAGE_NAME;

/**
 * Created by SHREEDA on 26-09-2016.
 */

public class ScheduleUpdateService extends IntentService {

    private ResultReceiver mReceiver;

    public ScheduleUpdateService() {
        super("ScheduleUpdateService");
    }
    EventTableManager eventTableManager;
    @Override
    protected void onHandleIntent(Intent intent) {
//        if (intent != null) {
//            mReceiver = intent.getParcelableExtra(AppConfig.RECEIVER);
//
//        }
        eventTableManager=new EventTableManager(this);
        addDummyData();
        sendRequest();
    }

    private void addDummyData() {
        /*eventTableManager.addEntry(4, "Music", "Carrom",
                "<div class=\"tab-pane active\" id=\"rules_carrom\">\n" +
                        "                                    <p class=\"text\">\n" +
                        "                                        1. There will be only 1 team from the institute comprising of minimum 4 players. \n" +
                        "                                        <br>\n" +
                        "                                        2. There will be 3 singles and 2 doubles based upon the Davis Cup format. \n" +
                        "                                        <br>\n" +
                        "                                        3. All the international rules will be followed as per the carrom federation website. \n" +
                        "                                        <br>\n" +
                        "                                        4. The format will be knockout or round robin based on number of participating teams. \n" +
                        "                                        <br>\n" +
                        "                                        5. The refereeâ€™s decision will be deemed final in any case of controversy and that will stand above any challenge by any team. \n" +
                        "                                    </p>\n" +
                        "                                </div>",
                "[{\"name\":\"Payal Shand\",\"contact\":\"7506732696\"}]",
                "http://www.spjimrsprint.com/images/events/carrom.jpg"
        );
        eventTableManager.addEntry(3, "Music", "Carrom",
                "<div class=\"tab-pane active\" id=\"rules_carrom\">\n" +
                        "                                    <p class=\"text\">\n" +
                        "                                        1. There will be only 1 team from the institute comprising of minimum 4 players. \n" +
                        "                                        <br>\n" +
                        "                                        2. There will be 3 singles and 2 doubles based upon the Davis Cup format. \n" +
                        "                                        <br>\n" +
                        "                                        3. All the international rules will be followed as per the carrom federation website. \n" +
                        "                                        <br>\n" +
                        "                                        4. The format will be knockout or round robin based on number of participating teams. \n" +
                        "                                        <br>\n" +
                        "                                        5. The refereeâ€™s decision will be deemed final in any case of controversy and that will stand above any challenge by any team. \n" +
                        "                                    </p>\n" +
                        "                                </div>",
                "[{\"name\":\"Payal Shand\",\"contact\":\"7506732696\"}]",
                "http://www.spjimrsprint.com/images/events/carrom.jpg"
        );
        eventTableManager.addEntry(5, "Music", "Carrom",
                "<div class=\"tab-pane active\" id=\"rules_carrom\">\n" +
                        "                                    <p class=\"text\">\n" +
                        "                                        1. There will be only 1 team from the institute comprising of minimum 4 players. \n" +
                        "                                        <br>\n" +
                        "                                        2. There will be 3 singles and 2 doubles based upon the Davis Cup format. \n" +
                        "                                        <br>\n" +
                        "                                        3. All the international rules will be followed as per the carrom federation website. \n" +
                        "                                        <br>\n" +
                        "                                        4. The format will be knockout or round robin based on number of participating teams. \n" +
                        "                                        <br>\n" +
                        "                                        5. The refereeâ€™s decision will be deemed final in any case of controversy and that will stand above any challenge by any team. \n" +
                        "                                    </p>\n" +
                        "                                </div>",
                "[{\"name\":\"Payal Shand\",\"contact\":\"7506732696\"}]",
                "http://www.spjimrsprint.com/images/events/carrom.jpg"
        );
        eventTableManager.addEntry(6, "Music", "Carrom",
                "<div class=\"tab-pane active\" id=\"rules_carrom\">\n" +
                        "                                    <p class=\"text\">\n" +
                        "                                        1. There will be only 1 team from the institute comprising of minimum 4 players. \n" +
                        "                                        <br>\n" +
                        "                                        2. There will be 3 singles and 2 doubles based upon the Davis Cup format. \n" +
                        "                                        <br>\n" +
                        "                                        3. All the international rules will be followed as per the carrom federation website. \n" +
                        "                                        <br>\n" +
                        "                                        4. The format will be knockout or round robin based on number of participating teams. \n" +
                        "                                        <br>\n" +
                        "                                        5. The refereeâ€™s decision will be deemed final in any case of controversy and that will stand above any challenge by any team. \n" +
                        "                                    </p>\n" +
                        "                                </div>",
                "[{\"name\":\"Payal Shand\",\"contact\":\"7506732696\"}]",
                "http://www.spjimrsprint.com/images/events/carrom.jpg"
        );*/
    }


    private void sendRequest() {
        final ScheduleTableManager scheduleTableManager = new ScheduleTableManager(this);

        StringRequest request = new StringRequest(Request.Method.POST, ControllerConstant.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("Schedule.class", s);

                /*try {
                    JSONArray array=new JSONArray(s);
                    for(int i=0; i<array.length(); i++){
                        JSONObject a=array.getJSONObject(i);
                        JSONObject venue=new JSONObject(a.getString("venue"));
                        Log.e("Schedule service",venue.getString("id"));
                    }
                } catch (JSONException e) {
                    Log.e("Schedule service",e.toString());
                    e.printStackTrace();
                }*/


                try {
                    Long updatedAt = 0l;
                    JSONArray array = new JSONArray(s);
                    scheduleTableManager.deleteAllEntry();
                    scheduleTableManager.open();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        //todo update updated time
                        if (object.getLong("updated_at") > updatedAt)
                            updatedAt = object.getLong("updated_at");
                        scheduleTableManager.addEntry(object.getInt("Event_id"),
                                object.getString("Round_Name"),
                                object.getString("Event_Name"),
                                object.getLong("Start_time") * 1000,
                                new JSONObject(object.getString("venue")).getString("name")
                        );
                    }
                    scheduleTableManager.close();

                    SharedPreferences preferences=getApplicationContext().getSharedPreferences(PACKAGE_NAME,MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putLong("last" , updatedAt);
                    editor.apply();
                    deliverResultToReceiver(1, "Refreshed");

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Schedule service", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("ScheduleUpdateService", volleyError.toString());
//                deliverResultToReceiver(0, "Check Internet Connection");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> temp = new HashMap<>();
                temp.put("tag", "check_time");
                SharedPreferences prefs=getApplicationContext().getSharedPreferences(PACKAGE_NAME,MODE_PRIVATE);
                temp.put("check_time", "0");
                return temp;
            }
        };

        VolleySingleton.getInstance().getRequestQueue().add(request);
    }

    private void deliverResultToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString("1", message);

        if (mReceiver != null)
            mReceiver.send(resultCode, bundle);
    }


}