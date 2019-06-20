package jfak.sinwiq.sneqwe;

import jfak.sinwiq.sneqwe.aas.Aas;
import jfak.sinwiq.sneqwe.and.And;
import jfak.sinwiq.sneqwe.bt.Bt;
import jfak.sinwiq.sneqwe.cmp.Cmp;
import jfak.sinwiq.sneqwe.dec.Dec;
import jfak.sinwiq.sneqwe.inc.Inc;
import jfak.sinwiq.sneqwe.label.LabelJL;
import jfak.sinwiq.sneqwe.macro.Macro;
import jfak.sinwiq.sneqwe.mov.Mov;
import jfak.sinwiq.sneqwe.or.Or;
import jfak.sinwiq.sneqwe.segment.Segment;
import jfak.sinwiq.sneqwe.variables.Variables;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static jfak.sinwiq.sneqwe.Constants.*;

public class Additional {

    public static Segment segment;
    public static Variables variables;
    public static Macro macro;
    public static Mov mov;
    public static Bt bt;
    public static Inc inc;
    public static Aas aas;
    public static Dec dec;
    public static  And and;
    public static Or or;
    public static Cmp cmp;
    public static LabelJL labelJL;


    public static int index = 1;
    @NotNull
    public static String convertStringToHex(@NotNull String str){

        char[] chars = str.toCharArray();

        StringBuffer hex = new StringBuffer();
        for(int i = 0; i < chars.length; i++){
            hex.append(Integer.toHexString((int)chars[i]) + " ");
        }

        return hex.toString();
    }
    public static  String convertNumberToHex(@NotNull String numberS){
        String number = "";
        if (numberS.charAt(numberS.length()-1) == 'H'){
            number = String.format("%1$04X",Integer.parseInt(numberS.substring(0, numberS.length() - 1), 16));
        }else
        if (numberS.charAt(numberS.length()-1) == 'B'){
            int num = Integer.parseInt(numberS.substring(0, numberS.length() - 1), 2);
            if (num > 255){
                number =  String.format("%1$04X",num);
            }else{
                number =  String.format("%1$02X",num);
            }

        }else {
            number =  String.format("%1$04X",Integer.parseInt(numberS));
        }

        return number;
    }
    @Contract(pure = true)
    public static boolean ifContainsInLABEL_TABLE(String splitText){

        for (String[] el : LABEL_TABLE){
            for (String item : el){
                if (item.equals(splitText))
                    return true;
            }
        }
        return false;
    }
    public static String getOffsetForLABELS(String[] splitText, int ind) {
        String shift = "";
        for (String el: FIRST_PASS) {
            if (el.contains(splitText[ind] + ":")){
                String[] arr = el.split("\\s+");
                shift = arr[1];
                return shift;

            }
        }
        return shift;
    }
    public static String getOffsetFromFirstPass(String[] splitText, int ind) {
        String shift = "";
        for (String el: FIRST_PASS) {
            if (el.contains(splitText[ind])){
                String[] arr = el.split("\\s+");
                shift = arr[1] + " R";
                return shift;

            }
        }
        return shift;
    }
    public static int countOffset(@NotNull String s){
        int number = 0;
        String[] arr = s.split("\\s+");
        for (String el : arr){
            number += el.length() / 2;
        }

        return number;
    }
    public static void addInFirstPass(int offset, String s, String bytes){
        index++;

        String hexAddress = String.format("%1$04X",SHIFT);
        FIRST_PASS.add(String.valueOf(index) + "\t" + hexAddress + "\t"  + "\t" + bytes + "\t\t" + s);
        if (!LOCK_COUNT_IF_MACRO){
            SHIFT = SHIFT + offset;
        }
    }
    private static int insertIndex = 0;
    public static String[] superSplitter(@NotNull String s){
        String[] dividerLine = s.split("\\s+");
        String[] tmp = new String[0];
        String symbol = "";
        ArrayList<String> list = new ArrayList<String>();
        for (String str : dividerLine){
            if (str.contains(",")){
                tmp = str.split(",");
                tmp = getSelfRecursiveChecking(tmp);
                symbol = ",";
            }else
            if (str.contains("[")){
                tmp = str.split("\\[");
                tmp = getSelfRecursiveChecking(tmp);
                symbol = "[";
            }else
            if (str.contains("]")){
                tmp = str.split("\\]");
                tmp = getSelfRecursiveChecking(tmp);
                symbol = "]";
            }else
            if (str.contains("+")){
                tmp = str.split("\\+");
                tmp = getSelfRecursiveChecking(tmp);
                symbol = "+";
            }else
            if (str.contains(":")){
                tmp = str.split(":");
                tmp = getSelfRecursiveChecking(tmp);
                symbol = ":";
            }

            if (tmp.length > 0 && symbol != ""){
                List<String> lst = new LinkedList<String>(Arrays.asList(tmp));
                if (str.indexOf(symbol.charAt(0)) == 0){
                    lst.add(0, symbol);
                }else
                if (lst.size() > 1 && str.indexOf(symbol.charAt(0)) == str.length() - 1){
                    lst.add(lst.size(), symbol);
                }else{
                    if (insertIndex == 0){
                        lst.add(1, symbol);
                    }
                    else{
                        if (insertIndex > lst.size() - 1){
                            lst.add(lst.size(), symbol);
                        }else
                            lst.add(insertIndex, symbol);
                        insertIndex = 0;
                    }

                }
                tmp = new String[0];
                symbol = "";
                for (String el : lst){
                    if (el.length() > 0){
                        list.add(el);
                    }

                }

            }
            else{
                list.add(str);
            }
        }
        dividerLine = list.toArray(new String[0]);
        return dividerLine;
    }
    @NotNull
    private static String[] getSelfRecursiveChecking(@NotNull String[] tmp) {
        List<String> lst = new LinkedList<String>();
        for (String t : tmp){
            String[] testingFunctionSuperSplitter = superSplitter(t);
            if (testingFunctionSuperSplitter.length > 1){
                boolean t1 = false;
                for (String recursiveStr : testingFunctionSuperSplitter){
                    if (recursiveStr.length() > 0){
                        lst.add(recursiveStr);
                        t1 = true;
                    }

                }
                if (t1)
                    insertIndex++;
            }else{
                if (t.length() > 0)
                    lst.add(t);
            }
        }
        tmp = lst.toArray(new String[0]);
        return tmp;
    }
    public static void printTable(){

        System.out.println("\t№ \tLexeme\tLength\tType");
        System.out.println("----------------------------------------------------------");
    }
    public static void printSyntax(int labelField, int mnemField, int sizeMnemField, int operand1, int sizeOfOperand1, int operand2, int sizeOfOperand2){
        System.out.println("\nLabel field/name: " + labelField +
                "\nMnemonic field: " + mnemField +
                "\nSize mnemonic: " + sizeMnemField +
                "\nOperand № 1: " + operand1 +
                "\nSize of operand № 1: " + sizeOfOperand1 +
                "\nOperand № 2: " + operand2 +
                "\nSize of operand № 2: " + sizeOfOperand2);
    }
    public static void checkLexeme(String s, int check){
        // CDATA SEGMENT
        segment.segment(s);
        // CDATA ENDS

        // M1 DD 111b
        // M2 DW 002ah
        // M3 DB 'asdasd'
        variables.variables(s);


        //INCAX MACRO
        //INCNUM MACRO NUMBER
        if (check != 4)
        macro.macro(s);
        //ENDM

        //MOV AX, 01h
        //MOV AX, 11b
        mov.mov(s);

        //BT AX, BX
        bt.bt(s);

        //AAS
        aas.aas(s);
        //INC CX
        inc.inc(s);

        //DEC WORD PTR [DI]
        dec.dec(s);

        //AND AX,WORD PTR [DI]
        and.and(s);

        //OR WORD PTR[DI + BX], 0001B
        or.or(s);

        //CMP WORD PTR [DI], DX
        cmp.cmp(s);
        //JL LBLJL
        labelJL.labelJL(s);

        //END
    }
}
