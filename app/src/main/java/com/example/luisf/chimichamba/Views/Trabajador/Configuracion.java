package com.example.luisf.chimichamba.Views.Trabajador;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luisf.chimichamba.Datos.Trabajador.Trabajador;
import com.example.luisf.chimichamba.Datos.Trabajador.TrabajadorCRUD;
import com.example.luisf.chimichamba.Adaptadores.ExpandableCustomAdapter;
import com.example.luisf.chimichamba.R;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Configuracion extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private Trabajador trabajador;
    private TrabajadorCRUD trabajadorCRUD;

    TextView tvConfNombre, tvConfCorreo;
    private String nombreUsuario, idUsuarioFb, fotoUrlUsuario;

    Button bGuardarTrabajador;
    EditText etProf;
    TextView tvExperiencia;
    TextView tvUbicacion;
    TextView tvUrl;
    SeekBar seekBarExperiencia;
    SeekBar seekBarUbicacion;
    ImageButton bCambiarFoto;
    Switch simpleSwitch;
    int progressExperiencia = 0; //progress of seekbarExp
    int progressUbicacion = 0; //progress of seekbarUbi
    String lat, lon;
    String categoria = "";

    private static final int SELECT_PHOTO = 100;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        bGuardarTrabajador = (Button) findViewById(R.id.bGuardarTrabajador);
        tvUrl = (TextView) findViewById(R.id.tvUrl);
        tvConfNombre = (TextView) findViewById(R.id.tvConfNombre);
        tvConfCorreo = (TextView) findViewById(R.id.tvConfCorreo);
        bCambiarFoto = (ImageButton) findViewById(R.id.ibTakePhoto);
        etProf = (EditText) findViewById(R.id.etProf);
        tvExperiencia = (TextView) findViewById(R.id.tvExperienciaSeekBar);
        tvUbicacion = (TextView) findViewById(R.id.tvUbicacionSeekBar);
        seekBarExperiencia = (SeekBar) findViewById(R.id.seekBar2);
        seekBarUbicacion = (SeekBar) findViewById(R.id.seekBar);
        simpleSwitch = (Switch) findViewById(R.id.switch1);

        trabajadorCRUD = new TrabajadorCRUD(this);
        Bundle bundle = getIntent().getExtras();
        nombreUsuario = bundle.getString("NombreUsuario");
        idUsuarioFb = bundle.getString("IDUsuario");
        fotoUrlUsuario = bundle.getString("FotoUrlUsuario");

        if (trabajadorCRUD.hayTrabajador(idUsuarioFb)) {
            trabajador = trabajadorCRUD.getTrabajador(idUsuarioFb);
            Picasso.with(this).load(trabajador.getUrl_foto()).into(bCambiarFoto);
            tvConfNombre.setText(trabajador.getNombre());
            tvConfCorreo.setText(trabajador.getEmail());
            etProf.setText(trabajador.getProfesion());
        }

        lat = bundle.getString("lat");
        lon = bundle.getString("lon");
        tvExperiencia.setText("0");
        tvUbicacion.setText("0");
        expListView = (ExpandableListView) findViewById(R.id.lvUserFiltro);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableCustomAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        seekBarExperiencia.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressVal, boolean fromUser) {
                progressExperiencia = progressVal;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvExperiencia.setText(String.valueOf(progressExperiencia));
            }
        });

        seekBarUbicacion.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressVal, boolean fromUser) {
                progressUbicacion = progressVal;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvUbicacion.setText(String.valueOf(progressUbicacion));
            }
        });


        bGuardarTrabajador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Obtener toda la informacion
                String correo = "luisfarfanlara@hotmail.com";
                String profesion = etProf.getText().toString();
                int switch1 = 0;
                if (simpleSwitch.isChecked()) {
                    switch1 = 1;
                } else {
                    switch1 = 0;
                }
                String urlFoto = tvUrl.getText().toString();

                Trabajador trabajador1 = new Trabajador(idUsuarioFb, nombreUsuario, correo,
                        profesion, categoria, progressExperiencia, lat, lon,
                        progressUbicacion, switch1, "", fotoUrlUsuario);
                /*Log.d("USERidFb: ", idFb);
                Log.d("USERnombre: ", nombre);
                Log.d("USERprofesion: ", profesion);
                Log.d("USERcategoria: ", categoria);
                Log.d("USERexperiencia: ", String.valueOf(progressExperiencia));
                Log.d("USERlat: ", lat);
                Log.d("USERlon: ", lon);
                Log.d("USERubicacion: ", String.valueOf(progressUbicacion));
                Log.d("USERviajar: ", String.valueOf(switch1));
                Log.d("USERfoto: ", String.valueOf(urlFoto));*/

                trabajadorCRUD.updateTrabajador(trabajador1);
                Toast.makeText(Configuracion.this, "La informacion se guardó correctamente",
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Configuracion.this, MiPerfil.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                intent.putExtra("NombreUsuario", nombreUsuario);
                intent.putExtra("IDUsuario", idUsuarioFb);
                intent.putExtra("FotoUrlUsuario", fotoUrlUsuario);
                startActivity(intent);

            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                categoria = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                Toast.makeText(Configuracion.this, categoria.toString() + " fue seleccionada.",
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });

        bCambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarPermisosStorage();
            }
        });
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Tipo");

        // Adding child data
        List<String> tipo = new ArrayList<String>();
        tipo.add("Tecnologia");
        tipo.add("Contaduria");
        tipo.add("Leyes");
        tipo.add("Vida y Estilo");
        tipo.add("Redes");
        tipo.add("Hogar");
        tipo.add("Otros");

        listDataChild.put(listDataHeader.get(0), tipo);
    }

    public void sobreMiConf(View view) {
        switch (view.getId()) {
            case R.id.bConfSobreMi:
                Intent intent = new Intent(Configuracion.this, SobreMi.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                startActivity(intent);
                break;
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    //CAMARA

    public void validarPermisosStorage() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_EXTERNAL_STORAGE);
            }
        } else {
            iniciarIntentSeleccionarFotos();
        }
    }

    private void iniciarIntentSeleccionarFotos() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    iniciarIntentSeleccionarFotos();
                } else {
                    // Permiso negado
                }
                return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri imagenSeleccionada = imageReturnedIntent.getData();
                    try {
                        InputStream imagenStream = getContentResolver().openInputStream(imagenSeleccionada);
                        Bitmap imagen = BitmapFactory.decodeStream(imagenStream);
                        bCambiarFoto.setImageBitmap(imagen);
                        //urlFoto = imagenSeleccionada.toString();
                        tvUrl.setText(imagenSeleccionada.toString());

                    } catch (FileNotFoundException fnte) {
                        Toast.makeText(this, fnte.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                    return;
                }
        }
    }
}
