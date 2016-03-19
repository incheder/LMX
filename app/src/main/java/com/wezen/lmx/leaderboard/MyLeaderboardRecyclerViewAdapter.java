package com.wezen.lmx.leaderboard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wezen.lmx.leaderboard.LeaderboardFragment.OnListFragmentInteractionListener;
import com.wezen.lmx.R;
import com.wezen.lmx.model.Team;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Team} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyLeaderboardRecyclerViewAdapter extends RecyclerView.Adapter<MyLeaderboardRecyclerViewAdapter.ViewHolder> {

    private final List<Team> mValues;
    private final OnListFragmentInteractionListener mListener;


    public MyLeaderboardRecyclerViewAdapter(List<Team> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_leaderboard_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).id);
        //holder.mContentView.setText(mValues.get(position).content);

        Picasso.with(holder.mImageView.getContext())
                .load(holder.mItem.getImageUrl())
                //.fit()
                //.centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                //.into(holder.mImageView);
                .into(holder.myTarget);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    private static class MyTarget implements Target {
        private final ViewHolder holder;

        public MyTarget(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            loadRoundedImage(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            if(placeHolderDrawable != null){
                Bitmap bitmap = ((BitmapDrawable)placeHolderDrawable).getBitmap();
                loadRoundedImage(bitmap);

            }
        }

        private void loadRoundedImage(Bitmap bitmap){
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(holder.mImageView.getContext().getResources(),bitmap);
            roundedBitmapDrawable.setCornerRadius(Math.max(bitmap.getWidth(), bitmap.getHeight()) / 2.0f);
            holder.mImageView.setImageDrawable(roundedBitmapDrawable);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imageViewLeaderboardItem)
        ImageView mImageView;
        @Bind(R.id.textViewLeaderboardItemName)
        TextView mTextViewName;
        @Bind(R.id.textViewLeaderboardItemPosition)
        TextView mTextViewPosition;
        @Bind(R.id.textViewLeaderboardItemPoints)
        TextView mTextViewPoints;

        public final View mView;
        public Team mItem;
        public MyTarget myTarget;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this,view);
            myTarget = new MyTarget(this);
        }

        @Override
        public String toString() {
            //return super.toString();
            return super.toString() + " '" + mTextViewName.getText() + "'";
        }
    }
}
