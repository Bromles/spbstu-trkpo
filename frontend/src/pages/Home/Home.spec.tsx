import {
  useClientEmail,
  useClientId,
  useClientToken,
  useGlobalStore,
  useSelectionStore,
} from "@/utils/hooks";
import { render } from "@testing-library/react";
import { Home } from "./Home";
import { Mock } from "vitest";
import { getBackendUrl } from "@/utils/apiUtils";

vi.mock("@/utils/hooks", () => ({
  useClientId: vi.fn(),
  useClientToken: vi.fn().mockReturnValue("mocked-client-token"),
  useClientEmail: vi.fn(),
  useGlobalStore: vi.fn(),
  useSelectionStore: vi.fn(),
}));

vi.mock("@/utils/apiUtils", () => ({
  getBackendUrl: vi.fn(() => {
    console.log("Mocked getBackendUrl called");
    return "test-url";
  }),
}));

vi.mock("@/pages/Home/HomeApi", () => ({
  fetchHospitals: vi.fn(() => {
    console.log("Mocked fetchHospitals called");
    return new Promise((resolve) => resolve([]));
  }),
  addTracking: vi.fn(() => {
    console.log("Mocked addTracking called");
    return new Promise<void>((resolve) => resolve());
  }),
  saveClient: vi.fn(() => {
    console.log("Mocked saveClient called");
    return new Promise<void>((resolve) => resolve());
  }),
}));

vi.mock("@/components/Tracking/Tracking", () => ({
  Tracking: vi.fn(),
}));

vi.mock("@/components/Selection/DirectionSelection", () => ({
  DirectionSelection: vi.fn(),
}));

vi.mock("@/components/Selection/DistrictSelection", () => ({
  DistrictSelection: vi.fn(),
}));

vi.mock("@/components/Selection/HospitalSelection", () => ({
  HospitalSelection: vi.fn(),
}));

vi.mock("@/components/Selection/DoctorSelection", () => ({
  DoctorSelection: vi.fn(),
}));

describe("Home", () => {
  afterEach(() => {
    vi.resetAllMocks();
  });

  it("should render", () => {
    const id = {};
    const token = {};
    const email = "";
    const globalStore = { hospitals: [] };
    const selectionStore = {};
    const backendUrl = {};

    (useClientId as Mock).mockReturnValue(id);
    (useClientToken as Mock).mockReturnValue(token);
    (useClientEmail as Mock).mockReturnValue(email);
    (useGlobalStore as Mock).mockReturnValue(globalStore);
    (useSelectionStore as Mock).mockReturnValue(selectionStore);
    (getBackendUrl as Mock).mockReturnValue(backendUrl);

    render(<Home />);
  });
});
