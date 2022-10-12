package lab.ds2022_assignment_1.services.api;

import lab.ds2022_assignment_1.controllers.handlers.requests.DeviceDetailsRequest;
import lab.ds2022_assignment_1.controllers.handlers.requests.LinkDeviceRequest;
import lab.ds2022_assignment_1.dtos.DeviceDTO;
import lab.ds2022_assignment_1.model.entities.HourlyEnergyConsumption;
import lab.ds2022_assignment_1.model.exceptions.DuplicateDataException;
import lab.ds2022_assignment_1.model.exceptions.EntityNotFoundException;

import java.util.Date;
import java.util.List;

public interface DeviceService {

    /**
     * Create a device and link it to a user account.
     *
     * @param request the {@link DeviceDetailsRequest}
     * @return the device that was added
     */
    DeviceDTO addDevice(final DeviceDetailsRequest request);

    /**
     * Create a user-device mapping.
     *
     * @param accountId the account id of the owner of the device
     * @param request   {@link LinkDeviceRequest}
     * @throws EntityNotFoundException if no user account or no device exists with the given id
     */
    void linkDeviceToUser(final String accountId, final LinkDeviceRequest request) throws EntityNotFoundException, DuplicateDataException;

    /**
     * Find a device by its unique id.
     *
     * @param id the device id
     * @return {@link DeviceDTO}
     * @throws EntityNotFoundException if no device can be found by the given id
     */
    DeviceDTO findDeviceById(final String id) throws EntityNotFoundException;

    /**
     * Find a list of devices belonging to the given user.
     *
     * @param accountId the id of the user account
     * @return a list of {@link DeviceDTO}
     * @throws EntityNotFoundException if no user exists with the given id
     */
    List<DeviceDTO> findDevicesByAccountId(final String accountId) throws EntityNotFoundException;

    /**
     * Remove (unlink) a device from a user account.
     *
     * @param deviceId the id of the device
     * @throws EntityNotFoundException if no device exists with the given id
     */
    void deleteDevice(final String deviceId) throws EntityNotFoundException;

    /**
     * Update an existing device with the given information.
     *
     * @param deviceId the id of the device
     * @throws EntityNotFoundException if no device exists with the given id
     * @throws DuplicateDataException  if the user already has a device registered with the same address
     */
    DeviceDTO updateDevice(final String deviceId, final DeviceDetailsRequest request) throws EntityNotFoundException, DuplicateDataException;

    /**
     * Find the hourly energy consumption of a given device for a given day.
     *
     * @param accountId the id of the user's account
     * @param deviceId  the id of the device
     * @param date      the date for which the energy consumption is listed
     * @return a list of {@link HourlyEnergyConsumption} for the given device
     * @throws EntityNotFoundException if no device exists with the given id
     */
    List<HourlyEnergyConsumption> findHourlyDeviceEnergyConsumption(final String accountId, final String deviceId, final Date date) throws EntityNotFoundException;

    /**
     * Find the hourly energy consumption of all devices of a given user for a given day.
     *
     * @param accountId the id of the user's account
     * @param date      the date for which the energy consumption is listed
     * @return a list of {@link HourlyEnergyConsumption} for every device associated to the given user account
     */
    List<List<HourlyEnergyConsumption>> findHourlyEnergyConsumption(final String accountId, final Date date);
}
