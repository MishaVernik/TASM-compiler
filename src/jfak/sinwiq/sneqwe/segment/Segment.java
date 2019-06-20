package jfak.sinwiq.sneqwe.segment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import static jfak.sinwiq.sneqwe.Additional.*;
import static jfak.sinwiq.sneqwe.Constants.*;

public class Segment {

    public Segment(){

    }

    public static void segment(@NotNull String s){

        String[] splitText = s.split("\\s+");
        // CDATA SEGMENT
        if (Pattern.compile("\\s*[A-Z][A-Z0-9]{0,5}\\s+SEGMENT\\s*").matcher(s).matches()){
            CHECK_IF_ERROR = false;
            SEGMNET_TABLE.add(splitText);
            printTable();
            System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tuser id or undefined");
            System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tdirective");
            printSyntax(0, 1,1,-1,-1,-1,-1);
            CURRENT_SEGMENT++;
            addInFirstPass(0, s, "");
        }

        // CDATA ENDS
        if (Pattern.compile("\\s*[A-Z][A-Z0-9]{0,5}\\s+ENDS\\s*").matcher(s).matches()){
            SEGMNET_TABLE.add(splitText);
            CHECK_IF_ERROR = false;
            printTable();
            System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tuser id or undefined");
            System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tdirective end");
            printSyntax(0, 1,1,-1,-1,-1,-1);
            CURRENT_SEGMENT--;

            addInFirstPass(0, s, "");
            SHIFT = 0;
        }
    }
}
