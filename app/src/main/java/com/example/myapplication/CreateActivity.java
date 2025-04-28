package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.myapplication.db.AppDatabase;
import com.example.myapplication.models.Book;
import com.example.myapplication.models.User;

import java.util.Objects;

public class CreateActivity extends AppCompatActivity {

    String TAG = "CREATING";
    final String DB_NAME = "books-shop";
    // GETTING BOOK DATA
    EditText editTextTitle, editTextAuthor, editTextCopies, editTextPrice , editTextBankInfo, editTextCoverPage;

    // SELLER DETAILS
    EditText editTextEmail ,editTextFirstName , editTextLastName;
    Button buttonUpload;

    private AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.tertiary));


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DB_NAME)
                .fallbackToDestructiveMigration()
                .build();

        // GETTING BOOK DATA
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        editTextCopies = findViewById(R.id.editTextCopies);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextBankInfo = findViewById(R.id.editTextBankInfo);
        editTextCoverPage = findViewById(R.id.editTextCoverPage);

        // SELLER DETAILS
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        buttonUpload = findViewById(R.id.buttonUpload);

    }


    public void uploadBook(View view){
        new Thread(()->{
            String email = editTextEmail.getText().toString().trim().toLowerCase();
            String firstName = editTextFirstName.getText().toString().trim();
            String lastName = editTextLastName.getText().toString().trim();
            User user = db.userDao().getUserByEmail(email);

            if(user == null) {
                // create a user if he doesn't exists
                db.userDao().insertUser(new User(email, firstName, lastName));
            }
            user = db.userDao().getUserByEmail(email);
            String title = editTextTitle.getText().toString().trim();
            String author = editTextAuthor.getText().toString().trim();
            int copies = Integer.parseInt(editTextCopies.getText().toString().trim());
            double price = Double.parseDouble(editTextPrice.getText().toString().strip());
            String bankDetails = editTextBankInfo.getText().toString().trim();
            String seller = user.getFirstName().concat(" ").concat(user.getLastName());
            String cover = editTextCoverPage.getText().toString().trim().isEmpty() ? "https://img.freepik.com/premium-photo/blank-cover-closed-book-white-background_392895-235235.jpg" :
                    editTextCoverPage.getText().toString();
            Book book = new Book(
                    title, author, seller, copies,
                    price, bankDetails, cover, user.getId()
            );
            Log.d(TAG, "Book: " + book);
            db.bookDao().insertBook(book);
            Log.d(TAG, "User: "+ user);
            finish();
        }).start();
    }


    @SuppressLint("StaticFieldLeak")
    class InsertAsyncUserTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, DB_NAME).build();
            db.userDao().insertUser(users[0]);
            return null;
        }
    }
    @SuppressLint("StaticFieldLeak")
    class InsertAsyncBookTask extends AsyncTask<Book, Void, Void> {
        @Override
        protected Void doInBackground(Book... books) {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, DB_NAME).build();
            db.bookDao().insertBook(books[0]);
            return null;
        }
    }
}