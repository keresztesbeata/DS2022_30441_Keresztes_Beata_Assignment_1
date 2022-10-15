import React from "react";

export const SERVER_BASE_URL = "http://localhost:8080";

export const ERROR_PAGE = "/error?message=";

export const INFO = "alert-info";
export const SUCCESS = "alert-success";
export const WARNING = "alert-warning";
export const ERROR = "alert-danger";

export function handleErrorResponse(response, errorCode, errorMessage) {
    if (response.status === errorCode) {
        redirect(ERROR_PAGE + errorMessage)
    } else {
        return response;
    }
}

export function redirect(url) {
    window.history.pushState(null, '', url);
    window.location.href = url;
}


