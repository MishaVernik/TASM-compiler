package jfak.sinwiq.sneqwe.or;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static jfak.sinwiq.sneqwe.Additional.*;
import static jfak.sinwiq.sneqwe.Constants.*;

public class Or {

    public Or(){}

    public static void or(@NotNull String s){
        String[] splitText = superSplitter(s);

        //OR WORD PTR[DI + BX], 0001B
        if (Pattern.compile("\\s*OR\\s+WORD\\s+PTR\\s*\\[[A-Z][A-Z0-9]{0,5}\\s*\\+\\s*[A-Z][A-Z0-9]{0,5}\\s*\\]\\s*,\\s*[a-zA-Z0-9]+\\s*").matcher(s).matches()){
            CHECK_IF_ERROR = false;
            printTable();
            System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tor mnemonic name");
            System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tid type 3");
            System.out.println("\t2\t" + splitText[2] + "\t" + splitText[2].length() + "\tid operator type defining");
            System.out.println("\t3\t" + splitText[3] + "\t" + splitText[3].length() + "\tone symbol");
            System.out.println("\t4\t" + splitText[4] + "\t" + splitText[4].length() + "\tregister 16x");
            System.out.println("\t5\t" + splitText[5] + "\t" + splitText[5] + "\tone symbol");
            System.out.println("\t4\t" + splitText[6] + "\t" + splitText[6].length() + "\tregister 16x");
            System.out.println("\t6\t" + splitText[7] + "\t" + splitText[7].length() + "\tcoma one symbol");

            System.out.println("\t7\t" + splitText[8] + "\t" + splitText[8].length() + "\tone symbol");
            System.out.println("\t7\t" + splitText[9] + "\t" + splitText[9].length() + "\tconst");

            printSyntax(-1, 0,1,1,7,9,1);
            String sBytes = "";
            String number = convertNumberToHex(splitText[9]);
            if (splitText[4].equals("BX")) {
                if (splitText[6].equals("SI")) {
                    sBytes += "83 08 " + number;
                } else if (splitText[6].equals("DI")) {
                    sBytes += "81 09 " + number;
                }
            }else
            if (splitText[4].equals("BP")) {
                if (splitText[6].equals("SI")) {
                    sBytes += "81 0A " + number;
                } else if (splitText[6].equals("DI")) {
                    sBytes += "81 0B " + number;
                }
            }else
                sBytes += "Error there is no register16x";

            // Change 3 or 2 number according to sBytes
            int offset = countOffset(sBytes);
            addInFirstPass(offset, s, sBytes + SPECIAL_NUMBER_FOR_MACRO);
        }

        //OR WORD PTR[DI], 0001B
        if (Pattern.compile("\\s*OR\\s+WORD\\s+PTR\\s*\\[[A-Z][A-Z0-9]{0,5}\\s*\\]\\s*,\\s*[a-zA-Z0-9]+\\s*").matcher(s).matches()){
            CHECK_IF_ERROR = false;
            printTable();
            System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tor mnemonic name");
            System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tid type 3");
            System.out.println("\t2\t" + splitText[2] + "\t" + splitText[2].length() + "\tid operator type defining");
            System.out.println("\t3\t" + splitText[3] + "\t" + splitText[3].length() + "\tone symbol");
            System.out.println("\t4\t" + splitText[4] + "\t" + splitText[4].length() + "\tregister 16x");
            System.out.println("\t6\t" + splitText[5] + "\t" + splitText[5].length() + "\tcoma one symbol");
            System.out.println("\t7\t" + splitText[6] + "\t" + splitText[6].length() + "\tone symbol");
            System.out.println("\t7\t" + splitText[7] + "\t" + splitText[7].length() + "\tconst");

            printSyntax(-1, 0,1,1,7,9,1);
            String sBytes = "";
            String number = convertNumberToHex(splitText[7]);
            if (splitText[4].equals("BX")) {
                sBytes += "81 0F " + number;
            }else
            if (splitText[4].equals("SI")) {
                sBytes += "83 0C " + number;
            }else
            if (splitText[4].equals("DI")) {
                sBytes += "83 0D " + number;
            }else
                sBytes += "Error there is no register16x";

            // Change 3 or 2 number according to sBytes
            int offset = countOffset(sBytes);
            addInFirstPass(offset, s, sBytes + SPECIAL_NUMBER_FOR_MACRO);
        }

        //OR M2, 0001B
        if (Pattern.compile("\\s*OR\\s*[A-Z][A-Z0-9]{0,5}\\s*,\\s*[a-zA-Z0-9]*\\s*").matcher(s).matches()){
            CHECK_IF_ERROR = false;
            printTable();
            System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tor mnemonic name");
            System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tmemory");
            System.out.println("\t2\t" + splitText[2] + "\t" + splitText[2].length() + "\tcoma one symbol");
            System.out.println("\t2\t" + splitText[3] + "\t" + splitText[3].length() + "\tconstant");

            // TODO: change pritSyntax ---->
            printSyntax(-1, 0,1,1,7,9,1);
            String sBytes = "";
            String number = convertNumberToHex(splitText[3]);
            if (USER_ID_TABLE.contains(splitText[1])) {
                sBytes += "83 0E" + getOffsetFromFirstPass(splitText, 1) + " " + number;
            }else{
                sBytes +="Error -------------";
            }
            int offset = countOffset(sBytes);
            addInFirstPass(offset, s, sBytes + SPECIAL_NUMBER_FOR_MACRO);
        }
    }
}
