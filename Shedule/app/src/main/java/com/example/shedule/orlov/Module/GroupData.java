package com.example.shedule.orlov.Module;

public class GroupData {


    private String groupDayID;
    private String groupLessonsID;
    private String groupPodgr;
    private String nameLesson ;
    private String nameTeacher;
    private String groupAuditorium;



    public GroupData( String groupDayID, String groupLessonsID, String groupPodgr, String nameLesson, String nameTeacher, String groupAuditorium) {

        this.groupDayID = groupDayID;
        this.groupLessonsID = groupLessonsID;
        this.groupPodgr = groupPodgr;
        this.nameLesson = nameLesson;
        this.nameTeacher = nameTeacher;
        this.groupAuditorium = groupAuditorium;
    }

    public String getGroupDayID() {
        return groupDayID;
    }

    public String getGroupLessonsID() {
        return groupLessonsID;
    }

    public String getGroupPodgr() {
        return groupPodgr;
    }

    public String getNameLesson() {
        return nameLesson;
    }

    public String getNameTeacher() {
        return nameTeacher;
    }

    public String getGroupAuditorium() {
        return groupAuditorium;
    }

    @Override
    public String toString() {
        return "GroupData{" +
                ", groupDayID='" + groupDayID + '\'' +
                ", groupLessonsID='" + groupLessonsID + '\'' +
                ", groupPodgr='" + groupPodgr + '\'' +
                ", nameLesson='" + nameLesson + '\'' +
                ", nameTeacher='" + nameTeacher + '\'' +
                ", groupAuditorium='" + groupAuditorium + '\'' +
                '}';
    }
}
