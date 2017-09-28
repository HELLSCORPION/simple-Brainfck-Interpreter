/**
 * A class that controlls the workflow of a brainfuck file
 */
public class Main{

    private static Executor exec;


    /**
     * main method of the Brainfuck interpreter controller class
     * @param args
     */
    public static void main(String args[]){
        String code = "";

        for(int i = 0; i < args.length; i++){
            code += args[i];
        }
        start(code);
    }

    /**
     * start method of the controller class
     */
    public static void start(String code){
        exec = new Executor(code);
        exec.start();
    }
}
