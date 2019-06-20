package jfak.sinwiq.sneqwe.aas;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static jfak.sinwiq.sneqwe.Additional.*;
import static jfak.sinwiq.sneqwe.Constants.CHECK_IF_ERROR;
import static jfak.sinwiq.sneqwe.Constants.SPECIAL_NUMBER_FOR_MACRO;

public class Aas {

    public Aas(){}

    public static void aas(@NotNull String s){

        String[] splitText = s.split("\\s+");
        //AAS
        if (Pattern.compile("\\s*AAS\\s*").matcher(s).matches()){

            printTable();
            System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\taas mnemonic name");
            printSyntax(-1, 0,1,-1,-1,-1,-1);

            String sBytes = "3F";
            CHECK_IF_ERROR = false;
            addInFirstPass(1, s, sBytes + SPECIAL_NUMBER_FOR_MACRO);
        }
    }
}

