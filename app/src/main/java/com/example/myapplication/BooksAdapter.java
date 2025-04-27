package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.models.BookWithUser;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<
        BooksAdapter.BookViewHolder
        > {

    private List<BookWithUser> booksWithUsers;
    private OnItemClickListener onItemClickListener;

    public BooksAdapter(List<BookWithUser> booksWithUsers, OnItemClickListener onItemClickListener) {
        this.booksWithUsers = booksWithUsers;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        BookWithUser bookWithUser = booksWithUsers.get(position);
        holder.titleTextView.setText(bookWithUser.book.getTitle());
        holder.authorTextView.setText("Authour: " + bookWithUser.book.getAuthor());
        holder.sellerTextView.setText("Seller Name: " + bookWithUser.book.getSellerName());
        holder.bookSellerEmailView.setText("Seller Email: " + bookWithUser.user.getEmail());
        holder.bookCopiesBadgeView.setText("Copies: ".concat(
                String.valueOf(bookWithUser.book.getNumberOfCopies())));
        holder.priceTextView.setText("R".concat(String.valueOf(bookWithUser.book.getPrice())));
        Glide.with(holder.coverImageView.getContext())
                .load(bookWithUser.book.getCoverPage())
                .into(holder.coverImageView);
    }
    @Override
    public int getItemCount() {
        return booksWithUsers.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView bookSellerEmailView,
                titleTextView, authorTextView,
                sellerTextView, priceTextView, bookCopiesBadgeView;
        ImageView coverImageView;

        public BookViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.bookTitle);
            authorTextView = itemView.findViewById(R.id.bookAuthor);
            sellerTextView = itemView.findViewById(R.id.bookSeller);
            priceTextView = itemView.findViewById(R.id.bookPrice);
            coverImageView = itemView.findViewById(R.id.bookCover);
            bookSellerEmailView = itemView.findViewById(R.id.bookSellerEmail);
            bookCopiesBadgeView = itemView.findViewById(R.id.bookCopiesBadge);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Trigger the onItemClick method
                    if ( listener != null) {
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
