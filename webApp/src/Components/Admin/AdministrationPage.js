import { RedirectUrl } from "../Router.js";
import Navbar from "../Navbar.js";

let adminPage = `<h4 id="pageTitle">Admin</h4>
    <button class="btn btn-primary" id="validation">Demandes d'inscriptions</button>
    <button class="btn btn-success" id="">Demandes de visites</button>
    <button class="btn btn-success" id="search">Recherche</button>
    <button class="btn btn-dark" id="furnitureType">Ajouter ou supprimer type meuble</button>

`;


const AdministrationPage = () => {  
    let page = document.querySelector("#page");
    page.innerHTML  = adminPage;

    let buttonValidation = document.getElementById("validation");
    buttonValidation.addEventListener("click",onValidation);
    let buttonFurnitureType = document.getElementById("furnitureType");
    buttonFurnitureType.addEventListener("click",onFurnitureType);
    let buttonSearch = document.getElementById("search");
    buttonSearch.addEventListener("click",onSearch);
};

const onValidation = () => {
    Navbar();
    RedirectUrl("/admin/validation");
}

const onFurnitureType = () => {
    Navbar();
    RedirectUrl("/admin/furnitureType");
}

const onSearch = () => {
    Navbar();
    RedirectUrl("/admin/search");
}


export default AdministrationPage;