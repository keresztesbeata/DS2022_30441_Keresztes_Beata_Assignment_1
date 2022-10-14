package lab.ds2022_assignment_1.dtos.mappers;

import lab.ds2022_assignment_1.controllers.handlers.requests.EnergyConsumptionData;
import lab.ds2022_assignment_1.dtos.EnergyConsumptionDTO;
import lab.ds2022_assignment_1.model.entities.EnergyConsumption;

public class EnergyConsumptionMapper implements Mapper<EnergyConsumption, EnergyConsumptionData, EnergyConsumptionDTO> {
    public EnergyConsumption mapToEntity(EnergyConsumptionData data) {
        return EnergyConsumption.builder()
                .energy(data.getEnergy())
                .timestamp(data.getTimestamp())
                .build();
    }

    public EnergyConsumptionDTO mapToDto(EnergyConsumption entity) {
        return EnergyConsumptionDTO.builder()
                .deviceId(entity.getDevice().getId().toString())
                .timestamp(entity.getTimestamp())
                .energy(entity.getEnergy())
                .build();
    }
}
