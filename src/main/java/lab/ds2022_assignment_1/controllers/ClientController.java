package lab.ds2022_assignment_1.controllers;

import lab.ds2022_assignment_1.controllers.handlers.requests.EnergyConsumptionData;
import lab.ds2022_assignment_1.controllers.handlers.requests.ValidUUID;
import lab.ds2022_assignment_1.dtos.AccountDTO;
import lab.ds2022_assignment_1.dtos.DeviceDTO;
import lab.ds2022_assignment_1.dtos.EnergyConsumptionDTO;
import lab.ds2022_assignment_1.model.exceptions.EntityNotFoundException;
import lab.ds2022_assignment_1.model.exceptions.InvalidAccessException;
import lab.ds2022_assignment_1.services.api.AccountService;
import lab.ds2022_assignment_1.services.api.DeviceService;
import lab.ds2022_assignment_1.services.api.EnergyConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static lab.ds2022_assignment_1.controllers.Constants.*;

@RestController
@Validated
public class ClientController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private EnergyConsumptionService energyConsumptionService;

    @GetMapping(CLIENT_ACCOUNT_PATH)
    public ResponseEntity<AccountDTO> getClientAccount() throws EntityNotFoundException {
        return ResponseEntity.ok(accountService.getCurrentUserAccount());
    }

    @GetMapping(CLIENT_DEVICES_PATH)
    public ResponseEntity<List<DeviceDTO>> getClientDevices() throws EntityNotFoundException {
        final AccountDTO accountDTO = accountService.getCurrentUserAccount();

        return ResponseEntity.ok(deviceService.findDevicesByAccountId(accountDTO.getId()));
    }

    @GetMapping(CLIENT_DEVICE_ID_PATH)
    public ResponseEntity<DeviceDTO> getClientDeviceById(@PathVariable(DEVICE_ID) @ValidUUID String deviceId) throws EntityNotFoundException, InvalidAccessException {
        final AccountDTO accountDTO = accountService.getCurrentUserAccount();

        return ResponseEntity.ok(deviceService.findDeviceByIdAndAccountId(deviceId, accountDTO.getId()));
    }

    @GetMapping(ENERGY_CONSUMPTION_PATH)
    public ResponseEntity<List<EnergyConsumptionDTO>> getClientEnergyConsumption(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") @Valid LocalDate date) throws EntityNotFoundException {
        final AccountDTO accountDTO = accountService.getCurrentUserAccount();

        return ResponseEntity.ok(energyConsumptionService.findHourlyEnergyConsumption(accountDTO.getId(), date));
    }

    @GetMapping(DEVICE_ENERGY_CONSUMPTION_PATH)
    public ResponseEntity<List<EnergyConsumptionDTO>> getClientDeviceEnergyConsumption(@PathVariable(DEVICE_ID) @ValidUUID String deviceId,
                                                                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") @Valid LocalDate date) throws EntityNotFoundException, InvalidAccessException {
        final AccountDTO accountDTO = accountService.getCurrentUserAccount();

        return ResponseEntity.ok(energyConsumptionService.findHourlyDeviceEnergyConsumption(accountDTO.getId(), deviceId, date));
    }

    @PostMapping(value = ENERGY_CONSUMPTION_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnergyConsumptionDTO> registerEnergyConsumption(@RequestBody @Valid EnergyConsumptionData data) throws EntityNotFoundException {
        return ResponseEntity.ok(energyConsumptionService.registerEnergyConsumption(data));
    }
}
