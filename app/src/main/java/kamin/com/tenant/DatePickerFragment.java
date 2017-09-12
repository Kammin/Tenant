package kamin.com.tenant;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends android.support.v4.app.DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    DatePickerFragment datePickerFragment;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        datePickerFragment = this;
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR) - 30;
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        final TenantPage tenantPage = (TenantPage) getActivity();
        View v = getActivity().getLayoutInflater().inflate(R.layout.date_picker, null);
        final DatePicker picker = (DatePicker) v.findViewById(R.id.dialog_date_datePicker);
        v.findViewById(R.id.btOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Fragment ","btOk");
                tenantPage.etDateBirth.setText(picker.getMonth()+1+"/"+picker.getDayOfMonth()+"/"+picker.getYear());
                datePickerFragment.dismiss();
            }
        });
        v.findViewById(R.id.btCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerFragment.dismiss();
            }
        });
        return new AlertDialog.Builder(getActivity()).setView(v).create();
    }

/*    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR)-30;
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Dialog_MinWidth, this, year, month, day);
        DatePicker datePicker = (DatePicker)datePickerDialog.getLayoutInflater().inflate(R.layout.date_picker,null);
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getDatePicker().setSpinnersShown(true);
        datePickerDialog.setContentView(datePicker);
        return datePickerDialog;
    }*/

    public void onDateSet(DatePicker view, int year, int month, int day) {
    }
}
