package osborne.william.glucotrak;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class EditBloodGlucose extends AppCompatActivity {

    ImageButton cancelButton;
    ImageButton acceptButton;

    EditText bgConcentration;
    Spinner bgRelTime;
    EditText bgDate;
    EditText bgTime;
    EditText bgNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_blood_glucose);

        cancelButton = (ImageButton) findViewById(R.id.bgCancelImageButton);
        acceptButton = (ImageButton) findViewById(R.id.bgAcceptImageButton);

        bgConcentration = (EditText) findViewById(R.id.bgConcentrationEditText);
        bgRelTime = (Spinner) findViewById(R.id.bgRelativeTimeMeasuredSpinner);
        bgDate = (EditText) findViewById(R.id.bgDateEditText);
        bgTime = (EditText) findViewById(R.id.bgTimeEditText);
        bgNote = (EditText) findViewById(R.id.bgNotesEditText);



        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                BloodGlucoseDAO bgDao;
                BloodGlucoseRoomDatabase db = Room.databaseBuilder(getApplicationContext(), BloodGlucoseRoomDatabase.class, "bgData")
                        .allowMainThreadQueries()  // TODO: DO NOT ALLOW MAIN THREAD QUERIES!  This is awful.
                        .build();

                bgDao = db.bloodGlucoseDAO();

                double bsCon = Double.parseDouble(bgConcentration.getText().toString());
                String relTime = bgRelTime.getSelectedItem().toString();
                String notes = bgNote.getText().toString();

                bgDao.insert(new BloodGlucoseRecord(System.currentTimeMillis(), bsCon, relTime, notes));

                setResult(1);
                finish();
            }
        });


    }
}
