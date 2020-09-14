package com.example.addfoods;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.firebase.firestore.DocumentReference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class AddMenu extends AppCompatActivity {

    private static final int REQUEST_GET_SINGLE_FILE = 1;
    private ArrayList<Food> foodList = new ArrayList<>();
    private FoodAdapter mAdapter;

    public static final String EXTRA_REPLY = "com.example.android.addfoods.extra.REPLY";

    Button btn_choose, btn_plusmenu;
    EditText etName, etHarga, etDesc;
    String imagePath, mName, mHarga, mDesc, imgUrl;
    ImageView img;
    byte[] selectedImageBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        img = findViewById(R.id.img_food);
        etName = findViewById(R.id.menu_name);
        etHarga = findViewById(R.id.menu_price);
        etDesc = findViewById(R.id.desc);

        btn_choose = findViewById(R.id.btn_choose);
        btn_plusmenu = findViewById(R.id.btn_plusmenu);

        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalery();
            }
        });

        btn_plusmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputFood();
            }
        });
    }

    private void openGalery(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                REQUEST_GET_SINGLE_FILE);
    }


    private void inputFood(){
        mName = etName.getText().toString().trim();
        mHarga = etHarga.getText().toString().trim();
        mDesc = etDesc.getText().toString().trim();

        if (mName.isEmpty()){
            pesan("Nama harus diisi");
            return;
        }
        if (mHarga.isEmpty()){
            pesan("Harga harus diisi");
            return;
        }
        if (mDesc.isEmpty()){
            pesan("Deskripsi harus diisi");
            return;
        }
        saveData();
    }

    private void saveData() {

        final StorageReference up = FirebaseStorage.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()+""+System.currentTimeMillis());

        up.putBytes(selectedImageBytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                up.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imgUrl = uri.toString();

                        //saveDb();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
            }
        });
    }

    /*private void saveDb() {
        FirebaseFirestore.getInstance().collection("Food").add(new Food(mName, mHarga, mDesc, imgUrl)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()){

                    backToMain();
                }
            }
        });
    }*/
    private void backToMain(){
        Intent i = new Intent(AddMenu.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    private void pesan(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }



}
