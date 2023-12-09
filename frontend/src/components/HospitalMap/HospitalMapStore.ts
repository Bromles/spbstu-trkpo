import { makeAutoObservable } from "mobx";

class Store {
  private _selectedPlacemarkId: number;
  private _activePortal: boolean;

  constructor() {
    this._selectedPlacemarkId = -1;
    this._activePortal = false;

    makeAutoObservable(this);
  }

  get selectedPlacemarkId() {
    return this._selectedPlacemarkId;
  }

  set selectedPlacemarkId(selectedPlacemarkId: number) {
    this._selectedPlacemarkId = selectedPlacemarkId;
  }

  get activePortal() {
    return this._activePortal;
  }

  toggleActivePortal() {
    this._activePortal = !this._activePortal;
  }
}

export const HospitalMapStore = new Store();
