package riku.fitness.activityservice.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import riku.fitness.activityservice.dto.ActivityRequest;
import riku.fitness.activityservice.dto.ActivityResponse;
import riku.fitness.activityservice.services.ActivityService;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@AllArgsConstructor
public class ActivityController {
    private ActivityService service;

    @PostMapping("/add")
    public ResponseEntity<ActivityResponse> trackActivity(
            @RequestBody ActivityRequest req
    ){
        return ResponseEntity.ok(service.trackActivity(req));
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<List<ActivityResponse>> getActivityByUserId(
            @PathVariable String user_id
    ){
        return ResponseEntity.ok(service.getActByUserId(user_id));
    }
}
