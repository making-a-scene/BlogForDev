import "./Header.css";
import { useState } from "react";
import { BsFillMoonFill, BsFillSunFill } from "react-icons/bs";
import { Link } from "react-router-dom";
import styled from "styled-components";

const Header = ({ toggleTheme, isDarkTheme }) => {
  const [isToggled, setIsToggled] = useState(isDarkTheme);

  const onToggle = () => {
    setIsToggled(!isToggled);
    toggleTheme();
  };

  return (
    <div className="header-container">
      <div className="header">
        <Title to="/sign_in">BlogDev</Title>
        <p>
          <BsFillSunFill />
        </p>
        <div className="slider-container">
          <label className="switch">
            <input type="checkbox" checked={isToggled} onChange={onToggle} />
            <span className="slider round"></span>
          </label>
        </div>
        <p>
          <BsFillMoonFill />
        </p>
      </div>
    </div>
  );
};

export default Header;

const Title = styled(Link)`
  color: white;
  text-decoration: none;
  position: fixed;
  left: 1em;
  font-size: 1.5em;
`;
