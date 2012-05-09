/*
 * Made by Dominik Schlecht
 * dominik.schlecht@hotmail.de
 * 
 * WordLGen.java
 * v. 1.1
 * 2012.05.09
 */
public class WordLGen {
    
    public static void main(String[] args) throws Exception{
        String input = "";
        String filename = "";
        WordLGenWorker worker = new WordLGenWorker();
        
        for (int i = 0; i < args.length; i++){
            switch (args[i]){
            case "-a":
                i = i+1;
                input = args[i];
                break;
            case "-d":
                worker.setConsoleOutput(true);
                break;
            case "-f":
                i = i+1;
                filename = args[i];
                break;
            case "-h":
            default:
                worker.printHelp();
                return;
            }
        }
        worker.combine(input, filename);
    }
}
