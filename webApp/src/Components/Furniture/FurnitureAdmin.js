import { RedirectUrl } from "../Router.js";
import Navbar from "../Navbar.js";
import { getUserSessionData } from "../../utils/session.js";
import { callAPI, callAPIWithoutJSONResponse } from "../../utils/api.js";
import PrintError from "../PrintError.js"
import img1 from "./1.jpg";
import img2 from "./2.jpg";
const API_BASE_URL = "/api/furniture/";
const API_BASE_URL_ADMIN = "/api/admin/";
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
      <option id="achete" value="achete">Achete</option>
      <option id="ne_convient_pas" value="ne_convient_pas">Ne convient pas</option>
      <option id="en_restauration" value="en_restauration">En restauration</option>
      <option id="en_magasin" value="en_magasin">En magasin</option>
      <option id="en_vente" value="en_vente">En vente</option>
      <option id="retire_de_vente" value="retire_de_vente">Retiré de la vente</option>
      <option id="en_option" value="en_option">En option</option>
      <option id="emporte_par_client" value="emporte_par_client">Emporte par le client</option>
    </select>
  </div>

  <button class="btn btn-success" id="btnSave" type="submit">Enregistrer</button>
  <p>
  <div id=option></div>

  <div id="toast"></div>

</div>
`;

let isOption = `
<div class="alert alert-secondary" role="alert">
  Il y a une option sur ce meuble (<strong><span id="userOption"> </span></strong>).
</div>
<button class="btn btn-danger" id="btnOption" type="submit">Annuler l'option</button>
  `;

let noOption =`
<div class="alert alert-secondary alert-dismissible fade show" role="alert">
  Il n'y a aucune option sur ce meuble.
  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
</div>`;

const FurnitureAdmin = async(f) => {
  let page = document.querySelector("#page");
  page.innerHTML = furniturePage;



  let btnSave = document.querySelector("#btnSave");
  btnSave.addEventListener("click", onSave);
  

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

  let optionCondition = document.querySelector("#"+furniture.condition);
  optionCondition.setAttribute("selected","");
  

  onFurniture();
  onCheckOption();

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
    }
    
  });
}

const onFurniture = () => {

  let type = document.querySelector("#type");
  type.innerHTML = `<input class="form-control" id="type" type="text" placeholder=${furniture.type.name} readonly />`;
  let prix = document.querySelector("#prix");
  prix.innerHTML = `<input class="form-control" id="inputPrix" type="text" placeholder=${furniture.sellingPrice} readonly />`;
  let furnitureDescription = document.querySelector("#furnitureDescription");
  furnitureDescription.innerHTML = `<textarea class="form-control" id="furnituredescription" rows="6" readonly>${furniture.description}</textarea>`;
  
}

const onSale = () => {
  let price = document.querySelector("#prix");
  //best solution -> removeAttribute but doesnt work (have to investigate)
  price.innerHTML = `<input class="form-control" id="inputPrix" type="text" placeholder=${furniture.sellingPrice} />`;
}


const onSave = async() => {
    let conditionChoice = document.querySelector("#conditions");
    conditionChoice = conditionChoice.value;

    let user = getUserSessionData();
    let struct;

    if(conditionChoice == "en_vente"){
      let p = document.querySelector("#inputPrix");
      p = p.value;
      struct = {
        condition: conditionChoice,
        price: p
      }
      furniture.sellingPrice = p;
    }else if(conditionChoice != furniture.condition){
      struct = {
        condition: conditionChoice,
        price: 0
      };
    } 
   
    if(conditionChoice != furniture.condition || furniture.condition == "en_vente"){
      try {
        await callAPIWithoutJSONResponse(API_BASE_URL + furniture.id, "PUT", user.token, struct);
        let toast = document.querySelector("#toast");
      } catch (err) {
        console.error("FurnitureAdmin::Change condition", err);
        PrintError(err);
      }

    }

 

    



}


const onCheckOption = async() => {

  let option = await callAPI(API_BASE_URL + furniture.id + "/option", "GET");
  let optionDocument = document.querySelector("#option");
  console.log(option);
  if(option.status != undefined && option.status == "en_cours") {
    optionDocument.innerHTML = isOption;
    let userOption = document.querySelector("#userOption");
    userOption.innerHTML = furniture.buyer.email;
    let btn = document.querySelector("#btnOption")
    btn.addEventListener("click", onClickCancelOption);
  }else{
    optionDocument.innerHTML = noOption;
  }


}

const onClickCancelOption = async (e) => {
  e.preventDefault();
  let optionDocument = document.querySelector("#option");
  try {
    let user = getUserSessionData();
    await callAPIWithoutJSONResponse(API_BASE_URL_ADMIN + furniture.id + "/cancelOption", "PUT",user.token);
    optionDocument.innerHTML = noOption;
    try {
      furniture = await callAPI(API_BASE_URL + furniture.id , "GET",undefined);
    } catch (err) {
      console.error("FurnitureAdmin::GetFurnitureByID", err);
      PrintError(err);
    }
    let optionCondition = document.querySelector("#"+furniture.condition);
    optionCondition.setAttribute("selected","");
  } catch (e) {
    console.log(e);
    PrintError(e);
    //Erreur
  }
}


export default FurnitureAdmin;