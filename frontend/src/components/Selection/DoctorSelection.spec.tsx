import { useGlobalStore, useSelectionStore } from "@/utils/hooks";
import { DoctorSelection } from "./DoctorSelection";
import { fireEvent, render, screen } from "@testing-library/react";
import { Mock, afterEach, describe, expect, it, vi } from "vitest";
import {DistrictSelection} from "@/components/Selection/DistrictSelection";
import {HospitalSelection} from "@/components/Selection/HospitalSelection";
import {DirectionSelection} from "@/components/Selection/DirectionSelection";
import {MockGlobalStore, MockSelectionStore} from "@/mocks/stores";

vi.mock("@/utils/hooks", () => ({
  useGlobalStore: vi.fn(),
  useSelectionStore: vi.fn(),
  useClientToken: vi.fn().mockReturnValue("mocked-client-token"),
}));

describe("DoctorSelection", () => {
  let globalStore: MockGlobalStore;
  let selectionStore: MockSelectionStore;

  beforeEach(() => {

    selectionStore = new MockSelectionStore();
    globalStore = new MockGlobalStore();
    (useGlobalStore as Mock).mockReturnValue(globalStore);
    (useSelectionStore as Mock).mockReturnValue(selectionStore);
  });

  afterEach(() => {
    vi.resetAllMocks();
  });

  it("should save directions to global store correctly", () => {
    expect(globalStore.districts[0].name).toEqual("Адмиралтейский");
    expect(globalStore.directions[0].countFreeTicket).toEqual(25);
    expect(globalStore.trackingToggle).toEqual(false);
  });

  it("should render the component without crashing", () => {
    render(<DoctorSelection />);
    expect(screen.getByLabelText("Доктор:")).toBeTruthy();
  });

    it("should handle doctor selection change", () => {
      render(<DistrictSelection />);
      const districtSelect = screen.getByLabelText("Район:") as HTMLSelectElement;
      fireEvent.change(districtSelect, {target: {value: "1"}});

      render(<HospitalSelection />);
      const hospitalSelect = screen.getByLabelText("Больница:") as HTMLSelectElement;
      fireEvent.change(hospitalSelect, {target: {value: "2"}});

      render(<DirectionSelection />);
      const directionSelect = screen.getByLabelText("Направление:") as HTMLSelectElement;
      fireEvent.change(directionSelect, { target: { value: "1" } });

      render(<DoctorSelection />);
      const doctorSelect = screen.getByLabelText("Доктор:") as HTMLSelectElement;
      expect(doctorSelect.value).toEqual("-1");

      fireEvent.change(doctorSelect, { target: { value: "1" } });
      expect(selectionStore.selectedDoctorId).toEqual(1);

      const displayedDoctorName = screen.getByText("Петров");
      expect(displayedDoctorName).toBeTruthy();
    });
});
