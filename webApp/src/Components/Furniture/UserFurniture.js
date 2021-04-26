import { RedirectUrl } from "../Router.js";
import { getUserSessionData } from "../../utils/session.js";
import {callAPI, callAPIWithoutJSONResponse} from "../../utils/api.js";
import PrintError from "../PrintError.js"
import img1 from "./1.jpg";
import img2 from "./2.jpg";
const API_BASE_URL = "/api/furniture/";
const IMAGES = "../../../../images";

let furniture;

let furniturePage = `
<h4 id="pageTitle">Furniture User</h4>
<div class="row">
  <div class="col-6">
  <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
  <ol class="carousel-indicators">
    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
  </ol>
  <div class="carousel-inner">
    <div class="carousel-item active">
      <img src="${img1}" class="d-block w-100" alt="1">
    </div>
    <div class="carousel-item">
      <img src="${img2}" class="d-block w-100" alt="2">
    </div>
    <div class="carousel-item">
      <img src="${img2}" class="d-block w-100" alt="3">
    </div>
  </div>
  <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="sr-only">Previous</span>
  </a>
  <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="sr-only">Next</span>
  </a>
  </div>
  </br>
  <button class="btn btn-secondary" id="btnReturn">Retour</button>
</div>

<div class="col-6">
  <div class="form-group">
    <div class="row">
      <div class="col-6">
          <div class="form-group">
            <label for="type">Type de meuble</label>
            <div id="type"></div>
          </div>
      </div>
      <div class="col-6">
        <div class="form-group">
            <div id="prix"><label for="type">Prix</label></div>
        </div>
      </div>
      <div class="col-6">
          <div class="form-group">
              <label for="condition">Etat</label>
              <div id="condition"></div>
          </div>
      </div>
      <div class="col-6">
        <div class="form-group">
          <label for="dateOfSale">Date de vente</label>
          <div id="dateOfSale"></div>
        </div>
      </div>
      <div class="col-6">
          <div class="form-group">
              <label for="withdrawalDateFromCustomer">Date de retrait</label>
              <div id="withdrawalDateFromCustomer"></div>
          </div>
        </div>
        <div class="col-6">
          <div class="form-group">
              <label for="deliveryDate">Date de livraison</label>
              <div id="deliveryDate"></div>
          </div>
        </div>
  </div>

  <div class="row">
    <div class="col-12">
      <div class="form-group">
        <label for="furnituredescription">Description du meuble</label>
        <div id="furnitureDescription"></div>
      </div>
    </div>
  </div>
  

</div>
`;



const UserFurniture = async (data) => {
  let page = document.querySelector("#page");
  page.innerHTML = furniturePage;
  const user = getUserSessionData();

  let btnReturn = document.querySelector("#btnReturn");
  btnReturn.addEventListener("click", () => RedirectUrl("/myfurnitures"));

  if(data == null){
    let queryString = window.location.search;
    let urlParams = new URLSearchParams(queryString);
    let id = urlParams.get("id");
    try {
      furniture = await callAPI(API_BASE_URL +  "personal/" + id , "GET",user.token);
    } catch (err) {
      console.error("UserFurniture::GetFurnitureByID", err);
      PrintError(err);
    }
  }else{
    furniture = data;
  }



  onFurniture();
}

const onFurniture = () => {
  let type = document.querySelector("#type");
  if(furniture){
    type.innerHTML = `<input class="form-control" id="type" type="text" placeholder=${furniture.type.name} readonly />`;
  
  let prix = document.querySelector("#prix");
  if(furniture.purchasePrice){
    prix.innerHTML = `<label for="prix">Prix d'achat</label> <input class="form-control" id="prix" type="text" placeholder=${furniture.purchasePrice} readonly />`;
  }else 
    prix.innerHTML = `<label for="prix">Prix de vente</label> <input class="form-control" id="prix" type="text" placeholder=${furniture.sellingPrice} readonly />`;
  let furnitureDescription = document.querySelector("#furnitureDescription");
  furnitureDescription.innerHTML = `<textarea class="form-control" id="furnituredescription" rows="6" readonly>${furniture.description}</textarea>`;

  let deliveryDate = document.querySelector("#deliveryDate");
  if(furniture.deliveryDate)
    deliveryDate.innerHTML = `<input class="form-control" id="inputDeliveryDate" type="date" value="${furniture.deliveryDate}" readonly/>`;
  else
    deliveryDate.innerHTML = `<input class="form-control" id="inputDeliveryDate" type="date" readonly/>`;

  let withdrawalDateFromCustomer = document.querySelector("#withdrawalDateFromCustomer");
    if(furniture.withdrawalDateFromCustomer)
      withdrawalDateFromCustomer.innerHTML = `<input class="form-control" id="inputWithdrawalDateFromCustomer" type="date" value="${furniture.withdrawalDateFromCustomer}" readonly/>`;
    else
      withdrawalDateFromCustomer.innerHTML = `<input class="form-control" id="inputWithdrawalDateFromCustomer" type="date" readonly/>`;
  let dateOfSale = document.querySelector("#dateOfSale");
  if(furniture.sellingDate){
    dateOfSale.innerHTML = `<input class="form-control" id="dateOfSale" type="date" value="${furniture.sellingDate}" readonly/>`;
  }else
    dateOfSale.innerHTML = `<input class="form-control" id="dateOfSale" type="date" readonly/>`;
  let condition = document.querySelector("#condition");
  condition.innerHTML = `<input class="form-control" id="condition" type="text" placeholder=${furniture.condition} readonly />`;
  }
}







export default UserFurniture;



