package lab.ds2022_assignment_1.dtos.mappers;

import lab.ds2022_assignment_1.controllers.handlers.requests.DeviceDetailsRequest;
import lab.ds2022_assignment_1.dtos.DeviceDTO;
import lab.ds2022_assignment_1.model.entities.Device;

public class DeviceMapper {
    public Device mapRequestToEntity(DeviceDetailsRequest request) {
        return Device.builder()
                .address(request.getAddress())
                .description(request.getDescription())
                .build();
    }

    public DeviceDTO mapEntityToDto(Device device) {
        final AccountMapper accountMapper = new AccountMapper();

        return DeviceDTO.builder()
                .id(device.getId().toString())
                .account(accountMapper.mapEntityToDto(device.getAccount()))
                .address(device.getAddress())
                .description(device.getDescription())
                .maxHourlyEnergyConsumption(device.getMaxHourlyEnergyConsumption())
                .hourlyEnergyConsumption(device.getHourlyEnergyConsumption())
                .build();
    }
}
