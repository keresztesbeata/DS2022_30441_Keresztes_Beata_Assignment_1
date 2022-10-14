package lab.ds2022_assignment_1.services.api;

import lab.ds2022_assignment_1.controllers.handlers.requests.EnergyConsumptionData;
import lab.ds2022_assignment_1.dtos.EnergyConsumptionDTO;
import lab.ds2022_assignment_1.model.entities.EnergyConsumption;
import lab.ds2022_assignment_1.model.exceptions.EntityNotFoundException;
import lab.ds2022_assignment_1.model.exceptions.InvalidAccessException;

import java.time.LocalDate;
import java.util.List;

public interface EnergyConsumptionService {
    /**
     * Find the hourly energy consumption of a given device for a given day.
     *
     * @param accountId the id of the user's account
     * @param deviceId  the id of the device
     * @param date      the date for which the energy consumption is listed
     * @return a list of {@link EnergyConsumption} for the given device
     * @throws EntityNotFoundException if no device exists with the given id
     * @throws InvalidAccessException  if the device belongs to a different user
     */
    List<EnergyConsumptionDTO> findHourlyDeviceEnergyConsumption(final String accountId, final String deviceId, final LocalDate date) throws EntityNotFoundException, InvalidAccessException;

    /**
     * Find the hourly energy consumption of all devices of a given user for a given day.
     *
     * @param accountId the id of the user's account
     * @param date      the date for which the energy consumption is listed
     * @return a list of {@link EnergyConsumption} for every device associated to the given user account
     */
    List<EnergyConsumptionDTO> findHourlyEnergyConsumption(final String accountId, final LocalDate date);

    /**
     * Register the enrgy consumption data.
     *
     * @param data {@link EnergyConsumptionData} representing the energy consumed by a given device in the specified hour.
     * @return {@link EnergyConsumptionDTO}
     * @throws EntityNotFoundException if no device exists with the given id
     */
    EnergyConsumptionDTO registerEnergyConsumption(final EnergyConsumptionData data) throws EntityNotFoundException;
}
