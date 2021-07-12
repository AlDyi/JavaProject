package library;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class LibraryCards {
    private final Storage filePersons = new Storage("Persons");
    private final Storage fileRent = new Storage("RentOfBook");
    private final Storage fileBooks = new Storage("FileBooks");
    DateFormat df = new SimpleDateFormat("dd/MM/yyy");
    public static final int COPIES = 2;
    public static final int ISSUE = 3;
    public static final int BEGIN_DATE_RENT = 2;
    public static final int END_DATE_RENT = 3;

    void addPerson(Person human) throws IOException {
        filePersons.writeToStorage(human.getIdPerson() + ", " + human.getFio() + ", " + df.format(human.getDateBegin().getTime()) + ", " + df.format(human.getDateEnd().getTime()) + '\n', true);
        System.out.println("Данные добавлены");
    }

    void takeBook(Person human, Book book) throws IOException {
        if (fileBooks.foundLine(book.getId())) { //Проверяем, доступна ли книга с данным id
            System.out.println("Книги с таким ID нет в базе. Проверьте корректность введенных данных.");
            return;
        }
        Scanner scanner = new Scanner(fileBooks.getFile());
        String[] splitLineBook = new String[0];
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(Integer.toString(book.getId()))) {
                splitLineBook = line.split(", ");
                if (splitLineBook[COPIES].equals(splitLineBook[ISSUE])) {
                    System.out.println("Данной книги пока нет в наличии");
                    return;
                }
            }
        }
        scanner.close();

        if (!filePersons.foundLine(human.getIdPerson())) {
            System.out.println("Пользователя с таким ID нет в системе."); //Проверяем, есть ли пользователь с таким id
            return;
        }

        Books myBooks = new Books(); //перенести в books //че?!
        book.setNumberOfIssue(book.getNumberOfIssue() + 1);
        myBooks.changeBook(book);

        GregorianCalendar pickUpDate = new GregorianCalendar();
        GregorianCalendar returnDate = new GregorianCalendar();
        returnDate.add(Calendar.MONTH, 1);
        fileRent.writeToStorage(human.getIdPerson() + ", " + book.getId() + ", " + df.format(pickUpDate.getTime()) + ", " + df.format(returnDate.getTime()) + '\n', true);
        System.out.println("Книга отдана");
    }

    void returnBook(Person human, Book book) throws IOException {
        Scanner scanner = new Scanner(fileRent.getFile()); //Проверяем, есть ли записи с данными id
        int k = 0;
        StringBuilder writeLine = new StringBuilder();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(Integer.toString(book.getId())) && line.contains((Integer.toString(human.getIdPerson())))) {
                k++;
            } else {
                writeLine.append(line).append('\n');
            }
        }
        if (k == 0) {
            System.out.println("Нет записей");
            return;
        }
        scanner.close();

        Books myBooks = new Books();
        book.setNumberOfIssue(book.getNumberOfIssue() - 1);
        myBooks.changeBook(book);

        fileRent.writeToStorage(writeLine.toString(), false);
        System.out.println("Книга возвращена");
    }

    void renewBook(Person human, Book book, int month) throws IOException, ParseException { //Продление книги
        Scanner scanner = new Scanner(fileRent.getFile());
        int k = 0;
        String[] splitLine = new String[0];
        StringBuilder writeLine = new StringBuilder();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(Integer.toString(book.getId())) && line.contains((Integer.toString(human.getIdPerson())))) {
                k++;
                splitLine = line.split(", ");
            } else {
                writeLine.append(line).append('\n');
            }
        }
        if (k == 0) {
            System.out.println("Нет записей");
            return;
        }
        scanner.close();

        Date date = df.parse(splitLine[END_DATE_RENT]); //Продлеваем дату сдачи на указанное количество месяцев
        Calendar endRentDate = Calendar.getInstance();
        endRentDate.setTime(date);
        endRentDate.add(Calendar.MONTH, month);

        fileRent.writeToStorage(writeLine.toString() + human.getIdPerson() + ", " + book.getId() + ", " + splitLine[BEGIN_DATE_RENT] + ", " + df.format(endRentDate.getTime()) + '\n', false);
        System.out.println("Книга продлена");
    }
}
