package com.example.shreeda.atmos2k16.Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shreeda.atmos2k16.TableManagers.EventTableManager;
import com.example.shreeda.atmos2k16.TableManagers.ScheduleTableManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import App.AppController;
import App.Config;
import App.ControllerConstant;
import App.VolleySingleton;
import Helper.UpdatedTimeManager;

/**
 * Created by SHREEDA on 26-09-2016.
 */

public class ScheduleUpdateService extends IntentService {

    EventTableManager eventTableManager;
    private ResultReceiver mReceiver;

    public ScheduleUpdateService() {
        super("ScheduleUpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        if (intent != null) {
//            mReceiver = intent.getParcelableExtra(AppConfig.RECEIVER);
//
//        }
        eventTableManager = new EventTableManager(this);
//        sendRequest();
        sendEventRequest();
    }

    private void sendEventRequest() {
        final EventTableManager eventTableManager = new EventTableManager(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ControllerConstant.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("Event resp", s);

                try {
                    JSONObject object = new JSONObject(s);
                    if (!object.getBoolean("error")) {
                        JSONArray array = object.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            if (jsonObject.has("delete")) {
                                if (jsonObject.getInt("delete") == 1) {
                                    eventTableManager.deleteEntry(jsonObject);
                                } else {
                                    if (eventTableManager.addEntry(array.getJSONObject(i)) >= 0) {
                                        UpdatedTimeManager.updateEventTime(ScheduleUpdateService.this, array.getJSONObject(i).getLong("updated_at"));
                                    }
                                }
                            } else {
                                if (eventTableManager.addEntry(array.getJSONObject(i)) >= 0) {
                                    UpdatedTimeManager.updateEventTime(ScheduleUpdateService.this, array.getJSONObject(i).getLong("updated_at"));
                                }
                            }

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sendScheduleRequest();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Event Error", volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tag", "getEventDetails");
                params.put("updated_at", String.valueOf(UpdatedTimeManager.getEventTime(ScheduleUpdateService.this)));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void sendScheduleRequest() {
        final ScheduleTableManager scheduleTableManager = new ScheduleTableManager(this);

        StringRequest request = new StringRequest(Request.Method.POST, ControllerConstant.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("Schedule.class", s);


/*
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

                    SharedPreferences preferences = getApplicationContext().getSharedPreferences(Config.LastUpdated, MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putLong("last", updatedAt);
                    editor.apply();
                    deliverResultToReceiver(1, "Refreshed");

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Schedule service", e.toString());
                }*/
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
                temp.put("tag", "getSchedule");
                SharedPreferences preferences = getApplicationContext().getSharedPreferences(Config.LastUpdated, MODE_PRIVATE);
                temp.put("updated_at", "last");
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