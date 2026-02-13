package riku.fitness.aiservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import riku.fitness.aiservice.model.Activity;
import riku.fitness.aiservice.model.Recommendation;
import riku.fitness.aiservice.repository.RecRepo;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final AiActivityService aiService;
    private final RecRepo repo;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "activity-processer-group")
    public void processActivity(Activity activity){
        try {
            log.info("Recieved Activity for processing {}", activity.getUserId());
            Recommendation recommendation = aiService.generateRecommendation(activity);
            repo.save(recommendation);
        }catch (Exception e){
            log.error("Error processing activity {}", activity.getId(), e);
        }
    }
}
