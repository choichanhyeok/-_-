package chapter1.리팩토링_실습.code;

import chapter1.리팩토링_실습.code.data.Invoice;
import chapter1.리팩토링_실습.code.data.Performance;
import chapter1.리팩토링_실습.code.data.Play;
import chapter1.리팩토링_실습.code.data.Plays;
import chapter1.리팩토링_실습.code.dto.StatementData;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Theater {

    public String statement(Invoice invoice, Plays plays) throws Exception {
        StatementData statementData = new StatementData(invoice.getPerformances(), invoice.getCustomer());

        return renderPlainText(statementData, invoice, plays);
    }

    private String renderPlainText(StatementData data, Invoice invoice, Plays plays) throws Exception {
        String result = "청구 내역 (고객명: " + data.getCustomer() + ")\n";


        for (Performance perf : data.getPerformances()) {
            Play play = plays.getPlayMap(perf.getPlayId());

            result += " "
                    + play.getName()
                    + ": "
                    + usd(amountFor(perf, play))
                    + " (" + perf.getAudience()
                    + "석)\n";

        }

        result += "총액: "
                + usd(totalAmount(invoice, plays))
                + "\n";
        result += "적립 포인트: "
                + totalVolumeCredits(invoice, plays)
                + "점\n";

        return result;
    }

    private int totalAmount(Invoice invoice, Plays plays) throws Exception {
        int totalAmount = 0;

        for (Performance perf: invoice.getPerformances()) {
            totalAmount += amountFor(perf, plays.getPlayMap(perf.getPlayId()));
        }

        return totalAmount;
    }


    private int totalVolumeCredits(Invoice invoice, Plays plays) {
        int result = 0;
        for (Performance perf : invoice.getPerformances()) {

            result += volumeCreditsFor(perf, plays.getPlayMap(perf.getPlayId()));
        }

        return result;
    }

    private int volumeCreditsFor(Performance aPerformance, Play play) {
        int volumeCredits = 0;
        volumeCredits += Math.max(aPerformance.getAudience() - 30, 0);

        if ("comedy".equals(play.getType())) {
            volumeCredits += Math.floor(aPerformance.getAudience() / 5.0);
        }

        return volumeCredits;
    }


    private int amountFor(Performance aPerformance, Play play) throws Exception {
        int result = 0;
        switch (play.getType()) {

            case "tragedy":
                result = 40000; // 비극 기본 요금
                if (aPerformance.getAudience() > 30) {
                    // 초과 인원당 1,000원
                    result += 1000 * (aPerformance.getAudience() - 30);
                }
                break;

            case "comedy":
                result = 30000; // 희극 기본 요금
                if (aPerformance.getAudience() > 20) {
                    result += 10000 + 500 * (aPerformance.getAudience() - 20);
                }
                result += 300 * aPerformance.getAudience();
                break;

            default:
                throw new Exception("알 수 없는 장르: " + play.getType());
        }

        return result;
    }

    private String playFor(Performance aPerformance) {

        return aPerformance.getPlayId();
    }

    private String usd(int aNumber) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(aNumber / 100.0);
    }


}
