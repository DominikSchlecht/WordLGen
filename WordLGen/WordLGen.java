/*
 * Made by Dominik Schlecht
 * dominik.schlecht@hotmail.de
 * 
 * WordLGen.java
 * v. 1.11
 * 2012.05.10
 */
public class WordLGen {
    
    public static void main(String[] args) throws Exception{
        String input = "",
                outFilename = "",
                inFilename = "";
        boolean inputSourceSet = false;
        WordLGenWorker worker = new WordLGenWorker();
        
        for (int i = 0; i < args.length; i++){
            switch (args[i]){
            
            case "-a":
                i = i+1; //TODO Fehler absichern
                if (!inputSourceSet){
                    input = args[i];
                } else {
                    System.out.println("Input specified two times,\n" +
                            "programm exits.\n");
                    worker.printHelp();
                    return;
                }
                inputSourceSet = true;
                break;
                
            case "-d":
                worker.setConsoleOutput(true);
                break;
                
            case "-f":
                i = i+1;
                outFilename = args[i];
                break;
                
            case "-r":
                i = i+1; //TODO Fehler absichern
                if (!inputSourceSet){
                    worker.setInputFile(true);
                    inFilename = args[i];
                } else {
                    System.out.println("Input specified two times,\n" +
                    		"programm exits.\n");
                    worker.printHelp();
                    return;
                }
                inputSourceSet = true;
                break;
                
            case "-h":
            default:
                worker.printHelp();
                return;
            }
        }
        worker.combine(input, outFilename, inFilename);
    }
}
