package com.company;

import java.util.ArrayList;

public class Resultmy {
    int error = 0;
    String message = "";
    private double a, b, rr, ta, tb,tкрит,alpha,koefKorr,fisher;
    ArrayList<ItemMy> itemMyArrayList = new ArrayList<>();

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getRr() {
        return rr;
    }

    public void setRr(double rr) {
        this.rr = rr;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getTa() {
        return ta;
    }

    public void setTa(double ta) {
        this.ta = ta;
    }

    public double getTb() {
        return tb;
    }

    public void setTb(double tb) {
        this.tb = tb;
    }

    public double getTкрит() {
        return tкрит;
    }

    public void setTкрит(double tкрит) {
        this.tкрит = tкрит;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getFisher() {
        return fisher;
    }

    public void setFisher(double fisher) {
        this.fisher = fisher;
    }

    public ArrayList<ItemMy> getItemMyArrayList() {
        return itemMyArrayList;
    }

    public void setItemMyArrayList(ArrayList<ItemMy> itemMyArrayList) {
        this.itemMyArrayList = itemMyArrayList;
    }

    public double getKoefKorr() {
        return koefKorr;
    }

    public void setKoefKorr(double koefKorr) {
        this.koefKorr = koefKorr;
    }

    @Override
    public String toString() {
        return "Resultmy{" +
                "a=" + a +
                ", b=" + b +
                ", rr=" + rr +
                ", itemMyArrayList=" + itemMyArrayList +
                '}';
    }

    public String showResult() {
        if (error == 0) {
            return
                    "a =" + a +
                            ", b = " + b ;
        } else {
            return
                    message;
        }

    }
}
