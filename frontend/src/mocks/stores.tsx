import {SelectionStore} from "@/stores/SelectionStore";
import {makeObservable} from "mobx";
import {GlobalStore} from "@/stores/GlobalStore";
import {Direction, District, Doctor, Hospital} from "@/utils/types";

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

    mockDistricts: District[]  = [];
    mockDoctors: Doctor[] = [];
    mockDirections: Direction[] = [];
    mockHospitals: Hospital[] = [];

    constructor() {
        super();
        this.mockDistricts = [{ id: 1, gorzdravId: 1, name: "Адмиралтейский" }];
        this.mockHospitals = [
            { id: 1, gorzdravId: 1, districtId: 1, shortName: "Больница №12", fullName: "Городская больница №12 имени Пушкина",
                latitude: 3.2, longitude: 12.3, address: "за тридевять земель", phone: "88005553535" },
            { id: 2, gorzdravId: 2, districtId: 1, shortName: "Наркодиспансер", fullName: "Областной наркологический диспансер",
                latitude: 1060.2, longitude: 6392.3, address: "у черта на куличиках", phone: "--" },
        ];
        this.mockDirections = [{ id: 1, countFreeTicket: 25, name: "Терапевт" }];
        this.mockDoctors = [{ gorzdravId: 1, name: "Петров" }];
    }

    get districts(): District[] {
        return this.mockDistricts;
    }

    set districts(data: District[]) {
        this.mockDistricts = data;
    }

    get hospitals(): Hospital[] {
        return this.mockHospitals;
    }

    set hospitals(data: Hospital[]) {
        this.mockHospitals = data;
    }

    get directions(): Direction[] {
        return this.mockDirections;
    }

    set directions(data: Direction[]) {
        this.mockDirections = data;
    }

    get doctors(): Doctor[] {
        return this.mockDoctors;
    }

    set doctors(data: Doctor[]) {
        this.mockDoctors = data;
    }
}