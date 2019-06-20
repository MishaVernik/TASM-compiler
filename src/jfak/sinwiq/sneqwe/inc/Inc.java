package jfak.sinwiq.sneqwe.inc;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static jfak.sinwiq.sneqwe.Additional.*;
import static jfak.sinwiq.sneqwe.Constants.*;

public class Inc {

    public Inc(){}

    public static void inc(@NotNull String s){

        String[] splitText = superSplitter(s);
        //INC CX
        if (Pattern.compile("\\s*INC\\s+[A-Z][A-Z0-9]{0,5}\\s*").matcher(s).matches()){
            CHECK_IF_ERROR = false;
            printTable();
            /*
                TODO: check if register in list of RESERVED_ID_NAMES
             */
            System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tinc mnemonic name");
            System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tregister 16x");

            printSyntax(-1, 0,1,2,1,-1,-1);
            String sBytes = "";
            if (!LOCK_COUNT_IF_MACRO) {
                if (splitText[1].equals("AX")) sBytes += " 40";
                else if (splitText[1].equals("BX")) sBytes += " 43";
                else if (splitText[1].equals("CX")) sBytes += " 41";
                else if (splitText[1].equals("DX")) sBytes += " 42";
                else if (splitText[1].equals("DI")) sBytes += " 47";
                else if (splitText[1].equals("SI")) sBytes += " 46";
                else if (splitText[1].equals("BP")) sBytes += " 45";
                else if (USER_ID_TABLE.contains(splitText[1]))
                    sBytes += "FF 06 " + getOffsetFromFirstPass(splitText, 1);
                else
                    sBytes += "Error there is no register16x";
            }
            addInFirstPass(countOffset(sBytes), s, sBytes + SPECIAL_NUMBER_FOR_MACRO);
            /*
            if (Pattern.compile("[ABCDLH]{2}").matcher(splitText[1]).matches()){
                addInFirstPass(2, s, sBytes);
            }else {
                if (USER_ID_TABLE.contains(splitText[1])) {
                    addInFirstPass(5, s, sBytes);
                } else {
                    addInFirstPass(1, s, sBytes);
                }
            }
            */
            // INC reg
            // 2 bytes

        }

    }
}
