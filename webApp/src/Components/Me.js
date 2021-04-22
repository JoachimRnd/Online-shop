import {getUserInSessionStorage} from "../utils/session.js";
import {getUserInLocalStorage} from "../utils/session.js";

const Me = () => {
    let user = getUserSessionData();
    if(user) {
        //=> Appel api
        user = callAPI("/api/users/me", "GET", user.token);
        //=> Enregistrer dans le storage
        //=> local ou session suivant là ou il était déjà
        if(getUserInSessionStorage) {
          setUserSessionData(user);
        } else if (getUserInLocalStorage) {
          setUserLocalData(user);
        }
      }
}
