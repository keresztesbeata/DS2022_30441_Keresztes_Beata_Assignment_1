package lab.ds2022_assignment_1.controllers;

import lab.ds2022_assignment_1.controllers.handlers.requests.DeviceData;
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
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class DeviceRestController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping(value = DEVICES_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public DeviceDTO addDevice(@Valid @RequestBody DeviceData request) {
        return deviceService.addDevice(request);
    }

    @GetMapping(DEVICES_PATH)
    public List<DeviceDTO> getDevicesByAccountId(@RequestParam String accountId) throws EntityNotFoundException {
        return deviceService.findDevicesByAccountId(accountId);
    }

    @PutMapping(value = DEVICE_ID_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public DeviceDTO updateDevice(@PathVariable(DEVICE_ID) String deviceId,
                                  @RequestBody DeviceData request) throws DuplicateDataException, EntityNotFoundException {
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

    @PostMapping(value = LINK_DEVICE_PATH, consumes = APPLICATION_JSON_VALUE)
    public void linkDevice(@RequestBody LinkDeviceRequest request) throws DuplicateDataException, EntityNotFoundException {
        deviceService.linkDeviceToUser(request);
    }
}
