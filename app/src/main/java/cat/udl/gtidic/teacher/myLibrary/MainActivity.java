package cat.udl.gtidic.teacher.myLibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import cat.udl.gtidic.teacher.myLibrary.model.Comic;
import cat.udl.gtidic.teacher.myLibrary.model.helpers.ComicDatabase;
import cat.udl.gtidic.teacher.myLibrary.model.helpers.SQLModelHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    protected String myClassTag = this.getClass().getSimpleName();
    SQLModelHelper dbHelper;

//    Room
    ComicDatabase dbRoom;
    // In a real situation, define this value in a global singleton class
    public static final String dbRoomName = "Comics.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        SQLiteOpenHelper
        dbHelper = new SQLModelHelper(this.getBaseContext());

//        books
        findViewById(R.id.addBookButton).setOnClickListener(v -> moveToAddBookActivity());
        findViewById(R.id.listBooks).setOnClickListener(v -> listBooks());
        findViewById(R.id.clearBooks).setOnClickListener(v -> clearBooks());

//        comics
        findViewById(R.id.addComicButton).setOnClickListener(v -> moveToAddComicActivity());
        findViewById(R.id.listComics).setOnClickListener(v -> listComics());
        findViewById(R.id.clearComics).setOnClickListener(v -> clearComics());

    }

    private void clearBooks() {
        dbHelper.deleteAll();
    }

    private void listBooks() {
        dbHelper.readAllData();
        Cursor c = dbHelper.readAllData();
        Log.d(myClassTag, "CURRENT LIBRARY BOOKS:");
        while(c.moveToNext()) {
            int id = c.getInt(0);
            String title = c.getString(1);
            String author = c.getString(2);
            int numPages = c.getInt(3);
            Log.d(myClassTag, String.format(" · Book id %d: Title: %s, Author: %s, Pages: %d", id, title, author, numPages));
        }
        Log.d(myClassTag, "END OF LIBRARY BOOKS");
    }

    private void moveToAddBookActivity(){
        Intent i = new Intent(this, AddBookActivity.class);
        startActivity(i);
    }

    private void clearComics() {
        dbRoom = Room.databaseBuilder(getApplicationContext(), ComicDatabase.class, dbRoomName).allowMainThreadQueries().build();
        dbRoom.comicDAO().deleteAll();
        dbRoom.close();
    }

    private void listComics() {
        dbRoom = Room.databaseBuilder(getApplicationContext(), ComicDatabase.class, dbRoomName).allowMainThreadQueries().build();
        List<Comic> comicsInDB = dbRoom.comicDAO().getAll();
        Log.d(myClassTag, "CURRENT LIBRARY COMICS");
        for(Comic oneComic:comicsInDB){
            String info = String.format(" · Comic id: %d, Title: %s, Author: %s, Num Characters: %d", oneComic.id, oneComic.title, oneComic.author, oneComic.numCharacters);
            Log.d(myClassTag, info);
        }
        Log.d(myClassTag, "END OF LIBRARY COMICS");
    }

    private void moveToAddComicActivity(){
        Intent i = new Intent(this, AddComicActivity.class);
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