package com.rastrakidyana.ugd10_f_9618;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rastrakidyana.ugd10_f_9618.Adapter.UserRecyclerViewAdapter;
import com.rastrakidyana.ugd10_f_9618.Database.DatabaseClient;
import com.rastrakidyana.ugd10_f_9618.Model.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private UserRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshLayout = findViewById(R.id.swipe_refresh);
        recyclerView = findViewById(R.id.recycler_view_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUsers();
                refreshLayout.setRefreshing(false);
            }
        });

        getUsers();

        FloatingActionButton fab = findViewById(R.id.fbAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                CreateFragment createFragment = new CreateFragment();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, createFragment)
                        .commit();
            }
        });

        FloatingActionButton fabx = findViewById(R.id.fbChart);
        fabx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChartActivity.class);
                startActivity(intent);

            }
        });
    }

    private void getUsers(){
        class GetUsers extends AsyncTask<Void, Void, List<User>> {

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);
                adapter = new UserRecyclerViewAdapter(MainActivity.this, users);
                recyclerView.setAdapter(adapter);
                if (users.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Empty List", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected List<User> doInBackground(Void... voids) {
                List<User> userList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getDatabase()
                        .userDAO()
                        .getAll();
                return userList;
            }
        }

        GetUsers get = new GetUsers();
        get.execute();
    }
}