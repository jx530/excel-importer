import ajax from "../utils/ajax";

const sync = () => ajax("/sync");

export async function list(page, size) {
    return (await sync().query({ page, size }).get()).json();
}

export async function update(payload) {
    return (await sync().payload(payload).post()).json();
}