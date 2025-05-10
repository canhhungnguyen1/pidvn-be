package pidvn.repositories.one;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pidvn.entities.one.QaMaterialCheckSheetRecord;

import java.util.List;

@Repository
public interface QaMaterialCheckSheetRecordRepo extends JpaRepository<QaMaterialCheckSheetRecord, Integer> {

    List<QaMaterialCheckSheetRecord> findByQaCardOrderByIdDesc(String qaCard);


}
