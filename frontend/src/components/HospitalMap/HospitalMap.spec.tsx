import {
  useClientToken,
  useGlobalStore,
  useSelectionStore,
} from "@/utils/hooks";
import { render } from "@testing-library/react";
import { HospitalMap } from "./HospitalMap";
import { Mock, describe } from "vitest";

vi.mock("@/utils/hooks", () => ({
  useClientToken: vi.fn().mockReturnValue("mocked-client-token"),
  useGlobalStore: vi.fn(),
  useSelectionStore: vi.fn(),
}));

describe("HospitalMap", () => {
  afterEach(() => {
    vi.resetAllMocks();
  });

  it("should render", () => {
    const token = {};
    const globalStore = { hospitals: [] };
    const selectionStore = {};

    (useClientToken as Mock).mockReturnValue(token);
    (useGlobalStore as Mock).mockReturnValue(globalStore);
    (useSelectionStore as Mock).mockReturnValue(selectionStore);

    render(<HospitalMap />);
  });
});
