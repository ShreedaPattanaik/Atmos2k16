package com.example.shreeda.atmos2k16;

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
import android.widget.EditText;
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
import java.util.regex.Pattern;

import App.VolleySingleton;

import static App.ControllerConstant.url;

/**
 * Created by SHREEDA on 02-10-2016.
 */

public class Register2 extends Fragment {

    MultiSelectionSpinner spinner;
    public static String KEY_SELECTED = "Selected";
    public static String TAG;
    TextInputLayout Name,emailid,college, phone;

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


        final Button register = (Button) view.findViewById(R.id.register);

        String[] array = {"Algomaniac", "Anatomy of Murder", "Case Study", "ChemE Car", "Code Jam", "Court Room", "Cubing Atmosphere", "Designing Industrial Unit", "Enigma", "D.I.Y", "Ground Reality", "Hackathon", "iNavigate", "Junkyard Wars", "Law Follower,", "Maze Perilious", "Mini GP", "nCrypton", "Paper presentation", "Phygure it", "PyBits Conference", "Quadcopter", "Reverse Coding", "Robowars", "Suit up", "Tech expo", "The Bot Shot", "The Sci-Tech Quiz"};
        spinner.setItems(array);

//
        final TextInputLayout Name = (TextInputLayout) view.findViewById(R.id.Name);
        final String name = Name.getEditText().getText().toString();
        register.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(Name);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        final TextInputLayout college = (TextInputLayout) view.findViewById(R.id.college);
        final String College = college.getEditText().getText().toString();
        register.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                Validation.hasText(college);
            }
        });

        final TextInputLayout emailid = (TextInputLayout) view.findViewById(R.id.emailAd);
        final String Email = emailid.getEditText().getText().toString();
        register.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.isEmailAddress(emailid, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        final TextInputLayout phone = (TextInputLayout) view.findViewById(R.id.phone);
        register.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                Validation.isPhoneNumber(phone, false);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                       try {
                            JSONObject jsonObject = new JSONObject();

                           boolean error = jsonObject.getBoolean("error");
                            FragmentManager manager;
                            manager = getActivity().getSupportFragmentManager();
                            Fragment fragment = RegisteredFragment.newInstance(s);
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.replace(R.id.container, fragment, "registered");
                            transaction.commit();


                       } catch (JSONException e) {
                           e.printStackTrace();
                        }
                            Log.e(TAG, "onResponse: Data received" + s);
                        }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(), "Could not complete registration", Toast.LENGTH_LONG).show();

                    }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("tag", "registerUser");
                            params.put("name", name);
                            params.put("college", College);
                            params.put("email", Email);
//                        params.put("phone", String.valueOf(phoneno));
                            params.put("events", spinner.getSelectedItemsAsString());
                            return params;
                        }
                    };
                    VolleySingleton.getInstance().getRequestQueue().add(stringRequest);


                }
                else
                    Toast.makeText(getActivity(), "Form contains error", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void submitForm() {
        // Submit your form here. your form is valid
        Toast.makeText(getActivity(), "Succesfully Registered", Toast.LENGTH_LONG).show();

    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.hasText(Name)) ret = false;
        if (!Validation.isEmailAddress(emailid, true)) ret = false;
        if (!Validation.isPhoneNumber(phone, false)) ret = false;

        return ret;



    }
}


   class Validation {

    // Regular Expression
    // you can change the expression based on your need
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "\\d{3}-\\d{7}";

    // Error Messages
    private static final String REQUIRED_MSG = "required";
    private static final String EMAIL_MSG = "invalid email";
    private static final String PHONE_MSG = "Enter 10-digit phone no.";

    public static boolean isEmailAddress(TextInputLayout textInputLayout, boolean required) {
        return isValid(textInputLayout, EMAIL_REGEX, EMAIL_MSG, required);
    }

    public static boolean isPhoneNumber(TextInputLayout editText, boolean required) {
        return isValid(editText, PHONE_REGEX, PHONE_MSG, required);
    }

    public static boolean isValid(TextInputLayout editText, String regex, String errMsg, boolean required) {

        String text = editText.getEditText().getText().toString().trim();
        editText.setError(null);

        if (required && !hasText(editText)) return false;

        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        }


        return true;
    }

    public static boolean hasText(TextInputLayout editText) {

        String text = editText.getEditText().getText().toString().trim();
        editText.setError(null);

        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }

        return true;
    }
}