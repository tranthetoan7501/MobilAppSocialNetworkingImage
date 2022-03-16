package com.hcmus.mobilappsocialnetworkingimage.fragment;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.api.Distribution;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.hcmus.mobilappsocialnetworkingimage.R;
import com.hcmus.mobilappsocialnetworkingimage.UserInfor;
import com.hcmus.mobilappsocialnetworkingimage.edit_profile;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hcmus.mobilappsocialnetworkingimage.adapter.*;

public class accountFragment extends Fragment implements View.OnClickListener {
    ImageView avatar;
    Button edit_pf;
    TextView username,about;
    RecyclerView recyclerView;
    thumbnailsAdapter thumbnailsAdapter;
    ImageButton all_pictures;
    ImageButton video;
    ImageButton tag;
    FirebaseAuth mAuth;
    UserInfor userInfor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
       avatar = view.findViewById(R.id.avatar);
       username=view.findViewById(R.id.username);
       about=view.findViewById(R.id.description);
        edit_pf =view.findViewById(R.id.edit_pf);
        edit_pf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), edit_profile.class);
                Bundle bundle=new Bundle();
                bundle.putString("uid",mAuth.getUid().toString());
                intent.putExtras(bundle);

                intent.putExtra("userInfor",userInfor);
                startActivity(intent);
            }
        });
//        Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU").into(avatar);
        if (container != null) {
            container.removeAllViews();
        }
        all_pictures = view.findViewById(R.id.all_pictures);
        video = view.findViewById(R.id.video);
        tag = view.findViewById(R.id.tag);
        recyclerView = view.findViewById(R.id.grid);
        video.setOnClickListener(this);
        all_pictures.setOnClickListener(this);
        getData(1);
        return view;
    }
    public void setUserInfo(UserInfor user,FirebaseAuth mAuth){
            this.mAuth=mAuth;
            this.userInfor=user;
            username.setText(userInfor.getUsername());
            about.setText(userInfor.getAbout());
            Picasso.get().load(userInfor.getAvatar()).into(avatar);


//        if(user==null){
//            return;
//        }
//        String id = user.getUid();
//        Uri photoUrl = user.getPhotoUrl();
//
//        DocumentReference doc = document.collection("account").document(id);
//        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        username.setText(document.getString("username"));
//                        about.setText(document.getString("about"));
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });


        //Glide.with(this).load(photoUrl).error(R.drawable.default_avatar);

    }

    void getData(int i){
        List<String> image = new ArrayList<>();
        thumbnailsAdapter = new thumbnailsAdapter(image,getContext(),i);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(thumbnailsAdapter);
        if(i==1){
            image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
            image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
            image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
            image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
            image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
            image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
            image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
            image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
            image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
            image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
            image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
            image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
            image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
            image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
            image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
            image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
            image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
            image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
        }
        else if(i==2){
            image.add("https://firebasestorage.googleapis.com/v0/b/video-app-af9bf.appspot.com/o/Videos%2FVideo_20200812_141638_.mp4?alt=media&token=d87f94e0-6ca3-4de2-856c-b228884185f7");
        }
        thumbnailsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.all_pictures:
                getData(1);
                break;
            case R.id.video:
                getData(2);
                break;
        }
    }
}