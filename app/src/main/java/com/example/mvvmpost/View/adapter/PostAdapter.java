package com.example.mvvmpost.View.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmpost.Data.Model.PostModel;
import com.example.mvvmpost.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private List<PostModel> postList;

    public PostAdapter(Context context, List<PostModel> postList) {
        this.context = context;
        this.postList = postList;
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        TextView Text_UserId, Text_Id, Text_Title, Text_Body;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            Text_UserId = itemView.findViewById(R.id.Text_userId);
            Text_Id = itemView.findViewById(R.id.Text_id);
            Text_Title = itemView.findViewById(R.id.Text_title);
            Text_Body = itemView.findViewById(R.id.Text_body);
        }
    }


    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.model_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {
        PostModel postModel = postList.get(position);

        holder.Text_UserId.setText("userId: " + postModel.getUserId());
        holder.Text_Id.setText("id: " + postModel.getId());
        holder.Text_Title.setText("title: " + postModel.getTitle());
        holder.Text_Body.setText("body: " + postModel.getBody());


    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void setPostList(List<PostModel> postList) {
        this.postList = postList;
        notifyDataSetChanged();
    }
}
