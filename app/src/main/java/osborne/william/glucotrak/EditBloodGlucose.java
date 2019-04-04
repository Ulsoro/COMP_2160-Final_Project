package osborne.william.glucotrak;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

public class EditBloodGlucose extends AppCompatActivity {

    ImageButton cancelButton;
    ImageButton acceptButton;

    EditText bgConcentration;
    Spinner bgRelTime;
    EditText bgDate;
    EditText bgTime;
    EditText bgNote;

    Calendar date;

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


        bgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDateTimePicker();

            }
        });







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

    public void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(getApplicationContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        Log.v("EDIT BG", "The choosen one " + date.getTime());
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
        bgDate.setText(currentDate.toString());
    }
}
