package osborne.william.glucotrak;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class A1CRepository {

    private A1CDAO a1CDAO;
    private LiveData<List<A1CRecord>> a1cRecords;

    A1CRepository(Application application) {
        GlucoTrackRoomDatabase db = GlucoTrackRoomDatabase.getDatabase(application);
        a1CDAO = db.a1CDAO();
        a1cRecords = a1CDAO.getAllRecords();
    }

    // Live Data wrapped around list for recyclerviews
    LiveData<List<A1CRecord>> getAllRecords() {
        return a1cRecords;
    }

    public void insert(A1CRecord a1CRecord) {
        new A1CRepository.insertAsyncTask(a1CDAO).execute(a1CRecord);
    }

    private static class insertAsyncTask extends AsyncTask<A1CRecord, Void, Void> {

        private A1CDAO mAsyncTaskDAO;

        insertAsyncTask(A1CDAO a1CDAO) { mAsyncTaskDAO = a1CDAO; }

        @Override
        protected Void doInBackground(final A1CRecord... params) {
            mAsyncTaskDAO.insert(params[0]);
            return null;
        }
    }

    public void deleteRecord(A1CRecord a1CRecord) {
        new A1CRepository.deleteA1CRecordAsyncTask(a1CDAO).execute(a1CRecord);
    }

    private static class deleteA1CRecordAsyncTask extends AsyncTask<A1CRecord, Void, Void> {

        private A1CDAO mAsyncTaskDAO;

        deleteA1CRecordAsyncTask(A1CDAO a1CDAO) {
            mAsyncTaskDAO = a1CDAO;
        }

        @Override
        protected Void doInBackground(final A1CRecord... params) {
            mAsyncTaskDAO.deleteRecord(params[0]);
            return null;
        }
    }


}
