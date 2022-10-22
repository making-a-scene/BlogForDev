import "./Email.css";
import Button from "../common/Button";
import Input from "../common/Input";

const Email = () => {
  return (
    <>
      <form className="form">
        <label htmlFor="email" className="topic">
          Email
        </label>
        <Input
          id="email"
          type="text"
          name="email"
          placeholder="Enter your email address"
        />
        <div>
          <Button id="sign-up" type="submit">
            Send
          </Button>
        </div>
      </form>
    </>
  );
};

export default Email;
