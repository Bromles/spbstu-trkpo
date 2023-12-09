export const fetchTrackingItems = async (
  backendUrl: string,
  token: string,
  clientId: string
) => {
  try {
    const res = await fetch(`${backendUrl}/v1/tracking/${clientId}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return await res.json();
  } catch (error) {
    console.error("Не удалось получить отслеживания: ", error);
  }
};

export const deleteTrackingItem = async (
  backendUrl: string,
  token: string,
  itemId: number
) => {
  try {
    await fetch(`${backendUrl}/v1/tracking/${itemId}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    });
  } catch (error) {
    console.error("Ошибка при удалении отслеживания:", error);
  }
};

export const fetchHospitalInfo = async (
  backendUrl: string,
  token: string,
  hospitalGorzdavId: number,
  directionId: number,
  doctorId: number
) => {
  try {
    const res = await fetch(
      `${backendUrl}/v1/gorzdrav/trackingInfo/${hospitalGorzdavId}/${directionId}/${doctorId}`,
      {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    return await res.json();
  } catch (error) {
    console.error("Ошибка при получении данных о враче:", error);
  }
};
