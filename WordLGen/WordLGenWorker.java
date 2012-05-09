/*
 * Made by Dominik Schlecht
 * dominik.schlecht@hotmail.de
 * 
 * WordLGenWorker.java
 * v. 1.0
 * 2012.05.08
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class WordLGenWorker {
    
    //Attributes
    private String[] parts;
    private List<String> erg;
    private boolean consoleOutput = false;
    FileWriter fstream;

    
    
    //Constructor
    
    //Methods    
    public void combine(String inputParam) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out;
        File f;
        String input;
        int anzW = 0;
        long anzC = 0;
        
        
        //Get the input
        if (inputParam.isEmpty()){
            System.out.println("Enter the parts to be combined, seperated by ' ' (space)");
            input = br.readLine();
            br.close();
        } else {
            input = inputParam;
        }
        
        //divide in parts
        for (int i = 0; i < input.length(); i++){
            //System.out.println(input.charAt(i));
            if (input.charAt(i) == ' '){
                anzW++;
                //System.out.println("one '|' found" + anz);
            } else {
                //System.out.println("no '|' found");
            }
        }
        anzW++;
        
        //print the new infos
        System.out.println("You entered '" + input +
                "' for input, found " + anzW + " parts.");
        if (anzW <= 1){
            System.out.println("Only one part found, \nprogramm exits");
            return;
        }
        
        //Work with the input
        
        //Fields
        parts = new String [anzW];
        
        //Insert the single parts in the parts[]
        int last = 0, next = 0, plInParts = 0;
        while (next < input.length()){
            if (input.charAt(next) == ' '){
                parts[plInParts] = input.substring(last, next);
                plInParts++;
                last = next+1;
            }
            next++;
        }
        parts[plInParts] = input.substring(last);
        
        //Fakultaet
        int tmp1, tmp2 = 1;
        for (int j = 1; j <= anzW; j++){
            tmp1 = tmp2;
            //fakultät in the heigth of j
            for (int i = 0; i < j; i++){
                tmp1 = tmp1 * anzW;
            }
            anzC = anzC + tmp1;
        }
        
        System.out.println("Number of Combinations: " + anzC);
        
        //initialize obj for real work
        erg = new LinkedList<String>();
        f = new File("out.txt");
        if(!f.exists()){
            f.createNewFile();
            System.out.println("File created in " + f.getAbsolutePath());
        } else {
            System.out.println("File already exists, the existing file will " +
            		"be overwritten.");
        }
        fstream = new FileWriter("out.txt");
        out = new BufferedWriter(fstream);
        
        
        //for-loop to get also lenghtes < parts.length
        for (int i = 0; i < parts.length;i++){
            this.combineAndWrite("", 0, i, out);
        }
        out.close();
        System.out.println("Everything went well, good luck out there");
    }
    
    /*
     * recusive worker class
     */
    public void combineAndWrite(String tmp, int curPos, int desLen, 
            BufferedWriter out){
        
        if (curPos == desLen){
            for (int i = 0; i < parts.length; i++){
                this.erg.add(tmp + this.parts[i]);
                if (consoleOutput){
                    System.out.println(tmp + this.parts[i]);
                }
                try {
                    out.write(tmp + this.parts[i] + " ");
                } catch (IOException e) {
                    System.err.println("Error (writing to file): "
                            + e.getMessage());
                }
            }
        } else {
            String ntmp;
            ntmp = tmp;
            for (int i = 0; i < parts.length; i++){
                ntmp = tmp + this.parts[i]; 
                this.combineAndWrite(ntmp, curPos+1, desLen, out);
            }
        }
    }
    
    public void printHelp(){
        System.out.print("Help for 'WordLGen', the easy to use Word List Generator\n\n" +
        		"Usage:\n" +
        		"'java WordLGen [-d] [-a \"part1 {part2}]'\n\n" +
        		"Params:\n" +
        		"'-h' display this help\n" +
        		"'-a' use argumented mode, meaning you write the parts to combine after this param, divided by ' '\n" +
        		"'-d' displays all combined words in console, can  be combined with '-a'\n" +
        		"\n" +
        		"Examples:\n" +
        		"Working: \n" +
        		"'java WordLGen' starts the programm and allowes you to ender the part afterwards\n" +
        		"'java WordLGen -a \"one two three\"' will combine the 3 parts\n" +
        		"\n" +
        		"Less usefull:" +
        		"'java WordLGen -h' will display the help\n" +
        		"'java WordLGen -a \"one two three\" uselessParam' will display the help\n" +
        		"'java WordLGen -a -b -d -c' will display the help (which you seem to need)\n" +
        		"'java WordLGen \"one two three\"' will display the help"); //TODO
    }
    
    public String setConsoleOutput(boolean value){
        consoleOutput = value;
        return "Conssole Output " + consoleOutput;
    }

}
