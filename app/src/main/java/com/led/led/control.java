package com.led.led;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by MaXiMuS on 18/03/2017.
 */

public class control extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{
    int cuarto;
    Switch sw_recepcion, sw_procesos, sw_lab, sw_bodega, sw_bano, sw_almacen, sw_oficina, sw_planta_alta, sw_foco;
    ImageButton btn_salir, btn_about;
    String address = null;
    ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //recibimos la mac address obtenida en la actividad anterior
        setContentView(R.layout.control);
        new control.ConnectBT().execute(); //Call the class to connect


        //INSTANCEAMOS BOTONES SWITCH
        sw_recepcion = (Switch) findViewById(R.id.switch_recepcion);
        sw_procesos = (Switch) findViewById(R.id.switch_procesos);
        sw_lab = (Switch) findViewById(R.id.switch_lab);
        sw_bodega = (Switch) findViewById(R.id.switch_bodega);
        sw_bano = (Switch) findViewById(R.id.switch_bano);
        sw_almacen = (Switch) findViewById(R.id.switch_almacen);
        sw_oficina = (Switch) findViewById(R.id.switch_oficina_almacen);
        sw_planta_alta = (Switch) findViewById(R.id.switch_planta_alta);
        sw_foco = (Switch) findViewById(R.id.switch_foco);

        btn_salir = (ImageButton) findViewById(R.id.btn_salir);


        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disconnect();
            }
        });

        sw_recepcion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cuarto = 1;
                if(isChecked){
                    turnOnLed();
                }else{
                    turnOffLed();
                }
            }
        });
        sw_procesos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cuarto = 2;
                if(isChecked){
                    turnOnLed();
                }else{
                    turnOffLed();
                }
            }
        });
        sw_lab.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cuarto = 3;
                if(isChecked){
                    turnOnLed();
                }else{
                    turnOffLed();
                }
            }
        });
        sw_bodega.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cuarto = 4;
                if(isChecked){
                    turnOnLed();
                }else{
                    turnOffLed();
                }
            }
        });

        sw_bano.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cuarto = 5;
                if(isChecked){
                    turnOnLed();
                }else{
                    turnOffLed();
                }
            }
        });
        sw_almacen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cuarto = 6;
                if(isChecked){
                    turnOnLed();
                }else{
                    turnOffLed();
                }
            }
        });
        sw_oficina.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cuarto = 7;
                if(isChecked){
                    turnOnLed();
                }else{
                    turnOffLed();
                }
            }
        });
        sw_planta_alta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cuarto = 8;
                if(isChecked){
                    turnOnLed();
                }else{
                    turnOffLed();
                }
            }
        });
        sw_foco.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cuarto = 9;
                if(isChecked){
                    turnOnLed();
                }else{
                    turnOffLed();
                }
            }
        });

    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    public class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute(){
            progress = ProgressDialog.show(control.this, "Conectando...", "Espera por favor!!");
        }

        @Override
        protected Void doInBackground(Void... devices){
            try{
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//conectamos al dispositivo y chequeamos si esta disponible
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            }
            catch (IOException e) {
                ConnectSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (!ConnectSuccess) {
                msg("Conexión Fallida");
                finish();
            }
            else {
                msg("Conectado");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

    public void turnOnLed() {
        switch (cuarto) {
            case 1:
                if (btSocket != null) {
                    try {
                        //EN ESTA PARTE SE ENVIA AL ARDUINO PARA ENCENDER EL LED "*10|02|3#" <-- ESTA CADENA ES LA
                        // QUE SE ENVIA AL ARDUINO PARA ENCENDER O APAGAR EL LED, ASI COMO PARA SELECCIONAR CUAL LED
                        // SERA EL QUE SE VA A ENCENDER Y APAGAR

                        btSocket.getOutputStream().write("*10|02|3#".toString().getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
                break;
            case 2:
                if (btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("*10|03|3#".toString().getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
                break;
            case 3:
                if (btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("*10|04|3#".toString().getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
                break;
            case 4:
                if (btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("*10|05|3#".toString().getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
                break;
            case 5:
                if (btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("*10|06|3#".toString().getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
                break;
            case 6:
                if (btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("*10|07|3#".toString().getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
                break;
            case 7:
                if (btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("*10|08|3#".toString().getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
                break;
            case 8:
                if (btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("*10|09|3#".toString().getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
                break;
            case 9:
                if (btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("*10|10|3#".toString().getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
                break;
        }
    }

    private void turnOffLed() {
        switch (cuarto){
            case 1:
                if (btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("*10|02|2#".toString().getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
                break;
            case 2:
                if (btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("*10|03|2#".toString().getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
                break;
            case 3:
                if (btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("*10|04|2#".toString().getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
                break;
            case 4:
                if (btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("*10|05|2#".toString().getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
                break;
            case 5:
                if (btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("*10|06|2#".toString().getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
                break;
            case 6:
                if (btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("*10|07|2#".toString().getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
                break;
            case 7:
                if (btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("*10|08|2#".toString().getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
                break;
            case 8:
                if (btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("*10|09|2#".toString().getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
                break;
            case 9:
                if (btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("*10|10|2#".toString().getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
                break;
        }
    }

    public void Disconnect() {
        if (btSocket!=null) {
            try {
                btSocket.close();
            }
            catch (IOException e) {
                msg("Error");
            }
        }
        finish();
        System.exit(0);
    }

    public void msg(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
}
