package com.auspost.dates;

public class DateUtil {


//    Each month has 28, 30, or 31 days during a normal year, which has 365 days.
//    A leap year occurs nearly every 4 years which adds an extra day to February.
//    February is the only month with 28 days.
//    There are 30 days in April, June, September and November.
//    There are 31 days in January, March, May, July, August, October, December.
    private static final int[] daysInMonth = new int[] {
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
    public static int dateDiff(String date1, String date2) {
        CustomDate cd1 = parseDateString(date1);
        CustomDate cd2 = parseDateString(date2);

        int daysDiff = getDaysInCurrentYear(cd2) - getDaysInCurrentYear(cd1);
        int numLeapYears = findNumberOfLeapYears(cd1, cd2);

        int numNonLeapYears = Math.abs(cd1.getYear() - cd2.getYear()) - numLeapYears;
        daysDiff += (numNonLeapYears * 365) + (numLeapYears * 366);
        return daysDiff;
    }

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
            total += daysInMonth[i];
            if (i == 1 && isLeapYear(date.getYear())) {
                total += 1;
            }
        }
        return total;
    }



    /**
     * Rules for determining if it's a leap year:
     * The year is evenly divisible by 4;
     * If the year can be evenly divided by 100, it is NOT a leap year, unless;
     * The year is also evenly divisible by 400. Then it is a leap year.
     *
     */
    public static boolean isLeapYear(int year) {
        return (year % 100 == 0 && year % 400 == 0) ||
                (year % 100 != 0 && year % 4 == 0);
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
