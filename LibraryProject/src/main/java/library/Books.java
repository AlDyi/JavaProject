package library;

import java.io.*;


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
        String[][] buffer = fileBooks.scannerFileReturnBuffer(id);
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
        String findLine = fileBooks.scannerFileReturnLine(authorOrName);
        System.out.println(findLine);
        if (findLine == null) {
            return "Данные не найдены!";
        } else {
            return findLine;
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
