package umc.spring.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {

    Optional<Region> findByName(String name);

}
