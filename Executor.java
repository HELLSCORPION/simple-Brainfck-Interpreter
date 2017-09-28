import java.util.Scanner;

/**
 *  A class that takes a Brainfuck Code snippet without loops and executes given commands on a memory reference
 */
public class Executor {
    private String code;
    private Memory mem;
    private int loopStartIndex[];
    private int loopEndIndex[];

    private Scanner s;

    /**
     * Constructor of a code executor
     *
     * @param code Brainfuck Code given to execute
     * @param mem  Reference to a global memory object
     */
    public Executor(String code, Memory mem) {
        setCode(code);
        setMemory(mem);
        loopStartIndex = new int[countLoops()];
        loopEndIndex = new int[loopStartIndex.length];
        s = new Scanner(System.in);
    }

    public Executor(String code){
        this(code, new Memory());
    }

    public Executor(Memory mem){
        this("", mem);
    }

    public Executor(){
        this("");
    }

    /**
     * sets the internal instruction String to a given Brainfuck code snipped (without loops)
     *
     * @param code given Brainfuck code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * sets the Memory where operations will be executed
     *
     * @param mem
     */
    public void setMemory(Memory mem) {
        this.mem = mem;
    }

    /**
     * executes the given instruction at the internal memory reference
     *
     * @param c instruction
     * @throws IllegalStateException when there is a problem with the sourceCode
     *
     */
    private void execute(char c) {
        try {
            switch (c) {
                case '+':
                    mem.increment();
                    break;
                case '-':
                    mem.decrement();
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
                    byte tmp;
                    tmp = (byte) s.nextLine().charAt(0);
                    mem.setValue(tmp);
            }
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage() + ": char '" + c + "'");
        }
    }

    /**
     * starts and finishes the execution of the code
     *
     * @return true when finished
     * @throws IllegalStateException when there is an error in the SourceCode
     */
    public void start() {
        int iptr = 0;
        char c;
        determineLoops();
        try {
            for (; iptr < code.length(); iptr++) {
                c = code.charAt(iptr);
                execute(c);
                if (c == ']') {
                    if (mem.getValue() != 0) {
                        for (int i = 0; i < loopStartIndex.length; i++) {
                            if (loopEndIndex[i] == iptr) {
                                iptr = loopStartIndex[i];
                            }
                        }
                    }
                }
            }
        } catch (IllegalStateException e) {
            iptr++;
            throw new IllegalStateException(e.getMessage() + "(" + iptr + ")");
        }

        setCode("");
    }

    private void determineLoops() {
        int b;
        b = 0;


        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '[') {
                loopStartIndex[b] = i;
                int counter = 0;
                for (int j = i + 1; j < code.length(); j++) {
                    if (code.charAt(j) == '[') {
                        counter++;
                    } else if (code.charAt(j) == ']') {
                        if (counter == 0) {
                            loopEndIndex[b] = j;
                            break;
                        }
                        counter--;
                    }
                }
                b++;
            }
        }

    }

    /**
     * counts how many loops are in the saved sourcecode
     * @return loopcount
     * @throws IllegalStateException when there are half open loops
     */

    private int countLoops() {
        int l, r;
        r = 0;
        l = 0;
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

        return l;

    }
}
