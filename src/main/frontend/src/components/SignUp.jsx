import Button from "../common/Button";
import Input from "../common/Input";
import Topic from "../common/Topic";
import AlertText from "../common/AlertText";
import React, { useState } from "react";
import styled from "styled-components";
import { toast } from "react-toastify";
import ToastOption from "../common/toastOption";
import { useNavigate } from "react-router";

const SignUp = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const [emailError, setEmailError] = useState(false);
  const [pwdError, setPwdError] = useState(false);
  const [confirmPwdError, setConfirmPwdError] = useState(false);

  const navigate = useNavigate();

  const onChangeEmail = (e) => {
    const emailRegex =
      /^[A-Za-z0-9]([-_.]?[A-Za-z0-9])*@[A-Za-z0-9]([-_.]?[A-Za-z0-9])*\.[A-Za-z]{2,3}$/;
    if (!e.target.value || emailRegex.test(e.target.value)) {
      setEmailError(false);
    } else {
      setEmailError(true);
    }
    setEmail(e.target.value);
  };

  const onChangePassword = (e) => {
    const passwordRegex = /^(?=.*[a-zA-Z])(?=.*[0-9]).{6,25}$/;
    if (!e.target.value || passwordRegex.test(e.target.value))
      setPwdError(false);
    else setPwdError(true);
    setPassword(e.target.value);
    if (confirmPassword === "") setConfirmPwdError(false);
    else if (confirmPassword !== e.target.value) setConfirmPwdError(true);
  };

  const onChangePasswordConfirm = (e) => {
    if (password === e.target.value) setConfirmPwdError(false);
    else setConfirmPwdError(true);
    setConfirmPassword(e.target.value);
  };

  const validation = () => {
    if (!password) setPwdError(true);
    if (!confirmPassword) setConfirmPwdError(true);
    if (!email) setEmailError(true);

    if (
      password &&
      confirmPassword &&
      email &&
      !pwdError &&
      !confirmPwdError &&
      !emailError
    )
      return true;
    else return false;
  };

  // active Sign Up
  const onSignUp = (e) => {
    if (validation()) {
      e.preventDefault();
      toast.success("회원 가입 완료!", ToastOption);
      navigate("/sign_in");
    } else {
      e.preventDefault();
      toast.error("정보 입력을 확실하게 해주세요.", ToastOption);
    }
  };

  const onSignIn = (e) => {
    e.preventDefault();
    navigate("/sign_in");
  };

  return (
    <>
      <Form>
        <Topic htmlFor="email">
          Email{emailError && <AlertText>Invalid email address</AlertText>}
        </Topic>
        <Input
          id="email"
          type="text"
          name="email"
          placeholder="Enter your email address"
          onChange={onChangeEmail}
        />
        <Topic htmlFor="password">
          Password
          {pwdError && (
            <AlertText>At least 6 chars with alphabets & numbers</AlertText>
          )}
        </Topic>
        <Input
          id="password"
          type="password"
          name="password"
          placeholder="At least 6 characters with alphabets & numbers"
          onChange={onChangePassword}
        />
        <Topic htmlFor="password-confirm">
          Password Confirmation
          {confirmPwdError && <AlertText>Incorrect</AlertText>}
        </Topic>
        <Input
          id="password-confirm"
          type="password"
          name="password-confirm"
          placeholder="Enter your password again"
          onChange={onChangePasswordConfirm}
        />

        <div>
          <Button
            style={{ marginRight: "1em" }}
            id="return"
            type="button"
            onClick={onSignIn}
          >
            Back
          </Button>
          <Button id="sign-up" type="submit" onClick={onSignUp}>
            Sign up
          </Button>
        </div>
      </Form>
    </>
  );
};

export default SignUp;

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
