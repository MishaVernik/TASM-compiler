package jfak.sinwiq.sneqwe.cmp;

import jfak.sinwiq.sneqwe.dec.Dec;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static jfak.sinwiq.sneqwe.Additional.*;
import static jfak.sinwiq.sneqwe.Constants.*;

public class Cmp {

    public Cmp(){}

    public static void cmp(@NotNull String s){

        String[] splitText = superSplitter(s);

        //CMP WORD PTR[BP], DX
        if (Pattern.compile("\\s*CMP\\s+WORD\\s+PTR\\s*\\[[A-Z][A-Z0-9]{0,5}\\]\\s*,\\s*[A-Z][A-Z0-9]{0,5}\\s*").matcher(s).matches()){
            printTable();
            CHECK_IF_ERROR = false;
            System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tcmp mnemonic name");
            System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tid type 3");
            System.out.println("\t2\t" + splitText[2] + "\t" + splitText[2].length() + "\tid operator type defining");
            System.out.println("\t3\t" + splitText[3] + "\t" + splitText[3].length() + "\tone symbol");
            System.out.println("\t4\t" + splitText[4] + "\t" + splitText[4].length() + "\tregister 16x");
            System.out.println("\t5\t" + splitText[5] + "\t" + splitText[5].length() + "\tone symbol");
            System.out.println("\t6\t" + splitText[6] + "\t" + splitText[6].length() + "\tcoma one symbol");
            System.out.println("\t7\t" + splitText[7] + "\t" + splitText[7].length() + "\tregister 16x");
            printSyntax(-1, 0,1,1,5,7,1);

            String sBytes = "39";
            if (splitText[4].equals("BP")) {
                if (splitText[7].equals("AX") || splitText[7].equals("AL")) sBytes += " 46 00"; else
                if (splitText[7].equals("BX") || splitText[7].equals("BL")) sBytes += " 4E 00"; else
                if (splitText[7].equals("CX") || splitText[7].equals("CL")) sBytes += " 5E 00"; else
                if (splitText[7].equals("DX") || splitText[7].equals("DL")) sBytes += " 56 00"; else
                    sBytes += "Error there is no register16x";
            }else
            if (splitText[4].equals("SI")) {
                if (splitText[7].equals("AX") || splitText[7].equals("AL")) sBytes += " 04"; else
                if (splitText[7].equals("BX") || splitText[7].equals("BL")) sBytes += " 0C"; else
                if (splitText[7].equals("CX") || splitText[7].equals("CL")) sBytes += " 1C"; else
                if (splitText[7].equals("DX") || splitText[7].equals("DL")) sBytes += " 14"; else
                    sBytes += "Error there is no register16x";
            }else
            if (splitText[4].equals("DI")) {
                if (splitText[7].equals("AX") || splitText[7].equals("AL")) sBytes += " 05"; else
                if (splitText[7].equals("BX") || splitText[7].equals("BL")) sBytes += " 0D"; else
                if (splitText[7].equals("CX") || splitText[7].equals("CL")) sBytes += " 1D"; else
                if (splitText[7].equals("DX") || splitText[7].equals("DL")) sBytes += " 15"; else
                    sBytes += "Error there is no register16x";
            }

            getCMPoffset(s, splitText, sBytes, 7);
        }

        //CMP M2, DX
        if (Pattern.compile("\\s*CMP\\s*[A-Z][A-Z0-9]{0,5}\\s*,\\s*[A-Z][A-Z0-9]{0,5}\\s*").matcher(s).matches()){
            printTable();
            CHECK_IF_ERROR = false;
            System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tcmp mnemonic name");
            System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tuser id or constant");
            System.out.println("\t2\t" + splitText[2] + "\t" + splitText[2].length() + "\tone symbol");
            System.out.println("\t3\t" + splitText[3] + "\t" + splitText[3].length() + "\tregister 16x");

            // TODO: check print Syntaxis
            // printSyntax(-1, 0,1,1,5,7,1);
            String shift = getOffsetFromFirstPass(splitText, 1);
            String sBytes = "39";
            if (USER_ID_TABLE.contains(splitText[1])) {
                if (splitText[3].equals("AX") || splitText[3].equals("AL")) sBytes += " 06 " + shift; else
                if (splitText[3].equals("BX") || splitText[3].equals("BL")) sBytes += " 1E " + shift; else
                if (splitText[3].equals("CX") || splitText[3].equals("CL")) sBytes += " 0E " + shift; else
                if (splitText[3].equals("DX") || splitText[3].equals("DL")) sBytes += " 16 " + shift; else
                    sBytes += "Error there is no register";
            }else{
                sBytes +="Error there is no user ID with this name";
            }

            getCMPoffset(s, splitText, sBytes, 3);
        }

        //CMP WORD PTR[BX + SI], AX
    }

    private static void getCMPoffset(@NotNull String s, @NotNull String[] splitText, String sBytes, int index) {
        if (Pattern.compile("[ABCDLH]{2}").matcher(splitText[index]).matches()){
            addInFirstPass(2, s, sBytes + SPECIAL_NUMBER_FOR_MACRO);
        }else{
            if (USER_ID_TABLE.contains(splitText[index])) {
                addInFirstPass(4, s, sBytes + SPECIAL_NUMBER_FOR_MACRO);
            } else {
                addInFirstPass(3, s, sBytes + SPECIAL_NUMBER_FOR_MACRO);
            }
        }
    }
}
