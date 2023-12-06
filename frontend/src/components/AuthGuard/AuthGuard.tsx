import { useAuth } from "react-oidc-context";
import React from "react";
import { UnauthHome } from "@/pages/UnauthHome/UnauthHome";

export const AuthGuard = ({ children }: { children: React.ReactNode }) => {
  const auth = useAuth();

  switch (auth.activeNavigator) {
    case "signinSilent":
      return <div>Signing you in ...</div>;
    case "signoutRedirect":
      return <div>Signing you out ...</div>;
  }

  if (auth.isLoading) {
    return <div>Auth is loading...</div>;
  }

  if (auth.error) {
    return <div>Oops... Auth error {auth.error.message}</div>;
  }

  if (!auth.isAuthenticated) {
    return <UnauthHome />;
  } else {
    return <>{children}</>;
  }
};
