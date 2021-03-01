package com.example.shedule;

import com.example.shedule.orlov.Module.GroupData;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class MyHandlerParsing extends DefaultHandler {

    public static List<GroupData> dataGroup = new ArrayList<>();

    private String groupDayID;
    private String groupLessonsID;
    private String groupPodgr;
    private String nameLesson;
    private String nameTeacher;
    private String groupAuditorium;


    public String mygroup = "3703";
    private boolean IsMyGroup = false;

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start Parsing ...");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        String element = qName;
        if (!mygroup.equals("")) {
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
        else



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
        System.out.println("End Parsing ...");
    }
}
