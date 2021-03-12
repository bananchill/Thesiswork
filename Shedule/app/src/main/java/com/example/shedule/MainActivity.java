package com.example.shedule;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.shedule.orlov.Module.GroupData;
import com.example.shedule.orlov.Module.ReplacementData;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PrintLessons {
    private final String LOG_TAG = "MyLog";
    private final String FILENAMEShedule = "fileshedule.xml";
    private final String FILENAMEReplacementShedule = "filePrplacementShedule.xml";
    private final String FILENAMEGroup = "filegroupp.xml";

    private DrawerLayout drawer;

    private TextView txtViewDay;
    private TextView txtViewLessons;
    private TextView textViewNameOfWeek;
    private TextView txtViewLessons2;
    private TextView txtViewLessons3;
    private TextView txtViewLessons4;
    private TextView txtViewLessons5;
    private TextView txtViewLessons6;

    public TextView editchoosingGroupmain;

    private EditText inputGroup;

    private Toolbar toolbar;

    private String checkingTheDayForANull = null;
    private final String checkingTheDayForANullReplacement = null;

    boolean lsot = false;

    private FileInputStream fin = null;

    private Calendar date = Calendar.getInstance();
    private Date timeOfDay = new Date();


    @RequiresApi(api = Build.VERSION_CODES.O)
    private final String calendarDayNowDate = String.valueOf(LocalDateTime.now().toLocalDate());
    int days = 0;
    int week = 0;

    private String dayOfWeek = String.valueOf(date.get(Calendar.DAY_OF_WEEK) - 1);
    private StringBuffer builder = new StringBuffer();


    private final int HourOfDay = parseInt(String.valueOf(timeOfDay.getHours()));
    private final int minutesOfHour = parseInt(String.valueOf(timeOfDay.getMinutes()));


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        editchoosingGroupmain = findViewById(R.id.textViewChoosingGroupMain);

        DifferenceDate();

        try {
            //System.out.println(date.get(Calendar.DAY_OF_WEEK));
            String radingShedul = null;
            if (String.valueOf(date.get(Calendar.DAY_OF_WEEK)).equals("7")) {
                radingShedul = Client.ClientConnection("C:\\Users\\maksm\\Desktop\\xml\\3.xml");
                CheckMyShedule(radingShedul);
                fin = openFileInput(FILENAMEShedule);
                MethodSaxAndRead(fin, "shedule");
                fin.close();
            }
            if (!readFileSD()) {
                radingShedul = Client.ClientConnection("C:\\Users\\maksm\\Desktop\\xml\\3.xml");
                startActivity(new Intent(this, SheduleGroupActivity.class));
                if (radingShedul == null) {
                    Log.d("MyLog", "isEmpty");
                }
                CheckMyShedule(radingShedul);
                fin = openFileInput(FILENAMEShedule);
                MethodSaxAndRead(fin, "shedule");
                fin.close();
            } else {
                fin = openFileInput(FILENAMEShedule);
                MethodSaxAndRead(fin, "shedule");
                fin.close();
            }
            if (HourOfDay >= 14 && minutesOfHour == 0 || minutesOfHour == 6 && HourOfDay <= 23) {
                String readReplacementScheduleClient = Client.ClientConnection("C:\\Users\\maksm\\Desktop\\xml\\dop.xml");
                CheckMyReplacementShedule(readReplacementScheduleClient);
            } else {
                fin = openFileInput(FILENAMEReplacementShedule);
                MethodSaxAndRead(fin, "replacement");
                fin.close();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        txtViewDay = findViewById(R.id.textView4);
        textViewNameOfWeek = findViewById(R.id.textViewNameOfWeek);
        txtViewLessons = findViewById(R.id.textViewPara1);
        txtViewLessons2 = findViewById(R.id.textViewPara2);
        txtViewLessons3 = findViewById(R.id.textViewPara3);
        txtViewLessons4 = findViewById(R.id.textViewPara4);
        txtViewLessons5 = findViewById(R.id.textViewPara5);
        txtViewLessons6 = findViewById(R.id.textViewPara6);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this); // присваиваем всем элементам шторки слева id для передачи в метод onNavidation
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(actionBarDrawerToggle); // строка для выдвижной шторки слева
        actionBarDrawerToggle.syncState(); //синхронизация управления
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        printLessonsShedule("");

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


    @Override
    public void printLessonsShedule(String next) {
        if (week / 2 != 0) {
            dayOfWeek = String.valueOf(parseInt(dayOfWeek) + 7);
        }
        switch (next) {
            case "next":
                dayOfWeek = String.valueOf(parseInt(dayOfWeek) + 1);
                if(parseInt(dayOfWeek) > 14)
                    dayOfWeek = String.valueOf(parseInt(dayOfWeek) -14);
                break;
            case "thePast":
                if (!dayOfWeek.equals("-1")) {
                    dayOfWeek = String.valueOf(parseInt(dayOfWeek) - 1);
                } else {
                    dayOfWeek = String.valueOf(parseInt(dayOfWeek) + 7);
                } if(parseInt(dayOfWeek) <= -1)
                dayOfWeek = String.valueOf(parseInt(dayOfWeek) +15);
                break;
            default:
                dayOfWeek = String.valueOf(date.get(Calendar.DAY_OF_WEEK) );
        }
        ClearTextView();
        for (GroupData groupData : MyHandlerParsing.dataGroup) {
            if (!groupData.getGroupDayID().isEmpty()) {
                checkingTheDayForANull = groupData.getGroupDayID();
            }

            lsot = dayOfWeek.equals(checkingTheDayForANull);
            if (lsot) {
                System.out.println("getNameLesson " + groupData.getNameLesson());
                switch (groupData.getGroupLessonsID()) {
                    case "1":

                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 10, "").append("\n").
                                append(groupData.getGroupAuditorium());
                        txtViewLessons.setText(builder);
                        txtViewLessons.setTextColor(Color.WHITE);
                        break;
                    case "2":

                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 8, "").append("\n").
                                append(groupData.getGroupAuditorium());
                        txtViewLessons2.setText(builder);
                        txtViewLessons3.setTextColor(Color.WHITE);
                        break;
                    case "3":

                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 5, "").append("\n").
                                append(groupData.getGroupAuditorium());
                        txtViewLessons3.setText(builder);
                        txtViewLessons3.setTextColor(Color.WHITE);
                        break;
                    case "4":
                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 5, "").append("\n").
                                append(groupData.getGroupAuditorium());
                        txtViewLessons4.setText(builder);
                        txtViewLessons4.setTextColor(Color.WHITE);
                        break;
                    case "5":

                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 5, "").append("\n").
                                append(groupData.getGroupPodgr());
                        txtViewLessons5.setText(builder);
                        txtViewLessons5.setTextColor(Color.WHITE);
                        break;
                    case "6":
                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 5, "").append("\n").
                                append(groupData.getGroupLessonsID());
                        txtViewLessons6.setText(builder);
                        txtViewLessons6.setTextColor(Color.WHITE);
                        break;
                }

                builder = new StringBuffer();
            }

            lsot = false;
        }
        printReplacementLessonsShedule(dayOfWeek);

        //System.out.println(new DateFormatSymbols().getShortWeekdays()[Integer.parseInt(dayOfWeek) + 1]);
        switch (Integer.parseInt(dayOfWeek)) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                txtViewDay.setText(new DateFormatSymbols().getWeekdays()[Integer.parseInt(dayOfWeek) + 1]);
                textViewNameOfWeek.setText("Числитель");
                break;
            case 7:
                txtViewDay.setText(new DateFormatSymbols().getWeekdays()[Integer.parseInt(dayOfWeek) - 6]);
                break;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                txtViewDay.setText(new DateFormatSymbols().getWeekdays()[Integer.parseInt(dayOfWeek)-7 + 1]);
                textViewNameOfWeek.setText("Знаменатель");
                break;
            case 14:
                txtViewDay.setText(new DateFormatSymbols().getWeekdays()[Integer.parseInt(dayOfWeek)-7]);
                break;

        }
    }


    public void printReplacementLessonsShedule(String next) {

        for (ReplacementData groupData : MyHandlerParsing.replacementData) {
            System.out.println("getNameLesson " + groupData.getNameLesson());
            if (next.equals(groupData.getGroupDayID()))
                switch (groupData.getGroupLessonsID()) {
                    case "1":
                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 10, "").append("\n").
                                append(groupData.getGroupAuditorium());
                        txtViewLessons.setText(builder);
                        txtViewLessons.setTextColor(Color.RED);
                        break;
                    case "2":
                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 8, "").append("\n").
                                append(groupData.getGroupAuditorium());
                        txtViewLessons2.setText(builder);
                        txtViewLessons2.setTextColor(Color.RED);
                        break;
                    case "3":
                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 5, "").append("\n").
                                append(groupData.getGroupAuditorium());
                        txtViewLessons3.setText(builder);
                        txtViewLessons3.setTextColor(Color.RED);
                        break;
                    case "4":
                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 5, "").append("\n").
                                append(groupData.getGroupAuditorium());
                        txtViewLessons4.setText(builder);
                        txtViewLessons4.setTextColor(Color.RED);
                        break;
                    case "5":

                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 5, "").append("\n").
                                append(groupData.getGroupAuditorium());
                        txtViewLessons5.setText(builder);
                        txtViewLessons5.setTextColor(Color.RED);
                        break;
                    case "6":
                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 5, "").append("\n").
                                append(groupData.getGroupAuditorium());
                        txtViewLessons6.setText(builder);
                        txtViewLessons6.setTextColor(Color.RED);
                        break;
                }

            builder = new StringBuffer();
        }
    }


    private void CheckMyReplacementShedule(String readReplacementSchedule) {
        try {

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(FILENAMEReplacementShedule, MODE_PRIVATE)));

            bw.write(readReplacementSchedule);
            // закрываем поток
            bw.close();
            Log.d(LOG_TAG, "Файл записан");
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void CheckMyShedule(String data) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(FILENAMEShedule, MODE_PRIVATE)));

            bw.write(data);
            // закрываем поток
            bw.close();
            Log.d(LOG_TAG, "Файл записан");
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean readFileSD() {
        String inputGroup = null;
        boolean cheackread = false;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput(FILENAMEGroup)));
            Scanner scan = new Scanner(reader);
            inputGroup = scan.nextLine();
            if (!inputGroup.isEmpty())
                cheackread = true;
            System.out.println("777777" + inputGroup);

            editchoosingGroupmain.setText(inputGroup);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("allalala" + cheackread);
        return cheackread;
    }


    public void buttonTheBackShedule(View view) {
        printLessonsShedule("thePast");
    }

    public void buttonNextShedule(View view) {
        printLessonsShedule("next");
    }

    public void buttonToday(View view) {
        printLessonsShedule("");
    }

    private void ClearTextView() {
        txtViewLessons.setText("-----------------------");
        txtViewLessons.setTextColor(Color.WHITE);
        txtViewLessons2.setText("----------------------- ");
        txtViewLessons2.setTextColor(Color.WHITE);
        txtViewLessons3.setText("----------------------- ");
        txtViewLessons3.setTextColor(Color.WHITE);
        txtViewLessons4.setText("----------------------- ");
        txtViewLessons4.setTextColor(Color.WHITE);
        txtViewLessons5.setText("----------------------- ");
        txtViewLessons5.setTextColor(Color.WHITE);
        txtViewLessons6.setText("----------------------- ");
        txtViewLessons6.setTextColor(Color.WHITE);
    }


    private void MethodSaxAndRead(FileInputStream fileInputStream, String checkChoosing) {
        SAXParserFactoryClass.SaxParserFactoryVoid(fileInputStream, String.valueOf(editchoosingGroupmain.getText()), checkChoosing);
        readFileSD();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void DifferenceDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");

        Date dateOne = null;
        Date dateTwo = null;

        try {
            String calendarDayFixed = "08.03.2021";
            dateOne = format.parse(calendarDayFixed);
            dateTwo = format2.parse(calendarDayNowDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long difference = dateTwo.getTime() - dateOne.getTime();
        // Перевод количества дней между датами из миллисекунд в дни
        days = (int) (difference / (24 * 60 * 60 * 1000)); // миллисекунды / (24ч * 60мин * 60сек * 1000мс)
        week = days / 7;
        System.out.println(days % 2 + "  asdads");
    }


}