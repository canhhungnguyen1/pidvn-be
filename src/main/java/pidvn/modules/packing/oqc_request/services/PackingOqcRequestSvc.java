package pidvn.modules.packing.oqc_request.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pidvn.entities.one.OqcRequest;
import pidvn.mappers.one.packing.oqc_request.PackingOqcRequestMapper;
import pidvn.mappers.one.qa.oqc_check.OqcCheckMapper;
import pidvn.modules.packing.oqc_request.models.*;
import pidvn.modules.relay.datecode_management.models.DateCodeVo;
import pidvn.modules.relay.datecode_management.services.RelayDateCodeSvc;
import pidvn.repositories.one.OqcRequestRepo;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PackingOqcRequestSvc implements IPackingOqcRequest {

    @Autowired
    private OqcRequestRepo oqcRequestRepo;

    @Autowired
    private RelayDateCodeSvc relayDateCodeSvc;

    @Autowired
    private PackingOqcRequestMapper packingOqcRequestMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private OqcCheckMapper oqcCheckMapper;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public Map createOqcRequest(OqcRequestVo oqcRequestVo) throws MessagingException, UnsupportedEncodingException {

        // TODO

        /**
         * B1: Kiểm tra phiếu đã được tạo chưa
         */
        List<OqcRequest> reqNos = this.oqcRequestRepo.findByQaCard(oqcRequestVo.getQaCard());

        /**
         * Trường hợp không phải là hàng sorting,
         * nếu đã tồn tại request thì không cho tạo mới
         */
        if (reqNos.size() > 0 && !oqcRequestVo.isSorting()) {
            Map result = new HashMap();
            result.put("status","ERROR");
            result.put("messages","Đã có request tạo cho QA card : " + oqcRequestVo.getQaCard());
            return result;
        }

        /**
         * B2: Tạo mã OQC request (req_no)
         */
        int totalRequest = this.oqcRequestRepo.getTotalRequestInMonth();
        int sequenceNo = totalRequest+=1;
        String strDate = new SimpleDateFormat("yy-MM").format(Calendar.getInstance().getTime());
        String reqNo = "ROQC-" + strDate + "-" + sequenceNo;

        /**
         * B3. Tạo request cho hàng sorting
         */
        if (oqcRequestVo.isSorting()) {

            /**
             * 1. Update lại request cũ thành NG, remark lại do hàng sorting
             */
            List<OqcRequest> oqcReqList = this.oqcRequestRepo.findByQaCard(oqcRequestVo.getQaCard());
            if (oqcReqList.size() > 0) {
                OqcRequest oldOqcReq = oqcReqList.get(oqcReqList.size()-1);
                oldOqcReq.setJudgment("NG");
                oldOqcReq.setRemark("System: NG do hàng sorting");
                oldOqcReq.setOqcDate(new Date());
                oldOqcReq.setOqcRequestStatus(3);
                this.oqcRequestRepo.save(oldOqcReq);
            }

            /**
             * 2. Tạo request mới
             */
            OqcRequest oqcReq = new OqcRequest();
            oqcReq.setReqNo(reqNo);
            oqcReq.setQaCard(oqcRequestVo.getQaCard());
            oqcReq.setOqcRequestStatus(1);
            oqcReq.setPriority(oqcRequestVo.getPriority());
            oqcReq.setCreatedBy(oqcRequestVo.getCreatedBy());
            oqcReq.setSortingQty(oqcRequestVo.getSortingQty());
            oqcReq.setRemark(oqcRequestVo.getRemark());
            oqcReq.setIsSpecialRequest(oqcRequestVo.getIsSpecialRequest());
            oqcReq.setDeliveryDate(oqcRequestVo.getDeliveryDate());
            oqcReq.setFlag(1);
            OqcRequest data = this.oqcRequestRepo.save(oqcReq);

            Map result = new HashMap();
            result.put("status","OK");
            result.put("messages","Tạo request thành công");
            result.put("data", data);

            return result;
        }

        /**
         * B3: Tạo request cho hàng thường
         */
        OqcRequest oqcReq = new OqcRequest();
        oqcReq.setReqNo(reqNo);
        oqcReq.setQaCard(oqcRequestVo.getQaCard());
        oqcReq.setOqcRequestStatus(1);
        oqcReq.setPriority(oqcRequestVo.getPriority());
        oqcReq.setCreatedBy(oqcRequestVo.getCreatedBy());
        oqcReq.setSortingQty(null);
        oqcReq.setRemark(oqcRequestVo.getRemark());
        oqcReq.setIsSpecialRequest(oqcRequestVo.getIsSpecialRequest());
        oqcReq.setDeliveryDate(oqcRequestVo.getDeliveryDate());
        oqcReq.setFlag(1);

        OqcRequest data = this.oqcRequestRepo.save(oqcReq);

        this.sendEmail(data);

        Map result = new HashMap();
        result.put("status","OK");
        result.put("messages","Tạo request thành công");
        result.put("data", data);

        return result;
    }


    private void sendEmail(OqcRequest oqcRequest) throws MessagingException, UnsupportedEncodingException {
        /**
         * Gửi email: Trường hợp nếu request cần phải xác nhận
         */
        if (oqcRequest.getIsSpecialRequest() == 1) {

            pidvn.modules.qa.oqc_check.models.SearchVo searchVo = new pidvn.modules.qa.oqc_check.models.SearchVo();
            searchVo.setReqNo(oqcRequest.getReqNo());
            pidvn.modules.qa.oqc_check.models.OqcRequestVo obj = this.oqcCheckMapper.getOqcRequests(searchVo).get(0);


            // TODO: Gửi email
            MimeMessage message = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);


            // Gán biến động vào template
            Context context = new Context();
            context.setVariable("reqNo", obj.getReqNo());
            context.setVariable("model", obj.getModel());
            context.setVariable("qaCard", obj.getQaCard());
            context.setVariable("dateCode", obj.getDateCode());
            context.setVariable("qty", obj.getQty());
            context.setVariable("createdBy", obj.getCreatedBy());
            context.setVariable("createdByName", obj.getCreatedByName());
            context.setVariable("remark", obj.getRemark());

            String htmlContent = this.templateEngine.process("oqc-accept-request-template", context);

            helper.setFrom("it.pidvn", "IT PIDVN");
            helper.setSubject("[OQC-REQUEST] - Thông báo phê duyệt OQC request");
            helper.setTo("canhhung.nguyen@vn.panasonic.com");
            helper.setCc("canhhung.nguyen@vn.panasonic.com");
            helper.setText(htmlContent, true); // true => HTML
            this.mailSender.send(message);
        }
    }

    @Override
    public OqcRequest updateOqcRequest(OqcRequestVo oqcRequestVo) {
        OqcRequest oqcRequestUpdate = this.oqcRequestRepo.findById(oqcRequestVo.getId()).get();
        oqcRequestUpdate.setSortingQty(oqcRequestVo.getSortingQty());
        oqcRequestUpdate.setDeliveryDate(oqcRequestVo.getDeliveryDate());
        oqcRequestUpdate.setPriority(oqcRequestVo.getPriority());
        oqcRequestUpdate.setRemark(oqcRequestVo.getRemark());
        oqcRequestUpdate.setOqcRequestStatus(oqcRequestVo.getRequestStatusId());
        return this.oqcRequestRepo.save(oqcRequestUpdate);
    }

    private String createOqcRequestNo() {

        // List<OqcRequest> requests = this.oqcRequestRepo.getAllByCurrentMonth();

        



        return "";
    }

    @Override
    public List<DataSummaryVo> summaryData(SearchVo searchVo) {
        return this.packingOqcRequestMapper.summaryData(searchVo);
    }

    @Override
    public Map<String, Object> systemValidate(String qaCard) {

         List<DateCodeVo> dateCodes = this.relayDateCodeSvc.getDateCodes(qaCard);

        // Tính tỷ lệ nvl scan
        List<DateCodeRateVo> dateCodeRate = this.packingOqcRequestMapper.getDateCodeRate(qaCard);

        // Lấy dữ liệu scan nvl so sánh với dữ liệu nhận nvl
        List<ModelVo> dataScan = this.packingOqcRequestMapper.getDataScan(qaCard);

        Map<String, Object> result = new HashMap<>();
        result.put("dateCodes", dateCodes);
        result.put("dateCodeRate", dateCodeRate);
        result.put("dataScan", dataScan);

        return result;
    }
}
