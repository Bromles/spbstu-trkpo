import { Outlet } from "react-router-dom";
import { AuthGuard } from "@/components/AuthGuard/AuthGuard";
import { NavBar } from "@/components/Navbar/Navbar";
import { NavigationLoading } from "@/components/NavigationLoading/NavigationLoading";
import styles from "./App.module.css";

export const App = () => {
  return (
    <>
      <NavBar />
      <div className={styles.mainContainer}>
        <NavigationLoading>
          <AuthGuard>
            <Outlet />
          </AuthGuard>
        </NavigationLoading>
      </div>
    </>
  );
};
