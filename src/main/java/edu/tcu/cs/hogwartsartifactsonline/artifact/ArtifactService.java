package edu.tcu.cs.hogwartsartifactsonline.artifact;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.hogwartsartifactsonline.artifact.dto.ArtifactDto;
import edu.tcu.cs.hogwartsartifactsonline.artifact.utils.IdWorker;
import edu.tcu.cs.hogwartsartifactsonline.system.exception.ObjectNotFoundException;
import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ArtifactService {

    private final ArtifactRepository artifactRepository;



    public ArtifactService(ArtifactRepository artifactRepository) {
        this.artifactRepository = artifactRepository;
    }

    @Observed(name = "artifact", contextualName = "findByIdService")
    public Artifact findById(String artifactId) {
        return this.artifactRepository.findById(artifactId)
                .orElseThrow(() -> new ObjectNotFoundException("artifact", artifactId));
    }

    @Timed("findAllArtifactsService.time")
    public List<Artifact> findAll() {
        return this.artifactRepository.findAll();
    }

    public Artifact save(Artifact newArtifact) {
        return this.artifactRepository.save(newArtifact);
    }

    public Artifact update(String artifactId, Artifact update) {
        return this.artifactRepository.findById(artifactId)
                .map(oldArtifact -> {
                    oldArtifact.setName(update.getName());
                    oldArtifact.setDescription(update.getDescription());
                    return this.artifactRepository.save(oldArtifact);
                })
                .orElseThrow(() -> new ObjectNotFoundException("artifact", artifactId));
    }

    public void delete(String artifactId) {
        this.artifactRepository.findById(artifactId)
                .orElseThrow(() -> new ObjectNotFoundException("artifact", artifactId));
        this.artifactRepository.deleteById(artifactId);
    }

    /**
     * Returns a summary of the existing artifacts. This method is responsible for preparing the AiChatRequest and parsing the AiChatResponse.
     *
     * @param artifactDtos a list of artifact dtos to be summarized
     * @return a summary of the existing artifacts
     * @throws JsonProcessingException
     */


}