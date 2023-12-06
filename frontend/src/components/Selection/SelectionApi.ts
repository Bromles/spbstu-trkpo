export const fetchDirections = async (
  backendUrl: string,
  token: string,
  hospitalId: number
) => {
  try {
    const res = await fetch(
      `${backendUrl}/v1/gorzdrav/specialties/${hospitalId}`,
      {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    return await res.json();
  } catch (error) {
    console.error("Ошибка при получении направлений:", error);
  }
};

export const fetchDistricts = async (backendUrl: string, token: string) => {
  try {
    const res = await fetch(`${backendUrl}/v1/gorzdrav/district`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return await res.json();
  } catch (error) {
    console.error("Ошибка при получении районов:", error);
  }
};

export const fetchDoctors = async (backendUrl: string, token: string, hospitalId: number, directionId: number) => {
  try {
    const res = await fetch(`${backendUrl}/v1/gorzdrav/doctors/${hospitalId}/${directionId}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return await res.json();
  } catch (error) {
    console.error("Ошибка при получении районов:", error);
  }
};
