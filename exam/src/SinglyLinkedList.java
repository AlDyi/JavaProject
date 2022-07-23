public class SinglyLinkedList {

    private Node head;

    public SinglyLinkedList() {
        head = null;
    }

    public class Node {
        public Integer data;
        public Node next;

        public Node(Integer data) {
            this.data = data;
            next = null;
        }

        public void displayNodeData() {
            System.out.print(data +  " -> ");
        }
    }
    //    добавление элемента в начало
    public void addFirst(Integer data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
    }

    //    удаление элемента
    public void remove(int index) {
        Node currentNode = head;
        Node previousNode = null;
        int i = 0;

        while (currentNode != null) {
            if (i == index) {
                if (currentNode == head) {
                    head = currentNode.next;
                } else {
                    previousNode.next = currentNode.next;
                }
            }
            i++;
            previousNode = currentNode;
            currentNode = currentNode.next;
        }
    }

    //    вывод элемента по индексу
    public Integer get (int index) {
        Node currentNode = head;
        int i = 0;

        while (currentNode != null) {
            if (i == index) {
                return currentNode.data;
            } else {
                i++;
                currentNode = currentNode.next;
            }
        }
        return null;
    }

    public void printLinkedList() {
        Node current = head;
        while (current != null) {
            current.displayNodeData();
            current = current.next;
        }
        System.out.print("NULL");
    }

}
