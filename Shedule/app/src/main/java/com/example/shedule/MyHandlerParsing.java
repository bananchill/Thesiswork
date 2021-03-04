package com.example.shedule;

import com.example.shedule.orlov.Module.GroupData;
import com.example.shedule.orlov.Module.NameGroup;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class MyHandlerParsing extends DefaultHandler {

    public static ArrayList<GroupData> dataGroup = new ArrayList<>();
    public static ArrayList<NameGroup> nameGroup = new ArrayList<>();


    private String groupDayID;
    private String groupLessonsID;
    private String groupPodgr;
    private String nameLesson;
    private String nameTeacher;
    private String groupAuditorium;
    private String whereAuditorium;

    private String lessoms = "";
    int i = 0;

    private boolean checkAuditorium = false;
    private boolean checkTeacher = false;

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
            if (IsMyGroup || i < 6) {
                switch (element) {
                    case "Day":
                        groupDayID = attributes.getValue(0);
                    case "Lesson":
                        groupLessonsID = attributes.getValue(0);
                        // System.out.println(nameLesson);
                    case "Part":
                        groupPodgr = attributes.getValue(1);
                    case "Auditorium":
                        groupAuditorium = attributes.getValue(1);
                    case "Name":
                    case "Teacher":
                        checkTeacher = true;

                }
                i++;
            }
        } else if (element.equals("Group"))
            nameGroup.add(new NameGroup(attributes.getValue(0)));

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (IsMyGroup) {
            if (checkTeacher) {
                nameLesson += new String(ch, start, length);

            }
        }
    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (IsMyGroup) {
            if (!groupAuditorium.isEmpty()) {
                lessoms = groupAuditorium;
                groupAuditorium = "";
            } else {
                System.out.println(nameLesson);
                dataGroup.add(new GroupData(groupDayID,
                        groupLessonsID, groupPodgr, nameLesson, nameTeacher, groupAuditorium));
                lessoms = "";
                nameTeacher = "";
                checkAuditorium = false;
                groupDayID = "";
                groupLessonsID = "";
                groupPodgr = "";
                nameLesson = "";
                whereAuditorium = "";
                checkTeacher = false;
            }


            //  System.out.println(nameLesson);
        }
    }


    @Override
    public void endDocument() throws SAXException {
        System.out.println("End Parsing ...");
    }
}
