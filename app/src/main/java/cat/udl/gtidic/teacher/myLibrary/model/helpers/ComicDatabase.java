package cat.udl.gtidic.teacher.myLibrary.model.helpers;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import cat.udl.gtidic.teacher.myLibrary.model.Comic;

@Database(entities = {Comic.class}, version = 1)
public abstract class ComicDatabase extends RoomDatabase {
    public abstract ComicDAO comicDAO();
}