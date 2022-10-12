package lab.ds2022_assignment_1.services.impl;

import lab.ds2022_assignment_1.controllers.handlers.requests.DeviceDetailsRequest;
import lab.ds2022_assignment_1.controllers.handlers.requests.LinkDeviceRequest;
import lab.ds2022_assignment_1.dtos.DeviceDTO;
import lab.ds2022_assignment_1.dtos.mappers.DeviceMapper;
import lab.ds2022_assignment_1.model.entities.Account;
import lab.ds2022_assignment_1.model.entities.Device;
import lab.ds2022_assignment_1.model.exceptions.DuplicateDataException;
import lab.ds2022_assignment_1.model.exceptions.EntityNotFoundException;
import lab.ds2022_assignment_1.repositories.AccountRepository;
import lab.ds2022_assignment_1.repositories.DeviceRepository;
import lab.ds2022_assignment_1.services.api.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceDTO addDevice(final DeviceDetailsRequest request) {
        final Device savedDevice = deviceRepository.save(deviceMapper.mapRequestToEntity(request));
        log.debug("Device with id {} was successfully added!", savedDevice.getId());

        return deviceMapper.mapEntityToDto(savedDevice);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void linkDeviceToUser(final String accountId, final LinkDeviceRequest request) throws EntityNotFoundException, DuplicateDataException {
        Account account =
                accountRepository.findById(UUID.fromString(accountId))
                        .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_ACCOUNT_ERR_MSG));
        Device device = deviceRepository.findById(UUID.fromString(request.getDeviceId()))
                .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_DEVICE_ERR_MSG));

        if (!deviceRepository.findByAccountAndAddress(account, device.getAddress()).isEmpty()) {
            throw new DuplicateDataException(String.format(DUPLICATE_ADDRESS_ERR_MSG, account.getUsername(), device.getAddress()));
        }

        device.setAccount(account);
        final Device savedDevice = deviceRepository.save(device);

        account.addDevice(savedDevice);
        accountRepository.save(account);

        log.debug("Device with id {} was successfully linked to the account with id {}!", device.getId(), accountId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceDTO findDeviceById(final String id) throws EntityNotFoundException {
        return deviceMapper.mapEntityToDto(deviceRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_DEVICE_ERR_MSG)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DeviceDTO> findDevicesByAccountId(final String accountId) throws EntityNotFoundException {
        final Account account =
                accountRepository.findById(UUID.fromString(accountId))
                        .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_ACCOUNT_ERR_MSG));

        return deviceRepository.findByAccount(account)
                .stream()
                .map(deviceMapper::mapEntityToDto)
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
    public DeviceDTO updateDevice(final String deviceId, final DeviceDetailsRequest request) throws EntityNotFoundException, DuplicateDataException {
        final Device oldDevice =
                deviceRepository.findById(UUID.fromString(deviceId))
                        .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_DEVICE_ERR_MSG));
        Device newDevice = deviceMapper.mapRequestToEntity(request);

        if (!newDevice.getAddress().equals(oldDevice.getAddress()) &&
                !deviceRepository.findByAccountAndAddress(oldDevice.getAccount(), newDevice.getAddress()).isEmpty()) {
            throw new DuplicateDataException(String.format(DUPLICATE_ADDRESS_ERR_MSG, oldDevice.getAccount().getUsername(), newDevice.getAddress()));
        }

        newDevice.setId(UUID.fromString(deviceId));
        final Device savedDevice = deviceRepository.save(newDevice);
        log.debug("Device with id {} was successfully updated!", deviceId);

        return deviceMapper.mapEntityToDto(savedDevice);
    }
}
