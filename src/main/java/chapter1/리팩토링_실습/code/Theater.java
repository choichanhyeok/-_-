package chapter1.리팩토링_실습.code;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Theater {
    private final Map<String, Map<String, Object>> plays;
    private final Map<String, Object> invoice;

    public Theater(Map<String, Map<String, Object>> plays, Map<String, Object> invoice) {
        this.plays = plays;
        this.invoice = invoice;
    }

    public String statement(Map<String, Object> invoice) throws Exception {

        int totalAmount = 0;

        String result = "청구 내역 (고객명: " + invoice.get("customer") + ")\n";


        for (Map<String, Object> perf : (List<Map<String, Object>>) invoice.get("performances")) {

            if (playFor(perf) == null) {
                throw new Exception("알 수 없는 playID: " + (String) perf.get("playID"));
            }

            result += " " + (String) playFor(perf).get("name") + ": "
                    + usd(amountFor(perf))
                    + " (" + (int) perf.get("audience") + "석)\n";

        }

        totalAmount = appleSauce();

        result += "총액: " + usd(totalAmount) + "\n";
        result += "적립 포인트: " + totalVolumeCredits() + "점\n";

        return result;
    }

    private int appleSauce() throws Exception {
        int totalAmount = 0;

        for (Map<String, Object> perf : (List<Map<String, Object>>) invoice.get("performances")) {
            totalAmount += amountFor(perf);
        }

        return totalAmount;
    }


    private int totalVolumeCredits() {
        int volumeCredits = 0;
        for (Map<String, Object> perf : (List<Map<String, Object>>) invoice.get("performances")) {

            volumeCredits += volumeCreditsFor(perf);
        }

        return volumeCredits;
    }

    private int volumeCreditsFor(Map<String, Object> aPerformance) {
        int volumeCredits = 0;
        volumeCredits += Math.max((int) aPerformance.get("audience") - 30, 0);

        if ("comedy".equals(playFor(aPerformance).get("type"))) {
            volumeCredits += Math.floor((int) aPerformance.get("audience") / 5.0);
        }

        return volumeCredits;
    }


    private int amountFor(Map<String, Object> aPerformance) throws Exception {
        int result = 0;
        switch ((String) playFor(aPerformance).get("type")) {

            case "tragedy":
                result = 40000; // 비극 기본 요금
                if ((int) aPerformance.get("audience") > 30) {
                    // 초과 인원당 1,000원
                    result += 1000 * ((int) aPerformance.get("audience") - 30);
                }
                break;

            case "comedy":
                result = 30000; // 희극 기본 요금
                if ((int) aPerformance.get("audience") > 20) {
                    result += 10000 + 500 * ((int) aPerformance.get("audience") - 20);
                }
                result += 300 * (int) aPerformance.get("audience");
                break;

            default:
                throw new Exception("알 수 없는 장르: " + playFor(aPerformance).get("type"));
        }

        return result;
    }

    private Map<String, Object> playFor(Map<String, Object> aPerformance) {

        return plays.get(aPerformance.get("playID"));
    }

    private String usd(int aNumber) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(aNumber / 100.0);
    }


}
