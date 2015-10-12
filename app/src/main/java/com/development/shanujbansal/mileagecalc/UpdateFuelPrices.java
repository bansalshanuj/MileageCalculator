package com.development.shanujbansal.mileagecalc;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;


public class UpdateFuelPrices extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_fuel_prices);

        Drawable backgroundImage = getResources().getDrawable(R.drawable.background);
        backgroundImage.setAlpha(25);
        LinearLayout mainActivityLayout = (LinearLayout) findViewById(R.id.fuelPricesUpdateLL);
        mainActivityLayout.setBackgroundDrawable(backgroundImage);

        final Spinner regionSpinner = (Spinner) findViewById(R.id.regionSelectSpinner);
        ArrayList<String> regionsList = DatabaseHelper.getInstance(this).getRegionsList();
        regionSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, regionsList));
        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int regionId = regionSpinner.getSelectedItemPosition() + 1;
                HashMap<String, String> fuelPrices = DatabaseHelper.getInstance(view.getContext()).getFuelPrices(regionId);

                if (fuelPrices != null && fuelPrices.size() > 0) {
                    if (fuelPrices.containsKey("petrolPrice"))
                        ((EditText) findViewById(R.id.petrolPriceDisplay)).setText(fuelPrices.get("petrolPrice"));

                    if (fuelPrices.containsKey("dieselPrice"))
                        ((EditText) findViewById(R.id.dieselPriceDisplay)).setText(fuelPrices.get("dieselPrice"));

                    if (fuelPrices.containsKey("cngPrice"))
                        ((EditText) findViewById(R.id.cngPriceDisplay)).setText(fuelPrices.get("cngPrice"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button updateRegionFuelPrice = (Button) findViewById(R.id.updateRegionFuelPrice);
        updateRegionFuelPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to hide the keypad in case it's opened
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (Exception ex) {
                }

                double petrolPrice = Double.parseDouble(((EditText) findViewById(R.id.petrolPriceDisplay)).getText().toString().trim());
                double dieselPrice = Double.parseDouble(((EditText) findViewById(R.id.dieselPriceDisplay)).getText().toString().trim());
                double cngPrice = Double.parseDouble(((EditText) findViewById(R.id.cngPriceDisplay)).getText().toString().trim());
                int regionId = regionSpinner.getSelectedItemPosition() + 1;

                DatabaseHelper.getInstance(getApplicationContext()).updateRegionFuelPrices(petrolPrice, dieselPrice, cngPrice, regionId);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_fuel_prices, menu);
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
                Intent usageIntent = new Intent(this,HowToUseActivity.class);
                // usageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                usageIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(usageIntent);
                return true;
            case R.id.action_info:
                Intent infoIntent = new Intent(this,InfoActivity.class);
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