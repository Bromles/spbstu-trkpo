export const getBackendUrl = () => {
  return import.meta.env.MODE === "production"
    ? import.meta.env.VITE_PROD_BACKEND_URL
    : import.meta.env.VITE_DEV_BACKEND_URL;
};
