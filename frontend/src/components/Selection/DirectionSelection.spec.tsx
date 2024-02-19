import {useGlobalStore, useSelectionStore} from "@/utils/hooks";
import {DirectionSelection} from "./DirectionSelection";
import {fireEvent, render, screen} from "@testing-library/react";
import {afterEach, describe, expect, it, Mock, vi} from "vitest";
import {DistrictSelection} from "@/components/Selection/DistrictSelection";
import {HospitalSelection} from "@/components/Selection/HospitalSelection";
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

describe("DirectionSelection", () => {
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

    it("should render the component without crashing", async () => {
        render(<DirectionSelection />);
        expect(screen.getByLabelText("Направление:")).toBeTruthy();
    });

    it("should handle direction selection change", async () => {
        render(<DistrictSelection />);
        const districtSelect = screen.getByLabelText("Район:") as HTMLSelectElement;
        fireEvent.change(districtSelect, {target: {value: "1"}});

        render(<HospitalSelection />);
        const hospitalSelect = screen.getByLabelText("Больница:") as HTMLSelectElement;
        fireEvent.change(hospitalSelect, {target: {value: "2"}});

        render(<DirectionSelection />);

        const directionSelect = screen.getByLabelText("Направление:") as HTMLSelectElement;
        expect(directionSelect.value).toEqual("-1");
        fireEvent.change(directionSelect, {target: {value: "1"}});
        expect(selectionStore.selectedDirectionId).toEqual(1);

        const displayedDirectionName = screen.getByText("Терапевт");
        expect(displayedDirectionName).toBeTruthy();
    });
});
