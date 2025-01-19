package chapter1.리팩토링_실습.code.dto;

public class EnrichedPerformance {
    private final String playId;
    private final int audience;
    private final int amount;
    private final int volumeCredits;
    private final String playName;
    private final String playType;

    public EnrichedPerformance(String playId, int audience, int amount, int volumeCredits, String playName, String playType) {
        this.playId = playId;
        this.audience = audience;
        this.amount = amount;
        this.volumeCredits = volumeCredits;
        this.playName = playName;
        this.playType = playType;
    }

    public String getPlayId() {
        return playId;
    }

    public int getAudience() {
        return audience;
    }

    public int getAmount() {
        return amount;
    }

    public int getVolumeCredits() {
        return volumeCredits;
    }

    public String getPlayName() {
        return playName;
    }

    public String getPlayType() {
        return playType;
    }
}
