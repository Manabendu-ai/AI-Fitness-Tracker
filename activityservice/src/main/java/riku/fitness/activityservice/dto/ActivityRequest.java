package riku.fitness.activityservice.dto;

import lombok.Data;
import riku.fitness.activityservice.models.ActivityType;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ActivityRequest {
    private String userId;
    private ActivityType type;
    private Integer duration;
    private Integer calBurned;
    private LocalDateTime startTime;
    private Map<String, Object> additionalMetrics;
}
