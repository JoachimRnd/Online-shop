import { RedirectUrl } from "../Router.js";
import { getUserSessionData } from "../../utils/session.js";
import { callAPI } from "../../utils/api.js";
import PrintError from "../PrintError.js";
const API_BASE_URL = "/api/furniture/";

let page = document.querySelector("#page");
let furniturePage;

const FurnitureUserListPage = async () => {  
  furniturePage += `
  <h4 id="pageTitle">Mes meubles</h4>
    <div class="row">
    <div class="col-6">
      <div class="form-group">
          <label for="furniture_buyed">Meuble acheté par le client</label>
          <div id="furniture_buyed"></div>
      </div>
    </div>
    <div class="col-6">
      <div class="form-group">
          <label for="furniture_selled">Meuble vendu par le client</label>
          <div id="furniture_selled"></div>
      </div>
    </div>
  </div>`;
    page.innerHTML = furniturePage;

    const user = getUserSessionData();
    let furnitureBuyByUser = await callAPI(API_BASE_URL + "furniturebuyby/" + user.user.id , "GET", user.token);
    onFurnitureBuy(furnitureBuyByUser);
    let furnitureSellByUser = await callAPI(API_BASE_URL + "furnituresellby/" + user.user.id , "GET", user.token);
    onFurnitureSell(furnitureSellByUser);
};


const onFurnitureBuy = (data) => {
  let furnitureBuyed = document.querySelector('#furniture_buyed');
  let display =
      `<div id="tablefurnitures" class="table-responsive mt-3">
          <table class="table">
              <thead>
                  <tr>
                      <th>Description</th>
                      <th>Type</th>
                      <th>Etat</th>
                  </tr>
              </thead>
              <tbody>`;
  data.forEach((element) => {
      display +=  `<tr>
                                  <td><a id="furnitureBuyed${element.id}" href="" target="_blank">${element.description}</a></td>
                                  <td>${element.type.name}</td>
                                  <td>${element.condition}</td>
                              </tr>`;
  });
  display += `</tbody></table></div>`;
  furnitureBuyed.innerHTML = display;

  data.forEach((element) => {
      let furnitureElement = document.getElementById("furnitureBuyed"+element.id);
      furnitureElement.addEventListener("click", (e) => {
          e.preventDefault();
          
          RedirectUrl("/myfurniture",element,"?id="+element.id); 
      });
  });
};

const onFurnitureSell = (data) => {
  let furnitureSelled = document.querySelector('#furniture_selled');
  let display =
      `<div id="tablefurnitures" class="table-responsive mt-3">
          <table class="table">
              <thead>
                  <tr>
                      <th>Description</th>
                      <th>Type</th>
                      <th>Etat</th>
                  </tr>
              </thead>
              <tbody>`;
  data.forEach((element) => {
      display +=  `<tr>
                                  <td><a id="furnitureSelled${element.id}" href="" target="_blank">${element.description}</a></td>
                                  <td>${element.type.name}</td>
                                  <td>${element.condition}</td>
                              </tr>`;
  });
  display += `</tbody></table></div>`;
  furnitureSelled.innerHTML = display;

  data.forEach((element) => {
      let furnitureElement = document.getElementById("furnitureSelled"+element.id);
      furnitureElement.addEventListener("click", (e) => {
          e.preventDefault();
          RedirectUrl("/myfurniture",element,"?id="+element.id);
      });
  });
};


export default FurnitureUserListPage;
