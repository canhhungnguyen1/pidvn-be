package pidvn.repositories.one;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pidvn.entities.one.SparePart;

import java.util.List;
import java.util.Map;

@Repository
public interface SparePartRepo extends JpaRepository<SparePart, Integer> {
    @Query(value =
            " select A.*  from 																																																					" +
                    " (select A.*, B.date_out, B.sum_qty_out, (A.tondau + A.sum_qty_in - B.sum_qty_out) as tonkho from                                                                                                                                  " +
                    " (select A.code, A.name, A.rack_ivt, LEFT(A.rack_ivt , 2) as rack, SUBSTRING(A.rack_ivt, 4, 1) AS text_rack, A.qty as tondau, A.date_time as date_ivt, A.date_in,                                                                  " +
                    " case                                                                                                                                                                                                                              " +
                    " 	when A.sum_qty_in is null then 0                                                                                                                                                                                                " +
                    " 	else A.sum_qty_in                                                                                                                                                                                                               " +
                    " end as sum_qty_in from                                                                                                                                                                                                            " +
                    " (select A.code, A.name, A.rack_ivt, A.qty, A.date_time, A.date_in, sum(qty_in) as sum_qty_in from                                                                                                                                 " +
                    " (select A.*, B.date date_in, B.qty qty_in from (select A.* from                                                                                                                                                                   " +
                    " (select A.galileo_name code , A.part_number name, B.qty,B.rack_type as rack_ivt, B.date_time, ROW_NUMBER() OVER (PARTITION BY A.galileo_name ORDER BY B.date_time DESC) as RN  from spare_parts A, pidvn_tooljig_inventory B      " +
                    " where A.galileo_name = B.code) A where RN = 1) A                                                                                                                                                                                  " +
                    " left join (select * from spare_part_records A where A.`type` = 'INPUT' ) B                                                                                                                                                        " +
                    " on A.name = B.part_number                                                                                                                                                                                                         " +
                    " and A.date_time < B.`date`) A  group by code) A) A,                                                                                                                                                                               " +
                    " (select A.code, A.rack_ivt, A.qty as tondau, A.date_time, A.date_out,                                                                                                                                                             " +
                    " case                                                                                                                                                                                                                              " +
                    " 	when A.sum_qty_out is null then 0                                                                                                                                                                                               " +
                    " 	else A.sum_qty_out                                                                                                                                                                                                              " +
                    " end as sum_qty_out from                                                                                                                                                                                                           " +
                    " (select A.code, A.name, A.rack_ivt, A.qty, A.date_time, A.date_out, sum(qty_out) as sum_qty_out from                                                                                                                              " +
                    " (select A.*, B.date date_out, B.qty qty_out from (select A.* from                                                                                                                                                                 " +
                    " (select A.galileo_name code , A.part_number name, B.qty,B.rack_type as rack_ivt, B.date_time, ROW_NUMBER() OVER (PARTITION BY A.galileo_name ORDER BY B.date_time DESC) as RN  from spare_parts A, pidvn_tooljig_inventory B      " +
                    " where A.galileo_name = B.code) A where RN = 1) A                                                                                                                                                                                  " +
                    " left join (select * from spare_part_records A where A.`type` = 'OUTPUT' ) B                                                                                                                                                       " +
                    " on A.name = B.part_number                                                                                                                                                                                                         " +
                    " and A.date_time < B.`date`) A  group by code) A) B where A.code = B.code ) A where A.name like '%TD-05005-01407%' 																												"
            ,nativeQuery = true)
    List<Map<String, Object>> findAllrackivt();

    @Query(value =
            " select A.*, B.name as people_name from pidvn_tooljig_inventory A, users B where A.username = B.username and A.ivt_no = :ivtNo"
            ,nativeQuery = true)
    List<Map<String, Object>> getSparePartByIvtNo(String ivtNo);

    List<SparePart> findAllByOrderByIdDesc();
    SparePart findByPartNumber(String partNumber);
    SparePart findByGalileoName(String galileoName);
}
