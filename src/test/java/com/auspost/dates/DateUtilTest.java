package com.auspost.dates;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.*;


class DateUtilTest {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
    @Test
    void dateDiff_BasicExhaustive() {
        LocalDate dateBefore = LocalDate.of(1900, 1, 1);
        LocalDate dateAfter = dateBefore;
        for (int i = 1; dateAfter.getYear() < 2022; i++) {
            dateAfter = dateAfter.plusDays(1);
            doDateDiffAssertion(dateBefore, dateAfter);
        }
    }

    @Test
    void dateDiff_sameYearSameMonth() {
        LocalDate dateBefore = LocalDate.of(2000, 5, 11);
        LocalDate dateAfter = LocalDate.of(2000, 5, 22);

        doDateDiffAssertion(dateBefore, dateAfter);
    }

    @Test
    void dateDiff_differentYearsNoLeapYears() {
        LocalDate dateBefore = LocalDate.of(1997, 5, 11);
        LocalDate dateAfter = LocalDate.of(1999, 2, 22);

        doDateDiffAssertion(dateBefore, dateAfter);
    }

    @Test
    void dateDiff_differentYearsLeapYears() {
        LocalDate dateBefore = LocalDate.of(1999, 5, 11);
        LocalDate dateAfter = LocalDate.of(2000, 2, 22);

        doDateDiffAssertion(dateBefore, dateAfter);
    }


    @Test
    void dateDiff_partialYearIsLeapYear() {
        LocalDate dateBefore = LocalDate.of(2000, 5, 11);
        LocalDate dateAfter = LocalDate.of(2004, 2, 22);

        doDateDiffAssertion(dateBefore, dateAfter);
    }


    @Test
    void dateDiff_partialYearIsLeapYear2() {
        LocalDate dateBefore = LocalDate.of(1999, 4, 11);
        LocalDate dateAfter = LocalDate.of(2000, 3, 22);

        doDateDiffAssertion(dateBefore, dateAfter);
    }



    private int doDateDiffAssertion(LocalDate dateBefore, LocalDate dateAfter) {
        int diff = DateUtil.dateDiff(dateBefore.format(formatter), dateAfter.format(formatter));

        assertEquals(DAYS.between(dateBefore, dateAfter), diff);
        return diff;
    }


    @ParameterizedTest
    @CsvSource({
            "1900,1901,0",
            "2000,2000,0",
            "2000,2001,1",
            "1999,2000,0",
            "2000,2005,2",
            "2000,2021,6"
    })
    void findNumberOfLeapYears(int year1, int year2, int expected) {
        CustomDate date1 = new CustomDate(1, 1, year1);
        CustomDate date2 = new CustomDate(1, 1, year2);
        int numberOfLeapYears = DateUtil.findNumberOfLeapYears(date1, date2);

        assertEquals(expected, numberOfLeapYears);
    }

    @ParameterizedTest
    @ValueSource(ints = {1904, 1908, 1912, 1916, 1920, 1924, 1928, 1932, 1936,
            1940, 1944, 1948, 1952, 1956, 1960, 1964, 1968, 1972, 1976,
            1980, 1984, 1988, 1992, 1996, 2000, 2004, 2008, 2012, 2016,
            2020, 2024})
    void isLeapYear_True(int year) {
        assertTrue(DateUtil.isLeapYear(year));
    }

    @ParameterizedTest
    @ValueSource(ints = {1900, 2200, 2001, 2002, 2003, 1901, 1902, 1903})
    void isLeapYear_False(int year) {
        assertFalse(DateUtil.isLeapYear(year));
    }

    @Test
    void parseDateString() {
        CustomDate customDate = DateUtil.parseDateString("22 11 2022");
        assertEquals(22, customDate.getDay());
        assertEquals(11, customDate.getMonth());
        assertEquals(2022, customDate.getYear());
    }

    @Test
    void getDaysInCurrentYear() {
        CustomDate date = new CustomDate(22,3,2001);
        int days = DateUtil.getDaysInCurrentYear(date);

    }
}