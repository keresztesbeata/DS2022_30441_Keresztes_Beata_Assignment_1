package lab.ds2022_assignment_1.services.impl;

import lab.ds2022_assignment_1.controllers.handlers.requests.EnergyConsumptionData;
import lab.ds2022_assignment_1.dtos.EnergyConsumptionDTO;
import lab.ds2022_assignment_1.dtos.mappers.EnergyConsumptionMapper;
import lab.ds2022_assignment_1.model.entities.Device;
import lab.ds2022_assignment_1.model.entities.EnergyConsumption;
import lab.ds2022_assignment_1.model.entities.TotalEnergyConsumption;
import lab.ds2022_assignment_1.model.exceptions.EntityNotFoundException;
import lab.ds2022_assignment_1.model.exceptions.InvalidAccessException;
import lab.ds2022_assignment_1.model.exceptions.InvalidDataException;
import lab.ds2022_assignment_1.repositories.DeviceRepository;
import lab.ds2022_assignment_1.repositories.EnergyConsumptionRepository;
import lab.ds2022_assignment_1.services.api.EnergyConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EnergyConsumptionServiceImpl implements EnergyConsumptionService {
    @Autowired
    private EnergyConsumptionRepository repository;
    @Autowired
    private DeviceRepository deviceRepository;
    private final EnergyConsumptionMapper mapper = new EnergyConsumptionMapper();
    private static final String NOT_EXISTENT_DEVICE_ERR_MSG = "This device doesn't exist!";
    private static final String CANNOT_ACCESS_DEVICE_ERR_MSG = "You cannot view the energy consumption of this device! It belongs to a different user.";
    private static final String INVALID_DATE_ERR_MSG = "Invalid date! You cannot select a date in the future.";

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EnergyConsumptionDTO> findHourlyDeviceEnergyConsumption(final String accountId, final String deviceId, final LocalDate date) throws EntityNotFoundException, InvalidAccessException, InvalidDataException {
        final Device device = deviceRepository.findById(UUID.fromString(deviceId))
                .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_DEVICE_ERR_MSG));

        if (!device.getAccount().getId().toString().equals(accountId)) {
            throw new InvalidAccessException(CANNOT_ACCESS_DEVICE_ERR_MSG);
        }

        if (date.isAfter(LocalDate.now())) {
            throw new InvalidDataException(INVALID_DATE_ERR_MSG);
        }

        return repository.findByAccountIdAndDeviceIdAndTimestamp(accountId, deviceId, date)
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TotalEnergyConsumption> findHourlyTotalEnergyConsumption(final String accountId, final LocalDate date) throws InvalidDataException {
        if (date.isAfter(LocalDate.now())) {
            throw new InvalidDataException(INVALID_DATE_ERR_MSG);
        }

        return repository.findTotalEnergyConsumptionByAccountIdAndTimestamp(accountId, date);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EnergyConsumptionDTO registerEnergyConsumption(EnergyConsumptionData data) throws EntityNotFoundException {
        EnergyConsumption energyConsumption = mapper.mapToEntity(data);
        Device device = deviceRepository.findById(UUID.fromString(data.getDeviceId()))
                .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_DEVICE_ERR_MSG));

        energyConsumption.setDevice(device);
        final EnergyConsumption savedEntity = repository.save(energyConsumption);

        return mapper.mapToDto(savedEntity);
    }
}
