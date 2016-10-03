//package com.example.shreeda.atmos2k16;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.design.widget.TextInputLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import App.VolleySingleton;
//
//import static App.ControllerConstant.url;
//import static com.example.shreeda.atmos2k16.R.id.register;
//
//
///**
// * Created by SHREEDA on 28-09-2016.
// */
//
//public class Register extends Fragment {
//
//
//    MultiSelectionSpinner spinner;
//    public static String KEY_SELECTED = "Selected";
//    public static String TAG;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.register, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        spinner = (MultiSelectionSpinner) view.findViewById(R.id.myevents);
//
//        final TextInputLayout Name = (TextInputLayout) view.findViewById(R.id.Name);
//        final TextInputLayout college = (TextInputLayout) view.findViewById(R.id.college);
//        final TextInputLayout emailid = (TextInputLayout) view.findViewById(R.id.emailAd);
//        final TextInputLayout phone = (TextInputLayout) view.findViewById(R.id.phone);
//        final Button register = (Button) view.findViewById(R.id.register);
//
//        String[] array = {"Algomaniac", "Anatomy of Murder", "Case Study", "ChemE Car", "Code Jam", "Court Room", "Cubing Atmosphere", "Designing Industrial Unit", "Enigma", "D.I.Y", "Ground Reality", "Hackathon", "iNavigate", "Junkyard Wars", "Law Follower,", "Maze Perilious", "Mini GP", "nCrypton", "Paper presentation", "Phygure it", "PyBits Conference", "Quadcopter", "Reverse Coding", "Robowars", "Suit up", "Tech expo", "The Bot Shot", "The Sci-Tech Quiz"};
//        spinner.setItems(array);
//
//        Button bt = (Button) view.findViewById(R.id.check);
//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String selected = spinner.getSelectedItemsAsString();
//                Log.e("getSelected", selected);
//            }
//        });
//
//        final String name = Name.getEditText().getText().toString();
//
////        final long phoneno = Long.parseLong((phone.getEditText().getText().toString()));
//          if(name.equalsIgnoreCase(" ")){
//            Name.setHint("Please enter name");
//        }
//        final String College = college.getEditText().getText().toString();
//          if(College.equalsIgnoreCase(" ")){
//            college.setError("Please enter college name");
//        }
//        final String Email = emailid.getEditText().getText().toString();
//          if(isValidEmail(Email)==false){
//            emailid.setError("Enter a valid Email Address");
//        }
//
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//
//            public void onClick(View v) {
//
//                /*final Response.Listener<String> responseListener = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject JSONResponse = new JSONObject(response);
//                            boolean success = JSONResponse.getBoolean("success");
//
//                            if (success) {
//                                Toast.makeText(Register.this, Name + "Registered Succesfully", Toast.LENGTH_LONG).show();
//                                Intent intent = new Intent(Register.this, RegisteredActivity.class);
//                                intent.putExtra(KEY_SELECTED , spinner.getSelectedItemsAsString());
//                                startActivity(intent);
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                } ;
//
//                RegisterRequest registerRequest = new RegisterRequest(name, College, Email, phoneno, responseListener);
//                RequestQueue requestQueue= Volley.newRequestQueue(Register.this);*/
//
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String s) {
////                        try {
//                        JSONObject jsonObject = new JSONObject();
//
////                            boolean error = jsonObject.getBoolean("error");
////
//                        FragmentManager manager;
//                        manager = getActivity().getSupportFragmentManager();
//                        Fragment fragment = RegisteredFragment.newInstance(s);
//                        FragmentTransaction transaction = manager.beginTransaction();
//                        transaction.replace(R.id.container, fragment, "registered");
//                        transaction.commit();
//
//
////                        } catch (JSONException e) {
////                            e.printStackTrace();
////                        }
//                        Log.e(TAG, "onResponse: Data received" + s);
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        Toast.makeText(getActivity(), "Could not complete registration", Toast.LENGTH_LONG).show();
//
//                    }
//                }) {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("tag", "registerUser");
//                        params.put("name", name);
//                        params.put("college", College);
//                        params.put("email", Email);
////                        params.put("phone", String.valueOf(phoneno));
//                        params.put("events", spinner.getSelectedItemsAsString());
//                        return params;
//                    }
//                };
//                VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
//            }
//        });
//    }
//
//    private boolean isValidEmail(String email) {
//        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//
//        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
//        Matcher matcher = pattern.matcher(email);
//        return matcher.matches();
//    }
//
//
//
//
//    public static class Validation {
//
//        // Regular Expression
//        // you can change the expression based on your need
//        private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//        private static final String PHONE_REGEX = "\\d{3}-\\d{7}";
//
//        // Error Messages
//        private static final String REQUIRED_MSG = "required";
//        private static final String EMAIL_MSG = "invalid email";
//        private static final String PHONE_MSG = "###-#######";
//
//        // call this method when you need to check email validation
//        public static boolean isEmailAddress(TextInputLayout textInputLayout, boolean required) {
//            return isValid(textInputLayout, EMAIL_REGEX, EMAIL_MSG, required);
//        }
//
//        // call this method when you need to check phone number validation
//        public static boolean isPhoneNumber(TextInputLayout editText, boolean required) {
//            return isValid(editText, PHONE_REGEX, PHONE_MSG, required);
//        }
//
//        // return true if the input field is valid, based on the parameter passed
//        public  boolean isValid(TextInputLayout editText, String regex, String errMsg, boolean required) {
//
//            String text = editText.getEditText().getText().toString().trim();
//            // clearing the error, if it was previously set by some other values
//            editText.setError(null);
//
//            // text required and editText is blank, so return false
//            if ( required && !hasText(editText) ) return false;
//
//            // pattern doesn't match so returning false
//            if (required && !Pattern.matches(regex, text)) {
//                editText.setError(errMsg);
//                return false;
//            };
//
//            return true;
//        }
//
//        // check the input field has any text or not
//        // return true if it contains text otherwise false
//        public static boolean hasText(TextInputLayout editText) {
//
//            String text = editText.getEditText().getText().toString().trim();
//            editText.setError(null);
//
//            // length 0 means there is no text
//            if (text.length() == 0) {
//                editText.setError(REQUIRED_MSG);
//                return false;
//            }
//
//            return true;
//        }
//    }
//}
//
//
//
//
