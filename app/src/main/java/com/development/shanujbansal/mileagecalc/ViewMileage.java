package com.development.shanujbansal.mileagecalc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;


public class ViewMileage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mileage);

        // close the onscreenkeyboard in case it is opened.
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception ex) {
        }

        Bundle infoBundle = getIntent().getExtras();
        String selectedVehicle = "";
        if (infoBundle != null) {
            selectedVehicle = infoBundle.getString("SelectedVehicle");
        }

        Drawable backgroundImage = getResources().getDrawable(R.drawable.background);
        backgroundImage.setAlpha(25);
        LinearLayout mainActivityLayout = (LinearLayout) findViewById(R.id.viewMileageLL);
        mainActivityLayout.setBackgroundDrawable(backgroundImage);

        final TextView mileageFeedback = (TextView)findViewById(R.id.mileageFeedback);
        mileageFeedback.setVisibility(View.GONE);

        Spinner vehicleSpinner = (Spinner) findViewById(R.id.vehiclesList);
        ArrayList<String> vehiclesList = DatabaseHelper.getInstance(this).getVehiclesList();
        ArrayAdapter<String> vehiclesListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vehiclesList);
        vehicleSpinner.setAdapter(vehiclesListAdapter);

        if (!selectedVehicle.isEmpty()) {
            int spinnerPosition = vehiclesListAdapter.getPosition(selectedVehicle);
            vehicleSpinner.setSelection(spinnerPosition);
            spinnerPosition = 0;
        }

        vehicleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String vehicleSelected = parent.getSelectedItem().toString().trim();
                int selectedVehicleId = DatabaseHelper.getInstance(view.getContext()).getVehicleId(vehicleSelected);
                double expectedMileage = DatabaseHelper.getInstance(view.getContext()).getExpectedMileage(selectedVehicleId);
                double actualMileage = 0.0;

                HashMap<String, String> mileageInfo = DatabaseHelper.getInstance(view.getContext()).getVehicleMileage(selectedVehicleId);

                if (mileageInfo != null && mileageInfo.size() > 0) {
                    if (mileageInfo.containsKey("Mileage")) {
                        ((TextView) findViewById(R.id.vehicleMileage)).setText(mileageInfo.get("Mileage") + " Km/L");
                        actualMileage = Double.valueOf(mileageInfo.get("Mileage"));
                    }
                    if (mileageInfo.containsKey("ExpensePerKm")) ;
                    ((TextView) findViewById(R.id.expensePerKm)).setText("Rs. " + mileageInfo.get("ExpensePerKm"));
                }

                if(actualMileage>=expectedMileage){
                    mileageFeedback.setVisibility(View.VISIBLE);
                    mileageFeedback.setTextColor(Color.GREEN);
                    mileageFeedback.setText(getResources().getString(R.string.good_Mileage_feedback));
                }else {
                    mileageFeedback.setVisibility(View.VISIBLE);
                    mileageFeedback.setTextColor(Color.RED);
                    mileageFeedback.setText(getResources().getString(R.string.low_Mileage_feedback));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_mileage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_how_to_use:
                Intent usageIntent = new Intent(this, HowToUseActivity.class);
                //usageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                usageIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(usageIntent);
                return true;
            case R.id.action_info:
                Intent infoIntent = new Intent(this, InfoActivity.class);
                //infoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                infoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(infoIntent);
                return true;
            case R.id.action_share_app:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
