package osborne.william.glucotrak;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class A1CViewModel extends AndroidViewModel {
    
    private A1CRepository mRepository;

    private LiveData<List<A1CRecord>> a1cRecords;

    public A1CViewModel (Application application) {
        super(application);
        mRepository = new A1CRepository(application);
        a1cRecords = mRepository.getAllRecords();
    }

    LiveData<List<A1CRecord>> getAllRecords() {
        return a1cRecords;
    }

    public void insert(A1CRecord a1cRecord) {
        mRepository.insert(a1cRecord);
    }

    public void deleteRecord(A1CRecord a1cRecord) {
        mRepository.deleteRecord(a1cRecord);
    }
}
