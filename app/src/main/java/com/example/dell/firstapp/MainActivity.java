package com.example.dell.firstapp;


import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.*;

public class MainActivity extends ActionBarActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    EditText edit1;
    Button button1;
    SeekBar s1;
    TextView rate,mpay;
    float value, mnth_val;
    RadioGroup rgrp;
    RadioButton rbut;
    double amt,tax=0;
    int j,k;
    int selectedID;
    double mnths;
    double  pay;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

        edit1 = (EditText)findViewById(R.id.amount_borrowed);
        //mpay.setText(Integer.toString(0));
        button1 = (Button)findViewById(R.id.calcbutton);
        s1 = (SeekBar)findViewById(R.id.seek1);
        rate = (TextView)findViewById(R.id.rate);
        rgrp = (RadioGroup)findViewById(R.id.radiogrp);
        mpay = (TextView)findViewById(R.id.mpay);
        final CheckBox chk1 = (CheckBox)findViewById(R.id.check1);
        button1.setOnClickListener(this);
        s1.setOnSeekBarChangeListener(this);
        value= s1.getProgress();
        rate.setText("Current:"+value);

        chk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chk1.isChecked()){
                    Toast.makeText(MainActivity.this,"0.1% will be added to your borrowed amount",Toast.LENGTH_SHORT).show();
                    j = 1;
                }
                else{
                    j = 0;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mortgage__calc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        value = s1.getProgress();
        mnth_val = value/1200;
        try {
            amt = Double.valueOf(edit1.getText().toString());
            //  Toast.makeText(Mortgage_Calc.this, "Amount borrowed: " + amt, LENGTH_SHORT).show();
        }catch (NumberFormatException e){
            edit1.setError("Amount cannot be left blank");
        }

        try {
            int selectedID = rgrp.getCheckedRadioButtonId();
            rbut = (RadioButton) findViewById(selectedID);
            mnths = Double.parseDouble(rbut.getText().toString());

        }catch (NullPointerException e){
            Toast.makeText(MainActivity.this,"Select Radio button", Toast.LENGTH_SHORT).show();
        }
        //      Toast.makeText(Mortgage_Calc.this,"Select Radio button", Toast.LENGTH_SHORT).show();


        if(mnths == 7.00){
            mnths = 7.00 * 12.00;
        }
        else if(mnths == 15.00){
            mnths = 15.00 * 12.00;
        }
        else if(mnths == 30.00){
            mnths = 30.00 * 12.00;
        }
        if(value != 0){
            if(j == 1){
                tax= 0.001 * amt;
            }
            pay = (amt * (mnth_val/(1-Math.pow((1+mnth_val),-mnths)))+tax);
            mpay.setText("Monthly Pay:"+pay);
        }
        else{
            if(j == 1){
                tax= 0.001 * amt;
            }
            pay = (amt/mnths)+tax;
            mpay.setText("Monthly Pay:"+pay);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        value = progress;
        rate.setText ("Current:"+value);
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
