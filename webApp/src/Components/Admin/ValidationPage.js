import { getUserSessionData } from "../../utils/session.js";
import { callAPI,callAPIWithoutJSONResponse } from "../../utils/api.js";
import PrintError from "../PrintError.js";
const API_BASE_URL = "/api/admin/";


let validationPage;
let page = document.querySelector("#page");


const ValidationPage = async () => {
    validationPage = `<h4 id="pageTitle">Validation</h4>`;  
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
                        <option value="admin">Administrateur</option>
                    </select>
                    <button class="btn btn-primary" id="buttonValidation${user.id}">Valider</button>
                    <div id="messageBoard${user.id}"></div>
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

            let selectUserType = document.getElementById("selectUserType"+user.id).value;
            console.log(selectUserType);
            if(selectUserType == "Chosir type utilisateur"){
                document.getElementById("messageBoard"+user.id).innerHTML = `
                <h6 style="color:red">Vous devez choisir sélectionner un type utilisateur</h6>`;
            } else {
                let unvalidatedUser = {
                    id: user.id,
                    type: selectUserType
                };
                user = getUserSessionData();

                try {
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
                  }
            }
        });
    });

}

const onValidate = (userId) => {
    document.getElementById("user"+userId).innerHTML = `<h3 style="color:green">L'utilisateur a bien été validé</h3>`;
}

export default ValidationPage;