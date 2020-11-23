package com.dataBinding.image.example;

import android.os.Bundle;
import android.util.Log;

import com.dataBinding.image.R;
import com.dataBinding.image.example.model.Movie;
import com.dataBinding.image.example.paging.MovieAdapter;
import com.dataBinding.image.example.paging.MovieViewModel;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 演示如何使用Paging库，获取并展示豆瓣当前上映的电影列表
 * */
public class MainActivity1 extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final MovieAdapter movieAdapter = new MovieAdapter(this);
        MovieViewModel movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieViewModel.moviePagedList.observe(this, new Observer<PagedList<Movie>>()
        {
            @Override
            public void onChanged(PagedList<Movie> movies)
            {
                Log.e("MainActivity", "onChange()->"+movies);
                movieAdapter.submitList(movies);
            }
        });
        recyclerView.setAdapter(movieAdapter);
    }
}
