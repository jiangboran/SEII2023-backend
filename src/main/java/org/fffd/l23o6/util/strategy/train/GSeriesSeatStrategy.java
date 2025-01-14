package org.fffd.l23o6.util.strategy.train;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.Nullable;

public class GSeriesSeatStrategy extends TrainSeatStrategy {
    public static final GSeriesSeatStrategy INSTANCE = new GSeriesSeatStrategy();

    private final Map<Integer, String> BUSINESS_SEAT_MAP = new HashMap<>();
    private final Map<Integer, String> FIRST_CLASS_SEAT_MAP = new HashMap<>();
    private final Map<Integer, String> SECOND_CLASS_SEAT_MAP = new HashMap<>();

    private final Map<GSeriesSeatType, Map<Integer, String>> TYPE_MAP = new HashMap<>() {
        {
            put(GSeriesSeatType.BUSINESS_SEAT, BUSINESS_SEAT_MAP);
            put(GSeriesSeatType.FIRST_CLASS_SEAT, FIRST_CLASS_SEAT_MAP);
            put(GSeriesSeatType.SECOND_CLASS_SEAT, SECOND_CLASS_SEAT_MAP);
        }
    };

    private GSeriesSeatStrategy() {

        int counter = 0;

        for (String s : Arrays.asList("1车1A", "1车1C", "1车1F")) {
            BUSINESS_SEAT_MAP.put(counter++, s);
        }

        for (String s : Arrays.asList("2车1A", "2车1C", "2车1D", "2车1F", "2车2A", "2车2C", "2车2D", "2车2F", "3车1A", "3车1C",
                "3车1D", "3车1F")) {
            FIRST_CLASS_SEAT_MAP.put(counter++, s);
        }

        for (String s : Arrays.asList("4车1A", "4车1B", "4车1C", "4车1D", "4车2F", "4车2A", "4车2B", "4车2C", "4车2D", "4车2F",
                "4车3A", "4车3B", "4车3C", "4车3D", "4车3F")) {
            SECOND_CLASS_SEAT_MAP.put(counter++, s);
        }

    }

    public enum GSeriesSeatType implements SeatType {
        BUSINESS_SEAT("商务座"), FIRST_CLASS_SEAT("一等座"),
        SECOND_CLASS_SEAT("二等座"), NO_SEAT("无座");

        private String text;

        GSeriesSeatType(String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }

        public static GSeriesSeatType fromString(String text) {
            for (GSeriesSeatType b : GSeriesSeatType.values()) {
                if (b.text.equalsIgnoreCase(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    public @Nullable void returnSeat(int startStationIndex,int endStationIndex,String seatType,String seat,boolean[][] seatMap){
        if(seatType.equals("商务座")){
            for(int i = 0;i < BUSINESS_SEAT_MAP.size();i++){
                if(seat.equals(BUSINESS_SEAT_MAP.get(i))){
                    for (int j = startStationIndex; j < endStationIndex; j++) {
                        seatMap[j][i] = false;
                    }
                    break;
                }
            }
        }
        else if(seatType.equals("一等座")){
            for (int i = BUSINESS_SEAT_MAP.size(); i < BUSINESS_SEAT_MAP.size()
                    + FIRST_CLASS_SEAT_MAP.size(); i++) {
                if(seat.equals(FIRST_CLASS_SEAT_MAP.get(i))) {
                    for (int j = startStationIndex; j < endStationIndex; j++) {
                        seatMap[j][i] = false;
                    }
                    break;
                }
            }
        }
        else if(seatType.equals("二等座")){
            for (int i = BUSINESS_SEAT_MAP.size() + FIRST_CLASS_SEAT_MAP.size(); i < seatMap[0].length; i++) {
                if(seat.equals(SECOND_CLASS_SEAT_MAP.get(i))) {
                    for (int j = startStationIndex; j < endStationIndex; j++) {
                        seatMap[j][i] = false;
                    }
                    break;
                }
            }
        }
    }

    public @Nullable String allocSeat(int startStationIndex, int endStationIndex, GSeriesSeatType type,
            boolean[][] seatMap) {
        // endStationIndex - 1 = upper bound
        // TODO
        boolean flag;
        switch (type) {
            case BUSINESS_SEAT -> {
                for (int i = 0; i < BUSINESS_SEAT_MAP.size(); i++) {
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
                        return BUSINESS_SEAT_MAP.get(i);
                    }
                }
                return null;
            }
            case FIRST_CLASS_SEAT -> {
                for (int i = BUSINESS_SEAT_MAP.size(); i < BUSINESS_SEAT_MAP.size()
                        + FIRST_CLASS_SEAT_MAP.size(); i++) {
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
                        return FIRST_CLASS_SEAT_MAP.get(i);
                    }
                }
                return null;
            }
            case SECOND_CLASS_SEAT -> {
                for (int i = BUSINESS_SEAT_MAP.size() + FIRST_CLASS_SEAT_MAP.size(); i < seatMap[0].length; i++) {
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
                        return SECOND_CLASS_SEAT_MAP.get(i);
                    }
                }
                return null;
            }
            default -> {
                return null;
            }
        }
    }

    public Map<GSeriesSeatType, Integer> getLeftSeatCount(int startStationIndex, int endStationIndex,
            boolean[][] seatMap) {
        // TODO
        return null;
    }

    public boolean[][] initSeatMap(int stationCount) {
        return new boolean[stationCount - 1][BUSINESS_SEAT_MAP.size() + FIRST_CLASS_SEAT_MAP.size()
                + SECOND_CLASS_SEAT_MAP.size()];
    }
}
