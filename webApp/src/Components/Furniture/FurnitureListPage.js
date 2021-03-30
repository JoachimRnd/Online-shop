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
                <td>${element.description}</td>
                <td><a href="${element.link}" target="_blank">${element.type.name}</a></td>
            </tr>
            `;
  });

  furniturePage += `</tbody>
  </table>
  </div>`;

  page.innerHTML = furniturePage;
};


export default FurnitureListPage;
