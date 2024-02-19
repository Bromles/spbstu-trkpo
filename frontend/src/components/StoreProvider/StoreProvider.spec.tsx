import { GlobalStore } from '@/stores/GlobalStore';
import { SelectionStore } from '@/stores/SelectionStore';
import {expect} from "vitest";

describe('GlobalStore', () => {
    it('should create a GlobalStore', () => {
        const globalStore = new GlobalStore();
        expect(globalStore).toBeTruthy();
    });

    it('should set hospitals', () => {
        const globalStore = new GlobalStore();
        const hospitals = [ { id: 1, gorzdravId: 1, districtId: 1, shortName: "Травмпункт 1", fullName: "Травмпункт 1 Выборгского района",
            latitude: 3344, longitude: 555, address: "за тридевять земель", phone: "88005553535" }];
        globalStore.hospitals = hospitals;
        expect(globalStore.hospitals).toEqual(hospitals);
    });

    it('reload toggle', () => {
        const globalStore = new GlobalStore();
        expect(globalStore.trackingToggle).toEqual(false);
        globalStore.toggleReload();
        expect(globalStore.trackingToggle).toEqual(true);
    });
});

describe('SelectionStore', () => {
    it('should create a SelectionStore', () => {
        const selectionStore = new SelectionStore();
        expect(selectionStore).toBeTruthy();
    });

    it('should set selected hospital id', () => {
        const selectionStore = new SelectionStore();
        selectionStore.selectedHospitalId = 1;
        expect(selectionStore.selectedHospitalId).toEqual(1);
    });
});