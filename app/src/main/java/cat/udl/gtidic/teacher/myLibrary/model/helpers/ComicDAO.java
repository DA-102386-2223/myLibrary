package cat.udl.gtidic.teacher.myLibrary.model.helpers;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import cat.udl.gtidic.teacher.myLibrary.model.Comic;

import java.util.List;

@Dao
public interface ComicDAO {

    @Insert
    void insertAll(Comic... comics);

    @Query("SELECT * FROM comic")
    List<Comic> getAll();

    @Delete
    void deleteOne(Comic comic);

    @Query("DELETE FROM comic")
    void deleteAll();
}
