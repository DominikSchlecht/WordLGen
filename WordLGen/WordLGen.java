/*
 * Made by Dominik Schlecht
 * dominik.schlecht@hotmail.de
 * 
 * WordLGen.java
 * v. 1.2
 * 2012.05.23
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
            
            //Case a for argumented mode TODO prevent -f -r
            case "-a":
                //If to prevent bufferoverflow error
                try {
                    i = i+1;
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
                } catch (Exception e){
                    System.out.println("Wrong use of parameter '-a'\n");
                    return;
                }
            
            //Case d for console output
            case "-d":
                worker.setConsoleOutput(true);
                break;
            
            //Case f for file output
            case "-f":
                try {
                    i = i+1;
                    outFilename = args[i];
                    break;
                } catch (Exception e){
                    System.out.println("Wrong use of parameter '-f'\n");
                    return;
                }
                
            //Case r to read the words from file
            case "-r":
                //If to prevent bufferoverflow
                try {
                    i = i+1;
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
                } catch (Exception e){
                    System.out.println("Wrong use of parameter '-r'\n");
                    return;
                }
            
            //Case cC to combine cases
            case "-cc":
            case "-cC":
                worker.setCaseCombine(true);
                break;
               
            //Case h to print help
            case "-h":
            default:
                worker.printHelp();
                return;
            }
        }
        worker.combine(input, outFilename, inFilename);
    }
}
