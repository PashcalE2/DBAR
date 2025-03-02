export function setAdmin(access_token, refresh_token) {
    sessionStorage.access_token = access_token;
    sessionStorage.refresh_token = refresh_token;
}

export function clearAdmin() {
    sessionStorage.access_token = null;
    sessionStorage.refresh_token = null;
}

export function getAccessToken() {
    return sessionStorage.access_token
}

export function getRefreshToken() {
    return sessionStorage.refresh_token
}
