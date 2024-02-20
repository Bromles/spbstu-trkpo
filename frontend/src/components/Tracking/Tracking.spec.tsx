import { useClientId, useClientToken, useGlobalStore } from "@/utils/hooks";
import { render, screen } from "@testing-library/react";
import { Tracking } from "./Tracking";
import { Mock } from "vitest";
import { getBackendUrl } from "@/utils/apiUtils";
import {
  deleteTrackingItem,
  fetchHospitalInfo,
  fetchTrackingItems,
} from "./TrackingApi";

vi.mock("@/utils/hooks", () => ({
  useClientId: vi.fn(),
  useClientToken: vi.fn().mockReturnValue("mocked-client-token"),
  useGlobalStore: vi.fn(),
}));

vi.mock("@/utils/apiUtils", () => ({
  getBackendUrl: vi.fn(() => {
    console.log("Mocked getBackendUrl called");
    return "test-url";
  }),
}));

vi.mock("@/components/Tracking/TrackingApi", () => ({
  fetchTrackingItems: vi.fn(() => {
    console.log("Mocked fetchTrackingItems called");
    return new Promise((resolve) => resolve([]));
  }),
  fetchHospitalInfo: vi.fn(() => {
    console.log("Mocked fetchHospitalInfo called");
    return new Promise((resolve) => resolve({}));
  }),
  deleteTrackingItem: vi.fn(() => {
    console.log("Mocked deleteTrackingItem called");
    return new Promise<void>((resolve) => resolve());
  }),
}));

describe("Tracking", () => {
  afterEach(() => {
    vi.resetAllMocks();
  });

  it("should render", () => {
    const id = {};
    const token = {};
    const globalStore = {};
    const backendUrl = {};

    (useClientId as Mock).mockReturnValue(id);
    (useClientToken as Mock).mockReturnValue(token);
    (useGlobalStore as Mock).mockReturnValue(globalStore);
    (getBackendUrl as Mock).mockReturnValue(backendUrl);

    render(<Tracking />);
  });

  // it("should render hospital name", async () => {
  //   const id = {};
  //   const token = {};
  //   const globalStore = {};
  //   const backendUrl = {};

  //   (useClientId as Mock).mockReturnValue(id);
  //   (useClientToken as Mock).mockReturnValue(token);
  //   (useGlobalStore as Mock).mockReturnValue(globalStore);
  //   (getBackendUrl as Mock).mockReturnValue(backendUrl);
  //   (fetchTrackingItems as Mock).mockReturnValue(fetchTrackingItems);
  //   (fetchHospitalInfo as Mock).mockReturnValue(fetchHospitalInfo);
  //   (deleteTrackingItem as Mock).mockReturnValue(deleteTrackingItem);

  //   render(<Tracking />);
  //   expect(await screen.findByText(/Testing hospital name/)).toBeTruthy();
  // });
});
