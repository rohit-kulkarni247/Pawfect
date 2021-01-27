package com.example.petlify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyCamps extends RecyclerView.Adapter<MyCamps.MyViewHolder> {

    Context c;
    ArrayList<String> camp_img,camp_date,camp_time,camp_lat,camp_lng,camp_name;
    OnItemClickListener listener;

    @NonNull
    @Override
    public MyCamps.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout=LayoutInflater.from(c);
        View view=layout.inflate(R.layout.camp_row,parent,false);
        return new MyViewHolder(view);
    }

    public MyCamps(Context context,ArrayList<String> camp_img,ArrayList<String>camp_name){//,camp_name
        this.c=context;
        this.camp_img=camp_img;
        this.camp_name=camp_name;
    }

    @Override
    public void onBindViewHolder(@NonNull MyCamps.MyViewHolder holder, int position) {
        holder.text.setText(camp_name.get(position));
        Picasso.get().load(camp_img.get(position)).into(holder.img);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return camp_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView text;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            text=itemView.findViewById(R.id.camp_name);
            img=itemView.findViewById(R.id.camp_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    listener.onItemClick(position);
                }
            });
        }
    }
}
