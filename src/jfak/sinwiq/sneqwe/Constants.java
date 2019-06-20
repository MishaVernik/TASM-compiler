package jfak.sinwiq.sneqwe;

import java.util.ArrayList;

public class Constants {
   public static final String[] RESERVED_NAMES = {"SEGMENT", "DD", "DW", "DB", "ENDS", "MACRO", "ENDM", "MOV",
            "AX", "DS", "AAS", "INC", "DEC", "AND", "WORD", "PTR", "JL", "ENDS", "END"};

   public static ArrayList<String> USER_ID_TABLE = new ArrayList<String>();
   public static ArrayList<String[]> SEGMNET_TABLE = new ArrayList<String[]>();
   public static ArrayList<String[]> VARIABLE_TABLE = new ArrayList<String[]>();
   public static ArrayList<String[]> MACRO_TABLE = new ArrayList<String[]>();
   public static ArrayList<String[]> LABEL_TABLE = new ArrayList<String[]>();
   public static ArrayList<String> RESERVED_ID_TABLE = new ArrayList<String>();
   public static ArrayList<String> FIRST_PASS = new ArrayList<String>();

   public static boolean CHECK_IF_ERROR = false;
   public static String GLOBAL_OFFSET = "";
   public static int  CURRENT_SEGMENT = 0;
   public static int  SHIFT = 0;
   public static Boolean LOCK_COUNT_IF_MACRO = false;
   public static int JUMP_NUMBER = 255;
   public static String SPECIAL_NUMBER_FOR_MACRO = "";
}
