package cat.udl.gtidic.teacher.myLibrary.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Comic {

//    attributes should be private, they are public just to simplify the example
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String author;
    @ColumnInfo(name = "num_characters")
    public int numCharacters; // personatges

    public Comic(String title, String author, int numCharacters){
        this.title = title;
        this.author = author;
        this.numCharacters = numCharacters;
    }
}
