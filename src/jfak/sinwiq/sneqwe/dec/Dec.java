package jfak.sinwiq.sneqwe.dec;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static jfak.sinwiq.sneqwe.Additional.*;
import static jfak.sinwiq.sneqwe.Constants.*;

public class Dec {
    public Dec(){}

    public static void dec(@NotNull String s){

        String[] splitText = superSplitter(s);
        //DEC WORD PTR [DI]
        if (Pattern.compile("\\s*DEC\\s+WORD\\s+PTR\\s*\\[\\s*[A-Z][A-Z0-9]{0,5}\\s*\\]\\s*").matcher(s).matches()){
            printTable();
            CHECK_IF_ERROR = false;
            System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tdec mnemonic name");
            System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tid type 3");
            System.out.println("\t2\t" + splitText[2] + "\t" + splitText[2].length() + "\tid operator type defining");
            System.out.println("\t3\t" + splitText[3] + "\t" + splitText[3].length() + "\tone symbol");
            /*
                TODO: check whether register or user ID
             */

            System.out.println("\t4\t" + splitText[4] + "\t" + splitText[4].length() + "\tregister 16x");
            System.out.println("\t3\t" + splitText[5] + "\t" + splitText[5].length() + "\tone symbol");

            printSyntax(-1, 0,1,1,5,-1,-1);

            String sBytes = "FF";

                if (splitText[4].equals("BP")) sBytes += " 4E 00"; else
                if (splitText[4].equals("SI")) sBytes += " 0C"; else
                if (splitText[4].equals("DI")) sBytes += " 0D"; else
                if (splitText[4].equals("BX")) sBytes += " 0F"; else
                    sBytes += "Error there is no register16x";

            if (splitText[4].equals("BP")){
                addInFirstPass(3, s, sBytes + SPECIAL_NUMBER_FOR_MACRO);
            }else
            if (splitText[4].equals("DI")){
                addInFirstPass(2, s, sBytes + SPECIAL_NUMBER_FOR_MACRO);
            }

            return;
        }
        //DEC SI
        if (Pattern.compile("\\s*DEC\\s*[A-Z][A-Z0-9]{0,5}\\s*").matcher(s).matches()){
            CHECK_IF_ERROR = false;
            printTable();
            System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tdec mnemonic name");
            System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tmemory");

            // TODO: change printSyntax ---->
            printSyntax(-1, 0,1,1,5,-1,-1);

            String sBytes = "";
          if (splitText[1].equals("SI")){
                sBytes = "4E";
            }else
            if (splitText[1].equals("DI")){
                sBytes = "4F";

            }else
            if (splitText[1].equals("AX")){
                sBytes = "48";

            }else
            if (splitText[1].equals("BX")){
                sBytes = "4B";

            }else
            if (splitText[1].equals("CX")){
                sBytes = "49";

            }else
                if (splitText[1].equals("DX")){
                sBytes = "4A";

            }else
            if (splitText[1].equals("AL")){
                sBytes = "FE C8";
            }
            else
            if (splitText[1].equals("BL")){
                sBytes = "FE CB";
            }
            else
            if (splitText[1].equals("CL")){
                sBytes = "FE C9";
            }
            else
            if (splitText[1].equals("DL")){
                sBytes = "FE CA";
            }
            else
            if (splitText[1].equals("BP")){
                sBytes = "4D";
            }else
                sBytes = "Error";



            String shift = getOffsetFromFirstPass(splitText, 1);

            addInFirstPass(1, s, sBytes + SPECIAL_NUMBER_FOR_MACRO);

            return;
        }

        //DEC M2
        if (Pattern.compile("\\s*DEC\\s*[A-Z][A-Z0-9]{0,5}\\s*").matcher(s).matches()){
            CHECK_IF_ERROR = false;
            printTable();
            System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tdec mnemonic name");
            System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tmemory");

            // TODO: change printSyntax ---->
            printSyntax(-1, 0,1,1,5,-1,-1);
            String sBytes = "FF 0E ";
            String shift = getOffsetFromFirstPass(splitText, 1);

            if (USER_ID_TABLE.contains(splitText[1])) {
                sBytes += shift;
            }else{
                sBytes +="Error there is no user ID with this name";
            }

            addInFirstPass(4, s, sBytes+ SPECIAL_NUMBER_FOR_MACRO);
        }

    }

}
