package com.example.petlify;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyHolder extends RecyclerView.Adapter<MyHolder.MyViewHolder> {


    Context c;
    ArrayList<String>name_pet,image_pet;
    ArrayList<String>petName,petType,petAge,gender,color,breed,image,petEmail;
    OnItemClickListener listener;

    @NonNull
    @Override
    public MyHolder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout=LayoutInflater.from(c);
        View view=layout.inflate(R.layout.pet_row,parent,false);
        return new MyViewHolder(view);
    }
    public  MyHolder(Context context, ArrayList<String>name_pet,ArrayList<String> image_pet ,ArrayList<String>petType,ArrayList<String>petAge,ArrayList<String>gender,ArrayList<String>color,ArrayList<String>breed,ArrayList<String>petEmail){
        this.c=context;
        this.name_pet=name_pet;
        this.image_pet=image_pet;
//        this.petName=petName;
        this.petType=petType;
        this.petAge=petAge;
        this.gender=gender;
        this.color=color;
        this.breed=breed;
        this.petEmail=petEmail;
       // this.image=image_pet;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder.MyViewHolder holder, int position) {
        holder.text.setText(name_pet.get(position));
        Picasso.get().load(image_pet.get(position)).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return name_pet.size();
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView text;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            text=itemView.findViewById(R.id.pet_name);
            img=itemView.findViewById(R.id.pet_image);
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
