package adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;

import org.parceler.Parcels;

import java.util.List;

import models.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.viewHolder> {

    Context context ;
    List<Movie> movies ;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    //Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder" ) ;
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new viewHolder(movieView);
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position ) ;
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        // Bind the movie data into the VH
        holder.bind(movie) ;

    }


    // Return the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        RelativeLayout container ;
        TextView tvTitle ;
        TextView tvOverview ;
        ImageView ivPoster ;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle) ;
            tvOverview = itemView.findViewById(R.id.tvOverview) ;
            ivPoster = itemView.findViewById(R.id.ivPoster) ;
            container = itemView.findViewById(R.id.container) ;
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            // if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // imageUrl = back drop image
                imageUrl = movie.getBackdropPath();

            } else {
                // imageUrl = poster image
                imageUrl = movie.getPosterPath();
            }
            Glide.with(context).load(movie.getPosterPath()).into(ivPoster);

            //1. Register Click Listener on the whole row
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //2.Navigate to a new activity on tap
                    Intent i = new Intent(context, DetailActivity.class) ;
                    i.putExtra("movie" , Parcels.wrap(movie)) ;
                    context.startActivity(i);

                }
            });
        }
    }
}
