import { useAuth } from "react-oidc-context";

export const NavBar = () => {
    const auth = useAuth();
    
    const authButtonHandler = () => auth.isAuthenticated ? void auth.removeUser() : void auth.signinRedirect();

    return(
        <nav>
            {auth.isAuthenticated && `Hello ${auth.user?.profile.preferred_username}`}
            <button onClick={authButtonHandler}>{auth.isAuthenticated ? 'Log out' : 'Log in'}</button>
        </nav>
    );
};