package com.example.ernestoramos.apptrip;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import eu.inmite.android.lib.validations.form.FormValidator;
import eu.inmite.android.lib.validations.form.annotations.MinLength;
import eu.inmite.android.lib.validations.form.annotations.NotEmpty;
import eu.inmite.android.lib.validations.form.annotations.RegExp;
import eu.inmite.android.lib.validations.form.callback.SimpleErrorPopupCallback;

import static eu.inmite.android.lib.validations.form.annotations.RegExp.EMAIL;

public class Registrar extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener, View.OnFocusChangeListener {
    //Variables a utilizar
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progeso;

    TextView lblIniciar;
    TextView lblMensaje;

    //AGREGAMOS LA VARIABLE PARA GUARDAR LOS DATOS DE SESION
    private Preference preferencias;

    Button btnIngresar;
    @NotEmpty(messageId = R.string.usuario,order=1)
    @MinLength(value = 4, messageId = R.string.MinValueNombre, order = 2)
    EditText txtNombre;
    @NotEmpty(messageId = R.string.correo,order=3)
    @RegExp(value = EMAIL, messageId = R.string.EmailValido,order=4)
    EditText txtEmail;
    @NotEmpty(messageId = R.string.contraseña, order=5)
    @MinLength(value = 8,messageId =R.string.ContraValida, order = 6)
    EditText txtClave;
    @NotEmpty(messageId = R.string.contraseña, order=6)
    @MinLength(value = 8,messageId =R.string.ContraValida, order = 7)
    EditText txtConfirmarClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle(R.string.SignUp);
        }
        //Inicializando controles
        lblIniciar=findViewById(R.id.lblIniciar);
        lblMensaje=findViewById(R.id.lblMensaje);
        btnIngresar=findViewById(R.id.btnIngresar);
        txtNombre=findViewById(R.id.txtNombre);
        txtClave=findViewById(R.id.txtClaveR);
        txtEmail=findViewById(R.id.txtEmail);
        txtConfirmarClave=findViewById(R.id.txtConfirmarClave);

        txtConfirmarClave.setText(R.string.IngreseConfirmPass);
        txtConfirmarClave.setTextColor(Color.parseColor("#afafaf"));
        txtClave.setText(R.string.IngresePass);
        txtClave.setTextColor(Color.parseColor("#afafaf"));
        //Inicializando variable
        requestQueue= Volley.newRequestQueue(this);
        //Creando los listener
        lblIniciar.setOnClickListener(this);
        btnIngresar.setOnClickListener(this);
        txtClave.setOnFocusChangeListener(this);
        txtConfirmarClave.setOnFocusChangeListener(this);
    }

    private void LlamarWebServices(){
        progeso=new ProgressDialog(this);
        progeso.setMessage("Enviando datos al servidor");
        progeso.show();

        String url="http://tidesignsolutions.com/esperanzayvida/Modelos/registrar.php?nombre="+txtNombre.getText().toString()+"&email="+txtEmail.getText().toString()+"&credencial="+txtClave.getText().toString()+"";
        url.replace(" ","%20");
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progeso.hide();
        lblMensaje.setText(R.string.Error);
    }

    @Override
    public void onResponse(JSONObject response) {
        progeso.hide();
        Toast.makeText(getApplicationContext(), "Registrado con exito",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lblIniciar:
                    Intent act = new Intent(Registrar.this, MainActivity.class);
                    startActivity(act);
                    finish();
                break;
            case R.id.btnIngresar:
                if(FormValidator.validate(this,new SimpleErrorPopupCallback(this))){
                    if(txtClave.getText().toString().equals(txtConfirmarClave.getText().toString())) {
                    LlamarWebServices();
                    }else{
                        lblMensaje.setText(R.string.PassNoCoinciden);
                    }
                }
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean b) {
        switch (v.getId()) {
            case R.id.txtClaveR: {
                String vtext=txtClave.getText().toString();
                if (b) {
                    if(txtClave.getText().toString().contains(getString(R.string.IngresePass))){
                        txtClave.setText("");
                    }else {
                        txtClave.setText(vtext);
                    }
                    txtClave.setTextColor(Color.BLACK);
                }else{
                    if(txtClave.getText().toString().isEmpty()){
                        txtClave.setText(R.string.IngresePass);
                        txtClave.setTextColor(Color.parseColor("#afafaf"));
                    }
                }
            }
                break;
            case R.id.txtConfirmarClave:{
                String vtext = txtConfirmarClave.getText().toString();
                if (b) {
                    if (txtConfirmarClave.getText().toString().contains(getString(R.string.IngreseConfirmPass))) {
                        txtConfirmarClave.setText("");
                    } else {
                        txtConfirmarClave.setText(vtext);
                    }
                    txtConfirmarClave.setTextColor(Color.BLACK);
                } else {
                    if (txtConfirmarClave.getText().toString().isEmpty()) {
                        txtConfirmarClave.setText(R.string.IngreseConfirmPass);
                        txtConfirmarClave.setTextColor(Color.parseColor("#afafaf"));
                    }
                }
            }
        }
    }
}
