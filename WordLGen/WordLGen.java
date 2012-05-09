/*
 * Made by Dominik Schlecht
 * dominik.schlecht@hotmail.de
 * 
 * WordLGen.java
 * v. 1.0
 * 2012.05.06
 */
public class WordLGen {
    
    public static void main(String[] args) throws Exception{
        WordLGenWorker worker = new WordLGenWorker();
        String input = "";
        if (args.length != 0){
            switch (args[0]){
            case "-h":
                worker.printHelp();
                break;
                
            case "-a":
                if (args.length == 2){
                    input = args[1];
                    worker.combine(input);
                } else {
                    worker.printHelp();
                }
                break;
                
            case "-d":
                worker.setConsoleOutput(true);
                if (args.length == 3){
                    input = args[2];
                    worker.combine(input);
                    //System.out.println(args[1]);
                } else if (args.length > 3){
                    worker.printHelp();
                }
                
                break;
                
            default:
                worker.printHelp();
                break;
            }
        } else {
            worker.combine(input);
        }
    }
}
