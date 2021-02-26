import { RedirectUrl } from "./Router.js";
import Navbar from "./Navbar.js";
import { setUserSessionData } from "../utils/session.js";
import callAPI from "../utils/api.js";
import PrintError from "./PrintError.js";
const API_BASE_URL = "/api/auths/";

let registerPage = `<h4 id="pageTitle">Register</h4>
<form>
<div class="form-group">
  <label for="login">Login</label>
  <input class="form-control" id="login" type="text" placeholder="Enter your login" required="" />
</div>
<div class="form-group">
  <label for="password">Password</label>
  <input class="form-control" id="password" type="password" name="password" placeholder="Enter your password" required="" />
</div>
<button class="btn btn-primary" id="btn" type="submit">Submit</button>
<!-- Create an alert component with bootstrap that is not displayed by default-->
<div class="alert alert-danger mt-2 d-none" id="messageBoard"></div><span id="errorMessage"></span>
</form>`;

const RegisterPage = () => {
  let page = document.querySelector("#page");
  page.innerHTML = registerPage;
  let registerForm = document.querySelector("form");
  registerForm.addEventListener("submit", onRegister);
};

const onRegister = async (e) => {
  e.preventDefault();
  let user = {
    login: document.getElementById("login").value,
    password: document.getElementById("password").value,
  };

  try {
    const userRegistered = await callAPI(
      API_BASE_URL + "register",
      "POST",
      undefined,
      user
    );
    onUserRegistration(userRegistered);
  } catch (err) {
    console.error("RegisterPage::onRegister", err);
    PrintError(err);
  }
};

const onUserRegistration = (userData) => {
  console.log("onUserRegistration", userData);
  const user = { ...userData, isAutenticated: true };
  setUserSessionData(user);
  // re-render the navbar for the authenticated user
  Navbar();
  RedirectUrl("/");
};

export default RegisterPage;
