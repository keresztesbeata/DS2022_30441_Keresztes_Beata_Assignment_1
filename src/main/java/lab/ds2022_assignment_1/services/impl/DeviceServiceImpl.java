package lab.ds2022_assignment_1.services.impl;

import lab.ds2022_assignment_1.controllers.handlers.requests.DeviceData;
import lab.ds2022_assignment_1.controllers.handlers.requests.LinkDeviceRequest;
import lab.ds2022_assignment_1.controllers.handlers.requests.SearchCriteria;
import lab.ds2022_assignment_1.dtos.DeviceDTO;
import lab.ds2022_assignment_1.dtos.mappers.DeviceMapper;
import lab.ds2022_assignment_1.model.entities.Account;
import lab.ds2022_assignment_1.model.entities.Device;
import lab.ds2022_assignment_1.model.exceptions.DuplicateDataException;
import lab.ds2022_assignment_1.model.exceptions.EntityNotFoundException;
import lab.ds2022_assignment_1.model.exceptions.InvalidAccessException;
import lab.ds2022_assignment_1.model.exceptions.InvalidFilterException;
import lab.ds2022_assignment_1.repositories.AccountRepository;
import lab.ds2022_assignment_1.repositories.DeviceRepository;
import lab.ds2022_assignment_1.services.api.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper = new DeviceMapper();
    private static final String NOT_EXISTENT_ACCOUNT_ERR_MSG = "This account doesn't exist!";
    private static final String NOT_EXISTENT_DEVICE_ERR_MSG = "This device doesn't exist!";
    private static final String DUPLICATE_ADDRESS_ERR_MSG = "Duplicate address! The user %s already has a smart device linked to the address %s.";
    private static final String CANNOT_ACCESS_DEVICE_ERR_MSG = "You cannot access this device, it belongs to a different user!";

    @Override
    public List<DeviceDTO> filterDevices(SearchCriteria searchCriteria) throws InvalidFilterException {
        final Specification<Device> specification = new DeviceSpecification(searchCriteria);
        ((FilterValidator) specification).validate(searchCriteria);

        return deviceRepository.findAll(specification)
                .stream()
                .map(deviceMapper::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DeviceDTO> findDevices() {
        return deviceRepository.findAll().stream()
                .map(deviceMapper::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceDTO addDevice(final DeviceData request) {
        final Device savedDevice = deviceRepository.save(deviceMapper.mapToEntity(request));
        log.debug("Device with id {} was successfully added!", savedDevice.getId());

        return deviceMapper.mapToDto(savedDevice);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void linkDeviceToUser(final LinkDeviceRequest request) throws EntityNotFoundException, DuplicateDataException {
        Account account =
                accountRepository.findById(UUID.fromString(request.getAccountId()))
                        .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_ACCOUNT_ERR_MSG));
        Device device = deviceRepository.findById(UUID.fromString(request.getDeviceId()))
                .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_DEVICE_ERR_MSG));

        if (!deviceRepository.findByAccountAndAddress(account, device.getAddress()).isEmpty()) {
            throw new DuplicateDataException(String.format(DUPLICATE_ADDRESS_ERR_MSG, account.getUsername(), device.getAddress()));
        }

        device.setAccount(account);
        deviceRepository.save(device);

        log.debug("Device with id {} was successfully linked to the account with id {}!", device.getId(), account.getId());
    }

    @Override
    public DeviceDTO findDeviceById(String deviceId) throws EntityNotFoundException {
        return deviceMapper.mapToDto(deviceRepository.findById(UUID.fromString(deviceId))
                .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_DEVICE_ERR_MSG)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceDTO findDeviceByIdAndAccountId(final String deviceId, final String accountId) throws EntityNotFoundException, InvalidAccessException {
        final Device device = deviceRepository.findById(UUID.fromString(deviceId))
                .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_DEVICE_ERR_MSG));

        if (!device.getAccount().getId().toString().equals(accountId)) {
            throw new InvalidAccessException(CANNOT_ACCESS_DEVICE_ERR_MSG);
        }

        return deviceMapper.mapToDto(device);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DeviceDTO> findDevicesByAccountId(final String accountId) throws EntityNotFoundException {
        if (accountId == null) {
            return deviceRepository.findAll()
                    .stream()
                    .map(deviceMapper::mapToDto)
                    .toList();
        }
        final Account account =
                accountRepository.findById(UUID.fromString(accountId))
                        .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_ACCOUNT_ERR_MSG));

        return deviceRepository.findByAccount(account)
                .stream()
                .map(deviceMapper::mapToDto)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteDevice(final String deviceId) throws EntityNotFoundException {
        final Device device = deviceRepository.findById(UUID.fromString(deviceId))
                .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_DEVICE_ERR_MSG));

        deviceRepository.delete(device);
        log.debug("Device with id {} was successfully deleted!", deviceId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceDTO updateDevice(final String deviceId, final DeviceData request) throws EntityNotFoundException, DuplicateDataException {
        final Device oldDevice =
                deviceRepository.findById(UUID.fromString(deviceId))
                        .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_DEVICE_ERR_MSG));
        Device newDevice = deviceMapper.mapToEntity(request);

        if (!newDevice.getAddress().equals(oldDevice.getAddress()) &&
                !deviceRepository.findByAccountAndAddress(oldDevice.getAccount(), newDevice.getAddress()).isEmpty()) {
            throw new DuplicateDataException(String.format(DUPLICATE_ADDRESS_ERR_MSG, oldDevice.getAccount().getUsername(), newDevice.getAddress()));
        }

        newDevice.setId(UUID.fromString(deviceId));
        final Device savedDevice = deviceRepository.save(newDevice);
        log.debug("Device with id {} was successfully updated!", deviceId);

        return deviceMapper.mapToDto(savedDevice);
    }
}
