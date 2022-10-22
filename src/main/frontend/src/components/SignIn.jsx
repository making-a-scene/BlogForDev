import Button from "../common/Button";
import Topic from "../common/Topic";
import Input from "../common/Input";
import ImgButton from "../common/ImgButton";
import google from "../icons/google_icon.png";
import github from "../icons/github_icon.png";
import styled from "styled-components";
import { useNavigate } from "react-router";
import StyledLink from "../common/StyledLink";

const SignIn = () => {
  const navigate = useNavigate();

  const onSignIn = (e) => {
    e.preventDefault();
    navigate("/sign_in");
  };

  return (
    <>
      <Form>
        <Topic htmlFor="email">Email</Topic>
        <Input
          id="email"
          type="text"
          name="email"
          placeholder="Enter your email address"
        />
        <Topic htmlFor="password">Password</Topic>
        <Input
          id="password"
          type="text"
          name="password"
          placeholder="Enter your password"
        />
        <StyledLink to="/forget_password">Forget Password?</StyledLink>
        <div>
          <ImgButton>
            <Img src={google} alt="google" />
          </ImgButton>
          <ImgButton>
            <Img src={github} alt="github" />
          </ImgButton>
        </div>
        <p>
          <StyledLink to="/sign_up">Don't have an account? Register</StyledLink>
        </p>
        <div>
          <Button id="sign-in" type="submit" onClick={onSignIn}>
            Sign In
          </Button>
        </div>
      </Form>
    </>
  );
};

export default SignIn;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: center;
  align-content: center;
  grid-column: 2 / 3;
  grid-row: 2 / 3;

  input {
    margin-bottom: 2em;
  }
`;

const Img = styled.img`
  width: inherit;
  height: inherit;
`;
