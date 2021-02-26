package com.example.shedule;

import android.graphics.Paint;
import android.provider.DocumentsContract;

import com.example.shedule.orlov.Module.GroupData;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class MyHandlerParsing extends DefaultHandler {
    private static ArrayList<GroupData> dataGroup = new ArrayList<>();

    private String groupDayID;
    private String groupLessonsID;
    private String groupPodgr;
    private String nameLesson;
    private String nameTeacher;
    private String groupAuditorium;


    public String mygroup = "10-02к";
    private boolean IsMyGroup = false;

private MainActivity mainActivity = new MainActivity();

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start Parsing ...");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        String element = qName;
        if (element.equals("Group")) {
            if (attributes.getValue(0).equals(mygroup)) {
                System.out.println(attributes.getValue(0));
                nameLesson = attributes.getValue(0);
                IsMyGroup = true;
            } else
                IsMyGroup = false;
        }
        if (IsMyGroup) {
            switch (element) {
                case "Day":
                    groupDayID = attributes.getValue(0);
                case "Lesson":
                    nameLesson = attributes.getValue(0);
                case "Part":
                    groupPodgr = attributes.getValue(1);
                case "Auditorium":
                    groupAuditorium = attributes.getValue(1);
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (IsMyGroup) {
            nameLesson = new String(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (IsMyGroup) {
            if (!nameLesson.isEmpty())
                dataGroup.add(new GroupData(groupDayID,
                        groupLessonsID, groupPodgr, nameLesson, nameTeacher, groupAuditorium));


//            switch (secondElement) {
//                case "Day":
//                    System.out.println("Stop : " + secondElement);
//                case "Lesson":
//                    if (!groupLessonsID.isEmpty())
//                        System.out.println(groupLessonsID);
//                case "Part":
//                    if (!groupPodgr.isEmpty())
//                        System.out.println(groupPodgr);
//                case "Auditorium":
//                    if (!groupAuditorium.isEmpty() )
//                        System.out.println(numberauditorium + " " + auditorium);

        }
        groupDayID = "";
        groupLessonsID = "";
        groupPodgr = "";
        nameLesson = "";
        nameTeacher = "";
        groupAuditorium = "";
    }


    @Override
    public void endDocument() throws SAXException {
        mainActivity.printLessons(dataGroup);
        System.out.println("End Parsing ...");
    }
}
