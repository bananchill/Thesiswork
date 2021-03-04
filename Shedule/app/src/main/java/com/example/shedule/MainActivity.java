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
                CheckMyShedule("l");
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

    public void printLessons(List<GroupData> dataArrayList) {
        Log.d("Data data :", dayOfWeek);
        String weekday = null;

        for (GroupData groupData : dataArrayList) {
            //System.out.println(builder.append(groupData.getNameLesson()).append("\n"));
            if (groupData.getGroupDayID().equals(dayOfWeek)) {

                //System.out.println(builder.append(groupData.getGroupLessonsID()));
                switch (groupData.getGroupLessonsID()) {
                    case "1":
                        builder.append(groupData.getNameLesson()).append("\n").
                                append(groupData.getNameTeacher()).append("\n");
                        txtViewLessons.setText(builder.toString());
                        builder = new StringBuffer();
                    case "2":
                        builder.append(groupData.getNameTeacher()).
                                append(groupData.getNameLesson()).append("\n");
                        txtViewLessons2.setText(builder.toString());
                        builder = new StringBuffer();
                    case "3":
                        builder.append(groupData.getNameLesson()).append("\n").
                                append(groupData.getNameTeacher()).append("\n");
                        txtViewLessons3.setText(builder.toString());
                        builder = new StringBuffer();
                    case "4":
                        builder.append(groupData.getNameLesson()).append("\n").
                                append(groupData.getNameTeacher()).append("\n");
                        txtViewLessons4.setText(builder.toString());
                        builder = new StringBuffer();

                    case "5":
                        builder.append(groupData.getNameLesson()).append("\n").
                                append(groupData.getNameTeacher()).append("\n");
                        txtViewLessons5.setText(builder.toString());
                        builder = null;
                }
            }
        }
        txtViewDay.setText(new DateFormatSymbols().getShortWeekdays()[Integer.parseInt(dayOfWeek) + 1]);
    }

    private void CheckMyShedule(String data) {
        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(FILENAME, MODE_PRIVATE)));
            // пишем данные
            bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<YhZav>\n" +
                    "\t<Year>2021</Year>\n" +
                    "\t<Week>22</Week>\n" +
                    "\t<Numerator>True</Numerator>\n" +
                    "\t<Monday>25.01.2021</Monday>\n" +
                    "\t<Work>С 25/01/21 по 06/02/21</Work>\n" +
                    "\t<ListGroup>\n" +
                    "\t\t<Group Name=\"10-02к\">\n" +
                    "\t\t\t<Timetable>\n" +
                    "\t\t\t\t<Day N=\"1\">\n" +
                    "\t\t\t\t\t<Lesson N=\"1\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Экон.организации</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Попова Вера Михайловна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/306\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"2\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>ДОУ</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Елисеева Ольга Николаевна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"1/206\" Building=\"1 корпус (Балтийская, 35)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"3\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"2\">\n" +
                    "\t\t\t\t\t\t\t<Name>ИТ в ПД</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Король Юлия Александровна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"1/304*\" Building=\"1 корпус (Балтийская, 35)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t\t<Part N=\"2\" Podgr=\"1\">\n" +
                    "\t\t\t\t\t\t\t<Name>ИТ в ПД</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Смычкова Екатерина Викторовна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"1/304\" Building=\"1 корпус (Балтийская, 35)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"4\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Псих.соц-прав.деят.</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Тишкевич Светлана Борисовна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/301\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t</Day>\n" +
                    "\t\t\t\t<Day N=\"2\">\n" +
                    "\t\t\t\t\t<Lesson N=\"1\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Псих.соц-прав.деят.</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Тишкевич Светлана Борисовна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/301\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"2\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Сем.право</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Гребенкина Ольга Юрьевна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/207\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"3\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Гражд.процесс</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Гребенкина Ольга Юрьевна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/207\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"4\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Сем.право</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Гребенкина Ольга Юрьевна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/207\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t</Day>\n" +
                    "\t\t\t\t<Day N=\"3\">\n" +
                    "\t\t\t\t\t<Lesson N=\"2\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Физ.культура</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Жмырко Ирина Владимировна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"1/С.зал№1\" Building=\"1 корпус (Балтийская, 35)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"3\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"2\">\n" +
                    "\t\t\t\t\t\t\t<Name>Ин.яз.</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Шиленкова Татьяна Михайловна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/202\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t\t<Part N=\"2\" Podgr=\"1\">\n" +
                    "\t\t\t\t\t\t\t<Name>Ин.яз.</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Перн Ольга Евгеньевна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/302\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"4\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Прав.соц.обесп.</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Краскина Светлана Анатольевна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/211\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"5\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Гражд.право</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Краскина Светлана Анатольевна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/211\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t</Day>\n" +
                    "\t\t\t\t<Day N=\"4\">\n" +
                    "\t\t\t\t\t<Lesson N=\"1\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Сем.право</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Гребенкина Ольга Юрьевна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/207\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"2\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>БЖ</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Покудина Ирина Викторовна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"1/206\" Building=\"1 корпус (Балтийская, 35)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"3\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Статистика</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Ступак Наталья Алексеевна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"1/208\" Building=\"1 корпус (Балтийская, 35)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"4\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"1\">\n" +
                    "\t\t\t\t\t\t\t<Name>Информатика</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Лебедева Елена Николаевна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"1/304*\" Building=\"1 корпус (Балтийская, 35)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t\t<Part N=\"2\" Podgr=\"2\">\n" +
                    "\t\t\t\t\t\t\t<Name>Информатика</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Пяткова Елена Игоревна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"1/304\" Building=\"1 корпус (Балтийская, 35)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t</Day>\n" +
                    "\t\t\t\t<Day N=\"5\">\n" +
                    "\t\t\t\t\t<Lesson N=\"1\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Труд.право</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Батова Ольга Владимировна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/104\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"2\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Труд.право</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Батова Ольга Владимировна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/104\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"3\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Труд.право</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Батова Ольга Владимировна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/104\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t</Day>\n" +
                    "\t\t\t\t<Day N=\"8\">\n" +
                    "\t\t\t\t\t<Lesson N=\"1\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Экон.организации</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Попова Вера Михайловна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/206\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"2\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Псих.соц-прав.деят.</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Тишкевич Светлана Борисовна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/301\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"3\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"1\">\n" +
                    "\t\t\t\t\t\t\t<Name>ИТ в ПД</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Смычкова Екатерина Викторовна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"1/304\" Building=\"1 корпус (Балтийская, 35)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t\t<Part N=\"2\" Podgr=\"2\">\n" +
                    "\t\t\t\t\t\t\t<Name>ИТ в ПД</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Король Юлия Александровна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"1/304*\" Building=\"1 корпус (Балтийская, 35)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"4\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Менедж.</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Чернянская Ирина Владимировна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/101\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"5\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Псих.соц-прав.деят.</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Тишкевич Светлана Борисовна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/301\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t</Day>\n" +
                    "\t\t\t\t<Day N=\"9\">\n" +
                    "\t\t\t\t\t<Lesson N=\"2\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Гражд.процесс</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Гребенкина Ольга Юрьевна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/207\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"3\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>ДОУ</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Елисеева Ольга Николаевна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"1/225\" Building=\"1 корпус (Балтийская, 35)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"4\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Псих.соц-прав.деят.</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Тишкевич Светлана Борисовна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/301\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t</Day>\n" +
                    "\t\t\t\t<Day N=\"10\">\n" +
                    "\t\t\t\t\t<Lesson N=\"1\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Прав.соц.обесп.</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Краскина Светлана Анатольевна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/211\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"2\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Физ.культура</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Жмырко Ирина Владимировна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"1/С.зал№1\" Building=\"1 корпус (Балтийская, 35)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"3\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"2\">\n" +
                    "\t\t\t\t\t\t\t<Name>Ин.яз.</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Шиленкова Татьяна Михайловна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/202\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t\t<Part N=\"2\" Podgr=\"1\">\n" +
                    "\t\t\t\t\t\t\t<Name>Ин.яз.</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Перн Ольга Евгеньевна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/302\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t</Day>\n" +
                    "\t\t\t\t<Day N=\"11\">\n" +
                    "\t\t\t\t\t<Lesson N=\"1\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Прав.соц.обесп.</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Краскина Светлана Анатольевна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/211\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"2\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>БЖ</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Покудина Ирина Викторовна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"1/206\" Building=\"1 корпус (Балтийская, 35)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"3\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Менедж.</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Чернянская Ирина Владимировна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"1/208\" Building=\"1 корпус (Балтийская, 35)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"4\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"2\">\n" +
                    "\t\t\t\t\t\t\t<Name>Информатика</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Пяткова Елена Игоревна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"1/304\" Building=\"1 корпус (Балтийская, 35)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t\t<Part N=\"2\" Podgr=\"1\">\n" +
                    "\t\t\t\t\t\t\t<Name>Информатика</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Лебедева Елена Николаевна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"1/304*\" Building=\"1 корпус (Балтийская, 35)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t</Day>\n" +
                    "\t\t\t\t<Day N=\"12\">\n" +
                    "\t\t\t\t\t<Lesson N=\"1\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Гражд.право</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Краскина Светлана Анатольевна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"4/211\" Building=\"4 корпус (Швецова, 22)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t\t<Lesson N=\"2\">\n" +
                    "\t\t\t\t\t\t<Part N=\"1\" Podgr=\"0\">\n" +
                    "\t\t\t\t\t\t\t<Name>Статистика</Name>\n" +
                    "\t\t\t\t\t\t\t<Teacher>Ступак Наталья Алексеевна</Teacher>\n" +
                    "\t\t\t\t\t\t\t<Auditorium Number=\"1/225\" Building=\"1 корпус (Балтийская, 35)\"/>\n" +
                    "\t\t\t\t\t\t</Part>\n" +
                    "\t\t\t\t\t</Lesson>\n" +
                    "\t\t\t\t</Day>\n" +
                    "\t\t\t</Timetable>\n" +
                    "\t\t</Group>" +
                    "\t</ListGroup>\n" +
                    "</YhZav>");
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