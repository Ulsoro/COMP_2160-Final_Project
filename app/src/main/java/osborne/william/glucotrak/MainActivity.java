package osborne.william.glucotrak;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    FloatingActionButton fab;


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

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1) {
            recyclerView.setAdapter(adapter);

        }
    }
}
