import {render, screen} from '@testing-library/react';
import {useGlobalStore, useSelectionStore} from "@/utils/hooks";
import {SelectionStore} from "@/stores/SelectionStore";
import {makeObservable} from "mobx";
import {HospitalSelection} from "@/components/Selection/HospitalSelection";

class MockSelectionStore extends SelectionStore {
    constructor() {
        super();
        this.selectedHospitalId = -1;
        this.selectedDistrictId = 1;
        this.selectedDoctorId = -1;
        this.selectedDirectionId = -1;
        makeObservable(this);
    }
}

jest.mock("@/utils/hooks", () => ({
    useGlobalStore: jest.fn(),
    useSelectionStore: jest.fn(),
    useClientToken: jest.fn().mockReturnValue('mocked-client-token'),
}));

describe("HospitalSelection", () => {
    let globalStore;
    let selectionStore: MockSelectionStore;

    beforeEach(() => {
        globalStore = {
            districts: [
                {gorzdravId: 1, name: 'Адмиралтейский'},
            ],
            hospitals: [
                {gorzdravId: 1, shortName: "Больница №12"},
                {gorzdravId: 2, shortName: "Наркодиспансер"}
            ]
        };

        selectionStore = new MockSelectionStore();
        (useGlobalStore as jest.Mock).mockReturnValue(globalStore);
        (useSelectionStore as jest.Mock).mockReturnValue(selectionStore);
    });

    afterEach(() => {
        jest.resetAllMocks();
    });

    it("should render the component without crashing", () => {
        render(<HospitalSelection />);
        expect(screen.getByLabelText("Больница:")).toBeTruthy();
    });

    // it("should handle hospital selection change", () => {
    //     render(<HospitalSelection />);
    //
    //     // const districtSelect = screen.getByLabelText("Район:") as HTMLSelectElement;
    //     const hospitalSelect = screen.getByLabelText("Больница:") as HTMLSelectElement;
    //     // expect(districtSelect.value).toEqual("-1");
    //     expect(hospitalSelect.value).toEqual("-1");
    //
    //     // fireEvent.change(districtSelect, {target: {value: "1"}});
    //     fireEvent.change(hospitalSelect, {target: {value: "2"}});
    //     expect(selectionStore.selectedHospitalId).toEqual(2);
    //
    //     const displayedDistrictName = screen.getByText("Наркодиспансер");
    //     expect(displayedDistrictName).toBeTruthy();
    // });
});
