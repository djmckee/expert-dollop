package main;

import java.io.Serializable;

/**
 * Created by djmckee on 17/11/2016.
 */
public class Counter implements Serializable {
    int counter;
    String last;

    public int getCount() {
        counter++;
        return counter;
    }

    public int doubleCount() {
        counter = counter * 2;
        return counter;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String uri) {
        last = uri;
    }
}