import { useAuth } from "react-oidc-context";
import styles from "./Navbar.module.css";

export const NavBar = () => {
  const auth = useAuth();

  const authButtonHandler = () =>
    auth.isAuthenticated ? void auth.removeUser() : void auth.signinRedirect();

  return (
    <header className={styles.header}>
      <div className={styles.container}>
        <div>
          {auth.isAuthenticated && auth.user?.profile.preferred_username}
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
