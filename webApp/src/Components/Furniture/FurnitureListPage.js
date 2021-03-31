import { RedirectUrl } from "../Router.js";
import { getUserSessionData } from "../../utils/session.js";
import { callAPI } from "../../utils/api.js";
import PrintError from "../PrintError.js";
const API_BASE_URL = "/api/furniture/";

let page = document.querySelector("#page");
let furniturePage;

const FurnitureListPage = async () => {
  furniturePage = `<h4 id="pageTitle">Liste de meubles</h4>`;
  page.innerHTML = furniturePage;

  try {
    const furnitures = await callAPI(API_BASE_URL + "allFurniture", "GET", undefined);
    onFurnitureList(furnitures);
  } catch (err) {
    console.error("FurnitureListPage::onFurnitureList", err);
    PrintError(err);
  }
};

const onFurnitureList = (data) => {
  console.log(data);
  if (!data) return;
  furniturePage += `
  <div id="tablefurnitures" class="table-responsive mt-3">
  <table class="table">
      <thead>
          <tr>
              <th>Description</th>
              <th>Type</th>
          </tr>
      </thead>
      <tbody>`;

  data.forEach((element) => {
    furniturePage += `<tr>
                <td><a id="furniture${element.id}" href="#" target="_blank">${element.description}</a></td>
                <td>${element.type.name}</td>
            </tr>
            `;
  });

  furniturePage += `</tbody>
  </table>
  </div>`;

  page.innerHTML = furniturePage;

  data.forEach((element) => {
    let furnitureElement = document.getElementById("furniture"+element.id);
    furnitureElement.addEventListener("click", (e) => {
      e.preventDefault();
      RedirectUrl("/furniture",element);
    });
  });
};


export default FurnitureListPage;
