package lab.ds2022_assignment_1.controllers;

import lab.ds2022_assignment_1.dtos.AccountDTO;
import lab.ds2022_assignment_1.dtos.DeviceDTO;
import lab.ds2022_assignment_1.model.entities.HourlyEnergyConsumption;
import lab.ds2022_assignment_1.model.exceptions.EntityNotFoundException;
import lab.ds2022_assignment_1.services.api.AccountService;
import lab.ds2022_assignment_1.services.api.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static lab.ds2022_assignment_1.controllers.Constants.*;

@RestController
public class ClientController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private DeviceService deviceService;

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
    public ResponseEntity<List<List<HourlyEnergyConsumption>>> getClientEnergyConsumption(@RequestParam Date date) throws EntityNotFoundException {
        final AccountDTO accountDTO = accountService.getCurrentUserAccount();

        return ResponseEntity.ok(deviceService.findHourlyEnergyConsumption(accountDTO.getId(), date));
    }

    @GetMapping(DEVICE_ENERGY_CONSUMPTION_PATH)
    public ResponseEntity<List<HourlyEnergyConsumption>> getClientDeviceEnergyConsumption(@PathVariable(DEVICE_ID) String deviceId, @RequestParam Date date) throws EntityNotFoundException {
        final AccountDTO accountDTO = accountService.getCurrentUserAccount();

        return ResponseEntity.ok(deviceService.findHourlyDeviceEnergyConsumption(accountDTO.getId(), deviceId, date));
    }
}
