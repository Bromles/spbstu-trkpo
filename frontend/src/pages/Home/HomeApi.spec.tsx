import { addTracking, fetchHospitals, saveClient } from "./HomeApi";

global.fetch = vi.fn();

describe("HomeApi", () => {
  beforeEach(() => vi.resetAllMocks());

  it("should post correct request to addTracking endpoint", async () => {
    const token = "token";
    const url = "url";
    const body = "";

    await addTracking(url, token, body);

    expect(fetch).toHaveBeenCalledWith(`${url}/v1/tracking`, {
      body: "",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      method: "POST",
    });
  });

  it("should post correct request to saveClient endpoint", async () => {
    const token = "token";
    const userId = "id";
    const url = "url";
    const email = "test@test.ru";

    await saveClient(url, token, userId, email);

    expect(fetch).toHaveBeenCalledWith(`${url}/v1/client`, {
      body: JSON.stringify({ keycloakId: userId, email }),
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      method: "POST",
    });
  });

  it("should send correct request to fetchHospitals endpoint", async () => {
    const token = "token";
    const url = "url";

    await fetchHospitals(url, token);

    expect(fetch).toHaveBeenCalledWith(`${url}/v1/gorzdrav/hospital`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      method: "GET",
    });
  });
});
