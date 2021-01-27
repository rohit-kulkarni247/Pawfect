//package com.example.petlify;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.util.ArrayList;
//
//public class Campaign extends AppCompatActivity {
//
//
//    private static final String TAG = "MainActivity";
//
//    ArrayList<String> camp_place,camp_date,camp_time,camp_lat,camp_lng;
//
//
//
//
//    private FirebaseFirestore db;
//    private CollectionReference campRef;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_campaign);
//
//        camp_place=new ArrayList<>();
//        camp_date=new ArrayList<>();
//        camp_time=new ArrayList<>();
//        camp_lat=new ArrayList<>();
//        camp_lng=new ArrayList<>();
//
//         db=FirebaseFirestore.getInstance();
//        campRef=db.collection("camp");
//        campRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
//                    Camp camp = doc.toObject(Camp.class);
//
//
////                  String name=camp.getName();
//                    String place=camp.getPlace();
//                    String date = camp.getDate();
//                    String time= camp.getTime();
//                    String lat= camp.getLat();
//                    String lng=camp.getLng();
//
//
//                    //camp_name.add(name);
//                    camp_place.add(place);
//                    camp_date.add(date);
//                    camp_time.add(time);
//                    camp_lat.add(lat);
//                    camp_lng.add(lng);
//                }
//            }
//        });
//    }
//
//
//}
//
