import { GlobalStore } from "@/stores/GlobalStore";
import { SelectionStore } from "@/stores/SelectionStore";
import { render } from "@testing-library/react";
import { expect } from "vitest";
import { StoreProvider } from "./StoreProvider";

describe("GlobalStore", () => {
  let globalStore: GlobalStore;

  beforeEach(() => {
    globalStore = new GlobalStore();
  });

  it("should create a GlobalStore", () => {
    expect(globalStore).toBeTruthy();
  });

  it("get and set hospitals", () => {
    const hospitals = [
      {
        id: 1,
        gorzdravId: 1,
        districtId: 1,
        shortName: "Травмпункт 1",
        fullName: "Травмпункт 1 Выборгского района",
        latitude: 3344,
        longitude: 555,
        address: "за тридевять земель",
        phone: "88005553535",
      },
    ];
    globalStore.hospitals = hospitals;
    expect(globalStore.hospitals).toEqual(hospitals);
  });

  it("get and set districts", () => {
    const districts = [{ id: 1, gorzdravId: 1, name: "Адмиралтейский" }];
    globalStore.districts = districts;
    expect(globalStore.districts).toEqual(districts);
  });

  it("get and set directions", () => {
    const directions = [{ id: 1, countFreeTicket: 25, name: "Терапевт" }];
    globalStore.directions = directions;
    expect(globalStore.directions).toEqual(directions);
  });

  it("get and set doctors", () => {
    const doctors = [{ gorzdravId: 1, name: "Петров" }];
    globalStore.doctors = doctors;
    expect(globalStore.doctors).toEqual(doctors);
  });

  it("reload toggle", () => {
    expect(globalStore.trackingToggle).toEqual(false);
    globalStore.toggleReload();
    expect(globalStore.trackingToggle).toEqual(true);
  });
});

describe("SelectionStore", () => {
  let selectionStore: SelectionStore;

  beforeEach(() => {
    selectionStore = new SelectionStore();
  });

  it("should create a SelectionStore", () => {
    expect(selectionStore).toBeTruthy();
  });

  it("should set selected hospital id", () => {
    selectionStore.selectedHospitalId = 1;
    expect(selectionStore.selectedHospitalId).toEqual(1);
  });
});

describe("StoreProvider", () => {
  it("should render", () => {
    render(
      <StoreProvider>
        <></>
      </StoreProvider>
    );
  });
});
