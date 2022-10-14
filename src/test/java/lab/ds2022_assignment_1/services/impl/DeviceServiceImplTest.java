package lab.ds2022_assignment_1.services.impl;

import lab.ds2022_assignment_1.controllers.handlers.requests.DeviceData;
import lab.ds2022_assignment_1.controllers.handlers.requests.LinkDeviceRequest;
import lab.ds2022_assignment_1.model.entities.Account;
import lab.ds2022_assignment_1.model.entities.Device;
import lab.ds2022_assignment_1.model.entities.UserRole;
import lab.ds2022_assignment_1.model.exceptions.DuplicateDataException;
import lab.ds2022_assignment_1.model.exceptions.EntityNotFoundException;
import lab.ds2022_assignment_1.repositories.AccountRepository;
import lab.ds2022_assignment_1.repositories.DeviceRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static lab.ds2022_assignment_1.services.Constants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class DeviceServiceImplTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private DeviceRepository deviceRepository;
    @InjectMocks
    private DeviceServiceImpl service;
    private Device device;
    private Account account;
    private DeviceData request;

    @BeforeEach
    void setUp() {
        account = Account.builder()
                .id(UUID.fromString(ID_1))
                .name(NAME_1)
                .username(USERNAME_1)
                .password(new BCryptPasswordEncoder().encode(PASSWORD_1))
                .role(UserRole.CLIENT)
                .build();

        device = Device.builder()
                .id(UUID.fromString(DEVICE_ID_1))
                .address(ADDRESS_1)
                .description(DESCRIPTION)
                .account(account)
                .build();

        Set<Device> devices = new HashSet<>();
        devices.add(device);
        account.setDevices(devices);

        request = new DeviceData();
        request.setAddress(ADDRESS_1);
        request.setDescription(DESCRIPTION);
    }

    @Test
    @SneakyThrows
    void addDevice() {
        Mockito.when(deviceRepository.save(any(Device.class)))
                .thenReturn(device);

        // add a device
        Assertions.assertEquals(DEVICE_ID_1, service.addDevice(request).getId());

        Mockito.when(accountRepository.findById(UUID.fromString(ID_1)))
                .thenReturn(Optional.of(account));
        Mockito.when(accountRepository.save(any(Account.class)))
                .thenReturn(account);

        LinkDeviceRequest linkDeviceRequest = new LinkDeviceRequest();
        linkDeviceRequest.setDeviceId(DEVICE_ID_1);
        linkDeviceRequest.setAccountId(ID_1);

        // map the device to an existing account
        Assertions.assertDoesNotThrow(() -> service.linkDeviceToUser(linkDeviceRequest));

        Mockito.when(deviceRepository.findByAccountAndAddress(account, ADDRESS_1))
                .thenReturn(List.of(device));

        // map the device to an account which already has a device with the same address
        Assertions.assertThrows(DuplicateDataException.class, () -> service.linkDeviceToUser(linkDeviceRequest));

        verify(deviceRepository, times(2)).findByAccountAndAddress(account, ADDRESS_1);
        verify(accountRepository, times(3)).findById(UUID.fromString(ID_1));
    }

    @Test
    @SneakyThrows
    void findDeviceById() {
        Mockito.when(deviceRepository.findById(UUID.fromString(DEVICE_ID_1)))
                .thenReturn(Optional.of(device));

        Assertions.assertEquals(DEVICE_ID_1, service.findDeviceById(DEVICE_ID_1).getId());
        verify(deviceRepository).findById(UUID.fromString(DEVICE_ID_1));

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.findDeviceById(DEVICE_ID_2));
        verify(deviceRepository).findById(UUID.fromString(DEVICE_ID_2));
    }

    @Test
    @SneakyThrows
    void findDevicesByAccountId() {
        final Device device2 = Device.builder()
                .id(UUID.fromString(DEVICE_ID_2))
                .address(ADDRESS_2)
                .description(DESCRIPTION)
                .account(account)
                .build();

        Mockito.when(accountRepository.findById(UUID.fromString(ID_1)))
                .thenReturn(Optional.of(account));

        Mockito.when(deviceRepository.findByAccount(any(Account.class)))
                .thenReturn(List.of(device, device2));

        Assertions.assertDoesNotThrow(() -> service.findDevicesByAccountId(ID_1));
        Assertions.assertEquals(2, service.findDevicesByAccountId(ID_1).size());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.findDevicesByAccountId(ID_2));
    }

    @Test
    void removeDevice() {
        Mockito.when(deviceRepository.findById(UUID.fromString(DEVICE_ID_1)))
                .thenReturn(Optional.of(device));

        Assertions.assertDoesNotThrow(() -> service.deleteDevice(DEVICE_ID_1));
        verify(deviceRepository).delete(any(Device.class));

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.deleteDevice(DEVICE_ID_2));
    }

    @Test
    @SneakyThrows
    void updateDevice() {
        Mockito.when(deviceRepository.save(any(Device.class)))
                .thenReturn(device);
        Assertions.assertEquals(DEVICE_ID_1, service.addDevice(request).getId());

        Mockito.when(deviceRepository.findById(UUID.fromString(DEVICE_ID_1)))
                .thenReturn(Optional.of(device));

        Assertions.assertEquals(ADDRESS_1, service.updateDevice(DEVICE_ID_1, request).getAddress());
        verify(accountRepository).findById(UUID.fromString(ID_1));

        Mockito.when(deviceRepository.findByAccountAndAddress(any(Account.class), eq(ADDRESS_2)))
                .thenReturn(List.of(device));

        request.setAddress(ADDRESS_2);
        Assertions.assertThrows(DuplicateDataException.class, () -> service.updateDevice(DEVICE_ID_1, request));
    }
}