package osborne.william.glucotrak;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class BloodGlucoseRepository {

    private BloodGlucoseDAO bloodGlucoseDAO;
    private LiveData<List<BloodGlucoseRecord>> glucoseRecords;
    //private static Double allTimeBGAverage;
    //private List<BloodGlucoseRecord> glucoseRecordList;


    BloodGlucoseRepository(Application application) {
        GlucoTrackRoomDatabase db = GlucoTrackRoomDatabase.getDatabase(application);
        bloodGlucoseDAO = db.bloodGlucoseDAO();
        glucoseRecords = bloodGlucoseDAO.getAllRecords();
        //glucoseRecordList = bloodGlucoseDAO.getAllRecordList();
        //allTimeBGAverage = getAllTimeBGAverage();
    }

    // Live Data wrapped around list for recyclerviews
    LiveData<List<BloodGlucoseRecord>> getAllRecords() {
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


    public void deleteRecord(BloodGlucoseRecord bloodGlucoseRecord) {
        new deleteBGRecordAsyncTask(bloodGlucoseDAO).execute(bloodGlucoseRecord);
    }

    private static class deleteBGRecordAsyncTask extends AsyncTask<BloodGlucoseRecord, Void, Void> {

        private BloodGlucoseDAO mAsyncTaskDAO;

        deleteBGRecordAsyncTask(BloodGlucoseDAO bGDAO) {
            mAsyncTaskDAO = bGDAO;
        }

        @Override
        protected Void doInBackground(final BloodGlucoseRecord... params) {
            mAsyncTaskDAO.deleteRecord(params[0]);
            return null;
        }
    }



    /* TODO: Get Async Querries that return a value working
    public Double getAllTimeBGAverage() {
        new getAllTimeBGAverageAsyncTask().execute();
        return allTimeBGAverage;
    }

    private static class getAllTimeBGAverageAsyncTask extends AsyncTask<Void, Void, Void> {

        private BloodGlucoseDAO mAsyncTaskDAO;

        @Override
        protected Void doInBackground(Void... voids) {
            allTimeBGAverage = mAsyncTaskDAO.getAllTimeBGAverage();
            return null;
        }
    } */




}
