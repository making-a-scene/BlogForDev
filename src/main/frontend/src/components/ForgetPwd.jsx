import Button from "../common/Button";
import Topic from "../common/Topic";
import Input from "../common/Input";
import styled from "styled-components";
import AlertText from "../common/AlertText";
import { useNavigate } from "react-router";
import { toast } from "react-toastify";
import React, { useState } from "react";
import ToastOption from "../common/toastOption";

const ForgetPwd = () => {
  const navigate = useNavigate();
  const [emailError, setEmailError] = useState(false);
  const [email, setEmail] = useState("");

  const onSignIn = (e) => {
    e.preventDefault();
    navigate("/sign_in");
  };

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

  const submitEmail = (e) => {
    if (!emailError && email) {
      e.preventDefault();
      toast.success(
        "인증 링크 전송했습니다! 메일함을 확인해주세요.",
        ToastOption
      );
      navigate("/sign_in");
    } else {
      e.preventDefault();
      toast.error("이메일 주소를 다시 한 번 확인해주세요.", ToastOption);
    }
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
        <div>
          <Button
            style={{ marginRight: "1em" }}
            id="return"
            type="button"
            onClick={onSignIn}
          >
            Back
          </Button>
          <Button id="next" type="submit" onClick={submitEmail}>
            Next
          </Button>
        </div>
      </Form>
    </>
  );
};

export default ForgetPwd;

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
