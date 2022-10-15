import axios from "axios";
import {redirect, SERVER_BASE_URL} from "../common/Utils";
import React from "react";
import {ErrorPage} from "../common/ErrorPage";

const HOME_PAGE = "/";
const LOGIN_PAGE = "/login";

const LOGIN_URL = SERVER_BASE_URL + "/login";
const REGISTER_URL = SERVER_BASE_URL + "/register";
const LOGOUT_URL = SERVER_BASE_URL + "/logout";

export const AUTHORITIES = "authorities"
export const TOKEN = "token"

export const CLIENT_ROLE = "CLIENT";
export const ADMIN_ROLE = "ADMIN";

/**
 * Verify if the user is logged in.
 * @returns {boolean}
 */
export function isLoggedIn() {
    return localStorage.getItem(TOKEN) !== null;
}

/**
 * Verify if the user is logged in and has the necessary authority to access the resources.
 * @param authority
 * @returns {boolean}
 */
export function isAuthorized(authority) {
    return isLoggedIn() && localStorage.getItem(AUTHORITIES) !== null && localStorage.getItem(AUTHORITIES).includes(authority);
}

/**
 * Send a login request to the server with the given credentials.
 * @param username
 * @param password
 * @returns {Promise<AxiosResponse<any>>}
 * @constructor
 */
export function Login(username, password) {
    return axios.post(LOGIN_URL, {
        username: username, password: password
    })
        .then((response) => {
            if (response.status === 200) {
                // successful login
                localStorage.setItem(TOKEN, response.data.accessToken);
                localStorage.setItem(AUTHORITIES, response.data.authorities);
                redirect(HOME_PAGE);
            }
        })
        .catch(error => {
            if (error.response.status !== 200) {
                // failed to log in
                throw new Error(error.response.data);
            }
        })
}

/**
 * Registers the user with the given account data.
 * @param data
 * @returns {Promise<AxiosResponse<any>>}
 * @constructor
 */
export function Register(data) {
    return axios.post(REGISTER_URL, data)
        .catch(error => {
            if (error.response.status !== 201) {
                // failed to register user
                throw new Error(error.response.data);
            }
        })
}

/**
 * Logs out the user, removing its authorization from the local storage.
 * @returns {Promise<AxiosResponse<any>>}
 * @constructor
 */
export function Logout() {
    if (isLoggedIn()) {
        axios.post(LOGOUT_URL, {}, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem(TOKEN)
            }
        })
            .then(() => {
                localStorage.removeItem(TOKEN);
                localStorage.removeItem(AUTHORITIES);
                redirect(LOGIN_PAGE);
            })
    }
}

/**
 * Verify the authorization of the user before returning the requested resource.
 * In case the user lacks the authroization, it returns an error page with a corresponding error message
 * @param component
 * @param authority
 * @returns {JSX.Element}
 * @constructor
 */
export function ProtectedComponent({component, authority}) {
    return !isLoggedIn() ?
        // redirect to the login page if the user is not logged in
        <ErrorPage message={"Unauthorized access! You cannot view this page, because you are not logged in!"}/>
        :
        (isAuthorized(authority) ?
                // if authorized return the component
                component
                : // if not authorized return an error page
                <ErrorPage
                    message={"Unauthorized access! You cannot view this page, because you do not have sufficient" +
                        " access rights."}/>
        )
}