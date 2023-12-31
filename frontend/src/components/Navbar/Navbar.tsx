import { useAuth } from "react-oidc-context";
import styles from "./Navbar.module.css";

export const NavBar = () => {
  const auth = useAuth();
  const clientEmail = auth.user?.profile.preferred_username;
  const clientLastname = auth.user?.profile.family_name;

  const authButtonHandler = () =>
    auth.isAuthenticated
      ? void auth.signoutRedirect({
          post_logout_redirect_uri: window.location.origin,
        })
      : void auth.signinRedirect();

  return (
    <header className={styles.header}>
      <div className={styles.container}>
        <div className={styles.name}>
          {auth.isAuthenticated && clientLastname && <br />}
          {auth.isAuthenticated && clientEmail}
        </div>
        <nav>
          <button onClick={authButtonHandler} className={styles.loginButton}>
            {auth.isAuthenticated ? "Выход" : "Вход | Регистрация"}
          </button>
        </nav>
      </div>
    </header>
  );
};
