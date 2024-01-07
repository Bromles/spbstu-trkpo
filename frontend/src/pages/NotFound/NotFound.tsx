import {Link} from "react-router-dom";

export const NotFound = () => {
  return (
    <>
      <p>Not Found</p>
      <p>
        <Link to="/">Home</Link>
      </p>
    </>
  );
};
