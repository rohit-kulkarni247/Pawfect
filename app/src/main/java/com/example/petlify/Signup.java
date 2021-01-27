package com.example.petlify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import static com.example.petlify.R.string.default_web_client_id;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    TextInputEditText e, p, c;
    Button btn, login;
    FloatingActionButton google;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference usersRef;
    String KEY_TITLE="email";
    private static final int RC_SIGN_IN =123 ;
    private static final String TAG ="abcd" ;
    private GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        e = (TextInputEditText) findViewById(R.id.emailid);
        p = (TextInputEditText) findViewById(R.id.password);
        c = (TextInputEditText) findViewById(R.id.confirm_password);
        btn = (Button) findViewById(R.id.signup_submit);
        login = (Button) findViewById(R.id.already);
        google=findViewById(R.id.google);

        mAuth = FirebaseAuth.getInstance();




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Signup.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        createRequest();

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });



        btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                String email = e.getText().toString();
                String pass = p.getText().toString();
                String cpass = c.getText().toString();

                if (cpass.equals(pass)) {
                    mAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Map<String,Object> mp=new HashMap<>();
                                        mp.put(KEY_TITLE,email);
                                       db=FirebaseFirestore.getInstance();
                                       db.collection("Users").document(email).set(mp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                           @Override
                                           public void onSuccess(Void aVoid) {

                                               Toast.makeText(Signup.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                                               Intent i = new Intent(Signup.this, LoginActivity.class);
                                               startActivity(i);
                                               finish();
                                           }
                                       });








                                    } else {

                                        Toast.makeText(Signup.this, "Something Went Wrong!!\nPlease Try Again", Toast.LENGTH_SHORT).show();

                                    }


                                }
                            });
                }else{
                    Toast.makeText(Signup.this, "Ooops!! Password Does Not Match", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }



    @Override
    protected void onStart() {
        super.onStart();
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            Intent i=new Intent(Signup.this,Userdetails.class);
            startActivity(i);
        }
    }

    private void createRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(Signup.this, gso);


    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(this," "+data+" "+e.getMessage(),Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        Task<AuthResult> authResultTask = mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();

                            Map<String,Object> mp=new HashMap<>();
                            mp.put(KEY_TITLE,user.getEmail());
                            db=FirebaseFirestore.getInstance();
                            db.collection("Users").document(user.getEmail()).set(mp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Signup.this, "Signup successful", Toast.LENGTH_SHORT).show();
                                    Intent it=new Intent(getApplicationContext(),LoginActivity.class);
                                    startActivity(it);
                                }
                            });




                        } else {

                            Toast.makeText(Signup.this,"Sorry",Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }


}

