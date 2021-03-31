import { RedirectUrl } from "../Router.js";
import Navbar from "../Navbar.js";
import { getUserSessionData } from "../../utils/session.js";
import { callAPI } from "../../utils/api.js";
import PrintError from "../PrintError.js"
import img1 from "./1.jpg";
import img2 from "./2.jpg";
const API_BASE_URL = "/api/furniture/";
const IMAGES = "../../../../images";
let option;

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
  
  <div id=option></div>

</div>
`;

let validateOption = `<p>Mettre une option sur ce meuble</p>
<div class="row">
  <div class="col-12">
    <div class="row">
      Délai de l'option (max. 5 jours cumulés) :
      <div class="col-2">
          <select class="custom-select custom-select-sm>
            <option value="0">0</option>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
          </select> 
      </div>    
      <button class="btn btn-success" id="btnOption" type="submit">Valider</button>
    </div>
  </div>
</div>`;

let cancelOption = `<p>Option sur ce meuble</p>
<div class="row">
  <div class="col-12">
    <div class="row">
      Délai de l'option :
      <div class="col-2">
          <select class="custom-select custom-select-sm>
            <option value="0">0</option>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
          </select> 
      </div>
      Délai cumulé (max 5. jours) : 2   
      <button class="btn btn-danger" id="btnOption" type="submit">Annuler</button>
    </div>
  </div>
</div>`;


const FurnitureUser = (furniture) => {
  let page = document.querySelector("#page");
  page.innerHTML = furniturePage;

  option = document.querySelector("#option");

  // if option is not taken
  if(getUserSessionData() != null){
    if(!optionTaken){
      option.innerHTML = validateOption;
    }else{
      option.innerHTML = cancelOption;
    }

  //Valider / Cancel meuble
  let btnOption = document.querySelector("#btnOption");
  btnOption.addEventListener("click",onClickOption);
  }



  //Question => Mettre l'id dans l'url
  /*try {
    const furniture = await callAPI(API_BASE_URL + id , "GET",undefined);
    onFurniture(furniture);
  } catch (err) {
    console.error("FurnitureUser::onFurniture", err);
    PrintError(err);
  }*/

  
  onFurniture(furniture);



}

const onFurniture = (data) => {

  if (!data) return;
  let type = document.querySelector("#type");
  type.innerHTML = `<input class="form-control" id="type" type="text" placeholder=${data.type.name} readonly />`;
  let prix = document.querySelector("#prix");
  prix.innerHTML = `<input class="form-control" id="prix" type="text" placeholder=${data.sellingPrice} readonly />`;
  let furnitureDescription = document.querySelector("#furnitureDescription");
  furnitureDescription.innerHTML = `<textarea class="form-control" id="furnituredescription" rows="6" readonly>${data.description}</textarea>`;

}


const onClickOption = (e) => {
  e.preventDefault();
  if(optionTaken){
    console.log("cancel Option");
    option.innerHTML = validateOption;
    //Il faut cancel l'option
    optionTaken = !optionTaken;
  }else{
    console.log("take option");
    option.innerHTML = cancelOption;
    //Il faut prendre l'option
    optionTaken = !optionTaken;
  }
}


export default FurnitureUser;



