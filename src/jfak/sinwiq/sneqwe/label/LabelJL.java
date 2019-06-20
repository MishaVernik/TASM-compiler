package jfak.sinwiq.sneqwe.label;

import jfak.sinwiq.sneqwe.Additional;
import jfak.sinwiq.sneqwe.Constants;
import jfak.sinwiq.sneqwe.aas.Aas;
import jfak.sinwiq.sneqwe.and.And;
import jfak.sinwiq.sneqwe.bt.Bt;
import jfak.sinwiq.sneqwe.cmp.Cmp;
import jfak.sinwiq.sneqwe.dec.Dec;
import jfak.sinwiq.sneqwe.inc.Inc;
import jfak.sinwiq.sneqwe.macro.Macro;
import jfak.sinwiq.sneqwe.mov.Mov;
import jfak.sinwiq.sneqwe.or.Or;
import jfak.sinwiq.sneqwe.segment.Segment;
import jfak.sinwiq.sneqwe.variables.Variables;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static jfak.sinwiq.sneqwe.Additional.*;
import static jfak.sinwiq.sneqwe.Constants.*;

public class LabelJL {
    private static String whatChange;

    public LabelJL(){}

    public static void labelJL(@NotNull String s){

        String[] splitText = superSplitter(s);

        //JL LBLJL
        if (Pattern.compile("\\s*JL\\s+[A-Z][A-Z0-9]{0,5}\\s*").matcher(s).matches()){
            String sBytes = "7C";
            String offset = getOffsetFromFirstPass(splitText, 1);
            if (ifContainsInLABEL_TABLE(splitText[1])){

                    JUMP_NUMBER -= SHIFT + 1;
                    String hexAddress = String.format("%1$02X",JUMP_NUMBER);
                    sBytes += " " + hexAddress;


                addInFirstPass(2,s,sBytes);
            }else{
                if (offset == "")
                    sBytes = "7C " + "____ 90 90";
                else sBytes = "0F 8C " + offset + " R";
                addInFirstPass(4,s, sBytes + SPECIAL_NUMBER_FOR_MACRO);
            }
            CHECK_IF_ERROR = false;
            LABEL_TABLE.add(splitText);
            printTable();
            System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tjl id");
            System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tuser id or undefined");
            printSyntax(-1, 0,1,1,1,-1,-1);
            // if label before JL

            // TODO: task
            // if label after JL
            // addInFirstPass(4, s);
        }
        // INCAX
        if (USER_ID_TABLE.contains(splitText[0]) && splitText.length == 1){
            boolean t = false;
            boolean bPrint = false;
            CHECK_IF_ERROR = false;
            for (String[] el : MACRO_TABLE) {

                for (int i = 0; i < el.length; i++) {

                    if (splitText[0].contains(el[i]) && !bPrint){
                        printTable();
                        SPECIAL_NUMBER_FOR_MACRO =  " 1";
                        System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tmacro id");
                        printSyntax(0, -1,-1,-1,-1,-1,-1);
                        addInFirstPass(0,s, "");

                        bPrint = true;
                        break;
                    }

                }
                if (bPrint){
                    StringBuilder builder = new StringBuilder();
                    for(String elm : el) {
                        builder.append(elm + " ");
                    }
                    String str = builder.toString();
                    if (macroChecking(str)) break;

                }
            }
        }
        // INCNUM M1
        if (USER_ID_TABLE.contains(splitText[0]) && splitText.length == 2 && USER_ID_TABLE.contains(splitText[1])){
            boolean t = false;
            boolean bPrint = false;
            CHECK_IF_ERROR = false;
            for (String[] el : MACRO_TABLE) {

                for (int i = 0; i < el.length; i++) {

                    if (splitText[0].equals(el[i]) && !bPrint){
                        printTable();
                        SPECIAL_NUMBER_FOR_MACRO =  " 1";
                        System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tmacro id with params");
                        System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tparams");
                        printSyntax(0, -1,-1,1,1,-1,-1);
                        addInFirstPass(0,s, "");
                        bPrint = true;
                        break;
                    }

                }
                if (bPrint){
                    int tmp = 0;
                    String toChange = splitText[1];
                    StringBuilder builder = new StringBuilder();
                    for(String elm : el) {
                        if (tmp == 3) {
                            whatChange = elm;
                        }
                        if (elm.contains(splitText[0])) tmp = 1;
                        if (tmp != 3)
                        tmp++;

                        builder.append(elm + " ");
                    }
                    String str = builder.toString();
                   str = str.replace(whatChange, toChange);
                    if (macroChecking(str)) break;

                }
            }
        }

        //LBLJL:
        if (Pattern.compile("\\s*[A-Z][A-Z0-9]{0,5}:\\s*").matcher(s).matches()){
            LABEL_TABLE.add(splitText);
            printTable();
            System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tuser id or undefined");
            System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tone symbol");

            printSyntax(0, 1,1,-1,-1,-1,-1);
            CHECK_IF_ERROR = false;
            addInFirstPass(0, s, "");
        }

    }

    private static boolean macroChecking(@NotNull String str) {
        boolean t;
        if (str.contains("ENDM")){
            SPECIAL_NUMBER_FOR_MACRO =  "";
            t = true;
            return true;
        }
        if (str.contains("MACRO") == false) {
            System.out.println("\n" + str);
            System.out.println("------------------------------");
        }
        Additional.checkLexeme(str, 4);
        return false;
    }
}
