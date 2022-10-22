import React from "react";
import styled from "styled-components";

const StyledTopic = styled.label`
  font-size: 1.3em;
  font-weight: 600;
  text-align: left;
  margin-bottom: 5px;
`;

function Topic({ children, ...rest }) {
  return <StyledTopic {...rest}>{children}</StyledTopic>;
}

export default Topic;
