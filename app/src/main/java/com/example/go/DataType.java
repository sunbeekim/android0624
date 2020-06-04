package com.example.go;

import java.util.Date;

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
        return this.defalt2;
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

    //===========================================================================================================//
    String userName, chatContext, userid, chatDate;
    int count;
    public DataType(int count, String userid, String userName, String chatContext, String chatDate){
        this.userName = userName;
        this.chatContext = chatContext;
        this.chatDate = chatDate;
        this.userid = userid;
        this.count = count;
    }
    int getCount(){
        return  this.count;
    }
    void setCount(int count){
        this.count = count;
    }
    String getUserName(){
        return this.userName;
    }
    void setUserName(String userName){
        this.userName = userName;
    }

    String getChatContext(){
        return this.chatContext;
    }

    void setChatContext(String chatContext){
        this.chatContext = chatContext;
    }

    String getChatDate(){
        return this.chatDate;
    }
    //===========================================================================================================//
    void setChatDate(String chatDate){
        this.chatDate = chatDate;
    }
    String chatName, chatLastContext;
    int userCount;
    DataType(String chatName, String chatLastContext, int userCount){
        this.chatName = chatName;
        this.chatLastContext = chatLastContext;
        this.userCount = userCount;

    }
    String getUserid(){
        return  this.userid;
    }
    void setUserid(String userid){
        this.userid = userid;
    }
    String getChatName(){
        return this.chatName;
    }
    void setChatName(String chatName){
        this.chatName = chatName;
    }
    String getChatLastContext(){
        return this.chatLastContext;
    }
    void setChatLastContext(String chatLastContext){
        this.chatLastContext = chatLastContext;
    }
    int getUserCount(){
        return this.userCount;
    }
    void setUserCount(int userCount){
        this.userCount = userCount;
    }
    //===========================================================================================================//

}
