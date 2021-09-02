package library;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class LibraryCards {
    private final Storage filePersons = new Storage("Persons");
    private final Storage fileRent = new Storage("RentOfBook");
    private final Storage fileBooks = new Storage("FileBooks");
    DateFormat df = new SimpleDateFormat("dd/MM/yyy");
    public static final int COPIES = 2;
    public static final int ISSUE = 3;
    public static final int BEGIN_DATE_RENT = 2;
    public static final int END_DATE_RENT = 3;

    String addPerson(Person human) throws IOException {
        filePersons.writeToStorage(human.getIdPerson() + ", " + human.getFio() + ", " + df.format(human.getDateBegin().getTime()) + ", " + df.format(human.getDateEnd().getTime()) + '\n', true);
        return "Данные добавлены";
    }

    String takeBook(Person human, Book book) throws IOException {
        if (!fileBooks.foundLine(book.getId())) { //Проверяем, доступна ли книга с данным id
            return "Книги с таким ID нет в базе. Проверьте корректность введенных данных.";
        }
        String[] splitLineBook = fileBooks.scannerFileReturnArray(book);
        if (splitLineBook[COPIES].equals(splitLineBook[ISSUE])) { //Есть ли экзампляры книги в наличие
            return "Данной книги пока нет в наличии";
        }

        if (!filePersons.foundLine(human.getIdPerson())) {
            return "Пользователя с таким ID нет в системе."; //Проверяем, есть ли пользователь с таким id
        }

        Books myBooks = new Books(); //перенести в books //че?!
        book.setNumberOfIssue(book.getNumberOfIssue() + 1);
        myBooks.changeBook(book);

        GregorianCalendar pickUpDate = new GregorianCalendar();
        GregorianCalendar returnDate = new GregorianCalendar();
        returnDate.add(Calendar.MONTH, 1);
        fileRent.writeToStorage(human.getIdPerson() + ", " + book.getId() + ", " + df.format(pickUpDate.getTime()) + ", " + df.format(returnDate.getTime()) + '\n', true);
        return "Книга отдана";
    }

    String returnBook(Person human, Book book) throws IOException {//Проверяем, есть ли записи с данными id
        if (!fileRent.foundLine(human, book)) {
            return "Нет записей";
        }
        String writeLine = fileRent.scannerFileSkipLine(human, book);

        Books myBooks = new Books();
        book.setNumberOfIssue(book.getNumberOfIssue() - 1);
        myBooks.changeBook(book);

        fileRent.writeToStorage(writeLine, false);
        return "Книга возвращена";
    }

    String renewBook(Person human, Book book, int month) throws IOException, ParseException { //Продление книги
        if (!fileRent.foundLine(human, book)) {
            return "Нет записей";
        }
        String[] splitLine = fileRent.scannerFileReturnArray(human, book);
        String line = fileRent.scannerFileSkipLine(human, book);

        Date date = df.parse(splitLine[END_DATE_RENT]); //Продлеваем дату сдачи на указанное количество месяцев
        Calendar endRentDate = Calendar.getInstance();
        endRentDate.setTime(date);
        endRentDate.add(Calendar.MONTH, month);

        fileRent.writeToStorage(line + human.getIdPerson() + ", " + book.getId() + ", " + splitLine[BEGIN_DATE_RENT] + ", " + df.format(endRentDate.getTime()) + '\n', false);
        return "Книга продлена";
    }
}
