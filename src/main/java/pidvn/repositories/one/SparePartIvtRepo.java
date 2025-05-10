package pidvn.repositories.one;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pidvn.entities.one.SparePartIvt;

@Repository
public interface SparePartIvtRepo extends JpaRepository<SparePartIvt, Integer> {

}
