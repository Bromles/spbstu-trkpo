import {SelectionStore} from "@/stores/SelectionStore";
import {makeObservable} from "mobx";
import {GlobalStore} from "@/stores/GlobalStore";

export class MockSelectionStore extends SelectionStore {
    constructor() {
        super();
        this.selectedHospitalId = -1;
        this.selectedDistrictId = -1;
        this.selectedDoctorId = -1;
        this.selectedDirectionId = -1;
        makeObservable(this);
    }
}

export class MockGlobalStore extends GlobalStore {
    constructor() {
        super();
        this.districts = [{ id: 1, gorzdravId: 1, name: "Адмиралтейский" }];
        this.hospitals = [
            { id: 1, gorzdravId: 1, districtId: 1, shortName: "Больница №12", fullName: "Городская больница №12 имени Пушкина",
                latitude: 3.2, longitude: 12.3, address: "за тридевять земель", phone: "88005553535" },
            { id: 2, gorzdravId: 2, districtId: 1, shortName: "Наркодиспансер", fullName: "Областной наркологический диспансер",
                latitude: 1060.2, longitude: 6392.3, address: "у черта на куличиках", phone: "--" },
        ];
        this.directions = [{ id: 1, countFreeTicket: 25, name: "Терапевт" }];
        this.doctors = [{ gorzdravId: 1, name: "Петров" }];
        makeObservable(this);
    }
}