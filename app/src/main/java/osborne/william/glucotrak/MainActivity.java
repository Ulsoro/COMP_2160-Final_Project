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
import java.util.Set;

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

    int currentButton; // Tracks which menu is currently in use
                       // 1 - Glucose
                       // 2 - Pressure
                       // 3 - A1C
                       // 4 - Medications
                       // 5 - Medical Contacts

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    static BloodGlucoseAdapter bgAdapter;
    static BPAdapter bpAdapter;

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
                switch (currentButton) {
                    case 1:
                        startActivity(new Intent(MainActivity.this, EditBloodGlucoseActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, EditBloodPressureActivity.class));
                        break;

                    // Buttons not currently implemented
                    case 3:
                    case 4:
                    case 5:
                    default:
                        break;
                }

            }
        });

        // Menu Selection Listeners
        mainStatsButton.setOnClickListener(menuListener);
        mainGlucoseButton.setOnClickListener(menuListener);
        mainPressureButton.setOnClickListener(menuListener);
        mainA1CButton.setOnClickListener(menuListener);
        mainMedicationButton.setOnClickListener(menuListener);
        mainMedicalContactButton.setOnClickListener(menuListener);



        // Set up RecyclerView Adapters
        recyclerView = findViewById(R.id.mainRecycler);

        bgAdapter = new BloodGlucoseAdapter(this);
        bpAdapter = new BPAdapter(this);

        recyclerView.setAdapter(bgAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bloodGlucoseViewModel = ViewModelProviders.of(this).get(BloodGlucoseViewModel.class);

        bloodGlucoseViewModel.getAllRecords().observe(this, new Observer<List<BloodGlucoseRecord>>() {
            @Override
            public void onChanged(@Nullable List<BloodGlucoseRecord> bloodGlucoseRecords) {
                bgAdapter.setBloodGlucose(bloodGlucoseRecords);
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


    // Lstener for the menu selection
    private View.OnClickListener menuListener = new View.OnClickListener() {
        @Override
        public void onClick(View btn) {

            Button clickedButton =(Button) btn;

            // Set menu items to default color
            mainGlucoseButton.setTextColor(getResources().getColor(R.color.colorBlack));
            mainPressureButton.setTextColor(getResources().getColor(R.color.colorBlack));

            // Different actions depending on which button is selected
            switch (btn.getId()) {

                case R.id.mainGlucoseButton:
                    if (currentButton != 1) {
                        // A Button other than Glucose is Selected
                        mainGlucoseButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                        currentButton = 1;

                        recyclerView.setAdapter(bgAdapter);
                    }
                    break;

                case R.id.mainPressureButton:
                    if (currentButton != 2) {
                        // A Button other than Pressure is Selected
                        mainPressureButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                        currentButton = 2;

                        recyclerView.setAdapter(bpAdapter);
                    }
                    break;

                // Buttons that are not yet implemented
                case R.id.mainStatsButton:
                case R.id.mainA1CButton:
                case R.id.mainMedicationButton:
                case R.id.mainContactButton:
                default:
                    Toast.makeText(getApplicationContext(), "This Feature is not yet implemented", Toast.LENGTH_LONG).show();
                    break;
            }

        }
    };
}
