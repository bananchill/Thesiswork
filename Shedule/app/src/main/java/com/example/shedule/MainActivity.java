package com.example.shedule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shedule.orlov.Module.GroupData;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final String LOG_TAG = "MyLog";
   // private final String FILENAME = "file.xml";

    private DrawerLayout drawer;
    private TextView txtViewLessons;
    private EditText inputGroup;
    private Toolbar toolbar;

    private Client client = new Client();
    private Object Exception;

    private String infConnectionServer = null;

    // private static MessageException messageException = new MessageException();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        txtViewLessons = findViewById(R.id.txtPars);

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
        client.ClientConnection();
        toolbar.setTitle(R.string.Shedule);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            toolbar.setTitle(R.string.Shedule);
            Toast.makeText(this, "fff", Toast.LENGTH_SHORT).show();
            client.ClientConnection();

        } else if (id == R.id.nav_gallery) {
            toolbar.setTitle(R.string.SheduleGroup);
            //readFile();
            startActivity(new Intent(MainActivity.this, SheduleGroupActivity.class));
        } else if (id == R.id.nav_slideshow) {
            toolbar.setTitle(R.string.SheduleTeahcer);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void printLessons(ArrayList<GroupData> datagroup) {
        StringBuilder builder = new StringBuilder();
        for (GroupData groupData : datagroup) {
            builder.append(groupData.getGroupDayID()).append("\n").
                    append(groupData.getGroupLessonsID()).append("\n").
                    append(groupData.getGroupAuditorium()).append("\n").
                    append(groupData.getGroupPodgr()).append("\n").
                    append(groupData.getNameLesson()).append("\n").
                    append(groupData.getNameTeacher()).append("\n");
        }
        txtViewLessons.setText(builder.toString());
    }

    public void CheckMyShedule(String FILENAME,String data) {
        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(FILENAME, MODE_PRIVATE)));
            // пишем данные
            bw.write(data);
            // закрываем поток
            bw.close();
            Log.d(LOG_TAG, "Файл записан");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void readFile() {
//        try {
//            // открываем поток для чтения
//            BufferedReader br = new BufferedReader(new InputStreamReader(
//                    openFileInput()));
//            String str = "";
//            // читаем содержимое
//            while ((str = br.readLine()) != null) {
//                Log.d(LOG_TAG, str);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}