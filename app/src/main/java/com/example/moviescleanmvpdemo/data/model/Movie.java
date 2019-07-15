package com.example.moviescleanmvpdemo.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "favourites_movies")
public class Movie implements Parcelable {

    @PrimaryKey
    private int id;
    private Double vote_average;
    private String title;
    private Double popularity;
    private String poster_path;
    private String original_language;
    private String original_title;
    private String overview;
    private String release_date;

    public Movie() {
    }

    public Movie(Double voteAverage, String title, Double popularity, String posterPath,
                 String originalLanguage, String originalTitle,
                 String overview, String releaseDate, int id) {
        this.vote_average = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.poster_path = posterPath;
        this.original_language = originalLanguage;
        this.original_title = originalTitle;
        this.overview = overview;
        this.release_date = releaseDate;
        this.id = id;
    }

    private Movie(Parcel in) {
        id = in.readInt();
        if (in.readByte() == 0) {
            vote_average = null;
        } else {
            vote_average = in.readDouble();
        }
        title = in.readString();
        if (in.readByte() == 0) {
            popularity = null;
        } else {
            popularity = in.readDouble();
        }
        poster_path = in.readString();
        original_language = in.readString();
        original_title = in.readString();
        overview = in.readString();
        release_date = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        if (vote_average == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(vote_average);
        }
        dest.writeString(title);
        if (popularity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(popularity);
        }
        dest.writeString(poster_path);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeString(overview);
        dest.writeString(release_date);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id &&
                vote_average.equals(movie.vote_average) &&
                title.equals(movie.title) &&
                popularity.equals(movie.popularity) &&
                poster_path.equals(movie.poster_path) &&
                original_language.equals(movie.original_language) &&
                original_title.equals(movie.original_title) &&
                overview.equals(movie.overview) &&
                release_date.equals(movie.release_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vote_average, title, popularity, poster_path, original_language, original_title, overview, release_date);
    }

}
