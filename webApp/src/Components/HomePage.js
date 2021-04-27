import { RedirectUrl } from "./Router.js";
import Navbar from "./Navbar.js";
import { callAPIFormData } from "../utils/api.js";
import PrintError from "./PrintError.js";
const API_BASE_URL = "/api/uploads/";

let homePage = `<h4 id="pageTitle">Home</h4>
<form id="uploadForm">
  <input id="file" type="file" />
  <input type="submit" value="Upload"/>
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

  let file = document.getElementById("file").files[0];

  try {
    const fileUploaded = await callAPIFormData(
      API_BASE_URL + "image",
      "POST",
      undefined,
      file
    );
    onFileUploaded(fileUploaded);
  } catch (err) {
    console.error("HomePage::onUpload", err);
    PrintError(err);
  }
};

const onFileUploaded = (fileData) => {
  //console.log(fileData);
  Navbar();
  RedirectUrl("/");
};

export default HomePage;