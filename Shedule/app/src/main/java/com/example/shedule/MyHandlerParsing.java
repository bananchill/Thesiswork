package com.example.shedule;

import com.example.shedule.orlov.Module.GroupData;
import com.example.shedule.orlov.Module.NameGroup;
import com.example.shedule.orlov.Module.ReplacementData;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class MyHandlerParsing extends DefaultHandler {

    public static ArrayList<GroupData> dataGroup = new ArrayList<>();
    public static ArrayList<ReplacementData> replacementData = new ArrayList<>();
    public static ArrayList<NameGroup> nameGroup = new ArrayList<>();


    private String groupDayID;
    private String groupLessonsID;
    private String groupPodgr;
    private String nameLesson;
    private String nameTeacher;
    private String groupAuditorium;
    private String whereAuditorium;
    private String Name;

    private String checkAuditorium = "";

    int i = 0;


    private boolean checkTeacher = false;

    public String mygroup;
    public String checkChoose;
    private boolean IsMyGroup = false;

    public MyHandlerParsing(String mygroup, String checkChoose) {
        this.mygroup = mygroup;
        this.checkChoose = checkChoose;
    }

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
        }
        if (element.equals("Group"))
            nameGroup.add(new NameGroup(attributes.getValue(0)));

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (IsMyGroup) {
            if (checkTeacher) {

                Name += new String(ch, start, length);
                System.out.println(Name + " Day");
            }
        }
    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (IsMyGroup) {
            if (!groupAuditorium.isEmpty()) {
                checkAuditorium = groupAuditorium;
                nameLesson = Name;
                groupAuditorium = "";
                Name = "";
            } else {
                if (checkChoose.equals("replacement"))
                    replacementData.add(new ReplacementData(groupDayID,
                            groupLessonsID, groupPodgr, nameLesson, Name, checkAuditorium));
                else
                    dataGroup.add(new GroupData(groupDayID,
                            groupLessonsID, groupPodgr, nameLesson, Name, checkAuditorium));
                checkAuditorium = "";
                nameTeacher = "";
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