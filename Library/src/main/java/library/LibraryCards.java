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
    File filePersons = new File("Persons");
    File fileRent = new File("RentOfBook");
    File fileBooks = new File("FileBooks");
    DateFormat df = new SimpleDateFormat("dd/MM/yyy");

    void addPerson(Person human) throws IOException {
        FileWriter fileWriter = new FileWriter(filePersons, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(human.idPerson + ", " + human.FIO + ", " + df.format(human.dateBegin.getTime()) + ", " + df.format(human.dateEnd.getTime()) + '\n');
        bufferedWriter.close();
        System.out.println("Данные добавлены");
    }

    void takeBook(int idPerson, int idBook) throws IOException {
        Scanner scanner = new Scanner(fileBooks); //Проверяем, доступна ли книга с данным id
        int k = 0;
        String[] splitLineBook = new String[0];
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(Integer.toString(idBook))) {
                k++;
                splitLineBook = line.split(", ");
                if (splitLineBook[2].equals(splitLineBook[3])) {
                    System.out.println("Данной книги пока нет в наличии");
                    return;
                }
            }
        }
        if (k == 0) {
            System.out.println("Книги с таким ID нет в базе. Проверьте корректность введенных данных.");
            return;
        }
        scanner.close();

        Scanner scanner1 = new Scanner(filePersons); //Проверяем, есть ли пользователь с таким id
        int i = 0;
        while (scanner1.hasNextLine()) {
            String line = scanner1.nextLine();
            if (line.contains(Integer.toString(idPerson))) {
                i++;
            }
        }
        if (i == 0) {
            System.out.println("Пользователя с таким ID нет в системе.");
            return;
        }
        scanner1.close();

        Books myBooks = new Books();
        myBooks.deleteBook(idBook);
        Book myBook = new Book(Integer.parseInt(splitLineBook[0]), splitLineBook[1], splitLineBook[2], Integer.parseInt(splitLineBook[3]), Integer.parseInt(splitLineBook[4])+1);
        myBooks.addBook(myBook);

        FileWriter fileWriter = new FileWriter(fileRent, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        GregorianCalendar pickUpDate = new GregorianCalendar();
        GregorianCalendar returnDate = new GregorianCalendar();
        returnDate.add(Calendar.MONTH, 1);
        bufferedWriter.write(idPerson + ", " + idBook + ", " + df.format(pickUpDate.getTime()) + ", " + df.format(returnDate.getTime()));
        bufferedWriter.close();
        System.out.println("Книга отдана");
    }

    void returnBook(int idPerson, int idBook) throws IOException {
        Scanner scanner = new Scanner(fileRent); //Проверяем, есть ли записи с данными id
        int k = 0;
        StringBuilder writeLine = new StringBuilder();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(Integer.toString(idBook)) && line.contains((Integer.toString(idPerson)))) {
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

        Scanner scanner2 = new Scanner(fileBooks);
        String[] splitLineBook = new String[0];
        while (scanner2.hasNextLine()) {
            String line = scanner2.nextLine();
            if (line.contains(Integer.toString(idBook))) {
                splitLineBook = line.split(", ");
            }
        }

        Books myBooks = new Books();
        myBooks.deleteBook(idBook);
        Book myBook = new Book(Integer.parseInt(splitLineBook[0]), splitLineBook[1], splitLineBook[2], Integer.parseInt(splitLineBook[3]), Integer.parseInt(splitLineBook[4])-1);
        myBooks.addBook(myBook);

        FileWriter fileWriter = new FileWriter(fileRent, false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(writeLine.toString());
        bufferedWriter.close();
        System.out.println("Книга возвращена");
    }

    void renewBook(int idPerson, int idBook, int month) throws IOException, ParseException { //Продление книги
        Scanner scanner = new Scanner(fileRent);
        int k = 0;
        String[] splitLine = new String[0];
        StringBuilder writeLine = new StringBuilder();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(Integer.toString(idBook)) && line.contains((Integer.toString(idPerson)))) {
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

        Date date = df.parse(splitLine[3]); //Продлеваем дату сдачи на указанное количество месяцев
        Calendar endRentDate = Calendar.getInstance();
        endRentDate.setTime(date);
        endRentDate.add(Calendar.MONTH, month);

        FileWriter fileWriter = new FileWriter(fileRent, false); //Перезаписываем файл
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(writeLine.toString() + idPerson + ", " + idBook + ", " + splitLine[2] + ", " + df.format(endRentDate.getTime()) + '\n');
        bufferedWriter.close();
        System.out.println("Книга продлена");
    }
}
