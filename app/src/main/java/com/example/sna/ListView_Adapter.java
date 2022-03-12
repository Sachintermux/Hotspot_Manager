package com.example.sna;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListView_Adapter extends ArrayAdapter<ViewsList> {
    ArrayList<ViewsList> lists = new ArrayList<>();
    Context context;
    public ListView_Adapter( @NonNull Context context, ArrayList<ViewsList> lists ) {
        super(context, R.layout.listview_layout, lists);
        this.lists = lists;
        this.context = context;
    }


    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView( int position, @Nullable View convertView, @NonNull ViewGroup parent ) {
        ViewsList list = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_layout, parent, false);
        }
        TextView deviceName,deviceMac,deviceIp;
        deviceName = convertView.findViewById(R.id.deviceName_txt);
        deviceIp = convertView.findViewById(R.id.deviceIp_txt);
        deviceMac = convertView.findViewById(R.id.deviceMac_txt);
        if (list != null) {
            deviceName.setText("Name_" + position+1);
            deviceIp.setText(list.getIpAddress());
            deviceMac.setText(list.getMacAddress());
        }

return convertView;
    }
}
