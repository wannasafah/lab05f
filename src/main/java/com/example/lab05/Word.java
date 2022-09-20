package com.example.lab05;

import java.util.ArrayList;

public class Word {
    public ArrayList<String> badWord = new ArrayList<>();
    public ArrayList<String> goodWord = new ArrayList<>();

    public Word(){
        goodWord.add("happy");
        goodWord.add("enjoy");
        goodWord.add("life");
        badWord.add("fuck");
        badWord.add("olo");
    }
}
