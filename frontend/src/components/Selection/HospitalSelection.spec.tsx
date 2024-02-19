import {fireEvent, render, screen} from "@testing-library/react";
import { useGlobalStore, useSelectionStore } from "@/utils/hooks";
import { HospitalSelection } from "@/components/Selection/HospitalSelection";
import { Mock, afterEach, describe, expect, it, vi } from "vitest";
import {DistrictSelection} from "@/components/Selection/DistrictSelection";
import {MockGlobalStore, MockSelectionStore} from "@/mocks/stores";

vi.mock("@/utils/hooks", () => ({
  useGlobalStore: vi.fn(),
  useSelectionStore: vi.fn(),
  useClientToken: vi.fn().mockReturnValue("mocked-client-token"),
}));
vi.mock("@/utils/apiUtils", () => ({
  getBackendUrl: vi.fn(() => {
    console.log("Mocked getBackendUrl called");
    return "test-url";
  }),
}));
let globalStore: MockGlobalStore;

vi.mock("@/components/Selection/SelectionApi", () => ({
  fetchDirections: vi.fn(() => Promise.resolve({data: globalStore.directions})),
  fetchDoctors: vi.fn((directionId: number) => Promise.resolve(
      {data: [globalStore.doctors.find(doctor => doctor.gorzdravId === directionId)]})),
}));

describe("HospitalSelection", () => {
  let selectionStore: MockSelectionStore;

  beforeEach(() => {
    globalStore = new MockGlobalStore();
    selectionStore = new MockSelectionStore();
    (useGlobalStore as Mock).mockReturnValue(globalStore);
    (useSelectionStore as Mock).mockReturnValue(selectionStore);
  });

  afterEach(() => {
    vi.resetAllMocks();
  });

  it("should render the component without crashing", () => {
    render(<HospitalSelection />);
    expect(screen.getByLabelText("Больница:")).toBeTruthy();
  });

  it("should handle hospital selection change", () => {
    render(<DistrictSelection />);
    const districtSelect = screen.getByLabelText("Район:") as HTMLSelectElement;
    fireEvent.change(districtSelect, {target: {value: "1"}});

    render(<HospitalSelection />);
    const hospitalSelect = screen.getByLabelText("Больница:") as HTMLSelectElement;
    expect(hospitalSelect.value).toEqual("-1");

    fireEvent.change(hospitalSelect, {target: {value: "2"}});
    expect(selectionStore.selectedHospitalId).toEqual(2);

    const displayedHospitalName = screen.getByText("Наркодиспансер");
    expect(displayedHospitalName).toBeTruthy();
  });
});
