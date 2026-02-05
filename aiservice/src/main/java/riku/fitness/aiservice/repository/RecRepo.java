package riku.fitness.aiservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import riku.fitness.aiservice.model.Recommendation;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecRepo extends MongoRepository<Recommendation, String> {
    Optional<Recommendation> findByActivityId(String actId);

    List<Recommendation> findByUserId(String userId);
}
