package com.example.petlify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class CampDetails extends AppCompatActivity {

    ImageView img,logoofcamp;
    TextView campDate,campTime,campPlace,campName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_details);

        img=(ImageView) findViewById(R.id.map);
        logoofcamp=findViewById(R.id.logo_image);
        campDate=findViewById(R.id.date);
        campTime=findViewById(R.id.time);
        campPlace=findViewById(R.id.venue);
        campName=findViewById(R.id.camp_name);

        Intent intent=getIntent();

        String date=intent.getStringExtra("dateofcamp");
        String logo=intent.getStringExtra("imageofcamp");
        String name=intent.getStringExtra("nameofcamp");
        String time=intent.getStringExtra("timeofcamp");
        String place=intent.getStringExtra("placeofcamp");
        String lat=intent.getStringExtra("latofcamp");
        String lng=intent.getStringExtra("lngofcamp");

        campDate.setText(date);
        campName.setText(name);
        campTime.setText(time);
        campPlace.setText(place);
        Picasso.get().load(logo).into(logoofcamp);


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=null,chooser=null;
                intent=new Intent(Intent.ACTION_VIEW);

                intent.setData(Uri.parse("geo:"+lat+","+lng));

                chooser=Intent.createChooser(intent,"Launch Maps");
                startActivity(chooser);

            }
        });

    }
}