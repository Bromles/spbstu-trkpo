import React from "react";
import { AuthProvider } from "react-oidc-context";
import { oidcConfig } from "./oidcConfig";

export const Providers = ({ children }: { children: React.ReactNode }) => {
  return (
    <>
      <AuthProvider {...oidcConfig}>
          {children}
      </AuthProvider>
    </>
  );
};
