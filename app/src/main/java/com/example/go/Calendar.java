package com.example.go;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * MaterialCalendarView = 외부 라이브러리
 * > https://github.com/prolificinteractive/material-calendarview
 */
public class Calendar extends AppCompatActivity {

    MaterialCalendarView materialCalendarView;

    String pre_year, pre_month, pre_day;
    String cur_year, cur_month, cur_day;
//    날짜 설정
    String formatDate_pre, formatDate_cur;
//    설정한 날짜들을 하나에 모음
    String intent_date;
//    인텐트값에 설정한 날짜들을 넘겨주기 위해 사용

    Button button_ok;

    List<String> result = new ArrayList<>();
//    설정한 날짜에 점을 표시해서 띄워주기 위해서 사용함.

    OneDayDecorator oneDayDecorator = new OneDayDecorator();
//    오늘 날짜 표시해주는 클래스

    boolean select = false;
    boolean reset = false;

    TextView textView_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        this.setTitle("기간 설정");

        textView_date = findViewById(R.id.textView_date_calendar);
        materialCalendarView = findViewById(R.id.calendarView);
        button_ok = findViewById(R.id.button_ok_calendar);
        calendarSetting();

//        결정 버튼 클릭
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pre_year.isEmpty() && !cur_year.isEmpty()) { // 각 값들이 설정이 되어있는지?
                    /**
                     * WAS 일정 저장
                     *
                     * formatDate_pre, formatDate_cur 값을 저장
                     *
                     * 불러올때는 이 클래스에 존재하는 while문을 참조하여
                     * 이어 붙이기.
                     */

                    Intent intent = new Intent(Calendar.this, ChallengeGrant.class);
                    intent.putExtra("date", intent_date);
                    intent.putExtra("pre_date", formatDate_pre);
                    intent.putExtra("cur_date", formatDate_cur);
                    setResult(101, intent); // result 코드를 넘겨서 grant 액티비티에서 받음
                    finish();
                }
            }
        });

        materialCalendarView.setSelectedDate(CalendarDay.today()); // 캘린더에 오늘 날짜선택
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() { // 캘린더의 선택 날짜가 변경 될 떄
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if (!select) { // false
//                    날짜 선택(1)
                    pre_year = String.valueOf(date.getYear());
                    pre_month = String.valueOf(date.getMonth());
                    pre_day = String.valueOf(date.getDay());
                    select = true;
                }
                else { // true
//                    날짜 선택(2)
                    cur_year = String.valueOf(date.getYear());
                    cur_month = String.valueOf(date.getMonth());
                    cur_day = String.valueOf(date.getDay());
                    select = false;

                    /**
                     * 리셋이 false일 경우 sort 함수 호출
                     * sort를 호출해서 날짜를 정리
                     *
                     * reset은 선택 하고 다시 선택했는지를 판별함
                     */
                    if (!reset) {
                        sort();
                        reset = true;
                    }
                    else {
                        /**
                         * 이전에 선택했던 날짜 값이 있다면
                         * 캘린더에 표시된 데코레이터들을 지우고
                         * 다시 설정하고 sort 호출
                         */
                        materialCalendarView.removeDecorators(); // 모든 데코 ( 주말에 색상 칠하고 점 찍어주고 등등 )
                        calendarSetting();
                        result.clear(); // 저장되어 있는 값들 삭제
                        sort();
                    }
                }

            }
        });
        materialCalendarView.getSelectedDate();
    }

    private void calendarSetting() {
//        각 클래스 사용
        materialCalendarView.addDecorators(
                new SundayDecorator(), // 일요일 설정
                new SaturdayDecorator(), // 토요일 설정
                new MySelectorDecorator(this), // 선택한 날짜 설정
                oneDayDecorator // 오늘 날짜 설정
        );
    }

    @SuppressLint("SetTextI18n")
    private void sort() {
        String tmp;
        if (!pre_year.isEmpty() && !cur_year.isEmpty()) { // 시작 기간과 종료 기간이 존재 한다고 가정함

            /**
             * 앞의 날짜가 뒤의 날짜보다 작으면 변경
             * ex) pre가 2020.05.20 cur이 2020.04.01일 경우
             */

            if (Integer.parseInt(pre_month) > Integer.parseInt(cur_month)) {
                tmp = pre_month;
                pre_month = cur_month;
                cur_month = tmp;

                tmp = pre_day;
                pre_day = cur_day;
                cur_day = tmp;
            }
            if (Integer.parseInt(pre_month) == Integer.parseInt(cur_month)
                    && Integer.parseInt(pre_day) > Integer.parseInt(cur_day)) {
                tmp = pre_day;
                pre_day = cur_day;
                cur_day = tmp;
            }


            int i_tmp;

//            month값 맞추기
            i_tmp = Integer.parseInt(pre_month);
            i_tmp++;
            pre_month = String.valueOf(i_tmp);

            i_tmp = Integer.parseInt(cur_month);
            i_tmp++;
            cur_month = String.valueOf(i_tmp);

            textView_date.setText("설정한 기간 \n" + pre_year + "년 " + pre_month + "월 " + pre_day + "일 ~ "
                    + cur_year + "년 " + cur_month + "월 " + cur_day + "일");

            intent_date = pre_year + "년 " + pre_month + "월 " + pre_day + "일 ~ "
                    + cur_year + "년 " + cur_month + "월 " + cur_day + "일";

//         날짜 양식을 맞춰 저장
            formatDate_pre = String.valueOf(pre_year);
            if (Integer.parseInt(pre_month) <= 9)
                formatDate_pre += ",0" + Integer.parseInt(pre_month) + ",";
            else
                formatDate_pre += "," + Integer.parseInt(pre_month) + ",";
            if (Integer.parseInt(pre_day) <= 9)
                formatDate_pre += "0" + Integer.parseInt(pre_day);
            else
                formatDate_pre += Integer.parseInt(pre_day);

            formatDate_cur = String.valueOf(pre_year);
            if (Integer.parseInt(cur_month) <= 9)
                formatDate_cur += ",0" + Integer.parseInt(cur_month) + ",";
            else
                formatDate_cur += "," + Integer.parseInt(cur_month) + ",";
            if (Integer.parseInt(cur_day) <= 9)
                formatDate_cur += "0" + Integer.parseInt(cur_day);
            else
                formatDate_cur += Integer.parseInt(cur_day);

            String DATE_PATTERN = "yyyy,MM,dd";
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);

//        Date startDate = sdf.parse(formatDate_pre);
            Date startDate = null;
            try {
                startDate = sdf.parse(formatDate_pre);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date endDate = null;
            try {
                endDate = sdf.parse(formatDate_cur);
            } catch (ParseException e) {
                e.printStackTrace();
            }

//        정리한 날짜들의 중간 날짜들을 구하여 리스트에 저장
            Date currentDate = startDate;
            while (currentDate.compareTo(endDate) <= 0) {
                result.add(sdf.format(currentDate));
                java.util.Calendar c = java.util.Calendar.getInstance();
                c.setTime(currentDate);
                c.add(java.util.Calendar.DAY_OF_MONTH, 1);
                currentDate = c.getTime();
            }
//            ...까지
            result.add("0000,00,00");
            new ApiSimulator(result).executeOnExecutor(Executors.newSingleThreadExecutor());
//            각 날짜에 점을 표시해주기 위해 사용
        }
        else {
            Toast.makeText(this, "날짜를 설정해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

//    캘린더에 표시하기 위한 클래스
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        String[] Time_Result;

        ApiSimulator(List<String> Time_Result){
            this.Time_Result = Time_Result.toArray(new String[0]);
        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            java.util.Calendar calendar = java.util.Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();

            // string 문자열인 Time_Result 을 받아와서 ,를 기준으로짜르고 string을 int 로 변환
            for(int i = 0 ; i < Time_Result.length ; i ++){
                CalendarDay calendarDay = CalendarDay.from(calendar);
                String[] time = Time_Result[i].split(",");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int day = Integer.parseInt(time[2]);

                dates.add(calendarDay);
                if (i == 0)
                    dates.remove(0);
                calendar.set(year, month-1, day);
            }
            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }

            materialCalendarView.addDecorator(new EventDecorator(Color.RED, calendarDays, Calendar.this));
        }
    }
}
