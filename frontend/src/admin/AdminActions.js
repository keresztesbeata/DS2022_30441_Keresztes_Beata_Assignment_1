import axios from "axios";
import {SERVER_BASE_URL} from "../common/Utils";
import {TOKEN} from "../auth/Authentication";

const API_URL = SERVER_BASE_URL + "/api"
const ACCOUNTS_URL = API_URL + "/accounts"


/**
 * Fetch all client accounts
 * @returns {Promise<AxiosResponse<any>>}
 */
export function GetAccounts(name) {
    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem(TOKEN),
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
        },
        params: {
            'name': name
        }
    }

    return axios.get(ACCOUNTS_URL, config)
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
 * Update a user's account.
 * @param data
 * @returns {Promise<AxiosResponse<any>>}
 * @constructor
 */
export function UpdateAccount(data) {
    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem(TOKEN),
        }
    }

    return axios.put(ACCOUNTS_URL + "/" + data.id, data, config)
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
 * Add a new account.
 * @param data
 * @returns {Promise<AxiosResponse<any>>}
 * @constructor
 */
export function InsertAccount(data) {
    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem(TOKEN),
        }
    }

    return axios.post(ACCOUNTS_URL, data, config)
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
 * Delete a user's account.
 * @param id
 * @returns {Promise<AxiosResponse<any>>}
 * @constructor
 */
export function DeleteAccount(id) {
    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem(TOKEN),
        }
    }

    return axios.delete(ACCOUNTS_URL + "/" + id, config)
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
