package com.example.go;

public class Fragment2_Contents {
    String name;
    String title;
    String contents;
    String reward;
    String period;

    public Fragment2_Contents(String name, String title, String contents, String reward,
                              String period){
        this.name = name;
        this.title = title;
        this.contents = contents;
        this.reward = reward;
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

}
