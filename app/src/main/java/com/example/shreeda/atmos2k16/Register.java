package com.example.shreeda.atmos2k16;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.app.ActionBarActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import  com.example.shreeda.atmos2k16.MultiSelectionSpinner;

import java.util.Map;

import App.AppController;
import App.VolleySingleton;

import static App.ControllerConstant.url1;


/**
 * Created by SHREEDA on 28-09-2016.
 */

public class Register extends AppCompatActivity {


     MultiSelectionSpinner spinner;
    public static String KEY_SELECTED = "Selected";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

         spinner = (MultiSelectionSpinner) findViewById(R.id.myevents);

        final TextInputLayout Name = (TextInputLayout) findViewById(R.id.Name);
        final TextInputLayout college = (TextInputLayout) findViewById(R.id.college);
        final TextInputLayout emailid = (TextInputLayout) findViewById(R.id.emailAd);
        final TextInputLayout phone = (TextInputLayout) findViewById(R.id.phone);
        Button register = (Button) findViewById(R.id.register);

        String[] array = {"Algomaniac", "Anatomy of Murder", "Case Study", "ChemE Car", "Code Jam", "Court Room", "Cubing Atmosphere", "Designing Industrial Unit", "Enigma", "D.I.Y","Ground Reality", "Hackathon","iNavigate","Junkyard Wars","Law Follower,", "Maze Perilious", "Mini GP","nCrypton","Paper presentation","Phygure it","PyBits Conference","Quadcopter","Reverse Coding","Robowars","Suit up","Tech expo","The Bot Shot","The Sci-Tech Quiz"};
        spinner.setItems(array);

        Button bt = (Button) findViewById(R.id.check);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected = spinner.getSelectedItemsAsString();
                Log.e("getSelected", selected);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                final String name = Name.getEditText().getText().toString();
                final String College = college.getEditText().getText().toString();
                final String Email = emailid.getEditText().getText().toString();
                final int phoneno = Integer.parseInt(phone.getEditText().getText().toString());



                /*final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject JSONResponse = new JSONObject(response);
                            boolean success = JSONResponse.getBoolean("success");

                            if (success) {
                                Toast.makeText(Register.this, Name + "Registered Succesfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Register.this, RegisteredActivity.class);
                                intent.putExtra(KEY_SELECTED , spinner.getSelectedItemsAsString());
                                startActivity(intent);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } ;

                RegisterRequest registerRequest = new RegisterRequest(name, College, Email, phoneno, responseListener);
                RequestQueue requestQueue= Volley.newRequestQueue(Register.this);*/

                StringRequest stringRequest=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                       try{
                           JSONObject JsonResponse = new JSONObject();
                           boolean success = JsonResponse.getBoolean("success");


                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Register.this, "Could not complete registration", Toast.LENGTH_LONG).show();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        return super.getParams();
                    }
                };
                VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
            }
        });


    }
}
