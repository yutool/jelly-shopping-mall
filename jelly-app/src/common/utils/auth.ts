import Cookies from 'js-cookie';

const tokenKey = 'token';

// 不晓得存在Cookies怎么不能关闭浏览器了

export function getToken() {
  return Cookies.get(tokenKey);
  // return localStorage.getItem(tokenKey);
}

export function setToken(token: string) {
  Cookies.set(tokenKey, token);
  // localStorage.setItem(tokenKey, token);
}

export function removeToken() {
  Cookies.remove(tokenKey);
  // localStorage.removeItem(tokenKey);
}
