export const SERVER_BASE_URL = "http://localhost:8080";

export function redirect(url) {
    window.history.pushState(null, '', url);
    window.location.href = url;
}


