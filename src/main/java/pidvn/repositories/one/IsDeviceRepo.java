package pidvn.repositories.one;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pidvn.entities.one.IsDevice;

@Repository
public interface IsDeviceRepo extends JpaRepository<IsDevice, Integer> {

}
