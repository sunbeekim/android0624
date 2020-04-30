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

public class ConnectDB extends AsyncTask<String, Void, String> {
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

            if(sendMsg.equals("survey1result")){ // 설문 1값 쓰기
                sendMsg = "&type="+strings[0]+"&userid="+strings[1]+"&save="+strings[2]+"&count="+strings[3];
            }else if(sendMsg.equals("survey1")){ // 설문1 불러오기
                sendMsg = "&type="+strings[0];
            }
            else if(sendMsg.equals("userid")){ //id값 지정 //fr_child getresult() //webtest survey1get
                sendMsg = "&type="+strings[0]+"&userid="+strings[1];
            }
            else if(sendMsg.equals("survey1count")){//설문 된 값
                sendMsg = "&type="+strings[0];
            }else if(sendMsg.equals("survey1rc")){//설문 된 수 //surveyResultCount() //webtest surveyResultCount
                sendMsg = "&type="+strings[0]+"&userid="+strings[1];
            }else if(sendMsg.equals("typeresult")){//타입 입력
                Log.d("typesum==>", strings[2]);
                sendMsg = "&type="+strings[0]+"&userid="+strings[1]+"&typesum="+strings[2];
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