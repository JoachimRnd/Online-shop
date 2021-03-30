import { RedirectUrl } from "../Router.js";
import Navbar from "../Navbar.js";

let adminPage = `<h4 id="pageTitle">Admin</h4>
    <button class="btn btn-primary" id="validation">Demandes d'inscriptions</button>
    <button class="btn btn-success" id="">Demandes de visites</button>
    <button class="btn btn-dark" id="">Ajouter type meuble</button>

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