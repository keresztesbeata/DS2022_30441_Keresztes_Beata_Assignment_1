package lab.ds2022_assignment_1.controllers;

import lab.ds2022_assignment_1.dtos.AccountDTO;
import lab.ds2022_assignment_1.dtos.DeviceDTO;
import lab.ds2022_assignment_1.model.entities.EnergyConsumption;
import lab.ds2022_assignment_1.model.exceptions.EntityNotFoundException;
import lab.ds2022_assignment_1.services.api.AccountService;
import lab.ds2022_assignment_1.services.api.DeviceService;
import lab.ds2022_assignment_1.services.api.EnergyConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static lab.ds2022_assignment_1.controllers.Constants.*;

@RestController
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

    @GetMapping(ENERGY_CONSUMPTION_PATH)
    public ResponseEntity<List<EnergyConsumption>> getClientEnergyConsumption(@RequestParam LocalDate date) throws EntityNotFoundException {
        final AccountDTO accountDTO = accountService.getCurrentUserAccount();

        return ResponseEntity.ok(energyConsumptionService.findHourlyEnergyConsumption(accountDTO.getId(), date));
    }

    @GetMapping(DEVICE_ENERGY_CONSUMPTION_PATH)
    public ResponseEntity<List<EnergyConsumption>> getClientDeviceEnergyConsumption(@PathVariable(DEVICE_ID) String deviceId, @RequestParam LocalDate date) throws EntityNotFoundException {
        final AccountDTO accountDTO = accountService.getCurrentUserAccount();

        return ResponseEntity.ok(energyConsumptionService.findHourlyDeviceEnergyConsumption(accountDTO.getId(), deviceId, date));
    }
}
