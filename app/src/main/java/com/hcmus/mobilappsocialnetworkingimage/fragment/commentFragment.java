package com.hcmus.mobilappsocialnetworkingimage.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hcmus.mobilappsocialnetworkingimage.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hcmus.mobilappsocialnetworkingimage.adapter.commentsAdapter;
import adapter.postsAdapter;
import de.hdodenhof.circleimageview.CircleImageView;

public class commentFragment extends Fragment implements View.OnClickListener{
    commentsAdapter  commentsAdapter;
    ImageButton previous;
    CircleImageView avatar;
    RecyclerView recyclerView;
    EditText comment;
    CircleImageView belowAvatar;
    TextView description;
    TextView date;
    Bundle bundle = new Bundle();
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        avatar = view.findViewById(R.id.avatar);
        recyclerView = view.findViewById(R.id.all_comments);
        previous = view.findViewById(R.id.previous);
        belowAvatar = view.findViewById(R.id.avatar_below);
        comment = view.findViewById(R.id.comment);
        description = view.findViewById(R.id.description);
        previous.setOnClickListener(this);
        bundle = getArguments();
        date = view.findViewById(R.id.date);
        mAuth = FirebaseAuth.getInstance();
        getData();
        return view;
    }

    void getData(){
        database = FirebaseDatabase.getInstance("https://social-media-f92fc-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference postDetails = database.getReference("user_photos/"+bundle.get("user_id")+"/"+bundle.get("post_id"));
        postDetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                description.setText(snapshot.child("caption").getValue().toString());
                date.setText(snapshot.child("date_created").getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference postOwner = database.getReference("user_account_settings");
        postOwner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if(dataSnapshot.getKey().equals(bundle.getString("user_id"))){
                        Picasso.get().load(dataSnapshot.child("profile_photo").getValue().toString()).into(avatar);
                    }
                    if(dataSnapshot.getKey().equals(mAuth.getUid())){
                        Picasso.get().load(dataSnapshot.child("profile_photo").getValue().toString()).into(belowAvatar);
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }

//    void getData(){
//        Picasso.get().load(bundle.get("image").toString()).into(avatar);
//        String str[] = bundle.get("description").toString().split("\n");
//        description.setText(Html.fromHtml("<b>" + str[0]+"</b>" + " " + str[1]));
//        date.setText(bundle.get("date").toString());
//
//        List<String> image = new ArrayList<>();
//        List<String> description = new ArrayList<>();
//        List<String> date = new ArrayList<>();
//        List<String> num_likes = new ArrayList<>();
//
//        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
//        commentsAdapter = new commentsAdapter(getContext(),description,date,num_likes,image);
//        recyclerView.setLayoutManager(linearLayout);
//        recyclerView.setAdapter(commentsAdapter);
//
//        image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
//        description.add("Toàn lé\nAnh em nhóm 5 thánh bú liếm");
//        date.add("27/10/2001");
//        num_likes.add("15 likes");
//
//        image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
//        description.add("Toàn lé\nAnh em nhóm 5 thánh bú liếm");
//        date.add("27/10/2001");
//        num_likes.add("15 likes");
//
//        image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
//        description.add("Toàn lé\nAnh em nhóm 5 thánh bú liếm");
//        date.add("27/10/2001");
//        num_likes.add("15 likes");
//
//        commentsAdapter.notifyDataSetChanged();
//
//    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.previous:
                getActivity().finish();
                break;
        }
    }
}
