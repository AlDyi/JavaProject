import java.lang.System;

public class SimpleStack {
    private Integer[] stack;
    private int size;

    public SimpleStack() {
        size = 1;
        stack = new Integer[size];
    }

    public void push(Integer value) {
        if (stack[0] == null) {
            stack[0] = value;
        } else {
            Integer[] newStack = new Integer[size+1];
            System.arraycopy(stack, 0, newStack, 0, size);
            stack = newStack;
            stack[size] = value;
            size++;
        }
    }

    public void pop() {
        if (size == 1) {
            stack[0] = null;
        } else {
            size--;
            Integer[] newStack = new Integer[size];
            System.arraycopy(stack, 0, newStack, 0, size);
            stack = newStack;
        }
    }

    public Integer get(int index) {
        if (index < 0 || index > size - 1) return null;
        else {
            return stack[index];
        }
    }
}
