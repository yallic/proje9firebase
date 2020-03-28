package com.yallic.proje9firebase;

import android.service.autofill.AutofillService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder> {

    private ArrayList<String> mailArray ;
    private ArrayList<String> imageArray;
    private ArrayList<String> commentArray;

    public FeedRecyclerAdapter(ArrayList<String> mailArray, ArrayList<String> imageArray, ArrayList<String> commentArray) {
        this.mailArray = mailArray;
        this.imageArray = imageArray;
        this.commentArray = commentArray;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyler_row,parent,false);
        return  new PostHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {

        holder.textView.setText(mailArray.get(position));
        holder.textView2.setText(commentArray.get(position));
        Picasso.get().load(imageArray.get(position)).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mailArray.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        TextView textView2;
        public PostHolder(@NonNull View itemView) {
            super(itemView);

           imageView = itemView.findViewById(R.id.RimageView);
           textView = itemView.findViewById(R.id.RtextView);
           textView2 = itemView.findViewById(R.id.RtextView2);
        }
    }
}
