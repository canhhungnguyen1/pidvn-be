package pidvn.repositories.one;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pidvn.entities.one.MeaModel;

@Repository
public interface MeaModelRepo extends JpaRepository<MeaModel, Integer> {
}
