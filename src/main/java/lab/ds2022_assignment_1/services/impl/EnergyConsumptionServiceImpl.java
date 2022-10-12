package lab.ds2022_assignment_1.services.impl;

import lab.ds2022_assignment_1.dtos.mappers.EnergyConsumptionMapper;
import lab.ds2022_assignment_1.model.entities.EnergyConsumption;
import lab.ds2022_assignment_1.model.exceptions.EntityNotFoundException;
import lab.ds2022_assignment_1.repositories.DeviceRepository;
import lab.ds2022_assignment_1.repositories.EnergyConsumptionRepository;
import lab.ds2022_assignment_1.services.api.EnergyConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnergyConsumptionServiceImpl implements EnergyConsumptionService {
    @Autowired
    private EnergyConsumptionRepository repository;
    @Autowired
    private DeviceRepository deviceRepository;
    private final EnergyConsumptionMapper mapper = new EnergyConsumptionMapper();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EnergyConsumption> findHourlyDeviceEnergyConsumption(final String accountId, final String deviceId, final LocalDate date) throws EntityNotFoundException {
        return repository.findByAccountIdDeviceIdAndTimestamp(accountId, deviceId, date);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EnergyConsumption> findHourlyEnergyConsumption(final String accountId, final LocalDate date) {
        return repository.findByAccountIdAndTimestamp(accountId, date);
    }
}
