package chapter1.리팩토링_실습.code.data;

public class Play {
    private String name;
    private String type; /* ex. tragedy, comedy */

    public Play(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
