import React, { useState } from "react";
import { ToastContainer } from "react-toastify";
import Header from "./components/Header";
import Leftside from "./components/Leftside";
import Rightside from "./components/Rightside";
import "react-toastify/dist/ReactToastify.css";
import styled, { ThemeProvider } from "styled-components";
import { BrowserRouter } from "react-router-dom";

const darkTheme = {
  body: "#3F3F43",
  fontColor: "#F0F0F0",
};

const lightTheme = {
  body: "#F0F0F0",
  fontColor: "black",
};

function App() {
  const [theme, setTheme] = useState("light");
  const isDarkTheme = theme === "dark";

  const toggleTheme = () => {
    setTheme(isDarkTheme ? "light" : "dark");
  };

  return (
    <ThemeProvider theme={isDarkTheme ? darkTheme : lightTheme}>
      <Container>
        <BrowserRouter>
          <ToastContainer
            position="top-right"
            autoClose={1000}
            hideProgressBar={false}
            newestOnTop={false}
            closeOnClick
            rtl={false}
            pauseOnFocusLoss
            draggable
            pauseOnHover
          />
          <Header toggleTheme={toggleTheme} isDarkTheme={isDarkTheme} />
          <Leftside />
          <Rightside />
        </BrowserRouter>
      </Container>
    </ThemeProvider>
  );
}

export default App;

const Container = styled.div`
  font-family: Helvetica, Arial, sans-serif;
  text-align: center;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-template-rows: 60px 1fr 1fr;
  height: 100%;
  background-color: ${(props) => props.theme.body};
  color: ${(props) => props.theme.fontColor};
  transition: all 0.25s ease;
`;
