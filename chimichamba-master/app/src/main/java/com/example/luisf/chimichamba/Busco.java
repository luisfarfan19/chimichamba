package com.example.luisf.chimichamba;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luisf.chimichamba.Views.Busco_Trabajador.BuscoTrabajadorV;
import com.example.luisf.chimichamba.Views.Trabajador.MiPerfil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

public class Busco extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private TextView tvLat, tvLon;
    private String latitud, longitud;
    private String nombreUsuario, idUsuario, fotoUrlUsuario;
    private GoogleApiClient googleApiClient; // Cliente de Google API
    private android.location.Location location;           // Objeto para obtener ubicación
    private final int REQUEST_LOCATION = 1;   // Código de petición para ubicación

    public void buscoChamba(View view) {
        switch (view.getId()) {
            case R.id.bBuscoChamba:
                Intent intent = new Intent(Busco.this, MiPerfil.class);
                intent.putExtra("lat", tvLat.getText().toString());
                intent.putExtra("lon", tvLon.getText().toString());
                intent.putExtra("NombreUsuario", nombreUsuario);
                intent.putExtra("IDUsuario", idUsuario);
                intent.putExtra("FotoUrlUsuario", fotoUrlUsuario);
                startActivity(intent);
                break;
        }
    }

    public void buscoTrabajador(View view) {
        switch (view.getId()) {
            case R.id.bBuscoTrabajador:
                Intent intent = new Intent(Busco.this, BuscoTrabajadorV.class);
                intent.putExtra("lat", tvLat.getText().toString());
                intent.putExtra("lon", tvLon.getText().toString());
                intent.putExtra("NombreUsuario", nombreUsuario);
                intent.putExtra("IDUsuario", idUsuario);
                intent.putExtra("FotoUrlUsuario", fotoUrlUsuario);
                startActivity(intent);
                break;
        }
    }

    // OBTENER LA UBICACION

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busco);
        Bundle bundle = getIntent().getExtras();
        nombreUsuario = bundle.getString("NombreUsuario");
        idUsuario = bundle.getString("IDUsuario");
        fotoUrlUsuario = bundle.getString("FotoUrlUsuario");

        tvLat = (TextView) findViewById(R.id.tvLat);
        tvLon = (TextView) findViewById(R.id.tvLon);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        processLocation();
    }

    private void processLocation() {
        getLocation();

        if (location != null) {
            updateLocationUI();
        }
    }

    private void getLocation() {
        if (isLocationPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        } else {
            requestPermission();
        }
    }

    private boolean isLocationPermissionGranted() {
        int permission = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permission == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(this, "No quisiste dar acceso a tu ubicación", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
    }

    private void updateLocationUI() {
        tvLat.setText(String.valueOf(location.getLatitude()));
        tvLon.setText(String.valueOf(location.getLongitude()));

        latitud = String.valueOf(location.getLatitude());
        longitud = String.valueOf(location.getLongitude());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                if (location != null)
                    updateLocationUI();
                else
                    Toast.makeText(this, "Ubicación no encontrada", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Permisos no otorgados", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        this.location = location;
        Log.d("onLocationChanged", "cambió ubicación");
        updateLocationUI();
    }

}
