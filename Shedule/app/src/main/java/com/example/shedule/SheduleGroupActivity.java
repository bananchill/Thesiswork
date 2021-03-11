package com.example.shedule;

import android.content.Intent;
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
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.shedule.orlov.Module.GroupData;
import com.example.shedule.orlov.Module.NameGroup;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.lang.Integer.parseInt;

public class SheduleGroupActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PrintLessons {
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


    private TextView txtViewDay;
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

    private FileInputStream fin = null;
    private final String FILENAMEShedule = "fileshedule.xml";
    private String checkingTheDayForANull = null;
    boolean lsot = false;

    private String checkSpinnerClickGroup;

    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_shedule_activity);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        spinnerGroup = findViewById(R.id.spinner);
        txtViewChoosing = findViewById(R.id.textViewChoosingGroupMain);


        txtViewDay = findViewById(R.id.textViewDayOfWeek);
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
        spinnerGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                ClearTextView();
                checkSpinnerClickGroup = spinnerGroup.getSelectedItem().toString();
                try {

                    fin = openFileInput(FILENAMEShedule);
                    SAXParserFactoryClass.SaxParserFactoryVoid(fin, checkSpinnerClickGroup, "shedule" );
                    printLessonsShedule( "");
                } catch (FileNotFoundException e) {
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

        parsingHelper(MyHandlerParsing.dataGroup,dayOfWeek);
        switch (Integer.parseInt(dayOfWeek)) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                txtViewDay.setText(new DateFormatSymbols().getShortWeekdays()[Integer.parseInt(dayOfWeek) + 1]);
                break;
            case 7:
                txtViewDay.setText(new DateFormatSymbols().getShortWeekdays()[Integer.parseInt(dayOfWeek) - 7]);
                break;
            default:
                txtViewDay.setText(new DateFormatSymbols().getShortWeekdays()[Integer.parseInt((dayOfWeek) + 1) - 6]);

        }

    }



    private void ClearTextView() {
        txtViewLessons.setText("-----------------------");
        txtViewLessons2.setText("----------------------- ");
        txtViewLessons3.setText("----------------------- ");
        txtViewLessons4.setText("----------------------- ");
        txtViewLessons5.setText("----------------------- ");
        txtViewLessons6.setText("----------------------- ");
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

    public void parsingHelper(ArrayList<GroupData> arrayNameOfGroup, String next) {
        for (GroupData groupData : arrayNameOfGroup) {

            if (!groupData.getGroupDayID().isEmpty())
                checkingTheDayForANull = groupData.getGroupDayID();

            if (next.equals(checkingTheDayForANull)) {
                lsot = true;
            } else lsot = false;

            if (lsot) {
                System.out.println("zzzzz" + next);
                switch (groupData.getGroupLessonsID()) {
                    case "1":
                        ClearTextView();
                        countLessons++;
                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 10, "");
                        txtViewLessons.setText(builder);
                        break;
                    case "2":
                        countLessons++;
                        if (countLessons == 1) {
                            ClearTextView();
                        }
                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 5, "");
                        txtViewLessons2.setText(builder);
                        break;
                    case "3":
                        countLessons++;
                        if (countLessons == 1) {
                            ClearTextView();
                        }
                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 5, "");
                        txtViewLessons3.setText(builder);
                        break;
                    case "4":
                        countLessons++;
                        if (countLessons == 1) {
                            ClearTextView();
                        }
                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 5, "");
                        txtViewLessons4.setText(builder);
                        txtViewLessons5.setText(" ");
                        txtViewLessons6.setText(" ");
                        break;
                    case "5":
                        countLessons++;
                        if (countLessons == 1) {
                            ClearTextView();
                        }
                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 5, "");
                        txtViewLessons5.setText(builder);
                        txtViewLessons6.setText(" ");
                        break;
                    case "6":
                        countLessons++;
                        if (countLessons == 1) {
                            ClearTextView();
                        }
                        builder.append(groupData.getNameLesson()).replace(0, 10, "").append("\n").
                                append(groupData.getNameTeacher()).replace(0, 5, "");
                        txtViewLessons6.setText(builder);
                        break;
                }

                builder = new StringBuffer();
            }
            lsot = false;
        }
    }
}
