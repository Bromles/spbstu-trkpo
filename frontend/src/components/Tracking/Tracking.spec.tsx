import { useClientId, useClientToken, useGlobalStore } from "@/utils/hooks";
import { render, screen } from "@testing-library/react";
import { Tracking } from "./Tracking";
import { Mock } from "vitest";
import { getBackendUrl } from "@/utils/apiUtils";

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

  // it("should render hospital name", () => {
  //   const id = {};
  //   const token = {};
  //   const globalStore = {};
  //   const backendUrl = {};

  //   (useClientId as Mock).mockReturnValue(id);
  //   (useClientToken as Mock).mockReturnValue(token);
  //   (useGlobalStore as Mock).mockReturnValue(globalStore);
  //   (getBackendUrl as Mock).mockReturnValue(backendUrl);

  //   render(<Tracking />);
  //   expect(screen.getByText(/Testing hospital name/)).toBeTruthy();
  // });
});
