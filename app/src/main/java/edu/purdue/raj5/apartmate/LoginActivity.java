package main.java.edu.purdue.raj5.apartmate;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button login;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // login();
        register();

    }

    private void register() {
        email = (EditText)findViewById(R.id.et_email);
        password = (EditText)findViewById(R.id.et_password);
        login = (Button)findViewById(R.id.bt_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),email.getText()+" "+password.getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login()
    {
        email = (EditText)findViewById(R.id.et_email);
        password = (EditText)findViewById(R.id.et_password);
        login = (Button)findViewById(R.id.bt_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),email.getText()+" "+password.getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}

