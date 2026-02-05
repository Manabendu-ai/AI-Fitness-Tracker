package riku.fitness.aiservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import riku.fitness.aiservice.model.Activity;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final AiActivityService aiService;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "activity-processer-group")
    public void processActivity(Activity activity){
        log.info("Recieved Activity for processing {}", activity.getUserId());
        aiService.generateRecommendation(activity);
    }
}
