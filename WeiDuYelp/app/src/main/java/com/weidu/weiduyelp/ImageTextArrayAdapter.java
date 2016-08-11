package com.weidu.weiduyelp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by adimv on 2016/7/29.
 */
public class ImageTextArrayAdapter extends ArrayAdapter {
    public final static String TAG = "ImageTextArrayAdapter";
    int layoutResourceId;
    Context context;
    private List<Restaurant> data;
    private LayoutInflater inflater;
    public ImageTextArrayAdapter(Context context, int layoutResourceId, List<Restaurant> data){
        super(context,layoutResourceId,data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    static class dataHolder{
        ImageView image;
        TextView name;
        TextView cost;
        TextView location;
        TextView food_type;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        dataHolder holder = null;
        if(null == convertView){
            Log.d(TAG,"getView:rowView null:position make new holder "+position);
            convertView = inflater.inflate(layoutResourceId,parent,false);
            holder = new dataHolder();
            holder.image = (ImageView)convertView.findViewById(R.id.image);
            holder.name = (TextView)convertView.findViewById(R.id.name);
            holder.cost = (TextView)convertView.findViewById(R.id.cost);
            holder.food_type = (TextView)convertView.findViewById(R.id.food_type);
            holder.location = (TextView)convertView.findViewById(R.id.location);
            convertView.setTag(holder);//Tags can be used to store data within a view.

        } else {
            Log.d(TAG,"getView:rowView !null-reuse holder:position "+position);
            holder = (dataHolder)convertView.getTag();
        }

        Restaurant restaurant = data.get(position);
        holder.image.setImageResource(restaurant.image);
        holder.location.setText(restaurant.location);
        holder.name.setText(restaurant.name);
        holder.cost.setText(restaurant.cost);
        holder.food_type.setText(restaurant.food_type);

        return convertView;
    }
}
