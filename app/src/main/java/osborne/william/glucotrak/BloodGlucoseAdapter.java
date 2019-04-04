package osborne.william.glucotrak;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

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
        View itemView = mInflater.inflate(R.layout.blood_glucose_row, parent, false);
        return new BloodGlucoseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BloodGlucoseViewHolder holder, int position) {
        if (mBGList != null) {
            BloodGlucoseRecord current = mBGList.get(position);
            holder.dateTextView.setText(String.valueOf(current.getDate()));
            holder.relativeTimeTextView.setText(current.getRelativeTime());
            holder.bloodGlucoseReadingTextView.setText(String.valueOf(current.getBloodSugarConcentration()));
        } else {
            // Covers the case of data not being ready yet.
            holder.dateTextView.setText("No Date");
            holder.relativeTimeTextView.setText("No Relative Time");
            holder.bloodGlucoseReadingTextView.setText("No Glucose Reading");
        }
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