package osborne.william.glucotrak;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {BloodGlucoseRecord.class}, version = 1, exportSchema = false)
public abstract class BloodGlucoseRoomDatabase extends RoomDatabase {

    public abstract BloodGlucoseDAO bloodGlucoseDAO();

    // Ensures there is only one instance of the Database
    private static volatile BloodGlucoseRoomDatabase INSTANCE;

    static BloodGlucoseRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BloodGlucoseRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BloodGlucoseRoomDatabase.class, "BloodGlucoseDatabase")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
