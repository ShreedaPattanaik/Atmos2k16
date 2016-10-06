package com.example.shreeda.atmos2k16.Services;

import android.app.IntentService;
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
        addDummyData();
    }

    private void addDummyData() {
        EventTableManager tableManager = new EventTableManager(this);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_id", 0);
            jsonObject.put("prize", 2000);
            jsonObject.put("problem_statement", "<h2>Event Description</h2><p>Register in teams of two.</br>A case study will be provided 2 days prior to the competition. <br>Role of Plaintiff: Put up relevant arguments against the concerned firm.<br>Role of Defendant: Defend its maneuver, supported by relevant arguments. <br>The next day, teams choose to be the defendant or the plaintiff, entirely based on first come first basis. (This will be kept hidden from the participants.)On the day of the debate, each team will be paired with another (same side for that particular case). Hence forming teams of four, in a set of 8 for a particular case study. </p> <h2>Format Of Debate</h2> <p>Each contestant speaks for 2 minutes at most (minimum 1 minute), giving the whole set 16 minutes.Followed by a Cross-Questioning session between the two teams lasting 4 minutes.Followed by a Question-Answer session with the audience lasting 2 minutes. </p> <h2>Judging Criteria</h2> <ul> <li>Speaking Skills</li> <li>Argument relevance </li> <li>Spontaneity </li> <li>Body Language </li> <li>Marks to be deducted for a speech less than one minute.</li> <li>The ability of the team members to merge their content(continuity and  &nbsp;&nbsp;&nbsp; consistency of arguments). </li> <li>Marks to be deducted for excessive redundancy. </li> <li>Cross-questioning Ethics . </li> </ul><br> <b>Contact Details: </b><br>Aravind A S : +91 9553325392");
            jsonObject.put("description", "<h2>Event Description</h2><p>Register in teams of two.</br>A case study will be provided 2 days prior to the competition. <br>Role of Plaintiff: Put up relevant arguments against the concerned firm.<br>Role of Defendant: Defend its maneuver, supported by relevant arguments. <br>The next day, teams choose to be the defendant or the plaintiff, entirely based on first come first basis. (This will be kept hidden from the participants.)On the day of the debate, each team will be paired with another (same side for that particular case). Hence forming teams of four, in a set of 8 for a particular case study. </p> <h2>Format Of Debate</h2> <p>Each contestant speaks for 2 minutes at most (minimum 1 minute), giving the whole set 16 minutes.Followed by a Cross-Questioning session between the two teams lasting 4 minutes.Followed by a Question-Answer session with the audience lasting 2 minutes. </p> <h2>Judging Criteria</h2> <ul> <li>Speaking Skills</li> <li>Argument relevance </li> <li>Spontaneity </li> <li>Body Language </li> <li>Marks to be deducted for a speech less than one minute.</li> <li>The ability of the team members to merge their content(continuity and  &nbsp;&nbsp;&nbsp; consistency of arguments). </li> <li>Marks to be deducted for excessive redundancy. </li> <li>Cross-questioning Ethics . </li> </ul><br> <b>Contact Details: </b><br>Aravind A S : +91 9553325392");
            jsonObject.put("image_link", "http://bits-atmos.org/Events/img/courtroom.jpg");
            jsonObject.put("fb_url", "https://www.facebook.com/search/top/?q=the%20court%20room");
            jsonObject.put("type", 1);
            jsonObject.put("tab", "Economics");
            jsonObject.put("event_name", "The Court Room");

            tableManager.addEntry(jsonObject);

            jsonObject.put("event_id", 1);
            tableManager.addEntry(jsonObject);
            jsonObject.put("event_id", 2);
            tableManager.addEntry(jsonObject);
            jsonObject.put("event_id", 3);
            tableManager.addEntry(jsonObject);
            jsonObject.put("event_id", 4);
            tableManager.addEntry(jsonObject);

            jsonObject.put("tab","Computer Science");
            jsonObject.put("event_id", 5);
            tableManager.addEntry(jsonObject);
            jsonObject.put("event_id", 6);
            tableManager.addEntry(jsonObject);
            jsonObject.put("event_id", 7);
            tableManager.addEntry(jsonObject);
            jsonObject.put("event_id", 8);
            tableManager.addEntry(jsonObject);

        } catch (JSONException e) {

        }

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