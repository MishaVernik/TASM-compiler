package jfak.sinwiq.sneqwe.and;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static jfak.sinwiq.sneqwe.Additional.*;
import static jfak.sinwiq.sneqwe.Constants.*;

public class And {

    public And() {
    }

    public static void and(@NotNull String s) {

        String[] splitText = superSplitter(s);

        //AND AX,WORD PTR [DI]
        if (Pattern.compile("\\s*AND\\s+[A-Z][A-Z0-9]{0,5}\\s*,\\s*WORD\\s+PTR\\s+\\[[A-Z][A-Z0-9]{0,5}\\]\\s*").matcher(s).matches()) {


            printTable();
            System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tand mnemonic name");
            System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tregister 16x");
            System.out.println("\t2\t" + splitText[2] + "\t" + splitText[2].length() + "\tcoma one symbol");
            System.out.println("\t3\t" + splitText[3] + "\t" + splitText[3].length() + "\tid type 3");
            System.out.println("\t4\t" + splitText[4] + "\t" + splitText[4].length() + "\tid operator type defining");
            System.out.println("\t5\t" + splitText[5] + "\t" + splitText[5].length() + "\tone symbol");
            /*
                TODO: check whether register or user ID
             */
            System.out.println("\t6\t" + splitText[6] + "\t" + splitText[6].length() + "\tregister 16x");
            System.out.println("\t7\t" + splitText[7] + "\t" + splitText[7].length() + "\tone symbol");
            printSyntax(-1, 0, 1, 1, 1, 3, 5);
// 2 if DI
            CHECK_IF_ERROR = false;
            String sBytes = "";
            if (splitText[1].equals("AX")) {
                if (splitText[6].equals("BP")) {
                    sBytes += "23 46 00";
                } else if (splitText[6].equals("SI")) {
                    sBytes += "23 04";
                } else if (splitText[6].equals("DI")) {
                    sBytes += "23 05";
                } else if (splitText[6].equals("BX")) {
                    sBytes += "23 46 00";
                }else sBytes +="Error-----";
            } else
                if (splitText[1].equals("BX")) {
                if (splitText[6].equals("BP")) {
                    sBytes += "23 5E 00";
                } else
                    if (splitText[6].equals("SI")) {
                    sBytes += "23 1C";
                } else
                    if (splitText[6].equals("DI")) {
                    sBytes += "23 1D";
                } else
                    if (splitText[6].equals("BX")) {
                    sBytes += "23 04";
                } else
                    sBytes +="Error-----";
            } else
                if (splitText[1].equals("CX")) {
                if (splitText[6].equals("BP")) {
                    sBytes += "23 4E 00";
                } else
                    if (splitText[6].equals("SI")) {
                    sBytes += "23 0C";
                } else
                    if (splitText[6].equals("DI")) {
                    sBytes += "23 0D";
                } else
                    if (splitText[6].equals("BX")) {
                    sBytes += "23 04";
                } else sBytes +="Error-----";
            } else
                if (splitText[1].equals("DX")) {
                if (splitText[6].equals("BP")) {
                    sBytes += "23 56 00";
                } else
                    if (splitText[6].equals("SI")) {
                    sBytes += "23 14";
                } else
                    if (splitText[6].equals("DI")) {
                    sBytes += "23 15";
                } else
                    if (splitText[6].equals("BX")) {
                    sBytes += "23 04";
                } else sBytes +="Error-----";
            } else
                if (splitText[1].equals("AL")) {
                if (splitText[6].equals("BP")) {
                    sBytes += "22 46 00";
                } else if (splitText[6].equals("SI")) {
                    sBytes += "22 04";
                } else
                    if (splitText[6].equals("DI")) {
                    sBytes += "22 05";
                } else if (splitText[6].equals("BX")) {
                    sBytes += "23 04";
                } else sBytes +="Error-----";
            } else
                if (splitText[1].equals("BL")) {
                if (splitText[6].equals("BP")) {
                    sBytes += "22 5E 00";
                } else if (splitText[6].equals("SI")) {
                    sBytes += "22 1C";
                } else if (splitText[6].equals("DI")) {
                    sBytes += "22 1D";
                } else if (splitText[6].equals("BX")) {
                    sBytes += "23 04";
                } else sBytes +="Error-----";
            } else
                if (splitText[1].equals("CL")) {
                if (splitText[6].equals("BP")) {
                    sBytes += "22 4E 00";
                } else
                    if (splitText[6].equals("SI")) {
                    sBytes += "22 0C";
                } else
                    if (splitText[6].equals("DI")) {
                    sBytes += "22 0D";
                } else if (splitText[6].equals("BX")) {
                    sBytes += "23 04";
                }
                else sBytes +="Error-----";
            } else
                if (splitText[1].equals("DL")) {
                if (splitText[6].equals("BP")) {
                    sBytes += "22 56 00";
                } else
                    if (splitText[6].equals("SI")) {
                    sBytes += "22 14";
                } else
                    if (splitText[6].equals("DI")) {
                    sBytes += "22 15";
                } else
                    if (splitText[6].equals("BX")) {
                    sBytes += "23 04";
                } else sBytes +="Error-----";
            } else
                sBytes += "Error -------------";
            // 3 if BP
          /*  if (splitText[6].equals("BP")) {
                addInFirstPass(3, s, sBytes);
            } else if (splitText[6].equals("DI")) {
                addInFirstPass(2, s, sBytes);
            }
*/
            int offset = countOffset(sBytes);
            addInFirstPass(offset, s, sBytes + SPECIAL_NUMBER_FOR_MACRO);
        }
        //AND AX, M2
        if (Pattern.compile("\\s*AND\\s+[A-Z][A-Z0-9]{0,5}\\s*,\\s*\\[A-Z][A-Z0-9]{0,5}\\s*").matcher(s).matches()) {

            CHECK_IF_ERROR = false;
            printTable();
            System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tand mnemonic name");
            System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tregister 16x");
            System.out.println("\t2\t" + splitText[2] + "\t" + splitText[2].length() + "\tcoma one symbol");
            System.out.println("\t3\t" + splitText[3] + "\t" + splitText[3].length() + "\tmemory");

            printSyntax(-1, 0, 1, 1, 1, 3, 3);
            String sBytes = "";
            String number = getOffsetFromFirstPass(splitText, 3);
            if (USER_ID_TABLE.contains(splitText[3])) {
                if (splitText[1].equals("AX")){
                    sBytes += "23 06 " + number;
                }else
                    if (splitText[1].equals("BX")){
                        sBytes += "23 1E " + number;
                }else
                    if (splitText[1].equals("CX")){
                        sBytes += "23 0E " + number;
                }else
                    if (splitText[1].equals("DX")){
                        sBytes += "23 16 " + number;
                }else
                    if (splitText[1].equals("AL")){
                        sBytes += "22 06 " + number;
                    }else
                    if (splitText[1].equals("BL")){
                        sBytes += "22 1E " + number;
                    }else
                    if (splitText[1].equals("CL")){
                        sBytes += "22 0E " + number;
                    }else if (splitText[1].equals("DL")){
                        sBytes += "22 16 " + number;
                    }else sBytes += "Error -------";

             }else sBytes +="Error-----";

            addInFirstPass(4, s, sBytes + SPECIAL_NUMBER_FOR_MACRO);
        }
    }
}
