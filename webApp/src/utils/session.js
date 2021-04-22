const STORE_NAME = "user";

const THEME = "theme";

const getUserSessionData = () => {
  const retrievedUserSession = getUserInSessionStorage;
  if (retrievedUserSession) return JSON.parse(retrievedUserSession);
  const retrievedUser = getUserInLocalStorage;
  if (!retrievedUser) return;
  return JSON.parse(retrievedUser);
};

const getUserInSessionStorage = () => {
  return sessionStorage.getItem(STORE_NAME);
}

const getUserInLocalStorage = () => {
  return localStorage.getItem(STORE_NAME);
}

const setUserLocalData = (user) => {
  const storageValue = JSON.stringify(user);
  localStorage.setItem(STORE_NAME, storageValue);
};

const setUserSessionData = (user) => {
  const storageValue = JSON.stringify(user);
  sessionStorage.setItem(STORE_NAME, storageValue);
};

const getTheme = () => {
  const theme = localStorage.getItem(THEME);
  if (!theme) return;
  return JSON.parse(theme);
};

const setTheme = (theme) => {
  const storageValue = JSON.stringify(theme);
  localStorage.setItem(THEME, storageValue);
};

const removeSessionData = () => {
  localStorage.removeItem(STORE_NAME);
  localStorage.removeItem(THEME);
  sessionStorage.removeItem(STORE_NAME);
  sessionStorage.removeItem(THEME);
};

export {
  getUserSessionData,
  setUserSessionData,
  setUserLocalData,
  removeSessionData,
  getTheme,
  setTheme,
};
