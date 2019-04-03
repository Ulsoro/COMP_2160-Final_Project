package osborne.william.glucotrak;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "BloodGlucoseTable")
public class BloodGlucoseRecord {

    // Date and Time in Milliseconds
    @PrimaryKey
    @ColumnInfo(name = "date")
    public Long date;

    // Blood sugar Concentration
    @ColumnInfo(name = "bloodSugarConcentration")
    public double bloodSugarConcentration;

    // Relative tag for time measures
    @ColumnInfo(name = "relativeTime")
    public String relativeTime;

    // Additional notes
    @ColumnInfo(name = "notes")
    public String notes;

    public BloodGlucoseRecord(Long date, double bloodSugarConcentration, String relativeTime, String notes) {
        this.date = date;
        this.bloodSugarConcentration = bloodSugarConcentration;
        this.relativeTime = relativeTime;
        this.notes = notes;
    }

    // Getters and Setters
    public double getBloodSugarConcentration() {
        return bloodSugarConcentration;
    }

    public void setBloodSugarConcentration(float bloodSugarConcentration) {
        this.bloodSugarConcentration = bloodSugarConcentration;
    }

    public String getRelativeTime() {
        return relativeTime;
    }

    public void setRelativeTime(String relativeTime) {
        this.relativeTime = relativeTime;
    }

    public Long getDateTime() {
        return date;
    }

    public void setDateTime(Long dateTime) {
        this.date = dateTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "BloodGlucoseRecord{" +
                "date=" + date +
                ", bloodSugarConcentration=" + bloodSugarConcentration +
                ", relativeTime='" + relativeTime + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
