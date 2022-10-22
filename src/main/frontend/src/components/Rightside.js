import "./Rightside.css";
import SignUp from "./SignUp";
import SignIn from "./SignIn";
import ForgetPwd from "./ForgetPwd";
import ChangePwd from "./ChangePwd";
import { Routes, Route } from "react-router-dom";

const Rightside = () => {
  return (
    <div className="right-canvas">
      <Routes>
        <Route path="/sign_up" element={<SignUp />} />
        <Route path="/sign_in" element={<SignIn />} />
        <Route path="/" element={<SignIn />} />
        <Route path="/forget_password" element={<ForgetPwd />} />
        <Route path="/change_password" element={<ChangePwd />} />
      </Routes>
    </div>
  );
};

export default Rightside;
