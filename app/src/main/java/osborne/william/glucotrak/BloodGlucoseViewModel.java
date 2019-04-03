package osborne.william.glucotrak;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class BloodGlucoseViewModel extends AndroidViewModel {

    private BloodGlucoseRepository mRepository;

    //private LiveData<List<BloodGlucoseRecord>> mBloodGlucoseRecords;
    private List<BloodGlucoseRecord> mBloodGlucoseRecords;


    public BloodGlucoseViewModel (Application application) {
        super(application);
        mRepository = new BloodGlucoseRepository(application);
        mBloodGlucoseRecords = mRepository.getAllRecords();
    }

    // LiveData<List<BloodGlucoseRecord>>
    List<BloodGlucoseRecord> getAllRecords() {

        return mBloodGlucoseRecords;
    }

    public void insert(BloodGlucoseRecord bloodGlucoseRecord) {

        mRepository.insert(bloodGlucoseRecord);
    }
}
