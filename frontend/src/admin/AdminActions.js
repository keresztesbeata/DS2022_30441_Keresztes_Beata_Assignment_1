import axios from "axios";
import {SERVER_BASE_URL} from "../common/Utils";
import {TOKEN} from "../auth/Authentication";

const API_URL = SERVER_BASE_URL + "/api"
const ACCOUNTS_URL = API_URL + "/accounts"
const DEVICES_URL = API_URL + "/devices"
const LINK_DEVICE_URL = API_URL + "/link_device"

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
    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem(TOKEN),
        }
    }

    // build url
    const url = buildUrl(entityType)

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
 * @param filterParam
 * @param filterValue
 * @returns {Promise<AxiosResponse<any>>}
 */
export function Filter(entityType, filterParam, filterValue) {
    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem(TOKEN),
        }
    }

    // build url
    const url = buildUrl(entityType) + "/filter?" + filterParam + "=" + filterValue

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
    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem(TOKEN),
        }
    }

    const url = buildUrl(entityType) + "/" + data.id;

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
    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem(TOKEN),
        }
    }

    const url = buildUrl(entityType);

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
    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem(TOKEN),
        }
    }

    const url = buildUrl(entityType) + "/" + id;

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
        }
    }
    const data = {
        deviceId: deviceId,
        accountId: accountId
    }

    return axios.post(LINK_DEVICE_URL, data, config)
        .then((response) => {
            if (response.status === 200) {
                return data;
            }
        })
        .catch(error => {
            if (error.response.status !== 200) {
                // failed to delete data
                throw new Error(error.response.data);
            }
        })
}