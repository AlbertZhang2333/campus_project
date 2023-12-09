package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.entity.Building;
import com.example.ooadgroupproject.entity.BusRelation;
import com.example.ooadgroupproject.entity.BusStop;
import com.example.ooadgroupproject.entity.mapResponse.BusNavigation;
import com.example.ooadgroupproject.entity.mapResponse.IPLocation;
import com.example.ooadgroupproject.entity.mapResponse.WalkingNavigation;
import com.example.ooadgroupproject.service.BuildingService;
import com.example.ooadgroupproject.Utils.WebUtil;
import com.example.ooadgroupproject.service.BusRelationService;
import com.example.ooadgroupproject.service.BusStopService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class MapController {
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private BusStopService busStopService;
    @Autowired
    private BusRelationService busRelationService;
    private final String key = "86512bb0d38529de9188d2f78ab858cd";
    private final RestTemplate restTemplate;

    public MapController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping("/location")
    public IPLocation getLocation(HttpServletRequest request) {
        String ip = WebUtil.getIpAddress(request);
        String url = "https://restapi.amap.com/v3/ip?ip=" + ip + "&output=json&key=" + key;
        IPLocation ipLocation = this.restTemplate.getForObject(url, IPLocation.class);
        return ipLocation;
    }

    @GetMapping("/bus/{name}")
    public List<BusNavigation> busNavigation(@PathVariable String name, @RequestParam double lat, @RequestParam double lng) {
        BusStop startStop = this.busStopService.findNearBusStop(lat, lng);
        BusStop desStop = busStopService.findByName(this.buildingService.findByName(name).getBusStop());
        List<BusRelation> busRelation = this.busRelationService.findByStartEnd(startStop.getId(), desStop.getId());
        List<BusNavigation> navigations = new ArrayList<>();
        for(int i = 0; i < busRelation.size(); i++){
            BusNavigation cur = new BusNavigation();
            cur.setOriginStop(startStop.getName());
            cur.setDestinationStop(desStop.getName());
            cur.setLineId(busRelation.get(i).getLineId());
            cur.setDuration(busRelation.get(i).getTime());
            navigations.add(cur);
        }
        return navigations;
    }

    @GetMapping("/walking/{name}")
    public WalkingNavigation getWalking(HttpServletRequest request, @PathVariable String name) {
        String ip = WebUtil.getIpAddress(request);
        String url = "https://restapi.amap.com/v3/ip?ip=" + ip + "&output=json&key=" + key;
        IPLocation ipLocation = this.restTemplate.getForObject(url, IPLocation.class);
        String rectangle = null;
        if(ipLocation != null) rectangle = ipLocation.getRectangle();
        //异常处理error
        if(rectangle == null) return null;
        String[] locations = rectangle.split(";");
        double lat = (Double.parseDouble(locations[0].split(",")[0]) + Double.parseDouble(locations[1].split(",")[0])) / 2;
        double lng = (Double.parseDouble(locations[0].split(",")[1]) + Double.parseDouble(locations[1].split(",")[1])) / 2;
        String oriLocation = lng + "," + lat;
        Building destination = this.buildingService.findByName(name);
        String desLocation = destination.getLng() + "," + destination.getLat();
        String walkingUrl = "https://restapi.amap.com/v3/direction/walking?origin=" + oriLocation + "&destination=" + desLocation + "&key=" + key;
        WalkingNavigation route = this.restTemplate.getForObject(walkingUrl, WalkingNavigation.class);
        assert route != null;
        System.out.println(route.getCount());
        return route;
    }

    @GetMapping("/test/walking")
    public String getWalkingTest() {
        String url = "https://restapi.amap.com/v3/ip?ip=114.247.50.2&output=json&key=" + key;
        IPLocation ipLocation = this.restTemplate.getForObject(url, IPLocation.class);
        String ipString = this.restTemplate.getForObject(url, String.class);
        HashMap<String, String> ipMap = this.restTemplate.getForObject(url, HashMap.class);
        url = "https://restapi.amap.com/v3/direction/walking?origin=116.45925,39.910031&destination=116.587922,40.081577&output=json&key=" + key;
        WalkingNavigation walkingNavigation = this.restTemplate.getForObject(url, WalkingNavigation.class);
        String walkingString = this.restTemplate.getForObject(url, String.class);
        HashMap<String, String> walkingMap = this.restTemplate.getForObject(url, HashMap.class);
        System.out.println("walking");
        return null;
    }
}
