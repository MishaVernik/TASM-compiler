package jfak.sinwiq.sneqwe.macro;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static jfak.sinwiq.sneqwe.Additional.*;
import static jfak.sinwiq.sneqwe.Constants.*;

public class Macro {

    private static boolean macroWithout = false;
    private static boolean macroWith = false;


    public Macro(){

    }

    public static void macro(@NotNull String s){

        String[] splitText = s.split("\\s+");

        //ENDM
        if (Pattern.compile("\\s*ENDM\\s*").matcher(s).matches()){
            macroWithout = false;
            SPECIAL_NUMBER_FOR_MACRO = "";
            macroWith = false;
            LOCK_COUNT_IF_MACRO = false;
            MACRO_TABLE.add(splitText);
            printTable();
            CHECK_IF_ERROR = false;
            System.out.println("\t1\t" + splitText[0] + "\t" + splitText[0].length() + "\tmacro end");
            addInFirstPass(0,s, "");
        }

        if (macroWithout){
            LOCK_COUNT_IF_MACRO = true;
            MACRO_TABLE.add(splitText);

        }
        if (macroWith){
            LOCK_COUNT_IF_MACRO = true;
            MACRO_TABLE.add(splitText);
        }
        //INCAX MACRO
        if (Pattern.compile("\\s*[A-Z][A-Z0-9]{0,5}\\s+MACRO\\s*").matcher(s).matches()){
            macroWithout = true;
            if (LOCK_COUNT_IF_MACRO)
                SPECIAL_NUMBER_FOR_MACRO = " 1";
            USER_ID_TABLE.add(splitText[0]);
            MACRO_TABLE.add(splitText);
            printTable();
            CHECK_IF_ERROR = false;
            System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tuser id or undefined");
            System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tmacro name");
            printSyntax(0, 1,1,-1,-1,-1,-1);
            addInFirstPass(0,s, "");
        }

        else
        //INCNUM MACRO NUMBER
        if (Pattern.compile("\\s*[A-Z][A-Z0-9]{0,5}\\s+MACRO\\s+[A-Z][A-Z0-9]{0,5}\\s*").matcher(s).matches()){
            macroWith = true;
            if (LOCK_COUNT_IF_MACRO)
            SPECIAL_NUMBER_FOR_MACRO = " 1";
            USER_ID_TABLE.add(splitText[0]);
            MACRO_TABLE.add(splitText);
            printTable();
            CHECK_IF_ERROR = false;
            System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tuser id or undefined");
            System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tmacro name");
            System.out.println("\t2\t" + splitText[2] + "\t" + splitText[2].length() + "\tuser params");
            printSyntax(0, 1,1,1,1,-1,-1);

            addInFirstPass(0,s, "");
        }




    }

}


