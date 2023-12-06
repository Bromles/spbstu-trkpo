import { useAuth } from "react-oidc-context";
import styles from "./Navbar.module.css";
import { useClientEmail } from "@/utils/hooks";

export const NavBar = () => {
  const auth = useAuth();
  const clientEmail = useClientEmail();

  const authButtonHandler = () =>
    auth.isAuthenticated ? void auth.removeUser() : void auth.signinRedirect();

  return (
    <header className={styles.header}>
      <div className={styles.container}>
        <div className={styles.name}>
          {auth.isAuthenticated && clientEmail}
        </div>
        <nav>
          <button onClick={authButtonHandler}>
            {auth.isAuthenticated ? "Выход" : "Вход | Регистрация"}
          </button>
        </nav>
      </div>
    </header>
  );
};
