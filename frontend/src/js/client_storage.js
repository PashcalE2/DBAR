export function setClient(id, login, email, phone_number, password) {
    sessionStorage.id = id;
    sessionStorage.login = login;
    sessionStorage.email = email;
    sessionStorage.phone_number = phone_number;
    sessionStorage.password = password;
}

export function clearClient() {
    sessionStorage.id = null;
    sessionStorage.login = null;
    sessionStorage.email = null;
    sessionStorage.phone_number = null;
    sessionStorage.password = null;
}

export function getId() {
    return sessionStorage.id;
}

export function setId(id) {
    sessionStorage.id = id;
}

export function getLogin() {
    return sessionStorage.login;
}

export function setLogin(login) {
    sessionStorage.login = login;
}

export function getEmail() {
    return sessionStorage.email;
}

export function setEmail(email) {
    sessionStorage.email = email;
}

export function getPhoneNumber() {
    return sessionStorage.phone_number;
}

export function setPhoneNumber(phone_number) {
    sessionStorage.phone_number = phone_number;
}

export function getPassword() {
    return sessionStorage.password;
}

export function setPassword(password) {
    sessionStorage.password = password;
}
