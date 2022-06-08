package com.mobileapplication.vrmain;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> distance = new ArrayList<>();
    private ArrayList<String> time = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private Context msavedroutes;


    public RecyclerViewAdapter(Context savedRoutes2, ArrayList<String> mDistance) {
        distance = mDistance;
       //time = mtimes;
        msavedroutes = savedRoutes2;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutlist, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //msavedroutes = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        //holder.wtime.setText(weathertime.get(position));
        /*Glide.with(msavedroutes)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.img)*/
        holder.mdistance.setText(distance.get(position));
        holder.mtime.setText("Adress: ");


    }

    @Override
    public int getItemCount() {
        return distance.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mdistance;
        TextView mtime;
        LinearLayout parentLayout;
        CircleImageView img;


        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.profile_image);
            mdistance = itemView.findViewById(R.id.distance);
            mtime = itemView.findViewById(R.id.time);
            parentLayout = itemView.findViewById(R.id.parent_layout2);
        }
    }
}

