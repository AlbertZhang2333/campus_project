package com.example.ooadgroupproject.entity;

public enum ReservationState {
    NotChecked(0),
    Canceled(1),
    ArriveLate(2),
    Checked(3);

    private final int state;

    ReservationState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    // 将数字和状态对应，可以通过数字来获得对应的状态
    public static ReservationState getByCode(int stateCode) {
        for (ReservationState s : ReservationState.values()) {
            if (s.state == stateCode) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid ReservationState code: " + stateCode);
    }
}
