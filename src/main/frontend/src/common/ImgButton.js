import React from "react";
import styled from "styled-components";

const StyleImgdButton = styled.button`
  cursor: pointer;
  -webkit-transition: 0.2s;
  width: 3.5em;
  height: 3.5em;
  border: none;
  margin: 45px 25px 45px 25px;
  background-color: transparent;
`;

function ImgButton({ children, ...rest }) {
  return <StyleImgdButton {...rest}>{children}</StyleImgdButton>;
}

export default ImgButton;
