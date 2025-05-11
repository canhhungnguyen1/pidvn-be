package pidvn.repositories.one;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pidvn.entities.one.IsDeviceTransaction;

@Repository
public interface IsDeviceTransactionRepo extends JpaRepository<IsDeviceTransaction, Integer> {
}
