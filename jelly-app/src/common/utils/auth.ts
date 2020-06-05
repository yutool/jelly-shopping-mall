import Cookies from 'js-cookie';

const tokenKey = 'token';

export function getToken() {
  return Cookies.get(tokenKey);
  // return localStorage.getItem(tokenKey);
}

export function setToken(token: string) {
  const expires = new Date(new Date().getTime() + 24 * 60 * 60 * 1000);
  Cookies.set(tokenKey, token, { expires });
  // localStorage.setItem(tokenKey, token);
}

export function removeToken() {
  Cookies.remove(tokenKey);
  // localStorage.removeItem(tokenKey);
}
