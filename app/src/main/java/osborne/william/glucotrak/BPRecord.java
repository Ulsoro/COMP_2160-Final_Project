package osborne.william.glucotrak;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "BloodPressureTable")
public class BPRecord {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Long id;

    // Date and Time in Milliseconds
    @ColumnInfo(name = "date")
    public Long date;

    // Systolic Pressure
    @ColumnInfo(name="systolic")
    public int systolic;

    @ColumnInfo(name = "diastolic")
    public int diastolic;

    @ColumnInfo(name = "arm")
    String arm;

    @ColumnInfo(name = "notes")
    String notes;

    // Constructor without id
    public BPRecord(Long date, int systolic, int diastolic, String arm, String notes) {
        this.date = date;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.arm = arm;
        this.notes = notes;
    }

    // Constructor with id
    @Ignore
    public BPRecord(Long id, Long date, int systolic, int diastolic, String arm, String notes) {
        this.id = id;
        this.date = date;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.arm = arm;
        this.notes = notes;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public int getSystolic() {
        return systolic;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

    public String getArm() {
        return arm;
    }

    public void setArm(String arm) {
        this.arm = arm;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "osborne.william.glucotrak.BPRecord{" +
                "id=" + id +
                ", date=" + date +
                ", systolic=" + systolic +
                ", diastolic=" + diastolic +
                ", arm='" + arm + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
