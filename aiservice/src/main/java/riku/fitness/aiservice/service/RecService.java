package riku.fitness.aiservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import riku.fitness.aiservice.model.Recommendation;
import riku.fitness.aiservice.repository.RecRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecService {

    private final RecRepo repo;

    public List<Recommendation> getUserRec(String userId) {
        return repo.findByUserId(userId);
    }

    public Recommendation getActRec(String actId) {
        return repo.findByActivityId(actId).orElseThrow(
                ()->new RuntimeException("No Recommendation Found for this Activity!")
        );
    }
}
