import { RedirectUrl } from "../Router.js";
import { getUserSessionData } from "../../utils/session.js";
import { callAPI } from "../../utils/api.js";
import PrintError from "../PrintError.js";
const API_BASE_URL = "/api/search/";

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
    const furnitures = await callAPI(API_BASE_URL, "GET", user.token);
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
              <th class="save">Save</th>
              <th class="delete">Delete</th>
          </tr>
      </thead>
      <tbody>`;

  data.forEach((element) => {
    table += `<tr data-id="${element.id}">
                <td class="title" contenteditable="true">${element.title}</td>
                <td class="link" contenteditable="true"><a href="${element.link}" target="_blank">${element.link}</a></td>
                <td class="duration" contenteditable="true">${element.duration}</td>
                <td class="budget" contenteditable="true">${element.budget}</td>
                <td class="save"><button class="btn btn-primary saveBtn">Save</button></td>
                <td class="delete"><button class="btn btn-dark deleteBtn">Delete</button></td>
            </tr>
            `;
  });

  table += `</tbody>
  </table>
  </div>`;
  page.innerHTML += table;

  page.innerHTML +=
    '<button id="addBtn" class="btn btn-primary mt-2">Add film</button>';

  const saveBtns = document.querySelectorAll(".saveBtn");
  const deleteBtns = document.querySelectorAll(".deleteBtn");
  deleteBtns.forEach((deleteBtn) => {
    deleteBtn.addEventListener("click", onDelete);
  });

  saveBtns.forEach((saveBtn) => {
    saveBtn.addEventListener("click", onSave);
  });

  const addBtn = document.querySelector("#addBtn");
  addBtn.addEventListener("click", onAddFilm);
};


export default FurnitureListPage;
