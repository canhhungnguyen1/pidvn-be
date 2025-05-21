package pidvn.modules.spare_part.services;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pidvn.entities.one.*;
import pidvn.exceptions.ConflictException;
import pidvn.mappers.one.spare_part.SparePartMapper;
import pidvn.modules.spare_part.models.*;
import pidvn.repositories.one.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SparePartSvc implements ISparePartSvc {

    Logger logger = LoggerFactory.getLogger(SparePartSvc.class);

    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private SparePartRepo sparePartRepo;

    @Autowired
    private SparePartIvtRepo sparePartIvtRepo;

    @Autowired
    private SparePartRecordRepo sparePartRecordRepo;

    @Autowired
    private SparePartInventoryRequestRepo sparePartInventoryRequestRepo;

    @Autowired
    private SparePartInventoryDataRepo sparePartInventoryDataRepo;

    @Autowired
    private SparePartMapper sparePartMapper;

    @Autowired
    private SparePartLineStandardRepo sparePartLineStandardRepo;

    @Autowired
    private SparePartMachineStandardRepo sparePartMachineStandardRepo;

    @Autowired
    private SectionRepo sectionRepo;

    @Autowired
    private SubsectionRepo subsectionRepo;

    @Autowired
    private SparePartRequestMasterRepo sparePartRequestMasterRepo;

    @Autowired
    private SparePartRequestDetailRepo sparePartRequestDetailRepo;

    @Autowired
    private SparePartRackRepo sparePartRackRepo;

    @Override
    public List<Users> getUsers() {
        return this.usersRepo.findAllByOrderByIdDesc();
    }

    @Override
    public List<SparePart> getSpareParts() {
        return this.sparePartRepo.findAllByOrderByIdDesc();
    }

    public List<Map<String, Object>> getSparePartsivt(String code) {
        return this.sparePartMapper.getSparePartIvt(code);
    }


    public List<Map<String, Object>> getSparePartsivthis() {
        return this.sparePartMapper.getSparePartIvtHis();
    }

    public List<Map<String,Object>> getSparePartByIvtNo(String ivtNo){
        return this.sparePartRepo.getSparePartByIvtNo(ivtNo);
    }

    @Override
    public SparePart saveSparePart(SparePart sparePart) throws Exception {

        if (this.sparePartRepo.findByPartNumber(sparePart.getPartNumber()) != null) {
            throw new Exception("Part Number đã tồn tại");
        }

        return this.sparePartRepo.save(sparePart);
    }

    @Override
    public List<SparePartRecordVo> getSparePartRecords(SearchVo searchVo) {
        return this.sparePartMapper.getSparePartRecords(searchVo);
    }

    @Override
    public List<SparePartRecord> saveSparePartRecords(List<SparePartRecord> sparePartRecords) {
        return this.sparePartRecordRepo.saveAll(sparePartRecords);
    }

    @Override
    public SparePartRecord updateSparePartRecord(SparePartRecord sparePartRecord) {

        SparePartRecord record = this.sparePartRecordRepo.findById(sparePartRecord.getId()).get();
        record.setPartNumber(sparePartRecord.getPartNumber());
        record.setFactoryCode(sparePartRecord.getFactoryCode());
        record.setLine(sparePartRecord.getLine());
        record.setMachine(sparePartRecord.getMachine());
        record.setReceiveUserCode(sparePartRecord.getReceiveUserCode());
        record.setRemark(sparePartRecord.getRemark());
        record.setQty(sparePartRecord.getQty());
        record.setType(sparePartRecord.getQty() < 0 ? "OK_RETURN" : "OUTPUT");

        return this.sparePartRecordRepo.save(record);
    }

    @Override
    public Map deleteSparePartRecord(Integer id) {

        this.sparePartRecordRepo.deleteById(id);

        Map result = new HashMap();
        result.put("message", "Xóa thành công");

        return result;
    }

//    @Override
//    public SparePartRecord saveSparePartRecord(SparePartRecord sparePartRecord) {
//        return this.sparePartRecordRepo.save(sparePartRecord);
//    }


    @Override
    public SparePartInventoryRequest saveSparePartInventoryRequest(SparePartInventoryRequest request) throws ResponseStatusException, ConflictException {

        /**
         * Nếu đã có phiếu kiểm kê trong tháng rồi thì ko tạo nữa
         */
        List<SparePartInventoryRequest> requests = this.sparePartInventoryRequestRepo.findByCurrentMonth();

        if (requests.size() > 0) {
            // throw new ResponseStatusException(HttpStatus.CONFLICT, "Đã có phiếu kiểm kê tháng này");
            throw new ConflictException("Đã có phiếu kiểm kê tháng này");
        }

        return this.sparePartInventoryRequestRepo.save(request);
    }

    @Override
    public List<SparePartInventoryRequest> getSparePartInventoryRequests() {
        return this.sparePartInventoryRequestRepo.findAllByOrderByIdDesc();
    }

    @Override
    public Map saveInventoryData(List<SparePartInventoryData> sparePartInventoryDataList) {

        List<SparePartInventoryData> resultOK = new ArrayList<>();
        List<SparePartInventoryData> resultNG = new ArrayList<>();

        for (SparePartInventoryData item : sparePartInventoryDataList) {
            try {
                SparePartInventoryData ivtData = this.sparePartInventoryDataRepo.save(item);
                resultOK.add(ivtData);
            } catch (Exception e) {
                logger.debug(e.toString());
                resultNG.add(item);
            }
        }

        Map result = new HashMap();
        result.put("resultOK", resultOK);
        result.put("resultNG", resultNG);

        return result;
    }

    @Override
    public Map uploadExcel(MultipartFile file, String recordType) throws IOException {

        Map result = null;

        if (recordType.equals("OUT")) {
            result = this.readExcelSparePartOutput(file);
        }

        List<SparePartRecord> data = (List<SparePartRecord>) result.get("data");

        this.sparePartRecordRepo.saveAll(data);


        return result;
    }

    @Override
    public List<SparePartLineStandard> getLineStandard() {
        return this.sparePartLineStandardRepo.findAll();
    }

    @Override
    public List<SparePartMachineStandard> getMachineStandard() {
        return this.sparePartMachineStandardRepo.findAll();
    }

    @Override
    public List<SparePartRack> getRacks() {
        return this.sparePartRackRepo.findAll();
    }

    @Override
    public List<SparePartRecordVo> getSparePartRecordsByStandardPrice(SearchVo searchVo) {
        return this.sparePartMapper.getSparePartRecordsByStandardPrice(searchVo);
    }

    /**
     * Tạo request yêu cầu xuất hàng M4/M8
     * B1: tạo record insert vào bảng spare_part_request_master
     * B2: thêm dữ liệu vào M4/M8 vào bảng spare_part_request_detail
     *
     * @param spareParts
     * @return
     */
    @Override
    public List<SparePartRequestDetail> createRequestSparePart(
            List<SparePartRequestDetail> spareParts,
            String factoryCode,
            Integer subsectionId,
            String requestType
    ) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String createdBy = userDetails.getUsername();

        //Section section = this.sectionRepo.findById(sectionId).get();

        // TODO
        // B1: tạo request

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String date = formatter.format(new Date());


        Integer sequence = this.sparePartRequestMasterRepo.getTotalRequestInDay(requestType) + 1;
        String paddedSequence = String.format("%02d", sequence); // 2 chữ số, thêm số 0 ở đầu nếu cần

        SparePartRequestMaster obj = new SparePartRequestMaster();
        obj.setRequestNo((Objects.equals(requestType, "ORDER") ? "REQ-" : "RET-") + date + "-" + paddedSequence);
        obj.setCreatedBy(createdBy);
        obj.setFactoryCode(factoryCode);
        obj.setSubsectionId(subsectionId);
        obj.setDate(new Date());
        obj.setActive(true);
        obj.setType(requestType);
        SparePartRequestMaster master = this.sparePartRequestMasterRepo.save(obj);

        // B2: thêm dữ liệu vào bảng spare_part_request_detail
        for (SparePartRequestDetail item : spareParts) {
            item.setId(null);
            item.setRequestId(master.getId());
        }

        return this.sparePartRequestDetailRepo.saveAll(spareParts);
    }

    @Override
    public List<Section> getSections() {
        return this.sectionRepo.findAll();
    }

    @Override
    public List<Subsection> getSubsections() {
        return this.subsectionRepo.findAll();
    }

    @Override
    public List<SparePartRequestVo> getSparePartRequestMaster(SearchVo searchVo) {
        return this.sparePartMapper.getSparePartRequestMasters(searchVo);
    }

    @Override
    public List<SparePartRequestVo> getSparePartRequestDetailByRequestId(Integer requestId) {
        return this.sparePartMapper.getSparePartRequestDetailByRequestId(requestId);
    }

    @Override
    public ByteArrayInputStream downloadQaCard(Integer requestId) throws IOException {

        SearchVo searchVo = new SearchVo();
        searchVo.setRequestMasterId(requestId);

        List<SparePartRequestVo> requests = this.sparePartMapper.getSparePartRequestMasters(searchVo);




        SparePartRequestVo request = requests.get(0);
//        for (SparePartRequestVo req: requests) {
//            if (req.getId() == requestId) {
//                request = req;
//                break;
//            }
//        }

        List<SparePartRequestVo> data = this.sparePartMapper.getSparePartRequestDetailByRequestId(requestId);


        String tempName = "temp-" + new Random().nextInt(1000) + ".xlsx";
        String rootFolder = "\\\\10.92.152.55\\pvg-data$\\PIDVN-Data\\Public Drive\\IS\\(C) Save File FDCS\\FDCS-Server-2\\M4M8\\";

        String fileName = request.getType().equals("ORDER") ? "MaterialRequest.xlsx" : "MaterialRequest - RETURN.xlsx";

        String sourcePath = rootFolder + fileName;
        String targetPath = rootFolder + tempName;



        String pathFile = null;

        Workbook workbook = null;
        FileInputStream inputStream = null;
        ByteArrayOutputStream out = null;
        ByteArrayInputStream result = null;

        try {
            pathFile = this.createTempFile(sourcePath, targetPath);
            File file = new File(pathFile);
            inputStream = new FileInputStream(file);

            // Tạo workbook từ input stream
            workbook = new XSSFWorkbook(inputStream);

            // Đọc sheet đầu tiên của file excel
            Sheet sheet = workbook.getSheetAt(0);

            // Cập nhật các ô với dữ liệu từ request
            Cell requestNoCell = sheet.getRow(7).getCell(5);
            requestNoCell.setCellValue(request.getRequestNo());

            Cell requestDateCell = sheet.getRow(8).getCell(5);
            requestDateCell.setCellValue(request.getDate());

            Cell requestSectionCell = sheet.getRow(7).getCell(0);
            requestSectionCell.setCellValue("From/Từ:" + request.getSubsectionName());

            // Cập nhật dữ liệu từ requestDetail
            int rowNum = 12;
            for (SparePartRequestVo item : data) {
                Row row = sheet.getRow(rowNum++);
                if (row == null) {
                    row = sheet.createRow(rowNum - 1);
                }
                row.getCell(1).setCellValue(item.getPartName());
                row.getCell(2).setCellValue(item.getPartNumber());
                row.getCell(3).setCellValue(item.getUnit());
                row.getCell(4).setCellValue(item.getRequestQty() == null ? 0 : item.getRequestQty());
                row.getCell(5).setCellValue(item.getIssuedQty() == null ? 0 : item.getIssuedQty());
            }

            // Ghi dữ liệu vào ByteArrayOutputStream
            out = new ByteArrayOutputStream();
            workbook.write(out);
            result = new ByteArrayInputStream(out.toByteArray());

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            // Đảm bảo tài nguyên được đóng
            if (workbook != null) {
                workbook.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (out != null) {
                out.close();
            }
            // Xóa tệp tạm thời
            Files.deleteIfExists(Paths.get(pathFile));
        }

        return result;
    }

    @Override
    public SparePartRequestMaster deleteSparePartRequestMaster(Integer id) {
        SparePartRequestMaster data = this.sparePartRequestMasterRepo.findById(id).get();
        data.setActive(false);
        return this.sparePartRequestMasterRepo.save(data);
    }

    @Override
    public List<SparePartRecord> changeRack(List<SparePartRecord> sparePartRecords) {
        return this.sparePartRecordRepo.saveAll(sparePartRecords);
    }

    private String createTempFile(String sourcePath, String targetPath) throws IOException {
        File source = new File(sourcePath);
        File target = new File(targetPath);
        FileUtils.copyFile(source, target);
        return targetPath;
    }


    /**
     * Đọ dữ liệu xuất spare part từ excel
     *
     * @param file
     * @return
     */
    private Map readExcelSparePartOutput(MultipartFile file) {

        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        XSSFSheet sheet = workbook.getSheetAt(0);


        List<SparePartRecord> data = new ArrayList<>();
        List<RowExcelErrorVo> rowNG = new ArrayList<>();

        int lastRowNum = sheet.getPhysicalNumberOfRows();

        for (int i = 6; i <= lastRowNum; i++) {
            SparePartRecord obj = new SparePartRecord();
            try {
                XSSFRow row = sheet.getRow(i);

                if (row.getCell(2).getStringCellValue().trim().equals("")
                        || row.getCell(2).getStringCellValue() == null
                ) {
                    Integer rowNum = i + 1;
                    rowNG.add(new RowExcelErrorVo(rowNum, "Không có PartNumber"));
                    continue;
                }

                if ((int) row.getCell(4).getNumericCellValue() <= 0) {
                    Integer rowNum = i + 1;
                    rowNG.add(new RowExcelErrorVo(rowNum, "Qty <= 0"));
                    continue;
                }

                obj.setPartNumber(row.getCell(2).getStringCellValue());
                obj.setWhUserCode(row.getCell(5).getStringCellValue());
                obj.setReceiveUserCode(row.getCell(7).getStringCellValue());
                obj.setQty((float) row.getCell(4).getNumericCellValue());
                //obj.setMachine(row.getCell(8).getStringCellValue());
                obj.setLine(row.getCell(9).getStringCellValue());
                obj.setDate(row.getCell(0).getDateCellValue());
                obj.setInsertType("excel");
                obj.setType("OUT");
                obj.setFactoryCode("RE");
                obj.setCreatedAt(new Date());
                obj.setUpdatedAt(new Date());

                data.add(obj);

            } catch (Exception e) {
                Integer rowNum = i + 1;
                RowExcelErrorVo item = new RowExcelErrorVo(rowNum, e.toString());
                rowNG.add(item);
            }
        }

        Map result = new HashMap();

        result.put("data", data);
        result.put("error", rowNG);

        return result;
    }
}
