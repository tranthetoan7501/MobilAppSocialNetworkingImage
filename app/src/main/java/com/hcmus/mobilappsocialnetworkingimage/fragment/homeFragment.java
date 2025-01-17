package com.hcmus.mobilappsocialnetworkingimage.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
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
import com.hcmus.mobilappsocialnetworkingimage.activity.editProfileActivity;
import com.hcmus.mobilappsocialnetworkingimage.activity.postStory;
import com.hcmus.mobilappsocialnetworkingimage.activity.shareActivity;
import com.hcmus.mobilappsocialnetworkingimage.activity.storiesActivity;
import com.hcmus.mobilappsocialnetworkingimage.adapter.postsAdapter;
import com.hcmus.mobilappsocialnetworkingimage.model.likeModel;
import com.hcmus.mobilappsocialnetworkingimage.model.postModel;
import com.hcmus.mobilappsocialnetworkingimage.adapter.*;
import com.hcmus.mobilappsocialnetworkingimage.photoEditor.EditImageActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import okhttp3.internal.cache.DiskLruCache;


public class homeFragment extends Fragment {
    RecyclerView stories;
    storiesAdapter storiesAdapter;
    RecyclerView posts;
    postsAdapter postsAdapter;
    ImageButton upItemBtn;
    RecyclerView post_recyclerView;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    ImageButton add_str;

    Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance("https://social-media-f92fc-default-rtdb.asia-southeast1.firebasedatabase.app/");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        stories = view.findViewById(R.id.stories);
        posts = view.findViewById(R.id.posts);
        upItemBtn = view.findViewById(R.id.add_button);
        post_recyclerView = view.findViewById(R.id.posts);
        add_str=view.findViewById(R.id.add_story);
        add_str.setOnClickListener(view1 -> {
            openDialogStory();
        });
        getDataStories();
        getDataPosts();
        upItemBtn.setOnClickListener(v -> openDialog());
        context=this.getContext();
        return view;
    }

    private void openDialog() {
        final Dialog dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.up_item_fragment);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        upItemBtn.getLocationOnScreen(new int[2]);
        wlp.gravity = Gravity.TOP | Gravity.LEFT;
        wlp.x = upItemBtn.getLeft() - upItemBtn.getWidth() / 2;
        wlp.y = upItemBtn.getTop() + upItemBtn.getHeight() / 2;
        window.setAttributes(wlp);
        dialog.show();

        LinearLayout add_post = dialog.findViewById(R.id.post_layout);
        LinearLayout add_story=dialog.findViewById(R.id.story_layout);

        add_post.setOnClickListener(v -> {
            dialog.dismiss();
            openDialogPost();
        } );

        add_story.setOnClickListener(v->{
            dialog.dismiss();
            openDialogStory();

        });
    }

    private void openDialogPost() {
        Intent intent = new Intent(getActivity(), shareActivity.class);
        startActivity(intent);
    }
    private void openDialogStory(){
        Intent intent = new Intent(getActivity(), postStory.class);
        startActivity(intent);

    }
    void getDataStories(){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://social-media-f92fc-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myFollowing = database.getReference("following/"+mAuth.getUid());
        myFollowing.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> key = new ArrayList<>();
                key.add(mAuth.getUid());
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    key.add(snapshot.getKey());
                }
                DatabaseReference databaseReference=database.getReference("user_stories");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshotsnapshot) {
                        FirebaseDatabase database1= FirebaseDatabase.getInstance("https://social-media-f92fc-default-rtdb.asia-southeast1.firebasedatabase.app/");
                        DatabaseReference databaseReference1=database1.getReference("user_account_settings");
                        databaseReference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<List<String>> image = new Vector<>();
                                Vector<String> name = new Vector<>();
                                Vector<String>  avt=new Vector<>();
                                List<String> currentUser=new Vector<>();
                                LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                                storiesAdapter = new storiesAdapter(currentUser,image,name,avt,getContext());
                                stories.setAdapter(storiesAdapter);
                                stories.setLayoutManager(linearLayout);
                                for (DataSnapshot snapshot : dataSnapshotsnapshot.getChildren()) {
                                    for(DataSnapshot snapshot1: snapshot.getChildren()) {
                                        if(key.contains(snapshot.getKey())){
                                        if (!name.contains(snapshot.getKey())) {
                                            name.add(snapshot.getKey());
                                            Vector<String> temp = new Vector<>();
                                            temp.add(snapshot1.getValue().toString());
                                            image.add(temp);
                                        } else {
                                            image.get(name.indexOf(snapshot.getKey())).add(snapshot1.getValue().toString());
                                        }
                                    }
                                    }

                                }
                                int temp=name.indexOf(mAuth.getUid());

                                for (int i=0;i<name.size();i++){
                                    avt.add(dataSnapshot.child(name.get(i)).child("profile_photo").getValue().toString());
                                    name.set(i,dataSnapshot.child(name.get(i)).child("username").getValue().toString());
                                }
                                if(temp>-1){
                                    Collections.swap(name,temp,0);
                                    Collections.swap(avt,temp,0);
                                    Collections.swap(image,temp,0);
                                    currentUser.add(name.get(0));
                                }
                                storiesAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//
//        name.add("Sơn Thanh");
//        name.add("Vinh Quang");
//        image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
//        image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
//        name.add("Sơn Thanh");
//        name.add("Vinh Quang");
//        image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
//        image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
//        name.add("Sơn Thanh");
//        name.add("Vinh Quang");
//        image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");
//        image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAkfPHSBKmkBxQOjAQPB3jvYaBaQ9a6bh_rA&usqp=CAU");


    }
    void getDataPosts(){
        List<postModel> post = new ArrayList<>();
        postsAdapter = new postsAdapter(post,getContext());
        post_recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        post_recyclerView.setAdapter(postsAdapter);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://social-media-f92fc-default-rtdb.asia-southeast1.firebasedatabase.app/");
                DatabaseReference myFollowing = database.getReference("following/"+mAuth.getUid());
                myFollowing.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<String> key = new ArrayList<>();
                        key.add(mAuth.getUid());
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            key.add(snapshot.getKey());
                        }

                            DatabaseReference myPosts = database.getReference();
                           myPosts.addValueEventListener(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                   post.clear();
                                   for(DataSnapshot data : snapshot.child("user_photos").getChildren()){
                                       if(key.contains(data.getKey())){
                                           for(DataSnapshot p : data.getChildren()){
                                               ArrayList<likeModel> likes = new ArrayList<>();
                                               for(DataSnapshot j : p.child("likes").getChildren()){
                                                   likes.add(j.getValue(likeModel.class));
                                               }
                                               postModel model = new postModel(p.child("caption").getValue().toString(), likes,p.child("date_created").getValue().toString(),(ArrayList<String>) p.child("image_paths").getValue(),p.child("user_id").getValue().toString(),p.child("post_id").getValue().toString());
                                               //System.out.println(p.child("post_id").getValue().toString()+"----");
                                               post.add(model);
                                           }
                                       }
                                   }
                                   List<postModel> remove = new ArrayList<postModel>();
                                   for(DataSnapshot data : snapshot.child("hide_post/"+mAuth.getUid()).getChildren()){

                                       for(postModel p : post){
                                           if(p.getPost_id().equals(data.getKey())){
                                               remove.add(p);
                                           }
                                       }
                                   }
                                   post.removeAll(remove);
                                   postsAdapter.notifyDataSetChanged();
                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError error) {

                               }
                           });
                        //DatabaseReference hide = database.getReference().child("hide_post/"+mAuth.getUid());
                       /* hide.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                List<postModel> remove = new ArrayList<postModel>();
                                for(DataSnapshot data : snapshot.getChildren()){

                                        for(postModel p : post){
                                            if(p.getPost_id().equals(data.getKey())){
                                                remove.add(p);
                                            }
                                        }
                                }
                                post.removeAll(remove);
                                postsAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });*/
                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }
}