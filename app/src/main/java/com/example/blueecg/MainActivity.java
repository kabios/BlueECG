package com.example.blueecg;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {
    private ListView lstvw;
    private ArrayAdapter aAdapter;
    private BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();

    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (bAdapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth Not Supported", Toast.LENGTH_SHORT).show();
        } else {
            Set<BluetoothDevice> pairedDevices = bAdapter.getBondedDevices();
            ArrayList list = new ArrayList();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    //String devicename = device.getName();
                    String macAddress = device.getAddress();
                    //list.add("Name: " + devicename + "MAC Address: " + macAddress);
                    list.add(macAddress);
                }
                lstvw = (ListView) findViewById(R.id.deviceList);
                aAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                lstvw.setAdapter(aAdapter);
            }


            lstvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                   // String info = ((TextView) v).getText().toString();
                    //String address = info.substring(info.length() - 17);
                    BluetoothDevice hc06 = bAdapter.getRemoteDevice(String.valueOf(parent.getItemAtPosition(position)));


                    BluetoothSocket btSocket = null;


                            try {
                                btSocket = hc06.createRfcommSocketToServiceRecord(mUUID);

                            } catch (IOException e) {
                                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        try{
                            btSocket.connect();
                        }catch (IOException connEx)
                        {
                            try {
                                btSocket.close();
                            } catch (IOException closeException) {
                                //Error
                            }
                        }

                    if (btSocket != null && btSocket.isConnected()) {
                        //Socket is connected, now we can obtain our IO streams
                        Toast.makeText(MainActivity.this, "Connected: " + String.valueOf(hc06.getName()), Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(MainActivity.this, DeviceListActivity.class);
                        startActivity(intent1);
                    }

                }
            });
        }
    }
//    private static final Random RANDOM = new Random();
//    private LineGraphSeries<DataPoint> series;
//    private int lastX = 0;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        GraphView graph = (GraphView) findViewById(R.id.graph);
//        // data
//        series = new LineGraphSeries<DataPoint>();
//        graph.addSeries(series);
//        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
//
//        // customize a little bit viewport
//        Viewport viewport = graph.getViewport();
//        viewport.setYAxisBoundsManual(true);
//        viewport.setMinY(0);
//        viewport.setMaxY(10);
//        viewport.setScrollable(true);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.connect_menu, menu);
//        return true;
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch(item.getItemId()) {
//            case R.id.connect:
//                Intent intent1 = new Intent(this, DeviceListActivity.class);
//                startActivity(intent1);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        // we're going to simulate real time with thread that append data to the graph
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                // we add 100 new entries
//                for (int i = 0; i < 100; i++) {
//                    runOnUiThread(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            addEntry();
//                        }
//                    });
//
//                    // sleep to slow down the add of entries
//                    try {
//                        Thread.sleep(600);
//                    } catch (InterruptedException e) {
//                        // manage error ...
//                    }
//                }
//            }
//        }).start();
//    }
//
//    // add random data to graph
//    private void addEntry() {
//        // here, we choose to display max 10 points on the viewport and we scroll to end
//        series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 10d), true, 10);
//    }
//
}