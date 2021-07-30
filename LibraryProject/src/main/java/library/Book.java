package library;

public class Book {
    private Integer id;
    private String name;
    private String author;
    private Integer numberOfCopies;
    private Integer numberOfIssue;

    Book () {
        this.numberOfIssue = 0;
    }

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

    public Integer getId() {
        return id;
    }

    public Integer getNumberOfCopies() {
        return numberOfCopies;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getNumberOfIssue() {
        return numberOfIssue;
    }

    public String getName() {
        return name;
    }

    public void setNumberOfIssue(Integer numberOfIssue) {
        this.numberOfIssue = numberOfIssue;
    }
}
