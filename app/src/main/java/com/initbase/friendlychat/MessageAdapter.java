package com.initbase.friendlychat;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.FriendlyMessage>{
    ArrayList<SingleMessage> data;
    public MessageAdapter(ArrayList<SingleMessage> data){
        this.data = data;
    }

    @NonNull
    @Override
    public FriendlyMessage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_message,parent,false);
        FriendlyMessage friendlyMessage = new FriendlyMessage(view);
        return friendlyMessage;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendlyMessage holder, int position) {
        SingleMessage singleMessage = data.get(position);
        holder.v_usersName.setText(singleMessage.getUsersName());
        holder.v_message.setText(singleMessage.getMessage());
//        if (singleMessage.getImage()!=null){
//            holder.v_image.setImageURI(singleMessage.getImage());
//            holder.v_image.setVisibility(View.VISIBLE);
//        }else {
//            holder.v_image.setVisibility(View.GONE);
//        }
        if (singleMessage.getMessage()==null){
            holder.v_message.setVisibility(View.GONE);
        }else {
            holder.v_message.setVisibility(View.VISIBLE);
        }
        holder.v_date_time.setText(String.valueOf(singleMessage.getTime()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class FriendlyMessage extends RecyclerView.ViewHolder{
        private LinearLayout message_con;
        private ImageView v_image;
        private TextView v_usersName,v_message,v_date_time;

        public FriendlyMessage(@NonNull View itemView) {
            super(itemView);
            message_con = (LinearLayout) itemView.findViewById(R.id.message_con);
            v_usersName = itemView.findViewById(R.id.users_name);
            v_message = itemView.findViewById(R.id.message);
            v_image = itemView.findViewById(R.id.image);
            v_date_time = itemView.findViewById(R.id.date_time);
        }

    }
}
