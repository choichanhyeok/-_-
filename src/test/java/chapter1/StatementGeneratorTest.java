package chapter1;

import chapter1.리팩토링_실습.code.Theater;
import chapter1.리팩토링_실습.code.data.Invoice;
import chapter1.리팩토링_실습.code.data.Performance;
import chapter1.리팩토링_실습.code.data.Play;
import chapter1.리팩토링_실습.code.data.Plays;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatementGeneratorTest {

    @Test
    void statementTest() throws Exception {


        /* given */
        // Invoice 준비
        List<Performance> performances = new ArrayList<>();
        performances.add(new Performance("hamlet", 55));
        performances.add(new Performance("as-like", 35));
        Invoice invoice = new Invoice("BigCo", performances);

        // plays 준비
        Map<String, Play> playMap = new HashMap<>();
        playMap.put("hamlet", new Play("Hamlet", "tragedy"));
        playMap.put("as-like", new Play("As You Like It", "comedy"));
        playMap.put("othello", new Play("Othello", "tragedy"));
        Plays plays = new Plays(playMap);


        /* when */
        Theater theater = new Theater();


        /* then */
        String actual = theater.statement(invoice, plays);

        String expected =
                "청구 내역 (고객명: BigCo)\n" +
                        " Hamlet: $650.00 (55석)\n" +
                        " As You Like It: $580.00 (35석)\n" +
                        "총액: $1,230.00\n" +
                        "적립 포인트: 37점\n";
        assertEquals(expected, actual);
    }
}