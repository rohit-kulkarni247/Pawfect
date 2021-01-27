package com.example.petlify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Source;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    TextInputEditText name,add,city,country,pincode,contactNo;
    Button btn,skip,image_upload,image_choose;
    FirebaseAuth mAuth;
    String TAG = "123";
    FirebaseFirestore db;
    String email;
    String downloadImageURL;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    private  static final int pick_image=1;
    private ProgressBar mProgressBar;
    private ProgressDialog pd;

    private Uri mImageUri;

    @Override
    protected void onStart() {
        super.onStart();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");


        name=(TextInputEditText) findViewById(R.id.name);
        add=(TextInputEditText) findViewById(R.id.address);
        city=(TextInputEditText) findViewById(R.id.City);
        country=(TextInputEditText) findViewById(R.id.country);
        pincode=(TextInputEditText) findViewById(R.id.pincode);
        contactNo=(TextInputEditText) findViewById(R.id.contactno);

        image_upload=findViewById(R.id.imageupload);
        skip=findViewById(R.id.skip);

        btn=(Button) findViewById(R.id.profile_submit);





        mAuth= FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        email=currentUser.getEmail();


        db=FirebaseFirestore.getInstance();



        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Profile.this,Userdetails.class);
                startActivity(i);
                finish();
            }
        });


        image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });




        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d(TAG, "onCreate: "+email);
                String n=name.getText().toString();
                String a=add.getText().toString();
                String ci=city.getText().toString();
                String co=country.getText().toString();
                String p=pincode.getText().toString();
                String con=contactNo.getText().toString();


                DocumentReference doc=db.collection("Users").document(email);

//                Pro pro =new Pro(n,a,ci,co,p,con);

                Map<String,Object> pro=new HashMap<>();
                pro.put("name",n);
                pro.put("address",a);
                pro.put("city",ci);
                pro.put("country",co);
                pro.put("pincode",p);
                pro.put("contact_no",con);
                pro.put("image",downloadImageURL);

                doc.update(pro).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Profile.this, "Profile Data Added Successfully", Toast.LENGTH_SHORT).show();
                        Intent i =new Intent(Profile.this,
                                Userdetails.class);

                        startActivity(i);
                        finish();
                    }
                });




            }
        });



    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile() {
        if (mImageUri != null) {
            final StorageReference filePath = mStorageRef.child(mImageUri.getLastPathSegment() + ".jpg");

            final UploadTask uploadTask = filePath.putFile(mImageUri);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Profile.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(Profile.this, "Image Uploaded successfully", Toast.LENGTH_SHORT).show();

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
                                Toast.makeText(Profile.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
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