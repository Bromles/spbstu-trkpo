import {fireEvent, render, screen} from '@testing-library/react';
import {DistrictSelection} from "@/components/Selection/DistrictSelection";
import {useGlobalStore, useSelectionStore} from "@/utils/hooks";
import {SelectionStore} from "@/stores/SelectionStore";
import {makeObservable} from "mobx";
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
        (useGlobalStore as Mock).mockReturnValue(globalStore);
        (useSelectionStore as Mock).mockReturnValue(selectionStore);
    });

    afterEach(() => {
        vi.resetAllMocks();
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
