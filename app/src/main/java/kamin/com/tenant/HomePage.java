package kamin.com.tenant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class HomePage extends AppCompatActivity {
    ImageButton ibTenant, ibExit;
    Context context;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        int id = android.os.Process.myPid();
        android.os.Process.killProcess(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("EXIT", false)) {
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        context = getApplicationContext();
        ibTenant = (ImageButton) findViewById(R.id.ibTenant);
        ibExit = (ImageButton) findViewById(R.id.ibExit);
        ibTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, TenantPage.class);
                startActivity(i);
            }
        });
        ibExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        VolleySingleton.getInstance(this);
    }

}
