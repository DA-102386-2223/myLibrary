package cat.udl.gtidic.teacher.myLibrary.model.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SQLModelHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyLibrary.db";

    private final Context context;

    public SQLModelHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE book (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title VARCHAR(100)," +
                "author VARCHAR(100)," +
                "num_pages INT" +
                ")";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE book IF EXISTS";
        sqLiteDatabase.execSQL(query);
        this.onCreate(sqLiteDatabase);
    }

    public void addBook(String title, String author, int numPages){
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("author", author);
        cv.put("num_pages", numPages);

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert("book", null, cv);
        String message = "Successfully inserted";
        if (result < 0) message = "Error on inserting data";
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public Cursor readAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            String query = "SELECT * FROM book";
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Boolean updateById(String id, String title, String author, String numPages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("author", author);
        cv.put("num_pages", numPages);
        String[] whereArgs = new String[]{id};
        int numRows = db.update("book", cv, "id = ?", whereArgs);
        if (numRows != 1){
            Toast.makeText(this.context, "Update failed", Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(this.context, "Book updated", Toast.LENGTH_LONG).show();
        return true;
    }

    public Boolean deleteById(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("book", "id=?", new String[]{id});
        if (result == -1){
            Toast.makeText(this.context, "Delete Failed", Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(this.context, "Delete done", Toast.LENGTH_LONG).show();
        return true;
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("book", "1=1", null);
        Toast.makeText(this.context, "All books deleted!", Toast.LENGTH_LONG).show();
    }
}
