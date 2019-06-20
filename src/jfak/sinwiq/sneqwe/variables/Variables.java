package jfak.sinwiq.sneqwe.variables;

import jfak.sinwiq.sneqwe.Main;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static jfak.sinwiq.sneqwe.Additional.*;
import static jfak.sinwiq.sneqwe.Constants.*;

@SuppressWarnings("ALL")
public class Variables {

    public  Variables(){


    }

    public static void variables(@NotNull String s){

        String[] splitText = s.split("\\s+");
        String sBytes = "";
        // M1 DD 111B
        if (Pattern.compile("\\s*[A-Z][A-Z0-9]{0,5}\\s+DD\\s+0?[A-Z0-9]*B?H?\\s*").matcher(s).matches()){
            CHECK_IF_ERROR = false;
            USER_ID_TABLE.add(splitText[0]);
            VARIABLE_TABLE.add(splitText);
            printTable();
            System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tuser id or undefined");
            System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tid directives data type 2");
            System.out.println("\t2\t" + splitText[2] + "\t" + splitText[2].length() + "\tconstant");

            printData();
            sBytes = convertNumberToHex(splitText[2]);
            addInFirstPass(4, s, sBytes + SPECIAL_NUMBER_FOR_MACRO);
        }
        else
            // M2 DW 002ah
            if (Pattern.compile("\\s*[A-Z][A-Z0-9]{0,5}\\s+DW\\s+0?[A-Z0-9]*B?H?\\s*").matcher(s).matches()){
                CHECK_IF_ERROR = false;
                USER_ID_TABLE.add(splitText[0]);
                VARIABLE_TABLE.add(splitText);
                printTable();
                System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tuser id or undefined");
                System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tid directives data type 3");
                System.out.println("\t2\t" + splitText[2] + "\t" + splitText[2].length() + "\tconstant");

                printData();
                sBytes = convertNumberToHex(splitText[2]);
                addInFirstPass(2, s, sBytes + SPECIAL_NUMBER_FOR_MACRO);
            }
            else
                // M3 DB 'asdasd'
                if (Pattern.compile("\\s*[A-Z0-9][A-Z0-9]{0,5}\\s+DB\\s+('[a-zA-Z _@!.0-9]*')?(\\d*B?H?)?\\s*?").matcher(s).matches()){

                    CHECK_IF_ERROR = false;
                    splitText = Main.getStrings(splitText);

                    USER_ID_TABLE.add(splitText[0]);
                    VARIABLE_TABLE.add(splitText);
                    printTable();
                    System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tuser id or undefined");
                    System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tid directives data type 1");
                    System.out.println("\t2\t" + splitText[2] + "\t" + splitText[2].length() + "\tconstant");

                    printData();
                    // TODO: if we`ll have a DB constant not string
                    if (splitText[2].charAt(splitText[2].length() - 1) == 'B'){
                        sBytes = convertNumberToHex(splitText[2].substring(1, splitText[2].length() - 1));
                        addInFirstPass(1, s, sBytes + SPECIAL_NUMBER_FOR_MACRO);
                    }else{
                        sBytes = convertStringToHex(splitText[2].substring(1, splitText[2].length() - 1));
                        addInFirstPass(splitText[2].length() - 2, s, sBytes + SPECIAL_NUMBER_FOR_MACRO);
                    }
                }
    }

    private static void printData(){
        printSyntax(0, 1,1,2,1,-1,-1);
    }
}
