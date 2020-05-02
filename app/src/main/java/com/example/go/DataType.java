package com.example.go;

public class DataType extends MyItem{
    String studyName;
    //String clickname;
    String mytype;
    String percent;
    String clicknum;
    String studyadress;
    //String studyScore;
    String surveyName;
    String surveyNum;
    int defalt;
    int defalt2;
    public DataType(int defalt, String  surveyNum, String surveyName){
        this.surveyName = surveyName;
        this.surveyNum = surveyNum;
        this.defalt = defalt;
    }
    public String getSurveyNum(){
        return surveyNum;
    }

    public void setSurveyNum(String surveyNum){
        this.surveyNum = surveyNum;
    }
    public String getSurveyName(){
        return surveyName;
    }

    public void setSurveyName(String surveyName){
        this.surveyName = surveyName;
    }

    public int getDefalt(){
        return defalt;
    }

    public void setDefalt(int defalt){
        this.defalt = defalt;
    }

    public DataType(int defalt2, String studyName, String studyadress, String mytype, String percent, String clicknum){
        this.studyName = studyName;
        this.mytype = mytype;
        this.percent = percent;
        this.clicknum = clicknum;
        this.studyadress = studyadress;
        this.defalt2 = defalt2;
    }
    public void setDefalt2(int defalt2){
        this.defalt2 = defalt2;
    }
    public int getDefalt2(){
        return this.defalt2 = defalt2;
    }
    public void setStudyadress(String studyadress){
        this.studyadress = studyadress;
    }
    public String getStudyadress(){
        return this.studyadress;
    }
    public void setClicknum(String clicknum){
        this.clicknum = clicknum;
    }
    public String getClicknum(){
        return this.clicknum;
    }
    public void setPercent(String percent){
        this.percent = percent;
    }
    public String getPercent(){
        return this.percent;
    }
    public String getMytype(){
        return this.mytype;
    }
    public void setMytype(String mytype){
        this.mytype = mytype;
    }

    public String getStudyName(){
        return studyName;
    }

    public void setStudyName(String studyName){
        this.studyName = studyName;
    }

//    public String getStudyScore(){
//        return studyScore;
//    }
//
//    public void setStudyScore(String studyScore){
//        this.studyScore = studyScore;
//    }
}
