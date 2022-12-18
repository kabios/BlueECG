package com.example.blueecg;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class DeviceListActivity extends AppCompatActivity {
    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;

    private String receiveBuffer = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        // data
        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);

        // customize a little bit viewport
        Viewport viewport = graph.getViewport();
        viewport.setMinY(0);
        viewport.setMaxY(10);
        viewport.setScrollable(true);

    }


    @Override
    protected void onResume() {
        super.onResume();
        // we're going to simulate real time with thread that append data to the graph

    }





//    private ListView lstvw;
//    private ArrayAdapter aAdapter;
//    private BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
//
//    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.test);
//
//        if (bAdapter == null) {
//            Toast.makeText(getApplicationContext(), "Bluetooth Not Supported", Toast.LENGTH_SHORT).show();
//        } else {
//            Set<BluetoothDevice> pairedDevices = bAdapter.getBondedDevices();
//            ArrayList list = new ArrayList();
//            if (pairedDevices.size() > 0) {
//                for (BluetoothDevice device : pairedDevices) {
//                    String devicename = device.getName();
//                    String macAddress = device.getAddress();
//                    list.add("Name: " + devicename + "MAC Address: " + macAddress);
//                }
//                lstvw = (ListView) findViewById(R.id.deviceList);
//                aAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
//                lstvw.setAdapter(aAdapter);
//            }
//
//
//            lstvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                    String info = ((TextView) v).getText().toString();
//                    String address = info.substring(info.length() - 17);
//                    BluetoothDevice hc06 = bAdapter.getRemoteDevice(address);
//
//                    BluetoothSocket btSocket = null;
//                    int counter = 1;
//                    do {
//                        try {
//                            btSocket = hc06.createRfcommSocketToServiceRecord(mUUID);
//                            btSocket.connect();
//                            if (btSocket.isConnected()) {
//                                Toast.makeText(DeviceListActivity.this, "Connected: " + String.valueOf(hc06.getName()) + " za " + String.valueOf(counter), Toast.LENGTH_SHORT).show();
//                                finish();
//                            }
//                        } catch (IOException e) {
//                            Toast.makeText(DeviceListActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                            e.printStackTrace();
//                        }
//                        counter++;
//                    }while(!btSocket.isConnected() && counter < 4);
//                }
//            });
//        }
//    }
}
