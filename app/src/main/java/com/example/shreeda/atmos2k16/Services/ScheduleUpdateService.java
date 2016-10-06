package com.example.shreeda.atmos2k16.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
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
import App.ControllerConstant;
import App.VolleySingleton;
import Helper.SharedPrefDataManager;

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
        eventTableManager = new EventTableManager(this);
        sendEventRequest();
        sendTokenToServer();
    }

    private void sendTokenToServer() {
        if (SharedPrefDataManager.tokenNeedToBeSent(this)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ControllerConstant.url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Log.d("Token resp", s);
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        if (!jsonObject.getBoolean("error")) {
                            SharedPrefDataManager.tokenSentToServer(ScheduleUpdateService.this);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e("Token Error", volleyError.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("tag", "add_token");
                    params.put("token", SharedPrefDataManager.getToken(ScheduleUpdateService.this));
                    return params;
                }
            };
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
        }
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
                                        SharedPrefDataManager.updateEventTime(ScheduleUpdateService.this, array.getJSONObject(i).getLong("updated_at"));
                                    }
                                }
                            } else {
                                if (eventTableManager.addEntry(array.getJSONObject(i)) >= 0) {
                                    SharedPrefDataManager.updateEventTime(ScheduleUpdateService.this, array.getJSONObject(i).getLong("updated_at"));
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
                params.put("updated_at", String.valueOf(SharedPrefDataManager.getEventTime(ScheduleUpdateService.this)));
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
                try {
                    JSONObject object = new JSONObject(s);
                    if (!object.getBoolean("error")) {
                        JSONArray array = object.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            if (jsonObject.has("delete")) {
                                if (jsonObject.getInt("delete") == 1) {
                                    scheduleTableManager.deleteEntry(jsonObject);
                                } else {
                                    if (scheduleTableManager.addEntry(array.getJSONObject(i)) >= 0) {
                                        SharedPrefDataManager.updateScheduleTime(ScheduleUpdateService.this, array.getJSONObject(i).getLong("updated_at"));
                                    }
                                }
                            } else {
                                if (scheduleTableManager.addEntry(array.getJSONObject(i)) >= 0) {
                                    SharedPrefDataManager.updateScheduleTime(ScheduleUpdateService.this, array.getJSONObject(i).getLong("updated_at"));
                                }
                            }

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Schedule service", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("ScheduleUpdateService", volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> temp = new HashMap<>();
                temp.put("tag", "getSchedule");
                temp.put("updated_at", String.valueOf(SharedPrefDataManager.getScheduleTime(ScheduleUpdateService.this)));
                return temp;
            }
        };

        VolleySingleton.getInstance().getRequestQueue().add(request);
    }
    //todo download feeds when done
}