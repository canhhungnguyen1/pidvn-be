package pidvn.modules.qa.oqc_check.services;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pidvn.entities.one.AuditConfigFdcs;
import pidvn.entities.one.OqcDataFile;
import pidvn.entities.one.OqcRequest;
import pidvn.mappers.one.qa.oqc_check.OqcCheckMapper;
import pidvn.modules.qa.oqc_check.models.*;
import pidvn.modules.relay.measurement.utils.FileUploadUtil;
import pidvn.repositories.one.AuditConfigFdcsRepo;
import pidvn.repositories.one.OqcDataFileRepo;
import pidvn.repositories.one.OqcRequestRepo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OqcCheckSvc implements IOqcCheckSvc {

    private String FILE_PATH_ROOT = "\\\\10.92.152.55\\pvg-data$\\PIDVN-Data\\Public Drive\\IS\\CanhHung\\FDCS\\QA\\OQC\\Relay";

    @Autowired
    private OqcRequestRepo oqcRequestRepo;

    @Autowired
    private OqcDataFileRepo oqcDataFileRepo;

    @Autowired
    private OqcCheckMapper oqcCheckMapper;

    @Autowired
    private AuditConfigFdcsRepo auditConfigFdcsRepo;

    @Override
    public List<OqcDataVo> getOqcMasterData(String reqNo, String qaCard) {
        return this.oqcCheckMapper.getOqcMasterData(reqNo,qaCard);
    }

    /**
     * Tìm kiếm oqc Requests
     * @param searchVo
     * @return
     */
    @Override
    public List<OqcRequestVo> getOqcRequests(SearchVo searchVo) {

        /**
         * Config khi audit
         * Nếu điều kiện = "TRUE"
         * Lấy thông tin config từ bảng audit_config_fdcs
         */
        String isConfig = this.auditConfigFdcsRepo.findByConfigName("oqc_check_audit").get(0).getConfigValue();
        searchVo.setIsAudit(isConfig);

        List<OqcRequestVo> result = this.oqcCheckMapper.getOqcRequests(searchVo);



        return result;
    }

    @Override
    public OqcRequest updateOqcRequest(OqcRequestVo oqcRequestVo) {

        OqcRequest oqcRequest = this.oqcRequestRepo.findByReqNo(oqcRequestVo.getReqNo()).get(0);

        oqcRequest.setPriority(oqcRequestVo.getPriority() == null ? oqcRequest.getPriority() : oqcRequestVo.getPriority());
        oqcRequest.setOqcRequestStatus(oqcRequestVo.getRequestStatusId() == null ? oqcRequest.getOqcRequestStatus() : oqcRequestVo.getRequestStatusId());

        return this.oqcRequestRepo.save(oqcRequest);
    }

    @Override
    public OqcRequest oqcRequestHandle(OqcRequestVo oqcRequestVo) {
        OqcRequest oqcRequest = this.oqcRequestRepo.findById(oqcRequestVo.getId()).get();
        oqcRequest.setOqcRequestStatus(2);
        return this.oqcRequestRepo.save(oqcRequest);
    }

    @Override
    public OqcDataFile uploadFile(MultipartFile file, String createdBy, String filePathRoot, String reqNo, String remark) throws IOException {

//        System.out.println("AAA");
//        return null;


        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
        String strDate = formatter.format(date);

        String fileName = "Data_" + strDate + "." + FilenameUtils.getExtension(file.getOriginalFilename());

        String uploadDir = filePathRoot + "\\" + reqNo + "\\";
        FileUploadUtil.saveFile(uploadDir, fileName, file);

        OqcDataFile oqcDataFile = new OqcDataFile();
        oqcDataFile.setReqNo(reqNo);
        oqcDataFile.setFileFormat(file.getContentType());
        oqcDataFile.setUrl(uploadDir);
        oqcDataFile.setFileName(fileName);
        oqcDataFile.setCreatedBy(createdBy);
        oqcDataFile.setRootFolder(filePathRoot);


        // TODO: Lưu vào database
        OqcDataFile result = this.oqcDataFileRepo.save(oqcDataFile);

        // TODO: Cập nhật Judgment cho Request
        String finalJudgment = this.getFinalJudgment(file);
        OqcRequest oqcRequest = this.oqcRequestRepo.findByReqNo(reqNo).get(0);
        oqcRequest.setJudgment(finalJudgment == "" ? null : finalJudgment);
        oqcRequest.setOqcRequestStatus(3);
        oqcRequest.setOqcDate(new Date());
        oqcRequest.setRemark(remark);

        this.oqcRequestRepo.save(oqcRequest);

        return result;


    }

    /**
     * Lấy Judgment từ attach file
     * @param file
     * @return
     */
    private String getFinalJudgment(MultipartFile file) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        String result = workbook.getSheetAt(0).getRow(38).getCell(17).getStringCellValue();
        return result;
    }

    @Override
    public List<OqcDataFile> getOqcDataFiles(String reqNo) {
        return this.oqcDataFileRepo.findByReqNoOrderByIdDesc(reqNo);
    }

    @Override
    public AuditConfigFdcs changeConfigAudit(String configValue) {
        String configName = "oqc_check_audit";
        AuditConfigFdcs config = this.auditConfigFdcsRepo.findByConfigName(configName).get(0);
        config.setConfigValue(configValue);
        return this.auditConfigFdcsRepo.save(config);
    }

    @Override
    public AuditConfigFdcs getConfigAudit(String configName) {
        return this.auditConfigFdcsRepo.findByConfigName(configName).get(0);
    }

    @Override
    public Map<String, Object> systemValidate(String qaCard) {
        List<PsMasterVo> psMasterData = this.oqcCheckMapper.getPsMasterData(qaCard);
        List<DateCodeVo> dateCodeData = this.oqcCheckMapper.getDateCode(qaCard);

        Map<String, Object> result = new HashMap<>();
        result.put("psMasterData", psMasterData);
        result.put("dateCodeData", dateCodeData);
        return result;
    }

    @Override
    public OqcRequest handleAbnormalRequest(OqcRequestVo oqcRequestVo) {

        Optional<OqcRequest> optional = this.oqcRequestRepo.findById(oqcRequestVo.getId());

        if (!optional.isPresent()) {
            throw new RuntimeException("Không tìm thấy Request: " + oqcRequestVo.getReqNo());
        }

        OqcRequest obj = optional.get();
        obj.setAcceptedBy(oqcRequestVo.getAcceptedBy());
        obj.setAcceptedResult(oqcRequestVo.getAcceptedResult());
        obj.setSpecialRemark(oqcRequestVo.getSpecialRemark());

        return this.oqcRequestRepo.save(obj);
    }
}
