package library;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Person {
    private Integer idPerson;
    private String fio;
    private GregorianCalendar dateBegin;
    private GregorianCalendar dateEnd;

    Person (Integer idPerson, String fio)
    {
        this.idPerson = idPerson;
        this.fio = fio;
        dateBegin = new GregorianCalendar();
        dateEnd = new GregorianCalendar();
        dateEnd.add(Calendar.YEAR, 5);
    }

    public GregorianCalendar getDateBegin() {
        return dateBegin;
    }

    public GregorianCalendar getDateEnd() {
        return dateEnd;
    }

    public Integer getIdPerson() {
        return idPerson;
    }

    public String getFio() {
        return fio;
    }
}
