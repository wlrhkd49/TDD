package chap3;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpiryDateCalculator {

    public LocalDate calculateExpiryDate(PayData payData) {
        int addedMonths = payData.getPayAmount() / 10000 == 10 ? 12 : payData.getPayAmount() / 10000;
        if(payData.getFirstBillingDate()!=null) {
            return expiryDateUsingFirstBuillingDate(payData, addedMonths);
        }
        return payData.getBillingDate().plusMonths(addedMonths);
    }

    private LocalDate expiryDateUsingFirstBuillingDate(PayData payData, int addedMonths) {
        // 후보 만료일을 정함
        LocalDate candidateExp = payData.getBillingDate().plusMonths(addedMonths);
        final int dayOfFirstBilling = payData.getFirstBillingDate().getDayOfMonth();
        // 첫 납부일의 일자와 후보 만료일의 일자가 다르면
        if(dayOfFirstBilling != candidateExp.getDayOfMonth()) {
            final int dayLenOfCandiMon = YearMonth.from(candidateExp).lengthOfMonth();
            if(dayLenOfCandiMon < dayOfFirstBilling) {
                return candidateExp.withDayOfMonth(dayLenOfCandiMon);
            }
            // 첫 납부일의 일자를 후보 만료일의 일자로 사용
            return candidateExp.withDayOfMonth(
                    dayOfFirstBilling
            );
        } else {
            return candidateExp;
        }
    }
}
