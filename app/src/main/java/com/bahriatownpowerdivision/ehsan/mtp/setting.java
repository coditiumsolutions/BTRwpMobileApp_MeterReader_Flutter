package com.bahriatownpowerdivision.ehsan.mtp;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by EHSAN on 12/11/2015.
 */
public class setting extends Activity implements OnItemSelectedListener {

    public static final String DB_NAME = "mrs.sqlite";
    //������� ��������� �������� ������� ���� ����� �� �����������
    public SQLiteDatabase database;
    private Button login;
    private EditText name;
    private EditText pwd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        login = (Button) findViewById(R.id.login);
        name = (EditText) findViewById(R.id.text2);
        pwd = (EditText) findViewById(R.id.text4);

        login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (name.getText().toString().equals("adb") && pwd.getText().toString().equals("3720")) {
                    Toast.makeText(getBaseContext(), "Configuration already set", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Wrong credential provide for login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}