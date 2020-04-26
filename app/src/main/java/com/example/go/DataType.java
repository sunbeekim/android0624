package com.example.go;

public class DataType extends MyItem{
    String studyName;
    String studyScore;
    String surveyName;
    String surveyNum;
    int defalt;
    public DataType(int defalt, String  surveyNum, String surveyName){
        this.surveyName = surveyName;
        this.surveyNum = surveyNum;
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

    public DataType(String studyName, String studyScore){
        this.studyName = studyName;
        this.studyScore = studyScore;
    }

    public String getStudyName(){
        return studyName;
    }

    public void setStudyName(String studyName){
        this.studyName = studyName;
    }

    public String getStudyScore(){
        return studyScore;
    }

    public void setStudyScore(String studyScore){
        this.studyScore = studyScore;
    }
}
