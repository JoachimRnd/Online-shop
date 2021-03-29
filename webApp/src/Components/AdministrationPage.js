import { RedirectUrl } from "./Router.js";
import Navbar from "./Navbar.js";

let adminPage = `<h4 id="pageTitle">Admin</h4>
    <button id="validation">Demande d'inscriptions</button>
`;


const AdministrationPage = () => {  
    let page = document.querySelector("#page");
    page.innerHTML  = adminPage;

    let buttonValidation = document.getElementById("validation");
    buttonValidation.addEventListener("click",onValidation);
};

const onValidation = () => {
    Navbar();
    RedirectUrl("/admin/validation");
}


export default AdministrationPage;