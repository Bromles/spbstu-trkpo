import { SelectionStore } from "@/stores/SelectionStore";
import { useGlobalStore, useSelectionStore } from "@/utils/hooks";
import { makeObservable } from "mobx";
import { DirectionSelection } from "./DirectionSelection";
import { fireEvent, render, screen } from "@testing-library/react";
import { Mock, afterEach, describe, expect, it, vi } from "vitest";

class MockSelectionStore extends SelectionStore {
  constructor() {
    super();
    this.selectedHospitalId = -1;
    this.selectedDistrictId = -1;
    this.selectedDoctorId = -1;
    this.selectedDirectionId = -1;
    makeObservable(this);
  }
}

vi.mock("@/utils/hooks", () => ({
  useGlobalStore: vi.fn(),
  useSelectionStore: vi.fn(),
  useClientToken: vi.fn().mockReturnValue("mocked-client-token"),
}));

describe("DirectionSelection", () => {
  let globalStore;
  let selectionStore: MockSelectionStore;

  beforeEach(() => {
    globalStore = {
      directions: [{ id: 1, countFreeTicket: 1, name: "Терапевт" }],
    };

    selectionStore = new MockSelectionStore();
    (useGlobalStore as Mock).mockReturnValue(globalStore);
    (useSelectionStore as Mock).mockReturnValue(selectionStore);
  });

  afterEach(() => {
    vi.resetAllMocks();
  });

  it("should render the component without crashing", () => {
    render(<DirectionSelection />);
    expect(screen.getByLabelText("Направление:")).toBeTruthy();
  });

//   it("should handle direction selection change", () => {
//     render(<DirectionSelection />);

//     const directionSelect = screen.getByLabelText(
//       "Направление:"
//     ) as HTMLSelectElement;
//     expect(directionSelect.value).toEqual("-1");

//     fireEvent.change(directionSelect, { target: { value: "1" } });
//     expect(selectionStore.selectedDirectionId).toEqual(1);

//     const displayedDistrictName = screen.getByText("Терапевт");
//     expect(displayedDistrictName).toBeTruthy();
//   });
});
