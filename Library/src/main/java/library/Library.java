package library;

public class Library {
    public static void main(String[] args) {
        Books netbook = new Books();

        netbook.AddBook("Евгений Онегин", "А.С. Пушкин", 3, 1);
        netbook.PrintBook();
        netbook.FindBookName("Евгений Онегин");
        netbook.DeleteBook();
    }
}
