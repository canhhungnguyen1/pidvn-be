package pidvn.modules.qa.material_checksheet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pidvn.commons.dto.ApiResponse;
import pidvn.entities.one.QaMaterialCheckSheetConfirm;
import pidvn.entities.one.QaMaterialCheckSheetRecord;
import pidvn.modules.qa.material_checksheet.models.PsMaterDto;
import pidvn.modules.qa.material_checksheet.models.QaCardDto;
import pidvn.modules.qa.material_checksheet.models.SearchDto;
import pidvn.modules.qa.material_checksheet.services.MaterialCheckSheetSvcImpl;

import java.util.List;

@RestController
@RequestMapping("QA/MaterialCheckSheet")
public class MaterialCheckSheetCtrl {

    @Autowired
    private MaterialCheckSheetSvcImpl materialCheckSheetSvc;


    /**
     * Lấy danh sách QA card
     * @return
     */
    @PostMapping("QaCards")
    public ResponseEntity<ApiResponse<?>> getQaCards(@RequestBody SearchDto searchDto) {
        ApiResponse<List<QaCardDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.materialCheckSheetSvc.getQaCards(searchDto));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * API: QA/MaterialCheckSheet/PsMasters
     * @param qaCard
     * @return
     */
    @GetMapping("PsMasters")
    public ResponseEntity<ApiResponse<?>> getPsMasters(@RequestParam String qaCard) {
        ApiResponse<List<PsMaterDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.materialCheckSheetSvc.getPsMasters(qaCard));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("Confirm")
    public ResponseEntity<ApiResponse<?>> confirmCheckSheet(@RequestBody QaMaterialCheckSheetConfirm obj) {
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.materialCheckSheetSvc.confirmCheckSheet(obj));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * API: QA/MaterialCheckSheet/CheckSheetRecords
     * @param qaCard
     * @return
     */
    @GetMapping("CheckSheetRecords")
    public ResponseEntity<ApiResponse<?>> getCheckSheetRecords(@RequestParam String qaCard) {
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.materialCheckSheetSvc.getCheckSheetRecords(qaCard));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("CheckSheetRecords/{id}")
    public ResponseEntity<ApiResponse<?>> deleteCheckSheetRecord(@PathVariable Integer id) {
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.materialCheckSheetSvc.deleteCheckSheetRecord(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * API: QA/MaterialCheckSheet/ScanMaterial
     * @param material
     * @return
     */
    @PostMapping("ScanMaterial")
    public ResponseEntity<ApiResponse<?>> scanMaterial(@RequestBody QaMaterialCheckSheetRecord material) {
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.materialCheckSheetSvc.saveCheckSheetRecord(material));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
