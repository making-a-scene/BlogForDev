import React from "react";
import styled from "styled-components";

const StyledInput = styled.input`
  width: 25em;
  height: 2em;
  border-left-width: 0;
  border-right-width: 0;
  border-top-width: 0;
  border-bottom-width: 1;
  background-color: transparent;
  text-indent: 5px;
  font-size: 1em;
 
  &:focus {
    outline: none;
  }
`;

function Input({ children, ...rest }) {
  return <StyledInput {...rest}>{children}</StyledInput>;
}

export default Input;
