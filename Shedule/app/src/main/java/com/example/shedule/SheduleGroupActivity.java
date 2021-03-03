package com.example.shedule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class SheduleGroupActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final String LOG_TAG = "MyLog";
    private final String FILENAME = "file";

    private TextView txtViewLessons;

    private EditText inputGroup;
    private Spinner spinnerGroup;

    private DrawerLayout drawer;
    private Toolbar toolbar;

    private Client client = new Client();

    private char[] arrayNameOfGroup = null;
    // private static MessageException messageException = new MessageException();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_shedule_activity);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        spinnerGroup = findViewById(R.id.spinner);

        ArrayAdapter<NameGroup> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, MyHandlerParsing.nameGroup);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGroup.setAdapter(adapter);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this); // присваиваем всем элементам шторки слева id для передачи в метод onNavidation
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(actionBarDrawerToggle); // строка для выдвижной шторки слева
        actionBarDrawerToggle.syncState(); //синхронизация управления
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
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
            readFile();

        } else if (id == R.id.nav_slideshow) {
            toolbar.setTitle(R.string.SheduleTeahcer);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void CheckMyShedule() {
        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(FILENAME, MODE_PRIVATE)));
            // пишем данные
            bw.write("Содержимое файла");
            // закрываем поток
            bw.close();
            Log.d(LOG_TAG, "Файл записан");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void readFile() {
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));
            String str = "";
            // читаем содержимое
            while ((str = br.readLine()) != null) {
                Log.d(LOG_TAG, str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}