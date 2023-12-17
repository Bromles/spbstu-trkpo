import { AuthProviderProps } from "react-oidc-context";
import { getAuthUrl } from "./apiUtils";

export const oidcConfig: AuthProviderProps = {
  authority: getAuthUrl(),
  client_id: "frontend",
  redirect_uri: window.location.origin,
  onSigninCallback: (): void => {
    window.history.replaceState({}, document.title, window.location.pathname);
  },
};
