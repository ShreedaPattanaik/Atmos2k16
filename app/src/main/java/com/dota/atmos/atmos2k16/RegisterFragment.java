package com.dota.atmos.atmos2k16;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import App.VolleySingleton;

import static App.ControllerConstant.url;


/**
 * Created by SHREEDA on 28-09-2016.
 */

public class RegisterFragment extends Fragment {


    public static String KEY_SELECTED = "Selected";
    public static String TAG;
    MultiSelectionSpinner spinner;
    String name;
    String College;
    String Email;
    String phoneno;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.register, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner = (MultiSelectionSpinner) view.findViewById(R.id.myevents);

        final TextInputLayout Name = (TextInputLayout) view.findViewById(R.id.Name);
        final TextInputLayout college = (TextInputLayout) view.findViewById(R.id.college);
        final TextInputLayout emailid = (TextInputLayout) view.findViewById(R.id.emailAd);
        final TextInputLayout phone = (TextInputLayout) view.findViewById(R.id.phone);
        final Button register = (Button) view.findViewById(R.id.register);
        Name.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Name.getEditText().setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        college.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                college.getEditText().setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        String[] array = {"Algomaniac", "Anatomy of Murder", "Case Study", "ChemE Car", "Code Jam", "Court Room", "Cubing Atmosphere", "Designing Industrial Unit", "Enigma", "D.I.Y", "Ground Reality", "Hackathon", "iNavigate", "Junkyard Wars", "Law Follower,", "Maze Perilious", "Mini GP", "nCrypton", "Paper presentation", "Phygure it", "PyBits Conference", "Quadcopter", "Reverse Coding", "Robowars", "Suit up", "Tech expo", "The Bot Shot", "The Sci-Tech Quiz"};
        spinner.setItems(array);

        Button bt = (Button) view.findViewById(R.id.check);
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

               /* int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
                    progressDialog = new ProgressDialog(getActivity(),
                            R.style.AppTheme_Dark_Dialog);
                } else{
                    progressDialog = new ProgressDialog(getActivity());
                }*/
                int success = 1;
                name = Name.getEditText().getText().toString();
                College = college.getEditText().getText().toString();
                Email = emailid.getEditText().getText().toString();
                phoneno = phone.getEditText().getText().toString();
                if (name.trim().equals("")) {
                    success = 0;
                    Name.getEditText().setError("Empty name");
                }
                if (College.trim().equals("")) {
                    success = 0;
                    college.getEditText().setError("Empty College name");
                }
                if (isEmailValid(Email) == false) {
                    success = 0;
                    emailid.getEditText().setError("invalid email");
                }
                if (phoneno.trim().equals("") || phoneno.length() < 10) {
                    success = 0;
                    phone.getEditText().setError("invalid phone no.");
                }
                if (success == 1) {
                    if (spinner.getSelectedItemsAsString().equals("")) {
                        //Log.e("empty",spinner.getSelectedItemsAsString());
                        Toast.makeText(getActivity(), "Please select event", Toast.LENGTH_LONG).show();
                        spinner.performClick();
                    } else {
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Registering...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        SendRequest();
                    }

                }

            }
        });
    }

    private void SendRequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    Log.e("response", object.toString());
                    if (object.getBoolean("error")) {
                        progressDialog.cancel();
                        Toast.makeText(getActivity(), object.getString("error_msg"), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "registered", Toast.LENGTH_LONG).show();
                        progressDialog.cancel();
                        FragmentManager manager;
                        manager = getActivity().getSupportFragmentManager();
                        Fragment fragment;
                        Log.e("Register", spinner.getSelectedItemsAsString());
                        fragment = OnRegisteredFragment.newInstance(object.getString("events"), object.getString("id"), object.getString("email"));
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.container, fragment, "registered");
                        transaction.commit();
//{"id":"ATMH2008","college":"rfg","phone":"1234567890","registration":1,"error":false,"email":"r@g.v","name":"ffh"} Response on success

                    }

                    Log.e(TAG, "onResponse: Data received" + object.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.cancel();
                //Toast.makeText(getActivity(), "Could not complete registration", Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "registerUser");
                params.put("name", name);
                params.put("college", College);
                params.put("email", Email);
                params.put("phone", String.valueOf(phoneno));
                params.put("events", spinner.getSelectedItemsAsString());
                Log.e("sending", params.toString());
                return params;
            }
        };
        VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}




