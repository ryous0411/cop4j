package org.cop4j;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Period {

  private final LocalDate from;

  private final Integer numberOfMonths;

  private final List<MonthlyPeriod> monthlyPeriods;

  public Period(LocalDate from, Integer numberOfMonths) {
    this.from = Optional.ofNullable(from)
        .orElseThrow(() -> new IllegalArgumentException("[from] is required."));
    this.numberOfMonths = Optional.ofNullable(numberOfMonths)
        .orElseThrow(() -> new IllegalArgumentException("[numberOfMonths] is required."));
    this.monthlyPeriods = calcMonthlyPeriods();
  }

  private List<MonthlyPeriod> calcMonthlyPeriods() {

    var startOfMonths = IntStream.range(0, this.numberOfMonths + 1)
        .mapToObj(i -> {

          var nextMonth = from.plusMonths(i);

          return from.getDayOfMonth() <= nextMonth.lengthOfMonth()
              ? nextMonth
              : nextMonth.plusMonths(1).withDayOfMonth(1);

        }).collect(Collectors.toList());

    return IntStream.range(1, startOfMonths.size())
        .mapToObj(i -> {

          var fromOfMonth = startOfMonths.get(i - 1);
          var toOfMonth = startOfMonths.get(i).minusDays(1);

          return new MonthlyPeriod(i, fromOfMonth, toOfMonth);

        }).collect(Collectors.toList());
  }

  @Getter
  @AllArgsConstructor
  public static class MonthlyPeriod {

    private Integer months;

    private LocalDate from;

    private LocalDate to;
  }
}
