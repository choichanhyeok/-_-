package chapter1.리팩토링_실습.code.beforeRefactoring;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TheaterBack {

    public static String statement(Map<String, Object> invoice,
                                   Map<String, Map<String, Object>> plays) throws Exception {

        int totalAmount = 0;
        int volumeCredits = 0;

        String result = "청구 내역 (고객명: " + invoice.get("customer") + ")\n";
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);


        for (Map<String, Object> perf : (List<Map<String, Object>>) invoice.get("performances")) {

            Map<String, Object> play = plays.get((String) perf.get("playID"));
            if (play == null) {
                throw new Exception("알 수 없는 playID: " + (String) perf.get("playID"));
            }

            int thisAmount = 0;
            switch ((String) play.get("type")) {

                case "tragedy":
                    thisAmount = 40000; // 비극 기본 요금
                    if ((int) perf.get("audience") > 30) {
                        // 초과 인원당 1,000원
                        thisAmount += 1000 * ((int) perf.get("audience") - 30);
                    }
                    break;

                case "comedy":
                    thisAmount = 30000; // 희극 기본 요금
                    if ((int) perf.get("audience") > 20) {
                        thisAmount += 10000 + 500 * ((int) perf.get("audience") - 20);
                    }
                    thisAmount += 300 * (int) perf.get("audience");
                    break;

                default:
                    throw new Exception("알 수 없는 장르: " + play.get("type"));
            }

            volumeCredits += Math.max((int) perf.get("audience") - 30, 0);

            if ("comedy".equals(play.get("type"))) {
                volumeCredits += Math.floor((int) perf.get("audience") / 5.0);
            }

            result += " " + (String) play.get("name") + ": "
                    + format.format(thisAmount / 100.0)
                    + " (" + (int) perf.get("audience") + "석)\n";

            totalAmount += thisAmount;
        }

        result += "총액: " + format.format(totalAmount / 100.0) + "\n";
        result += "적립 포인트: " + volumeCredits + "점\n";

        return result;
    }
}
