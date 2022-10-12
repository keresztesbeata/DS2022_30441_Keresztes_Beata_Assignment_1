package lab.ds2022_assignment_1.dtos.mappers;

import lab.ds2022_assignment_1.controllers.handlers.requests.EnergyConsumptionDetailsRequest;
import lab.ds2022_assignment_1.dtos.EnergyConsumptionDTO;
import lab.ds2022_assignment_1.model.entities.EnergyConsumption;

public class EnergyConsumptionMapper {
    public EnergyConsumption mapRequestToEntity(EnergyConsumptionDetailsRequest request) {
        return EnergyConsumption.builder()
                .energy(request.getEnergy())
                .timestamp(request.getTimestamp())
                .build();
    }

    public EnergyConsumptionDTO mapEntityToDto(EnergyConsumption energyConsumption) {
        return EnergyConsumptionDTO.builder()
                .deviceId(energyConsumption.getDevice().getId().toString())
                .timestamp(energyConsumption.getTimestamp())
                .energy(energyConsumption.getEnergy())
                .build();
    }
}
