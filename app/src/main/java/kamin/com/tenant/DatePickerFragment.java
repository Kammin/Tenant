package kamin.com.tenant;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    DatePickerFragment datePickerFragment;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        datePickerFragment = this;
        final Calendar c = Calendar.getInstance();
        Bundle args = getArguments();
        int year = args.getInt("year",c.get(Calendar.YEAR) - 30);
        int month = args.getInt("month",c.get(Calendar.MONTH));
        int day = args.getInt("day",c.get(Calendar.DAY_OF_MONTH));
        final TenantPage tenantPage = (TenantPage) getActivity();
        View v = getActivity().getLayoutInflater().inflate(R.layout.date_picker, null);
        final DatePicker picker = (DatePicker) v.findViewById(R.id.dialog_date_datePicker);
        picker.setMinDate(System.currentTimeMillis()-6151680000000l);
        picker.setMaxDate(System.currentTimeMillis());
        picker.updateDate(year,month,day);
        v.findViewById(R.id.btOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Fragment ","btOk");
                tenantPage.etDateBirth.setText(picker.getMonth()+1+"/"+picker.getDayOfMonth()+"/"+picker.getYear());
                tenantPage.etPhone.requestFocus();
                datePickerFragment.dismiss();
            }
        });
        v.findViewById(R.id.btCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tenantPage.etPhone.requestFocus();
                datePickerFragment.dismiss();
            }
        });
        return new AlertDialog.Builder(getActivity()).setView(v).create();
    }



    public void onDateSet(DatePicker view, int year, int month, int day) {
    }
}
