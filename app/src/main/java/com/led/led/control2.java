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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by MaXiMuS on 25/03/2017.
 */

public class control2 extends AppCompatActivity {
        int cuarto;
        Switch btn_sala, btn_recamara, btn_comedor, btn_bano;
        Button btn_0, btn_90, btn_180;
        String address = null;
        ProgressDialog progress;
        BluetoothAdapter myBluetooth = null;
        BluetoothSocket btSocket = null;
        boolean isBtConnected = false;
        boolean led_sala = false, led_recamara = false, led_comedor = false, led_bano = false;
        static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            Intent newint = getIntent();
            address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //recibimos la mac address obtenida en la actividad anterior
            setContentView(R.layout.control2);
            new com.led.led.control2.ConnectBT().execute(); //Call the class to connect


            //INSTANCEAMOS BOTONES SWITCH
            btn_0= (Button) findViewById(R.id.btn_cero);
            btn_90 = (Button) findViewById(R.id.btn_noventa);
            btn_180 = (Button) findViewById(R.id.btn_cien);

            //BOTON SALIR


            btn_0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btSocket != null) {
                        try {
                            btSocket.getOutputStream().write("A".toString().getBytes());
                        } catch (IOException e) {
                            msg("Error");
                        }
                    }

                }
            });
            btn_90.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btSocket != null) {
                        try {
                            btSocket.getOutputStream().write("B".toString().getBytes());
                        } catch (IOException e) {
                            msg("Error");
                        }
                    }

                }
            });
            btn_180.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btSocket != null) {
                        try {
                            btSocket.getOutputStream().write("C".toString().getBytes());
                        } catch (IOException e) {
                            msg("Error");
                        }
                    }

                }
            });
        }



        public class ConnectBT extends AsyncTask<Void, Void, Void> {
            private boolean ConnectSuccess = true;

            @Override
            protected void onPreExecute(){
                progress = ProgressDialog.show(com.led.led.control2.this, "Conectando...", "Espera por favor!!");
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