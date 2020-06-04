package com.example.go;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConnectDB extends AsyncTask<String, Void, String> { //비동기 처리방식
    public static String ip ="123.100.189.65"; //자신의 IP번호
    String sendMsg, receiveMsg;
    String serverip = "http://"+ip+":8088/WebTest/androidDB.jsp"; // 연결할 jsp주소
//http://localhost:8088/WebTest/androidDB.jsp
    ConnectDB(String sendmsg){
        this.sendMsg = sendmsg;
    }
    @Override
    protected String doInBackground(String... strings) {
        try {
            String str;
            URL url = new URL(serverip);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            //최적화 해야함
            //트랜잭션 최소화 필요 * 자주 쓰이고 변하지 않는 값들 스태틱 변수에 대입하고 값이 NULL일때만 가져오기
            //이클립스 정리 필요 *
            //ex - com.dbChat, com.dbChall, com.Survey 패키지 별 분류
            //com.dbChat - 각 테이블마다 클래스 하나 - 클래스 내에서 테이블의 삽입, 삭제, 업데이트 메소드 분류
            //TSD 정리 필요
            if(sendMsg.equals("survey_send")){ // 설문 1값 쓰기 SurveyResult - select *from survey_response where questionsnum = " + count + "and response = "+"'"+respon+"'"
                //update survey_response set surveynum = '"+ save +"' where response = '"+ respon +"' and questionsnum = '"+ count+"'"
                //insert into survey_response (response, questionsnum, surveynum) values('"+respon+"', '"+count+"', '"+save+"')"
                sendMsg = "&type="+strings[0]+"&respon="+strings[1]+"&save="+strings[2]+"&count="+strings[3];
            }else if(sendMsg.equals("survey1")){ // 설문1 목록 가져오기 Survey1 - select * from survey
                sendMsg = "&type="+strings[0];
            }
            else if(sendMsg.equals("responcount")){ //설문 포지션 별 값 가져오기 //fr_child getresult() //webtest Survey1Get - "select questionsnum, surveynum FROM survey_response where response = '" + respon+"' order by questionsnum asc";
                sendMsg = "&type="+strings[0]+"&respon="+strings[1];
            }
            else if(sendMsg.equals("survey1rc")){//설문 된 수 //surveyResultCount() //webtest surveyResultCount - "select * from survey_response where response = '"+ respon+"'"
                sendMsg = "&type="+strings[0]+"&respon="+strings[1];
            }else if(sendMsg.equals("typeresult")){//유형 결과 입력하기 TypeResult  - rs = pstmt.executeQuery("insert into typeresult(userid, type1, type2, type3, typesum, checkposition, checkdate) values('"+userid+"', '"+type[0]+"', '"+type[1]+"', '"+type[2]+"', '"+typesum+"', "+ (toint+1) +", to_char(sysdate, 'yyyy.mm.dd.hh24:mi'))");
                                                                                    //rs = pstmt.executeQuery("insert into survey_position(userid, checkposition, response) values('"+userid+"', "+(toint+1)+", '"+respon+"')");
                                                                                    //typeresult, survey_position 테이블 삽입
                sendMsg = "&type="+strings[0]+"&userid="+strings[1]+"&typesum="+strings[2];

            }else if(sendMsg.equals("surveycheck")){//설문 여부 확인 SurveyCheck - select *from typeresult where userid = '"+userid+"' and rownum = 1 order by checkposition desc
                sendMsg = "&type="+strings[0]+"&userid="+strings[1];
            }
            else if(sendMsg.equals("studylist")){//학습법 목록 출력 StudyList - "select * from studylist order by studynum asc"
                sendMsg = "&type="+strings[0];
            }else if(sendMsg.equals("typeget")){//유형결과 가져오기 TypeReturn - SELECT max(checkposition) checkposition FROM typeresult where userid = '"+userid+"' and rownum = 1 order by checkposition desc"
                                                //"select * from typeresult where userid = '"+userid+"' and rownum = 1 order by checkposition desc"
                sendMsg = "&type="+strings[0]+"&userid="+strings[1];
            }

            else if(sendMsg.equals("log")){//클릭 학습법 입력 Log - "insert into log(userid, typesum, studynum, click, logdate) values('"+userid+"', '"+typesum+"', "+count+", "+(toint+1)+", to_char(sysdate, 'yyyy.mm.dd hh24:mi'))
                                            // 날짜 분 단위를 mm 으로 했을 때 입력이 안되는 현상 일어남, 안드로이드와 오라클의 날짜의 형식 지정이 차이가 좀 있음?
                sendMsg = "&type="+strings[0]+"&userid="+strings[1]+"&typesum="+strings[2]+"&count="+strings[3];
            }
            else if(sendMsg.equals("type_sum")){//타입별 총 추천수 가져오기 TypeSum * 데이터프로세싱을 거친 뒤 만들어 졌다는 가정하의 테이블
                sendMsg = "&type="+strings[0]+"&typesum="+strings[1];
            }
            else if(sendMsg.equals("type_questions")){//타입의 각 학습법 추천 수 가져오기 Type_Questions  * 데이터프로세싱을 거친 뒤 만들어 졌다는 가정하의 테이블
                sendMsg = "&type="+strings[0]+"&typesum="+strings[1]+"&count="+strings[2];
            }
            else if(sendMsg.equals("type_log")){//타입별 클릭 수 가져오기 TypeLog * 데이터프로세싱을 거친 뒤 만들어 졌다는 가정하의 테이블
                sendMsg = "&type="+strings[0]+"&typesum="+strings[1]+"&count="+strings[2];
            }
            //============================================================================================================//
            //"userinfo", userid, chatid, username
            else if(sendMsg.equals("userinfo")){//유저가 가진 채팅방 목록에 삽입(conn) Userinfo
                sendMsg = "&type="+strings[0]+"&userid="+strings[1]+"&chatid="+strings[2]+"&username="+strings[3];
            } else if(sendMsg.equals("getuserinfo")){//유저가 가진 채팅방 정보 가져오기 GetUserInfo
                sendMsg = "&type="+strings[0]+"&userid="+strings[1];
            }else if(sendMsg.equals("inputmsg")){//채팅 삽입 InsertChat
                sendMsg = "&type="+strings[0]+"&userid="+strings[1]+"&context="+strings[2]+"&pos="+strings[3];
            }
            else if(sendMsg.equals("selectchat")){//채팅 출력 SelectChat
                sendMsg = "&type="+strings[0]+"&userid="+strings[1]+"&pos="+strings[2];
            }else if(sendMsg.equals("polling")){//
                sendMsg = "&type="+strings[0];//채팅 폴링
            }else if(sendMsg.equals("chatmake")){//
                sendMsg = "&type="+strings[0]+"&userid="+strings[1]+"&chatname="+strings[2]+"&username="+strings[3]; //roommake button done event!
            }else if(sendMsg.equals("survey2result")){//
                sendMsg = "&type="+strings[0]+"&survey2result="+strings[1]; //roommake button done event!
            }



//출처 https://coding-factory.tistory.com/31?category=758272
            osw.write(sendMsg);
            osw.flush();
            if(conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
            } else {
                Log.i("통신 결과", conn.getResponseCode()+"에러");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receiveMsg;
    }
}