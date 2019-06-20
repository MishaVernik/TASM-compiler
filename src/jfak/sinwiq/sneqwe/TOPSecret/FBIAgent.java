package jfak.sinwiq.sneqwe.TOPSecret;

import jfak.sinwiq.sneqwe.Constants;

import static jfak.sinwiq.sneqwe.Constants.FIRST_PASS;

public class FBIAgent implements Runnable {
    Thread thread;
    String hexOffset;

    FBIAgent(String hexOffset){
        this.thread = new Thread(this, "Agent");
        this.hexOffset = hexOffset;
        this.thread.start();
    }

    @Override
    public void run() {
        boolean check = true;
        while (check){
            for (String el: FIRST_PASS) {
                if (el.contains(" " + hexOffset)){
                    Constants.GLOBAL_OFFSET = el.substring(0, 4) + " R";
                    check = false;
                    return;
                }
            }
        }
    }

}
