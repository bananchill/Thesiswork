package com.example.shedule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private TextView txtViewLessons2;
    private TextView txtViewLessons3;
    private TextView txtViewLessons4;
    private TextView txtViewLessons5;
    private TextView txtViewLessons6;

    public TextView editchoosingGroupmain;

    private EditText inputGroup;

    private Toolbar toolbar;

    private String radingShedul = null;
    private String readReplacementScheduleClient = null;
    private String checkingTheDayForANull = null;
    private String checkingTheDayForANullReplacement = null;

    boolean lsot = false;

    private FileInputStream fin = null;

    private Calendar date = Calendar.getInstance();
    private Date timeOfDay = new Date();

    private String dayOfWeek = String.valueOf(date.get(Calendar.DAY_OF_WEEK) - 1);
    private StringBuffer builder = new StringBuffer();


    int refresh = 0;
    int HourOfDay = parseInt(String.valueOf(timeOfDay.getHours()));
    int minutesOfHour = parseInt(String.valueOf(timeOfDay.getMinutes()));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        editchoosingGroupmain = findViewById(R.id.textViewChoosingGroupMain);

        try {
            System.out.println("TimeOfDay " + HourOfDay + minutesOfHour);

            //System.out.println(date.get(Calendar.DAY_OF_WEEK));
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
                readReplacementScheduleClient = Client.ClientConnection("C:\\Users\\maksm\\Desktop\\xml\\dop.xml");
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
        switch (next) {
            case "next":
                if (!dayOfWeek.equals("7")) {
                    dayOfWeek = String.valueOf(parseInt(dayOfWeek) + 1);
                } else {
                    dayOfWeek = String.valueOf(parseInt(dayOfWeek) - 7);
                }
                break;
            case "thePast":
                if (!dayOfWeek.equals("-1")) {
                    dayOfWeek = String.valueOf(parseInt(dayOfWeek) - 1);
                } else {
                    dayOfWeek = String.valueOf(parseInt(dayOfWeek) + 7);
                }
                break;
            default:
                dayOfWeek = String.valueOf(date.get(Calendar.DAY_OF_WEEK) - 1);
        }

        ClearTextView();
        for (GroupData groupData : MyHandlerParsing.dataGroup) {
            if (!groupData.getGroupDayID().isEmpty()) {
                checkingTheDayForANull = groupData.getGroupDayID();
            }

            lsot = dayOfWeek.equals(checkingTheDayForANull);
            if (lsot) {
                System.out.println("getNameLesson " + groupData.getNameLesson());
               // for (ReplacementData replacementData : MyHandlerParsing.replacementData)
                    switch (groupData.getGroupLessonsID()) {
                        case "1":
                            //if()
                            builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                    append(groupData.getNameTeacher()).replace(0, 10, "");

                            txtViewLessons.setText(builder);
                            break;
                        case "2":
                            builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                    append(groupData.getNameTeacher()).replace(0, 8, "");
                            txtViewLessons2.setText(builder);
                            break;
                        case "3":
                            builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                    append(groupData.getNameTeacher()).replace(0, 10, "");
                            txtViewLessons3.setText(builder);
                            break;
                        case "4":
                            builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                    append(groupData.getNameTeacher()).replace(0, 10, "");
                            txtViewLessons4.setText(builder);
                            break;
                        case "5":
                            builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                    append(groupData.getNameTeacher()).replace(0, 10, "");
                            txtViewLessons5.setText(builder);
                            break;
                        case "6":
                            builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                    append(groupData.getNameTeacher()).replace(0, 10, "");
                            txtViewLessons6.setText(builder);
                            break;
                    }

                builder = new StringBuffer();
            }

            lsot = false;
        }


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
                break;
            case 7:
                txtViewDay.setText(new DateFormatSymbols().getWeekdays()[Integer.parseInt(dayOfWeek) - 6]);
                break;
            default:
                txtViewDay.setText(new DateFormatSymbols().getWeekdays()[Integer.parseInt((dayOfWeek) + 1) - 6]);

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
        txtViewLessons.setText("");
        txtViewLessons2.setText("");
        txtViewLessons3.setText("");
        txtViewLessons4.setText("");
        txtViewLessons5.setText("");
        txtViewLessons6.setText("");
    }


    private void MethodSaxAndRead(FileInputStream fileInputStream, String checkChoosing) {
        SAXParserFactoryClass.SaxParserFactoryVoid(fileInputStream, String.valueOf(editchoosingGroupmain.getText()), checkChoosing);
        readFileSD();
    }


}