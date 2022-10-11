package lab.ds2022_assignment_1.controllers;

import lab.ds2022_assignment_1.controllers.handlers.requests.DeviceDetailsRequest;
import lab.ds2022_assignment_1.controllers.handlers.requests.LinkDeviceRequest;
import lab.ds2022_assignment_1.dtos.DeviceDTO;
import lab.ds2022_assignment_1.model.exceptions.DuplicateDataException;
import lab.ds2022_assignment_1.model.exceptions.EntityNotFoundException;
import lab.ds2022_assignment_1.services.api.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static lab.ds2022_assignment_1.controllers.Constants.*;

@RestController
public class DeviceRestController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping(DEVICES_PATH)
    public DeviceDTO addDevice(@Valid @RequestBody DeviceDetailsRequest request) {
        return deviceService.addDevice(request);
    }

    @GetMapping(DEVICES_PATH)
    public List<DeviceDTO> getDevicesByAccountId(@RequestParam String accountId) throws EntityNotFoundException {
        return deviceService.findDevicesByAccountId(accountId);
    }

    @PutMapping(DEVICE_ID_PATH)
    public DeviceDTO updateDevice(@PathVariable(DEVICE_ID) String deviceId,
                                  @RequestBody DeviceDetailsRequest request) throws DuplicateDataException, EntityNotFoundException {
        return deviceService.updateDevice(deviceId, request);
    }

    @GetMapping(DEVICE_ID_PATH)
    public DeviceDTO getDeviceById(@PathVariable(DEVICE_ID) String deviceId) throws EntityNotFoundException {
        return deviceService.findDeviceById(deviceId);
    }

    @DeleteMapping(DEVICE_ID_PATH)
    public void deleteDevice(@PathVariable(DEVICE_ID) String deviceId) throws EntityNotFoundException {
        deviceService.deleteDevice(deviceId);
    }

    @PostMapping(LINK_DEVICE_PATH)
    public void linkDevice(@PathVariable(ACCOUNT_ID) String accountId, @RequestBody LinkDeviceRequest request) throws DuplicateDataException, EntityNotFoundException {
        deviceService.linkDeviceToUser(accountId, request);
    }
}
