package com.cagataygul.ortaksison;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
public static final int msg_type_left =0;
public static final int msg_type_Right =1;
    FirebaseDatabase database;
    DatabaseReference dbref ;
private Context mContext;
private List<Chat> mChat;
FirebaseUser fuser;
String currentUser,id;

public MessageAdapter(Context mContext, List <Chat> mChat){

    this.mChat=mChat;
    this.mContext=mContext;
}

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if(viewType==msg_type_Right) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
        return new MessageAdapter.ViewHolder(view);
    }
    else
        {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         Chat chat =mChat.get(position);
         holder.show_message.setText(chat.getMessage());

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


    public TextView show_message;
    public ViewHolder(View itemView){
        super(itemView);
        show_message=itemView.findViewById(R.id.show_message);
    }


}



    @Override
    public int getItemViewType(int position) {
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        dbref = FirebaseDatabase.getInstance().getReference("User");
        currentUser = fuser.getEmail();
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    HashMap<String,String> hmap = (HashMap<String, String>) ds.getValue();

                    if( hmap.get("email").equalsIgnoreCase(currentUser)) {
                        id = hmap.get("id");


                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        System.out.println("blablablabla"+mChat.get(position).getSender());
        if(mChat.get(position).getSender().equals(id)){

            return msg_type_Right;
        }
        else {
            return msg_type_left;
        }



    }
}
