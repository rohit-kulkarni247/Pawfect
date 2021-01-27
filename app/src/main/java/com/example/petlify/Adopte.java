package com.example.petlify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Adopte extends AppCompatActivity implements MyHolder.OnItemClickListener {

    RecyclerView recycle;
    MyHolder myHolder;
    ArrayList<String> petName,petType,petAge,gender,color,breed,image,pet_name,pet_image,pet_email;
    private FirebaseFirestore db;
    private CollectionReference animalRef;
    String TAG="123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopte);

        recycle=(RecyclerView) findViewById(R.id.recycle_list);
        petAge=new ArrayList<>();

        petType=new ArrayList<>();
        gender=new ArrayList<>();
        color=new ArrayList<>();
        breed=new ArrayList<>();

        pet_image=new ArrayList<>();
        pet_name=new ArrayList<>();
        pet_email=new ArrayList<>();




        //getDetails();



        db=FirebaseFirestore.getInstance();
        animalRef=db.collection("Animals");

        animalRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Animal animal=documentSnapshot.toObject(Animal.class);

                    String name=animal.getName();
                    String age=animal.getAge();
                    String color1=animal.getColor();
                    String breed1=animal.getBreed();
                    String type=animal.getType();
                    String gen=animal.getGender();
                    String image=animal.getImage();
                    String email=animal.getEmail();

                    pet_name.add(name);
                    petAge.add(age);
                    color.add(color1);
                    breed.add(breed1);
                    petType.add(type);
                    gender.add(gen);
                    pet_image.add(image);
                    pet_email.add(email);

                    Log.d(TAG, "img "+image);

                }

                myHolder=new MyHolder(Adopte.this,pet_name,pet_image,petType,petAge,gender,color,breed,pet_email);
                recycle.setAdapter(myHolder);
                recycle.setLayoutManager(new LinearLayoutManager(Adopte.this));
                myHolder.setOnItemClickListener(Adopte.this);
            }
        });




    }

    @Override
    public void onItemClick(int position) {
       // Toast.makeText(this, "Card was clicked at pos: "+Integer.toString(position), Toast.LENGTH_SHORT).show();
        Intent i=new Intent(Adopte.this,AnimalDetails.class);
        i.putExtra("animal_name",pet_name.get(position));
        i.putExtra("animal_age",petAge.get(position));
        i.putExtra("animal_color",color.get(position));
        i.putExtra("animal_breed",breed.get(position));
        i.putExtra("animal_type",petType.get(position));
        i.putExtra("animal_gender",gender.get(position));
        i.putExtra("animal_image",pet_image.get(position));
        i.putExtra("animal_email",pet_email.get(position));
        startActivity(i);
    }

//    private void getDetails() {
//
//    }
}