package com.example.petlify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class AnimalDetails extends AppCompatActivity {

    TextView name,breed,type,gender,age,color;
    ImageView img;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_details);

        name=(TextView)findViewById(R.id.nameOfAnimal);
        breed=(TextView)findViewById(R.id.breedOfAnimal);
        type=(TextView)findViewById(R.id.typeOfAnimal);
        gender=(TextView)findViewById(R.id.genderOfAnimal);
        age=(TextView)findViewById(R.id.ageOfAnimal);
        color=(TextView)findViewById(R.id.colorOfAnimal);
        btn=findViewById(R.id.adoptAnimal);
        img=findViewById(R.id.img);

        Intent intent=getIntent();
        String namef=intent.getStringExtra("animal_name");
        String breedf=intent.getStringExtra("animal_breed");
        String typef=intent.getStringExtra("animal_type");
        String genderf=intent.getStringExtra("animal_gender");
        String agef=intent.getStringExtra("animal_age");
        String imge=intent.getStringExtra("animal_image");
        String colorf=intent.getStringExtra("animal_color");
        String emailpass=intent.getStringExtra("animal_email");

        name.setText(namef);
        breed.setText(breedf);
        type.setText(typef);
        gender.setText(genderf);
        age.setText(agef);
        color.setText(colorf);
        Picasso.get().load(imge).into(img);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AnimalDetails.this,Interaction.class);
                i.putExtra("contactemail",emailpass);
                startActivity(i);

            }
        });

    }
}