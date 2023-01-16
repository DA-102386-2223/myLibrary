package cat.udl.gtidic.teacher.myLibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import cat.udl.gtidic.teacher.myLibrary.model.Comic;
import cat.udl.gtidic.teacher.myLibrary.model.helpers.ComicDatabase;

public class AddComicActivity extends AppCompatActivity {

    EditText title;
    EditText author;
    EditText numCharacters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comic);
        title = findViewById(R.id.editTextTitleComics);
        author = findViewById(R.id.editTextAuthorComics);
        numCharacters = findViewById(R.id.editTextCharacters);
        findViewById(R.id.buttonAddComic).setOnClickListener(v -> addComic());
    }

    private void addComic(){
//        in a MVVM, the VM should be in the middle of this connection
        String dbName = MainActivity.dbRoomName;
        ComicDatabase dbRoom = Room.databaseBuilder(getApplicationContext(), ComicDatabase.class, dbName).allowMainThreadQueries().build();

        String titleString = title.getText().toString();
        String authorString = author.getText().toString();
        int numCharactersInt = Integer.parseInt(numCharacters.getText().toString());
        Comic comicAdded = new Comic(titleString, authorString, numCharactersInt);

//        adding aditional comic just for learning purposes
        Comic comicExtra = new Comic("Tintin al Tíbet", "Hergé", 5);

        dbRoom.comicDAO().insertAll(comicAdded, comicExtra);
        dbRoom.close();

        Toast.makeText(this, "Comic added", Toast.LENGTH_LONG).show();

        finish();
    }
}