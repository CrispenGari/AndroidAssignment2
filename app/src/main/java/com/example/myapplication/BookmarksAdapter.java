package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.models.BookWithUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.BookmarkViewHolder> {

    private ArrayList<BookWithUser> bookmarks;

    public BookmarksAdapter(ArrayList<BookWithUser> bookmarks) {
        this.bookmarks = bookmarks;
    }

    @Override
    public BookmarkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark, parent, false);
        return new BookmarkViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BookmarkViewHolder holder, int position) {
        BookWithUser bookmark = bookmarks.get(position);
        holder.titleTextView.setText(bookmark.book.getTitle());
        holder.authorTextView.setText("Author: "+ bookmark.book.getAuthor());
        holder.numberOfCopiesView.setText("Copies: " + bookmark.book.getNumberOfCopies());
        holder.sellerTextView.setText("Seller: " + bookmark.user.getFirstName() + " " + bookmark.user.getLastName());

        Glide.with(holder.coverImageView.getContext())
                .load(bookmark.book.getCoverPage())  // Assuming the book has a cover image URL
                .into(holder.coverImageView);

        holder.removeFromBookmarksButton.setOnClickListener(v -> {
            removeBookFromBookmarks(holder.itemView.getContext(), bookmark);
            bookmarks.remove(position);
            notifyItemRemoved(position);
        });

        holder.bookItemContainer.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), BookActivity.class);
            intent.putExtra("id", String.valueOf(bookmark.book.getId()));
            holder.itemView.getContext().startActivity(intent);
        });
    }

    public void removeBookFromBookmarks(Context context, BookWithUser bookWithUser) {
        SharedPreferences sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = sharedPref.getString("bookmarks", null);
        Type type = new TypeToken<ArrayList<BookWithUser>>() {}.getType();
        ArrayList<BookWithUser> bookmarks;

        if (json == null) {
            bookmarks = new ArrayList<>();
        } else {
            bookmarks = gson.fromJson(json, type);
        }
        for (int i = 0; i < bookmarks.size(); i++) {
            if (Objects.equals(bookmarks.get(i).book.getId(), bookWithUser.book.getId())) {
                bookmarks.remove(i);
                break;
            }
        }
        String updatedJson = gson.toJson(bookmarks);
        editor.putString("bookmarks", updatedJson);
        editor.apply();
        Toast.makeText(context, "Book removed from bookmarks.", Toast.LENGTH_LONG).show();
    }

    @Override
    public int getItemCount() {
        return bookmarks.size();
    }

    public static class BookmarkViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, authorTextView, sellerTextView, numberOfCopiesView;
        ImageView coverImageView;
        Button removeFromBookmarksButton;
        LinearLayout  bookItemContainer;


        public BookmarkViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.bookTitle);
            authorTextView = itemView.findViewById( R.id.bookAuthor);
            sellerTextView = itemView.findViewById(R.id.bookSeller);
            coverImageView = itemView.findViewById(R.id.bookCoverImage);
            numberOfCopiesView = itemView.findViewById(R.id.bookCopies);
            bookItemContainer = itemView.findViewById(R.id.bookItemContainer);
            removeFromBookmarksButton = itemView.findViewById(R.id.removeFromBookmarksButton);
        }
    }
}
