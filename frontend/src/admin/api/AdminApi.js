import axios from "axios";
import {SERVER_BASE_URL} from "../../common/Utils";
import {TOKEN} from "../../common/auth/Authentication";

const API_URL = SERVER_BASE_URL + "/api"
const ACCOUNTS_URL = API_URL + "/accounts"
const DEVICES_URL = API_URL + "/devices"
const LINK_DEVICE_URL = API_URL + "/link_device"
const AVAILABLE_DEVICES_URL = DEVICES_URL + "/available"

export const ACCOUNT_ENTITY = "Account"
export const DEVICE_ENTITY = "Device"

const buildUrl = (entityType) => {
    switch (entityType) {
        case ACCOUNT_ENTITY:
            return ACCOUNTS_URL;
        case DEVICE_ENTITY:
            return DEVICES_URL;
        default:
            return null;
    }
}

/**
 * Fetch all entities
 * @param entityType
 * @returns {Promise<AxiosResponse<any>>}
 */
export function GetAll(entityType) {
    // build url
    const url = buildUrl(entityType)

    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem(TOKEN),
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
                throw new Error(error.response.data);
            }
        })
}

/**
 * Fetch all entities
 * @param entityType
 * @param filterKey
 * @param filterValue
 * @returns {Promise<AxiosResponse<any>>}
 */
export function Filter(entityType, filterKey, filterValue) {
    // build url
    const url = buildUrl(entityType) + "/filter"

    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem(TOKEN),
        },
        params: {
            'filterKey': filterKey,
            'filterValue': filterValue
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
                throw new Error(error.response.data);
            }
        })
}

/**
 * Update an existing entity.
 * @param entityType
 * @param data to be updated
 * @returns {Promise<AxiosResponse<any>>}
 * @constructor
 */
export function Update(entityType, data) {
    const url = buildUrl(entityType) + "/" + data.id;

    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem(TOKEN),
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
    }

    return axios.put(url, data, config)
        .then((response) => {
            if (response.status === 200) {
                return response.data;
            }
        })
        .catch(error => {
            if (error.response.status !== 200) {
                // failed to update data
                throw new Error(error.response.data);
            }
        })
}

/**
 * Add a new entity.
 * @param entityType
 * @param data to be inserted
 * @returns {Promise<AxiosResponse<any>>}
 * @constructor
 */
export function Insert(entityType, data) {
    const url = buildUrl(entityType);

    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem(TOKEN),
            'Content-Type': 'application/json',
        }
    }

    return axios.post(url, data, config)
        .then((response) => {
            if (response.status === 201) {
                return response.data;
            }
        })
        .catch(error => {
            if (error.response.status !== 201) {
                // failed to insert data
                throw new Error(error.response.data);
            }
        })
}

/**
 * Delete an entity.
 * @param entityType
 * @param id
 * @returns {Promise<AxiosResponse<any>>}
 * @constructor
 */
export function Delete(entityType, id) {
    const url = buildUrl(entityType) + "/" + id;

    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem(TOKEN),
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
    }

    return axios.delete(url, config)
        .then((response) => {
            if (response.status === 200) {
                return id;
            }
        })
        .catch(error => {
            if (error.response.status !== 200) {
                // failed to delete data
                throw new Error(error.response.data);
            }
        })
}

/**
 * Fetch all available devices.
 *
 * @returns {Promise<AxiosResponse<any>>}
 */
export function FindAvailableDevices() {
    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem(TOKEN),
        }
    }

    return axios.get(AVAILABLE_DEVICES_URL, config)
        .then((response) => {
            if (response.status === 200) {
                return response.data;
            }
        })
        .catch(error => {
            if (error.response.status !== 200) {
                // failed to retrieve data
                throw new Error(error.response.data);
            }
        })
}

/**
 * Link a device to a user account.
 * @param deviceId
 * @param accountId
 * @returns {Promise<AxiosResponse<any>>}
 * @constructor
 */
export function LinkDeviceToUser(deviceId, accountId) {
    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem(TOKEN),
            'Content-Type': 'application/json',
        }
    }

    const data = {
        'deviceId': deviceId,
        'accountId': accountId
    }

    return axios.post(LINK_DEVICE_URL, data, config)
        .then((response) => {
            if (response.status === 200) {
                return data;
            }
        })
        .catch(error => {
            console.log(error)
            if (error.response.status !== 200) {
                // failed to delete data
                throw new Error(error.response.data);
            }
        })
}