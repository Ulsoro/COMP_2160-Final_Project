package osborne.william.glucotrak;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private FloatingActionButton fab;

    private BloodGlucoseViewModel bloodGlucoseViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, EditBloodGlucose.class), 1);
            }
        });

        recyclerView = findViewById(R.id.mainRecycler);
        final BloodGlucoseAdapter adapter = new BloodGlucoseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bloodGlucoseViewModel = ViewModelProviders.of(this).get(BloodGlucoseViewModel.class);

        bloodGlucoseViewModel.getAllRecords().observe(this, new Observer<List<BloodGlucoseRecord>>() {
            @Override
            public void onChanged(@Nullable List<BloodGlucoseRecord> bloodGlucoseRecords) {
                adapter.setBloodGlucose(bloodGlucoseRecords);
            }
        });

        // bloodGlucoseViewModel.insert(new BloodGlucoseRecord(System.currentTimeMillis(), 11.2, "Before Breakfast", ""));
        // bloodGlucoseViewModel.insert(new BloodGlucoseRecord(System.currentTimeMillis(), 8.4, "Before Sleep", ""));

        /*
        BloodGlucoseDAO bgDao;

        recyclerView = findViewById(R.id.mainRecycler);

        BloodGlucoseRoomDatabase db = Room.databaseBuilder(getApplicationContext(), BloodGlucoseRoomDatabase.class, "bgData")
                .allowMainThreadQueries()  // TODO: DO NOT ALLOW MAIN THREAD QUERIES!
                .build();

        bgDao = db.bloodGlucoseDAO();

        // bgDao.deleteAll();
        //bgDao.insert(new BloodGlucoseRecord(System.currentTimeMillis(), 11.2, "Before Bed", ""));
        //bgDao.insert(new BloodGlucoseRecord((long)4, 5.2, "Breakfast", ""));

        // bgDao.insert(new BloodGlucoseRecord(System.currentTimeMillis(), 6.6, "Before Lunch", "A Curious Note."));

        List<BloodGlucoseRecord> bgRecords = db.bloodGlucoseDAO().getAllRecords();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BloodGlucoseAdapter(bgRecords);
        recyclerView.setAdapter(adapter);


        for (int i = 0; i < bgRecords.size(); i++) {
            Log.d("BG", "" + bgRecords.get(i).toString());
        }
        */

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1) {
            recyclerView.setAdapter(adapter);

        }
    }
}
