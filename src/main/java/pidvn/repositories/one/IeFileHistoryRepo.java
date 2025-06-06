package pidvn.repositories.one;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pidvn.entities.one.IeFileHistory;

@Repository
public interface IeFileHistoryRepo extends JpaRepository<IeFileHistory, Integer> {
}
