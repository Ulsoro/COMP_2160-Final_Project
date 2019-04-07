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
    private static A1CViewModel a1cViewModel;

    int currentButton; // Tracks which menu is currently in use
                       // 1 - Glucose
                       // 2 - Pressure
                       // 3 - A1C
                       // 4 - Medications
                       // 5 - Medical Contacts

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // Recyclerview adapters for each section
    static BloodGlucoseAdapter bgAdapter;
    static BPAdapter bpAdapter;
    static A1CAdapter a1CAdapter;

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
                    case 3:
                        startActivity(new Intent(MainActivity.this, EditA1CActivity.class));
                        break;

                    // Buttons not currently implemented

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
        a1CAdapter = new A1CAdapter(this);

        // Load the lost recycler adapter that the user viewed
        switch (currentButton) {
            case 1:
                recyclerView.setAdapter(bgAdapter);
                mainGlucoseButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 2:
                recyclerView.setAdapter(bpAdapter);
                mainPressureButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 3:
                recyclerView.setAdapter(a1CAdapter);
                mainA1CButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 4:
            case 5:
            default:
                break;
        }


        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Set up the adapters and view models for each list
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

        a1cViewModel = ViewModelProviders.of(this).get(A1CViewModel.class);

        a1cViewModel.getAllRecords().observe(this, new Observer<List<A1CRecord>>() {
            @Override
            public void onChanged(@Nullable List<A1CRecord> a1CRecords) {
                a1CAdapter.setA1C(a1CRecords);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Remember the last menu selection
        editor.putInt("MenuSelection", currentButton);
        editor.apply();
    }


    // Listener for the menu selection
    private View.OnClickListener menuListener = new View.OnClickListener() {
        @Override
        public void onClick(View btn) {

            // Set menu items to default color
            mainGlucoseButton.setTextColor(getResources().getColor(R.color.colorBlack));
            mainPressureButton.setTextColor(getResources().getColor(R.color.colorBlack));
            mainA1CButton.setTextColor(getResources().getColor(R.color.colorBlack));

            // Switch the recyclerview adapter to the correct menu choice
            // Change the color of the currently selected menu item to show it is selected
            switch (btn.getId()) {

                case R.id.mainGlucoseButton:
                    if (currentButton != 1) {
                        // A Button other than Glucose is Selected
                        currentButton = 1;

                        recyclerView.setAdapter(bgAdapter);
                    }
                    mainGlucoseButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                    break;

                case R.id.mainPressureButton:
                    if (currentButton != 2) {
                        // A Button other than Pressure is Selected
                        currentButton = 2;

                        recyclerView.setAdapter(bpAdapter);
                    }
                    mainPressureButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                    break;

                case R.id.mainA1CButton:
                    if (currentButton != 3) {
                        // A button other than A1C is currently selected
                        currentButton = 3;

                        recyclerView.setAdapter(a1CAdapter);
                    }
                    mainA1CButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                    break;

                // Buttons that are not yet implemented
                case R.id.mainStatsButton:
                case R.id.mainMedicationButton:
                case R.id.mainContactButton:
                default:
                    Toast.makeText(getApplicationContext(), "This Feature is not yet implemented", Toast.LENGTH_LONG).show();
                    break;
            }

        }
    };


    // Used by The BloodGlucoseAdapter to remove an item from the database
    public static void removeRecord(BloodGlucoseRecord bloodGlucoseRecord) {

        bloodGlucoseViewModel.deleteRecord(bloodGlucoseRecord);
    }

    // Used to remove Blood Pressure records
    public static void removeRecord(BPRecord bpRecord) {

        bpViewModel.deleteRecord(bpRecord);
    }

    // Used to remove A1C records
    public static void removeRecord(A1CRecord a1CRecord) {

        a1cViewModel.deleteRecord(a1CRecord);
    }

}
