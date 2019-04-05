package osborne.william.glucotrak;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BloodGlucoseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BloodGlucoseRecord bloodGlucoseRecord);

    @Query("DELETE FROM BloodGlucoseTable")
    void deleteAll();

    @Query("SELECT * FROM BloodGlucoseTable")
    LiveData<List<BloodGlucoseRecord>> getAllRecords();

    //@Query("SELECT * FROM BloodGlucoseTable")
    //List<BloodGlucoseRecord> getAllRecordList();



}
