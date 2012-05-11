/*
 * Made by Dominik Schlecht
 * dominik.schlecht@hotmail.de
 * 
 * WordLGenWorker.java
 * v. 1.12
 * 2012.05.11
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class WordLGenWorker {
    
    //Attributes
    private String[] parts;
    private boolean consoleOutput = false,
                    fileInput = false;
    FileWriter fstream;

    //No constructor needed
    
    //Methods
    /*
     * Main Method, intializing everything needed
     */
    public void combine(String iPWords, String iPFilename, String iPInFilename) throws Exception{
        //Init
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out;
        File fOutput;
        String input, 
                filename = iPFilename;
        int anzW = 0;
        long anzC = 0;
        
        //Get the input from file
        
        if (fileInput){
            Scanner scanner = null;
            String line = "";
            try {
                scanner = new Scanner(new File(iPInFilename));
                while (scanner.hasNextLine()){
                    line = line + scanner.nextLine();
                    System.out.println("line :" + line);
                }
            } catch (IOException e){
                System.out.println("The inputfile you specified can not be found,\n" +
                		"programm exits.\n");
                this.printHelp();
                return;
            } finally {
                try {
                    scanner.close();
                } catch (Exception e) {
                    //swallow
                }
            }
            input = line;
        } else {
            //Get the words
            if (iPWords.isEmpty()){
                System.out.println("Enter the parts to be combined, seperated by ' ' (space)");
                input = br.readLine();
            } else {
                input = iPWords;
            }
        }
        
        
        //Count words
        for (int i = 0; i < input.length(); i++){
            if (input.charAt(i) == ' '){
                //Found new part
                anzW++;
            } else {
                //No part found
            }
        }
        //Count plus 1 because normal humans count from 1, we from 0..
        anzW++;
        
        //Print the new infos
        System.out.println("You entered '" + input +
                "' for input, found " + anzW + " words.");
        if (anzW <= 1){
            //Not sure of "throw new Exception" might be a bit hard for error
            //throw new Exception("Only one word given, \n programm exits");
            System.out.println("Only one word given, programm exits");
            return;
        }
        
        //Work with the input
        
        //Initialize words
        parts = new String [anzW];
        
        //Insert the single parts in parts
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
        
        //Faktorial
        int tmp1, tmp2 = 1;
        for (int j = 1; j <= anzW; j++){
            tmp1 = tmp2;
            //faktorial in the heigth of j
            for (int i = 0; i < j; i++){
                tmp1 = tmp1 * anzW;
            }
            anzC = anzC + tmp1;
        }
        
        //Print the number of combinations
        System.out.println("Number of combinations: " + anzC);
        
        //Ask for file
        if (filename.isEmpty()){
            System.out.println("Desired filename: ");
            filename = br.readLine();
        }
        fOutput = new File(filename);
        if(fOutput.exists()){
            //File already exists, complain
            System.out.println("File already exists, do you want the file to " +
                    "be overwritten?\n(Allowed input: 'yes', 'y', 'no', 'n')" +
                    "");
            boolean ok = false;
            while (!ok){
                input = br.readLine(); //Old Input not required anymore (-> parts[])
                switch (input){
                case "yes":
                case "y":
                    //User wants to overwrite file
                    System.out.println("The existing file will be overwritten");
                    fOutput.createNewFile();
                    ok = true;
                    break;
                case "no":
                case "n":
                    //User doesn't want to overwrite it, exit 
                    //(TODO feature retype filename)
                    System.out.println("The file was not overwritten,\n"+
                    		"programm exits.");
                    return;
                default:
                    System.out.println("Allowed input: 'yes', 'y', 'no', 'n'");
                }
            }
        }
        fOutput.createNewFile();
        System.out.println("File created: " + fOutput.getAbsolutePath());
        fstream = new FileWriter(filename);
        out = new BufferedWriter(fstream);
        
        
        //for-loop to get also lenghtes < parts.length
        for (int i = 0; i < parts.length;i++){
            this.combineAndWrite("", 0, i, out);
        }
        out.close();
        br.close();
        System.out.println("Everything went well, \n" +
        		"thank you for using my programm.");
    }
    
    /*
     * recusive worker class
     */
    public void combineAndWrite(String tmp, int curPos, int desLen, 
            BufferedWriter out){
        
        //Recursive abort test
        if (curPos == desLen){
            //Last recursive element
            for (int i = 0; i < parts.length; i++){
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
            //Recursive element somewhere in the middle
            String ntmp;
            ntmp = tmp;
            for (int i = 0; i < parts.length; i++){
                ntmp = tmp + this.parts[i]; 
                this.combineAndWrite(ntmp, curPos+1, desLen, out);
            }
        }
    }
    
    public void printHelp(){
        System.out.print("Help for 'WordLGen', the easy to use Word List " +
        		"Generator\n\n" +
        		"Usage:\n" +
        		"'java WordLGen [-d] [-f <filename>] [(-r <filename>)|(-a \"part1 {part2})]'\n\n" +
        		"Params:\n" +
                "'-a' use argumented mode, meaning you write the words to\n" +
                "      combine after this param, divided by ' ' (space).\n" +
        		"'-d' displays all combined words in console, can be\n" +
        		"     combined with '-a'. All combinations are still written\n" +
        		"     to the file (out).\n" +
        		"'-f' the name of the file to write to.\n" +
                "'-h' display this help\n." +
                "'-r' read input from file, not from console." +
        		"\n" +
        		"Examples:\n" +
        		"Working: \n" +
        		"'java WordLGen' starts the programm and allowes you to ender " +
        		"the part afterwards.\n" +
        		"'java WordLGen -a \"one two three\"' will combine the 3 " +
        		"parts.\n" +
                "'java WordLGen -d -f file1 -a \"one two three\"' will combine "+
                "the words 'one' 'two' 'three'\n" +
                "     and write it to console and to file 'file1'.\n" +
        		"\n" +
        		"Less usefull:" +
        		"'java WordLGen -h' will display the help\n" +
        		"'java WordLGen -a \"one two three\" uselessParam' will " +
        		"display the help.\n" +
        		"'java WordLGen -a -b -d -c' will display the help (which you " +
        		"seem to need).\n" +
        		"'java WordLGen \"one two three\"' will display the help.\n\n" +
        		"Made by Dominik Schlecht, dominik.schlecht@hotmail.de");
    }
    
    public void setConsoleOutput(boolean value){
        consoleOutput = value;
    }
    
    public void setInputFile(boolean value){
        this.fileInput = value;
    }

}
