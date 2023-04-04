package com.example.mvvmpost.View.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmpost.Data.Model.PostModel;
import com.example.mvvmpost.Data.database.PostDatabase;
import com.example.mvvmpost.ModelView.DeleteViewModel;
import com.example.mvvmpost.ModelView.PostViewModel;
import com.example.mvvmpost.ModelView.PutPostViewModel;
import com.example.mvvmpost.Network.Api;
import com.example.mvvmpost.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private static final String TAG = "PostAdapter";

    private PutPostViewModel pViewModel;
    private PostAdapter adapter;
    private PostDatabase database;
    private Context context;
    private Api api;
    private PostViewModel viewModel;
    private DeleteViewModel dViewModel;
    private List<PostModel> postList;
    private PostAdapterEvents postAdapterEvents;
    private DeleteAdapterEvents deleteAdapterEvents;


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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {
        PostModel postModel = postList.get(position);


        //My main code for update data
        holder.Text_UserId.setText("userId: " + postModel.getUserId());
        holder.Text_Id.setText("id: " + postModel.getId());
        holder.Text_Title.setText("title: " + postModel.getTitle());
        holder.Text_Body.setText("body: " + postModel.getBody());

        holder.itemView.setOnClickListener(v -> {

            // Show popup menu
            PopupMenu popupMenu = new PopupMenu(context, holder.itemView, Gravity.END, 0, R.style.PopupMenuStyle);
            popupMenu.inflate(R.menu.popup_menu);


            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.action_update:
                        // Handle update action
                        showUpdateDialog(postModel);
                        return true;
                    case R.id.action_delete:
                        // Handle delete action
                        showDeleteDialog(postModel);
                        return true;
                    default:
                        return false;

                }
            });
            popupMenu.show();
        });
    }

    private void showUpdateDialog(PostModel postModel) {
        // Inflate the update dialog layout
        LayoutInflater inflater = LayoutInflater.from(context);

        View dialogView = inflater.inflate(R.layout.dialog_box, null);


        TextInputEditText editTextTitle = dialogView.findViewById(R.id.edit_text_titleID);
        TextInputEditText editTextBody = dialogView.findViewById(R.id.edit_text_bodyID);
        Button btn1Submit = dialogView.findViewById(R.id.btn_submitID);

        // Set the text of each EditText field to the current value of the data
        editTextTitle.setText(postModel.getTitle());
        editTextBody.setText(postModel.getBody());

        // Create the dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();

        btn1Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String title = editTextTitle.getText().toString().trim();
                String body = editTextBody.getText().toString().trim();

                if (TextUtils.isEmpty(title)) {
                    Toast.makeText(view.getContext(), "Enter the Title", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(body)) {
                    Toast.makeText(view.getContext(), "Enter the Body", Toast.LENGTH_SHORT).show();
                } else {
                    postModel.setTitle(title);
                    postModel.setBody(body);
                    postAdapterEvents.onListClick(postModel);
                    notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
    }

    private void showDeleteDialog(PostModel postModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Post");
        builder.setMessage("Are you sure you want to delete this post?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // Log.d(TAG, "onClick: " + postModel);
                deleteAdapterEvents.isDeleteClick(postModel);
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void setPostList(List<PostModel> postList) {
        this.postList = postList;
        notifyDataSetChanged();
    }

    public void setPostAdapterEvents(PostAdapterEvents postAdapterEvents) {
        this.postAdapterEvents = postAdapterEvents;
        notifyDataSetChanged();
    }

    public void setDeleteAdapterEvents(DeleteAdapterEvents deleteAdapterEvents) {
        this.deleteAdapterEvents = deleteAdapterEvents;
        notifyDataSetChanged();
    }
}
