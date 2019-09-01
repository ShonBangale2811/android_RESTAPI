package com.example.gen_plaza.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gen_plaza.Controller.MainActivity;
import com.example.gen_plaza.Model.User;
import com.example.gen_plaza.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {


    private  Context context;
    ArrayList<User> list;

    public ListAdapter(Activity context_name,ArrayList<User> user_list) {

        this.context = context_name;
        this.list = user_list;
    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ListViewHolder holder, int position) {

        User current = list.get(position);

        String imageUrl = current.getImageUrl();
        String firstName = current.getFirstName();
        String lastName = current.getLastName();

        holder.textView.setText(firstName +" "+lastName);

        Picasso.with(context).load(imageUrl).fit().centerInside().into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;


        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView = itemView.findViewById(R.id.text_view);

        }
    }
}


