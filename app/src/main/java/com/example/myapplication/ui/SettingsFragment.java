package com.example.myapplication.ui;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapplication.R;

public class SettingsFragment extends Fragment {

    private RelativeLayout clearBookmarksItem, reportIssuesItem, checkUpdatesItem, rateAppItem;

    TextView appVersionView;
    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        // Bind the UI elements
        clearBookmarksItem = rootView.findViewById(R.id.clearBookmarksItem);
        reportIssuesItem = rootView.findViewById(R.id.reportIssuesItem);
        checkUpdatesItem = rootView.findViewById(R.id.checkUpdatesItem);
        rateAppItem = rootView.findViewById(R.id.rateAppItem);
        appVersionView = rootView.findViewById(R.id.appVersionText);

        // Set click listeners
        clearBookmarksItem.setOnClickListener(v -> clearBookmarks());
        reportIssuesItem.setOnClickListener(v -> reportIssues());
        checkUpdatesItem.setOnClickListener(v -> checkForUpdates());
        rateAppItem.setOnClickListener(v -> rateApp());

        String appVersion = "App Version: " + "1.0.0";
        appVersionView.setText(appVersion);
        return rootView;
    }
    // Method to clear bookmarks
    public void clearBookmarks() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("MyPrefs",
                getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("bookmarks"); // Removing the bookmarks from SharedPreferences (or use your DB clearing method)
        editor.apply();
        Toast.makeText(getActivity(), "Bookmarks cleared", Toast.LENGTH_SHORT).show();
    }

    // Method to report issues
    public void reportIssues() {
        String url = "https://github.com/CrispenGari/AndroidAssignment2/issues";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    // Method to check for updates
    public void checkForUpdates() {
        Toast.makeText(getActivity(), "Checking for updates...", Toast.LENGTH_SHORT).show();
    }

    // Method to rate the app
    public void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "Couldn't open Play Store", Toast.LENGTH_SHORT).show();
        }
    }
}