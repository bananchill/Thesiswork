package com.example.shedule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.shedule.orlov.Module.NameGroup;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class SheduleGroupActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final String LOG_TAG = "MyLog";
    private final String FILENAMEGroup = "filegroupp.xml";

    private TextView txtViewChoosing;

    private EditText inputGroup;
    private Spinner spinnerGroup;

    private DrawerLayout drawer;
    private Toolbar toolbar;

    private Client client = new Client();

    private ArrayList<String> arrayNameOfGroup = new ArrayList<>();
    // private static MessageException messageException = new MessageException();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_shedule_activity);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        spinnerGroup = findViewById(R.id.spinner);
        txtViewChoosing = findViewById(R.id.textViewChoosingGroupMain);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this); // присваиваем всем элементам шторки слева id для передачи в метод onNavidation
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(actionBarDrawerToggle); // строка для выдвижной шторки слева
        actionBarDrawerToggle.syncState(); //синхронизация управления
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        completionSpinner(MyHandlerParsing.nameGroup);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            toolbar.setTitle(R.string.Shedule);
            startActivity(new Intent(SheduleGroupActivity.this, MainActivity.class));


        } else if (id == R.id.nav_gallery) {
            toolbar.setTitle(R.string.SheduleGroup);

        } else if (id == R.id.nav_slideshow) {
            toolbar.setTitle(R.string.SheduleTeahcer);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void completionSpinner(List<NameGroup> nameGroups) {
        for (NameGroup names : nameGroups) {
            arrayNameOfGroup.add(String.valueOf(names.getNameGroup()));
        }
        ArrayAdapter<String> nameGroupArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, arrayNameOfGroup);
        nameGroupArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerGroup.setAdapter(nameGroupArrayAdapter);

    }

    private void CheckMyGroup(String sad) {
        try {
            BufferedWriter bs = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(FILENAMEGroup, MODE_PRIVATE)));

            bs.write(sad);
            // закрываем поток
            bs.close();
            Log.d(LOG_TAG, "Файл записан");
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buttonChoose(View view) {
        String check = spinnerGroup.getSelectedItem().toString();
        CheckMyGroup(check);
        Intent intent = new Intent(SheduleGroupActivity.this, MainActivity.class);
intent.putExtra("Refresh", 1);
        startActivity(intent);
    }
}