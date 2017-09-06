package kamin.com.tenant;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TenantPage extends AppCompatActivity {
    EditText etAddress1, etAddress2, etCity, etDateBirth, etDriverLicense, etEmail, etFirstName, etLastName, etPassword, etPhone, etSSN, etState, etUsername, etZipCode;
    String username, password, firstName, lastName, address1, address2, city, state, zipCode, phone, email, driverLicense, dateBirth, ssn;
    Button btSave, btClear;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_page);
        context = getApplicationContext();
        initUI();
        btSave = (Button) findViewById(R.id.btSave);
        btClear = (Button) findViewById(R.id.btClear);
        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEnter();
            }
        });

    }


    void clear() {
        etAddress1.setText("");
        etAddress2.setText("");
        etCity.setText("");
        etDateBirth.setText("");
        etDriverLicense.setText("");
        etEmail.setText("");
        etFirstName.setText("");
        etLastName.setText("");
        etPassword.setText("");
        etPhone.setText("");
        etSSN.setText("");
        etState.setText("");
        etUsername.setText("");
        etZipCode.setText("");
    }

    void initUI() {
        etAddress1 = (EditText) findViewById(R.id.etAddress1);
        etAddress1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(getResources().getInteger(R.integer.address1Lentgh))});
        etAddress2 = (EditText) findViewById(R.id.etAddress2);
        etAddress2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(getResources().getInteger(R.integer.address2Lentgh))});
        etCity = (EditText) findViewById(R.id.etCity);
        etCity.setFilters(new InputFilter[] {new InputFilter.LengthFilter(getResources().getInteger(R.integer.cityLentgh))});
        etDateBirth = (EditText) findViewById(R.id.etDateBirth);
        etDateBirth.setFilters(new InputFilter[] {new InputFilter.LengthFilter(getResources().getInteger(R.integer.dateBirthLentgh))});
        etDriverLicense = (EditText) findViewById(R.id.etDriverLicense);
        etDriverLicense.setFilters(new InputFilter[] {new InputFilter.LengthFilter(getResources().getInteger(R.integer.driverLicenseLentgh))});
        etEmail = (EditText) findViewById(R.id.etEmail);
        etEmail.setFilters(new InputFilter[] {new InputFilter.LengthFilter(getResources().getInteger(R.integer.emailLentgh))});
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etFirstName.setFilters(new InputFilter[] {new InputFilter.LengthFilter(getResources().getInteger(R.integer.firstNameLentgh))});
        etLastName = (EditText) findViewById(R.id.etLastName);
        etLastName.setFilters(new InputFilter[] {new InputFilter.LengthFilter(getResources().getInteger(R.integer.lastNameLentgh))});
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword.setFilters(new InputFilter[] {new InputFilter.LengthFilter(getResources().getInteger(R.integer.passwordLentgh))});
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPhone.setFilters(new InputFilter[] {new InputFilter.LengthFilter(getResources().getInteger(R.integer.phoneLentgh))});
        etSSN = (EditText) findViewById(R.id.etSSN);
        etSSN.setFilters(new InputFilter[] {new InputFilter.LengthFilter(getResources().getInteger(R.integer.ssnLentgh))});
        etState = (EditText) findViewById(R.id.etState);
        etState.setFilters(new InputFilter[] {new InputFilter.LengthFilter(getResources().getInteger(R.integer.stateLentgh))});
        etUsername = (EditText) findViewById(R.id.etUsername);
        etUsername.setFilters(new InputFilter[] {new InputFilter.LengthFilter(getResources().getInteger(R.integer.usernameLentgh))});
        etZipCode = (EditText) findViewById(R.id.etZipCode);
        etZipCode.setFilters(new InputFilter[] {new InputFilter.LengthFilter(getResources().getInteger(R.integer.zipCodeLentgh))});
    }

    boolean checkEnter() {
        username = etFirstName.getText().toString();
        if(!username.equals(""))
            return LastName();
        else{
            Toast.makeText(this,R.string.FirstName_ERROR,Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    boolean LastName() {
        username = etLastName.getText().toString();
        if(!username.equals(""))
            return true;
        else{
            Toast.makeText(this,R.string.FirstName_ERROR,Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    //username, password, firstName, lastName, address1, address2, city, state, zipCode, phone, email, driverLicense, dateBirth, ssn;
}
