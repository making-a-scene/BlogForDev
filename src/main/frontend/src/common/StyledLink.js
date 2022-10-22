import React from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";

const StyledLink = styled(Link)`
  text-decoration: none;
  color: inherit;
  -webkit-transition: 0.2s;
  transition: 0.2s;

  &:hover {
    color: rgb(24, 26, 150);
  }

  &:active {
    color: rgb(5, 6, 69);
  }
`;

export default (props) => <StyledLink {...props} />;
