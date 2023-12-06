import { useAuth } from "react-oidc-context";

export const useClientToken = () => {
  const auth = useAuth();

  return auth.user!.access_token;
};

export const useClientId = () => {
  const auth = useAuth();

  return auth.user!.profile.sub;
};

export const useClientEmail = () => {
  const auth = useAuth();

  return auth.user!.profile.preferred_username!;
};
