import React from "react";
import ReactDOM from "react-dom/client";
import "@/index.css";
import { router } from "@/utils/Router";
import { RouterProvider } from "react-router-dom";
import { Providers } from "@/utils/Providers.tsx";
import { AppLoading } from "@/components/AppLoading/AppLoading";

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <Providers>
      <RouterProvider router={router} fallbackElement={<AppLoading />} />
    </Providers>
  </React.StrictMode>
);
