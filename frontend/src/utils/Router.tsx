import {createBrowserRouter} from "react-router-dom";
import {NotFound} from "@/pages/NotFound/NotFound.tsx";
import {App} from "@/App.tsx";
import {ErrorPage} from "@/pages/ErrorPage/ErrorPage";

export const router = createBrowserRouter([
    {
        element: <App/>,
        errorElement: <ErrorPage/>,
        index: true
    },
    {
        path: '*',
        element: <NotFound/>
    }
]);