package chapter1.리팩토링_실습.code.data;


import java.util.Map;

/*
 * 여러개의 Play 클래스를 관리할 래퍼 클래스
 */
public class Plays {
    private Map<String, Play> playMap;

    public Plays(Map<String, Play> playMap) {
        this.playMap = playMap;
    }

    public Play getPlayMap(String playId) {
        return playMap.get(playId);
    }

}
