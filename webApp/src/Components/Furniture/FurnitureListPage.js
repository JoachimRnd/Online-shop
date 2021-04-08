import { RedirectUrl } from "../Router.js";
import { getUserSessionData } from "../../utils/session.js";
import { callAPI } from "../../utils/api.js";
import PrintError from "../PrintError.js";
const API_BASE_URL_ADMIN = "/api/admin/";
const API_BASE_URL_USER = "/api/furniture/";

let page = document.querySelector("#page");
let furniturePage;

const FurnitureListPage = async () => {
  furniturePage = `<h4 id="pageTitle">Liste de meubles</h4>`;
  page.innerHTML = furniturePage;

  const user = getUserSessionData();
  if(user && user.user.userType == "admin"){
    try {
      const furnitures = await callAPI(API_BASE_URL_ADMIN + "allFurniture", "GET", user.token);
      onFurnitureList(furnitures);
    } catch (err) {
      console.error("FurnitureListPage::onFurnitureList", err);
      PrintError(err);
    }
  } else {
    try {
      const furnitures = await callAPI(API_BASE_URL_USER + "allFurniture", "GET", undefined);
      onFurnitureList(furnitures);
    } catch (err) {
      console.error("FurnitureListPage::onFurnitureList", err);
      PrintError(err);
    }
  }
};

const onFurnitureList = (data) => {
  if (!data) return;
  furniturePage += `
  <div id="tablefurnitures" class="table-responsive mt-3">
  <table class="table">
      <thead>
          <tr>
              <th>Description</th>
              <th>Type</th>
              <th>Etat</th>
          </tr>
      </thead>
      <tbody>`;

    const user = getUserSessionData();

    data.forEach((element) => {
      furniturePage += `<tr>
                  <td><a id="furniture${element.id}" href="" target="_blank">${element.description}</a></td>
                  <td>${element.type.name}</td>`
                  if(user && user.user.userType == "admin")
                    furniturePage += `<td>${element.condition}</td>`
                  furniturePage +=
                `</tr>`
              ;
    });

  furniturePage += `</tbody>
  </table>
  </div>`;


  page.innerHTML = furniturePage;

  data.forEach((element) => {
    let furnitureElement = document.getElementById("furniture"+element.id);
    furnitureElement.addEventListener("click", (e) => {
      e.preventDefault();
      if(getUserSessionData() == null || getUserSessionData().user.userType != "admin"){
        RedirectUrl("/furniture",element,"?id="+element.id);
      }else{
        RedirectUrl("/furnitureAdmin",element,"?id="+element.id);
      }
    });
  });
};


export default FurnitureListPage;
