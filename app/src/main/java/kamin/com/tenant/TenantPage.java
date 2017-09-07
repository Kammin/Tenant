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
        etAddress1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.address1Lentgh))});
        etAddress2 = (EditText) findViewById(R.id.etAddress2);
        etAddress2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.address2Lentgh))});
        etCity = (EditText) findViewById(R.id.etCity);
        etCity.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.cityLentgh))});
        etDateBirth = (EditText) findViewById(R.id.etDateBirth);
        etDateBirth.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.dateBirthLentgh))});
        etDriverLicense = (EditText) findViewById(R.id.etDriverLicense);
        etDriverLicense.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.driverLicenseLentgh))});
        etEmail = (EditText) findViewById(R.id.etEmail);
        etEmail.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.emailLentgh))});
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etFirstName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.firstNameLentgh))});
        etLastName = (EditText) findViewById(R.id.etLastName);
        etLastName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.lastNameLentgh))});
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.passwordLentgh))});
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.phoneLentgh))});
        etSSN = (EditText) findViewById(R.id.etSSN);
        etSSN.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.ssnLentgh))});
        etState = (EditText) findViewById(R.id.etState);
        etState.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.stateLentgh))});
        etUsername = (EditText) findViewById(R.id.etUsername);
        etUsername.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.usernameLentgh))});
        etZipCode = (EditText) findViewById(R.id.etZipCode);
        etZipCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.zipCodeLentgh))});
    }

    boolean checkEnter() {
        firstName = etFirstName.getText().toString();
        if (firstName.length() != 0)
            return lastName();
        else {
            Toast.makeText(this, R.string.FirstName_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    boolean lastName() {
        lastName = etLastName.getText().toString();
        if (lastName.length() != 0)
            return adress1();
        else {
            Toast.makeText(this, R.string.LastName_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    boolean adress1() {
        address1 = etAddress1.getText().toString();
        if (address1.length() != 0)
            return adress2();
        else {
            Toast.makeText(this, R.string.Address1_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    boolean adress2() {
        address2 = etAddress2.getText().toString();
        if (address2.length() != 0)
            return city();
        else {
            Toast.makeText(this, R.string.Address2_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    boolean city() {
        city = etCity.getText().toString();
        if (city.length() != 0)
            return dateBirth();
        else {
            Toast.makeText(this, R.string.City_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    boolean dateBirth() {
        dateBirth = etDateBirth.getText().toString();
        if (dateBirth.length() != 0)
            if (isValidDateBirth(dateBirth))
                return driverLicense();
            else {
                Toast.makeText(this, R.string.DateBirth_ERROR2, Toast.LENGTH_SHORT).show();
                return false;
            }
        else {
            Toast.makeText(this, R.string.DateBirth_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    boolean driverLicense() {
        driverLicense = etDriverLicense.getText().toString();
        if (driverLicense.length() != 0)
            return email();
        else {
            Toast.makeText(this, R.string.DriverLicense_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    boolean email() {
        email = etEmail.getText().toString();
        if (email.length() != 0) {
            if (isValidEmailAddress(email))
                return pass();
            else {
                Toast.makeText(this, R.string.Email_ERROR2, Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, R.string.Email_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    boolean pass() {
        password = etPassword.getText().toString();
        if (password.length() != 0)
            return phone();
        else {
            Toast.makeText(this, R.string.Password_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    boolean phone() {
        phone = etPhone.getText().toString();
        if (phone.length() != 0)
            return ssn();
        else {
            Toast.makeText(this, R.string.Phone_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    boolean ssn() {
        ssn = etSSN.getText().toString();
        if (ssn.length() != 0)
            return state();
        else {
            Toast.makeText(this, R.string.SSN_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    boolean state() {
        state = etState.getText().toString();
        if (state.length() != 0)
            return username();
        else {
            Toast.makeText(this, R.string.State_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    boolean username() {
        username = etUsername.getText().toString();
        if (username.length() != 0)
            return zipCode();
        else {
            Toast.makeText(this, R.string.Username_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    boolean zipCode() {
        zipCode = etZipCode.getText().toString();
        if (zipCode.length() != 0)
            return true;
        else {
            Toast.makeText(this, R.string.ZipCode_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static boolean isValidEmailAddress(String email) {
        boolean stricterFilter = true;
        String stricterFilterString = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        String laxString = ".+@.+\\.[A-Za-z]{2}[A-Za-z]*";
        String emailRegex = stricterFilter ? stricterFilterString : laxString;
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(emailRegex);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isValidDateBirth(String dateBirth) {
        boolean valid = true;
        int count = 0;
        while((valid = true)&&(count < dateBirth.length())){
            if((!(((count==0)||(count==1)||(count==3)||(count==4)||(count==6)||(count==7)||(count==8)||(count==9))&&(dateBirth.substring(count, count+1).matches("[0-9]"))))||
                    (!(((count==3)||(count==5))&&(dateBirth.substring(count, count+1).matches("/")))))
                return false;
            count++;
        }
        return valid;
    }
}
