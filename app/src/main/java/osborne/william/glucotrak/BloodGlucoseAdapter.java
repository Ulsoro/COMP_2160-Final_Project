package osborne.william.glucotrak;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class BloodGlucoseAdapter extends RecyclerView.Adapter<BloodGlucoseAdapter.BloodGlucoseViewHolder> {



    class BloodGlucoseViewHolder extends RecyclerView.ViewHolder {

        private TextView dateTextView;
        private TextView relativeTimeTextView;
        private TextView bloodGlucoseReadingTextView;

        private BloodGlucoseViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.bgDateTextView);
            relativeTimeTextView = itemView.findViewById(R.id.bgRelativeTimeTextView);
            bloodGlucoseReadingTextView = itemView.findViewById(R.id.bgBloodGlucoseTextView);
        }
    }

    private final LayoutInflater mInflater;
    private List<BloodGlucoseRecord> mBGList; // Cached copy of Blood Glucose Readings

    BloodGlucoseAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public BloodGlucoseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.blood_glucose_row, parent, false);
        return new BloodGlucoseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BloodGlucoseViewHolder holder, int position) {

        final BloodGlucoseRecord current = mBGList.get(position);

        if (mBGList != null) {

            holder.dateTextView.setText(DateFormat.format("dd-MM-yyyy hh:mm a", current.getDate()).toString());
            holder.relativeTimeTextView.setText(current.getRelativeTime());
            holder.bloodGlucoseReadingTextView.setText(String.valueOf(current.getBloodSugarConcentration()));
        } else {
            // Covers the case of data not being ready yet.
            holder.dateTextView.setText("No Date");
            holder.relativeTimeTextView.setText("No Relative Time");
            holder.bloodGlucoseReadingTextView.setText("No Glucose Reading");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, EditBloodGlucoseActivity.class);
                intent.putExtra("existing", true);
                intent.putExtra("id", current.getId());
                intent.putExtra("bgcon", current.getBloodSugarConcentration());
                intent.putExtra("date", current.getDate());
                intent.putExtra("relativeTime", current.getRelativeTime());
                intent.putExtra("notes", current.getNotes());
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                MainActivity.removeRecord(current);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Delete Record?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();


                return false;
            }
        });
    }

    void setBloodGlucose(List<BloodGlucoseRecord> bgList){
        mBGList = bgList;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mBGList != null)
            return mBGList.size();
        else return 0;
    }
}