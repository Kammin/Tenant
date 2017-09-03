package kamin.com.tenant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class HomePage extends AppCompatActivity {
    ImageButton ibTenant;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        context = getApplicationContext();
        ibTenant = (ImageButton) findViewById(R.id.ibTenant);
        ibTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, TenantPage.class);
                startActivity(i);
            }
        });
    }

}
