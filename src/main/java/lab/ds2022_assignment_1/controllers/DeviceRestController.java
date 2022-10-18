package lab.ds2022_assignment_1.controllers;

import lab.ds2022_assignment_1.controllers.handlers.requests.DeviceData;
import lab.ds2022_assignment_1.controllers.handlers.requests.LinkDeviceRequest;
import lab.ds2022_assignment_1.controllers.handlers.requests.SearchCriteria;
import lab.ds2022_assignment_1.controllers.handlers.requests.ValidUUID;
import lab.ds2022_assignment_1.dtos.DeviceDTO;
import lab.ds2022_assignment_1.model.exceptions.DuplicateDataException;
import lab.ds2022_assignment_1.model.exceptions.EntityNotFoundException;
import lab.ds2022_assignment_1.model.exceptions.InvalidFilterException;
import lab.ds2022_assignment_1.services.api.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static lab.ds2022_assignment_1.controllers.Constants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@Validated
public class DeviceRestController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping(value = DEVICES_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceDTO> addDevice(@RequestBody @Valid DeviceData data) {
        return new ResponseEntity<>(deviceService.addDevice(data), HttpStatus.CREATED);
    }

    @GetMapping(DEVICES_PATH)
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        return ok(deviceService.findDevices());
    }

    @GetMapping(ACCOUNT_DEVICES_PATH)
    public ResponseEntity<List<DeviceDTO>> getDevicesByAccountId(@PathVariable(ACCOUNT_ID) @ValidUUID String accountId) throws EntityNotFoundException {
        return ok(deviceService.findDevicesByAccountId(accountId));
    }

    @GetMapping(DEVICES_FILTER_PATH)
    public ResponseEntity<List<DeviceDTO>> filterDevices(@RequestParam String filterKey, @RequestParam String filterValue) throws InvalidFilterException {
        return ok(deviceService.filterDevices(new SearchCriteria(filterKey, filterValue)));
    }

    @PutMapping(value = DEVICE_ID_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceDTO> updateDevice(@PathVariable(DEVICE_ID) @ValidUUID String deviceId,
                                                  @RequestBody @Valid DeviceData data) throws DuplicateDataException, EntityNotFoundException {
        return ok(deviceService.updateDevice(deviceId, data));
    }

    @GetMapping(DEVICE_ID_PATH)
    public ResponseEntity<DeviceDTO> getDeviceById(@PathVariable(DEVICE_ID) @ValidUUID String deviceId) throws EntityNotFoundException {
        return ok(deviceService.findDeviceById(deviceId));
    }

    @DeleteMapping(DEVICE_ID_PATH)
    public ResponseEntity deleteDevice(@PathVariable(DEVICE_ID) @ValidUUID String deviceId) throws EntityNotFoundException {
        deviceService.deleteDevice(deviceId);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = LINK_DEVICE_PATH, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity linkDevice(@RequestBody @Valid LinkDeviceRequest request) throws DuplicateDataException, EntityNotFoundException {
        deviceService.linkDeviceToUser(request);

        return ResponseEntity.ok().build();
    }
}
