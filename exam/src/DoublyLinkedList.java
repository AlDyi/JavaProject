public class DoublyLinkedList {

    private Node head;
    private Node last;

    public DoublyLinkedList() {
        head = null;
        last = null;
    }

    public class Node {
        public Integer data;
        public Node next;
        public Node prev;

        public Node(Integer data) {
            this.data = data;
            next = null;
            prev = null;
        }

        public void displayNodeData() {
            System.out.print(data +  " <-> ");
        }
    }

    public void addFirst(Integer data) {
        Node newNode = new Node(data);
        newNode.next = head;
        if (head != null) {
            head.prev = newNode;
        }
        if (last == null) {
            last = newNode;
        }
        head = newNode;
    }

    public void addLast(Integer data) {
        Node newNode = new Node(data);
        newNode.prev = last;
        if (head == null) {
            head = newNode;
        }
        if (last != null) {
            last.next = newNode;
        }
        last = newNode;
    }

    public void remove(int index) {
        Node currentNode = head;
        int i = 0;

        while (currentNode != null) {
            if (i == index) {
                if (currentNode == head) {
                    head = currentNode.next;
                    head.prev = null;
                } else if (currentNode == last) {
                    last = currentNode.prev;
                    last.next = null;
                } else {
                    currentNode.next.prev = currentNode.prev;
                    currentNode.prev.next = currentNode.next;
                }
            }
            i++;
            currentNode = currentNode.next;
        }
    }

    public void printLinkedList() {
        System.out.print("NULL <-> ");
        Node current = head;
        while (current != null) {
            current.displayNodeData();
            current = current.next;
        }
        System.out.print("NULL");
    }
}
