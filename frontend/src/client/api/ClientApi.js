import axios from "axios";
import {customError, SERVER_BASE_URL} from "../../common/Utils";
import {TOKEN} from "../../common/auth/Authentication";

const CLIENT_URL = SERVER_BASE_URL + "/client"
const DEVICES_URL = CLIENT_URL + "/devices"
const ENERGY_CONSUMPTION_URL = CLIENT_URL + "/energy_consumption"

/**
 * Fetch all devices associated to the currently logged in client.
 * @returns {Promise<AxiosResponse<any>>}
 */
export function GetDevicesOfClient() {
    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem(TOKEN),
        }
    }

    return axios.get(DEVICES_URL, config)
        .then((response) => {
            if (response.status === 200) {
                return response.data;
            }
        })
        .catch(error => {
            if (error.response.status !== 200) {
                // failed to retrieve data
                throw customError(error.response.data);
            }
        })
}

/**
 * Fetch the total energy consumption of every device associated to the currently logged in client for each hour.
 * @returns {Promise<AxiosResponse<any>>}
 */
export function GetEnergyConsumptionByDay(day) {
    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem(TOKEN),
        },
        params: {
            date: day
        }
    }

    return axios.get(ENERGY_CONSUMPTION_URL, config)
        .then((response) => {
            if (response.status === 200) {
                return response.data;
            }
        })
        .catch(error => {
            console.log(error)
            if (error.response.status !== 200) {
                // failed to retrieve data
                throw customError(error.response.data);
            }
        })
}

/**
 * Fetch the energy consumption of the given device associated to the currently logged in client for each hour.
 * @returns {Promise<AxiosResponse<any>>}
 */
export function GetEnergyConsumptionByDayAndDeviceId(day, deviceId) {
    const url = ENERGY_CONSUMPTION_URL + "/" + deviceId;
    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem(TOKEN),
        },
        params: {
            date: day
        }
    }

    return axios.get(url, config)
        .then((response) => {
            if (response.status === 200) {
                return response.data;
            }
        })
        .catch(error => {
            if (error.response.status !== 200) {
                // failed to retrieve data
                throw customError(error.response.data);
            }
        })
}