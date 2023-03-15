package com.example.mvvmpost.View.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.mvvmpost.Data.DAO.PostDao;
import com.example.mvvmpost.Data.database.PostDatabase;
import com.example.mvvmpost.Data.Model.PostModel;
import com.example.mvvmpost.ModelView.CreatePostViewModel;
import com.example.mvvmpost.ModelView.PostViewModel;
import com.example.mvvmpost.R;
import com.example.mvvmpost.Repository.CreatePostRepository;
import com.example.mvvmpost.Repository.PostRepository;
import com.example.mvvmpost.View.adapter.PostAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private PostDatabase database;
    private PostRepository repository;
    private CreatePostRepository cRepository;
    private PostDao postDao;
    private PostViewModel viewModel;
    private PostModel postModel;
    private ImageView btnImage;
    private Button button_save;
    private CreatePostViewModel cPostViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnImage = findViewById(R.id.image);
        recyclerView = findViewById(R.id.recId);
        repository = new PostRepository(database);
        adapter = new PostAdapter(this, new ArrayList<>());
        postModel = new PostModel();
        setAdapter();

        cRepository = new CreatePostRepository(this,database);
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.createpost_layout);

                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int hight = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setLayout(width, hight);
                dialog.show();

                EditText editText_Title = dialog.findViewById(R.id.edit_title_ID);
                EditText editText_Text = dialog.findViewById(R.id.edit_text_ID);
                Button button_save = dialog.findViewById(R.id.button_Save);

                button_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String title = editText_Title.getText().toString().trim();
                        String text = editText_Text.getText().toString().trim();

                        if (TextUtils.isEmpty(title)) {
                            Toast.makeText(MainActivity.this, "Enter the Title", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(text)) {
                            Toast.makeText(MainActivity.this, "Enter the Body", Toast.LENGTH_SHORT).show();
                        } else {

                            postModel.setTitle(title);
                            postModel.setBody(text);
                            cPostViewModel.requestPost(postModel);
                            dialog.dismiss();
                        }
                    }
                });

            }
        });


        viewModel = new ViewModelProvider(this).get(PostViewModel.class);
        cPostViewModel = new ViewModelProvider(this).get(CreatePostViewModel.class);
        viewModel.requestDataServer();
        viewModel.getAllData().observe(this, new Observer<List<PostModel>>() {
            @Override
            public void onChanged(List<PostModel> postModels) {
               // Toast.makeText(MainActivity.this, "Post Get Successfully", Toast.LENGTH_SHORT).show();
                if (adapter != null) {
                    adapter.setPostList(postModels);
                }
            }
        });

    }

    public void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}