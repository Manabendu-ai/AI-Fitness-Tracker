package riku.fitness.aiservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import riku.fitness.aiservice.model.Recommendation;
import riku.fitness.aiservice.service.RecService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendations")
public class RecController {

    private final RecService service;

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<Recommendation>> getUserRecommendation(
            @PathVariable("user_id") String userId
    ){
        return ResponseEntity.ok(
                service.getUserRec(userId)
        );
    }

    @GetMapping("/activity/{act_id}")
    public ResponseEntity<Recommendation> getActivityRecommendation(
            @PathVariable("act_id") String actId
    ){
        return ResponseEntity.ok(
                service.getActRec(actId)
        );
    }
}
