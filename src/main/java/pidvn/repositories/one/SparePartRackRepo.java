package pidvn.repositories.one;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pidvn.entities.one.SparePartRack;

@Repository
public interface SparePartRackRepo extends JpaRepository<SparePartRack, Integer> {
}
