package com.example.quizapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/*this class extends the RecyclerView.Adapter and defines how items are displayed and interacted with*/
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<Photo> animalist;
    private OnPhotoClickListener listener;

    /**
     * defines a method that the activity will implement to handle image clicks
     * allows separation of UI handling and user interaction*/
    public interface OnPhotoClickListener {
        void onPhotoClick(Photo photo);
    }

    /*constructor. initializes the adapter with data and listene
     * added to allow dynamic data interaction handling*/
    public CustomAdapter(List<Photo> animalist, OnPhotoClickListener listener) {
        this.animalist = animalist;
        this.listener = listener;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    /**
     * android dev template method
     * holds reference to the views in each item layout*/
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            imageView = view.findViewById(R.id.animal_image);
            textView = view.findViewById(R.id.textView);
        }

        public void bind(Photo photo) {
            imageView.setImageResource(photo.getImageResId());
            textView.setText(photo.getName());
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    //public CustomAdapter(String[] dataSet) {
    //  localDataSet = dataSet;
    //}

    // Create new views (invoked by the layout manager)
    /*android dev template method
     * creates and return a new ViewHolder for a list item
     * */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    /* android dev template method
     * binds the data for a given position in the list to the views in the ViewHolder.
     * retrieve the Photo object for the current position
     * populate the Textview and imageView with the data from the photo object
     * attach a clicklistener to imageview. when clicked the, listener triggers onPhotoClick method, passing the clicked photo*/
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Photo photo = animalist.get(position);
        viewHolder.textView.setText(photo.getName());

        if (photo.getImageUri() != null) {
            // Load image from URI using Glide
            viewHolder.imageView.setImageURI(photo.getImageUri());
        }else {
            viewHolder.imageView.setImageResource(photo.getImageResId());
        }

        viewHolder.imageView.setOnClickListener(v -> {
            listener.onPhotoClick(photo);
        });
        //viewHolder.bind(animalist.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    /*android dev template method
     * returns the total number of items in the list
     * recyclerview uses this value to determine how many items to display*/
    @Override
    public int getItemCount() {
        return animalist.size();
    }
}