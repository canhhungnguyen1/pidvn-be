package pidvn.repositories.one;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pidvn.entities.one.IsDeviceInventoryData;

@Repository
public interface IsDeviceInventoryDataRepo extends JpaRepository<IsDeviceInventoryData, Integer> {
}
