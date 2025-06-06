import { RedirectUrl } from "../Router.js";
import { getUserSessionData } from "../../utils/session.js";
import { callAPI } from "../../utils/api.js";
import PrintError from "../PrintError.js"
const API_BASE_URL_USER = "/api/furniture/";
const API_BASE_URL_ADMIN = "/api/admin/";

let visitListAdminPage = `
<h4 id="pageTitle">Liste de demandes de visites</h4>
<div id="visitList"></div>
`;

const VisitListAdminPage = async(f) => {
    let page = document.querySelector("#page");
    page.innerHTML = visitListAdminPage;

    let user = getUserSessionData();
    try {
        let visitList = await callAPI(API_BASE_URL_ADMIN + "allvisitsopenned", "GET", user.token);
        onVisitList(visitList);
    } catch (err) {
        console.error("FurnitureListPage::onFurnitureList", err);
        PrintError(err);
    }


};

const onVisitList = (data) => {
    if (!data) return;
    let visitList = `
  <div id="tablevisits" class="table-responsive mt-3">
  <table class="table">
      <thead>
          <tr>
              <th>Demandeur</th>
              <th>Date de la demande</th>
              <th>Etat</th>
          </tr>
      </thead>
      <tbody>`;

    data.forEach((element) => {
        let date = new Date(element.requestDate);
        visitList += `<tr>
                  <td><a id="visit${element.id}" href="" target="_blank">${element.customer.firstName} ${element.customer.lastName}</a></td>
                  <td>${date.getDate() + "/" + (date.getMonth()+1) + "/" + date.getFullYear()}</td>
                  <td>${element.status}</td>
               </tr>`;
    });

    visitList += `</tbody>
  </table>
  </div>`;


    let visitListDiv = document.querySelector("#visitList");
    visitListDiv.innerHTML = visitList;

    data.forEach((element) => {
        let visitElement = document.getElementById("visit"+element.id);
        visitElement.addEventListener("click", (e) => {
            e.preventDefault();
            RedirectUrl("/admin/visit",element,"?id="+element.id);
        });
    });
};

export default VisitListAdminPage;