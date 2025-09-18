package com.example.fixnflex;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button login,signup,forgot;
    CheckBox rememberme;
    Button loginblue;
    Button register;
    LinearLayout resetbutton;
    TextView reseterror;
    EditText email,email2,fname,sname,resetemail;
    TextView emailerror,passerror,fnameerror,lastnameerror,emailerror2,passerror2,cpasserror;
    TextInputLayout password_text_input_layout,password_text_input_layout2,cpassword_text_input_layout;
    TextInputEditText password_text_input_edit_text,password_text_input_edit_text2,cpassword_text_input_edit_text;
    ViewFlipper viewFlipper;
    FirebaseAuth firebaseAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        rememberme=(CheckBox)findViewById(R.id.remember);

        login=(Button) findViewById(R.id.btn_login);
        signup=(Button) findViewById(R.id.btn_signin);
        forgot=(Button) findViewById(R.id.forgot);

        reseterror=(TextView)findViewById(R.id.reseterror);
        resetemail=(EditText)findViewById(R.id.resetemail);
        resetbutton=(LinearLayout)findViewById(R.id.resetbutton);

        emailerror=(TextView) findViewById(R.id.emailerror1);
        passerror=(TextView) findViewById(R.id.passerror1);
        fnameerror=(TextView) findViewById(R.id.firstnameerror);
        lastnameerror=(TextView) findViewById(R.id.lastnameerror);
        emailerror2=(TextView) findViewById(R.id.emailerror2);
        passerror2=(TextView) findViewById(R.id.passerror2);
        cpasserror=(TextView)findViewById(R.id.cpasserror);

        loginblue=(Button) findViewById(R.id.btn_loginblue);
        register=(Button)findViewById(R.id.registerbutton);

        firebaseAuth=FirebaseAuth.getInstance();

        fname=(EditText)findViewById(R.id.firstname);
        sname=(EditText)findViewById(R.id.lastname);
        email=(EditText)findViewById(R.id.emailtext1);
        email2=(EditText)findViewById(R.id.email2);
        password_text_input_layout=(TextInputLayout)findViewById(R.id.password_text_input_layout);
        password_text_input_edit_text=(TextInputEditText)findViewById(R.id.password_text_input_edit_text);
        password_text_input_layout2=(TextInputLayout)findViewById(R.id.password_text_input_layout2);
        password_text_input_edit_text2=(TextInputEditText)findViewById(R.id.password_text_input_edit_text2);
        cpassword_text_input_layout=(TextInputLayout)findViewById(R.id.cpassword_text_input_layout);
        cpassword_text_input_edit_text=(TextInputEditText)findViewById(R.id.cpassword_text_input_edit_text);


        email.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        password_text_input_edit_text.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        email2.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        password_text_input_edit_text2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        cpassword_text_input_edit_text.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        View toggle=password_text_input_layout.findViewById(com.google.android.material.R.id.text_input_end_icon);
        if(toggle!=null){
            toggle.setBackground(null);
        }
        View toggle2=password_text_input_layout2.findViewById(com.google.android.material.R.id.text_input_end_icon);
        if(toggle2!=null){
            toggle2.setBackground(null);
        }
        View toggle3 =cpassword_text_input_layout.findViewById(com.google.android.material.R.id.text_input_end_icon);
        if(toggle3!=null){
            toggle3.setBackground(null);
        }

        viewFlipper=(ViewFlipper) findViewById(R.id.viewFlipper);

        password_text_input_edit_text.setTypeface(ResourcesCompat.getCachedFont(this,R.font.kohshantareg), Typeface.BOLD);
        password_text_input_edit_text2.setTypeface(ResourcesCompat.getCachedFont(this,R.font.kohshantareg), Typeface.BOLD);
        cpassword_text_input_edit_text.setTypeface(ResourcesCompat.getCachedFont(this,R.font.kohshantareg), Typeface.BOLD);

        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        boolean remember = prefs.getBoolean("rememberMe", false);
        if(remember && firebaseAuth.getCurrentUser()!=null){
            Intent i1=new Intent(MainActivity.this, logout.class);
            startActivity(i1);
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setBackgroundResource(R.drawable.log_sin_inside);
                signup.setBackgroundColor(Color.TRANSPARENT);
                clearSignupForm();
                viewFlipper.setDisplayedChild(0);
                findViewById(R.id.main).requestFocus();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup.setBackgroundResource(R.drawable.log_sin_inside);
                login.setBackgroundColor(Color.TRANSPARENT);
                clearLoginForm();
                viewFlipper.setDisplayedChild(1);
                findViewById(R.id.main).requestFocus();
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setBackgroundColor(Color.TRANSPARENT);
                viewFlipper.setDisplayedChild(2);
            }
        });



        resetemail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    resetemail.setHint("");
                }
                else {
                    if(resetemail.getText().toString().isEmpty()){
                        resetemail.setHint("xyz@gmail.com");
                    }
                }
            }
        });
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    email.setHint("");
                }
                else {
                    if(email.getText().toString().isEmpty()){
                        email.setHint("xyz@gmail.com");
                    }
                }
            }
        });
        password_text_input_edit_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    password_text_input_edit_text.setHint("");
                }
                else {
                    if(password_text_input_edit_text.getText().toString().isEmpty()){
                        password_text_input_edit_text.setHint("∗∗∗∗∗∗∗∗∗∗");
                    }
                }
            }
        });
        fname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    fname.setHint("");
                }
                else {
                    if(fname.getText().toString().isEmpty()){
                        fname.setHint("XYZ name");
                    }
                }
            }
        });
        sname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    sname.setHint("");
                }
                else {
                    if(sname.getText().toString().isEmpty()){
                        sname.setHint("last name");
                    }
                }
            }
        });
        password_text_input_edit_text2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    password_text_input_edit_text2.setHint("");
                }
                else {
                    if(password_text_input_edit_text2.getText().toString().isEmpty()){
                        password_text_input_edit_text2.setHint("∗∗∗∗∗∗∗∗∗∗");
                    }
                }
            }
        });
        cpassword_text_input_edit_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    cpassword_text_input_edit_text.setHint("");
                }
                else {
                    if(cpassword_text_input_edit_text.getText().toString().isEmpty()){
                        cpassword_text_input_edit_text.setHint("∗∗∗∗∗∗∗∗∗∗");
                    }
                }
            }
        });
        email2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    email2.setHint("");
                }
                else {
                    if(email2.getText().toString().isEmpty()){
                        email2.setHint("abc@gmail.com");
                    }
                }
            }
        });




        loginblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailerror.setVisibility(View.GONE);
                passerror.setVisibility(View.GONE);
                String e1=email.getText().toString().trim();
                String e2=password_text_input_edit_text.getText().toString();
                if(e1.equals("")){
                    emailerror.setText("*Please provide a valid Email Address");
                    emailerror.setVisibility(View.VISIBLE);
                    return;
                }
                else {
                    if(e2.equals("")){
                        passerror.setText("*Please provide a valid Password");
                        passerror.setVisibility(View.VISIBLE);
                        emailerror.setVisibility(View.GONE);
                        return;
                    }
                    else {
                        passerror.setVisibility(View.GONE);
                        loginblue.setText("Logging in...");
                        firebaseAuth.signInWithEmailAndPassword(e1,e2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putBoolean("rememberMe", rememberme.isChecked());
                                    editor.apply();


                                    Intent i1=new Intent(MainActivity.this, logout.class);
                                    startActivity(i1);
                                    finish();

                                }
                                else {
                                    loginblue.setText("Log in");
                                    Toast.makeText(MainActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }

                }

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fnameerror.setVisibility(View.GONE);
                lastnameerror.setVisibility(View.GONE);
                emailerror2.setVisibility(View.GONE);
                passerror2.setVisibility(View.GONE);
                cpasserror.setVisibility(View.GONE);
                String fn=fname.getText().toString().trim();
                String ln=sname.getText().toString().trim();
                String e1=email2.getText().toString().trim();
                String e2=password_text_input_edit_text2.getText().toString();
                String e3=cpassword_text_input_edit_text.getText().toString();
                if(fn.isEmpty()){
                    fnameerror.setText("*Please provide a valid First Name");
                    fnameerror.setVisibility(View.VISIBLE);
                    return;
                }
                else{
                    fnameerror.setVisibility(View.GONE);
                }

                if(ln.isEmpty()){
                    lastnameerror.setText("*Please provide a valid Last Name");
                    lastnameerror.setVisibility(View.VISIBLE);
                    return;
                }
                else {
                    lastnameerror.setVisibility(View.GONE);
                }

                if(e1.isEmpty()){
                    emailerror2.setText("*Please provide a valid Email Address");
                    emailerror2.setVisibility(View.VISIBLE);
                    return;
                }
                else{
                    emailerror2.setVisibility(View.GONE);
                }

                if(e2.isEmpty()){
                    passerror2.setText("*Please provide a valid Password");
                    passerror2.setVisibility(View.VISIBLE);
                    return;
                }
                else {
                    passerror2.setVisibility(View.GONE);
                }

                if(e3.isEmpty()){
                    cpasserror.setText("*Please provide a valid Confirm Password");
                    cpasserror.setVisibility(View.VISIBLE);
                    return;
                }
                else {
                    cpasserror.setVisibility(View.GONE);
                }
                if(!e2.equals(e3)){
                    cpasserror.setText("*Password and Confirm Password must be same");
                    cpasserror.setVisibility(View.VISIBLE);
                    return;
                }
                else {
                    cpasserror.setVisibility(View.GONE);
                    register.setText("Registering...");
                    firebaseAuth.createUserWithEmailAndPassword(e1,e2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "You are Registered", Toast.LENGTH_SHORT).show();
                                viewFlipper.setDisplayedChild(0);
                            }
                            else {
                                register.setText("Register");
                                Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }




            }
        });
        resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reseterror.setVisibility(View.GONE);
                String e1=resetemail.getText().toString().trim();
                if(e1.isEmpty()){
                    reseterror.setText("*Please provide an Email Address");
                    reseterror.setVisibility(View.VISIBLE);
                    return;
                }
                else {
                    reseterror.setVisibility(View.GONE);
                }
                firebaseAuth.sendPasswordResetEmail(e1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            reseterror.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, "If this email is registered, a reset link has been sent.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            reseterror.setText("*Invalid Email Address");
                            reseterror.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    private void clearLoginForm() {
        email.setText("");
        password_text_input_edit_text.setText("");
        resetemail.setText("");

        // reset hints
        email.setHint("xyz@gmail.com");
        password_text_input_edit_text.setHint("∗∗∗∗∗∗∗∗∗∗");
        resetemail.setHint("xyz@gmail.com");

        // hide errors
        emailerror.setVisibility(View.GONE);
        passerror.setVisibility(View.GONE);
        reseterror.setVisibility(View.GONE);
    }

    private void clearSignupForm() {
        fname.setText("");
        sname.setText("");
        email2.setText("");
        password_text_input_edit_text2.setText("");
        cpassword_text_input_edit_text.setText("");
        resetemail.setText("");

        // reset hints
        fname.setHint("XYZ name");
        sname.setHint("last name");
        email2.setHint("abc@gmail.com");
        password_text_input_edit_text2.setHint("∗∗∗∗∗∗∗∗∗∗");
        cpassword_text_input_edit_text.setHint("∗∗∗∗∗∗∗∗∗∗");
        resetemail.setHint("xyz@gmail.com");

        // hide errors
        fnameerror.setVisibility(View.GONE);
        lastnameerror.setVisibility(View.GONE);
        emailerror2.setVisibility(View.GONE);
        passerror2.setVisibility(View.GONE);
        cpasserror.setVisibility(View.GONE);
        reseterror.setVisibility(View.GONE);
    }

}