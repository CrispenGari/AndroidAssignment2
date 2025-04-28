package com.example.myapplication;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.myapplication.db.AppDatabase;
import com.example.myapplication.models.BookWithUser;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ImageButton searchButtonView, backButtonView;
    ProgressBar loadingSpinner;
    private BooksAdapter booksAdapter;
    private AppDatabase db;
    final String DB_NAME = "books-shop";
    String TAG = "Data";
    private RecyclerView recyclerView;
    private EditText searchEditText;
    private BookmarksAdapter adapter;
    private ArrayList<BookWithUser> results = new ArrayList<>(); // all data
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        db = Room.databaseBuilder(this, AppDatabase.class, DB_NAME)
                .fallbackToDestructiveMigration()
                .build();

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.tertiary));

        searchButtonView = findViewById(R.id.searchButton);
        backButtonView = findViewById(R.id.backButton);
        loadingSpinner = findViewById(R.id.loadingSpinner);
        searchEditText = findViewById(R.id.searchEditText);

        recyclerView = findViewById(R.id.recyclerViewBooks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create and set the adapter
        booksAdapter = new BooksAdapter(results, new BooksAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position) {
                BookWithUser clickedBook = results.get(position);
                Intent intent = new Intent(getApplicationContext(), BookActivity.class);
                intent.putExtra("id", String.valueOf(clickedBook.book.getId()));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(booksAdapter);

        searchButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSearch();
            }
        });
        backButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void performSearch() {

        loadingSpinner.setVisibility(View.VISIBLE);
        String query = searchEditText.getText().toString().trim();
        // Perform database query
        db.bookDao().searchBooks(query).observe(this, new  Observer<List<BookWithUser>>(){
            @Override
            public void onChanged(List<BookWithUser> books) {
                results.clear();
                results.addAll(books);
                booksAdapter.notifyDataSetChanged();
                loadingSpinner.setVisibility(View.GONE);
                Log.d(TAG, "performSearch: "+ results);
            }
        });

    }

}