package tabitha.com.asthmacarenew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import io.paperdb.Paper;

public class Login extends AppCompatActivity {

    private EditText inputEmail,inputPassword;
    private Button btn_login;
    private ProgressDialog loadingBar;
    //private TextView adminLink,notAdminLink;

    private String parentDBName="User";
    private CheckBox checkRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        btn_login=(Button)findViewById(R.id.btn_login);
        inputEmail=(EditText)findViewById(R.id.input_login_Email);
        inputPassword=(EditText)findViewById(R.id.input_login_password);
        loadingBar=new ProgressDialog(this);
        //adminLink=(TextView)findViewById(R.id.link_adminPanel);
        //notAdminLink=(TextView)findViewById(R.id.link_not_adminPanel);

        checkRememberMe=(CheckBox)findViewById(R.id.remember_me_checkbox);
        Paper.init(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                loginUser();

            }
        });

        /*adminLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                btn_login.setText("Login Admin");
                adminLink.setVisibility(View.INVISIBLE);
                notAdminLink.setVisibility(View.VISIBLE);
                parentDBName="Admins";
            }
        });
        notAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
             btn_login.setText("Login");
             adminLink.setVisibility(View.VISIBLE);
             notAdminLink.setVisibility(View.INVISIBLE);
             parentDBName="Users";
            }
        });*/
    }

    private void loginUser()
    {
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter your email...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please input a password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait,while we check your credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessAccount(email,password);
        }
    }

    private void AllowAccessAccount(final String email, final String password)
    {
        if(checkRememberMe.isChecked())
        {
            Paper.book().write(Online.userEmailkey,email);
            Paper.book().write(Online.userPasswordkey,password);
        }
        final DatabaseReference rootRef;
        rootRef= FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child(parentDBName).child(email).exists())
                {
                    User usersData=dataSnapshot.child(parentDBName).child(email).getValue(User.class);

                    if(usersData.getEmail().equals(email))
                    {
                        if(usersData.getPassword().equals(password))
                        {
                            if(parentDBName.equals("User"))
                            {
                                Toast.makeText(Login.this, "Logged in successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                startActivity(new Intent(Login.this,MainActivity.class));
                                Online.onlineUsers=usersData;
                            }

                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(Login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(Login.this, "Account with this " + email + "don't exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    //Toast.makeText(login.this, "Create another account", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}