package osborne.william.glucotrak;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {BloodGlucoseRecord.class, BPRecord.class, A1CRecord.class}, version = 1, exportSchema = false)
public abstract class GlucoTrackRoomDatabase extends RoomDatabase {

    public abstract BloodGlucoseDAO bloodGlucoseDAO();
    public abstract BPDAO bpDAO();
    public abstract A1CDAO a1CDAO();

    // Ensures there is only one instance of the Database
    private static volatile GlucoTrackRoomDatabase INSTANCE;

    static GlucoTrackRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GlucoTrackRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GlucoTrackRoomDatabase.class, "BloodGlucoseDatabase")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
