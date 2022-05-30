package org.cop4j;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;
import org.cop4j.Period.MonthlyPeriod;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PeriodTest {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

  @ParameterizedTest
  @MethodSource("successPeriodsTestDataProvider")
  void successCalculateOfPeriod(LocalDate from, Integer numberOfMonths, List<MonthlyPeriod> periodsTestData) {

    var period = new Period(from, numberOfMonths);

    assertThat(period.getFrom()).isEqualTo(from);
    assertThat(period.getNumberOfMonths()).isEqualTo(numberOfMonths);

    var monthlyPeriods = period.getMonthlyPeriods();

    for (int i = 0; i < period.getNumberOfMonths(); i++) {

      assertThat(monthlyPeriods.get(i).getMonths()).isEqualTo(periodsTestData.get(i).getMonths());
      assertThat(monthlyPeriods.get(i).getFrom()).isEqualTo(periodsTestData.get(i).getFrom());
      assertThat(monthlyPeriods.get(i).getTo()).isEqualTo(periodsTestData.get(i).getTo());
    }
  }

  @Test
  void failureCalculateOfPeriod() {

    var from = toLocalDate("2020-02-28");
    assertThrows(IllegalArgumentException.class, () -> new Period(from, null));
  }

  static Stream<Arguments> successPeriodsTestDataProvider() {

    return Stream.of(
        arguments("2020-02-01", beginningOfTheMonth().size(), beginningOfTheMonth()),
        arguments("2020-01-15", dayOfTheMonth().size(), dayOfTheMonth()),
        arguments("2021-02-28", endOfTheMonth28().size(), endOfTheMonth28()),
        arguments("2020-02-29", endOfTheMonth29().size(), endOfTheMonth29()),
        arguments("2020-04-30", endOfTheMonth30().size(), endOfTheMonth30()),
        arguments("2021-07-31", endOfTheMonth31().size(), endOfTheMonth31()),
        arguments("2021-01-30", beforeTheEndOfTheMonth().size(),
            beforeTheEndOfTheMonth())
    );
  }

  static List<MonthlyPeriod> beginningOfTheMonth() {
    // 起算日が月の初日
    return List.of(
        new MonthlyPeriod(1, toLocalDate("2020-02-01"), toLocalDate("2020-02-29")),
        new MonthlyPeriod(2, toLocalDate("2020-03-01"), toLocalDate("2020-03-31")),
        new MonthlyPeriod(3, toLocalDate("2020-04-01"), toLocalDate("2020-04-30")),
        new MonthlyPeriod(4, toLocalDate("2020-05-01"), toLocalDate("2020-05-31")),
        new MonthlyPeriod(5, toLocalDate("2020-06-01"), toLocalDate("2020-06-30")),
        new MonthlyPeriod(6, toLocalDate("2020-07-01"), toLocalDate("2020-07-31")),
        new MonthlyPeriod(7, toLocalDate("2020-08-01"), toLocalDate("2020-08-31")),
        new MonthlyPeriod(8, toLocalDate("2020-09-01"), toLocalDate("2020-09-30")),
        new MonthlyPeriod(9, toLocalDate("2020-10-01"), toLocalDate("2020-10-31")),
        new MonthlyPeriod(10, toLocalDate("2020-11-01"), toLocalDate("2020-11-30")),
        new MonthlyPeriod(11, toLocalDate("2020-12-01"), toLocalDate("2020-12-31")),
        new MonthlyPeriod(12, toLocalDate("2021-01-01"), toLocalDate("2021-01-31")),
        new MonthlyPeriod(13, toLocalDate("2021-02-01"), toLocalDate("2021-02-28")),
        new MonthlyPeriod(14, toLocalDate("2021-03-01"), toLocalDate("2021-03-31")),
        new MonthlyPeriod(15, toLocalDate("2021-04-01"), toLocalDate("2021-04-30"))
    );
  }

  static List<MonthlyPeriod> dayOfTheMonth() {
    // 月によって期間を定めた場合において最後の月に応答日がある
    return List.of(
        new MonthlyPeriod(1, toLocalDate("2020-01-15"), toLocalDate("2020-02-14")),
        new MonthlyPeriod(2, toLocalDate("2020-02-15"), toLocalDate("2020-03-14")),
        new MonthlyPeriod(3, toLocalDate("2020-03-15"), toLocalDate("2020-04-14")),
        new MonthlyPeriod(4, toLocalDate("2020-04-15"), toLocalDate("2020-05-14")),
        new MonthlyPeriod(5, toLocalDate("2020-05-15"), toLocalDate("2020-06-14")),
        new MonthlyPeriod(6, toLocalDate("2020-06-15"), toLocalDate("2020-07-14")),
        new MonthlyPeriod(7, toLocalDate("2020-07-15"), toLocalDate("2020-08-14")),
        new MonthlyPeriod(8, toLocalDate("2020-08-15"), toLocalDate("2020-09-14")),
        new MonthlyPeriod(9, toLocalDate("2020-09-15"), toLocalDate("2020-10-14")),
        new MonthlyPeriod(10, toLocalDate("2020-10-15"), toLocalDate("2020-11-14")),
        new MonthlyPeriod(11, toLocalDate("2020-11-15"), toLocalDate("2020-12-14")),
        new MonthlyPeriod(12, toLocalDate("2020-12-15"), toLocalDate("2021-01-14")),
        new MonthlyPeriod(13, toLocalDate("2021-01-15"), toLocalDate("2021-02-14")),
        new MonthlyPeriod(14, toLocalDate("2021-02-15"), toLocalDate("2021-03-14")),
        new MonthlyPeriod(15, toLocalDate("2021-03-15"), toLocalDate("2021-04-14")),
        new MonthlyPeriod(16, toLocalDate("2021-04-15"), toLocalDate("2021-05-14")),
        new MonthlyPeriod(17, toLocalDate("2021-05-15"), toLocalDate("2021-06-14")),
        new MonthlyPeriod(18, toLocalDate("2021-06-15"), toLocalDate("2021-07-14")),
        new MonthlyPeriod(19, toLocalDate("2021-07-15"), toLocalDate("2021-08-14")),
        new MonthlyPeriod(20, toLocalDate("2021-08-15"), toLocalDate("2021-09-14")),
        new MonthlyPeriod(21, toLocalDate("2021-09-15"), toLocalDate("2021-10-14")),
        new MonthlyPeriod(22, toLocalDate("2021-10-15"), toLocalDate("2021-11-14")),
        new MonthlyPeriod(23, toLocalDate("2021-11-15"), toLocalDate("2021-12-14")),
        new MonthlyPeriod(24, toLocalDate("2021-12-15"), toLocalDate("2022-01-14"))
    );
  }

  static List<MonthlyPeriod> endOfTheMonth28() {
    // 起算日が月末(28日)、かつ月によって期間を定めた場合において最後の月に応答日がある
    return List.of(
        new MonthlyPeriod(1, toLocalDate("2021-02-28"), toLocalDate("2021-03-27")),
        new MonthlyPeriod(2, toLocalDate("2021-03-28"), toLocalDate("2021-04-27")),
        new MonthlyPeriod(3, toLocalDate("2021-04-28"), toLocalDate("2021-05-27")),
        new MonthlyPeriod(4, toLocalDate("2021-05-28"), toLocalDate("2021-06-27")),
        new MonthlyPeriod(5, toLocalDate("2021-06-28"), toLocalDate("2021-07-27")),
        new MonthlyPeriod(6, toLocalDate("2021-07-28"), toLocalDate("2021-08-27")),
        new MonthlyPeriod(7, toLocalDate("2021-08-28"), toLocalDate("2021-09-27")),
        new MonthlyPeriod(8, toLocalDate("2021-09-28"), toLocalDate("2021-10-27")),
        new MonthlyPeriod(9, toLocalDate("2021-10-28"), toLocalDate("2021-11-27")),
        new MonthlyPeriod(10, toLocalDate("2021-11-28"), toLocalDate("2021-12-27")),
        new MonthlyPeriod(11, toLocalDate("2021-12-28"), toLocalDate("2022-01-27")),
        new MonthlyPeriod(12, toLocalDate("2022-01-28"), toLocalDate("2022-02-27")),
        new MonthlyPeriod(13, toLocalDate("2022-02-28"), toLocalDate("2022-03-27")),
        new MonthlyPeriod(14, toLocalDate("2022-03-28"), toLocalDate("2022-04-27")),
        new MonthlyPeriod(15, toLocalDate("2022-04-28"), toLocalDate("2022-05-27")),
        new MonthlyPeriod(16, toLocalDate("2022-05-28"), toLocalDate("2022-06-27")),
        new MonthlyPeriod(17, toLocalDate("2022-06-28"), toLocalDate("2022-07-27")),
        new MonthlyPeriod(18, toLocalDate("2022-07-28"), toLocalDate("2022-08-27")),
        new MonthlyPeriod(19, toLocalDate("2022-08-28"), toLocalDate("2022-09-27")),
        new MonthlyPeriod(20, toLocalDate("2022-09-28"), toLocalDate("2022-10-27")),
        new MonthlyPeriod(21, toLocalDate("2022-10-28"), toLocalDate("2022-11-27")),
        new MonthlyPeriod(22, toLocalDate("2022-11-28"), toLocalDate("2022-12-27")),
        new MonthlyPeriod(23, toLocalDate("2022-12-28"), toLocalDate("2023-01-27")),
        new MonthlyPeriod(24, toLocalDate("2023-01-28"), toLocalDate("2023-02-27")),
        new MonthlyPeriod(25, toLocalDate("2023-02-28"), toLocalDate("2023-03-27")),
        new MonthlyPeriod(26, toLocalDate("2023-03-28"), toLocalDate("2023-04-27")),
        new MonthlyPeriod(27, toLocalDate("2023-04-28"), toLocalDate("2023-05-27")),
        new MonthlyPeriod(28, toLocalDate("2023-05-28"), toLocalDate("2023-06-27")),
        new MonthlyPeriod(29, toLocalDate("2023-06-28"), toLocalDate("2023-07-27")),
        new MonthlyPeriod(30, toLocalDate("2023-07-28"), toLocalDate("2023-08-27")),
        new MonthlyPeriod(31, toLocalDate("2023-08-28"), toLocalDate("2023-09-27")),
        new MonthlyPeriod(32, toLocalDate("2023-09-28"), toLocalDate("2023-10-27")),
        new MonthlyPeriod(33, toLocalDate("2023-10-28"), toLocalDate("2023-11-27")),
        new MonthlyPeriod(34, toLocalDate("2023-11-28"), toLocalDate("2023-12-27")),
        new MonthlyPeriod(35, toLocalDate("2023-12-28"), toLocalDate("2024-01-27")),
        new MonthlyPeriod(36, toLocalDate("2024-01-28"), toLocalDate("2024-02-27")),
        new MonthlyPeriod(37, toLocalDate("2024-02-28"), toLocalDate("2024-03-27")),
        new MonthlyPeriod(38, toLocalDate("2024-03-28"), toLocalDate("2024-04-27")),
        new MonthlyPeriod(39, toLocalDate("2024-04-28"), toLocalDate("2024-05-27")),
        new MonthlyPeriod(40, toLocalDate("2024-05-28"), toLocalDate("2024-06-27")),
        new MonthlyPeriod(41, toLocalDate("2024-06-28"), toLocalDate("2024-07-27")),
        new MonthlyPeriod(42, toLocalDate("2024-07-28"), toLocalDate("2024-08-27")),
        new MonthlyPeriod(43, toLocalDate("2024-08-28"), toLocalDate("2024-09-27")),
        new MonthlyPeriod(44, toLocalDate("2024-09-28"), toLocalDate("2024-10-27")),
        new MonthlyPeriod(45, toLocalDate("2024-10-28"), toLocalDate("2024-11-27")),
        new MonthlyPeriod(46, toLocalDate("2024-11-28"), toLocalDate("2024-12-27")),
        new MonthlyPeriod(47, toLocalDate("2024-12-28"), toLocalDate("2025-01-27")),
        new MonthlyPeriod(48, toLocalDate("2025-01-28"), toLocalDate("2025-02-27"))
    );
  }

  static List<MonthlyPeriod> endOfTheMonth29() {
    // 起算日が月末(29日)、かつ月によって期間を定めた場合において最後の月に応答日がない
    return List.of(
        new MonthlyPeriod(1, toLocalDate("2020-02-29"), toLocalDate("2020-03-28")),
        new MonthlyPeriod(2, toLocalDate("2020-03-29"), toLocalDate("2020-04-28")),
        new MonthlyPeriod(3, toLocalDate("2020-04-29"), toLocalDate("2020-05-28")),
        new MonthlyPeriod(4, toLocalDate("2020-05-29"), toLocalDate("2020-06-28")),
        new MonthlyPeriod(5, toLocalDate("2020-06-29"), toLocalDate("2020-07-28")),
        new MonthlyPeriod(6, toLocalDate("2020-07-29"), toLocalDate("2020-08-28")),
        new MonthlyPeriod(7, toLocalDate("2020-08-29"), toLocalDate("2020-09-28")),
        new MonthlyPeriod(8, toLocalDate("2020-09-29"), toLocalDate("2020-10-28")),
        new MonthlyPeriod(9, toLocalDate("2020-10-29"), toLocalDate("2020-11-28")),
        new MonthlyPeriod(10, toLocalDate("2020-11-29"), toLocalDate("2020-12-28")),
        new MonthlyPeriod(11, toLocalDate("2020-12-29"), toLocalDate("2021-01-28")),
        new MonthlyPeriod(12, toLocalDate("2021-01-29"), toLocalDate("2021-02-28")),
        new MonthlyPeriod(13, toLocalDate("2021-03-01"), toLocalDate("2021-03-28")),
        new MonthlyPeriod(14, toLocalDate("2021-03-29"), toLocalDate("2021-04-28")),
        new MonthlyPeriod(15, toLocalDate("2021-04-29"), toLocalDate("2021-05-28")),
        new MonthlyPeriod(16, toLocalDate("2021-05-29"), toLocalDate("2021-06-28")),
        new MonthlyPeriod(17, toLocalDate("2021-06-29"), toLocalDate("2021-07-28")),
        new MonthlyPeriod(18, toLocalDate("2021-07-29"), toLocalDate("2021-08-28")),
        new MonthlyPeriod(19, toLocalDate("2021-08-29"), toLocalDate("2021-09-28")),
        new MonthlyPeriod(20, toLocalDate("2021-09-29"), toLocalDate("2021-10-28")),
        new MonthlyPeriod(21, toLocalDate("2021-10-29"), toLocalDate("2021-11-28")),
        new MonthlyPeriod(22, toLocalDate("2021-11-29"), toLocalDate("2021-12-28")),
        new MonthlyPeriod(23, toLocalDate("2021-12-29"), toLocalDate("2022-01-28")),
        new MonthlyPeriod(24, toLocalDate("2022-01-29"), toLocalDate("2022-02-28")),
        new MonthlyPeriod(25, toLocalDate("2022-03-01"), toLocalDate("2022-03-28")),
        new MonthlyPeriod(26, toLocalDate("2022-03-29"), toLocalDate("2022-04-28")),
        new MonthlyPeriod(27, toLocalDate("2022-04-29"), toLocalDate("2022-05-28")),
        new MonthlyPeriod(28, toLocalDate("2022-05-29"), toLocalDate("2022-06-28")),
        new MonthlyPeriod(29, toLocalDate("2022-06-29"), toLocalDate("2022-07-28")),
        new MonthlyPeriod(30, toLocalDate("2022-07-29"), toLocalDate("2022-08-28")),
        new MonthlyPeriod(31, toLocalDate("2022-08-29"), toLocalDate("2022-09-28")),
        new MonthlyPeriod(32, toLocalDate("2022-09-29"), toLocalDate("2022-10-28")),
        new MonthlyPeriod(33, toLocalDate("2022-10-29"), toLocalDate("2022-11-28")),
        new MonthlyPeriod(34, toLocalDate("2022-11-29"), toLocalDate("2022-12-28")),
        new MonthlyPeriod(35, toLocalDate("2022-12-29"), toLocalDate("2023-01-28")),
        new MonthlyPeriod(36, toLocalDate("2023-01-29"), toLocalDate("2023-02-28")),
        new MonthlyPeriod(37, toLocalDate("2023-03-01"), toLocalDate("2023-03-28")),
        new MonthlyPeriod(38, toLocalDate("2023-03-29"), toLocalDate("2023-04-28")),
        new MonthlyPeriod(39, toLocalDate("2023-04-29"), toLocalDate("2023-05-28")),
        new MonthlyPeriod(40, toLocalDate("2023-05-29"), toLocalDate("2023-06-28")),
        new MonthlyPeriod(41, toLocalDate("2023-06-29"), toLocalDate("2023-07-28")),
        new MonthlyPeriod(42, toLocalDate("2023-07-29"), toLocalDate("2023-08-28")),
        new MonthlyPeriod(43, toLocalDate("2023-08-29"), toLocalDate("2023-09-28")),
        new MonthlyPeriod(44, toLocalDate("2023-09-29"), toLocalDate("2023-10-28")),
        new MonthlyPeriod(45, toLocalDate("2023-10-29"), toLocalDate("2023-11-28")),
        new MonthlyPeriod(46, toLocalDate("2023-11-29"), toLocalDate("2023-12-28")),
        new MonthlyPeriod(47, toLocalDate("2023-12-29"), toLocalDate("2024-01-28")),
        new MonthlyPeriod(48, toLocalDate("2024-01-29"), toLocalDate("2024-02-28")),
        new MonthlyPeriod(49, toLocalDate("2024-02-29"), toLocalDate("2024-03-28")),
        new MonthlyPeriod(50, toLocalDate("2024-03-29"), toLocalDate("2024-04-28")),
        new MonthlyPeriod(51, toLocalDate("2024-04-29"), toLocalDate("2024-05-28")),
        new MonthlyPeriod(52, toLocalDate("2024-05-29"), toLocalDate("2024-06-28")),
        new MonthlyPeriod(53, toLocalDate("2024-06-29"), toLocalDate("2024-07-28")),
        new MonthlyPeriod(54, toLocalDate("2024-07-29"), toLocalDate("2024-08-28")),
        new MonthlyPeriod(55, toLocalDate("2024-08-29"), toLocalDate("2024-09-28")),
        new MonthlyPeriod(56, toLocalDate("2024-09-29"), toLocalDate("2024-10-28")),
        new MonthlyPeriod(57, toLocalDate("2024-10-29"), toLocalDate("2024-11-28")),
        new MonthlyPeriod(58, toLocalDate("2024-11-29"), toLocalDate("2024-12-28")),
        new MonthlyPeriod(59, toLocalDate("2024-12-29"), toLocalDate("2025-01-28")),
        new MonthlyPeriod(60, toLocalDate("2025-01-29"), toLocalDate("2025-02-28"))
    );
  }

  static List<MonthlyPeriod> endOfTheMonth30() {
    // 起算日が月末(30日)、かつ月によって期間を定めた場合において最後の月に応答日がない
    return List.of(
        new MonthlyPeriod(1, toLocalDate("2020-04-30"), toLocalDate("2020-05-29")),
        new MonthlyPeriod(2, toLocalDate("2020-05-30"), toLocalDate("2020-06-29")),
        new MonthlyPeriod(3, toLocalDate("2020-06-30"), toLocalDate("2020-07-29")),
        new MonthlyPeriod(4, toLocalDate("2020-07-30"), toLocalDate("2020-08-29")),
        new MonthlyPeriod(5, toLocalDate("2020-08-30"), toLocalDate("2020-09-29")),
        new MonthlyPeriod(6, toLocalDate("2020-09-30"), toLocalDate("2020-10-29")),
        new MonthlyPeriod(7, toLocalDate("2020-10-30"), toLocalDate("2020-11-29")),
        new MonthlyPeriod(8, toLocalDate("2020-11-30"), toLocalDate("2020-12-29")),
        new MonthlyPeriod(9, toLocalDate("2020-12-30"), toLocalDate("2021-01-29")),
        new MonthlyPeriod(10, toLocalDate("2021-01-30"), toLocalDate("2021-02-28")),
        new MonthlyPeriod(11, toLocalDate("2021-03-01"), toLocalDate("2021-03-29")),
        new MonthlyPeriod(12, toLocalDate("2021-03-30"), toLocalDate("2021-04-29"))
    );
  }

  static List<MonthlyPeriod> endOfTheMonth31() {
    // 起算日が月末(31日)、かつ月によって期間を定めた場合において最後の月に応答日がない
    return List.of(
        new MonthlyPeriod(1, toLocalDate("2021-07-31"), toLocalDate("2021-08-30")),
        new MonthlyPeriod(2, toLocalDate("2021-08-31"), toLocalDate("2021-09-30")),
        new MonthlyPeriod(3, toLocalDate("2021-10-01"), toLocalDate("2021-10-30")),
        new MonthlyPeriod(4, toLocalDate("2021-10-31"), toLocalDate("2021-11-30")),
        new MonthlyPeriod(5, toLocalDate("2021-12-01"), toLocalDate("2021-12-30")),
        new MonthlyPeriod(6, toLocalDate("2021-12-31"), toLocalDate("2022-01-30")),
        new MonthlyPeriod(7, toLocalDate("2022-01-31"), toLocalDate("2022-02-28")),
        new MonthlyPeriod(8, toLocalDate("2022-03-01"), toLocalDate("2022-03-30")),
        new MonthlyPeriod(9, toLocalDate("2022-03-31"), toLocalDate("2022-04-30")),
        new MonthlyPeriod(10, toLocalDate("2022-05-01"), toLocalDate("2022-05-30")),
        new MonthlyPeriod(11, toLocalDate("2022-05-31"), toLocalDate("2022-06-30")),
        new MonthlyPeriod(12, toLocalDate("2022-07-01"), toLocalDate("2022-07-30")),
        new MonthlyPeriod(13, toLocalDate("2022-07-31"), toLocalDate("2022-08-30")),
        new MonthlyPeriod(14, toLocalDate("2022-08-31"), toLocalDate("2022-09-30")),
        new MonthlyPeriod(15, toLocalDate("2022-10-01"), toLocalDate("2022-10-30")),
        new MonthlyPeriod(16, toLocalDate("2022-10-31"), toLocalDate("2022-11-30"))
    );
  }

  static List<MonthlyPeriod> beforeTheEndOfTheMonth() {
    // 起算日が月末でない(30日)、かつ月によって期間を定めた場合において最後の月に応答日がない
    return List.of(
        new MonthlyPeriod(1, toLocalDate("2021-01-30"), toLocalDate("2021-02-28")),
        new MonthlyPeriod(2, toLocalDate("2021-03-01"), toLocalDate("2021-03-29")),
        new MonthlyPeriod(3, toLocalDate("2021-03-30"), toLocalDate("2021-04-29")),
        new MonthlyPeriod(4, toLocalDate("2021-04-30"), toLocalDate("2021-05-29")),
        new MonthlyPeriod(5, toLocalDate("2021-05-30"), toLocalDate("2021-06-29")),
        new MonthlyPeriod(6, toLocalDate("2021-06-30"), toLocalDate("2021-07-29")),
        new MonthlyPeriod(7, toLocalDate("2021-07-30"), toLocalDate("2021-08-29")),
        new MonthlyPeriod(8, toLocalDate("2021-08-30"), toLocalDate("2021-09-29")),
        new MonthlyPeriod(9, toLocalDate("2021-09-30"), toLocalDate("2021-10-29")),
        new MonthlyPeriod(10, toLocalDate("2021-10-30"), toLocalDate("2021-11-29")),
        new MonthlyPeriod(11, toLocalDate("2021-11-30"), toLocalDate("2021-12-29")),
        new MonthlyPeriod(12, toLocalDate("2021-12-30"), toLocalDate("2022-01-29"))
    );
  }

  private static LocalDate toLocalDate(String localDateStr) {

    return LocalDate.parse(localDateStr, formatter);
  }
}
