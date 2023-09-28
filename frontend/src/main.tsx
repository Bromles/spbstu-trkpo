import React from 'react';
import ReactDOM from 'react-dom/client';
import '@/index.css';
import {router} from "@/utils/Router";
import {RouterProvider} from "react-router-dom";
import {Providers} from "@/utils/Providers.tsx";

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
      <Providers>
          <RouterProvider router={router}/>
      </Providers>
  </React.StrictMode>
);