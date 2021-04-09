import { getUserSessionData } from "../../utils/session.js";
import { callAPI,callAPIWithoutJSONResponse } from "../../utils/api.js";
import PrintError from "../PrintError.js";
const API_BASE_URL = "/api/furniture/";

let furnitureTypePage;
let page = document.querySelector("#page");

const FurnitureTypePage = async () => {
    furnitureTypePage = `
    <h4 id="pageTitle">Ajouter ou supprimer un type de meuble</h4>
    <div id="typesList"></div>

    `;
    page.innerHTML = furnitureTypePage;

    try {
        const types = await callAPI(API_BASE_URL + "allFurnitureTypes", "GET", undefined);
        onTypesList(types);
      } catch (err) {
        console.error("FurnitureTypePage::onTypesList", err);
        PrintError(err);
      }
}

const onTypesList = (data) => {
    let typesList = `
    <h3>Types existants</h3>
    <div class="row">`;

    data.forEach(type => {
        typesList += `
            <div class="col-4">
                <div class="row"> 
                    <div class="col-6">
                        <h5>${type.name}</h5>
                    </div>
                    <div class="col-6">
                        <button class="btn btn-danger" id="buttonDelete${type.id}">Supprimer</button>
                    </div>
                </div> </br>
            </div>`
    });

    typesList += `</div>`;
    document.getElementById("typesList").innerHTML = typesList;

    data.forEach(type => {
        let buttonDelete = document.getElementById("buttonDelete"+type.id);
        buttonDelete.addEventListener("click", async () => {
            
        });
    });
    console.log(data);
}

export default FurnitureTypePage;