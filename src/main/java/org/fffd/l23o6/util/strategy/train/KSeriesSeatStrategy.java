package org.fffd.l23o6.util.strategy.train;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.Nullable;

public class KSeriesSeatStrategy extends TrainSeatStrategy {
    public static final KSeriesSeatStrategy INSTANCE = new KSeriesSeatStrategy();

    private final Map<Integer, String> SOFT_SLEEPER_SEAT_MAP = new HashMap<>();
    private final Map<Integer, String> HARD_SLEEPER_SEAT_MAP = new HashMap<>();
    private final Map<Integer, String> SOFT_SEAT_MAP = new HashMap<>();
    private final Map<Integer, String> HARD_SEAT_MAP = new HashMap<>();

    private final Map<KSeriesSeatType, Map<Integer, String>> TYPE_MAP = new HashMap<>() {
        {
            put(KSeriesSeatType.SOFT_SLEEPER_SEAT, SOFT_SLEEPER_SEAT_MAP);
            put(KSeriesSeatType.HARD_SLEEPER_SEAT, HARD_SLEEPER_SEAT_MAP);
            put(KSeriesSeatType.SOFT_SEAT, SOFT_SEAT_MAP);
            put(KSeriesSeatType.HARD_SEAT, HARD_SEAT_MAP);
        }
    };

    private KSeriesSeatStrategy() {

        int counter = 0;

        for (String s : Arrays.asList("软卧1号上铺", "软卧2号下铺", "软卧3号上铺", "软卧4号上铺", "软卧5号上铺", "软卧6号下铺", "软卧7号上铺", "软卧8号上铺")) {
            SOFT_SLEEPER_SEAT_MAP.put(counter++, s);
        }

        for (String s : Arrays.asList("硬卧1号上铺", "硬卧2号中铺", "硬卧3号下铺", "硬卧4号上铺", "硬卧5号中铺", "硬卧6号下铺", "硬卧7号上铺", "硬卧8号中铺",
                "硬卧9号下铺", "硬卧10号上铺", "硬卧11号中铺", "硬卧12号下铺")) {
            HARD_SLEEPER_SEAT_MAP.put(counter++, s);
        }

        for (String s : Arrays.asList("1车1座", "1车2座", "1车3座", "1车4座", "1车5座", "1车6座", "1车7座", "1车8座", "2车1座", "2车2座",
                "2车3座", "2车4座", "2车5座", "2车6座", "2车7座", "2车8座")) {
            SOFT_SEAT_MAP.put(counter++, s);
        }

        for (String s : Arrays.asList("3车1座", "3车2座", "3车3座", "3车4座", "3车5座", "3车6座", "3车7座", "3车8座", "3车9座", "3车10座",
                "4车1座", "4车2座", "4车3座", "4车4座", "4车5座", "4车6座", "4车7座", "4车8座", "4车9座", "4车10座")) {
            HARD_SEAT_MAP.put(counter++, s);
        }
    }

    public enum KSeriesSeatType implements SeatType {
        SOFT_SLEEPER_SEAT("软卧"), HARD_SLEEPER_SEAT("硬卧"), SOFT_SEAT("软座"), HARD_SEAT("硬座"), NO_SEAT("无座");

        private String text;

        KSeriesSeatType(String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }

        public static KSeriesSeatType fromString(String text) {
            for (KSeriesSeatType b : KSeriesSeatType.values()) {
                if (b.text.equalsIgnoreCase(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    public void returnSeat(int startStationIndex,int endStationIndex,String seatType,String seat, boolean[][] seatMap){
        //true 代表已经占用
        int i1 = 0;
        int i2 = SOFT_SLEEPER_SEAT_MAP.size();
        int i3 = i2 + HARD_SLEEPER_SEAT_MAP.size();
        int i4 = i3 + SOFT_SEAT_MAP.size();
        switch (seatType) {
            case "软卧" -> {
                for(int i = i1;i < i2;i++){
                    if(seat.equals(SOFT_SLEEPER_SEAT_MAP.get(i))) {
                        for (int j = startStationIndex; j < endStationIndex; j++) {
                            seatMap[j][i] = false;
                        }
                        break;
                    }
                }
            }
            case "硬卧" -> {
                for (int i = i2; i < i3; i++) {
                    if(seat.equals(HARD_SLEEPER_SEAT_MAP.get(i))) {
                        for (int j = startStationIndex; j < endStationIndex; j++) {
                            seatMap[j][i] = false;
                        }
                        break;
                    }
                }
            }
            case "软座" -> {
                for (int i = i3; i < i4; i++) {
                    if(seat.equals(SOFT_SEAT_MAP.get(i))) {
                        for (int j = startStationIndex; j < endStationIndex; j++) {
                            seatMap[j][i] = false;
                        }
                        break;
                    }
                }
            }
            case "硬座" -> {
                for (int i = i4; i < seatMap[0].length; i++) {
                    if(seat.equals(HARD_SEAT_MAP.get(i))) {
                        for (int j = startStationIndex; j < endStationIndex; j++) {
                            seatMap[j][i] = false;
                        }
                        break;
                    }
                }
            }
        }
    }
    public @Nullable String allocSeat(int startStationIndex, int endStationIndex, KSeriesSeatType type,
            boolean[][] seatMap) {
        // endStationIndex - 1 = upper bound
        boolean flag;
        int i1 = 0;
        int i2 = SOFT_SLEEPER_SEAT_MAP.size();
        int i3 = i2 + HARD_SLEEPER_SEAT_MAP.size();
        int i4 = i3 + SOFT_SEAT_MAP.size();
        switch (type) {
            case SOFT_SLEEPER_SEAT -> {
                for (int i = i1; i < i2; i++) {
                    flag = true;
                    for (int j = startStationIndex; j < endStationIndex; j++) {
                        if (seatMap[j][i]) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        for (int j = startStationIndex; j < endStationIndex; j++) {
                            seatMap[j][i] = true;
                        }
                        return SOFT_SLEEPER_SEAT_MAP.get(i);
                    }
                }
                return null;
            }
            case HARD_SLEEPER_SEAT -> {
                for (int i = i2; i < i3; i++) {
                    flag = true;
                    for (int j = startStationIndex; j < endStationIndex; j++) {
                        if (seatMap[j][i]) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        for (int j = startStationIndex; j < endStationIndex; j++) {
                            seatMap[j][i] = true;
                        }
                        return HARD_SLEEPER_SEAT_MAP.get(i);
                    }
                }
                return null;
            }
            case SOFT_SEAT -> {
                for (int i = i3; i < i4; i++) {
                    flag = true;
                    for (int j = startStationIndex; j < endStationIndex; j++) {
                        if (seatMap[j][i]) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        for (int j = startStationIndex; j < endStationIndex; j++) {
                            seatMap[j][i] = true;
                        }
                        return SOFT_SEAT_MAP.get(i);
                    }
                }
                return null;
            }
            case HARD_SEAT -> {
                for (int i = i4; i < seatMap[0].length; i++) {
                    flag = true;
                    for (int j = startStationIndex; j < endStationIndex; j++) {
                        if (seatMap[j][i]) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        for (int j = startStationIndex; j < endStationIndex; j++) {
                            seatMap[j][i] = true;
                        }
                        return HARD_SEAT_MAP.get(i);
                    }
                }
                return null;
            }
            default -> {
                return null;
            }
        }
    }

    public Map<KSeriesSeatType, Integer> getLeftSeatCount(int startStationIndex, int endStationIndex,
            boolean[][] seatMap) {
        return null;
    }

    public boolean[][] initSeatMap(int stationCount) {
        return new boolean[stationCount - 1][SOFT_SLEEPER_SEAT_MAP.size() + HARD_SLEEPER_SEAT_MAP.size()
                + SOFT_SEAT_MAP.size() + HARD_SEAT_MAP.size()];
    }
}
