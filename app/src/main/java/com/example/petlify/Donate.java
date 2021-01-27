package com.example.petlify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Donate extends AppCompatActivity {


    Button btn;
    TextInputEditText  name,age,color,breed,type;
    RadioButton radioButton;
    RadioGroup radioGroup;
    FirebaseFirestore db;
    Button upload;
    CollectionReference animalRef;
    FirebaseAuth mAuth;
    String downloadImageURL;
    Uri mImageUri;
    private StorageReference mStorageRef;
    private  static final int pick_image=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        btn=(Button) findViewById(R.id.submit);
        upload=findViewById(R.id.imageupload);
        radioGroup=(RadioGroup) findViewById(R.id.radio_group);
        name=(TextInputEditText) findViewById(R.id.name_animal);
        age=(TextInputEditText) findViewById(R.id.animal_age);
        color=(TextInputEditText) findViewById(R.id.color);
        breed=(TextInputEditText) findViewById(R.id.breed);
        type=(TextInputEditText) findViewById(R.id.type_animal);

        db=FirebaseFirestore.getInstance();
        animalRef=db.collection("Animals");

        mAuth= FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        String email=currentUser.getEmail();

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    int _id=radioGroup.getCheckedRadioButtonId();
                    radioButton=findViewById(_id);
//                    Animal animal=new Animal(name.getText().toString(),age.getText().toString(),color.getText().toString(),breed.getText().toString(),type.getText().toString(),radioButton.getText().toString());
                Map<String,Object> animal=new HashMap<>();
                animal.put("name",name.getText().toString());
                animal.put("type",type.getText().toString());
                animal.put("age",age.getText().toString());
                animal.put("color",color.getText().toString());
                animal.put("breed",breed.getText().toString());
                animal.put("gender",radioButton.getText().toString());
                animal.put("image",downloadImageURL);
                animal.put("email",email);

                animalRef.add(animal).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Donate.this, "Animal Added", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Donate.this,Userdetails.class);
                        startActivity(i);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Donate.this, "not submitting", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }


    private void uploadFile() {
        if (mImageUri != null) {
            final StorageReference filePath = mStorageRef.child(mImageUri.getLastPathSegment() + ".jpg");

            final UploadTask uploadTask = filePath.putFile(mImageUri);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Donate.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(Donate.this, "Image Uploaded successfully", Toast.LENGTH_SHORT).show();

                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                downloadImageURL = task.getResult().toString();
                                Log.i("URL", downloadImageURL);
                                Toast.makeText(Donate.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });

        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }


    private void openFileChooser() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, pick_image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==pick_image && resultCode==RESULT_OK
                && data!=null){
            mImageUri=data.getData();

            uploadFile();


        }

    }
}