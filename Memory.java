import java.util.LinkedList;
import java.util.List;

/**
 * A Class that represents a memory unit working according to the given rules of Brainfuck
 */
public class Memory {
    List<Byte> mem;
    int ptr;

    /**
     * constructor of the memory unit
     */
    public Memory(){
        mem = new LinkedList<>();
        ptr = 0;
        fitSize();
    }

    /**
     * Sets the memory size dynamically
     */
    private void fitSize(){
        while(ptr+1 > mem.size()){
            mem.add((byte)0);
        }
    }

    /**
     * checks if the value inside a memory byte is valid
     * @param v value to check
     */
    private void checkValue(byte v){
        if(v<0){
            throw new IllegalStateException("Memory value cannot be negative");
        }
    }

    /**
     * moves the memory pointer one index to the right
     */
    public void right(){
        ptr++;
        fitSize();
    }

    /**
     * moves the memory pointer one index to the left
     */
    public void left(){
        if(ptr-1<0){
            throw new IllegalStateException("Memory out of bounds");
        }
        ptr--;
    }

    /**
     * increments the memory byte at the memory pointer's position
     */
    public void increase(){
        byte tmp;
        tmp = mem.get(ptr);
        tmp++;
        checkValue(tmp);
        mem.set(ptr, tmp);
    }

    /**
     * decrements the memory byte at the memory pointer's position
     */
    public void decrease(){
        byte tmp;
        tmp = mem.get(ptr);
        tmp--;
        checkValue(tmp);
        mem.set(ptr, tmp);
    }

    /**
     * sets the value of the memory byte at the memory pointer's position to a given value
     * @param v given value
     */
    public void setValue(byte v){
        checkValue(v);
        mem.set(ptr, v);
    }

    /**
     * returns the value of the memory byte at the memory pointer's position
     * @return current value
     */
    public byte getValue(){
        return mem.get(ptr);
    }

}
