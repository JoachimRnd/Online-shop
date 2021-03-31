import { RedirectUrl } from "../Router.js";
import Navbar from "../Navbar.js";
import { getUserSessionData } from "../../utils/session.js";
import { callAPIWithoutJSONResponse } from "../../utils/api.js";
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
      <option id="en_restauration" value="en_restauration">En restauration</option>
      <option id="en_magasin" value="en_magasin">En magasin</option>
      <option id="en_vente" value="en_vente">En vente</option>
      <option id="retire_de_vente" value="retire_de_vente">Retiré de la vente</option>
    </select>
  </div>

  <button class="btn btn-success" id="btnSave" type="submit">Enregistrer</button>

</div>
`;

const FurnitureAdmin = (f) => {
  furniture = f;
  let page = document.querySelector("#page");
  page.innerHTML = furniturePage;



  let optionCondition = document.querySelector("#"+furniture.condition);
  optionCondition.setAttribute("selected","");

  let btnSave = document.querySelector("#btnSave");
  btnSave.addEventListener("click", onSave);
  

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
    }
    
  });




  //Question => Mettre l'id dans l'url
  /*try {
    const furniture = await callAPI(API_BASE_URL + id , "GET",undefined);
    onFurniture(furniture);
  } catch (err) {
    console.error("FurnitureUser::onFurniture", err);
    PrintError(err);
  }*/

  



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
        condition: conditionChoice
      };
    } 

    if(conditionChoice != furniture.condition || furniture.condition == "en_vente"){
      await callAPIWithoutJSONResponse(API_BASE_URL + furniture.id, "PUT", user.token, struct);
    }
}


export default FurnitureAdmin;