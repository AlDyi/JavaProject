package library;

import java.io.*;
import java.util.*;

public class Books {
    File file = new File("FileBooks");

    void addBook(Book book) throws IOException { //Добавление книги
        FileWriter fileWriter = new FileWriter(file, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write( book.id + ", " + book.name + ", " + book.author + ", " + book.numberOfCopies + ", " + book.numberOfIssue + '\n');
        bufferedWriter.close();
        System.out.println("Данные добавлены");
    }

    void deleteBook(Integer id) throws IOException { //Удаление книги
        Scanner scanner = new Scanner(file);
        Books length = new Books();
        if (length.countFile(id) == -1) {
            System.out.println("Данная книга не найдена");
            return;
        }
        String[][] buffer = new String[length.countFile(id)][5];
        int j = 0;
        while (scanner.hasNextLine() && j < buffer.length) {
            String line = scanner.nextLine();
            if (line.contains(Integer.toString(id))) {
                System.out.println(line);
                continue;
            }
            else {
                String[] splitLine = line.split(", ");
                for (int k = 0; k < buffer[j].length; k++) {
                    buffer[j][k] = splitLine[k];
                }
                j++;
            }

        }
        FileWriter fileReWriter = new FileWriter(file, false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileReWriter);
        StringBuilder lineInFile = new StringBuilder();
        for (String[] strings : buffer) {
            for (int l = 0; l < strings.length; l++) {
                if (l == strings.length - 1) {
                    lineInFile.append(strings[l]).append('\n');
                } else {
                    lineInFile.append(strings[l]).append(", ");
                }
            }
        }
        bufferedWriter.write(lineInFile.toString());
        bufferedWriter.close();
        scanner.close();
        System.out.println("Данные удалены");
    }

    int countFile(int id) throws FileNotFoundException { //Считает количество строк в файле для создания массива
        Scanner scanner = new Scanner(file);
        int i = 0;
        int j = -1;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(Integer.toString(id))) {
                j++;
            }
            else
            {
                i++;
            }
        }
        scanner.close();
        return (j == -1 ? j : i);
    }

    void findBookAuthorOrName(String authorOrName) throws FileNotFoundException { //Найти книгу по автору или названию
        Scanner scanner = new Scanner(file);
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String lowerLine = line.toLowerCase();
            if (lowerLine.contains(authorOrName.toLowerCase())) {
                System.out.println(line);
                i++;
            }
        }
        if (i == 0) {
            System.out.println("Данные не найдены!");
        }
        scanner.close();
    }

    void changeBookAuthor (Integer id, String author) throws IOException { //Изменить автора книги по id
        Scanner scanner = new Scanner(file);
        String name;
        int numberOfCopies;
        int numberOfIssue;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(Integer.toString(id))) {
                String[] splitLine = line.split(", ");
                name = splitLine[0];
                numberOfCopies = Integer.parseInt(splitLine[2]);
                numberOfIssue = Integer.parseInt(splitLine[3]);
                deleteBook(id);
                Book newBook = new Book(id, name, author, numberOfCopies, numberOfIssue);
            } else {
                System.out.println("Книга не найдена");
            }
        }
    }

    void changeBookName (Integer id, String name) throws IOException { //Изменить название книги по id
        Scanner scanner = new Scanner(file);
        String author;
        int numberOfCopies;
        int numberOfIssue;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(Integer.toString(id))) {
                String[] splitLine = line.split(", ");
                author = splitLine[1];
                numberOfCopies = Integer.parseInt(splitLine[2]);
                numberOfIssue = Integer.parseInt(splitLine[3]);
                deleteBook(id);
                Book newBook = new Book(id, name, author, numberOfCopies, numberOfIssue);
            } else {
                System.out.println("Книга не найдена");
            }
        }
    }

    void changeBookCopies (int id, int numberOfCopies) throws IOException { //Изменить количество копий книги по id
        Scanner scanner = new Scanner(file);
        String name;
        String author;
        int numberOfIssue;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(Integer.toString(id))) {
                String[] splitLine = line.split(", ");
                name = splitLine[0];
                author = splitLine[1];
                numberOfIssue = Integer.parseInt(splitLine[3]);
                deleteBook(id);
                Book newBook = new Book(id, name, author, numberOfCopies, numberOfIssue);
                System.out.println("Данные изменены");
            } else {
                System.out.println("Книга не найдена");
            }
        }
    }
}
