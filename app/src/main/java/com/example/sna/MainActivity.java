package com.example.sna;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Runnable mToastRunnable;
    Handler mHandler = new Handler();
    private TextView connectedDevice_txt, valueIs_txt;
    private ListView listView;
    private ImageView imageView;
    private boolean upDate = false;
    private ArrayList<ViewsList> arrayList = new ArrayList<>();
    private int numberOfDevice = 0;
    private FirebaseDatabase database;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectedDevice_txt = findViewById(R.id.connectedDevice_txt);
        valueIs_txt = findViewById(R.id.valueIs_txt);
        listView = findViewById(R.id.listView);
        imageView = findViewById(R.id.imageView);
        database = FirebaseDatabase.getInstance();
        arrayList = getConnectionsAndPopulateLists();
        setListView();
        handle();
        setmHandler();
        sendDataToFireBase(1);
    }


    @Override
    protected void onDestroy() {
        stopHandle();
        super.onDestroy();
    }

    private void handle() {
        mToastRunnable = new Runnable() {
            @Override
            public void run() {
                arrayList = getConnectionsAndPopulateLists();
                setListView();
                System.out.println("Hello");
                mHandler.postDelayed(this, 3000);
            }
        };
    }

    private void setmHandler() {
        mToastRunnable.run();
    }

    private void stopHandle() {
        mHandler.removeCallbacks(mToastRunnable);
    }


    private void setListView() {
        ListView_Adapter adapter = new ListView_Adapter(this, arrayList);
        listView.setAdapter(adapter);
    }

    private ArrayList<ViewsList> getConnectionsAndPopulateLists() {
        ArrayList<ViewsList> list = new ArrayList<>();
        try {
            Process exec = Runtime.getRuntime().exec("ip neighbor");
            exec.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            int i = 0;

            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                String[] split = readLine.split("\\s+");
                if (split.length >= 6 && split[0] != null && !split[0].contains(":") && split[4] != null) {
                    ViewsList viewsList = new ViewsList(split[4], split[0]);
                    if (!arrayList.contains(viewsList)) {
                        upDate = true;
                    }
                    list.add(viewsList);
                    i++;
                }
                if (i != numberOfDevice) {
                    numberOfDevice = i;
                    connectedDevice_txt.setText(numberOfDevice + " Device Connected");
                }
            }

        } catch (Exception e) {
            numberOfDevice = 0;
            connectedDevice_txt.setText(numberOfDevice + " Device Connected");
        }

        return list;
    }

    private void sendDataToFireBase( int value ) {
        DatabaseReference myRef = database.getReference();
      myRef = myRef.child("sna-project");
      myRef.child("State").setValue(value);
    }

    public void imageViewClick( View view ) {
        int value =Integer.parseInt(valueIs_txt.getText().toString());
        if(value == 3) value = 1;
        else value++;
        valueIs_txt.setText(String.valueOf(value));
        Drawable drawable;
        if (value== 1) {
            drawable = getResources().getDrawable(R.drawable.group1);
        } else if (value == 2) {
            drawable = getResources().getDrawable(R.drawable.group2);
        } else {
            drawable = getResources().getDrawable(R.drawable.group3);
        }
        imageView.setImageDrawable(drawable);

        sendDataToFireBase(value);
    }
}