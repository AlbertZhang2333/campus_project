package com.example.ooadgroupproject.entity.mapResponse;

import lombok.Data;

import java.util.List;

@Data
public class WalkingNavigation {
    private String status;
    private String info;
    private String infocode;
    private String count;
    private Route route;
    @Data
    public static class Route{
        private String origin;
        private String destination;
        private List<Path> paths;
        @Data
        public static class Path{
            private String distance;
            private String duration;
            private List<Step> steps;
            @Data
            public static class Step{
                private String instruction;
                private String orientation;
                private String road;
                private String distance;
                private String duration;
                private String polyline;
                private String action;
                private String assistant_action;
                private String walk_type;
            }
        }
    }
}



