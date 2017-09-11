package kamin.com.tenant;


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {


    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.HOUR;
        int minute = c.MINUTE;
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),this,hour, minute,true);
        return timePickerDialog;
    }
}
