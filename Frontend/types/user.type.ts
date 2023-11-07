export interface User{
    id: string,
    username: string,
    password: string,
    email: string,
    firstName: string,
    lastName: string
    telephone: string
    photos: string | null,
    roles: Role[] | null
    addresses : Address []
}

export interface Role {
    id: string,
    name: string
}
export interface Address {
    addressLine: string,
    postalCode: string | null,
    defaultAddress: boolean | null,
}