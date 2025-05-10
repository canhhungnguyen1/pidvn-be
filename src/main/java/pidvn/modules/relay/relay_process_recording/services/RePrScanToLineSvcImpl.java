package pidvn.modules.relay.relay_process_recording.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pidvn.entities.one.Lots;
import pidvn.entities.one.MaterialControls;
import pidvn.mappers.one.relay.relay_process_recording.RePrScanToLineMapper;
import pidvn.modules.relay.relay_process_recording.models.LotDto;
import pidvn.modules.relay.relay_process_recording.models.MaterialControlDto;
import pidvn.repositories.one.LotsRepo;
import pidvn.repositories.one.MaterialControlsRepo;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RePrScanToLineSvcImpl implements RePrScanToLineSvc {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RePrScanToLineMapper rePrScanToLineMapper;

    @Autowired
    private LotsRepo lotRepo;

    @Autowired
    private MaterialControlsRepo materialControlsRepo;

    @Override
    public Map<String, Object> scanMaterial(LotDto lotDto) {

        Map<String, Object> result = new HashMap<>();

        String message = null;
        String information = null;


        Lots lot = this.lotRepo.findByLotNo(lotDto.getLotNo());


        // kiểm tra LotNo có tồn tại
        if (lot == null) {
            information = "Không cho phép nhập NVL vào Line";
            message = MessageFormat.format("Lot: {0} không tồn tại trong hệ thống", lotDto.getLotNo());

            result.put("status", "ERROR");
            result.put("information", information);
            result.put("message", message);
            result.put("data", null);
            return result;

        }



        /**
         * Kiểm tra đã nhận đủ NVL chưa
         * Nếu phiếu chưa nhận đủ NVL thì bắt buộc phải nhận đủ
         */
        LotDto data = this.rePrScanToLineMapper.getLotRequestAndLotReceive(lotDto.getLotNo());
        if (data == null) {
            information = "Không cho phép nhập NVL vào Line";
            message = MessageFormat.format("Lot: {0} chưa được xuất kho",lotDto.getLotNo());

            result.put("status", "ERROR");
            result.put("information", information);
            result.put("message", message);
            result.put("data", null);
            return result;
        }

        if ( data.getTotalLotReceive() < data.getTotalLotRequest()) {
            message = MessageFormat.format(
                "Lot: {0} không cho phép nhập vào Line. Phiếu: {1} cần nhận đủ số lượng NVL. Số lượng đã nhận: ({2}/{3}) lot",
                data.getLotNo(),
                data.getReqNo(),
                data.getTotalLotReceive(),
                data.getTotalLotRequest()
            );

            information = "Không cho phép nhập NVL vào Line";

            result.put("status", "ERROR");
            result.put("information", information);
            result.put("message", message);
            result.put("data", null);

            return result;
        }


        LotDto obj = this.modelMapper.map(lot, LotDto.class);
        obj.setQty(lot.getQty());
        obj.setRemainQty(lot.getQty());

        // Kiểm tra hàng hết hạn
        if (lot.getExpiredDate() != null) {

            Date currentDate = new Date();
            Date expiredDate = lot.getExpiredDate();
            long diffDays = (currentDate.getTime() - expiredDate.getTime()) / (1000 * 60 * 60 * 24);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String expiredDateString = formatter.format(expiredDate);


            if (diffDays > 0) {
                message = MessageFormat.format(
                    "Lot: {0} đã quá hạn {1} ngày. Ngày hết hạn: {2}", lotDto.getLotNo(), diffDays, expiredDateString
                );
                information = "Không cho phép nhập NVL vào Line";
                result.put("status", "ERROR");
                result.put("information", information);
                result.put("message", message);
                result.put("data", null);
                return result;
            }


            if (diffDays >= -30 && diffDays < 0) {
                message = MessageFormat.format(
                    "Lot: {0} còn {1} ngày sẽ hết hạn. Ngày hết hạn: {2}", lotDto.getLotNo(), diffDays*(-1), expiredDateString
                );
                information = "Cho phép nhập NVL vào LINE";
                result.put("status", "OK");
                result.put("information", information);
                result.put("message", message);
                obj.setRemark(MessageFormat.format("Còn {0} ngày sẽ hết hạn. Ngày hết hạn: {1}", diffDays*(-1), expiredDateString));
                result.put("data", obj);
                return result;
            }

            /**
             * Còn hạn thì sẽ hiển thị thông tin ngày hết hạn
             */
            if (diffDays < -30) {
                message = MessageFormat.format(
                    "Lot: {0} có thể sử dụng. Ngày hết hạn: {1}" , lotDto.getLotNo(), expiredDateString
                );
                information = "Cho phép nhập NVL vào LINE";

                result.put("status", "OK");
                result.put("information", information);
                result.put("message", message);
                result.put("data", obj);
                return result;
            }
        }

        // Các trường hợp OK còn lại

        message = "Cho phép nhập NVL vào LINE";
        information = "Cho phép nhập NVL vào LINE";

        result.put("status", "OK");
        result.put("information", information);
        result.put("message", message);
        result.put("data", obj);
        return result;
    }

    @Override
    public Map<String, Object> insertToLine(List<MaterialControlDto> dataInsert) {

        Map<String, Object> result = new HashMap<>();

        List<MaterialControls> data = dataInsert.stream().map(
                item -> modelMapper.map(item, MaterialControls.class)
        ).collect(Collectors.toList());

        List<MaterialControls> response = this.materialControlsRepo.saveAll(data);

        result.put("data", response);

        return result;
    }

    @Autowired
    private EntityManager entityManager;
    @Override
    public List<MaterialControls> getMaterialsInsertToLine(String qaCard) {
        String jpql = "SELECT m FROM MaterialControls m WHERE m.plotno = :plotno and m.recordType = :recordType";
        TypedQuery<MaterialControls> query = entityManager.createQuery(jpql,MaterialControls.class);
        query.setParameter("plotno", qaCard);
        query.setParameter("recordType", "CDL");
        return query.getResultList();
    }


}
