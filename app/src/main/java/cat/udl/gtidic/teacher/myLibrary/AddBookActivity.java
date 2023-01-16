package cat.udl.gtidic.teacher.myLibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import cat.udl.gtidic.teacher.myLibrary.model.helpers.SQLModelHelper;

public class AddBookActivity extends AppCompatActivity {

    EditText title;
    EditText author;
    EditText numPages;
    SQLModelHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        title = findViewById(R.id.editTextTitle);
        author = findViewById(R.id.editTextAuthor);
        numPages = findViewById(R.id.editTextPages);
        findViewById(R.id.buttonAdd).setOnClickListener(v -> addBook());
        helper = new SQLModelHelper(this);
    }

    private void addBook(){
        helper.addBook(title.getText().toString(), author.getText().toString(), Integer.valueOf(numPages.getText().toString()));
        finish();
    }
}