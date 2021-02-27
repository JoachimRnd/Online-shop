let themeSelection = document.querySelector("#themeSelection");
import { setTheme, getTheme } from "../utils/session.js";

const ThemeDropDown = () => {
  const dropDown = `
    <select id="themes">
  <option value="dark">Dark</option>
  <option value="green">Green</option>
  <option value="red">Red</option>
</select>
    `;

  const theme = getTheme();
  if(theme)
    setColor(theme);
  themeSelection.innerHTML = dropDown;
  const themeDropDown = document.querySelector("#themes");
  themeDropDown.addEventListener("change", onChange);
};

const onChange = (e) => {
  const themeDropDown = document.querySelector("#themes");
  setColor(themeDropDown.value);
  setTheme(themeDropDown.value);
};

const setColor = (color) => {
  const coloredAreas = document.querySelectorAll(
    ".header,.footer,.left,.right"
  );
  coloredAreas.forEach((element) => {
    element.classList.remove("bg-dark");
    element.classList.remove("bg-danger");
    element.classList.remove("bg-success");
    switch (color) {
      case "dark":
        element.classList.add("bg-dark");
        break;
      case "red":
        element.classList.add("bg-danger");
        break;

      case "green":
        element.classList.add("bg-success");
        break;
    }
  });
};

export default ThemeDropDown;
