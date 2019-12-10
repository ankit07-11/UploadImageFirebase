package com.example.goolu.upload_image;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private static final int Pick_image_request=1;
    private Button cho, upl;
    private TextView text;
    private EditText edit;
    private ImageView image;
    private ProgressBar probar;
    private Uri mImageUri;
    private StorageReference mStorageReference;
    private DatabaseReference mDataBaseReference;
    private StorageTask mStorageTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cho=findViewById(R.id.choose);
        upl=findViewById(R.id.upload);
        text=findViewById(R.id.textView);
        edit=findViewById(R.id.editText);
        image=findViewById(R.id.imageView);
        probar=findViewById(R.id.progressBar);
        cho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });
        mStorageReference=FirebaseStorage.getInstance().getReference().child("upload");
        mDataBaseReference=FirebaseDatabase.getInstance().getReference("upload");


        upl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mStorageTask!=null && mStorageTask.isInProgress()){
                    Toast.makeText(MainActivity.this,"upload in progress",Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadFile();
                }

            }
        });


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageActivity();

            }
        });


    }



    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, Pick_image_request);
    }




    @Override


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Pick_image_request && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            mImageUri=data.getData();
            image.setImageURI(mImageUri);
        }
    }

    private  void uploadFile(){
        if (mImageUri!=null){
            final StorageReference fileReference=mStorageReference.child(System.currentTimeMillis()+".jpg");

            mStorageTask=fileReference.putFile(mImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()){

                    }
                }
            });

            mStorageTask=fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            probar.setProgress(0);

                        }
                    },5000);

                    Toast.makeText(MainActivity.this,"uploads successful",Toast.LENGTH_SHORT).show();
                    upload mUpload=new upload((edit.getText().toString().trim()),taskSnapshot.getMetadata().getReference().getDownloadUrl().toString()
                    );

                    String UploadId=mDataBaseReference.push().getKey();
                    mDataBaseReference.child(UploadId).setValue(mUpload);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress=(100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    probar.setProgress((int) progress);
                }
            });


        }else{
            Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show();
        }

    }
    private void openImageActivity(){
        startActivity(new Intent(MainActivity.this,imageActivity.class));
    }




}
