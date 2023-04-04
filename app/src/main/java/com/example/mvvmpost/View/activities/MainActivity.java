package com.example.mvvmpost.View.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mvvmpost.Data.DAO.PostDao;
import com.example.mvvmpost.Data.database.PostDatabase;
import com.example.mvvmpost.Data.Model.PostModel;
import com.example.mvvmpost.ModelView.CreatePostViewModel;
import com.example.mvvmpost.ModelView.DeleteViewModel;
import com.example.mvvmpost.ModelView.PostViewModel;
import com.example.mvvmpost.ModelView.PutPostViewModel;
import com.example.mvvmpost.R;
import com.example.mvvmpost.Repository.CreatePostRepository;
import com.example.mvvmpost.Repository.DeleteRepository;
import com.example.mvvmpost.Repository.PostRepository;
import com.example.mvvmpost.Repository.PutRepository;
import com.example.mvvmpost.View.adapter.PostAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

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
    private FloatingActionButton button_save;
    private Button btnSubmit;
    private CreatePostViewModel cPostViewModel;
    private TextInputEditText editTextTitle, editTextBody;
    private PutRepository pRepository;
    private PutPostViewModel pViewModel;
    private DeleteViewModel dViewModel;
    private DeleteRepository dRepository;
    private List<PostModel> postList;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button_save = findViewById(R.id.image);
        recyclerView = findViewById(R.id.recId);
        adapter = new PostAdapter(this, new ArrayList<>());
        postModel = new PostModel();
        setAdapter();


        repository = new PostRepository(database);
        dRepository = new DeleteRepository(this, database);
        pRepository = new PutRepository(this, database);
        cRepository = new CreatePostRepository(this, database);


        button_save = findViewById(R.id.image);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }

            private void showCustomDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.createpost_layout, null);

                editTextTitle = dialogView.findViewById(R.id.edit_text_title);
                editTextBody = dialogView.findViewById(R.id.edit_text_body);
                Button btnSubmit = dialogView.findViewById(R.id.btn_submit);
                builder.setView(dialogView);


                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title = editTextTitle.getText().toString().trim();
                        String text = editTextBody.getText().toString().trim();

                        if (TextUtils.isEmpty(title)) {
                            Toast.makeText(MainActivity.this, "Enter the Title", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(text)) {
                            Toast.makeText(MainActivity.this, "Enter the Body", Toast.LENGTH_SHORT).show();
                        } else {
                            postModel.setTitle(title);
                            postModel.setBody(text);
                            cPostViewModel.requestPost(postModel);
                            alertDialog.dismiss();

                        }
                    }
                });
            }
        });
        // create a new instance of the Delete ViewModel
        dViewModel = new ViewModelProvider(this).get(DeleteViewModel.class);


        // create a new instance of the Put ViewModel
        pViewModel = new ViewModelProvider(this).get(PutPostViewModel.class);


        // create a new instance of the Create ViewModel
        cPostViewModel = new ViewModelProvider(this).get(CreatePostViewModel.class);


        // create a new instance of the get ViewModel
        viewModel = new ViewModelProvider(this).get(PostViewModel.class);
        viewModel.requestDataServer();
        viewModel.getAllData().observe(this, new Observer<List<PostModel>>() {
            @Override
            public void onChanged(List<PostModel> postModels) {

                if (adapter != null) {
                    adapter.setPostList(postModels);
                }
            }
        });



   /*     adapter.setPostAdapterEvents(postModel -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Choose an action");
            String[] options = {"Update", "Delete"};
            builder.setItems(options, (dialog, which) -> {
                switch (which) {
                    case 0: // Update
                         pViewModel= new PutPostViewModel(new Application());
                        pViewModel.requestPut(postModel);
                        break;
                    case 1: // Delete
                         dViewModel = new DeleteViewModel(new Application());
                        dViewModel.requestDelete(postModel);
                        break;
                }
            });
            builder.show();
        });*/


        adapter.setPostAdapterEvents(postModel -> {
            //for update from adapter
            pViewModel = new PutPostViewModel(new Application());
            pViewModel.requestPut(postModel);

            // for delete from adapter
        });
        adapter.setDeleteAdapterEvents(postModel -> {
            dViewModel = new DeleteViewModel(new Application());
            dViewModel.requestDelete(postModel);
        });
    }


    public void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}