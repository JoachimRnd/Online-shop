import { RedirectUrl } from "../Router.js";
import { getUserSessionData } from "../../utils/session.js";
import { callAPI } from "../../utils/api.js";
import PrintError from "../PrintError.js"

let userAdmin = ``;

const UserAdmin = async(f) => {
    let page = document.querySelector("#page");
    page.innerHTML = userAdmin;
}

export default UserAdmin;