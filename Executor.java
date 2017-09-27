import java.util.Scanner;

/**
 *  A class that takes a Brainfuck Code snippet without loops and executes given commands on a memory reference
 */
public class Executor{
    String code;
    Memory mem;
    int loopB[];
    int loopE[];

    /**
     * Constructor of a code executor
     * @param code Brainfuck Code given to execute
     * @param mem Reference to a global memory object
     */
    public Executor(String code, Memory mem){
        setCode(code);
        setMemory(mem);
    }

    /**
     * sets the internal instruction String to a given Brainfuck code snipped (without loops)
     * @param code given Brainfuck code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * sets the Memory where operations will be executed
     * @param mem
     */
    public void setMemory(Memory mem) {
        this.mem = mem;
    }

    /**
     * executes the given instruction at the internal memory reference
     * @param c instruction
     */
    private void execute(char c){
        try {
            switch (c) {
                case '+':
                    mem.increase();
                    break;
                case '-':
                    mem.decrease();
                    break;
                case '<':
                    mem.left();
                    break;
                case '>':
                    mem.right();
                    break;
                case '.':
                    System.out.print((char) mem.getValue());
                    break;
                case ',':
                    Scanner s = new Scanner(System.in);
                    byte tmp;
                    tmp = (byte) s.nextLine().charAt(0);
                    mem.setValue(tmp);
            }
        } catch (IllegalStateException e){
            throw new IllegalStateException(e.getMessage() + ": char '" + c + "'");
        }
    }

    /**
     * starts the execution of the code
     * @return true when finished
     */
    public boolean start() {
        int iptr = 0;
        char c;
        determineLoops();
        try {
            for (iptr = 0; iptr < code.length(); iptr++) {
                c = code.charAt(iptr);
                execute(c);
                if(c == ']'){
                    if(mem.getValue()!=0){
                        for (int i = 0; i < loopB.length; i++){
                            if(loopE[i] == iptr){
                                iptr=loopB[i];
                            }
                        }
                    }
                }
            }
        } catch (IllegalStateException e){
            iptr++;
            throw new IllegalStateException(e.getMessage() + "(" + iptr + ")");
        }

        setCode("");
        return true;
    }

    private void determineLoops() {

        int l, r;
        r = l = 0;
        /*
        Determines if loop beginnings and endings are consistent
         */
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '[') {
                l++;

            } else if (code.charAt(i) == ']') {
                r++;
            }
        }

        if (r != l) {
            throw new IllegalStateException("Loops are inconsistent");
        }


        /*
        initializes and identifies loops
         */
        loopB = new int[l];
        loopE = new int[r];

        int b, e;
        b = 0;
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '[') {
                loopB[b] = i;
                int counter = 0;
                for (int j = i + 1; j < code.length(); j++) {
                    if (code.charAt(j) == '[') {
                        counter++;
                    } else if (code.charAt(j) == ']') {
                        if (counter == 0) {
                            loopE[b] = j;
                            break;
                        }
                        counter--;
                    }
                }
                b++;
            }
        }

    }
}
