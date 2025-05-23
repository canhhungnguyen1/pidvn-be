package pidvn.repositories.one;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pidvn.entities.one.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
}
