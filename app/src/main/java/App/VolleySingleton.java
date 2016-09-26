package App;


/**
 * Created by SHREEDA on 26-09-2016.
 */
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class VolleySingleton {
    private static VolleySingleton sInstance=null;
    private RequestQueue requestQueue;
    private VolleySingleton(){
        requestQueue= Volley.newRequestQueue(AppController.getInstance());
    }
    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
    public static VolleySingleton getInstance() {
        if (sInstance == null) {
            sInstance = new VolleySingleton();
        }
        return sInstance;
    }
}
