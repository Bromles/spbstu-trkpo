import {fireEvent, render, screen} from '@testing-library/react';
import {DistrictSelection} from "@/components/Selection/DistrictSelection";
import {useGlobalStore, useSelectionStore} from "@/utils/hooks";
import {SelectionStore} from "@/stores/SelectionStore";
import {makeObservable} from "mobx";

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

jest.mock("@/utils/hooks", () => ({
    useGlobalStore: jest.fn(),
    useSelectionStore: jest.fn(),
}));

describe("DistrictSelection", () => {
    let globalStore;
    let selectionStore: MockSelectionStore;

    beforeEach(() => {
        globalStore = {
            districts: [
                {gorzdravId: 1, name: 'Адмиралтейский'},
            ],
        };

        selectionStore = new MockSelectionStore();
        (useGlobalStore as jest.Mock).mockReturnValue(globalStore);
        (useSelectionStore as jest.Mock).mockReturnValue(selectionStore);
    });

    afterEach(() => {
        jest.resetAllMocks();
    });

    it("should render the component without crashing", () => {
        render(<DistrictSelection />);
        expect(screen.getByLabelText("Район:")).toBeTruthy();
    });

    it("should handle district selection change", () => {
        render(<DistrictSelection />);

        const districtSelect = screen.getByLabelText("Район:") as HTMLSelectElement;
        expect(districtSelect.value).toEqual("-1");

        fireEvent.change(districtSelect, {target: {value: "1"}});
        expect(selectionStore.selectedDistrictId).toEqual(1);

        const displayedDistrictName = screen.getByText("Адмиралтейский");
        expect(displayedDistrictName).toBeTruthy();
    });
});
