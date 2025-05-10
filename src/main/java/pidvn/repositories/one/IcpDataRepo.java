package pidvn.repositories.one;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pidvn.entities.one.IcpData;

@Repository
public interface IcpDataRepo extends JpaRepository<IcpData, Integer> {
}
