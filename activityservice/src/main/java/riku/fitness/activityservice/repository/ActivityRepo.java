package riku.fitness.activityservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import riku.fitness.activityservice.models.Activity;

import java.util.List;

@Repository
public interface ActivityRepo extends MongoRepository<Activity, String> {
    List<Activity> getActivityByUserId(String userId);
}
