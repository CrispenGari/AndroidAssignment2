package com.example.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.BookActivity;
import com.example.myapplication.BooksAdapter;
import com.example.myapplication.R;
import com.example.myapplication.SearchActivity;
import com.example.myapplication.db.AppDatabase;
import com.example.myapplication.models.Book;
import com.example.myapplication.models.BookWithUser;

import java.util.List;

public class HomeFragment extends Fragment {
    private AppDatabase db;
    final String DB_NAME = "books-shop";
    String TAG = "Data";
    private RecyclerView recyclerView;
    private BooksAdapter bookAdapter;
    public static HomeFragment newInstance(String param1, String param2) {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout first
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Now use the inflated view to find the RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewBooks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set up the Room database
        db = Room.databaseBuilder(getContext(), AppDatabase.class, DB_NAME)
                .fallbackToDestructiveMigration()
                .build();

        // Observe the LiveData from the Room database
        db.bookDao().allBooks().observe(getViewLifecycleOwner(), new Observer<List<BookWithUser>>() {
            @Override
            public void onChanged(List<BookWithUser> books) {
                // Log book and user details
                bookAdapter = new BooksAdapter(books, new BooksAdapter.OnItemClickListener(){
                    @Override
                    public void onItemClick(int position) {
                        BookWithUser clickedBook = books.get(position);
                        Intent intent = new Intent(requireContext(), BookActivity.class);
                        intent.putExtra("id", String.valueOf(clickedBook.book.getId()));
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(bookAdapter);
                for (BookWithUser book : books) {
                    Log.d(TAG, "onChanged: " + book.user);
                    Log.d(TAG, "onChanged: " + book.book);
                }
            }
        });
        // Return the inflated view
        return view;
    }

}