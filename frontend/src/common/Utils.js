export const SERVER_BASE_URL = "http://localhost:8080";

export function redirect(url) {
    window.history.pushState(null, '', url);
    window.location.href = url;
}

export function customError(apiError) {
    let error = new Error(apiError.message);
    error.errors = apiError.errors;

    return error;
}


