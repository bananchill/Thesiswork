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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final String LOG_TAG = "MyLog";
    private final String FILENAME = "file";

    private DrawerLayout drawer;
    private TextView txtViewDay;
    private TextView txtViewLessons;
    private TextView txtViewLessons2;
    private TextView txtViewLessons3;
    private TextView txtViewLessons4;
    private TextView txtViewLessons5;
    private EditText inputGroup;
    private Toolbar toolbar;

    private String infConnectionServer = null;
    // private String infConnectionServer = null;

    private static int countConnection = 0;
    // private static MessageException messageException = new MessageException();

    private File newFile = new File(FILENAME);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        txtViewDay = findViewById(R.id.textView4);
        txtViewLessons = findViewById(R.id.textViewPara1);
        txtViewLessons2 = findViewById(R.id.textViewPara2);
        txtViewLessons3 = findViewById(R.id.textViewPara3);
        txtViewLessons4 = findViewById(R.id.textViewPara4);
        txtViewLessons5 = findViewById(R.id.textViewPara5);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this); // присваиваем всем элементам шторки слева id для передачи в метод onNavidation
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(actionBarDrawerToggle); // строка для выдвижной шторки слева
        actionBarDrawerToggle.syncState(); //синхронизация управления
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        try {
            if (countConnection >= 1) {
                countConnection++;
            } else {
                countConnection++;
                infConnectionServer = Client.ClientConnection();

                if (infConnectionServer != null) {
                    CheckMyShedule(infConnectionServer);
                } else Log.d("MyLog", "isEmpty");

                FileInputStream fin = openFileInput(FILENAME);
                SAXParserFactoryClass.SaxParserFactoryVoid(fin);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        printLessons(MyHandlerParsing.dataGroup);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

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

    Calendar date = Calendar.getInstance();
    String dayOfWeek = String.valueOf(date.get(Calendar.DAY_OF_WEEK) - 1);
    StringBuffer builder = new StringBuffer();

    public void printLessons(ArrayList<GroupData> dataArrayList) {
        Log.d("Data data :", dayOfWeek);
        boolean lsot = false;

        for (GroupData groupData : dataArrayList) {

            // System.out.println(groupData.getNameLesson());
            System.out.println("aaaaa" + groupData.getGroupDayID());

            if (dayOfWeek.equals(groupData.getGroupDayID()))
                lsot = true;
            else lsot = false;

            if (lsot) {
                switch (groupData.getGroupLessonsID()) {
                    case "1":
                        System.out.println("fgfggf" + groupData.getNameLesson());
                        builder.append(groupData.getNameLesson()).
                                append(groupData.getNameTeacher()).append("\n");
                        txtViewLessons.setText(builder + "");
                        break;

                    case "2":
                        System.out.println("fgfggf" + groupData.getNameLesson());
                        builder.append(groupData.getNameLesson()).append("\n").append(groupData.getNameTeacher()).append("\n").
                                append(groupData.getNameTeacher()).append("\n");
                        txtViewLessons2.setText(builder.toString());
                        break;

                    case "3":
                        System.out.println("fgfggf" + groupData.getNameLesson());
                        builder.append(groupData.getNameLesson()).append("\n").append(groupData.getNameTeacher()).append("\n").
                                append(groupData.getNameTeacher()).append("\n");
                        txtViewLessons3.setText(builder.toString());
                        break;

                    case "4":
                        System.out.println("fgfggf" + groupData.getNameLesson());
                        builder.append(groupData.getNameLesson()).append("\n").
                                append(groupData.getNameTeacher()).append("\n");
                        txtViewLessons4.setText(builder.toString());

                        break;
                    case "5":
                        System.out.println("fgfggf" + groupData.getNameLesson());
                        builder.append(groupData.getNameLesson()).append("\n").
                                append(groupData.getNameTeacher()).append("\n");
                        txtViewLessons5.setText(builder.toString());

                        lsot = false;
                }
            }

            builder = new StringBuffer();
        }
        builder = null;
        txtViewDay.setText(new DateFormatSymbols().getShortWeekdays()[Integer.parseInt(dayOfWeek) + 1]);
    }

    private void CheckMyShedule(String data) {
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
//                    openFileInput(FILENAME)));
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