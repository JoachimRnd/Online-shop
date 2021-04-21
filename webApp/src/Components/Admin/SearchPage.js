import { getUserSessionData } from "../../utils/session.js";
import { callAPI,callAPIWithoutJSONResponse } from "../../utils/api.js";
import PrintError from "../PrintError.js";
const API_BASE_URL = "/api/admin/";

let userList, typeList;

let fieldsTypeUser = `
<input class="col-auto" type="text" id="user" placeholder="Pseudo">
<input class="col-auto" type="text" id="postcode" placeholder="Code Postal">
<input class="col-auto" type="text" id="commune" placeholder="Commune">`;
let fieldsTypeFurniture = `
<input class="col-auto" type="text" id="type" placeholder="Type">
<input class="col-auto" type="number" id="price" placeholder="Prix max">
<input class="col-auto" type="text" id="user" placeholder="Utilisateur">`;
let searchPage = `<h4 id="pageTitle">Recherche</h4>
<div class="row">
<div class="col-2">
<select class="custom-select custom-select-sm" id="searchType">
<option value="user">Client</option>
<option value="furniture">Meuble</option>
</select> 
</div>
<div class="col-8" id="searchTypeFields">
</div>
<div class="col-2">
<button class="btn btn-success" id="searchBtn" type="submit">Rechercher</button>
</div>
</div>
<div id="autocomplete"></div>
<div id="results"></div>`;

const SearchPage = async () => {
    let page = document.querySelector("#page");
    page.innerHTML = searchPage;
    let searchType = document.querySelector('#searchType');
    searchType.addEventListener("change", onSearchType);
    let user = getUserSessionData();
    userList = await callAPI(API_BASE_URL + "allusers", "GET", user.token);
    typeList = await callAPI(API_BASE_URL + "alltypes", "GET", user.token);
    let searchBtn = document.querySelector('#searchBtn');
    searchBtn.addEventListener("click", onSubmitSearch);
    onUserSearchType();
};

const onSearchType = () => {
    clearResults();
    let searchType = document.querySelector('#searchType');
    searchType = searchType.value;
    if(searchType === "user") {
        onUserSearchType();
    } else {
        onFurnitureSearchType();
    }
};

const onUserSearchType = () => {
    let searchTypeFields = document.querySelector('#searchTypeFields');
    searchTypeFields.innerHTML = fieldsTypeUser;
    let usernameField = document.querySelector('#user');
    usernameField.addEventListener("click", onInput);
    let postcodeField = document.querySelector('#postcode');
    postcodeField.addEventListener("click", onInput);
    let communeField = document.querySelector('#commune');
    communeField.addEventListener("click", onInput);
};

const onFurnitureSearchType = () => {
    let searchTypeFields = document.querySelector('#searchTypeFields');
    searchTypeFields.innerHTML = fieldsTypeFurniture;
    let typeField = document.querySelector('#type');
    typeField.addEventListener("click", onInput);
    let priceField = document.querySelector('#price');
    priceField.addEventListener("click", onInput);
    let usernameField = document.querySelector('#user');
    usernameField.addEventListener("click", onInput);
};

const onInput = async () => {
    //Fermer l'autocomplete déjà existant
    let autocomplete = document.querySelector('#autocomplete');
    autocomplete.innerHTML = "";
    //Verifier si valeur déjà ecrite
    let input = document.activeElement;
    //Ajouter listener à chaque nouvelle lettre
    onAutoComplete();
    input.addEventListener('keyup', onAutoComplete);
    input.addEventListener('blur', onCloseAutocomplete);
};

const onAutoComplete = async () => {
    //Récupérer valeur à filtrer
    let searchInput = document.activeElement;
    let search = searchInput.value;
    //Si rien à filtrer => Résultats vide
    if (search.length === 0) {
        showAutoCompleteResults();
        return;
    }
    //Filtrer resultats
    let matches = [];
    if(searchInput == document.querySelector('#user')) {
        matches = userList.filter(data => {
                let regex = new RegExp(`^${search}`, 'gi');
                return data.username.match(regex);
            }
        )
    } else if (searchInput == document.querySelector('#commune')) {
        matches = userList.filter(data => {
                let regex = new RegExp(`^${search}`, 'gi');
                return data.address.commune.match(regex);
            }
        )
    } else if (searchInput == document.querySelector('#type')) {
        matches = typeList.filter(data => {
                let regex = new RegExp(`^${search}`, 'gi');
                return data.name.match(regex);
            }
        )
    }
    //Afficher les résultats
    showAutoCompleteResults(matches);
};

const showAutoCompleteResults = (matches = []) => {
    //Afficher resultat
    if(matches.length === 0){
        onCloseAutocomplete();
        return;
    }
    let results = document.querySelector('#autocomplete')
    if(document.activeElement === document.querySelector('#user')) {
        results.innerHTML = matches.map(match => `
            <div class="card card-body mb-1">
                <p>${match.username}</p>
            </div>
        `).join('');
    } else if (document.activeElement === document.querySelector('#commune')) {
        results.innerHTML = matches.map(match => `
            <div class="card card-body mb-1">
                <p>${match.address.commune}</p>
            </div>
        `).join('');
    } else if (document.activeElement === document.querySelector('#type')) {
        results.innerHTML = matches.map(match => `
            <div class="card card-body mb-1">
                <p>${match.name}</p>
            </div>
        `).join('');
    }
    //TODO gestion du click sur resultat
    //TODO gestion fleche haut bas
    //TODO gestion enter sur resultat
    //TODO afficher ça en dropdown
};

const onCloseAutocomplete = () => {
    let results = document.querySelector('#autocomplete')
    results.innerHTML = '';
}

const onSubmitSearch = () => {
    clearResults();
    let searchType = document.querySelector('#searchType');
    if(searchType.value === "user") {
        onSubmitSearchUser();
    } else {
        onSubmitSearchFurniture();
    }
}

const onSubmitSearchUser = async () => {
    let usernameField = document.querySelector('#user');
    let postcodeField = document.querySelector('#postcode');
    let communeField = document.querySelector('#commune');
    let filters = "";
    if (usernameField.value.length !== 0) {
        filters += "?username=" + usernameField.value;
    }
    if (postcodeField.value.length !== 0) {
        if(filters.length === 0) {
            filters += "?";
        } else {
            filters += "&";
        }
        filters += "postcode=" + postcodeField.value;
    }
    if (communeField.value.length !== 0) {
        if(filters.length === 0) {
            filters += "?";
        } else {
            filters += "&";
        }
        filters += "commune=" + communeField.value;
    }
    let user = getUserSessionData();
    console.log(filters);
    let resultList = await callAPI(API_BASE_URL + "users" + filters, "GET", user.token);
    onResults(resultList);
};

const onSubmitSearchFurniture = async () => {
    let typeField = document.querySelector('#type');
    let priceField = document.querySelector('#price');
    let usernameField = document.querySelector('#user');
    let filters = "";
    if (typeField.value.length !== 0) {
        filters += "?type=" + typeField.value;
    }
    if (priceField.value.length !== 0) {
        if(filters.length === 0) {
            filters += "?";
        } else {
            filters += "&";
        }
        filters += "price=" + priceField.value;
    }
    if (usernameField.value.length !== 0) {
        if(filters.length === 0) {
            filters += "?";
        } else {
            filters += "&";
        }
        filters += "username=" + usernameField.value;
    }
    let user = getUserSessionData();
    console.log(filters);
    let resultList = await callAPI(API_BASE_URL + "furnitures" + filters, "GET", user.token);
    onResults(resultList);
};

const onResults = (resultList = []) => {
    if(resultList.length === 0) {
        PrintError({message : "Il n'y a pas de résultats"})
        return;
    }
    console.log(resultList);
    if(resultList[0].username !== undefined) {
        onResultsForUsers(resultList);
    } else {
        onResultsForFurnitures(resultList);
    }
};

const onResultsForUsers = (resultList) => {
    let resultDiv = document.querySelector('#results');
    resultList.forEach((item, index) => {
        resultDiv.innerHTML += (index+1) + `) ` + item.username + `<br/>`;
    })
};

const onResultsForFurnitures = (resultList) => {
    let resultDiv = document.querySelector('#results');
    resultList.forEach((item, index) => {
        resultDiv.innerHTML += (index+1) + `) ` + item.description + `<br/>`;
    })
};

const clearResults = () => {
    let resultDiv = document.querySelector('#results');
    resultDiv.innerHTML = "";
    let errorBoard = document.querySelector('#errorBoard');
    if(errorBoard !== null) {
        errorBoard.remove();
    }
};

export default SearchPage;