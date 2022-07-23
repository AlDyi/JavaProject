import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.String;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;


public class Main {
    public static Program p = new Program();
    public static void main(String[] args) throws IOException {

//        SinglyLinkedList list = new SinglyLinkedList();
//        list.addFirst(5);
//        list.addFirst(4);
//        list.addFirst(3);
//        list.addFirst(2);
//        list.addFirst(1);
//        list.addFirst(0);
//
//        System.out.println(list.get(4));
//        list.remove(5);
//        list.printLinkedList();

//        DoublyLinkedList dList = new DoublyLinkedList();
//        dList.addFirst(3);
//        dList.addFirst(2);
//        dList.addFirst(1);
//        dList.addFirst(0);
//        dList.addLast(4);
//        dList.addLast(5);
//        dList.addLast(6);
//
//        dList.printLinkedList();
//        dList.remove(1);
//        System.out.println();
//        dList.printLinkedList();
//
//        Stack<Integer> stack = new Stack<>();

        SimpleStack stack = new SimpleStack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        System.out.println(Arrays.toString(stack.getStack()));
        System.out.println(stack.get(7));
    }

}
