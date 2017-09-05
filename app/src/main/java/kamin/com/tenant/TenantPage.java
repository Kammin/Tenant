package kamin.com.tenant;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TenantPage extends AppCompatActivity {
    EditText etAddress1, etAddress2, etCity, etDateBirth, etDriverLicense, etEmail, etFirstName, etLastName, etPassword, etPhone, etSSN, etState, etTenant_ID, etUsername, etZipCode;
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
                finish();
            }
        });
        setListeners();
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
        etTenant_ID.setText("");
        etUsername.setText("");
        etZipCode.setText("");
    }

    void initUI() {
        etAddress1 = (EditText) findViewById(R.id.etAddress1);
        etAddress2 = (EditText) findViewById(R.id.etAddress2);
        etCity = (EditText) findViewById(R.id.etCity);
        etDateBirth = (EditText) findViewById(R.id.etDateBirth);
        etDriverLicense = (EditText) findViewById(R.id.etDriverLicense);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etSSN = (EditText) findViewById(R.id.etSSN);
        etState = (EditText) findViewById(R.id.etState);
        etTenant_ID = (EditText) findViewById(R.id.etTenant_ID);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etZipCode = (EditText) findViewById(R.id.etZipCode);
    }

    void setListeners(){
        etSSN.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("TextWatcher before "," charSequence "+charSequence+"  start "+i+"  count "+i1+"  after "+i2);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("TextWatcher Changed "," charSequence "+charSequence+"  start "+i+"  count "+i1+"  after "+i2);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("TextWatcher after ","editable "+ editable.toString());
                etSSN.removeTextChangedListener(this);

                etSSN.addTextChangedListener(this);
            }
        });
    }
}
