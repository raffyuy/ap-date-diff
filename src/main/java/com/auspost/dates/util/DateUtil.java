package com.auspost.dates.util;

public class DateUtil {

//    February is the only month with 28 days.
//    There are 30 days in April, June, September and November.
//    There are 31 days in January, March, May, July, August, October, December.
    private static final int[] daysInMonthInCommonYear = new int[] {
            31, // Jan
            28, // Feb
            31, // March
            30, // April
            31, // May
            30, // June
            31, // July
            31, // August
            30, // September
            31, // October
            30, // November
            31, // December
    };

    /**
     * Gets the number of days in between two dates
     * @param earlierDate
     * @param laterDate
     * @return int number of days
     */
    public static int dateDiff(String earlierDate, String laterDate) {
        CustomDate earlier = parseDateString(earlierDate);
        CustomDate later = parseDateString(laterDate);

        // calculate the days in the partial years first
        int daysDiff = getDaysInCurrentYear(later) - getDaysInCurrentYear(earlier);

        // calculate the days in the full years, if any
        int numLeapYears = findNumberOfLeapYears(earlier, later);
        int numNonLeapYears = later.getYear() - earlier.getYear() - numLeapYears;

        // add it all up
        daysDiff += (numNonLeapYears * 365) + (numLeapYears * 366);
        return daysDiff;
    }

    /**
     * Counts the number of leap years between two dates, upperbound exclusive
     * @param date1
     * @param date2
     * @return
     */
    public static int findNumberOfLeapYears(CustomDate date1, CustomDate date2) {
        int floorYear = Math.min(date1.getYear(), date2.getYear());
        int ceilingYear = Math.max(date1.getYear(), date2.getYear());

        int count = 0;
        for (int year = floorYear; year < ceilingYear; year++) {

            if (DateUtil.isLeapYear(year)) {
                count = 1 + (ceilingYear - 1 - year) / 4;
                break;
            }
        }

        return count;
    }

    public static int getDaysInCurrentYear(CustomDate date) {
        int total = date.getDay();
        int prevMonthIndex = date.getMonth() - 2; // -2 because -1 index offset -1 month
        for (int i = prevMonthIndex; i >= 0; i--) {
            total += daysInMonthInCommonYear[i];
            if (i == 1 && isLeapYear(date.getYear())) { // Feb
                total += 1;
            }
        }
        return total;
    }



    /**
     * https://www.timeanddate.com/date/leapyear.html
     *
     * In our modern-day Gregorian calendar, three criteria must be taken into account to identify leap years:
     * The year must be evenly divisible by 4;
     * If the year can also be evenly divided by 100, it is not a leap year;
     * unless...
     * The year is also evenly divisible by 400. Then it is a leap year.
     * According to these rules, the years 2000 and 2400 are leap years,
     * while 1800, 1900, 2100, 2200, 2300, and 2500 are not leap years.
     */
    public static boolean isLeapYear(int year) {
        return (year % 100 == 0 && year % 400 == 0) || // centuries
                (year % 100 != 0 && year % 4 == 0); // other years
    }


    public static CustomDate parseDateString(String date) {
        // possibly do validaiton first

        String[] s1 = date.split(" ");
        int day = Integer.parseInt(s1[0]);
        int month = Integer.parseInt(s1[1]);
        int year = Integer.parseInt(s1[2]);

        return new CustomDate(day, month, year);
    }
}
