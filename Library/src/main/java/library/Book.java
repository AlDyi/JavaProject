package library;

public class Book {
    Integer id;
    String name;
    String author;
    Integer numberOfCopies;
    Integer numberOfIssue;

    Book (Integer id,String name, String author, Integer numberOfCopies) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.numberOfCopies = numberOfCopies;
        this.numberOfIssue = 0;
    }

    Book (Integer id,String name, String author, Integer numberOfCopies, Integer numberOfIssue) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.numberOfCopies = numberOfCopies;
        this.numberOfIssue = numberOfIssue;
    }
}
