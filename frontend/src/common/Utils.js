export const SERVER_BASE_URL = "http://localhost:8080";

export function redirect(url) {
    window.history.pushState(null, '', url);
    window.location.href = url;
}

export function mapAndRethrowError(axiosError) {
    if (!axiosError.response || !axiosError.response.status) {
        throw new Error("Network error! Server is not responding!");
    }
    if (axiosError.response.status !== 200 && axiosError.response.status !== 201) {
        if(!axiosError.response.data || !axiosError.response.data.message) {
            throw new Error("Unexpected error occurred!");
        }
        let error = new Error(axiosError.response.data.message);
        error.errors = axiosError.response.data.errors;
        throw error;
    }
}


