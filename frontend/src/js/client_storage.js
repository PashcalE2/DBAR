export function setClient(access_token, refresh_token) {
    sessionStorage.access_token = access_token;
    sessionStorage.refresh_token = refresh_token;
}

export function clearClient() {
    sessionStorage.access_token = null;
    sessionStorage.refresh_token = null;
}

export function getAccessToken() {
    return sessionStorage.access_token;
}
