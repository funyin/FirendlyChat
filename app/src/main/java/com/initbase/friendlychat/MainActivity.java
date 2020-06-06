package com.initbase.friendlychat;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MessageAdapter adapter;
    ImageButton add_image,send;
    EditText txt_message;
    TextView image_indctr;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ChildEventListener childEventListener;
    private int picker_request_code =1;
    Uri image_uri =null;
    public ArrayList<SingleMessage> data;
    private SingleMessage newMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("messages");

        recyclerView = findViewById(R.id.recycler);
        add_image = findViewById(R.id.add_image);
        image_indctr = findViewById(R.id.image_indctr);
        txt_message = findViewById(R.id.edit_message);
        send = findViewById(R.id.send);

        data = new ArrayList<>();

        adapter = new MessageAdapter(data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        txt_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence==null||String.valueOf(charSequence).isEmpty()||charSequence==""){
                    send.setEnabled(false);
                }else {
                    send.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                newMessage = dataSnapshot.getValue(SingleMessage.class);
                data.add(newMessage);
                recyclerView.swapAdapter(adapter,false);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addChildEventListener(childEventListener);
    }
    public void sendMessage(View view){
        String message = txt_message.getText().toString();
//        databaseReference.push().setValue(new SingleMessage("me",message,2020));
//        databaseReference.push().setValue("{ name:\"anonymus\" text:"+"\""+message+"\" }");
        image_indctr.setVisibility(View.GONE);
        if (image_uri==null){
//            data.add(new SingleMessage("Funyinoluwa Kashimawo", message,new Date(System.currentTimeMillis()).toString()));
            databaseReference.push().setValue(new SingleMessage("Funyinoluwa Kashimawo", message,new Date(System.currentTimeMillis()).toString()));
        }else{
            databaseReference.push().setValue(new SingleMessage("Funyinoluwa Kashimawo", message,image_uri,new Date(System.currentTimeMillis()).toString()));
            image_uri=null;
        }
        txt_message.setText("");
    }
    public void pickImage(View view){
        Intent imagePicker = new Intent(Intent.ACTION_GET_CONTENT);
        imagePicker.setType("image/jpeg");
        startActivityForResult(Intent.createChooser(imagePicker,"Share Image"),picker_request_code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==picker_request_code) {
            if (resultCode== RESULT_OK) {
                image_uri = data.getData();
                image_indctr.setVisibility(View.VISIBLE);
                Toast.makeText(this,"Image Successfully loaded",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Failed to load Image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
