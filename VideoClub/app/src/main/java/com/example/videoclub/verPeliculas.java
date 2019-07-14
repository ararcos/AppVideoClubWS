package com.example.videoclub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.videoclub.Modelos.Alquiler;
import com.example.videoclub.Modelos.Socio;
import com.example.videoclub.adapter.AlquilerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class verPeliculas extends AppCompatActivity implements Response.Listener<JSONArray>,Response.ErrorListener {

    TextView mensaje;
    Button btnConsultar;
    RecyclerView recyclerView;
    ArrayList<Alquiler> listAlquiler;
    ProgressDialog progressDialog;
    RequestQueue request;
    JsonArrayRequest jsonArrayRequest;
    String Cedula;
    private static final String preference = "Sesion";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_peliculas);
        Toolbar toolbar = findViewById(R.id.toolvar);
        setSupportActionBar(toolbar);
        btnConsultar=findViewById(R.id.btnConsultar);
        recyclerView=findViewById(R.id.listId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mensaje=findViewById(R.id.et_mensaje);
        request= Volley.newRequestQueue(this);

        listAlquiler=new ArrayList<>();
        Cedula=cargarSocio();
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.menu_cerrar_sesion){
            CerrarSesion();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private String cargarSocio() {
        SharedPreferences sharedPreferences = getSharedPreferences(preference,MODE_PRIVATE);
        return sharedPreferences.getString("cedula","");
    }

    private void cargarWebService() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Consultando...........");
        progressDialog.show();
        String url="http://192.168.1.7:8080/WSVideoClub/webresources/entity.alquiler/"+Cedula;
        jsonArrayRequest=new JsonArrayRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonArrayRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"No Se Puede Conectar"+error.toString(),Toast.LENGTH_LONG).show();
        progressDialog.hide();
    }

    @Override
    public void onResponse(JSONArray response) {
        listAlquiler=new ArrayList<>();
        Alquiler alquiler=null;
        try {
            for (int i=0;i<response.length();i++){
                alquiler=new Alquiler();
                JSONObject jsonObject,objecGenero,objecFormato,objecPelicula;
                jsonObject=response.getJSONObject(i);
                objecPelicula=jsonObject.optJSONObject("pelId");
                objecFormato=objecPelicula.optJSONObject("forId");
                objecGenero=objecPelicula.optJSONObject("genId");
                alquiler.setId(jsonObject.optInt("alqId"));
                alquiler.setCosto(jsonObject.optDouble("alqValor"));
                alquiler.setFechaDesde(jsonObject.optString("alqFechaDesde"));
                alquiler.setFechaHasta(jsonObject.optString("alqFechaHasta"));
                alquiler.setFechaEntrega(jsonObject.optString("alqFechaEntrega"));
                alquiler.getPelicula().setFormato(objecFormato.optString("forNombre"));
                alquiler.getPelicula().setGenero(objecGenero.optString("genNombre"));
                alquiler.getPelicula().setNombre(objecPelicula.optString("pelNombre"));
                if (alquiler.getFechaEntrega().equals("")){
                    listAlquiler.add(alquiler);
                }
            }
            progressDialog.hide();
            AlquilerAdapter alquilerAdapter = new AlquilerAdapter(listAlquiler);
            recyclerView.setAdapter(alquilerAdapter);
            if (listAlquiler.size()==0)
                mensaje.setText("No Tiene Peliculas Que Devolver");
            else
                mensaje.setText("Peliculas No Devueltas");
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"No Se Puede Conectar"+e.toString(),Toast.LENGTH_LONG).show();
            progressDialog.hide();
        }
    }
    private void CerrarSesion() {
        SharedPreferences sharedPreferences = getSharedPreferences(preference,MODE_PRIVATE);
        sharedPreferences.edit().putInt("id",0).apply();
        sharedPreferences.edit().putString("cedula","").apply();
        sharedPreferences.edit().putBoolean("logeado",false).apply();
    }
}
