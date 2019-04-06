package osborne.william.glucotrak;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class BloodGlucoseViewModel extends AndroidViewModel {

    private BloodGlucoseRepository mRepository;

    private LiveData<List<BloodGlucoseRecord>> mallBloodGlucoseRecords;

    public BloodGlucoseViewModel (Application application) {
        super(application);
        mRepository = new BloodGlucoseRepository(application);
        mallBloodGlucoseRecords = mRepository.getAllRecords();
    }

    LiveData<List<BloodGlucoseRecord>> getAllRecords() {

        return mallBloodGlucoseRecords;
    }


    public void insert(BloodGlucoseRecord bloodGlucoseRecord) {

        mRepository.insert(bloodGlucoseRecord);
    }


    public void deleteRecord(BloodGlucoseRecord bloodGlucoseRecord) {

        mRepository.deleteRecord(bloodGlucoseRecord);
    }
}
