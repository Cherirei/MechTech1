package com.example.mechtech;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {

    private View view;
    FirebaseFirestore fStore;
    RecyclerView recyclerView;
    AdapterUser adapterUser;
    List<UserHelperClass>userList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_user, container, false);

        recyclerView=view.findViewById(R.id.recycleView_user);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fStore = FirebaseFirestore.getInstance();

        //init user list

        userList=new ArrayList<>();

        //getAll user
        getAllUsers();


        return view;
    }

    private void getAllUsers() {
        //get current users
        final FirebaseUser fUser= FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseFirestore fStore=FirebaseFirestore.getInstance();

        //get path of database named "Users" containing users info
        DocumentReference ref= fStore.collection("Users").document();
        //get all data from path
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userList.clear();
//                for (DataSnapshot dataSnapshot: documentSnapshot.getData()){
//                    UserHelperClass userHelperClass=dataSnapshot.getValue(UserHelperClass.class);
//
//                    //get all users except the currently signed in user
//                    if (!userHelperClass.getUid().equals(fUser.getUid())){
//                        userList.add(userHelperClass);
//                    }
//                    //adapter
//                    adapterUser=new AdapterUser(getActivity(), userList);
//                    //set adapter to recycler view
//                    recyclerView.setAdapter(adapterUser);
//                }
            }
        });
    }
}