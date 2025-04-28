package com.example.myapplication.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.BookmarksAdapter;
import com.example.myapplication.R;
import com.example.myapplication.models.BookWithUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class BookmarksFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookmarksAdapter adapter;
    private ArrayList<BookWithUser> bookmarks;
    public static BookmarksFragment newInstance(String param1, String param2) {
        BookmarksFragment fragment = new BookmarksFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewBookmarks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadBookmarks();
        return view;
    }

    private void loadBookmarks() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("MyPrefs", getContext().MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("bookmarks", null);
        Type type = new TypeToken<ArrayList<BookWithUser>>() {}.getType();
        if (json != null) {
            bookmarks = gson.fromJson(json, type);
        } else {
            bookmarks = new ArrayList<>();
            Toast.makeText(getContext(), "No bookmarks found.", Toast.LENGTH_LONG).show();
        }
        adapter = new BookmarksAdapter(bookmarks);
        recyclerView.setAdapter(adapter);
    }
}