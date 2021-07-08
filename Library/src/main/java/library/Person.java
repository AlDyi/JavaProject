package library;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Person {
    Integer idPerson;
    String FIO;
    GregorianCalendar dateBegin;
    GregorianCalendar dateEnd;

    Person (Integer idPerson, String FIO)
    {
        this.idPerson = idPerson;
        this.FIO = FIO;
        dateBegin = new GregorianCalendar();
        dateEnd = new GregorianCalendar();
        dateEnd.add(Calendar.YEAR, 5);
//        DateFormat df = new SimpleDateFormat("dd/MM/yyy");
//        System.out.println(this.idPerson + " " + this.FIO + " " + df.format(dateBegin.getTime()) + " " + df.format(dateEnd.getTime()));
    }
}
