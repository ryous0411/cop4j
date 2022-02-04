package org.cop4j;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;

public class Main {

  public static void main(String args[]) {

    var contracts = createTestData();

    // 本日からXヶ月前の日付を範囲指定で取得
    var triggerMonth = 14; // 契約開始日からXヶ月目
    var today = LocalDate.of(2022, 3, 1);
    var rangeOfContractDates = getDates(today, triggerMonth - 1, 4);

    // ↑で取得した日付範囲に契約開始日がある契約を取得。実際の実装ではここはSQLでの取得になるかな
    var targetContracts = rangeOfContractDates.stream()
        .flatMap(d -> contracts.stream()
            .filter(c -> c.contractStartDate.equals(d)))
        .collect(Collectors.toList());

    var actualTargetContracts = targetContracts.stream()
        .filter(c -> {
          // 契約開始日・終了日から月毎の契約開始日・終了日を算出
          var periods =
              new Period(c.contractStartDate, c.contactEndDate).getMonthlyPeriods();

          // 本日が契約開始日からXヶ月目、かつ本日の日付と月毎の契約開始日が等しい契約であれば対象となる
          return periods.stream()
              .filter(p -> p.getMonths() == triggerMonth)
              .filter(p -> p.getFrom().equals(today))
              .findAny()
              .isPresent();
        })
        .collect(Collectors.toList());

    actualTargetContracts.forEach(a -> System.out.println(a.id));
  }

  public static List<LocalDate> getDates(LocalDate mailSendDate, int monthsAgo, int daysAgo) {

    var referenceDate = mailSendDate.minusMonths(monthsAgo);

    return IntStream.range(0, daysAgo)
        .mapToObj(i -> referenceDate.minusDays(i))
        .collect(Collectors.toList());
  }

  public static List<Contract> createTestData() {

    return List.of(
        new Contract(1L, LocalDate.of(2021, 1, 28), LocalDate.of(2024, 1, 27)),
        new Contract(2L, LocalDate.of(2021, 1, 29), LocalDate.of(2024, 1, 28)),
        new Contract(3L, LocalDate.of(2021, 1, 30), LocalDate.of(2024, 1, 29)),
        new Contract(4L, LocalDate.of(2021, 1, 31), LocalDate.of(2024, 1, 30)),
        new Contract(5L, LocalDate.of(2021, 2, 1), LocalDate.of(2024, 1, 31)),
        new Contract(6L, LocalDate.of(2021, 2, 2), LocalDate.of(2024, 2, 1))
    );
  }

  @AllArgsConstructor
  public static class Contract {

    private final Long id;

    private final LocalDate contractStartDate;

    private final LocalDate contactEndDate;
  }
}
