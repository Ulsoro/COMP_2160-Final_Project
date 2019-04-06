package osborne.william.glucotrak;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private FloatingActionButton fab;
    private Button mainStatsButton;
    private Button mainGlucoseButton;
    private Button mainPressureButton;
    private Button mainA1CButton;
    private Button mainMedicationButton;
    private Button mainMedicalContactButton;

    private static BloodGlucoseViewModel bloodGlucoseViewModel;
    private static BPViewModel bpViewModel;

    int currentButton;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set Up buttons
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mainStatsButton = (Button) findViewById(R.id.mainStatsButton);
        mainGlucoseButton = (Button) findViewById(R.id.mainGlucoseButton);
        mainPressureButton = (Button) findViewById(R.id.mainPressureButton);
        mainA1CButton = (Button) findViewById(R.id.mainA1CButton);
        mainMedicationButton = (Button) findViewById(R.id.mainMedicationButton);
        mainMedicalContactButton = (Button) findViewById(R.id.mainContactButton);

        // Set up Shared Preferences to remember menu choice
        sharedPreferences = getSharedPreferences("GlucoTrack", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // By Default open the Blood Glucose List.  Otherwise, if returning from another view,
        // open the correct list
        currentButton = sharedPreferences.getInt("MenuSelection", 1);



        // FAB Listener
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this, EditBloodGlucoseActivity.class));
            }
        });

        // Statistics Button Listener
        mainStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Statistics not yet Implemented", Toast.LENGTH_LONG).show();
            }
        });

        // Set up RecyclerView Adapter
        recyclerView = findViewById(R.id.mainRecycler);
        final BloodGlucoseAdapter adapter = new BloodGlucoseAdapter(this);
        final BPAdapter bpAdapter = new BPAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bloodGlucoseViewModel = ViewModelProviders.of(this).get(BloodGlucoseViewModel.class);

        bloodGlucoseViewModel.getAllRecords().observe(this, new Observer<List<BloodGlucoseRecord>>() {
            @Override
            public void onChanged(@Nullable List<BloodGlucoseRecord> bloodGlucoseRecords) {
                adapter.setBloodGlucose(bloodGlucoseRecords);
            }
        });


        bpViewModel = ViewModelProviders.of(this).get(BPViewModel.class);

        bpViewModel.getAllRecords().observe(this, new Observer<List<BPRecord>>() {
            @Override
            public void onChanged(@Nullable List<BPRecord> bpRecords) {
                bpAdapter.setBP(bpRecords);
            }
        });
    }


    // Used by The BloodGlucoseAdapter to remove an item from the database
    public static void removeRecord(BloodGlucoseRecord bloodGlucoseRecord) {

        bloodGlucoseViewModel.deleteRecord(bloodGlucoseRecord);
    }

    // User to remove Blood Pressure records
    public static void removeRecord(BPRecord bpRecord) {

        bpViewModel.deleteRecord(bpRecord);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Remember the last menu selection
        editor.putInt("MenuSelection", currentButton);
        editor.apply();
    }
}
