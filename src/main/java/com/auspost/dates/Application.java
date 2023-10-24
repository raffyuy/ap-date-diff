package com.auspost.dates;

import com.auspost.dates.util.DateUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Application {

    public static void main(String[] args) {
        ClassLoader classLoader = Application.class.getClassLoader();

        try (
              InputStream inputStream = classLoader.getResourceAsStream("input.csv");
              BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            reader.lines().forEach(line -> {
                String[] dates = line.split(",");
                System.out.printf("%s, %s, %d\n", dates[0], dates[1], DateUtil.dateDiff(dates[0], dates[1]));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
