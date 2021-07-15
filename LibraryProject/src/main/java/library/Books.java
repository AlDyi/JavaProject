package library;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.*;


public class Books {
    private Storage fileBooks = new Storage("FileBooks");

    String addBook(Book book) throws IOException { //Добавление книги заменить
        fileBooks.writeToStorage(book.getId() + ", " + book.getName() + ", " + book.getAuthor() + ", " + book.getNumberOfCopies() + ", " + book.getNumberOfIssue() + '\n', true);
        return "Данные добавлены";
    }

    String deleteBook(Integer id) throws IOException { //Удаление книги
        if (!fileBooks.foundLine(id)) {
            return "Данная книга не найдена";
        }
        Scanner scanner = new Scanner(fileBooks.getFile());
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
        return "Данные удалены";
    }


    String findBookAuthorOrName(String authorOrName) throws FileNotFoundException { //Найти книгу по автору или названию
        Scanner scanner = new Scanner(fileBooks.getFile());
        int i = 0;
        StringBuilder findLine = new StringBuilder();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String lowerLine = line.toLowerCase();
            if (lowerLine.contains(authorOrName.toLowerCase())) {
                findLine.append(line).append('\n');
                i++;
            }
        }
        //System.out.println(findLine.toString());
        scanner.close();
        if (i == 0) {
            return "Данные не найдены!";
        } else {
            return findLine.toString();
        }
    }

    String changeBook (Book book) throws IOException { //Изменить автора книги по id
        if (fileBooks.foundLine(book.getId())) {
            deleteBook(book.getId());
            addBook(book);
            return "Книга изменена";
        } else {
            return "Книга не найдена";
        }
    }
}
