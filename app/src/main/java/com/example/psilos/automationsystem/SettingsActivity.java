package com.example.psilos.automationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import function.SettingsActivityResetButton;
import function.SettingsSaveTheGuiRunningParametersActivity;
import function.SettingsActivityShowCurrentRunningParameters;
import service.BackgroundService;
import settings.AutomationSystemSettings;

/**
 * The type Settings activity.
 */
public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {


    private SettingsActivityShowCurrentRunningParameters settingsActivityShowCurrentRunningParameters;
    private SettingsSaveTheGuiRunningParametersActivity settingsSaveTheGuiRunningParametersActivity;
    private SettingsActivityResetButton settingsActivityResetButton;

    private  AutomationSystemSettings settings = AutomationSystemSettings.getInstance();
    private Button buttons[] = new Button[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        buttons[0] = (Button) findViewById(R.id.saveChanges);
        buttons[1] = (Button) findViewById(R.id.resetChanges);
        for(int i =0; i < 2; i++){
            buttons[i].setOnClickListener(this);
        }

        settingsActivityShowCurrentRunningParameters = new SettingsActivityShowCurrentRunningParameters(this);
        settingsSaveTheGuiRunningParametersActivity = new SettingsSaveTheGuiRunningParametersActivity(this);
        settingsActivityResetButton = new SettingsActivityResetButton(settingsActivityShowCurrentRunningParameters);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_exit:
                if(BackgroundService.serviceRunning){
                    Intent intent = new Intent(this, BackgroundService.class);
                    stopService(intent);
                }
                finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        settingsActivityShowCurrentRunningParameters.showTheCurrentRunningParameters();
    }

    @Override
    protected void onStop() {
        super.onStop();
        settings.saveToPreferences(this);
    }



    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.saveChanges)) {
            settingsSaveTheGuiRunningParametersActivity.saveTheGuiRunningParameters();

        }else if(v == findViewById(R.id.resetChanges)){
            settingsActivityResetButton.resetChanges();
        }
    }
}