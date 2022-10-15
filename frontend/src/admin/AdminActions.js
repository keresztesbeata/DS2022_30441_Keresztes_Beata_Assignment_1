import axios from "axios";
import {SERVER_BASE_URL} from "../common/Utils";
import {TOKEN} from "../auth/Authentication";

const API_URL = SERVER_BASE_URL + "/api"
const ACCOUNTS_URL = API_URL + "/accounts"

/**
 * Fetch all user accounts
 * @returns {Promise<AxiosResponse<any>>}
 */
export function GetAccounts(name) {
    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem(TOKEN)
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
