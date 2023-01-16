package cat.udl.gtidic.teacher.myLibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import cat.udl.gtidic.teacher.myLibrary.model.helpers.SQLModelHelper;

public class MainActivity extends AppCompatActivity {
    protected String myClassTag = this.getClass().getSimpleName();
    SQLModelHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new SQLModelHelper(this.getBaseContext());

//        books
        findViewById(R.id.addBookButton).setOnClickListener(v -> moveToAddBookActivity());
        findViewById(R.id.listBooks).setOnClickListener(v -> listBooks());
        findViewById(R.id.clearBooks).setOnClickListener(v -> clearBooks());
    }

    private void clearBooks() {
        dbHelper.deleteAll();
    }

    private void listBooks() {
        dbHelper.readAllData();
        Cursor c = dbHelper.readAllData();
        Log.d(myClassTag, "CURRENT LIBRARY:");
        while(c.moveToNext()) {
            int id = c.getInt(0);
            String title = c.getString(1);
            String author = c.getString(2);
            int numPages = c.getInt(3);
            Log.d(myClassTag, String.format(" · Book id %d: Title: %s, Author: %s, Pages: %d", id, title, author, numPages));
        }
        Log.d(myClassTag, "END OF LIBRARY");
    }

    private void moveToAddBookActivity(){
        Intent i = new Intent(this, AddBookActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deleteAllButton){
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle("Are you sure you want to delete all your library?");
            ad.setPositiveButton("Yes", (dialogInterface, i) -> deleteAllData());
            ad.setNegativeButton("No", null);
            ad.create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllData(){
        dbHelper.deleteAll();
    }
}