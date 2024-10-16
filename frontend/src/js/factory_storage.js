export function setFactory(id, password) {
    sessionStorage.id = id;
    sessionStorage.password = password;
}

export function clearFactory() {
    sessionStorage.id = null;
    sessionStorage.password = null;
}

export function getId() {
    return sessionStorage.id;
}

export function setId(id) {
    sessionStorage.id = id;
}

export function getPassword() {
    return sessionStorage.password;
}

export function setPassword(password) {
    sessionStorage.password = password;
}
