package pidvn.repositories.one;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pidvn.entities.one.IsDeviceLocation;

@Repository
public interface IsDeviceLocationRepo extends JpaRepository<IsDeviceLocation, Integer> {
}
