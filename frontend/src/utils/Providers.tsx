import React from "react";
import { AuthProvider } from "react-oidc-context";
import { oidcConfig } from "@/utils/oidcConfig";
import { YMaps } from "@pbe/react-yandex-maps";

export const Providers = ({ children }: { children: React.ReactNode }) => {
  return (
    <>
      <AuthProvider {...oidcConfig}>
        <YMaps
          query={{
            apikey: "5ef6c9f7-9d43-4357-af6b-ac44bc4686e2",
            lang: "ru_RU",
          }}
        >
          {children}
        </YMaps>
      </AuthProvider>
    </>
  );
};
