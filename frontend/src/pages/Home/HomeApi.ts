export const addTracking = async (
  backendUrl: string,
  token: string,
  body: string
) => {
  try {
    const response = await fetch(`${backendUrl}/v1/tracking`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: body,
    });

    return response;
  } catch (error) {
    console.error("Ошибка во время запроса:", error);
  }
};

export const saveClient = async (
  backendUrl: string,
  token: string,
  userId: string,
  userEmail: string
) => {
  try {
    await fetch(`${backendUrl}/v1/client`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ keycloakId: userId, email: userEmail }),
    });
  } catch (error) {
    console.error("Не удалось сохранить клиента: ", error);
  }
};

export const fetchHospitals = async (backendUrl: string, token: string) => {
  try {
    const response = await fetch(`${backendUrl}/v1/gorzdrav/hospital`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return await response.json();
  } catch (error) {
    console.error("Ошибка при получении данных:", error);
    return [];
  }
};
