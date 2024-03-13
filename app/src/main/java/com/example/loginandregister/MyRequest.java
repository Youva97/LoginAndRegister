package com.example.loginandregister;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyRequest {

    private String email;

    private Context context;
    private RequestQueue queue;

    public MyRequest(Context context, RequestQueue queue) {
        this.context = context;
        this.queue = queue;
    }

    public void register(final String LOGIN, final String EMAIL, final String PASSWORD, final String PASSWORD2, final RetoursPHP rP) {
        String url = "http://192.168.1.167/controller/register.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("res", response);

                try {
                    JSONObject json = new JSONObject(response);
                    boolean error = json.getBoolean("error");
                    if (!error) {
                        rP.toutOK(json,"Felicitation vous avez réussis");
                    } else {
                        rP.pasOK(json.getString("message"));
                        Log.d("PHP", "Passage tp.PasOK dans Register");
                    }
                } catch (JSONException e) {
                    Log.d("PHP", "Passage du catch de register");
                    rP.systemError("Une erreur est survenue, veuillez renouvelez votre essaie");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "error :" + error);
                if ((error instanceof NetworkError)) {
                    rP.systemError("Une erreur s'est produite,\n\r impossible de joindre le serveur");
                } else if (error instanceof VolleyError) {
                    rP.systemError("Erreur serveur");
                }
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("login", LOGIN);
                map.put("email", EMAIL);
                map.put("password", PASSWORD);
                map.put("password2", PASSWORD2);
                Log.d("PHP", "envoie du map:" + map);
                return map;
            }
        };

        queue.add(request);
    }

    public void login(final String LOGIN, final String PASSWORD, final RetoursPHP rP){
        String url = "http://192.168.1.167/controller/login.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("res", response);

                try {
                    JSONObject json = new JSONObject(response);
                    boolean error = json.getBoolean("error");
                    if (!error) {
                        rP.toutOK( json,"Felicitation vous avez réussis a vous connecter");
                        JSONObject user = json.getJSONObject("user");
                        String email = user.getString("email");
                        String pseudo = user.getString("pseudo");
                        int id = user.getInt("id");
                        String password = user.getString("password");
                        Log.d("User Info", "ID: " + id + ", Pseudo: " + pseudo + ", Email: " + email + ", Password: " + password);

                    } else {
                        rP.pasOK(json.getString("message"));
                        Log.d("PHP", "Passage tp.PasOK dans Login");
                    }
                } catch (JSONException e) {
                    Log.d("PHP", "Passage du catch de Login" + e);
                    rP.systemError("Une erreur est survenue, veuillez renouvelez votre essaie");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "error :" + error);
                if ((error instanceof NetworkError)) {
                    rP.systemError("Une erreur s'est produite,\n\r impossible de joindre le serveur");
                } else if (error instanceof VolleyError) {
                    rP.systemError("Erreur serveur");
                }
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("login", LOGIN);
                map.put("password", PASSWORD);
                Log.d("PHP", "envoie du map:" + map);
                return map;
            }

        };
        queue.add(request);
    }

    public interface RetoursPHP {
        void toutOK(JSONObject js, String message) throws JSONException;//tout c'est bien passé

        void pasOK(String message);// erreur de saisie

        void systemError(String message);// gestion  des erreurs systeme

    }
}
