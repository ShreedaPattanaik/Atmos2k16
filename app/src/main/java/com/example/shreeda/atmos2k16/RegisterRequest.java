package com.example.shreeda.atmos2k16;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SHREEDA on 28-09-2016.
 */

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://bits-atmos.org/register/index.php";
    private Map<String, String> params;


    public RegisterRequest(String name, String college, String email, long phone, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("College", college);
        params.put("email", email);
        params.put("phone", phone + "");
    }



    @Override
    public Map<String, String> getParams() {
        return params;
    }

}