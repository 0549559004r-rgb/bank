package com.example.bank;

import java.util.Date;

public class transaction {
    public String type;
    public double sum;
    public Date date;
    public String des;

    public transaction(String type, double sum, String des) {
        this.type=type;
        this.date=new Date();
        this.sum=sum;
        this.des=des;
    }

    public String getType() {
        return type;
    }
    public double getSum() {
        return sum;
    }

    public Date getDate() {
        return date;
    }

    public String getDes() {
        return des;
    }
    public String toString(){
        return type + " | " + sum + " | " + date + " | " + des;
    }

}


