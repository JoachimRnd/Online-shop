import { RedirectUrl } from "./Router.js";
import Navbar from "./Navbar.js";
import { getUserSessionData } from "../utils/session.js";
import { callAPI, callAPIFormData, callAPIWithoutJSONResponse } from "../utils/api.js";
import PrintError from "./PrintError.js"
const API_BASE_URL = "/api/visit/";
const API_BASE_URL_FURNITURE = "/api/furniture/";

let furnitureList = [];
let picturesList = [];
let fd = new FormData();

let visitPage = `
<h4 id="pageTitle">Ajouter Demande visite</h4>
<div class="row">
    <div class="col-6">
        <div class="row">
            <p>Adresse du lieu</p>
        </div>
        <div class="row">
            <div class="col-6">
                <small>(Veuillez entrer votre adresse complète)</small>
            </div>
            <div class="col-6">
                <small>champs obligatoires : *</small>
            </div>
        </div>
        <form id="visitRequestForm">
            <div class="row">
                <div class="form-group">
                    <label for="street">Rue*</label>
                    <input class="form-control" id="street" type="text" placeholder="Entrez votre rue" required="" />
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label for="buildingnumber">Numéro*</label>
                    <input class="form-control" id="buildingnumber" type="text" placeholder="Entrez votre numéro" required="" />
                </div>
                <div class="form-group">
                    <label for="unitnumber">Boite</label>
                    <input class="form-control" id="unitnumber" type="text" placeholder="Entrez votre boite" />
                </div>
                <div class="form-group">
                    <label for="postcode">Code postal*</label>
                    <input class="form-control" id="postcode" type="text" placeholder="Entrez votre code postal" required="" />
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label for="commune">Commune*</label>
                    <input class="form-control" id="commune" type="text" placeholder="Entrez votre commune" required="" />
                </div>
                <div class="form-group">
                    <label for="country">Pays*</label>
                    <input class="form-control" id="country" type="text" placeholder="Entrez votre pays" required="" />
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <div class="form-group">
                        <label for="timeslot">Plage horaire*</label>
                        <textarea class="form-control" id="timeslot" rows="3"></textarea>
                    </div>
                </div>
            </div>
            <div class="row">
                <p>Liste meubles</p>
            </div>
            <div class="row">
                <button class="btn btn-primary" id="btnVisitRequest" type="submit">Introduire ma demande</button>
            </div>
        </form>
    </div>
    <div class="col-6">
        <form id="furnitureForm">
            <div class="row">
                <div class="col-12">
                    <div class="form-group">
                        <label for="furnituredescription">Description du meuble*</label>
                        <textarea class="form-control" id="furnituredescription" rows="6"></textarea>
                    </div>
                </div>
            </div>
            <div id="typesList"></div>
            <div class="row">
                <p>Liste photos</p>
            </div>
            <div class="row">
                <button class="btn btn-primary" id="btnaddfurniture" type="submit">Ajouter le meuble</button>
            </div>
        </form>
        <form id="pictureForm">
            <div class="row">
                <div class="form-group">
                    <label for="file">Vous devez choisir au moins une photo de votre meuble*</label>
                    <input class="form-control" id="file" type="file" />
                    <button class="btn btn-primary" id="btnaddpicture" type="submit">Ajouter la photo</button>
                </div>
            </div>
        </form>
    </div>
</div>
`;

const VisitPage = async () => {

    let page = document.querySelector("#page");
    page.innerHTML  = visitPage;

    let visitRequestForm = document.getElementById("visitRequestForm");
    visitRequestForm.addEventListener("submit", onVisitRequest);
    let furnitureForm = document.getElementById("furnitureForm");
    furnitureForm.addEventListener("submit", onFurniture);

    const user = getUserSessionData();
    try {
        const address = await callAPI(API_BASE_URL + "address/" + user.user.id, "GET", undefined);
        onAddress(address);
    } catch (err) {
        console.error("VisitPage::onAddress", err);
        PrintError(err);
    }

    try {
        const types = await callAPI(API_BASE_URL_FURNITURE + "allFurnitureTypes", "GET", undefined);
        onTypesList(types);
    } catch (err) {
        console.error("VisitPage::onTypesList", err);
        PrintError(err);
    }
    let pictureForm = document.getElementById("pictureForm");
    pictureForm.addEventListener("submit", onPicture);
}

const onAddress = (address) => {
    document.getElementById("street").value = address.street;
    document.getElementById("buildingnumber").value = address.buildingNumber;
    document.getElementById("unitnumber").value = address.unitNumber;
    document.getElementById("postcode").value = address.postcode;
    document.getElementById("commune").value = address.commune;
    document.getElementById("country").value = address.country;
}

const onTypesList = (typesList) => {
    let typesListPage = `
    <div class="row">
    <label for="furnituretype">Sélectionner type de meuble*</label>
    <select class="form-control" id="furnituretype">`;
      typesList.forEach(type => {
        typesListPage += `<option id="${type.id}" value="${type.id}">${type.name}</option>`;
      });
    
    typesListPage += `
    </select>
      </div>`;
  
  document.getElementById("typesList").innerHTML = typesListPage;
}
  
const onVisitRequest = async (e) => {
    e.preventDefault();
    let unitNumber = null;
    if(document.getElementById("unitnumber").value != "")
        unitNumber = document.getElementById("unitnumber").value;
    
    let address = {
        street: document.getElementById("street").value,
        buildingNumber: document.getElementById("buildingnumber").value,
        unitNumber: unitNumber,
        postcode: document.getElementById("postcode").value,
        commune: document.getElementById("commune").value,
        country: document.getElementById("country").value,
    };

    let visitRequest = {
        address: address,
        timeSlot: document.getElementById("timeslot").value,
        furnitureList: furnitureList
    };

    console.log(visitRequest);
    fd.append("json", JSON.stringify(visitRequest));
    const user = getUserSessionData();

    for(let i=0; i<visitRequest.furnitureList.length; i++){
        for(let j=0; j<visitRequest.furnitureList[i].picturesList.length; j++){
            fd.append("file"+i,visitRequest.furnitureList[i].picturesList[j]);
        }
    }
    try {
        const visitRequested = await callAPIFormData(
          API_BASE_URL + "image",
          "POST",
          user.token,
          fd
        );
        onVisitRequestAdded(visitRequested);
      } catch (err) {
        console.error("VisitPage::onVisitRequestAdded", err);
        PrintError(err);
      }
      fd = new FormData();
}

const onFurniture = (e) => {
    e.preventDefault();

    let type = {
        id:document.getElementById("furnituretype").value,
    };
    let furniture = {
        description: document.getElementById("furnituredescription").value,
        type: type,
        picturesList: picturesList,
    };
    furnitureList.push(furniture);
    document.getElementById("furnitureForm").reset();
    picturesList = [];
    console.log(furniture);
}

const onPicture = (e) => {
    e.preventDefault();
    let file = document.getElementById("file").files[0];
    picturesList.push(file);
}

const onVisitRequestAdded = (data) => {
    console.log(data);
    //Navbar();
    //RedirectUrl("/");
}

export default VisitPage;