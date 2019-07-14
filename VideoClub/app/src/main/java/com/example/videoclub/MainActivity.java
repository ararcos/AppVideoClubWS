package com.example.videoclub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.videoclub.Modelos.Socio;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    Button btnLogin;
    EditText txtCedula;
    ProgressDialog proceso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private static final String preference = "Sesion";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin=findViewById(R.id.btn_login);
        txtCedula=findViewById(R.id.et_cedula);
        if (verificarLogin()){
            Intent intent = new Intent(this,verPeliculas.class);
            startActivity(intent);
            finish();
        }

        request= Volley.newRequestQueue(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });
    }

    private Boolean verificarLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences(preference,MODE_PRIVATE);

        return sharedPreferences.getBoolean("logeado",false);
    }

    private void cargarWebService() {
        String url="http://192.168.1.7:8080/WSVideoClub/webresources/entity.socio/buscar/"+txtCedula.getText().toString();
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"No Se Logeo"+error.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Socio socio = new Socio();
            socio.setNombre(response.optString("socNombre"));
            socio.setCedula(response.optString("socCedula"));
            socio.setCorreo(response.optString("socCorreo"));
            socio.setDireccion(response.optString("socDireccion"));
            socio.setTelefono(response.optString("socTelefono"));
            socio.setId(response.optInt("socId"));
            guardarSesion(socio.getId(),socio.getCedula());
        Intent intent = new Intent(this,verPeliculas.class);
        startActivity(intent);
        finish();

    }

    private void guardarSesion(Integer id , String cedula) {
        SharedPreferences sharedPreferences = getSharedPreferences(preference,MODE_PRIVATE);
        sharedPreferences.edit().putInt("id",id).apply();
        sharedPreferences.edit().putString("cedula",cedula).apply();
        sharedPreferences.edit().putBoolean("logeado",true).apply();
    }
}
