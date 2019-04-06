package osborne.william.glucotrak;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class BPRepository {

    private BPDAO bpdao;
    private LiveData<List<BPRecord>> bpRecords;

    BPRepository(Application application) {

        GlucoTrackRoomDatabase db = GlucoTrackRoomDatabase.getDatabase(application);
        bpdao = db.bpDAO();
        bpRecords = bpdao.getAllRecords();
    }

    // Live Data wrapped around list for recyclerviews
    LiveData<List<BPRecord>> getAllRecords() {
        return bpRecords;
    }

    public void insert(BPRecord bpRecord) {
        new BPRepository.insertAsyncTask(bpdao).execute(bpRecord);
    }

    private static class insertAsyncTask extends AsyncTask<BPRecord, Void, Void> {

        private BPDAO mAsyncTaskDAO;

        insertAsyncTask(BPDAO bpDAO) { mAsyncTaskDAO = bpDAO; }

        @Override
        protected Void doInBackground(final BPRecord... params) {
            mAsyncTaskDAO.insert(params[0]);
            return null;
        }
    }

    public void deleteRecord(BPRecord bpRecord) {
        new BPRepository.deleteBPRecordAsyncTask(bpdao).execute(bpRecord);
    }

    private static class deleteBPRecordAsyncTask extends AsyncTask<BPRecord, Void, Void> {

        private BPDAO mAsyncTaskDAO;

        deleteBPRecordAsyncTask(BPDAO bpdao) {
            mAsyncTaskDAO = bpdao;
        }

        @Override
        protected Void doInBackground(final BPRecord... params) {
            mAsyncTaskDAO.deleteRecord(params[0]);
            return null;
        }
    }

}
