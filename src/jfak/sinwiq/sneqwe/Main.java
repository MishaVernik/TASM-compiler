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
import sun.plugin.javascript.navig.Array;

import java.awt.*;
import java.io.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.sun.org.apache.xalan.internal.lib.ExsltStrings.split;
import static jfak.sinwiq.sneqwe.Additional.*;
import static jfak.sinwiq.sneqwe.Constants.*;


public class Main {

    protected static void printFile(@NotNull ArrayList<String[]> data){

        for (int i = 0; i < data.size(); i++){

            for (String word : data.get(i)){
                System.out.print(word + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {

        String[] test1 = superSplitter("MOV AX,ES:ES:[BX+SI]");
        ArrayList<String> macroWithoutParams = new ArrayList<String>();
        ArrayList<String> macroWithParams = new ArrayList<String>();;

        Boolean ifMacroWithoutParams = false;
        Boolean ifMacroWithParams = false;
        String[] convertStringWithoutSpaces;
        ArrayList<String[]> fileData = readFromFile();
        String fileData1 = "CDATA HsELLO";
        System.out.println(Pattern.compile("\\s*[A-Z][A-Z0-9]{0,5}\\s+SEGMENT\\s*").matcher(fileData1).matches());
        // printFile(fileData);

        Pattern p = Pattern.compile("[A-Z][A-Z]{0,4}?\\s*SEGMENT");

        System.out.println(fileData.get(0)[0].toString() + " HELLO");
        System.out.println(p.matcher(fileData.get(0).toString()).matches());
        System.out.println("----------------------------------------------------------");
        for (int i = 0; i < fileData.size(); i++){

            String s = "";
            // System.out.println(fileData.get(i));
            for (String word : fileData.get(i)){
                    word.replaceAll("\\s+", "");
                    if (word.length() > 0)
                        s +=word + " ";
            }

            System.out.println("\n"+s);
            System.out.println("------------------------------");
            convertStringWithoutSpaces = nextStr(s);

            s = s.replaceAll("\uFEFF", "");
            CHECK_IF_ERROR = true;
            Additional.checkLexeme(s, 1);
            if (Pattern.compile("\\s*END\\s*").matcher(s).matches()){
                CHECK_IF_ERROR = false;
                printTable();
                int counter = 0;
                for (String el : FIRST_PASS){
                    if (el.contains("____")){
                        String[] arrOFElements = el.split("\\s+");
                        String getOffset = getOffsetForLABELS(arrOFElements, arrOFElements.length - 1);

                        int num = Integer.parseInt(getOffset, 16);
                        int num1 = Integer.parseInt(arrOFElements[1], 16);

                        el = el.replace("____",String.format("%1$02X",num-num1 - 2));
                        System.out.println(el);
                        FIRST_PASS.set(counter, el);
                    }
                    counter++;
                }
                System.out.println("\t0\t" + convertStringWithoutSpaces[0] + "\t" + convertStringWithoutSpaces[0].length() + "\tend");
                addInFirstPass(SHIFT, s, "");
            }

            if (ifMacroWithoutParams == true){

                macroWithoutParams.add(s);
            }

            if (ifMacroWithParams == true){

                macroWithParams.add(s);
            }

            if (CHECK_IF_ERROR == true && !s.isEmpty()){
                FIRST_PASS.add("------------ERROR-----------");
            }


        }
        writeToFile("output.txt");
    }

    public static void writeToFile(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        for(String str: FIRST_PASS) {
            writer.write(str + "\n");
        }
        writer.close();
    }

    public static void printStuff(int filedName, int fieldMnem, int sizeMnem, @NotNull ArrayList<Integer> operands, ArrayList<Integer> sizeOperands){

        System.out.println("Поле меток/имени: " + filedName);
        System.out.println("Поле мнемокода: " + fieldMnem);
        System.out.println("Размер мнемокода: " + sizeMnem);
        for (int i = 0; i < operands.size(); i++){
            System.out.println("Операнд №" + i + " : " + operands.get(i));
            System.out.println("Размер операнда №1: ");
        }

        System.out.println("Размер операнда №1: ");
        System.out.println("Операнд №2: ");
        System.out.println("Рамзер операнда №2: ");
    }

    @Contract(pure = true)
    public static String[] nextStr(@NotNull String str){

        String[] res =  str.split("\\s+\\[?\\]?");
        return res;
    }
    public static ArrayList<String[]> readFromFile(){

        String fileName = "test.txt";
        ArrayList<String[]> mylist = new ArrayList<String[]>();
        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            int i = 0;
            while((line = bufferedReader.readLine()) != null) {
                String[] dividerLine = line.split("\\s+");
                // Need to check a case:
                // 'Hello    world'
                // will be divided such as : ['Hello world']
                // TODO: check the number of missed spaces
                dividerLine = getStrings(dividerLine);

                if (dividerLine.length > 0)
                    mylist.add(dividerLine);
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }

        return mylist;
    }

    @NotNull
    public static String[] getStrings(@NotNull String[] dividerLine) {
        int j = 3;
        int len =  dividerLine.length;
        if (len > 2 && dividerLine[2].contains("'"))
        {
            List<String> list = new ArrayList<String>(Arrays.asList(dividerLine));
            while (j < list.size() ){
                list.set(2, list.get(2) + " " + list.get(j++));
                list.remove(--j);
            }
            dividerLine = list.toArray(new String[0]);
        }
        return dividerLine;
    }
}
