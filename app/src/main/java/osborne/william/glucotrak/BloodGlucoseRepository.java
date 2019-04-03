package osborne.william.glucotrak;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class BloodGlucoseRepository {

    private BloodGlucoseDAO bloodGlucoseDAO;
    //private LiveData<List<BloodGlucoseRecord>> glucoseRecords;
    private List<BloodGlucoseRecord> glucoseRecords;

    BloodGlucoseRepository(Application application) {
        BloodGlucoseRoomDatabase db = BloodGlucoseRoomDatabase.getDatabase(application);
        bloodGlucoseDAO = db.bloodGlucoseDAO();
        glucoseRecords = bloodGlucoseDAO.getAllRecords();
    }
    //LiveData<List<BloodGlucoseRecord>>
    List<BloodGlucoseRecord> getAllRecords() {
        return glucoseRecords;
    }

    public void insert(BloodGlucoseRecord bloodGlucoseRecord) {
        new insertAsyncTask(bloodGlucoseDAO).execute(bloodGlucoseRecord);
    }

    private static class insertAsyncTask extends AsyncTask<BloodGlucoseRecord, Void, Void> {

        private BloodGlucoseDAO mAsyncTaskDAO;

        insertAsyncTask(BloodGlucoseDAO bGDAO) {
            mAsyncTaskDAO = bGDAO;
        }

        @Override
        protected Void doInBackground(final BloodGlucoseRecord... params) {
            mAsyncTaskDAO.insert(params[0]);
            return null;
        }
    }
}
