package chapter1.리팩토링_실습.code.dto;

import chapter1.리팩토링_실습.code.data.Performance;

import java.util.List;

public class StatementData {
    private String customer;
    private List<Performance> performances;

    public StatementData(List<Performance> performances) {
        this.performances = performances;
    }

    public String getCustomer() {
        return customer;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

}
