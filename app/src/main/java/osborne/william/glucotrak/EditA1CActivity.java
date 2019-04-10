package osborne.william.glucotrak;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditA1CActivity extends AppCompatActivity {

    ImageButton cancelButton;
    ImageButton acceptButton;

    EditText a1cAverageSugarConcentrationEditText;
    EditText a1cDateEditText;
    EditText a1cTimeEditText;
    EditText a1cNotesEditText;

    Intent intent;

    boolean editExisting;

    Calendar myCalendar;

    private A1CViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_a1_c);

        myCalendar = Calendar.getInstance();

        // Set up elements
        cancelButton = (ImageButton) findViewById(R.id.a1cCancelImageButton);
        acceptButton = (ImageButton) findViewById(R.id.a1cAcceptImageButton);

        a1cAverageSugarConcentrationEditText = (EditText) findViewById(R.id.a1cAverageSugarConcentrationEditText);
        a1cDateEditText = (EditText) findViewById(R.id.a1cDateEditText);
        a1cTimeEditText = (EditText) findViewById(R.id.a1cTimeEditText);
        a1cNotesEditText = (EditText) findViewById(R.id.a1cNotesEditText);

        myViewModel = ViewModelProviders.of(this).get(A1CViewModel.class);

        intent = getIntent();

        editExisting = intent.getBooleanExtra("existing", false);


        if (editExisting) {

            // Load the saved time into the calendar
            myCalendar.setTimeInMillis(intent.getLongExtra("date", 0));

            a1cAverageSugarConcentrationEditText.setText(String.valueOf(intent.getDoubleExtra("a1c", 0)));
            a1cNotesEditText.setText(intent.getStringExtra("notes"));
        }
        updateLabel();



        // Cancels edits and returns to Main Activity
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Accepts the Edits entered and inserts into the Database
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double a1cReading;
                try {
                    a1cReading = Double.parseDouble(a1cAverageSugarConcentrationEditText.getText().toString());
                }
                catch (NumberFormatException numException) {
                    a1cReading = 0.0;
                }

                long date = myCalendar.getTimeInMillis();
                String note = a1cNotesEditText.getText().toString();

                if (editExisting){
                    long id = intent.getLongExtra("id", System.currentTimeMillis());
                    myViewModel.insert(new A1CRecord(id, date, a1cReading, note));
                }
                else {
                    myViewModel.insert(new A1CRecord(date, a1cReading, note));
                }

                finish();
            }
        });

        // Updates the Calendar when the DatePicked selects a date
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        a1cDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditA1CActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Updates the Time when the TimePicker selects a time
        a1cTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                int minute = myCalendar.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EditA1CActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        myCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        myCalendar.set(Calendar.MINUTE, selectedMinute);
                        updateLabel();
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


    }


    // Updates the Date and Time with current values from the Calendar Object
    private void updateLabel() {
        String dateFormat = "dd-MMM-yyyy"; // Format for Date
        String timeFormat = "h:mm aa";
        SimpleDateFormat sdfDate = new SimpleDateFormat(dateFormat, Locale.US);
        SimpleDateFormat sdfTime = new SimpleDateFormat(timeFormat, Locale.US);

        a1cDateEditText.setText(sdfDate.format(myCalendar.getTime()));
        a1cTimeEditText.setText(sdfTime.format(myCalendar.getTime()));
    }
}
