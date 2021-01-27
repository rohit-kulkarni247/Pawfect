package com.example.petlify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Interaction extends AppCompatActivity {

    TextView name,mob,email,add,city,country;
    FirebaseAuth mAuth;
    String TAG = "123";
    FirebaseFirestore db;
    String emailid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interaction);


        name=findViewById(R.id.person_name);
        mob=findViewById(R.id.person_mobile);
        email=findViewById(R.id.person_email);
        add=findViewById(R.id.person_address);
        city=findViewById(R.id.person_city);
        country=findViewById(R.id.person_country);

        mAuth= FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        emailid=currentUser.getEmail();
        Log.d(TAG, "email: "+emailid);
        db=FirebaseFirestore.getInstance();


        Intent intent=getIntent();
        String mainemail=intent.getStringExtra("contactemail");

        DocumentReference doc=db.collection("Users").document(mainemail);

        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {



                    String nameofperson = documentSnapshot.getString("name");
                    String cityofperson = documentSnapshot.getString("city");
                    String addressofperson = documentSnapshot.getString("address");
                    String countryofperson = documentSnapshot.getString("country");
                    String contactofperson = documentSnapshot.getString("contact_no");
                    String emailofperson = documentSnapshot.getString("email");


                    name.setText(nameofperson);
                    mob.setText(contactofperson);
                    city.setText(cityofperson);
                    add.setText(addressofperson);
                    email.setText(emailofperson);
                    country.setText(countryofperson);
                }

            }
        });


    }
}