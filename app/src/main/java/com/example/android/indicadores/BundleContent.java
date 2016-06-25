package com.example.android.indicadores;

import java.io.Serializable;

/**
 * Created by samirsanmartin on 5/17/16.
 */
public class BundleContent implements Serializable {
    private String from, rate, to, date, id;

    public BundleContent(String id, String from, String rate, String to, String date){
        this.id=id;
        this.from=from;
        this.rate=rate;
        this.to=to;
        this.date=date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
