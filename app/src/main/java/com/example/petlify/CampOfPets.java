package com.example.petlify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CampOfPets extends AppCompatActivity implements MyCamps.OnItemClickListener {

    RecyclerView recycle;
    MyCamps myCamps;
    ArrayList<String> camp_place,camp_date,camp_time,camp_lat,camp_lng,camp_name,camp_img;
    private FirebaseFirestore db;
    private CollectionReference animalRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_of_pets);

        recycle=(RecyclerView) findViewById(R.id.recycle_list);

        camp_place=new ArrayList<>();
        camp_date=new ArrayList<>();
        camp_time=new ArrayList<>();
        camp_lat=new ArrayList<>();
        camp_lng=new ArrayList<>();
        camp_img=new ArrayList<>();
        camp_name=new ArrayList<>();


        db=FirebaseFirestore.getInstance();
        animalRef=db.collection("camp");

        animalRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Camp camp = documentSnapshot.toObject(Camp.class);


//                  String name=camp.getName();
                    String place=camp.getPlace();
                    String date = camp.getDate();
                    String time= camp.getTime();
                    String lat= camp.getLat();
                    String lng=camp.getLng();
                    String img=camp.getImage();
                    String name=camp.getName();


                  camp_name.add(name);
                    camp_place.add(place);
                    camp_date.add(date);
                    camp_time.add(time);
                    camp_lat.add(lat);
                    camp_lng.add(lng);
                    camp_img.add(img);

//                    Log.d(TAG, "name= "+name);

                }
                myCamps=new MyCamps(CampOfPets.this,camp_img,camp_name);
                recycle.setAdapter(myCamps);
                recycle.setLayoutManager(new LinearLayoutManager(CampOfPets.this));
                myCamps.setOnItemClickListener(CampOfPets.this);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
//        Toast.makeText(this, "I got clicked at "+position, Toast.LENGTH_SHORT).show();
        Intent i=new Intent(CampOfPets.this,CampDetails.class);
        i.putExtra("placeofcamp",camp_place.get(position));
        i.putExtra("dateofcamp",camp_date.get(position));
        i.putExtra("timeofcamp",camp_time.get(position));
        i.putExtra("latofcamp",camp_lat.get(position));
        i.putExtra("nameofcamp",camp_name.get(position));
        i.putExtra("imageofcamp",camp_img.get(position));
        i.putExtra("lngofcamp",camp_lng.get(position));
        startActivity(i);
    }
}