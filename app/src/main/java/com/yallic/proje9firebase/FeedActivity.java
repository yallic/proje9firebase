package com.yallic.proje9firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    ArrayList<String> mailArray;
    ArrayList<String> commentArray;
    ArrayList<String> imageArray;

    FeedRecyclerAdapter feedRecyclerAdapter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.insta_options_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.upload){

            Intent intent = new Intent(FeedActivity.this,UploadActivity.class);
            startActivity(intent);
            //finish();
        }else if (item.getItemId()==R.id.signout){

            firebaseAuth.signOut();

            Intent intent = new Intent(FeedActivity.this,SignInActivity.class);
            startActivity(intent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);



        mailArray = new ArrayList<>();
        commentArray = new ArrayList<>();
        imageArray = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();



        //RecylerView

        RecyclerView recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedRecyclerAdapter = new FeedRecyclerAdapter(mailArray,imageArray,commentArray);
        recyclerView.setAdapter(feedRecyclerAdapter);



        getData();
    }

    public void getData(){

        CollectionReference collectionReference = firebaseFirestore.collection("Posts");
        collectionReference.orderBy("time", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null){
                    Toast.makeText(FeedActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG);
                }

                if(queryDocumentSnapshots !=null){
                    for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){

                        Map <String,Object > data = documentSnapshot.getData();

                        String comment = (String) data.get("comment");
                        String imageurl = (String) data.get("downloadurl");
                        String mail = (String) data.get("useremail");
                        System.out.println(comment);
                        mailArray.add(mail);
                        imageArray.add(imageurl);
                        commentArray.add(comment);

                        feedRecyclerAdapter.notifyDataSetChanged();

                    }



                }
            }
        });


    }
}











