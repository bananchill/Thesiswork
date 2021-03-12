package com.example.shedule;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
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
import com.example.shedule.orlov.Module.NameGroup;
import com.example.shedule.orlov.Module.ReplacementData;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;

public class SheduleGroupActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PrintLessons {
    private final String LOG_TAG = "MyLog";
    private final String FILENAMEShedule = "fileshedule.xml";
    private final String FILENAMEReplacementShedule = "filePrplacementShedule.xml";
    private final String FILENAMEGroup = "filegroupp.xml";


    private FileInputStream fin = null;


    private TextView txtViewChoosing;

    private EditText inputGroup;
    private Spinner spinnerGroup;

    private DrawerLayout drawer;
    private Toolbar toolbar;

    private Client client = new Client();

    private ArrayList<String> arrayNameOfGroup = new ArrayList<>();
    // private static MessageException messageException = new MessageException();


    private TextView txtViewDay;
    private TextView textViewNameOfWeek;
    private TextView txtViewLessons;
    private TextView txtViewLessons2;
    private TextView txtViewLessons3;
    private TextView txtViewLessons4;
    private TextView txtViewLessons5;
    private TextView txtViewLessons6;

    private Calendar date = Calendar.getInstance();
    private String dayOfWeek = String.valueOf(date.get(Calendar.DAY_OF_WEEK) - 1);
    private StringBuffer builder = new StringBuffer();
    int refresh = 0;


    @RequiresApi(api = Build.VERSION_CODES.O)
    private final String calendarDayNowDate = String.valueOf(LocalDateTime.now().toLocalDate());
    int days = 0;
    int week = 0;


    private String checkingTheDayForANull = null;
    boolean lsot = false;

    private String checkSpinnerClickGroup;

    int i = 1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_shedule_activity);

        DifferenceDate();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        spinnerGroup = findViewById(R.id.spinner);
        txtViewChoosing = findViewById(R.id.textViewChoosingGroupMain);


        txtViewDay = findViewById(R.id.textViewDayOfWeek);
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
        completionSpinner();
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


    private void completionSpinner() {
        for (NameGroup names : MyHandlerParsing.nameGroup) {
            arrayNameOfGroup.add(String.valueOf(names.getNameGroup()));
        }

        ArrayAdapter<String> nameGroupArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, arrayNameOfGroup);
        nameGroupArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerGroup.setAdapter(nameGroupArrayAdapter);
        spinnerGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                ClearTextView();
                checkSpinnerClickGroup = spinnerGroup.getSelectedItem().toString();
                try {
                    fin = openFileInput(FILENAMEShedule);
                    SAXParserFactoryClass.SaxParserFactoryVoid(fin, checkSpinnerClickGroup, "shedule");
                    fin.close();

                    fin = openFileInput(FILENAMEReplacementShedule);
                    SAXParserFactoryClass.SaxParserFactoryVoid(fin, checkSpinnerClickGroup, "replacement");
                    fin.close();

                    printLessonsShedule("");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Ваш выбор: " + checkSpinnerClickGroup, Toast.LENGTH_SHORT);
                toast.show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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


    private int countLessons = 0;
    private String numberCountLessons;

    @Override
    public void printLessonsShedule(String next) {
        if (week / 2 != 0) {
            dayOfWeek = String.valueOf(parseInt(dayOfWeek) + 7);
            boolean checkChangingTheSchedule = false;
        }
        switch (next) {
            case "next":
                dayOfWeek = String.valueOf(parseInt(dayOfWeek) + 1);
                if (parseInt(dayOfWeek) > 14)
                    dayOfWeek = String.valueOf(parseInt(dayOfWeek) - 14);
                break;
            case "thePast":
                if (!dayOfWeek.equals("-1")) {
                    dayOfWeek = String.valueOf(parseInt(dayOfWeek) - 1);
                } else {
                    dayOfWeek = String.valueOf(parseInt(dayOfWeek) + 7);
                }
                if (parseInt(dayOfWeek) <= -1)
                    dayOfWeek = String.valueOf(parseInt(dayOfWeek) + 15);
                break;
            default:
                dayOfWeek = String.valueOf(date.get(Calendar.DAY_OF_WEEK));
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
                                append(groupData.getNameTeacher()).replace(0, 5, "");
                        txtViewLessons3.setText(builder);

                        break;
                    case "4":
                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 5, "");
                        txtViewLessons4.setText(builder);

                        break;
                    case "5":

                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 5, "");
                        txtViewLessons5.setText(builder);

                        break;
                    case "6":
                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 5, "");
                        txtViewLessons6.setText(builder);

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
                                append(groupData.getNameTeacher()).replace(0, 10, "");
                        txtViewLessons.setText(builder);
                        txtViewLessons.setTextColor(Color.RED);
                        break;
                    case "2":
                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 8, "");
                        txtViewLessons2.setText(builder);
                        txtViewLessons2.setTextColor(Color.RED);
                        break;
                    case "3":
                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 5, "");
                        txtViewLessons3.setText(builder);
                        txtViewLessons3.setTextColor(Color.RED);
                        break;
                    case "4":
                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 5, "");
                        txtViewLessons4.setText(builder);
                        txtViewLessons4.setTextColor(Color.RED);
                        break;
                    case "5":
                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 5, "");
                        txtViewLessons5.setText(builder);
                        txtViewLessons5.setTextColor(Color.RED);
                        break;
                    case "6":
                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 5, "");
                        txtViewLessons6.setText(builder);
                        txtViewLessons6.setTextColor(Color.RED);
                        break;
                }

            builder = new StringBuffer();
        }
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

    public void buttonChoose(View view) {
        CheckMyGroup(checkSpinnerClickGroup);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Refresh", 1);
        startActivity(intent);
    }

    public void buttonNextShedule(View view) {
        printLessonsShedule("next");
    }

    public void buttonTheBackShedule(View view) {
        printLessonsShedule("thePast");

    }

    public void buttonToday(View view) {
        printLessonsShedule("");
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
