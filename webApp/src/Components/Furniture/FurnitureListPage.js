import { RedirectUrl } from "../Router.js";
import { getUserSessionData } from "../../utils/session.js";
import { callAPI } from "../../utils/api.js";
import PrintError from "../PrintError.js";
const API_BASE_URL = "/api/furniture/";

const FurnitureListPage = async () => {
  // deal with page title
  let page = document.querySelector("#page");
  // clear the page
  page.innerHTML = "";
  let title = document.createElement("h4");
  title.id = "pageTitle";
  title.innerText = "List of furnitures";
  page.appendChild(title);

  const user = getUserSessionData();

  try {
    const furnitures = await callAPI(API_BASE_URL + "allFurniture", "GET", user.token);
    onFurnitureList(furnitures);
  } catch (err) {
    console.error("FurnitureListPage::onFurnitureList", err);
    PrintError(err);
  }
};

const onFurnitureList = (data) => {

  //@TODO Elements de la liste
  if (!data) return;
  let table = `
  <div id="tablefurnitures" class="table-responsive mt-3">
  <table class="table">
      <thead>
          <tr>
              <th class="title">Title</th>
              <th class="link">Link</th>
              <th class="duration">Duration (min)</th>
              <th class="budget">Budget (million)</th>
          </tr>
      </thead>
      <tbody>`;

  data.forEach((element) => {
    table += `<tr data-id="${element.id}">
                <td class="title" contenteditable="true">${element.title}</td>
                <td class="link" contenteditable="true"><a href="${element.link}" target="_blank">${element.link}</a></td>
                <td class="duration" contenteditable="true">${element.duration}</td>
                <td class="budget" contenteditable="true">${element.budget}</td>

            </tr>
            `;
  });

  table += `</tbody>
  </table>
  </div>`;

};


export default FurnitureListPage;
