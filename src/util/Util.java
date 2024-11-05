package util;

import java.util.Calendar;

public class Util {

    /**Enum representing all the months of the year.
     * The months are zero indexed.
     *
     */
    public static enum Months {
        JANUARY("Jan"),
        FEBUARY("Feb"),
        MARCH("March"),
        APRIL("April"),
        MAY("May"),
        JUNE("June"),
        JULY("July"),
        AUGUST("Aug"),
        SEPTEMBER("Sep"),
        OCTOBER("Oct"),
        NOVEMBER("Nov"),
        DECEMBER("Dec");

        public final String name;

        /**Returns the String symbol for each month.
         *
         * @return the month's symbol
         */
        @Override
        public String toString() {
            return name;
        }

        Months(String symbol) {
            name = symbol;
        }

    }

}
