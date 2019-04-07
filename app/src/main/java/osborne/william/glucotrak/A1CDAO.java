package osborne.william.glucotrak;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface A1CDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(A1CRecord a1CRecord);

    @Query("DELETE FROM A1CTable")
    void deleteAll();

    @Delete
    void deleteRecord(A1CRecord a1CRecord);

    @Query("SELECT * FROM A1CTable ORDER BY date DESC")
    LiveData<List<A1CRecord>> getAllRecords();
}
