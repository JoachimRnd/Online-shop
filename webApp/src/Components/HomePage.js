import { RedirectUrl } from "./Router.js";
import Navbar from "./Navbar.js";
import callAPI from "../utils/api.js";
import PrintError from "./PrintError.js";
const API_BASE_URL = "/api/upload/";

let homePage = `<h4 id="pageTitle">Home</h4>
<form>
  <input id="file" type="file" />
  <input type="submit" value="Upload"/>
  <div id="outputDiv"></div>
</form>
`;


const HomePage = () => {  
  let page = document.querySelector("#page");
  page.innerHTML  = homePage;

  let uploadForm = document.querySelector("form");
  uploadForm.addEventListener("submit", onUpload);
};

const onUpload = async (e) => {
  e.preventDefault();  
  let uploadForm = document.querySelector("form");

  let file = document.getElementById("file");
  let oData = new FormData(uploadForm);


  try {
    const upload = await callAPI(
      API_BASE_URL + "image",
      "POST",
      undefined,
      oData
    );
    onFileUploaded(upload);
  } catch (err) {
    console.error("HomePage::onUpload", err);
    PrintError(err);
  }
};

const onFileUploaded = (fileData) => {
  // re-render the navbar for the authenticated user
  console.log(fileData);
  Navbar();
  RedirectUrl("/");
};

export default HomePage;