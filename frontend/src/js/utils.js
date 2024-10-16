const date_re = /(\d\d\d\d)-(\d\d)-(\d\d)T(\d\d):(\d\d)/;

export function reformatDate(date) {
    if (date == null) {
        return ""
    }

    let re_result = date_re.exec(date);
    let year = parseInt(re_result[1]);
    let month = parseInt(re_result[2]) - 1;
    let day = parseInt(re_result[3]);
    let hours = parseInt(re_result[4]);
    let minutes = parseInt(re_result[5]);
    let new_date = new Date(year, month, day, hours + 3, minutes);

    return new_date.toLocaleDateString();
}

export function reformatDateTime(date) {
    if (date == null) {
        return ""
    }

    let re_result = date_re.exec(date);
    let year = parseInt(re_result[1]);
    let month = parseInt(re_result[2]) - 1;
    let day = parseInt(re_result[3]);
    let hours = parseInt(re_result[4]);
    let minutes = parseInt(re_result[5]);
    let new_date = new Date(year, month, day, hours + 3, minutes);

    return new_date.toLocaleDateString() + " " + new_date.toLocaleTimeString();
}