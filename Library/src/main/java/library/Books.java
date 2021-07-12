package library;

import java.io.*;
import java.util.*;


public class Books {
    private Storage fileBooks = new Storage("FileBooks");

    void addBook(Book book) throws IOException { //Добавление книги заменить
        fileBooks.writeToStorage(book.getId() + ", " + book.getName() + ", " + book.getAuthor() + ", " + book.getNumberOfCopies() + ", " + book.getNumberOfIssue() + '\n', true);
        System.out.println("Данные добавлены");
    }

    void deleteBook(Integer id) throws IOException { //Удаление книги
        Scanner scanner = new Scanner(fileBooks.getFile());
        if (!fileBooks.foundLine(id)) {
            System.out.println("Данная книга не найдена");
            return;
        }
        String[][] buffer = new String[fileBooks.countLines() - 1][5];
        int j = 0;
        while (scanner.hasNextLine() && j < buffer.length) {
            String line = scanner.nextLine();
            if (!line.contains(Integer.toString(id))) {
                String[] splitLine = line.split(", ");
                System.arraycopy(splitLine, 0, buffer[j], 0, buffer[j].length);
                j++;
            }
        }
        scanner.close();

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
        fileBooks.writeToStorage(lineInFile.toString(), false);
        System.out.println("Данные удалены");
    }


    void findBookAuthorOrName(String authorOrName) throws FileNotFoundException { //Найти книгу по автору или названию
        Scanner scanner = new Scanner(fileBooks.getFile());
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

    void changeBook (Book book) throws IOException { //Изменить автора книги по id
        if (fileBooks.foundLine(book.getId())) {
            deleteBook(book.getId());
            addBook(book);
        } else {
            System.out.println("Книга не найдена");
        }
    }
}
