package lab.ds2022_assignment_1.dtos.mappers;

import lab.ds2022_assignment_1.controllers.handlers.requests.DeviceData;
import lab.ds2022_assignment_1.dtos.DeviceDTO;
import lab.ds2022_assignment_1.model.entities.Device;

public class DeviceMapper implements Mapper<Device, DeviceData, DeviceDTO> {
    @Override
    public Device mapToEntity(DeviceData data) {
        return Device.builder()
                .address(data.getAddress())
                .description(data.getDescription())
                .build();
    }

    @Override
    public Device mapDtoToEntity(DeviceDTO dto) {
        return Device.builder()
                .address(dto.getAddress())
                .description(dto.getDescription())
                .build();
    }

    @Override
    public DeviceDTO mapToDto(Device entity) {
        return DeviceDTO.builder()
                .id(entity.getId().toString())
                .accountId(entity.getAccount() != null ? entity.getAccount().getId().toString() : null)
                .address(entity.getAddress())
                .description(entity.getDescription())
                .build();
    }
}
