import { RedirectUrl } from "./Router.js";
import Navbar from "./Navbar.js";
import { getUserSessionData } from "../utils/session.js";
import { callAPI,callAPIWithoutJSONResponse } from "../utils/api.js";
import PrintError from "./PrintError.js";
const API_BASE_URL = "/api/admin/";


let validationPage = `<h4 id="pageTitle">Validation</h4>
`;
let page = document.querySelector("#page");


const ValidationPage = async () => {  
    page.innerHTML  = validationPage;

    const user = getUserSessionData();

    try {
        const users = await callAPI(API_BASE_URL + "validate", "GET", user.token);
        onUnvalidatedUsers(users);
    } catch (err) {
        console.error("ValidationPage::onUnvalidatedUsers", err);
        PrintError(err);
    }
};

const onUnvalidatedUsers = (users) => {

    users.forEach((user) => {
        console.log(user);
        let address = user.address
        validationPage += `
        <div id="user${user.id}">
            <div class="row">
                <div class="col-6">
                    <p>Pseudo : ${user.username}</p>
                    <p>Nom : ${user.lastName}</p>
                    <p>Prénom : ${user.firstName}</p>
                    <p>Email : ${user.email}</p>
                    <p>Date d'inscription : </p>
                    <select id="selectUserType${user.id}" class="form-select" aria-label="Default select example">
                        <option selected>Chosir type utilisateur</option>
                        <option value="client">Client</option>
                        <option value="antiquaire">Antiquaire</option>
                        <option value="administrateur">Administrateur</option>
                    </select>
                    <button class="btn btn-primary" id="buttonValidation${user.id}">Valider</button>
                    <div class="alert alert-danger mt-2 d-none" id="messageBoard${user.id}"></div>
                </div>
                <div class="col-6">
                    <p>Rue : ${address.street}</p>
                    <p>Numéro : ${address.buildingNumber}</p>
                    <p>Boite : ${address.unitNumber}</p>
                    <p>Code postal : ${address.postcode}</p>
                    <p>Commune : ${address.commune}</p>
                    <p>Pays : ${address.country}</p>
                </div>
            </div>
        </div>
        <hr/>
        `;
    });

    page.innerHTML = validationPage;
    
    users.forEach((user) => {

        let buttonValidation = document.getElementById("buttonValidation"+user.id);
        buttonValidation.addEventListener("click", async () => {

            let selectUserType = document.getElementById("selectUserType"+user.id);

            let unvalidatedUser = {
                id: user.id,
                type: selectUserType.value
            };
            user = getUserSessionData();

            onValidate(unvalidatedUser.id);
            /*try {
                await callAPIWithoutJSONResponse(
                  API_BASE_URL + "validate",
                  "PUT",
                  user.token,
                  unvalidatedUser
                );
                onValidate(unvalidatedUser.id);
              } catch (err) {
                console.error("ValidationPage::onValidate", err);
                PrintError(err);
              }*/
        });
    });

}

const onValidate = (userId) => {
    document.getElementById("user"+userId).innerHTML = `<h1 style="color:green">L'utilisateur a bien été supprimé</h1>`;
}

export default ValidationPage;