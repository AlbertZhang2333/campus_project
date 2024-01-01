package com.example.ooadgroupproject.common;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommentCheck {
    private static final String  ForbiddenWords =
            "(大傻子|笨蛋|蠢货|伏地魔|穷鬼|奥泽)";
    private static Pattern patternForbiddenWords = Pattern.compile(ForbiddenWords, Pattern.CASE_INSENSITIVE);

    public boolean ForbiddenWordsCheck(String article){
        Matcher a=patternForbiddenWords.matcher(article);
        return a.find();
    }

}
