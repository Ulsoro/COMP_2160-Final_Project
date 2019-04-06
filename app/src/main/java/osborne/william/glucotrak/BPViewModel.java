package osborne.william.glucotrak;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class BPViewModel extends AndroidViewModel {

    private BPRepository mRepository;

    private LiveData<List<BPRecord>> bpRecords;

    public BPViewModel (Application application) {
        super(application);
        mRepository = new BPRepository(application);
        bpRecords = mRepository.getAllRecords();
    }

    LiveData<List<BPRecord>> getAllRecords() {
        return bpRecords;
    }

    public void insert(BPRecord bpRecord) {
        mRepository.insert(bpRecord);
    }

    public void deleteRecord(BPRecord bpRecord) {
        mRepository.deleteRecord(bpRecord);
    }
}
