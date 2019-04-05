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
                 startActivity(new Intent(MainActivity.this, EditBloodGlucoseActivity.class));
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

        //bloodGlucoseViewModel.insert(new BloodGlucoseRecord(System.currentTimeMillis(), 11.2, "Before Breakfast", ""));
        //bloodGlucoseViewModel.insert(new BloodGlucoseRecord(System.currentTimeMillis(), 8.4, "Before Sleep", ""));
    }
}
