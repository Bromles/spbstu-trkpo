import { Link, useRouteError } from "react-router-dom";

export const ErrorPage = () => {
  const error = useRouteError();

  return (
    <>
      <p>Something went wrong</p>
      <p>
        {(error as Error)?.message ||
          (error as { statusText?: string })?.statusText}
      </p>
      <p>
        <Link to="/">Home</Link>
      </p>
    </>
  );
};
