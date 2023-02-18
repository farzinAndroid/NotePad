package com.farzin.notepad2.Utils;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.farzin.notepad2.AppConfig.AppConfig;
import com.farzin.notepad2.R;


public class Dialogs {





    public static void showDialog(Activity activity){

        AppConfig appConfig = new AppConfig(activity);

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        View view = View.inflate(activity,R.layout.dialog_layout,null);
        alert.setView(view);

        AppCompatButton btn_cancel , btn_login;
        AppCompatEditText edt_name, edt_pass;
        edt_name = view.findViewById(R.id.edt_name);
        edt_pass = view.findViewById(R.id.edt_pass);
        btn_cancel = view.findViewById(R.id.btncancel);
        btn_login = view.findViewById(R.id.btnlogin);

        edt_name.setText(appConfig.saveUser() + "");
        edt_pass.setText(appConfig.savePassword() + "");


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finishAffinity();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = edt_name.getText().toString();
                String pass = edt_pass.getText().toString();

                if (username.equals("android") && pass.equals("1234")){

                    appConfig.saveUserPass(username,pass);

                }else
                    Toast.makeText(activity, "user or password is wrong", Toast.LENGTH_SHORT).show();



            }
        });

        alert.show();
    }


}
