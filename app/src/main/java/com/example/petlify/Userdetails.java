package com.example.petlify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class  Userdetails extends AppCompatActivity {

    private FloatingActionButton total;
    private ExtendedFloatingActionButton adopt,donate,camp;
    TextView fname,email;
    Button logout;
    CircleImageView imgview;

    private boolean open;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        total=(FloatingActionButton) findViewById(R.id.total);

        adopt=(ExtendedFloatingActionButton) findViewById(R.id.adopt);
        donate=(ExtendedFloatingActionButton) findViewById(R.id.donate);
        camp=(ExtendedFloatingActionButton) findViewById(R.id.campaign);

        fname=(TextView) findViewById(R.id.full_name);
        email=(TextView) findViewById(R.id.emailid_text);
        logout=findViewById(R.id.logout);

        imgview=findViewById(R.id.profilephoto);
        mAuth=FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        db=FirebaseFirestore.getInstance();

        db.collection("Users").document(currentUser.getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                fname.setText((CharSequence) documentSnapshot.get("name"));
                Picasso.get().load((String) documentSnapshot.get("image")).into(imgview);
            }
        });
        email.setText(currentUser.getEmail());
        open=false;

        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(open){
                    adopt.setVisibility(View.INVISIBLE);
                    donate.setVisibility(View.INVISIBLE);
                    camp.setVisibility(View.INVISIBLE);
                    open=false;
                }
                else{
                    adopt.setVisibility(View.VISIBLE);
                    donate.setVisibility(View.VISIBLE);
                    camp.setVisibility(View.VISIBLE);
                    open=true;
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(Userdetails.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        camp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Userdetails.this,CampOfPets.class);
                startActivity(i);
            }
        });

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent donate_page=new Intent(Userdetails.this,Donate.class);
                startActivity(donate_page);
            }
        });

        adopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Userdetails.this,Adopte.class);
                startActivity(i);
            }
        });


    }


}
