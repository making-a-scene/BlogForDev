import React from "react";
import styled from "styled-components";

const StyledButton = styled.button`
  cursor: pointer;
  -webkit-transition: 0.2s;
  transition: 0.2s;
  border-radius: 34px;
  padding: 0.8em;
  width: 12em;
  background-color: rgb(5, 6, 69);
  color: white;
  border: solid 1px rgb(5, 6, 69);
  font-size: 0.9em;

  &:hover {
    background-color: white;
    color: rgb(5, 6, 69);
  }

  &:active {
    background-color: rgb(141, 142, 145);
    color: rgb(5, 6, 69);
  }
`;

function Button({ children, ...rest }) {
  return <StyledButton {...rest}>{children}</StyledButton>;
}

export default Button;
