package com.example.finalcpn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
//public class ImagePredictionAdapte extends RecyclerView.Adapter<ImagePredictionAdapte.ImagePredictionViewHolder> {
//
//    private Context mContext;
//    private List<ImagePrediction> mImagePredictionList;
//
//    public ImagePredictionAdapte(Context context, List<ImagePrediction> imagePredictionList) {
//        mContext = context;
//        mImagePredictionList = imagePredictionList;
//    }
//
//    @NonNull
//    @Override
//    public ImagePredictionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_history, parent, false);
//        return new ImagePredictionViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ImagePredictionViewHolder holder, int position) {
//        ImagePrediction imagePrediction = mImagePredictionList.get(position);
//
//        String imageUrl = imagePrediction.getImageUrl();
//        String predictionResult = imagePrediction.getPredictionResult();
//
//        Picasso.get().load(imageUrl).into(holder.imageView);
//        holder.predictionTextView.setText(predictionResult);
//
//        // Set a click listener for the delete button
//        holder.deleteButton.setOnClickListener(v -> {
//
//            // Get the selected image prediction
//            ImagePrediction selectedImagePrediction = mImagePredictionList.get(holder.getAdapterPosition());
//
//            // Remove the selected image prediction from the list
//            mImagePredictionList.remove(selectedImagePrediction);
//
//            // Notify the adapter that the data set has changed
//            notifyDataSetChanged();
//
//
//            // Delete the selected image prediction from the database
//            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
//            String id = selectedImagePrediction.getId();
//            // Delete the selected image prediction from the database
//
//
//            if (id != null) {
//                userRef.child("images").child(id).removeValue().addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        // Remove the selected image prediction from the list
//                        mImagePredictionList.remove(selectedImagePrediction);
//
//                        // Notify the adapter that the data set has changed
//                        notifyDataSetChanged();
//                        Picasso.get().invalidate(imageUrl);
//                    } else {
//                        // Handle the error
//                    }
//                });
//            }
//
////            userRef.child("images").child(selectedImagePrediction.getId()).removeValue();
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return mImagePredictionList.size();
//    }
//
//    public static class ImagePredictionViewHolder extends RecyclerView.ViewHolder {
//
//        public ImageView imageView;
//        public TextView predictionTextView;
//        public Button deleteButton;
//
//        public ImagePredictionViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            imageView = itemView.findViewById(R.id.image_view);
//            predictionTextView = itemView.findViewById(R.id.prediction_text_view);
//            deleteButton = itemView.findViewById(R.id.delete_button);
//        }
//    }
//}


//abstract method are overrideent extend is used that tha class beahve as recycler view
//by default methods are overireden viewholder class is used to hold view again and again
public class PredictionAdapter extends RecyclerView.Adapter<PredictionAdapter.ImagePredictionViewHolder> {

    private Context Context;
    private List<predictionModel> PredictionModelList;

    public PredictionAdapter(Context context, List<predictionModel> predictionModelList) {
//        contet represent the current state of the application access resourcess
        Context = context;
        PredictionModelList = predictionModelList;
    }

    @NonNull
    @Override
    //viewholder is created
    public ImagePredictionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        layoutinfalter is used to generate view of list item
        View view = LayoutInflater.from(Context).inflate(R.layout.activity_history, parent, false);
        return new ImagePredictionViewHolder(view);
    }

    @Override
    //views are binnded
    public void onBindViewHolder(@NonNull ImagePredictionViewHolder holder, int position) {
        predictionModel predictionModel = PredictionModelList.get(position);

        String imageUrl = predictionModel.getImageUrl();
        String predictionResult = predictionModel.getPredictionResult();

        Picasso.get().load(imageUrl).into(holder.imageView);
        holder.predictionTextView.setText(predictionResult);
    }

    @Override
    //for adapter  to know the number of views
    public int getItemCount() {
        return PredictionModelList.size();
    }

    public class ImagePredictionViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView predictionTextView;

        public ImagePredictionViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view);
            predictionTextView = itemView.findViewById(R.id.prediction_text_view);
        }
    }
}
