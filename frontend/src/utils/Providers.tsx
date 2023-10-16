import React from "react";
import { AuthProvider } from "react-oidc-context";
import { oidcConfig } from "@/utils/oidcConfig";
import { YMaps } from "@pbe/react-yandex-maps";

export const Providers = ({ children }: { children: React.ReactNode }) => {
  return (
    <>
      <AuthProvider {...oidcConfig}>
        <YMaps query={{ lang: 'ru_RU' }}>
          {children}
        </YMaps>
      </AuthProvider>
    </>
  );
};
