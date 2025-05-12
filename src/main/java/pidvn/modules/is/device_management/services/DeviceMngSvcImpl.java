package pidvn.modules.is.device_management.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pidvn.entities.one.IsDevice;
import pidvn.entities.one.IsDeviceTransaction;
import pidvn.entities.one.Users;
import pidvn.mappers.one.is.device_management.DeviceMngMapper;
import pidvn.modules.is.device_management.models.DeviceDto;
import pidvn.modules.is.device_management.models.TransactionDto;
import pidvn.modules.is.device_management.models.UserDto;
import pidvn.repositories.one.IsDeviceRepo;
import pidvn.repositories.one.IsDeviceTransactionRepo;
import pidvn.repositories.one.UsersRepo;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceMngSvcImpl implements DeviceMngSvc {

    @Autowired
    private IsDeviceRepo isDeviceRepo;

    @Autowired
    private DeviceMngMapper deviceMngMapper;

    @Autowired
    private IsDeviceTransactionRepo isDeviceTransactionRepo;

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
            throw new Exception("Hệ thống không nhận diện được mã thiết bị");
        }

        return this.modelMapper.map(this.isDeviceRepo.findByName(deviceName), DeviceDto.class);
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

        // Thêm data vào bảng is_device_transaction
        IsDeviceTransaction transaction = this.modelMapper.map(transactionDto, IsDeviceTransaction.class);
        IsDeviceTransaction response1 = this.isDeviceTransactionRepo.save(transaction);

        // Cập nhật transaction_id vào bảng is_device
        IsDevice device = this.isDeviceRepo.findByName(transaction.getDeviceName());
        device.setTransactionId(response1.getId());
        IsDevice response2 = this.isDeviceRepo.save(device);

        Map<String, Object> result = new HashMap<>();
        result.put("transaction", response1);
        result.put("device", response2);

        // Gửi email
        this.sendSimpleEmail(device, transaction);

        return result;
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
