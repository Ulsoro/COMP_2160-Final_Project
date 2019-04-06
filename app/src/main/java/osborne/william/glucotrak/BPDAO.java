package osborne.william.glucotrak;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BPDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BPRecord bpRecord);

    @Query("DELETE FROM BloodPressureTable")
    void deleteAll();

    @Delete
    void deleteRecord(BPRecord bpRecord);

    @Query("SELECT * FROM BloodPressureTable ORDER BY date DESC")
    LiveData<List<BPRecord>> getAllRecords();
}
