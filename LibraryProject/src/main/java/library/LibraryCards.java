package library;

import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class LibraryCards {
    private final Storage filePersons = new Storage("Persons");
    private final FileLibraryCardStorage fileRent = new FileLibraryCardStorage("RentOfBook");
    private final FileBookStorage fileBooks = new FileBookStorage("FileBooks");
    DateFormat df = new SimpleDateFormat("dd/MM/yyy");
    public static final int NAME = 1;
    public static final int AUTHOR = 2;
    public static final int COPIES = 3;
    public static final int ISSUE = 4;
    public static final int BEGIN_DATE_RENT = 2;
    public static final int END_DATE_RENT = 3;

    String addPerson(Person human) throws IOException {
        filePersons.writeToStorage(human.getIdPerson() + ", " + human.getFio() + ", " + df.format(human.getDateBegin().getTime()) + ", " + df.format(human.getDateEnd().getTime()) + '\n', true);
        return "Данные добавлены";
    }

    String takeBook(int idHuman, int idBook) throws IOException { //Принимать id
        if (!fileBooks.foundLine(idBook)) { //Проверяем, доступна ли книга с данным id
            return "Книги с таким ID нет в базе. Проверьте корректность введенных данных.";
        }
        if (!filePersons.foundLine(idHuman)) {
            return "Пользователя с таким ID нет в системе."; //Проверяем, есть ли пользователь с таким id
        }
        String[] splitLineBook = fileBooks.scannerFileReturnArray(idBook);
        if (splitLineBook[COPIES].equals(splitLineBook[ISSUE])) { //Есть ли экзампляры книги в наличие
            return "Данной книги пока нет в наличии";
        }
        Books myBooks = new Books();
        splitLineBook[ISSUE] += 1;
        Book book = new Book(idBook, splitLineBook[NAME], splitLineBook[AUTHOR], Integer.parseInt(splitLineBook[COPIES]), Integer.parseInt(splitLineBook[ISSUE]));
        myBooks.deleteBook(idBook);
        myBooks.addBook(book);

        GregorianCalendar pickUpDate = new GregorianCalendar();
        GregorianCalendar returnDate = new GregorianCalendar();
        returnDate.add(Calendar.MONTH, 1);
        fileRent.writeToStorage(idHuman + ", " + idBook + ", " + df.format(pickUpDate.getTime()) + ", " + df.format(returnDate.getTime()) + '\n', true);
        return "Книга отдана";
    }

    String returnBook(int idHuman, int idBook) throws IOException {//Проверяем, есть ли записи с данными id
        if (!fileRent.foundLine(idHuman, idBook)) {
            return "Нет записей";
        }
        String writeLine = fileRent.scannerFileSkipLine(idHuman, idBook);
        String[] splitLineBook = fileBooks.scannerFileReturnArray(idBook);

        Books myBooks = new Books();
        Book book = new Book(idBook, splitLineBook[NAME], splitLineBook[AUTHOR], Integer.parseInt(splitLineBook[COPIES]), Integer.parseInt(splitLineBook[ISSUE]) - 1);
        myBooks.deleteBook(idBook);
        myBooks.addBook(book);

        fileRent.writeToStorage(writeLine, false);
        return "Книга возвращена";
    }

    String renewBook(int idHuman, int idBook, int month) throws IOException, ParseException { //Продление книги
        if (!fileRent.foundLine(idHuman, idBook)) {
            return "Нет записей";
        }
        String[] splitLine = fileRent.scannerFileReturnArray(idHuman, idBook);
        String line = fileRent.scannerFileSkipLine(idHuman, idBook);

        Date date = df.parse(splitLine[END_DATE_RENT]); //Продлеваем дату сдачи на указанное количество месяцев
        Calendar endRentDate = Calendar.getInstance();
        endRentDate.setTime(date);
        endRentDate.add(Calendar.MONTH, month);

        fileRent.writeToStorage(line + idHuman + ", " + idBook + ", " + splitLine[BEGIN_DATE_RENT] + ", " + df.format(endRentDate.getTime()) + '\n', false);
        return "Книга продлена";
    }
}
