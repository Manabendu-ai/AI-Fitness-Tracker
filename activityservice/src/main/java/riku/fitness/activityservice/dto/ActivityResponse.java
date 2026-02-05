package riku.fitness.activityservice.dto;

import lombok.Builder;
import lombok.Data;
import riku.fitness.activityservice.models.ActivityType;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ActivityResponse {
    private String id;
    private String userId;
    private ActivityType type;
    private Integer duration;
    private Integer calBurned;
    private LocalDateTime startTime;
    private Map<String, Object> additionalMetrics;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
