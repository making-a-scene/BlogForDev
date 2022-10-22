import React from "react";
import styled from "styled-components";

const StyledText = styled.div`
  color: red;
  font-size: 15px;
  margin-left: 1em;
  font-weight: 100;
  display: inline;
`;

function AlertText({ children, ...rest }) {
  return <StyledText {...rest}>{children}</StyledText>;
}

export default AlertText;
