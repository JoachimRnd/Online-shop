import { RedirectUrl } from "../Router.js";
import Navbar from "../Navbar.js";
import { getUserSessionData } from "../../utils/session.js";
import { callAPI, callAPIWithoutJSONResponse } from "../../utils/api.js";
import PrintError from "../PrintError.js"
import img1 from "./1.jpg";
import img2 from "./2.jpg";
const API_BASE_URL = "/api/furniture/";
const IMAGES = "../../../../images";
let option;
let furniture;

let optionTaken = false;

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
            <label for="prix">Prix de vente</label>
            <div id="prix"></div>
        </div>
      </div>
      <div class="col-6">
      <div class="form-group">
          <label for="specialPrice">Prix de vente spécial</label>
          <div id="specialPrice"></div>
      </div>
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
  
  <div class="input-group mb-3">
    <div class="input-group-prepend">
      <label class="input-group-text" for="inputGroupSelect01">Etat du meuble</label>
    </div>
    <select class="custom-select" id="conditions">
      <option id="propose" value="propose">Proposé</option>
      <option id="ne_convient_pas" value="ne_convient_pas">Ne convient pas</option>
      <option id="achete" value="achete">Achete</option>
      <option id="emporte_par_patron" value="emporte_par_patron">Emporté par le patron</option>
      <option id="en_restauration" value="en_restauration">En restauration</option>
      <option id="en_magasin" value="en_magasin">En magasin</option>
      <option id="en_vente" value="en_vente">En vente</option>
      <option id="retire_de_vente" value="retire_de_vente">Retiré de la vente</option>
      <option id="en_option" value="en_option">En option</option>
      <option id="vendu" value="vendu">Vendu</option>
      <option id="reserve" value="reserve">Réservé</option>
      <option id="livre" value="livre">Livré</option>
      <option id="emporte_par_client" value="emporte_par_client">Emporté par le client</option>
    </select>
  </div>

  <button class="btn btn-success" id="btnSave" type="submit">Enregistrer</button>

  <div id="toast"></div>

</div>
`;

const FurnitureAdmin = async(f) => {
  let page = document.querySelector("#page");
  page.innerHTML = furniturePage;

  let btnSave = document.querySelector("#btnSave");
  btnSave.addEventListener("click", onSave);

  let btnReturn = document.querySelector("#btnReturn");
  btnReturn.addEventListener("click", () => RedirectUrl("/search"));

  if(f == null){
    let queryString = window.location.search;
    let urlParams = new URLSearchParams(queryString);
    let id = urlParams.get("id");
    try {
      furniture = await callAPI(API_BASE_URL + id , "GET",undefined);
    } catch (err) {
      console.error("FurnitureUser::GetFurnitureByID", err);
      PrintError(err);
    }
  }else{
    furniture = f;
  }

  try {
    const types = await callAPI(API_BASE_URL + "allFurnitureTypes", "GET", undefined);
    onTypesList(types);
  } catch (err) {
    console.error("FurnitureAdmin::onTypesList", err);
    PrintError(err);
  }

  let optionCondition = document.querySelector("#"+furniture.condition);
  optionCondition.setAttribute("selected","");
  

  onFurniture();

  let conditions = document.querySelector("#conditions");
  if(conditions.value == "en_vente"){

    onSale();
  }
  conditions.addEventListener("change",(e)=>{
    if(conditions.value == "en_vente"){
      onSale();
    }else{
      let price = document.querySelector("#prix");
      //best solution -> removeAttribute but doesnt work (have to investigate)
      price.innerHTML = `<input class="form-control" id="prix" type="text" placeholder=${furniture.sellingPrice} readonly/>`;

      let specialPrice = document.querySelector("#specialPrice");
      specialPrice.innerHTML = `<input class="form-control" id="inputSpecialPrice" type="text" placeholder=${furniture.specialSalePrice} readonly />`;
    }
    
  });
}

const onTypesList = (typesList) => {
  let typesListPage = `
  <div class="input-group mb-3">
    <select class="custom-select" id="typesList">`;
    typesList.forEach(type => {
      if(furniture.type.id == type.id)
        typesListPage += `<option id="${type.id}" selected="selected" value="${type.id}">${type.name}</option>`;
      else
        typesListPage += `<option id="${type.id}" value="${type.id}">${type.name}</option>`;
    });
  
  typesListPage += 
    `</select>
  </div>`;

document.getElementById("type").innerHTML = typesListPage;
}

const onFurniture = () => {
  console.log(furniture);
  let prix = document.querySelector("#prix");
  prix.innerHTML = `<input class="form-control" id="inputPrix" type="text" placeholder=${furniture.sellingPrice} readonly />`;

  let specialPrice = document.querySelector("#specialPrice");
  specialPrice.innerHTML = `<input class="form-control" id="inputSpecialPrice" type="text" placeholder=${furniture.specialSalePrice} readonly />`;

  let furnitureDescription = document.querySelector("#furnitureDescription");
  furnitureDescription.innerHTML = `<textarea class="form-control" id="furnituredescription" rows="6" >${furniture.description}</textarea>`;
  
}

const onSale = () => {
  let price = document.querySelector("#prix");
  let specialPrice = document.querySelector("#specialPrice");
  //best solution -> removeAttribute but doesnt work (have to investigate)
  price.innerHTML = `<input class="form-control" id="inputPrix" type="text" placeholder=${furniture.sellingPrice} />`;
  specialPrice.innerHTML = `<input class="form-control" id="inputSpecialPrice" type="text" placeholder=${furniture.specialSalePrice} />`;
}


const onSave = async() => {
    let conditionChoice = document.querySelector("#conditions");
    conditionChoice = conditionChoice.value;

    let user = getUserSessionData();
    let p = 0;
    let specialPrice = 0;

    if(conditionChoice == "en_vente"){
      p = document.querySelector("#inputPrix").value;
      furniture.sellingPrice = p;
      specialPrice = document.querySelector("#inputSpecialPrice").value;
      furniture.specialSalePrice = specialPrice;
    }
    let struct = {
      condition: conditionChoice,
      type: document.getElementById("typesList").value,
      price: p,
      specialPrice: specialPrice,
      description: document.getElementById("furnituredescription").value
    }
    
    console.log(struct);
   
    if(conditionChoice != furniture.condition || furniture.condition == "en_vente"){
      let response = await callAPIWithoutJSONResponse(API_BASE_URL + furniture.id, "PUT", user.token, struct);
      if(response.ok){
        //TODO remplacer par vrai toast
        document.getElementById("toast").innerHTML = `</br><h5 style="color:green">L'état a bien été modifié</h5>`;
      }
    }
}


export default FurnitureAdmin;