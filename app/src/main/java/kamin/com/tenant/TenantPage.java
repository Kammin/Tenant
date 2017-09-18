package kamin.com.tenant;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class TenantPage extends FragmentActivity {
    public EditText etAddress1, etAddress2, etCity, etDateBirth, etDriverLicense, etEmail, etFirstName, etLastName, etPhone, etSSN, etState, etZipCode;
    TextView tvTitle;
    ProgressBar progressBar;
    String firstName, lastName, address1, address2, city, state, zipCode, phone, email, driverLicense, dateBirth, ssn;
    Button btSave, btClose;
    Gson gson;
    Context context;
    Tenant tenant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_page);
        context = getApplicationContext();
        initUI();
        btSave = (Button) findViewById(R.id.btSave);
        btClose = (Button) findViewById(R.id.btClose);
        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEnter())
                    update();
            }
        });
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        addListeners();
        if ((tenant == null) && (connection()))
            LoadPersonalData("stravnikov@gmail.com");
    }


    private void addListeners() {
        etDateBirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    Bundle bundle = new Bundle();
                    Log.d("DateBirth", "on focus");
                    String d = etDateBirth.getText().toString();
                    String[] mmddyyyy = d.split("/");
                    if (mmddyyyy.length == 3)
                        for (int i = 0; i < mmddyyyy.length; i++) {
                            Log.d("DateBirth", mmddyyyy[i]);
                            int ix = Integer.valueOf(mmddyyyy[i]);
                            if ((ix > 0) && (i == 0))
                                bundle.putInt("month", ix - 1);
                            if ((ix > 0) && (i == 1))
                                bundle.putInt("day", ix);
                            if ((ix > 0) && (i == 2))
                                bundle.putInt("year", ix);
                        }
                    final DatePickerFragment newFragment = new DatePickerFragment();
                    newFragment.setArguments(bundle);
                    newFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
                    newFragment.show(getSupportFragmentManager(), "timePicker");
                }
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
        etPhone.setText("");
        etSSN.setText("");
        etState.setText("");
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
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.phoneLentgh))});
        etSSN = (EditText) findViewById(R.id.etSSN);
        etSSN.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.ssnLentgh))});
        etState = (EditText) findViewById(R.id.etState);
        etState.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.stateLentgh))});
        etZipCode = (EditText) findViewById(R.id.etZipCode);
        etZipCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.zipCodeLentgh))});
        etDateBirth.setKeyListener(null);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.Applicant));
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    public boolean connection() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
            return true;
        else {
            Toast.makeText(this, R.string.Connecteon_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean checkEnter() {
        if (connection())
            return FirstName();
        else {
            Toast.makeText(this, R.string.Connecteon_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    boolean FirstName() {
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
        return driverLicense();
    }

    boolean driverLicense() {
        driverLicense = etDriverLicense.getText().toString();
        return city();
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
                return email();
            else {
                Toast.makeText(this, R.string.DateBirth_ERROR2, Toast.LENGTH_SHORT).show();
                return false;
            }
        else {
            Toast.makeText(this, R.string.DateBirth_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    boolean email() {
        email = etEmail.getText().toString();
        if (email.length() != 0) {
            if (isValidEmailAddress(email))
                return phone();
            else {
                Toast.makeText(this, R.string.Email_ERROR2, Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, R.string.Email_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    boolean phone() {
        phone = etPhone.getText().toString();
        if (phone.length() != 0)
            if (phone.length() == 10)
                return ssn();
            else {
                Toast.makeText(this, R.string.Phone_Length_ERROR, Toast.LENGTH_SHORT).show();
                return false;
            }
        else {
            Toast.makeText(this, R.string.Phone_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    boolean ssn() {
        ssn = etSSN.getText().toString();
        if (ssn.length() != 0)
            if (ssn.length() == 9)
                return state();
            else {
                Toast.makeText(this, R.string.SSN_minLength_ERROR, Toast.LENGTH_SHORT).show();
                return false;
            }
        else {
            Toast.makeText(this, R.string.SSN_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    boolean state() {
        state = etState.getText().toString();
        if (state.length() != 0)
            return zipCode() ;
        else {
            Toast.makeText(this, R.string.State_ERROR, Toast.LENGTH_SHORT).show();
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

        String[] mmddyyyy = dateBirth.split("/");
        if (mmddyyyy.length == 3)
            for (int i = 0; i < mmddyyyy.length; i++) {
                int ix = Integer.valueOf(mmddyyyy[i]);
                if (i == 0) {
                    if ((ix < 1) || (ix > 12))
                        valid = false;
                }
                if (i == 1) {
                    if ((ix < 1) || (ix > 31))
                        valid = false;
                }
                if (i == 2) {
                    if (ix < 1800)
                        valid = false;
                }
            }
        else
            valid = false;
        return valid;
    }
    void fillFilds(Tenant tenant){
        etAddress1.setText(tenant.Address1);
        etAddress2.setText(tenant.Address2);
        etCity.setText(tenant.City);
        etDateBirth.setText(tenant.DateBirth);
        etDriverLicense.setText(tenant.DriverLicense);
        etEmail.setText(tenant.Email);
        etFirstName.setText(tenant.FirstName);
        etLastName.setText(tenant.LastName);
        etPhone.setText(tenant.Phone);
        etSSN.setText(tenant.SSN);
        etState.setText(tenant.State);
        etZipCode.setText(tenant.ZipCode);
    }
    void update() {
        tvTitle.setText(getResources().getString(R.string.Saving));
        progressBar.setVisibility(View.VISIBLE);
        JSONObject jsonBody = new JSONObject();
        Dialog dialogTransparent = new Dialog(this, android.R.style.Theme_Black);
        View view = LayoutInflater.from(this).inflate(
                R.layout.remove_border, null);
        dialogTransparent.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTransparent.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogTransparent.setContentView(view);

        final String JsonString = "{ \"username\":\"" + tenant.Username + "\",\"password\":\"" + tenant.Password + "\",\"FirstName\":\"" + firstName + "\",\"LastName\":\"" + lastName + "\",\"Address1\":\"" + address1 + "\",\"Address2\":\"" + address2 + "\",\"City\":\"" + city + "\",\"State\":\"" + state + "\",\"ZipCode\":\"" + zipCode + "\",\"Phone\":\"" + phone + "\",\"Email\":\"" + email + "\",\"DriverLicense\":\"" + driverLicense + "\",\"DateBirth\":\"" + dateBirth + "\",\"SSN\":\"" + ssn + "\"}";
        try {
            jsonBody = new JSONObject(JsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.PUT,
                URLs.update, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        tenant = gson.fromJson(response.toString(), Tenant.class);
                        fillFilds(tenant);
                        Toast.makeText(getApplicationContext(), "Data saved", Toast.LENGTH_SHORT).show();
                        tvTitle.setText(getResources().getString(R.string.Applicant));
                        progressBar.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                if(error.networkResponse.statusCode==500){
                    tvTitle.setText(getResources().getString(R.string.Applicant));
                    progressBar.setVisibility(View.GONE);
                }
            }
        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                if (response.statusCode != 200) {
                    Toast.makeText(getApplicationContext(), "Network Response Code" + response.statusCode, Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
                return super.parseNetworkResponse(response);
            }

            @Override
            public byte[] getBody() {
                try {
                    return JsonString.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", JsonString, "utf-8");
                    progressBar.setVisibility(View.GONE);
                    return null;
                }
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        request_json.setRetryPolicy(new DefaultRetryPolicy(2000,2,1f));
        VolleySingleton.getInstance(this).addToRequestQueue(request_json);
    }

    private void LoadPersonalData(String email) {
        tvTitle.setText(getResources().getString(R.string.Loading));
        progressBar.setVisibility(View.VISIBLE);
        JSONObject jsonBody = new JSONObject();

        final String JsonString = "{ \"Email\":\"" + email + "\"}";
        try {
            jsonBody = new JSONObject(JsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.PUT,
                URLs.get, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        tenant = gson.fromJson(response.toString(), Tenant.class);
                        fillFilds(tenant);
                        tvTitle.setText(getResources().getString(R.string.Applicant));
                        progressBar.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                tvTitle.setText(getResources().getString(R.string.Applicant));
                progressBar.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                if (response.statusCode != 200) {
                    Toast.makeText(getApplicationContext(), "Network Response Code" + response.statusCode, Toast.LENGTH_LONG).show();
                    tvTitle.setText(getResources().getString(R.string.Applicant));
                    progressBar.setVisibility(View.GONE);
                }
                return super.parseNetworkResponse(response);
            }

            @Override
            public byte[] getBody() {
                try {
                    return JsonString.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", JsonString, "utf-8");
                    return null;
                }
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(request_json);
    }

}
