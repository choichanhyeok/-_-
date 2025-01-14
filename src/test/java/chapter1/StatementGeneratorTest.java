package chapter1;

import chapter1.리팩토링_실습.code.Theater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatementGeneratorTest {

    public static void main(String[] args) {

        /* invoice 세팅하기 */
        Map<String, Object> invoice = new HashMap<>();
        invoice.put("customer", "BigCo");

        List<Map<String, Object>> performances = new ArrayList<>();
        Map<String, Object> perf1 = new HashMap<>();
        perf1.put("playID", "hamlet");
        perf1.put("audience", 55);
        performances.add(perf1);

        Map<String, Object> perf2 = new HashMap<>();
        perf2.put("playID", "as-like");
        perf2.put("audience", 35);
        performances.add(perf2);

        invoice.put("performances", performances);

        /* plays 세팅하기 */
        Map<String, Map<String, Object>> plays = new HashMap<>();
        Map<String, Object> hamlet = new HashMap<>();
        hamlet.put("name", "Hamlet");
        hamlet.put("type", "tragedy");
        plays.put("hamlet", hamlet);

        Map<String, Object> asLike = new HashMap<>();
        asLike.put("name", "As You Like It");
        asLike.put("type", "comedy");
        plays.put("as-like", asLike);

        Map<String, Object> othello = new HashMap<>();
        othello.put("name", "Othello");
        othello.put("type", "tragedy");
        plays.put("othello", othello);

        Theater theater = new Theater(plays, invoice);

        String actual = null;
        try {
            actual = theater.statement();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(actual);


        String expected =
                "청구 내역 (고객명: BigCo)\n" +
                        " Hamlet: $650.00 (55석)\n" +
                        " As You Like It: $580.00 (35석)\n" +
                        "총액: $1,230.00\n" +
                        "적립 포인트: 37점\n";  // 혹은 47점, 로직에 따라 수정

        assertEquals(expected, actual);
    }
}