package org.cop4j;

import static java.time.temporal.ChronoUnit.MONTHS;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Period {

  private final LocalDate from;

  private final LocalDate to;

  private final Long numberOfMonths;

  private final List<MonthlyPeriod> monthlyPeriods;

  public Period(LocalDate from, LocalDate to) {
    this.from = Optional.ofNullable(from)
        .orElseThrow(() -> new IllegalArgumentException("[from] is required."));
    this.to = Optional.ofNullable(to)
        .orElseThrow(() -> new IllegalArgumentException("[to] is required."));
    this.numberOfMonths = this.from.until(this.to, MONTHS) + 1;
    this.monthlyPeriods = calcMonthlyPeriods();
  }

  private List<MonthlyPeriod> calcMonthlyPeriods() {

    if (!valid()) {
      throw new IllegalArgumentException(
          "The specified period is invalid. [" + from + " - " + to + "]");
    }

    if (from.getDayOfMonth() == 1) {

      return calcMonthlyPeriods(
          monthlyPeriodFrom -> monthlyPeriodFrom.withDayOfMonth(monthlyPeriodFrom.lengthOfMonth())
      );

    } else if (from.getDayOfMonth() >= 29) {

      return calcMonthlyPeriods(
          monthlyPeriodFrom -> {

            var lastMonth = monthlyPeriodFrom.getDayOfMonth() == 1
                ? monthlyPeriodFrom
                : monthlyPeriodFrom.plusMonths(1);
            return lastMonth
                .withDayOfMonth(Math.min(to.getDayOfMonth(), lastMonth.lengthOfMonth()));
          }
      );
    }

    return calcMonthlyPeriods(
        monthlyPeriodFrom -> monthlyPeriodFrom.plusMonths(1).minusDays(1)
    );
  }

  private boolean valid() {

    return from.getDayOfMonth() == to.plusDays(1).getDayOfMonth()
        || to.plusDays(1).getDayOfMonth() == 1 && from.getDayOfMonth() > to.getDayOfMonth();
  }

  private List<MonthlyPeriod> calcMonthlyPeriods(UnaryOperator<LocalDate> endDateFunc) {

    List<MonthlyPeriod> calculatedPeriods = new ArrayList<>();

    for (int i = 0; i < numberOfMonths; i++) {

      var startDate = i == 0 ? this.from : calculatedPeriods.get(i - 1).getTo().plusDays(1);
      var endDate = endDateFunc.apply(startDate);

      calculatedPeriods.add(
          new MonthlyPeriod(
              i + 1,
              startDate,
              endDate
          )
      );
    }

    return calculatedPeriods;
  }

  @Getter
  @AllArgsConstructor
  public static class MonthlyPeriod {

    private long months;

    private LocalDate from;

    private LocalDate to;
  }
}
