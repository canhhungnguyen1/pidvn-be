package pidvn.repositories.one;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pidvn.entities.one.IsDeviceInventoryRequest;

@Repository
public interface IsDeviceInventoryRequestRepo extends JpaRepository<IsDeviceInventoryRequest, Integer> {
}
