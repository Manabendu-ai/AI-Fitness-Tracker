package riku.fitness.activityservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import riku.fitness.activityservice.dto.ActivityRequest;
import riku.fitness.activityservice.dto.ActivityResponse;
import riku.fitness.activityservice.models.Activity;
import riku.fitness.activityservice.repository.ActivityRepo;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepo repo;
    private final UserValidationService userVAL;
    private final KafkaTemplate<String, Activity> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

    public ActivityResponse trackActivity(ActivityRequest req) {
        boolean isValid = userVAL.validateUser(req.getUserId());
        if(!isValid){
            throw new RuntimeException("Invalid User: "+req.getUserId());
        }
        Activity act = repo.save(reqToACT(req));

        try {
            kafkaTemplate.send(topicName, act.getUserId(), act);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return actToRES(act);
    }

    private ActivityResponse actToRES(Activity act) {
        return ActivityResponse.builder()
                .id(act.getId())
                .type(act.getType())
                .userId(act.getUserId())
                .duration(act.getDuration())
                .startTime(act.getStartTime())
                .calBurned(act.getCalBurned())
                .additionalMetrics(act.getAdditionalMetrics())
                .createdAt(act.getCreatedAt())
                .updatedAt(act.getUpdatedAt())
                .build();
    }

    private Activity reqToACT(ActivityRequest req){
        return Activity.builder()
                .userId(req.getUserId())
                .type(req.getType())
                .calBurned(req.getCalBurned())
                .startTime(req.getStartTime())
                .additionalMetrics(req.getAdditionalMetrics())
                .duration(req.getDuration())
                .build();
    }


    public List<ActivityResponse> getActByUserId(String userId) {
        List<Activity> acts = repo.getActivityByUserId(userId);
        List<ActivityResponse> responses = new ArrayList<>();
        acts.forEach(
                x -> responses.add(actToRES(x))
        );
        return responses;
    }
}
