package com.dataBinding.image.example.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class Movie
{

    public String id;

    public String title;

    public String year;

    public Images images;

    public class Images
    {
        public String small;

        @Override
        public String toString()
        {
            return "Images{" +
                    "small='" + small + '\'' +
                    '}';
        }
    }

    @Override
    public String toString()
    {
        return "Movie{" +
                "id='" + id + '\'' +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", images=" + images +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return id.equals(movie.id) &&
                title.equals(movie.title) &&
                year.equals(movie.year) &&
                images.equals(movie.images);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode()
    {
        return Objects.hash(id, title, year, images);
    }
}
