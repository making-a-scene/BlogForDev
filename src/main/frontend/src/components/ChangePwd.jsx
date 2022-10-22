import Button from "../common/Button";
import Topic from "../common/Topic";
import Input from "../common/Input";
import styled from "styled-components";
import AlertText from "../common/AlertText";
import { useNavigate } from "react-router";
import { useState } from "react";
import { toast } from "react-toastify";
import ToastOption from "../common/toastOption";

const ChangePwd = () => {
  const navigate = useNavigate();
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const [pwdError, setPwdError] = useState(false);
  const [confirmPwdError, setConfirmPwdError] = useState(false);

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

    if (password && confirmPassword && !pwdError && !confirmPwdError)
      return true;
    else return false;
  };

  const onSignIn = (e) => {
    if (validation()) {
      e.preventDefault();
      toast.success("비밀번호 변경 완료!", ToastOption);
      navigate("/sign_in");
    } else {
      e.preventDefault();
      toast.error("정보 입력을 확실하게 해주세요.", ToastOption);
    }
  };

  return (
    <>
      <Form>
        <Topic htmlFor="new-password">
          New Password
          {pwdError && (
            <AlertText>At least 6 chars with alphabets & numbers</AlertText>
          )}
        </Topic>
        <Input
          id="password"
          type="password"
          name="new-password"
          placeholder="At least 6 characters with alphabets & numbers"
          onChange={onChangePassword}
        />
        <Topic htmlFor="new-password-confirm">
          Password Confirmation
          {confirmPwdError && <AlertText>Incorrect</AlertText>}
        </Topic>
        <Input
          id="password-confirm"
          type="password"
          name="new-password-confirm"
          placeholder="Enter your password again"
          onChange={onChangePasswordConfirm}
        />
        <div>
          <Button id="return" type="submit" onClick={onSignIn}>
            Done
          </Button>
        </div>
      </Form>
    </>
  );
};

export default ChangePwd;

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
