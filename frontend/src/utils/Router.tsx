import { createBrowserRouter } from "react-router-dom";
import { NotFound } from "@/pages/NotFound/NotFound.tsx";
import { App } from "@/App.tsx";
import { ErrorPage } from "@/pages/ErrorPage/ErrorPage";
import { Home } from "@/pages/Home/Home";

export const router = createBrowserRouter([
  {
    element: <App />,
    errorElement: <ErrorPage />,
    children: [
      {
        index: true,
        element: <Home />,
      },
    ],
  },
  {
    path: "*",
    element: <NotFound />,
  },
]);
