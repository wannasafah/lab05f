package com.example.lab05;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.SimpleTimeZone;

@RestController
public class WordPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    protected Word words = new Word();

    @RequestMapping(value = "/addBad/{word}", method = RequestMethod.GET)
    public ArrayList<String> addBadWord (@PathVariable("word") String s){
        words.badWord.add(s);
        return words.badWord;
    }

    @RequestMapping(value = "/delBad/{word}", method = RequestMethod.GET)
    public ArrayList<String> deleteBadWord(@PathVariable("word") String s){
        words.badWord.remove(words.badWord.indexOf(s));
        return words.badWord;
    }

    @RequestMapping(value = "/addGood/{word}", method = RequestMethod.GET)
    public ArrayList<String> addGoodWord(@PathVariable("word") String s){
        words.goodWord.add(s);
        return words.goodWord;
    }

    @RequestMapping(value = "/delGood/{word}")
    public ArrayList<String> deleteGoodWord(@PathVariable("word") String s){
        words.goodWord.remove(words.goodWord.indexOf(s));
        return words.goodWord;
    }

    @RequestMapping(value = "/proof/{sentence}", method = RequestMethod.GET)
    public void proofSentence(@PathVariable("sentence") String s){
        boolean check_good = false, check_bad = false;
        for (String good:words.goodWord) {
            if (good.contains(s)) {
                check_good = true;
            }
        }
        for (String bad:words.badWord) {
            if (bad.contains(s)) {
                check_bad = true;
            }
        }

        if (check_good) {
            rabbitTemplate.convertAndSend("Direct", "good", s);
        } else if (check_bad) {
            rabbitTemplate.convertAndSend("Direct", "bad", s);
        } else if (check_bad && check_good) {
            rabbitTemplate.convertAndSend("Fanout", "", s);
        }
    }
}
