package com.led.led;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;


public class DeviceList extends AppCompatActivity {
    //Declaramos Los Componentes
    Button btnVinculados;
    ListView listaDispositivos;
    //Bluetooth
    private BluetoothAdapter myBluetooth = null;
    public static String EXTRA_ADDRESS = "device_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        //Declaramos nuestros componenetes ralcionandolos con los del layout
        btnVinculados = (Button) findViewById(R.id.button);
        listaDispositivos = (ListView) findViewById(R.id.listView);

        //Comprobamos que el dispositivo tiene bluetooth
        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        if (myBluetooth == null) {
            //Mostramos un mensaje, indicando al usuario que no tiene conexión bluetooth disponible
            Toast.makeText(getApplicationContext(), "Bluetooth no disponible", Toast.LENGTH_LONG).show();

            //finalizamos la aplicación
            finish();
        } else if (!myBluetooth.isEnabled()) {

            //Preguntamos al usuario si desea encender el bluetooth
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon, 1);

        }
        listaDispositivosvinculados();
    }

    private void listaDispositivosvinculados() {
        Set<BluetoothDevice> dispVinculados = myBluetooth.getBondedDevices();
        ArrayList<String> list = new ArrayList<>();

        if (dispVinculados.size() > 0) {
            for (BluetoothDevice bt : dispVinculados) {
                list.add(bt.getName() + "\n" + bt.getAddress()); //Obtenemos los nombres y direcciones MAC de los disp. vinculados
            }
        } else {
            Toast.makeText(getApplicationContext(), "No se han encontrado dispositivos vinculados", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listaDispositivos.setAdapter(adapter);
        listaDispositivos.setOnItemClickListener(myListClickListener);

    }

    private final AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // OBTENEMOS LA MAC Y LA MOSTRAMOS EN LA LISTA
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            // HACEMOS EL INTENT PARA EMPEZAR LA OTRA ACTIVITY
            Intent i = new Intent(DeviceList.this, control.class);
            i.putExtra(EXTRA_ADDRESS, address); //MANDAMOS LA MAC A LA SIGUIENTE ACTIVITY
            startActivity(i);
            finish();
        }
    };
}
