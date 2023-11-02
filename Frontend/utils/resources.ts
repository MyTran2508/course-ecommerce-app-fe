export enum Action {
  SENT_OTP = 'SENT_OTP',
  REGISTER = 'REGISTER'
}

export enum StatusCode {
  REQUEST_SUCCESS = 200,
  DATA_NOT_FOUND = 404,
  DATA_NOT_MAP = 406,
  NOT_IMPLEMENTED = 501,
  DATA_CONFLICT = 409,
  NOT_PERMISSION = 403
}

export enum ToastStatus {
  SUCCESS = 'success',
  WARNING = 'warning',
  ERROR = 'error'
}

export enum ToastMessage {
  LOGIN_SUCCESS = 'Login Success',
  LOGIN_FAIL = 'Login Fail',
  LOGOUT_SUCCESS = 'Logout Success',
  NOT_SELECT_PAYMENT = "Please select a payment method",
  ADD_CART_SUCCESS = "Add Cart Success",
  DELETE_CART_SUCCESS = "Delete Cart Success",
  IMAGE_NOT_FOUND = "Avatar not found"
}