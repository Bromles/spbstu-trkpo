import {Link} from "react-router-dom";

export const NotFound = () => {
  return (
    <>
      <p>Не найдено</p>
      <p>
        <Link to="/">На главную</Link>
      </p>
    </>
  );
};
