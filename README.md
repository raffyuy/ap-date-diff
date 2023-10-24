# DateUtil for date diffs
submission for the date diff coding challenge

##Assumptions
- input dates will always be in `dd MM yyyy` format.
- first date will always be the earlier date.
- input.csv will always have the correct format.



## To run:
```
./gradlew clean build run
```


## Additional note
I've used the `java.time.LocalDate` and `java.time.temporal.ChronoUnit.DAYS` 
in the unit test class as the source of truth. In other words, I'm verifying
if my `DateUtil.dateDiff(d1,d2)` works the same way as `DAYS.between(d1,d2)`