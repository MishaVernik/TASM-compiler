package jfak.sinwiq.sneqwe.mov;

import jfak.sinwiq.sneqwe.Additional;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static jfak.sinwiq.sneqwe.Additional.*;
import static jfak.sinwiq.sneqwe.Constants.*;

public class Mov {
    public Mov(){}

    public static void mov(@NotNull String s){

        String[] splitText = superSplitter(s);

        //MOV AX, 01h
        if (Pattern.compile("\\s*MOV\\s+[A-Z][A-Z0-9]{0,5}\\s*,\\s*0?[A-Z0-9]*H?B?\\s*").matcher(s).matches()){
            printTable();
            CHECK_IF_ERROR = false;
            System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() + "\tmnemonic id of machine instruction");
            /*
                TODO: check if splitText[1] in RESERVED_ID_NAMES
            */
            System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() + "\tregister 16x");
            System.out.println("\t2\t" + splitText[2] + "\t" + splitText[2].length() + "\tcoma one symbol");
            System.out.println("\t3\t" + splitText[3] + "\t" + splitText[3].length() + "\tconst");
            printSyntax(-1, 0,1,1,1,3,1);

            String number = Additional.convertNumberToHex(splitText[3]);

            String sBytes = "";
            if (splitText[1].equals("AX")) sBytes += "B8 " + number; else
            if (splitText[1].equals("BX")) sBytes += "BB " + number; else
            if (splitText[1].equals("CX")) sBytes += "B9 " + number; else
            if (splitText[1].equals("DX")) sBytes += "BA " + number; else
            if (splitText[1].equals("AL")) sBytes += "B0 " + number; else
            if (splitText[1].equals("BL")) sBytes += "B3 " + number; else
            if (splitText[1].equals("CL")) sBytes += "B2 " + number; else
            if (splitText[1].equals("DL")) sBytes += "B1 " + number; else
                sBytes += "Error there is no register16x";

            if (Pattern.compile("[ABCDLH]{2}").matcher(splitText[1]).matches()){
                addInFirstPass(2, s, sBytes + SPECIAL_NUMBER_FOR_MACRO);
            }else
            if (Pattern.compile("[ABCDX]{2}").matcher(splitText[1]).matches()){
                addInFirstPass(3, s, sBytes + SPECIAL_NUMBER_FOR_MACRO);
            }

        }
        //MOV AX, [BX+SI]
        if (Pattern.compile("\\s*MOV\\s+[A-Z][A-Z0-9]{0,5}\\s*,\\s*\\[[A-Z][A-Z0-9]{0,5}\\s*\\+\\s*[A-Z][A-Z0-9]{0,5}\\]\\s*").matcher(s).matches()){
            printTable();
            CHECK_IF_ERROR = false;
            System.out.println("\t0\t" + splitText[0] + "\t" + splitText[0].length() +  "\tid mnemonic machine instruction");
            System.out.println("\t1\t" + splitText[1] + "\t" + splitText[1].length() +  "\tregister 16x");
            System.out.println("\t2\t" + splitText[2] + "\t" + splitText[2].length() +  "\tcoma one symbol");
            System.out.println("\t4\t" + splitText[3] + "\t" + splitText[3].length() +  "\tone symbol");
            System.out.println("\t3\t" + splitText[4] + "\t" + splitText[4].length() +  "\tregister16x");
            System.out.println("\t5\t" + splitText[5] + "\t" + splitText[5].length() +  "\tone symbol");
            System.out.println("\t5\t" + splitText[6] + "\t" + splitText[6].length() +  "\tregister16x");
            printSyntax(-1, 0,1,1,1,3,5);
            String sBytes = "";

            if (splitText[1].equals("AX")) {
                if (splitText[4].equals("BX")){
                    if (splitText[6].equals("SI")){
                        sBytes += "8B 00";
                    }else
                    if (splitText[6].equals("DI")){

                    }
                }

            } else {
                sBytes = getBytesOffset(splitText, sBytes, 1,4,6);
            }
            addInFirstPass(2, s, sBytes + SPECIAL_NUMBER_FOR_MACRO);
        }
        //MOV CL, ES:[BX + SI]
        if (Pattern.compile("\\s*MOV\\s+[A-Z][A-Z0-9]{0,5}\\s*,\\s*[A-Z][A-Z0-9]{0,5}:\\[[A-Z][A-Z0-9]{0,5}\\s*\\+\\s*[A-Z][A-Z0-9]{0,5}\\]\\s*").matcher(s).matches()){
            printTable();
            CHECK_IF_ERROR = false;
            System.out.println("\t0\t" + splitText[0] + "\t"  + splitText[0].length() +  "\tmov");
            System.out.println("\t1\t" + splitText[1] + "\t"  + splitText[1].length() +  "\tregister 16x");
            System.out.println("\t2\t" + splitText[2] + "\t"  + splitText[2].length() +  "\tcoma one symbol");
            System.out.println("\t3\t" + splitText[3] + "\t"  + splitText[3].length() +  "\tdata segment");
            System.out.println("\t4\t" + splitText[4] + "\t"  + splitText[4].length() +  "\tone symbol");
            System.out.println("\t5\t" + splitText[5] + "\t"  + splitText[5].length() +  "\tone symbol");
            System.out.println("\t6\t" + splitText[6] + "\t"  + splitText[6].length() +  "\tregister 16x");
            System.out.println("\t7\t" + splitText[7] + "\t"  + splitText[7].length() +  "\tone symbol");
            System.out.println("\t8\t" + splitText[8] + "\t"  + splitText[8].length() +  "\tregister 16x");
            System.out.println("\t9\t" + splitText[9] + "\t"  + splitText[9].length() +  "\tone symbol");
            printSyntax(-1, 0,1,1,1,3,7);
            String sBytes = "";

            if (splitText[3].equals("ES")){ sBytes += "26: ";}else
            if (splitText[3].equals("CS")){ sBytes += "2E: ";}

            sBytes = getBytesOffset(splitText, sBytes, 1,6,8);

            addInFirstPass(3, s, sBytes + SPECIAL_NUMBER_FOR_MACRO);
        }
    }

    private static String getBytesOffset(@NotNull String[] splitText, String sBytes, int index1, int index2, int index3) {
        if (splitText[index1].equals("AX")) {
            if (splitText[index2].equals("BX")){
                if (splitText[index3].equals("SI")){
                    sBytes += "8B 00";
                }else
                if (splitText[index3].equals("DI")){
                    sBytes += "8B 01";
                }
            }else
            if (splitText[index2].equals("BP")){
                if (splitText[index3].equals("SI")){
                    sBytes += "8B 02";
                }else
                if (splitText[index3].equals("DI")){
                    sBytes += "8B 03";
                }
            }

        } else
        if (splitText[index1].equals("BX")) {
            if (splitText[index2].equals("BX")) {
                if (splitText[index3].equals("SI")) {
                    sBytes += "8B 18";
                } else if (splitText[index3].equals("DI")) {
                    sBytes += "8B 19";
                }
            }else
                if (splitText[index2].equals("BP")){
                    if (splitText[index3].equals("SI")){
                        sBytes += "8B 1A";
                    }else
                    if (splitText[index3].equals("DI")){
                        sBytes += "8B 1B";
                    }
                }
        } else
        if (splitText[index1].equals("CX")) {
            if (splitText[index2].equals("BX")) {
                if (splitText[index3].equals("SI")) {
                    sBytes += "8B 08";
                } else if (splitText[index3].equals("DI")) {
                    sBytes += "8B 09";
                }
            }
                else
                if (splitText[index2].equals("BP")){
                    if (splitText[index3].equals("SI")){
                        sBytes += "8B 0A";
                    }else
                    if (splitText[index3].equals("DI")){
                        sBytes += "8B 0B";
                    }
            }
        } else
        if (splitText[index1].equals("DX")) {
            if (splitText[index2].equals("BX")) {
                if (splitText[index3].equals("SI")) {
                    sBytes += "8B 10";
                } else if (splitText[index3].equals("DI")) {
                    sBytes += "8B 11";
                }
            }else
                if (splitText[index2].equals("BP")){
                    if (splitText[index3].equals("SI")){
                        sBytes += "8B 12";
                    }else
                    if (splitText[index3].equals("DI")){
                        sBytes += "8B 13";
                    }
                }
        } else
        if (splitText[index1].equals("AL")) {
            if (splitText[index2].equals("BX")){
                if (splitText[index3].equals("SI")){
                    sBytes += "8A 00";
                }else
                if (splitText[index3].equals("DI")){
                    sBytes += "8A 01";
                }
            }
        else
            if (splitText[index2].equals("BP")){
                if (splitText[index3].equals("SI")){
                    sBytes += "8A 02";
                }else
                if (splitText[index3].equals("DI")){
                    sBytes += "8A 03";
                }
            }
        } else
        if (splitText[index1].equals("BL")) {
            if (splitText[index2].equals("BX")){
                if (splitText[index3].equals("SI")){
                    sBytes += "8A 18";
                }else
                if (splitText[index3].equals("DI")){
                    sBytes += "8A 19";
                }
            }else
            if (splitText[index2].equals("BP")){
                if (splitText[index3].equals("SI")){
                    sBytes += "8A 1A";
                }else
                if (splitText[index3].equals("DI")){
                    sBytes += "8A 1B";
                }
            }
        } else
        if (splitText[index1].equals("CL")) {
            if (splitText[index2].equals("BX")){
                if (splitText[index3].equals("SI")){
                    sBytes += "8A 08";
                }else
                if (splitText[index3].equals("DI")){
                    sBytes += "8A 09";
                }
            }else
            if (splitText[index2].equals("BP")){
                if (splitText[index3].equals("SI")){
                    sBytes += "8A 0A";
                }else
                if (splitText[index3].equals("DI")){
                    sBytes += "8A 0B";
                }
            }
        } else
        if (splitText[index1].equals("DL")) {
            if (splitText[index2].equals("BX")){
                if (splitText[index3].equals("SI")){
                    sBytes += "8A 10";
                }else
                if (splitText[index3].equals("DI")){
                    sBytes += "8A 11";
                }
            }else
            if (splitText[index2].equals("BP")){
                if (splitText[index3].equals("SI")){
                    sBytes += "8A 12";
                }else
                if (splitText[index3].equals("DI")){
                    sBytes += "8A 13";
                }
            }
        } else
            sBytes += "Error -----------------";
        return sBytes;
    }
}
