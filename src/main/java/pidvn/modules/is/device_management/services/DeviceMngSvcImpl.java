package pidvn.modules.is.device_management.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pidvn.entities.one.*;
import pidvn.mappers.one.is.device_management.DeviceMngMapper;
import pidvn.modules.is.device_management.models.*;
import pidvn.repositories.one.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeviceMngSvcImpl implements DeviceMngSvc {

    @Autowired
    private IsDeviceRepo isDeviceRepo;

    @Autowired
    private IsDeviceTransactionRepo isDeviceTransactionRepo;

    @Autowired
    private IsDeviceLocationRepo isDeviceLocationRepo;

    @Autowired
    private IsDeviceInventoryRequestRepo isDeviceInventoryRequestRepo;

    @Autowired
    private IsDeviceInventoryDataRepo isDeviceInventoryDataRepo;

    @Autowired
    private DeviceMngMapper deviceMngMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public List<DeviceDto> getDevices() {
        return this.deviceMngMapper.getDevices();
    }

    @Override
    public DeviceDto getDevice(String deviceName) throws Exception {
        IsDevice result = this.isDeviceRepo.findByName(deviceName);

        if (result == null) {
            throw new Exception("Mã QR thiết bị không đúng !");
        }

        return this.modelMapper.map(this.isDeviceRepo.findByName(deviceName), DeviceDto.class);
    }


    @Override
    public List<IsDeviceLocation> getLocations() {
        return this.isDeviceLocationRepo.findAll();
    }

    @Override
    public List<UserDto> getUsers() {
        return this.deviceMngMapper.getUsers();
    }

    @Override
    public List<TransactionDto> getDeviceTransactions() {
        return this.deviceMngMapper.getDeviceTransactions();
    }

    @Autowired
    private UsersRepo userRepo;

    @Override
    public Map<String, Object> saveTransaction(TransactionDto transactionDto) throws MessagingException, UnsupportedEncodingException {

        // 1. Tìm thiết bị liên quan
        IsDevice device = isDeviceRepo.findByName(transactionDto.getDeviceName());
        device = Optional.ofNullable(device)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thiết bị: " + transactionDto.getDeviceName()));


        // 2. Map DTO -> entity và lưu giao dịch
        IsDeviceTransaction transaction = modelMapper.map(transactionDto, IsDeviceTransaction.class);
        transaction.setDate(new Date());
        IsDeviceTransaction savedTransaction = isDeviceTransactionRepo.save(transaction);

        // 3. Cập nhật thông tin thiết bị
        device.setTransactionId(savedTransaction.getId());
        device.setLocationId(savedTransaction.getLocationId());
        IsDevice updatedDevice = isDeviceRepo.save(device);

        // 4. Gửi email thông báo
        sendSimpleEmail(device, transaction);

        // 5. Trả kết quả
        Map<String, Object> result = new HashMap<>();
        result.put("transaction", savedTransaction);
        result.put("device", updatedDevice);
        return result;
    }

    @Override
    public InventoryRequestDto createInventoryRequest(InventoryRequestDto inventoryRequestDto) {
        IsDeviceInventoryRequest request = this.modelMapper.map(inventoryRequestDto, IsDeviceInventoryRequest.class);
        IsDeviceInventoryRequest response = this.isDeviceInventoryRequestRepo.save(request);
        return this.modelMapper.map(response, InventoryRequestDto.class);
    }

    @Override
    public List<InventoryRequestDto> getInventoryRequests() {
        List<IsDeviceInventoryRequest> data = this.isDeviceInventoryRequestRepo.findAll();
        return data.stream().map(item -> modelMapper.map(item, InventoryRequestDto.class)).collect(Collectors.toList());
    }

    @Override
    public InventoryDataDto saveInventoryData(InventoryDataDto ivtDataDto) {

        // Map DTO -> Entity
        IsDeviceInventoryData ivtData = modelMapper.map(ivtDataDto, IsDeviceInventoryData.class);
        ivtData.setDate(new Date());

        // Lưu dữ liệu kiểm kê
        IsDeviceInventoryData savedData = isDeviceInventoryDataRepo.save(ivtData);

        // Cập nhật location nếu cần
        IsDevice device = isDeviceRepo.findByName(ivtData.getDeviceName());
        if (!Objects.equals(device.getLocationId(), ivtDataDto.getLocationId())) {
            device.setLocationId(ivtDataDto.getLocationId());
            isDeviceRepo.save(device);
        }

        // Trả kết quả dạng DTO
        return modelMapper.map(savedData, InventoryDataDto.class);
    }

    @Override
    public List<InventoryDataDto> getInventoryData(Integer requestId) {
        return this.deviceMngMapper.getInventoryData(requestId);
    }

    public void sendSimpleEmail(IsDevice device, IsDeviceTransaction transaction) throws MessagingException, UnsupportedEncodingException {

        Users picUser = this.userRepo.findByUsername(transaction.getPicCode());
        Users itUser = this.userRepo.findByUsername(transaction.getItUserCode());

        if (picUser.getEmail() == null || picUser.getEmail().isEmpty()) {
            return;
        }

        MimeMessage message = this.mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        // Gán biến động vào template
        Context context = new Context();
        context.setVariable("picCode", picUser.getUsername());
        context.setVariable("picName", picUser.getName());
        context.setVariable("deviceName", device.getName());
        context.setVariable("model", device.getModel());
        context.setVariable("type", device.getType());
        context.setVariable("brand", device.getBrand());
        context.setVariable("faCode", device.getFaCode());
        context.setVariable("os", device.getOs());
        context.setVariable("purchaseDate", device.getPurchaseDate());
        String htmlContent = this.templateEngine.process("device-email-template", context);

        helper.setFrom("it.pidvn", "IT PIDVN");
        helper.setSubject("IT PIDVN - Thông báo bàn giao thiết bị");
        helper.setTo(picUser.getEmail());
        helper.setCc(itUser.getEmail());
        helper.setText(htmlContent, true); // true => HTML

        this.mailSender.send(message);
    }


}
