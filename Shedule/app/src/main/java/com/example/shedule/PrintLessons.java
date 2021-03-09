package com.example.shedule;

import android.widget.TextView;

import com.example.shedule.orlov.Module.GroupData;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

import static java.lang.Integer.parseInt;

public interface PrintLessons {

     void printLessonsShedule(ArrayList<GroupData> dataArrayList, String next);

}
