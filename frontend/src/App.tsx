import { Outlet } from "react-router-dom";
import { AuthGuard } from "@/components/AuthGuard/AuthGuard";
import { NavBar } from "@/components/Navbar/Navbar";
import { NavigationLoading } from "@/components/NavigationLoading/NavigationLoading";

export const App = () => {
  return (
    <>
      <NavBar />
      <NavigationLoading>
        <AuthGuard>
          <Outlet />
        </AuthGuard>
      </NavigationLoading>
    </>
  );
};
