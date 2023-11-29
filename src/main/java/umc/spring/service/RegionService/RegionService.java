package umc.spring.service.RegionService;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.spring.domain.Region;
import umc.spring.repository.RegionRepository;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    public Region findRegionByNameElseBuild(String region) {

        Optional<Region> optionalRegion = regionRepository.findByName(region);

        return optionalRegion.orElseGet(() -> {
            Region newRegion = Region.builder().name(region).build();
            regionRepository.save(newRegion);
            return newRegion;
        });
    }
}
