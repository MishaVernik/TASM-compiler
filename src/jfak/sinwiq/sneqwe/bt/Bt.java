package jfak.sinwiq.sneqwe.bt;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static jfak.sinwiq.sneqwe.Additional.*;
import static jfak.sinwiq.sneqwe.Constants.*;

public class Bt {
    public Bt(){}

    public static String[] REGISTER16X = {"AX", "BX", "CX", "DX"};

    public  static void bt(@NotNull String s){

        String[] splitText = superSplitter(s);
        //BT AX, BX
        if (Pattern.compile("\\s*BT\\s+[A-Z][A-Z0-9]{0,5}\\s*,\\s*[A-Z][A-Z0-9]{0,5}\\s*").matcher(s).matches()){
            CHECK_IF_ERROR = false;
            printTable();
            System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tbt mnemonic name");
            System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tregister 16x");
            System.out.println("\t2\t" + splitText[2] + "\t" + splitText[2].length() + "\tcoma one symbol");
            System.out.println("\t3\t" + splitText[3] + "\t" + splitText[3].length() + "\tregister 16x");

            printSyntax(-1, 0,1,1,1,3,1);
            String sBytes = "0F A3";
            if (splitText[1].equals("AX")){
                if (splitText[3].equals("AX")) sBytes += " C0"; else
                if (splitText[3].equals("BX")) sBytes += " D8"; else
                if (splitText[3].equals("CX")) sBytes += " C8"; else
                if (splitText[3].equals("DX")) sBytes += " D0"; else
                    sBytes += " Error, there is no register 16x";
            }else
                if (splitText[1].equals("BX")){
                    if (splitText[3].equals("AX")) sBytes += " C3"; else
                    if (splitText[3].equals("BX")) sBytes += " DB"; else
                    if (splitText[3].equals("CX")) sBytes += " CB"; else
                    if (splitText[3].equals("DX")) sBytes += " D3"; else
                        sBytes += " Error, there is no register 16x";
            }else
                if (splitText[1].equals("CX")){
                    if (splitText[3].equals("AX")) sBytes += " C1"; else
                    if (splitText[3].equals("BX")) sBytes += " D9"; else
                    if (splitText[3].equals("CX")) sBytes += " C9"; else
                    if (splitText[3].equals("DX")) sBytes += " D1"; else
                        sBytes += " Error, there is no register 16x";
                }
                else
                    if (splitText[1].equals("DX")){
                        if (splitText[3].equals("AX")) sBytes += " C2"; else
                        if (splitText[3].equals("BX")) sBytes += " DA"; else
                        if (splitText[3].equals("CX")) sBytes += " CA"; else
                        if (splitText[3].equals("DX")) sBytes += " D2"; else
                            sBytes += " Error, there is no register 16x";
                }
            addInFirstPass(3, s, sBytes + SPECIAL_NUMBER_FOR_MACRO);
        }
    }
}
