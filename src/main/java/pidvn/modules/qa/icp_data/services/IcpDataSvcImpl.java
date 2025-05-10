package pidvn.modules.qa.icp_data.services;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pidvn.entities.one.IcpData;
import pidvn.entities.one.IqcData;
import pidvn.entities.one.Model;
import pidvn.entities.one.PsMaster;
import pidvn.mappers.one.qa.icp_data.IcpDataMapper;
import pidvn.modules.qa.icp_data.models.IcpDataDto;
import pidvn.repositories.one.IcpDataRepo;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class IcpDataSvcImpl implements IcpDataSvc {

    Logger logger = LoggerFactory.getLogger(IcpDataSvcImpl.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private IcpDataMapper icpDataMapper;

    @Autowired
    private IcpDataRepo icpDataRepo;


    public final String ROOT_FOLDER = "Y:\\Public\\Huệ admin QA\\ICP on FDCS demo\\";

    @Override
    public List<Model> getModel() {
        String jpql = "SELECT m FROM Model m";
        TypedQuery<Model> query = entityManager.createQuery(jpql, Model.class);
        return query.getResultList();
    }

    @Override
    public List<IcpDataDto> getIcpData(String parentModel) {
        return this.icpDataMapper.getIcpData(parentModel);
    }

    @Override
    public List<PsMaster> getPsMasters(String parentModel) {
        String jpql = "SELECT m FROM PsMaster m where m.pnpa = :parentModel";
        TypedQuery<PsMaster> query = entityManager.createQuery(jpql, PsMaster.class);
        query.setParameter("parentModel", parentModel);
        return query.getResultList();
    }

    @Override
    public IcpData insertIcpData(IcpDataDto icpDataDto) {
        IcpData icpData = this.modelMapper.map(icpDataDto, IcpData.class);
        return this.icpDataRepo.save(icpData);
    }

    @Override
    public Map<String, Object> updateIcpData(IcpDataDto icpDataDto) throws Exception {

        Map<String, Object> result = new HashMap<>();


        IcpData icpObj = this.icpDataRepo.findById(icpDataDto.getId()).orElse(null);

        if (icpObj == null) {
            throw new Exception("Không tìm thấy dữ liệu");
        }

        // Nếu tên testNo thay đổi
        if (!icpObj.getTestNo().equals(icpDataDto.getTestNo())) {

            String jpql = "SELECT m FROM IcpData m where m.testNo = :testNo";
            TypedQuery<IcpData> query = entityManager.createQuery(jpql, IcpData.class);
            query.setParameter("testNo", icpObj.getTestNo());

            List<IcpData> data = query.getResultList();
            for (IcpData item : data) {
                item.setTestNo(icpDataDto.getTestNo());
            }

            List<IcpData> dataSaved = icpDataRepo.saveAll(data);
            result.put("dataRelated", dataSaved);
        }


        // Cập nhật thông tin
        IcpData res = icpDataRepo.save(this.modelMapper.map(icpDataDto, IcpData.class));
        result.put("updateData", res);

        return result;
    }

    @Override
    public Integer deleteIcpData(Integer id) {
        this.icpDataRepo.deleteById(id);
        return id;
    }

    @Override
    public Map<String, Object> uploadTestReports(MultipartFile[] files) {


        Map<String, Object> result = new HashMap<>();

        List<String> fileNames = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                byte[] bytes = file.getBytes();

                Path path = Paths.get(this.ROOT_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
                fileNames.add(file.getOriginalFilename());
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        result.put("fileNames", fileNames);

        return result;
    }


}
